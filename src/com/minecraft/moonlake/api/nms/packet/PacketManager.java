package com.minecraft.moonlake.api.nms.packet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/6/23.
 */
public final class PacketManager {

    /**
     * 获取玩家数组对象从玩家名数组
     *
     * @param names 玩家名
     * @return 玩家数组对象
     */
    public static Player[] getPlayersfromNames(String... names) {

        Player[] players = new Player[names.length];
        int index = 0;

        for(String name : names) {

            players[index] = Bukkit.getServer().getPlayer(name);
            index++;
        }
        return players;
    }
}
