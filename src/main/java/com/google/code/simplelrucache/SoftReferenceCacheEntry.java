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

import java.lang.ref.SoftReference;

/**
 * Cache entry which uses SoftReference to store value
 * 
 * @author Damian Momot
 */
class SoftReferenceCacheEntry<V> implements LruCacheEntry<V> {
    private SoftReference<V> valueReference;
    private long expirationTime;
    
    /**
     * Creates LruCacheEntry with desired ttl
     *
     * @param value
     * @param ttl time to live in milliseconds
     * @throws IllegalArgumentException if ttl is not positive
     */
    SoftReferenceCacheEntry(V value, long ttl) {
        if (ttl <= 0) throw new IllegalArgumentException("ttl must be positive");

        valueReference = new SoftReference<V>(value);
        expirationTime = System.currentTimeMillis() + ttl;
    }
    
    /**
     * Returns value if entry is valid, null otherwise.
     * 
     * Entry is invalid if SoftReference is cleared or entry has expired
     * 
     * @return value if entry is valid
     */
    @Override
    public V getValue() {
        V value = null;

        //check expiration time
        if (System.currentTimeMillis() <= expirationTime) {
            value = valueReference.get();
        }

        return value;
    }
}
