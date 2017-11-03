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

package com.mcmoonlake.api.reflect

import java.lang.reflect.Constructor
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
    fun getConstructor(params: Array<out Class<*>>): Constructor<*>
            = getConstructor(source, params)

    @Throws(NoSuchMethodException::class)
    private fun getConstructor(clazz: Class<*>, params: Array<out Class<*>>, isPrimitive: Boolean = false): Constructor<*> {
        val primitiveTypes = if(isPrimitive) params else DataType.getPrimitive(params)
        clazz.declaredConstructors
                .filter { (forceAccess || Modifier.isPublic(it.modifiers)) && DataType.compare(it.parameterTypes, primitiveTypes) }
                .forEach { it.isAccessible = forceAccess; return it; }
        if(clazz.superclass != null)
            return getConstructor(clazz.superclass, primitiveTypes, true)
        throw NoSuchMethodException("在类 $clazz 中未找到 ${params.toList()} 的构造函数.")
    }

    @Throws(NoSuchMethodException::class)
    fun getMethod(name: String, vararg params: Class<*>): Method
            = getMethod(source, name, params)

    @Throws(NoSuchMethodException::class)
    private fun getMethod(clazz: Class<*>, name: String, params: Array<out Class<*>>, isPrimitive: Boolean = false): Method {
        val primitiveTypes = if(isPrimitive) params else DataType.getPrimitive(params)
        clazz.declaredMethods
                .filter { (forceAccess || Modifier.isPublic(it.modifiers)) && it.name == name && DataType.compare(it.parameterTypes, primitiveTypes) }
                .forEach { it.isAccessible = forceAccess; return it }
        if(clazz.superclass != null)
            return getMethod(clazz.superclass, name, primitiveTypes, true)
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

    fun forceAccess(): ExactReflect
            = ExactReflect(source, true)
}
