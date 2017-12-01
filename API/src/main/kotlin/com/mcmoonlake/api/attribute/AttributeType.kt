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

package com.mcmoonlake.api.attribute

import com.mcmoonlake.api.Valuable
import com.mcmoonlake.api.isOrLater
import com.mcmoonlake.api.version.IllegalBukkitVersionException
import com.mcmoonlake.api.version.MinecraftVersion

import org.bukkit.entity.Horse
import org.bukkit.entity.Zombie

/**
 * ## AttributeType (属性类型)
 *
 * * Enumerate the attribute types available in minecraft. [Details](https://minecraft.gamepedia.com/Attribute)
 * * 枚举出 Minecraft 中可用的属性类型. [详情](https://minecraft.gamepedia.com/Attribute)
 *
 * @author lgou2w
 * @since 2.0
 */
enum class AttributeType(
        /**
         * * The type name of this attribute type.
         * * 此属性类型的类型名称.
         */
        val type: String,
        /**
         * * The default value of this attribute type.
         * * 此属性类型的默认值.
         */
        val def: Double,
        /**
         * * The minimum value of this attribute type.
         * * 此属性类型的最小值
         */
        val min: Double,
        /**
         * * The maximum value of this attribute type.
         * * 此属性类型的最大值
         */
        val max: Double,
        /**
         * * Minecraft version supported by this attribute type. If `null` then all is supported.
         * * 此属性类型支持的 Minecraft 版本. 如果 `null` 则支持所有.
         */
        val mcVer: MinecraftVersion? = null) : Valuable<String> {

    /**
     * * Attribute Type: Max Health
     * * 属性类型: 最大生命
     */
    MAX_HEALTH("generic.maxHealth", 20.0, .0, 2048.0),
    /**
     * * Attribute Type: Follow Range
     * * 属性类型: 追踪范围
     */
    FOLLOW_RANGE("generic.followRange", 32.0, .0, 2048.0),
    /**
     * * Attribute Type: Knockback Resistance
     * * 属性类型: 击退抗性
     */
    KNOCKBACK_RESISTANCE("generic.knockbackResistance", .0, .0, 1.0),
    /**
     * * Attribute Type: Movement Speed
     * * 属性类型: 移动速度
     */
    MOVEMENT_SPEED("generic.movementSpeed", 0.699999988079071, .0, 2048.0),
    /**
     * * Attribute Type: Attack Damage
     * * 属性类型: 攻击伤害
     */
    ATTACK_DAMAGE("generic.attackDamage", 2.0, .0, 2048.0),
    /**
     * * Attribute Type: Attack Speed
     * * 属性类型: 攻击速度
     */
    ATTACK_SPEED("generic.attackSpeed", 4.0, .0, 1024.0, MinecraftVersion.V1_9),
    /**
     * * Attribute Type: Armor
     * * 属性类型: 护甲
     */
    ARMOR("generic.armor", .0, .0, 30.0, MinecraftVersion.V1_9),
    /**
     * * Attribute Type: Armor Toughness
     * * 属性类型: 护甲韧性
     */
    ARMOR_TOUGHNESS("generic.armorToughness", .0, .0, 20.0, MinecraftVersion.V1_9),
    /**
     * * Attribute Type: Luck
     * * 属性类型: 幸运
     */
    LUCK("generic.luck", .0, -1024.0, 1024.0, MinecraftVersion.V1_9),
    /**
     * * Attribute Type: Flying Speed
     * * 属性类型: 飞行速度
     */
    FLYING_SPEED("generic.flyingSpeed", 0.4000000059604645, .0, 1024.0, MinecraftVersion.V1_12),
    /**
     * * Attribute Type: Horse Jump Strength
     * * 属性类型: 马弹跳力
     *
     * @see [Horse]
     */
    HORSE_JUMP_STRENGTH("horse.jumpStrength", 0.69999999999999996, .0, 2.0),
    /**
     * * Attribute Type: Zombie Spawn Reinforcements
     * * 属性类型: 僵尸增援生成率
     *
     * @see [Zombie]
     */
    ZOMBIE_SPAWN_REINFORCEMENTS("zombie.spawnReinforcements", .0, .0, 1.0),
    ;

    override fun value(): String
            = type

    /**
     * * Verify that the attribute type is supported.
     * * 验证当前属性是否支持.
     *
     * @throws IllegalBukkitVersionException If the attribute type is not supported.
     * @throws IllegalBukkitVersionException 如果属性类型不支持.
     */
    @Throws(IllegalBukkitVersionException::class)
    fun validateSupport() = mcVer.let {
        if(it != null && !MinecraftVersion.currentVersion().isOrLater(it))
            throw IllegalBukkitVersionException("当前 Bukkit 版本不支持此 $this 属性类型.")
    }
}
