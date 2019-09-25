package com.serverless.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.serverless.client.S3Client;
import com.serverless.models.ApiGatewayResponse;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadBillFileHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        Map<String, String> params = (Map<String, String>) input.get("pathParameters");

        String accountId = params.get("accountId");
        String billId = params.get("billId");
        String path = accountId + "/" + billId + ".pdf";

        String body = (String) input.get("body");

        ByteArrayInputStream inputStream = new ByteArrayInputStream(body.getBytes());

        String bucket_name = System.getenv("BUCKET_NAME");

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("application/pdf");
        objectMetadata.setContentLength(body.length());

        S3Client.INSTANCE.putObject(bucket_name,
                path, inputStream, objectMetadata);

        String fileLocation = S3Client.INSTANCE.getBucketLocation(bucket_name) + "/" + path;

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Location", fileLocation);

        return ApiGatewayResponse.builder().setStatusCode(201).setHeaders(headers).build();

    }

}
