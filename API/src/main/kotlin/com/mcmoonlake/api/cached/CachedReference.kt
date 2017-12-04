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

/**
 * ## CachedReference (高速缓存引用)
 *
 * @see [Cached]
 * @see [CachedReferenceAbstract]
 * @author lgou2w
 * @since 2.0
 */
interface CachedReference<K, V, R: CachedRef<K, V>> : Cached {

    /**
     * * Get the cache size for this cached reference.
     * * 从此高速缓存引用获取缓存大小.
     */
    fun size(): Int

    /**
     * * Gets the value specified by this cache reference from the given key.
     * * 从给定的键获取此高速缓存引用指定的值.
     *
     * @param key Key.
     * @param key 键.
     */
    fun getCache(key: K): V
}
