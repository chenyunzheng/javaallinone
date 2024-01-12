package com.chris.allinone.algo.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> {

    private LinkedHashMap<K, V> linkedHashMap;

    public LRUCache(int capacity) {
        this.linkedHashMap = new LinkedHashMap<K, V>(capacity, 0.75f, false) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        };
    }

    public V get(K key) {
        return linkedHashMap.get(key);
    }

    public void put(K key, V value) {
        linkedHashMap.put(key, value);
    }

    public void printAllKeys() {
        linkedHashMap.keySet().forEach(System.out::println);
    }

    public static void main(String[] args) {
        LRUCache<String, String> lruCache = new LRUCache<>(3);
        lruCache.put("1","1");
        lruCache.put("2","2");
        lruCache.put("3","3");
        lruCache.put("4","4");
        lruCache.put("5","5");
        lruCache.put("6","6");
        lruCache.printAllKeys();
    }
}
