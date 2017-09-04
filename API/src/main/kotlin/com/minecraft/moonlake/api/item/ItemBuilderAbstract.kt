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
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.block.banner.Pattern
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.potion.PotionType

abstract class ItemBuilderAbstract(private val type: Material, private var amount: Int = 1, private var durability: Int = 0) : ItemBuilder {

    private val itemMeta: ItemMeta = ItemStack(type).itemMeta // copy material item meta object

    override fun build(): ItemStack {
        val itemStack = ItemStack(type, amount, durability.toShort())
        itemStack.itemMeta = itemMeta.clone()
        return itemStack
    }

    /** general */

    override fun setAmount(amount: Int): ItemBuilder
            { this.amount = amount; return this; }

    override fun setDurability(durability: Int): ItemBuilder
            { this.durability = durability; return this; }

    override fun addDurability(durability: Int): ItemBuilder
            { this.durability -= durability; return this; }

    override fun takeDurability(durability: Int): ItemBuilder
            { this.durability += durability; return this; }

    override fun setDisplayName(displayName: String): ItemBuilder
            { itemMeta.displayName = displayName; return this; }

    override fun setLocalizedName(localizedName: String): ItemBuilder {
        // TODO NBT
        throw UnsupportedOperationException()
    }

    override fun setLore(vararg lore: String): ItemBuilder
            = setLore(lore.toList())

    override fun setLore(lore: Collection<String>): ItemBuilder
            { itemMeta.lore = lore.toList(); return this; }

    override fun addLore(vararg lore: String): ItemBuilder
            = addLore(lore.toList())

    override fun addLore(lore: Collection<String>): ItemBuilder {
        val itemLore = itemMeta.lore ?: ArrayList()
        itemLore.addAll(lore)
        itemMeta.lore = itemLore
        return this
    }

    override fun clearLore(): ItemBuilder
            { itemMeta.lore = null; return this; }

    override fun addEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            { itemMeta.addEnchant(enchantment, level, true); return this; }

    override fun addSafeEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            { itemMeta.addEnchant(enchantment, level, false); return this; }

    override fun removeEnchant(enchantment: Enchantment): ItemBuilder
            { itemMeta.removeEnchant(enchantment); return this; }

    override fun clearEnchant(): ItemBuilder
            { Enchantment.values().forEach { itemMeta.removeEnchant(it) }; return this; }

    override fun addFlag(vararg flag: ItemFlag): ItemBuilder
            { itemMeta.addItemFlags(*flag); return this; }

    override fun addFlag(flag: Collection<ItemFlag>): ItemBuilder
            = addFlag(*flag.toTypedArray())

    override fun removeFlag(vararg flag: ItemFlag): ItemBuilder
            { itemMeta.removeItemFlags(*flag); return this; }

    override fun removeFlag(flag: Collection<ItemFlag>): ItemBuilder
            = removeFlag(*flag.toTypedArray())

    override fun clearFlag(): ItemBuilder
            { itemMeta.removeItemFlags(*ItemFlag.values()); return this; }

    override fun setUnbreakable(unbreakable: Boolean): ItemBuilder {
        // TODO NBT
        throw UnsupportedOperationException()
    }

    override fun setAttribute(type: AttributeType, operation: AttributeOperation, amount: Double): ItemBuilder {
        // TODO NBT
        throw UnsupportedOperationException()
    }

    override fun setAttribute(type: AttributeType, operation: AttributeOperation, slot: AttributeSlot, amount: Double): ItemBuilder {
        // TODO NBT
        throw UnsupportedOperationException()
    }

    override fun setAge(age: Int): ItemBuilder {
        // TODO NBT
        throw UnsupportedOperationException()
    }

    override fun setPickupDelay(pickupDelay: Int): ItemBuilder {
        // TODO NBT
        throw UnsupportedOperationException()
    }

    override fun setCanDestroy(vararg type: Material): ItemBuilder {
        // TODO NBT
        throw UnsupportedOperationException()
    }

    override fun setCanPlaceOn(vararg type: Material): ItemBuilder {
        // TODO NBT
        throw UnsupportedOperationException()
    }

    /** leather armor */

    override fun setLeatherColor(color: Color): ItemBuilder
            { (itemMeta as LeatherArmorMeta).color = color; return this; }

    override fun setLeatherColor(red: Int, green: Int, blue: Int): ItemBuilder
            { (itemMeta as LeatherArmorMeta).color = Color.fromBGR(red, green, blue); return this; }

    /** book */

