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

import com.mcmoonlake.api.player.MoonLakePlayer
import com.mcmoonlake.api.region.Region
import com.mcmoonlake.api.wrapper.EconomyResponse

/**
 * ## DependPlayer (依赖玩家)
 *
 * @see [Depend]
 * @author lgou2w
 * @since 2.0
 */
interface DependPlayer : Depend {

    /**
     * PlaceholderAPI
     *  @see [DependPlaceholderAPI]
     */

    /**
     * @see [DependPlaceholderAPI.setPlaceholders]
     */
    @Throws(DependPluginException::class)
    fun setPlaceholders(text: String): String

    /**
     * @see [DependPlaceholderAPI.setBracketPlaceholders]
     */
    @Throws(DependPluginException::class)
    fun setPlaceholdersBracket(text: String): String

    /**
     * @see [DependPlaceholderAPI.setRelationalPlaceholders]
     */
    @Throws(DependPluginException::class)
    fun setPlaceholdersRelational(target: MoonLakePlayer, text: String): String

    /**
     * Vault Economy
     * @see [DependVaultEconomy]
     */

    /**
     * @see [DependVaultEconomy.format]
     */
    @Throws(DependPluginException::class)
    fun formatEconomy(value: Double): String

    /**
     * @see [DependVaultEconomy.hasAccount]
     */
    @Throws(DependPluginException::class)
    fun hasEconomyAccount(world: String? = null): Boolean

    /**
     * @see [DependVaultEconomy.createAccount]
     */
    @Throws(DependPluginException::class)
    fun createEconomyAccount(world: String? = null): Boolean

    /**
     * @see [DependVaultEconomy.getBalance]
     */
    @Throws(DependPluginException::class)
    fun getEconomyBalance(world: String? = null): Double

    /**
     * @see [DependVaultEconomy.hasBalance]
     */
    @Throws(DependPluginException::class)
    fun hasEconomyBalance(value: Double, world: String? = null): Boolean

    /**
     * @see [DependVaultEconomy.withdraw]
     */
    @Throws(DependPluginException::class)
    fun withdrawEconomy(value: Double, world: String? = null): EconomyResponse

    /**
     * @see [DependVaultEconomy.deposit]
     */
    @Throws(DependPluginException::class)
    fun depositEconomy(value: Double, world: String? = null): EconomyResponse

    /**
     * @see [DependVaultEconomy.isBankOwner]
     */
    @Throws(DependPluginException::class)
    fun isEconomyBankOwner(name: String): EconomyResponse

    /**
     * @see [DependVaultEconomy.isBankMember]
     */
    @Throws(DependPluginException::class)
    fun isEconomyBankMember(name: String): EconomyResponse

    /**
     * WorldEdit
     * @see [DependWorldEdit]
     */

    /**
     * @see [DependWorldEdit.getSelection]
     */
    @Throws(DependPluginException::class)
    fun getWorldEditSelection(): Region?

    /**
     * @see [DependWorldEdit.setSelection]
     */
    @Throws(DependPluginException::class, IllegalArgumentException::class)
    fun setWorldEditSelection(region: Region)
}
