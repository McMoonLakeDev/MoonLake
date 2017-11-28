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

data class AttributeModifier(
        val name: String,
        val operation: Operation,
        val amount: Double,
        val uuid: UUID = UUID.randomUUID()) : ConfigurationSerializable, Comparable<AttributeModifier> {

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
