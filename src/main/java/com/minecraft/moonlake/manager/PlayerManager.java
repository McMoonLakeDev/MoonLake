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
 
 
package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.player.*;
import com.minecraft.moonlake.api.utility.MinecraftBukkitVersion;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.validate.Validate;
import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PlayerManager</h1>
 * 玩家管理实现类
 *
 * @version 1.0.1
 * @author Month_Light
 */
public class PlayerManager extends MoonLakeManager {

    private final static Class<? extends SimpleMoonLakePlayer> CLASS_SIMPLEMOONLAKEPLAYER;
    private final static Class<?> CLASS_CRAFTMAGICNUMBERS;
    private final static Class<?> CLASS_ITEMCOOLDOWN;
    private final static Class<?> CLASS_ITEM;
    private final static Class<?> CLASS_CRAFTPLAYER;
    private final static Class<?> CLASS_ENTITYHUMAN;
    private final static Class<?> CLASS_ENTITYPLAYER;
    private final static Constructor<? extends SimpleMoonLakePlayer> CONSTRUCTOR_SIMPLEMOONLAKEPLAYER;
    private final static Method METHOD_GETHANDLE;
    private final static Method METHOD_GETPROFILE;
    private final static Method METHOD_GETITEM;
    private final static Method METHOD_TARGET;
    private final static Method METHOD_A0;
    private final static Method METHOD_A1;
    private final static Field FIELD_LOCALE;
    private final static Field FIELD_PING;

    static {

        try {

            MinecraftVersion mcVersion = MoonLakeAPI.currentMCVersion();

            if(mcVersion == null) // Not Support
                CLASS_SIMPLEMOONLAKEPLAYER = null;
            else if(mcVersion.equalsMinor(MinecraftVersion.V1_8)) // Bukkit 1.8
                CLASS_SIMPLEMOONLAKEPLAYER = SimpleMoonLakePlayer_v1_8.class;
            else if(mcVersion.equalsMinor(MinecraftVersion.V1_9)) // Bukkit 1.9
                CLASS_SIMPLEMOONLAKEPLAYER = SimpleMoonLakePlayer_v1_9.class;
            else if(mcVersion.equalsMinor(MinecraftVersion.V1_10)) // Bukkit 1.10
                CLASS_SIMPLEMOONLAKEPLAYER = SimpleMoonLakePlayer_v1_10.class;
            else if(mcVersion.equalsMinor(MinecraftVersion.V1_11)) // Bukkit 1.11
                CLASS_SIMPLEMOONLAKEPLAYER = SimpleMoonLakePlayer_v1_11.class;
            else // Not Support
                CLASS_SIMPLEMOONLAKEPLAYER = null;

            if(CLASS_SIMPLEMOONLAKEPLAYER != null)
                CONSTRUCTOR_SIMPLEMOONLAKEPLAYER = CLASS_SIMPLEMOONLAKEPLAYER.getConstructor(Player.class);
            else
                CONSTRUCTOR_SIMPLEMOONLAKEPLAYER = null;

            CLASS_CRAFTPLAYER = PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            CLASS_ENTITYHUMAN = PackageType.MINECRAFT_SERVER.getClass("EntityHuman");
            CLASS_ENTITYPLAYER = PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            METHOD_GETHANDLE = getMethod(CLASS_CRAFTPLAYER, "getHandle");
            METHOD_GETPROFILE = getMethod(CLASS_ENTITYHUMAN, "getProfile");
            FIELD_LOCALE = getField(CLASS_ENTITYPLAYER, true, "locale");
            FIELD_PING = getField(CLASS_ENTITYPLAYER, true, "ping");

            if(MoonLakeAPI.currentBukkitVersionIsOrLater(MinecraftBukkitVersion.V1_9_R1)) {

                String name = null;

                // TODO 函数名十分不固定, 以后可以用模糊反射来获取
                switch (MoonLakeAPI.currentBukkitVersionString()) {
                    case "v1_9_R1":
                        name = "da"; break;
                    case "v1_9_R2":
                        name = "db"; break;
                    case "v1_10_R1":
                        name = "df"; break;
                    case "v1_11_R1":
                        name = "di"; break;
                    default:
                        name = "di"; break;
                }
                CLASS_ITEM = PackageType.MINECRAFT_SERVER.getClass("Item");
                CLASS_ITEMCOOLDOWN = PackageType.MINECRAFT_SERVER.getClass("ItemCooldown");
                CLASS_CRAFTMAGICNUMBERS = PackageType.CRAFTBUKKIT_UTIL.getClass("CraftMagicNumbers");
                METHOD_GETITEM = getMethod(CLASS_CRAFTMAGICNUMBERS, "getItem", Material.class);
                METHOD_TARGET = getMethod(CLASS_ENTITYHUMAN, name);
                METHOD_A0 = getMethod(CLASS_ITEMCOOLDOWN, "a", CLASS_ITEM, int.class);
                METHOD_A1 = getMethod(CLASS_ITEMCOOLDOWN, "a", CLASS_ITEM);
            }
            else {
                CLASS_ITEM = null;
                CLASS_ITEMCOOLDOWN = null;
                CLASS_CRAFTMAGICNUMBERS = null;
                METHOD_GETITEM = null;
                METHOD_TARGET = null;
                METHOD_A0 = null;
                METHOD_A1 = null;
            }
        }
        catch (Exception e) {

            throw new MoonLakeException("The player manager reflect raw exception.", e);
        }
    }

