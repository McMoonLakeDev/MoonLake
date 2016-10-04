package com.minecraft.moonlake.api.item.skull;

import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.exception.NotImplementedException;
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
     * @param displayName 头颅显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createSkull(String displayName);

    /**
     * 创建头颅物品栈 ItemStack 对象
     *
     * @param skullOwner 头颅拥有者
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅拥有者对象为 {@code null} 则抛出异常
     */
    ItemStack createSkullWithOwner(String skullOwner);

    /**
     * 创建头颅物品栈 ItemStack 对象
     *
     * @param skullOwner 头颅拥有者
     * @param displayName 头颅显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅拥有者对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果头颅显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack createSkullWithOwner(String skullOwner, String displayName);

    /**
     * 创建头颅物品栈 ItemStack 对象
     *
     * @param skinURL 头颅材质信息 URL
     * @param displayName 头颅显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅显示名称对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果头颅材质信息对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果设置头颅材质时错误则抛出异常
     */
    ItemStack createSkullWithSkin(String skinURL, String displayName) throws MoonLakeException;

    /**
     * 创建头颅物品栈 ItemStack 对象
     *
     * @param skinURL 头颅材质信息 URL
     * @param displayName 头颅显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅显示名称对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果头颅材质信息对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果设置头颅材质时错误则抛出异常
     */
    ItemStack createSkullWithSkin(URL skinURL, String displayName) throws MoonLakeException;

    /**
     * 获取头颅物品栈 ItemStack 对象的拥有者
     *
     * @param itemStack 头颅物品栈
     * @return 头颅拥有者 没有则返回 null
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.SKULL_ITEM} 则抛出异常
     */
    String getSkullOwner(ItemStack itemStack);

    /**
     * 获取指定头颅物品栈的皮肤材质 URL
     *
     * @param itemStack 头颅物品栈
     * @return 材质 URL
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.SKULL_ITEM} 则抛出异常
     * @throws NotImplementedException 尚未实现
     */
    String getSkullSkinURL(ItemStack itemStack);
}
