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

interface NBTList<T> : NBTBase<MutableList<NBTBase<T>>>, Iterable<T> {

    fun getElementType(): NBTType

    fun setElementType(type: NBTType)

    fun add(element: NBTBase<T>)

    fun addString(value: String)

    fun addByte(value: Byte)

    fun addByte(value: Int)

    fun addShort(value: Short)

    fun addShort(value: Int)

    fun addInt(value: Int)

    fun addLong(value: Long)

    fun addFloat(value: Float)

    fun addDouble(value: Double)

    fun addByteArray(value: ByteArray)

    fun addIntArray(value: IntArray)

    fun addBoolean(value: Boolean)

    fun addCompound(value: NBTCompound)

    fun remove(value: Any)

    fun getValue(index: Int): T

    fun size(): Int

    fun clear()

    fun isEmpty(): Boolean

    fun isNotEmpty(): Boolean
}
