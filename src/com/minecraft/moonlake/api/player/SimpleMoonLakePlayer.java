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

import org.bukkit.entity.Player;

/**
 * <h1>SimpleMoonLakePlayer</h1>
 * 月色之湖玩家接口单包装类
 *
 * @version 1.0
 * @author Month_Light
 * @see MoonLakePlayer
 * @see AbstractPlayer
 */
public class SimpleMoonLakePlayer extends AbstractPlayer {

    /**
     * 月色之湖玩家接口单包装类构造函数
     *
     * @param name 玩家名
     */
    public SimpleMoonLakePlayer(String name) {

        super(name);
    }

    /**
     * 月色之湖玩家接口单包装类构造函数
     *
     * @param player 玩家对象
     */
    public SimpleMoonLakePlayer(Player player) {

        super(player);
    }
}
