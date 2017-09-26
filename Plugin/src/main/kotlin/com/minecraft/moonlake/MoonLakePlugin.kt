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
import com.minecraft.moonlake.api.depend.DependPlaceholderAPI
import com.minecraft.moonlake.api.depend.DependPlugins
import com.minecraft.moonlake.api.depend.DependVaultEconomy
import com.minecraft.moonlake.api.depend.DependWorldEdit
import com.minecraft.moonlake.api.packet.PacketListeners
import com.minecraft.moonlake.api.region.*
import com.minecraft.moonlake.api.registerEvent
import com.minecraft.moonlake.api.setMoonLake
import com.minecraft.moonlake.api.version.MinecraftBukkitVersion
import com.minecraft.moonlake.api.version.MinecraftVersion
import com.minecraft.moonlake.impl.depend.DependPlaceholderAPIImpl
import com.minecraft.moonlake.impl.depend.DependVaultEconomyImpl
import com.minecraft.moonlake.impl.depend.DependWorldEditImpl
import com.minecraft.moonlake.impl.listeners.PluginListeners
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin

class MoonLakePlugin : JavaPlugin, MoonLake {

    constructor(): super()

    override fun onLoad() {
        setMoonLake(this)
        registerConfigurationSerializable()
        registerDependPluginImplement()
    }

    override fun onEnable() {
        this.registerMoonLakePluginListeners()
        this.classLoader.loadClass(PacketListeners::class.java.name)
        this.logger.info("Server ${MinecraftVersion.currentVersion()} NMS: ${MinecraftBukkitVersion.currentVersion().getVersion()}")
        this.logger.info("月色之湖核心 API 插件 v${getPluginVersion()} 成功加载.")
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
        DependPlugins.register(DependVaultEconomy::class.java, DependVaultEconomyImpl::class.java)
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
