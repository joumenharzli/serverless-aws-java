package com.serverless.utils;

import java.util.HashMap;

public class HashMapBuilder<K, V> {

    private HashMap<K, V> map;

    private HashMapBuilder() {
        map = new HashMap<>();
    }

    public static <K, V> HashMapBuilder<K, V> builder() {
        return new HashMapBuilder<>();
    }

    public static <K, V> HashMap<K, V> singleElement(K key, V value) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public HashMapBuilder<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    public HashMap<K, V> build() {
        return map;
    }


}
