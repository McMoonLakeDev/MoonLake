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

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.InputStream
import java.util.logging.Logger

abstract class DependPluginAbstract<out T: Plugin> @Throws(DependPluginException::class) constructor(plugin: Plugin?) : DependPlugin {

    private val plugin: T

    init {
        if(plugin == null || !plugin.isEnabled)
            throw DependPluginException("依赖插件不存在或没有加载.")
        try {
            @Suppress("UNCHECKED_CAST")
            this.plugin = plugin as T
        } catch(e: Exception) {
            throw DependPluginException(e.message, e)
        }
    }

    override fun getPlugin(): T
            = plugin

    override fun getPluginPrefix(): String
            = plugin.description.prefix

    override fun getPluginName(): String
            = plugin.description.name

    override fun getPluginMain(): String
            = plugin.description.main

    override fun getPluginVersion(): String
            = plugin.description.version

    override fun getPluginWebsite(): String
            = plugin.description.website

    override fun getPluginDescription(): String
            = plugin.description.description

    override fun getPluginAuthors(): Set<String>
            = plugin.description.authors.toSet()

    override fun getPluginDepends(): Set<String>
            = plugin.description.depend.toSet()

    override fun getPluginSoftDepends(): Set<String>
            = plugin.description.softDepend.toSet()

    override fun getDataFolder(): File
            = plugin.dataFolder

    override fun getLogger(): Logger
            = plugin.logger

    override fun getConfig(): FileConfiguration?
            = plugin.config

    override fun getResource(filename: String): InputStream?
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
