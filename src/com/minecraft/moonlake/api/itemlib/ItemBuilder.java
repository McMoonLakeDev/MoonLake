package com.minecraft.moonlake.api.itemlib;

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

    private ItemStack item;
    private ItemMeta meta;
    private List<String> lores;

    public ItemBuilder(int id, int data) {

        this(Material.getMaterial(id), data);
    }

    public ItemBuilder(Material type, int data) {

        this.item = new ItemStack(type, 1, (short)data);
        this.meta = item.getItemMeta();
        this.lores = new ArrayList<>();
    }

    public ItemBuilder setAmount(int amount) {

        this.item.setAmount(amount);

        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {

        this.meta.setDisplayName(displayName);

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

    public ItemStack build() {

        this.meta.setLore(Util.color(lores));
        this.item.setItemMeta(this.meta);

        return this.item.clone();
    }
}
