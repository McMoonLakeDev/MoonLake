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

package com.minecraft.moonlake.impl.player

import com.minecraft.moonlake.api.attribute.Attribute
import com.minecraft.moonlake.api.attribute.AttributeType
import com.minecraft.moonlake.api.effect.EffectType
import com.minecraft.moonlake.api.item.ItemCooldowns
import com.minecraft.moonlake.impl.player.attribute.AttributeImpl_v1_9_R1
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect

open class MoonLakePlayerImpl_v1_9_R1(player: Player) : MoonLakePlayerImpl_v1_8_R3(player) {

    override fun getAttribute(type: AttributeType): Attribute
            = AttributeImpl_v1_9_R1(this, type)

    override var isInvulnerable: Boolean
        get() = bukkitPlayer.isInvulnerable
        set(value) { bukkitPlayer.isInvulnerable = value }

    override var isGlowing: Boolean
        get() = bukkitPlayer.isGlowing
        set(value) { bukkitPlayer.isGlowing = value }

    override var isGliding: Boolean
        get() = bukkitPlayer.isGliding
        set(value) { bukkitPlayer.isGlowing = value }

    override var spectatorTarget: Entity?
        get() = bukkitPlayer.spectatorTarget
        set(value) { bukkitPlayer.spectatorTarget = value }

    override fun addPotionEffect(type: EffectType, duration: Int, amplifier: Int, ambient: Boolean, particle: Boolean, color: Color?): Boolean
            = bukkitPlayer.addPotionEffect(PotionEffect(type.cast(), duration, amplifier, ambient, particle, color))

    override var itemInHand: ItemStack
        get() = itemInMainHand
        set(value) { itemInMainHand = value }

    override var itemInMainHand: ItemStack
        get() = inventory.itemInMainHand
        set(value) { inventory.itemInMainHand = value }

    override var itemInOffHand: ItemStack
        get() = inventory.itemInOffHand
        set(value) { inventory.itemInOffHand = value }

    override fun setCooldown(type: Material, ticks: Int)
            = ItemCooldowns.set(bukkitPlayer, type, ticks)

    override fun getCooldown(type: Material): Int
            = ItemCooldowns.get(bukkitPlayer, type)

    override fun hasCooldown(type: Material): Boolean
            = ItemCooldowns.has(bukkitPlayer, type)
}
