package com.minecraft.moonlake.api.itemlib;

import com.minecraft.moonlake.api.lorelib.Lorelib;
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
}
