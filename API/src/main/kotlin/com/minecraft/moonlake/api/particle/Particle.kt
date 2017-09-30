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

package com.minecraft.moonlake.api.particle

import com.minecraft.moonlake.api.currentMCVersion
import com.minecraft.moonlake.api.isOrLater
import com.minecraft.moonlake.api.packet.PacketOutParticles
import com.minecraft.moonlake.api.version.IllegalBukkitVersionException
import com.minecraft.moonlake.api.version.MinecraftVersion
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.util.Vector

/**
 * @author [DarkBlade12](https://github.com/DarkBlade12) by origin, [lgou2w](https://github.com/lgou2w) by modified.
 */
enum class Particle {

    /** 
     * 粒子效果: [普通爆炸](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#explode) (版本: 全版本 | 需求: 矢量方向)
     */
    EXPLOSION_NORMAL("explode", 0, ParticleProperty.DIRECTIONAL),

    /** 
     * 粒子效果: [大型爆炸](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#largeexplode) (版本: 全版本)
     */
    EXPLOSION_LARGE("largeexplode", 1),

    /** 
     * 粒子效果: [巨大爆炸](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#hugeexplosion) (版本: 全版本)
     */
    EXPLOSION_HUGE("hugeexplosion", 2),

    /** 
     * 粒子效果: [烟花尾迹](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#fireworksSpark) (版本: 全版本)
     */
    FIREWORKS_SPARK("fireworksSpark", 3, ParticleProperty.DIRECTIONAL),

    /** 
     * 粒子效果: [水泡](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#bubble) (版本: 全版本 | 需求: 矢量方向, 需求: 水源)
     */
    WATER_BUBBLE("bubble", 4, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_WATER),

    /** 
     * 粒子效果: [水花溅起](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#splash) (版本: 全版本 | 需求: 矢量方向)
     */
    WATER_SPLASH("splash", 5, ParticleProperty.DIRECTIONAL),

    /** 
     * 粒子效果: [水尾波](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#wake) (版本: 全版本 | 需求: 矢量方向)
     */
    WATER_WAKE("wake", 6, ParticleProperty.DIRECTIONAL),

    /**
     * 粒子效果: [水下颗粒](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#suspended) (版本: 全版本 | 需求: 水源)
     */
    SUSPENDED("suspended", 7, ParticleProperty.REQUIRES_WATER),

    /**
     * 粒子效果: [虚空颗粒](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#depthSuspended) (版本: 全版本 | 需求: 矢量方向)
     */
    SUSPENDED_DEPTH("depthSuspended", 8, ParticleProperty.DIRECTIONAL),

    /** 
     * 粒子效果: [暴击](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#crit) (版本: 全版本 | 需求: 矢量方向)
     */
    CRIT("crit", 9, ParticleProperty.DIRECTIONAL),

    /** 
     * 粒子效果: [魔法暴击](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#magicCrit) (版本: 全版本 | 需求: 矢量方向)
     */
    CRIT_MAGIC("magicCrit", 10, ParticleProperty.DIRECTIONAL),

    /** 
     * 粒子效果: [烟雾](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#smoke) (版本: 全版本 | 需求: 矢量方向)
     */
    SMOKE_NORMAL("smoke", 11, ParticleProperty.DIRECTIONAL),

    /** 
     * 粒子效果: [大型烟雾](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#largesmoke) (版本: 全版本 | 需求: 矢量方向)
     */
    SMOKE_LARGE("largesmoke", 12, ParticleProperty.DIRECTIONAL),

    /** 
     * 粒子效果: [药水符咒](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#spell) (版本: 全版本)
     */
    SPELL("spell", 13),

    /** 
     * 粒子效果: [瞬间药水符咒](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#instantSpell) (版本: 全版本)
     */
    SPELL_INSTANT("instantSpell", 14),

    /** 
     * 粒子效果: [实体药水符咒](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#mobSpell) (版本: 全版本 | 需求: 颜色)
     */
    SPELL_MOB("mobSpell", 15, ParticleProperty.COLOR),

    /** 
     * 粒子效果: [实体药水符咒环境](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#mobSpellAmbient) (版本: 全版本 | 需求: 颜色)
     */
    SPELL_MOB_AMBIENT("mobSpellAmbient", 16, ParticleProperty.COLOR),

    /** 
     * 粒子效果: [女巫魔法](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#witchMagic) (版本: 全版本)
     */
    SPELL_WITCH("witchMagic", 17),

    /** 
     * 粒子效果: [滴水](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#dripWater) (版本: 全版本)
     */
    DRIP_WATER("dripWater", 18),

    /** 
     * 粒子效果: [滴岩浆](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#dripLava) (版本: 全版本)
     */
    DRIP_LAVA("dripLava", 19),

    /** 
     * 粒子效果: [村民生气](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#angryVillager) (版本: 全版本)
     */
    VILLAGER_ANGRY("angryVillager", 20),

    /** 
     * 粒子效果: [村民高兴](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#happyVillager) (版本: 全版本 | 需求: 矢量方向)
     */
    VILLAGER_HAPPY("happyVillager", 21, ParticleProperty.DIRECTIONAL),

    /** 
     * 粒子效果: [菌丝颗粒](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#townAura) (版本: 全版本)
     */
    TOWN_AURA("townAura", 22),

    /** 
     * 粒子效果: [音符](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#note) (版本: 全版本 | 需求: 颜色)
     */
    NOTE("note", 23, ParticleProperty.COLOR),

    /** 
     * 粒子效果: [传送门](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#portal) (版本: 全版本 | 需求: 矢量方向)
     */
    PORTAL("portal", 24, ParticleProperty.DIRECTIONAL),

