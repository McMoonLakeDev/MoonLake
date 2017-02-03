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


package com.minecraft.moonlake.api.event.player;

import com.minecraft.moonlake.api.event.MoonLakeEvent;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

/**
 * <h1>MoonLakePlayerEvent</h1>
 * 月色之湖玩家事件类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see MoonLakeEvent
 */
public abstract class MoonLakePlayerEvent extends MoonLakeEvent {

    private final Player player;

    /**
     * 月色之湖玩家事件类构造函数
     *
     * @param player Bukkit 玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public MoonLakePlayerEvent(Player player) throws IllegalArgumentException {

        Validate.notNull(player, "The player object is null.");

        this.player = player;
    }

    /*/**
     * 月色之湖玩家事件类构造函数
     *
     * @param moonLakePlayer MoonLake 玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    /*public MoonLakePlayerEvent(MoonLakePlayer moonLakePlayer) throws IllegalArgumentException {

        Validate.notNull(moonLakePlayer, "The moonlake player object is null.");

        this.moonLakePlayer = moonLakePlayer;
    }

    /**
     * 获取此玩家事件的 MoonLake 玩家对象
     *
     * @return MoonLakePlayer
     */
    /*public final MoonLakePlayer getPlayer() {

        return moonLakePlayer;
    }*/

    /**
     * 获取此玩家事件的 Bukkit 玩家对象
     *
     * @return BukkitPlayer
     */
    public final Player getBukkitPlayer() {

        return player;
    }

    /**
     * 获取此玩家事件的玩家名
     *
     * @return 玩家名
     */
    public final String getName() {

        return player.getName();
    }
}
