package com.minecraft.moonlake.item;

import com.minecraft.moonlake.property.ReadOnlyBooleanProperty;
import com.minecraft.moonlake.property.ReadOnlyIntegerProperty;
import com.minecraft.moonlake.property.ReadOnlyStringProperty;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by MoonLake on 2016/9/13.
 */
public class ItemExpressionWrapped extends ItemExpression {

    private final CraftExpression craftExpression;
    private final SkullExpression skullExpression;

    public ItemExpressionWrapped() {

        this.craftExpression = new CraftExpression();
        this.skullExpression = new SkullExpression();
    }

    @Override
    public ItemStack createSkull() {

        return skullExpression.createSkull();
    }

    @Override
    public ItemStack createSkull(String skullOwner) {

        return skullExpression.createSkull(skullOwner);
    }

    @Override
    public ReadOnlyStringProperty getSkullOwner(ItemStack itemStack) {

        return skullExpression.getSkullOwner(itemStack);
    }

    @Override
    public void setUnbreakable(ItemStack itemStack, boolean unbreakable) {

        craftExpression.setUnbreakable(itemStack, unbreakable);
    }

    @Override
    public ReadOnlyBooleanProperty isUnreakable(ItemStack itemStack) {

        return craftExpression.isUnreakable(itemStack);
    }

    @Override
    public ItemStack create(int id) {

        return craftExpression.create(id);
    }

    @Override
    public ItemStack create(int id, int data) {

        return craftExpression.create(id, data);
    }

    @Override
    public ItemStack create(int id, int data, int amount) {

        return craftExpression.create(id, data, amount);
    }

    @Override
    public ItemStack create(int id, int data, int amount, String displayName) {

        return craftExpression.create(id, data, amount, displayName);
    }

    @Override
    public ItemStack create(int id, int data, int amount, String displayName, String... lore) {

        return craftExpression.create(id, data, amount, displayName, lore);
    }

    @Override
    public ItemStack create(String type) {

        return craftExpression.create(type);
    }

    @Override
    public ItemStack create(String type, int data) {

        return craftExpression.create(type, data);
    }

    @Override
    public ItemStack create(String type, int data, int amount) {

        return craftExpression.create(type, data);
    }

    @Override
    public ItemStack create(String type, int data, int amount, String displayName) {

        return craftExpression.create(type, data, amount, displayName);
    }

    @Override
    public ItemStack create(String type, int data, int amount, String displayName, String... lore) {

        return craftExpression.create(type, data, amount, displayName, lore);
    }

    @Override
    public ItemStack create(Material material) {

        return craftExpression.create(material);
    }

    @Override
    public ItemStack create(Material material, int data) {

        return craftExpression.create(material, data);
    }

    @Override
    public ItemStack create(Material material, int data, int amount) {

        return craftExpression.create(material, data, amount);
    }

    @Override
    public ItemStack create(Material material, int data, int amount, String displayName) {

        return craftExpression.create(material, data, amount, displayName);
    }

    @Override
    public ItemStack create(Material material, int data, int amount, String displayName, String... lore) {

        return craftExpression.create(material, data, amount, displayName, lore);
    }

    @Override
    public void setDisplayName(ItemStack itemStack, String displayName) {

        craftExpression.setDisplayName(itemStack, displayName);
    }

    @Override
    public void setAmount(ItemStack itemStack, int amount) {

        craftExpression.setAmount(itemStack, amount);
    }

    @Override
    public void setDurability(ItemStack itemStack, int durability) {

        craftExpression.setDurability(itemStack, durability);
    }

    @Override
    public ReadOnlyIntegerProperty getDurability(ItemStack itemStack) {

        return craftExpression.getDurability(itemStack);
    }

    @Override
    public void resetDurability(ItemStack itemStack) {

        craftExpression.resetDurability(itemStack);
    }

    @Override
    public void addDurability(ItemStack itemStack, int durability) {

        craftExpression.addDurability(itemStack, durability);
    }

    @Override
    public void takeDurability(ItemStack itemStack, int durability) {

        craftExpression.takeDurability(itemStack, durability);
    }

    @Override
    public Set<String> getLores(ItemStack itemStack) {

        return craftExpression.getLores(itemStack);
    }

    @Override
    public Set<String> getLores(ItemStack itemStack, boolean ignoreColor) {

        return craftExpression.getLores(itemStack, ignoreColor);
    }

    @Override
    public void setLore(ItemStack itemStack, String... lore) {

        craftExpression.setLore(itemStack, lore);
    }

