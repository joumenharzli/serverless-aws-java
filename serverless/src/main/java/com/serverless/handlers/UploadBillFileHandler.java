package com.serverless.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.config.DaggerApplicationComponent;
import com.serverless.handlers.models.ApiGatewayResponse;
import com.serverless.handlers.utils.LambdaUtils;
import com.serverless.services.BillFileService;
import com.serverless.utils.HashMapBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UploadBillFileHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger LOGGER = LogManager.getLogger(UploadBillFileHandler.class);

    private final BillFileService billFileService;

    public UploadBillFileHandler() {
        this.billFileService = DaggerApplicationComponent.create().billFileService();
    }

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        LOGGER.debug(() -> "Lambda request to upload bill file with request " + input);

        Map<String, String> params = LambdaUtils.parsePathParameters(input);
        String clientId = params.get("clientId");
        String billId = params.get("billId");

        URL url = billFileService.getBillFileUploadUrl(clientId, billId);

        HashMap<String, String> body = HashMapBuilder.singleElement("uploadURL", url.toString());

        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(body)
                .build();

    }

}
