/*
 * Copyright (C) 2016-2017 The MoonLake (mcmoonlake@hotmail.com)
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

import com.minecraft.moonlake.api.chat.ChatAction
import com.minecraft.moonlake.api.chat.ChatComponent
import com.minecraft.moonlake.api.chat.ChatComponentText

class PacketOutChat(
        var message: ChatComponent,
        var action: ChatAction) : PacketOutBukkitAbstract("PacketPlayOutChat") {

    constructor(message: ChatComponent) : this(message, ChatAction.CHAT)
    constructor() : this(ChatComponentText(""), ChatAction.CHAT)

    override fun read(data: PacketBuffer) {
        message = data.readChatComponent()
        action = ChatAction.fromValue(data.readByte())
    }

    override fun write(data: PacketBuffer) {
        data.writeChatComponent(message)
        data.writeByte(action.value)
    }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is PacketOutChat)
            return super.equals(other) && message == other.message && action == other.action
        return false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + message.hashCode()
        result = 31 * result + action.hashCode()
        return result
    }

    override fun toString(): String {
        return "PacketOutChat(message=$message, action=$action)"
    }
}
