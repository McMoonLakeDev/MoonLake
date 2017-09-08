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
import com.minecraft.moonlake.api.getAddBitModifier
import com.minecraft.moonlake.api.getRemoveBitModifier
import com.minecraft.moonlake.api.nbt.NBTCompound
import com.minecraft.moonlake.api.nbt.NBTFactory
import com.minecraft.moonlake.api.nbt.NBTList
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.block.banner.Pattern
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*

abstract class ItemBuilderAbstract(type: Material, amount: Int = 1, durability: Int = 0) : ItemBuilder {

    private val itemStack = ItemStack(type, amount, durability.toShort()) // new material item stack object
    private val tag: NBTCompound by lazy { NBTFactory.ofCompound("tag") }

    override fun build(): ItemStack
            { NBTFactory.writeStackTag(itemStack, tag); return itemStack; }

    /** protected */

    open protected fun tagDisplay(): NBTCompound
            = tag.getCompoundOrDefault("display")

    open protected fun tagDisplayLore(): NBTList<String>
            = tagDisplay().getListOrDefault("Lore")

    open protected fun tagEnchant(): NBTList<NBTCompound>
            = tag.getListOrDefault("ench")

    open protected fun tagAttributeModifiers(): NBTList<NBTCompound>
            = tag.getListOrDefault("AttributeModifiers")

    /** general */

    override fun setDisplayName(displayName: String): ItemBuilder
            { tagDisplay().putString("Name", displayName); return this; }

    override fun setLocalizedName(localizedName: String): ItemBuilder
            { tagDisplay().putString("LocName", localizedName); return this; }

    override fun setLore(vararg lore: String): ItemBuilder
            = setLore(lore.toList())

    override fun setLore(lore: Collection<String>): ItemBuilder
            { clearLore(); return addLore(lore); }

    override fun addLore(vararg lore: String): ItemBuilder
            = addLore(lore.toList())

    override fun addLore(lore: Collection<String>): ItemBuilder
            { lore.forEach { tagDisplayLore().addString(it) }; return this; }

    override fun clearLore(): ItemBuilder
            { tagDisplayLore().clear(); return this; }

    override fun addEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            { tagEnchant().addCompound(NBTFactory.ofCompound("").putShort("id", enchantment.id).putShort("lvl", level)); return this; }

    override fun addSafeEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            { /** check safe */ return addEnchant(enchantment, level); }

    override fun removeEnchant(enchantment: Enchantment): ItemBuilder
            { itemStack.removeEnchantment(enchantment); return this; }

    override fun clearEnchant(): ItemBuilder
            { tagEnchant().clear(); return this; }

    override fun addFlag(vararg flag: ItemFlag): ItemBuilder
            { tag.putInt("HideFlags", flag.getAddBitModifier()); return this; }

    override fun addFlag(flag: Collection<ItemFlag>): ItemBuilder
            = addFlag(*flag.toTypedArray())

    override fun removeFlag(vararg flag: ItemFlag): ItemBuilder
            { tag.putInt("HideFlags", flag.getRemoveBitModifier()); return this; }

    override fun removeFlag(flag: Collection<ItemFlag>): ItemBuilder
            = removeFlag(*flag.toTypedArray())

    override fun clearFlag(): ItemBuilder
            { tag.remove("HideFlags"); return this; }

    override fun setUnbreakable(unbreakable: Boolean): ItemBuilder
            { tag.putBoolean("Unbreakable", unbreakable); return this; }

    override fun setAttribute(type: AttributeType, operation: AttributeOperation, amount: Double): ItemBuilder
            = setAttribute(type, operation, null, amount)

    override fun setAttribute(type: AttributeType, operation: AttributeOperation, slot: AttributeSlot?, amount: Double): ItemBuilder {
        val attribute = NBTFactory.ofCompound()
        val attributeUUID = UUID.randomUUID()
        if(slot != null) attribute.putString("Slot", slot.value())
        attribute.putString("Name", type.value())
        attribute.putString("AttributeName", type.value())
        attribute.putInt("Operation", operation.ordinal)
        attribute.putDouble("Amount", amount)
        attribute.putLong("UUIDMost", attributeUUID.mostSignificantBits)
        attribute.putLong("UUIDLeast", attributeUUID.leastSignificantBits)
        tagAttributeModifiers().addCompound(attribute)
        return this
    }

