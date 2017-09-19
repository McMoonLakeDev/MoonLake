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
 
 
package com.minecraft.moonlake.api.item.potion;

import com.minecraft.moonlake.validate.Validate;

/**
 * <h1>PotionBase</h1>
 * 基础药水效果
 *
 * @version 1.0.1
 * @author Month_Light
 */
public abstract class PotionBase {

    /**
     * 基础药水效果类型: 水瓶
     */
    public final static PotionBase WATER = new PotionBase("water") {};
    /**
     * 基础药水效果类型: 平凡的药水
     */
    public final static PotionBase MUNDANE_WATER = new PotionBase("mundane") {};
    /**
     * 基础药水效果类型: 浑浊的药水
     */
    public final static PotionBase THICK_WATER = new PotionBase("thick") {};
    /**
     * 基础药水效果类型: 粗制的药水
     */
    public final static PotionBase AWKWARD_WATER = new PotionBase("awkward") {};
    /**
     * 基础药水效果类型: 夜视药水
     */
    public final static PotionBase NIGHT_VISION = new PotionBase("night_vision") {};
    /**
     * 基础药水效果类型: 夜视药水 延长版
     */
    public final static PotionBase LONG_NIGHT_VISION = new PotionBase("long_night_vision") {};
    /**
     * 基础药水效果类型: 隐身药水
     */
    public final static PotionBase INVISIBILITY = new PotionBase("invisibility") {};
    /**
     * 基础药水效果类型: 隐身药水 延长版
     */
    public final static PotionBase LONG_INVISIBILITY = new PotionBase("long_invisibility") {};
    /**
     * 基础药水效果类型: 跳跃药水
     */
    public final static PotionBase LEAPING = new PotionBase("leaping") {};
    /**
     * 基础药水效果类型: 跳跃药水 高级版
     */
    public final static PotionBase STRONG_LEAPING = new PotionBase("strong_leaping") {};
    /**
     * 基础药水效果类型: 跳跃药水 延长版
     */
    public final static PotionBase LONG_LEAPING = new PotionBase("long_leaping") {};
    /**
     * 基础药水效果类型: 抗火药水
     */
    public final static PotionBase FIRE_RESISTANCE = new PotionBase("fire_resistance") {};
    /**
     * 基础药水效果类型: 抗火药水 延长版
     */
    public final static PotionBase LONG_FIRE_RESISTANCE = new PotionBase("long_fire_resistance") {};
    /**
     * 基础药水效果类型: 迅捷药水
     */
    public final static PotionBase SWIFTNESS = new PotionBase("swiftness") {};
    /**
     * 基础药水效果类型: 迅捷药水 高级版
     */
    public final static PotionBase STRONG_SWIFTNESS = new PotionBase("strong_swiftness") {};
    /**
     * 基础药水效果类型: 迅捷药水 延长版
     */
    public final static PotionBase LONG_SWIFTNESS = new PotionBase("long_swiftness") {};
    /**
     * 基础药水效果类型: 缓慢药水
     */
    public final static PotionBase SLOWNESS = new PotionBase("slowness") {};
    /**
     * 基础药水效果类型: 缓慢药水 延长版
     */
    public final static PotionBase LONG_SLOWNESS = new PotionBase("long_slowness") {};
    /**
     * 基础药水效果类型: 水下呼吸
     */
    public final static PotionBase WATER_BREATHING = new PotionBase("water_breathing") {};
    /**
     * 基础药水效果类型: 水下呼吸 延长版
     */
    public final static PotionBase LONG_WATER_BREATHING = new PotionBase("long_water_breathing") {};
    /**
     * 基础药水效果类型: 治疗药水
     */
    public final static PotionBase HEALING = new PotionBase("healing") {};
    /**
     * 基础药水效果类型: 治疗药水 高级版
     */
    public final static PotionBase STRONG_HEALING = new PotionBase("strong_healing") {};
    /**
     * 基础药水效果类型: 伤害药水
     */
    public final static PotionBase HARMING = new PotionBase("harming") {};
    /**
     * 基础药水效果类型: 伤害药水 高级版
     */
    public final static PotionBase STRONG_HARMING = new PotionBase("strong_harming") {};
    /**
     * 基础药水效果类型: 中毒药水
     */
    public final static PotionBase POISON = new PotionBase("poison") {};
    /**
     * 基础药水效果类型: 中毒药水 高级版
     */
    public final static PotionBase STRONG_POISON = new PotionBase("strong_poison") {};
    /**
     * 基础药水效果类型: 中毒药水 延长版
     */
    public final static PotionBase LONG_POISON = new PotionBase("long_poison") {};
    /**
     * 基础药水效果类型: 再生药水
     */
    public final static PotionBase REGENERATION = new PotionBase("regeneration") {};
    /**
     * 基础药水效果类型: 再生药水 高级版
     */
    public final static PotionBase STRONG_REGENERATION = new PotionBase("strong_regeneration") {};
    /**
     * 基础药水效果类型: 再生药水 延长版
     */
    public final static PotionBase LONG_REGENERATION = new PotionBase("long_regeneration") {};
    /**
     * 基础药水效果类型: 力量药水
     */
    public final static PotionBase STRENGTH = new PotionBase("strength") {};
    /**
     * 基础药水效果类型: 力量药水 高级版
     */
    public final static PotionBase STRONG_STRENGTH = new PotionBase("strong_strength") {};
    /**
     * 基础药水效果类型: 力量药水 延长版
     */
    public final static PotionBase LONG_STRENGTH = new PotionBase("long_strength") {};
    /**
     * 基础药水效果类型: 虚弱药水
     */
    public final static PotionBase WEAKNESS = new PotionBase("weakness") {};
    /**
     * 基础药水效果类型: 虚弱药水 延长版
     */
    public final static PotionBase LONG_WEAKNESS = new PotionBase("long_weakness") {};
    /**
     * 基础药水效果类型: 幸运药水
     */
    public final static PotionBase LUCK = new PotionBase("luck") {};

