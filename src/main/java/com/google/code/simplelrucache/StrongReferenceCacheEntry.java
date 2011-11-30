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
 * Cache entry which uses normal (strong) reference to store value
 * 
 * @author Damian Momot
 */
class StrongReferenceCacheEntry<V> implements LruCacheEntry<V> {
    private V value;
    private long expirationTime;
    
    /**
     * Creates StrongReferencyCacheEntry with desired ttl
     *
     * @param value
     * @param ttl time to live in milliseconds
     * @throws IllegalArgumentException if ttl is not positive
     */
    StrongReferenceCacheEntry(V value, long ttl) {
        if (ttl <= 0) throw new IllegalArgumentException("ttl must be positive");

        this.value = value;
        expirationTime = System.currentTimeMillis() + ttl;
    }
    
    /**
     * Returns value if entry is valid, null otherwise.
     * 
     * Entry is invalid if it's expired
     * 
     * @return value if entry is valid
     */
    @Override
    public V getValue() {
        if (System.currentTimeMillis() > expirationTime) return null;
        else return value;
    }
}
