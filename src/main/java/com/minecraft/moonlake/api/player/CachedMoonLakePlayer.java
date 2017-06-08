/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.event.MoonLakeListener;
import com.minecraft.moonlake.api.utility.MoonLakeReflection;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.ref.cached.CachedReferenceQueue;
import com.minecraft.moonlake.ref.cached.CachedWeakRef;
import com.minecraft.moonlake.ref.cached.CachedWeakReference;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;

/**
 * <h1>CachedMoonLakePlayer</h1>
 * 缓存月色之湖玩家缓存器类
 *
 * @version 1.1.2
 * @author Month_Light
 * @see MoonLakePlayer
 * @see CachedWeakReference
 */
public final class CachedMoonLakePlayer extends CachedWeakReference<String, MoonLakePlayer> {

    private static CachedMoonLakePlayer instance;

    /**
     * 获取此缓存月色之湖玩家的缓冲器对象
     *
     * @return CachedMoonLakePlayer
     */
    public static CachedMoonLakePlayer getInstance() {
        if(instance == null)
            instance = new CachedMoonLakePlayer();
        return instance;
    }

    /**
     * 缓存月色之湖玩家缓存器类构造函数
     */
    private CachedMoonLakePlayer() {
        super();
        this.registerEventListener();
    }

    /**
     * 从缓存月色之湖玩家缓冲器内获取指定月色之湖玩家对象
     *
     * @param name 玩家名
     * @return MoonLakePlayer
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     * @see #getCache(Player)
     */
    @Override
    public MoonLakePlayer getCache(String name) {
        Validate.notNull(name, "The name object is null.");
        Player player = PlayerManager.fromName(name);
        if(player == null)
            throw new PlayerNotOnlineException(name); // 如果玩家不在线则抛出异常
        return getCache(player);
    }

    /**
     * 从缓存月色之湖玩家缓冲器内获取指定月色之湖玩家对象
     *
     * @param player 玩家
     * @return MoonLakePlayer
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public MoonLakePlayer getCache(Player player) {
        Validate.notNull(player, "The player object is null.");
        return super.getCache(player.getName());
    }

    /**
     * 从缓存月色之湖玩家缓冲器内获取在线月色之湖玩家数组对象
     *
     * @return MoonLakePlayer[]
     */
    public MoonLakePlayer[] getCacheOnlines() {
        Collection<? extends Player> players = PlayerManager.getOnlinePlayers();
        MoonLakePlayer[] array = new MoonLakePlayer[players.size()];
        int index = 0;
        for(Player player : players)
            array[index++] = getCache(player);
        return array;
    }

    /**
     * 注册玩家退出游戏事件监听器并清除缓存
     */
    private void registerEventListener() {
        if(MoonLakeAPI.getMoonLake().isEnabled()) {
            MoonLakeAPI.registerEvent(new MoonLakeListener() {
                @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
                public void onQuit(PlayerQuitEvent event) {
                    removeCache(event.getPlayer().getName());
                }
            }, MoonLakeAPI.getMoonLake());
        }
    }

    @Override
    protected MoonLakePlayer produceValue(String key) {
        Player player = PlayerManager.fromName(key);
        if(player == null)
            throw new PlayerNotOnlineException(key);
        return MoonLakeReflection.getSimpleMoonLakePlayerInstance(player);
    }

    @Override
    protected CachedWeakMoonLakePlayer produceRef(String key, MoonLakePlayer value, CachedReferenceQueue<MoonLakePlayer> queue) {
        return new CachedWeakMoonLakePlayer(key, value, queue);
    }

    private final static class CachedWeakMoonLakePlayer extends CachedWeakRef<String, MoonLakePlayer> {

        private CachedWeakMoonLakePlayer(String key, MoonLakePlayer referent, CachedReferenceQueue<? super MoonLakePlayer> queue) {
            super(key, referent, queue);
        }
    }
}
