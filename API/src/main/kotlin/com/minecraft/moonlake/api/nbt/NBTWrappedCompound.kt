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

package com.minecraft.moonlake.api.nbt

import com.minecraft.moonlake.api.exception.MoonLakeException
import com.minecraft.moonlake.api.util.ConvertedMap

class NBTWrappedCompound(handle: Any, name: String) : NBTWrapper<MutableMap<String, NBTBase<*>>>, NBTCompound {

    private val container: NBTWrappedElement<MutableMap<String, Any>> = NBTWrappedElement(handle, name)
    private val savedMap: ConvertedMap<String, Any, NBTBase<*>> by lazy {
        object: ConvertedMap<String, Any, NBTBase<*>>(container.value) {
            override fun toIn(outer: NBTBase<*>): Any
                    = NBTFactory.fromBase(outer).handle
            override fun toOut(inner: Any): NBTBase<*>
                    = NBTFactory.fromNMS<Any>(inner)
            override fun toOut(key: String, inner: Any): NBTBase<*>
                    = NBTFactory.fromNMS<Any>(inner, key)
            override fun toString(): String
                    = this@NBTWrappedCompound.toString()
        }
    }

    override val handle: Any
        get() = container.handle

    override var name: String
        get() = container.name
        set(value) { container.name = value }

    override val type: NBTType
        get() = NBTType.TAG_COMPOUND

    override var value: MutableMap<String, NBTBase<*>>
        get() = savedMap
        set(value) { value.entries.forEach { put(it.value) } }

    override fun size(): Int
            = value.size

    override fun getKeys(): Set<String>
            = value.keys

    override fun remove(key: String): NBTBase<*>?
            = value.remove(key)

    override fun containsKey(key: String): Boolean
            = value.containsKey(key)

    @Suppress("UNCHECKED_CAST")
    override fun <T> getValue(key: String): NBTBase<T>?
            = value[key] as NBTBase<T>?

    private fun <T> getValueExact(key: String): NBTBase<T>
            = getValue(key) ?: throw MoonLakeException("无法查找到键为 $key 的值.")

    override fun getValueOfDefault(key: String, type: NBTType): NBTBase<*>
            = getValueOfDefault0<Any>(key, type)

    private fun <T> getValueOfDefault0(key: String, type: NBTType): NBTBase<T> {
        var nbt = getValue<T>(key)
        if(nbt == null) {
            nbt = NBTFactory.ofWrapper(type, key)
            put(nbt)
        } else if(nbt.type != type) {
            throw MoonLakeException("无法获取 NBT 对象 $nbt, 错误的类型: $type")
        }
        return nbt
    }

    private fun <T> getValueOfNull(key: String): NBTBase<T>? {
        if(!containsKey(key))
            return null
        return getValue(key)
    }

    override fun <T> put(entry: NBTBase<T>): NBTCompound
            { value.put(entry.name, entry); return this; }

    override fun <T> put(key: String, value: NBTBase<T>): NBTCompound
            { this.value.put(key, value); return this; }

    override fun getString(key: String): String
            = getValueExact<String>(key).value

    override fun getStringOrNull(key: String): String?
            = getValue<String>(key)?.value

    override fun getStringOrDefault(key: String): String
            = getValueOfDefault0<String>(key, NBTType.TAG_STRING).value

    override fun putString(key: String, value: String): NBTCompound
            { this.value.put(key, NBTFactory.of(key, value)); return this; }

    override fun getByte(key: String): Byte
            = getValueExact<Byte>(key).value

    override fun getByteOrNull(key: String): Byte?
            = getValue<Byte>(key)?.value

    override fun getByteOrDefault(key: String): Byte
            = getValueOfDefault0<Byte>(key, NBTType.TAG_BYTE).value

    override fun putByte(key: String, value: Byte): NBTCompound
            { this.value.put(key, NBTFactory.of(key, value)); return this; }

    override fun putByte(key: String, value: Int): NBTCompound
            = putByte(key, value.toByte())

    override fun getShort(key: String): Short
            = getValueExact<Short>(key).value

    override fun getShortOrNull(key: String): Short?
            = getValue<Short>(key)?.value

    override fun getShortOrDefault(key: String): Short
            = getValueOfDefault0<Short>(key, NBTType.TAG_SHORT).value

    override fun putShort(key: String, value: Short): NBTCompound
            { this.value.put(key, NBTFactory.of(key, value)); return this; }

    override fun putShort(key: String, value: Int): NBTCompound
            = putShort(key, value.toShort())

    override fun getInt(key: String): Int
            = getValueExact<Int>(key).value

    override fun getIntOrNull(key: String): Int?
            = getValue<Int>(key)?.value

    override fun getIntOrDefault(key: String): Int
            = getValueOfDefault0<Int>(key, NBTType.TAG_INT).value

    override fun putInt(key: String, value: Int): NBTCompound
            { this.value.put(key, NBTFactory.of(key, value)); return this; }

    override fun getLong(key: String): Long
            = getValueExact<Long>(key).value

