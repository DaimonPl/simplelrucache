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
 * Modification of SynchronizedLruCache which uses SoftReference to store values
 * 
 * @author Damian Momot
 */
public class SoftReferenceSynchronizedLruCache<K, V> extends SynchronizedLruCache<K, V> {

    public SoftReferenceSynchronizedLruCache(int capacity, long ttl, int initialCapacity, float loadFactor) {
        super(capacity, ttl, initialCapacity, loadFactor);
    }

    public SoftReferenceSynchronizedLruCache(int capacity, long ttl, int initialCapacity) {
        super(capacity, ttl, initialCapacity);
    }

    public SoftReferenceSynchronizedLruCache(int capacity, long ttl) {
        super(capacity, ttl);
    }
    
    @Override
    protected LruCacheEntry<V> createEntry(V value, long ttl) {
        return new SoftReferenceCacheEntry<V>(value, ttl);
    }
}
