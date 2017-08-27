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

package com.minecraft.moonlake.api.player

import com.minecraft.moonlake.api.attribute.Attributable
import com.minecraft.moonlake.api.version.IllegalBukkitVersionException
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.entity.*
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.inventory.*
import org.bukkit.metadata.MetadataValue
import org.bukkit.metadata.Metadatable
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.util.Vector
import java.net.InetSocketAddress

interface MoonLakePlayer : AnimalTamer, Attributable, CommandSender, Metadatable, InventoryHolder, Comparable<MoonLakePlayer> {

    /** Base Function */

    fun getBukkitPlayer(): Player

    fun getEntityId(): Int

    fun getDisplayName(): String

    fun setDisplayName(displayName: String)

    fun getTabListName(): String

    fun setTabListName(listName: String)

    fun hasBeforePlayed(): Boolean

    fun getWorld(): World

    fun getLocation(): Location

    fun getX(): Double

    fun getY(): Double

    fun getZ(): Double

    fun getYaw(): Float

    fun getPitch(): Float

    fun getBlockX(): Int

    fun getBlockY(): Int

    fun getBlockZ(): Int

    fun getEyeLocation(): Location

    fun getEyeHeight(): Double

    fun getEyeHeight(ignoreSneaking: Boolean): Double

    fun getTargetBlock(distance: Int): Block?

    fun getTargetBlock(ignoreType: Set<Material>?, distance: Int): Block?

    fun distance(target: Location): Double

    fun distanceSq(target: Location): Double

    fun playSound(sound: Sound, volume: Float, pitch: Float)

    fun playSound(sound: String, volume: Float, pitch: Float)

    fun playNote(instrument: Instrument, note: Note)

    fun getActivePotionEffects(): Collection<PotionEffect>

    fun hasPotionEffect(type: PotionEffectType): Boolean

    fun removePotionEffect(type: PotionEffectType)

    fun clearPotionEffects()

    fun getVelocity(): Vector

    fun setVelocity(vector: Vector)

    fun getNoDamageTicks(): Int

    fun setNoDamageTicks(ticks: Int)

    fun onEject(): Boolean

    fun createExplosion(power: Float)

    fun createExplosion(power: Float, fire: Boolean)

    fun createExplosion(power: Float, fire: Boolean, breakBlock: Boolean)

    fun getTime(): Long

    fun setTime(time: Long)

    fun setTime(time: Long, relative: Boolean)

    fun getWeather(): WeatherType

    fun setWeather(type: WeatherType)

    fun resetTime()

    fun resetWeather()

    fun getDirection(): Vector

    fun getLastDamage(): Double

    fun getLastDamageCause(): EntityDamageEvent

    fun chat(message: String)

    fun send(message: String)

    fun send(vararg messages: String)

    fun send(message: String, vararg args: Any)

    fun onKick()

    fun onKick(reason: String)

    fun getHealth(): Double

    fun setHealth(health: Double)

    fun giveHealth(value: Double)

    fun takeHealth(value: Double)

    fun getMaxHealth(): Double

    fun setMaxHealth(maxHealth: Double)

    fun resetMaxHealth()

    fun getExp(): Float

    fun setExp(exp: Float)

    fun giveExp(value: Float)

    fun takeExp(value: Float)

    fun getLevel(): Int

    fun setLevel(level: Int)

    fun giveLevel(value: Int)

    fun takeLevel(value: Int)

    fun getFlySpeed(): Float

    fun setFlySpeed(flySpeed: Float)

    fun getWalkSpeed(): Float

    fun setWalkSpeed(walkSpeed: Float)

    fun getFoodLevel(): Int

    fun setFoodLevel(foodLevel: Int)

    fun isFlying(): Boolean

    fun isAllowFly(): Boolean

    fun setAllowFly(allowFly: Boolean)

    fun damage(value: Double)

    fun damage(value: Double, damager: LivingEntity)

    fun teleport(location: Location): Boolean

    fun teleport(player: Player): Boolean

    fun teleport(player: MoonLakePlayer): Boolean

    fun teleport(x: Double, y: Double, z: Double): Boolean

