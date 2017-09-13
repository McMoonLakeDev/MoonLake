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

package com.minecraft.moonlake.api.depend

class DependPlugins private constructor() {

    private val cached: MutableMap<Class<out DependPlugin>, DependPlugin> = HashMap() // interface class : impl instance
    private val implements: MutableMap<Class<out DependPlugin>, Class<out DependPluginAbstract<*>>> = HashMap() // interface class : impl class

    companion object {

        @JvmStatic
        private val instance: DependPlugins by lazy(DependPlugins::class.java) { DependPlugins() }

        @JvmStatic
        @JvmName("of")
        @Synchronized
        @Throws(DependPluginException::class)
        fun <T: DependPlugin> of(clazz: Class<T>): T {
            var depend = instance.cached[clazz]
            if(depend == null) {
                val implClazz = instance.implements[clazz]
                if(implClazz == null || !implClazz.interfaces.contains(clazz))
                    throw DependPluginException("依赖插件接口类不存在实现类或实现类未实现接口.")
                depend = implClazz.newInstance()
                instance.cached.put(clazz, depend)
            }
            if(depend != null && clazz.isInstance(depend))
                return clazz.cast(depend)
            throw DependPluginException("无法依赖插件.")
        }

        @JvmStatic
        @JvmName("ofSafe")
        @Synchronized
        fun <T: DependPlugin> ofSafe(clazz: Class<T>): T? = try {
            of(clazz)
        } catch(e: DependPluginException) {
            null
        }

        @JvmStatic
        @JvmName("register")
        @Synchronized
        @Throws(DependPluginException::class)
        fun <T: DependPlugin> register(clazz: Class<T>, implClazz: Class<out DependPluginAbstract<*>>) {
            if(!implClazz.interfaces.contains(clazz))
                throw DependPluginException("依赖插件实现类未实现接口类.")
            instance.implements.put(clazz, implClazz)
        }
    }
}
