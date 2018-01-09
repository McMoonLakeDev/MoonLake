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
import com.mcmoonlake.api.nbt.NBTCompound

data class PacketOutTileEntityData(
        var blockPosition: BlockPosition,
        var action: Int,
        var value: NBTCompound?
) : PacketOutBukkitAbstract("PacketPlayOutTileEntityData") {

    @Deprecated("")
    constructor() : this(BlockPosition.ZERO, 0, null)

    override fun read(data: PacketBuffer) {
        blockPosition = data.readBlockPosition()
        action = data.readUnsignedByte().toInt()
        value = data.readNBTComponent()
    }

    override fun write(data: PacketBuffer) {
        data.writeBlockPosition(blockPosition)
        data.writeByte(action)
        data.writeNBTComponent(value)
    }
}
