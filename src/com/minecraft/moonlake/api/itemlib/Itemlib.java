package com.minecraft.moonlake.api.itemlib;

import com.minecraft.moonlake.api.lorelib.Lorelib;
import com.minecraft.moonlake.util.Util;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by MoonLake on 2016/4/26.
 * @version 1.0
 * @author Month_Light
 */
public interface Itemlib extends Lorelib {

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
    ItemStack enchantment(ItemStack item, Enchantment ench, int lvl);

    /**
     * 给物品栈添加的附魔
     *
     * @param item 物品栈
     * @param ench 附魔和等级Map
     * @return 附魔后的 ItemStack
     */
    ItemStack enchantment(ItemStack item, Map<Enchantment, Integer> ench);

    /**
     * 给物品栈添加的附魔
     *
     * @param item 物品栈
     * @param id 附魔ID
     * @param lvl 附魔等级
     * @return 附魔后的 ItemStack
     */
    ItemStack enchantment(ItemStack item, int id, int lvl);

    /**
     * 给物品栈添加的附魔
     *
     * @param item 物品栈
     * @param id 附魔类型
     * @param lvl 附魔等级
     * @return 附魔后的 ItemStack
     */
    ItemStack enchantment(ItemStack item, String id, int lvl);

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
     * 给物品栈设置特殊属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param type 属性类型
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @return 设置特殊属性后的 ItemStack
     */
    @Deprecated
    ItemStack setAttribute(AttributeType type, double count, boolean isPercent);

    /**
     * 给物品栈添加特殊属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param typeDoubleMap 属性类型和数量Map
     * @param isPercent 是否百分比数组
     * @return 设置特殊属性后的 ItemStack
     */
    @Deprecated
    ItemStack addAttribute(Map<AttributeType, Double> typeDoubleMap, boolean... isPercent);

    /**
     * 物品栈特殊属性类型枚举
     */
    enum AttributeType {

        /**
         * 物品栈特殊属性: 攻击伤害
         */
        ATTACK_DAMAGE("AttackDamage", "generic.attackDamage"),
        /**
         * 物品栈特殊属性: 移动速度
         */
        MOVE_SPEED("MoveSpeed", "generic.movementSpeed"),
        /**
         * 物品栈特殊属性: 击退抗性
         */
        KNOCKBACK_RESISTANCE("KnockbackResistance", "generic.knockbackResistance"),
        /**
         * 物品栈特殊属性: 血量上限
         */
        MAX_HEALTH("MaxHealth", "generic.maxHealth"),
        /**
         * 物品栈特殊属性: 跟踪范围
         */
        FOLLOW_RANGE("FollowRange", "generic.followRange"),
        ;

        private String type;
        private String attributeName;

        AttributeType(String type, String attributeName) {
            this.type = type;
            this.attributeName = attributeName;
        }

        public String getType() {
            return type;
        }

        public String getAttributeName() {
            return attributeName;
        }

        @Override
        public String toString() {
            StringBuilder toString = new StringBuilder("AttributeType{").append(type + "," + attributeName);
            return toString.append("}").toString();
        }

        /**
         * 将字串符序列化为物品栈特殊属性对象
         *
         * @param type 物品栈特殊属性类型
         * @return AttributeType 如果不存在类型则返回 null
         */
        public static AttributeType fromType(String type) {
            Util.notNull(type, "待转换的特殊属性类型是 null 值");

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
                default:
                    return null;
            }
        }
    }
}
