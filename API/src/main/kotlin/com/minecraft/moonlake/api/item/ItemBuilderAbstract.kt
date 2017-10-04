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
import com.minecraft.moonlake.api.nbt.NBTCompound
import com.minecraft.moonlake.api.nbt.NBTFactory
import com.minecraft.moonlake.api.nbt.NBTList
import com.minecraft.moonlake.api.nbt.NBTType
import com.minecraft.moonlake.api.util.Enums
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
    private val tag: NBTCompound

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
    }

    /** protected */

    /**
     * Check the tag contains a key and the key is empty, cleared.
     */
    open protected fun checkEmptyTag(tag: NBTCompound): NBTCompound {
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
        return tag
    }

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

    open protected fun tagBlockEntityTag(): NBTCompound
            = tag.getCompoundOrDefault(TAG_BLOCK_ENTITY_TAG)

    open protected fun tagFireworks(): NBTCompound
            = tag.getCompoundOrDefault(TAG_FIREWORKS)

    open protected fun tagFireworksExplosions(): NBTList<NBTCompound>
            = tagFireworks().getListOrDefault(TAG_FIREWORKS_EXPLOSIONS)

    open protected fun tagBannerPatterns(): NBTList<NBTCompound>
            = tagBlockEntityTag().getListOrDefault(TAG_BANNER_PATTERNS)

    /** general */

    override fun getDisplayName(block: (self: ItemBuilder, displayName: String?) -> Unit): ItemBuilder
            { block(this, tagDisplay().getStringOrNull(TAG_DISPLAY_NAME)); return this; }

    override fun setDisplayName(displayName: String): ItemBuilder
            { tagDisplay().putString(TAG_DISPLAY_NAME, displayName); return this; }

    override fun getLocalizedName(block: (self: ItemBuilder, localizedName: String?) -> Unit): ItemBuilder
            { block(this, tagDisplay().getStringOrNull(TAG_DISPLAY_LOC_NAME)); return this; }

    override fun setLocalizedName(localizedName: String): ItemBuilder
            { tagDisplay().putString(TAG_DISPLAY_LOC_NAME, localizedName); return this; }

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

    override fun getAttribute(block: (self: ItemBuilder, attribute: Set<AttributeModifier>?) -> Unit): ItemBuilder {
        val attributeModifiers = tag.getListOrNull<NBTCompound>(TAG_ATTRIBUTE_MODIFIERS)
        val attributes: MutableSet<AttributeModifier>? = if(attributeModifiers == null) null else HashSet()
        if(attributeModifiers != null) for(attribute in attributeModifiers) {
            val type = Enums.ofValuable(AttributeType::class.java, attribute.getString(TAG_ATTRIBUTE_NAME)) ?: continue
            val operation = attribute.getIntOrNull(TAG_ATTRIBUTE_OPERATION).let { if(it == null) null else Enums.ofValuable(Operation::class.java, it) } ?: continue
            val slot = attribute.getStringOrNull(TAG_ATTRIBUTE_SLOT).let { if(it == null) null else Enums.ofValuable(Slot::class.java, it) }
            val uuidMost = attribute.getLongOrNull(TAG_ATTRIBUTE_UUID_MOST)
            val uuidLeast = attribute.getLongOrNull(TAG_ATTRIBUTE_UUID_LEAST)
            val amount = attribute.getDoubleOrNull(TAG_ATTRIBUTE_AMOUNT)
            val uuid = if(uuidMost == null || uuidLeast == null) UUID.randomUUID() else UUID(uuidMost, uuidLeast)
            attributes?.add(AttributeModifier(type, operation, slot, amount ?: .0, uuid))
        }
        block(this, attributes)
        return this
    }

    override fun setAttribute(type: AttributeType, operation: Operation, amount: Double): ItemBuilder
            = setAttribute(type, operation, null, amount)

    override fun setAttribute(type: AttributeType, operation: Operation, slot: Slot?, amount: Double): ItemBuilder {
        val attribute = NBTFactory.ofCompound()
        val attributeUUID = UUID.randomUUID()
        if(slot != null) attribute.putString(TAG_ATTRIBUTE_SLOT, slot.value())
        attribute.putString(TAG_ATTRIBUTE_NAME, type.value())
        attribute.putString(TAG_ATTRIBUTE_TYPE, type.value())
        attribute.putInt(TAG_ATTRIBUTE_OPERATION, operation.value())
        attribute.putDouble(TAG_ATTRIBUTE_AMOUNT, amount)
        attribute.putLong(TAG_ATTRIBUTE_UUID_MOST, attributeUUID.mostSignificantBits)
        attribute.putLong(TAG_ATTRIBUTE_UUID_LEAST, attributeUUID.leastSignificantBits)
        tagAttributeModifiers().addCompound(attribute)
        return this
    }

    override fun clearAttribute(): ItemBuilder
            { tag.remove(TAG_ATTRIBUTE_MODIFIERS); return this; }

    private fun getMaterialFromStringList(key: String): Set<Material>?
            = tag.getListOrNull<String>(key)?.mapNotNull { Material.matchMaterial(it.replaceFirst("minecraft:", "", true)) }?.toSet()

    private fun setMaterialFromArray(key: String, vararg type: Material): ItemBuilder {
        val list = NBTFactory.ofList<String>(key)
        type.forEach { list.addString("minecraft:${it.name.toLowerCase()}") }
        tag.putList(list)
        return this
    }

    override fun getCanDestroy(block: (self: ItemBuilder, canDestroy: Set<Material>?) -> Unit): ItemBuilder
            { block(this, getMaterialFromStringList(TAG_CAN_DESTROY)); return this; }

    override fun setCanDestroy(vararg type: Material): ItemBuilder
            = setMaterialFromArray(TAG_CAN_DESTROY, *type)

    override fun getCanPlaceOn(block: (self: ItemBuilder, canPlaceOn: Set<Material>?) -> Unit): ItemBuilder
            { block(this, getMaterialFromStringList(TAG_CAN_PLACE_ON)); return this; }

    override fun setCanPlaceOn(vararg type: Material): ItemBuilder
            = setMaterialFromArray(TAG_CAN_PLACE_ON, *type)

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
            = setLeatherColor(Color.fromBGR(red, green, blue))

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

    override fun getStoredEnchant(block: (self: ItemBuilder, ench: Map<Enchantment, Int>?) -> Unit): ItemBuilder
            { block(this, getEnchantFromKey(TAG_STORED_ENCHANTMENTS)); return this; }

    override fun addStoredEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            { tagStoredEnchantments().addCompound(NBTFactory.ofCompound().putShort(TAG_ENCH_ID, enchantment.id).putShort(TAG_ENCH_LVL, level)); return this; }

    override fun addStoredSafeEnchant(enchantment: Enchantment, level: Int): ItemBuilder
            { enchantment.checkSafe(level); return addStoredEnchant(enchantment, level); }

    override fun clearStoredEnchant(): ItemBuilder
            { tag.remove(TAG_STORED_ENCHANTMENTS); return this; }

    /** skull */

    override fun getSkullOwner(block: (self: ItemBuilder, owner: String?) -> Unit): ItemBuilder
            { block(this, tag.getStringOrNull(TAG_SKULL_OWNER)); return this; }

    override fun setSkullOwner(owner: String): ItemBuilder
            { tag.putString(TAG_SKULL_OWNER, owner); return this; }

    override fun getSkullTexture(block: (self: ItemBuilder, value: String?) -> Unit): ItemBuilder {
        val skullOwner = tag.getValueOfNull(TAG_SKULL_OWNER)
        var value: String? = null
        if(skullOwner?.type == NBTType.TAG_COMPOUND) {
            val properties = (skullOwner as NBTCompound).getCompoundOrNull(TAG_SKULL_PROPERTIES)
            val textures = properties?.getListOrNull<NBTCompound>(TAG_SKULL_TEXTURES)
            if(textures != null) for(texture in textures) {
                value = texture.getStringOrNull("Value")
                if(value != null)
                    break
            }
        }
        block(this, value)
        return this
    }

    override fun setSkullTexture(value: String): ItemBuilder {
        tag.getCompoundOrDefault(TAG_SKULL_OWNER)
                    .putString("Id", UUID.randomUUID().toString())
                    .getCompoundOrDefault(TAG_SKULL_PROPERTIES)
                    .getListOrDefault<NBTCompound>(TAG_SKULL_TEXTURES).clearSelf()
                    .addCompound(NBTFactory.ofCompound().putString("Value", value))
        return this
    }

    /** spawn egg */

    override fun getSpawnEggType(block: (self: ItemBuilder, type: EntityType?) -> Unit): ItemBuilder // TODO EntityType.fromName
            { block(this, tag.getCompoundOrNull(TAG_ENTITY_TAG)?.getStringOrNull(TAG_ENTITY_TAG_ID).let { if(it == null) null else EntityType.fromName(it) }); return this; }

    override fun setSpawnEggType(type: EntityType): ItemBuilder
            { tagEntityTag().putString(TAG_ENTITY_TAG_ID, type.name); return this; }

    override fun setSpawnEggType(entity: Entity): ItemBuilder
            { tag.putCompound(NBTFactory.readEntityTag(entity).putString(TAG_ENTITY_TAG_ID, entity.type.name)); return this; }

    /** map */

    override fun getMapScaling(block: (self: ItemBuilder, scaling: Boolean) -> Unit): ItemBuilder
            { block(this, tag.getBooleanOrFalse(TAG_MAP_SCALING)); return this; }

    override fun setMapScaling(scaling: Boolean): ItemBuilder
            { tag.putBoolean(TAG_MAP_SCALING, scaling); return this; }

    override fun getMapLocationName(block: (self: ItemBuilder, locationName: String?) -> Unit): ItemBuilder
            { block(this, tag.getCompoundOrNull(TAG_DISPLAY)?.getStringOrNull(TAG_DISPLAY_LOC_NAME)); return this; }

    override fun setMapLocationName(locationName: String): ItemBuilder
            { tagDisplay().putString(TAG_DISPLAY_LOC_NAME, locationName); return this; }

    override fun getMapColor(block: (self: ItemBuilder, color: Color?) -> Unit): ItemBuilder
            { block(this, tag.getCompoundOrNull(TAG_DISPLAY)?.getIntOrNull(TAG_MAP_COLOR).let { if(it == null) null else Color.fromRGB(it) }); return this; }

    override fun setMapColor(color: Color): ItemBuilder
            { tagDisplay().putInt(TAG_MAP_COLOR, color.asRGB()); return this; }

    /** potion */

    override fun getPotionColor(block: (self: ItemBuilder, color: Color?) -> Unit): ItemBuilder
            { block(this, tag.getIntOrNull(TAG_CUSTOM_POTION_COLOR).let { if(it == null) null else Color.fromRGB(it) }); return this; }

    override fun setPotionColor(color: Color): ItemBuilder
            { tag.putInt(TAG_CUSTOM_POTION_COLOR, color.asRGB()); return this; }

    override fun getPotionBase(block: (self: ItemBuilder, base: EffectBase?) -> Unit): ItemBuilder
            { block(this, tag.getStringOrNull(TAG_POTION).let { if(it == null) null else EffectBase.fromName(it) }); return this; }

    override fun setPotionBase(base: EffectBase): ItemBuilder
            { tag.putString(TAG_POTION, base.value); return this; }

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

    override fun addPotionEffect(effect: EffectCustom): ItemBuilder
            = addPotionEffect(effect.type, effect.duration, effect.amplifier, effect.ambient, effect.particle, effect.color)

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

    override fun clearBannerPattern(): ItemBuilder
            { tagBlockEntityTag().remove(TAG_BANNER_PATTERNS); return this; }
}
