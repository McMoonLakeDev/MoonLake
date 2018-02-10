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

package com.mcmoonlake.api.item

import com.mcmoonlake.api.attribute.AttributeItemModifier
import com.mcmoonlake.api.attribute.AttributeType
import com.mcmoonlake.api.attribute.Operation
import com.mcmoonlake.api.attribute.Slot
import com.mcmoonlake.api.chat.ChatComponent
import com.mcmoonlake.api.chat.ChatSerializer
import com.mcmoonlake.api.effect.EffectBase
import com.mcmoonlake.api.effect.EffectCustom
import com.mcmoonlake.api.effect.EffectType
import com.mcmoonlake.api.nbt.NBTCompound
import com.mcmoonlake.api.nbt.NBTFactory
import com.mcmoonlake.api.nbt.NBTList
import com.mcmoonlake.api.nbt.NBTType
import com.mcmoonlake.api.ofValuable
import com.mcmoonlake.api.ofValuableNotNull
import com.mcmoonlake.api.util.Enums
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.HashMap

abstract class ItemBuilderAbstract : ItemBuilder {

    /** member */

    private val itemStack: ItemStack
    protected val tag: NBTCompound

    /** constructor */

    constructor(itemStack: ItemStack) {
        this.itemStack = itemStack.clone() // clone
        this.tag = NBTFactory.readStackTagSafe(this.itemStack)
    }

    constructor(type: Material, amount: Int = 1, durability: Int = 0) {
        this.itemStack = ItemStack(type, amount, durability.toShort())
        this.tag = NBTFactory.readStackTagSafe(this.itemStack)
    }

    /** build */

