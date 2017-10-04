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

package com.minecraft.moonlake.api.item

import com.minecraft.moonlake.api.attribute.AttributeModifier
import com.minecraft.moonlake.api.attribute.AttributeType
import com.minecraft.moonlake.api.attribute.Operation
import com.minecraft.moonlake.api.attribute.Slot
import com.minecraft.moonlake.api.effect.EffectBase
import com.minecraft.moonlake.api.effect.EffectCustom
import com.minecraft.moonlake.api.effect.EffectType
import com.minecraft.moonlake.api.funs.Builder
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

interface ItemBuilder : Builder<ItemStack> {

    override fun build(): ItemStack

    /**
     * general meta
     * @see org.bukkit.inventory.meta.ItemMeta
     */

    fun getDisplayName(block: (self: ItemBuilder, displayName: String?) -> Unit): ItemBuilder

    fun setDisplayName(displayName: String): ItemBuilder

    fun getLocalizedName(block: (self: ItemBuilder, localizedName: String?) -> Unit): ItemBuilder

    fun setLocalizedName(localizedName: String): ItemBuilder

    fun getLore(block: (self: ItemBuilder, lore: List<String>?) -> Unit): ItemBuilder

    fun setLore(vararg lore: String): ItemBuilder

    fun setLore(lore: Collection<String>): ItemBuilder

    fun addLore(vararg lore: String): ItemBuilder

    fun addLore(lore: Collection<String>): ItemBuilder

    fun clearLore(): ItemBuilder

    fun getEnchant(block: (self: ItemBuilder, ench: Map<Enchantment, Int>?) -> Unit): ItemBuilder

    fun addEnchant(enchantment: Enchantment, level: Int): ItemBuilder

    fun clearEnchant(): ItemBuilder

    fun getFlag(block: (self: ItemBuilder, flag: Array<out ItemFlag>?) -> Unit): ItemBuilder

    fun addFlag(vararg flag: ItemFlag): ItemBuilder

    fun addFlag(flag: Collection<ItemFlag>): ItemBuilder

    fun removeFlag(vararg flag: ItemFlag): ItemBuilder

    fun removeFlag(flag: Collection<ItemFlag>): ItemBuilder

    fun clearFlag(): ItemBuilder

    fun isUnbreakable(block: (self: ItemBuilder, unbreakable: Boolean) -> Unit): ItemBuilder

    fun setUnbreakable(unbreakable: Boolean): ItemBuilder

    fun getAttribute(block: (self: ItemBuilder, attribute: Set<AttributeModifier>?) -> Unit): ItemBuilder

    fun setAttribute(type: AttributeType, operation: Operation, amount: Double): ItemBuilder

    fun setAttribute(type: AttributeType, operation: Operation, slot: Slot?, amount: Double): ItemBuilder

    fun clearAttribute(): ItemBuilder

    fun getCanDestroy(block: (self: ItemBuilder, canDestroy: Set<Material>?) -> Unit): ItemBuilder

    fun setCanDestroy(vararg type: Material): ItemBuilder

    fun getCanPlaceOn(block: (self: ItemBuilder, canPlaceOn: Set<Material>?) -> Unit): ItemBuilder

    fun setCanPlaceOn(vararg type: Material): ItemBuilder

    fun getRepairCost(block: (self: ItemBuilder, repairCost: Int?) -> Unit): ItemBuilder

    fun setRepairCost(value: Int): ItemBuilder

    /**
     * leather armor meta
     * @see org.bukkit.inventory.meta.LeatherArmorMeta
     */

    fun getLeatherColor(block: (self: ItemBuilder, color: Color?) -> Unit): ItemBuilder

    fun setLeatherColor(color: Color): ItemBuilder

    fun setLeatherColor(red: Int, green: Int, blue: Int): ItemBuilder

    /**
     * book meta
     * @see org.bukkit.inventory.meta.BookMeta
     */

    fun getBookTitle(block: (self: ItemBuilder, title: String?) -> Unit): ItemBuilder

    fun setBookTitle(title: String): ItemBuilder

    fun getBookAuthor(block: (self: ItemBuilder, author: String?) -> Unit): ItemBuilder

    fun setBookAuthor(author: String): ItemBuilder

    fun getBookGeneration(block: (self: ItemBuilder, generation: Generation?) -> Unit): ItemBuilder

    fun setBookGeneration(generation: Generation): ItemBuilder

    fun getBookPages(block: (self: ItemBuilder, pages: Collection<String>?) -> Unit): ItemBuilder