    /**
     * 玩家管理实现类构造函数
     */
    private PlayerManager() {

    }

    /**
     * 获取指定玩家对象从目标名称
     *
     * @param name 玩家名
     * @return 玩家对象 没有则返回 null
     * @throws IllegalArgumentException 如果玩家名字对象为 {@code null} 则抛出异常
     */
    public static Player fromName(String name) {

        return Bukkit.getServer().getPlayer(name);
    }

    /**
     * 获取指定玩家对象从目标 UUID
     *
     * @param uuid 玩家 UUID
     * @return 玩家对象 没有则返回 null
     * @throws IllegalArgumentException 如果玩家 UUID 对象为 {@code null} 则抛出异常
     */
    public static Player fromUUID(UUID uuid) {

        return Bukkit.getServer().getPlayer(uuid);
    }

    /**
     * 获取指定离线玩家对象从目标名称
     *
     * @param name 玩家名
     * @return 离线玩家对象
     * @throws IllegalArgumentException 如果玩家名字对象为 {@code null} 则抛出异常
     * @deprecated
     * <p>用户的持久存储应该由 {@code UUID}, 因为名称在单个会话之后不再是唯一的.</p>
     * <p>该方法可能涉及阻塞服务器, 向 {@code Web} 发送请求以获取给定名称的 {@code UUID}.</p>
     * <p>即使玩家不存在, 也会返回一个对象. 对于这种方法, 所有玩家都会存在.</p>
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public static OfflinePlayer fromNameOffline(String name) {

        return Bukkit.getServer().getOfflinePlayer(name);
    }

    /**
     * 获取指定离线玩家对象从目标 UUID
     *
     * @param uuid 玩家 UUID
     * @return 离线玩家对象
     * @throws IllegalArgumentException 如果玩家 UUID 对象为 {@code null} 则抛出异常
     */
    public static OfflinePlayer fromUUIDOffline(UUID uuid) {

        return Bukkit.getServer().getOfflinePlayer(uuid);
    }

    /**
     * 获取在线玩家的集合对象
     *
     * @return 在线玩家集合
     */
    public static Collection<? extends Player> getOnlinePlayers() {

        return Bukkit.getServer().getOnlinePlayers();
    }

    /**
     * 获取离线玩家的集合对象
     *
     * @return 离线玩家集合
     */
    public static Collection<? extends OfflinePlayer> getOfflinePlayers() {

        return Arrays.asList(getOfflines());
    }

    /**
     * 获取在线月色之湖玩家的集合对象
     *
     * @return 在线月色之湖玩家集合
     */
    public static Collection<? extends MoonLakePlayer> getOnlineMoonLakePlayers() {

        Player[] rawArray = getOnlines();
        MoonLakePlayer[]  resultArray = adapter(rawArray);
        return Arrays.asList(resultArray);
    }

    /**
     * 获取在线玩家的数组对象
     *
     * @return 在线玩家数组
     */
    public static Player[] getOnlines() {

        Collection<? extends Player> collection = getOnlinePlayers();
        return collection.toArray(new Player[collection.size()]);
    }

    /**
     * 获取离线玩家的数组对象
     *
     * @return 离线玩家数组
     */
    public static OfflinePlayer[] getOfflines() {

        return Bukkit.getServer().getOfflinePlayers();
    }

    /**
     * 获取在线玩家的集合对象除了目标玩家
     *
     * @param target 目标玩家
     * @return 在线玩家集合除了目标玩家
     * @throws IllegalArgumentException 如果目标玩家对象为 {@code null} 则抛出异常
     */
    public static Collection<? extends Player> getOnlinePlayersExceptTarget(Player target) {

        Validate.notNull(target, "The player target object is null.");

        Collection<? extends Player> collection = getOnlinePlayers();
        Iterator<? extends Player> iterator = collection.iterator();

        while(iterator.hasNext())
            if(iterator.next().equals(target))
                iterator.remove();

        return collection;
    }

