package com.minecraft.moonlake.api.item.skull;

import com.minecraft.moonlake.property.ReadOnlyStringProperty;
import org.bukkit.inventory.ItemStack;

import java.net.URL;

/**
 * Created by MoonLake on 2016/9/12.
 */
public interface SkullLibrary {

    /**
     * 创建头颅物品栈 ItemStack 对象
     *
     * @return ItemStack
     */
    ItemStack createSkull();

    /**
     * 创建头颅物品栈 ItemStack 对象
     *
     * @param skullOwner 头颅拥有者
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅拥有者对象为 {@code null} 则抛出异常
     */
    ItemStack createSkull(String skullOwner);

    /**
     * 创建头颅物品栈 ItemStack 对象
     *
     * @param skullOwner 头颅拥有者
     * @param displayName 头颅显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅拥有者对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果头颅显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createSkull(String skullOwner, String displayName);

    /**
     * 创建头颅物品栈 ItemStack 对象
     *
     * @param skullOwner 头颅拥有者
     * @param displayName 头颅显示名称
     * @param prefile 头颅材质信息 URL
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅拥有者对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果头颅显示名称对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果头颅材质信息对象为 {@code null} 则抛出异常
     */
    ItemStack createSkull(String skullOwner, String displayName, String prefile);

    /**
     * 创建头颅物品栈 ItemStack 对象
     *
     * @param skullOwner 头颅拥有者
     * @param displayName 头颅显示名称
     * @param prefile 头颅材质信息 URL
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅拥有者对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果头颅显示名称对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果头颅材质信息对象为 {@code null} 则抛出异常
     */
    ItemStack createSkull(String skullOwner, String displayName, URL prefile);

    /**
     * 获取头颅物品栈 ItemStack 对象的拥有者
     *
     * @param itemStack 头颅物品栈
     * @return 头颅拥有者 没有则返回 null
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ReadOnlyStringProperty getSkullOwner(ItemStack itemStack);
}