    override fun setCanDestroy(vararg type: Material): ItemBuilder {
        val canDestroy = NBTFactory.ofList<String>("CanDestroy")
        type.forEach { canDestroy.addString("minecraft:${it.name.toLowerCase()}") }
        tag.putList(canDestroy)
        return this
    }

    override fun setCanPlaceOn(vararg type: Material): ItemBuilder {
        val canPlaceOn = NBTFactory.ofList<String>("CanPlaceOn")
        type.forEach { canPlaceOn.addString("minecraft:${it.name.toLowerCase()}") }
        tag.putList(canPlaceOn)
        return this
    }

    /** leather armor */

    override fun setLeatherColor(color: Color): ItemBuilder
            { tagDisplay().putInt("color", color.asRGB()); return this; }

    override fun setLeatherColor(red: Int, green: Int, blue: Int): ItemBuilder
            { tagDisplay().putInt("color", Color.fromRGB(red, green, blue).asRGB()); return this; }

    /** book */

    override fun setBookTitle(title: String): ItemBuilder
            { tag.putString("title", title); return this; }

    override fun setBookAuthor(author: String): ItemBuilder
            { tag.putString("author", author); return this; }

    override fun setBookGeneration(generation: BookGeneration): ItemBuilder
            { tag.putInt("generation", generation.ordinal); return this; }

    override fun setBookPage(index: Int, page: String): ItemBuilder
            = throw UnsupportedOperationException()

    override fun setBookPages(vararg pages: String): ItemBuilder
            = throw UnsupportedOperationException()

    override fun setBookPages(pages: Collection<String>): ItemBuilder
            = setBookPages(*pages.toTypedArray())

    override fun addBookPages(vararg pages: String): ItemBuilder
            = throw UnsupportedOperationException()

    override fun addBookPages(pages: Collection<String>): ItemBuilder
            = addBookPages(*pages.toTypedArray())

    /** enchantment storage */

    override fun addStoredEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            = throw UnsupportedOperationException()

    override fun addStoredSafeEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            = throw UnsupportedOperationException()

    override fun removeStoredEnchant(enchantment: Enchantment): ItemBuilder
            = throw UnsupportedOperationException()

    override fun clearStoredEnchant(): ItemBuilder
            = throw UnsupportedOperationException()

    /** skull */

    override fun setSkullOwner(owner: String): ItemBuilder
            { tag.putString("SkullOwner", owner); return this; }

    /** spawn egg */

    override fun setSpawnEggType(type: EntityType): ItemBuilder
            = throw UnsupportedOperationException()

    /** map */

    override fun setMapScaling(scaling: Boolean): ItemBuilder
            = throw UnsupportedOperationException()

    override fun setMapLocationName(locationName: String): ItemBuilder
            { tagDisplay().putString("LocName", locationName); return this; }

    override fun setMapColor(color: Color): ItemBuilder
            { tagDisplay().putInt("MapColor", color.asRGB()); return this; }

    /** potion */

    override fun setPotionColor(color: Color): ItemBuilder
            { tag.putInt("CustomPotionColor", color.asRGB()); return this; }

    override fun setPotionBase(type: String): ItemBuilder
            { tag.putString("Potion", type); return this; }

    override fun addPotionEffect(effect: PotionEffect, overwrite: Boolean): ItemBuilder
            = throw UnsupportedOperationException()

    override fun removePotionEffect(type: PotionEffectType): ItemBuilder
            = throw UnsupportedOperationException()

    override fun clearPotionEffect(): ItemBuilder
            = throw UnsupportedOperationException()

    /** firework */

    override fun addFireworkEffect(vararg effect: FireworkEffect): ItemBuilder
            = throw UnsupportedOperationException()

    override fun addFireworkEffect(effect: Collection<FireworkEffect>): ItemBuilder
            = throw UnsupportedOperationException()

    override fun clearFireworkEffect(): ItemBuilder
            = throw UnsupportedOperationException()

    override fun setFireworkPower(power: Int): ItemBuilder
            = throw UnsupportedOperationException()

    /** banner */

    override fun setBannerPattern(index: Int, pattern: Pattern): ItemBuilder
            = throw UnsupportedOperationException()

    override fun setBannerPattern(pattern: Collection<Pattern>): ItemBuilder
            = throw UnsupportedOperationException()

    override fun addBannerPattern(pattern: Pattern): ItemBuilder
            = throw UnsupportedOperationException()

    override fun removeBannerPattern(index: Int): ItemBuilder
            = throw UnsupportedOperationException()
}
