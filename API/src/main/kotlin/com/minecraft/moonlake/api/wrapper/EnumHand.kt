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

package com.minecraft.moonlake.api.wrapper

import com.minecraft.moonlake.api.Valuable
import com.minecraft.moonlake.api.currentBukkitVersion
import com.minecraft.moonlake.api.isOrLater
import com.minecraft.moonlake.api.version.MinecraftBukkitVersion

/**
 * Only applies to Minecraft 1.9+
 */
enum class EnumHand(val value: Int) : Valuable<Int> {

    MAIN(0),
    OFF(1),
    ;

    override fun value(): Int
            = value

    companion object {

        @JvmField
        val HAND_AVAILABLE = currentBukkitVersion().isOrLater(MinecraftBukkitVersion.V1_9_R1)

        @JvmStatic
        @JvmName("support")
        fun support(): Boolean
                = HAND_AVAILABLE
    }
}
