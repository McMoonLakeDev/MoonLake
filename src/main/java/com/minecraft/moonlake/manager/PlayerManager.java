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
import com.minecraft.moonlake.api.player.CachedMoonLakePlayer;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.validate.Validate;
import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

/**
 * <h1>PlayerManager</h1>
 * 玩家管理实现类
 *
 * @version 1.1
 * @author Month_Light
 */
public class PlayerManager extends MoonLakeManager {

    static {
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

        MoonLakePlayer[]  resultArray = getCacheOnlines();
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
     * 从缓存月色之湖玩家缓冲器内获取指定月色之湖玩家对象
     *
     * @param name 玩家名
     * @return MoonLakePlayer
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     * @see CachedMoonLakePlayer#getCache(String)
     */
    public static MoonLakePlayer getCache(String name) throws PlayerNotOnlineException {
        return CachedMoonLakePlayer.getInstance().getCache(name);
    }

    /**
     * 从缓存月色之湖玩家缓冲器内获取指定月色之湖玩家对象
     *
     * @param player 玩家
     * @return MoonLakePlayer
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public static MoonLakePlayer getCache(Player player) {
        return CachedMoonLakePlayer.getInstance().getCache(player);
    }

    /**
     * 从缓存月色之湖玩家缓冲器内获取指定月色之湖玩家对象
     *
     * @param players 玩家
     * @return MoonLakePlayer[]
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public static MoonLakePlayer[] getCache(Player... players) {
        MoonLakePlayer[] cache = new MoonLakePlayer[players.length];
        int index = 0;
        for(Player player : players)
            cache[index++] = CachedMoonLakePlayer.getInstance().getCache(player);
        return cache;
    }

    /**
     * 从缓存月色之湖玩家缓冲器内获取在线月色之湖玩家数组对象
     *
     * @return MoonLakePlayer[]
     */
    public static MoonLakePlayer[] getCacheOnlines() {
        return CachedMoonLakePlayer.getInstance().getCacheOnlines();
    }

    /**
     * 将 Bukkit 玩家对象转换到 MoonLake 玩家对象
     *
     * @param players Bukkit 玩家
     * @return MoonLake 玩家
     * @throws IllegalArgumentException 如果 Bukkit 玩家对象为 {@code null} 则抛出异常
     * @throws IllegalBukkitVersionException 如果 Bukkit 服务器版本不支持则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #getCache(Player...)}
     * @see #getCache(Player...)
     */
    @Deprecated
    public static MoonLakePlayer[] adapter(Player... players) {

        Validate.notNull(players, "The player object is null.");

        return getCache(players);
    }

    /**
     * 将 Bukkit 玩家对象转换到 MoonLake 玩家对象
     *
     * @param player Bukkit 玩家
     * @return MoonLake 玩家
     * @throws IllegalArgumentException 如果 Bukkit 玩家对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #getCache(Player)}
     * @see #getCache(Player)
     */
    @Deprecated
    public static MoonLakePlayer adapter(Player player) {

        Validate.notNull(player, "The player object is null.");

        return getCache(player);
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

            return MinecraftReflection.getPlayerPing(player);
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
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link MinecraftReflection#getEntityHumanProfile(HumanEntity)}
     */
    @Deprecated
    public static GameProfile getProfile(Player player) { // TODO 2.0

        Validate.notNull(player, "The player object is null.");

        try {

            return MinecraftReflection.getEntityHumanProfile(player);
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
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link MinecraftReflection#getPlayerLocale(Player)}
     */
    @Deprecated
    public static String getLanguage(Player player) { // TODO 2.0

        Validate.notNull(player, "The player object is null.");

        try {

            return MinecraftReflection.getPlayerLocale(player);
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
     * @see MinecraftReflection#setItemCooldown(HumanEntity, Material, int)
     */
    public static void setItemCoolDown(Player player, Material material, int tick) throws IllegalBukkitVersionException {
        MinecraftReflection.setItemCooldown(player, material, tick);
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
     * @see MinecraftReflection#hasItemCooldown(HumanEntity, Material)
     */
    public static boolean hasItemCoolDown(Player player, Material material) throws IllegalBukkitVersionException {
        return MinecraftReflection.hasItemCooldown(player, material);
    }

    /**
     * 获取指定玩家物品栈类型拥有的冷却时间
     *
     * @param player 玩家名
     * @param material 物品栈类型
     * @return 物品栈类型拥有的冷却时间
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws IllegalBukkitVersionException 如果服务器 Bukkit 版本不支持则抛出异常
     * @see MinecraftReflection#getItemCooldown(HumanEntity, Material)
     */
    public static int getItemCoolDown(Player player, Material material) throws IllegalBukkitVersionException {
        return MinecraftReflection.getItemCooldown(player, material);
    }
}
