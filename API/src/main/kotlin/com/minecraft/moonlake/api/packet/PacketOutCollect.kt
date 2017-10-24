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

import com.minecraft.moonlake.api.isExplorationOrLaterVer

data class PacketOutCollect(
        var itemEntityId: Int,
        var entityId: Int,
        /**
         * * Pick up the amount, Only valid at 1.11 or higher.
         * * 拾取数量, 仅在 1.11 或更高版本有效.
         */
        var pickupCount: Int) : PacketOutBukkitAbstract("PacketPlayOutCollect") {

    @Deprecated("")
    constructor() : this(-1, -1, -1)

    override fun read(data: PacketBuffer) {
        itemEntityId = data.readVarInt()
        entityId = data.readVarInt()
        if(isExplorationOrLaterVer)
            pickupCount = data.readVarInt()
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(itemEntityId)
        data.writeVarInt(entityId)
        if(isExplorationOrLaterVer)
            data.writeVarInt(pickupCount)
    }
}
