package com.minecraft.moonlake.api.itemlib;

import com.minecraft.moonlake.api.lorelib.Lorelib;
import com.minecraft.moonlake.api.potionlib.Potionlib;
import com.minecraft.moonlake.exception.NotArmorItemException;
import com.minecraft.moonlake.util.Util;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <h1>提供物品栈的API函数 (创建、添加效果等等)</h1>
 * @version 1.0
 * @author Month_Light
 */
public interface Itemlib extends Lorelib, Potionlib {

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @return ItemStack
     */
    ItemStack create(int id);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @return ItemStack
     */
    ItemStack create(int id, int data);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @param amount 物品数量
     * @return ItemStack
     */
    ItemStack create(int id, int data, int amount);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @param amount 物品数量
     * @param name 物品名称
     * @return ItemStack
     */
    ItemStack create(int id, int data, int amount, String name);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @param amount 物品数量
     * @param name 物品名称
     * @param lore 物品标签
     * @return ItemStack
     */
    ItemStack create(int id, int data, int amount, String name, String... lore);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @param amount 物品数量
     * @param name 物品名称
     * @param lore 物品标签
     * @return ItemStack
     */
    ItemStack create(int id, int data, int amount, String name, List<String> lore);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @return ItemStack
     */
    ItemStack create(String id);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @return ItemStack
     */
    ItemStack create(String id, int data);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @param amount 物品数量
     * @return ItemStack
     */
    ItemStack create(String id, int data, int amount);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @param amount 物品数量
     * @param name 物品名称
     * @return ItemStack
     */
    ItemStack create(String id, int data, int amount, String name);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @param amount 物品数量
     * @param name 物品名称
     * @param lore 物品标签
     * @return ItemStack
     */
    ItemStack create(String id, int data, int amount, String name, String... lore);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @param amount 物品数量
     * @param name 物品名称
     * @param lore 物品标签
     * @return ItemStack
     */
    ItemStack create(String id, int data, int amount, String name, List<String> lore);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @return ItemStack
     */
    ItemStack create(Material id);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @return ItemStack
     */
    ItemStack create(Material id, int data);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @param amount 物品数量
     * @return ItemStack
     */
    ItemStack create(Material id, int data, int amount);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @param amount 物品数量
     * @param name 物品名称
     * @return ItemStack
     */
    ItemStack create(Material id, int data, int amount, String name);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @param amount 物品数量
     * @param name 物品名称
     * @param lore 物品标签
     * @return ItemStack
     */
    ItemStack create(Material id, int data, int amount, String name, String... lore);

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @param data 物品副ID
     * @param amount 物品数量
     * @param name 物品名称
     * @param lore 物品标签
     * @return ItemStack
     */
    ItemStack create(Material id, int data, int amount, String name, List<String> lore);

    /**
     * 给物品栈添加的附魔
     *
     * @param item 物品栈
     * @param ench 附魔
     * @param lvl 附魔等级
     * @return 附魔后的 ItemStack
     */
    ItemStack addEnchantment(ItemStack item, Enchantment ench, int lvl);

    /**
     * 给物品栈添加的附魔
     *
     * @param item 物品栈
     * @param ench 附魔和等级Map
     * @return 附魔后的 ItemStack
     */
    ItemStack addEnchantment(ItemStack item, Map<Enchantment, Integer> ench);

    /**
     * 给物品栈添加的附魔
     *
     * @param item 物品栈
     * @param id 附魔ID
     * @param lvl 附魔等级
     * @return 附魔后的 ItemStack
     */
    ItemStack addEnchantment(ItemStack item, int id, int lvl);

    /**
     * 给物品栈添加的附魔
     *
     * @param item 物品栈
     * @param id 附魔类型
     * @param lvl 附魔等级
     * @return 附魔后的 ItemStack
     */
    ItemStack addEnchantment(ItemStack item, String id, int lvl);

    /**
     * 给物品栈删除指定的附魔
     *
     * @param item 物品栈
     * @param ench 附魔
     * @return 删除附魔后的 ItemStack
     */
    ItemStack removeEnchantment(ItemStack item, Enchantment ench);

    /**
     * 给物品栈删除指定的附魔
     *
     * @param item 物品栈
     * @param id 附魔ID
     * @return 删除附魔后的 ItemStack
     */
    ItemStack removeEnchantment(ItemStack item, int id);

