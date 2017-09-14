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

    private val names: MutableMap<String, Class<out DependPlugin>> = HashMap() // name : interface class
    private val values: MutableMap<Class<out DependPlugin>, DependPlugin> = HashMap() // interface class : impl instance
    private val implements: MutableMap<Class<out DependPlugin>, Class<out DependPluginAbstract<*>>> = HashMap() // interface class : impl class

    companion object {

        @JvmStatic
        private val instance: DependPlugins by lazy(DependPlugins::class.java) { DependPlugins() }

        @JvmStatic
        @JvmName("of")
        @Synchronized
        @Throws(DependPluginException::class)
        fun <T: DependPlugin> of(clazz: Class<T>): T {
            var depend = instance.values[clazz]
            if(depend == null) {
                val implClazz = instance.implements[clazz]
                if(implClazz == null || !implClazz.interfaces.contains(clazz))
                    throw DependPluginException("依赖插件接口类不存在实现类或实现类未实现接口.")
                val value = implClazz.newInstance()
                instance.values.put(clazz, value)
                instance.names.put(value.getPluginName(), clazz)
                depend = value
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
        fun <T: DependPlugin> register(clazz: Class<T>, implClazz: Class<out DependPluginAbstract<*>>): Boolean {
            if(!implClazz.interfaces.contains(clazz))
                throw DependPluginException("依赖插件实现类未实现接口类.")
            if(instance.implements.containsKey(clazz))
                throw DependPluginException("依赖插件接口类 $clazz 已经被注册.")
            return instance.implements.put(clazz, implClazz) == null
        }

        @JvmStatic
        @JvmName("unregister")
        @Synchronized
        fun <T: DependPlugin> unregister(clazz: Class<T>): Boolean {
            if(!instance.implements.containsKey(clazz))
                return false
            return instance.implements.remove(clazz) != null &&
                    instance.values.remove(clazz) != null
        }

        @JvmStatic
        @JvmName("unregister")
        @Synchronized
        fun unregister(name: String): Boolean {
            val clazz = instance.names[name] ?: return false
            return unregister(clazz) && instance.names.remove(name) != null
        }

        @JvmStatic
        @JvmName("unregisterAll")
        @Synchronized
        fun unregisterAll() {
            instance.implements.clear()
            instance.values.clear()
            instance.names.clear()
        }

        @JvmStatic
        @JvmName("contains")
        @Synchronized
        fun contains(name: String): Boolean
                = instance.names.contains(name)
    }
}
