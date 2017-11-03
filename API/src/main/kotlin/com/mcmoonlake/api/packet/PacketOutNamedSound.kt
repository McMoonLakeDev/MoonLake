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

import com.mcmoonlake.api.isFrostburnOrLaterVer
import com.mcmoonlake.api.util.Enums
import org.bukkit.SoundCategory

data class PacketOutNamedSound(
        var sound: String,
        var category: SoundCategory,
        var x: Int, var y: Int, var z: Int,
        var volume: Float,
        var pitch: Float) : PacketOutBukkitAbstract("PacketPlayOutCustomSoundEffect"), PacketBukkitLegacy { // 1.9+

    @Deprecated("")
    constructor() : this("null", SoundCategory.MUSIC, 0, 0, 0, 1f, 1f)

    constructor(sound: String, category: SoundCategory, x: Double, y: Double, z: Double, volume: Float, pitch: Float) : this(sound, category, (x * 8.0).toInt(), (y * 8.0).toInt(), (z * 8.0).toInt(), volume, pitch)

    override fun read(data: PacketBuffer) {
        sound = data.readString()
        category = Enums.ofOrigin(SoundCategory::class.java, data.readVarInt()) ?: SoundCategory.MUSIC
        x = data.readInt()
        y = data.readInt()
        z = data.readInt()
        volume = data.readFloat()
        pitch = if(!isFrostburnOrLaterVer) data.readUnsignedByte() / 63f else data.readFloat()
    }

    override fun write(data: PacketBuffer) {
        data.writeString(sound)
        data.writeVarInt(category.ordinal)
        data.writeInt(x)
        data.writeInt(y)
        data.writeInt(z)
        data.writeFloat(volume)
        if(!isFrostburnOrLaterVer)
            data.writeByte((pitch * 63f).toInt())
        else
            data.writeFloat(pitch)
    }
}
