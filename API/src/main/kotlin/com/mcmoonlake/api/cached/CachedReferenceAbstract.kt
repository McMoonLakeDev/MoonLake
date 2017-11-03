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

abstract class CachedReferenceAbstract<K, V, R: CachedRef<K, V>> : CachedReference<K, V, R> {

    /** member */

    private val cachedMap: MutableMap<K, R>
    private val queue: CachedReferenceQueue<V>

    /** constructor */

    protected constructor() : this(null)
    protected constructor(map: MutableMap<K, R>?) {
        this.cachedMap = map ?: HashMap()
        this.queue = CachedReferenceQueue()
    }

    override fun size(): Int
            = cachedMap.size

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
        this.cachedMap.clear()
    }

    protected abstract fun produceValue(key: K): V

    protected abstract fun produceRef(key: K, value: V, queue: CachedReferenceQueue<V>): R

    @Suppress("UNCHECKED_CAST")
    open protected fun removeCache() {
        var ref: Reference<out V>? = null
        while (queue.poll().apply { ref = this } != null)
            cachedMap.remove((ref as R).key())
    }

    open protected fun removeCache(key: K) {
        if(cachedMap.containsKey(key))
            cachedMap.remove(key)
        removeCache()
    }

    private fun addCache(key: K, value: V) {
        removeCache()
        val ref = produceRef(key, value, queue)
        cachedMap.put(key, ref)
    }
}
