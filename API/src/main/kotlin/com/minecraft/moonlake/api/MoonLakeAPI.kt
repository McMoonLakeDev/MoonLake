/*
 * Copyright (C) 2016-2017 The MoonLake (mcmoonlake@hotmail.com)
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

@file:JvmName("MoonLakeAPI")

package com.minecraft.moonlake.api

import com.minecraft.moonlake.api.event.MoonLakeEvent
import com.minecraft.moonlake.api.event.MoonLakeListener
import com.minecraft.moonlake.api.exception.MoonLakeException
import com.minecraft.moonlake.api.funs.Consumer
import com.minecraft.moonlake.api.player.MoonLakePlayer
import com.minecraft.moonlake.api.player.MoonLakePlayerCached
import com.minecraft.moonlake.api.reflect.Reflect
import com.minecraft.moonlake.api.task.MoonLakeRunnable
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.FutureTask

/** MoonLake API Extended Function */

private var moonlake: MoonLake? = null

@Throws(IllegalStateException::class)
fun setMoonLake(obj: MoonLake) {
    if(!Reflect.callerClassLastEquals(MoonLake.MAIN))
        throw IllegalStateException("当前函数只有 '${MoonLake.MAIN}' 类拥有访问权限.")
    moonlake = obj
}

fun getMoonLake(): MoonLake
        = moonlake.notNull()

/** extended function */

fun <T> T?.notNull(message: String = "验证的对象值为 null 时异常."): T {
    if(this == null)
        throw IllegalArgumentException(message)
    return this
}

fun <T, C: Comparable<T>> C.isLater(other: T): Boolean
        = compareTo(other) > 0

fun <T, C: Comparable<T>> C.isOrLater(other: T): Boolean
        = compareTo(other) >= 0

fun <T, C: Comparable<T>> C.isRange(min: T, max: T): Boolean
        = compareTo(min) > 0 && compareTo(max) < 0

fun <T, C: Comparable<T>> C.isOrRange(min: T, max: T): Boolean
        = compareTo(min) >= 0 && compareTo(max) <= 0

fun String.toColor(): String
        = ChatColor.translateAlternateColorCodes('\u0026', this)

fun String.toColor(altColorChar: Char): String
        = ChatColor.translateAlternateColorCodes(altColorChar, this)

fun Array<out String>.toColor(): Array<out String>
        = this.clone().toList().map { it -> it.toColor() }.toTypedArray()

fun Array<out String>.toColor(altColorChar: Char): Array<out String>
        = this.clone().toList().map { it -> it.toColor(altColorChar) }.toTypedArray()

fun String.toBukkitWorld(): World?
        = Bukkit.getWorld(this)

fun UUID.toPlayer(): Player?
        = Bukkit.getPlayer(this)

fun Player.toMoonLakePlayer(): MoonLakePlayer
        = MoonLakePlayerCached.instance().getCache(this)

/** event function */

fun Event.callEvent()
        = Bukkit.getServer().pluginManager.callEvent(this)

fun Event.callEventAsync(plugin: Plugin)
        = runTaskAsync(plugin, Runnable { Bukkit.getServer().pluginManager.callEvent(this) })

fun MoonLakeEvent.callEvent()
        = Bukkit.getServer().pluginManager.callEvent(this)

fun MoonLakeEvent.callEventAsync(plugin: Plugin)
        = runTaskAsync(plugin, Runnable { Bukkit.getServer().pluginManager.callEvent(this) })

fun Listener.registerEvent(plugin: Plugin)
        = Bukkit.getServer().pluginManager.registerEvents(this, plugin)

fun MoonLakeListener.registerEvent(plugin: Plugin)
        = Bukkit.getServer().pluginManager.registerEvents(this, plugin)

fun <T: Event> Class<out T>.registerEvent(listener: Listener, priority: EventPriority, executor: EventExecutor, plugin: Plugin, ignoreCancelled: Boolean = false)
        = Bukkit.getServer().pluginManager.registerEvent(this, listener, priority, executor, plugin, ignoreCancelled)

