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

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Capacity based LRU version of LinkedHashMap
 * 
 * @param <K> map key class
 * @param <V> map value class
 * @author Damian Momot
 */
class CapacityLruLinkedHashMap<K, V> extends LinkedHashMap<K, V> {   
    private int capacity;
    
    /**
     * Creates LRU LinkedHashMap
     * 
     * @param capacity
     * @param initialCapacity
     * @param loadFactor
     * @throws IllegalArgumentException if capacity is not positive
     */
    protected CapacityLruLinkedHashMap(int capacity, int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor, true);
        
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be positive");
        
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > capacity;
    }
    
    /**
     * Returns capacity of map
     * 
     * @return 
     */
    public int getCapacity() {
        return this.capacity;
    }
}
