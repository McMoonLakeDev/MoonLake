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

package com.mcmoonlake.api.item

import com.mcmoonlake.api.Valuable
import com.mcmoonlake.api.adapter.AdapterBukkit
import com.mcmoonlake.api.version.MinecraftVersion

enum class Enchantment(
        val id: Int,
        val max: Int,
        val type: String,
        val mcVer: MinecraftVersion? = null
) : AdapterBukkit<org.bukkit.enchantments.Enchantment>,
        Valuable<String> {

    /**
     * Enchantment: Environmental Protection (附魔类型: 保护)
     */
    PROTECTION(0, 4, "PROTECTION_ENVIRONMENTAL"),
    /**
     * Enchantment: Fire Protection (附魔类型: 火焰保护)
     */
    PROTECTION_FIRE(1, 4, "PROTECTION_FIRE"),
    /**
     * Enchantment: Fall Protection (附魔类型: 摔落保护)
     */
    PROTECTION_FALL(2, 4, "PROTECTION_FALL"),
    /**
     * Enchantment: Explosions Protection (附魔类型: 爆炸保护)
     */
    PROTECTION_EXPLOSIONS(3, 4, "PROTECTION_EXPLOSIONS"),
    /**
     * Enchantment: Projectile Protection (附魔类型: 弹射物保护)
     */
    PROTECTION_PROJECTILE(4, 4, "PROTECTION_PROJECTILE"),
    /**
     * Enchantment: Oxygen (附魔类型: 水下呼吸)
     */
    OXYGEN(5, 3, "OXYGEN"),
    /**
     * Enchantment: Water Worker (附魔类型: 水下速掘)
     */
    WATER_WORKER(6, 1, "WATER_WORKER"),
    /**
     * Enchantment: Thorns (附魔类型: 荆棘)
     */
    THORNS(7, 3, "THORNS"),
    /**
     * Enchantment: Depth Strider (附魔类型: 深海探索者)
     */
    DEPTH_STRIDER(8, 3, "DEPTH_STRIDER"),
    /**
     * Enchantment: Frost Walker (附魔类型: 冰霜行者)
     */
    FROST_WALKER(9, 2, "FROST_WALKER", MinecraftVersion.V1_9),
    /**
     * Enchantment: Binding Curse (附魔类型: 绑定诅咒)
     */
    BINDING_CURSE(10, 1, "BINDING_CURSE", MinecraftVersion.V1_11),

    /**
     * Enchantment: All Damage (附魔类型: 锋利)
     */
    DAMAGE(16, 5, "DAMAGE_ALL"),
    /**
     * Enchantment: Undead Damage (附魔类型: 亡灵杀手)
     */
    DAMAGE_UNDEAD(17, 5, "DAMAGE_UNDEAD"),
    /**
     * Enchantment: Arthropods Damage (附魔类型: 节肢杀手)
     */
    DAMAGE_ARTHROPODS(18, 5, "DAMAGE_ARTHROPODS"),
    /**
     * Enchantment: Knockback (附魔类型: 击退)
     */
    KNOCKBACK(19, 2, "KNOCKBACK"),
    /**
     * Enchantment: Fire Aspect (附魔类型: 火焰附加)
     */
    FIRE_ASPECT(20, 2, "FIRE_ASPECT"),
    /**
     * Enchantment: Loot Bonus Mobs (附魔类型: 抢夺)
     */
    LOOT_BONUS_MOBS(21, 3, "LOOT_BONUS_MOBS"),
    /**
     * Enchantment: Sweeping Edge (附魔类型: 横扫之刃)
     */
    SWEEPING_EDGE(22, 3, "SWEEPING_EDGE", MinecraftVersion(1, 11, 1)),

    /**
     * Enchantment: Dig Speed (附魔类型: 效率)
     */
    DIG_SPEED(32, 5, "DIG_SPEED"),
    /**
     * Enchantment: Silk Touch (附魔类型: 精准采集)
     */
    SILK_TOUCH(33, 1, "SILK_TOUCH"),
    /**
     * Enchantment: Durability (附魔类型: 耐久)
     */
    DURABILITY(34, 3, "DURABILITY"),
    /**
     * Enchantment: Lott Bonus Blocks (附魔类型: 时运)
     */
    LOOT_BONUS_BLOCKS(35, 3, "LOOT_BONUS_BLOCKS"),

    /**
     * Enchantment: Arrow Damage (附魔类型: 力量)
     */
    ARROW_DAMAGE(48, 5, "ARROW_DAMAGE"),
    /**
     * Enchantment: Arrow Knockback (附魔类型: 冲击)
     */
    ARROW_KNOCKBACK(49, 2, "ARROW_KNOCKBACK"),
    /**
     * Enchantment: Arrow Fire (附魔类型: 火矢)
     */
    ARROW_FIRE(50, 1, "ARROW_FIRE"),
    /**
     * Enchantment: Arrow Infinite (附魔类型: 无限)
     */
    ARROW_INFINITE(51, 1, "ARROW_INFINITE"),

    /**
     * Enchantment: Luck (附魔类型: 海之眷顾)
     */
    LUCK(61, 3, "LUCK"),
    /**
     * Enchantment: Lure (附魔类型: 饵钓)
     */
    LURE(62, 3, "LURE"),

    /**
     * Enchantment: Mending (附魔类型: 经验修补)
     */
    MENDING(70, 1, "MENDING", MinecraftVersion.V1_9),
    /**
     * Enchantment: Vanishing Curse (附魔类型: 消失诅咒)
     */
    VANISHING_CURSE(71, 1, "VANISHING_CURSE", MinecraftVersion.V1_11),
    ;

    override fun value(): String
            = type

    override fun cast(): org.bukkit.enchantments.Enchantment
            = org.bukkit.enchantments.Enchantment.getByName(type)

    companion object {

        @JvmStatic
        private val ID_MAP: MutableMap<Int, Enchantment> = HashMap()

        init {
            val regex = Regex("(?i)[a-z]([a-z_\\d]*)")
            values().forEach { if(it.name.matches(regex)) ID_MAP[it.id] = it }
        }

        @JvmStatic
        @JvmName("fromName")
        fun fromName(name: String): Enchantment
                = Enchantment.valueOf(name.toUpperCase())

        @JvmStatic
        @JvmName("fromId")
        @Throws(IllegalArgumentException::class)
        fun fromId(id: Int): Enchantment
                = ID_MAP[id] ?: throw IllegalArgumentException("错误: 无效的附魔效果 ID $id 值.")

        @JvmStatic
        @JvmName("hasId")
        fun hasId(id: Int): Boolean
                = ID_MAP.containsKey(id)
    }
}
