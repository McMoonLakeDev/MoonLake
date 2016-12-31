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
 
 
package com.minecraft.moonlake.api.item;

import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.validate.Validate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <h1>ItemStack AttributeModify</h1>
 * 物品栈属性修改封装类
 *
 * @version 1.0
 * @author Month_Light
 */
public final class AttributeModify {

    private ObjectProperty<Type> attributeType;
    private ObjectProperty<Slot> attributeSlot;
    private ObjectProperty<Operation> operation;
    private ObjectProperty<UUID> uuid;
    private DoubleProperty amount;

    /**
     * 物品栈属性修改封装类构造函数
     *
     * @param type 属性类型
     * @param operation 属性运算方式
     * @param amount 数量值
     * @deprecated 已过时，请使用 {@link #AttributeModify(Type, Operation, double)}
     * @see #AttributeModify(Type, Operation, double)
     */
    @Deprecated
    public AttributeModify(Type type, int operation, int amount) {

        this(type, Slot.ALL, amount, operation);
    }

    /**
     * 物品栈属性修改封装类构造函数
     *
     * @param type 属性类型
     * @param operation 属性运算方式
     * @param amount 数量值
     * @param uuid 属性 UUID
     * @deprecated 已过时，请使用 {@link #AttributeModify(Type, Operation, double, UUID)}
     * @see #AttributeModify(Type, Operation, double, UUID)
     */
    @Deprecated
    public AttributeModify(Type type, int operation, int amount, UUID uuid) {

        this(type, Slot.ALL, amount, operation, uuid);
    }

    /**
     * 物品栈属性修改封装类构造函数
     *
     * @param type 属性类型
     * @param slot 属性生效槽位
     * @param operation 属性运算方式
     * @param amount 数量值
     * @deprecated 已过时，请使用 {@link #AttributeModify(Type, Slot, Operation, double)}
     * @see #AttributeModify(Type, Slot, Operation, double)
     */
    @Deprecated
    public AttributeModify(Type type, Slot slot, int operation, int amount) {

        this(type, slot, Operation.fromValue(operation), amount);
    }

    /**
     * 物品栈属性修改封装类构造函数
     *
     * @param type 属性类型
     * @param slot 属性生效槽位
     * @param operation 属性运算方式
     * @param amount 数量值
     * @param uuid 属性 UUID
     * @deprecated 已过时，请使用 {@link #AttributeModify(Type, Slot, Operation, double, UUID)}
     * @see #AttributeModify(Type, Slot, Operation, double, UUID)
     */
    @Deprecated
    public AttributeModify(Type type, Slot slot, int operation, int amount, UUID uuid) {

        this(type, slot, Operation.fromValue(operation), amount, uuid);
    }

    /**
     * 物品栈属性修改封装类构造函数
     *
     * @param type 属性类型
     * @param operation 属性运算方式
     * @param amount 数量值
     */
    public AttributeModify(Type type, Operation operation, double amount) {

        this(type, Slot.ALL, operation, amount);
    }

    /**
     * 物品栈属性修改封装类构造函数
     *
     * @param type 属性类型
     * @param operation 属性运算方式
     * @param amount 数量值
     * @param uuid 属性 UUID
     */
    public AttributeModify(Type type, Operation operation, double amount, UUID uuid) {

        this(type, Slot.ALL, operation, amount, uuid);
    }

    /**
     * 物品栈属性修改封装类构造函数
     *
     * @param type 属性类型
     * @param slot 属性生效槽位
     * @param operation 属性运算方式
     * @param amount 数量值
     */
    public AttributeModify(Type type, Slot slot, Operation operation, double amount) {

        this(type, slot, operation, amount, null);
    }

    /**
     * 物品栈属性修改封装类构造函数
     *
     * @param type 属性类型
     * @param slot 属性生效槽位
     * @param operation 属性运算方式
     * @param amount 数量值
     * @param uuid 属性 UUID
     */
    public AttributeModify(Type type, Slot slot, Operation operation, double amount, UUID uuid) {

        this.attributeType = new SimpleObjectProperty<>(type);
        this.attributeSlot = new SimpleObjectProperty<>(slot);
        this.amount = new SimpleDoubleProperty(amount);
        this.operation = new SimpleObjectProperty<>(operation);
        this.uuid = new SimpleObjectProperty<>(uuid);
    }

    public ObjectProperty<Slot> getSlot() {

        return attributeSlot;
    }

    public ObjectProperty<Type> getType() {

        return attributeType;
    }

    public ObjectProperty<Operation> getOperation() {

        return operation;
    }

    public ObjectProperty<UUID> getUUID() {

        return uuid;
    }

    public DoubleProperty getAmount() {

        return amount;
    }

    /**
     * 属性的类型项: <a href="http://minecraft-zh.gamepedia.com/%E5%B1%9E%E6%80%A7#.E6.89.80.E6.9C.89.E5.AE.9E.E4.BD.93.E5.9D.87.E5.8C.85.E5.90.AB.E7.9A.84.E5.B1.9E.E6.80.A7.E9.A1.B9">详情</a>
     */
    public enum Type {

        /**
         * 物品栈特殊属性: 攻击伤害 (def: 1, min: 0, max: 1.7x10<sup>308</sup>)
         */
        ATTACK_DAMAGE("AttackDamage", "damage", "generic.attackDamage"),
        /**
         * 物品栈特殊属性: 移动速度 (def: 0.7*, min: 0, max: 1.7x10<sup>308</sup>)
         */
        MOVE_SPEED("MoveSpeed", "movement_speed", "generic.movementSpeed"),
        /**
         * 物品栈特殊属性: 击退抗性 (def: 0, min: 0, max: 1)
         */
        KNOCKBACK_RESISTANCE("KnockbackResistance", "knockback_resistance", "generic.knockbackResistance"),
        /**
         * 物品栈特殊属性: 血量上限 (def: 20, min: 0, max: 1.7x10<sup>308</sup>)
         */
        MAX_HEALTH("MaxHealth", "max_health", "generic.maxHealth"),
        /**
         * 物品栈特殊属性: 跟踪范围 (def: 32, min: 0, max: 2048)
         */
        FOLLOW_RANGE("FollowRange", "follow_range", "generic.followRange"),

