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

package com.minecraft.moonlake.api.depend

import com.minecraft.moonlake.api.player.MoonLakePlayer
import com.minecraft.moonlake.api.wrapper.EconomyResponse
import org.bukkit.OfflinePlayer

interface DependVaultEconomy : DependPlugin {

    fun format(value: Double): String

    fun hasAccount(player: OfflinePlayer, world: String? = null): Boolean

    fun hasAccount(player: MoonLakePlayer, world: String? = null): Boolean

    fun createAccount(player: OfflinePlayer, world: String? = null): Boolean

    fun createAccount(player: MoonLakePlayer, world: String? = null): Boolean

    fun getBalance(player: OfflinePlayer, world: String? = null): Double

    fun getBalance(player: MoonLakePlayer, world: String? = null): Double

    fun hasBalance(player: OfflinePlayer, value: Double, world: String? = null): Boolean

    fun hasBalance(player: MoonLakePlayer, value: Double, world: String? = null): Boolean

    fun withdraw(player: OfflinePlayer, value: Double, world: String? = null): EconomyResponse

    fun withdraw(player: MoonLakePlayer, value: Double, world: String? = null): EconomyResponse

    fun deposit(player: OfflinePlayer, value: Double, world: String? = null): EconomyResponse

    fun deposit(player: MoonLakePlayer, value: Double, world: String? = null): EconomyResponse

    // TODO Bank function is not currently available

    companion object {

        const val NAME = "Vault"
    }
}
