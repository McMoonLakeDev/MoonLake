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
import com.minecraft.moonlake.api.nbt.NBTCompound
import com.minecraft.moonlake.api.nbt.NBTFactory
import com.minecraft.moonlake.api.nbt.NBTList
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.block.banner.Pattern
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import java.util.*

abstract class ItemBuilderAbstract(type: Material, amount: Int = 1, durability: Int = 0) : ItemBuilder {

    /** member */

    private val itemStack = ItemStack(type, amount, durability.toShort()) // new material item stack object
    private val tag: NBTCompound by lazy { NBTFactory.ofCompound(TAG) }

    /** build */

    override fun build(): ItemStack
            = NBTFactory.writeStackTag(itemStack, tag)

    /** static */

    companion object {

        // const tag key

        private const val TAG = "tag"
        private const val TAG_ENCH = "ench"
        private const val TAG_ENCH_ID = "id"
        private const val TAG_ENCH_LVL = "lvl"
        private const val TAG_DISPLAY = "display"
        private const val TAG_DISPLAY_LORE = "Lore"
        private const val TAG_DISPLAY_NAME = "Name"
        private const val TAG_DISPLAY_LOC_NAME = "LocName"
        private const val TAG_ENTITY_TAG = "EntityTag"
        private const val TAG_ENTITY_TAG_ID = "id"
        private const val TAG_HIDE_FLAGS = "HideFlags"
        private const val TAG_REPAIR_COST = "RepairCost"
        private const val TAG_CAN_DESTROY = "CanDestroy"
        private const val TAG_CAN_PLACE_ON = "CanPlaceOn"
        private const val TAG_UNBREAKABLE = "Unbreakable"
        private const val TAG_ATTRIBUTE_SLOT = "Slot"
        private const val TAG_ATTRIBUTE_NAME = "Name"
        private const val TAG_ATTRIBUTE_AMOUNT = "Amount"
        private const val TAG_ATTRIBUTE_TYPE = "AttributeName"
        private const val TAG_ATTRIBUTE_OPERATION = "Operation"
        private const val TAG_ATTRIBUTE_UUID_MOST = "UUIDMost"
        private const val TAG_ATTRIBUTE_UUID_LEAST= "UUIDLeast"
        private const val TAG_ATTRIBUTE_MODIFIERS = "AttributeModifiers"
        private const val TAG_STORED_ENCHANTMENTS = "StoredEnchantments"
        private const val TAG_CUSTOM_POTION_EFFECTS = "CustomPotionEffects"
        private const val TAG_CUSTOM_POTION_COLOR = "CustomPotionColor"
        private const val TAG_BOOK_TITLE = "title"
        private const val TAG_BOOK_PAGES = "pages"
        private const val TAG_BOOK_AUTHOR = "author"
        private const val TAG_BOOK_GENERATION = "generation"
        private const val TAG_LEATHER_ARMOR_COLOR = "color"
        private const val TAG_MAP_COLOR = "MapColor"
        private const val TAG_MAP_SCALING = "map_is_scaling"
        private const val TAG_SKULL_OWNER = "SkullOwner"
        private const val TAG_POTION = "Potion"
        private const val TAG_POTION_ID = "Id"
        private const val TAG_POTION_AMPLIFIER = "Amplifier"
        private const val TAG_POTION_DURATION = "Duration"
        private const val TAG_POTION_AMBIENT = "Ambient"
        private const val TAG_POTION_SHOW_PARTICLES = "ShowParticles"
    }

    /** protected */

    open protected fun tagDisplay(): NBTCompound
            = tag.getCompoundOrDefault(TAG_DISPLAY)

    open protected fun tagDisplayLore(): NBTList<String>
            = tagDisplay().getListOrDefault(TAG_DISPLAY_LORE)

    open protected fun tagEnchant(): NBTList<NBTCompound>
            = tag.getListOrDefault(TAG_ENCH)

    open protected fun tagAttributeModifiers(): NBTList<NBTCompound>
            = tag.getListOrDefault(TAG_ATTRIBUTE_MODIFIERS)

    open protected fun tagStoredEnchantments(): NBTList<NBTCompound>
            = tag.getListOrDefault(TAG_STORED_ENCHANTMENTS)

    open protected fun tagPages(): NBTList<String>
            = tag.getListOrDefault(TAG_BOOK_PAGES)

    open protected fun tagEntityTag(): NBTCompound
            = tag.getCompoundOrDefault(TAG_ENTITY_TAG)

