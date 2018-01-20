/*
 * Copyright (C) 2016-Present The MoonLake (mcmoonlake@hotmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mcmoonlake.api.cached

import com.mcmoonlake.api.notNull
import java.lang.ref.Reference

/**
 * ## CachedReferenceAbstract (高速缓存引用抽象)
 *
 * @see [CachedSoftRef]
 * @see [CachedWeakRef]
 * @see [CachedPhantomRef]
 * @author lgou2w
 * @since 2.0
 */
abstract class CachedReferenceAbstract<K, V, R: CachedRef<K, V>> : CachedReference<K, V, R> {

    /** member */

    private val cachedMap: MutableMap<K, R>
    private val queue: CachedReferenceQueue<V>

    /** constructor */

    /**
     * @constructor CachedReferenceAbstract
     */
    protected constructor() : this(null)

    /**
     * @constructor CachedReferenceAbstract
     *
     * @param map Cache map. If `null` then use a non-thread-safe [HashMap].
     * @param map 缓存映射. 如果为 `null` 则使用非线程安全的 [HashMap].
     */
    protected constructor(map: MutableMap<K, R>?) {
        this.cachedMap = map ?: HashMap()
        this.queue = CachedReferenceQueue()
    }

    override fun size(): Int
            = cachedMap.size

    /**
     * * Gets the value specified by this cache reference from the given key.
     * * * Created by this [produceValue] function if the value does not exist.
     * * 从给定的键获取此高速缓存引用指定的值.
     * * * 如果值不存在则通过此 [produceValue] 函数创建.
     *
     * @param key Key.
     * @param key 键.
     */
    override fun getCache(key: K): V {
        var value: V? = null
        if(cachedMap.containsKey(key)) {
            val ref = cachedMap[key]
            value = ref?.reference()?.get()
        }
        if(value == null) {
            value = produceValue(key)
            addCache(key, value)
        }
        return value.notNull()
    }

    override fun gc() {
        cachedMap.clear()
    }

    /**
     * * Produce the value from the given key.
     * * 从给定的键生产值.
     *
     * @param key Key.
     * @param key 键.
     */
    protected abstract fun produceValue(key: K): V

    /**
     * * Produce the value reference from the given key and value.
     * * 从给定的键和值生产值引用.
     *
     * @see [CachedRef]
     * @param key Key.
     * @param key 键.
     * @param value Value.
     * @param value 值.
     * @param queue Queue.
     * @param queue 队列.
     */
    protected abstract fun produceRef(key: K, value: V, queue: CachedReferenceQueue<V>): R

    /**
     * * Check the this cached queue for value reference and garbage collection.
     * * 检测此高速缓存队列的值引用以进行垃圾回收.
     */
    protected open fun checkCacheReference() {
        var ref: Reference<out V>? = null
        @Suppress("UNCHECKED_CAST")
        while (queue.poll().apply { ref = this } != null)
            cachedMap.remove((ref as R).key())
    }

    /**
     * * Remove the given key from the cached and check cache queue.
     * * 将给定的键从高速缓存中移除并检测缓存队列.
     *
     * @see [checkCacheReference]
     * @param key Key.
     * @param key 键.
     */
    protected open fun removeCache(key: K) {
        if(cachedMap.containsKey(key))
            cachedMap.remove(key)
        checkCacheReference()
    }

    private fun addCache(key: K, value: V) {
        checkCacheReference()
        val ref = produceRef(key, value, queue)
        cachedMap.put(key, ref)
    }
}
