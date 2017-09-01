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

package com.minecraft.moonlake.api.reflect.accessor

import com.minecraft.moonlake.api.exception.MoonLakeException
import com.minecraft.moonlake.api.throwMoonLake
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

object Accessors {

    /** api */

    @JvmStatic
    @JvmName("getAccessorConstructor")
    fun <T> getAccessorConstructor(constructor: Constructor<T>, forceAccess: Boolean = false): AccessorConstructor<T> {
        constructor.isAccessible = forceAccess
        return AccessorConstructorSimple(constructor)
    }

    @JvmStatic
    @JvmName("getAccessorMethod")
    fun getAccessorMethod(method: Method, forceAccess: Boolean = false): AccessorMethod {
        method.isAccessible = forceAccess
        return AccessorMethodSimple(method)
    }

    @JvmStatic
    @JvmName("getAccessorField")
    fun getAccessorField(field: Field, forceAccess: Boolean = false): AccessorField {
        field.isAccessible = forceAccess
        return AccessorFieldSimple(field)
    }

    /** inner class */

    private data class AccessorConstructorSimple<T>(private val constructor: Constructor<T>) : AccessorConstructor<T> {
        override fun newInstance(vararg params: Any): T = try {
            constructor.newInstance(params)
        } catch(e: Exception) {
            when(e) {
                is IllegalAccessException -> throw MoonLakeException("无法使用构造函数.", e)
                is InvocationTargetException -> throw MoonLakeException("发生内部错误.", e.cause)
                is InstantiationException -> throw MoonLakeException("无法实例化对象.", e)
                else -> e.throwMoonLake()
            }
        }

        override fun getAccessor(): Constructor<T>
                = constructor
    }

    private data class AccessorMethodSimple(private val method: Method) : AccessorMethod {
        override fun invoke(instance: Any?, vararg params: Any): Any = try {
            method.invoke(instance, params)
        } catch(e: Exception) {
            when(e) {
                is IllegalAccessException -> throw MoonLakeException("无法使用反射器.", e)
                is InvocationTargetException -> throw MoonLakeException("发生内部错误.", e.cause)
                else -> e.throwMoonLake()
            }
        }

        override fun getAccessor(): Method
                = method
    }

    private data class AccessorFieldSimple(private val field: Field) : AccessorField {
        override fun get(instance: Any?): Any = try {
            field.get(instance)
        } catch(e: Exception) {
            when(e) {
                is IllegalArgumentException -> throw MoonLakeException("无法读取字段: $field", e)
                is IllegalAccessException -> throw MoonLakeException("无法使用反射器.", e)
                else -> e.throwMoonLake()
            }
        }

        override fun set(instance: Any?, value: Any) = try {
            field.set(instance, value)
        } catch(e: Exception) {
            when(e) {
                is IllegalArgumentException -> throw MoonLakeException("无法设置字段 $field 的值为: $value", e)
                is IllegalAccessException -> throw MoonLakeException("无法使用反射器.", e)
                else -> e.throwMoonLake()
            }
        }

        override fun getAccessor(): Field
                = field
    }
}
