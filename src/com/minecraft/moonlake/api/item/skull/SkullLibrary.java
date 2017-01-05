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
 
 
package com.minecraft.moonlake.api.item.skull;

import com.minecraft.moonlake.exception.MoonLakeException;
import org.bukkit.inventory.ItemStack;

/**
 * <h1>ItemStack SkullLibrary</h1>
 * 头颅物品栈支持库（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
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
     * @param data 头颅材质信息
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅材质信息对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果设置头颅材质时错误则抛出异常
     */
    ItemStack createSkullWithSkin(String data) throws MoonLakeException;

    /**
     * 创建头颅物品栈 ItemStack 对象
     *
     * @param value 头颅材质值
     * @param signature 头颅材质签名
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅材质值对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果设置头颅材质时错误则抛出异常
     */
    ItemStack createSkullWithSkins(String value, String signature) throws MoonLakeException;

    /**
     * 创建头颅物品栈 ItemStack 对象
     *
     * @param data 头颅材质信息
     * @param displayName 头颅显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅显示名称对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果头颅材质信息对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果设置头颅材质时错误则抛出异常
     */
    ItemStack createSkullWithSkin(String data, String displayName) throws MoonLakeException;

    /**
     * 创建头颅物品栈 ItemStack 对象
     *
     * @param value 头颅材质值
     * @param signature 头颅材质签名
     * @param displayName 头颅显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅材质值对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果设置头颅材质时错误则抛出异常
     */
    ItemStack createSkullWithSkins(String value, String signature, String displayName) throws MoonLakeException;

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
     * 设置头颅物品栈的皮肤材质数据
     *
     * @param data 头颅材质信息
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅材质信息对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.SKULL_ITEM} 则抛出异常
     * @throws MoonLakeException 如果设置头颅材质时错误则抛出异常
     */
    ItemStack setSkullWithSkin(ItemStack itemStack, String data) throws MoonLakeException;

    /**
     * 设置头颅物品栈的皮肤材质数据
     *
     * @param value 头颅材质值
     * @param signature 头颅材质签名
     * @return ItemStack
     * @throws IllegalArgumentException 如果头颅材质值对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型不为 {@code Material.SKULL_ITEM} 则抛出异常
     * @throws MoonLakeException 如果设置头颅材质时错误则抛出异常
     */
    ItemStack setSkullWithSkin(ItemStack itemStack, String value, String signature) throws MoonLakeException;
}
