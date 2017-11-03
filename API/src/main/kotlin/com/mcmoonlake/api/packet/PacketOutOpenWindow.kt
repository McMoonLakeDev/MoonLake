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

import com.mcmoonlake.api.chat.ChatComponent
import com.mcmoonlake.api.chat.ChatComponentText

data class PacketOutOpenWindow(
        var windowId: Int,
        var windowType: String,
        var windowTitle: ChatComponent,
        var slots: Int,
        /**
         * * Only available when [windowType] is `EntityHorse`.
         * * 仅当 [windowType] 为 `EntityHorse` 时可用.
         */
        var entityHorseId: Int = -1) : PacketOutBukkitAbstract("PacketPlayOutOpenWindow") {

    @Deprecated("")
    constructor() : this(-1, "Null", ChatComponentText(), 0)

    override fun read(data: PacketBuffer) {
        windowId = data.readUnsignedByte().toInt()
        windowType = data.readString()
        windowTitle = data.readChatComponent()
        slots = data.readUnsignedByte().toInt()
        if(windowType == "EntityHorse")
            entityHorseId = data.readInt()
    }

    override fun write(data: PacketBuffer) {
        data.writeByte(windowId)
        data.writeString(windowType)
        data.writeChatComponent(windowTitle)
        data.writeByte(slots)
        if(windowType == "EntityHorse")
            data.writeInt(entityHorseId)
    }
}
