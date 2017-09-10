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

package com.minecraft.moonlake.api.item

import com.minecraft.moonlake.api.attribute.AttributeOperation
import com.minecraft.moonlake.api.attribute.AttributeSlot
import com.minecraft.moonlake.api.attribute.AttributeType
import com.minecraft.moonlake.api.funs.Builder
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.block.banner.Pattern
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect

interface ItemBuilder : Builder<ItemStack> {

    override fun build(): ItemStack

    /**
     * general meta
     * @see org.bukkit.inventory.meta.ItemMeta
     */

    fun setDisplayName(displayName: String): ItemBuilder

    fun setLocalizedName(localizedName: String): ItemBuilder

    fun setLore(vararg lore: String): ItemBuilder

    fun setLore(lore: Collection<String>): ItemBuilder

    fun addLore(vararg lore: String): ItemBuilder

    fun addLore(lore: Collection<String>): ItemBuilder

    fun clearLore(): ItemBuilder

    fun addEnchant(enchantment: Enchantment, level: Int): ItemBuilder

    fun addSafeEnchant(enchantment: Enchantment, level: Int): ItemBuilder

    fun clearEnchant(): ItemBuilder

    fun addFlag(vararg flag: ItemFlag): ItemBuilder

    fun addFlag(flag: Collection<ItemFlag>): ItemBuilder

    fun removeFlag(vararg flag: ItemFlag): ItemBuilder

    fun removeFlag(flag: Collection<ItemFlag>): ItemBuilder

    fun clearFlag(): ItemBuilder

    fun setUnbreakable(unbreakable: Boolean): ItemBuilder

    fun setAttribute(type: AttributeType, operation: AttributeOperation, amount: Double): ItemBuilder

    fun setAttribute(type: AttributeType, operation: AttributeOperation, slot: AttributeSlot?, amount: Double): ItemBuilder

    fun clearAttribute(): ItemBuilder

    fun setCanDestroy(vararg type: Material): ItemBuilder

    fun setCanPlaceOn(vararg type: Material): ItemBuilder

    fun setRepairCost(value: Int): ItemBuilder

    /**
     * leather armor meta
     * @see org.bukkit.inventory.meta.LeatherArmorMeta
     */

    fun setLeatherColor(color: Color): ItemBuilder

    fun setLeatherColor(red: Int, green: Int, blue: Int): ItemBuilder

    /**
     * book meta
     * @see org.bukkit.inventory.meta.BookMeta
     */

    fun setBookTitle(title: String): ItemBuilder

    fun setBookAuthor(author: String): ItemBuilder

    fun setBookGeneration(generation: BookGeneration): ItemBuilder

    fun setBookPages(vararg pages: String): ItemBuilder

    fun setBookPages(pages: Collection<String>): ItemBuilder

    fun addBookPages(vararg pages: String): ItemBuilder

    fun addBookPages(pages: Collection<String>): ItemBuilder

    fun clearBookPage(): ItemBuilder

    /**
     * enchantment storage meta
     * @see org.bukkit.inventory.meta.EnchantmentStorageMeta
     */

    fun addStoredEnchant(enchantment: Enchantment, level: Int): ItemBuilder

    fun addStoredSafeEnchant(enchantment: Enchantment, level: Int): ItemBuilder

    fun clearStoredEnchant(): ItemBuilder

    /**
     * skull meta
     * @see org.bukkit.inventory.meta.SkullMeta
     */

    fun setSkullOwner(owner: String): ItemBuilder

    /**
     * spawn egg meta
     * @see org.bukkit.inventory.meta.SpawnEggMeta
     */

    fun setSpawnEggType(type: EntityType): ItemBuilder

    // TODO fun setSpawnEggType(entity: Entity); read entity nbt data

    /**
     * map meta
     * @see org.bukkit.inventory.meta.MapMeta
     */

    fun setMapScaling(scaling: Boolean): ItemBuilder

    fun setMapLocationName(locationName: String): ItemBuilder

    fun setMapColor(color: Color): ItemBuilder

    /**
     * potion meta
     * @see org.bukkit.inventory.meta.PotionMeta
     */

    fun setPotionColor(color: Color): ItemBuilder

    fun setPotionBase(type: String): ItemBuilder // TODO PotionBaseWrapper

    fun addPotionEffect(effect: PotionEffect): ItemBuilder

    fun clearPotionEffect(): ItemBuilder

    /**
     * firework meta
     * @see org.bukkit.inventory.meta.FireworkMeta
     */

    fun addFireworkEffect(vararg effect: FireworkEffect): ItemBuilder

    fun addFireworkEffect(effect: Collection<FireworkEffect>): ItemBuilder

    fun clearFireworkEffect(): ItemBuilder

    fun setFireworkPower(power: Int): ItemBuilder

    /**
     * banner meta
     * @see org.bukkit.inventory.meta.BannerMeta
     */

    fun setBannerPattern(index: Int, pattern: Pattern): ItemBuilder

    fun setBannerPattern(pattern: Collection<Pattern>): ItemBuilder

    fun addBannerPattern(pattern: Pattern): ItemBuilder

    /** static */

    companion object {

        fun of(itemStack: ItemStack): ItemBuilder
                = object: ItemBuilderAbstract(itemStack.type, itemStack.amount, itemStack.durability.toInt()) {}

        fun of(material: Material, amount: Int = 1, durability: Int = 0): ItemBuilder
                = object: ItemBuilderAbstract(material, amount, durability) {}
    }
}