    open protected fun tagCustomPotionEffects(): NBTList<NBTCompound>
            = tag.getListOrDefault(TAG_CUSTOM_POTION_EFFECTS)

    /** general */

    override fun setDisplayName(displayName: String): ItemBuilder
            { tagDisplay().putString(TAG_DISPLAY_NAME, displayName); return this; }

    override fun setLocalizedName(localizedName: String): ItemBuilder
            { tagDisplay().putString(TAG_DISPLAY_LOC_NAME, localizedName); return this; }

    override fun setLore(vararg lore: String): ItemBuilder
            = setLore(lore.toList())

    override fun setLore(lore: Collection<String>): ItemBuilder
            { clearLore(); return addLore(lore); }

    override fun addLore(vararg lore: String): ItemBuilder
            = addLore(lore.toList())

    override fun addLore(lore: Collection<String>): ItemBuilder
            { lore.forEach { tagDisplayLore().addString(it) }; return this; }

    override fun clearLore(): ItemBuilder
            { if(tag.containsKey(TAG_DISPLAY)) tagDisplay().remove(TAG_DISPLAY_LORE); return this; }

    override fun addEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            { tagEnchant().addCompound(NBTFactory.ofCompound().putShort(TAG_ENCH_ID, enchantment.id).putShort(TAG_ENCH_LVL, level)); return this; }

    override fun addSafeEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            { enchantment.checkSafe(level); return addEnchant(enchantment, level); }

    override fun clearEnchant(): ItemBuilder
            { tag.remove(TAG_ENCH); return this; }

    private fun Array<out ItemFlag>.getAddBitModifier(modifier: Int = 0): Int {
        var value = modifier
        forEach { value = value or(1 shl it.ordinal)}
        return value
    }

    private fun Array<out ItemFlag>.getRemoveBitModifier(modifier: Int = 0): Int {
        var value = modifier
        forEach { value = value and(1 shl it.ordinal).inv() }
        return value
    }

    override fun addFlag(vararg flag: ItemFlag): ItemBuilder
            { tag.putInt(TAG_HIDE_FLAGS, flag.getAddBitModifier()); return this; }

    override fun addFlag(flag: Collection<ItemFlag>): ItemBuilder
            = addFlag(*flag.toTypedArray())

    override fun removeFlag(vararg flag: ItemFlag): ItemBuilder
            { tag.putInt(TAG_HIDE_FLAGS, flag.getRemoveBitModifier()); return this; }

    override fun removeFlag(flag: Collection<ItemFlag>): ItemBuilder
            = removeFlag(*flag.toTypedArray())

    override fun clearFlag(): ItemBuilder
            { tag.remove(TAG_HIDE_FLAGS); return this; }

    override fun setUnbreakable(unbreakable: Boolean): ItemBuilder
            { tag.putBoolean(TAG_UNBREAKABLE, unbreakable); return this; }

    override fun setAttribute(type: AttributeType, operation: AttributeOperation, amount: Double): ItemBuilder
            = setAttribute(type, operation, null, amount)

    override fun setAttribute(type: AttributeType, operation: AttributeOperation, slot: AttributeSlot?, amount: Double): ItemBuilder {
        val attribute = NBTFactory.ofCompound()
        val attributeUUID = UUID.randomUUID()
        if(slot != null) attribute.putString(TAG_ATTRIBUTE_SLOT, slot.value())
        attribute.putString(TAG_ATTRIBUTE_NAME, type.value())
        attribute.putString(TAG_ATTRIBUTE_TYPE, type.value())
        attribute.putInt(TAG_ATTRIBUTE_OPERATION, operation.ordinal)
        attribute.putDouble(TAG_ATTRIBUTE_AMOUNT, amount)
        attribute.putLong(TAG_ATTRIBUTE_UUID_MOST, attributeUUID.mostSignificantBits)
        attribute.putLong(TAG_ATTRIBUTE_UUID_LEAST, attributeUUID.leastSignificantBits)
        tagAttributeModifiers().addCompound(attribute)
        return this
    }

    override fun clearAttribute(): ItemBuilder
            { tag.remove(TAG_ATTRIBUTE_MODIFIERS); return this; }

    override fun setCanDestroy(vararg type: Material): ItemBuilder {
        val canDestroy = NBTFactory.ofList<String>(TAG_CAN_DESTROY)
        type.forEach { canDestroy.addString("minecraft:${it.name.toLowerCase()}") }
        tag.putList(canDestroy)
        return this
    }

