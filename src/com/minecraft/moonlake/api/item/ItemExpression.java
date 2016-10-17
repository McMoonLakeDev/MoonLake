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

import com.minecraft.moonlake.api.item.potion.PotionType;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * <h1>ItemExpression</h1>
 * 物品栈接口抽象实现类
 *
 * @version 1.0
 * @author Month_Light
 */
abstract class ItemExpression implements ItemLibrary {

    /**
     * 物品栈接口抽象实现类构造函数
     */
    public ItemExpression() {

    }

    @Override
    public boolean isTool(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        Material type = getType(itemStack);

        if(type == null || type == Material.AIR) {

            return false;
        }
        return
                /// wood tool
                type == Material.WOOD_SWORD ||
                type == Material.WOOD_PICKAXE ||
                type == Material.WOOD_SPADE ||
                type == Material.WOOD_AXE ||
                type == Material.WOOD_HOE ||
                /// stone tool
                type == Material.STONE_SWORD ||
                type == Material.STONE_PICKAXE ||
                type == Material.STONE_SPADE ||
                type == Material.STONE_AXE ||
                type == Material.STONE_HOE ||
                /// iron tool
                type == Material.IRON_SWORD ||
                type == Material.IRON_PICKAXE ||
                type == Material.IRON_SPADE ||
                type == Material.IRON_AXE ||
                type == Material.IRON_HOE ||
                /// gold tool
                type == Material.GOLD_SWORD ||
                type == Material.GOLD_PICKAXE ||
                type == Material.GOLD_SPADE ||
                type == Material.GOLD_AXE ||
                type == Material.GOLD_HOE ||
                /// diamond tool
                type == Material.DIAMOND_SWORD ||
                type == Material.DIAMOND_PICKAXE ||
                type == Material.DIAMOND_SPADE ||
                type == Material.DIAMOND_AXE ||
                type == Material.DIAMOND_HOE ||
                // other tool
                type == Material.FLINT_AND_STEEL ||
                type == Material.FISHING_ROD ||
                type == Material.SHEARS ||
                type == Material.LEASH
        ;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        Material type = getType(itemStack);

        if(type == null || type == Material.AIR) {

            return false;
        }
        return
                // sword weapon
                type == Material.WOOD_SWORD ||
                type == Material.STONE_SWORD ||
                type == Material.IRON_SWORD ||
                type == Material.GOLD_SWORD ||
                type == Material.DIAMOND_SWORD ||
                // other weapon
                type == Material.BOW
        ;
    }

    @Override
    public boolean isArmor(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        Material type = getType(itemStack);

        if(type == null || type == Material.AIR) {

            return false;
        }
        return
                // leather armor
                type == Material.LEATHER_HELMET ||
                type == Material.LEATHER_CHESTPLATE ||
                type == Material.LEATHER_LEGGINGS ||
                type == Material.LEATHER_BOOTS ||
                // chainmail armor
                type == Material.CHAINMAIL_HELMET ||
                type == Material.CHAINMAIL_CHESTPLATE ||
                type == Material.CHAINMAIL_LEGGINGS ||
                type == Material.CHAINMAIL_BOOTS ||
                // iron armor
                type == Material.IRON_HELMET ||
                type == Material.IRON_CHESTPLATE ||
                type == Material.IRON_LEGGINGS ||
                type == Material.IRON_BOOTS ||
                // diamond armor
                type == Material.DIAMOND_HELMET ||
                type == Material.DIAMOND_CHESTPLATE ||
                type == Material.DIAMOND_LEGGINGS ||
                type == Material.DIAMOND_BOOTS ||
                // gold armor
                type == Material.GOLD_HELMET ||
                type == Material.GOLD_CHESTPLATE ||
                type == Material.GOLD_LEGGINGS ||
                type == Material.GOLD_BOOTS;
    }

    @Override
    public boolean isLeatherArmor(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        Material type = getType(itemStack);

        if(type == null || type == Material.AIR) {

            return false;
        }
        return
                // leather armor
                type == Material.LEATHER_HELMET ||
                type == Material.LEATHER_CHESTPLATE ||
                type == Material.LEATHER_LEGGINGS ||
                type == Material.LEATHER_BOOTS;
    }

    @Override
    public boolean isPotion(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        Material type = getType(itemStack);

        if(type == null || type == Material.AIR) {

            return false;
        }
        return PotionType.fromItemStack(itemStack) != null;
    }

    @Override
    public boolean isWrittenBook(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        Material type = getType(itemStack);

        if(type == null || type == Material.AIR) {

            return false;
        }
        return type == Material.WRITTEN_BOOK;
    }

    /**
     * 获取物品栈的物品类型
     *
     * @param itemStack 物品栈
     * @return 物品栈类型
     */
    protected final Material getType(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        return itemStack.getType();
    }
}
