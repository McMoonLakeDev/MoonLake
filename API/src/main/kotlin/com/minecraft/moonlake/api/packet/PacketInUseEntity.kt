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

import com.minecraft.moonlake.api.Valuable
import com.minecraft.moonlake.api.util.Enums
import com.minecraft.moonlake.api.wrapper.EnumHand
import org.bukkit.util.Vector

data class PacketInUseEntity(var entityId: Int, var action: Action, var target: Vector?, var hand: EnumHand?) : PacketInBukkitAbstract("PacketPlayInUseEntity") {

    @Deprecated("")
    constructor() : this(-1, Action.INTERACT, null, null)

    override fun read(data: PacketBuffer) {
        entityId = data.readVarInt()
        action = Enums.ofValuable(Action::class.java, data.readVarInt()) ?: Action.INTERACT
        if(action == Action.INTERACT_AT)
            target = Vector(data.readFloat(), data.readFloat(), data.readFloat())
        if((action == Action.INTERACT || action == Action.INTERACT_AT) && EnumHand.support())
            hand = Enums.ofValuable(EnumHand::class.java, data.readVarInt(), EnumHand.MAIN)
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(entityId)
        data.writeVarInt(action.type)
        if(action == Action.INTERACT_AT) {
            data.writeFloat(target?.x?.toFloat() ?: 0f)
            data.writeFloat(target?.y?.toFloat() ?: 0f)
            data.writeFloat(target?.z?.toFloat() ?: 0f)
        }
        if((action == Action.INTERACT || action == Action.INTERACT_AT) && EnumHand.support())
            data.writeVarInt(hand?.value ?: EnumHand.MAIN.value)
    }

// TODO NMS World getEntity(Int)
//    fun getEntity(world: World): Entity?
//            = world.entities.find { it.entityId == entityId }

    enum class Action(val type: Int) : Valuable<Int> {

        INTERACT(0),
        ATTACK(1),
        INTERACT_AT(2),
        ;

        override fun value(): Int
                = type
    }
}