    override fun setCanPlaceOn(vararg type: Material): ItemBuilder {
        val canPlaceOn = NBTFactory.ofList<String>(TAG_CAN_PLACE_ON)
        type.forEach { canPlaceOn.addString("minecraft:${it.name.toLowerCase()}") }
        tag.putList(canPlaceOn)
        return this
    }

    override fun setRepairCost(value: Int): ItemBuilder
            { tag.putInt(TAG_REPAIR_COST, value); return this; }

    /** leather armor */

    override fun setLeatherColor(color: Color): ItemBuilder
            { tagDisplay().putInt(TAG_LEATHER_ARMOR_COLOR, color.asRGB()); return this; }

    override fun setLeatherColor(red: Int, green: Int, blue: Int): ItemBuilder
            = setLeatherColor(Color.fromBGR(red, green, blue))

    /** book */

    override fun setBookTitle(title: String): ItemBuilder
            { tag.putString(TAG_BOOK_TITLE, title); return this; }

    override fun setBookAuthor(author: String): ItemBuilder
            { tag.putString(TAG_BOOK_AUTHOR, author); return this; }

    override fun setBookGeneration(generation: BookGeneration): ItemBuilder
            { tag.putInt(TAG_BOOK_GENERATION, generation.ordinal); return this; }

    override fun setBookPages(vararg pages: String): ItemBuilder
            { clearBookPage(); return addBookPages(*pages); }

    override fun setBookPages(pages: Collection<String>): ItemBuilder
            = setBookPages(*pages.toTypedArray())

    override fun addBookPages(vararg pages: String): ItemBuilder
            { pages.forEach { tagPages().addString(it) }; return this; }

    override fun addBookPages(pages: Collection<String>): ItemBuilder
            = addBookPages(*pages.toTypedArray())

    override fun clearBookPage(): ItemBuilder
            { tag.remove(TAG_BOOK_PAGES); return this; }

    /** enchantment storage */

    override fun addStoredEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            { tagStoredEnchantments().addCompound(NBTFactory.ofCompound().putShort(TAG_ENCH_ID, enchantment.id).putShort(TAG_ENCH_LVL, level)); return this; }

    override fun addStoredSafeEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            { enchantment.checkSafe(level); return addStoredEnchant(enchantment, level); }

    override fun clearStoredEnchant(): ItemBuilder
            { tag.remove(TAG_STORED_ENCHANTMENTS); return this; }

    /** skull */

    override fun setSkullOwner(owner: String): ItemBuilder
            { tag.putString(TAG_SKULL_OWNER, owner); return this; }

    /** spawn egg */

    override fun setSpawnEggType(type: EntityType): ItemBuilder
            { tagEntityTag().putString(TAG_ENTITY_TAG_ID, type.name); return this; }

    /** map */

    override fun setMapScaling(scaling: Boolean): ItemBuilder
            { tag.putBoolean(TAG_MAP_SCALING, scaling); return this; }

    override fun setMapLocationName(locationName: String): ItemBuilder
            { tagDisplay().putString(TAG_DISPLAY_LOC_NAME, locationName); return this; }

    override fun setMapColor(color: Color): ItemBuilder
            { tagDisplay().putInt(TAG_MAP_COLOR, color.asRGB()); return this; }

    /** potion */

    override fun setPotionColor(color: Color): ItemBuilder
            { tag.putInt(TAG_CUSTOM_POTION_COLOR, color.asRGB()); return this; }

    override fun setPotionBase(type: String): ItemBuilder
            { tag.putString(TAG_POTION, type); return this; }

    override fun addPotionEffect(effect: PotionEffect): ItemBuilder {
        val potionEffect = NBTFactory.ofCompound()
        potionEffect.putByte(TAG_POTION_ID, effect.type.id) // TODO EffectType
        potionEffect.putByte(TAG_POTION_AMPLIFIER, effect.amplifier)
        potionEffect.putInt(TAG_POTION_DURATION, effect.duration)
        potionEffect.putBoolean(TAG_POTION_AMBIENT, effect.isAmbient)
        potionEffect.putBoolean(TAG_POTION_SHOW_PARTICLES, effect.hasParticles())
        tagCustomPotionEffects().addCompound(potionEffect)
        return this
    }

    override fun clearPotionEffect(): ItemBuilder
            { tag.remove(TAG_POTION); tag.remove(TAG_CUSTOM_POTION_EFFECTS); return this; }

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
}
