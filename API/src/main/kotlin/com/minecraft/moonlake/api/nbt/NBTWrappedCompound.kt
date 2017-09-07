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
 * Copyright (C) 2016-2017 The MoonLake (mcmoonlake@hotmail.com)
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
        object: ConvertedMap<String, Any, NBTBase<*>>(container.getValue()) {
            override fun toIn(outer: NBTBase<*>): Any
                    = NBTFactory.fromBase(outer).getHandle()
            override fun toOut(inner: Any): NBTBase<*>
                    = NBTFactory.fromNMS<Any>(inner)
            override fun toOut(key: String, inner: Any): NBTBase<*>
                    = NBTFactory.fromNMS<Any>(inner, key)
            override fun toString(): String
                    = this@NBTWrappedCompound.toString()
        }
    }

    /** api */

    override fun getHandle(): Any
            = container.getHandle()

    override fun getName(): String
            = container.getName()

    override fun setName(name: String)
            { container.setName(name) }

    override fun getType(): NBTType
            = NBTType.TAG_COMPOUND

    override fun getValue(): MutableMap<String, NBTBase<*>>
            = savedMap

    override fun setValue(value: MutableMap<String, NBTBase<*>>)
            = value.entries.forEach { put(it.value) }

    override fun size(): Int
            = getValue().size

    override fun getKeys(): Set<String>
            = getValue().keys

    override fun remove(key: String): NBTBase<*>?
            = getValue().remove(key)

    override fun containsKey(key: String): Boolean
            = getValue().containsKey(key)

    @Suppress("UNCHECKED_CAST")
    override fun <T> getValue(key: String): NBTBase<T>?
            = getValue()[key] as NBTBase<T>

    private fun <T> getValueExact(key: String): NBTBase<T>
            = getValue(key) ?: throw MoonLakeException("无法查找到键为 $key 的值.")

    override fun getValueOfDefault(key: String, type: NBTType): NBTBase<*>
            = getValueOfDefault0<Any>(key, type)

    private fun <T> getValueOfDefault0(key: String, type: NBTType): NBTBase<T> {
        var nbt = getValue<T>(key)
        if(nbt == null) {
            nbt = NBTFactory.ofWrapper(type, key)
            put(nbt)
        } else if(nbt.getType() != type) {
            throw MoonLakeException("无法获取 NBT 对象 $nbt, 错误的类型: $type")
        }
        return nbt
    }

    override fun <T> put(entry: NBTBase<T>): NBTCompound
            { getValue().put(entry.getName(), entry); return this; }

    override fun <T> put(key: String, value: NBTBase<T>): NBTCompound
            { getValue().put(key, value); return this; }

    override fun getString(key: String): String
            = getValueExact<String>(key).getValue()

    override fun getStringOrDefault(key: String): String
            = getValueOfDefault0<String>(key, NBTType.TAG_STRING).getValue()

    override fun put(key: String, value: String): NBTCompound
            { getValue().put(key, NBTFactory.of(key, value)); return this; }

    override fun getByte(key: String): Byte
            = getValueExact<Byte>(key).getValue()

    override fun getByteOrDefault(key: String): Byte
            = getValueOfDefault0<Byte>(key, NBTType.TAG_BYTE).getValue()

    override fun put(key: String, value: Byte): NBTCompound
            { getValue().put(key, NBTFactory.of(key, value)); return this; }

    override fun getShort(key: String): Short
            = getValueExact<Short>(key).getValue()

    override fun getShortOrDefault(key: String): Short
            = getValueOfDefault0<Short>(key, NBTType.TAG_SHORT).getValue()

    override fun put(key: String, value: Short): NBTCompound
            { getValue().put(key, NBTFactory.of(key, value)); return this; }

    override fun getInteger(key: String): Int
            = getValueExact<Int>(key).getValue()

    override fun getIntegerOrDefault(key: String): Int
            = getValueOfDefault0<Int>(key, NBTType.TAG_INT).getValue()

    override fun put(key: String, value: Int): NBTCompound
            { getValue().put(key, NBTFactory.of(key, value)); return this; }

    override fun getLong(key: String): Long
            = getValueExact<Long>(key).getValue()

    override fun getLongOrDefault(key: String): Long
            = getValueOfDefault0<Long>(key, NBTType.TAG_LONG).getValue()

    override fun put(key: String, value: Long): NBTCompound
            { getValue().put(key, NBTFactory.of(key, value)); return this; }

    override fun getFloat(key: String): Float
            = getValueExact<Float>(key).getValue()

    override fun getFloatOrDefault(key: String): Float
            = getValueOfDefault0<Float>(key, NBTType.TAG_FLOAT).getValue()

    override fun put(key: String, value: Float): NBTCompound
            { getValue().put(key, NBTFactory.of(key, value)); return this; }

    override fun getDouble(key: String): Double
            = getValueExact<Double>(key).getValue()

    override fun getDoubleOrDefault(key: String): Double
            = getValueOfDefault0<Double>(key, NBTType.TAG_DOUBLE).getValue()

    override fun put(key: String, value: Double): NBTCompound
            { getValue().put(key, NBTFactory.of(key, value)); return this; }

    override fun getByteArray(key: String): ByteArray
            = getValueExact<ByteArray>(key).getValue()

    override fun getByteArrayOrDefault(key: String): ByteArray
            = getValueOfDefault0<ByteArray>(key, NBTType.TAG_BYTE_ARRAY).getValue()

    override fun put(key: String, value: ByteArray): NBTCompound
            { getValue().put(key, NBTFactory.of(key, value)); return this; }

    override fun getIntegerArray(key: String): IntArray
            = getValueExact<IntArray>(key).getValue()

    override fun getIntegerArrayOrDefault(key: String): IntArray
            = getValueOfDefault0<IntArray>(key, NBTType.TAG_INT_ARRAY).getValue()

    override fun put(key: String, value: IntArray): NBTCompound
            { getValue().put(key, NBTFactory.of(key, value)); return this; }

    override fun getBoolean(key: String): Boolean
            = getByte(key) == 1.toByte()

    override fun getBooleanOrDefault(key: String): Boolean
            = getByteOrDefault(key) == 1.toByte()

    override fun put(key: String, value: Boolean): NBTCompound
            = put(key, (if(value) 1 else 0).toByte())

    override fun getCompound(key: String): NBTCompound
            = getValueExact<NBTCompound>(key).getValue()

    override fun getCompoundOrDefault(key: String): NBTCompound
            = getValueOfDefault0<NBTCompound>(key, NBTType.TAG_COMPOUND).getValue()

    override fun put(compound: NBTCompound): NBTCompound
            { getValue().put(compound.getName(), compound); return this; }

    override fun <T> getList(key: String): NBTList<T>
            = getValueExact<NBTList<T>>(key).getValue()

    override fun <T> getListOrDefault(key: String): NBTList<T>
            = getValueOfDefault0<NBTList<T>>(key, NBTType.TAG_LIST).getValue()

    override fun <T> put(list: NBTList<T>): NBTCompound
            { getValue().put(list.getName(), list); return this; }

    override fun iterator(): MutableIterator<NBTBase<*>>
            = getValue().values.iterator()

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
                if(it.getType() == NBTType.TAG_STRING)
                    append("\"${it.getName()}\":\"${it.getValue()}\"")
                else
                    append("\"${it.getName()}\":${it.getValue()}")
                if(index != size - 1) append(",")
            }
            append("}")
        }
    }
}
