package com.serverless.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.domain.Transaction;
import com.serverless.dto.AddTransactionDto;
import com.serverless.models.ApiGatewayResponse;
import com.serverless.models.Response;
import com.serverless.repositories.TransactionRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

public class AddTransactionHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final TransactionRepository TRANSACTION_REPOSITORY = new TransactionRepository();
    private static final Log LOGGER = LogFactory.getLog(AddTransactionHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        LOGGER.debug("REST request to add transaction");

        Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");

        String accountId = pathParameters.get("accountId");

        try {
            AddTransactionDto dto = new ObjectMapper().readValue((String) input.get("body"), AddTransactionDto.class);

            Transaction transaction = new Transaction();
            transaction.setAccountId(accountId);
            transaction.setAmount(dto.getAmount());

            Transaction persistedTransaction = TRANSACTION_REPOSITORY.add(transaction);

            return ApiGatewayResponse.builder()
                    .setStatusCode(201)
                    .setObjectBody(persistedTransaction)
                    .build();

        } catch (Exception e) {
            LOGGER.error("Cannot add transaction", e);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(new Response("Internal Server Error"))
                    .build();
        }

    }


}
