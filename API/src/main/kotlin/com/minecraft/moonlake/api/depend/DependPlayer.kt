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

package com.minecraft.moonlake.api.depend

import com.minecraft.moonlake.api.player.MoonLakePlayer
import com.minecraft.moonlake.api.region.Region
import com.minecraft.moonlake.api.wrapper.EconomyResponse

interface DependPlayer {

    /**
     * PlaceholderAPI
     *  @see [DependPlaceholderAPI]
     */

    @Throws(DependPluginException::class)
    fun setPlaceholders(text: String): String

    @Throws(DependPluginException::class)
    fun setPlaceholdersBracket(text: String): String

    @Throws(DependPluginException::class)
    fun setPlaceholdersRelational(target: MoonLakePlayer, text: String): String

    /**
     * Vault Economy
     * @see [DependVaultEconomy]
     */

    @Throws(DependPluginException::class)
    fun formatEconomy(value: Double): String

    @Throws(DependPluginException::class)
    fun hasEconomyAccount(world: String? = null): Boolean

    @Throws(DependPluginException::class)
    fun createEconomyAccount(world: String? = null): Boolean

    @Throws(DependPluginException::class)
    fun getEconomyBalance(world: String? = null): Double

    @Throws(DependPluginException::class)
    fun hasEconomyBalance(value: Double, world: String? = null): Boolean

    @Throws(DependPluginException::class)
    fun withdrawEconomy(value: Double, world: String? = null): EconomyResponse

    @Throws(DependPluginException::class)
    fun depositEconomy(value: Double, world: String? = null): EconomyResponse

    /**
     * WorldEdit
     * @see [DependWorldEdit]
     */

    @Throws(DependPluginException::class)
    fun getWorldEditSelection(): Region?

    @Throws(DependPluginException::class, IllegalArgumentException::class)
    fun setWorldEditSelection(region: Region)
}
