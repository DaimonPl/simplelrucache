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
 * LRU cache interface.
 * 
 * @param <K> class used as key of cache
 * @param <V> class uses as values
 * @author Damian Momot
 */
public interface LruCache<K, V> {
    /**
     * Removes all entries from cache
     */
    public void clear();
    
    /**
     * Checks whether cache contains valid entry for key
     * 
     * @param key
     * @return true if cache contains key and entry is valid
     */
    public boolean contains(K key);
    
    /**
     * Returns value cached with key.
     * 
     * @param key
     * @return value or null if key doesn't exist or entry is not valid
     */
    public V get(K key);
    
    /**
     * Tries to get element from cache. If get fails callback is used
     * to create element and returned value is stored in cache.
     * 
     * Default TTL is used
     *
     * @param key
     * @param callback
     * @return
     * @throws Exception if callback throws exception
     */
    public V get(K key, Callable<V> callback) throws Exception;
    
    /**
     * Tries to get element from cache. If get fails callback is used
     * to create element and returned value is stored in cache
     *
     * @param key
     * @param callback
     * @param ttl time to live in milliseconds
     * @return
     * @throws Exception if callback throws exception
     */
    public V get(K key, Callable<V> callback, long ttl) throws Exception;
    
    /**
     * Returns cache capacity
     *
     * @return capacity of cache
     */
    public int getCapacity();
    
    /**
     * Returns number of entries stored in cache (including invalid ones)
     * 
     * @return number of entries
     */
    public int getSize();
    
    /**
     * Returns cache TTL
     * 
     * @return ttl in milliseconds
     */
    public long getTtl();
    
    /**
     * Checks whether cache is empty.
     * 
     * If any entry exists (including invalid one) this method will return true
     * 
     * @return true if no entries are stored in cache
     */
    public boolean isEmpty();
    
    /**
     * Puts value under key into cache. Default TTL is used
     * 
     * @param key
     * @param value 
     */
    public void put(K key, V value);
    
    /**
     * Puts value under key into cache with desired TTL
     * 
     * @param key
     * @param value
     * @param ttl time to live in milliseconds
     */
    public void put(K key, V value, long ttl);
    
    /**
     * Removes entry from cache (if exists)
     * 
     * @param key 
     */
    public void remove(K key);
}
