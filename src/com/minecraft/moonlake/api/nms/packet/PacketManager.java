package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.validate.Validate;
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
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     */
    public static Player[] getPlayersfromNames(String... names) {

        Validate.notNull(names, "The player names object is null.");

        Player[] players = new Player[names.length];
        int index = 0;

        for(String name : names) {

            players[index] = Bukkit.getServer().getPlayer(name);
            index++;
        }
        return players;
    }

    /**
     * 获取玩家名数组对象从玩家数组
     *
     * @param players 玩家数组
     * @return 玩家名数组对象
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public static String[] getNamesfromPlayers(Player... players) {

        Validate.notNull(players, "The players object is null.");

        String[] names = new String[players.length];
        int index = 0;

        for(Player player : players) {

            names[index] = player.getName();
            index++;
        }
        return names;
    }

    /**
     * 获取玩家名数组对象从玩家数组
     *
     * @param players 玩家数组
     * @return 玩家名数组对象
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public static String[] getNamesfromPlayers(MoonLakePlayer... players) {

        Validate.notNull(players, "The moonlake players object is null.");

        String[] names = new String[players.length];
        int index = 0;

        for(MoonLakePlayer player : players) {

            names[index] = player.getName();
            index++;
        }
        return names;
    }
}
