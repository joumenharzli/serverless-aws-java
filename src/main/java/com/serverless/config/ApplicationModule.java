package com.serverless.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.IDynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.serverless.services.BillFileService;
import com.serverless.services.BillFileServiceImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    public IDynamoDBMapper dynamoDBMapper() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .build();

        return new DynamoDBMapper(client);
    }

    @Provides
    @Singleton
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.defaultClient();

    }

    @Provides
    @Singleton
    public BillFileService billFileService(AmazonS3 amazonS3) {
        return new BillFileServiceImpl(amazonS3);
    }


}
