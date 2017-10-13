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

import com.minecraft.moonlake.api.isCombatOrLaterVer
import com.minecraft.moonlake.api.util.Enums
import com.minecraft.moonlake.api.wrapper.EnumChatVisibility
import com.minecraft.moonlake.api.wrapper.EnumMainHand

data class PacketInSettings(
        var locale: String,
        var viewDistance: Int,
        var chatVisibility: EnumChatVisibility,
        var chatColor: Boolean,
        var skinDisplayed: Int,
        var hand: EnumMainHand?) : PacketInBukkitAbstract("PacketPlayInSettings") {

    @Deprecated("")
    constructor() : this("en_US", 12, EnumChatVisibility.FULL, true, 0x80, null)

    override fun read(data: PacketBuffer) {
        locale = data.readString()
        viewDistance = data.readByte().toInt()
        chatVisibility = Enums.ofValuable(EnumChatVisibility::class.java, data.readVarInt()) ?: EnumChatVisibility.FULL
        chatColor = data.readBoolean()
        skinDisplayed = data.readByte().toInt()
        if(isCombatOrLaterVer)
            hand = Enums.ofValuable(EnumMainHand::class.java, data.readVarInt(), EnumMainHand.RIGHT)
    }

    override fun write(data: PacketBuffer) {
        data.writeString(locale)
        data.writeByte(viewDistance)
        data.writeVarInt(chatVisibility.value)
        data.writeBoolean(chatColor)
        data.writeByte(skinDisplayed)
        if(isCombatOrLaterVer)
            data.writeVarInt(hand?.value ?: EnumMainHand.RIGHT.value)
    }
}
