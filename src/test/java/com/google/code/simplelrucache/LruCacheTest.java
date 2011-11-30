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

import org.junit.* ;
import static org.junit.Assert.* ;

/**
 *
 * @author Damian Momot
 */
abstract public class LruCacheTest {
    private class SimpleCallback implements Callable<String> {
        public static final String str = "callbackResult";
        
        @Override
        public String call() throws Exception {
            return str;
        }
        
    }
    
    private static String[] keys;
    private static String[] values;
    private static String key;
    private static String value;
    private static int capacity;
    private static long ttl;
    
    abstract protected LruCache<String, String> createCache(int capacity, long ttl);
    
    private void insertData(LruCache cache) {
        for (int i = 0; i < keys.length; ++i) {
            cache.put(keys[i], values[i]);
        }
    }
    
    @BeforeClass
    public static void setUpClass() {
        keys = new String[] {"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh"};
        values = new String[] {"aaa", "bbb", "ccc", "ddd", "eer", "fff", "ggg", "hhh"};
        key = "cc";
        value = "ccc";
        capacity = keys.length * 2;
        ttl = 3600 * 1000;
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void constructorZeroCapacityTest() {
        createCache(0, ttl);
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorNegativeCapacityTest() {
        createCache(-5, ttl);
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorZeroTtlTest() {
        createCache(capacity, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorNegativeTtlTest() {
        createCache(capacity, -5);
    }
    
    @Test
    public void clearTest() {
        LruCache<String, String> cache = createCache(capacity, ttl);
        insertData(cache);
        cache.clear();
        
        assertEquals(0, cache.getSize());
        for (int i = 0; i < keys.length; ++i) {
            assertFalse(cache.contains(keys[i]));
        }
    }
    
    @Test
    public void containsTest() {
        LruCache<String, String> cache = createCache(capacity, ttl);
        insertData(cache);
        
        for (int i = 0; i < keys.length; ++i) {
            assertTrue(cache.contains(keys[i]));
        }
        
        assertFalse(cache.contains("a"));
        assertFalse(cache.contains("b"));
        assertFalse(cache.contains("c"));
        assertFalse(cache.contains("d"));
    }
    
    @Test
    public void getPutTest() {
        LruCache<String, String> cache = createCache(capacity, ttl);
        
        cache.put(key, value);
        
        assertEquals(value, cache.get(key));
    }
    
    @Test
    public void getCallbackTest() throws Exception {
        LruCache<String, String> cache = createCache(capacity, ttl);
        
        String val = cache.get(key, new SimpleCallback());
        
        assertEquals(SimpleCallback.str, val);
    }
    
    @Test
    public void getCallbackTtl() throws Exception {
        LruCache<String, String> cache = createCache(capacity, ttl);
        String val = cache.get(key, new SimpleCallback(), 10);

        assertTrue(cache.contains(key));
        assertEquals(SimpleCallback.str, cache.get(key));

        Thread.sleep(20);

        assertFalse(cache.contains(key));
        assertNull(cache.get(key));
    }
    
    @Test
    public void getCapacityTest() {       
        LruCache<String, String> cache = createCache(capacity, ttl);
        
        assertEquals(capacity, cache.getCapacity());
    }
    
    @Test
    public void getSizeTest() {
        LruCache<String, String> cache = createCache(capacity, ttl);
        
        insertData(cache);
        assertEquals(keys.length, cache.getSize());

        //retest
        insertData(cache);
        assertEquals(keys.length, cache.getSize());

        cache.clear();

        assertEquals(0, cache.getSize());
    }
    
    @Test
    public void getTtlTest() {       
        LruCache<String, String> cache = createCache(capacity, ttl);
        
        assertEquals(ttl, cache.getTtl());
    }
    
    @Test
    public void isEmptyTest() {       
        LruCache<String, String> cache = createCache(capacity, ttl);
        
        assertTrue(cache.isEmpty());
        
        insertData(cache);
        cache.clear();
        assertTrue(cache.isEmpty());
    }
    
    @Test
    public void putTtlTest() throws InterruptedException {
        LruCache<String, String> cache = createCache(capacity, ttl);
        cache.put(key, value, 10);

        assertTrue(cache.contains(key));
        assertEquals(value, cache.get(key));

        Thread.sleep(20);

        assertFalse(cache.contains(key));
        assertNull(cache.get(key));
    }

    @Test
    public void nullsNotStoredTest() {
        LruCache<String, String> cache = createCache(capacity, ttl);
        
        for (int i = 0; i < keys.length; ++i) {
            cache.put(keys[i], null);
        }

        assertEquals(0, cache.getSize());

        for (int i = 0; i < keys.length; ++i) {
            assertFalse(cache.contains(keys[i]));
        }
    }
}
