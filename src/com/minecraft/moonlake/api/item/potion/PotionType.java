package com.minecraft.moonlake.api.item.potion;

import org.bukkit.Material;

/**
 * Created by MoonLake on 2016/9/15.
 */
public enum PotionType {

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
    LINGERING_POTION("LingeringPotion", Material.LINGERING_POTION),
    ;

    private final String type;
    private final Material material;

    PotionType(String type, Material material) {

        this.type = type;
        this.material = material;
    }

    /**
     * 获取此药水的类型
     *
     * @return 类型名
     */
    public String getType() {

        return type;
    }

    /**
     * 获取此药水的物品栈类型
     *
     * @return 物品栈类型
     */
    public Material getMaterial() {

        return material;
    }
}
