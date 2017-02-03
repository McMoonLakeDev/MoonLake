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

import com.minecraft.moonlake.api.item.firework.FireworkBuilder;
import com.minecraft.moonlake.api.item.meta.MetaLibrary;
import com.minecraft.moonlake.api.item.potion.PotionLibrary;
import com.minecraft.moonlake.api.item.potion.PotionType;
import com.minecraft.moonlake.api.item.skull.SkullLibrary;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * <h1>ItemLibraryFactory</h1>
 * 物品栈支持库工厂（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class ItemLibraryFactory {

    /**
     * 静态 ItemLibraryFactory 实例对象
     */
    private static ItemLibraryFactory instance;

    /**
     * 物品栈支持库工厂类构造函数
     */
    private ItemLibraryFactory() {

    }

    /**
     * 获取 ItemLibraryFactory 对象
     *
     * @return ItemLibraryFactory
     */
    public static ItemLibraryFactory get() {

        if(instance == null) {

            instance = new ItemLibraryFactory();
        }
        return instance;
    }

    /**
     * 获取 ItemLibrary 实例对象
     *
     * @return ItemLibrary
     */
    public ItemLibrary item() {

        return new ItemExpressionWrapped();
    }

    /**
     * 获取 CraftLibrary 实例对象
     *
     * @return CraftLibrary
     */
    public CraftLibrary craft() {

        return new CraftExpression();
    }

    /**
     * 获取 MetaLibrary 实例对象
     *
     * @return MetaLibrary
     */
    public MetaLibrary meta() {

        return new MetaExpression();
    }

    /**
     * 获取 SkullLibrary 实例对象
     *
     * @return SkullLibrary
     */
    public SkullLibrary skull() {

        return new SkullExpression();
    }

    /**
     * 获取 AttributeLibrary 实例对象
     *
     * @return AttributeLibrary
     */
    public AttributeLibrary attribute() {

        return new AttributeExpression();
    }

    /**
     * 获取 PotionLibrary 实例对象
     *
     * @return PotionLibrary
     */
    public PotionLibrary potion() {

        return new ItemExpressionWrapped();
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param itemStack 物品栈对象
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    public ItemBuilder itemBuilder(ItemStack itemStack) {

        return new ItemBuilderWrapped(itemStack);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param material 物品栈类型
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     */
    public ItemBuilder itemBuilder(Material material) {

        return new ItemBuilderWrapped(material);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     */
    public ItemBuilder itemBuilder(Material material, int data) {

        return new ItemBuilderWrapped(material, data);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     */
    public ItemBuilder itemBuilder(Material material, int data, int amount) {

        return new ItemBuilderWrapped(material, data, amount);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    public ItemBuilder itemBuilder(Material material, int data, int amount, String displayName) {

        return new ItemBuilderWrapped(material, data, amount, displayName);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param lore 物品栈标签信息
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    public ItemBuilder itemBuilder(Material material, int data, int amount, String displayName, String... lore) {

        return new ItemBuilderWrapped(material, data, amount, displayName, lore);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param potion 药水类型
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     */
    public ItemBuilder itemBuilder(PotionType potion) {

        return new ItemBuilderWrapped(potion);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param potion 药水类型
     * @param data 物品栈数据
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     */
    public ItemBuilder itemBuilder(PotionType potion, int data) {

        return new ItemBuilderWrapped(potion, data);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param potion 药水类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     */
    public ItemBuilder itemBuilder(PotionType potion, int data, int amount) {

        return new ItemBuilderWrapped(potion, data, amount);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param potion 药水类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    public ItemBuilder itemBuilder(PotionType potion, int data, int amount, String displayName) {

        return new ItemBuilderWrapped(potion, data, amount, displayName);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param potion 药水类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param lore 物品栈标签信息
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果药水类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果标签信息对象为 {@code null} 则抛出异常
     */
    public ItemBuilder itemBuilder(PotionType potion, int data, int amount, String displayName, String... lore) {

        return new ItemBuilderWrapped(potion, data, amount, displayName, lore);
    }

    /**
     * 获取 FireworkBuilder 实例对象
     *
     * @return FireworkBuilder
     */
    public FireworkBuilder fireworkBuilder() {

        return new FireworkBuilderWrapped();
    }
}
