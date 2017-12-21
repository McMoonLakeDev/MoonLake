/*
 * Copyright (C) 2016-Present The MoonLake (mcmoonlake@hotmail.com)
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

package com.mcmoonlake.api.player

import com.mcmoonlake.api.attribute.Attributable
import com.mcmoonlake.api.attribute.Attribute
import com.mcmoonlake.api.attribute.AttributeType
import com.mcmoonlake.api.chat.ChatAction
import com.mcmoonlake.api.chat.ChatComponent
import com.mcmoonlake.api.chat.ChatComponentFancy
import com.mcmoonlake.api.depend.DependPlayer
import com.mcmoonlake.api.effect.EffectType
import com.mcmoonlake.api.service.ServiceException
import com.mcmoonlake.api.version.IllegalBukkitVersionException
import com.mcmoonlake.api.wrapper.Mod
import com.mojang.authlib.GameProfile
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.entity.*
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.inventory.*
import org.bukkit.metadata.MetadataValue
import org.bukkit.metadata.Metadatable
import org.bukkit.potion.PotionEffect
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.util.Vector
import java.net.InetSocketAddress

interface MoonLakePlayer : AnimalTamer, Attributable, CommandSender, DependPlayer, InventoryHolder, Metadatable, Comparable<MoonLakePlayer> {

    /** Base Function */

    val bukkitPlayer: Player

    /**
     * Gets the attribute instance of the this player.
     *
     * @throws IllegalBukkitVersionException If the server does not support attribute type.
     * @throws NoSuchElementException If the player does not have this attribute type.
     */
    @Throws(IllegalBukkitVersionException::class, NoSuchElementException::class)
    override fun getAttribute(type: AttributeType): Attribute

    val entityId: Int

    var displayName: String

    var tabListName: String

    val profile: GameProfile

    fun hasBeforePlayed(): Boolean

    val world: World

    val location: Location

    val x: Double

    val y: Double

    val z: Double

    val yaw: Float

    val pitch: Float

    val blockX: Int

    val blockY: Int

    val blockZ: Int

    val eyeLocation: Location

    val eyeHeight: Double

    fun getEyeHeight(ignoreSneaking: Boolean): Double

    fun getTargetBlock(distance: Int): Block?

    fun getTargetBlock(ignoreType: Set<Material>?, distance: Int): Block?

    fun distance(target: Location): Double

    fun distanceSq(target: Location): Double

    fun playSound(sound: Sound, volume: Float, pitch: Float)

    fun playSound(sound: String, volume: Float, pitch: Float)

    fun playNote(instrument: Instrument, note: Note)

    fun getActivePotionEffects(): Collection<PotionEffect>

    fun hasPotionEffect(type: EffectType): Boolean

    fun addPotionEffect(type: EffectType, duration: Int, amplifier: Int, ambient: Boolean = true, particle: Boolean = true, color: Color? = null): Boolean

    fun removePotionEffect(type: EffectType)

    fun clearPotionEffects()

    var velocity: Vector

    var noDamageTicks: Int

    fun onEject(): Boolean

    fun createExplosion(power: Float)

    fun createExplosion(power: Float, fire: Boolean)

    fun createExplosion(power: Float, fire: Boolean, breakBlock: Boolean)

    var time: Long

    fun setTime(time: Long, relative: Boolean)

    var weather: WeatherType

    fun resetTime()

    fun resetWeather()

    val direction: Vector

    val lastDamage: Double

    val lastDamageCause: EntityDamageEvent

    fun chat(message: String)

    fun send(message: String)

    fun send(vararg messages: String)

    fun send(message: String, vararg args: Any)

    fun send(component: ChatComponent, action: ChatAction = ChatAction.CHAT)

    fun send(vararg component: ChatComponent)

    fun send(componentFancy: ChatComponentFancy, action: ChatAction = ChatAction.CHAT)

    fun onKick()

    fun onKick(reason: String)

    var health: Double

    fun giveHealth(value: Double)

    fun takeHealth(value: Double)

    var maxHealth: Double

    fun resetMaxHealth()

    var exp: Float

    fun giveExp(value: Float)

    fun takeExp(value: Float)

    var level: Int

    fun giveLevel(value: Int)

    fun takeLevel(value: Int)

    var flySpeed: Float

    var walkSpeed: Float

    var foodLevel: Int

    fun isFlying(): Boolean

    var isAllowFly: Boolean

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

    var isCanPickupItems: Boolean

    var fallDistance: Float

    var gameMode: GameMode

    val isSurvivalMode: Boolean

    val isCreativeMode: Boolean

    val isSpectatorMode: Boolean

    val isAdventureMode: Boolean

    val isSneaking: Boolean

    val isSprinting: Boolean

    var scoreboard: Scoreboard

    fun <T: Projectile> launchProjectile(projectile: Class<T>): T

    fun <T: Projectile> launchProjectile(projectile: Class<T>, vector: Vector): T

    fun performCommand(command: String): Boolean

    var compassTarget: Location?

    var bedSpawnLocation: Location?

    fun setBedSpawnLocation(target: Location?, force: Boolean = false)

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

    val ping: Int

    val ip: String

    val port: Int

    val address: InetSocketAddress

    val inventory: PlayerInventory

    val enderChest: Inventory

    fun updateInventory()

    fun closeInventory()

    fun clearInventory()

    var itemInHand: ItemStack

    var itemOnCursor: ItemStack

    var openInventory: InventoryView

    fun addItems(vararg itemStacks: ItemStack): Map<Int, ItemStack>

    fun removeItems(vararg itemStacks: ItemStack): Map<Int, ItemStack>

    var isNoGravity: Boolean

    val locale: String

    fun sendTitle(title: String, subTitle: String? = null, fadeIn: Int = 10, stay: Int = 70, fadeOut: Int = 20)

    fun sendTitle(title: ChatComponent, subTitle: ChatComponent? = null, fadeIn: Int = 10, stay: Int = 70, fadeOut: Int = 20)

    fun sendTitleReset()

    @Throws(ServiceException::class)
    fun getForgeMods(): Array<Mod>?

    @Throws(ServiceException::class)
    fun resetForgeMods(): Boolean

    /** Minecraft Bukkit 1.9 */

    var isInvulnerable: Boolean

    var isGlowing: Boolean

    var isGliding: Boolean

    var isSilent: Boolean

    var spectatorTarget: Entity?

    var itemInMainHand: ItemStack

    var itemInOffHand: ItemStack

    fun setCooldown(type: Material, ticks: Int)

    fun getCooldown(type: Material): Int

    fun hasCooldown(type: Material): Boolean

    /** Minecraft Bukkit 1.10 */

    fun stopSound(sound: Sound)

    fun stopSound(sound: String)
}
