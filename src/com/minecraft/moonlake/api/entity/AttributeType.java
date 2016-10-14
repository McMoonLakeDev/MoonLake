package com.minecraft.moonlake.api.entity;

import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.validate.Validate;
import org.spigotmc.SpigotConfig;

/**
 * <hr />
 * <div>
 *     <h1>Minecraft Entity Attribute Type</h1>
 *     <p>By Month_Light Ver: 1.0</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>Minecraft 实体属性枚举类型</h1>
 *     <p>详情可以去中文wiki查看更详细的内容:<a href="http://minecraft-zh.gamepedia.com/%E5%B1%9E%E6%80%A7" target="_blank">查看</a></p>
 * </div>
 * <hr />
 *
 * @version 1.0
 * @author Month_Light
 */
public enum AttributeType {

    /**
     * 实体属性类型: 生命上限 (def: 20.0, range: 0.0 - SpigotConfig.maxHealth)
     *
     * @see org.spigotmc.SpigotConfig#maxHealth
     * @see "spigot.yml >> settings.attribute.maxHealth.max (def: 2048.0)"
     */
    MAX_HEALTH("MaxHealth", "maxHealth", 20.0d, 0.0d, SpigotConfig.maxHealth),
    /**
     * 实体属性类型: 追踪范围 (def: 32.0, range: 0.0 - 2048.0)
     */
    FOLLOW_RANGE("FollowRange", "FOLLOW_RANGE", 32.0d, 0.0d, 2048.0d),
    /**
     * 实体属性类型: 抗击退 (def: 0.0, range: 0.0 - 1.0)
     */
    KNOCK_BACK_RESISTANCE("KnockBackResistance", "c", 0.0d, 0.0d, 1.0d),
    /**
     * 实体属性类型: 移动速度 (def: 0.699999988079071, range: 0.0 - SpigotConfig.movementSpeed)
     *
     * @see org.spigotmc.SpigotConfig#movementSpeed
     * @see "spigot.yml >> settings.attribute.movementSpeed.max (def: 2048.0)"
     */
    MOVEMENT_SPEED("MovementSpeed", "MOVEMENT_SPEED", 0.699999988079071d, 0.0d, SpigotConfig.movementSpeed),
    /**
     * 实体属性类型: 攻击伤害 (def: 2.0, range: 0.0 - SpigotConfig.attackDamage)
     *
     * @see org.spigotmc.SpigotConfig#attackDamage
     * @see "spigot.yml >> settings.attribute.attackDamage.max (def: 2048.0)"
     */
    ATTACK_DAMAGE("AttackDamage", "ATTACK_DAMAGE", 2.0d, 0.0d, SpigotConfig.attackDamage),
    /**
     * 实体属性类型: 攻击速度 (def: 4.0, range: 0.0 - 1024.0)
     */
    ATTACK_SPEED("AttackSpeed", "f", 9, 4.0d, 0.0d, 1024.0d),
    /**
     * 实体属性类型: 护甲 (def: 0.0, range: 0.0 - 30.0)
     */
    ARMOR("Armor", "g", 9, 0.0d, 0.0d, 30.0d),
    /**
     * 实体属性类型: 护甲韧性 (def: 0.0, range: 0.0 - 20.0)
     */
    ARMOR_TOUGHNESS("ArmorToughness", "h", 9, 0.0d, 0.0d, 20.0d),
    /**
     * 实体属性类型: 幸运 (def: 0.0, range: -1024.0 - 1024.0)
     */
    LUCK("Luck", "i", 9, 0.0d, -1024.0d, 1024.0d),
    ;

    private String type;
    private String field;
    private int minimumVersion;
    private double def;
    private double min;
    private double max;

    AttributeType(String type, String field, double def, double min, double max) {

        this(type, field, -1, def, min, max);
    }

    AttributeType(String type, String field, int minimumVersion, double def, double min, double max) {

        this.type = type;
        this.field = field;
        this.minimumVersion = minimumVersion;
        this.def = def;
        this.min = min;
        this.max = max;
    }

    public String getType() {

        return type;
    }

    public String getField() {

        return field;
    }

    public double getDef() {

        return def;
    }

    public double getMin() {

        return min;
    }

    public double getMax() {

        return max;
    }

    public double getSafeValue(double value) {

        Validate.isTrue(value >= min, "The attribute type target value less than min.");
        Validate.isTrue(value <= max, "The attribute type target value greater than max.");

        return value;
    }

    public boolean isSupported() {

        boolean support = minimumVersion == -1 || Reflect.getServerVersionNumber() >= minimumVersion;

        if(!support) {

            throw new IllegalBukkitVersionException("The entity attribute type not support bukkit version.");
        }
        return true;
    }
}