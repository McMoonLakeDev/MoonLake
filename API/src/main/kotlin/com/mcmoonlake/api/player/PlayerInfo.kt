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

package com.mcmoonlake.api.player

import com.mcmoonlake.api.chat.ChatComponent
import com.mcmoonlake.api.chat.ChatSerializer
import com.mojang.authlib.GameProfile
import org.bukkit.GameMode
import java.util.*

data class PlayerInfo(val profile: GameProfile, val displayName: ChatComponent?, val mode: GameMode?, val latency: Int) {

    constructor(id: UUID, displayName: String?, mode: GameMode? = GameMode.SURVIVAL, latency: Int = 0) : this(GameProfile(id, null), ChatSerializer.fromRawOrNull(displayName), mode, latency)
    constructor(id: UUID, name: String, displayName: String? = null, mode: GameMode? = GameMode.SURVIVAL, latency: Int = 0) : this(GameProfile(id, name), ChatSerializer.fromRawOrNull(displayName), mode, latency)
}
