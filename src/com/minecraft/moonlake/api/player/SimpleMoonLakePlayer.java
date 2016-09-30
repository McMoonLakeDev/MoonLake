package com.minecraft.moonlake.api.player;

import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/9/29.
 */
public class SimpleMoonLakePlayer extends AbstractPlayer {

    public SimpleMoonLakePlayer(String name) {

        super(name);
    }

    public SimpleMoonLakePlayer(Player player) {

        super(player);
    }
}
