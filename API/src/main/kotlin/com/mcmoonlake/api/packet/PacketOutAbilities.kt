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

package com.mcmoonlake.api.packet

import com.mcmoonlake.api.player.PlayerAbilities
import org.bukkit.entity.Player

data class PacketOutAbilities(
        var isInvulnerable: Boolean,
        var isFlying: Boolean,
        var canFly: Boolean,
        var canInstantlyBuild: Boolean,
        var flySpeed: Float,
        var walkSpeed: Float) : PacketOutBukkitAbstract("PacketPlayOutAbilities") {

    constructor(abilities: PlayerAbilities) : this(abilities.isInvulnerable, abilities.isFlying, abilities.canFly, abilities.canInstantlyBuild, abilities.flySpeed, abilities.walkSpeed)
    constructor(player: Player) : this(PlayerAbilities.ofPlayer(player))

    @Deprecated("")
    constructor() : this(false, false, false, false, .05f, .1f)

    override fun read(data: PacketBuffer) {
        val flag = data.readByte().toInt()
        isInvulnerable = flag and 1 > 0
        isFlying = flag and 2 > 0
        canFly = flag and 4 > 0
        canInstantlyBuild = flag and 8 > 0
        flySpeed = data.readFloat()
        walkSpeed = data.readFloat()
    }

    override fun write(data: PacketBuffer) {
        var flag = 0
        if(isInvulnerable)
            flag = flag or 1
        if(isFlying)
            flag = flag or 2
        if(canFly)
            flag = flag or 4
        if(canInstantlyBuild)
            flag = flag or 8
        data.writeByte(flag)
        data.writeFloat(flySpeed)
        data.writeFloat(walkSpeed)
    }
}
