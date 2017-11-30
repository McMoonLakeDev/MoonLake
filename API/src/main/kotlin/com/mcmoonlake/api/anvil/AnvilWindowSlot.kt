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

package com.mcmoonlake.api.anvil

import com.mcmoonlake.api.Valuable

/**
 * ## AnvilWindowSlot (铁砧窗口槽位)
 *
 * @see [Valuable]
 * @author lgou2w
 * @since 2.0
 */
enum class AnvilWindowSlot(
        /**
         * * Current slot index value.
         * * 当前槽位索引值.
         */
        val slot: Int) : Valuable<Int> {

    /**
     * * Anvil Window Slot: Left Input
     * * 铁砧窗口槽: 左输入
     */
    INPUT_LEFT(0),
    /**
     * * Anvil Window Slot: Right Input
     * * 铁砧窗口槽: 右输入
     */
    INPUT_RIGHT(1),
    /**
     * * Anvil Window Slot: Output
     * * 铁砧窗口槽: 输出
     */
    OUTPUT(2),
    ;

    override fun value(): Int
            = slot

    /** static */

    companion object {

        /**
         * * Obtain the anvil window slot from a given slot index value.
         * * 从给定的槽位索引值中获取铁砧窗口槽
         *
         * @param slot Slot index value.
         * @param slot 槽位索引值.
         */
        @JvmStatic
        @JvmName("fromSlot")
        fun fromSlot(slot: Int): AnvilWindowSlot? = when(slot) {
            0 -> INPUT_LEFT
            1 -> INPUT_RIGHT
            2 -> OUTPUT
            else -> null
        }
    }
}
