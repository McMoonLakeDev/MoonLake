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

    public SimpleMoonLakePlayer(String name) {

        super(name);
    }

    public SimpleMoonLakePlayer(Player player) {

        super(player);
    }
}
