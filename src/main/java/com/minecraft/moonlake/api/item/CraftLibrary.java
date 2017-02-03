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

import com.minecraft.moonlake.api.item.potion.PotionLibrary;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * <h1>ItemStack CraftLibrary</h1>
 * 物品栈创建支持库（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface CraftLibrary extends PotionLibrary {

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param id 物品栈 ID
     * @return ItemStack 如果不存在 ID 则返回 {@code null}
     * @deprecated 不推荐使用 ID 来创建
     * @see CraftLibrary#create(Material)
     */
    @Deprecated
    ItemStack create(int id);

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param id 物品栈 ID
     * @param data 物品栈数据
     * @return ItemStack 如果不存在 ID 则返回 {@code null}
     * @deprecated 不推荐使用 ID 来创建
     * @see CraftLibrary#create(Material)
     */
    @Deprecated
    ItemStack create(int id, int data);

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param id 物品栈 ID
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @return ItemStack 如果不存在 ID 则返回 {@code null}
     * @deprecated 不推荐使用 ID 来创建
     * @see CraftLibrary#create(Material)
     */
    @Deprecated
    ItemStack create(int id, int data, int amount);

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param id 物品栈 ID
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemStack 如果不存在 ID 则返回 {@code null}
     * @deprecated 不推荐使用 ID 来创建
     * @see CraftLibrary#create(Material)
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    @Deprecated
    ItemStack create(int id, int data, int amount, String displayName);

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param id 物品栈 ID
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param lore 物品栈标签
     * @return ItemStack 如果不存在 ID 则返回 {@code null}
     * @deprecated 不推荐使用 ID 来创建
     * @see CraftLibrary#create(Material)
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    @Deprecated
    ItemStack create(int id, int data, int amount, String displayName, String... lore);

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param type 物品栈类型名
     * @return ItemStack 如果不存在类型名则返回 {@code null}
     * @deprecated 不推荐使用类型名来创建
     * @see CraftLibrary#create(Material)
     * @throws IllegalArgumentException 如果物品栈类型名对象为 {@code null} 则抛出异常
     */
    @Deprecated
    ItemStack create(String type);

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param type 物品栈类型名
     * @param data 物品栈数据
     * @return ItemStack 如果不存在类型名则返回 {@code null}
     * @deprecated 不推荐使用类型名来创建
     * @see CraftLibrary#create(Material)
     * @throws IllegalArgumentException 如果物品栈类型名对象为 {@code null} 则抛出异常
     */
    @Deprecated
    ItemStack create(String type, int data);

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param type 物品栈类型名
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @return ItemStack 如果不存在类型名则返回 {@code null}
     * @deprecated 不推荐使用类型名来创建
     * @see CraftLibrary#create(Material)
     * @throws IllegalArgumentException 如果物品栈类型名对象为 {@code null} 则抛出异常
     */
    @Deprecated
    ItemStack create(String type, int data, int amount);

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param type 物品栈类型名
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemStack 如果不存在类型名则返回 {@code null}
     * @deprecated 不推荐使用类型名来创建
     * @see CraftLibrary#create(Material)
     * @throws IllegalArgumentException 如果物品栈类型名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    @Deprecated
    ItemStack create(String type, int data, int amount, String displayName);

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param type 物品栈类型名
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param lore 物品栈标签
     * @return ItemStack 如果不存在类型名则返回 {@code null}
     * @deprecated 不推荐使用类型名来创建
     * @see CraftLibrary#create(Material)
     * @throws IllegalArgumentException 如果物品栈类型名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    @Deprecated
    ItemStack create(String type, int data, int amount, String displayName, String... lore);

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param material 物品栈类型
     * @return ItemStack
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     */
    ItemStack create(Material material);

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @return ItemStack
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     */
    ItemStack create(Material material, int data);

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @return ItemStack
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     */
    ItemStack create(Material material, int data, int amount);

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @return ItemStack
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack create(Material material, int data, int amount, String displayName);

    /**
     * 创建物品栈 ItemStack 对象
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param lore 物品栈标签
     * @return ItemStack
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果显示名称对象为 {@code null} 则抛出异常
     */
    ItemStack create(Material material, int data, int amount, String displayName, String... lore);
}