        /**
         * 物品栈特殊属性: 盔甲防御 (def: 0, min: 0, max: 30)
         */
        ARMOR_DEFENSE("ArmorDefense", "armor", "generic.armor"),

        /**
         * 物品栈特殊属性: 盔甲韧性 (def: 0, min: 0, max: 20)
         */
        ARMOR_TOUGHNESS("ArmorToughness", "armorToughness", "generic.armorToughness"),

        /**
         * 玩家特殊属性: 攻击速度 (def: 4, min: 0, max: 1024)
         */
        ATTACK_SPEED("AttackSpeed", "attackSpeed", "generic.attackSpeed"),

        /**
         * 玩家特殊属性: 幸运 (def: 0, min: -1024, max: 1024)
         */
        LUCK("Luck", "luck", "generic.luck"),
        ;

        private final String type;
        private final String name;
        private final String attributeName;
        private final static Map<String, Type> NAME_MAP;

        static {

            NAME_MAP = new HashMap<>();

            for(final Type type : values()) {

                NAME_MAP.put(type.getType(), type);
                NAME_MAP.put(type.getAttributeName(), type); // put attribute name
            }
        }

        /**
         * 属性类型构造函数
         *
         * @param type 类型名
         * @param name 名称
         * @param attributeName 属性名
         */
        Type(String type, String name, String attributeName) {

            this.type = type;
            this.name = name;
            this.attributeName = attributeName;
        }

        /**
         * 特殊属性的类型名
         *
         * @return 类型名
         */
        public String getType() {

            return type;
        }

        /**
         * 特殊属性的名字
         *
         * @return 名字
         */
        public String getName() {

            return name;
        }

        /**
         * 特殊属性的属性名
         *
         * @return 属性名
         */
        public String getAttributeName() {

            return attributeName;
        }

        public static Type fromType(String type) {

            Validate.notNull(type, "The attribute type object is null.");

            return NAME_MAP.get(type);
        }
    }

    /**
     * 属性的生效槽位: <a href="http://minecraft-zh.gamepedia.com/%E5%B1%9E%E6%80%A7#.E5.B1.9E.E6.80.A7.E5.BA.94.E7.94.A8">详情</a>
     *
     * <p style="color: red">注意: 只适应于 1.9 以上的版本</p>
     */
    public enum Slot {

        /**
         * 特殊属性槽位: 全部
         */
        ALL("All", null),
        /**
         * 特殊属性槽位: 主手
         */
        MAIN_HAND("MainHand", "mainhand"),
        /**
         * 特殊属性槽位: 副手
         */
        OFF_HAND("OffHand", "offhand"),
        /**
         * 特殊属性槽位: 头
         */
        HEAD("Head", "head"),
        /**
         * 特殊属性槽位: 胸
         */
        CHEST("Chest", "chest"),
        /**
         * 特殊属性槽位: 腿
         */
        LEGS("Legs", "legs"),
        /**
         * 特殊属性槽位: 脚
         */
        FEET("Feet", "feet"),
        ;

        private final String type;
        private final String slot;
        private final static Map<String, Slot> NAME_MAP;

        static {

            NAME_MAP = new HashMap<>();

            for(final Slot slot : values()) {

                NAME_MAP.put(slot.getType(), slot);
            }
        }

        /**
         * 属性槽位类型构造函数
         *
         * @param type 类型名
         * @param slot 槽位名
         */
        Slot(String type, String slot) {

            this.type = type;
            this.slot = slot;
        }

        /**
         * 特殊属性槽位类型名
         *
         * @return 类型名
         */
        @Deprecated
        public String getType() {

            return type;
        }

        /**
         * 特殊属性槽位名
         *
         * @return 槽位名
         */
        public String getSlot() {

            return slot;
        }

        public static Slot fromType(String type) {

            Validate.notNull(type, "The slot type object is null.");

            return NAME_MAP.get(type);
        }
    }

    /**
     * 属性值的运算模式: <a href="http://minecraft-zh.gamepedia.com/%E5%B1%9E%E6%80%A7#.E8.BF.90.E7.AE.97.E6.A8.A1.E5.BC.8F">详情</a>
     */
    public enum Operation {

        ADD_NUMBER("AddNumber", 0),
        MULTIPLY_PERCENTAGE("MultiplyPercentage", 1),
        ADD_PERCENTAGE("AddPercentage", 2)
        ;

        private final String type;
        private final int operation;
        private final static Map<Integer, Operation> VALUE_MAP;

        static {

            VALUE_MAP = new HashMap<>();

            for(final Operation operation : values()) {

                VALUE_MAP.put(operation.getOperation(), operation);
            }
        }

        /**
         * 属性运算方式构造函数
         *
         * @param type 类型名
         * @param operation 运算方式
         */
        Operation(String type, int operation) {

            this.type = type;
            this.operation = operation;
        }

        /**
         * 获取属性运算模式的类型
         *
         * @return 类型名
         */
        public String getType() {

            return type;
        }

        /**
         * 获取属性运行模式的值
         *
         * @return 值
         */
        public int getOperation() {

            return operation;
        }

        public static Operation fromValue(int value) {

            Operation operation = VALUE_MAP.get(value);

            if(operation == null) {

                operation = ADD_NUMBER;
            }
            return operation;
        }
    }
}
