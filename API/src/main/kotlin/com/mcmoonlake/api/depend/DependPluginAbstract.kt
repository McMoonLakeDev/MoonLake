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

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.InputStream
import java.util.logging.Logger

/**
 * ## DependPluginAbstract (依赖插件抽象)
 *
 * @see [DependPlugin]
 * @author lgou2w
 * @since 2.0
 * @param T Plugin type.
 * @param T 插件类型.
 * @constructor DependPluginAbstract
 * @param plugin Plugin.
 * @param plugin 插件.
 * @throws DependPluginException If the plugin does not exist or is not enabled.
 * @throws DependPluginException 如果插件不存在或未启用.
 */
abstract class DependPluginAbstract<out T: Plugin> @Throws(DependPluginException::class) constructor(plugin: Plugin?) : DependPlugin {

    private val _plugin: T

    init {
        if(plugin == null || !plugin.isEnabled)
            throw DependPluginException("依赖插件不存在或没有加载.")
        try {
            @Suppress("UNCHECKED_CAST")
            this._plugin = plugin as T
        } catch(e: Exception) {
            throw DependPluginException(e)
        }
    }

    override final val plugin: T
            = _plugin

    override final val pluginPrefix: String
        get() = plugin.description.prefix

    override final val pluginName: String
        get() = plugin.description.name

    override final val pluginMain: String
        get() = plugin.description.main

    override final val pluginVersion: String
        get() = plugin.description.version

    override final val pluginWebsite: String
        get() = plugin.description.website

    override final val pluginDescription: String
        get() = plugin.description.description

    override final val pluginAuthors: Set<String>
        get() = plugin.description.authors.toSet()

    override final val pluginDepends: Set<String>
        get() = plugin.description.depend.toSet()

    override final val pluginSoftDepends: Set<String>
        get() = plugin.description.softDepend.toSet()

    override final val dataFolder: File
        get() = plugin.dataFolder

    override final val logger: Logger
        get() = plugin.logger

    override final val config: FileConfiguration?
        get() = plugin.config

    override final fun getResource(filename: String): InputStream?
            = plugin.getResource(filename)

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is DependPluginAbstract<*>)
            return plugin == other.plugin
        return false
    }

    override fun hashCode(): Int {
        return plugin.hashCode()
    }

    override fun toString(): String {
        return "DependPlugin(plugin=$plugin)"
    }
}
