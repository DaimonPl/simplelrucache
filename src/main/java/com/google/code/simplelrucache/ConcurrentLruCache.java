/**
 * Copyright 2011 Damian Momot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.code.simplelrucache;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;

/**
 * Threadsafe highly concurrent implementation of LruCache based on
 * ConcurrentLinkedHashMap.
 * This implementation should be used with big number of threads.
 * 
 * @param <K> key type
 * @param <V> value type
 * @author Damian Momot
 */
public class ConcurrentLruCache<K, V> extends BaseLruCache<K, V> {
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    
    private ConcurrentLinkedHashMap<K, LruCacheEntry<V>> cacheMap;
    
    /**
     * Creates new concurrent LRU cache
     * 
     * @param capacity
     * @param ttl
     * @param initialCapacity 
     * @param concurrencyLevel 
     */
    public ConcurrentLruCache(int capacity, long ttl, int initialCapacity, int concurrencyLevel) {
        super(ttl);
        
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be positive");
        
        cacheMap = new ConcurrentLinkedHashMap.Builder<K, LruCacheEntry<V>>()
            .maximumWeightedCapacity(capacity)
            .weigher(Weighers.singleton())
            .initialCapacity(initialCapacity)
            .concurrencyLevel(concurrencyLevel)
            .build();
    }
    
    /**
     * Creates new ConcurrentLruCache with DEFAULT_CONCURRENCY_LEVEL
     * 
     * @param capacity
     * @param ttl
     * @param initialCapacity 
     */
    public ConcurrentLruCache(int capacity, long ttl, int initialCapacity) {
        this(capacity, ttl, initialCapacity, DEFAULT_CONCURRENCY_LEVEL);
    }
    
    /**
     * Creates new ConcurrentLruCache with DEFAULT_CONCURRENCY_LEVEL and
     * DEFAULT_INITIAL_CAPACITY
     * 
     * @param capacity
     * @param ttl 
     */
    public ConcurrentLruCache(int capacity, long ttl) {
        this(capacity, ttl, DEFAULT_INITIAL_CAPACITY, DEFAULT_CONCURRENCY_LEVEL);
    }

    @Override
    public void clear() {
        cacheMap.clear();
    }
    
    @Override
    public int getCapacity() {
        return cacheMap.capacity();
    }
    
    @Override
    protected LruCacheEntry<V> getEntry(K key) {
        return cacheMap.get(key);
    }
    
    @Override
    public int getSize() {
        return cacheMap.size();
    }
    
    @Override
    protected void putEntry(K key, LruCacheEntry<V> entry) {
        cacheMap.put(key, entry);
    }

    @Override
    public void remove(K key) {
        cacheMap.remove(key);
    }
}
