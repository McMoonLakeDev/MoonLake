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

object DependPlugins {

    @JvmStatic
    private val names: MutableMap<String, Class<out DependPlugin>> by lazy {
        HashMap<String, Class<out DependPlugin>>() }
    @JvmStatic
    private val values: MutableMap<Class<out DependPlugin>, DependPlugin> by lazy {
        HashMap<Class<out DependPlugin>, DependPlugin>() }
    @JvmStatic
    private val implements: MutableMap<Class<out DependPlugin>, Class<out DependPluginAbstract<*>>> by lazy {
        HashMap<Class<out DependPlugin>, Class<out DependPluginAbstract<*>>>() }

    @JvmStatic
    @JvmName("of")
    @Synchronized
    @Throws(DependPluginException::class)
    fun <T: DependPlugin> of(clazz: Class<T>): T {
        var depend = values[clazz]
        if(depend == null) {
            val implClazz = implements[clazz]
            if(implClazz == null || !implClazz.interfaces.contains(clazz))
                throw DependPluginException("依赖插件接口类不存在实现类或实现类未实现接口.")
            val value = implClazz.newInstance()
            values.put(clazz, value)
            names.put(value.pluginName, clazz)
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
        if(implements.containsKey(clazz))
            throw DependPluginException("依赖插件接口类 $clazz 已经被注册.")
        return implements.put(clazz, implClazz) == null
    }

    @JvmStatic
    @JvmName("unregister")
    @Synchronized
    fun <T: DependPlugin> unregister(clazz: Class<T>): Boolean {
        if(!implements.containsKey(clazz))
            return false
        return implements.remove(clazz) != null && values.remove(clazz) != null
    }

    @JvmStatic
    @JvmName("unregister")
    @Synchronized
    fun unregister(name: String): Boolean {
        val clazz = names[name] ?: return false
        return unregister(clazz) && names.remove(name) != null
    }

    @JvmStatic
    @JvmName("unregisterAll")
    @Synchronized
    fun unregisterAll() {
        implements.clear()
        values.clear()
        names.clear()
    }

    @JvmStatic
    @JvmName("contains")
    @Synchronized
    fun contains(name: String): Boolean
            = names.contains(name)
}
