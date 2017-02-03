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
 
 
package com.minecraft.moonlake.task;

import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.executor.Consumer;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * <h1>TaskHelper</h1>
 * 任务帮助器（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public final class TaskHelper {

    private final static BukkitScheduler BUKKIT_SCHEDULER;

    static {

        BUKKIT_SCHEDULER = Bukkit.getScheduler();
    }

    /**
     * 任务帮助器类构造函数
     */
    private TaskHelper() {

    }

    /**
     * 调用任务帮助器运行任务
     *
     * @param plugin 插件
     * @param task 任务
     * @return BukkitTask
     */
    public static BukkitTask runTask(Plugin plugin, Runnable task) {

        return BUKKIT_SCHEDULER.runTask(plugin, task);
    }

    /**
     * 调用任务帮助器运行延迟任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @return BukkitTask
     */
    public static BukkitTask runTaskLater(Plugin plugin, Runnable task, long delay) {

        return BUKKIT_SCHEDULER.runTaskLater(plugin, task, delay);
    }

    /**
     * 调用任务帮助器运行定时器任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @param period 周期
     * @return BukkitTask
     */
    public static BukkitTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period) {

        return BUKKIT_SCHEDULER.runTaskTimer(plugin, task, delay, period);
    }

    /**
     * 调用任务帮助器运行异步任务
     *
     * @param plugin 插件
     * @param task 任务
     * @return BukkitTask
     */
    public static BukkitTask runTaskAsync(Plugin plugin, Runnable task) {

        return BUKKIT_SCHEDULER.runTaskAsynchronously(plugin, task);
    }

    /**
     * 调用任务帮助器运行异步延迟任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @return BukkitTask
     */
    public static BukkitTask runTaskLaterAsync(Plugin plugin, Runnable task, long delay) {

        return BUKKIT_SCHEDULER.runTaskLaterAsynchronously(plugin, task, delay);
    }

    /**
     * 调用任务帮助器运行异步定时器任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @param period 周期
     * @return BukkitTask
     */
    public static BukkitTask runTaskTimerAsync(Plugin plugin, Runnable task, long delay, long period) {

        return BUKKIT_SCHEDULER.runTaskTimerAsynchronously(plugin, task, delay, period);
    }

    /**
     * 调用任务帮助器运行任务
     *
     * @param plugin 插件
     * @param task 任务
     * @return BukkitTask
     */
    public static BukkitTask runTask(Plugin plugin, MoonLakeRunnable task) {

        return runTask(plugin, (Runnable) task);
    }

    /**
     * 调用任务帮助器运行延迟任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @return BukkitTask
     */
    public static BukkitTask runTaskLater(Plugin plugin, MoonLakeRunnable task, long delay) {

        return runTaskLater(plugin, (Runnable) task, delay);
    }

    /**
     * 调用任务帮助器运行定时器任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @param period 周期
     * @return BukkitTask
     */
    public static BukkitTask runTaskTimer(Plugin plugin, MoonLakeRunnable task, long delay, long period) {

        return runTaskTimer(plugin, (Runnable) task, delay, period);
    }

    /**
     * 调用任务帮助器运行异步任务
     *
     * @param plugin 插件
     * @param task 任务
     * @return BukkitTask
     */
    public static BukkitTask runTaskAsync(Plugin plugin, MoonLakeRunnable task) {

        return runTaskAsync(plugin, (Runnable) task);
    }

    /**
     * 调用任务帮助器运行异步延迟任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @return BukkitTask
     */
    public static BukkitTask runTaskLaterAsync(Plugin plugin, MoonLakeRunnable task, long delay) {

        return runTaskLaterAsync(plugin, (Runnable) task, delay);
    }

    /**
     * 调用任务帮助器运行异步定时器任务
     *
     * @param plugin 插件
     * @param task 任务
     * @param delay 延迟
     * @param period 周期
     * @return BukkitTask
     */
    public static BukkitTask runTaskTimerAsync(Plugin plugin, MoonLakeRunnable task, long delay, long period) {

        return runTaskTimerAsync(plugin, (Runnable) task, delay, period);
    }

    /**
     * 调用任务帮助器触发异步函数
     *
     * @param plugin 插件
     * @param task 任务
     * @param <T> 类型
     * @return Future
     */
    public static <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task) {

        return BUKKIT_SCHEDULER.callSyncMethod(plugin, task);
    }

    /**
     * 调用任务帮助器触发 Future
     *
     * @param plugin 插件
     * @param task 任务
     * @param <T> 类型
     * @return 类型值
     * @throws MoonLakeException 如果触发错误则抛出异常
     */
    public static <T> T callFuture(Plugin plugin, Callable<T> task) throws MoonLakeException {

        return callFuture(callSyncMethod(plugin, task));
    }

    /**
     * 调用任务帮助器触发 Future
     *
     * @param future Future
     * @param <T> 类型
     * @return 类型值
     * @throws MoonLakeException 如果触发错误则抛出异常
     */
    public static <T> T callFuture(Future<T> future) throws MoonLakeException {

        try {

            return future.get();
        }
        catch (Exception e) {

            throw new MoonLakeException("The get future value exception.", e);
        }
    }

    /**
     * 调用同步任务来获取回调并给予消费者
     *
     * @param plugin 插件
     * @param callback 回调
     * @param consumer 消费者
     * @param <T> 类型
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果回调对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果运行时错误则抛出异常
     */
    public static <T> void callBackSyncConsumer(Plugin plugin, Callable<T> callback, Consumer<T> consumer) {

        callBackTaskConsumer0(plugin, callback, consumer, null, false);
    }

    /**
     * 调用同步任务来延迟获取回调并给予消费者
     *
     * @param plugin 插件
     * @param callback 回调
     * @param consumer 消费者
     * @param <T> 类型
     * @param delay 延迟
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果回调对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果运行时错误则抛出异常
     */
    public static <T> void callBackSyncConsumer(Plugin plugin, Callable<T> callback, Consumer<T> consumer, long delay) {

        callBackTaskConsumer0(plugin, callback, consumer, delay, false);
    }

    /**
     * 调用异步任务来获取回调并给予消费者
     *
     * @param plugin 插件
     * @param callback 回调
     * @param consumer 消费者
     * @param <T> 类型
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果回调对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果运行时错误则抛出异常
     */
    public static <T> void callBackAsyncConsumer(Plugin plugin, Callable<T> callback, Consumer<T> consumer) {

        callBackTaskConsumer0(plugin, callback, consumer, null, true);
    }

    /**
     * 调用异步任务来延迟获取回调并给予消费者
     *
     * @param plugin 插件
     * @param callback 回调
     * @param consumer 消费者
     * @param <T> 类型
     * @param delay 延迟
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果回调对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果运行时错误则抛出异常
     */
    public static <T> void callBackAsyncConsumer(Plugin plugin, Callable<T> callback, Consumer<T> consumer, long delay) {

        callBackTaskConsumer0(plugin, callback, consumer, delay, true);
    }

    private static <T> void callBackTaskConsumer0(Plugin plugin, final Callable<T> callback, final Consumer<T> consumer, @Nullable Long delay, boolean async) {

        Validate.notNull(plugin, "The plugin object is null.");
        Validate.notNull(callback, "The callback object is null.");
        Validate.notNull(consumer, "The consumer object is null.");

        final FutureTask<T> futureTask = new FutureTask<>(callback);
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    futureTask.run();
                    consumer.accept(futureTask.get());
                } catch (Exception e) {
                    throw new MoonLakeException(e.getMessage(), e);
                }
            }
        };

        if (delay == null) {
            if (async)
                runTaskAsync(plugin, runnable);
            else
                runTask(plugin, runnable);
        }
        else {
            if(async)
                runTaskLaterAsync(plugin, runnable, delay);
            else
                runTaskLater(plugin, runnable, delay);
        }
    }

    /**
     * 调用任务帮助器关闭指定任务
     *
     * @param task 任务
     */
    public static void cancelTask(BukkitTask task) {

        if(task != null)
            task.cancel();
    }

    /**
     * 调用任务帮助器关闭指定任务
     *
     * @param taskId 任务 Id
     */
    public static void cancelTask(int taskId) {

        BUKKIT_SCHEDULER.cancelTask(taskId);
    }

    /**
     * 调用任务帮助器关闭指定插件的所有任务
     *
     * @param plugin 插件
     */
    public static void cancelTasks(Plugin plugin) {

        BUKKIT_SCHEDULER.cancelTasks(plugin);
    }

    /**
     * 调用任务帮助器关闭所有任务
     */
    public static void cancelAllTasks() {

        BUKKIT_SCHEDULER.cancelAllTasks();
    }
}
