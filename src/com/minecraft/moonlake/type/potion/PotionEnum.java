package com.minecraft.moonlake.type.potion;

import org.bukkit.Material;

/**
 * Created by MoonLake on 2016/4/29.
 * @version 1.0
 * @author Month_Light
 */
public enum PotionEnum {

    /**
     * 药水类型枚举: 普通药水
     */
    POTION("Potion", Material.POTION),
    /**
     * 药水类型枚举: 投掷药水
     */
    SPLASH_POTION("SplashPotion", Material.SPLASH_POTION),
    /**
     * 药水类型枚举: 滞留药水
     */
    LINGERING_POTION("LingeringPotion", Material.LINGERING_POTION);

    private String type;
    private Material material;

    PotionEnum(String type, Material material) {
        this.type = type;
        this.material = material;
    }

    /**
     * 药水效果的名称
     *
     * @return 名称
     */
    @Deprecated
    public String getType() {
        return type;
    }

    /**
     * 药水类型的材料
     *
     * @return 材料
     */
    public Material getMaterial() {
        return material;
    }
}
