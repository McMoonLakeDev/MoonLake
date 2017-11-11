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

data class PacketInSteerVehicle(
        var strafeSpeed: Float,
        var forwardSpeed: Float,
        var jumping: Boolean,
        var sneaking: Boolean) : PacketInBukkitAbstract("PacketPlayInSteerVehicle") {

    @Deprecated("")
    constructor() : this(0f, 0f, false, false)

    override fun read(data: PacketBuffer) {
        strafeSpeed = data.readFloat()
        forwardSpeed = data.readFloat()
        val flag = data.readByte().toInt()
        jumping = (flag and 1) > 0
        sneaking = (flag and 2) > 0
    }

    override fun write(data: PacketBuffer) {
        data.writeFloat(strafeSpeed)
        data.writeFloat(forwardSpeed)
        var flag = 0
        if(jumping)
            flag = flag or 1
        if(sneaking)
            flag = flag or 2
        data.writeByte(flag)
    }
}
