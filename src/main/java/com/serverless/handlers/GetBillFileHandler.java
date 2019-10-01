package com.serverless.handlers;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.serverless.client.S3Client;
import com.serverless.models.ApiGatewayResponse;

import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GetBillFileHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        Map<String, String> params = (Map<String, String>) input.get("pathParameters");
        String clientId = params.get("clientId");
        String billId = params.get("billId");
        String path = clientId + "/" + billId + ".pdf";

        String bucketName = System.getenv("BUCKET_NAME");

        Date expirationTime =
                Date.from(
                        Instant.now()
                                .plus(1, ChronoUnit.HOURS)
                );

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, path)
                .withMethod(HttpMethod.GET)
                .withExpiration(expirationTime);

        URL url = S3Client.INSTANCE.generatePresignedUrl(generatePresignedUrlRequest);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Location", url.toString());

        return ApiGatewayResponse.builder()
                .setStatusCode(302)
                .setHeaders(headers)
                .build();

    }

}
