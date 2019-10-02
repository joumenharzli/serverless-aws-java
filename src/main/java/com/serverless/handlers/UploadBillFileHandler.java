package com.serverless.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.config.DaggerApplicationComponent;
import com.serverless.handlers.utils.HashMapBuilder;
import com.serverless.handlers.utils.LambdaUtils;
import com.serverless.models.ApiGatewayResponse;
import com.serverless.services.BillFileService;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UploadBillFileHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final BillFileService billFileService;

    public UploadBillFileHandler() {
        this.billFileService = DaggerApplicationComponent.create().billFileService();
    }

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        Map<String, String> params = LambdaUtils.parsePathParameters(input);
        String clientId = params.get("clientId");
        String billId = params.get("billId");

        URL url = billFileService.getBillFileUploadUrl(clientId, billId);

        HashMap<String, String> body = HashMapBuilder.<String, String>builder()
                .put("uploadURL", url.toString())
                .build();

        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(body)
                .build();

    }

}
