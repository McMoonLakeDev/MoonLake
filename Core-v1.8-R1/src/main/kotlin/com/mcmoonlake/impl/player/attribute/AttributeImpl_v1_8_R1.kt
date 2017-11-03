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

package com.mcmoonlake.impl.player.attribute

import com.mcmoonlake.api.attribute.Attribute
import com.mcmoonlake.api.attribute.AttributeModifier
import com.mcmoonlake.api.attribute.AttributeType
import com.mcmoonlake.api.attribute.Attributes
import com.mcmoonlake.api.player.MoonLakePlayer

open class AttributeImpl_v1_8_R1(player: MoonLakePlayer, type: AttributeType) : AttributeBase(player, type) {

    private val handle: Attribute by lazy {
        Attributes.getEntityAttribute(player.bukkitPlayer, type) }

    override var baseValue: Double
        get() = handle.baseValue
        set(value) { handle.baseValue = value }

    override val value: Double
        get() = handle.value

    override val modifiers: Collection<AttributeModifier>
        get() = handle.modifiers

    override fun addModifier(modifier: AttributeModifier)
            = handle.addModifier(modifier)

    override fun removeModifier(modifier: AttributeModifier)
            = handle.removeModifier(modifier)
}
