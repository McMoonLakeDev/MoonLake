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

interface NBTCompound : NBTBase<MutableMap<String, NBTBase<*>>>, MutableIterable<NBTBase<*>> {

    fun size(): Int

    fun getKeys(): Set<String>

    fun remove(key: String): NBTBase<*>?

    fun containsKey(key: String): Boolean

    fun <T> getValue(key: String): NBTBase<T>?

    fun getValueOfDefault(key: String, type: NBTType): NBTBase<*>

    fun <T> put(entry: NBTBase<T>): NBTCompound

    fun <T> put(key: String, value: NBTBase<T>): NBTCompound

    fun getString(key: String): String

    fun getStringOrDefault(key: String): String

    fun putString(key: String, value: String): NBTCompound

    fun getByte(key: String): Byte

    fun getByteOrDefault(key: String): Byte

    fun putByte(key: String, value: Byte): NBTCompound

    fun putByte(key: String, value: Int): NBTCompound

    fun getShort(key: String): Short

    fun getShortOrDefault(key: String): Short

    fun putShort(key: String, value: Short): NBTCompound

    fun putShort(key: String, value: Int): NBTCompound

    fun getInt(key: String): Int

    fun getIntOrDefault(key: String): Int

    fun putInt(key: String, value: Int): NBTCompound

    fun getLong(key: String): Long

    fun getLongOrDefault(key: String): Long

    fun putLong(key: String, value: Long): NBTCompound

    fun getFloat(key: String): Float

    fun getFloatOrDefault(key: String): Float

    fun putFloat(key: String, value: Float): NBTCompound

    fun getDouble(key: String): Double

    fun getDoubleOrDefault(key: String): Double

    fun putDouble(key: String, value: Double): NBTCompound

    fun getByteArray(key: String): ByteArray

    fun getByteArrayOrDefault(key: String): ByteArray

    fun putByteArray(key: String, value: ByteArray): NBTCompound

    fun getIntArray(key: String): IntArray

    fun getIntArrayOrDefault(key: String): IntArray

    fun putIntArray(key: String, value: IntArray): NBTCompound

    fun getBoolean(key: String): Boolean

    fun getBooleanOrDefault(key: String): Boolean

    fun putBoolean(key: String, value: Boolean): NBTCompound

    fun getCompound(key: String): NBTCompound

    fun getCompoundOrDefault(key: String): NBTCompound

    fun putCompound(compound: NBTCompound): NBTCompound

    fun <T> getList(key: String): NBTList<T>

    fun <T> getListOrDefault(key: String): NBTList<T>

    fun <T> putList(list: NBTList<T>): NBTCompound
}
