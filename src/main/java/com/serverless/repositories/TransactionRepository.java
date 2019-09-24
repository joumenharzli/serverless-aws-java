package com.serverless.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.serverless.client.DynamoDBClient;
import com.serverless.domain.Transaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransactionRepository {

    private static final Log LOGGER = LogFactory.getLog(TransactionRepository.class);

    public Transaction add(Transaction transaction) {

        LOGGER.debug("Request to add transaction " + transaction);

        String id = UUID.randomUUID().toString();

        transaction.setId(id);
        transaction.setDate(ZonedDateTime.now().toString());
        DynamoDBClient.MAPPER.save(transaction);

        List<Transaction> result = findByAccountIdAndId(transaction.getAccountId(), transaction.getId());
        return result.get(0);

    }


    public List<Transaction> findByAccountId(String accountId) {

        LOGGER.debug("Request find transactions for account with id " + accountId);

        Map<String, AttributeValue> attributes = new HashMap<>();
        attributes.put(":id", new AttributeValue().withS(accountId));

        DynamoDBQueryExpression<Transaction> expression =
                new DynamoDBQueryExpression<Transaction>()
                        .withKeyConditionExpression("AccountId = :id")
                        .withExpressionAttributeValues(attributes);

        return DynamoDBClient.MAPPER.query(Transaction.class, expression);

    }

    public List<Transaction> findByAccountIdAndId(String accountId, String id) {

        LOGGER.debug("Request find transactions for account with id " + accountId + " and id " + id);

        Map<String, AttributeValue> attributes = new HashMap<>();
        attributes.put(":id", new AttributeValue().withS(id));
        attributes.put(":accountId", new AttributeValue().withS(accountId));

        DynamoDBQueryExpression<Transaction> expression =
                new DynamoDBQueryExpression<Transaction>()
                        .withKeyConditionExpression("AccountId = :accountId")
                        .withFilterExpression("Id = :id")
                        .withExpressionAttributeValues(attributes);

        return DynamoDBClient.MAPPER.query(Transaction.class, expression);

    }


}
