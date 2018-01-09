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

import com.mcmoonlake.api.block.BlockDirection
import com.mcmoonlake.api.block.BlockPosition
import com.mcmoonlake.api.ofValuable
import org.bukkit.inventory.ItemStack

data class PacketInBlockPlaceLegacy(
        var timestamp: Long,
        var blockPosition: BlockPosition,
        var direction: BlockDirection?,
        var itemStack: ItemStack?,
        var cursorX: Float,
        var cursorY: Float,
        var cursorZ: Float
) : PacketInBukkitAbstract("PacketPlayInBlockPlace"),
        PacketBukkitLegacy,
        PacketLegacy {

    @Deprecated("")
    constructor() : this(-1L, BlockPosition.ZERO, null, null, 0f, 0f, 0f)

    override fun read(data: PacketBuffer) {
        timestamp = System.currentTimeMillis()
        blockPosition = data.readBlockPosition()
        direction = ofValuable(data.readUnsignedByte().toInt())
        itemStack = data.readItemStack()
        cursorX = data.readUnsignedByte().toFloat() / 16f
        cursorY = data.readUnsignedByte().toFloat() / 16f
        cursorZ = data.readUnsignedByte().toFloat() / 16f
    }

    override fun write(data: PacketBuffer) {
        data.writeBlockPosition(blockPosition)
        data.writeByte(direction?.value() ?: 0xFF)
        data.writeItemStack(itemStack)
        data.writeByte((cursorX * 16f).toInt())
        data.writeByte((cursorY * 16f).toInt())
        data.writeByte((cursorZ * 16f).toInt())
    }
}
