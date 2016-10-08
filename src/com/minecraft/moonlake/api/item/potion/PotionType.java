package com.minecraft.moonlake.api.item.potion;

import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.Material;

/**
 * Created by MoonLake on 2016/9/15.
 */
public enum PotionType {

    /**
     * 药水类型枚举: 普通药水
     */
    POTION("Potion", 373),
    /**
     * 药水类型枚举: 投掷药水
     */
    SPLASH_POTION("SplashPotion", 438),
    /**
     * 药水类型枚举: 滞留药水
     */
    LINGERING_POTION("LingeringPotion", 439),
    ;

    private final String type;
    private final int id;

    PotionType(String type, int id) {

        this.type = type;
        this.id = id;
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
     * <p></p>
     * <p>注意: 假如您的支持库为 1.8 以下版本, 那么你是找不到下面这些类型的</p>
     * <ul>
     *     <li>1.9 增加的喷溅药水类型 {@link Material#SPLASH_POTION}</li>
     *     <li>1.9 增加的滞留药水类型 {@link Material#LINGERING_POTION}</li>
     * </ul>
     * <p>只会拥有这一个药水类型: {@link Material#POTION}</p>
     * @return 物品栈类型
     * @throws IllegalBukkitVersionException 如果 Bukkit 服务器版本不支持药水类型则抛出异常
     */
    @SuppressWarnings("deprecation")
    public Material getMaterial() throws IllegalBukkitVersionException {

        int version = Reflect.getServerVersionNumber();

        if(version <= 8 && this == LINGERING_POTION) {
            // 1.8 use lingering potion then throw new exception
            throw new IllegalBukkitVersionException("The lingering potion need bukkit version 1.9+ exception.");
        }
        if(version <= 8 && this == SPLASH_POTION) {
            // 1.8 Material not has Material.SPLASH_POTION
            // need Material.POTION type
            return Material.POTION;
        }
        return Material.getMaterial(id);
    }
}
