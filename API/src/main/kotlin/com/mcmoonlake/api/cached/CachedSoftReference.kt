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

import java.lang.ref.SoftReference

/**
 * ## CachedSoftReference (高速缓存软引用值)
 *
 * @see [CachedRef]
 * @see [SoftReference]
 * @author lgou2w
 * @since 2.0
 * @constructor CachedSoftReference
 * @param key Key.
 * @param key 键.
 * @param referent Value of referent.
 * @param referent 值引用.
 * @param queue Reference queue of value referent.
 * @param queue 值引用队列.
 */
open class CachedSoftReference<out K, V>(
        private val key: K,
        referent: V,
        queue: CachedReferenceQueue<in V>) : SoftReference<V>(referent, queue), CachedRef<K, V> {

    override fun reference(): SoftReference<V>
            = this

    override fun key(): K
            = key
}
