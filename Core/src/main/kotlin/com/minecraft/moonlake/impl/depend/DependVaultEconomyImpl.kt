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

package com.minecraft.moonlake.impl.depend

import com.minecraft.moonlake.api.depend.DependPluginAbstract
import com.minecraft.moonlake.api.depend.DependPluginException
import com.minecraft.moonlake.api.depend.DependVaultEconomy
import com.minecraft.moonlake.api.getPlugin
import com.minecraft.moonlake.api.getServicesManager
import net.milkbowl.vault.Vault
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.OfflinePlayer
import org.bukkit.plugin.RegisteredServiceProvider

class DependVaultEconomyImpl : DependPluginAbstract<Vault>(getPlugin(DependVaultEconomy.NAME)), DependVaultEconomy {

    /**
     * Vault 插件存在不代表 Economy 功能是否拥有服务实现, 所以初始化的时候获取并判断是否不为 null 否之抛出异常
     */

    private val economy: Economy

    init {
        val registered: RegisteredServiceProvider<Economy>? = getServicesManager().getRegistration(Economy::class.java)
        if(registered == null || registered.provider == null)
            throw DependPluginException("依赖插件 Vault 未存在注册的 Economy 服务功能.")
        this.economy = registered.provider
    }

    private fun adapter(value: EconomyResponse?): com.minecraft.moonlake.api.wrapper.EconomyResponse {
        if(value?.type == null)
            return com.minecraft.moonlake.api.wrapper.EconomyResponse(.0, .0, com.minecraft.moonlake.api.wrapper.EconomyResponse.Type.NULL, null)
        val type = when(value.type) {
            EconomyResponse.ResponseType.FAILURE -> com.minecraft.moonlake.api.wrapper.EconomyResponse.Type.FAILURE
            EconomyResponse.ResponseType.SUCCESS -> com.minecraft.moonlake.api.wrapper.EconomyResponse.Type.SUCCESS
            EconomyResponse.ResponseType.NOT_IMPLEMENTED -> com.minecraft.moonlake.api.wrapper.EconomyResponse.Type.NOT_IMPLEMENTED
            else -> com.minecraft.moonlake.api.wrapper.EconomyResponse.Type.NULL
        }
        return com.minecraft.moonlake.api.wrapper.EconomyResponse(value.amount, value.balance, type, value.errorMessage)
    }

    override fun format(value: Double): String
            = economy.format(value)

    override fun hasAccount(player: OfflinePlayer, world: String?): Boolean
            = economy.hasAccount(player, world)

    override fun createAccount(player: OfflinePlayer, world: String?): Boolean
            = economy.createPlayerAccount(player, world)

    override fun getBalance(player: OfflinePlayer, world: String?): Double
            = economy.getBalance(player, world)

    override fun hasBalance(player: OfflinePlayer, value: Double, world: String?): Boolean
            = economy.has(player, world, value)

    override fun withdraw(player: OfflinePlayer, value: Double, world: String?): com.minecraft.moonlake.api.wrapper.EconomyResponse
            = adapter(economy.withdrawPlayer(player, world, value))

    override fun deposit(player: OfflinePlayer, value: Double, world: String?): com.minecraft.moonlake.api.wrapper.EconomyResponse
            = adapter(economy.depositPlayer(player, world, value))
}
