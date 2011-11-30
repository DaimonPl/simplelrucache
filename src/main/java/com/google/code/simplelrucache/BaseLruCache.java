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

import java.util.concurrent.Callable;

/**
 * Base class for concrete implementations
 * 
 * @author Damian Momot
 */
abstract class BaseLruCache<K, V> implements LruCache<K, V> {
    private long ttl;

    protected BaseLruCache(long ttl) {
        if (ttl <= 0) throw new IllegalArgumentException("ttl must be positive");
        
        this.ttl = ttl;
    }
    
    @Override
    public boolean contains(K key) {
        //can't use contains because of expiration policy
        V value = getValue(key);

        return value != null;
    }
    
    /**
     * Creates new LruCacheEntry<V>
     * 
     * @param key
     * @param value
     * @param ttl
     * @return 
     */
    protected LruCacheEntry<V> createEntry(V value, long ttl) {
        return new SoftReferenceCacheEntry<V>(value, ttl);
    }
    
    @Override
    public V get(K key) {
        return getValue(key);
    }
    
    @Override
    public V get(K key, Callable<V> callback) throws Exception {
        return get(key, callback, ttl);
    }
    
    @Override
    public V get(K key, Callable<V> callback, long ttl) throws Exception {
        V value = getValue(key);

        //if element doesn't exist create it using callback
        if (value == null) {
            value = callback.call();
            put(key, value, ttl);
        }

        return value;
    }
    
    @Override
    public long getTtl() {
        return ttl;
    }
    
    /**
     * Returns LruCacheEntry mapped by key or null if it does not exist
     * 
     * @param key
     * @return 
     */
    abstract protected LruCacheEntry<V> getEntry(K key);
    
    /**
     * Tries to retrieve value by it's key. Automatically removes entry if
     * it's ttl has passed or reference has been cleared
     *
     * @param key
     * @return
     */
    protected V getValue(K key) {
        V value = null;

        LruCacheEntry<V> cacheEntry = getEntry(key);

        if (cacheEntry != null) {
            value = cacheEntry.getValue();

            //autoremove entry from map if it's not valid anymore
            if (value == null) remove(key);
        }

        return value;
    }
    
    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public void put(K key, V value) {
        put(key, value, ttl);
    }

    @Override
    public void put(K key, V value, long ttl) {
        if (value != null) putEntry(key, createEntry(value, ttl));
    }
    
    /**
     * Puts value as LruCacheEntry
     *
     * @param key
     * @param value
     * @param ttl
     */
    abstract protected void putEntry(K key, LruCacheEntry<V> entry);
}
