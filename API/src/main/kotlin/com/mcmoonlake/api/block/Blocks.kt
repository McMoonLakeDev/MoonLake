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

package com.mcmoonlake.api.block

import com.mcmoonlake.api.packet.PacketOutBlockAction
import org.bukkit.Material
import org.bukkit.block.Block

/**
 * ## Blocks
 *
 * @author lgou2w
 * @since 2.0
 */
object Blocks {

    /**
     * * Fake action with the given block. [Details](http://wiki.vg/Block_Actions)
     * * 将给定方块进行虚假交互. [详情](http://wiki.vg/Block_Actions)
     *
     * @param block Block.
     * @param block 方块.
     * @param action Action type.
     * @param action 交互类型.
     * @param parameter Action parameter.
     * @param parameter 交互参数.
     * @see [PacketOutBlockAction]
     */
    @JvmStatic
    @JvmName("fakeAction")
    @Deprecated("Removed when the first release version.")
    fun fakeAction(block: Block, action: Int, parameter: Int)
            = PacketOutBlockAction(block, action, parameter).sendToNearby(block.location, 64.0)

    /**
     * * Fake action with the given chest block.
     * * 将给定箱子方块进行虚假交互.
     *
     * @param block Chest block.
     * @param block 箱子方块.
     * @param open Whether open.
     * @param open 是否打开.
     * @throws IllegalArgumentException If the block type is not chest, trapped chest or ender chest.
     * @throws IllegalArgumentException 如果方块类型不是箱子, 陷阱箱或末影箱.
     */
    @JvmStatic
    @JvmName("fakeActionChest")
    @Throws(IllegalArgumentException::class)
    @Deprecated("Removed when the first release version.")
    fun fakeActionChest(block: Block, open: Boolean) {
        val type = block.type
        if(type == Material.CHEST || type == Material.TRAPPED_CHEST || type == Material.ENDER_CHEST)
            fakeAction(block, 1, if(open) 1 else 0)
        else
            throw IllegalArgumentException("待交互的方块类型不为箱子、陷阱箱或末影箱.")
    }
}
