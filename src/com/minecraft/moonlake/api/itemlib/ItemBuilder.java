package com.minecraft.moonlake.api.itemlib;

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.util.Util;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MoonLake on 2016/6/23.
 */
public class ItemBuilder {

    private final Itemlib itemlib;

    private ItemStack item;
    private ItemMeta meta;
    private List<String> lores;

    public ItemBuilder(int id) {

        this(Material.getMaterial(id), 0);
    }

    public ItemBuilder(int id, int data) {

        this(Material.getMaterial(id), data);
    }

    public ItemBuilder(Material type) {

        this(type, 0);
    }

    public ItemBuilder(Material type, int data) {

        this.item = new ItemStack(type, 1, (short)data);
        this.meta = item.getItemMeta();
        this.lores = new ArrayList<>();
        this.itemlib = MoonLakePlugin.getInstances().getItemlib();
    }

    public ItemBuilder setAmount(int amount) {

        this.item.setAmount(amount);

        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {

        this.meta.setDisplayName(Util.color(displayName));

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

        this.meta.addItemFlags(flags);

        return this;
    }

    public ItemBuilder removeFlag(ItemFlag... flags) {

        this.meta.removeItemFlags(flags);

        return this;
    }

    public ItemBuilder addLore(String lore) {

        this.lores.add(lore);

        return this;
    }

    public ItemBuilder addLores(String... lores) {

        this.lores.addAll(Arrays.asList(lores));

        return this;
    }

    public ItemBuilder removeLore(String lore) {

        this.lores.remove(lore);

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

    public ItemBuilder addFlags(ItemFlag... flags) {

        this.meta.addItemFlags(flags);

        return this;
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {

        this.meta.removeItemFlags(flags);

        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {

        this.item = itemlib.setUnbreakable(item, unbreakable);

        return this;
    }

    public ItemStack build() {

        this.meta.setLore(Util.color(lores));
        this.item.setItemMeta(this.meta);

        return this.item.clone();
    }
}
