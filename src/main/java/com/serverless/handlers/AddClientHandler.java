package com.serverless.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.config.DaggerApplicationComponent;
import com.serverless.dto.AddClientDto;
import com.serverless.dto.ClientDto;
import com.serverless.handlers.models.ApiGatewayResponse;
import com.serverless.handlers.models.Response;
import com.serverless.services.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

public class AddClientHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger LOGGER = LogManager.getLogger(AddClientHandler.class);

    private final ClientService clientService;

    public AddClientHandler() {
        this.clientService = DaggerApplicationComponent.create().clientService();
    }

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        LOGGER.debug(() -> "Lambda request to upload bill file with request " + input);

        try {
            AddClientDto addClientDto = new ObjectMapper().readValue((String) input.get("body"), AddClientDto.class);
            ClientDto addedClientDto = clientService.add(addClientDto);
            return ApiGatewayResponse.builder()
                    .setStatusCode(201)
                    .setObjectBody(addedClientDto)
                    .build();

        } catch (IOException e) {
            LOGGER.error(e);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(new Response("Internal Server Error"))
                    .build();
        }

    }

}
