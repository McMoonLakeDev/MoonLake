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

abstract class ClassSource {

    /** api */

    @Throws(ClassNotFoundException::class)
    abstract fun loadClass(name: String): Class<*>

    /** static */

    companion object {

        @JvmStatic
        @JvmName("fromClassLoader")
        fun fromClassLoader(): ClassSource
                = fromClassLoader(ClassSource::class.java.classLoader)

        @JvmStatic
        @JvmName("fromClassLoader")
        fun fromClassLoader(classLoader: ClassLoader): ClassSource = object: ClassSource() {
            override fun loadClass(name: String): Class<*>
                    = classLoader.loadClass(name)
        }

        @JvmStatic
        @JvmName("fromMap")
        fun fromMap(map: Map<String, Class<*>>): ClassSource = object: ClassSource() {
            override fun loadClass(name: String): Class<*>
                    = map[name] ?: throw ClassNotFoundException("指定类没有存在此 Map 中.")
        }
    }
}
