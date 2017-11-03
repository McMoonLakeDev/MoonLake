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

package com.mcmoonlake.api.nbt

import com.mcmoonlake.api.exception.MoonLakeException
import com.mcmoonlake.api.notNull
import com.mcmoonlake.api.reflect.FuzzyReflect
import com.mcmoonlake.api.reflect.StructureModifier
import com.mcmoonlake.api.utility.MinecraftReflection
import java.lang.reflect.Method

class NBTWrappedElement<T>(override val handle: Any, override var name: String) : NBTWrapper<T> {

    /** member */

    private var _type: NBTType? = null

    override val type: NBTType
        get() {
            if(_type == null) try {
                _type = NBTType.fromId((nbtBaseGetTypeId.invoke(handle) as Byte).toInt())
            } catch (e: Exception) {
                throw MoonLakeException("无法获取 $handle 的 NBT 类型.", e)
            }
            return _type.notNull()
        }

    fun getElementType(): NBTType {
        val elementType = currentModifier().withTarget<Any>(handle).withType<Byte>(Byte::class.java).read(0)
        return NBTType.fromId(elementType?.toInt() ?: 0)
    }

    fun setElementType(type: NBTType) {
        val elementType = type.rawId.toByte()
        currentModifier().withTarget<Any>(handle).withType<Byte>(Byte::class.java).write(0, elementType)
    }

    override var value: T
        @Suppress("UNCHECKED_CAST")
        get() = currentModifier().withTarget<T>(handle).read(0) as T
        set(value) { currentModifier().withTarget<T>(handle).write(0, value) }

    private fun currentModifier(): StructureModifier<*> {
        val index = type.ordinal
        var modifier = modifiers[index]
        if(modifier == null) synchronized(this) {
            if(modifiers[index] == null)
                modifiers[index] = StructureModifier.of(handle::class.java, MinecraftReflection.getNBTBaseClass())
            modifier = modifiers[index]
        }
        return modifier.notNull()
    }

    /** significant */

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is NBTBase<*>)
            return other.type == type && other.value == value
        return false
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (value?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return buildString {
            append("{")
            when(type) {
                NBTType.TAG_STRING -> append("\"$value\"")
                else -> append("$value")
            }
            append("}")
        }
    }

    override fun toMojangson(): String { // TODO v1.13
        return buildString {
            append("{")
            when(type) {
                NBTType.TAG_STRING -> append("\"$value\"")
                NBTType.TAG_BYTE -> append("${value}b") // byte = b
                NBTType.TAG_SHORT -> append("${value}s") // short = s
                NBTType.TAG_INT -> append("$value") // int = N/A
                NBTType.TAG_LONG -> append("${value}l") // long = l
                NBTType.TAG_FLOAT -> append("${value}f") // float = f
                NBTType.TAG_DOUBLE -> append("${value}d") // double = d
                else -> append("$value")
            }
            append("}")
        }
    }

    /** static */

    companion object {

        @JvmStatic
        private val nbtBaseGetTypeId: Method by lazy {
            FuzzyReflect.fromClass(MinecraftReflection.getNBTBaseClass())
                    .getMethodByParameters("getTypeId", Byte::class.java, arrayOf())
        }

        /*@JvmStatic
        private val nbtBaseClone: Method by lazy {
            val nbtBaseClass = MinecraftReflection.getNBTBaseClass()
            FuzzyReflect.fromClass(nbtBaseClass)
                    .getMethodByParameters("clone", nbtBaseClass, arrayOf())
        }*/

        @JvmStatic
        private val modifiers: Array<StructureModifier<*>?> = arrayOfNulls(NBTType.values().size)
    }
}