    /**
     * 获取在线月色之湖玩家的集合对象除了目标玩家
     *
     * @param target 目标玩家
     * @return 在线月色之湖玩家集合除了目标玩家
     * @throws IllegalArgumentException 如果目标玩家对象为 {@code null} 则抛出异常
     */
    public static Collection<? extends MoonLakePlayer> getOnlineMoonLakePlayersExceptTarget(MoonLakePlayer target) {

        Validate.notNull(target, "The player target object is null.");
        return getOnlineMoonLakePlayersExceptTarget(target.getBukkitPlayer());
    }

    /**
     * 获取在线月色之湖玩家的集合对象除了目标玩家
     *
     * @param target 目标玩家
     * @return 在线月色之湖玩家集合除了目标玩家
     * @throws IllegalArgumentException 如果目标玩家对象为 {@code null} 则抛出异常
     */
    public static Collection<? extends MoonLakePlayer> getOnlineMoonLakePlayersExceptTarget(Player target) {

        Validate.notNull(target, "The player target object is null.");

        Collection<? extends MoonLakePlayer> collection = getOnlineMoonLakePlayers();
        Iterator<? extends MoonLakePlayer> iterator = collection.iterator();

        while(iterator.hasNext())
            if(iterator.next().equals(target))
                iterator.remove();

        return collection;
    }

    /**
     * 获取在线玩家的数组对象除了目标玩家
     *
     * @param target 目标玩家
     * @return 在线玩家数组除了目标玩家
     * @throws IllegalArgumentException 如果目标玩家对象为 {@code null} 则抛出异常
     */
    public static Player[] getOnlinesExceptTarget(Player target) {

        Collection<? extends Player> collection = getOnlinePlayersExceptTarget(target);
        return collection.toArray(new Player[collection.size()]);
    }

    /**
     * 将字符串玩家对象转换到 Bukkit 玩家对象
     *
     * @param players 字符串 玩家
     * @return Bukkit 玩家
     * @throws IllegalArgumentException 如果字符串玩家对象为 {@code null} 则抛出异常
     */
    public static Player[] adapter(String... players) {

        Validate.notNull(players, "The player object is null.");

        int index = 0;
        Player[] adapter = new Player[players.length];

        for(final String player : players) {

            adapter[index++] = fromName(player);
        }
        return adapter;
    }

    /**
     * 将字符串玩家对象转换到 Bukkit 玩家对象
     *
     * @param player 字符串 玩家
     * @return Bukkit 玩家
     * @throws IllegalArgumentException 如果字符串玩家对象为 {@code null} 则抛出异常
     */
    public static Player adapter(String player) {

        Validate.notNull(player, "The player object is null.");

        return fromName(player);
    }

    /**
     * 将 Bukkit 玩家对象转换到 MoonLake 玩家对象
     *
     * @param players Bukkit 玩家
     * @return MoonLake 玩家
     * @throws IllegalArgumentException 如果 Bukkit 玩家对象为 {@code null} 则抛出异常
     * @throws IllegalBukkitVersionException 如果 Bukkit 服务器版本不支持则抛出异常
     */
    public static MoonLakePlayer[] adapter(Player... players) {

        Validate.notNull(players, "The player object is null.");

        //
        // 验证类是否为 null 则抛出非法 Bukkit 版本异常

        if(CLASS_SIMPLEMOONLAKEPLAYER == null) {

            throw new IllegalBukkitVersionException("The moonlake player class not support bukkit version.");
        }
        ///

        int index = 0;
        MoonLakePlayer[] adapter = new MoonLakePlayer[players.length];

        try {

            for(final Player player : players) {

                adapter[index++] = CONSTRUCTOR_SIMPLEMOONLAKEPLAYER.newInstance(player);
            }
        }
        catch (Exception e) {

            throw new MoonLakeException("The adapter player to moonlake player exception.", e);
        }
        return adapter;
    }

    /**
     * 将 Bukkit 玩家对象转换到 MoonLake 玩家对象
     *
     * @param player Bukkit 玩家
     * @return MoonLake 玩家
     * @throws IllegalArgumentException 如果 Bukkit 玩家对象为 {@code null} 则抛出异常
     */
    public static MoonLakePlayer adapter(Player player) {

        Validate.notNull(player, "The player object is null.");

        return adapter(new Player[] { player })[0];
    }

    /**
     * 将 MoonLake 玩家对象转换到 Bukkit 玩家对象
     *
     * @param players MoonLake 玩家
     * @return Bukkit 玩家
     * @throws IllegalArgumentException 如果 MoonLake 玩家对象为 {@code null} 则抛出异常
     */
    public static Player[] adapter(MoonLakePlayer... players) {

        Validate.notNull(players, "The player object is null.");

        int index = 0;
        Player[] adapter = new Player[players.length];

        for(final MoonLakePlayer player : players) {

            adapter[index++] = player.getBukkitPlayer();
        }
        return adapter;
    }

