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

import com.minecraft.moonlake.api.anvil.AnvilWindow
import com.minecraft.moonlake.api.depend.DependPlugin
import com.minecraft.moonlake.api.depend.DependPluginException
import com.minecraft.moonlake.api.depend.DependPlugins
import com.minecraft.moonlake.api.event.MoonLakeEvent
import com.minecraft.moonlake.api.event.MoonLakeListener
import com.minecraft.moonlake.api.exception.MoonLakeException
import com.minecraft.moonlake.api.funs.Function
import com.minecraft.moonlake.api.item.ItemBuilder
import com.minecraft.moonlake.api.nbt.NBTCompound
import com.minecraft.moonlake.api.nbt.NBTFactory
import com.minecraft.moonlake.api.nbt.NBTList
import com.minecraft.moonlake.api.player.MoonLakePlayer
import com.minecraft.moonlake.api.player.MoonLakePlayerCached
import com.minecraft.moonlake.api.region.*
import com.minecraft.moonlake.api.task.MoonLakeRunnable
import com.minecraft.moonlake.api.utility.MinecraftReflection
import com.minecraft.moonlake.api.version.MinecraftBukkitVersion
import com.minecraft.moonlake.api.version.MinecraftVersion
import org.bukkit.*
import org.bukkit.command.CommandSender
import org.bukkit.command.PluginCommand
import org.bukkit.configuration.Configuration
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.ServicesManager
import org.bukkit.plugin.messaging.Messenger
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import org.bukkit.scoreboard.Scoreboard
import java.lang.reflect.Modifier
import java.text.MessageFormat
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.FutureTask

/** MoonLake API Extended Function */

private var moonlake: MoonLake? = null

