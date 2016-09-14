package com.minecraft.moonlake.item;

import com.minecraft.moonlake.api.item.CraftLibrary;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/9/12.
 */
public class CraftExpression extends MetaExpression implements CraftLibrary {

    public CraftExpression() {

    }

    @Override
    public ItemStack create(int id) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material);
    }

    @Override
    public ItemStack create(int id, int data) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material, data);
    }

    @Override
    public ItemStack create(int id, int data, int amount) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material, data, amount);
    }

    @Override
    public ItemStack create(int id, int data, int amount, String displayName) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material, data, amount, displayName);
    }

    @Override
    public ItemStack create(int id, int data, int amount, String displayName, String... lore) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material, data, amount, displayName, lore);
    }

    @Override
    public ItemStack create(String type) {

        Validate.notNull(type, "The itemstack type object is null.");
        
        Material material = Material.getMaterial(type);
        return material == null ? null : create(material);
    }

    @Override
    public ItemStack create(String type, int data) {

        Validate.notNull(type, "The itemstack type object is null.");

        Material material = Material.getMaterial(type);
        return material == null ? null : create(material, data);
    }

    @Override
    public ItemStack create(String type, int data, int amount) {

        Validate.notNull(type, "The itemstack type object is null.");

        Material material = Material.getMaterial(type);
        return material == null ? null : create(material, data, amount);
    }

    @Override
    public ItemStack create(String type, int data, int amount, String displayName) {

        Validate.notNull(type, "The itemstack type object is null.");

        Material material = Material.getMaterial(type);
        return material == null ? null : create(material, data, amount, displayName);
    }

    @Override
    public ItemStack create(String type, int data, int amount, String displayName, String... lore) {

        Validate.notNull(type, "The itemstack type object is null.");

        Material material = Material.getMaterial(type);
        return material == null ? null : create(material, data, amount, displayName, lore);
    }

    @Override
    public ItemStack create(Material material) {

        Validate.notNull(material, "The itemstack material object is null.");

        return new ItemStack(material);
    }

    @Override
    public ItemStack create(Material material, int data) {

        Validate.notNull(material, "The itemstack material object is null.");

        return new ItemStack(material, 1, (short) data);
    }

    @Override
    public ItemStack create(Material material, int data, int amount) {

        Validate.notNull(material, "The itemstack material object is null.");

        return new ItemStack(material, amount, (short) data);
    }

    @Override
    public ItemStack create(Material material, int data, int amount, String displayName) {

        Validate.notNull(material, "The itemstack material object is null.");

        ItemStack itemStack = new ItemStack(material, amount, (short) data);

        setDisplayName(itemStack, displayName);

        return itemStack;
    }

    @Override
    public ItemStack create(Material material, int data, int amount, String displayName, String... lore) {

        Validate.notNull(material, "The itemstack material object is null.");

        ItemStack itemStack = new ItemStack(material, amount, (short) data);

        setDisplayName(itemStack, displayName);
        setLore(itemStack, lore);

        return itemStack;
    }
}
