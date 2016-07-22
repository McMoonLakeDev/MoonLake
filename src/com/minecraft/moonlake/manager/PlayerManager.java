package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.api.playerlib.Playerlib;
import com.minecraft.moonlake.reflect.Reflect;
import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.Collection;
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
    public static Player fromName(String name) {

        return Bukkit.getServer().getPlayer(name);
    }

    /**
     * 获取指定玩家对象从目标 UUID
     *
     * @param uuid 玩家 UUID
     * @return 玩家对象 没有则返回 null
     */
    public static Player fromUUID(UUID uuid) {

        return Bukkit.getServer().getPlayer(uuid);
    }

    /**
     * 获取在线玩家的数组对象
     *
     * @return 在线玩家数组
     */
    public static Player[] getOnlines() {

        Collection<? extends Player> collection = Bukkit.getServer().getOnlinePlayers();

        return collection.toArray(new Player[collection.size()]);
    }

    /**
     * 获取指定玩家的游戏简介
     *
     * @param player 玩家
     * @return 游戏简介 异常则返回 null
     */
    public static GameProfile getProfile(Player player) {

        GameProfile profile = null;

        try {

            Class<?> CraftPlayer = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            Class<?> EntityPlayer = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            Class<?> EntityHuman = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityHuman");

            Method getHandle = Reflect.getMethod(CraftPlayer, "getHandle");
            Object NMSPlayer = getHandle.invoke(player);

            Method getProfile = Reflect.getMethod(EntityHuman, "getProfile");
            return (GameProfile) getProfile.invoke(NMSPlayer);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return profile;
    }
}
