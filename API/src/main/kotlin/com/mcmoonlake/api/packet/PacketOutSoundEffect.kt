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
import org.bukkit.Sound
import org.bukkit.SoundCategory
import java.io.IOException

data class PacketOutSoundEffect(
        var sound: Sound,
        var category: SoundCategory,
        var x: Int, var y: Int, var z: Int,
        var volume: Float,
        var pitch: Float
) : PacketOutBukkitAbstract("PacketPlayOutNamedSoundEffect"),
        PacketBukkitFreshly {

    @Deprecated("")
    constructor() : this(Sound.AMBIENT_CAVE, SoundCategory.MUSIC, 0, 0, 0, 1f, 0f)

    override fun read(data: PacketBuffer) {
        sound = Enums.ofOrigin(Sound::class.java, data.readVarInt()) ?: throw IOException("Unknown Sound Effect.")
        category = Enums.ofOrigin(SoundCategory::class.java, data.readVarInt()) ?: SoundCategory.MUSIC
        x = data.readInt()
        y = data.readInt()
        z = data.readInt()
        volume = data.readFloat()
        pitch = if(isFrostburnOrLaterVer) data.readFloat() else data.readUnsignedByte() / 63.5f
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(sound.ordinal)
        data.writeVarInt(category.ordinal)
        data.writeInt(x)
        data.writeInt(y)
        data.writeInt(z)
        data.writeFloat(volume)
        if(isFrostburnOrLaterVer) data.writeFloat(pitch)
        else data.writeByte((pitch * 63.5f).toInt())
    }
}
