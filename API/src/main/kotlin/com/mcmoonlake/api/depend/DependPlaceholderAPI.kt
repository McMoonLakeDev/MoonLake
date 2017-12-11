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

import org.bukkit.entity.Player

/**
 * ## DependPlaceholderAPI (依赖 PlaceholderAPI 插件)
 *
 * @see [DependPlugin]
 * @author lgou2w
 * @since 2.0
 */
interface DependPlaceholderAPI : DependPlugin {

    /**
     * * Set placeholders in the text specified placeholders are matched with the pattern `%<placeholder>%` when set with this method.
     * * 使用此方法进行设置时, 在给定的文本中设置占位符, 占位符与模式 `%<placeholder>%` 匹配.
     */
    fun setPlaceholders(player: Player, text: String): String

    /**
     * * Set placeholders in the text specified placeholders are matched with the pattern `{<placeholder>}` when set with this method.
     * * 使用此方法进行设置时, 在给定的文本中设置占位符, 占位符与模式 `{<placeholder>}` 匹配.
     */
    fun setBracketPlaceholders(player: Player, text: String): String

    /**
     * * Not sure
     *
     * @throws DependPluginVersionException If the dependent plugin is less than `2.8.0` version.
     * @throws DependPluginVersionException 如果依靠插件小于 `2.8.0` 版本.
     */
    @Throws(DependPluginVersionException::class)
    fun setRelationalPlaceholders(one: Player, two: Player, text: String): String

    companion object {
        /**
         * * The plugin name for PlaceholderAPI
         */
        const val NAME = "PlaceholderAPI"
    }
}
