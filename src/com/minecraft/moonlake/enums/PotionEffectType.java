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

import com.minecraft.moonlake.api.item.potion.PotionEffectCustom;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.reflect.Reflect;

/**
 * <h1>PotionEffectType</h1>
 * 药水效果类型中文汉化版（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public abstract class PotionEffectType {

    // 翻译均来自中文 wiki
    // http://minecraft-zh.gamepedia.com/%E7%8A%B6%E6%80%81%E6%95%88%E6%9E%9C#.E6.95.88.E6.9E.9C.E5.88.97.E8.A1.A8
    ///

    /**
     * 药水效果类型: 速度
     */
    public final static PotionEffectType 速度 = new PotionEffectType(1, "SPEED") {};
    /**
     * 药水效果类型: 缓慢
     */
    public final static PotionEffectType 缓慢 = new PotionEffectType(2, "SLOW") {};
    /**
     * 药水效果类型: 急迫
     */
    public final static PotionEffectType 急迫 = new PotionEffectType(3, "FAST_DIGGING") {};
    /**
     * 药水效果类型: 挖掘疲劳
     */
    public final static PotionEffectType 挖掘疲劳 = new PotionEffectType(4, "SLOW_DIGGING") {};
    /**
     * 药水效果类型: 力量
     */
    public final static PotionEffectType 力量 = new PotionEffectType(5, "INCREASE_DAMAGE") {};
    /**
     * 药水效果类型: 瞬间治疗
     */
    public final static PotionEffectType 瞬间治疗 = new PotionEffectType(6, "HEAL") {};
    /**
     * 药水效果类型: 瞬间伤害
     */
    public final static PotionEffectType 瞬间伤害 = new PotionEffectType(7, "HARM") {};
    /**
     * 药水效果类型: 跳跃提升
     */
    public final static PotionEffectType 跳跃提升 = new PotionEffectType(8, "JUMP") {};
    /**
     * 药水效果类型: 反胃
     */
    public final static PotionEffectType 反胃 = new PotionEffectType(9, "CONFUSION") {};
    /**
     * 药水效果类型: 生命恢复
     */
    public final static PotionEffectType 生命恢复 = new PotionEffectType(10, "REGENERATION") {};
    /**
     * 药水效果类型: 抗性提升
     */
    public final static PotionEffectType 抗性提升 = new PotionEffectType(11, "DAMAGE_RESISTANCE") {};
    /**
     * 药水效果类型: 防火
     */
    public final static PotionEffectType 防火 = new PotionEffectType(12, "FIRE_RESISTANCE") {};
    /**
     * 药水效果类型: 水下呼吸
     */
    public final static PotionEffectType 水下呼吸 = new PotionEffectType(13, "WATER_BREATHING") {};
    /**
     * 药水效果类型: 隐身
     */
    public final static PotionEffectType 隐身 = new PotionEffectType(14, "INVISIBILITY") {};
    /**
     * 药水效果类型: 失明
     */
    public final static PotionEffectType 失明 = new PotionEffectType(15, "BLINDNESS") {};
    /**
     * 药水效果类型: 夜视
     */
    public final static PotionEffectType 夜视 = new PotionEffectType(16, "NIGHT_VISION") {};
    /**
     * 药水效果类型: 饥饿
     */
    public final static PotionEffectType 饥饿 = new PotionEffectType(17, "HUNGER") {};
    /**
     * 药水效果类型: 虚弱
     */
    public final static PotionEffectType 虚弱 = new PotionEffectType(18, "WEAKNESS") {};
    /**
     * 药水效果类型: 中毒
     */
    public final static PotionEffectType 中毒 = new PotionEffectType(19, "POISON") {};
    /**
     * 药水效果类型: 凋零
     */
    public final static PotionEffectType 凋零 = new PotionEffectType(20, "WITHER") {};
    /**
     * 药水效果类型: 生命提升
     */
    public final static PotionEffectType 生命提升 = new PotionEffectType(21, "HEALTH_BOOST") {};
    /**
     * 药水效果类型: 伤害吸收
     */
    public final static PotionEffectType 伤害吸收 = new PotionEffectType(22, "ABSORPTION") {};
    /**
     * 药水效果类型: 饱和
     */
    public final static PotionEffectType 饱和 = new PotionEffectType(23, "SATURATION") {};


    // 1.9 增加的新药水效果（不兼容 1.8 以下
    /**
     * 药水效果类型: 发光
     */
    public final static PotionEffectType 发光 = new PotionEffectType(24, "GLOWING", 9) {};
    /**
     * 药水效果类型: 漂浮
     */
    public final static PotionEffectType 漂浮 = new PotionEffectType(25, "LEVITATION", 9) {};
    /**
     * 药水效果类型: 幸运
     */
    public final static PotionEffectType 幸运 = new PotionEffectType(26, "LUCK", 9) {};
    /**
     * 药水效果类型: 霉运
     */
    public final static PotionEffectType 霉运 = new PotionEffectType(27, "UNLUCK", 9) {};
    ///

    private final int id;
    private final String name;
    private final int requiredVersion;

    /**
     * 药水效果类型构造函数
     *
     * @param id 药水效果 Id
     * @param name 药水效果名称
     */
    private PotionEffectType(int id, String name) {

        this(id, name, -1);
    }

    /**
     * 药水效果类型构造函数
     *
     * @param id 药水效果 Id
     * @param name 药水效果名称
     * @param requiredVersion 需求版本
     */
    private PotionEffectType(int id, String name, int requiredVersion) {

        this.id = id;
        this.name = name;
        this.requiredVersion = requiredVersion;
    }

    /**
     * 获取此药水效果类型的 Id
     *
     * @return Id
     * @deprecated 已过时, 请使用 {@link #getName()}
     */
    @Deprecated
    public int getId() {

        return id;
    }

    /**
     * 获取此药水效果类型的名称
     *
     * @return 名称
     */
    public String getName() {

        return name;
    }

    /**
     * 获取此药水效果类型需求的版本
     *
     * @return 版本
     */
    public int getRequiredVersion() {

        return requiredVersion;
    }

    /**
     * 转换为 Bukkit 的 PotionEffectType 对象
     *
     * @return PotionEffectType
     * @throws IllegalBukkitVersionException 如果 Bukkit 服务器版本不支持药水效果则抛出异常
     */
    @SuppressWarnings("deprecation")
    public org.bukkit.potion.PotionEffectType as() {

        if(requiredVersion != -1) {

            int version = Reflect.getServerVersionNumber();

            if(version < requiredVersion) {

                throw new IllegalBukkitVersionException("The bukkit version not support potion effect: " + id);
            }
        }
        //return org.bukkit.potion.PotionEffectType.getById(id);
        return org.bukkit.potion.PotionEffectType.getByName(name);
    }

    /**
     * 转换到 API 的 PotionEffectType 枚举对象
     *
     * @return PotionEffectType
     */
    @SuppressWarnings("deprecation")
    public com.minecraft.moonlake.api.item.potion.PotionEffectType to() {

        return com.minecraft.moonlake.api.item.potion.PotionEffectType.fromId(id);
    }

    /**
     * 将此药水效果创建为自定义药水效果对象
     *
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return PotionEffectCustom
     */
    public PotionEffectCustom createCustom(int amplifier, int duration, boolean ambient, boolean showParticles) {

        return new PotionEffectCustom(to(), amplifier, duration, ambient, showParticles);
    }

    /**
     * 将此药水效果创建为自定义药水效果对象
     *
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @return PotionEffectCustom
     */
    public PotionEffectCustom createCustom(int amplifier, int duration, boolean ambient) {

        return createCustom(amplifier, duration, ambient, false);
    }

    /**
     * 将此药水效果创建为自定义药水效果对象
     *
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @return PotionEffectCustom
     */
    public PotionEffectCustom createCustom(int amplifier, int duration) {

        return createCustom(amplifier, duration, false);
    }
}
