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

@file:JvmName("DSLItemBuilder")

package com.mcmoonlake.api.dsl

import com.mcmoonlake.api.attribute.AttributeItemModifier
import com.mcmoonlake.api.attribute.AttributeType
import com.mcmoonlake.api.attribute.Operation
import com.mcmoonlake.api.attribute.Slot
import com.mcmoonlake.api.chat.ChatComponent
import com.mcmoonlake.api.effect.EffectBase
import com.mcmoonlake.api.effect.EffectCustom
import com.mcmoonlake.api.effect.EffectType
import com.mcmoonlake.api.item.Enchantment
import com.mcmoonlake.api.item.Generation
import com.mcmoonlake.api.item.ItemBuilder
import com.mcmoonlake.api.item.Pattern
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * # If call the property getter, it returns the empty object.
 *
 * * Indicates that this property is only for the function setter.
 * * 指示此属性仅适用于函数设置器.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class DSLItemBuilderSetterOnly

@DslMarker
annotation class DSLItemBuilderMarker

@DSLItemBuilderMarker
class DSLItemBuilderScope(itemStack: ItemStack) {

    private val builder = ItemBuilder.of(itemStack)

    private inline fun <T> ItemBuilder.apply(value: T?, block: ItemBuilder.(T) -> Unit)
            = if(value != null) block(this, value) else {}

    /**
     * general meta
     * @see org.bukkit.inventory.meta.ItemMeta
     */

    var displayName: String?
        set(value) = builder.apply(value) { setDisplayName(it) }
        get() {
            var value: String? = null
            builder.getDisplayName { _, displayName -> value = displayName }
            return value
        }

    var localizedName: String?
        set(value) = builder.apply(value) { setLocalizedName(it) }
        get() {
            var value: String? = null
            builder.getLocalizedName { _, localizedName -> value = localizedName }
            return value
        }

    var lore: List<String>?
        set(value) = builder.apply(value) { setLore(it) }
        get() {
            var value: List<String>? = null
            builder.getLore { _, lore -> value = lore }
            return value
        }

    @DSLItemBuilderSetterOnly
    var addLore: Array<String>
        set(value) = builder.apply(value) { addLore(*it) }
        get() = emptyArray()

    fun clearLore()
            { builder.clearLore() }

    var enchant: Map<Enchantment, Int>?
        set(value) = builder.apply(value) { clearEnchant(); it.forEach { addEnchant(it.key, it.value) } }
        get() {
            var value: Map<Enchantment, Int>? = null
            builder.getEnchant { _, ench -> value = ench }
            return value
        }

    @DSLItemBuilderSetterOnly
    var addEnchants: Array<Pair<Enchantment, Int>>
        set(value) = builder.apply(value) { it.forEach { addEnchant(it.first, it.second) } }
        get() = emptyArray()

    class EnchantScope {
        var enchant: Enchantment? = null
        var level: Int? = null
    }

    fun addEnchant(block: EnchantScope.() -> Unit)
            = builder.apply(DSLItemBuilderScope.EnchantScope().apply(block)) {
        val enchant = it.enchant
        val level = it.level
        if(enchant != null && level != null)
            builder.addEnchant(enchant, level)
    }

    fun clearEnchant()
            { builder.clearEnchant() }

    var flag: Collection<ItemFlag>?
        set(value) = builder.apply(value) { clearFlag(); addFlag(it) }
        get() {
            var value: Collection<ItemFlag>? = null
            builder.getFlag { _, flag -> value = flag?.toList() }
            return value
        }

    @DSLItemBuilderSetterOnly
    var addFlag: Array<ItemFlag>
        set(value) = builder.apply(value) { addFlag(*it) }
        get() = emptyArray()

    @DSLItemBuilderSetterOnly
    var removeFlag: Array<ItemFlag>?
        set(value) = builder.apply(value) { removeFlag(*it) }
        get() = emptyArray()

    var isUnbreakable: Boolean?
        set(value) = builder.apply(value) { setUnbreakable(it) }
        get() {
            var value: Boolean? = null
            builder.isUnbreakable { _, unbreakable -> value = unbreakable }
            return value
        }

