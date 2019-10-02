package com.serverless.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.IDynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.serverless.domain.Client;
import com.serverless.utils.HashMapBuilder;
import com.serverless.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.UUID;

public class ClientRepositoryImpl implements ClientRepository {

    private static final Logger LOGGER = LogManager.getLogger(ClientRepositoryImpl.class);
    private static final String TABLE_NAME = System.getenv("CLIENTS_TABLE_NAME");
    private static final String ID_ATTRIBUTE = "Id";

    private static final DynamoDBMapperConfig.Builder DEFAULT_CONFIG =
            DynamoDBMapperConfig.builder()
                    .withTableNameOverride(TableNameOverride.withTableNameReplacement(TABLE_NAME));

    private final IDynamoDBMapper dbMapper;

    @Inject
    public ClientRepositoryImpl(IDynamoDBMapper dbMapper) {
        this.dbMapper = dbMapper;
    }

    @Override
    public Client save(Client client) {
        LOGGER.debug(() -> "DynamoDB request to save client " + client);

        if (StringUtils.isEmpty(client.getId())) {
            client.setId(UUID.randomUUID().toString());
        }

        dbMapper.save(client, DEFAULT_CONFIG.build());
        return client;
    }

    @Override
    public Page<Client, String> findAll(Integer limit, String lastEvaluatedKey) {
        LOGGER.debug(() -> "DynamoDB request to fetch clients with limit " + limit
                + " and last evaluated key " + lastEvaluatedKey);

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withLimit(limit);

        if (StringUtils.isNotEmpty(lastEvaluatedKey)) {
            scanExpression.withExclusiveStartKey(
                    HashMapBuilder.singleElement(ID_ATTRIBUTE, new AttributeValue(lastEvaluatedKey))
            );
        }

        ScanResultPage<Client> scan = dbMapper.scanPage(Client.class, scanExpression, DEFAULT_CONFIG.build());
        return new Page<>(scan.getResults(), scan.getLastEvaluatedKey().get(ID_ATTRIBUTE).getS());

    }
}
