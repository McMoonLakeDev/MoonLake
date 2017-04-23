/*
 * Copyright (C) 2017 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY throws CannotDependException; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.minecraft.moonlake.api.player.depend;

import com.minecraft.moonlake.exception.CannotDependException;
import org.bukkit.OfflinePlayer;

/**
 * <h1>DependEconomyVault</h1>
 * 依赖经济 Vault 接口 # 依赖 <a href="https://github.com/MilkBowl/Vault" target="_blank">Vault</a> 插件和 <a href="https://github.com/MilkBowl/VaultAPI" target="_blank">VaultAPI</a> 的 <a href="https://github.com/MilkBowl/VaultAPI/blob/master/src/main/java/net/milkbowl/vault/economy/Economy.java" target="_blank">Economy</a>
 *
 * @version 1.0
 * @author Month_Light
 * @see Depend
 */
public interface DependEconomyVault extends Depend {

    /**
     * 获取指定玩家的 Vault 经济是否拥有账户
     *
     * @param player 玩家
     * @return 是否拥有账户
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     */
    boolean hasAccount(OfflinePlayer player) throws CannotDependException;

    /**
     * 获取指定玩家在指定世界的 Vault 经济是否拥有账户
     *
     * @param player 玩家
     * @param worldName 世界名
     * @return 是否在指定世界拥有账户
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     */
    boolean hasAccount(OfflinePlayer player, String worldName) throws CannotDependException;

    /**
     * 获取指定玩家的 Vault 经济账户余额
     *
     * @param player 玩家
     * @return 余额
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     */
    double getBalance(OfflinePlayer player) throws CannotDependException;

    /**
     * 获取指定玩家在指定世界的 Vault 经济账户余额
     *
     * @param player 玩家
     * @param worldName 世界名
     * @return 余额
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     */
    double getBalance(OfflinePlayer player, String worldName) throws CannotDependException;

    /**
     * 获取指定玩家的 Vault 经济账户是否拥有指定数量余额
     *
     * @param player 玩家
     * @param amount 数量
     * @return 是否拥有指定数量余额
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     */
    boolean hasBalance(OfflinePlayer player, double amount) throws CannotDependException;

    /**
     * 获取指定玩家在指定世界的 Vault 经济账户是否拥有指定数量余额
     *
     * @param player 玩家
     * @param amount 数量
     * @param worldName 世界名
     * @return 是否拥有指定数量余额
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     */
    boolean hasBalance(OfflinePlayer player, double amount, String worldName) throws CannotDependException;

    /**
     * 将指定玩家的 Vault 经济账户拿走指定数量的余额
     *
     * @param player 玩家
     * @param amount 数量
     * @return 经济回应
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     */
    EconomyVaultPlayerResponse withdrawBalance(OfflinePlayer player, double amount) throws CannotDependException;

    /**
     * 将指定玩家在指定世界的 Vault 经济账户拿走指定数量的余额
     *
     * @param player 玩家
     * @param amount 数量
     * @param worldName 世界名
     * @return 经济回应
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     */
    EconomyVaultPlayerResponse withdrawBalance(OfflinePlayer player, double amount, String worldName) throws CannotDependException;

    /**
     * 将指定玩家的 Vault 经济账户存入指定数量的余额
     *
     * @param player 玩家
     * @param amount 数量
     * @return 经济回应
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     */
    EconomyVaultPlayerResponse depositBalance(OfflinePlayer player, double amount) throws CannotDependException;

    /**
     * 将指定玩家在指定世界的 Vault 经济账户存入指定数量的余额
     *
     * @param player 玩家
     * @param amount 数量
     * @param worldName 世界名
     * @return 经济回应
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     */
    EconomyVaultPlayerResponse depositBalance(OfflinePlayer player, double amount, String worldName) throws CannotDependException;

    /**
     * 将指定玩家的 Vault 经济账户进行创建
     *
     * @param player 玩家
     * @return 是否创建成功
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     */
    boolean createAccount(OfflinePlayer player) throws CannotDependException;

    /**
     * 将指定玩家在指定世界的 Vault 经济账户进行创建
     *
     * @param player 玩家
     * @param worldName 世界名
     * @return 是否创建成功
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     */
    boolean createAccount(OfflinePlayer player, String worldName) throws CannotDependException;
}