fun <T: MoonLakeEvent> Class<out T>.registerEvent(listener: MoonLakeListener, priority: EventPriority, executor: EventExecutor, plugin: Plugin, ignoreCancelled: Boolean = false)
        = Bukkit.getServer().pluginManager.registerEvent(this, listener, priority, executor, plugin, ignoreCancelled)

fun unregisterAll()
        = HandlerList.unregisterAll()

fun unregisterAll(plugin: Plugin)
        = HandlerList.unregisterAll(plugin)

fun unregisterAll(listener: Listener)
        = HandlerList.unregisterAll(listener)

fun unregisterAll(listener: MoonLakeListener)
        = HandlerList.unregisterAll(listener)

/** task function */

fun runTask(plugin: Plugin, task: Runnable): BukkitTask
        = Bukkit.getScheduler().runTask(plugin, task)

fun runTaskLater(plugin: Plugin, task: Runnable, delay: Long): BukkitTask
        = Bukkit.getScheduler().runTaskLater(plugin, task, delay)

fun runTaskTimer(plugin: Plugin, task: Runnable, delay: Long, period: Long): BukkitTask
        = Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period)

fun runTaskAsync(plugin: Plugin, task: Runnable): BukkitTask
        = Bukkit.getScheduler().runTaskAsynchronously(plugin, task)

fun runTaskLaterAsync(plugin: Plugin, task: Runnable, delay: Long): BukkitTask
        = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay)

fun runTaskTimerAsync(plugin: Plugin, task: Runnable, delay: Long, period: Long): BukkitTask
        = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, delay, period)

fun runTask(plugin: Plugin, task: MoonLakeRunnable): BukkitTask
        = Bukkit.getScheduler().runTask(plugin, task as Runnable)

fun runTaskLater(plugin: Plugin, task: MoonLakeRunnable, delay: Long): BukkitTask
        = Bukkit.getScheduler().runTaskLater(plugin, task as Runnable, delay)

fun runTaskTimer(plugin: Plugin, task: MoonLakeRunnable, delay: Long, period: Long): BukkitTask
        = Bukkit.getScheduler().runTaskTimer(plugin, task as Runnable, delay, period)

fun runTaskAsync(plugin: Plugin, task: MoonLakeRunnable): BukkitTask
        = Bukkit.getScheduler().runTaskAsynchronously(plugin, task as Runnable)

fun runTaskLaterAsync(plugin: Plugin, task: MoonLakeRunnable, delay: Long): BukkitTask
        = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task as Runnable, delay)

fun runTaskTimerAsync(plugin: Plugin, task: MoonLakeRunnable, delay: Long, period: Long): BukkitTask
        = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task as Runnable, delay, period)

fun <T> Callable<T>.callSyncMethod(plugin: Plugin): Future<T>
        = Bukkit.getScheduler().callSyncMethod(plugin, this)

fun <T> Callable<T>.callSyncConsumer(plugin: Plugin, consumer: Consumer<T>)
        = callTaskConsumer(plugin, consumer, -1, false)

fun <T> Callable<T>.callSyncConsumer(plugin: Plugin, consumer: Consumer<T>, delay: Long)
        = callTaskConsumer(plugin, consumer, delay, false)

fun <T> Callable<T>.callAsyncConsumer(plugin: Plugin, consumer: Consumer<T>)
        = callTaskConsumer(plugin, consumer, -1, true)

fun <T> Callable<T>.callAsyncConsumer(plugin: Plugin, consumer: Consumer<T>, delay: Long)
        = callTaskConsumer(plugin, consumer, delay, true)

private fun <T> Callable<T>.callTaskConsumer(plugin: Plugin, consumer: Consumer<T>, delay: Long = -1, async: Boolean = false) {
    val futureTask = FutureTask(this)
    val runnable = Runnable {
        try {
            futureTask.run()
            consumer.accept(futureTask.get())
        } catch (e: Exception) {
            throw MoonLakeException(e)
        }
    }
    when(delay <= 0) {
        true -> async.let { if(it) runTaskAsync(plugin, runnable) else runTask(plugin, runnable) }
        else -> async.let { if(it) runTaskLaterAsync(plugin, runnable, delay) else runTaskLater(plugin, runnable, delay) }
    }
}

