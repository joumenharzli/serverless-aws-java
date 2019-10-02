package com.serverless.handlers.utils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class LambdaUtils {

    @SuppressWarnings("unchecked")
    public static Map<String, String> parsePathParameters(Map<String, Object> request) {

        Objects.requireNonNull(request, "request cannot be null");

        return Optional.ofNullable(request.get("pathParameters"))
                .filter(params -> params instanceof Map)
                .map(params -> (Map<String, String>) params)
                .orElseThrow(() -> new UnsupportedOperationException("Request is not a valid Api Gateway Request"));

    }


}
