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
 
 
package com.minecraft.moonlake.api.entity;

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.validate.Validate;
import org.spigotmc.SpigotConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * <hr />
 * <div>
 *     <h1>Minecraft Entity Attribute Type</h1>
 *     <p>By Month_Light Ver: 1.1</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>Minecraft 实体属性枚举类型</h1>
 *     <p>详情可以去中文wiki查看更详细的内容:<a href="http://minecraft-zh.gamepedia.com/%E5%B1%9E%E6%80%A7" target="_blank">查看</a></p>
 * </div>
 * <hr />
 *
 * @version 1.1
 * @author Month_Light
 */
public enum AttributeType {

    /**
     * 实体属性类型: 生命上限 (def: 20.0, range: 0.0 - SpigotConfig.maxHealth)
     *
     * @see org.spigotmc.SpigotConfig#maxHealth
     * @see "spigot.yml >> settings.attribute.maxHealth.max (def: 2048.0)"
     */
    MAX_HEALTH("MaxHealth", "generic.maxHealth", 20.0d, 0.0d, SpigotConfig.maxHealth),
    /**
     * 实体属性类型: 追踪范围 (def: 32.0, range: 0.0 - 2048.0)
     */
    FOLLOW_RANGE("FollowRange", "generic.followRange", 32.0d, 0.0d, 2048.0d),
    /**
     * 实体属性类型: 抗击退 (def: 0.0, range: 0.0 - 1.0)
     */
    KNOCK_BACK_RESISTANCE("KnockBackResistance", "generic.knockbackResistance", 0.0d, 0.0d, 1.0d),
    /**
     * 实体属性类型: 移动速度 (def: 0.699999988079071, range: 0.0 - SpigotConfig.movementSpeed)
     *
     * @see org.spigotmc.SpigotConfig#movementSpeed
     * @see "spigot.yml >> settings.attribute.movementSpeed.max (def: 2048.0)"
     */
    MOVEMENT_SPEED("MovementSpeed", "generic.movementSpeed", 0.699999988079071d, 0.0d, SpigotConfig.movementSpeed),
    /**
     * 实体属性类型: 攻击伤害 (def: 2.0, range: 0.0 - SpigotConfig.attackDamage)
     *
     * @see org.spigotmc.SpigotConfig#attackDamage
     * @see "spigot.yml >> settings.attribute.attackDamage.max (def: 2048.0)"
     */
    ATTACK_DAMAGE("AttackDamage", "generic.attackDamage", 2.0d, 0.0d, SpigotConfig.attackDamage),
    /**
     * 实体属性类型: 攻击速度 (def: 4.0, range: 0.0 - 1024.0)
     */
    ATTACK_SPEED("AttackSpeed", "generic.attackSpeed", MinecraftVersion.V1_9, 4.0d, 0.0d, 1024.0d),
    /**
     * 实体属性类型: 护甲 (def: 0.0, range: 0.0 - 30.0)
     */
    ARMOR("Armor", "generic.armor", MinecraftVersion.V1_9, 0.0d, 0.0d, 30.0d),
    /**
     * 实体属性类型: 护甲韧性 (def: 0.0, range: 0.0 - 20.0)
     */
    ARMOR_TOUGHNESS("ArmorToughness", "generic.armorToughness", MinecraftVersion.V1_9, 0.0d, 0.0d, 20.0d),
    /**
     * 实体属性类型: 幸运 (def: 0.0, range: -1024.0 - 1024.0)
     */
    LUCK("Luck", "generic.luck", MinecraftVersion.V1_9, 0.0d, -1024.0d, 1024.0d),
    /**
     * 实体属性类型: 飞行速度 (def: 0.4000000059604645, range: 0.0 - 1024.0)
     */
    FLYING_SPEED("FlyingSpeed", "generic.flyingSpeed", MinecraftVersion.V1_12, 0.4000000059604645d, 0.0d, 1024.0d);
    ;

    private String type;
    private String name;
    private MinecraftVersion minimumVersion;
    private double def;
    private double min;
    private double max;
    private final static Map<AttributeType, Object> MAPPING = new HashMap<>();

    /**
     * 实体属性类型构造函数
     *
     * @param type 类型名
     * @param name 名称
     * @param def 默认值
     * @param min 最小值
     * @param max 最大值
     */
    AttributeType(String type, String name, double def, double min, double max) {

        this(type, name, null, def, min, max);
    }

    /**
     * 实体属性类型构造函数
     *
     * @param type 类型名
     * @param name 名称
     * @param minimumVersion 最低服务端版本
     * @param def 默认值
     * @param min 最小值
     * @param max 最大值
     */
    AttributeType(String type, String name, MinecraftVersion minimumVersion, double def, double min, double max) {

        this.type = type;
        this.name = name;
        this.minimumVersion = minimumVersion;
        this.def = def;
        this.min = min;
        this.max = max;
    }

    /**
     * 获取实体属性类型名
     *
     * @return 类型名
     */
    public String getType() {

        return type;
    }

    /**
     * 获取实体属性的名称
     *
     * @return 名称
     */
    public String getName() {

        return name;
    }

    /**
     * 获取实体属性类型在 NMS 中的字段名
     *
     * @return 字段名
     * @deprecated @deprecated 已过时, 将于 v2.0 删除.
     * @throws UnsupportedOperationException Exception
     */
    @Deprecated
    public String getField() {

        throw new UnsupportedOperationException();
    }

    /**
     * 获取实体属性的默认值
     *
     * @return 默认值
     */
    public double getDef() {

        return def;
    }

    /**
     * 获取实体属性的最小值
     *
     * @return 最小值
     */
    public double getMin() {

        return min;
    }

    /**
     * 获取实体属性的最大值
     *
     * @return 最大值
     */
    public double getMax() {

        return max;
    }

    /**
     * 获取实体属性的安全值
     *
     * @param value 值
     * @return 安全值
     * @throws IllegalArgumentException 如果值小于最小值或大于最大值则抛出异常
     */
    public double getSafeValue(double value) {

        Validate.isTrue(value >= min, "The attribute type target value less than min.");
        Validate.isTrue(value <= max, "The attribute type target value greater than max.");

        return value;
    }

    /**
     * 获取实体属性是否支持 Bukkit 版本
     *
     * @return 是否支持
     * @throws IllegalBukkitVersionException 如果 Bukkit 服务器版本不支持则抛出异常
     */
    public boolean isSupported() {

        boolean support = minimumVersion == null || MoonLakeAPI.currentMCVersion().isOrLater(minimumVersion);

        if(!support) {

            throw new IllegalBukkitVersionException("The entity attribute type not support bukkit version.");
        }
        return true;
    }

    public Object getIAttribute() {
        Object iAttribute = MAPPING.get(this);
        if(iAttribute == null) {
            iAttribute = MinecraftReflection.getGenericAttributes(this);
            MAPPING.put(this, iAttribute);
        }
        return iAttribute;
    }
}
