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

/**
 * Modification of ConcurrentLruCache which uses SoftReference's for storing values
 * 
 * @param <K> 
 * @param <V> 
 * @author Damian Momot
 */
public class SoftReferenceConcurrentLruCache<K, V> extends ConcurrentLruCache<K, V> {

    public SoftReferenceConcurrentLruCache(int capacity, long ttl, int initialCapacity, int concurrencyLevel) {
        super(capacity, ttl, initialCapacity, concurrencyLevel);
    }

    public SoftReferenceConcurrentLruCache(int capacity, long ttl, int initialCapacity) {
        super(capacity, ttl, initialCapacity);
    }

    public SoftReferenceConcurrentLruCache(int capacity, long ttl) {
        super(capacity, ttl);
    }
    
    @Override
    protected LruCacheEntry<V> createEntry(V value, long ttl) {
        return new SoftReferenceCacheEntry<V>(value, ttl);
    }
}