    override fun getLongOrNull(key: String): Long?
            = getValue<Long>(key)?.value

    override fun getLongOrDefault(key: String): Long
            = getValueOfDefault0<Long>(key, NBTType.TAG_LONG).value

    override fun putLong(key: String, value: Long): NBTCompound
            { this.value.put(key, NBTFactory.of(key, value)); return this; }

    override fun getFloat(key: String): Float
            = getValueExact<Float>(key).value

    override fun getFloatOrNull(key: String): Float?
            = getValue<Float>(key)?.value

    override fun getFloatOrDefault(key: String): Float
            = getValueOfDefault0<Float>(key, NBTType.TAG_FLOAT).value

    override fun putFloat(key: String, value: Float): NBTCompound
            { this.value.put(key, NBTFactory.of(key, value)); return this; }

    override fun getDouble(key: String): Double
            = getValueExact<Double>(key).value

    override fun getDoubleOrNull(key: String): Double?
            = getValue<Double>(key)?.value

    override fun getDoubleOrDefault(key: String): Double
            = getValueOfDefault0<Double>(key, NBTType.TAG_DOUBLE).value

    override fun putDouble(key: String, value: Double): NBTCompound
            { this.value.put(key, NBTFactory.of(key, value)); return this; }

    override fun getByteArray(key: String): ByteArray
            = getValueExact<ByteArray>(key).value

    override fun getByteArrayOrNull(key: String): ByteArray?
            = getValue<ByteArray>(key)?.value

    override fun getByteArrayOrDefault(key: String): ByteArray
            = getValueOfDefault0<ByteArray>(key, NBTType.TAG_BYTE_ARRAY).value

    override fun putByteArray(key: String, value: ByteArray): NBTCompound
            { this.value.put(key, NBTFactory.of(key, value)); return this; }

    override fun getIntArray(key: String): IntArray
            = getValueExact<IntArray>(key).value

    override fun getIntArrayOrNull(key: String): IntArray?
            = getValue<IntArray>(key)?.value

    override fun getIntArrayOrDefault(key: String): IntArray
            = getValueOfDefault0<IntArray>(key, NBTType.TAG_INT_ARRAY).value

    override fun putIntArray(key: String, value: IntArray): NBTCompound
            { this.value.put(key, NBTFactory.of(key, value)); return this; }

    override fun getBoolean(key: String): Boolean
            = getByte(key) == 1.toByte()

    override fun getBooleanOrNull(key: String): Boolean?
            = getByteOrNull(key).let { if(it == null) null else it == 1.toByte() }

    override fun getBooleanOrFalse(key: String): Boolean
            = getByteOrNull(key).let { if(it == null) false else it == 1.toByte() }

    override fun getBooleanOrDefault(key: String): Boolean
            = getByteOrDefault(key) == 1.toByte()

    override fun putBoolean(key: String, value: Boolean): NBTCompound
            = putByte(key, (if(value) 1 else 0).toByte())

    override fun getCompound(key: String): NBTCompound
            = getValueExact<NBTCompound>(key) as NBTCompound

    override fun getCompoundOrNull(key: String): NBTCompound?
            = getValueOfNull<NBTCompound>(key) as NBTCompound?

    override fun getCompoundOrDefault(key: String): NBTCompound
            = getValueOfDefault0<NBTCompound>(key, NBTType.TAG_COMPOUND) as NBTCompound

    override fun putCompound(compound: NBTCompound): NBTCompound
            { this.value.put(compound.name, compound); return this; }

    @Suppress("UNCHECKED_CAST")
    override fun <T> getList(key: String): NBTList<T>
            = getValueExact<NBTList<T>>(key) as NBTList<T>

    @Suppress("UNCHECKED_CAST")
    override fun <T> getListOrNull(key: String): NBTList<T>?
            = getValueOfNull<NBTList<T>>(key) as NBTList<T>?

    @Suppress("UNCHECKED_CAST")
    override fun <T> getListOrDefault(key: String): NBTList<T>
            = getValueOfDefault0<NBTList<T>>(key, NBTType.TAG_LIST) as NBTList<T>

    override fun <T> putList(list: NBTList<T>): NBTCompound
            { this.value.put(list.name, list); return this; }

    override fun isEmpty(): Boolean
            = size() <= 0

    override fun isNotEmpty(): Boolean
            = !isEmpty()

    override fun iterator(): MutableIterator<NBTBase<*>>
            = value.values.map { NBTFactory.fromBase(it) }.toMutableList().iterator()

    /** significant */

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is NBTWrappedCompound)
            return other.container == container
        return false
    }

    override fun hashCode(): Int {
        return container.hashCode()
    }

    override fun toString(): String {
        return buildString {
            append("{")
            val size = size()
            this@NBTWrappedCompound.forEachIndexed { index, it ->
                if(it.type == NBTType.TAG_STRING)
                    append("\"${it.name}\":\"${it.value}\"")
                else
                    append("\"${it.name}\":${it.value}")
                if(index != size - 1) append(",")
            }
            append("}")
        }
    }
}
