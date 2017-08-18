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

package com.minecraft.moonlake.player.attribute

import com.minecraft.moonlake.api.attribute.AttributeType
import com.minecraft.moonlake.api.player.MoonLakePlayer
import org.bukkit.attribute.Attribute

open class AttributeImpl_v1_12_R1(player: MoonLakePlayer, type: AttributeType) : AttributeImpl_v1_11_R1(player, type) {

    override fun adapter(): Attribute? = getType().let {
        when(it == AttributeType.FLYING_SPEED) {
            true -> org.bukkit.attribute.Attribute.GENERIC_FLYING_SPEED
            else -> super.adapter()
        }
    }
}