    private String value;

    /**
     * 基础药水效果类构造函数
     *
     * @param value 值
     */
    public PotionBase(String value) {

        this.value = value;
    }

    /**
     * 获取此基础药水的属性值
     * 
     * @return 基础属性值
     */
    public String getValue() {

        return value;
    }

    /**
     * 将指定基础效果名转换为药水基础效果对象
     *
     * @param type 名称
     * @return PotionBase
     * @throws IllegalArgumentException 如果名称对象为 {@code null} 则抛出异常
     */
    public static PotionBase valueOf(String type) {

        return valueOf(type, null);
    }

    /**
     * 将指定基础效果名转换为药水基础效果对象
     *
     * @param type 名称
     * @param def 默认值
     * @return PotionBase
     * @throws IllegalArgumentException 如果名称对象为 {@code null} 则抛出异常
     */
    public static PotionBase valueOf(String type, PotionBase def) {

        Validate.notNull(type, "The potion base type object is null.");

        switch (type.toLowerCase()) {

            case "water":
                return WATER;
            case "mundane":
                return MUNDANE_WATER;
            case "thick":
                return THICK_WATER;
            case "awkward":
                return AWKWARD_WATER;
            case "night_vision":
                return NIGHT_VISION;
            case "long_night_vision":
                return LONG_NIGHT_VISION;
            case "invisibility":
                return INVISIBILITY;
            case "long_invisibility":
                return LONG_INVISIBILITY;
            case "leaping":
                return LEAPING;
            case "strong_leaping":
                return STRONG_LEAPING;
            case "long_leaping":
                return LONG_LEAPING;
            case "fire_resistance":
                return FIRE_RESISTANCE;
            case "long_fire_resistance":
                return LONG_FIRE_RESISTANCE;
            case "swiftness":
                return SWIFTNESS;
            case "strong_swiftness":
                return STRONG_SWIFTNESS;
            case "long_swiftness":
                return LONG_SWIFTNESS;
            case "slowness":
                return SLOWNESS;
            case "long_slowness":
                return LONG_SLOWNESS;
            case "water_breathing":
                return WATER_BREATHING;
            case "long_water_breathing":
                return LONG_WATER_BREATHING;
            case "healing":
                return HEALING;
            case "strong_healing":
                return STRONG_HEALING;
            case "harming":
                return HARMING;
            case "strong_harming":
                return STRONG_HARMING;
            case "poison":
                return POISON;
            case "strong_poison":
                return STRONG_POISON;
            case "long_poison":
                return LONG_POISON;
            case "regeneration":
                return REGENERATION;
            case "strong_regeneration":
                return STRONG_REGENERATION;
            case "long_regeneration":
                return LONG_REGENERATION;
            case "strength":
                return STRENGTH;
            case "strong_strength":
                return STRONG_STRENGTH;
            case "long_strength":
                return LONG_STRENGTH;
            case "weakness":
                return WEAKNESS;
            case "long_weakness":
                return LONG_WEAKNESS;
            case "luck":
                return LUCK;
            default:
                return def;
        }
    }
}
