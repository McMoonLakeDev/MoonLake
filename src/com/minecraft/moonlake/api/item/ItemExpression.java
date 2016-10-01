package com.minecraft.moonlake.api.item;

import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/9/12.
 */
abstract class ItemExpression implements ItemLibrary {

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
                type == Material.DIAMOND_HOE;
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
                type == Material.DIAMOND_SWORD;
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
        return
                // potion
                type == Material.POTION ||
                type == Material.SPLASH_POTION ||
                type == Material.LINGERING_POTION;
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
