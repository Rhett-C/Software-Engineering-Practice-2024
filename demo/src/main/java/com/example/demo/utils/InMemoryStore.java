package com.example.demo.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryStore {
    private final Map<String, Object> store = new HashMap<>();

    public void put(String key, Object value) {
        store.put(key, value);
    }

    public Object get(String key) {
        return store.get(key);
    }

    public void remove(String key) {
        store.remove(key);
    }

    public boolean containsKey(String key) {
        return store.containsKey(key);
    }
}
