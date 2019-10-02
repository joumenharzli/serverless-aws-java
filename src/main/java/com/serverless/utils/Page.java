package com.serverless.utils;

import java.util.List;
import java.util.Objects;

public class Page<T, K> {

    private List<T> elements;
    private K lastEvaluatedKey;

    public Page(List<T> elements, K lastEvaluatedKey) {
        this.elements = elements;
        this.lastEvaluatedKey = lastEvaluatedKey;
    }

    public List<T> getElements() {
        return elements;
    }

    public Integer getCount() {
        return elements.size();
    }

    public K getLastEvaluatedKey() {
        return lastEvaluatedKey;
    }

    public Boolean isLastPage() {
        return Objects.nonNull(lastEvaluatedKey);
    }

    @Override
    public String toString() {
        return "Page{" +
                "elements=" + elements +
                ", lastEvaluatedKey='" + lastEvaluatedKey + '\'' +
                '}';
    }
}
