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

package com.minecraft.moonlake.api.reflect.accessor

import com.minecraft.moonlake.api.exception.MoonLakeException
import com.minecraft.moonlake.api.reflect.ExactReflect
import com.minecraft.moonlake.api.reflect.FuzzyReflect
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
    @JvmName("getAccessorConstructor")
    @Throws(NoSuchMethodException::class, SecurityException::class)
    fun <T> getAccessorConstructor(clazz: Class<T>, forceAccess: Boolean = false, vararg params: Class<*>): AccessorConstructor<T> {
        val constructor = if(forceAccess) clazz.getDeclaredConstructor(*params) else clazz.getConstructor(*params)
        return getAccessorConstructor(constructor, forceAccess)
    }

    @JvmStatic
    @JvmName("getAccessorConstructorOrNull")
    fun <T> getAccessorConstructorOrNull(clazz: Class<T>, forceAccess: Boolean = false, vararg params: Class<*>): AccessorConstructor<T>? = try {
        getAccessorConstructor(clazz, forceAccess, *params)
    } catch(e: Exception) {
        null
    }

    @JvmStatic
    @JvmName("setAccessorConstructor")
    fun <T> setAccessorConstructor(constructor: Constructor<T>, forceAccess: Boolean = false): Constructor<T> {
        if(forceAccess && !constructor.isAccessible)
            constructor.isAccessible = true
        return constructor
    }

    @JvmStatic
    @JvmName("getAccessorMethod")
    fun getAccessorMethod(method: Method, forceAccess: Boolean = false): AccessorMethod {
        method.isAccessible = forceAccess
        return AccessorMethodSimple(method)
    }

    @JvmStatic
    @JvmName("getAccessorMethod")
    @Throws(NoSuchMethodException::class)
    fun getAccessorMethod(clazz: Class<*>, name: String, forceAccess: Boolean = false, vararg params: Class<*>): AccessorMethod
            = getAccessorMethod(ExactReflect.fromClass(clazz, forceAccess).getMethod(name, *params), forceAccess)

    @JvmStatic
    @JvmName("getAccessorMethodOrNull")
    fun getAccessorMethodOrNull(clazz: Class<*>, name: String, forceAccess: Boolean = false, vararg params: Class<*>): AccessorMethod? = try {
        getAccessorMethod(ExactReflect.fromClass(clazz, forceAccess).getMethod(name, *params), forceAccess)
    } catch(e: Exception) {
        null
    }

    @JvmStatic
    @JvmName("getAccessorMethod")
    @Throws(NoSuchMethodException::class)
    fun getAccessorMethod(clazz: Class<*>, returnType: Class<*>, forceAccess: Boolean = false, params: Array<out Class<*>>): AccessorMethod
            = getAccessorMethod(FuzzyReflect.fromClass(clazz, forceAccess).getMethodByParameters(null, returnType, params), forceAccess)

    @JvmStatic
    @JvmName("getAccessorMethodOrNull")
    fun getAccessorMethodOrNull(clazz: Class<*>, returnType: Class<*>, forceAccess: Boolean = false, params: Array<out Class<*>>): AccessorMethod? = try {
        getAccessorMethod(FuzzyReflect.fromClass(clazz, forceAccess).getMethodByParameters(null, returnType, params), forceAccess)
    } catch(e: Exception) {
        null
    }

    @JvmStatic
    @JvmName("setAccessorMethod")
    fun setAccessorMethod(method: Method, forceAccess: Boolean = false): Method {
        if(forceAccess && !method.isAccessible)
            method.isAccessible = true
        return method
    }

    @JvmStatic
    @JvmName("getAccessorField")
    fun getAccessorField(field: Field, forceAccess: Boolean = false): AccessorField {
        field.isAccessible = forceAccess
        return AccessorFieldSimple(field)
    }

    @JvmStatic
    @JvmName("getAccessorField")
    @Throws(NoSuchFieldException::class)
    fun getAccessorField(clazz: Class<*>, type: Class<*>, forceAccess: Boolean = false): AccessorField
            = getAccessorField(FuzzyReflect.fromClass(clazz, forceAccess).getFieldByType(null, type), forceAccess)

    @JvmStatic
    @JvmName("getAccessorFieldOrNull")
    fun getAccessorFieldOrNull(clazz: Class<*>, type: Class<*>, forceAccess: Boolean = false): AccessorField? = try {
        getAccessorField(FuzzyReflect.fromClass(clazz, forceAccess).getFieldByType(null, type), forceAccess)
    } catch(e: Exception) {
        null
    }

    @JvmStatic
    @JvmName("getAccessorField")
    @Throws(NoSuchFieldException::class)
    fun getAccessorField(clazz: Class<*>, name: String, forceAccess: Boolean = false): AccessorField
            = getAccessorField(ExactReflect.fromClass(clazz, forceAccess).getField(name), forceAccess)

    @JvmStatic
    @JvmName("getAccessorFieldOrNull")
    fun getAccessorFieldOrNull(clazz: Class<*>, name: String, forceAccess: Boolean = false): AccessorField? = try {
        getAccessorField(ExactReflect.fromClass(clazz, forceAccess).getField(name), forceAccess)
    } catch(e: Exception) {
        null
    }

    @JvmStatic
    @JvmName("getAccessorField")
    @Throws(IndexOutOfBoundsException::class)
    fun getAccessorField(clazz: Class<*>, index: Int, forceAccess: Boolean = false): AccessorField
            = getAccessorField(FuzzyReflect.fromClass(clazz, forceAccess).getFieldByIndex(index), forceAccess)

    @JvmStatic
    @JvmName("getAccessorFieldOrNull")
    fun getAccessorFieldOrNull(clazz: Class<*>, index: Int, forceAccess: Boolean = false): AccessorField? = try {
        getAccessorField(FuzzyReflect.fromClass(clazz, forceAccess).getFieldByIndex(index), forceAccess)
    } catch(e: Exception) {
        null
    }

    @JvmStatic
    @JvmName("setAccessorField")
    fun setAccessorField(field: Field, forceAccess: Boolean = false): Field {
        if(forceAccess && !field.isAccessible)
            field.isAccessible = true
        return field
    }

    /** inner class */

    private data class AccessorConstructorSimple<T>(private val constructor: Constructor<T>) : AccessorConstructor<T> {
        override fun newInstance(vararg params: Any?): T = try {
            constructor.newInstance(*params)
        } catch(e: Exception) {
            when(e) {
                is IllegalAccessException -> throw MoonLakeException("无法使用构造函数.", e)
                is InvocationTargetException -> throw MoonLakeException("发生内部错误.", e.cause)
                is InstantiationException -> throw MoonLakeException("无法实例化对象.", e)
                else -> e.throwMoonLake()
            }
        }

        override val accessor: Constructor<T>
            get() = constructor
    }

    private data class AccessorMethodSimple(private val method: Method) : AccessorMethod {
        override fun invoke(instance: Any?, vararg params: Any?): Any? = try {
            method.invoke(instance, *params)
        } catch(e: Exception) {
            when(e) {
                is IllegalAccessException -> throw MoonLakeException("无法使用反射器.", e)
                is InvocationTargetException -> throw MoonLakeException("发生内部错误.", e.cause)
                else -> e.throwMoonLake()
            }
        }

        override val accessor: Method
            get() = method
    }

    private data class AccessorFieldSimple(private val field: Field) : AccessorField {
        override fun get(instance: Any?): Any? = try {
            field.get(instance)
        } catch(e: Exception) {
            when(e) {
                is IllegalArgumentException -> throw MoonLakeException("无法读取字段: $field", e)
                is IllegalAccessException -> throw MoonLakeException("无法使用反射器.", e)
                else -> e.throwMoonLake()
            }
        }

        override fun set(instance: Any?, value: Any?) = try {
            field.set(instance, value)
        } catch(e: Exception) {
            when(e) {
                is IllegalArgumentException -> throw MoonLakeException("无法设置字段 $field 的值为: $value", e)
                is IllegalAccessException -> throw MoonLakeException("无法使用反射器.", e)
                else -> e.throwMoonLake()
            }
        }

        override val accessor: Field
            get() = this.field
    }
}
