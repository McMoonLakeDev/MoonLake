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

package com.minecraft.moonlake.api.packet

import org.bukkit.Difficulty
import org.bukkit.GameMode
import org.bukkit.World
import org.bukkit.WorldType

data class PacketOutRespawn(
        var dimension: World.Environment,
        var difficulty: Difficulty,
        var mode: GameMode,
        var worldType: WorldType) : PacketOutBukkitAbstract("PacketPlayOutRespawn") {

    @Deprecated("")
    constructor() : this(World.Environment.NORMAL, Difficulty.EASY, GameMode.SURVIVAL, WorldType.NORMAL)

    override fun read(data: PacketBuffer) { // TODO v1.13
        dimension = World.Environment.getEnvironment(data.readInt())
        difficulty = Difficulty.getByValue(data.readUnsignedByte().toInt())
        mode = GameMode.getByValue(data.readUnsignedByte().toInt())
        worldType = WorldType.getByName(data.readString()) ?: WorldType.NORMAL
    }

    override fun write(data: PacketBuffer) { // TODO v1.13
        data.writeInt(dimension.id)
        data.writeByte(difficulty.value)
        data.writeByte(mode.value)
        data.writeString(worldType.getName())
    }
}