    /**
     * 给物品栈删除指定的附魔
     *
     * @param item 物品栈
     * @param id 附魔类型
     * @return 删除附魔后的 ItemStack
     */
    ItemStack removeEnchantment(ItemStack item, String id);

    /**
     * 获取物品栈的附魔
     *
     * @param item 物品栈
     * @return 附魔Map
     */
    Map<Enchantment, Integer> getEnchantments(ItemStack item);

    /**
     * 获取物品栈的标示
     *
     * @param item 物品栈
     * @return 标示数组
     */
    Set<ItemFlag> getFlags(ItemStack item);

    /**
     * 给物品栈添加标示
     *
     * @param item 物品栈
     * @param flags 标示
     * @return 添加标示后的 ItemStack
     */
    ItemStack addFlags(ItemStack item, ItemFlag... flags);

    /**
     * 给物品栈清除标示
     *
     * @param item 物品栈
     * @param flags 标示
     * @return 清除标示后的 ItemStack
     */
    ItemStack removeFlags(ItemStack item, ItemFlag... flags);

    /**
     * 获取物品栈是否拥有标示
     *
     * @param item 物品栈
     * @return 物品栈
     */
    boolean hasFlag(ItemStack item);

    /**
     * 获取物品栈是否拥有标示
     *
     * @param item 物品栈
     * @param flag 标示
     * @return 物品栈
     */
    boolean hasFlag(ItemStack item, ItemFlag flag);

    /**
     * 设置物品栈是否无法破坏
     *
     * @param item 物品栈
     * @param unbreakable 状态
     * @return 设置后的 ItemStack
     */
    ItemStack setUnbreakable(ItemStack item, boolean unbreakable);

    /**
     * 设置物品栈是否无法破坏 (NMS映射设置不推荐使用)
     *
     * @param item 物品栈
     * @param unbreakable 状态
     * @return 设置后的 ItemStack 异常返回 null
     */
    @Deprecated
    ItemStack setUnbreakableFromNMS(ItemStack item, boolean unbreakable);

    /**
     * 给物品栈添加特殊属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param type 属性类型
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置特殊属性后的 ItemStack 异常返回 null
     */
    ItemStack addAttribute(ItemStack item, AttributeType type, double count, boolean isPercent, AttributeType.Slot slot);

    /**
     * 给物品栈添加特殊属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param typeDoubleMap 属性类型和数量Map
     * @param isPercent 是否百分比数组
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置特殊属性后的 ItemStack 异常返回 null
     */
    ItemStack addAttribute(ItemStack item, Map<AttributeType, Double> typeDoubleMap, boolean[] isPercent, AttributeType.Slot slot);

    /**
     * 设置物品栈的攻击伤害属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置攻击伤害属性后的 ItemStack
     */
    ItemStack setItemAttackDamage(ItemStack item, double count, boolean isPercent, AttributeType.Slot slot);

    /**
     * 设置物品栈的血量上限属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置血量上限属性后的 ItemStack
     */
    ItemStack setItemMaxHealth(ItemStack item, double count, boolean isPercent, AttributeType.Slot slot);

    /**
     * 设置物品栈的移动速度属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置移动速度属性后的 ItemStack
     */
    ItemStack setItemMoveSpeed(ItemStack item, double count, boolean isPercent, AttributeType.Slot slot);

    /**
     * 设置物品栈的击退抗性属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置击退抗性属性后的 ItemStack
     */
    ItemStack setItemKnockbackResistance(ItemStack item, double count, boolean isPercent, AttributeType.Slot slot);

    /**
     * 设置物品栈的跟踪范围属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置跟踪范围属性后的 ItemStack
     */
    ItemStack setItemFollowRange(ItemStack item, double count, boolean isPercent, AttributeType.Slot slot);

    /**
     * 设置物品栈的幸运属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置幸运属性后的 ItemStack
     */
    ItemStack setItemLuck(ItemStack item, double count, boolean isPercent, AttributeType.Slot slot);

    /**
     * 设置护甲物品栈的护甲防御属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param armor 护甲物品栈
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置护甲防御属性后的 ItemStack
     * @throws NotArmorItemException 如果物品栈不是护甲类型则抛出异常
     */
    ItemStack setItemArmorDefense(ItemStack armor, double count, boolean isPercent, AttributeType.Slot slot);

