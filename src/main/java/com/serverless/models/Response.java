package com.serverless.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public class Response {

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Map<String, Object> input;

    public Response(String message) {
        this(message, null);
    }

    public Response(String message, Map<String, Object> input) {
        this.message = message;
        this.input = input;
    }

    public String getMessage() {
        return this.message;
    }

    public Map<String, Object> getInput() {
        return this.input;
    }
}
