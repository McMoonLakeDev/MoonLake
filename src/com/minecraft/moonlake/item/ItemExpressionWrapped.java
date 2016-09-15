package com.minecraft.moonlake.item;

import com.minecraft.moonlake.api.item.AttributeModify;
import com.minecraft.moonlake.api.item.potion.PotionBase;
import com.minecraft.moonlake.api.item.potion.PotionType;
import com.minecraft.moonlake.property.ReadOnlyBooleanProperty;
import com.minecraft.moonlake.property.ReadOnlyIntegerProperty;
import com.minecraft.moonlake.property.ReadOnlyStringProperty;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by MoonLake on 2016/9/13.
 */
public class ItemExpressionWrapped extends ItemExpression {
    
    private final SkullExpression skullExpression;

    public ItemExpressionWrapped() {
        
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
    public ItemStack createSkull(String skullOwner, String displayName) {

        return skullExpression.createSkull(skullOwner, displayName);
    }

    @Override
    public ItemStack createSkull(String skullOwner, String displayName, String prefile) {

        return skullExpression.createSkull(skullOwner, displayName, prefile);
    }

    @Override
    public ItemStack createSkull(String skullOwner, String displayName, URL prefile) {

        return skullExpression.createSkull(skullOwner, displayName, prefile);
    }

    @Override
    public ReadOnlyStringProperty getSkullOwner(ItemStack itemStack) {

        return skullExpression.getSkullOwner(itemStack);
    }

    @Override
    public void setUnbreakable(ItemStack itemStack, boolean unbreakable) {

        skullExpression.setUnbreakable(itemStack, unbreakable);
    }

    @Override
    public ReadOnlyBooleanProperty isUnreakable(ItemStack itemStack) {

        return skullExpression.isUnreakable(itemStack);
    }

    @Override
    public void setAttribute(ItemStack itemStack, AttributeModify attribute) {

        skullExpression.setAttribute(itemStack, attribute);
    }

    @Override
    public void setAttribute(ItemStack itemStack, AttributeModify.Type type, AttributeModify.Operation operation, double amount) {

        skullExpression.setAttribute(itemStack, type, operation, amount);
    }

    @Override
    public void setAttribute(ItemStack itemStack, AttributeModify.Type type, AttributeModify.Slot slot, AttributeModify.Operation operation, double amount) {

        skullExpression.setAttribute(itemStack, type, slot, operation, amount);
    }

    @Override
    public List<AttributeModify> getAttributes(ItemStack itemStack) {

        return skullExpression.getAttributes(itemStack);
    }

    @Override
    public ItemStack create(int id) {

        return skullExpression.create(id);
    }

    @Override
    public ItemStack create(int id, int data) {

        return skullExpression.create(id, data);
    }

    @Override
    public ItemStack create(int id, int data, int amount) {

        return skullExpression.create(id, data, amount);
    }

    @Override
    public ItemStack create(int id, int data, int amount, String displayName) {

        return skullExpression.create(id, data, amount, displayName);
    }

    @Override
    public ItemStack create(int id, int data, int amount, String displayName, String... lore) {

        return skullExpression.create(id, data, amount, displayName, lore);
    }

    @Override
    public ItemStack create(String type) {

        return skullExpression.create(type);
    }

    @Override
    public ItemStack create(String type, int data) {

        return skullExpression.create(type, data);
    }

    @Override
    public ItemStack create(String type, int data, int amount) {

        return skullExpression.create(type, data);
    }

    @Override
    public ItemStack create(String type, int data, int amount, String displayName) {

        return skullExpression.create(type, data, amount, displayName);
    }

    @Override
    public ItemStack create(String type, int data, int amount, String displayName, String... lore) {

        return skullExpression.create(type, data, amount, displayName, lore);
    }

    @Override
    public ItemStack create(Material material) {

        return skullExpression.create(material);
    }

    @Override
    public ItemStack create(Material material, int data) {

        return skullExpression.create(material, data);
    }

    @Override
    public ItemStack create(Material material, int data, int amount) {

        return skullExpression.create(material, data, amount);
    }

    @Override
    public ItemStack create(Material material, int data, int amount, String displayName) {

        return skullExpression.create(material, data, amount, displayName);
    }

    @Override
    public ItemStack create(Material material, int data, int amount, String displayName, String... lore) {

        return skullExpression.create(material, data, amount, displayName, lore);
    }

    @Override
    public ItemStack createPotion(PotionType type, PotionBase base) {

        return skullExpression.createPotion(type, base);
    }

    @Override
    public ItemStack createPotion(PotionType type, PotionBase base, int amount) {

        return skullExpression.createPotion(type, base, amount);
    }

    @Override
    public ItemStack createPotion(PotionType type, PotionBase base, int amount, String displayName) {

        return skullExpression.createPotion(type, base, amount, displayName);
    }

    @Override
    public ItemStack createNormalPotion(PotionBase base) {

        return skullExpression.createNormalPotion(base);
    }

    @Override
    public ItemStack createNormalPotion(PotionBase base, int amount) {

        return skullExpression.createNormalPotion(base, amount);
    }

    @Override
    public ItemStack createNormalPotion(PotionBase base, int amount, String displayName) {

        return skullExpression.createNormalPotion(base, amount, displayName);
    }

    @Override
    public ItemStack createSplashPotion(PotionBase base) {

        return skullExpression.createSplashPotion(base);
    }

    @Override
    public ItemStack createSplashPotion(PotionBase base, int amount) {

        return skullExpression.createSplashPotion(base, amount);
    }

    @Override
    public ItemStack createSplashPotion(PotionBase base, int amount, String displayName) {

        return skullExpression.createSplashPotion(base, amount, displayName);
    }

    @Override
    public ItemStack createLingeringPotion(PotionBase base) {

        return skullExpression.createLingeringPotion(base);
    }

    @Override
    public ItemStack createLingeringPotion(PotionBase base, int amount) {

        return skullExpression.createLingeringPotion(base, amount);
    }

    @Override
    public ItemStack createLingeringPotion(PotionBase base, int amount, String displayName) {

        return skullExpression.createLingeringPotion(base, amount, displayName);
    }

    @Override
    public void setDisplayName(ItemStack itemStack, String displayName) {

        skullExpression.setDisplayName(itemStack, displayName);
    }

    @Override
    public void setAmount(ItemStack itemStack, int amount) {

        skullExpression.setAmount(itemStack, amount);
    }

    @Override
    public void setDurability(ItemStack itemStack, int durability) {

        skullExpression.setDurability(itemStack, durability);
    }

    @Override
    public ReadOnlyIntegerProperty getDurability(ItemStack itemStack) {

        return skullExpression.getDurability(itemStack);
    }

    @Override
    public void resetDurability(ItemStack itemStack) {

        skullExpression.resetDurability(itemStack);
    }

    @Override
    public void addDurability(ItemStack itemStack, int durability) {

        skullExpression.addDurability(itemStack, durability);
    }

    @Override
    public void takeDurability(ItemStack itemStack, int durability) {

        skullExpression.takeDurability(itemStack, durability);
    }

    @Override
    public Set<String> getLores(ItemStack itemStack) {

        return skullExpression.getLores(itemStack);
    }

    @Override
    public Set<String> getLores(ItemStack itemStack, boolean ignoreColor) {

        return skullExpression.getLores(itemStack, ignoreColor);
    }

    @Override
    public void setLore(ItemStack itemStack, String... lore) {

        skullExpression.setLore(itemStack, lore);
    }

    @Override
    public void setLore(ItemStack itemStack, Collection<? extends String> lore) {

        skullExpression.setLore(itemStack, lore);
    }

    @Override
    public void addLore(ItemStack itemStack, String... lore) {

        skullExpression.addLore(itemStack, lore);
    }

    @Override
    public void addLore(ItemStack itemStack, Collection<? extends String> lore) {

        skullExpression.addLore(itemStack, lore);
    }

    @Override
    public void clearLore(ItemStack itemStack) {

        skullExpression.clearLore(itemStack);
    }

    @Override
    public ReadOnlyBooleanProperty hasLore(ItemStack itemStack) {

        return skullExpression.hasLore(itemStack);
    }

    @Override
    public ReadOnlyBooleanProperty containLore(ItemStack itemStack, String... lore) {

        return skullExpression.containLore(itemStack, lore);
    }

    @Override
    public ReadOnlyBooleanProperty containLore(ItemStack itemStack, Collection<? extends String> lore) {

        return skullExpression.containLore(itemStack, lore);
    }

    @Override
    public ReadOnlyBooleanProperty containLoreIgnoreColor(ItemStack itemStack, String... lore) {

        return skullExpression.containLoreIgnoreColor(itemStack, lore);
    }

    @Override
    public ReadOnlyBooleanProperty containLoreIgnoreColor(ItemStack itemStack, Collection<? extends String> lore) {

        return skullExpression.containLoreIgnoreColor(itemStack, lore);
    }

    @Override
    public Map<Enchantment, Integer> getEnchantments(ItemStack itemStack) {

        return skullExpression.getEnchantments(itemStack);
    }

    @Override
    public void addEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {

        skullExpression.addEnchantment(itemStack, enchantment, level);
    }

    @Override
    public void addEnchantment(ItemStack itemStack, Map<Enchantment, Integer> enchantmentMap) {

        skullExpression.addEnchantment(itemStack, enchantmentMap);
    }

    @Override
    public void addSafeEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {

        skullExpression.addSafeEnchantment(itemStack, enchantment, level);
    }

    @Override
    public void addSafeEnchantment(ItemStack itemStack, Map<Enchantment, Integer> enchantmentMap) {

        skullExpression.addSafeEnchantment(itemStack, enchantmentMap);
    }

    @Override
    public void removeEnchantment(ItemStack itemStack, Enchantment enchantment) {

        skullExpression.removeEnchantment(itemStack, enchantment);
    }

    @Override
    public void removeEnchantment(ItemStack itemStack, Collection<? extends Enchantment> enchantments) {

        skullExpression.removeEnchantment(itemStack, enchantments);
    }

    @Override
    public void clearEnchantment(ItemStack itemStack) {

        skullExpression.clearEnchantment(itemStack);
    }

    @Override
    public ReadOnlyBooleanProperty hasEnchantment(ItemStack itemStack, Enchantment enchantment) {

        return skullExpression.hasEnchantment(itemStack, enchantment);
    }

    @Override
    public Set<ItemFlag> getFlags(ItemStack itemStack) {

        return skullExpression.getFlags(itemStack);
    }

    @Override
    public void addFlags(ItemStack itemStack, ItemFlag... flags) {

        skullExpression.addFlags(itemStack, flags);
    }

    @Override
    public void addFlags(ItemStack itemStack, Collection<? extends ItemFlag> flags) {

        skullExpression.addFlags(itemStack, flags);
    }

    @Override
    public void removeFlags(ItemStack itemStack, ItemFlag... flags) {

        skullExpression.removeFlags(itemStack, flags);
    }

    @Override
    public void removeFlags(ItemStack itemStack, Collection<? extends ItemFlag> flags) {

        skullExpression.removeFlags(itemStack, flags);
    }

    @Override
    public ReadOnlyBooleanProperty hasFlags(ItemStack itemStack, ItemFlag... flags) {

        return skullExpression.hasFlags(itemStack, flags);
    }

    @Override
    public void clearFlags(ItemStack itemStack) {

        skullExpression.clearFlags(itemStack);
    }

    @Override
    public void setLeatherColor(ItemStack itemStack, Color color) {

        skullExpression.setLeatherColor(itemStack, color);
    }

    @Override
    public void setLeatherColor(ItemStack itemStack, int red, int green, int blue) {

        skullExpression.setLeatherColor(itemStack, red, green, blue);
    }
}