    /**
     * 设置护甲物品栈的护甲韧性属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param armor 护甲物品栈
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置护甲防御属性后的 ItemStack
     * @throws NotArmorItemException 如果物品栈不是护甲类型则抛出异常
     */
    ItemStack setItemArmorToughness(ItemStack armor, double count, boolean isPercent, AttributeType.Slot slot);

    /**
     * 设置物品栈的攻击速度属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置攻击速度属性后的 ItemStack
     */
    ItemStack setItemAttackSpeed(ItemStack item, double count, boolean isPercent, AttributeType.Slot slot);

    /**
     * 判断物品栈是否是护甲物品栈
     *
     * @param item 物品栈
     * @return 是否是护甲物品栈
     */
    boolean isArmor(ItemStack item);

    /**
     * 判断物品栈类型是否是护甲物品栈类型
     *
     * @param type 物品栈类型
     * @return 是否是护甲物品栈类型
     */
    boolean isArmor(Material type);

    /**
     * 判断物品栈类型是否是皮革护甲物品栈类型
     *
     * @param item 物品栈类型
     * @return 是否是皮革护甲物品栈类型
     */
    boolean isLeatherArmor(ItemStack item);

    /**
     * 判断物品栈类型是否是皮革护甲物品栈类型
     *
     * @param type 物品栈类型
     * @return 是否是皮革护甲物品栈类型
     */
    boolean isLeatherArmor(Material type);

    /**
     * 判断物品栈是否是药水物品栈
     *
     * @param item 物品栈
     * @return 是否是药水物品栈
     */
    boolean isPotion(ItemStack item);

    /**
     * 判断物品栈类型是否是药水物品栈类型
     *
     * @param type 物品栈类型
     * @return 是否是药水物品栈类型
     */
    boolean isPotion(Material type);

    /**
     * <h>物品栈特殊属性类型枚举: <a href="http://minecraft-zh.gamepedia.com/%E5%B1%9E%E6%80%A7#.E5.B1.9E.E6.80.A7">属性详情</a></h>
     */
    enum AttributeType {

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

        private String type;
        private String name;
        private String attributeName;

        AttributeType(String type, String name, String attributeName) {
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

        /**
         * 将字串符序列化为物品栈特殊属性对象
         *
         * @param type 物品栈特殊属性类型
         * @return AttributeType 如果不存在类型则返回 null
         */
        public static AttributeType fromType(String type) {
            Util.notEmpty(type, "待转换的特殊属性类型是 null 值");

            switch (type.toLowerCase()) {
                case "attackdamage":
                case "attack_damage":
                    return ATTACK_DAMAGE;
                case "movespeed":
                case "move_speed":
                    return MOVE_SPEED;
                case "knockbackresistance":
                case "knockback_resistance":
                    return KNOCKBACK_RESISTANCE;
                case "maxhealth":
                case "max_health":
                    return MAX_HEALTH;
                case "followrange":
                case "follow_range":
                    return FOLLOW_RANGE;
                case "armor":
                case "armordefense":
                case "armor_defense":
                    return ARMOR_DEFENSE;
                case "armortoughness":
                case "armor_toughness":
                    return ARMOR_TOUGHNESS;
                case "attackspeed":
                case "attack_speed":
                    return ATTACK_SPEED;
                case "luck":
                    return LUCK;
                default:
                    return null;
            }
        }

        /**
         * <h1>特殊属性生效的槽位: <a href="http://minecraft-zh.gamepedia.com/%E5%B1%9E%E6%80%A7#.E5.B1.9E.E6.80.A7.E5.BA.94.E7.94.A8">槽位详情</a></h1>
         */
        public enum Slot {

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

            private String type;
            private String slot;

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

            /**
             * 将字串符序列化为物品栈特殊属性槽位对象
             *
             * @param type 物品栈特殊属性槽位类型
             * @return Slot 如果不存在类型则返回 null
             */
            public static Slot fromType(String type) {
                Util.notEmpty(type, "待转换的特殊属性类型槽位是 null 值");

                switch (type.toLowerCase()) {
                    case "mainhand":
                    case "main_hand":
                        return MAIN_HAND;
                    case "offhand":
                    case "off_hand":
                        return OFF_HAND;
                    case "head":
                        return HEAD;
                    case "chest":
                        return CHEST;
                    case "legs":
                        return LEGS;
                    case "feet":
                        return FEET;
                    default:
                        return null;
                }
            }
        }
    }
}
