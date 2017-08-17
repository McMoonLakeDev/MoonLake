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

package com.minecraft.moonlake.player

import com.minecraft.moonlake.api.notNull
import com.minecraft.moonlake.api.player.IllegalOfflinePlayerException
import com.minecraft.moonlake.api.player.MoonLakePlayer
import com.minecraft.moonlake.api.toBukkitWorld
import com.minecraft.moonlake.api.toColor
import com.minecraft.moonlake.api.toMoonLakePlayer
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory
import org.bukkit.metadata.MetadataValue
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachment
import org.bukkit.permissions.PermissionAttachmentInfo
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.util.Vector
import java.net.InetSocketAddress
import java.util.*

abstract class MoonLakePlayerAbstract : MoonLakePlayer {

    /** member */

    private val player: Player

    /** constructor */

    @Throws(IllegalOfflinePlayerException::class)
    constructor(uuid: UUID) : this(Bukkit.getPlayer(uuid))

    @Throws(IllegalOfflinePlayerException::class)
    constructor(player: Player) {
        this.player = (!player.isOnline).let {
            when(it) {
                true -> throw IllegalOfflinePlayerException(player.name)
                else -> player
            }
        }
    }

    /** function */

    override final fun getBukkitPlayer(): Player
            = player

    override final fun spigot(): CommandSender.Spigot
            = throw UnsupportedOperationException()

    override fun getName(): String
            = getBukkitPlayer().name

    override fun getUniqueId(): UUID
            = getBukkitPlayer().uniqueId

    override fun sendMessage(message: String)
            = getBukkitPlayer().sendMessage(message)

    override fun sendMessage(messages: Array<out String>)
            = getBukkitPlayer().sendMessage(messages)

    override fun isPermissionSet(permission: String): Boolean
            = getBukkitPlayer().isPermissionSet(permission)

    override fun isPermissionSet(permission: Permission): Boolean
            = getBukkitPlayer().isPermissionSet(permission)

    override fun addAttachment(plugin: Plugin, name: String, value: Boolean): PermissionAttachment
            = getBukkitPlayer().addAttachment(plugin, name, value)

    override fun addAttachment(plugin: Plugin): PermissionAttachment
            = getBukkitPlayer().addAttachment(plugin)

    override fun addAttachment(plugin: Plugin, name: String, value: Boolean, ticks: Int): PermissionAttachment
            = getBukkitPlayer().addAttachment(plugin, name, value, ticks)

    override fun addAttachment(plugin: Plugin, ticks: Int): PermissionAttachment
            = getBukkitPlayer().addAttachment(plugin, ticks)

    override fun isOp(): Boolean
            = getBukkitPlayer().isOp

    override fun getEffectivePermissions(): MutableSet<PermissionAttachmentInfo>
            = getBukkitPlayer().effectivePermissions

    override fun getServer(): Server
            = getBukkitPlayer().server

    override fun removeAttachment(permissionAttachment: PermissionAttachment)
            = getBukkitPlayer().removeAttachment(permissionAttachment)

    override fun recalculatePermissions()
            = getBukkitPlayer().recalculatePermissions()

    override fun hasPermission(permission: String): Boolean
            = getBukkitPlayer().hasPermission(permission)

    override fun hasPermission(permission: Permission): Boolean
            = getBukkitPlayer().hasPermission(permission)

    override fun setOp(flag: Boolean)
            { getBukkitPlayer().isOp = flag }

    override fun hasMetadata(key: String): Boolean
            = getBukkitPlayer().hasMetadata(key)

    override fun removeMetadata(key: String, owner: Plugin)
            = getBukkitPlayer().removeMetadata(key, owner)

    override fun getMetadata(key: String): MutableList<MetadataValue>
            = getBukkitPlayer().getMetadata(key)

    override fun setMetadata(key: String, value: MetadataValue)
            = getBukkitPlayer().setMetadata(key, value)

    override fun getInventory(): PlayerInventory
            = getBukkitPlayer().inventory

    override fun getEntityId(): Int
            = getBukkitPlayer().entityId

