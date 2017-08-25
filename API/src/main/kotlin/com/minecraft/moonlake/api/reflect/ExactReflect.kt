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

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier

class ExactReflect(val source: Class<*>, val forceAccess: Boolean) {

    /** static */

    companion object {

        @JvmStatic
        @JvmName("fromClass")
        fun fromClass(source: Class<*>, forceAccess: Boolean = false): ExactReflect
                = ExactReflect(source, forceAccess)

        @JvmStatic
        @JvmName("fromObject")
        fun fromObject(reference: Any, forceAccess: Boolean = false): ExactReflect
                = ExactReflect(reference::class.java, forceAccess)
    }

    /** api */

    @Throws(NoSuchMethodException::class)
    fun getMethod(name: String, vararg params: Class<*>): Method
            = getMethod(source, name, params)

    @Throws(NoSuchMethodException::class)
    private fun getMethod(clazz: Class<*>, name: String, params: Array<out Class<*>>): Method {
        val primitiveTypes = DataType.getPrimitive(params)
        clazz.declaredMethods
                .filter { (forceAccess || Modifier.isPublic(it.modifiers)) && it.name == name && DataType.compare(it.parameterTypes, primitiveTypes) }
                .forEach { it.isAccessible = forceAccess; return it }
        if(clazz.superclass != null)
            return getMethod(clazz.superclass, name, params)
        throw NoSuchMethodException("在类 $clazz 中未找到名为 $name(${params.toList()}) 的函数.")
    }

    @Throws(NoSuchFieldException::class)
    fun getField(name: String): Field
            = getField(source, name)

    @Throws(NoSuchFieldException::class)
    private fun getField(clazz: Class<*>, name: String): Field {
        clazz.declaredFields
                .filter { it.name == name }
                .forEach { it.isAccessible = forceAccess; return it }
        if(clazz.superclass != null)
            return getField(clazz.superclass, name)
        throw NoSuchFieldException("在类 $clazz 中未找到名为 $name 的字段.")
    }

    @Throws(IndexOutOfBoundsException::class)
    fun getFieldByIndex(index: Int): Field {
        val field = source.declaredFields[index]
        field.isAccessible = forceAccess
        return field
    }

    fun forceAccess(): ExactReflect
            = ExactReflect(source, true)
}