    override fun build(): ItemStack
            = NBTFactory.writeStackTag(itemStack, checkEmptyTag(tag))

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
        private const val TAG_SKULL_PROPERTIES = "Properties"
        private const val TAG_SKULL_TEXTURES = "textures"
        private const val TAG_SKULL_TEXTURES_ID = "Id"
        private const val TAG_SKULL_TEXTURES_VALUE = "Value"
        private const val TAG_POTION = "Potion"
        private const val TAG_POTION_ID = "Id"
        private const val TAG_POTION_AMPLIFIER = "Amplifier"
        private const val TAG_POTION_DURATION = "Duration"
        private const val TAG_POTION_AMBIENT = "Ambient"
        private const val TAG_POTION_SHOW_PARTICLES = "ShowParticles"
        private const val TAG_BLOCK_ENTITY_TAG = "BlockEntityTag"
        private const val TAG_BANNER_PATTERNS = "Patterns"
        private const val TAG_BANNER_COLOR = "Color"
        private const val TAG_BANNER_PATTERN = "Pattern"
        private const val TAG_FIREWORKS = "Fireworks"
        private const val TAG_FIREWORKS_FLIGHT = "Flight"
        private const val TAG_FIREWORKS_EXPLOSIONS = "Explosions"
        private const val TAG_FIREWORKS_FLICKER = "Flicker"
        private const val TAG_FIREWORKS_TRAIL = "Trail"
        private const val TAG_FIREWORKS_TYPE = "Type"
        private const val TAG_FIREWORKS_COLORS = "Colors"
        private const val TAG_FIREWORKS_FADE_COLORS = "FadeColors"
        private const val TAG_KNOWLEDGE_BOOK_RECIPES = "Recipes"
    }

    /** protected */

    /**
     * Check the tag contains a key and the key is empty, cleared.
     */
    protected open fun checkEmptyTag(tag: NBTCompound): NBTCompound {
        if(tag.containsKey(TAG_DISPLAY) && tagDisplay().isEmpty())
            tag.remove(TAG_DISPLAY)
        if(tag.containsKey(TAG_DISPLAY_LORE) && tagDisplayLore().isEmpty())
            tagDisplay().remove(TAG_DISPLAY_LORE)
        if(tag.containsKey(TAG_ENCH) && tagEnchant().isEmpty())
            tag.remove(TAG_ENCH)
        if(tag.containsKey(TAG_ATTRIBUTE_MODIFIERS) && tagAttributeModifiers().isEmpty())
            tag.remove(TAG_ATTRIBUTE_MODIFIERS)
        if(tag.containsKey(TAG_STORED_ENCHANTMENTS) && tagStoredEnchantments().isEmpty())
            tag.remove(TAG_STORED_ENCHANTMENTS)
        if(tag.containsKey(TAG_BOOK_PAGES) && tagPages().isEmpty())
            tag.remove(TAG_BOOK_PAGES)
        if(tag.containsKey(TAG_ENTITY_TAG) && tagEntityTag().isEmpty())
            tag.remove(TAG_ENTITY_TAG)
        if(tag.containsKey(TAG_CUSTOM_POTION_EFFECTS) && tagCustomPotionEffects().isEmpty())
            tag.remove(TAG_CUSTOM_POTION_EFFECTS)
        if(tag.containsKey(TAG_HIDE_FLAGS) && tag.getIntOrDefault(TAG_HIDE_FLAGS) == 0)
            tag.remove(TAG_HIDE_FLAGS)
        if(tag.containsKey(TAG_CAN_DESTROY) && tag.getListOrDefault<String>(TAG_CAN_DESTROY).isEmpty())
            tag.remove(TAG_CAN_DESTROY)
        if(tag.containsKey(TAG_CAN_PLACE_ON) && tag.getListOrDefault<String>(TAG_CAN_PLACE_ON).isEmpty())
            tag.remove(TAG_CAN_PLACE_ON)
        if(tag.containsKey(TAG_BLOCK_ENTITY_TAG) && tagBlockEntityTag().containsKey(TAG_BANNER_PATTERNS) && tagBannerPatterns().isEmpty())
            tagBlockEntityTag().remove(TAG_BANNER_PATTERNS)
        if(tag.containsKey(TAG_BLOCK_ENTITY_TAG) && tagBlockEntityTag().isEmpty())
            tag.remove(TAG_BLOCK_ENTITY_TAG)
        if(tag.containsKey(TAG_BANNER_PATTERNS) && tagBannerPatterns().isEmpty())
            tag.remove(TAG_BANNER_PATTERNS)
        if(tag.containsKey(TAG_FIREWORKS) && tagFireworks().containsKey(TAG_FIREWORKS_EXPLOSIONS) && tagFireworksExplosions().isEmpty())
            tagFireworks().remove(TAG_FIREWORKS_EXPLOSIONS)
        if(tag.containsKey(TAG_FIREWORKS) && tagFireworks().isEmpty())
            tag.remove(TAG_FIREWORKS)
        if(tag.containsKey(TAG_KNOWLEDGE_BOOK_RECIPES) && tagKnowledgeBookRecipes().isEmpty())
            tag.remove(TAG_KNOWLEDGE_BOOK_RECIPES)
        return tag
    }

    protected open fun tagDisplay(): NBTCompound
            = tag.getCompoundOrDefault(TAG_DISPLAY)

    protected open fun tagDisplayLore(): NBTList<String>
            = tagDisplay().getListOrDefault(TAG_DISPLAY_LORE)

    protected open fun tagEnchant(): NBTList<NBTCompound>
            = tag.getListOrDefault(TAG_ENCH)

    protected open fun tagAttributeModifiers(): NBTList<NBTCompound>
            = tag.getListOrDefault(TAG_ATTRIBUTE_MODIFIERS)

    protected open fun tagStoredEnchantments(): NBTList<NBTCompound>
            = tag.getListOrDefault(TAG_STORED_ENCHANTMENTS)

    protected open fun tagPages(): NBTList<String>
            = tag.getListOrDefault(TAG_BOOK_PAGES)

    protected open fun tagEntityTag(): NBTCompound
            = tag.getCompoundOrDefault(TAG_ENTITY_TAG)

    protected open fun tagCustomPotionEffects(): NBTList<NBTCompound>
            = tag.getListOrDefault(TAG_CUSTOM_POTION_EFFECTS)

    protected open fun tagBlockEntityTag(): NBTCompound
            = tag.getCompoundOrDefault(TAG_BLOCK_ENTITY_TAG)

    protected open fun tagFireworks(): NBTCompound
            = tag.getCompoundOrDefault(TAG_FIREWORKS)

    protected open fun tagFireworksExplosions(): NBTList<NBTCompound>
            = tagFireworks().getListOrDefault(TAG_FIREWORKS_EXPLOSIONS)

    protected open fun tagBannerPatterns(): NBTList<NBTCompound>
            = tagBlockEntityTag().getListOrDefault(TAG_BANNER_PATTERNS)

    protected open fun tagKnowledgeBookRecipes(): NBTList<String>
            = tag.getListOrDefault(TAG_KNOWLEDGE_BOOK_RECIPES)

    private fun <T> NBTCompound.removeTagIf(key: String, predicate: (value: T) -> Boolean): ItemBuilder {
        val value = getValueOfNull(key)
        if(value != null) {
            val isPrimitive = value.type != NBTType.TAG_LIST && value.type != NBTType.TAG_COMPOUND
            if(isPrimitive && predicate(value.value as T))
                remove(key)
            else if(predicate(NBTFactory.ofWrapper(value.type, value.name, value.value) as T))
                remove(key)
        }
        return this@ItemBuilderAbstract
    }

    private fun <T> NBTList<T>.removeTagIf(predicate: (value: T) -> Boolean): ItemBuilder {
        val removeList = filter(predicate)
        if(removeList.isNotEmpty())
            removeAll(removeList)
        return this@ItemBuilderAbstract
    }

    /** general */

    override fun getDisplayName(block: (self: ItemBuilder, displayName: String?) -> Unit): ItemBuilder
            { block(this, tagDisplay().getStringOrNull(TAG_DISPLAY_NAME)); return this; }

    override fun setDisplayName(displayName: String): ItemBuilder
            { tagDisplay().putString(TAG_DISPLAY_NAME, displayName); return this; }

    override fun removeDisplayName(): ItemBuilder
            { tagDisplay().remove(TAG_DISPLAY_NAME); return this; }

    override fun removeDisplayNameIf(predicate: (displayName: String) -> Boolean): ItemBuilder
            = tagDisplay().removeTagIf(TAG_DISPLAY_NAME, predicate)

    override fun getLocalizedName(block: (self: ItemBuilder, localizedName: String?) -> Unit): ItemBuilder
            { block(this, tagDisplay().getStringOrNull(TAG_DISPLAY_LOC_NAME)); return this; }

    override fun setLocalizedName(localizedName: String): ItemBuilder
            { tagDisplay().putString(TAG_DISPLAY_LOC_NAME, localizedName); return this; }

    override fun removeLocalizedName(): ItemBuilder
            { tagDisplay().remove(TAG_DISPLAY_LOC_NAME); return this; }

    override fun removeLocalizedNameIf(predicate: (localizedName: String) -> Boolean): ItemBuilder
            = tagDisplay().removeTagIf(TAG_DISPLAY_LOC_NAME, predicate)

    override fun getLore(block: (self: ItemBuilder, lore: List<String>?) -> Unit): ItemBuilder
            { block(this, tagDisplay().getListOrNull<String>(TAG_DISPLAY_LORE)?.toList()); return this; }

    override fun setLore(vararg lore: String): ItemBuilder
            = setLore(lore.toList())

    override fun setLore(lore: Collection<String>): ItemBuilder
            { clearLore(); return addLore(lore); }

    override fun addLore(vararg lore: String): ItemBuilder
            = addLore(lore.toList())

    override fun addLore(lore: Collection<String>): ItemBuilder
            { lore.forEach { tagDisplayLore().addString(it) }; return this; }

    override fun removeLore(vararg lore: String): ItemBuilder
            { val tag = tagDisplayLore(); lore.forEach { tag.remove(it) }; return this; }

    override fun removeLoreIf(predicate: (lore: String) -> Boolean): ItemBuilder
            = tagDisplayLore().removeTagIf(predicate)

    override fun clearLore(): ItemBuilder
            { if(tag.containsKey(TAG_DISPLAY)) tagDisplay().remove(TAG_DISPLAY_LORE); return this; }

    private fun getEnchantFromKey(key: String): Map<Enchantment, Int>? {
        val enchantments = tag.getListOrNull<NBTCompound>(key)
        val enchs: MutableMap<Enchantment, Int>? = if(enchantments == null) null else HashMap()
        if(enchantments != null) for(enchantment in enchantments) {
            val type = enchantment.getShortOrNull(TAG_ENCH_ID)?.toInt().let { if(it == null || !Enchantment.hasId(it)) null else Enchantment.fromId(it) } ?: continue
            val level = enchantment.getShortOrNull(TAG_ENCH_LVL) ?: 0
            enchs?.put(type, level.toInt())
        }
        return enchs
    }

    override fun getEnchant(block: (self: ItemBuilder, ench: Map<Enchantment, Int>?) -> Unit): ItemBuilder
            { block(this, getEnchantFromKey(TAG_ENCH)); return this; }

    override fun addEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            { tagEnchant().addCompound(NBTFactory.ofCompound().putShort(TAG_ENCH_ID, enchantment.id).putShort(TAG_ENCH_LVL, level)); return this; }

    override fun removeEnchant(enchantment: Enchantment): ItemBuilder
            = tagEnchant().removeTagIf { Enchantment.fromId(it.getShort(TAG_ENCH_ID).toInt()) == enchantment }

    override fun removeEnchantIf(predicate: (ench: Enchantment, level: Int) -> Boolean): ItemBuilder
            = tagEnchant().removeTagIf {
        val enchantment = Enchantment.fromId(it.getShort(TAG_ENCH_ID).toInt())
        val level = it.getShort(TAG_ENCH_LVL).toInt()
        predicate(enchantment, level)
    }

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

    private fun getItemFlag(modifier: Int?): Array<out ItemFlag>?
            = if(modifier == null) null else ItemFlag.values().filter { it.ordinal and (1 shl it.ordinal) == 1 shl it.ordinal }.toTypedArray()

    override fun getFlag(block: (self: ItemBuilder, flag: Array<out ItemFlag>?) -> Unit): ItemBuilder
            { block(this, getItemFlag(tag.getIntOrNull(TAG_HIDE_FLAGS))); return this; }

    override fun addFlag(vararg flag: ItemFlag): ItemBuilder
            { tag.putInt(TAG_HIDE_FLAGS, flag.getAddBitModifier(tag.getIntOrDefault(TAG_HIDE_FLAGS))); return this; }

    override fun addFlag(flag: Collection<ItemFlag>): ItemBuilder
            = addFlag(*flag.toTypedArray())

    override fun removeFlag(vararg flag: ItemFlag): ItemBuilder
            { tag.putInt(TAG_HIDE_FLAGS, flag.getRemoveBitModifier(tag.getIntOrDefault(TAG_HIDE_FLAGS))); return this; }

    override fun removeFlag(flag: Collection<ItemFlag>): ItemBuilder
            = removeFlag(*flag.toTypedArray())

    override fun clearFlag(): ItemBuilder
            { tag.remove(TAG_HIDE_FLAGS); return this; }

    override fun isUnbreakable(block: (self: ItemBuilder, unbreakable: Boolean) -> Unit): ItemBuilder
            { block(this, tag.getBooleanOrFalse(TAG_UNBREAKABLE)); return this; }

    override fun setUnbreakable(unbreakable: Boolean): ItemBuilder
            { tag.putBoolean(TAG_UNBREAKABLE, unbreakable); return this; }

    override fun getAttribute(block: (self: ItemBuilder, attribute: Set<AttributeItemModifier>?) -> Unit): ItemBuilder {
        val attributeModifiers = tag.getListOrNull<NBTCompound>(TAG_ATTRIBUTE_MODIFIERS)
        val attributes: MutableSet<AttributeItemModifier>? = if(attributeModifiers == null) null else HashSet()
        if(attributeModifiers != null) for(attribute in attributeModifiers) {
            val type = Enums.ofValuable(AttributeType::class.java, attribute.getString(TAG_ATTRIBUTE_NAME)) ?: continue
            val operation = attribute.getIntOrNull(TAG_ATTRIBUTE_OPERATION).let { if(it == null) null else Enums.ofValuable(Operation::class.java, it) } ?: continue
            val slot = attribute.getStringOrNull(TAG_ATTRIBUTE_SLOT).let { if(it == null) null else Enums.ofValuable(Slot::class.java, it) }
            val uuidMost = attribute.getLongOrNull(TAG_ATTRIBUTE_UUID_MOST)
            val uuidLeast = attribute.getLongOrNull(TAG_ATTRIBUTE_UUID_LEAST)
            val amount = attribute.getDoubleOrNull(TAG_ATTRIBUTE_AMOUNT)
            val uuid = if(uuidMost == null || uuidLeast == null) UUID.randomUUID() else UUID(uuidMost, uuidLeast)
            attributes?.add(AttributeItemModifier(type, operation, slot, amount ?: .0, uuid))
        }
        block(this, attributes)
        return this
    }

    override fun addAttribute(type: AttributeType, operation: Operation, amount: Double, uuid: UUID): ItemBuilder
            = addAttribute(type, type.value(), operation, amount, uuid)

    override fun addAttribute(type: AttributeType, operation: Operation, slot: Slot?, amount: Double, uuid: UUID): ItemBuilder
            = addAttribute(type, type.value(), operation, slot, amount, uuid)

    override fun addAttribute(type: AttributeType, name: String, operation: Operation, amount: Double, uuid: UUID): ItemBuilder
            = addAttribute(type, name, operation, null, amount, uuid)

    override fun addAttribute(type: AttributeType, name: String, operation: Operation, slot: Slot?, amount: Double, uuid: UUID): ItemBuilder {
        val attribute = NBTFactory.ofCompound()
        if(slot != null) attribute.putString(TAG_ATTRIBUTE_SLOT, slot.value())
        attribute.putString(TAG_ATTRIBUTE_NAME, name) // nameable
        attribute.putString(TAG_ATTRIBUTE_TYPE, type.value())
        attribute.putInt(TAG_ATTRIBUTE_OPERATION, operation.value())
        attribute.putDouble(TAG_ATTRIBUTE_AMOUNT, amount)
        attribute.putLong(TAG_ATTRIBUTE_UUID_MOST, uuid.mostSignificantBits)
        attribute.putLong(TAG_ATTRIBUTE_UUID_LEAST, uuid.leastSignificantBits)
        tagAttributeModifiers().addCompound(attribute)
        return this
    }

    override fun removeAttribute(type: AttributeType)
            = tagAttributeModifiers().removeTagIf {
        val type0: AttributeType? = ofValuable(it.getString(TAG_ATTRIBUTE_TYPE))
        type == type0
    }

    override fun removeAttributeIf(predicate: (type: AttributeType, name: String, operation: Operation, slot: Slot?, amount: Double, uuid: UUID) -> Boolean): ItemBuilder
            = tagAttributeModifiers().removeTagIf {
        val type: AttributeType? = ofValuable(it.getString(TAG_ATTRIBUTE_TYPE))
        val name = it.getString(TAG_ATTRIBUTE_NAME)
        val operation: Operation = ofValuableNotNull(it.getInt(TAG_ATTRIBUTE_OPERATION))
        val slot: Slot? = ofValuable(it.getStringOrNull(TAG_ATTRIBUTE_SLOT))
        val amount = it.getDouble(TAG_ATTRIBUTE_AMOUNT)
        val uuidMost = it.getLong(TAG_ATTRIBUTE_UUID_MOST)
        val uuidLeast = it.getLong(TAG_ATTRIBUTE_UUID_LEAST)
        type != null && predicate(type, name, operation, slot, amount, UUID(uuidMost, uuidLeast))
    }

    override fun clearAttribute(): ItemBuilder
            { tag.remove(TAG_ATTRIBUTE_MODIFIERS); return this; }

    // TODO v1.13
    private fun namespaceToMaterial(key: String): Material?
            = Material.matchMaterial(key.replaceFirst("minecraft:", "", true))

    // TODO v1.13
    private fun materialToNamespace(type: Material): String
            = "minecraft:${type.name.toLowerCase()}"

    private fun getMaterialFromStringList(key: String): List<Material>?
            = tag.getListOrNull<String>(key)
            ?.mapNotNull { namespaceToMaterial(it) }

    override fun getCanDestroy(block: (self: ItemBuilder, canDestroy: List<Material>?) -> Unit): ItemBuilder
            { block(this, getMaterialFromStringList(TAG_CAN_DESTROY)); return this; }

    override fun setCanDestroy(vararg types: Material): ItemBuilder
            { clearCanDestroy(); return addCanDestroy(*types); }

    override fun addCanDestroy(vararg types: Material): ItemBuilder {
        val canDestroy = tag.getListOrDefault<String>(TAG_CAN_DESTROY)
        types.forEach { canDestroy.addString(materialToNamespace(it)) }
        return this
    }

    override fun removeCanDestroy(vararg types: Material): ItemBuilder
            { tag.getListOrNull<String>(TAG_CAN_DESTROY)?.removeAll(types.map { materialToNamespace(it)}); return this; }

    override fun removeCanDestroyIf(predicate: (type: Material) -> Boolean): ItemBuilder
            { tag.getListOrNull<String>(TAG_CAN_DESTROY)?.removeTagIf { val type = namespaceToMaterial(it); type != null && predicate(type) }; return this; }

    override fun clearCanDestroy(): ItemBuilder
            { tag.remove(TAG_CAN_DESTROY); return this; }

    override fun getCanPlaceOn(block: (self: ItemBuilder, canPlaceOn: List<Material>?) -> Unit): ItemBuilder
            { block(this, getMaterialFromStringList(TAG_CAN_PLACE_ON)); return this; }

    override fun setCanPlaceOn(vararg types: Material): ItemBuilder
            { clearCanPlaceOn(); return addCanPlaceOn(*types); }

    override fun addCanPlaceOn(vararg types: Material): ItemBuilder {
        val canPlaceOn = tag.getListOrDefault<String>(TAG_CAN_PLACE_ON)
        types.forEach { canPlaceOn.addString(materialToNamespace(it)) }
        return this
    }

    override fun removeCanPlaceOn(vararg types: Material): ItemBuilder
            { tag.getListOrNull<String>(TAG_CAN_PLACE_ON)?.removeAll(types.map { materialToNamespace(it)}); return this; }

    override fun removeCanPlaceOnIf(predicate: (type: Material) -> Boolean): ItemBuilder
            { tag.getListOrNull<String>(TAG_CAN_PLACE_ON)?.removeTagIf { val type = namespaceToMaterial(it); type != null && predicate(type) }; return this; }

    override fun clearCanPlaceOn(): ItemBuilder
            { tag.remove(TAG_CAN_PLACE_ON); return this; }

    override fun getRepairCost(block: (self: ItemBuilder, repairCost: Int?) -> Unit): ItemBuilder
            { block(this, tag.getIntOrNull(TAG_REPAIR_COST)); return this; }

    override fun setRepairCost(value: Int): ItemBuilder
            { tag.putInt(TAG_REPAIR_COST, value); return this; }

    /** leather armor */

    override fun getLeatherColor(block: (self: ItemBuilder, color: Color?) -> Unit): ItemBuilder
            { block(this, tag.getIntOrNull(TAG_LEATHER_ARMOR_COLOR).let { if(it == null) null else Color.fromRGB(it) }); return this; }

    override fun setLeatherColor(color: Color): ItemBuilder
            { tagDisplay().putInt(TAG_LEATHER_ARMOR_COLOR, color.asRGB()); return this; }

    override fun setLeatherColor(red: Int, green: Int, blue: Int): ItemBuilder
            = setLeatherColor(Color.fromRGB(red, green, blue))

    override fun removeLeatherColor(): ItemBuilder
            { tagDisplay().remove(TAG_LEATHER_ARMOR_COLOR); return this; }

    override fun removeLeatherColorIf(predicate: (color: Color) -> Boolean): ItemBuilder
            = tagDisplay().removeTagIf<Int>(TAG_LEATHER_ARMOR_COLOR) { predicate(Color.fromRGB(it)) }

    /** book */

    override fun getBookTitle(block: (self: ItemBuilder, title: String?) -> Unit): ItemBuilder
            { block(this, tag.getStringOrNull(TAG_BOOK_TITLE)); return this; }

    override fun setBookTitle(title: String): ItemBuilder
            { tag.putString(TAG_BOOK_TITLE, title); return this; }

    override fun getBookAuthor(block: (self: ItemBuilder, author: String?) -> Unit): ItemBuilder
            { block(this, tag.getStringOrNull(TAG_BOOK_AUTHOR)); return this; }

    override fun setBookAuthor(author: String): ItemBuilder
            { tag.putString(TAG_BOOK_AUTHOR, author); return this; }

    override fun getBookGeneration(block: (self: ItemBuilder, generation: Generation?) -> Unit): ItemBuilder
            { block(this, tag.getIntOrNull(TAG_BOOK_GENERATION).let { if(it == null) null else Enums.ofValuable(Generation::class.java, it) }); return this; }

    override fun setBookGeneration(generation: Generation): ItemBuilder
            { tag.putInt(TAG_BOOK_GENERATION, generation.ordinal); return this; }

    override fun getBookPages(block: (self: ItemBuilder, pages: Collection<String>?) -> Unit): ItemBuilder
            { block(this, tag.getListOrNull<String>(TAG_BOOK_PAGES)?.toList()); return this; }

    override fun setBookPages(vararg pages: String): ItemBuilder
            { clearBookPages(); return addBookPages(*pages); }

    override fun setBookPages(pages: Collection<String>): ItemBuilder
            = setBookPages(*pages.toTypedArray())

    override fun addBookPages(vararg pages: String): ItemBuilder
            { pages.forEach { tagPages().addString(it) }; return this; }

    override fun addBookPages(pages: Collection<String>): ItemBuilder
            = addBookPages(*pages.toTypedArray())

    override fun removeBookPages(vararg pages: String): ItemBuilder
            { tag.getListOrNull<String>(TAG_BOOK_PAGES)?.removeAll(pages.toList()); return this; }

    override fun setBookPages(vararg pages: ChatComponent): ItemBuilder
            = setBookPages(pages.map { it.toJson() })

    override fun addBookPages(vararg pages: ChatComponent): ItemBuilder
            = addBookPages(pages.map { it.toJson() })

    override fun removeBookPages(vararg pages: ChatComponent): ItemBuilder
            = removeBookPages(*pages.map { it.toJson() }.toTypedArray())

    override fun removeBookPagesIf(predicate: (page: ChatComponent) -> Boolean): ItemBuilder
            { tag.getListOrNull<String>(TAG_BOOK_PAGES)?.removeTagIf { predicate(ChatSerializer.fromJsonLenient(it)) }; return this; }

    override fun clearBookPages(): ItemBuilder
            { tag.remove(TAG_BOOK_PAGES); return this; }

    /** enchantment storage */

    override fun getStoredEnchant(block: (self: ItemBuilder, ench: Map<Enchantment, Int>?) -> Unit): ItemBuilder
            { block(this, getEnchantFromKey(TAG_STORED_ENCHANTMENTS)); return this; }

    override fun addStoredEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            { tagStoredEnchantments().addCompound(NBTFactory.ofCompound().putShort(TAG_ENCH_ID, enchantment.id).putShort(TAG_ENCH_LVL, level)); return this; }

    override fun removeStoredEnchant(enchantment: Enchantment): ItemBuilder
            = tagStoredEnchantments().removeTagIf { Enchantment.fromId(it.getShort(TAG_ENCH_ID).toInt()) == enchantment }

    override fun removeStoredEnchantIf(predicate: (ench: Enchantment, level: Int) -> Boolean): ItemBuilder
            = tagStoredEnchantments().removeTagIf {
        val enchantment = Enchantment.fromId(it.getShort(TAG_ENCH_ID).toInt())
        val level = it.getShort(TAG_ENCH_LVL).toInt()
        predicate(enchantment, level)
    }

    override fun clearStoredEnchant(): ItemBuilder
            { tag.remove(TAG_STORED_ENCHANTMENTS); return this; }

    /** skull */

    override fun getSkullOwner(block: (self: ItemBuilder, owner: String?) -> Unit): ItemBuilder
            { block(this, tag.getStringOrNull(TAG_SKULL_OWNER)); return this; }

    override fun setSkullOwner(owner: String): ItemBuilder
            { tag.putString(TAG_SKULL_OWNER, owner); return this; }

    override fun removeSkullOwner(): ItemBuilder
            { tag.remove(TAG_SKULL_OWNER); return this; }

    override fun removeSkullOwnerIf(predicate: (owner: String) -> Boolean): ItemBuilder
            = tag.removeTagIf(TAG_SKULL_OWNER, predicate)

    override fun getSkullTexture(block: (self: ItemBuilder, value: String?) -> Unit): ItemBuilder {
        val skullOwner = tag.getValueOfNull(TAG_SKULL_OWNER)
        var value: String? = null
        if(skullOwner?.type == NBTType.TAG_COMPOUND) {
            val properties = (skullOwner as NBTCompound).getCompoundOrNull(TAG_SKULL_PROPERTIES)
            val textures = properties?.getListOrNull<NBTCompound>(TAG_SKULL_TEXTURES)
            if(textures != null) for(texture in textures) {
                value = texture.getStringOrNull(TAG_SKULL_TEXTURES_VALUE)
                if(value != null)
                    break
            }
        }
        block(this, value)
        return this
    }

    override fun setSkullTexture(value: String): ItemBuilder {
        tag.getCompoundOrDefault(TAG_SKULL_OWNER)
                    .putString(TAG_SKULL_TEXTURES_ID, UUID.randomUUID().toString())
                    .getCompoundOrDefault(TAG_SKULL_PROPERTIES)
                    .getListOrDefault<NBTCompound>(TAG_SKULL_TEXTURES).clearSelf()
                    .addCompound(NBTFactory.ofCompound().putString(TAG_SKULL_TEXTURES_VALUE, value))
        return this
    }

    override fun removeSkullTexture(): ItemBuilder
            { tag.remove(TAG_SKULL_OWNER); return this; }

    override fun removeSkullTextureIf(predicate: (value: String) -> Boolean): ItemBuilder {
        tag.getCompoundOrNull(TAG_SKULL_OWNER)
                ?.getCompoundOrNull(TAG_SKULL_PROPERTIES)
                ?.getListOrNull<NBTCompound>(TAG_SKULL_TEXTURES)
                ?.removeTagIf { predicate(it.getString(TAG_SKULL_TEXTURES_VALUE)) }
        return this
    }

    /** spawn egg */

    override fun getSpawnEggType(block: (self: ItemBuilder, type: EntityType?) -> Unit): ItemBuilder // TODO EntityType.fromName  // TODO v1.13
            { block(this, tag.getCompoundOrNull(TAG_ENTITY_TAG)?.getStringOrNull(TAG_ENTITY_TAG_ID).let { if(it == null) null else EntityType.fromName(it) }); return this; }

    override fun setSpawnEggType(type: EntityType): ItemBuilder
            { tagEntityTag().putString(TAG_ENTITY_TAG_ID, type.name); return this; }

    override fun setSpawnEggType(entity: Entity): ItemBuilder
            { tag.putCompound(NBTFactory.readEntityTag(entity).putString(TAG_ENTITY_TAG_ID, entity.type.name)); return this; }

    override fun removeSpawnEggType(): ItemBuilder
            { tag.remove(TAG_ENTITY_TAG); return this; }

    override fun removeSpawnEggTypeIf(predicate: (type: EntityType) -> Boolean): ItemBuilder
            = tag.removeTagIf<NBTCompound>(TAG_ENTITY_TAG) {
        val typeName = it.getStringOrNull(TAG_ENTITY_TAG_ID)
        val type = if(typeName != null) EntityType.fromName(typeName) else null
        type != null && predicate(type)
    }

    /** map */

    override fun getMapScaling(block: (self: ItemBuilder, scaling: Boolean) -> Unit): ItemBuilder
            { block(this, tag.getBooleanOrFalse(TAG_MAP_SCALING)); return this; }

    override fun setMapScaling(scaling: Boolean): ItemBuilder
            { tag.putBoolean(TAG_MAP_SCALING, scaling); return this; }

    override fun getMapLocationName(block: (self: ItemBuilder, locationName: String?) -> Unit): ItemBuilder
            { block(this, tag.getCompoundOrNull(TAG_DISPLAY)?.getStringOrNull(TAG_DISPLAY_LOC_NAME)); return this; }

    override fun setMapLocationName(locationName: String): ItemBuilder
            { tagDisplay().putString(TAG_DISPLAY_LOC_NAME, locationName); return this; }

    override fun removeMapLocationName(): ItemBuilder
            { tagDisplay().remove(TAG_DISPLAY_LOC_NAME); return this; }

    override fun removeMapLocationNameIf(predicate: (locationName: String) -> Boolean): ItemBuilder
            = tagDisplay().removeTagIf(TAG_DISPLAY_LOC_NAME, predicate)

    override fun getMapColor(block: (self: ItemBuilder, color: Color?) -> Unit): ItemBuilder
            { block(this, tag.getCompoundOrNull(TAG_DISPLAY)?.getIntOrNull(TAG_MAP_COLOR).let { if(it == null) null else Color.fromRGB(it) }); return this; }

    override fun setMapColor(color: Color): ItemBuilder
            { tagDisplay().putInt(TAG_MAP_COLOR, color.asRGB()); return this; }

    override fun removeMapColor(): ItemBuilder
            { tagDisplay().remove(TAG_MAP_COLOR); return this; }

    override fun removeMapColorIf(predicate: (color: Color) -> Boolean): ItemBuilder
            = tagDisplay().removeTagIf<Int>(TAG_MAP_COLOR) { predicate(Color.fromRGB(it)) }

    /** potion */

    override fun getPotionColor(block: (self: ItemBuilder, color: Color?) -> Unit): ItemBuilder
            { block(this, tag.getIntOrNull(TAG_CUSTOM_POTION_COLOR).let { if(it == null) null else Color.fromRGB(it) }); return this; }

    override fun setPotionColor(color: Color): ItemBuilder
            { tag.putInt(TAG_CUSTOM_POTION_COLOR, color.asRGB()); return this; }

    override fun removePotionColor(): ItemBuilder
            { tag.remove(TAG_CUSTOM_POTION_COLOR); return this; }

    override fun removePotionColorIf(predicate: (color: Color) -> Boolean): ItemBuilder
            = tag.removeTagIf<Int>(TAG_CUSTOM_POTION_COLOR) { predicate(Color.fromRGB(it)) }

    override fun getPotionBase(block: (self: ItemBuilder, base: EffectBase?) -> Unit): ItemBuilder
            { block(this, tag.getStringOrNull(TAG_POTION).let { if(it == null) null else EffectBase.fromName(it) }); return this; }

    override fun setPotionBase(base: EffectBase): ItemBuilder
            { tag.putString(TAG_POTION, base.value); return this; }

    override fun removePotionBase(): ItemBuilder
            { tag.remove(TAG_POTION); return this; }

    override fun removePotionBaseIf(predicate: (base: EffectBase) -> Boolean): ItemBuilder
            = tag.removeTagIf<String>(TAG_POTION) { val base = EffectBase.fromName(it); base != null && predicate(base) }

    override fun getPotionEffect(block: (self: ItemBuilder, effect: Collection<EffectCustom>?) -> Unit): ItemBuilder {
        val potionEffects = tag.getListOrNull<NBTCompound>(TAG_CUSTOM_POTION_EFFECTS)
        val effects: MutableList<EffectCustom>? = if(potionEffects == null) null else ArrayList()
        if(potionEffects != null) for(potionEffect in potionEffects) {
            val type = potionEffect.getByteOrNull(TAG_POTION_ID)?.toInt().let { if(it == null) null else Enums.ofValuable(EffectType::class.java, it) } ?: continue
            val amplifier = potionEffect.getByteOrNull(TAG_POTION_AMPLIFIER) ?: 0
            val duration = potionEffect.getIntOrNull(TAG_POTION_DURATION) ?: 0
            val ambient = potionEffect.getBooleanOrFalse(TAG_POTION_AMBIENT)
            val particles = potionEffect.getBooleanOrFalse(TAG_POTION_SHOW_PARTICLES)
            var color: Color? = null; getPotionColor { _, v -> color = v }
            effects?.add(EffectCustom(type, duration, amplifier.toInt(), ambient, particles, color))
        }
        block(this, effects)
        return this
    }

    override fun setPotionEffect(effect: Collection<EffectCustom>): ItemBuilder
            { clearPotionEffect(); effect.forEach { addPotionEffect(it) }; return this; }

    override fun addPotionEffect(effect: EffectCustom): ItemBuilder
            = addPotionEffect(effect.type, effect.duration, effect.amplifier, effect.ambient, effect.particle, effect.color)

    override fun addPotionEffect(vararg effect: EffectCustom): ItemBuilder
            { effect.forEach { addPotionEffect(it) }; return this; }

    override fun addPotionEffect(effect: Collection<EffectCustom>): ItemBuilder
            { effect.forEach { addPotionEffect(it) }; return this; }

    override fun addPotionEffect(type: EffectType, duration: Int, amplifier: Int, ambient: Boolean, particle: Boolean, color: Color?): ItemBuilder {
        val potionEffect = NBTFactory.ofCompound()
        potionEffect.putByte(TAG_POTION_ID, type.id)
        potionEffect.putByte(TAG_POTION_AMPLIFIER, amplifier)
        potionEffect.putInt(TAG_POTION_DURATION, duration)
        potionEffect.putBoolean(TAG_POTION_AMBIENT, ambient)
        potionEffect.putBoolean(TAG_POTION_SHOW_PARTICLES, particle)
        tagCustomPotionEffects().addCompound(potionEffect)
        if(color != null) return setPotionColor(color)
        return this
    }

    override fun removePotionEffect(type: EffectType): ItemBuilder
            = tagCustomPotionEffects().removeTagIf { it.getByte(TAG_POTION_ID).toInt() == type.id }

    override fun removePotionEffectIf(predicate: (type: EffectType, duration: Int, amplifier: Int, ambient: Boolean, particle: Boolean, color: Color?) -> Boolean): ItemBuilder
            = tagCustomPotionEffects().removeTagIf {
        val type: EffectType? = ofValuable(it.getByte(TAG_POTION_ID).toInt())
        val duration = it.getInt(TAG_POTION_DURATION)
        val amplifier = it.getByte(TAG_POTION_AMPLIFIER).toInt()
        val ambient = it.getBoolean(TAG_POTION_AMBIENT)
        val particle = it.getBoolean(TAG_POTION_SHOW_PARTICLES)
        var color: Color? = null; getPotionColor { _, value -> color = value }
        type != null && predicate(type, duration, amplifier, ambient, particle, color)
    }

    override fun clearPotionEffect(): ItemBuilder
            { tag.remove(TAG_POTION); tag.remove(TAG_CUSTOM_POTION_EFFECTS); return this; }

    /** firework */

    override fun getFireworkEffect(block: (self: ItemBuilder, effect: Collection<FireworkEffect>?) -> Unit): ItemBuilder {
        val fireworks = tag.getCompoundOrNull(TAG_FIREWORKS)
        val fireworkExplosions = fireworks?.getListOrNull<NBTCompound>(TAG_FIREWORKS_EXPLOSIONS)
        val effects: MutableList<FireworkEffect>? = if(fireworkExplosions == null) null else ArrayList()
        if(fireworkExplosions != null) for(fireworkExplosion in fireworkExplosions) {
            val type = fireworkExplosion.getByteOrNull(TAG_FIREWORKS_TYPE).let { if(it == null) null else fromNBT(it.toInt()) } ?: continue
            val builder = FireworkEffect.builder().with(type)
            fireworkExplosion.getBooleanOrNull(TAG_FIREWORKS_FLICKER).also { if(it != null && it) builder.withFlicker() }
            fireworkExplosion.getBooleanOrNull(TAG_FIREWORKS_TRAIL).also { if(it != null && it) builder.withTrail() }
            fireworkExplosion.getIntArrayOrNull(TAG_FIREWORKS_COLORS).also { it?.forEach { builder.withColor(Color.fromRGB(it)) } }
            fireworkExplosion.getIntArrayOrNull(TAG_FIREWORKS_FADE_COLORS).also { it?.forEach { builder.withFade(Color.fromRGB(it)) } }
            effects?.add(builder.build())
        }
        block(this, effects)
        return this
    }

    override fun setFireworkEffect(effect: Collection<FireworkEffect>): ItemBuilder
            { clearFireworkEffect(); return addFireworkEffect(effect); }

    override fun addFireworkEffect(vararg effect: FireworkEffect): ItemBuilder {
        effect.forEach {
            val fireworkEffect = NBTFactory.ofCompound()
            fireworkEffect.putBoolean(TAG_FIREWORKS_FLICKER, it.hasFlicker())
            fireworkEffect.putBoolean(TAG_FIREWORKS_TRAIL, it.hasTrail())
            fireworkEffect.putByte(TAG_FIREWORKS_TYPE, it.type.nbt())
            fireworkEffect.putIntArray(TAG_FIREWORKS_COLORS, it.colors.map { it.asRGB() }.toIntArray())
            fireworkEffect.putIntArray(TAG_FIREWORKS_FADE_COLORS, it.fadeColors.map { it.asRGB() }.toIntArray())
            tagFireworksExplosions().addCompound(fireworkEffect)
        }
        return this
    }

    private fun fromNBT(type: Int): FireworkEffect.Type? = when(type) {
        0 -> FireworkEffect.Type.BALL
        1 -> FireworkEffect.Type.BALL_LARGE
        2 -> FireworkEffect.Type.STAR
        3 -> FireworkEffect.Type.CREEPER
        4 -> FireworkEffect.Type.BURST
        else -> null
    }

    private fun FireworkEffect.Type.nbt(): Int = when(this) {
        FireworkEffect.Type.BALL -> 0
        FireworkEffect.Type.BALL_LARGE -> 1
        FireworkEffect.Type.STAR -> 2
        FireworkEffect.Type.CREEPER -> 3
        FireworkEffect.Type.BURST -> 4
    }

    override fun addFireworkEffect(effect: Collection<FireworkEffect>): ItemBuilder
            = addFireworkEffect(*effect.toTypedArray())

    override fun removeFireworkEffectIf(predicate: (type: FireworkEffect.Type, flicker: Boolean, trail: Boolean, colors: Array<Color>, fadeColors: Array<Color>) -> Boolean): ItemBuilder
            = tagFireworksExplosions().removeTagIf {
        val type = fromNBT(it.getByte(TAG_FIREWORKS_TYPE).toInt())
        val flicker = it.getBoolean(TAG_FIREWORKS_FLICKER)
        val trail = it.getBoolean(TAG_FIREWORKS_TRAIL)
        val colors = it.getIntArray(TAG_FIREWORKS_COLORS).map { Color.fromRGB(it) }.toTypedArray()
        val fadeColors = it.getIntArray(TAG_FIREWORKS_FADE_COLORS).map { Color.fromRGB(it) }.toTypedArray()
        type != null && predicate(type, flicker, trail, colors, fadeColors)
    }

    override fun clearFireworkEffect(): ItemBuilder
            { tag.remove(TAG_FIREWORKS); return this; }

    override fun getFireworkPower(block: (self: ItemBuilder, power: Int?) -> Unit): ItemBuilder
            { block(this, tag.getCompoundOrNull(TAG_FIREWORKS)?.getByteOrNull(TAG_FIREWORKS_FLIGHT)?.toInt()); return this; }

    override fun setFireworkPower(power: Int): ItemBuilder
            { tagFireworks().putByte(TAG_FIREWORKS_FLIGHT, power); return this; }

    /** banner */

    override fun getBannerPattern(block: (self: ItemBuilder, pattern: Collection<Pattern>?) -> Unit): ItemBuilder {
        val blockEntityTag = tag.getCompoundOrNull(TAG_BLOCK_ENTITY_TAG)
        val bannerPatterns = blockEntityTag?.getListOrNull<NBTCompound>(TAG_BANNER_PATTERNS)
        val patterns: MutableList<Pattern>? = if(bannerPatterns == null) null else ArrayList()
        if(bannerPatterns != null) for(bannerPattern in bannerPatterns) {
            val color = bannerPattern.getIntOrNull(TAG_BANNER_COLOR).let { if(it == null) null else Enums.ofValuable(Pattern.Color::class.java, it) } ?: continue
            val type = bannerPattern.getStringOrNull(TAG_BANNER_PATTERN).let { if(it == null) null else Enums.ofValuable(Pattern.Type::class.java, it) } ?: continue
            patterns?.add(Pattern(color, type))
        }
        block(this, patterns)
        return this
    }

    override fun setBannerPattern(pattern: Collection<Pattern>): ItemBuilder
            { pattern.forEach { addBannerPattern(it) }; return this; }

    override fun addBannerPattern(pattern: Pattern): ItemBuilder {
        val bannerPattern = NBTFactory.ofCompound()
        bannerPattern.putInt(TAG_BANNER_COLOR, pattern.color.data)
        bannerPattern.putString(TAG_BANNER_PATTERN, pattern.type.identifier)
        tagBannerPatterns().addCompound(bannerPattern)
        return this
    }

    override fun removeBannerPattern(type: Pattern.Type): ItemBuilder
            = tagBannerPatterns().removeTagIf {
        val type0: Pattern.Type? = ofValuable(it.getString(TAG_BANNER_PATTERN))
        type0 == type
    }

    override fun removeBannerPatternIf(predicate: (type: Pattern.Type, color: Pattern.Color) -> Boolean): ItemBuilder
            = tagBannerPatterns().removeTagIf {
        val type: Pattern.Type? = ofValuable(it.getString(TAG_BANNER_PATTERN))
        val color: Pattern.Color? = ofValuable(it.getInt(TAG_BANNER_COLOR))
        type != null && color != null && predicate(type, color)
    }

    override fun clearBannerPattern(): ItemBuilder
            { tagBlockEntityTag().remove(TAG_BANNER_PATTERNS); return this; }

    /** knowledge book */

    override fun getKnowledgeBookRecipes(block: (self: ItemBuilder, recipes: List<Material>?) -> Unit): ItemBuilder
            { block(this, getMaterialFromStringList(TAG_KNOWLEDGE_BOOK_RECIPES)); return this; }

    override fun setKnowledgeBookRecipes(recipes: List<Material>): ItemBuilder
            { clearKnowledgeBookRecipes(); return addKnowledgeBookRecipes(*recipes.toTypedArray()); }

    override fun addKnowledgeBookRecipes(vararg recipes: Material): ItemBuilder {
        val knowledgeBookRecipes = tagKnowledgeBookRecipes()
        recipes.forEach { knowledgeBookRecipes.addString(materialToNamespace(it)) }
        return this
    }

    override fun removeKnowledgeBookRecipes(vararg recipes: Material): ItemBuilder
            { tagKnowledgeBookRecipes().removeAll(recipes.map { materialToNamespace(it) }); return this; }

    override fun removeKnowledgeBookRecipesIf(predicate: (recipe: Material) -> Boolean): ItemBuilder
            = tagKnowledgeBookRecipes().removeTagIf { val type = namespaceToMaterial(it); type != null && predicate(type) }

    override fun clearKnowledgeBookRecipes(): ItemBuilder
            { tag.remove(TAG_KNOWLEDGE_BOOK_RECIPES); return this; }
}