    override fun setBookTitle(title: String): ItemBuilder
            { (itemMeta as BookMeta).title = title; return this; }

    override fun setBookAuthor(author: String): ItemBuilder
            { (itemMeta as BookMeta).author = author; return this; }

    override fun setBookGeneration(generation: String): ItemBuilder {
        // TODO NBT
        throw UnsupportedOperationException()
    }

    override fun setBookPage(index: Int, page: String): ItemBuilder
            { (itemMeta as BookMeta).setPage(index, page); return this; }

    override fun setBookPages(vararg pages: String): ItemBuilder
            { (itemMeta as BookMeta).setPages(*pages); return this; }

    override fun setBookPages(pages: Collection<String>): ItemBuilder
            = setBookPages(*pages.toTypedArray())

    override fun addBookPages(vararg pages: String): ItemBuilder
            { (itemMeta as BookMeta).addPage(*pages); return this; }

    override fun addBookPages(pages: Collection<String>): ItemBuilder
            = addBookPages(*pages.toTypedArray())

    /** enchantment storage */

    override fun addStoredEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            { (itemMeta as EnchantmentStorageMeta).addStoredEnchant(enchantment, level, true); return this; }

    override fun addStoredSafeEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            { (itemMeta as EnchantmentStorageMeta).addStoredEnchant(enchantment, level, false); return this; }

    override fun removeStoredEnchant(enchantment: Enchantment): ItemBuilder
            { (itemMeta as EnchantmentStorageMeta).removeStoredEnchant(enchantment); return this; }

    override fun clearStoredEnchant(): ItemBuilder
            { Enchantment.values().forEach { (itemMeta as EnchantmentStorageMeta).removeStoredEnchant(it) }; return this; }

    /** skull */

    override fun setSkullOwner(owner: String): ItemBuilder
            { (itemMeta as SkullMeta).owner = owner; return this; }

    /** spawn egg */

    override fun setSpawnEggType(type: EntityType): ItemBuilder
            { (itemMeta as SpawnEggMeta).spawnedType = type; return this; }

    /** map */

    override fun setMapScaling(scaling: Boolean): ItemBuilder
            { (itemMeta as MapMeta).isScaling = scaling; return this; }

    override fun setMapLocationName(locationName: String): ItemBuilder {
        // TODO NBT
        throw UnsupportedOperationException()
    }

    override fun setMapColor(color: Color): ItemBuilder {
        // TODO NBT
        throw UnsupportedOperationException()
    }

    /** potion */

    override fun setPotionColor(color: Color): ItemBuilder {
        // TODO NBT
        throw UnsupportedOperationException()
    }

    override fun setPotionBase(type: PotionType): ItemBuilder {
        // TODO NBT
        throw UnsupportedOperationException()
    }

    override fun addPotionEffect(effect: PotionEffect, overwrite: Boolean): ItemBuilder
            { (itemMeta as PotionMeta).addCustomEffect(effect, overwrite); return this; }

    override fun removePotionEffect(type: PotionEffectType): ItemBuilder
            { (itemMeta as PotionMeta).removeCustomEffect(type); return this; }

    override fun clearPotionEffect(): ItemBuilder
            { (itemMeta as PotionMeta).clearCustomEffects(); return this; }

    /** firework */

    override fun addFireworkEffect(vararg effect: FireworkEffect): ItemBuilder
            { (itemMeta as FireworkMeta).addEffects(*effect); return this; }

    override fun addFireworkEffect(effect: Collection<FireworkEffect>): ItemBuilder
            { (itemMeta as FireworkMeta).addEffects(effect); return this; }

    override fun clearFireworkEffect(): ItemBuilder
            { (itemMeta as FireworkMeta).clearEffects(); return this; }

    override fun setFireworkPower(power: Int): ItemBuilder
            { (itemMeta as FireworkMeta).power = power; return this; }

    /** banner */

    override fun setBannerPattern(index: Int, pattern: Pattern): ItemBuilder
            { (itemMeta as BannerMeta).setPattern(index, pattern); return this; }

    override fun setBannerPattern(pattern: Collection<Pattern>): ItemBuilder
            { (itemMeta as BannerMeta).patterns = pattern.toList(); return this; }

    override fun addBannerPattern(pattern: Pattern): ItemBuilder
            { (itemMeta as BannerMeta).addPattern(pattern); return this; }

    override fun removeBannerPattern(index: Int): ItemBuilder
            { (itemMeta as BannerMeta).removePattern(index); return this; }
}
