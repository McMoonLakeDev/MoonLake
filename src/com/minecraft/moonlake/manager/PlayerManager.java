package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.api.playerlib.Playerlib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by MoonLake on 2016/7/17.
 */
public class PlayerManager extends MoonLakeManager {

    /**
     * 获取月色之湖玩家支持库实例对象
     *
     * @return 玩家支持库实例对象
     */
    public static Playerlib getLibrary() {

        return getMain().getPlayerlib();
    }

    /**
     * 获取指定玩家对象从目标名称
     *
     * @param name 玩家名
     * @return 玩家对象 没有则返回 null
     */
    public static Player getPlayerFromName(String name) {

        return Bukkit.getServer().getPlayer(name);
    }

    /**
     * 获取指定玩家对象从目标 UUID
     *
     * @param uuid 玩家 UUID
     * @return 玩家对象 没有则返回 null
     */
    public static Player getPlayerFromName(UUID uuid) {

        return Bukkit.getServer().getPlayer(uuid);
    }
}
