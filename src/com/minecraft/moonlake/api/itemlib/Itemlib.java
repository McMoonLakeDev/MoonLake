package com.minecraft.moonlake.api.itemlib;

import com.minecraft.moonlake.api.lorelib.Lorelib;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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
}
