/*
 * Copyright (C) 2016-2017 The MoonLake (mcmoonlake@hotmail.com)
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

package com.minecraft.moonlake

import com.minecraft.moonlake.api.MoonLake
import com.minecraft.moonlake.api.attribute.AttributeOperation
import com.minecraft.moonlake.api.attribute.AttributeSlot
import com.minecraft.moonlake.api.attribute.AttributeType
import com.minecraft.moonlake.api.event.MoonLakeListener
import com.minecraft.moonlake.api.item.ItemBuilder
import com.minecraft.moonlake.api.nbt.NBTFactory
import com.minecraft.moonlake.api.region.*
import com.minecraft.moonlake.api.registerEvent
import com.minecraft.moonlake.api.setMoonLake
import com.minecraft.moonlake.api.version.MinecraftBukkitVersion
import com.minecraft.moonlake.api.version.MinecraftVersion
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class MoonLakePlugin : JavaPlugin, MoonLake {

    constructor(): super()

    override fun onLoad() {
        registerConfigurationSerializable()
    }

    override fun onEnable() {
        setMoonLake(this)
        this.logger.info("Server ${MinecraftVersion.currentVersion()} NMS: ${MinecraftBukkitVersion.currentVersion().getVersion()}")
        this.logger.info("月色之湖核心 API 插件 v${getPluginVersion()} 成功加载.")

        object: MoonLakeListener {
            @EventHandler
            fun onCommand(event: PlayerCommandPreprocessEvent) {
                if(event.message == "/nbt") {
                    val itemStack = event.player.itemInHand
                    val tag = NBTFactory.readStackTag(itemStack)
                    event.player.sendMessage(tag.toString())
                }
                if(event.message == "/nbt w") {
                    val itemStack = ItemStack(Material.DIAMOND_SWORD)
                    val tag = NBTFactory.readSafeStackTag(itemStack)
                    tag.putBoolean("Unbreakable", true) // 不可破坏
                    NBTFactory.writeStackTag(itemStack, tag)
                    event.player.itemInHand = itemStack
                }
                if(event.message == "/ib") {
                    val itemStack = ItemBuilder.of(Material.IRON_SWORD)
                            .setLocalizedName("inventory.key") // 本地化名称
                            .setCanDestroy(Material.DIAMOND_ORE) // 只能破坏钻石矿
                            .setCanPlaceOn(Material.STONE) // 只能放置在石头上
                            .addEnchant(Enchantment.DAMAGE_ALL, 5) // 附魔锋利5
                            .setUnbreakable(true) // 不可破坏
                            .setAttribute(AttributeType.ATTACK_DAMAGE, AttributeOperation.MULTIPLY, AttributeSlot.MAIN_HAND, .05) // 攻击增加 5%
                            .setAttribute(AttributeType.ATTACK_SPEED, AttributeOperation.MULTIPLY, AttributeSlot.MAIN_HAND, .1) // 攻击速度增加 10%
                            .addLore("标签属性", "标签属性") // 标签属性
                            .build()
                    event.player.inventory.addItem(itemStack)
                }
            }
        }.registerEvent(this)
    }

    override fun onDisable() {
    }

    /** register moonlake wrapped configuration serializable class */
    private fun registerConfigurationSerializable() {
        arrayOf(
                RegionVector::class.java,
                RegionVector2D::class.java,
                RegionVectorBlock::class.java,
                RegionCuboid::class.java,
                RegionCylinder::class.java,
                RegionEllipsoid::class.java
        ).forEach { ConfigurationSerialization.registerClass(it) }
    }

    override fun getPluginPrefix(): String
            = description.prefix
    override fun getPluginName(): String
            = description.name
    override fun getPluginMain(): String
            = description.main
    override fun getPluginVersion(): String
            = description.version
    override fun getPluginWebsite(): String
            = description.website
    override fun getPluginDescription(): String
            = description.description
    override fun getPluginAuthors(): Set<String>
            = description.authors.toSet()
    override fun getPluginDepends(): Set<String>
            = description.depend.toSet()
    override fun getPluginSoftDepends(): Set<String>
            = description.softDepend.toSet()
}
