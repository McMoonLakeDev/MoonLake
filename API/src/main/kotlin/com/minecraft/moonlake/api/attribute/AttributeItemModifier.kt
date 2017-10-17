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

package com.minecraft.moonlake.api.attribute

import com.minecraft.moonlake.api.util.ComparisonChain
import java.util.*

data class AttributeItemModifier(val type: AttributeType, val operation: Operation, val slot: Slot?, val amount: Double, val uuid: UUID) : Comparable<AttributeItemModifier> {

    override fun compareTo(other: AttributeItemModifier): Int {
        return ComparisonChain.start()
                .compare(type, other.type)
                .compare(operation, other.operation)
                .compare(slot?.ordinal ?: -1, other.slot?.ordinal ?: -1)
                .compare(amount, other.amount)
                .compare(uuid, other.uuid)
                .result
    }
}