    /**
     * 将 MoonLake 玩家对象转换到 Bukkit 玩家对象
     *
     * @param player MoonLake 玩家
     * @return Bukkit 玩家
     * @throws IllegalArgumentException 如果 MoonLake 玩家对象为 {@code null} 则抛出异常
     */
    public static Player adapter(MoonLakePlayer player) {

        Validate.notNull(player, "The player object is null.");

        return adapter(new MoonLakePlayer[] { player })[0];
    }

    /**
     * 获取指定玩家的网络 Ping 值
     *
     * @param player 玩家
     * @return Ping 值
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public static int getPing(Player player) {

        Validate.notNull(player, "The player object is null.");

        try {

            Object nmsPlayer = METHOD_GETHANDLE.invoke(player);

            return (int) FIELD_PING.get(nmsPlayer);
        }
        catch (Exception e) {

            throw new MoonLakeException("The get player ping exception.", e);
        }
    }

    /**
     * 获取指定玩家的游戏简介
     *
     * @param player 玩家
     * @return 游戏简介 异常则返回 null
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public static GameProfile getProfile(Player player) {

        Validate.notNull(player, "The player object is null.");

        try {

            Object nmsPlayer = METHOD_GETHANDLE.invoke(player);

            return (GameProfile) METHOD_GETPROFILE.invoke(nmsPlayer);
        }
        catch (Exception e) {

            throw new MoonLakeException("The get player profile exception.", e);
        }
    }

    /**
     * 获取指定玩家的本地化语言
     *
     * @param player 玩家
     * @return 本地化语言 异常则返回 null
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public static String getLanguage(Player player) {

        Validate.notNull(player, "The player object is null.");

        try {

            Object nmsPlayer = METHOD_GETHANDLE.invoke(player);

            return (String) FIELD_LOCALE.get(nmsPlayer);
        }
        catch (Exception e) {

            throw new MoonLakeException("The get player language exception.", e);
        }
    }

    /**
     * 安全的更新指定玩家的物品栏
     *
     * @param player 玩家
     */
    public static void updateInventorySafe(Plugin plugin, final Player player) {

        Validate.notNull(plugin, "The plugin object is null");
        Validate.notNull(player, "The player object is null.");

        if(!MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9)) {

            MoonLakeAPI.runTaskLaterAsync(plugin, new Runnable() {

                @Override
                public void run() {

                    player.updateInventory();
                }
            }, 1L);
        }
        else {

            player.updateInventory();
        }
    }

    /**
     * 给指定玩家设置物品栈冷却时间
     *
     * @param player 玩家名
     * @param material 物品栈类型
     * @param tick 时间 Tick (1s = 20tick)
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws IllegalBukkitVersionException 如果服务器 Bukkit 版本不支持则抛出异常
     */
    public static void setItemCoolDown(Player player, Material material, int tick) throws IllegalBukkitVersionException {

        if(!MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9))
            throw new IllegalBukkitVersionException("The item cool down not support 1.8 or old bukkit version.");

        Validate.notNull(player, "The player object is null.");
        Validate.notNull(material, "The material object is null.");

        try {

            Object nmsPlayer = METHOD_GETHANDLE.invoke(player);
            Object nmsItemCooldown = METHOD_TARGET.invoke(nmsPlayer);
            METHOD_A0.invoke(nmsItemCooldown, METHOD_GETITEM.invoke(null, material), tick);
        }
        catch (Exception e) {

            throw new MoonLakeException("The set player '" + player.getName() + "' item cool down exception.", e);
        }
    }

    /**
     * 获取指定玩家物品栈类型是否拥有冷却时间
     *
     * @param player 玩家名
     * @param material 物品栈类型
     * @return true 则物品栈类型拥有冷却时间
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws IllegalBukkitVersionException 如果服务器 Bukkit 版本不支持则抛出异常
     */
    public static boolean hasItemCoolDown(Player player, Material material) throws IllegalBukkitVersionException {

        if(!MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9))
            throw new IllegalBukkitVersionException("The item cool down not support 1.8 or old bukkit version.");

        Validate.notNull(player, "The player object is null.");
        Validate.notNull(material, "The material object is null.");

        try {

            Object nmsPlayer = METHOD_GETHANDLE.invoke(player);
            Object nmsItemCooldown = METHOD_TARGET.invoke(nmsPlayer);
            return (boolean) METHOD_A1.invoke(nmsItemCooldown, METHOD_GETITEM.invoke(null, material));
        }
        catch (Exception e) {

            throw new MoonLakeException("The get player '" + player.getName() + "' has item cool down exception.", e);
        }
    }
}
