package com.minecraft.moonlake.api.item;

import com.minecraft.moonlake.api.item.potion.PotionBase;
import com.minecraft.moonlake.api.item.potion.PotionEffectCustom;
import com.minecraft.moonlake.api.item.potion.PotionEffectType;
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
class ItemExpressionWrapped extends ItemExpression {
    
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
    public ItemStack setUnbreakable(ItemStack itemStack, boolean unbreakable) {

        return skullExpression.setUnbreakable(itemStack, unbreakable);
    }

    @Override
    public ReadOnlyBooleanProperty isUnreakable(ItemStack itemStack) {

        return skullExpression.isUnreakable(itemStack);
    }

    @Override
    public ItemStack setAttribute(ItemStack itemStack, AttributeModify attribute) {

        return skullExpression.setAttribute(itemStack, attribute);
    }

    @Override
    public ItemStack setAttribute(ItemStack itemStack, AttributeModify.Type type, AttributeModify.Operation operation, double amount) {

        return skullExpression.setAttribute(itemStack, type, operation, amount);
    }

    @Override
    public ItemStack setAttribute(ItemStack itemStack, AttributeModify.Type type, AttributeModify.Slot slot, AttributeModify.Operation operation, double amount) {

        return skullExpression.setAttribute(itemStack, type, slot, operation, amount);
    }

