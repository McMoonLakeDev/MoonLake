package com.minecraft.moonlake.api.item.potion;

import com.minecraft.moonlake.property.ReadOnlyStringProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;

/**
 * Created by MoonLake on 2016/9/15.
 */
public abstract class PotionBase {

    /**
     * 基础药水效果类型: 水瓶
     */
    public final static PotionBase WATER = new PotionBaseWrapped("water");
    /**
     * 基础药水效果类型: 平凡的药水
     */
    public final static PotionBase MUNDANE_WATER = new PotionBaseWrapped("mundane");
    /**
     * 基础药水效果类型: 浑浊的药水
     */
    public final static PotionBase THICK_WATER = new PotionBaseWrapped("thick");
    /**
     * 基础药水效果类型: 粗制的药水
     */
    public final static PotionBase AWKWARD_WATER = new PotionBaseWrapped("awkward");
    /**
     * 基础药水效果类型: 夜视药水
     */
    public final static PotionBase NIGHT_VISION = new PotionBaseWrapped("night_vision");
    /**
     * 基础药水效果类型: 夜视药水 延长版
     */
    public final static PotionBase LONG_NIGHT_VISION = new PotionBaseWrapped("long_night_vision");
    /**
     * 基础药水效果类型: 隐身药水
     */
    public final static PotionBase INVISIBILITY = new PotionBaseWrapped("invisibility");
    /**
     * 基础药水效果类型: 隐身药水 延长版
     */
    public final static PotionBase LONG_INVISIBILITY = new PotionBaseWrapped("long_invisibility");
    /**
     * 基础药水效果类型: 跳跃药水
     */
    public final static PotionBase LEAPING = new PotionBaseWrapped("leaping");
    /**
     * 基础药水效果类型: 跳跃药水 高级版
     */
    public final static PotionBase STRONG_LEAPING = new PotionBaseWrapped("strong_leaping");
    /**
     * 基础药水效果类型: 跳跃药水 延长版
     */
    public final static PotionBase LONG_LEAPING = new PotionBaseWrapped("long_leaping");
    /**
     * 基础药水效果类型: 抗火药水
     */
    public final static PotionBase FIRE_RESISTANCE = new PotionBaseWrapped("fire_resistance");
    /**
     * 基础药水效果类型: 抗火药水 延长版
     */
    public final static PotionBase LONG_FIRE_RESISTANCE = new PotionBaseWrapped("long_fire_resistance");
    /**
     * 基础药水效果类型: 迅捷药水
     */
    public final static PotionBase SWIFTNESS = new PotionBaseWrapped("swiftness");
    /**
     * 基础药水效果类型: 迅捷药水 高级版
     */
    public final static PotionBase STRONG_SWIFTNESS = new PotionBaseWrapped("strong_swiftness");
    /**
     * 基础药水效果类型: 迅捷药水 延长版
     */
    public final static PotionBase LONG_SWIFTNESS = new PotionBaseWrapped("long_swiftness");
    /**
     * 基础药水效果类型: 缓慢药水
     */
    public final static PotionBase SLOWNESS = new PotionBaseWrapped("slowness");
    /**
     * 基础药水效果类型: 缓慢药水 延长版
     */
    public final static PotionBase LONG_SLOWNESS = new PotionBaseWrapped("long_slowness");
    /**
     * 基础药水效果类型: 水下呼吸
     */
    public final static PotionBase WATER_BREATHING = new PotionBaseWrapped("water_breathing");
    /**
     * 基础药水效果类型: 水下呼吸 延长版
     */
    public final static PotionBase LONG_WATER_BREATHING = new PotionBaseWrapped("long_water_breathing");
    /**
     * 基础药水效果类型: 治疗药水
     */
    public final static PotionBase HEALING = new PotionBaseWrapped("healing");
    /**
     * 基础药水效果类型: 治疗药水 高级版
     */
    public final static PotionBase STRONG_HEALING = new PotionBaseWrapped("strong_healing");
    /**
     * 基础药水效果类型: 伤害药水
     */
    public final static PotionBase HARMING = new PotionBaseWrapped("harming");
    /**
     * 基础药水效果类型: 伤害药水 高级版
     */
    public final static PotionBase STRONG_HARMING = new PotionBaseWrapped("strong_harming");
    /**
     * 基础药水效果类型: 中毒药水
     */
    public final static PotionBase POISON = new PotionBaseWrapped("poison");
    /**
     * 基础药水效果类型: 中毒药水 高级版
     */
    public final static PotionBase STRONG_POISON = new PotionBaseWrapped("strong_poison");
    /**
     * 基础药水效果类型: 中毒药水 延长版
     */
    public final static PotionBase LONG_POISON = new PotionBaseWrapped("long_poison");
    /**
     * 基础药水效果类型: 再生药水
     */
    public final static PotionBase REGENERATION = new PotionBaseWrapped("regeneration");
    /**
     * 基础药水效果类型: 再生药水 高级版
     */
    public final static PotionBase STRONG_REGENERATION = new PotionBaseWrapped("strong_regeneration");
    /**
     * 基础药水效果类型: 再生药水 延长版
     */
    public final static PotionBase LONG_REGENERATION = new PotionBaseWrapped("long_regeneration");
    /**
     * 基础药水效果类型: 力量药水
     */
    public final static PotionBase STRENGTH = new PotionBaseWrapped("strength");
    /**
     * 基础药水效果类型: 力量药水 高级版
     */
    public final static PotionBase STRONG_STRENGTH = new PotionBaseWrapped("strong_strength");
    /**
     * 基础药水效果类型: 力量药水 延长版
     */
    public final static PotionBase LONG_STRENGTH = new PotionBaseWrapped("long_strength");
    /**
     * 基础药水效果类型: 虚弱药水
     */
    public final static PotionBase LONG_WEAKNESS = new PotionBaseWrapped("weakness");
    /**
     * 基础药水效果类型: 虚弱药水 延长版
     */
    public final static PotionBase WEAKNESS = new PotionBaseWrapped("long_weakness");

    private ReadOnlyStringProperty value;

    public PotionBase(String value) {

        this.value = new SimpleStringProperty(value);
    }

    /**
     * 获取此基础药水的属性值
     * 
     * @return 基础属性值
     */
    public ReadOnlyStringProperty getValue() {

        return value;
    }
}
