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

package com.mcmoonlake.api.attribute

import com.mcmoonlake.api.item.ItemBuilder
import com.mcmoonlake.api.ofValuable
import com.mcmoonlake.api.ofValuableNotNull
import com.mcmoonlake.api.parseDouble
import com.mcmoonlake.api.parseInt
import com.mcmoonlake.api.util.ComparisonChain
import org.bukkit.configuration.serialization.ConfigurationSerializable
import java.util.*

/**
 * ## AttributeItemModifier (属性物品修改器)
 *
 * * Add or remove modifiers to the specified item stack.
 * * 从物品栈中添加或移除此修改器.
 *
 * @see [ItemBuilder]
 * @see [ItemBuilder.getAttribute]
 * @see [ConfigurationSerializable]
 * @author lgou2w
 * @since 2.0
 * @constructor AttributeItemModifier
 * @param type Attribute type.
 * @param type 属性类型.
 * @param operation Operation mode.
 * @param operation 运算模式.
 * @param slot This modifier takes effect in which slot of the player. If `null` then all slots.
 * @param slot 此修改器在玩家哪个槽位生效. 如果 `null` 则所有槽位.
 * @param amount Operation amount.
 * @param amount 运算数量.
 * @param uuid Unique id.
 * @param uuid 唯一 Id.
 */
data class AttributeItemModifier(
        /**
         * * The type of this modifier.
         * * 此修改器的类型.
         */
        val type: AttributeType,
        /**
         * * The name of this modifier.
         * * 此修改器的名称.
         */
        val name: String = type.name,
        /**
         * * The operation mode of this modifier.
         * * 此修改器的运算模式.
         */
        val operation: Operation,
        /**
         * * The modifier is takes effect in which slot.  If `null` then all slots.
         * * 此修改器的生效槽位. 如果 `null` 则所有槽位.
         */
        val slot: Slot?,
        /**
         * * The operation amount of this modifier.
         * * 此修改器的运算数量.
         */
        val amount: Double,
        /**
         * * The unique id of this modifier.
         * * 此修改器的唯一 Id.
         */
        val uuid: UUID

) : ConfigurationSerializable,
        Comparable<AttributeItemModifier> {

    override fun compareTo(other: AttributeItemModifier): Int {
        return ComparisonChain.start()
                .compare(type, other.type)
                .compare(name, other.name)
                .compare(operation, other.operation)
                .compare(slot?.ordinal ?: -1, other.slot?.ordinal ?: -1)
                .compare(amount, other.amount)
                .compare(uuid, other.uuid)
                .result
    }

    override fun serialize(): MutableMap<String, Any> {
        val result = LinkedHashMap<String, Any>()
        result.put("type", type.value())
        result.put("name", name)
        result.put("operation", operation.value())
        if(slot != null) result.put("slot", slot.value())
        result.put("amount", amount)
        result.put("uuid", uuid.toString())
        return result
    }

    /** static */

    companion object {

        @JvmStatic
        @JvmName("deserialize")
        fun deserialize(args: Map<String, Any>): AttributeItemModifier {
            val type: AttributeType = ofValuableNotNull(args["type"]?.toString())
            val operation: Operation = ofValuableNotNull(args["operation"]?.parseInt())
            val slot: Slot? = ofValuable(args["slot"]?.toString())
            val amount = args["amount"]?.parseDouble() ?: .0
            val uuid = UUID.fromString(args["uuid"]?.toString())
            val name = args["name"]?.toString() ?: type.name
            return AttributeItemModifier(type, name, operation, slot, amount, uuid)
        }
    }
}
