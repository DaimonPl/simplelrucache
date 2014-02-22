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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Damian Momot
 */
public class CapacityLruLinkedHashMapTest {
    private CapacityLruLinkedHashMap<String, String> map;
    private static String[] keys;
    private static String[] values;
    private static int capacity;

    @Before
    public void init() {
        map = new CapacityLruLinkedHashMap<String, String>(capacity, 1, 0.75f);
    }

    @BeforeClass
    public static void setUpClass() {
        keys = new String[] {"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh"};
        values = new String[] {"aaa", "bbb", "ccc", "ddd", "eer", "fff", "ggg", "hhh"};
        capacity = 5;
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorZeroCapacityTest() {
        map = new CapacityLruLinkedHashMap<String, String>(0, 1, 0.75f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNegativeCapacityTest() {
        map = new CapacityLruLinkedHashMap<String, String>(-5, 1, 0.75f);
    }

    @Test
    public void capacityTest() {
        assertTrue(map.isEmpty());

        for (int i = 0; i < capacity; ++i) {
            map.put(keys[i], values[i]);
        }

        assertEquals(capacity, map.size());

        for (int i = capacity; i < keys.length; ++i) {
            map.put(keys[i], values[i]);
        }

        assertEquals(capacity, map.size());

        for(int i = 0; i < keys.length - capacity; ++i) {
            assertFalse(map.containsKey(keys[i]));
        }

        for(int i = keys.length - capacity; i < keys.length; ++i) {
            assertTrue(map.containsKey(keys[i]));
        }
    }
}
