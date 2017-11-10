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

data class PacketOutWorldEvent(
        var id: Int,
        var location: BlockPosition,
        var data: Int,
        var volume: Boolean) : PacketOutBukkitAbstract("PacketPlayOutWorldEvent") {

    @Deprecated("")
    constructor() : this(-1, BlockPosition.ZERO, 0, false)

    override fun read(data: PacketBuffer) {
        id = data.readInt()
        location = data.readBlockPosition()
        this.data = data.readInt()
        volume = data.readBoolean()
    }

    override fun write(data: PacketBuffer) {
        data.writeInt(id)
        data.writeBlockPosition(location)
        data.writeInt(this.data)
        data.writeBoolean(volume)
    }
}
