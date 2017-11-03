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

import com.mcmoonlake.api.effect.EffectCustom
import com.mcmoonlake.api.effect.EffectType
import com.mcmoonlake.api.isCombatOrLaterVer
import com.mcmoonlake.api.util.Enums

data class PacketOutEntityEffect(
        var entityId: Int,
        var effect: EffectType?,
        var amplifier: Int,
        var duration: Int,
        var particle: Boolean,
        /**
         * * Only valid at 1.9 or higher.
         * * 仅在 1.9 或更高版本有效.
         */
        var ambient: Boolean) : PacketOutBukkitAbstract("PacketPlayOutEntityEffect") {

    @Deprecated("")
    constructor() : this(-1, null, 0, 0, false, false)

    constructor(entityId: Int, effect: EffectCustom) : this(entityId, effect.type, effect.amplifier, effect.duration, effect.particle, effect.ambient)

    override fun read(data: PacketBuffer) {
        entityId = data.readVarInt()
        effect = Enums.ofValuable(EffectType::class.java, data.readByte().toInt())
        amplifier = data.readByte().toInt()
        duration = data.readVarInt()
        if(!isCombatOrLaterVer) {
            particle = data.readByte() > 0
        } else {
            val flag = data.readByte().toInt()
            ambient = (flag and 1) > 0
            particle = (flag and 2) > 0
        }
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(entityId)
        data.writeByte((effect?.value() ?: 0) and 0xFF)
        data.writeByte(amplifier and 0xFF)
        data.writeVarInt(if(duration > 0x7FFF) 0x7FFF else duration)
        if(!isCombatOrLaterVer) {
            data.writeByte(if(particle) 1 else 0)
        } else {
            var flag = 0
            if(ambient) flag = flag or 1
            if(particle) flag = flag or 2
            data.writeByte(flag)
        }
    }
}
