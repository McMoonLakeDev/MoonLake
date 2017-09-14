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
import com.minecraft.moonlake.api.attribute.AttributeType
import com.minecraft.moonlake.api.attribute.Operation
import com.minecraft.moonlake.api.attribute.Slot
import com.minecraft.moonlake.api.chat.ChatComponentFancy
import com.minecraft.moonlake.api.depend.DependPlaceholderAPI
import com.minecraft.moonlake.api.depend.DependPlugins
import com.minecraft.moonlake.api.depend.DependWorldEdit
import com.minecraft.moonlake.api.effect.EffectType
import com.minecraft.moonlake.api.event.MoonLakeListener
import com.minecraft.moonlake.api.item.Enchantment
import com.minecraft.moonlake.api.item.ItemBuilder
import com.minecraft.moonlake.api.nbt.NBTFactory
import com.minecraft.moonlake.api.region.*
import com.minecraft.moonlake.api.registerEvent
import com.minecraft.moonlake.api.setMoonLake
import com.minecraft.moonlake.api.toMoonLakePlayer
import com.minecraft.moonlake.api.version.MinecraftBukkitVersion
import com.minecraft.moonlake.api.version.MinecraftVersion
import com.minecraft.moonlake.impl.depend.DependPlaceholderAPIImpl
import com.minecraft.moonlake.impl.depend.DependWorldEditImpl
import com.minecraft.moonlake.impl.listeners.PluginListeners
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class MoonLakePlugin : JavaPlugin, MoonLake {

    constructor(): super()

    override fun onLoad() {
        registerConfigurationSerializable()
        registerDependPluginImplement()
    }

    override fun onEnable() {
        setMoonLake(this)
        this.registerMoonLakePluginListeners()
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
                    var itemStack = ItemBuilder.of(Material.IRON_SWORD)
                            .setLocalizedName("tile.tnt.name") // 本地化名称
                            .setCanDestroy(Material.DIAMOND_ORE) // 只能破坏钻石矿
                            .setCanPlaceOn(Material.STONE) // 只能放置在石头上
                            .addEnchant(Enchantment.锋利, 5) // 附魔锋利5
                            .setUnbreakable(true) // 不可破坏
                            .setAttribute(AttributeType.攻击伤害, Operation.百分比, Slot.主手, .05) // 攻击增加 5%
                            .setAttribute(AttributeType.攻击速度, Operation.百分比, Slot.主手, .1) // 攻击速度增加 10%
                            .addLore("标签属性", "标签属性") // 标签属性
                            .clearEnchant()
                            .addFlag(ItemFlag.HIDE_UNBREAKABLE) // 隐藏不可破坏属性
                            .build()

                    itemStack = ItemBuilder.of(itemStack)
                            .removeFlag(ItemFlag.HIDE_UNBREAKABLE)
                            .build()

                    event.player.inventory.addItem(itemStack)
                }
                if(event.message == "/ib potion") {
                    val itemStack = ItemBuilder.of(Material.POTION)
                            .addPotionEffect(EffectType.速度, 100, 0)
                            .addPotionEffect(EffectType.跳跃提升, 100, 0)
                            .setPotionColor(Color.fromRGB(0, 0, 0))
                            .addLore("标签属性")
                            .setDisplayName("233")
                            .addEnchant(Enchantment.锋利, 1)
                            .build()
                    event.player.inventory.addItem(itemStack)
                    event.player.toMoonLakePlayer().send(ChatComponentFancy("物品展示: ").then("[ITEM]").tooltipItem(itemStack))
                }
                if(event.message == "/ib firework") {
                    val itemStack = ItemBuilder.of(Material.FIREWORK)
                            .addFireworkEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.RED).build())
                            .addLore("233")
                            .build()
                    event.player.inventory.addItem(itemStack)
                }
                if(event.message == "/dp we") {
                    val region = DependPlugins.of(DependWorldEdit::class.java).getSelection(event.player)
                    if(region == null)
                        event.player.sendMessage("未使用 WorldEdit 选中区域.")
                    else
                        event.player.sendMessage(region.toString())
                }
                if(event.message == "/dp pipa") {
                    val value = DependPlugins.of(DependPlaceholderAPI::class.java).setRelationalPlaceholders(event.player, Bukkit.getPlayer("Month_Light"), "233")
                    event.player.sendMessage(value)
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

    /** register moonlake depend plugin implement class */
    private fun registerDependPluginImplement() {
        DependPlugins.register(DependWorldEdit::class.java, DependWorldEditImpl::class.java)
        DependPlugins.register(DependPlaceholderAPI::class.java, DependPlaceholderAPIImpl::class.java)
    }

    /** register moonlake plugin listeners */
    private fun registerMoonLakePluginListeners() {
        PluginListeners().registerEvent(this)
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