    fun teleport(x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Boolean

    fun teleport(world: World, x: Double, y: Double, z: Double): Boolean

    fun teleport(world: World, x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Boolean

    fun teleport(world: String, x: Double, y: Double, z: Double): Boolean

    fun teleport(world: String, x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Boolean

    fun teleportSpawn(world: World): Boolean

    fun teleportSpawn(world: String): Boolean

    fun isCanPickupItems(): Boolean

    fun setCanPickupItems(canPickupItems: Boolean)

    fun getFallDistance(): Float

    fun setFallDistance(fallDistance: Float)

    fun getGameMode(): GameMode

    fun setGameMode(gameMode: GameMode)

    fun isSurvivalMode(): Boolean

    fun isCreativeMode(): Boolean

    fun isSpectatorMode(): Boolean

    fun isAdventureMode(): Boolean

    fun isSneaking(): Boolean

    fun isSprinting(): Boolean

    fun addPotionEffect(type: PotionEffectType, amplifier: Int, duration : Int): Boolean

    fun addPotionEffect(type: PotionEffectType, amplifier: Int, duration : Int, ambient: Boolean): Boolean

    fun addPotionEffect(type: PotionEffectType, amplifier: Int, duration : Int, ambient: Boolean, particles: Boolean): Boolean

    fun addPotionEffect(type: PotionEffectType, amplifier: Int, duration : Int, ambient: Boolean, particles: Boolean, color: Color): Boolean

    fun getScoreboard(): Scoreboard

    fun setScoreboard(scoreboard: Scoreboard)

    fun <T: Projectile> launchProjectile(projectile: Class<T>): T

    fun <T: Projectile> launchProjectile(projectile: Class<T>, vector: Vector): T

    fun performCommand(command: String): Boolean

    fun getCompassTarget(): Location?

    fun setCompassTarget(target: Location)

    fun getBedSpawnLocation(): Location?

    fun setBedSpawnLocation(target: Location, force: Boolean = false)

    fun getNearbyEntities(radius: Double): List<Entity>

    fun getNearbyLivingEntities(radius: Double): List<LivingEntity>

    fun getNearbyPlayers(radius: Double): List<MoonLakePlayer>

    fun getNearbyEntities(x: Double, y: Double, z: Double): List<Entity>

    fun getNearbyLivingEntities(x: Double, y: Double, z: Double): List<LivingEntity>

    fun getNearbyPlayers(x: Double, y: Double, z: Double): List<MoonLakePlayer>

    fun <T: Entity> getNearbyEntities(entityClass: Class<T>, radius: Double): List<T>

    fun <T: Entity> getNearbyEntities(entityClass: Class<T>, x: Double, y: Double, z: Double): List<T>

    fun getMetadataFirst(key: String): MetadataValue?

    fun getMetadataLast(key: String): MetadataValue?

    fun getPing(): Int

    fun getIp(): String

    fun getPort(): Int

    fun getAddress(): InetSocketAddress

    override fun getInventory(): PlayerInventory

    fun getEnderChest(): Inventory

    fun updateInventory()

    fun closeInventory()

    fun clearInventory()

    fun getItemInHand(): ItemStack

    fun setItemInHand(itemStack: ItemStack?)

    fun getItemOnCursor(): ItemStack

    fun setItemOnCursor(itemStack: ItemStack?)

    fun getOpenInventory(): InventoryView

    fun openInventory(inventory: Inventory): InventoryView

    fun addItems(vararg itemStacks: ItemStack): Map<Int, ItemStack>

    fun removeItems(vararg itemStacks: ItemStack): Map<Int, ItemStack>

    fun hasGravity(): Boolean

    fun setGravity(gravity: Boolean)

    fun getLocale(): String

    /** Minecraft Bukkit 1.9 */

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.9
     */
    @Throws(IllegalBukkitVersionException::class)
    fun isInvulnerable(): Boolean

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.9
     */
    @Throws(IllegalBukkitVersionException::class)
    fun setInvulnerable(invulnerable: Boolean)

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.9
     */
    @Throws(IllegalBukkitVersionException::class)
    fun isGlowing(): Boolean

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.9
     */
    @Throws(IllegalBukkitVersionException::class)
    fun setGlowing(glowing: Boolean)

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.9
     */
    @Throws(IllegalBukkitVersionException::class)
    fun isGliding(): Boolean

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.9
     */
    @Throws(IllegalBukkitVersionException::class)
    fun setGliding(gliding: Boolean)

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.9
     */
    @Throws(IllegalBukkitVersionException::class)
    fun isSilent(): Boolean

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.9
     */
    @Throws(IllegalBukkitVersionException::class)
    fun setSilent(silent: Boolean)

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.9
     */
    @Throws(IllegalBukkitVersionException::class)
    fun getSpectatorTarget(): Entity

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.9
     */
    @Throws(IllegalBukkitVersionException::class)
    fun setSpectatorTarget(target: Entity)

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.9
     */
    @Throws(IllegalBukkitVersionException::class)
    fun getItemInMainHand(): ItemStack

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.9
     */
    @Throws(IllegalBukkitVersionException::class)
    fun setItemInMainHand(itemStack: ItemStack?)

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.9
     */
    @Throws(IllegalBukkitVersionException::class)
    fun getItemInOffHand(): ItemStack

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.9
     */
    @Throws(IllegalBukkitVersionException::class)
    fun setItemInOffHand(itemStack: ItemStack?)

    /** Minecraft Bukkit 1.10 */

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.10
     */
    @Throws(IllegalBukkitVersionException::class)
    fun stopSound(sound: Sound)

    /**
     * @throws IllegalBukkitVersionException if bukkit version < 1.10
     */
    @Throws(IllegalBukkitVersionException::class)
    fun stopSound(sound: String)
}
