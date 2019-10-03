package com.serverless.dto;

public class AddClientDto {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AddClientDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
