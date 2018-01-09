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
import com.mcmoonlake.api.notNull
import com.mcmoonlake.api.ofValuableNotNull

data class PacketOutRecipes(
        var action: Action,
        var craftingBookOpen: Boolean,
        var craftingFilter: Boolean,
        var recipeIds: List<Int>,
        var recipeIdsTwo: List<Int>?
) : PacketOutBukkitAbstract("PacketPlayOutRecipes"),
        PacketBukkitFreshly {

    @Deprecated("")
    constructor() : this(Action.REMOVE, false, false, ArrayList(), null)

    override fun read(data: PacketBuffer) {
        action = ofValuableNotNull(data.readVarInt())
        craftingBookOpen = data.readBoolean()
        craftingFilter = data.readBoolean()
        recipeIds = (0 until data.readVarInt()).map { data.readVarInt() }
        if(action == Action.INIT)
            recipeIdsTwo = (0 until data.readVarInt()).map { data.readVarInt() }
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(action.value())
        data.writeBoolean(craftingBookOpen)
        data.writeBoolean(craftingFilter)
        data.writeVarInt(recipeIds.size)
        recipeIds.forEach { data.writeVarInt(it) }
        if(action == Action.INIT) {
            data.writeVarInt(recipeIdsTwo.notNull().size)
            recipeIdsTwo.notNull().forEach { data.writeVarInt(it) }
        }
    }

    enum class Action : Valuable<Int> {

        INIT,
        ADD,
        REMOVE,
        ;

        override fun value(): Int
                = ordinal
    }
}
