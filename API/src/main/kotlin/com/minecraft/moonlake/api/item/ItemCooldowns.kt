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

package com.minecraft.moonlake.api.item

import com.minecraft.moonlake.api.currentBukkitVersion
import com.minecraft.moonlake.api.entity.Entities
import com.minecraft.moonlake.api.isOrLater
import com.minecraft.moonlake.api.reflect.FuzzyReflect
import com.minecraft.moonlake.api.reflect.accessor.AccessorField
import com.minecraft.moonlake.api.reflect.accessor.AccessorMethod
import com.minecraft.moonlake.api.reflect.accessor.Accessors
import com.minecraft.moonlake.api.utility.MinecraftReflection
import com.minecraft.moonlake.api.version.IllegalBukkitVersionException
import com.minecraft.moonlake.api.version.MinecraftBukkitVersion
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * Only applies to Minecraft 1.9+
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
        Accessors.getAccessorMethod(itemCooldownClass, Void::class.java, true, arrayOf(MinecraftReflection.getItemClass(), Int::class.java)) }
    @JvmStatic
    private val itemCooldownHas: AccessorMethod by lazy {
        Accessors.getAccessorMethod(itemCooldownClass, Boolean::class.java, true, arrayOf(MinecraftReflection.getItemClass())) }
    @JvmStatic
    private val entityHumanItemCooldown: AccessorField by lazy {
        Accessors.getAccessorField(FuzzyReflect.fromClass(MinecraftReflection.getEntityHumanClass(), true)
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
     * @throws IllegalBukkitVersionException If the version is less than 1.9
     */
    @JvmStatic
    @JvmName("set")
    @Throws(IllegalBukkitVersionException::class)
    fun set(player: Player, type: Material, ticks: Int) {
        val handle = checkAndGet(player)
        val item = Items.getItemByType(type)
        itemCooldownSet.invoke(handle, item, ticks)
    }

    /**
     * @throws IllegalBukkitVersionException If the version is less than 1.9
     */
    @JvmStatic
    @JvmName("get")
    @Throws(IllegalBukkitVersionException::class)
    fun get(player: Player, type: Material): Int {
        val handle = checkAndGet(player)
        val item = Items.getItemByType(type)
        val map = itemCooldownMap.get(handle) as Map<*, *>
        val info = map[item] ?: return 0
        val infoEndTick = itemCooldownInfoEndTick.get(info) as Int
        val currentTick = itemCooldownCurrentTick.get(handle) as Int
        return Math.max(0, infoEndTick - currentTick)
    }

    /**
     * @throws IllegalBukkitVersionException If the version is less than 1.9
     */
    @JvmStatic
    @JvmName("has")
    @Throws(IllegalBukkitVersionException::class)
    fun has(player: Player, type: Material): Boolean {
        val handle = checkAndGet(player)
        val item = Items.getItemByType(type)
        return (itemCooldownHas.invoke(handle, item) as Boolean?) ?: false
    }
}
