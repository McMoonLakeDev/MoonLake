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

import com.mcmoonlake.api.notNull
import com.mcmoonlake.api.ofValuableNotNull
import com.mcmoonlake.api.parseDouble
import com.mcmoonlake.api.parseInt
import com.mcmoonlake.api.util.ComparisonChain
import org.bukkit.configuration.serialization.ConfigurationSerializable
import java.util.*

/**
 * ## AttributeModifier (属性修改器)
 *
 * * Add or remove this modifier from the attribute.
 * * 从属性中添加或移除此修改器.
 *
 * @see [Attribute]
 * @see [Attribute.addModifier]
 * @see [Attribute.removeModifier]
 * @see [ConfigurationSerializable]
 * @author lgou2w
 * @since 2.0
 */
data class AttributeModifier(
        /**
         * * The name of this modifier.
         * * 此修改器的名字.
         */
        val name: String,
        /**
         * * The operation mode of this modifier.
         * * 此修改器的运算模式.
         *
         * @see [Operation]
         */
        val operation: Operation,
        /**
         * * The operation amount of this modifier.
         * * 此修改器的运算数量.
         */
        val amount: Double,
        /**
         * * The unique id of this modifier.
         * * 此修改器的唯一 Id.
         */
        val uuid: UUID = UUID.randomUUID()

) : ConfigurationSerializable,
        Comparable<AttributeModifier> {

    override fun compareTo(other: AttributeModifier): Int {
        return ComparisonChain.start()
                .compare(name, other.name)
                .compare(operation, other.operation)
                .compare(amount, other.amount)
                .compare(uuid, other.uuid)
                .result
    }

    override fun serialize(): MutableMap<String, Any> {
        val result = LinkedHashMap<String, Any>()
        result.put("name", name)
        result.put("operation", operation.value())
        result.put("amount", amount)
        result.put("uuid", uuid.toString())
        return result
    }

    /** static */

    companion object {

        @JvmStatic
        @JvmName("deserialize")
        fun deserialize(args: Map<String, Any>): AttributeModifier {
            val name: String = args["name"]?.toString().notNull()
            val operation: Operation = ofValuableNotNull(args["operation"]?.parseInt())
            val amount = args["amount"]?.parseDouble() ?: .0
            val uuid = UUID.fromString(args["uuid"]?.toString())
            return AttributeModifier(name, operation, amount, uuid)
        }
    }
}
