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
import com.mcmoonlake.api.util.Enums
import com.mcmoonlake.api.wrapper.EnumHand

data class PacketInArmAnimation(
        /**
         * * Arm hand, only valid at 1.9 or higher.
         * * 手臂, 仅在 1.9 或更高版本有效.
         */
        var hand: EnumHand?) : PacketInBukkitAbstract("PacketPlayInArmAnimation") {

    @Deprecated("")
    constructor() : this(null)

    override fun read(data: PacketBuffer) {
        if(isCombatOrLaterVer)
            hand = Enums.ofValuable(EnumHand::class.java, data.readVarInt())
    }

    override fun write(data: PacketBuffer) {
        if(isCombatOrLaterVer)
            data.writeVarInt(hand?.value ?: EnumHand.MAIN.value)
    }
}
