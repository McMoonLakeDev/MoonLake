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

import com.minecraft.moonlake.api.util.Enums
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

data class PacketOutEntityEquipment(
        var entityId: Int,
        var equipmentSlot: EquipmentSlot,
        var itemStack: ItemStack?) : PacketOutBukkitAbstract("PacketPlayOutEntityEquipment") {

    @Deprecated("")
    constructor() : this(-1, EquipmentSlot.HAND, null)

    override fun read(data: PacketBuffer) {
        entityId = data.readVarInt()
        equipmentSlot = Enums.ofOrigin(EquipmentSlot::class.java, data.readVarInt()) ?: EquipmentSlot.HAND
        itemStack = data.readItemStack()
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(entityId)
        data.writeVarInt(equipmentSlot.ordinal)
        data.writeItemStack(itemStack)
    }
}
