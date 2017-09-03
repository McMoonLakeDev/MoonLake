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
import com.minecraft.moonlake.api.anvil.*
import com.minecraft.moonlake.api.event.MoonLakeListener
import com.minecraft.moonlake.api.region.*
import com.minecraft.moonlake.api.registerEvent
import com.minecraft.moonlake.api.setMoonLake
import com.minecraft.moonlake.api.toBukkitWorld
import com.minecraft.moonlake.api.utility.MinecraftConverters
import com.minecraft.moonlake.api.version.MinecraftBukkitVersion
import com.minecraft.moonlake.api.version.MinecraftVersion
import com.minecraft.moonlake.impl.anvil.AnvilWindowImpl_v1_12_R1
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.entity.IronGolem
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.server.ServerCommandEvent
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
            fun onCommand(event: ServerCommandEvent) {
                if(event.command == "cc") {
                    val world = "world".toBukkitWorld()!!
                    val entity = world.spawn(Location(world, .0, 100.0, .0), IronGolem::class.java)
                    val nmsEntity = MinecraftConverters.getEntity(IronGolem::class.java).getGeneric(entity)
                    println("entity=$entity")
                    println("nmsEntity=$nmsEntity")
                }
            }
            @EventHandler
            fun onPlace(event: BlockPlaceEvent) {
                if(event.block.type == Material.ANVIL) {
                    val anvilWindow = AnvilWindowImpl_v1_12_R1(this@MoonLakePlugin)
                    anvilWindow.setOpen(object: AnvilWindowEventHandler<AnvilWindowOpenEvent> {
                        override fun execute(param: AnvilWindowOpenEvent) {
                            // 在铁砧窗口打开的时候设置左边输入栏的物品为西瓜
                            param.player.sendMessage("open anvil")
                            param.anvilWindow.setItem(AnvilWindowSlot.INPUT_LEFT, ItemStack(Material.MELON))
                        }
                    })
                    anvilWindow.setClick(object: AnvilWindowEventHandler<AnvilWindowClickEvent> {
                        override fun execute(param: AnvilWindowClickEvent) {
                            // 当点击铁砧窗口输出栏的时候打印物品信息
                            if(param.clickSlot == AnvilWindowSlot.OUTPUT) {
                                param.player.sendMessage("output=" + param.clickItemStack)
                                param.player.closeInventory()
                            }
                        }
                    })
                    anvilWindow.setClose(object: AnvilWindowEventHandler<AnvilWindowCloseEvent> {
                        override fun execute(param: AnvilWindowCloseEvent) {
                            // 当铁砧窗口关闭的时候发送消息
                            param.player.sendMessage("close anvil")
                            param.anvilWindow.clear()
                        }
                    })
                    anvilWindow.setAllowMove(false) // 不允许移动物品
                    anvilWindow.open(event.player)
                    println("anvilWindow=$anvilWindow")
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
