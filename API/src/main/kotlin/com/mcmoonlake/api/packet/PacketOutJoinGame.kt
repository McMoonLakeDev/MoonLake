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

import com.mcmoonlake.api.isCombatOrLaterVer
import org.bukkit.Difficulty
import org.bukkit.GameMode
import org.bukkit.World
import org.bukkit.WorldType

data class PacketOutJoinGame(
        var entityId: Int,
        var mode: GameMode,
        var hardCore: Boolean,
        var dimension: World.Environment,
        var difficulty: Difficulty,
        var maxPlayer: Int,
        var worldType: WorldType,
        var reducedDebug: Boolean) : PacketOutBukkitAbstract("PacketPlayOutLogin") {

    @Deprecated("")
    constructor() : this(-1, GameMode.SURVIVAL, false, World.Environment.NORMAL, Difficulty.EASY, 20, WorldType.NORMAL, false)

    override fun read(data: PacketBuffer) {
        entityId = data.readInt()
        val flag = data.readUnsignedByte().toInt()
        hardCore = (flag and 8) == 8
        mode = GameMode.getByValue(if(!isCombatOrLaterVer) flag else flag and -9)
        dimension = World.Environment.getEnvironment(if(!isCombatOrLaterVer) data.readByte().toInt() else data.readInt())
        difficulty = Difficulty.getByValue(data.readUnsignedByte().toInt())
        maxPlayer = data.readUnsignedByte().toInt()
        worldType = WorldType.getByName(data.readString()) ?: WorldType.NORMAL
        reducedDebug = data.readBoolean()
    }

    override fun write(data: PacketBuffer) {
        data.writeInt(entityId)
        var flag = mode.value
        if(hardCore)
            flag = flag or 8
        data.writeByte(flag)
        if(!isCombatOrLaterVer) data.writeByte(dimension.id) else data.writeInt(dimension.id)
        data.writeByte(difficulty.value)
        data.writeByte(maxPlayer)
        data.writeString(worldType.getName())
        data.writeBoolean(reducedDebug)
    }
}