    /** 
     * 粒子效果: [附魔台](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#enchantmenttable) (版本: 全版本 | 需求: 矢量方向)
     */
    ENCHANTMENT_TABLE("enchantmenttable", 25, ParticleProperty.DIRECTIONAL),

    /** 
     * 粒子效果: [火焰](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#flame) (版本: 全版本 | 需求: 矢量方向)
     */
    FLAME("flame", 26, ParticleProperty.DIRECTIONAL),

    /** 
     * 粒子效果: [岩浆](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#lava) (版本: 全版本)
     */
    LAVA("lava", 27),

    /** 
     * 粒子效果: [脚印](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#footstep) (版本: 全版本)
     */
    FOOTSTEP("footstep", 28),

    /** 
     * 粒子效果: [云](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#cloud) (版本: 全版本 | 需求: 矢量方向)
     */
    CLOUD("cloud", 29, ParticleProperty.DIRECTIONAL),

    /** 
     * 粒子效果: [红尘](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#reddust) (版本: 全版本 | 需求: 颜色)
     */
    RED_DUST("reddust", 30, ParticleProperty.COLOR),

    /** 
     * 粒子效果: [雪球碎裂](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#snowballpoof) (版本: 全版本)
     */
    SNOWBALL("snowballpoof", 31),

    /** 
     * 粒子效果: [雪颗粒](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#snowshovel) (版本: 全版本 | 需求: 矢量方向)
     */
    SNOW_SHOVEL("snowshovel", 32, ParticleProperty.DIRECTIONAL),

    /** 
     * 粒子效果: [史莱姆](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#slime) (版本: 全版本)
     */
    SLIME("slime", 33),

    /** 
     * 粒子效果: [爱心](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#heart) (版本: 全版本)
     */
    HEART("heart", 34),

    /** 
     * 粒子效果: [屏障](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#barrier) (版本: 1.8+)
     */
    BARRIER("barrier", 35, MinecraftVersion.V1_8),

    /** 
     * 粒子效果: [物品碎裂](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#iconcrack) (版本: 全版本 | 需求: 矢量方向, 需求: 数据)
     */
    ITEM_CRACK("iconcrack", 36, 2, null, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),

    /** 
     * 粒子效果: [方块碎裂](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#blockcrack) (版本: 全版本 | 需求: 数据)
     */
    BLOCK_CRACK("blockcrack", 37, 1, null, ParticleProperty.REQUIRES_DATA),

    /** 
     * 粒子效果: [方块尘](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#blockdust) (版本: 全版本 | 需求: 矢量方向, 需求: 数据)
     */
    BLOCK_DUST("blockdust", 38, 1, null, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),

    /** 
     * 粒子效果: [雨滴](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#droplet) (版本: 1.8+)
     */
    WATER_DROP("droplet", 39, MinecraftVersion.V1_8),

    /** 
     * 粒子效果: [物品获取](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#take) (版本: 1.8+)
     */
    ITEM_TAKE("take", 40, MinecraftVersion.V1_8),

    /** 
     * 粒子效果: [远古守护者](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#mobappearance) (版本: 1.8+)
     */
    MOB_APPEARANCE("mobappearance", 41, MinecraftVersion.V1_8),

    /** 
     * 粒子效果: [龙息](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#dragonbreath) (版本: 1.9+)
     */
    DRAGON_BREATH("dragonbreath", 42, MinecraftVersion.V1_9),

    /** 
     * 粒子效果: [末地烛](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#endRod) (版本: 1.9+)
     */
    END_ROD("endRod", 43, MinecraftVersion.V1_9),

    /** 
     * 粒子效果: [伤害指示器](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#damageIndicator) (版本: 1.9+)
     */
    DAMAGE_INDICATOR("damageIndicator", 44, MinecraftVersion.V1_9),

    /** 
     * 粒子效果: [扫荡攻击](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#sweepAttack) (版本: 1.9+)
     */
    SWEEP_ATTACK("sweepAttack", 45, MinecraftVersion.V1_9),

    /** 
     * 粒子效果: [掉落尘](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#fallingdust) (版本: 1.9+ | 需求: 数据)
     */
    FALLING_DUST("fallingdust", 46, 1, MinecraftVersion.V1_10, ParticleProperty.REQUIRES_DATA),

    /** 
     * 粒子效果: [不死图腾颗粒](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#totem) (版本: 1.11+)
     */
    TOTEM("totem", 47, MinecraftVersion.V1_11),

    /** 
     * 粒子效果: [羊驼口水](https://minecraft-zh.gamepedia.com/%E9%A2%97%E7%B2%92#spit) (版本: 1.11+)
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

        fun sendTo(center: Location, range: Double) {
            val squared = if(range < 1.0) throw ParticleException("粒子效果数据包范围不能小于 1.0 的值.") else range * range
            center.world.players.iterator().forEach {
                if(it.location.distanceSquared(center) <= squared)
                    sendToBukkit(center, it)
            }
        }

        fun sendTo(center: Location, players: List<Player>) {
            players.iterator().forEach { sendToBukkit(center, it) }
        }

        /** implement */

        private fun sendToBukkit(center: Location, player: Player) {
            var arguments = intArrayOf()
            if(data != null) {
                arguments = when(particle) {
                    ITEM_CRACK -> data.packetData.toIntArray()
                    else -> intArrayOf(data.packetData[0].or(data.packetData[1].shl(12)))
                }
            }
            val packet = PacketOutParticles(particle, longDistance, center.x.toFloat(), center.y.toFloat(), center.z.toFloat(), offsetX, offsetY, offsetZ, speed, amount, arguments)
            packet.send(player)
        }
    }
}
