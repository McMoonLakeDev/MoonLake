/*
 * Copyright (C) 2016 The MoonLake Authors
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


package com.minecraft.moonlake.enums;

import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.reflect.Reflect;

/**
 * <h1>Enchantment</h1>
 * 附魔类型中文汉化版（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public abstract class Enchantment {

    // 翻译均来自中文 wiki
    // http://minecraft-zh.gamepedia.com/%E9%99%84%E9%AD%94#.E9.AD.94.E5.92.92
    ///

    /**
     * 附魔类型: 保护
     */
    public final static Enchantment 保护 = new Enchantment(0, "PROTECTION_ENVIRONMENTAL") {};
    /**
     * 附魔类型: 火焰保护
     */
    public final static Enchantment 火焰保护 = new Enchantment(1, "PROTECTION_FIRE") {};
    /**
     * 附魔类型: 摔落保护
     */
    public final static Enchantment 摔落保护 = new Enchantment(2, "PROTECTION_FALL") {};
    /**
     * 附魔类型: 爆炸保护
     */
    public final static Enchantment 爆炸保护 = new Enchantment(3, "PROTECTION_EXPLOSIONS") {};
    /**
     * 附魔类型: 弹射物保护
     */
    public final static Enchantment 弹射物保护 = new Enchantment(4, "PROTECTION_PROJECTILE") {};
    /**
     * 附魔类型: 水下呼吸
     */
    public final static Enchantment 水下呼吸 = new Enchantment(5, "OXYGEN") {};
    /**
     * 附魔类型: 水下速掘
     */
    public final static Enchantment 水下速掘 = new Enchantment(6, "WATER_WORKER") {};
    /**
     * 附魔类型: 荆棘
     */
    public final static Enchantment 荆棘 = new Enchantment(7, "THORNS") {};
    /**
     * 附魔类型: 深海探索者
     */
    public final static Enchantment 深海探索者 = new Enchantment(8, "DEPTH_STRIDER") {};

    /**
     * 附魔类型: 锋利
     */
    public final static Enchantment 锋利 = new Enchantment(16, "DAMAGE_ALL") {};
    /**
     * 附魔类型: 亡灵杀手
     */
    public final static Enchantment 亡灵杀手 = new Enchantment(17, "DAMAGE_UNDEAD") {};
    /**
     * 附魔类型: 节肢杀手
     */
    public final static Enchantment 节肢杀手 = new Enchantment(18, "DAMAGE_ARTHROPODS") {};
    /**
     * 附魔类型: 击退
     */
    public final static Enchantment 击退 = new Enchantment(19, "KNOCKBACK") {};
    /**
     * 附魔类型: 火焰附加
     */
    public final static Enchantment 火焰附加 = new Enchantment(20, "FIRE_ASPECT") {};
    /**
     * 附魔类型: 抢夺
     */
    public final static Enchantment 抢夺 = new Enchantment(21, "LOOT_BONUS_MOBS") {};

    /**
     * 附魔类型: 效率
     */
    public final static Enchantment 效率 = new Enchantment(32, "DIG_SPEED") {};
    /**
     * 附魔类型: 精准采集
     */
    public final static Enchantment 精准采集 = new Enchantment(33, "SILK_TOUCH") {};
    /**
     * 附魔类型: 耐久
     */
    public final static Enchantment 耐久 = new Enchantment(34, "DURABILITY") {};
    /**
     * 附魔类型: 时运
     */
    public final static Enchantment 时运 = new Enchantment(35, "LOOT_BONUS_BLOCKS") {};

    /**
     * 附魔类型: 力量
     */
    public final static Enchantment 力量 = new Enchantment(48, "ARROW_DAMAGE") {};
    /**
     * 附魔类型: 冲击
     */
    public final static Enchantment 冲击 = new Enchantment(49, "ARROW_KNOCKBACK") {};
    /**
     * 附魔类型: 火矢
     */
    public final static Enchantment 火矢 = new Enchantment(50, "ARROW_FIRE") {};
    /**
     * 附魔类型: 无限
     */
    public final static Enchantment 无限 = new Enchantment(51, "ARROW_INFINITE") {};

    /**
     * 附魔类型: 海之眷顾
     */
    public final static Enchantment 海之眷顾 = new Enchantment(61, "LUCK") {};
    /**
     * 附魔类型: 饵钓
     */
    public final static Enchantment 饵钓 = new Enchantment(62, "LURE") {};

    // 1.9 增加的新附魔（不兼容 1.8 以下
    /**
     * 附魔类型: 冰霜行者
     */
    public final static Enchantment 冰霜行者 = new Enchantment(9, "FROST_WALKER", 9) {};
    /**
     * 附魔类型: 经验修补
     */
    public final static Enchantment 经验修补 = new Enchantment(70, "MENDING", 9) {};
    ///

    private final int id;
    private final String name;
    private final int requiredVersion;

    /**
     * 附魔类型类构造函数
     *
     * @param id 附魔 ID
     * @param name 附魔名称
     */
    public Enchantment(int id, String name) {

        this(id, name, -1);
    }

    /**
     * 附魔类型类构造函数
     *
     * @param id 附魔 ID
     * @param name 附魔名称
     * @param requiredVersion 需求版本
     */
    public Enchantment(int id, String name, int requiredVersion) {

        this.id = id;
        this.name = name;
        this.requiredVersion = requiredVersion;
    }

    /**
     * 获取此附魔类型的 Id
     *
     * @return Id
     * @deprecated 已过时, 请使用 {@link #getName()}
     */
    @Deprecated
    public int getId() {

        return id;
    }

    /**
     * 获取此附魔类型的名称
     *
     * @return 名称
     */
    public String getName() {

        return name;
    }

    /**
     * 获取此附魔类型需求的版本
     *
     * @return 版本
     */
    public int getRequiredVersion() {

        return requiredVersion;
    }

    /**
     * 转换为 Bukkit 的 Enchantment 对象
     *
     * @return Enchantment
     * @throws IllegalBukkitVersionException 如果 Bukkit 服务器版本不支持附魔则抛出异常
     */
    @SuppressWarnings("deprecation")
    public org.bukkit.enchantments.Enchantment as() {

        if(requiredVersion != -1) {

            int version = Reflect.getServerVersionNumber();

            if(version < requiredVersion) {

                throw new IllegalBukkitVersionException("The bukkit version not support enchantment: " + id);
            }
        }
        //return org.bukkit.enchantments.Enchantment.getById(id);
        return org.bukkit.enchantments.Enchantment.getByName(name);
    }
}
