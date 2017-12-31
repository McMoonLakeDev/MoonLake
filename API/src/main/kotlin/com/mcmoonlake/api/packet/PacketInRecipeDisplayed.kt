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

data class PacketInRecipeDisplayed(
        var action: Action,
        var recipeId: Int?,
        var craftingBookOpen: Boolean?,
        var craftingFilter: Boolean?) : PacketInBukkitAbstract("PacketPlayInRecipeDisplayed"), PacketBukkitFreshly {

    @Deprecated("")
    constructor() : this(Action.SHOWN, -1, null, null)

    override fun read(data: PacketBuffer) {
        action = ofValuableNotNull(data.readVarInt())
        when(action) {
            Action.SHOWN -> recipeId = data.readInt()
            Action.SETTINGS -> {
                craftingBookOpen = data.readBoolean()
                craftingFilter = data.readBoolean()
            }
        }
    }

    override fun write(data: PacketBuffer) {
        data.writeVarInt(action.value())
        when(action) {
            Action.SHOWN -> data.writeInt(recipeId.notNull())
            Action.SETTINGS -> {
                data.writeBoolean(craftingBookOpen.notNull())
                data.writeBoolean(craftingFilter.notNull())
            }
        }
    }

    enum class Action : Valuable<Int> {

        SHOWN,
        SETTINGS,
        ;

        override fun value(): Int
                = ordinal
    }
}
