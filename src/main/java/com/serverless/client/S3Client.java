package com.serverless.client;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class S3Client {

    public static AmazonS3 INSTANCE;

    static {
        INSTANCE = AmazonS3ClientBuilder.defaultClient();
    }


}
