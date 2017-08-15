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

package com.minecraft.moonlake.core.player

import com.minecraft.moonlake.api.player.IllegalOfflinePlayerException
import com.minecraft.moonlake.api.player.MoonLakePlayer
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.metadata.MetadataValue
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachment
import org.bukkit.permissions.PermissionAttachmentInfo
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector
import java.util.*

abstract class MoonLakePlayerAbstract : MoonLakePlayer {

    /** member */

    private val player: Player

    /** constructor */

    @Throws(IllegalOfflinePlayerException::class)
    constructor(uuid: UUID) : this(Bukkit.getPlayer(uuid))

    @Throws(IllegalOfflinePlayerException::class)
    constructor(player: Player) {
        if(player == null || !player.isOnline)
            throw IllegalOfflinePlayerException(player.name)
        this.player = player
    }

    /** function */

    override final fun getBukkitPlayer(): Player
            = player

    override fun getName(): String
            = getBukkitPlayer().name

    override fun getUniqueId(): UUID
            = getBukkitPlayer().uniqueId

    override fun sendMessage(message: String)
            = getBukkitPlayer().sendMessage(message)

    override fun sendMessage(messages: Array<out String>)
            = getBukkitPlayer().sendMessage(messages)

    override fun spigot(): CommandSender.Spigot
            = throw UnsupportedOperationException()

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

    override fun getInventory(): Inventory
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
