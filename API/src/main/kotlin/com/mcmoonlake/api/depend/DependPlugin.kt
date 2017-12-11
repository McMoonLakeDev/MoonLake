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

import org.bukkit.plugin.Plugin

/**
 * ## DependPlugin (依赖插件)
 *
 * @see [Depend]
 * @see [DependPluginInfo]
 * @see [DependPluginAbstract]
 * @author lgou2w
 * @since 2.0
 */
interface DependPlugin : Depend, DependPluginInfo {

    /**
     * * Get this plugin dependent plugin object.
     * * 获取此依赖插件的插件对象.
     */
    val plugin: Plugin
}