    var attributes: Set<AttributeItemModifier>?
        set(value) = builder.apply(value) { it.forEach { addAttribute(it.type, it.operation, it.slot, it.amount, it.uuid) } }
        get() {
            var value: Set<AttributeItemModifier>? = null
            builder.getAttribute { _, attribute -> value = attribute }
            return value
        }

    @DSLItemBuilderMarker
    class AttributeScope {
        var type: AttributeType? = null
        var name: String? = null
        var operation: Operation? = null
        var slot: Slot? = null
        var amount: Double? = null
        var uuid: UUID = UUID.randomUUID()
    }

    fun addAttribute(block: DSLItemBuilderScope.AttributeScope.() -> Unit)
            = builder.apply(DSLItemBuilderScope.AttributeScope().apply(block)) {
        val type = it.type
        val operation = it.operation
        val amount = it.amount
        if(type != null && operation != null && amount != null)
            addAttribute(type, it.name ?: type.name, operation, it.slot, amount, it.uuid)
    }

    fun clearAttribute()
            { builder.clearAttribute() }

    var canDestroy: Array<Material>?
        set(value) = builder.apply(value) { setCanDestroy(*it) }
        get() {
            var value: Array<Material>? = null
            builder.getCanDestroy { _, canDestroy -> value = canDestroy?.toTypedArray() }
            return value
        }

    @DSLItemBuilderSetterOnly
    var addCanDestroy: Array<Material>
        set(value) = builder.apply(value) { addCanDestroy(*it) }
        get() = emptyArray()

    fun clearCanDestroy()
            { builder.clearCanDestroy() }

    var canPlaceOn: Array<Material>?
        set(value) = builder.apply(value) { setCanPlaceOn(*it) }
        get() {
            var value: Array<Material>? = null
            builder.getCanPlaceOn { _, canPlaceOn -> value = canPlaceOn?.toTypedArray() }
            return value
        }

    @DSLItemBuilderSetterOnly
    var addCanPlaceOn: Array<Material>
        set(value) = builder.apply(value) { addCanPlaceOn(*it) }
        get() = emptyArray()

    fun clearCanPlaceOn()
            { builder.clearCanPlaceOn() }

    var repairCost: Int?
        set(value) = builder.apply(value) { setRepairCost(it) }
        get() {
            var value: Int? = null
            builder.getRepairCost { _, repairCost -> value = repairCost }
            return value
        }

    /**
     * leather armor meta
     * @see org.bukkit.inventory.meta.LeatherArmorMeta
     */

    var leatherColor: Color?
        set(value) = builder.apply(value) { setLeatherColor(it) }
        get() {
            var value: Color? = null
            builder.getLeatherColor { _, color -> value = color }
            return value
        }

    /**
     * book meta
     * @see org.bukkit.inventory.meta.BookMeta
     */

    var bookTitle: String?
        set(value) = builder.apply(value) { setBookTitle(it) }
        get() {
            var value: String? = null
            builder.getBookTitle { _, title -> value = title }
            return value
        }

    var bookAuthor: String?
        set(value) = builder.apply(value) { setBookAuthor(it) }
        get() {
            var value: String? = null
            builder.getBookAuthor { _, author -> value = author }
            return value
        }

    var bookGeneration: Generation?
        set(value) = builder.apply(value) { setBookGeneration(it) }
        get() {
            var value: Generation? = null
            builder.getBookGeneration { _, generation -> value = generation }
            return value
        }

    var bookPages: Collection<String>?
        set(value) = builder.apply(value) { setBookPages(it) }
        get() {
            var value: Collection<String>? = null
            builder.getBookPages { _, pages -> value = pages }
            return value
        }

    @DSLItemBuilderSetterOnly
    var addBookPages: Array<String>
        set(value) = builder.apply(value) { addBookPages(*it) }
        get() = emptyArray()

    @DSLItemBuilderSetterOnly
    var addBookPageComponents: Array<ChatComponent>
        set(value) = builder.apply(value) { addBookPages(*it) }
        get() = emptyArray()

