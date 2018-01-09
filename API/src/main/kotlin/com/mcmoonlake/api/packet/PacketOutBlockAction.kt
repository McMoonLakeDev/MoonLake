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

import com.mcmoonlake.api.block.BlockPosition
import org.bukkit.Material
import org.bukkit.block.Block

data class PacketOutBlockAction(
        var blockPosition: BlockPosition,
        var action: Int,
        var parameter: Int,
        var block: Material
) : PacketOutBukkitAbstract("PacketPlayOutBlockAction") {

    constructor(block: Block, action: Int, parameter: Int) : this(BlockPosition(block.x, block.y, block.z), action, parameter, block.type)

    @Deprecated("")
    constructor() : this(BlockPosition.ZERO, -1, -1, Material.AIR)

    override fun read(data: PacketBuffer) {
        blockPosition = data.readBlockPosition()
        action = data.readUnsignedByte().toInt()
        parameter = data.readUnsignedByte().toInt()
        block = Material.getMaterial(data.readVarInt() and 4095) // TODO v1.13
    }

    override fun write(data: PacketBuffer) {
        data.writeBlockPosition(blockPosition)
        data.writeByte(action)
        data.writeByte(parameter)
        data.writeVarInt(block.id and 4095) // TODO v1.13
    }
}
