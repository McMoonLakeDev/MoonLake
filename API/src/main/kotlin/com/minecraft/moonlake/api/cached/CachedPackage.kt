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

package com.minecraft.moonlake.api.cached

import com.minecraft.moonlake.api.exception.MoonLakeException
import com.minecraft.moonlake.api.reflect.ClassSource
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class CachedPackage : Cached {

    /** member */

    private val cachedMap: MutableMap<String, Optional<Class<*>>>
    private val source: ClassSource
    private val packageName: String

    /** constructor */

    constructor(packageName: String, source: ClassSource) {
        this.cachedMap = ConcurrentHashMap()
        this.packageName = packageName
        this.source = source
    }

    /** api */

    @Throws(MoonLakeException::class)
    fun getPackageClass(className: String): Class<*> {
        if(cachedMap.containsKey(className)) {
            val result = cachedMap[className]!!
            if(!result.isPresent)
                throw MoonLakeException("无法查找到类: $className")
            return result.get()
        }
        try {
            val clazz = source.loadClass(combine(packageName, className))
            cachedMap.put(className, Optional.of(clazz))
            return clazz
        } catch(e: ClassNotFoundException) {
            cachedMap.put(className, Optional.empty())
            throw MoonLakeException("无法查找到类: $className", e)
        }
    }

    fun setPackageClass(className: String, clazz: Class<*>?) {
        if(clazz != null)
            cachedMap.put(className, Optional.of(clazz))
        else
            cachedMap.remove(className)
    }

    override fun gc() {
        this.cachedMap.clear()
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
