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

interface DependVaultEconomy : DependPlugin {

    val name: String

    fun fractionalDigits(): Int

    fun currencyNamePlural(): String

    fun currencyNameSingular(): String

    fun format(value: Double): String

    fun hasAccount(player: OfflinePlayer, world: String? = null): Boolean

    fun createAccount(player: OfflinePlayer, world: String? = null): Boolean

    fun getBalance(player: OfflinePlayer, world: String? = null): Double

    fun hasBalance(player: OfflinePlayer, value: Double, world: String? = null): Boolean

    fun withdraw(player: OfflinePlayer, value: Double, world: String? = null): EconomyResponse

    fun deposit(player: OfflinePlayer, value: Double, world: String? = null): EconomyResponse

    fun hasBankSupport(): Boolean

    fun createBank(name: String, owner: OfflinePlayer): EconomyResponse

    fun deleteBank(name: String): EconomyResponse

    fun getBankBalance(name: String): EconomyResponse

    fun hasBankBalance(name: String, value: Double): EconomyResponse

    fun bankWithdraw(name: String, value: Double): EconomyResponse

    fun bankDeposit(name: String, value: Double): EconomyResponse

    fun isBankOwner(name: String, player: OfflinePlayer): EconomyResponse

    fun isBankMember(name: String, player: OfflinePlayer): EconomyResponse

    val banks: List<String>

    companion object {

        const val NAME = "Vault"
    }
}
