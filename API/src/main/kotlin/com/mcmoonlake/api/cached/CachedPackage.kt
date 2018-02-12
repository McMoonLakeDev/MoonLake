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

package com.mcmoonlake.api.cached

import com.mcmoonlake.api.exception.MoonLakeException
import com.mcmoonlake.api.reflect.ClassSource
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * ## CachedPackage (高速缓存包)
 *
 * @see [Cached]
 * @author lgou2w
 * @since 2.0
 */
class CachedPackage : Cached {

    /** member */

    private val cachedMap: MutableMap<String, Optional<Class<*>>>
    private val source: ClassSource
    private val packageName: String

    /** constructor */

    /**
     * CachedPackage
     *
     * @param packageName The target package name.
     * @param packageName 目标包名.
     * @param source The target class source.
     * @param source 目标类源.
     */
    constructor(packageName: String, source: ClassSource) {
        this.cachedMap = ConcurrentHashMap()
        this.packageName = packageName
        this.source = source
    }

    /** api */

    /**
     * * Get the class of the current cached package from the given class name.
     * * 从给定的类名获取当前高速缓存包的类.
     *
     * @param className Class name.
     * @param className 类名称.
     * @throws MoonLakeException If the class does not exist.
     * @throws MoonLakeException 如果类不存在.
     */
    @Throws(MoonLakeException::class)
    fun getPackageClass(className: String): Class<*> {
        if(cachedMap.containsKey(className)) {
            val result = cachedMap[className]
            if(result == null || !result.isPresent)
                throw MoonLakeException("无法查找到类: $className")
            return result.get()
        }
        try {
            val clazz = source.loadClass(combine(packageName, className))
            cachedMap[className] = Optional.of(clazz)
            return clazz
        } catch(e: ClassNotFoundException) {
            cachedMap[className] = Optional.empty()
            throw MoonLakeException("无法查找到类: $className", e)
        }
    }

    /**
     * * Sets the class for this given class name for this cache package. Removed if [clazz] is `null`.
     * * 设置此高速缓存包的给定类名的类. 如果 [clazz] 为 `null` 则移除.
     *
     * @param className Class name.
     * @param className 类名称.
     * @param clazz Class.
     * @param clazz 类.
     */
    fun setPackageClass(className: String, clazz: Class<*>?) {
        if(clazz != null)
            cachedMap[className] = Optional.of(clazz)
        else
            cachedMap.remove(className)
    }

    override fun gc() {
        cachedMap.clear()
    }

    /** implement */

    private fun combine(packageName: String, className: String): String {
        if(packageName.isEmpty())
            return className
        if(className.isEmpty())
            return packageName
        return "$packageName.$className"
    }
}