    override fun getDisplayName(): String
            = getBukkitPlayer().displayName

    override fun setDisplayName(displayName: String)
            { getBukkitPlayer().displayName = displayName }

    override fun getTabListName(): String
            = getBukkitPlayer().playerListName

    override fun setTabListName(listName: String)
            { getBukkitPlayer().playerListName = listName }

    override fun hasBeforePlayed(): Boolean
            = getBukkitPlayer().hasPlayedBefore()

    override fun getWorld(): World
            = getBukkitPlayer().world

    override fun getLocation(): Location
            = getBukkitPlayer().location

    override fun getX(): Double
            = getLocation().x

    override fun getY(): Double
            = getLocation().y

    override fun getZ(): Double
            = getLocation().z

    override fun getYaw(): Float
            = getLocation().yaw

    override fun getPitch(): Float
            = getLocation().pitch

    override fun getBlockX(): Int
            = getX().toInt()

    override fun getBlockY(): Int
            = getY().toInt()

    override fun getBlockZ(): Int
            = getZ().toInt()

    override fun getEyeLocation(): Location
            = getBukkitPlayer().eyeLocation

    override fun getEyeHeight(): Double
            = getBukkitPlayer().eyeHeight

    override fun getEyeHeight(ignoreSneaking: Boolean): Double
            = getBukkitPlayer().getEyeHeight(ignoreSneaking)

    override fun getTargetBlock(distance: Int): Block?
            = getTargetBlock(null, distance)

    override fun getTargetBlock(ignoreType: Set<Material>?, distance: Int): Block?
            = getBukkitPlayer().getTargetBlock(ignoreType, distance)

    override fun distance(target: Location): Double
            = getLocation().distance(target)

    override fun distanceSq(target: Location): Double
            = getLocation().distanceSquared(target)

    override fun playSound(sound: Sound, volume: Float, pitch: Float)
            = getBukkitPlayer().playSound(getLocation(), sound, volume, pitch)

    override fun playSound(sound: String, volume: Float, pitch: Float)
            = getBukkitPlayer().playSound(getLocation(), sound, volume, pitch)

    override fun playNote(instrument: Instrument, note: Note)
            = getBukkitPlayer().playNote(getLocation(), instrument, note)

    override fun getActivePotionEffects(): Collection<PotionEffect>
            = getBukkitPlayer().activePotionEffects

    override fun hasPotionEffect(type: PotionEffectType): Boolean
            = getBukkitPlayer().hasPotionEffect(type)

    override fun removePotionEffect(type: PotionEffectType)
            = getBukkitPlayer().removePotionEffect(type)

    override fun clearPotionEffects()
            = getActivePotionEffects().map { it -> it.type }.filter { hasPotionEffect(it) }.forEach { removePotionEffect(it) }

    override fun getVelocity(): Vector
            = getBukkitPlayer().velocity

    override fun setVelocity(vector: Vector)
            { getBukkitPlayer().velocity = vector }

    override fun getNoDamageTicks(): Int
            = getBukkitPlayer().noDamageTicks

    override fun setNoDamageTicks(ticks: Int)
            { getBukkitPlayer().noDamageTicks = ticks }

    override fun onEject(): Boolean
            = getBukkitPlayer().eject()

    override fun createExplosion(power: Float)
            = createExplosion(power, true)

    override fun createExplosion(power: Float, fire: Boolean)
            = createExplosion(power, fire, true)

    override fun createExplosion(power: Float, fire: Boolean, breakBlock: Boolean)
            { getWorld().createExplosion(getX(), getY(), getZ(), power, fire, breakBlock) }

    override fun getTime(): Long
            = getBukkitPlayer().playerTime

    override fun setTime(time: Long)
            = setTime(time, true)

    override fun setTime(time: Long, relative: Boolean)
            = getBukkitPlayer().setPlayerTime(time, relative)

    override fun getWeather(): WeatherType
            = getBukkitPlayer().playerWeather

    override fun setWeather(type: WeatherType)
            { getBukkitPlayer().playerWeather = type }