    @Override
    public void setLore(ItemStack itemStack, Collection<? extends String> lore) {

        craftExpression.setLore(itemStack, lore);
    }

    @Override
    public void addLore(ItemStack itemStack, String... lore) {

        craftExpression.addLore(itemStack, lore);
    }

    @Override
    public void addLore(ItemStack itemStack, Collection<? extends String> lore) {

        craftExpression.addLore(itemStack, lore);
    }

    @Override
    public void clearLore(ItemStack itemStack) {

        craftExpression.clearLore(itemStack);
    }

    @Override
    public ReadOnlyBooleanProperty hasLore(ItemStack itemStack) {

        return craftExpression.hasLore(itemStack);
    }

    @Override
    public ReadOnlyBooleanProperty containLore(ItemStack itemStack, String... lore) {

        return craftExpression.containLore(itemStack, lore);
    }

    @Override
    public ReadOnlyBooleanProperty containLore(ItemStack itemStack, Collection<? extends String> lore) {

        return craftExpression.containLore(itemStack, lore);
    }

    @Override
    public ReadOnlyBooleanProperty containLoreIgnoreColor(ItemStack itemStack, String... lore) {

        return craftExpression.containLoreIgnoreColor(itemStack, lore);
    }

    @Override
    public ReadOnlyBooleanProperty containLoreIgnoreColor(ItemStack itemStack, Collection<? extends String> lore) {

        return craftExpression.containLoreIgnoreColor(itemStack, lore);
    }

    @Override
    public Map<Enchantment, Integer> getEnchantments(ItemStack itemStack) {

        return craftExpression.getEnchantments(itemStack);
    }

    @Override
    public void addEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {

        craftExpression.addEnchantment(itemStack, enchantment, level);
    }

    @Override
    public void addEnchantment(ItemStack itemStack, Map<Enchantment, Integer> enchantmentMap) {

        craftExpression.addEnchantment(itemStack, enchantmentMap);
    }

    @Override
    public void addSafeEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {

        craftExpression.addSafeEnchantment(itemStack, enchantment, level);
    }

    @Override
    public void addSafeEnchantment(ItemStack itemStack, Map<Enchantment, Integer> enchantmentMap) {

        craftExpression.addSafeEnchantment(itemStack, enchantmentMap);
    }

    @Override
    public void removeEnchantment(ItemStack itemStack, Enchantment enchantment) {

        craftExpression.removeEnchantment(itemStack, enchantment);
    }

    @Override
    public void removeEnchantment(ItemStack itemStack, Collection<? extends Enchantment> enchantments) {

        craftExpression.removeEnchantment(itemStack, enchantments);
    }

    @Override
    public void clearEnchantment(ItemStack itemStack) {

        craftExpression.clearEnchantment(itemStack);
    }

    @Override
    public ReadOnlyBooleanProperty hasEnchantment(ItemStack itemStack, Enchantment enchantment) {

        return craftExpression.hasEnchantment(itemStack, enchantment);
    }

    @Override
    public Set<ItemFlag> getFlags(ItemStack itemStack) {

        return craftExpression.getFlags(itemStack);
    }

    @Override
    public void addFlags(ItemStack itemStack, ItemFlag... flags) {

        craftExpression.addFlags(itemStack, flags);
    }

    @Override
    public void addFlags(ItemStack itemStack, Collection<? extends ItemFlag> flags) {

        craftExpression.addFlags(itemStack, flags);
    }

    @Override
    public void removeFlags(ItemStack itemStack, ItemFlag... flags) {

        craftExpression.removeFlags(itemStack, flags);
    }

    @Override
    public void removeFlags(ItemStack itemStack, Collection<? extends ItemFlag> flags) {

        craftExpression.removeFlags(itemStack, flags);
    }

    @Override
    public ReadOnlyBooleanProperty hasFlags(ItemStack itemStack, ItemFlag... flags) {

        return craftExpression.hasFlags(itemStack, flags);
    }

    @Override
    public void clearFlags(ItemStack itemStack) {

        craftExpression.clearFlags(itemStack);
    }

    @Override
    public void setLeatherColor(ItemStack itemStack, Color color) {

        craftExpression.setLeatherColor(itemStack, color);
    }

    @Override
    public void setLeatherColor(ItemStack itemStack, int red, int green, int blue) {

        craftExpression.setLeatherColor(itemStack, red, green, blue);
    }
}
