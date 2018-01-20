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

import com.mcmoonlake.api.attribute.AttributeModifier
import com.mcmoonlake.api.attribute.AttributeType
import com.mcmoonlake.api.attribute.Operation
import com.mcmoonlake.api.player.MoonLakePlayer
import com.mcmoonlake.api.util.Enums
import org.bukkit.attribute.AttributeInstance

open class AttributeImpl_v1_9_R1(
        player: MoonLakePlayer,
        type: AttributeType
) : AttributeImpl_v1_8_R1(player, type) {

    override var baseValue: Double
        get() = instance?.baseValue ?: super.baseValue
        set(value) {
            val instance = instance
            if(instance == null)
                super.baseValue = value
            else
                instance.baseValue = value
        }

    override val value: Double
        get() = instance?.value ?: super.value

    override val modifiers: Collection<AttributeModifier>
        get() = instance?.modifiers?.map { convert(it) }?.toList() ?: super.modifiers

    override fun addModifier(modifier: AttributeModifier)
            = instance?.addModifier(convert(modifier)) ?: super.addModifier(modifier)

    override fun removeModifier(modifier: AttributeModifier)
            = instance?.removeModifier(convert(modifier)) ?: super.removeModifier(modifier)

    private val instance: AttributeInstance?
        get() {
            val adapter = adapter
            return if(adapter == null)
                null
            else
                player.bukkitPlayer.getAttribute(adapter)
        }

    protected open val adapter: org.bukkit.attribute.Attribute?
        get() = when(type) {
            AttributeType.MAX_HEALTH -> org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH
            AttributeType.FOLLOW_RANGE -> org.bukkit.attribute.Attribute.GENERIC_FOLLOW_RANGE
            AttributeType.KNOCKBACK_RESISTANCE -> org.bukkit.attribute.Attribute.GENERIC_KNOCKBACK_RESISTANCE
            AttributeType.MOVEMENT_SPEED -> org.bukkit.attribute.Attribute.GENERIC_MOVEMENT_SPEED
            AttributeType.ATTACK_DAMAGE -> org.bukkit.attribute.Attribute.GENERIC_ATTACK_DAMAGE
            AttributeType.ATTACK_SPEED -> org.bukkit.attribute.Attribute.GENERIC_ATTACK_SPEED
            AttributeType.ARMOR -> org.bukkit.attribute.Attribute.GENERIC_ARMOR
            AttributeType.LUCK -> org.bukkit.attribute.Attribute.GENERIC_LUCK
            AttributeType.ARMOR_TOUGHNESS -> null
            else -> null
        }

    companion object {

        @JvmStatic
        @JvmName("convert")
        internal fun convert(bukkit: org.bukkit.attribute.AttributeModifier): AttributeModifier
                = AttributeModifier(bukkit.name, Enums.ofValuable(Operation::class.java, bukkit.operation.ordinal) ?: Operation.ADD, bukkit.amount, bukkit.uniqueId)

        @JvmStatic
        @JvmName("convert")
        internal fun convert(wrapped: AttributeModifier): org.bukkit.attribute.AttributeModifier
                = org.bukkit.attribute.AttributeModifier(wrapped.uuid, wrapped.name, wrapped.amount, Enums.ofOrigin(org.bukkit.attribute.AttributeModifier.Operation::class.java, wrapped.operation.value))
    }
}
