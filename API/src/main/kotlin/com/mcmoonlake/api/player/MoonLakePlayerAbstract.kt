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

import com.mcmoonlake.api.*
import com.mcmoonlake.api.attribute.AttributeType
import com.mcmoonlake.api.chat.ChatAction
import com.mcmoonlake.api.chat.ChatComponent
import com.mcmoonlake.api.chat.ChatComponentFancy
import com.mcmoonlake.api.depend.DependPlaceholderAPI
import com.mcmoonlake.api.depend.DependPlugins
import com.mcmoonlake.api.depend.DependVaultEconomy
import com.mcmoonlake.api.depend.DependWorldEdit
import com.mcmoonlake.api.effect.EffectType
import com.mcmoonlake.api.packet.PacketOutChat
import com.mcmoonlake.api.region.Region
import com.mcmoonlake.api.service.ServiceForgeHandshake
import com.mcmoonlake.api.utility.MinecraftPlayerMembers
import com.mcmoonlake.api.wrapper.EconomyResponse
import com.mcmoonlake.api.wrapper.Mod
import com.mojang.authlib.GameProfile
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
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.util.Vector
import java.net.InetSocketAddress
import java.util.*

abstract class MoonLakePlayerAbstract(
        val player: Player
) : MoonLakePlayer {

    init {
        if(!player.isOnline)
            throw IllegalOfflinePlayerException(player.name)
    }

    final override val bukkitPlayer: Player
        get() = player

    final override fun spigot(): CommandSender.Spigot
            = throw UnsupportedOperationException()

    final override fun getName(): String
            = bukkitPlayer.name

    final override fun getUniqueId(): UUID
            = bukkitPlayer.uniqueId

    override fun sendMessage(message: String)
            = bukkitPlayer.sendMessage(message)

    override fun sendMessage(messages: Array<out String>)
            = bukkitPlayer.sendMessage(messages)

    override fun isPermissionSet(permission: String): Boolean
            = bukkitPlayer.isPermissionSet(permission)

    override fun isPermissionSet(permission: Permission): Boolean
            = bukkitPlayer.isPermissionSet(permission)

    override fun addAttachment(plugin: Plugin, name: String, value: Boolean): PermissionAttachment
            = bukkitPlayer.addAttachment(plugin, name, value)

    override fun addAttachment(plugin: Plugin): PermissionAttachment
            = bukkitPlayer.addAttachment(plugin)

    override fun addAttachment(plugin: Plugin, name: String, value: Boolean, ticks: Int): PermissionAttachment
            = bukkitPlayer.addAttachment(plugin, name, value, ticks)

    override fun addAttachment(plugin: Plugin, ticks: Int): PermissionAttachment
            = bukkitPlayer.addAttachment(plugin, ticks)

    override fun isOp(): Boolean
            = bukkitPlayer.isOp

    override fun getEffectivePermissions(): MutableSet<PermissionAttachmentInfo>
            = bukkitPlayer.effectivePermissions

    override fun getServer(): Server
            = bukkitPlayer.server

    override fun removeAttachment(permissionAttachment: PermissionAttachment)
            = bukkitPlayer.removeAttachment(permissionAttachment)

    override fun recalculatePermissions()
            = bukkitPlayer.recalculatePermissions()

    override fun hasPermission(permission: String): Boolean
            = bukkitPlayer.hasPermission(permission)

    override fun hasPermission(permission: Permission): Boolean
            = bukkitPlayer.hasPermission(permission)

    override fun setOp(flag: Boolean)
            { bukkitPlayer.isOp = flag }

    override fun hasMetadata(key: String): Boolean
            = bukkitPlayer.hasMetadata(key)

    override fun removeMetadata(key: String, owner: Plugin)
            = bukkitPlayer.removeMetadata(key, owner)

    override fun getMetadata(key: String): MutableList<MetadataValue>
            = bukkitPlayer.getMetadata(key)

    override fun setMetadata(key: String, value: MetadataValue)
            = bukkitPlayer.setMetadata(key, value)

    override val inventory: PlayerInventory
        get() = bukkitPlayer.inventory

    override fun getInventory(): Inventory
            = inventory

    override val entityId: Int
        get() = bukkitPlayer.entityId

    override var displayName: String
        get() = bukkitPlayer.displayName
        set(value) { bukkitPlayer.displayName = value }

    override var tabListName: String
        get() = bukkitPlayer.playerListName
        set(value) { bukkitPlayer.playerListName = value }

    override val profile: GameProfile
        get() = MinecraftPlayerMembers.PROFILE.get(bukkitPlayer) as GameProfile

    override fun hasBeforePlayed(): Boolean
            = bukkitPlayer.hasPlayedBefore()

    override val world: World
        get() = bukkitPlayer.world

    override val location: Location
        get() = bukkitPlayer.location

    override val x: Double
        get() = location.x

    override val y: Double
        get() = location.y

    override val z: Double
        get() = location.z

    override val yaw: Float
        get() = location.yaw

    override val pitch: Float
        get() = location.pitch

    override val blockX: Int
        get() = x.toInt()

    override val blockY: Int
        get() = y.toInt()

    override val blockZ: Int
        get() = z.toInt()

    override val eyeLocation: Location
        get() = bukkitPlayer.eyeLocation

    override val eyeHeight: Double
        get() = bukkitPlayer.eyeHeight

    override fun getEyeHeight(ignoreSneaking: Boolean): Double
            = bukkitPlayer.getEyeHeight(ignoreSneaking)

    override fun getTargetBlock(distance: Int): Block?
            = getTargetBlock(null, distance)

    override fun getTargetBlock(ignoreType: Set<Material>?, distance: Int): Block?
            = bukkitPlayer.getTargetBlock(ignoreType, distance)

    override fun distance(target: Location): Double
            = location.distance(target)

    override fun distanceSq(target: Location): Double
            = location.distanceSquared(target)

    override fun playSound(sound: Sound, volume: Float, pitch: Float)
            = bukkitPlayer.playSound(location, sound, volume, pitch)

    override fun playSound(sound: String, volume: Float, pitch: Float)
            = bukkitPlayer.playSound(location, sound, volume, pitch)

    override fun playNote(instrument: Instrument, note: Note)
            = bukkitPlayer.playNote(location, instrument, note)

    override fun getActivePotionEffects(): Collection<PotionEffect>
            = bukkitPlayer.activePotionEffects

    override fun hasPotionEffect(type: EffectType): Boolean
            = bukkitPlayer.hasPotionEffect(type.cast())

    override fun addPotionEffect(type: EffectType, duration: Int, amplifier: Int, ambient: Boolean, particle: Boolean, color: Color?): Boolean
            = bukkitPlayer.addPotionEffect(PotionEffect(type.cast(), duration, amplifier, ambient, particle))

    override fun removePotionEffect(type: EffectType)
            = bukkitPlayer.removePotionEffect(type.cast())

    override fun clearPotionEffects()
            = getActivePotionEffects().map { it -> EffectType.fromName(it.type.name) }.filter { hasPotionEffect(it) }.forEach { removePotionEffect(it) }

    override var velocity: Vector
        get() = bukkitPlayer.velocity
        set(value) { bukkitPlayer.velocity = value }

    override var noDamageTicks: Int
        get() = bukkitPlayer.noDamageTicks
        set(value) { bukkitPlayer.noDamageTicks = value }

    override fun onEject(): Boolean
            = bukkitPlayer.eject()

    override fun createExplosion(power: Float)
            = createExplosion(power, true)

    override fun createExplosion(power: Float, fire: Boolean)
            = createExplosion(power, fire, true)

    override fun createExplosion(power: Float, fire: Boolean, breakBlock: Boolean)
            { world.createExplosion(x, y, z, power, fire, breakBlock) }

    override var time: Long
        get() = bukkitPlayer.playerTime
        set(value) { setTime(value, true) }

    override fun setTime(time: Long, relative: Boolean)
            = bukkitPlayer.setPlayerTime(time, relative)

    override var weather: WeatherType
        get() = bukkitPlayer.playerWeather
        set(value) { bukkitPlayer.playerWeather = value }

    override fun resetTime()
            = bukkitPlayer.resetPlayerTime()

    override fun resetWeather()
            = bukkitPlayer.resetPlayerWeather()

    override val direction: Vector
        get() = location.direction

    override val lastDamage: Double
        get() = bukkitPlayer.lastDamage

    override val lastDamageCause: EntityDamageEvent
        get() = bukkitPlayer.lastDamageCause

    override fun chat(message: String)
            = bukkitPlayer.chat(message)

    override fun send(message: String)
            = bukkitPlayer.sendMessage(message.toColor())

    override fun send(vararg messages: String)
            = bukkitPlayer.sendMessage(messages.toColor())

    override fun send(message: String, vararg args: Any)
            = bukkitPlayer.sendMessage(message.format(args).toColor())

    override fun send(component: ChatComponent, action: ChatAction)
            = PacketOutChat(component, action).send(bukkitPlayer)

    override fun send(vararg component: ChatComponent)
            = component.forEach { send(it) }

    override fun send(componentFancy: ChatComponentFancy, action: ChatAction)
            = send(componentFancy.build(), action)

    override fun onKick()
            = onKick("None")

    override fun onKick(reason: String)
            = bukkitPlayer.kickPlayer(reason)

    override var health: Double
        get() = bukkitPlayer.health
        set(value) { bukkitPlayer.health = value }

    override fun giveHealth(value: Double) = (health + value >= maxHealth).let {
        when(it) {
            true -> health = maxHealth
            else -> health += value
        }
    }

    override fun takeHealth(value: Double) = (health - value <= .0).let {
        when(it) {
            true -> health = .0
            else -> health -= value
        }
    }

    override var maxHealth: Double
        get() = getAttribute(AttributeType.MAX_HEALTH).value
        set(value) { getAttribute(AttributeType.MAX_HEALTH).baseValue = value }

    override fun resetMaxHealth()
            { maxHealth = AttributeType.MAX_HEALTH.def }

    override var exp: Float
        get() = bukkitPlayer.exp
        set(value) { bukkitPlayer.exp = value }

    override fun giveExp(value: Float)
            { exp += value }

    override fun takeExp(value: Float)
            { exp -= value }

    override var level: Int
        get() = bukkitPlayer.level
        set(value) { bukkitPlayer.level = value }

    override fun giveLevel(value: Int)
            { level += value }

    override fun takeLevel(value: Int)
            { level -= value }

    override var flySpeed: Float
        get() = bukkitPlayer.flySpeed
        set(value) { bukkitPlayer.flySpeed = value }

    override var walkSpeed: Float
        get() = bukkitPlayer.walkSpeed
        set(value) { bukkitPlayer.walkSpeed = value }

    override var foodLevel: Int
        get() = bukkitPlayer.foodLevel
        set(value) { bukkitPlayer.foodLevel = value }

    override fun isFlying(): Boolean
            = bukkitPlayer.isFlying

    override var isAllowFly: Boolean
        get() = bukkitPlayer.allowFlight
        set(value) { bukkitPlayer.allowFlight = value }

    override fun damage(value: Double)
            = bukkitPlayer.damage(value)

    override fun damage(value: Double, damager: LivingEntity)
            = bukkitPlayer.damage(value, damager)

    override fun teleport(location: Location): Boolean
            = bukkitPlayer.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN)

    override fun teleport(player: Player): Boolean
            = teleport(player.location)

    override fun teleport(player: MoonLakePlayer): Boolean
            = teleport(player.location)

    override fun teleport(x: Double, y: Double, z: Double): Boolean
            = teleport(x, y, z, yaw, pitch)

    override fun teleport(x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Boolean
            = teleport(world, x, y, z, yaw, pitch)

    override fun teleport(world: World, x: Double, y: Double, z: Double): Boolean
            = teleport(world, x, y, z, yaw, pitch)

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

    override var isCanPickupItems: Boolean
        get() = bukkitPlayer.canPickupItems
        set(value) { bukkitPlayer.canPickupItems = value }

    override var fallDistance: Float
        get() = bukkitPlayer.fallDistance
        set(value) { bukkitPlayer.fallDistance = value }

    override var gameMode: GameMode
        get() = bukkitPlayer.gameMode
        set(value) { bukkitPlayer.gameMode = value }

    override val isSurvivalMode: Boolean
        get() = gameMode == GameMode.SURVIVAL

    override val isCreativeMode: Boolean
        get() = gameMode == GameMode.CREATIVE

    override val isSpectatorMode: Boolean
        get() = gameMode == GameMode.SPECTATOR

    override val isAdventureMode: Boolean
        get() = gameMode == GameMode.ADVENTURE

    override val isSneaking: Boolean
        get() = bukkitPlayer.isSneaking

    override val isSprinting: Boolean
        get() = bukkitPlayer.isSprinting

    override var scoreboard: Scoreboard
        get() = bukkitPlayer.scoreboard
        set(value) { bukkitPlayer.scoreboard = value }

    override fun <T : Projectile> launchProjectile(projectile: Class<T>): T
            = bukkitPlayer.launchProjectile(projectile)

    override fun <T : Projectile> launchProjectile(projectile: Class<T>, vector: Vector): T
            = bukkitPlayer.launchProjectile(projectile, vector)

    override fun performCommand(command: String): Boolean = command.let {
        when(it[0] == '/') {
            true -> bukkitPlayer.performCommand(it.substring(1))
            else -> bukkitPlayer.performCommand(it)
        }
    }

    override var compassTarget: Location?
        get() = bukkitPlayer.compassTarget
        set(value) { bukkitPlayer.compassTarget = value }

    override var bedSpawnLocation: Location?
        get() = bukkitPlayer.bedSpawnLocation
        set(value) { setBedSpawnLocation(value, false) }

    override fun setBedSpawnLocation(target: Location?, force: Boolean)
            = bukkitPlayer.setBedSpawnLocation(target, force)

    override fun getNearbyEntities(radius: Double): List<Entity>
            = getNearbyEntities(radius, radius, radius)

    override fun getNearbyLivingEntities(radius: Double): List<LivingEntity>
            = getNearbyLivingEntities(radius, radius, radius)

    override fun getNearbyPlayers(radius: Double): List<MoonLakePlayer>
            = getNearbyPlayers(radius, radius, radius)

    override fun getNearbyEntities(x: Double, y: Double, z: Double): List<Entity>
            = bukkitPlayer.getNearbyEntities(x, y, z)

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

    override val ip: String
        get() = address.address?.hostName ?: "127.0.0.1"

    override val port: Int
        get() = address.port

    override val address: InetSocketAddress
        get() = bukkitPlayer.address

    override val enderChest: Inventory
        get() = bukkitPlayer.enderChest

    override fun updateInventory()
            = bukkitPlayer.updateInventory()

    override fun closeInventory()
            = bukkitPlayer.closeInventory()

    override fun clearInventory()
            = inventory.clear()

    @Suppress("DEPRECATION")
    override var itemInHand: ItemStack
        get() = bukkitPlayer.itemInHand
        set(value) { bukkitPlayer.itemInHand = value }

    override var itemOnCursor: ItemStack
        get() = bukkitPlayer.itemOnCursor
        set(value) { bukkitPlayer.itemOnCursor = value }

    override var openInventory: InventoryView
        get() = bukkitPlayer.openInventory
        set(value) { bukkitPlayer.openInventory(value) }

    override fun addItems(vararg itemStacks: ItemStack): Map<Int, ItemStack>
            = inventory.addItem(*itemStacks)

    override fun removeItems(vararg itemStacks: ItemStack): Map<Int, ItemStack>
            = inventory.removeItem(*itemStacks)

    override var isNoGravity: Boolean
        get() = bukkitPlayer.hasGravity()
        set(value) { bukkitPlayer.setGravity(value) }

    override fun sendTitle(title: String, subTitle: String?, fadeIn: Int, stay: Int, fadeOut: Int)
            = bukkitPlayer.sendPacketTitle(title, subTitle, fadeIn, stay, fadeOut)

    override fun sendTitle(title: ChatComponent, subTitle: ChatComponent?, fadeIn: Int, stay: Int, fadeOut: Int)
            = bukkitPlayer.sendPacketTitle(title, subTitle, fadeIn, stay, fadeOut)

    override fun sendTitleReset()
            = bukkitPlayer.sendPacketTitleReset()

    /** forge handshake service */

    private val forgeHandshakeService: ServiceForgeHandshake
        get() = getMoonLake().serviceManager.getService(ServiceForgeHandshake::class.java)

    override fun getForgeMods(): Array<Mod>?
            = forgeHandshakeService.getMods(this)

    override fun resetForgeMods(): Boolean
            = forgeHandshakeService.resetMods(this)

    /** depend plugin */

    private val placeholderAPI: DependPlaceholderAPI
        get() = DependPlugins.of(DependPlaceholderAPI::class.java)

    override fun setPlaceholders(text: String): String
            = placeholderAPI.setPlaceholders(bukkitPlayer, text)

    override fun setPlaceholdersBracket(text: String): String
            = placeholderAPI.setBracketPlaceholders(bukkitPlayer, text)

    override fun setPlaceholdersRelational(target: MoonLakePlayer, text: String): String
            = placeholderAPI.setRelationalPlaceholders(bukkitPlayer, target.bukkitPlayer, text)

    private val vaultEconomy: DependVaultEconomy
        get() = DependPlugins.of(DependVaultEconomy::class.java)

    override fun formatEconomy(value: Double): String
            = vaultEconomy.format(value)

    override fun hasEconomyAccount(world: String?): Boolean
            = vaultEconomy.hasAccount(bukkitPlayer, world)

    override fun createEconomyAccount(world: String?): Boolean
            = vaultEconomy.createAccount(bukkitPlayer, world)

    override fun getEconomyBalance(world: String?): Double
            = vaultEconomy.getBalance(bukkitPlayer, world)

    override fun hasEconomyBalance(value: Double, world: String?): Boolean
            = vaultEconomy.hasBalance(bukkitPlayer, value, world)

    override fun withdrawEconomy(value: Double, world: String?): EconomyResponse
            = vaultEconomy.withdraw(bukkitPlayer, value, world)

    override fun depositEconomy(value: Double, world: String?): EconomyResponse
            = vaultEconomy.deposit(bukkitPlayer, value, world)

    override fun isEconomyBankOwner(name: String): EconomyResponse
            = vaultEconomy.isBankOwner(name, bukkitPlayer)

    override fun isEconomyBankMember(name: String): EconomyResponse
            = vaultEconomy.isBankMember(name, bukkitPlayer)

    private val worldEdit: DependWorldEdit
        get() = DependPlugins.of(DependWorldEdit::class.java)

    override fun getWorldEditSelection(): Region?
            = worldEdit.getSelection(bukkitPlayer)

    override fun setWorldEditSelection(region: Region)
            = worldEdit.setSelection(bukkitPlayer, region)

    /** significant */

    final override fun compareTo(other: MoonLakePlayer): Int
            = name.compareTo(other.name)

    final override fun hashCode(): Int
            = name.hashCode()

    final override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is MoonLakePlayer)
            return bukkitPlayer == other.bukkitPlayer
        else if(other is Player)
            return bukkitPlayer == other
        return false
    }

    override fun toString(): String {
        return "MoonLakePlayer(player=${player.name})"
    }
}
