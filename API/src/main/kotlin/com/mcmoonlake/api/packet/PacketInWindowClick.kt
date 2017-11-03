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

import com.mcmoonlake.api.Valuable
import com.mcmoonlake.api.util.Enums
import org.bukkit.inventory.ItemStack

data class PacketInWindowClick(
        var windowId: Int,
        var slot: Int,
        var button: Int,
        var action: Int,
        var clickType: ClickType,
        var clickedItem: ItemStack?) : PacketInBukkitAbstract("PacketPlayInWindowClick") {

    @Deprecated("")
    constructor() : this(-1, 0, 0, 0, ClickType.PICKUP, null)

    override fun read(data: PacketBuffer) {
        windowId = data.readByte().toInt()
        slot = data.readShort().toInt()
        button = data.readByte().toInt()
        action = data.readShort().toInt()
        clickType = Enums.ofValuable(ClickType::class.java, data.readVarInt()) ?: ClickType.PICKUP
        clickedItem = data.readItemStack()
    }

    override fun write(data: PacketBuffer) {
        data.writeByte(windowId)
        data.writeShort(slot)
        data.writeByte(button)
        data.writeShort(action)
        data.writeVarInt(clickType.value())
        data.writeItemStack(clickedItem)
    }

    enum class ClickType(val value: Int) : Valuable<Int> {

        PICKUP(0),
        QUICK_MOVE(1),
        SWAP(2),
        CLONE(3),
        THROW(4),
        QUICK_CRAFT(5),
        PICKUP_ALL(6),
        ;

        override fun value(): Int
                = value
    }
}
