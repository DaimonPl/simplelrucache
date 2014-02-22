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
 * LRU (least recently used) cache interface.
 *
 * @param <K>
 *            class used as key of cache
 * @param <V>
 *            class uses as values
 * @author Damian Momot
 */
public interface LruCache<K, V> {
	/**
	 * Removes all entries from cache
	 */
	void clear();

	/**
	 * Checks whether cache contains valid entry for key
	 *
	 * @param key
	 *            key to check
	 * @return true if cache contains key and entry is valid
	 */
	boolean contains(K key);

	/**
	 * Returns value cached with key.
	 *
	 * @param key
	 *            key to get
	 * @return value or null if key doesn't exist or entry is not valid
	 */
	V get(K key);

	/**
	 * Tries to get element from cache. If get fails, callable is used to create
	 * element and its returned value is stored in cache.
	 * <p>
	 * Default TTL is used
	 *
	 * @param key
	 *            key to get
	 * @param callable
	 *            callable which will be used to create missing/invalid item
	 * @return cached value
	 * @throws Exception
	 *             if callable throws exception
	 */
	V get(K key, Callable<V> callable) throws Exception;

	/**
	 * Tries to get element from cache. If get fails, callable is used to create
	 * element and its returned value is stored in cache.
	 *
	 * @param key
	 *            key to get
	 * @param callable
	 *            callable which will be used to create missing/invalid item
	 * @param ttl
	 *            time to live in milliseconds
	 * @return cached value
	 * @throws Exception
	 *             if callable throws exception
	 */
	V get(K key, Callable<V> callable, long ttl) throws Exception;

	/**
	 * Returns cache capacity
	 *
	 * @return capacity of cache
	 */
	long getCapacity();

	/**
	 * Returns number of entries stored in cache (including invalid ones)
	 *
	 * @return number of entries
	 */
	long getSize();

	/**
	 * Returns cache TTL
	 *
	 * @return ttl in milliseconds
	 */
	long getTtl();

	/**
	 * Checks whether cache is empty.
	 *
	 * If any entry exists (including invalid one) this method will return true
	 *
	 * @return true if no entries are stored in cache
	 */
	boolean isEmpty();

	/**
	 * Puts value under key into cache. Default TTL is used
	 *
	 * @param key
	 *            key for cache entry
	 * @param value
	 *            value to cache
	 */
	void put(K key, V value);

	/**
	 * Puts value under key into cache with desired TTL
	 *
	 * @param key
	 *            key for cache entry
	 * @param value
	 *            value to cache
	 * @param ttl
	 *            time to live in milliseconds
	 */
	void put(K key, V value, long ttl);

	/**
	 * Removes entry from cache. If entry does not exist this operation has no
	 * effect
	 *
	 * @param key
	 *            key to remove
	 */
	void remove(K key);
}
