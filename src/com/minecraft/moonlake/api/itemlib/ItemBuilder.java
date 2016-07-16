package com.minecraft.moonlake.api.itemlib;

import com.minecraft.moonlake.MoonLakePlugin;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoonLake on 2016/6/23.
 */
public class ItemBuilder {

    private final Itemlib itemlib;

    private ItemStack item;
    private int data;

    public ItemBuilder(int id) {

        this(Material.getMaterial(id), 0);
    }

    public ItemBuilder(int id, int data) {

        this(Material.getMaterial(id), data);
    }

    public ItemBuilder(int id, int data, String displayName) {

        this(Material.getMaterial(id), data, displayName);
    }

    public ItemBuilder(Material type) {

        this(type, 0);
    }

    public ItemBuilder(Material type, int data) {

        this.data = data;
        this.item = new ItemStack(type, 1, (short)data);
        this.itemlib = MoonLakePlugin.getInstances().getItemlib();
    }

    public ItemBuilder(Material type, int data, String displayName) {

        this.data = data;
        this.itemlib = MoonLakePlugin.getInstances().getItemlib();
        this.item = itemlib.create(type, data, 1, displayName);
    }

    public ItemBuilder setAmount(int amount) {

        this.item.setAmount(amount);

        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {

        this.item.addUnsafeEnchantment(enchantment, level);

        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchantment) {

        this.item.removeEnchantment(enchantment);

        return this;
    }

    public ItemBuilder addFlag(ItemFlag... flags) {

        this.item = itemlib.addFlags(item, flags);

        return this;
    }

    public ItemBuilder removeFlag(ItemFlag... flags) {

        this.item = itemlib.removeFlags(item, flags);

        return this;
    }

    public ItemBuilder addLore(String lore) {

        this.item = itemlib.addLore(item, lore);

        return this;
    }

    public ItemBuilder addLores(String... lores) {

        this.item = itemlib.addLore(item, lores);

        return this;
    }

    public ItemBuilder addLores(List<String> lores) {

        this.item = itemlib.addLore(item, lores);

        return this;
    }

    public ItemBuilder removeLore() {

        this.item = itemlib.setLore(item, new ArrayList<String>());

        return this;
    }

    public ItemBuilder setAttackDamage(double damage, boolean percent, Itemlib.AttributeType.Slot slot) {

        this.item = itemlib.setItemAttackDamage(item, damage, percent, slot);

        return this;
    }

    public ItemBuilder setMoveSpeed(double moveSpeed, boolean percent, Itemlib.AttributeType.Slot slot) {

        this.item = itemlib.setItemMoveSpeed(item, moveSpeed, percent, slot);

        return this;
    }

    public ItemBuilder setArmorDefense(double defense, boolean percent, Itemlib.AttributeType.Slot slot) {

        this.item = itemlib.setItemArmorDefense(item, defense, percent, slot);

        return this;
    }

    public ItemBuilder setArmorToughness(double toughness, boolean percent, Itemlib.AttributeType.Slot slot) {

        this.item = itemlib.setItemArmorToughness(item, toughness, percent, slot);

        return this;
    }

    public ItemBuilder setAttackSpeed(double attackSpeed, boolean percent, Itemlib.AttributeType.Slot slot) {

        this.item = itemlib.setItemAttackSpeed(item, attackSpeed, percent, slot);

        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {

        this.item = itemlib.setUnbreakable(item, unbreakable);

        return this;
    }

    public ItemBuilder setLeatherColor(Color color) {

        this.item = itemlib.setLeatherArmorColor(item, color);

        return this;
    }

    public ItemStack build() {

        return this.item.clone();
    }
}
