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

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>PotionEffectType</h1>
 * 药水效果类型
 *
 * @version 1.0
 * @author Month_Light
 */
public enum PotionEffectType {

    /**
     * 药水效果类型: 速度
     */
    SPEED("Speed", 1, "speed"),
    /**
     * 药水效果类型: 缓慢
     */
    SLOW("Slow", 2, "slowness"),
    /**
     * 药水效果类型: 急迫
     */
    FAST_DIGGING("FastDigging", 3, "haste"),
    /**
     * 药水效果类型: 挖掘疲劳
     */
    SLOW_DIGGING("SlowDigging", 4, "mining_fatigue"),
    /**
     * 药水效果类型: 力量
     */
    INCREASE_DAMAGE("IncreaseDamage", 5, "strength"),
    /**
     * 药水效果类型: 瞬间治疗
     */
    HEAL("Heal", 6, "instant_health"),
    /**
     * 药水效果类型: 瞬间伤害
     */
    HARM("Harm", 7, "instant_damage"),
    /**
     * 药水效果类型: 跳跃提升
     */
    JUMP("Jump", 8, "jump_boost"),
    /**
     * 药水效果类型: 反胃
     */
    CONFUSION("Confusion", 9, "nausea"),
    /**
     * 药水效果类型: 生命恢复
     */
    REGENERATION("Regeneration", 10, "regeneration"),
    /**
     * 药水效果类型: 抗性提升
     */
    DAMAGE_RESISTANCE("DamageResistance", 11, "resistance"),
    /**
     * 药水效果类型: 防火
     */
    FIRE_RESISTANCE("FireResistance", 12, "fire_resistance"),
    /**
     * 药水效果类型: 水下呼吸
     */
    WATER_BREATHING("WaterBreathing", 13, "water_breathing"),
    /**
     * 药水效果类型: 隐身
     */
    INVISIBILITY("Invisibility", 14, "invisibility"),
    /**
     * 药水效果类型: 失明
     */
    BLINDNESS("Blindness", 15, "blindness"),
    /**
     * 药水效果类型: 夜视
     */
    NIGHT_VISION("NightVision", 16, "night_vision"),
    /**
     * 药水效果类型: 饥饿
     */
    HUNGER("Hunger", 17, "hunger"),
    /**
     * 药水效果类型: 虚弱
     */
    WEAKNESS("Weakness", 18, "weakness"),
    /**
     * 药水效果类型: 中毒
     */
    POISON("Poison", 19, "poison"),
    /**
     * 药水效果类型: 凋零
     */
    WITHER("Wither", 20, "wither"),
    /**
     * 药水效果类型: 生命提升
     */
    HEALTH_BOOST("HealthBoost", 21, "health_boost"),
    /**
     * 药水效果类型: 伤害吸收
     */
    ABSORPTION("Absorption", 22, "absorption"),
    /**
     * 药水效果类型: 饱和
     */
    SATURATION("Saturation", 23, "saturation"),
    /**
     * 药水效果类型: 发光
     */
    GLOWING("Glowing", 24, "glowing"),
    /**
     * 药水效果类型: 漂浮
     */
    LEVITATION("Levitation", 25, "levitation"),
    /**
     * 药水效果类型: 幸运
     */
    LUCK("Luck", 26, "luck"),
    /**
     * 药水效果类型: 霉运
     */
    UNLUCK("Unluck", 27, "unluck"),
    ;

    private final String type;
    private final int id;
    private final String tagName;
    private final static Map<String, PotionEffectType> NAME_MAP;
    private final static Map<Integer, PotionEffectType> ID_MAP;

    static {

        NAME_MAP = new HashMap<>();
        ID_MAP = new HashMap<>();

        for(final PotionEffectType type : values()) {

            NAME_MAP.put(type.getType(), type);
            ID_MAP.put(type.getId(), type);
        }
    }

    /**
     * 药水效果类型构造函数
     *
     * @param type 类型名
     * @param id 药水效果 Id
     * @param tagName 药水 NBT 名称
     */
    PotionEffectType(String type, int id, String tagName) {

        this.type = type;
        this.id = id;
        this.tagName = tagName;
    }

    /**
     * 获取药水效果的类型
     *
     * @return 类型名
     */
    public String getType() {

        return type;
    }

    /**
     * 获取药水效果的 ID
     *
     * @return ID
     */
    public int getId() {

        return id;
    }

    /**
     * 获取药水效果的 Tag 名称
     *
     * @return Tag 名称
     */
    public String getTagName() {

        return tagName;
    }

    public static PotionEffectType fromType(String type) {

        return NAME_MAP.get(type);
    }

    @Deprecated
    public static PotionEffectType fromId(int id) {

        return ID_MAP.get(id);
    }
}
