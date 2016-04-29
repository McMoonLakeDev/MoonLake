package com.minecraft.moonlake.type.potion;

/**
 * Created by MoonLake on 2016/4/29.
 * @version 1.0
 * @author Month_Light
 */
public enum PotionEffectEnum {

    /**
     * 药水效果类型: 水瓶
     */
    WATER("Water", "water", null, null),
    /**
     * 药水效果类型: 平凡的药水
     */
    MUNDANE_WATER("MundaneWater", "mundane", null, null),
    /**
     * 药水效果类型: 浑浊的药水
     */
    THICK_WATER("ThickWater", "thick", null, null),
    /**
     * 药水效果类型: 粗制的药水
     */
    AWKWARD_WATER("AwkwardWater", "awkward", null, null),

    /**
     * 药水效果类型: 夜视药水
     */
    NIGHT_VISION("NightVision", "night_vision", null, "long_night_vision"),
    /**
     * 药水效果类型: 隐身药水
     */
    INVISIBILITY("Invisibility", "invisibility", null, "long_invisibility"),
    /**
     * 药水效果类型: 跳跃药水
     */
    LEAPING("Leaping", "leaping", "strong_leaping", "long_leaping"),
    /**
     * 药水效果类型: 抗火药水
     */
    FIRE_RESISTANCE("FireResistance", "fire_resistance", null, "long_fire_resistance"),
    /**
     * 药水效果类型: 迅捷药水
     */
    SWIFTNESS("Swiftness", "swiftness", "strong_swiftness", "long_swiftness"),
    /**
     * 药水效果类型: 缓慢药水
     */
    SLOWNESS("Slowness", "slowness", null, "long_slowness"),
    /**
     * 药水效果类型: 水下呼吸
     */
    WATER_BREATHING("WaterBreathing", "water_breathing", null, "long_water_breathing"),
    /**
     * 药水效果类型: 治疗药水
     */
    HEALING("Healing", "healing", "strong_healing", null),
    /**
     * 药水效果类型: 伤害药水
     */
    HARMING("Harming", "harming", "strong_harming", null),
    /**
     * 药水效果类型: 中毒药水
     */
    POISON("Poison", "poison", "strong_poison", "long_poison"),
    /**
     * 药水效果类型: 再生药水
     */
    REGENERATION("Regeneration", "regeneration", "strong_regeneration", "long_regeneration"),
    /**
     * 药水效果类型: 力量药水
     */
    STRENGTH("Strength", "strength", "strong_strength", "long_strength"),
    /**
     * 药水效果类型: 虚弱药水
     */
    WEAKNESS("Weakness", "weakness", null, "long_weakness"),
    ;

    private String type;
    private String _1;
    private String _2;
    private String _long;

    PotionEffectEnum(String type, String _1, String _2, String _long) {
        this.type = type;
        this._1 = _1;
        this._2 = _2;
        this._long = _long;
    }

    /**
     * 药水效果的类型名称
     *
     * @return 名称
     */
    @Deprecated
    public String getType() {
        return type;
    }

    /**
     * 药水效果的基础版
     *
     * @return 基础
     */
    public String getBase() {
        return _1;
    }

    /**
     * 药水效果的升级版
     *
     * @return 升级
     * @throws NullPointerException 药水效果没有升级版则抛出异常
     */
    public String getUp() {
        return _2;
    }

    /**
     * 药水效果的延长版
     *
     * @return 延长
     * @throws NullPointerException 药水效果没有延长版则抛出异常
     */
    public String getLong() {
        return _long;
    }
}
