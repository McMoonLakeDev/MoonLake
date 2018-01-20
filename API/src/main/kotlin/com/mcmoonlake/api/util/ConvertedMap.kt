/*
 *  ProtocolLib - Bukkit server library that allows access to the Minecraft protocol.
 *  Copyright (C) 2012 Kristian S. Stangeland
 *
 *  This program is free software; you can redistribute it and/or modify it under the terms of the
 *  GNU General Public License as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this program;
 *  if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 *  02111-1307 USA
 */

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

package com.mcmoonlake.api.util

import com.google.common.collect.Collections2

abstract class ConvertedMap <K, VI, VO>(
        private val inner: MutableMap<K, VI>
) : ConvertedAbstract<VI, VO>(),
        MutableMap<K, VO> {

    private val outConverter: (key: K, inner: VI) -> VO = { key, inner ->  toOut(key, inner) }
    private val inConverter: (key: K, outer: VO) -> VI = { key, outer ->  toIn(key, outer) }

    override fun clear()
            = inner.clear()

    override fun containsKey(key: K): Boolean
            = inner.containsKey(key)

    override fun containsValue(value: VO): Boolean
            = inner.containsValue(toIn(value))

    protected open fun toOut(key: K, inner: VI): VO
            = toOut(inner)

    protected open fun toIn(key: K, outer: VO): VI
            = toIn(outer)

    override fun get(key: K): VO? {
        val get = inner[key]
        return if(get == null)
            null
        else
            toOut(key, get)
    }

    override fun isEmpty(): Boolean
            = inner.isEmpty()

    override val keys: MutableSet<K>
        get() = inner.keys

    override val values: MutableCollection<VO>
        get() = Collections2.transform(entries, { input -> input?.value })

    override val entries: MutableSet<MutableMap.MutableEntry<K, VO>>
        get() = object: ConvertedSet<MutableMap.MutableEntry<K, VI>, MutableMap.MutableEntry<K, VO>>(inner.entries) {
            override fun toOut(inner: MutableMap.MutableEntry<K, VI>): MutableMap.MutableEntry<K, VO> {
                return object: MutableMap.MutableEntry<K, VO> {
                    override val key: K
                        get() = inner.key
                    override val value: VO
                        get() = outConverter(key, inner.value)
                    override fun setValue(newValue: VO): VO
                            = outConverter(key, inner.setValue(inConverter(key, value)))
                }
            }
            override fun toIn(outer: MutableMap.MutableEntry<K, VO>): MutableMap.MutableEntry<K, VI> {
                return object: MutableMap.MutableEntry<K, VI> {
                    override val key: K
                        get() = outer.key
                    override val value: VI
                        get() = inConverter(key, outer.value)
                    override fun setValue(newValue: VI): VI
                            = inConverter(key, outer.setValue(outConverter(key, value)))
                }
            }
        }

    override fun put(key: K, value: VO): VO? {
        val put = inner.put(key, toIn(key, value))
        return if(put == null)
            null
        else
            toOut(key, put)
    }

    override fun putAll(from: Map<out K, VO>)
            = from.entries.forEach { put(it.key, it.value) }

    override fun remove(key: K): VO? {
        val remove = inner.remove(key)
        return if(remove == null)
            null
        else
            toOut(key, remove)
    }

    override val size: Int
        get() = inner.size
}
