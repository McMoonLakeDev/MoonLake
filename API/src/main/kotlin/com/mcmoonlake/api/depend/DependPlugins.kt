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

package com.mcmoonlake.api.depend

import com.mcmoonlake.api.getPlugin
import com.mcmoonlake.api.reflect.accessor.AccessorMethod
import com.mcmoonlake.api.reflect.accessor.Accessors
import com.mcmoonlake.api.throwGiven
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * ## DependPlugins
 *
 * @see [DependPlugin]
 * @author lgou2w
 * @since 2.0
 */
object DependPlugins {

    @JvmStatic
    private val VALUES: MutableMap<Class<out DependPlugin>, DependPlugin> = HashMap()
    @JvmStatic
    private val IMPLEMENTS: MutableMap<Class<out DependPlugin>, Class<out DependPluginAbstract<*>>> = HashMap()
    @JvmStatic
    private val JAVA_PLUGIN_GET_FILE: AccessorMethod by lazy { Accessors.getAccessorMethod(JavaPlugin::class.java, "getFile", true) }

    /**
     * * Obtain the implementation object from the given dependent plugin class.
     * * 从给定的依赖插件类获取实现对象.
     *
     * @param clazz Depend plugin class.
     * @param clazz 依赖插件类.
     * @throws DependPluginException If the implementation object exists, the real plugin is not enabled or the plugin file does not exist.
     * @throws DependPluginException If dependent plugin class does not exist implementation class.
     * @throws DependPluginException If can not dependent plugin.
     * @throws DependPluginException 如果实现对象存在, 但是真实插件未加载或插件文件不存在.
     * @throws DependPluginException 如果依赖插件类不存在实现类.
     * @throws DependPluginException 如果无法依赖插件.
     * @see [ofSafe]
     */
    @JvmStatic
    @JvmName("of")
    @Throws(DependPluginException::class)
    fun <T: DependPlugin> of(clazz: Class<T>): T {
        synchronized(this) {
            var DEPEND = VALUES[clazz]
            if(DEPEND != null && !isDependRealAlive(DEPEND)) {
                VALUES.remove(clazz) // remove cache
                throw DependPluginException("依赖插件缓存引用存在, 但是检测真实插件时未加载或插件文件不存在.")
            }
            if(DEPEND == null) {
                val IMPLEMENT = IMPLEMENTS[clazz]
                if(IMPLEMENT == null || !IMPLEMENT.interfaces.contains(clazz))
                    throw DependPluginException("依赖插件接口类不存在实现类或实现类未实现接口.")
                val VALUE = try {
                    IMPLEMENT.newInstance()
                } catch(e: Exception) {
                    e.throwGiven { DependPluginException(it.message, it.cause ?: it) }
                }
                VALUES.put(clazz, VALUE)
                DEPEND = VALUE
            }
            if(DEPEND != null && clazz.isInstance(DEPEND))
                return clazz.cast(DEPEND)
            throw DependPluginException("无法依赖插件.")
        }
    }

    @JvmStatic
    @JvmName("isDependRealAlive")
    private fun isDependRealAlive(depend: DependPlugin): Boolean {
        val DEPEND = getPlugin(depend.name)
        return if(DEPEND is JavaPlugin) {
            DEPEND.isEnabled && isDependRealAliveFile(DEPEND)
        } else {
            DEPEND != null && DEPEND.isEnabled
        }
    }

    @JvmStatic
    @JvmName("isDependRealAliveFile")
    private fun isDependRealAliveFile(depend: JavaPlugin): Boolean = try {
        val FILE = JAVA_PLUGIN_GET_FILE.invoke(depend) as File?
        FILE != null && FILE.exists()
    } catch(e: Exception) {
        false
    }

    /**
     * * Safe obtain the implementation object from the given dependent plugin class.
     * * 从给定的依赖插件类安全地获取实现对象.
     *
     * @param clazz Depend plugin class.
     * @param clazz 依赖插件类.
     * @see [of]
     */
    @JvmStatic
    @JvmName("ofSafe")
    fun <T: DependPlugin> ofSafe(clazz: Class<T>): T? = try {
        of(clazz)
    } catch(e: DependPluginException) {
        null
    }

    /**
     * * The given dependent plugin class is registered for the given implementation class.
     * * 将给定的依赖插件类注册给定的实现类.
     *
     * @param clazz Depend plugin class.
     * @param clazz 依赖插件类.
     * @param implClazz Depend plugin implementation class.
     * @param implClazz 依赖插件实现类.
     * @throws DependPluginException If dependent plugin implementation class interface class is not implemented
     * @throws DependPluginException If dependent plugin class has been registered.
     * @throws DependPluginException 如果依赖插件实现类未实现接口类.
     * @throws DependPluginException 如果依赖插件类已被注册.
     */
    @JvmStatic
    @JvmName("register")
    @Throws(DependPluginException::class)
    fun <T: DependPlugin> register(clazz: Class<T>, implClazz: Class<out DependPluginAbstract<*>>): Boolean {
        if(!implClazz.interfaces.contains(clazz))
            throw DependPluginException("依赖插件实现类未实现接口类.")
        synchronized(this) {
            if(IMPLEMENTS.containsKey(clazz))
                throw DependPluginException("依赖插件接口类 $clazz 已经被注册.")
            return IMPLEMENTS.put(clazz, implClazz) == null
        }
    }

    @JvmStatic
    @JvmName("unregister0")
    private fun <T: DependPlugin> unregister0(clazz: Class<T>?): Boolean
            = clazz != null && (IMPLEMENTS.remove(clazz) != null).also { if(it) VALUES.remove(clazz) }

    /**
     * * Unregister the implementation from the given dependent plugin class.
     * * 从给定的依赖插件类注销实现.
     *
     * @param clazz Depend plugin class.
     * @param clazz 依赖插件类.
     */
    @JvmStatic
    @JvmName("unregister")
    fun <T: DependPlugin> unregister(clazz: Class<T>): Boolean
            = synchronized(this) { unregister0(clazz) }

    /**
     * * Unregister the implementation from the given dependent plugin name.
     * * 从给定的依赖插件名称注销实现.
     *
     * @param pluginName Depend plugin name.
     * @param pluginName 依赖插件名称.
     */
    @JvmStatic
    @JvmName("unregister")
    fun unregister(pluginName: String): Boolean
            = synchronized(this) { unregister0(VALUES.entries.find { it.value.name == pluginName }?.key) }

    /**
     * * Unregister all dependent plugin implementation.
     * * 注销依赖插件的所有实现.
     */
    @JvmStatic
    @JvmName("unregisterAll")
    fun unregisterAll()
            = synchronized(this) { IMPLEMENTS.clear(); VALUES.clear(); }

    /**
     * * Whether to have the implementation object gets from the given dependent plugin name.
     * * 从给定的依赖插件名获取是否拥有实现对象.
     *
     * @param pluginName Depend plugin name.
     * @param pluginName 依赖插件名称.
     */
    @JvmStatic
    @JvmName("contains")
    fun contains(pluginName: String): Boolean
            = synchronized(this) { VALUES.values.find { it.name == pluginName } != null }
}
