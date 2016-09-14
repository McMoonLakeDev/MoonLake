package com.minecraft.moonlake.builder;

import com.minecraft.moonlake.api.item.ItemLibrary;
import com.minecraft.moonlake.api.item.ItemLibraryFactory;
import com.minecraft.moonlake.api.item.ItemLibraryFactorys;
import com.minecraft.moonlake.api.item.ItemBuilder;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;

/**
 * Created by MoonLake on 2016/9/13.
 */
public class ItemBuilderWrapped implements ItemBuilder {

    private final ItemStack itemStack;
    private final ItemLibrary itemLibrary;

    public ItemBuilderWrapped(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        this.itemStack = itemStack;
        this.itemLibrary = ItemLibraryFactory.get().item();
    }

    public ItemBuilderWrapped(Material material) {

        this(ItemLibraryFactorys.craft().create(material));
    }

    public ItemBuilderWrapped(Material material, int data) {

        this(ItemLibraryFactorys.craft().create(material, data));
    }

    public ItemBuilderWrapped(Material material, int data, int amount) {

        this(ItemLibraryFactorys.craft().create(material, data, amount));
    }

    public ItemBuilderWrapped(Material material, int data, int amount, String displayName) {

        this(ItemLibraryFactorys.craft().create(material, data, amount, displayName));
    }

    public ItemBuilderWrapped(Material material, int data, int amount, String displayName, String... lore) {

        this(ItemLibraryFactorys.craft().create(material, data, amount, displayName, lore));
    }

    @Override
    public ItemBuilder setDisplayName(String displayName) {
        
        itemLibrary.setDisplayName(itemStack, displayName);
        
        return this;
    }

    @Override
    public ItemBuilder setAmount(int amount) {

        itemLibrary.setAmount(itemStack, amount);

        return this;
    }

    @Override
    public ItemBuilder setDurability(int durability) {

        itemLibrary.setDurability(itemStack, durability);

        return this;
    }

    @Override
    public ItemBuilder resetDurability() {

        itemLibrary.resetDurability(itemStack);

        return this;
    }

    @Override
    public ItemBuilder addDurability(int durability) {

        itemLibrary.addDurability(itemStack, durability);

        return this;
    }

    @Override
    public ItemBuilder takeDurability(int durability) {

        itemLibrary.takeDurability(itemStack, durability);

        return this;
    }

    @Override
    public ItemBuilder setLore(String... lore) {

        itemLibrary.setLore(itemStack, lore);

        return this;
    }

    @Override
    public ItemBuilder setLore(Collection<? extends String> lore) {

        itemLibrary.setLore(itemStack, lore);

        return this;
    }

    @Override
    public ItemBuilder addLore(String... lore) {

        itemLibrary.addLore(itemStack, lore);

        return this;
    }

    @Override
    public ItemBuilder addLore(Collection<? extends String> lore) {

        itemLibrary.addLore(itemStack, lore);

        return this;
    }

    @Override
    public ItemBuilder clearLore() {

        itemLibrary.clearLore(itemStack);

        return this;
    }

    @Override
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {

        itemLibrary.addEnchantment(itemStack, enchantment, level);

        return this;
    }

    @Override
    public ItemBuilder addEnchantment(Map<Enchantment, Integer> enchantmentMap) {

        itemLibrary.addEnchantment(itemStack, enchantmentMap);

        return this;
    }

    @Override
    public ItemBuilder addSafeEnchantment(Enchantment enchantment, int level) {

        itemLibrary.addSafeEnchantment(itemStack, enchantment, level);

        return this;
    }

    @Override
    public ItemBuilder addSafeEnchantment(Map<Enchantment, Integer> enchantmentMap) {

        itemLibrary.addSafeEnchantment(itemStack, enchantmentMap);

        return this;
    }

    @Override
    public ItemBuilder removeEnchantment(Enchantment enchantment) {

        itemLibrary.removeEnchantment(itemStack, enchantment);

        return this;
    }

    @Override
    public ItemBuilder removeEnchantment(Collection<? extends Enchantment> enchantments) {

        itemLibrary.removeEnchantment(itemStack, enchantments);

        return this;
    }

    @Override
    public ItemBuilder clearEnchantment() {

        itemLibrary.clearEnchantment(itemStack);

        return this;
    }

    @Override
    public ItemBuilder addFlags(ItemFlag... flags) {

        itemLibrary.addFlags(itemStack, flags);

        return this;
    }

    @Override
    public ItemBuilder addFlags(Collection<? extends ItemFlag> flags) {

        itemLibrary.addFlags(itemStack, flags);

        return this;
    }

    @Override
    public ItemBuilder removeFlags(ItemFlag... flags) {

        itemLibrary.removeFlags(itemStack, flags);

        return this;
    }

    @Override
    public ItemBuilder removeFlags(Collection<? extends ItemFlag> flags) {

        itemLibrary.removeFlags(itemStack, flags);

        return this;
    }

    @Override
    public ItemBuilder clearFlags() {

        itemLibrary.clearFlags(itemStack);

        return this;
    }

    @Override
    public ItemBuilder setLeatherColor(Color color) {

        itemLibrary.setLeatherColor(itemStack, color);

        return this;
    }

    @Override
    public ItemBuilder setLeatherColor(int red, int green, int blue) {

        itemLibrary.setLeatherColor(itemStack, red, green, blue);

        return this;
    }

    @Override
    public ItemStack build() {

        return build(true);
    }

    @Override
    public ItemStack build(boolean safeObj) {

        return safeObj ? itemStack.clone() : itemStack;
    }
}
