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

import com.mcmoonlake.api.PluginInfo
import org.bukkit.configuration.file.FileConfiguration
import java.io.File
import java.io.InputStream
import java.util.logging.Logger

/**
 * ## DependPluginInfo (依赖插件信息)
 *
 * @see [PluginInfo]
 * @author lgou2w
 * @since 2.0
 */
interface DependPluginInfo : PluginInfo {

    /**
     * * Get this dependent plugin data folder object.
     * * 获取此依赖插件的数据目录对象.
     */
    val dataFolder: File

    /**
     * * Get this dependent plugin logger object.
     * * 获取此依赖插件的日志对象.
     */
    val logger: Logger

    /**
     * * Get this dependent plugin configuration object.
     * * 获取此依赖插件的配置文件对象.
     */
    val config: FileConfiguration?

    /**
     * * Gets this dependent plugin resource Input stream from the given file name.
     * * 从给定的文件名获取此依赖插件的资源输入流.
     *
     * @param filename File name.
     * @param filename 文件名.
     */
    fun getResource(filename: String): InputStream?
}
