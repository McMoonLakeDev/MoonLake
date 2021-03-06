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

package com.mcmoonlake.impl.player

import com.mcmoonlake.api.attribute.Attribute
import com.mcmoonlake.api.attribute.AttributeType
import com.mcmoonlake.impl.player.attribute.AttributeImpl_v1_12_R1
import org.bukkit.entity.Player

open class MoonLakePlayerImpl_v1_12_R1(
        player: Player
) : MoonLakePlayerImpl_v1_11_R1(player) {

    override val locale: String
        get() = bukkitPlayer.locale

    override fun getAttribute(type: AttributeType): Attribute
            = AttributeImpl_v1_12_R1(this, type)
}
