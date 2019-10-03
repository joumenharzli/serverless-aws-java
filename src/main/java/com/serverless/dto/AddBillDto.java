package com.serverless.dto;

public class AddBillDto {

    private String clientId;

    private Double amount;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "AddBillDto{" +
                "clientId='" + clientId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
