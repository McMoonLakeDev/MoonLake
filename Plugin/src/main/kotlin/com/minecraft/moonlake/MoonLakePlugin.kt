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
import com.minecraft.moonlake.api.region.*
import com.minecraft.moonlake.api.registerEvent
import com.minecraft.moonlake.api.service.ServiceConfig
import com.minecraft.moonlake.api.service.ServiceManager
import com.minecraft.moonlake.api.service.ServicePacketListener
import com.minecraft.moonlake.api.setMoonLake
import com.minecraft.moonlake.api.version.MinecraftBukkitVersion
import com.minecraft.moonlake.api.version.MinecraftVersion
import com.minecraft.moonlake.impl.depend.DependPlaceholderAPIImpl
import com.minecraft.moonlake.impl.depend.DependVaultEconomyImpl
import com.minecraft.moonlake.impl.depend.DependWorldEditImpl
import com.minecraft.moonlake.impl.listeners.PluginListeners
import com.minecraft.moonlake.impl.service.ServiceConfigImpl
import com.minecraft.moonlake.impl.service.ServiceManagerImpl
import com.minecraft.moonlake.impl.service.ServicePacketListenerImpl
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin

class MoonLakePlugin : JavaPlugin(), MoonLake {

    private val serviceManagerImpl = ServiceManagerImpl()

    override fun onLoad() {
        setMoonLake(this)
        registerConfigurationSerializable()
        registerDependPluginImplement()
    }

    override fun onEnable() {
        this.registerServiceCore()
        this.registerMoonLakePluginListeners()
        this.logger.info("Server ${MinecraftVersion.currentVersion()} NMS: ${MinecraftBukkitVersion.currentVersion().version}")
        this.logger.info("月色之湖核心 API 插件 v$pluginVersion 成功加载.")
    }

    override fun onDisable() {
    }

    override val serviceManager: ServiceManager
        get() = serviceManagerImpl

    /** register moonlake service core */
    private fun registerServiceCore() {
        val configService = ServiceConfigImpl()
        serviceManager.registerService(ServiceConfig::class.java, configService)
        if(configService.hasPacketListener())
            serviceManager.registerService(ServicePacketListener::class.java, ServicePacketListenerImpl())
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

    override val pluginPrefix: String
        get() = description.prefix

    override val pluginName: String
        get() = description.name

    override val pluginMain: String
        get() = description.main

    override val pluginVersion: String
        get() = description.version

    override val pluginWebsite: String
        get() = description.website

    override val pluginDescription: String
        get() = description.description

    override val pluginAuthors: Set<String>
        get() = description.authors.toSet()

    override val pluginDepends: Set<String>
        get() = description.depend.toSet()

    override val pluginSoftDepends: Set<String>
        get() = description.softDepend.toSet()
}
