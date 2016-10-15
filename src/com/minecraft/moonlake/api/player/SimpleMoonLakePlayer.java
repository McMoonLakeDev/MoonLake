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
