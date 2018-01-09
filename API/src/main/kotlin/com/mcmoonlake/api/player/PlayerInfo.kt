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
import com.mcmoonlake.api.parseInt
import com.mojang.authlib.GameProfile
import org.bukkit.GameMode
import org.bukkit.configuration.serialization.ConfigurationSerializable
import java.util.*

data class PlayerInfo(
        val profile: GameProfile,
        val displayName: ChatComponent?,
        val mode: GameMode?,
        val latency: Int
) : ConfigurationSerializable {

    constructor(id: UUID, displayName: String?, mode: GameMode? = GameMode.SURVIVAL, latency: Int = 0) : this(GameProfile(id, null), ChatSerializer.fromRawOrNull(displayName), mode, latency)
    constructor(id: UUID, name: String, displayName: String? = null, mode: GameMode? = GameMode.SURVIVAL, latency: Int = 0) : this(GameProfile(id, name), ChatSerializer.fromRawOrNull(displayName), mode, latency)

    override fun serialize(): MutableMap<String, Any> {
        val result = LinkedHashMap<String, Any>()
        val resultProfile = LinkedHashMap<String, Any>()
        resultProfile.put("id", profile.id.toString())
        if(profile.name != null) resultProfile.put("name", profile.name)
        result.put("profile", resultProfile)
        if(displayName != null) result.put("displayName", displayName.toRaw())
        result.put("mode", (mode ?: GameMode.SURVIVAL).name)
        result.put("latency", latency)
        return result
    }

    /** static */

    companion object {

        @JvmStatic
        @JvmName("deserialize")
        @SuppressWarnings("UNCHECKED_CAST")
        fun deserialize(args: Map<String, Any>): PlayerInfo {
            val profile = args["profile"] as Map<String, Any?>
            val profileId = UUID.fromString(profile["id"].toString())
            val profileName = profile["name"]?.toString()
            val displayName = ChatSerializer.fromRawOrNull(args["displayName"]?.toString())
            val mode = GameMode.valueOf(args["mode"]?.toString() ?: GameMode.SURVIVAL.name)
            val latency = args["latency"]?.parseInt() ?: 0
            return PlayerInfo(GameProfile(profileId, profileName), displayName, mode, latency)
        }
    }
}
