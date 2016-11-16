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

import com.minecraft.moonlake.api.player.MoonLakePlayer;

/**
 * <h1>EconomyPlayerData</h1>
 * 经济玩家数据类 # 依赖 <a href="https://www.github.com/u2g/MoonLakeEconomy" target="_blank">MoonLakeEconomy</a> 插件
 *
 * @version 1.0
 * @author Month_Light
 */
public final class EconomyPlayerData {

    private final MoonLakePlayer owner;
    private final double money;
    private final int point;

    /**
     * 经济玩家数据类构造函数
     *
     * @param owner 拥有者
     * @param money 金币数据
     * @param point 点券数据
     */
    public EconomyPlayerData(MoonLakePlayer owner, double money, int point) {

        this.owner = owner;
        this.money = money;
        this.point = point;
    }

    /**
     * 获取此经济玩家数据的拥有者
     *
     * @return 拥有者
     */
    public MoonLakePlayer getOwner() {

        return owner;
    }

    /**
     * 获取此经济玩家数据的金币
     *
     * @return 金币数据
     */
    public double getMoney() {

        return money;
    }

    /**
     * 获取此经济玩家数据的点券
     *
     * @return 点券数据
     */
    public int getPoint() {

        return point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EconomyPlayerData that = (EconomyPlayerData) o;

        if (Double.compare(that.money, money) != 0) return false;
        if (point != that.point) return false;
        return owner.equals(that.owner);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = owner.hashCode();
        temp = Double.doubleToLongBits(money);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + point;
        return result;
    }

    @Override
    public String toString() {
        return "EconomyPlayerData{" +
                "owner=" + owner.getName() +
                ", money=" + money +
                ", point=" + point +
                '}';
    }
}
