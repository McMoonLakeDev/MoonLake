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

package com.mcmoonlake.api.service

import com.mcmoonlake.api.player.MoonLakePlayer
import com.mcmoonlake.api.wrapper.Mod
import org.bukkit.entity.Player

interface ServiceForgeHandshake : Service {

    fun getMods(player: Player): Array<Mod>?

    fun getMods(player: MoonLakePlayer): Array<Mod>?

    fun resetMods(player: Player): Boolean

    fun resetMods(player: MoonLakePlayer): Boolean
}
