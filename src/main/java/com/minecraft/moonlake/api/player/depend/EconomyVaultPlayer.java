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


package com.minecraft.moonlake.api.player.depend;

import com.minecraft.moonlake.exception.CannotDependException;

/**
 * <h1>EconomyVaultPlayer</h1>
 * 经济 Vault 玩家接口类 # 依赖 <a href="https://github.com/MilkBowl/Vault" target="_blank">Vault</a> 插件和 <a href="https://github.com/MilkBowl/VaultAPI" target="_blank">VaultAPI</a> 的 <a href="https://github.com/MilkBowl/VaultAPI/blob/master/src/main/java/net/milkbowl/vault/economy/Economy.java" target="_blank">Economy</a>
 *
 * @version 1.0
 * @author Month_Light
 */
public interface EconomyVaultPlayer {

    /**
     * 获取此玩家的 Vault 经济是否拥有账户
     *
     * @return 是否拥有账户
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    boolean hasEconomyVaultAccount() throws CannotDependException;

    /**
     * 获取指定玩家在指定世界的 Vault 经济是否拥有账户
     *
     * @param world 世界名
     * @return 是否拥有账户
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    boolean hasEconomyVaultAccount(String world) throws CannotDependException;

    /**
     * 将指定玩家的 Vault 经济账户进行创建
     *
     * @return 是否创建成功
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    boolean createEconomyVaultAccount() throws CannotDependException;

    /**
     * 将指定玩家在指定世界的 Vault 经济账户进行创建
     *
     * @param world 世界名
     * @return 是否创建成功
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    boolean createEconomyVaultAccount(String world) throws CannotDependException;

    /**
     * 获取指定玩家的 Vault 经济账户余额
     *
     * @return 余额
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    double getEconomyVaultBalance() throws CannotDependException;

    /**
     * 获取指定玩家在指定世界的 Vault 经济账户余额
     *
     * @param world 世界名
     * @return 余额
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    double getEconomyVaultBalance(String world) throws CannotDependException;

    /**
     * 获取指定玩家的 Vault 经济账户是否拥有指定数量余额
     *
     * @param amount 数量
     * @return 是否拥有指定数量余额
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    boolean hasEconomyVaultBalance(double amount) throws CannotDependException;

    /**
     * 获取指定玩家在指定世界的 Vault 经济账户是否拥有指定数量余额
     *
     * @param amount 数量
     * @param world 世界名
     * @return 是否拥有指定数量余额
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    boolean hasEconomyVaultBalance(double amount, String world) throws CannotDependException;

    /**
     * 将指定玩家的 Vault 经济账户拿走指定数量的余额
     *
     * @param amount 数量
     * @return 经济回应
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    EconomyVaultPlayerResponse withdrawEconomyVaultBalance(double amount) throws CannotDependException;

    /**
     * 将指定玩家在指定世界的 Vault 经济账户拿走指定数量的余额
     *
     * @param amount 数量
     * @param world 世界名
     * @return 经济回应
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    EconomyVaultPlayerResponse withdrawEconomyVaultBalance(double amount, String world) throws CannotDependException;

    /**
     * 将指定玩家的 Vault 经济账户存入指定数量的余额
     *
     * @param amount 数量
     * @return 经济回应
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    EconomyVaultPlayerResponse depositEconomyVaultBalance(double amount) throws CannotDependException;

    /**
     * 将指定玩家在指定世界的 Vault 经济账户存入指定数量的余额
     *
     * @param amount 数量
     * @param world 世界名
     * @return 经济回应
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    EconomyVaultPlayerResponse depositEconomyVaultBalance(double amount, String world) throws CannotDependException;
}
