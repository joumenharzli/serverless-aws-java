package com.serverless.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.time.Instant;
import java.util.Objects;

@DynamoDBTable(tableName = "")
public class Bill {

    private String id;

    @DynamoDBHashKey(attributeName = "ClientId")
    private String clientId;

    @DynamoDBRangeKey(attributeName = "ClientId")
    private Instant creationDate;

    private Boolean validated;

    private Double amount;

    private Boolean fileUploaded;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Boolean getFileUploaded() {
        return fileUploaded;
    }

    public void setFileUploaded(Boolean fileUploaded) {
        this.fileUploaded = fileUploaded;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return id.equals(bill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", validated=" + validated +
                ", amount=" + amount +
                ", fileUploaded=" + fileUploaded +
                '}';
    }
}
