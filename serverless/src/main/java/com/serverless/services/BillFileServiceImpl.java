package com.serverless.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class BillFileServiceImpl implements BillFileService {

    private static final Logger LOGGER = LogManager.getLogger(BillFileServiceImpl.class);
    private static final String BUCKET_NAME = System.getenv("BUCKET_NAME");

    private final AmazonS3 s3Client;

    @Inject
    public BillFileServiceImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public URL getBillFileUrl(String clientId, String billId) {
        LOGGER.debug(() -> "Request to generate a pre-signed Url to retrieve file for client id " + clientId
                + " and bill id " + billId);
        return generatePreSignedUrl(clientId, billId, HttpMethod.GET);
    }

    @Override
    public URL getBillFileUploadUrl(String clientId, String billId) {
        LOGGER.debug(() -> "Request to generate a pre-signed Url to upload file for client id " + clientId
                + " and bill id " + billId);
        return generatePreSignedUrl(clientId, billId, HttpMethod.PUT);
    }

    private URL generatePreSignedUrl(String clientId, String billId, HttpMethod method) {
        String path = clientId + "/" + billId + ".pdf";

        Date expirationTime =
                Date.from(
                        Instant.now()
                                .plus(1, ChronoUnit.HOURS)
                );

        GeneratePresignedUrlRequest presignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME, path)
                .withMethod(method)
                .withContentType("application/pdf")
                .withExpiration(expirationTime);

        return s3Client.generatePresignedUrl(presignedUrlRequest);
    }
}