@Throws(MoonLakeException::class)
fun setMoonLake(obj: MoonLake) {
    if(moonlake != null)
        throw MoonLakeException("无法再次设置 MoonLakeAPI 的内部实例.")
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

inline fun <reified T: Enum<T>> fromName(name: String, def: T): T = try {
    enumValueOf(name)
} catch (e: Exception) {
    def
}

inline fun <reified T: Enum<T>> fromNameOrNull(name: String, def: T? = null): T? = try {
    enumValueOf<T>(name)
} catch (e: Exception) {
    def
}

/** version function */

fun currentMCVersion(): MinecraftVersion
        = MinecraftVersion.currentVersion()

fun currentBukkitVersion(): MinecraftBukkitVersion
        = MinecraftBukkitVersion.currentVersion()

/** util function */

fun String.toColor(): String
        = ChatColor.translateAlternateColorCodes('\u0026', this)

fun String.toColor(altColorChar: Char): String
        = ChatColor.translateAlternateColorCodes(altColorChar, this)

fun Array<out String>.toColor(): Array<out String>
        = this.toList().map { it -> it.toColor() }.toTypedArray()

fun Array<out String>.toColor(altColorChar: Char): Array<out String>
        = this.toList().map { it -> it.toColor(altColorChar) }.toTypedArray()

fun Iterable<String>.toColor(): List<String>
        = this.map { it -> it.toColor() }.let { ArrayList(it) }

fun Iterable<String>.toColor(altColorChar: Char): List<String>
        = this.map { it -> it.toColor(altColorChar) }

fun String.stripColor(): String
        = ChatColor.stripColor(this)

fun Array<out String>.stripColor(): Array<out String>
        = this.toList().map { it -> it.stripColor() }.toTypedArray()

fun Iterable<String>.stripColor(): List<String>
        = this.map { it -> it.stripColor() }

fun String.messageFormat(vararg args: Any?): String
        = MessageFormat.format(this, args)

@Throws(MoonLakeException::class)
fun Throwable.throwMoonLake(): Nothing = let {
    when(it is MoonLakeException) {
        true -> throw it
        else -> throw MoonLakeException(it)
    }
}

fun String.isInteger(): Boolean = isNullOrEmpty().let {
    when(it) {
        true -> false
        else -> return try {
            toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}

fun String.isDouble(): Boolean = isNullOrEmpty().let {
    when(it) {
        true -> false
        else -> return try {
            toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}

fun Any.parseInt(def: Int = 0): Int = (this is Number).let {
    when(it) {
        true -> (this as Number).toInt()
        else -> return try {
            toString().toInt()
        } catch (e: NumberFormatException) {
            def
        }
    }
}

fun Any.parseLong(def: Long = 0L): Long = (this is Number).let {
    when(it) {
        true -> (this as Number).toLong()
        else -> return try {
            toString().toLong()
        } catch (e: NumberFormatException) {
            def
        }
    }
}

fun Any.parseFloat(def: Float = .0f): Float = (this is Number).let {
    when(it) {
        true -> (this as Number).toFloat()
        else -> return try {
            toString().toFloat()
        } catch (e: NumberFormatException) {
            def
        }
    }
}

fun Any.parseDouble(def: Double = .0): Double = (this is Number).let {
    when(it) {
        true -> (this as Number).toDouble()
        else -> return try {
            toString().toDouble()
        } catch (e: NumberFormatException) {
            def
        }
    }
}

@Throws(MoonLakeException::class)
fun <T: ConfigurationSerializable> Class<T>.deserializeConfiguration(configuration: Configuration, key: String, def: T? = null): T? = configuration.get(key).let {
    when(it == null) {
        true -> def
        else -> when {
            isInstance(it) -> cast(it)
            it is Map<*, *> -> try {
                var method = getMethod("deserialize")
                if(method == null) method = getMethod("valueOf")
                if(method == null || !Modifier.isStatic(method.modifiers)) throw MoonLakeException("值为 Map 实例, 但是序列化类不存在 'deserialize' 或 'valueOf' 静态函数.")
                @Suppress("UNCHECKED_CAST")
                method.invoke(null, it) as T
            } catch (e: Exception) {
                e.throwMoonLake()
            }
            else -> def
        }
    }
}

fun createInventory(holder: InventoryHolder?, type: InventoryType): Inventory
        = Bukkit.createInventory(holder, type)

fun createInventory(holder: InventoryHolder?, type: InventoryType, title: String): Inventory
        = Bukkit.createInventory(holder, type, title)

fun createInventory(holder: InventoryHolder?, size: Int): Inventory
        = Bukkit.createInventory(holder, size)

fun createInventory(holder: InventoryHolder?, size: Int, title: String): Inventory
        = Bukkit.createInventory(holder, size, title)

fun getScoreboardMain(): Scoreboard
        = Bukkit.getScoreboardManager().mainScoreboard

fun getScoreboardNew(): Scoreboard
        = Bukkit.getScoreboardManager().newScoreboard

fun getMessenger(): Messenger
        = Bukkit.getMessenger()

fun getServicesManager(): ServicesManager
        = Bukkit.getServicesManager()

fun getPluginManager(): PluginManager
        = Bukkit.getPluginManager()

fun getPlugin(name: String): Plugin?
        = Bukkit.getPluginManager().getPlugin(name)

fun getScheduler(): BukkitScheduler
        = Bukkit.getScheduler()

fun getPluginCommand(name: String): PluginCommand
        = Bukkit.getPluginCommand(name)

fun dispatchCommand(sender: CommandSender, command: String): Boolean
        = Bukkit.dispatchCommand(sender, command)

fun dispatchConsoleCmd(command: String): Boolean
        = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command)

/** converter function */

fun UUID.toBukkitWorld(): World?
        = Bukkit.getWorld(this)

fun String.toBukkitWorld(): World?
        = Bukkit.getWorld(this)

fun UUID.toEntity(): Entity?
        = Bukkit.getEntity(this)

fun UUID.toPlayer(): Player?
        = Bukkit.getPlayer(this)

fun String.toPlayer(): Player?
        = Bukkit.getPlayer(this)

fun String.toPlayerExact(): Player?
        = Bukkit.getPlayerExact(this)

fun Player.toMoonLakePlayer(): MoonLakePlayer
        = MoonLakePlayerCached.instance().getCache(this)

fun UUID.toMoonLakePlayer(): MoonLakePlayer? = this.toPlayer().let {
    when(it == null) {
        true -> null
        else -> it.notNull().toMoonLakePlayer()
    }
}

fun String.toMoonLakePlayer(): MoonLakePlayer? = this.toPlayer().let {
    when(it == null) {
        true -> null
        else -> it.notNull().toMoonLakePlayer()
    }
}

fun Location.toRegionVector(): RegionVector
        = RegionVector(this.x, this.y, this.z)

fun Location.toRegionVectorBlock(): RegionVectorBlock
        = RegionVectorBlock(this.x, this.y, this.z)

fun Location.toRegionVector2D(): RegionVector2D
        = RegionVector2D(this.x, this.z)

fun <T, R> ((_: T) -> R).toFunction(): Function<T, R> = object: Function<T, R> {
    override fun apply(param: T): R
            = this@toFunction(param)
}

/** event function */

fun Event.callEvent()
        = Bukkit.getServer().pluginManager.callEvent(this)

fun Event.callEventAsync(plugin: Plugin)
        = plugin.runTaskAsync(Runnable { Bukkit.getServer().pluginManager.callEvent(this) })

fun MoonLakeEvent.callEvent()
        = Bukkit.getServer().pluginManager.callEvent(this)

fun MoonLakeEvent.callEventAsync(plugin: Plugin)
        = plugin.runTaskAsync(Runnable { Bukkit.getServer().pluginManager.callEvent(this) })

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

fun Plugin.unregisterAll()
        = HandlerList.unregisterAll(this)

fun Listener.unregisterAll()
        = HandlerList.unregisterAll(this)

fun MoonLakeListener.unregisterAll()
        = HandlerList.unregisterAll(this)

/** task function */

fun Plugin.runTask(task: Runnable): BukkitTask
        = Bukkit.getScheduler().runTask(this, task)

fun Plugin.runTaskLater(task: Runnable, delay: Long): BukkitTask
        = Bukkit.getScheduler().runTaskLater(this, task, delay)

fun Plugin.runTaskTimer(task: Runnable, delay: Long, period: Long): BukkitTask
        = Bukkit.getScheduler().runTaskTimer(this, task, delay, period)

fun Plugin.runTaskAsync(task: Runnable): BukkitTask
        = Bukkit.getScheduler().runTaskAsynchronously(this, task)

fun Plugin.runTaskLaterAsync(task: Runnable, delay: Long): BukkitTask
        = Bukkit.getScheduler().runTaskLaterAsynchronously(this, task, delay)

fun Plugin.runTaskTimerAsync(task: Runnable, delay: Long, period: Long): BukkitTask
        = Bukkit.getScheduler().runTaskTimerAsynchronously(this, task, delay, period)

fun Plugin.runTask(task: MoonLakeRunnable): BukkitTask
        = Bukkit.getScheduler().runTask(this, task as Runnable)

fun Plugin.runTaskLater(task: MoonLakeRunnable, delay: Long): BukkitTask
        = Bukkit.getScheduler().runTaskLater(this, task as Runnable, delay)

fun Plugin.runTaskTimer(task: MoonLakeRunnable, delay: Long, period: Long): BukkitTask
        = Bukkit.getScheduler().runTaskTimer(this, task as Runnable, delay, period)

fun Plugin.runTaskAsync(task: MoonLakeRunnable): BukkitTask
        = Bukkit.getScheduler().runTaskAsynchronously(this, task as Runnable)

fun Plugin.runTaskLaterAsync(task: MoonLakeRunnable, delay: Long): BukkitTask
        = Bukkit.getScheduler().runTaskLaterAsynchronously(this, task as Runnable, delay)

fun Plugin.runTaskTimerAsync(task: MoonLakeRunnable, delay: Long, period: Long): BukkitTask
        = Bukkit.getScheduler().runTaskTimerAsynchronously(this, task as Runnable, delay, period)

fun <T> Callable<T>.callMethodSync(plugin: Plugin): Future<T>
        = Bukkit.getScheduler().callSyncMethod(plugin, this)

fun <T> Callable<T>.callTaskSync(plugin: Plugin, consumer: (value: T) -> Unit)
        = callTaskConsumer(plugin, consumer, -1, false)

fun <T> Callable<T>.callTaskSync(plugin: Plugin, consumer: (value: T) -> Unit, delay: Long)
        = callTaskConsumer(plugin, consumer, delay, false)

fun <T> Callable<T>.callTaskAsync(plugin: Plugin, consumer: (value: T) -> Unit)
        = callTaskConsumer(plugin, consumer, -1, true)

fun <T> Callable<T>.callTaskAsync(plugin: Plugin, consumer: (value: T) -> Unit, delay: Long)
        = callTaskConsumer(plugin, consumer, delay, true)

private fun <T> Callable<T>.callTaskConsumer(plugin: Plugin, consumer: (value: T) -> Unit, delay: Long = -1, async: Boolean = false) {
    val futureTask = FutureTask(this)
    val runnable = Runnable {
        try {
            futureTask.run()
            consumer(futureTask.get())
        } catch (e: Exception) {
            throw MoonLakeException(e)
        }
    }
    when(delay <= 0) {
        true -> async.let { if(it) plugin.runTaskAsync(runnable) else plugin.runTask(runnable) }
        else -> async.let { if(it) plugin.runTaskLaterAsync(runnable, delay) else plugin.runTaskLater(runnable, delay) }
    }
}

fun cancelTask(task: BukkitTask?)
        = task?.cancel()

fun cancelTask(taskId: Int)
        = Bukkit.getScheduler().cancelTask(taskId)

fun Plugin.cancelTasks()
        = Bukkit.getScheduler().cancelTasks(this)

fun cancelAllTasks()
        = Bukkit.getScheduler().cancelAllTasks()

/** target function */

fun Entity.isInFront(target: Entity): Boolean {
    val facing = location.direction
    val relative = target.location.subtract(location).toVector().normalize()
    return facing.dot(relative) >= .0
}

fun Entity.isInFront(target: Entity, angle: Double): Boolean = angle.let {
    if(angle <= .0) return false
    if(angle >= 360.0) return true
    val dotTarget = Math.cos(angle)
    val facing = location.direction
    val relative = target.location.subtract(location).toVector().normalize()
    return facing.dot(relative) >= dotTarget
}

fun Entity.isBehind(target: Entity, angle: Double): Boolean
        = !isInFront(target, angle)

fun <T: LivingEntity> LivingEntity.getLivingTargets(clazz: Class<T>, range: Double, tolerance: Double = 4.0): List<T> {
    val entityList = getNearbyEntities(range, range, range)
    val targets = ArrayList<T>()
    val facing = location.direction
    val fLengthSq = facing.lengthSquared()
    entityList.filter { clazz.isInstance(it) && isInFront(it) }.forEach {
        val  relative = it.location.subtract(location).toVector()
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

fun LivingEntity.getLivingTargets(range: Double, tolerance: Double = 4.0): List<LivingEntity>
        = getLivingTargets(LivingEntity::class.java, range, tolerance)

fun <T: LivingEntity> LivingEntity.getLivingTarget(clazz: Class<T>, range: Double, tolerance: Double = 4.0): T? {
    val targets = getLivingTargets(clazz, range, tolerance)
    if(targets.isEmpty()) return null
    var target = targets.first()
    var minDistance = target.location.distanceSquared(location)
    targets.forEach {
        val distance = it.location.distanceSquared(location)
        if(distance < minDistance) {
            minDistance = distance
            target = it
        }
    }
    return target
}

fun LivingEntity.getLivingTarget(range: Double, tolerance: Double = 4.0): LivingEntity?
        = getLivingTarget(LivingEntity::class.java, range, tolerance)

fun <T: LivingEntity> MoonLakePlayer.getLivingTargets(clazz: Class<T>, range: Double, tolerance: Double = 4.0): List<T>
        = getBukkitPlayer().getLivingTargets(clazz, range, tolerance)

fun MoonLakePlayer.getLivingTargets(range: Double, tolerance: Double = 4.0): List<LivingEntity>
        = getBukkitPlayer().getLivingTargets(range, tolerance)

fun <T: LivingEntity> MoonLakePlayer.getLivingTarget(clazz: Class<T>, range: Double, tolerance: Double = 4.0): T?
        = getBukkitPlayer().getLivingTarget(clazz, range, tolerance)

fun MoonLakePlayer.getLivingTarget(range: Double, tolerance: Double = 4.0): LivingEntity?
        = getBukkitPlayer().getLivingTarget(range, tolerance)

/** region function */

fun World.createCuboidRegion(pos1: Location, pos2: Location): RegionCuboid
        = RegionCuboid(this, pos1.toRegionVector(), pos2.toRegionVector())

fun World.createCuboidRegion(pos1: RegionVector, pos2: RegionVector): RegionCuboid
        = RegionCuboid(this, pos1, pos2)

fun Region.createWorldBorder(): WorldBorder {
    val worldBorder = getWorld().worldBorder
    worldBorder.setSize(getLength().toDouble(), 0L)
    worldBorder.center = getCenter().toLocation(getWorld())
    return worldBorder
}

/** anvil window function */

fun Plugin.newAnvilWindow(): AnvilWindow
        = MinecraftReflection.anvilWindowConstructor.newInstance(this)

/** item builder function */

fun ItemStack.newItemBuilder(): ItemBuilder
        = ItemBuilder.of(this)

fun Material.newItemBuilder(amount: Int = 1, durability: Int = 0): ItemBuilder
        = ItemBuilder.of(this, amount, durability)

/** nbt function */

fun newNBTCompound(name: String = ""): NBTCompound
        = NBTFactory.ofCompound(name)

fun <T> newNBTList(name: String = ""): NBTList<T>
        = NBTFactory.ofList(name)

fun ItemStack.readTag(consumer: (tag: NBTCompound?) -> Unit): ItemStack
        { consumer(NBTFactory.readStackTag(this)); return this; }

fun ItemStack.readTagSafe(consumer: (tag: NBTCompound) -> Unit): ItemStack
        { consumer(NBTFactory.readStackTagSafe(this)); return this; }

fun ItemStack.writeTag(tag: NBTCompound?): ItemStack
        = NBTFactory.writeStackTag(this, tag)


/** depend plugin function */

@Throws(DependPluginException::class)
fun <T: DependPlugin> Class<T>.useDepend(consumer: (depend: T) -> Unit): T
        { val value = DependPlugins.of(this); consumer(value); return value; }

fun <T: DependPlugin> Class<T>.useDependSafe(consumer: (depend: T?) -> Unit): T?
        { val value = DependPlugins.ofSafe(this); consumer(value); return value; }
