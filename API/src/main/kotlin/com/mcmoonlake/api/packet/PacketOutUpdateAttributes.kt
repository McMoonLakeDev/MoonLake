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

import com.mcmoonlake.api.attribute.AttributeModifier
import com.mcmoonlake.api.attribute.AttributeSnapshot
import com.mcmoonlake.api.attribute.Operation
import com.mcmoonlake.api.ofValuable

data class PacketOutUpdateAttributes(
        var entityId: Int,
        var attributes: MutableList<AttributeSnapshot>
) : PacketOutBukkitAbstract("PacketPlayOutUpdateAttributes") {

    @Deprecated("")
    constructor() : this(-1, ArrayList())

    override fun read(data: PacketBuffer) {
        entityId = data.readVarInt()
        val length = data.readInt()
        attributes = ArrayList(length)
        (0 until length).forEach {
            val type = data.readString()
            val value = data.readDouble()
            val modifierSize = data.readVarInt()
            val modifiers = ArrayList<AttributeModifier>(modifierSize)
            (0 until modifierSize).forEach {
                val uuid = data.readUUID()
                val amount = data.readDouble()
                val operation = ofValuable(data.readByte().toInt()) ?: Operation.ADD
                modifiers.add(AttributeModifier("Unknown synced attribute modifier", operation, amount, uuid))
            }
            attributes.add(AttributeSnapshot(type, value, modifiers))
        }
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(entityId)
        data.writeInt(attributes.size)
        attributes.forEach {
            data.writeString(it.type)
            data.writeDouble(it.value)
            data.writeVarInt(it.modifiers.size)
            it.modifiers.forEach {
                data.writeUUID(it.uuid)
                data.writeDouble(it.amount)
                data.writeByte(it.operation.value())
            }
        }
    }
}
