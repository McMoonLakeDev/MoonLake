/*
 * Copyright (C) 2017 The MoonLake Authors
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

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.exception.CannotDependException;
import org.bukkit.entity.Player;

/**
 * <h1>DependEconomy</h1>
 * 依赖经济插件接口 # 依赖 <a href="https://www.github.com/u2g/MoonLakeEconomy" target="_blank">MoonLakeEconomy</a> 插件
 *
 * @version 1.0
 * @author Month_Light
 * @see Depend
 */
public interface DependEconomy extends Depend {

    /**
     * 获取指定玩家的金币数据
     *
     * @param name 玩家名
     * @return 金币
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     */
    double getMoney(String name) throws CannotDependException;

    /**
     * 获取指定玩家的点券数据
     *
     * @param name 玩家名
     * @return 点券
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     */
    int getPoint(String name) throws CannotDependException;

    /**
     * 设置指定玩家的金币数据
     *
     * @param name 玩家名
     * @param money 金币
     * @return 是否成功
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     */
    boolean setMoney(String name, double money) throws CannotDependException;

    /**
     * 设置指定玩家的点券数据
     *
     * @param name 玩家名
     * @param point 点券
     * @return 是否成功
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     */
    boolean setPoint(String name, int point) throws CannotDependException;

    /**
     * 给予指定玩家金币数据
     *
     * @param name 玩家名
     * @param money 金币
     * @return 是否成功
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     */
    boolean giveMoney(String name, double money) throws CannotDependException;

    /**
     * 给予指定玩家点券数据
     *
     * @param name 玩家名
     * @param point 点券
     * @return 是否成功
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     */
    boolean givePoint(String name, int point) throws CannotDependException;

    /**
     * 减少指定玩家的金币数据
     *
     * @param name 玩家名
     * @param money 金币
     * @return 是否成功
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     */
    boolean takeMoney(String name, double money) throws CannotDependException;

    /**
     * 减少指定玩家的点券数据
     *
     * @param name 玩家名
     * @param point 点券
     * @return 是否成功
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     */
    boolean takePoint(String name, int point) throws CannotDependException;

    /**
     * 获取指定玩家的经济数据并转换到 MoonLake 包的类对象
     *
     * @param player 玩家
     * @return 经济数据
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     * @throws IllegalArgumentException 如果月色之湖玩家对象为 {@code null} 则抛出异常
     */
    EconomyPlayerData getData(Player player) throws CannotDependException;

    /**
     * 获取指定玩家的经济数据并转换到 MoonLake 包的类对象
     *
     * @param moonLakePlayer 月色之湖玩家
     * @return 经济数据
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     * @throws IllegalArgumentException 如果月色之湖玩家对象为 {@code null} 则抛出异常
     */
    EconomyPlayerData getData(MoonLakePlayer moonLakePlayer) throws CannotDependException;
}
