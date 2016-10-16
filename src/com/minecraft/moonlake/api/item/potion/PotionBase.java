package com.minecraft.moonlake.api.item.potion;

/**
 * <h1>PotionBase</h1>
 * 基础药水效果
 *
 * @version 1.0
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
    public final static PotionBase LONG_WEAKNESS = new PotionBase("weakness") {};
    /**
     * 基础药水效果类型: 虚弱药水 延长版
     */
    public final static PotionBase WEAKNESS = new PotionBase("long_weakness") {};

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
}
