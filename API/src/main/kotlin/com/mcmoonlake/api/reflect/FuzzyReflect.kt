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
import java.util.regex.Pattern

class FuzzyReflect(val source: Class<*>, val forceAccess: Boolean) {

    /** static */

    companion object {

        @JvmStatic
        @JvmName("fromClass")
        fun fromClass(source: Class<*>, forceAccess: Boolean = false): FuzzyReflect
                = FuzzyReflect(source, forceAccess)

        @JvmStatic
        @JvmName("fromObject")
        fun fromObject(reference: Any, forceAccess: Boolean = false): FuzzyReflect
                = FuzzyReflect(reference::class.java, forceAccess)

        @JvmStatic
        @JvmName("union")
        private fun <T> union(vararg arrays: Array<out T>): Set<T> {
            val result = LinkedHashSet<T>()
            arrays.forEach { it.forEach { result.add(it) } }
            return result
        }
    }

    /** api */

    @Throws(NoSuchMethodException::class)
    fun getConstructor(params: Array<out Class<*>>): Constructor<*> {
        val primitiveTypes = DataType.getPrimitive(params)
        getConstructors()
                .filter { DataType.compare(it.parameterTypes, primitiveTypes) }
                .forEach { return it }
        throw NoSuchMethodException("在类 $source 中未找到 ${params.toList()} 的构造函数.")
    }

    @Throws(NoSuchMethodException::class)
    fun getConstructorByParameterCount(count: Int): Constructor<*> {
        getConstructors()
                .filter { it.parameterTypes.size == count }
                .forEach { return it }
        throw NoSuchMethodException("在类 $source 中未找到参数数量为 $count 的构造函数.")
    }

    @Throws(NoSuchMethodException::class)
    fun getMethodByName(nameRegex: String): Method {
        val pattern = Pattern.compile(nameRegex)
        getMethods()
                .filter { pattern.matcher(it.name).matches() }
                .forEach { return it }
        throw NoSuchMethodException("在类 $source 中未找到具有 $nameRegex 名称匹配的函数.")
    }

    /**
     * @param name only for exception info
     */
    @Throws(NoSuchMethodException::class)
    fun getMethodByParameters(name: String?, vararg params: Class<*>): Method {
        val methodList = getMethodListByParameters(*params)
        if(methodList.isNotEmpty())
            return methodList.first()
        throw NoSuchMethodException("在类 $source 中未找到具有 $name(${params.toList()}) 的函数.")
    }

    /**
     * @param name only for exception info
     */
    @Throws(NoSuchMethodException::class)
    fun getMethodByParameters(name: String?, returnType: Class<*>, params: Array<out Class<*>>): Method {
        val methodList = getMethodListByParameters(returnType, params)
        if(methodList.isNotEmpty())
            return methodList.first()
        throw NoSuchMethodException("在类 $source 中未找到具有 $name(${params.toList()}): $returnType 的函数.")
    }

    fun getMethodListByParameters(vararg params: Class<*>): List<Method> {
        val primitiveTypes = DataType.getPrimitive(params)
        return getMethods()
                .filter { DataType.compare(it.parameterTypes, primitiveTypes) }
                .toList()
    }

    fun getMethodListByParameters(returnType: Class<*>, params: Array<out Class<*>>): List<Method> {
        val primitiveReturnType = DataType.getPrimitive(returnType)
        val primitiveTypes = DataType.getPrimitive(params)
        return getMethods()
                .filter { it.returnType == primitiveReturnType && DataType.compare(it.parameterTypes, primitiveTypes) }
                .toList()
    }

    @Throws(NoSuchFieldException::class)
    fun getFieldByName(nameRegex: String): Field {
        val pattern = Pattern.compile(nameRegex)
        getFields()
                .filter { pattern.matcher(it.name).matches() }
                .forEach { return it }
        throw NoSuchFieldException("在类 $source 中未找到具有 $nameRegex 名称匹配的字段.")
    }

    @Throws(NoSuchFieldException::class)
    fun getFieldByType(name: String?, param: Class<*>): Field {
        val fieldList = getFieldListByType(param)
        if(fieldList.isNotEmpty())
            return fieldList.first()
        throw NoSuchFieldException("在类 $source 中未找到 $name($param) 的字段.")
    }

    fun getFieldListByType(param: Class<*>): List<Field> {
        val primitiveType = DataType.getPrimitive(param)
        return getFields()
                .filter { it.type.isAssignableFrom(primitiveType) }
                .toList()
    }

    @Throws(IndexOutOfBoundsException::class)
    fun getFieldByIndex(index: Int): Field
            = getFields().elementAt(index)

    fun getConstructors(): Set<Constructor<*>> = when(forceAccess) {
        true -> union(source.declaredConstructors)
        else -> union(source.constructors)
    }

    fun getMethods(): Set<Method> = when(forceAccess) {
        true -> union(source.declaredMethods, source.methods)
        else -> union(source.methods)
    }

    fun getFields(): Set<Field> = when(forceAccess) {
        true -> union(source.declaredFields, source.fields)
        else -> union(source.fields)
    }

    fun getDeclaredFields(excludeClass: Class<*>? = null): Set<Field> = when(forceAccess) {
        true -> {
            var current: Class<*>? = source
            val fields: MutableSet<Field> = LinkedHashSet()
            while(current != null && current != excludeClass) {
                fields.addAll(current.declaredFields.toList())
                current = current.superclass
            }
            fields
        }
        else -> getFields()
    }

    fun forceAccess(): FuzzyReflect
            = FuzzyReflect(source, true)
}
