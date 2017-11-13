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

import com.mcmoonlake.api.particle.Particle
import java.util.*

data class PacketOutParticles(
        var particle: Particle,
        var longDistance: Boolean,
        var x: Float,
        var y: Float,
        var z: Float,
        var xOffset: Float,
        var yOffset: Float,
        var zOffset: Float,
        var speed: Float,
        var amount: Int,
        var arguments: IntArray) : PacketOutBukkitAbstract("PacketPlayOutWorldParticles") {

    @Deprecated("")
    constructor() : this(Particle.BARRIER, false, -1f, -1f, -1f, 0f, 0f, 0f, 0f, 1, intArrayOf())

    override fun read(data: PacketBuffer) {
        particle = Particle.fromId(data.readInt())
        longDistance = data.readBoolean()
        x = data.readFloat()
        y = data.readFloat()
        z = data.readFloat()
        xOffset = data.readFloat()
        yOffset = data.readFloat()
        zOffset = data.readFloat()
        speed = data.readFloat()
        amount = data.readInt()
        arguments = IntArray(particle.dataLength)
        (0 until arguments.size).forEach { arguments[it] = data.readVarInt() }
    }

    override fun write(data: PacketBuffer) {
        data.writeInt(particle.id)
        data.writeBoolean(longDistance)
        data.writeFloat(x)
        data.writeFloat(y)
        data.writeFloat(z)
        data.writeFloat(xOffset)
        data.writeFloat(yOffset)
        data.writeFloat(zOffset)
        data.writeFloat(speed)
        data.writeInt(amount)
        (0 until arguments.size).forEach { data.writeVarInt(arguments[it]) }
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + particle.hashCode()
        result = 31 * result + longDistance.hashCode()
        result = 31 * result + x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + xOffset.hashCode()
        result = 31 * result + yOffset.hashCode()
        result = 31 * result + zOffset.hashCode()
        result = 31 * result + speed.hashCode()
        result = 31 * result + amount.hashCode()
        result = 31 * result + Arrays.hashCode(arguments)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is PacketOutParticles) {
            return super.equals(other) &&
                    particle == other.particle &&
                    longDistance == other.longDistance &&
                    x == other.x && y == other.y  && z == other.z &&
                    xOffset == other.xOffset && yOffset == other.yOffset && zOffset == other.zOffset &&
                    speed == other.speed && amount == other.amount && Arrays.equals(arguments, other.arguments)
        }
        return false
    }
}
