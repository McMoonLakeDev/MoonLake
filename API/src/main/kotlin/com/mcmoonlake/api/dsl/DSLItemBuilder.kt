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
import com.mcmoonlake.api.item.Enchantment
import com.mcmoonlake.api.item.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
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

class DSLItemBuilderScore(material: Material, amount: Int = 1, durability: Int = 0) {

    private val builder = ItemBuilder.of(material, amount, durability)

    private inline fun <T> ItemBuilder.apply(value: T?, block: ItemBuilder.(T) -> Unit)
            = if(value != null) block(this, value) else {}

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

    class EnchantScore {
        var enchant: Enchantment? = null
        var level: Int? = null
    }

    fun addEnchant(block: EnchantScore.() -> Unit)
            = builder.apply(DSLItemBuilderScore.EnchantScore().apply(block)) {
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

    class AttributeScore {
        var type: AttributeType? = null
        var name: String? = null
        var operation: Operation? = null
        var slot: Slot? = null
        var amount: Double? = null
        var uuid: UUID = UUID.randomUUID()
    }

    fun addAttribute(block: DSLItemBuilderScore.AttributeScore.() -> Unit)
            = builder.apply(DSLItemBuilderScore.AttributeScore().apply(block)) {
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
     * * Not recommended for external calls.
     *
     * @see [buildItemBuilder]
     */
    fun get(): ItemBuilder
            = builder
}

inline fun Material.buildItemBuilder(amount: Int = 1, durability: Int = 0, block: DSLItemBuilderScore.() -> Unit): ItemBuilder
        = DSLItemBuilderScore(this, amount, durability).also(block).get()
