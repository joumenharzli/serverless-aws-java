package com.serverless.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.serverless.domain.Bill;
import com.serverless.utils.HashMapBuilder;
import com.serverless.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.UUID;

public class BillRepositoryImpl implements BillRepository {

    private static final Logger LOGGER = LogManager.getLogger(BillRepositoryImpl.class);
    private static final String TABLE_NAME = System.getenv("BILLS_TABLE_NAME");
    private static final String ID_ATTRIBUTE = "ClientId";

    private static final DynamoDBMapperConfig.Builder DEFAULT_CONFIG =
            DynamoDBMapperConfig.builder()
                    .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(TABLE_NAME));

    private final IDynamoDBMapper dbMapper;

    @Inject
    public BillRepositoryImpl(IDynamoDBMapper dbMapper) {
        this.dbMapper = dbMapper;
    }

    @Override
    public Bill save(Bill bill) {
        LOGGER.debug(() -> "DynamoDB request to save bill " + bill);

        if (StringUtils.isEmpty(bill.getId())) {
            bill.setId(UUID.randomUUID().toString());
        }

        dbMapper.save(bill, DEFAULT_CONFIG.build());
        return bill;
    }

    @Override
    public Page<Bill, String> findByClientId(String clientId, Integer limit, String lastEvaluatedKey) {
        LOGGER.debug(() -> "DynamoDB request to fetch bills for client " + clientId + " with limit " + limit
                + " and last evaluated key " + lastEvaluatedKey);


        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression().withLimit(limit);

        if (StringUtils.isNotEmpty(lastEvaluatedKey)) {
            queryExpression.withExclusiveStartKey(
                    HashMapBuilder.singleElement(ID_ATTRIBUTE, new AttributeValue(lastEvaluatedKey))
            );
        }

        queryExpression.withKeyConditionExpression(ID_ATTRIBUTE + " = :v_id")
                .withExpressionAttributeValues(new ValueMap()
                        .withString(":v_id", clientId));

        QueryResultPage<Bill> scan = dbMapper.queryPage(Bill.class, queryExpression, DEFAULT_CONFIG.build());
        return new Page<>(scan.getResults(), scan.getLastEvaluatedKey().get(ID_ATTRIBUTE).getS());

    }

    @Override
    public Page<Bill, String> findAll(Integer limit, String lastEvaluatedKey) {
        LOGGER.debug(() -> "DynamoDB request to fetch bills with limit " + limit
                + " and last evaluated key " + lastEvaluatedKey);

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withLimit(limit);

        if (StringUtils.isNotEmpty(lastEvaluatedKey)) {
            scanExpression.withExclusiveStartKey(
                    HashMapBuilder.singleElement(ID_ATTRIBUTE, new AttributeValue(lastEvaluatedKey))
            );
        }

        ScanResultPage<Bill> scan = dbMapper.scanPage(Bill.class, scanExpression, DEFAULT_CONFIG.build());
        return new Page<>(scan.getResults(), scan.getLastEvaluatedKey().get(ID_ATTRIBUTE).getS());

    }


}
