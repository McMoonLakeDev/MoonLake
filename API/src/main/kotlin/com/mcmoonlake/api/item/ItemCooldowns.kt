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

package com.mcmoonlake.api.item

import com.mcmoonlake.api.currentBukkitVersion
import com.mcmoonlake.api.entity.Entities
import com.mcmoonlake.api.isOrLater
import com.mcmoonlake.api.reflect.FuzzyReflect
import com.mcmoonlake.api.reflect.accessor.AccessorField
import com.mcmoonlake.api.reflect.accessor.AccessorMethod
import com.mcmoonlake.api.reflect.accessor.Accessors
import com.mcmoonlake.api.utility.MinecraftReflection
import com.mcmoonlake.api.version.IllegalBukkitVersionException
import com.mcmoonlake.api.version.MinecraftBukkitVersion
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * ## ItemCooldowns (物品冷却时间)
 *
 * * Minecraft 1.9 After Cooldown Tool for ItemStack.
 * * Minecraft 1.9 之后物品栈的冷却时间工具类.
 *
 * @see [Material]
 * @author lgou2w
 * @since 2.0
 */
object ItemCooldowns {

    @JvmStatic
    private val itemCooldownClass: Class<*> by lazy {
        MinecraftReflection.getMinecraftClass("ItemCooldown") }
    @JvmStatic
    private val itemCooldownInfoClass: Class<*> by lazy {
        MinecraftReflection.getMinecraftClass("ItemCooldown\$Info") }
    @JvmStatic
    private val itemCooldownInfoEndTick: AccessorField by lazy {
        Accessors.getAccessorField(itemCooldownInfoClass, 1, true) }
    @JvmStatic
    private val itemCooldownMap: AccessorField by lazy {
        Accessors.getAccessorField(itemCooldownClass, Map::class.java, true) }
    @JvmStatic
    private val itemCooldownCurrentTick: AccessorField by lazy {
        Accessors.getAccessorField(itemCooldownClass, Int::class.java, true) }
    @JvmStatic
    private val itemCooldownSet: AccessorMethod by lazy {
        Accessors.getAccessorMethod(itemCooldownClass, Void::class.java, true, arrayOf(MinecraftReflection.itemClass, Int::class.java)) }
    @JvmStatic
    private val itemCooldownHas: AccessorMethod by lazy {
        Accessors.getAccessorMethod(itemCooldownClass, Boolean::class.java, true, arrayOf(MinecraftReflection.itemClass)) }
    @JvmStatic
    private val entityHumanItemCooldown: AccessorField by lazy {
        Accessors.getAccessorField(FuzzyReflect.fromClass(MinecraftReflection.entityHumanClass, true)
                .getFieldByType("itemCooldown", itemCooldownClass), true) }

    @JvmStatic
    @JvmName("checkAndGet")
    @Throws(IllegalBukkitVersionException::class)
    private fun checkAndGet(player: Player): Any? {
        if(!currentBukkitVersion().isOrLater(MinecraftBukkitVersion.V1_9_R1))
            throw IllegalBukkitVersionException("物品冷却功能不支持您的服务器, 需要 1.9+ 版本.")
        return entityHumanItemCooldown.get(Entities.asNMSEntity(player))
    }

    /**
     * * Set the cooldown for the given player to specify the material type.
     * * 将给定玩家的指定物品栈类型设置冷却时间.
     *
     * @param player Player.
     * @param player 玩家.
     * @param type Material type.
     * @param type 物品栈类型.
     * @param ticks Cooldown ticks.
     * @param ticks 冷却时间刻. (20Tick=1s)
     * @throws IllegalBukkitVersionException If the version is less than 1.9
     * @throws IllegalBukkitVersionException 如果版本小于 1.9
     */
    @JvmStatic
    @JvmName("set")
    @Throws(IllegalBukkitVersionException::class)
    fun set(player: Player, type: Material, ticks: Int) {
        val handle = checkAndGet(player)
        val item = Items.getItemByType(type) // TODO v1.13
        itemCooldownSet.invoke(handle, item, ticks)
    }

    /**
     * * Get the cooldown for the given player's specified material type.
     * * 获取给定玩家的指定物品栈类型的冷却时间.
     *
     * @param player Player.
     * @param player 玩家.
     * @param type Material type.
     * @param type 物品栈类型.
     * @throws IllegalBukkitVersionException If the version is less than 1.9
     * @throws IllegalBukkitVersionException 如果版本小于 1.9
     */
    @JvmStatic
    @JvmName("get")
    @Throws(IllegalBukkitVersionException::class)
    fun get(player: Player, type: Material): Int {
        val handle = checkAndGet(player)
        val item = Items.getItemByType(type) // TODO v1.13
        val map = itemCooldownMap.get(handle) as Map<*, *>
        val info = map[item] ?: return 0
        val infoEndTick = itemCooldownInfoEndTick.get(info) as Int
        val currentTick = itemCooldownCurrentTick.get(handle) as Int
        return Math.max(0, infoEndTick - currentTick)
    }

    /**
     * * Gets whether the given player has a cooldown for the specified material type.
     * * 获取给定玩家的指定物品栈类型是否拥有冷却时间.
     *
     * @param player Player.
     * @param player 玩家.
     * @param type Material type.
     * @param type 物品栈类型.
     * @throws IllegalBukkitVersionException If the version is less than 1.9
     * @throws IllegalBukkitVersionException 如果版本小于 1.9
     */
    @JvmStatic
    @JvmName("has")
    @Throws(IllegalBukkitVersionException::class)
    fun has(player: Player, type: Material): Boolean {
        val handle = checkAndGet(player)
        val item = Items.getItemByType(type) // TODO v1.13
        return (itemCooldownHas.invoke(handle, item) as Boolean?) ?: false
    }
}