    override fun resetTime()
            = getBukkitPlayer().resetPlayerTime()

    override fun resetWeather()
            = getBukkitPlayer().resetPlayerWeather()

    override fun getDirection(): Vector
            = getLocation().direction

    override fun getLastDamage(): Double
            = getBukkitPlayer().lastDamage

    override fun getLastDamageCause(): EntityDamageEvent
            = getBukkitPlayer().lastDamageCause

    override fun chat(message: String)
            = getBukkitPlayer().chat(message)

    override fun send(message: String)
            = getBukkitPlayer().sendMessage(message.toColor())

    override fun send(vararg messages: String)
            = getBukkitPlayer().sendMessage(messages.toColor())

    override fun send(message: String, vararg args: Any)
            = getBukkitPlayer().sendMessage(message.format(args).toColor())

    override fun onKick()
            = onKick("None")

    override fun onKick(reason: String)
            = getBukkitPlayer().kickPlayer(reason)

    override fun getHealth(): Double
            = getBukkitPlayer().health

    override fun setHealth(health: Double)
            { getBukkitPlayer().health = health }

    override fun giveHealth(value: Double) = (getHealth() + value >= getMaxHealth()).let {
        when(it) {
            true -> setHealth(getMaxHealth())
            else -> setHealth(getHealth() + value)
        }
    }

    override fun takeHealth(value: Double) = (getHealth() - value <= .0).let {
        when(it) {
            true -> setHealth(.0)
            else -> setHealth(getHealth() - value)
        }
    }

    override fun getMaxHealth(): Double
            = getBukkitPlayer().maxHealth

    override fun setMaxHealth(maxHealth: Double)
            { getBukkitPlayer().maxHealth = maxHealth }

    override fun resetMaxHealth()
            = getBukkitPlayer().resetMaxHealth()

    override fun getExp(): Float
            = getBukkitPlayer().exp

    override fun setExp(exp: Float)
            { getBukkitPlayer().exp = exp }

    override fun giveExp(value: Float)
            = setExp(getExp() + value)

    override fun takeExp(value: Float)
            = setExp(getExp() - value)

    override fun getLevel(): Int
            = getBukkitPlayer().level

    override fun setLevel(level: Int)
            { getBukkitPlayer().level = level }

    override fun giveLevel(value: Int)
            = setLevel(getLevel() + value)

    override fun takeLevel(value: Int)
            = setLevel(getLevel() - value)

    override fun getFlySpeed(): Float
            = getBukkitPlayer().flySpeed

    override fun setFlySpeed(flySpeed: Float)
            { getBukkitPlayer().flySpeed = flySpeed }

    override fun getWalkSpeed(): Float
            = getBukkitPlayer().walkSpeed

    override fun setWalkSpeed(walkSpeed: Float)
            { getBukkitPlayer().walkSpeed = walkSpeed }

    override fun getFoodLevel(): Int
            = getBukkitPlayer().foodLevel

    override fun setFoodLevel(foodLevel: Int)
            { getBukkitPlayer().foodLevel = foodLevel }

    override fun isFlying(): Boolean
            = getBukkitPlayer().isFlying

    override fun isAllowFly(): Boolean
            = getBukkitPlayer().allowFlight

    override fun setAllowFly(allowFly: Boolean)
            { getBukkitPlayer().allowFlight = allowFly }

    override fun damage(value: Double)
            = getBukkitPlayer().damage(value)

    override fun damage(value: Double, damager: LivingEntity)
            = getBukkitPlayer().damage(value, damager)

