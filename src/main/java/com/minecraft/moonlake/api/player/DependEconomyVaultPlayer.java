/*
 * Copyright (C) 2016 The MoonLake Authors
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


package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.player.depend.DependEconomyVault;
import com.minecraft.moonlake.api.player.depend.DependPluginPlayerAbstract;
import com.minecraft.moonlake.api.player.depend.EconomyVaultPlayerResponse;
import com.minecraft.moonlake.exception.CannotDependException;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * <h1>DependEconomyVaultPlayer</h1>
 * 依赖经济 Vault 玩家实现类 # 依赖 <a href="https://github.com/MilkBowl/Vault" target="_blank">Vault</a> 插件和 <a href="https://github.com/MilkBowl/VaultAPI" target="_blank">VaultAPI</a> 的 <a href="https://github.com/MilkBowl/VaultAPI/blob/master/src/main/java/net/milkbowl/vault/economy/Economy.java" target="_blank">Economy</a>
 *
 * @version 1.0
 * @author Month_Light
 */
class DependEconomyVaultPlayer extends DependPluginPlayerAbstract<Vault> implements DependEconomyVault {

    private Economy economy;

    /**
     * 依赖经济 Vault 玩家实现类构造函数
     *
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public DependEconomyVaultPlayer() throws CannotDependException {

        super((Vault) Bukkit.getServer().getPluginManager().getPlugin("Vault"));

        if(getOwn() == null) {

            throw new CannotDependException("The cannot depend 'Vault' plugin exception.");
        }
        RegisteredServiceProvider<Economy> economyRegisteredServiceProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

        if(economyRegisteredServiceProvider == null) {

            throw new CannotDependException("The depend 'Vault' plugin 'Economy' service provider is null.");
        }
        this.economy = economyRegisteredServiceProvider.getProvider();

        if(this.economy == null) {

            throw new CannotDependException("The depend 'Vault' plugin 'Economy' service provider object is null.");
        }

        MoonLakeAPI.getLogger().info("Success hook 'Vault' plugin 'Economy' service provider, 'EconomyVaultPlayer' interface be use.");
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {

        return economy.hasAccount(player);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {

        return economy.hasAccount(player, worldName);
    }

    @Override
    public double getBalance(OfflinePlayer player) {

        return economy.getBalance(player);
    }

    @Override
    public double getBalance(OfflinePlayer player, String worldName) {

        return economy.getBalance(player, worldName);
    }

    @Override
    public boolean hasBalance(OfflinePlayer player, double amount) {

        return economy.has(player, amount);
    }

    @Override
    public boolean hasBalance(OfflinePlayer player, double amount, String worldName) {

        return economy.has(player, worldName, amount);
    }

    /**
     * 将 Vault 的经济回应对象转换到 MoonLake 经济回应对象
     *
     * @param response Vault 经济回应对象
     * @return MoonLake 经济回应对象
     */
    protected EconomyVaultPlayerResponse convert(EconomyResponse response) {

        if(response == null) {

            return null;
        }
        return new EconomyVaultPlayerResponse(response.amount, response.balance, EconomyVaultPlayerResponse.ResponseType.valueOf(response.type.name()), response.errorMessage);
    }

    @Override
    public EconomyVaultPlayerResponse withdrawBalance(OfflinePlayer player, double amount) {

        return convert(economy.withdrawPlayer(player, amount));
    }

    @Override
    public EconomyVaultPlayerResponse withdrawBalance(OfflinePlayer player, double amount, String worldName) {

        return convert(economy.withdrawPlayer(player, worldName, amount));
    }

    @Override
    public EconomyVaultPlayerResponse depositBalance(OfflinePlayer player, double amount) {

        return convert(economy.depositPlayer(player, amount));
    }

    @Override
    public EconomyVaultPlayerResponse depositBalance(OfflinePlayer player, double amount, String worldName) {

        return convert(economy.depositPlayer(player, worldName, amount));
    }

    @Override
    public boolean createAccount(OfflinePlayer player) {

        return economy.createPlayerAccount(player);
    }

    @Override
    public boolean createAccount(OfflinePlayer player, String worldName) {

        return economy.createPlayerAccount(player, worldName);
    }

    //
    // 暂时不添加 Vault 经济的银行功能
    ///
}
