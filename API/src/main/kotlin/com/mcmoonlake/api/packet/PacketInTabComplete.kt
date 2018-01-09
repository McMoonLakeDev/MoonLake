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
import com.mcmoonlake.api.isCombatOrLaterVer
import com.mcmoonlake.api.notNull

data class PacketInTabComplete(
        var message: String,
        var blockPosition: BlockPosition?,
        /**
         * * Whether it is selected as a command block, only valid at 1.9 or later.
         * * 是否选中的为命令方块, 仅在 1.9 或更高版本有效.
         */
        var isCommandBlock: Boolean

) : PacketInBukkitAbstract("PacketPlayInTabComplete") {

    @Deprecated("")
    constructor() : this("", null, false)

    override fun read(data: PacketBuffer) {
        message = data.readString()
        if(isCombatOrLaterVer)
            isCommandBlock = data.readBoolean()
        if(data.readBoolean())
            blockPosition = data.readBlockPosition()
    }

    override fun write(data: PacketBuffer) {
        data.writeString(message)
        if(isCombatOrLaterVer)
            data.writeBoolean(isCommandBlock)
        data.writeBoolean(blockPosition != null)
        if(blockPosition != null)
            data.writeBlockPosition(blockPosition.notNull())
    }
}