    override fun teleport(location: Location): Boolean
            = getBukkitPlayer().teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN)

    override fun teleport(player: Player): Boolean
            = teleport(player.location)

    override fun teleport(player: MoonLakePlayer): Boolean
            = teleport(player.getLocation())

    override fun teleport(x: Double, y: Double, z: Double): Boolean
            = teleport(x, y, z, getYaw(), getPitch())

    override fun teleport(x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Boolean
            = teleport(getWorld(), x, y, z, yaw, pitch)

    override fun teleport(world: World, x: Double, y: Double, z: Double): Boolean
            = teleport(world, x, y, z, getYaw(), getPitch())

    override fun teleport(world: World, x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Boolean
            = teleport(Location(world, x, y, z, yaw, pitch))

    override fun teleport(world: String, x: Double, y: Double, z: Double): Boolean
            = world.toBukkitWorld().notNull().let { teleport(it, x, y, z) }

    override fun teleport(world: String, x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Boolean
            = world.toBukkitWorld().notNull().let { teleport(it, x, y, z, yaw, pitch) }

    override fun teleportSpawn(world: World): Boolean
            = teleport(world.spawnLocation)

    override fun teleportSpawn(world: String): Boolean
            = world.toBukkitWorld().notNull().let { teleport(it.spawnLocation) }

    override fun isCanPickupItems(): Boolean
            = getBukkitPlayer().canPickupItems

    override fun setCanPickupItems(canPickupItems: Boolean)
            { getBukkitPlayer().canPickupItems = canPickupItems }

    override fun getFallDistance(): Float
            = getBukkitPlayer().fallDistance

    override fun setFallDistance(fallDistance: Float)
            { getBukkitPlayer().fallDistance = fallDistance }

    override fun getGameMode(): GameMode
            = getBukkitPlayer().gameMode

    override fun setGameMode(gameMode: GameMode)
            { getBukkitPlayer().gameMode = gameMode }

    override fun isSurvivalMode(): Boolean
            = getGameMode() == GameMode.SURVIVAL

    override fun isCreativeMode(): Boolean
            = getGameMode() == GameMode.CREATIVE

    override fun isSpectatorMode(): Boolean
            = getGameMode() == GameMode.SPECTATOR

    override fun isAdventureMode(): Boolean
            = getGameMode() == GameMode.ADVENTURE

    override fun isSneaking(): Boolean
            = getBukkitPlayer().isSneaking

    override fun isSprinting(): Boolean
            = getBukkitPlayer().isSprinting

    override fun addPotionEffect(type: PotionEffectType, amplifier: Int, duration: Int): Boolean
            = addPotionEffect(type, amplifier, duration, false, false)

    override fun addPotionEffect(type: PotionEffectType, amplifier: Int, duration: Int, ambient: Boolean): Boolean
            = addPotionEffect(type, amplifier, duration, ambient, false)

    override fun addPotionEffect(type: PotionEffectType, amplifier: Int, duration: Int, ambient: Boolean, particles: Boolean): Boolean
            = getBukkitPlayer().addPotionEffect(PotionEffect(type, duration, amplifier, ambient, particles))

    override fun addPotionEffect(type: PotionEffectType, amplifier: Int, duration: Int, ambient: Boolean, particles: Boolean, color: Color): Boolean
            = getBukkitPlayer().addPotionEffect(PotionEffect(type, duration, amplifier, ambient, particles))

    override fun getScoreboard(): Scoreboard
            = getBukkitPlayer().scoreboard

    override fun setScoreboard(scoreboard: Scoreboard)
            { getBukkitPlayer().scoreboard = scoreboard }

    override fun <T : Projectile> launchProjectile(projectile: Class<T>): T
            = getBukkitPlayer().launchProjectile(projectile)

    override fun <T : Projectile> launchProjectile(projectile: Class<T>, vector: Vector): T
            = getBukkitPlayer().launchProjectile(projectile, vector)

    override fun performCommand(command: String): Boolean = command.let {
        when(it[0] == '/') {
            true -> getBukkitPlayer().performCommand(it.substring(1))
            else -> getBukkitPlayer().performCommand(it)
        }
    }

    override fun getCompassTarget(): Location
            = getBukkitPlayer().compassTarget

    override fun setCompassTarget(target: Location)
            { getBukkitPlayer().compassTarget = target }

    override fun getBedSpawnLocation(): Location
            = getBukkitPlayer().bedSpawnLocation

    override fun setBedSpawnLocation(target: Location, force: Boolean)
            = getBukkitPlayer().setBedSpawnLocation(target, force)

    override fun getNearbyEntities(radius: Double): List<Entity>
            = getNearbyEntities(radius, radius, radius)

    override fun getNearbyLivingEntities(radius: Double): List<LivingEntity>
            = getNearbyLivingEntities(radius, radius, radius)

    override fun getNearbyPlayers(radius: Double): List<MoonLakePlayer>
            = getNearbyPlayers(radius, radius, radius)

    override fun getNearbyEntities(x: Double, y: Double, z: Double): List<Entity>
            = getBukkitPlayer().getNearbyEntities(x, y, z)

    override fun getNearbyLivingEntities(x: Double, y: Double, z: Double): List<LivingEntity>
            = getNearbyEntities(x, y, z).filter { it is LivingEntity }.map { it as LivingEntity }

    override fun getNearbyPlayers(x: Double, y: Double, z: Double): List<MoonLakePlayer>
            = getNearbyEntities(x, y, z).filter { it is Player }.map { (it as Player).toMoonLakePlayer() }

    override fun <T : Entity> getNearbyEntities(entityClass: Class<T>, radius: Double): List<T>
            = getNearbyEntities(entityClass, radius, radius, radius)

    override fun <T : Entity> getNearbyEntities(entityClass: Class<T>, x: Double, y: Double, z: Double): List<T>
            = getNearbyEntities(x, y, z).filter { entityClass.isInstance(it) }.map { entityClass.cast(it) }

    override fun getMetadataFirst(key: String): MetadataValue? = getMetadata(key).let {
        when(it.isEmpty()) {
            true -> null
            else -> it.first()
        }
    }

    override fun getMetadataLast(key: String): MetadataValue? = getMetadata(key).let {
        when(it.isEmpty()) {
            true -> null
            else -> it.last()
        }
    }

    override fun getPing(): Int
            = -1 // TODO

    override fun getIp(): String = getAddress().address.let {
        when(it == null) {
            true -> "127.0.0.1"
            else -> it.hostName
        }
    }

    override fun getPort(): Int
            = getAddress().port

    override fun getAddress(): InetSocketAddress
            = getBukkitPlayer().address

    override fun getEnderChest(): Inventory
            = getBukkitPlayer().enderChest

    override fun updateInventory()
            = getBukkitPlayer().updateInventory()

    override fun closeInventory()
            = getBukkitPlayer().closeInventory()

    override fun clearInventory()
            = inventory.clear()

    @Suppress("DEPRECATION")
    override fun getItemInHand(): ItemStack
            = getBukkitPlayer().itemInHand

    @Suppress("DEPRECATION")
    override fun setItemInHand(itemStack: ItemStack?)
            { getBukkitPlayer().itemInHand = itemStack }

    override fun getItemOnCursor(): ItemStack
            = getBukkitPlayer().itemOnCursor

    override fun setItemOnCursor(itemStack: ItemStack?)
            { getBukkitPlayer().itemOnCursor = itemStack }

    override fun getOpenInventory(): InventoryView
            = getBukkitPlayer().openInventory

    override fun openInventory(inventory: Inventory): InventoryView
            = getBukkitPlayer().openInventory(inventory)

    override fun addItems(vararg itemStacks: ItemStack): Map<Int, ItemStack>
            = inventory.addItem(*itemStacks)

    override fun removeItems(vararg itemStacks: ItemStack): Map<Int, ItemStack>
            = inventory.removeItem(*itemStacks)

    override fun hasGravity(): Boolean
            = getBukkitPlayer().hasGravity()

    override fun setGravity(gravity: Boolean)
            { getBukkitPlayer().setGravity(gravity) }

    /** significant */

    override fun compareTo(other: MoonLakePlayer): Int
            = name.compareTo(other.name)

    override fun hashCode(): Int
            = name.hashCode()

    override fun equals(other: Any?): Boolean {
        if(other == this)
            return true
        if(other is MoonLakePlayer)
            return getBukkitPlayer() == other.getBukkitPlayer()
        else if(other is Player)
            return getBukkitPlayer() == other
        return false
    }
}
