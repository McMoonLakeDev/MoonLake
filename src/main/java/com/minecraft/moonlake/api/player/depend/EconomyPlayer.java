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
 * <h1>EconomyPlayer</h1>
 * 经济玩家接口类 # 依赖 <a href="https://www.github.com/u2g/MoonLakeEconomy" target="_blank">MoonLakeEconomy</a> 插件
 *
 * @version 1.0
 * @author Month_Light
 */
public interface EconomyPlayer {

    /**
     * 获取此玩家的金币数据
     *
     * @return 金币
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    double getEconomyMoney() throws CannotDependException;

    /**
     * 设置此玩家的金币数据
     *
     * @param money 金币
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    void setEconomyMoney(double money) throws CannotDependException;

    /**
     * 给予此玩家指定金币数据
     *
     * @param money 金币
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    void giveEconomyMoney(double money) throws CannotDependException;

    /**
     * 减少此玩家指定金币数据
     *
     * @param money 金币
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    void takeEconomyMoney(double money) throws CannotDependException;

    /**
     * 获取此玩家的点券数据
     *
     * @return 点券
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    int getEconomyPoint() throws CannotDependException;

    /**
     * 设置此玩家的点券数据
     *
     * @param point 点券
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    void setEconomyPoint(int point) throws CannotDependException;

    /**
     * 给予此玩家指定点券数据
     *
     * @param point 点券
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    void giveEconomyPoint(int point) throws CannotDependException;

    /**
     * 减少此玩家指定点券数据
     *
     * @param point 点券
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    void takeEconomyPoint(int point) throws CannotDependException;

    /**
     * 获取此玩家的经济数据
     *
     * @return 经济数据
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    EconomyPlayerData getEconomyData() throws CannotDependException;
}
