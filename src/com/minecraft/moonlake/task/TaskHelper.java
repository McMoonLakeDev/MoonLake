package com.minecraft.moonlake.task;

import com.minecraft.moonlake.exception.MoonLakeException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * <h1>TaskHelper</h1>
 * 任务帮助器（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class TaskHelper {

    private final static BukkitScheduler BUKKIT_SCHEDULER;

    static {

        BUKKIT_SCHEDULER = Bukkit.getScheduler();
    }

    /**
     * 任务帮助器类构造函数
     */
    private TaskHelper() {

    }

    public static BukkitTask runTask(Plugin plugin, Runnable task) {

        return BUKKIT_SCHEDULER.runTask(plugin, task);
    }

    public static BukkitTask runTaskLater(Plugin plugin, Runnable task, long delay) {

        return BUKKIT_SCHEDULER.runTaskLater(plugin, task, delay);
    }

    public static BukkitTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period) {

        return BUKKIT_SCHEDULER.runTaskTimer(plugin, task, delay, period);
    }

    public static BukkitTask runTaskAsync(Plugin plugin, Runnable task) {

        return BUKKIT_SCHEDULER.runTaskAsynchronously(plugin, task);
    }

    public static BukkitTask runTaskLaterAsync(Plugin plugin, Runnable task, long delay) {

        return BUKKIT_SCHEDULER.runTaskLaterAsynchronously(plugin, task, delay);
    }

    public static BukkitTask runTaskTimerAsync(Plugin plugin, Runnable task, long delay, long period) {

        return BUKKIT_SCHEDULER.runTaskTimerAsynchronously(plugin, task, delay, period);
    }

    public static BukkitTask runTask(Plugin plugin, MoonLakeRunnable task) {

        return runTask(plugin, (Runnable) task);
    }

    public static BukkitTask runTaskLater(Plugin plugin, MoonLakeRunnable task, long delay) {

        return runTaskLater(plugin, (Runnable) task, delay);
    }

    public static BukkitTask runTaskTimer(Plugin plugin, MoonLakeRunnable task, long delay, long period) {

        return runTaskTimer(plugin, (Runnable) task, delay, period);
    }

    public static BukkitTask runTaskAsync(Plugin plugin, MoonLakeRunnable task) {

        return runTaskAsync(plugin, (Runnable) task);
    }

    public static BukkitTask runTaskLaterAsync(Plugin plugin, MoonLakeRunnable task, long delay) {

        return runTaskLaterAsync(plugin, (Runnable) task, delay);
    }

    public static BukkitTask runTaskTimerAsync(Plugin plugin, MoonLakeRunnable task, long delay, long period) {

        return runTaskTimerAsync(plugin, (Runnable) task, delay, period);
    }

    public static <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task) {

        return BUKKIT_SCHEDULER.callSyncMethod(plugin, task);
    }

    public static <T> T callFuture(Plugin plugin, Callable<T> task) throws MoonLakeException {

        return callFuture(callSyncMethod(plugin, task));
    }

    public static <T> T callFuture(Future<T> future) throws MoonLakeException {

        try {

            return future.get();
        }
        catch (Exception e) {

            throw new MoonLakeException("The get future value exception.", e);
        }
    }

    public static void cancelTask(BukkitTask task) {

        if(task != null)
            task.cancel();
    }

    public static void cancelTask(int taskId) {

        BUKKIT_SCHEDULER.cancelTask(taskId);
    }

    public static void cancelTasks(Plugin plugin) {

        BUKKIT_SCHEDULER.cancelTasks(plugin);
    }

    public static void cancelAllTasks() {

        BUKKIT_SCHEDULER.cancelAllTasks();
    }
}
