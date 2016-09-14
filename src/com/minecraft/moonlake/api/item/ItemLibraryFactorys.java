package com.minecraft.moonlake.api.item;

import com.minecraft.moonlake.api.item.meta.MetaLibrary;
import com.minecraft.moonlake.api.item.skull.SkullLibrary;
import com.minecraft.moonlake.builder.ItemBuilderWrapped;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/9/13.
 */
public class ItemLibraryFactorys {

    /**
     * 静态 ItemLibrary 实例对象
     */
    private static ItemLibrary itemLibraryInstance;

    private ItemLibraryFactorys() {

    }

    /**
     * 获取 ItemLibrary 对象
     *
     * @return ItemLibrary
     */
    public static ItemLibrary item() {

        if(itemLibraryInstance == null) {

            itemLibraryInstance = ItemLibraryFactory.get().item();
        }
        return itemLibraryInstance;
    }

    /**
     * 获取 CraftLibrary 对象
     *
     * @return CraftLibrary
     */
    public static CraftLibrary craft() {

        return item();
    }

    /**
     * 获取 MetaLibrary 对象
     *
     * @return MetaLibrary
     */
    public static MetaLibrary meta() {

        return item();
    }

    /**
     * 获取 SkullLibrary 对象
     *
     * @return SkullLibrary
     */
    public static SkullLibrary skull() {

        return item();
    }

    /**
     * 获取 AttributeLibrary 对象
     *
     * @return AttributeLibrary
     */
    public static AttributeLibrary attribute() {

        return item();
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param itemStack 物品栈对象
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    public static ItemBuilder itemBuilder(ItemStack itemStack) {

        return new ItemBuilderWrapped(itemStack);
    }

    /**
     * 获取 ItemBuilder 实例对象
     *
     * @param material 物品栈类型
     * @return ItemBuilder
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     */
    public static ItemBuilder itemBuilder(Material material) {

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
    public static ItemBuilder itemBuilder(Material material, int data) {

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
    public static ItemBuilder itemBuilder(Material material, int data, int amount) {

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
    public static ItemBuilder itemBuilder(Material material, int data, int amount, String displayName) {

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
    public static ItemBuilder itemBuilder(Material material, int data, int amount, String displayName, String... lore) {

        return new ItemBuilderWrapped(material, data, amount, displayName, lore);
    }
}
