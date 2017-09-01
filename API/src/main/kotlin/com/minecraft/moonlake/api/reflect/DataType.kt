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

package com.minecraft.moonlake.api.reflect

enum class DataType(val primitive: Class<*>, val reference: Class<*>) {

    /** enums */

    VOID(java.lang.Void.TYPE, java.lang.Void::class.java),
    BYTE(java.lang.Byte.TYPE, java.lang.Byte::class.java),
    SHORT(java.lang.Short.TYPE, java.lang.Short::class.java),
    INTEGER(java.lang.Integer.TYPE, java.lang.Integer::class.java),
    LONG(java.lang.Long.TYPE, java.lang.Long::class.java),
    CHARACTER(java.lang.Character.TYPE, java.lang.Character::class.java),
    FLOAT(java.lang.Float.TYPE, java.lang.Float::class.java),
    DOUBLE(java.lang.Double.TYPE, java.lang.Double::class.java),
    BOOLEAN(java.lang.Boolean.TYPE, java.lang.Boolean::class.java),
    ;

    /** static */

    companion object {

        @JvmStatic
        private val CLASS_MAP: MutableMap<Class<*>, DataType> = HashMap()

        init {
            DataType.values().forEach {
                CLASS_MAP.put(it.primitive, it)
                CLASS_MAP.put(it.reference, it)
            }
        }

        @JvmStatic
        @JvmName("fromClass")
        fun fromClass(clazz: Class<*>): DataType?
                = CLASS_MAP[clazz]

        @JvmStatic
        @JvmName("getPrimitive")
        fun getPrimitive(clazz: Class<*>): Class<*>
                = fromClass(clazz)?.primitive ?: clazz

        @JvmStatic
        @JvmName("getReference")
        fun getReference(clazz: Class<*>): Class<*>
                = fromClass(clazz)?.reference ?: clazz

        @JvmStatic
        @JvmName("getPrimitive")
        fun getPrimitive(clazzs: Array<out Class<*>>): Array<out Class<*>>
                = clazzs.map { it -> getPrimitive(it) }.toTypedArray()

        @JvmStatic
        @JvmName("getReference")
        fun getReference(clazzs: Array<out Class<*>>): Array<out Class<*>>
                = clazzs.map { it -> getReference(it) }.toTypedArray()

        @JvmStatic
        @JvmName("getPrimitive")
        fun getPrimitive(clazzs: Array<out Any>): Array<out Class<*>>
                = clazzs.map { it -> getPrimitive(it.javaClass) }.toTypedArray()

        @JvmStatic
        @JvmName("getReference")
        fun getReference(clazzs: Array<out Any>): Array<out Class<*>>
                = clazzs.map { it -> getReference(it.javaClass) }.toTypedArray()

        @JvmStatic
        @JvmName("compare")
        fun compare(left: Array<out Class<*>>, right: Array<out Class<*>>): Boolean = (left.size != right.size).let {
            when(it) {
                true -> false
                else ->  {
                    (0 until left.size)
                            .filter { left[it] != right[it] && !left[it].isAssignableFrom(right[it]) }
                            .forEach { return false }
                    return true
                }
            }
        }
    }
}