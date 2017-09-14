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

import com.minecraft.moonlake.api.player.MoonLakePlayer
import org.bukkit.entity.Player

interface DependPlaceholderAPI : DependPlugin {

    fun setPlaceholders(player: Player, text: String): String

    fun setPlaceholders(player: MoonLakePlayer, text: String): String

    fun setBracketPlaceholders(player: Player, text: String): String

    fun setBracketPlaceholders(player: MoonLakePlayer, text: String): String

    @Throws(DependPluginVersionException::class)
    fun setRelationalPlaceholders(one: Player, two: Player, text: String): String

    @Throws(DependPluginVersionException::class)
    fun setRelationalPlaceholders(one: MoonLakePlayer, two: MoonLakePlayer, text: String): String

    companion object {

        const val NAME = "PlaceholderAPI"
    }
}
