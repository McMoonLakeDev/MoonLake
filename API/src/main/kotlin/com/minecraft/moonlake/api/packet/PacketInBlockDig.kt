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

import com.minecraft.moonlake.api.Valuable
import com.minecraft.moonlake.api.util.Enums
import com.minecraft.moonlake.api.wrapper.BlockDirection
import com.minecraft.moonlake.api.wrapper.BlockPosition

data class PacketInBlockDig(var status: Status, var blockPosition: BlockPosition, var direction: BlockDirection) : PacketInBukkitAbstract("PacketPlayInBlockDig") {

    constructor() : this(Status.STOP_DESTROY_BLOCK, BlockPosition.ZERO, BlockDirection.UP)

    override fun read(data: PacketBuffer) {
        status = Enums.ofValuable(Status::class.java, data.readVarInt()) ?: Status.STOP_DESTROY_BLOCK
        blockPosition = data.readBlockPosition()
        direction = Enums.ofValuable(BlockDirection::class.java, data.readUnsignedByte().toInt()) ?: BlockDirection.UP
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(status.value())
        data.writeBlockPosition(blockPosition)
        data.writeByte(direction.value())
    }

    enum class Status : Valuable<Int> {

        START_DESTROY_BLOCK,
        ABORT_DESTROY_BLOCK,
        STOP_DESTROY_BLOCK,
        DROP_ALL_ITEMS,
        DROP_ITEM,
        RELEASE_USE_ITEM,
        /**
         * Only Minecraft 1.9+
         */
        SWAP_HELD_ITEMS,
        ;

        override fun value(): Int
                = ordinal
    }
}
