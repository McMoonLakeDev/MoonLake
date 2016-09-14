package com.minecraft.moonlake._temp.itemlib;

import com.minecraft.moonlake.MoonLakePlugin;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoonLake on 2016/6/23.
 */
public class ItemBuilder {

    private final Itemlib itemlib;

    private ItemStack item;
    private int data;

    public ItemBuilder(int id) {

        this(Material.getMaterial(id), 0);
    }

    public ItemBuilder(int id, int data) {

        this(Material.getMaterial(id), data);
    }

    public ItemBuilder(int id, int data, String displayName) {

        this(Material.getMaterial(id), data, displayName);
    }

    public ItemBuilder(Material type) {

        this(type, 0);
    }

    public ItemBuilder(Material type, int data) {

        this.data = data;
        this.item = new ItemStack(type, 1, (short)data);
        this.itemlib = MoonLakePlugin.getInstances().getItemlib();
    }

    public ItemBuilder(Material type, int data, String displayName) {

        this.data = data;
        this.itemlib = MoonLakePlugin.getInstances().getItemlib();
        this.item = itemlib.create(type, data, 1, displayName);
    }

    /**
     * 设置此物品栈的数量
     *
     * @param amount 数量
     * @return 实例
     */
    public ItemBuilder setAmount(int amount) {

        this.item.setAmount(amount);

        return this;
    }

    /**
     * 将此物品栈添加附魔效果
     *
     * @param enchantment 附魔类型
     * @param level 等级
     * @return 实例
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int level) {

        this.item.addUnsafeEnchantment(enchantment, level);

        return this;
    }

    /**
     * 将此物品栈删除附魔效果
     *
     * @param enchantment 附魔类型
     * @return 实例
     */
    public ItemBuilder removeEnchant(Enchantment enchantment) {

        this.item.removeEnchantment(enchantment);

        return this;
    }

    /**
     * 将此物品栈添加标示
     *
     * @param flags 标示
     * @return 实例
     */
    public ItemBuilder addFlag(ItemFlag... flags) {

        this.item = itemlib.addFlags(item, flags);

        return this;
    }

    /**
     * 将此物品栈删除标示
     *
     * @param flags 标示
     * @return 实例
     */
    public ItemBuilder removeFlag(ItemFlag... flags) {

        this.item = itemlib.removeFlags(item, flags);

        return this;
    }

    /**
     * 将此物品栈添加标签
     *
     * @param lore 标签
     * @return 实例
     */
    public ItemBuilder addLore(String lore) {

        this.item = itemlib.addLore(item, lore);

        return this;
    }

    /**
     * 将此物品栈添加标签
     *
     * @param lores 标签
     * @return 实例
     */
    public ItemBuilder addLores(String... lores) {

        this.item = itemlib.addLore(item, lores);

        return this;
    }

    /**
     * 将此物品栈添加标签
     *
     * @param lores 标签
     * @return 实例
     */
    public ItemBuilder addLores(List<String> lores) {

        this.item = itemlib.addLore(item, lores);

        return this;
    }

    /**
     * 将此物品栈清除标签
     *
     * @return 实例
     */
    public ItemBuilder removeLore() {

        this.item = itemlib.setLore(item, new ArrayList<String>());

        return this;
    }

    /**
     * 设置此物品栈的攻击伤害值
     *
     * @param damage 值
     * @param percent 是否百分比
     * @param slot 生效的槽位
     * @return 实例
     */
    public ItemBuilder setAttackDamage(double damage, boolean percent, Itemlib.AttributeType.Slot slot) {

        this.item = itemlib.setItemAttackDamage(item, damage, percent, slot);

        return this;
    }

    /**
     * 设置此物品栈的移动速度值
     *
     * @param moveSpeed 值
     * @param percent 是否百分比
     * @param slot 生效的槽位
     * @return 实例
     */
    public ItemBuilder setMoveSpeed(double moveSpeed, boolean percent, Itemlib.AttributeType.Slot slot) {

        this.item = itemlib.setItemMoveSpeed(item, moveSpeed, percent, slot);

        return this;
    }

    /**
     * 设置此物品栈的防御值
     *
     * @param defense 值
     * @param percent 是否百分比
     * @param slot 生效的槽位
     * @return 实例
     */
    public ItemBuilder setArmorDefense(double defense, boolean percent, Itemlib.AttributeType.Slot slot) {

        this.item = itemlib.setItemArmorDefense(item, defense, percent, slot);

        return this;
    }

    /**
     * 设置此物品栈的防御韧性值
     *
     * @param toughness 值
     * @param percent 是否百分比
     * @param slot 生效的槽位
     * @return 实例
     */
    public ItemBuilder setArmorToughness(double toughness, boolean percent, Itemlib.AttributeType.Slot slot) {

        this.item = itemlib.setItemArmorToughness(item, toughness, percent, slot);

        return this;
    }

    /**
     * 设置此物品栈的攻击速度值
     *
     * @param attackSpeed 值
     * @param percent 是否百分比
     * @param slot 生效的槽位
     * @return 实例
     */
    public ItemBuilder setAttackSpeed(double attackSpeed, boolean percent, Itemlib.AttributeType.Slot slot) {

        this.item = itemlib.setItemAttackSpeed(item, attackSpeed, percent, slot);

        return this;
    }

    /**
     * 设置此物品栈是否无法破坏
     *
     * @param unbreakable 是否无法破坏
     * @return 实例
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {

        this.item = itemlib.setUnbreakable(item, unbreakable);

        return this;
    }

    /**
     * 设置此皮革护甲物品栈的颜色
     *
     * @param color 颜色
     * @return 实例
     */
    public ItemBuilder setLeatherColor(Color color) {

        this.item = itemlib.setLeatherArmorColor(item, color);

        return this;
    }

    /**
     * 构建此物品栈对象
     *
     * @return 物品栈
     */
    public ItemStack build() {

        return this.item.clone();
    }
}
