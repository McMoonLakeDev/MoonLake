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

enum class NBTType(val rawId: Int, valueType: Class<*>) {

    /** enums */

    TAG_END(0, java.lang.Void::class.java),
    TAG_BYTE(1, java.lang.Byte.TYPE),
    TAG_SHORT(2, java.lang.Short.TYPE),
    TAG_INT(3, java.lang.Integer.TYPE),
    TAG_LONG(4, java.lang.Long.TYPE),
    TAG_FLOAT(5, java.lang.Float.TYPE),
    TAG_DOUBLE(6, java.lang.Double.TYPE),
    TAG_BYTE_ARRAY(7, ByteArray::class.java),
    TAG_STRING(8, java.lang.String::class.java),
    TAG_LIST(9, java.util.List::class.java),
    TAG_COMPOUND(10, java.util.Map::class.java),
    TAG_INT_ARRAY(11, IntArray::class.java),
    ;

    /** static */

    companion object {

        @JvmStatic
        private val ID_MAP: MutableMap<Int, NBTType> = HashMap()

        init {
            values().forEach {
                ID_MAP.put(it.rawId, it)
            }
        }

        @JvmStatic
        @JvmName("fromId")
        @Throws(IllegalArgumentException::class)
        fun fromId(rawId: Int): NBTType
                = ID_MAP[rawId] ?: throw IllegalArgumentException("未知的 NBT 类型 Id: $rawId")
    }
}
