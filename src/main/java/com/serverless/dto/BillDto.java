package com.serverless.dto;

import java.time.Instant;
import java.util.Objects;

public class BillDto {

    private String id;

    private String clientId;

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
        BillDto bill = (BillDto) o;
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