    fun clearBookPages()
            { builder.clearBookPages() }

    /**
     * enchantment storage meta
     * @see org.bukkit.inventory.meta.EnchantmentStorageMeta
     */

    var storedEnchant: Map<Enchantment, Int>?
        set(value) = builder.apply(value) { clearStoredEnchant(); it.forEach { addStoredEnchant(it.key, it.value) } }
        get() {
            var value: Map<Enchantment, Int>? = null
            builder.getStoredEnchant { _, ench -> value = ench }
            return value
        }

    @DSLItemBuilderSetterOnly
    var addStoredEnchants: Array<Pair<Enchantment, Int>>
        set(value) = builder.apply(value) { it.forEach { addStoredEnchant(it.first, it.second) } }
        get() = emptyArray()

    fun addStoredEnchant(block: EnchantScope.() -> Unit)
            = builder.apply(DSLItemBuilderScope.EnchantScope().apply(block)) {
        val enchant = it.enchant
        val level = it.level
        if(enchant != null && level != null)
            builder.addStoredEnchant(enchant, level)
    }

    fun clearStoredEnchant()
            { builder.clearStoredEnchant() }

    /**
     * skull meta
     * @see org.bukkit.inventory.meta.SkullMeta
     */

    var skullOwner: String?
        set(value) = builder.apply(value) { setSkullOwner(it) }
        get() {
            var value: String? = null
            builder.getSkullOwner { _, owner -> value = owner }
            return value
        }

    var skullTexture: String?
        set(value) = builder.apply(value) { setSkullTexture(it) }
        get() {
            var value0: String? = null
            builder.getSkullTexture { _, value -> value0 = value }
            return value0
        }

    /**
     * spawn egg meta
     * @see org.bukkit.inventory.meta.SpawnEggMeta
     */

    var spawnEggType: EntityType?
        set(value) = builder.apply(value) { setSpawnEggType(it) }
        get() {
            var value: EntityType? = null
            builder.getSpawnEggType { _, type -> value = type }
            return value
        }

    @DSLItemBuilderSetterOnly
    var spawnEggEntity: Entity?
        set(value) = builder.apply(value) { setSpawnEggType(it) }
        get() = null

    /**
     * map meta
     * @see org.bukkit.inventory.meta.MapMeta
     */

    var isMapScaling: Boolean?
        set(value) = builder.apply(value) { setMapScaling(it) }
        get() {
            var value = false
            builder.getMapScaling { _, scaling -> value = scaling }
            return value
        }

    var mapLocationName: String?
        set(value) = builder.apply(value) { setMapLocationName(it) }
        get() {
            var value: String? = null
            builder.getMapLocationName { _, locationName -> value = locationName }
            return value
        }

    var mapColor: Color?
        set(value) = builder.apply(value) { setMapColor(it) }
        get() {
            var value: Color? = null
            builder.getMapColor { _, color -> value = color }
            return value
        }

    /**
     * potion meta
     * @see org.bukkit.inventory.meta.PotionMeta
     */

    var potionColor: Color?
        set(value) = builder.apply(value) { setPotionColor(it) }
        get() {
            var value: Color? = null
            builder.getPotionColor { _, color -> value = color }
            return value
        }

    var potionBase: EffectBase?
        set(value) = builder.apply(value) { setPotionBase(it) }
        get() {
            var value: EffectBase? = null
            builder.getPotionBase { _, base -> value = base }
            return value
        }

    var potionEffect: Collection<EffectCustom>?
        set(value) = builder.apply(value) { setPotionEffect(it) }
        get() {
            var value: Collection<EffectCustom>? = null
            builder.getPotionEffect { _, effect -> value = effect }
            return value
        }

    @DSLItemBuilderSetterOnly
    var addPotionEffect: Array<EffectCustom>
        set(value) = builder.apply(value) { addPotionEffect(*it) }
        get() = emptyArray()

    @DSLItemBuilderMarker
    class PotionEffectScope {
        var type: EffectType? = null
        var duration: Int? = null
        var amplifier: Int? = null
        var ambient: Boolean = true
        var particle: Boolean = true
        var color: Color? = null
    }

