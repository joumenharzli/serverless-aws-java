package com.serverless.utils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Page<T, K> {

    private List<T> elements;
    private K lastEvaluatedKey;

    public Page(List<T> elements, K lastEvaluatedKey) {
        this.elements = elements;
        this.lastEvaluatedKey = lastEvaluatedKey;
    }

    public <J> Page<J, K> map(Function<T, J> fn) {
        List<J> mappedElements = elements.stream().map(fn).collect(Collectors.toList());
        return new Page<>(mappedElements, lastEvaluatedKey);
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
