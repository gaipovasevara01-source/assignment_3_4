package com.example.demo.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache {

    private static final InMemoryCache INSTANCE = new InMemoryCache();

    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    private InMemoryCache() {}

    public static InMemoryCache getInstance() {
        return INSTANCE;
    }

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        return cache.get(key);
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public void clearAll() {
        cache.clear();
    }

    public boolean contains(String key) {
        return cache.containsKey(key);
    }
}
