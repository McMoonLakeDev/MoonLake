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

package com.mcmoonlake.api.depend

import com.mcmoonlake.api.wrapper.EconomyResponse
import org.bukkit.OfflinePlayer

/**
 * ## DependVaultEconomy (依赖 Vault 插件)
 *
 * @see [DependPlugin]
 * @author lgou2w
 * @since 2.0
 */
interface DependVaultEconomy : DependPlugin {

    /**
     * * Gets name of economy method.
     * * 获取经济方法的名称.
     */
    val name: String

    /**
     * * This function returns the number of digits the plugin keeps or `-1` if no rounding occurs.
     * * 此函数返回插件保留的位数, 如果不发生舍入, 则返回 `-1` .
     */
    fun fractionalDigits(): Int

    /**
     * * Returns the name of the currency in plural form.
     * * * If the economy being used does not support currency names then an empty string will be returned.
     * * 以复数形式返回货币的名称.
     * * * 如果使用的经济不支持货币名称, 那么将返回一个空字符串.
     */
    fun currencyNamePlural(): String

    /**
     * * Returns the name of the currency in singular form.
     * * * If the economy being used does not support currency names then an empty string will be returned.
     * * 以单数形式返回货币的名称.
     * * * 如果使用的经济不支持货币名称, 那么将返回一个空字符串.
     */
    fun currencyNameSingular(): String

    /**
     * * Format amount into a human readable String.
     * * * This provides translation into economy specific formatting to improve consistency between plugins.
     * * 将金额格式化为人类可读的字符串.
     * * * 这将翻译成经济特定的格式以提高插件之间的一致性.
     */
    fun format(value: Double): String

    /**
     * * Checks if this player has an account on the server yet.
     * * * This will always return `true` if the player has joined the server at least once.
     *       As all major economy plugins auto-generate a player account when the player joins the server.
     * * 检查该玩家是否在服务器上有帐户.
     * * * 如果玩家没有加入一次服务器, 这将始终返回 `true`.
     *       因为所有主要的经济插件在玩家加入服务器时自动生成一个玩家账户.
     *
     * @param player Player.
     * @param player 玩家.
     * @param world World-specific account.
     * @param world 世界特定账户.
     */
    fun hasAccount(player: OfflinePlayer, world: String? = null): Boolean

    /**
     * * Attempts to create a player account for the given player.
     * * 尝试为给定的玩家创建玩家帐户.
     *
     * @param player Player.
     * @param player 玩家.
     * @param world World-specific account.
     * @param world 世界特定账户.
     */
    fun createAccount(player: OfflinePlayer, world: String? = null): Boolean

    /**
     * * Gets balance of a player.
     * * 获取玩家的经济.
     *
     * @param player Player.
     * @param player 玩家.
     * @param world World-specific account.
     * @param world 世界特定账户.
     */
    fun getBalance(player: OfflinePlayer, world: String? = null): Double

    /**
     * * Check if the player has a given amount of balance.
     * * 检测玩家是否拥有给定数量的经济.
     *
     * @param player Player.
     * @param player 玩家.
     * @param value Amount.
     * @param value 数量.
     * @param world World-specific account.
     * @param world 世界特定账户.
     */
    fun hasBalance(player: OfflinePlayer, value: Double, world: String? = null): Boolean

    /**
     * * Withdraw an amount from a player.
     * * 从玩家提取一笔金额.
     *
     * @param player Player.
     * @param player 玩家.
     * @param value Amount.
     * @param value 数量.
     * @param world World-specific account.
     * @param world 世界特定账户.
     */
    fun withdraw(player: OfflinePlayer, value: Double, world: String? = null): EconomyResponse

    /**
     * * Deposit an amount to a player.
     * * 将一笔金额存入玩家.
     *
     * @param player Player.
     * @param player 玩家.
     * @param value Amount.
     * @param value 数量.
     * @param world World-specific account.
     * @param world 世界特定账户.
     */
    fun deposit(player: OfflinePlayer, value: Double, world: String? = null): EconomyResponse

    /**
     * * Returns `true` if the given implementation supports banks.
     * * 如果给定的实现支持银行, 则返回 `true`.
     */
    fun hasBankSupport(): Boolean

    /**
     * * Creates a bank account with the specified name and the player as the owner.
     * * 创建一个具有指定名称的银行账户, 并将该玩家作为所有者.
     *
     * @param name Bank name.
     * @param name 银行名称.
     * @param owner Bank owner.
     * @param owner 银行拥有者.
     */
    fun createBank(name: String, owner: OfflinePlayer): EconomyResponse

    /**
     * * Deletes a bank account with the specified name.
     * * 删除具有指定名称的银行帐户.
     *
     * @param name Bank name.
     * @param name 银行名称.
     */
    fun deleteBank(name: String): EconomyResponse

    /**
     * * Gets balance of a bank.
     * * 获取银行的经济.
     *
     * @param name Bank name.
     * @param name 银行名称.
     */
    fun getBankBalance(name: String): EconomyResponse

    /**
     * * Check if the bank has a given amount of balance.
     * * 检测银行是否拥有给定数量的经济.
     *
     * @param name Bank name.
     * @param name 银行名称.
     * @param value Amount.
     * @param value 数量.
     */
    fun hasBankBalance(name: String, value: Double): EconomyResponse

    /**
     * * Withdraw an amount from a bank account.
     * * 从银行账户提取金额.
     *
     * @param name Bank name.
     * @param name 银行名称.
     * @param value Amount.
     * @param value 数量.
     */
    fun bankWithdraw(name: String, value: Double): EconomyResponse

    /**
     * * Deposit an amount into a bank account.
     * * 将金额存入银行账户.
     *
     * @param name Bank name.
     * @param name 银行名称.
     * @param value Amount.
     * @param value 数量.
     */
    fun bankDeposit(name: String, value: Double): EconomyResponse

    /**
     * * Check if a player is the owner of a bank account.
     * * 检查一个玩家是否是银行账户的所有者.
     *
     * @param name Bank name.
     * @param name 银行名称.
     * @param player Player.
     * @param player 玩家.
     */
    fun isBankOwner(name: String, player: OfflinePlayer): EconomyResponse

    /**
     * * Check if the player is a member of the bank account.
     * * 检查玩家是否是银行账户的成员.
     *
     * @param name Bank name.
     * @param name 银行名称.
     * @param player Player.
     * @param player 玩家.
     */
    fun isBankMember(name: String, player: OfflinePlayer): EconomyResponse

    /**
     * * Gets the list of banks.
     * * 获取银行清单.
     */
    val banks: List<String>

    companion object {
        /**
         * * The plugin name for Vault
         */
        const val NAME = "Vault"
    }
}
