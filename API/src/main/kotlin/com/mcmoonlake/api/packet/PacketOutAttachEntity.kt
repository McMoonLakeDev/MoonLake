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

data class PacketOutAttachEntity(
        var entityId: Int,
        var holderId: Int
) : PacketOutBukkitAbstract("PacketPlayOutAttachEntity") {

    private var leash: Boolean = false

    @Deprecated("")
    constructor() : this(-1, -1)

    /**
     * * Only available in version 1.8 or earlier
     * * 仅在 1.8 或更早版本有效.
     */
    val isLeash: Boolean
        get() = leash

    override fun read(data: PacketBuffer) {
        entityId = data.readInt()
        holderId = data.readInt()
        if(!isCombatOrLaterVer)
            leash = data.readUnsignedByte() > 0
    }

    override fun write(data: PacketBuffer) {
        data.writeInt(entityId)
        data.writeInt(holderId)
        if(!isCombatOrLaterVer)
            data.writeByte(if(leash) 1 else 0)
    }
}
