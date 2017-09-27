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

package com.minecraft.moonlake.impl.depend

import com.minecraft.moonlake.api.depend.DependPlaceholderAPI
import com.minecraft.moonlake.api.depend.DependPluginAbstract
import com.minecraft.moonlake.api.depend.DependPluginVersionException
import com.minecraft.moonlake.api.getPlugin
import com.minecraft.moonlake.api.player.MoonLakePlayer
import me.clip.placeholderapi.PlaceholderAPI
import me.clip.placeholderapi.PlaceholderAPIPlugin
import org.bukkit.entity.Player

class DependPlaceholderAPIImpl : DependPluginAbstract<PlaceholderAPIPlugin>(getPlugin(DependPlaceholderAPI.NAME)), DependPlaceholderAPI {

    override fun setPlaceholders(player: MoonLakePlayer, text: String): String
            = setPlaceholders(player.bukkitPlayer, text)

    override fun setPlaceholders(player: Player, text: String): String
            = PlaceholderAPI.setPlaceholders(player, text)

    override fun setBracketPlaceholders(player: MoonLakePlayer, text: String): String
            = setBracketPlaceholders(player.bukkitPlayer, text)

    override fun setBracketPlaceholders(player: Player, text: String): String
            = PlaceholderAPI.setBracketPlaceholders(player, text)

    override fun setRelationalPlaceholders(one: MoonLakePlayer, two: MoonLakePlayer, text: String): String
            = setRelationalPlaceholders(one.bukkitPlayer, two.bukkitPlayer, text)

    override fun setRelationalPlaceholders(one: Player, two: Player, text: String): String = when(pluginVersion >= "2.8.0") {
        true -> PlaceholderAPI.setRelationalPlaceholders(one, two, text)
        else -> throw DependPluginVersionException("依赖插件占位符 #setRelationalPlaceholders() 最低需求 2.8.0 版本.")
    }
}
