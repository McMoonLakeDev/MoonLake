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

package com.mcmoonlake.api.particle

import com.mcmoonlake.api.currentMCVersion
import com.mcmoonlake.api.isOrLater
import com.mcmoonlake.api.packet.PacketOutParticles
import com.mcmoonlake.api.version.IllegalBukkitVersionException
import com.mcmoonlake.api.version.MinecraftVersion
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.util.Vector

/**
 * @author # [DarkBlade12](https://github.com/DarkBlade12) by origin, [lgou2w](https://github.com/lgou2w) by modified.
 */
enum class Particle {

    /**
     * Particle: Explosion, Version: All | Requires: Directional (粒子效果: 普通爆炸, 版本: 全版本 | 需求: 矢量方向)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#explode)
     */
    EXPLOSION_NORMAL("explode", 0, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Large Explode, Version: All (粒子效果: 大型爆炸, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#largeexplode)
     */
    EXPLOSION_LARGE("largeexplode", 1),

    /**
     * Particle: Huge Explosion, Version: All (粒子效果: 巨大爆炸, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#hugeexplosion)
     */
    EXPLOSION_HUGE("hugeexplosion", 2),

    /**
     * Particle: Fireworks Spark, Version: All (粒子效果: 烟花尾迹, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#fireworksSpark)
     */
    FIREWORKS_SPARK("fireworksSpark", 3, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Water Bubble, Version: All | Requires: Directional, Water (粒子效果: 水泡, 版本: 全版本 | 需求: 矢量方向, 需求: 水源)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#bubble)
     */
    WATER_BUBBLE("bubble", 4, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_WATER),

    /**
     * Particle: Water Splash, Version: All | Requires: Directional (粒子效果: 水花溅起, 版本: 全版本 | 需求: 矢量方向)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#splash)
     */
    WATER_SPLASH("splash", 5, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Water Wake, Version: All | Requires: Directional (粒子效果: 水尾波, 版本: 全版本 | 需求: 矢量方向)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#)
     */
    WATER_WAKE("wake", 6, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Suspended, Version: All | Requires: Water (粒子效果: 水下颗粒, 版本: 全版本 | 需求: 水源)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#suspended)
     */
    SUSPENDED("suspended", 7, ParticleProperty.REQUIRES_WATER),

    /**
     * Particle: Suspended Depth, Version: All | Requires: Directional  (粒子效果: 虚空颗粒, 版本: 全版本 | 需求: 矢量方向)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#depthSuspended)
     */
    SUSPENDED_DEPTH("depthSuspended", 8, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Crit, Version: All | Requires: Directional (粒子效果: 暴击, 版本: 全版本 | 需求: 矢量方向)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#crit)
     */
    CRIT("crit", 9, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Magic Crit, Version: All | Requires: Directional (粒子效果: 魔法暴击, 版本: 全版本 | 需求: 矢量方向)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#)
     */
    CRIT_MAGIC("magicCrit", 10, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Smoke, Version: All | Requires: Directional (粒子效果: 烟雾, 版本: 全版本 | 需求: 矢量方向)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#smoke)
     */
    SMOKE_NORMAL("smoke", 11, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Large Smoke, Version: All | Requires: Directional (粒子效果: 大型烟雾, 版本: 全版本 | 需求: 矢量方向)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#largesmoke)
     */
    SMOKE_LARGE("largesmoke", 12, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Spell, Version: All (粒子效果: 药水符咒, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#spell)
     */
    SPELL("spell", 13),

    /**
     * Particle: Instant Spell, Version: All (粒子效果: 瞬间药水符咒, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#instantSpell)
     */
    SPELL_INSTANT("instantSpell", 14),

    /**
     * Particle: Mob Spell, Version: All | Requires: Color (粒子效果: 实体药水符咒, 版本: 全版本 | 需求: 颜色)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#mobSpell)
     */
    SPELL_MOB("mobSpell", 15, ParticleProperty.COLOR),

    /**
     * Particle: Mob Spell Ambient, Version: All | Requires: Color (粒子效果: 实体药水符咒环境, 版本: 全版本 | 需求: 颜色)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#mobSpellAmbient)
     */
    SPELL_MOB_AMBIENT("mobSpellAmbient", 16, ParticleProperty.COLOR),

    /**
     * Particle: Witch Magic, Version: All (粒子效果: 女巫魔法, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#witchMagic)
     */
    SPELL_WITCH("witchMagic", 17),

    /**
     * Particle: Drip Water, Version: All (粒子效果: 滴水, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#dripWater)
     */
    DRIP_WATER("dripWater", 18),

    /**
     * Particle: Drip Lava, Version: All (粒子效果: 滴岩浆, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#dripLava)
     */
    DRIP_LAVA("dripLava", 19),

    /**
     * Particle: Villager Angry, Version: All (粒子效果: 村民生气, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#angryVillager)
     */
    VILLAGER_ANGRY("angryVillager", 20),

    /**
     * Particle: Villager Happy, Version: All | Requires: Directional (粒子效果: 村民高兴, 版本: 全版本 | 需求: 矢量方向)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#happyVillager)
     */
    VILLAGER_HAPPY("happyVillager", 21, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Town Aura, Version: All (粒子效果: 菌丝颗粒, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#townAura)
     */
    TOWN_AURA("townAura", 22),

    /**
     * Particle: Note, Version: All | Requires: Color (粒子效果: 音符, 版本: 全版本 | 需求: 颜色)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#note)
     */
    NOTE("note", 23, ParticleProperty.COLOR),

    /**
     * Particle: Portal, Version: All | Requires: Directional (粒子效果: 传送门, 版本: 全版本 | 需求: 矢量方向)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#portal)
     */
    PORTAL("portal", 24, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Enchantment Table, Version: All | Requires: Directional (粒子效果: 附魔台, 版本: 全版本 | 需求: 矢量方向)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#enchantmenttable)
     */
    ENCHANTMENT_TABLE("enchantmenttable", 25, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Flame, Version: All | Requires: Directional (粒子效果: 火焰, 版本: 全版本 | 需求: 矢量方向)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#flame)
     */
    FLAME("flame", 26, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Lava, Version: All (粒子效果: 岩浆, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#lava)
     */
    LAVA("lava", 27),

    /**
     * Particle: Footstep, Version: All (粒子效果: 脚印, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#footstep)
     */
    FOOTSTEP("footstep", 28),

    /**
     * Particle: Cloud, Version: All | Requires: Directional (粒子效果: 云, 版本: 全版本 | 需求: 矢量方向)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#cloud)
     */
    CLOUD("cloud", 29, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Red Dust, Version: All | Requires: Color (粒子效果: 红尘, 版本: 全版本 | 需求: 颜色)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#reddust)
     */
    RED_DUST("reddust", 30, ParticleProperty.COLOR),

    /**
     * Particle: Snow Ball, Version: All (粒子效果: 雪球碎裂, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#snowballpoof)
     */
    SNOWBALL("snowballpoof", 31),

    /**
     * Particle: Snow Shovel, Version: All | Requires: Directional (粒子效果: 雪颗粒, 版本: 全版本 | 需求: 矢量方向)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#snowshovel)
     */
    SNOW_SHOVEL("snowshovel", 32, ParticleProperty.DIRECTIONAL),

    /**
     * Particle: Slime, Version: All (粒子效果: 史莱姆, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#slime)
     */
    SLIME("slime", 33),

    /**
     * Particle: Heart, Version: All (粒子效果: 爱心, 版本: 全版本)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#heart)
     */
    HEART("heart", 34),

    /**
     * Particle: Barrier, Version: 1.8+  (粒子效果: 屏障, 版本: 1.8+)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#barrier)
     */
    BARRIER("barrier", 35, MinecraftVersion.V1_8),

    /**
     * Particle: Item Crack, Version: All | Requires: Directional, Data (粒子效果: 物品碎裂, 版本: 全版本 | 需求: 矢量方向, 需求: 数据)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#iconcrack)
     */
    ITEM_CRACK("iconcrack", 36, 2, null, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),

    /**
     * Particle: Block Crack, Version: All | Requires: Data (粒子效果: 方块碎裂, 版本: 全版本 | 需求: 数据)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#blockcrack)
     */
    BLOCK_CRACK("blockcrack", 37, 1, null, ParticleProperty.REQUIRES_DATA),

    /**
     * Particle: Block Dust, Version: All | Requires: Directional, Data (粒子效果: 方块尘, 版本: 全版本 | 需求: 矢量方向, 需求: 数据)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#blockdust)
     */
    BLOCK_DUST("blockdust", 38, 1, null, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),

    /**
     * Particle: Water Drop, Version: 1.8+ (粒子效果: 雨滴, 版本: 1.8+)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#droplet)
     */
    WATER_DROP("droplet", 39, MinecraftVersion.V1_8),

    /**
     * Particle: Item Take, Version: 1.8+ (粒子效果: 物品获取, 版本: 1.8+)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#take)
     */
    ITEM_TAKE("take", 40, MinecraftVersion.V1_8),

    /**
     * Particle: Mob Appearance, Version: 1.8+ (粒子效果: 远古守护者, 版本: 1.8+)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#mobappearance)
     */
    MOB_APPEARANCE("mobappearance", 41, MinecraftVersion.V1_8),

    /**
     * Particle: Dragon Breath, Version: 1.9+ (粒子效果: 龙息, 版本: 1.9+)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#dragonbreath)
     */
    DRAGON_BREATH("dragonbreath", 42, MinecraftVersion.V1_9),

    /**
     * Particle: End Rod, Version: 1.9+ (粒子效果: 末地烛, 版本: 1.9+)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#endRod)
     */
    END_ROD("endRod", 43, MinecraftVersion.V1_9),

    /**
     * Particle: Damage Indicator, Version: 1.9+ (粒子效果: 伤害指示器, 版本: 1.9+)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#damageIndicator)
     */
    DAMAGE_INDICATOR("damageIndicator", 44, MinecraftVersion.V1_9),

    /**
     * Particle: Sweep Attack, Version: 1.9+ (粒子效果: 扫荡攻击, 版本: 1.9+)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#sweepAttack)
     */
    SWEEP_ATTACK("sweepAttack", 45, MinecraftVersion.V1_9),

    /**
     * Particle: Falling Dust, Version: 1.9+ | Requires: Data (粒子效果: 掉落尘, 版本: 1.9+ | 需求: 数据)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#fallingdust)
     */
    FALLING_DUST("fallingdust", 46, 1, MinecraftVersion.V1_10, ParticleProperty.REQUIRES_DATA),

    /**
     * Particle: Totem, Version: 1.11+ (粒子效果: 不死图腾颗粒, 版本: 1.11+)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#totem)
     */
    TOTEM("totem", 47, MinecraftVersion.V1_11),

    /**
     * Particle: Spit, Version: 1.11+ (粒子效果: 羊驼口水, 版本: 1.11+)
     * - [Wiki](https://minecraft.gamepedia.com/Particle#spit)
     */
    SPIT("spit", 48, MinecraftVersion.V1_11),
    ;

    /** member */

    val id: Int
    val type: String
    val dataLength: Int
    val mcVer: MinecraftVersion?

    private val properties: Array<out ParticleProperty>

    /** constructor */

    constructor(type: String, id: Int, vararg properties: ParticleProperty) : this(type, id, null, *properties)
    constructor(type: String, id: Int, mcVer: MinecraftVersion? = null, vararg properties: ParticleProperty) : this(type, id, 0, mcVer, *properties)
    constructor(type: String, id: Int, dataLength: Int = 0, mcVer: MinecraftVersion? = null, vararg properties: ParticleProperty) {
        this.id = id
        this.type = type
        this.mcVer = mcVer
        this.properties = properties
        this.dataLength = dataLength
    }

    /** api */

    fun hasProperty(property: ParticleProperty): Boolean
            = properties.contains(property)

    fun isSupported()
            = mcVer == null || currentMCVersion().isOrLater(mcVer)

    @Throws(ParticleException::class)
    fun display(offsetX: Float, offsetY: Float, offsetZ: Float, speed: Float, amount: Int, center: Location, range: Double) {
        if(!isSupported())
            throw ParticleException(IllegalBukkitVersionException("粒子效果 $this 不支持当前服务端版本. $mcVer"))
        if(hasProperty(ParticleProperty.REQUIRES_DATA))
            throw ParticleException("粒子效果 $this 要求添加粒子数据属性.")
        if(hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center))
            throw ParticleException("粒子效果没有水源在中心位置.")
        ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256.0, null).sendTo(center, range)
    }

    @Throws(ParticleException::class)
    fun display(offsetX: Float, offsetY: Float, offsetZ: Float, speed: Float, amount: Int, center: Location, players: List<Player>) {
        if(!isSupported())
            throw ParticleException(IllegalBukkitVersionException("粒子效果 $this 不支持当前服务端版本. $mcVer"))
        if(hasProperty(ParticleProperty.REQUIRES_DATA))
            throw ParticleException("粒子效果 $this 要求添加粒子数据属性.")
        if(hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center))
            throw ParticleException("粒子效果没有水源在中心位置.")
        ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), null).sendTo(center, players)
    }

    @Throws(ParticleException::class)
    fun display(offsetX: Float, offsetY: Float, offsetZ: Float, speed: Float, amount: Int, center: Location, vararg players: Player)
            = display(offsetX, offsetY, offsetZ, speed, amount, center, players.toList())

    @Throws(ParticleException::class)
    fun display(direction: Vector, speed: Float, center: Location, range: Double) {
        if(!isSupported())
            throw ParticleException(IllegalBukkitVersionException("粒子效果 $this 不支持当前服务端版本. $mcVer"))
        if(hasProperty(ParticleProperty.REQUIRES_DATA))
            throw ParticleException("粒子效果 $this 要求添加粒子数据属性.")
        if(!hasProperty(ParticleProperty.DIRECTIONAL))
            throw ParticleException("粒子效果没有矢量方向属性.")
        if(hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center))
            throw ParticleException("粒子效果没有水源在中心位置.")
        ParticlePacket(this, direction, speed, range > 256.0, null).sendTo(center, range)
    }

    @Throws(ParticleException::class)
    fun display(direction: Vector, speed: Float, center: Location, players: List<Player>) {
        if(!isSupported())
            throw ParticleException(IllegalBukkitVersionException("粒子效果 $this 不支持当前服务端版本. $mcVer"))
        if(hasProperty(ParticleProperty.REQUIRES_DATA))
            throw ParticleException("粒子效果 $this 要求添加粒子数据属性.")
        if(!hasProperty(ParticleProperty.DIRECTIONAL))
            throw ParticleException("粒子效果没有矢量方向属性.")
        if(hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center))
            throw ParticleException("粒子效果没有水源在中心位置.")
        ParticlePacket(this, direction, speed, isLongDistance(center, players), null).sendTo(center, players)
    }

    @Throws(ParticleException::class)
    fun display(direction: Vector, speed: Float, center: Location, vararg players: Player)
            = display(direction, speed, center, players.toList())

    @Throws(ParticleException::class)
    fun display(color: ParticleColor, center: Location, range: Double) {
        if(!isSupported())
            throw ParticleException(IllegalBukkitVersionException("粒子效果 $this 不支持当前服务端版本. $mcVer"))
        if(!hasProperty(ParticleProperty.COLOR))
            throw ParticleException("粒子效果没有颜色值属性.")
        if(!isColorCorrect(this, color))
            throw ParticleException("粒子效果和效果颜色对象不符合.")
        ParticlePacket(this, color, range > 256.0).sendTo(center, range)
    }

    @Throws(ParticleException::class)
    fun display(color: ParticleColor, center: Location, players: List<Player>) {
        if(!isSupported())
            throw ParticleException(IllegalBukkitVersionException("粒子效果 $this 不支持当前服务端版本. $mcVer"))
        if(!hasProperty(ParticleProperty.COLOR))
            throw ParticleException("粒子效果没有颜色值属性.")
        if(!isColorCorrect(this, color))
            throw ParticleException("粒子效果和效果颜色对象不符合.")
        ParticlePacket(this, color, isLongDistance(center, players)).sendTo(center, players)
    }

    @Throws(ParticleException::class)
    fun display(color: ParticleColor, center: Location, vararg players: Player)
            = display(color, center, players.toList())

    @Throws(ParticleException::class)
    fun display(data: ParticleData, offsetX: Float, offsetY: Float, offsetZ: Float, speed: Float, amount: Int, center: Location, range: Double) {
        if(!isSupported())
            throw ParticleException(IllegalBukkitVersionException("粒子效果 $this 不支持当前服务端版本. $mcVer"))
        if(!hasProperty(ParticleProperty.REQUIRES_DATA))
            throw ParticleException("粒子效果没有粒子数据属性.")
        if(!isDataCorrect(this, data))
            throw ParticleException("粒子效果 $this 和粒子数据对象 $data 不符合.")
        ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256.0, data).sendTo(center, range)
    }

    @Throws(ParticleException::class)
    fun display(data: ParticleData, offsetX: Float, offsetY: Float, offsetZ: Float, speed: Float, amount: Int, center: Location, players: List<Player>) {
        if(!isSupported())
            throw ParticleException(IllegalBukkitVersionException("粒子效果 $this 不支持当前服务端版本. $mcVer"))
        if(!hasProperty(ParticleProperty.REQUIRES_DATA))
            throw ParticleException("粒子效果没有粒子数据属性.")
        if(!isDataCorrect(this, data))
            throw ParticleException("粒子效果 $this 和粒子数据对象 $data 不符合.")
        ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), data).sendTo(center, players)
    }

    @Throws(ParticleException::class)
    fun display(data: ParticleData, offsetX: Float, offsetY: Float, offsetZ: Float, speed: Float, amount: Int, center: Location, vararg players: Player)
            = display(data, offsetX, offsetY, offsetZ, speed, amount, center, players.toList())

    @Throws(ParticleException::class)
    fun display(data: ParticleData, direction: Vector, speed: Float, center: Location, range: Double) {
        if(!isSupported())
            throw ParticleException(IllegalBukkitVersionException("粒子效果 $this 不支持当前服务端版本. $mcVer"))
        if(!hasProperty(ParticleProperty.REQUIRES_DATA))
            throw ParticleException("粒子效果没有粒子数据属性.")
        if(!isDataCorrect(this, data))
            throw ParticleException("粒子效果 $this 和粒子数据对象 $data 不符合.")
        ParticlePacket(this, direction, speed, range > 256.0, data).sendTo(center, range)
    }

    @Throws(ParticleException::class)
    fun display(data: ParticleData, direction: Vector, speed: Float, center: Location, players: List<Player>) {
        if(!isSupported())
            throw ParticleException(IllegalBukkitVersionException("粒子效果 $this 不支持当前服务端版本. $mcVer"))
        if(!hasProperty(ParticleProperty.REQUIRES_DATA))
            throw ParticleException("粒子效果没有粒子数据属性.")
        if(!isDataCorrect(this, data))
            throw ParticleException("粒子效果 $this 和粒子数据对象 $data 不符合.")
        ParticlePacket(this, direction, speed, isLongDistance(center, players), data).sendTo(center, players)
    }

    @Throws(ParticleException::class)
    fun display(data: ParticleData, direction: Vector, speed: Float, center: Location, vararg players: Player)
            = display(data, direction, speed, center, players.toList())

    @Throws(ParticleException::class)
    fun display(center: Location, range: Double, offsetX: Float, offsetY: Float, offsetZ: Float, speed: Float, amount: Int)
            = display(offsetX, offsetY, offsetZ, speed, amount, center, range)

    @Throws(ParticleException::class)
    fun display(center: Location, range: Double)
            = display(0f, 0f, 0f, 0f, 1, center, range)

    @Throws(ParticleException::class)
    fun display(data: ParticleData, center: Location, range: Double, offsetX: Float, offsetY: Float, offsetZ: Float, speed: Float, amount: Int) {
        when(hasProperty(ParticleProperty.REQUIRES_DATA)) {
            true -> display(data, offsetX, offsetY, offsetZ, speed, amount, center, range)
            else -> display(offsetX, offsetY, offsetZ, speed, amount, center, range)
        }
    }

    /** static */

    companion object {

        @JvmStatic
        private val ID_MAP: MutableMap<Int, Particle> = HashMap()

        init {
            values().forEach { ID_MAP.put(it.id, it) }
        }

        @JvmStatic
        @JvmName("isWater")
        private fun isWater(location: Location): Boolean = location.block.type.let {
            it == Material.WATER || it == Material.STATIONARY_WATER
        }

        @JvmStatic
        @JvmName("isLongDistance")
        private fun isLongDistance(location: Location, players: List<Player>): Boolean = players.let {
            val world = location.world.name
            return it.any { world == it.world.name && it.location.distanceSquared(location) <= 65536.0 }
        }

        @JvmStatic
        @JvmName("isDataCorrect")
        private fun isDataCorrect(particle: Particle, data: ParticleData): Boolean
                = (particle == BLOCK_CRACK || particle == BLOCK_DUST) && data is ParticleDataBlock || particle == ITEM_CRACK && data is ParticleDataItem

        @JvmStatic
        @JvmName("isColorCorrect")
        private fun isColorCorrect(particle: Particle, color: ParticleColor): Boolean
                = (particle == SPELL_MOB || particle == SPELL_MOB_AMBIENT || particle == RED_DUST) && color is ParticleColorOrdinary || particle == NOTE && color is ParticleColorNote

        @JvmStatic
        @JvmName("fromId")
        fun fromId(id: Int): Particle
                = ID_MAP[id] ?: Particle.BARRIER
    }

    /** inner class */

    private inner class ParticlePacket {

        /** member */

        private val particle: Particle
        private var offsetX: Float
        private val offsetY: Float
        private val offsetZ: Float
        private val speed: Float
        private val amount: Int
        private val longDistance: Boolean
        private val data: ParticleData?

        /** constructor */

        constructor(particle: Particle, offsetX: Float, offsetY: Float, offsetZ: Float, speed: Float, amount: Int, longDistance: Boolean, data: ParticleData? = null) {
            this.particle = particle
            this.offsetX = offsetX
            this.offsetY = offsetY
            this.offsetZ = offsetZ
            this.speed = if(speed < 0f) 1f else speed
            this.amount = if(amount < 0) 1 else amount
            this.longDistance = longDistance
            this.data = data
        }

        constructor(particle: Particle, direction: Vector, speed: Float, longDistance: Boolean, data: ParticleData? = null) {
            this.particle = particle
            this.offsetX = direction.x.toFloat()
            this.offsetY = direction.y.toFloat()
            this.offsetZ = direction.z.toFloat()
            this.speed = if(speed < 0f) 1.0f else speed
            this.amount = 1
            this.longDistance = longDistance
            this.data = data
        }

        constructor(particle: Particle, color: ParticleColor, longDistance: Boolean) : this(particle, color.valueX, color.valueY, color.valueZ, 1f, 0, longDistance, null) {
            if(particle == RED_DUST && color is ParticleColorOrdinary && color.red == 0)
                offsetX = java.lang.Float.MIN_NORMAL
        }

        /** api */

        fun sendTo(center: Location, range: Double)
                = createPacket(center).sendToNearby(center, range)

        fun sendTo(center: Location, players: List<Player>)
                = createPacket(center).send(players.toTypedArray())

        /** implement */

        private fun createPacket(center: Location): PacketOutParticles {
            val arguments = ArrayList<Int>()
            if(data != null) when(particle) {
                ITEM_CRACK -> arguments.addAll(data.packetData)
                else -> arguments.add(data.packetData[0].or(data.packetData[1].shl(12)))
            }
            return PacketOutParticles(
                    particle,
                    longDistance,
                    center.x.toFloat(),
                    center.y.toFloat(), 
                    center.z.toFloat(),
                    offsetX,
                    offsetY,
                    offsetZ,
                    speed,
                    amount,
                    arguments)
        }
    }
}
