package com.minecraft.moonlake.type.potion;

import com.minecraft.moonlake._temp.util.Util;
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

    /**
     * 将字串符序列化为药水类型对象
     *
     * @param type 药水类型
     * @return PotionEnum 如果不存在类型则返回 null
     */
    public static PotionEnum fromType(String type) {

        Util.notEmpty(type, "待转换的药水类型是 null 值");

        switch (type.toLowerCase()) {
            case "potion":
                return POTION;
            case "splashpotion":
            case "splash_potion":
                return SPLASH_POTION;
            case "lingeringpotion":
            case "lingering_potion":
                return LINGERING_POTION;
            default:
                return null;
        }
    }
}
