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

import com.mcmoonlake.api.Valuable
import com.mcmoonlake.api.block.BlockPosition
import com.mcmoonlake.api.isCombatOrLaterVer
import com.mcmoonlake.api.notNull
import com.mcmoonlake.api.ofValuableNotNull
import java.util.*

data class PacketOutSpawnPainting(
        var entityId: Int,
        var title: String,
        var blockPosition: BlockPosition,
        var direction: Direction,
        /**
         * * Valid only in version 1.9 or later.
         * * 仅在 1.9 或更高版本有效.
         */
        var uuid: UUID?) : PacketOutBukkitAbstract("PacketPlayOutSpawnEntityPainting") {

    @Deprecated("")
    constructor() : this(-1, "", BlockPosition.ZERO, Direction.SOUTH, null)

    override fun read(data: PacketBuffer) {
        entityId = data.readVarInt()
        if(isCombatOrLaterVer)
            uuid = data.readUUID()
        title = data.readString()
        blockPosition = data.readBlockPosition()
        direction = ofValuableNotNull(data.readByte().toInt())
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(entityId)
        if(isCombatOrLaterVer)
            data.writeUUID(uuid.notNull())
        data.writeString(title)
        data.writeBlockPosition(blockPosition)
        data.writeByte(direction.value())
    }

    enum class Direction : Valuable<Int> {

        SOUTH,

        WEST,

        EAST,

        NORTH,
        ;

        override fun value(): Int
                = ordinal
    }
}
