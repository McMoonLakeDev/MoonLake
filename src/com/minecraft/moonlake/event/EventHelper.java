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


package com.minecraft.moonlake.event;

import com.minecraft.moonlake.api.event.MoonLakeEvent;
import com.minecraft.moonlake.api.event.MoonLakeListener;
import com.minecraft.moonlake.task.TaskHelper;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

/**
 * <h1>EventHelper</h1>
 * 事件帮助器（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public final class EventHelper {

    /**
     * 事件帮助器类构造函数
     */
    private EventHelper() {
    }

    /**
     * 调用事件帮助器触发指定事件
     *
     * @param event 事件
     */
    public static void callEvent(Event event) {

        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    /**
     * 调用事件帮助器触发指定事件
     *
     * @param event 事件
     */
    public static void callEvent(MoonLakeEvent event) {

        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    /**
     * 调用事件帮助器异步触发指定事件
     *
     * @param plugin 插件
     * @param event 事件
     */
    public static void callEventAsync(Plugin plugin, Event event) {

        TaskHelper.runTaskAsync(plugin, new Runnable() {

            @Override
            public void run() {

                callEvent(event);
            }
        });
    }

    /**
     * 调用事件帮助器异步触发指定事件
     *
     * @param plugin 插件
     * @param event 事件
     */
    public static void callEventAsync(Plugin plugin, MoonLakeEvent event) {

        TaskHelper.runTaskAsync(plugin, new Runnable() {

            @Override
            public void run() {

                callEvent(event);
            }
        });
    }

    /**
     * 调用事件帮助器注册事件监听器
     *
     * @param listener 监听器
     * @param plugin 插件
     */
    public static void registerEvent(Listener listener, Plugin plugin) {

        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    /**
     * 调用事件帮助器注册事件监听器
     *
     * @param listener 监听器
     * @param plugin 插件
     */
    public static void registerEvent(MoonLakeListener listener, Plugin plugin) {

        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    /**
     * 调用事件帮助器注册事件监听器
     *
     * @param event 事件类
     * @param listener 监听器
     * @param priority 事件优先度
     * @param executor 事件执行器
     * @param plugin 插件
     */
    public static void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin) {

        Bukkit.getServer().getPluginManager().registerEvent(event, listener, priority, executor, plugin);
    }

    /**
     * 调用事件帮助器注册事件监听器
     *
     * @param event 事件类
     * @param listener 监听器
     * @param priority 事件优先度
     * @param executor 事件执行器
     * @param plugin 插件
     * @param ignoreCancelled 是否忽略阻止
     */
    public static void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin, boolean ignoreCancelled) {

        Bukkit.getServer().getPluginManager().registerEvent(event, listener, priority, executor, plugin, ignoreCancelled);
    }

    /**
     * 调用事件帮助器注册事件监听器
     *
     * @param event 事件类
     * @param listener 监听器
     * @param priority 事件优先度
     * @param executor 事件执行器
     * @param plugin 插件
     */
    public static void registerEvent(Class<? extends MoonLakeEvent> event, MoonLakeListener listener, EventPriority priority, EventExecutor executor, Plugin plugin) {

        Bukkit.getServer().getPluginManager().registerEvent(event, listener, priority, executor, plugin);
    }

    /**
     * 调用事件帮助器注册事件监听器
     *
     * @param event 事件类
     * @param listener 监听器
     * @param priority 事件优先度
     * @param executor 事件执行器
     * @param plugin 插件
     * @param ignoreCancelled 是否忽略阻止
     */
    public static void registerEvent(Class<? extends MoonLakeEvent> event, MoonLakeListener listener, EventPriority priority, EventExecutor executor, Plugin plugin, boolean ignoreCancelled) {

        Bukkit.getServer().getPluginManager().registerEvent(event, listener, priority, executor, plugin, ignoreCancelled);
    }

    /**
     * 调用事件帮助器卸载全部事件监听器
     */
    public static void unregisterAll() {

        HandlerList.unregisterAll();
    }

    /**
     * 调用事件帮助器卸载指定插件的全部事件监听器
     *
     * @param plugin 插件
     */
    public static void unregisterAll(Plugin plugin) {

        HandlerList.unregisterAll(plugin);
    }

    /**
     * 调用事件帮助器卸载指定事件监听器
     *
     * @param listener 监听器
     */
    public static void unregisterAll(Listener listener) {

        HandlerList.unregisterAll(listener);
    }

    /**
     * 调用事件帮助器卸载指定事件监听器
     *
     * @param listener 监听器
     */
    public static void unregisterAll(MoonLakeListener listener) {

        HandlerList.unregisterAll(listener);
    }
}