    @Override
    public List<AttributeModify> getAttributes(ItemStack itemStack) {

        return skullExpression.getAttributes(itemStack);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectCustom... effects) {

        return skullExpression.setCustomPotion(itemStack, effects);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, Collection<? extends PotionEffectCustom> effects) {

        return skullExpression.setCustomPotion(itemStack, effects);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration) {

        return skullExpression.setCustomPotion(itemStack, effectType, amplifier, duration);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return skullExpression.setCustomPotion(itemStack, effectType, amplifier, duration, ambient);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return skullExpression.setCustomPotion(itemStack, effectType, amplifier, duration, showParticles);
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
    public ItemStack createCustomPotion(PotionType type, PotionEffectCustom... effects) {

        return skullExpression.createCustomPotion(type, effects);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, PotionEffectCustom... effects) {

        return skullExpression.createCustomPotion(type, amount, effects);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectCustom... effects) {

        return skullExpression.createCustomPotion(type, amount, displayName, effects);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, Collection<? extends PotionEffectCustom> effects) {

        return skullExpression.createCustomPotion(type, effects);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, Collection<? extends PotionEffectCustom> effects) {

        return skullExpression.createCustomPotion(type, amount, effects);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, Collection<? extends PotionEffectCustom> effects) {

        return skullExpression.createCustomPotion(type, amount, displayName, effects);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, PotionEffectType effectType, int amplifier, int duration) {

        return skullExpression.createCustomPotion(type, effectType, amplifier, duration);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, PotionEffectType effectType, int amplifier, int duration) {

        return skullExpression.createCustomPotion(type, amount, effectType, amplifier, duration);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectType effectType, int amplifier, int duration) {

        return skullExpression.createCustomPotion(type, amount, displayName, effectType, amplifier, duration);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return skullExpression.createCustomPotion(type, effectType, amplifier, duration, ambient);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return skullExpression.createCustomPotion(type, amount, effectType, amplifier, duration, ambient);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return skullExpression.createCustomPotion(type, amount, displayName, effectType, amplifier, duration, ambient);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return skullExpression.createCustomPotion(type, effectType, amplifier, duration, ambient, showParticles);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return skullExpression.createCustomPotion(type, amount, effectType, amplifier, duration, ambient, showParticles);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return skullExpression.createCustomPotion(type, amount, displayName, effectType, amplifier, duration, ambient, showParticles);
    }

    @Override
    public ItemStack setDisplayName(ItemStack itemStack, String displayName) {

        return skullExpression.setDisplayName(itemStack, displayName);
    }

    @Override
    public ItemStack setAmount(ItemStack itemStack, int amount) {

        return skullExpression.setAmount(itemStack, amount);
    }

    @Override
    public ItemStack setDurability(ItemStack itemStack, int durability) {

        return skullExpression.setDurability(itemStack, durability);
    }

    @Override
    public ReadOnlyIntegerProperty getDurability(ItemStack itemStack) {

        return skullExpression.getDurability(itemStack);
    }

    @Override
    public ItemStack resetDurability(ItemStack itemStack) {

        return skullExpression.resetDurability(itemStack);
    }

    @Override
    public ItemStack addDurability(ItemStack itemStack, int durability) {

        return skullExpression.addDurability(itemStack, durability);
    }

    @Override
    public ItemStack takeDurability(ItemStack itemStack, int durability) {

        return skullExpression.takeDurability(itemStack, durability);
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
    public ItemStack setLore(ItemStack itemStack, String... lore) {

        return skullExpression.setLore(itemStack, lore);
    }

    @Override
    public ItemStack setLore(ItemStack itemStack, Collection<? extends String> lore) {

        return skullExpression.setLore(itemStack, lore);
    }

    @Override
    public ItemStack addLore(ItemStack itemStack, String... lore) {

        return skullExpression.addLore(itemStack, lore);
    }

    @Override
    public ItemStack addLore(ItemStack itemStack, Collection<? extends String> lore) {

        return skullExpression.addLore(itemStack, lore);
    }

    @Override
    public ItemStack clearLore(ItemStack itemStack) {

        return skullExpression.clearLore(itemStack);
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
    public ItemStack addEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {

        return skullExpression.addEnchantment(itemStack, enchantment, level);
    }

    @Override
    public ItemStack addEnchantment(ItemStack itemStack, Map<Enchantment, Integer> enchantmentMap) {

        return skullExpression.addEnchantment(itemStack, enchantmentMap);
    }

    @Override
    public ItemStack addSafeEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {

        return skullExpression.addSafeEnchantment(itemStack, enchantment, level);
    }

    @Override
    public ItemStack addSafeEnchantment(ItemStack itemStack, Map<Enchantment, Integer> enchantmentMap) {

        return skullExpression.addSafeEnchantment(itemStack, enchantmentMap);
    }

    @Override
    public ItemStack removeEnchantment(ItemStack itemStack, Enchantment enchantment) {

        return skullExpression.removeEnchantment(itemStack, enchantment);
    }

    @Override
    public ItemStack removeEnchantment(ItemStack itemStack, Collection<? extends Enchantment> enchantments) {

        return skullExpression.removeEnchantment(itemStack, enchantments);
    }

    @Override
    public ItemStack clearEnchantment(ItemStack itemStack) {

        return skullExpression.clearEnchantment(itemStack);
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
    public ItemStack addFlags(ItemStack itemStack, ItemFlag... flags) {

        return skullExpression.addFlags(itemStack, flags);
    }

    @Override
    public ItemStack addFlags(ItemStack itemStack, Collection<? extends ItemFlag> flags) {

        return skullExpression.addFlags(itemStack, flags);
    }

    @Override
    public ItemStack removeFlags(ItemStack itemStack, ItemFlag... flags) {

        return skullExpression.removeFlags(itemStack, flags);
    }

    @Override
    public ItemStack removeFlags(ItemStack itemStack, Collection<? extends ItemFlag> flags) {

        return skullExpression.removeFlags(itemStack, flags);
    }

    @Override
    public ReadOnlyBooleanProperty hasFlags(ItemStack itemStack, ItemFlag... flags) {

        return skullExpression.hasFlags(itemStack, flags);
    }

    @Override
    public ItemStack clearFlags(ItemStack itemStack) {

        return skullExpression.clearFlags(itemStack);
    }

    @Override
    public ItemStack setLeatherColor(ItemStack itemStack, Color color) {

        return skullExpression.setLeatherColor(itemStack, color);
    }

    @Override
    public ItemStack setLeatherColor(ItemStack itemStack, int red, int green, int blue) {

        return skullExpression.setLeatherColor(itemStack, red, green, blue);
    }
}