    fun setBookPages(vararg pages: String): ItemBuilder

    fun setBookPages(pages: Collection<String>): ItemBuilder

    fun addBookPages(vararg pages: String): ItemBuilder

    fun addBookPages(pages: Collection<String>): ItemBuilder

    fun clearBookPage(): ItemBuilder

    /**
     * enchantment storage meta
     * @see org.bukkit.inventory.meta.EnchantmentStorageMeta
     */

    fun getStoredEnchant(block: (self: ItemBuilder, ench: Map<Enchantment, Int>?) -> Unit): ItemBuilder

    fun addStoredEnchant(enchantment: Enchantment, level: Int): ItemBuilder

    fun clearStoredEnchant(): ItemBuilder

    /**
     * skull meta
     * @see org.bukkit.inventory.meta.SkullMeta
     */

    fun getSkullOwner(block: (self: ItemBuilder, owner: String?) -> Unit): ItemBuilder

    fun setSkullOwner(owner: String): ItemBuilder

    fun getSkullTexture(block: (self: ItemBuilder, value: String?) -> Unit): ItemBuilder

    fun setSkullTexture(value: String): ItemBuilder

    /**
     * spawn egg meta
     * @see org.bukkit.inventory.meta.SpawnEggMeta
     */

    fun getSpawnEggType(block: (self: ItemBuilder, type: EntityType?) -> Unit): ItemBuilder

    fun setSpawnEggType(type: EntityType): ItemBuilder

    fun setSpawnEggType(entity: Entity): ItemBuilder

    /**
     * map meta
     * @see org.bukkit.inventory.meta.MapMeta
     */

    fun getMapScaling(block: (self: ItemBuilder, scaling: Boolean) -> Unit): ItemBuilder

    fun setMapScaling(scaling: Boolean): ItemBuilder

    fun getMapLocationName(block: (self: ItemBuilder, locationName: String?) -> Unit): ItemBuilder

    fun setMapLocationName(locationName: String): ItemBuilder

    fun getMapColor(block: (self: ItemBuilder, color: Color?) -> Unit): ItemBuilder

    fun setMapColor(color: Color): ItemBuilder

    /**
     * potion meta
     * @see org.bukkit.inventory.meta.PotionMeta
     */

    fun getPotionColor(block: (self: ItemBuilder, color: Color?) -> Unit): ItemBuilder

    fun setPotionColor(color: Color): ItemBuilder

    fun getPotionBase(block: (self: ItemBuilder, base: EffectBase?) -> Unit): ItemBuilder

    fun setPotionBase(base: EffectBase): ItemBuilder

    fun getPotionEffect(block: (self: ItemBuilder, effect: Collection<EffectCustom>?) -> Unit): ItemBuilder

    fun addPotionEffect(effect: EffectCustom): ItemBuilder

    fun addPotionEffect(type: EffectType, duration: Int, amplifier: Int, ambient: Boolean = true, particle: Boolean = true, color: Color? = null): ItemBuilder

    fun clearPotionEffect(): ItemBuilder

    /**
     * firework meta
     * @see org.bukkit.inventory.meta.FireworkMeta
     */

    fun getFireworkEffect(block: (self: ItemBuilder, effect: Collection<FireworkEffect>?) -> Unit): ItemBuilder

    fun addFireworkEffect(vararg effect: FireworkEffect): ItemBuilder

    fun addFireworkEffect(effect: Collection<FireworkEffect>): ItemBuilder

    fun clearFireworkEffect(): ItemBuilder

    fun getFireworkPower(block: (self: ItemBuilder, power: Int?) -> Unit): ItemBuilder

    fun setFireworkPower(power: Int): ItemBuilder

    /**
     * banner meta
     * @see org.bukkit.inventory.meta.BannerMeta
     */

    fun getBannerPattern(block: (self: ItemBuilder, pattern: Collection<Pattern>?) -> Unit): ItemBuilder

    fun setBannerPattern(pattern: Collection<Pattern>): ItemBuilder

    fun addBannerPattern(pattern: Pattern): ItemBuilder

    fun clearBannerPattern(): ItemBuilder

    /** static */

    companion object {

        fun of(itemStack: ItemStack): ItemBuilder
                = object: ItemBuilderAbstract(itemStack) {}

        fun of(material: Material, amount: Int = 1, durability: Int = 0): ItemBuilder
                = object: ItemBuilderAbstract(material, amount, durability) {}
    }
}