    fun addPotionEffect(block: DSLItemBuilderScope.PotionEffectScope.() -> Unit)
            = builder.apply(DSLItemBuilderScope.PotionEffectScope().apply(block)) {
        val type = it.type
        val duration = it.duration
        val amplifier = it.amplifier
        if(type != null && duration != null && amplifier != null)
            addPotionEffect(type, duration, amplifier, it.ambient, it.particle, it.color)
    }

    fun clearPotionEffect()
            { builder.clearPotionEffect() }

    /**
     * firework meta
     * @see org.bukkit.inventory.meta.FireworkMeta
     */

    var fireworkEffect: Collection<FireworkEffect>?
        set(value) = builder.apply(value) { setFireworkEffect(it) }
        get() {
            var value: Collection<FireworkEffect>? = null
            builder.getFireworkEffect { _, effect -> value = effect }
            return value
        }

    @DSLItemBuilderSetterOnly
    var addFireworkEffect: Array<FireworkEffect>
        set(value) = builder.apply(value) { addFireworkEffect(*it) }
        get() = emptyArray()

    @DSLItemBuilderMarker
    class FireworkEffectScope {
        var type: FireworkEffect.Type? = null
        var flicker: Boolean? = null
        var trail: Boolean? = null
        var colors: Array<Color>? = null
        var fadeColors: Array<Color>? = null
    }

    fun addFirework(block: DSLItemBuilderScope.FireworkEffectScope.() -> Unit)
            = builder.apply(DSLItemBuilderScope.FireworkEffectScope().apply(block)) {
        val type = it.type
        if(type != null) {
            val effect = FireworkEffect.builder()
                    .with(type)
                    .flicker(it.flicker ?: false)
                    .trail(it.trail ?: false)
                    .withColor(*it.colors ?: arrayOf())
                    .withFade(*it.fadeColors ?: arrayOf())
            addFireworkEffect(effect.build())
        }
    }

    fun clearFireworkEffect()
            { builder.clearFireworkEffect() }

    var fireworkPower: Int?
        set(value) = builder.apply(value) { setFireworkPower(it) }
        get() {
            var value: Int? = null
            builder.getFireworkPower { _, power -> value = power }
            return value
        }

    /**
     * banner meta
     * @see org.bukkit.inventory.meta.BannerMeta
     */

    var bannerPattern: Collection<Pattern>?
        set(value) = builder.apply(value) { setBannerPattern(it) }
        get() {
            var value: Collection<Pattern>? = null
            builder.getBannerPattern { _, pattern -> value = pattern }
            return value
        }

    @DSLItemBuilderSetterOnly
    var addBannerPattern: Array<Pattern>?
        set(value) = builder.apply(value) { it.forEach { addBannerPattern(it) } }
        get() = emptyArray()

    fun clearBannerPattern()
            { builder.clearBannerPattern() }

    /**
     * knowledge book meta
     * @see org.bukkit.inventory.meta.KnowledgeBookMeta
     */

    var knowledgeBookRecipes: List<Material>?
        set(value) = builder.apply(value) { setKnowledgeBookRecipes(it) }
        get() {
            var value: List<Material>? = null
            builder.getKnowledgeBookRecipes { _, recipes -> value = recipes }
            return value
        }

    @DSLItemBuilderSetterOnly
    var addKnowledgeBookRecipes: Array<Material>
        set(value) = builder.apply(value) { addKnowledgeBookRecipes(*it) }
        get() = emptyArray()

    /**
     * * Not recommended for external calls.
     *
     * @see [buildItemBuilder]
     */
    fun get(): ItemBuilder
            = builder
}

inline fun ItemStack.buildItemBuilder(block: DSLItemBuilderScope.() -> Unit): ItemBuilder
        = DSLItemBuilderScope(this).also(block).get()

@JvmOverloads
inline fun Material.buildItemBuilder(amount: Int = 1, durability: Int = 0, block: DSLItemBuilderScope.() -> Unit): ItemBuilder
        = DSLItemBuilderScope(ItemStack(this, amount, durability.toShort())).also(block).get()
