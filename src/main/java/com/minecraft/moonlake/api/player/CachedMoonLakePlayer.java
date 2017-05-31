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
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>CachedMoonLakePlayer</h1>
 * 缓存月色之湖玩家缓存器类
 *
 * @version 1.0
 * @author Month_Light
 * @see WeakReference
 * @see ReferenceQueue
 * @see MoonLakePlayer
 */
public final class CachedMoonLakePlayer {

    private static CachedMoonLakePlayer instance;
    private ReferenceQueue<MoonLakePlayer> queue;
    private Map<String, WeakMoonLakePlayer> cacheMap;

    static {
    }

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
        this.queue = new ReferenceQueue<>();
        this.cacheMap = new HashMap<>();
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
        final String name = Validate.checkNotNull(player).getName();
        MoonLakePlayer value = null;
        if(cacheMap.containsKey(name)) {
            WeakMoonLakePlayer ref = cacheMap.get(name);
            value = ref.get();
        }
        if(value == null) {
            value = MoonLakeReflection.getSimpleMoonLakePlayerInstance(player);
            //MoonLakeAPI.getLogger().info("月色之湖玩家'" + player.getName() + "'的对象已经被创建."); // debug
            cache(value);
        }
        return value;
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
     * 将指定月色之湖玩家对象加入到缓冲器内
     *
     * @param player 月色之湖玩家
     */
    private void cache(MoonLakePlayer player) {
        cleanCache();
        WeakMoonLakePlayer ref = new WeakMoonLakePlayer(player, queue);
        cacheMap.put(player.getName(), ref);
    }

    /**
     * 清理已经被加入到引用队列的月色之湖玩家弱引用对象
     */
    private void cleanCache() {
        WeakMoonLakePlayer ref;
        while ((ref = (WeakMoonLakePlayer) queue.poll()) != null) {
            cacheMap.remove(ref.key);
            //MoonLakeAPI.getLogger().info("月色之湖玩家'" + ref.key + "'的对象已被 JVM 回收."); // debug
        }
    }

    /**
     * 清理已经被加入到引用队列的指定月色之湖玩家弱引用对象
     *
     * @param name 玩家名
     */
    private void cleanCache(String name) {
        if(cacheMap.containsKey(name))
            cacheMap.remove(name);
        cleanCache();
    }

    /**
     * 注册玩家退出游戏事件监听器并清除缓存
     */
    private void registerEventListener() {
        MoonLakeAPI.registerEvent(new MoonLakeListener() {
            @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
            public void onQuit(PlayerQuitEvent event) {
                cleanCache(event.getPlayer().getName());
            }
        }, MoonLakeAPI.getMoonLake());
    }

    /**
     * 清除此缓冲器的所有缓存数据
     */
    public void clearCache() {
        cleanCache();
        cacheMap.clear();
    }

    /**
     * <h1>WeakMoonLakePlayer</h1>
     * 弱月色之湖玩家类
     *
     * @version 1.0
     * @author Month_Light
     * @see WeakReference
     * @see MoonLakePlayer
     */
    private final static class WeakMoonLakePlayer extends WeakReference<MoonLakePlayer> {

        /**
         * 存储月色之湖玩家引用的玩家名, 在缓存器中用于 Map 的键作用
         */
        private String key;

        /**
         * 弱月色之湖玩家类构造函数
         *
         * @param referent 月色之湖玩家引用
         * @param queue 引用队列
         */
        private WeakMoonLakePlayer(MoonLakePlayer referent, ReferenceQueue<? super MoonLakePlayer> queue) {
            super(referent, queue);
            this.key = referent.getName();
        }
    }
}
