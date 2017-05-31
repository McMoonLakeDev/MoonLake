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
import com.minecraft.moonlake.api.player.depend.DependEconomy;
import com.minecraft.moonlake.api.player.depend.DependPluginPlayerAbstract;
import com.minecraft.moonlake.api.player.depend.EconomyPlayerData;
import com.minecraft.moonlake.economy.EconomyPlugin;
import com.minecraft.moonlake.economy.api.MoonLakeEconomy;
import com.minecraft.moonlake.economy.api.PlayerEconomy;
import com.minecraft.moonlake.exception.CannotDependException;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * <h1>DependEconomyPlayer</h1>
 * 依赖经济插件玩家实现类 # 依赖 <a href="https://www.github.com/u2g/MoonLakeEconomy" target="_blank">MoonLakeEconomy</a> 插件
 *
 * @version 1.0.1
 * @author Month_Light
 */
class DependEconomyPlayer extends DependPluginPlayerAbstract<EconomyPlugin> implements DependEconomy {

    private MoonLakeEconomy moonLakeEconomy;

    /**
     * 依赖经济插件玩家实现类构造函数
     *
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public DependEconomyPlayer() throws CannotDependException {

        super((EconomyPlugin) Bukkit.getServer().getPluginManager().getPlugin("MoonLakeEconomy"));

        if(getOwn() == null) {

            throw new CannotDependException("The cannot depend 'MoonLakeEconomy' plugin exception.");
        }
        this.moonLakeEconomy = getOwn().getEconomy();

        MoonLakeAPI.getLogger().info("Success hook 'MoonLakeEconomy' plugin, 'EconomyPlayer' interface be use.");
    }

    @Override
    public double getMoney(String name) {

        return moonLakeEconomy.getMoney(name);
    }

    @Override
    public int getPoint(String name) {

        return moonLakeEconomy.getPoint(name);
    }

    @Override
    public boolean setMoney(String name, double money) {

        return moonLakeEconomy.setMoney(name, money);
    }

    @Override
    public boolean setPoint(String name, int point) {

        return moonLakeEconomy.setPoint(name, point);
    }

    @Override
    public boolean giveMoney(String name, double money) {

        return moonLakeEconomy.giveMoney(name, money);
    }

    @Override
    public boolean givePoint(String name, int point) {

        return moonLakeEconomy.givePoint(name, point);
    }

    @Override
    public boolean takeMoney(String name, double money) {

        return moonLakeEconomy.takeMoney(name, money);
    }

    @Override
    public boolean takePoint(String name, int point) {

        return moonLakeEconomy.takePoint(name, point);
    }

    /**
     * 获取指定玩家的经济数据
     *
     * @param name 玩家名
     * @return 经济数据
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     */
    protected PlayerEconomy getEconomy(String name) {

        return moonLakeEconomy.getData(name);
    }

    @Override
    public EconomyPlayerData getData(Player player) {

        Validate.notNull(player, "The player object is null.");

        PlayerEconomy playerEconomy = getEconomy(player.getName());
        return new EconomyPlayerData(PlayerManager.getCache(player), playerEconomy.getMoney(), playerEconomy.getPoint());
    }

    @Override
    public EconomyPlayerData getData(MoonLakePlayer moonLakePlayer) {

        Validate.notNull(moonLakePlayer, "The moonlake player object is null.");

        PlayerEconomy playerEconomy = getEconomy(moonLakePlayer.getName());
        return new EconomyPlayerData(moonLakePlayer, playerEconomy.getMoney(), playerEconomy.getPoint());
    }
}