fun cancelTask(task: BukkitTask?)
        = task?.cancel()

fun cancelTask(taskId: Int)
        = Bukkit.getScheduler().cancelTask(taskId)

fun cancelTasks(plugin: Plugin)
        = Bukkit.getScheduler().cancelTasks(plugin)

fun cancelAllTasks()
        = Bukkit.getScheduler().cancelAllTasks()

/** target function */

fun isInFront(source: Entity, target: Entity): Boolean {
    val facing = source.location.direction
    val relative = target.location.subtract(source.location).toVector().normalize()
    return facing.dot(relative) >= .0
}

fun isInFront(source: Entity, target: Entity, angle: Double): Boolean = angle.let {
    if(angle <= .0) return false
    if(angle >= 360.0) return true
    val dotTarget = Math.cos(angle)
    val facing = source.location.direction
    val relative = target.location.subtract(source.location).toVector().normalize()
    return facing.dot(relative) >= dotTarget
}

fun isBehind(source: Entity, target: Entity, angle: Double): Boolean
        = !isInFront(source, target, angle)

fun <T: LivingEntity> getLivingTargets(clazz: Class<T>, source: LivingEntity, range: Double, tolerance: Double = 4.0): List<T> {
    val entityList = source.getNearbyEntities(range, range, range)
    val targets = ArrayList<T>()
    val facing = source.location.direction
    val fLengthSq = facing.lengthSquared()
    entityList.filter { clazz.isInstance(it) && isInFront(source, it) }.forEach {
        val  relative = it.location.subtract(source.location).toVector()
        val dot = relative.dot(facing)
        val rLengthSq = relative.lengthSquared()
        val cosSquared = dot * dot / (rLengthSq * fLengthSq)
        val sinSquared = 1.0 - cosSquared
        val dSquared = rLengthSq * sinSquared
        if(dSquared < tolerance)
            targets.add(clazz.cast(it))
    }
    return targets
}

fun getLivingTargets(source: LivingEntity, range: Double, tolerance: Double = 4.0): List<LivingEntity>
        = getLivingTargets(LivingEntity::class.java, source, range, tolerance)

fun <T: LivingEntity> getLivingTarget(clazz: Class<T>, source: LivingEntity, range: Double, tolerance: Double = 4.0): T? {
    val targets = getLivingTargets(clazz, source, range, tolerance)
    if(targets.isEmpty()) return null
    var target = targets.first()
    var minDistance = target.location.distanceSquared(source.location)
    targets.forEach {
        val distance = it.location.distanceSquared(source.location)
        if(distance < minDistance) {
            minDistance = distance
            target = it
        }
    }
    return target
}

fun getLivingTarget(source: LivingEntity, range: Double, tolerance: Double = 4.0): LivingEntity?
        = getLivingTarget(LivingEntity::class.java, source, range, tolerance)

fun <T: LivingEntity> getLivingTargets(clazz: Class<T>, source: MoonLakePlayer, range: Double, tolerance: Double = 4.0): List<T>
        = getLivingTargets(clazz, source.getBukkitPlayer(), range, tolerance)

fun getLivingTargets(source: MoonLakePlayer, range: Double, tolerance: Double = 4.0): List<LivingEntity>
        = getLivingTargets(source.getBukkitPlayer(), range, tolerance)

fun <T: LivingEntity> getLivingTarget(clazz: Class<T>, source: MoonLakePlayer, range: Double, tolerance: Double = 4.0): T?
        = getLivingTarget(clazz, source.getBukkitPlayer(), range, tolerance)

fun getLivingTarget(source: MoonLakePlayer, range: Double, tolerance: Double = 4.0): LivingEntity?
        = getLivingTarget(source.getBukkitPlayer(), range, tolerance)
