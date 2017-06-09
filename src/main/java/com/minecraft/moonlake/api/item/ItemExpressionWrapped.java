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

import com.minecraft.moonlake.api.item.potion.PotionBase;
import com.minecraft.moonlake.api.item.potion.PotionEffectCustom;
import com.minecraft.moonlake.api.item.potion.PotionEffectType;
import com.minecraft.moonlake.api.item.potion.PotionType;
import com.minecraft.moonlake.api.nbt.NBTCompound;
import com.minecraft.moonlake.exception.MoonLakeException;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <h1>ItemExpressionWrapped</h1>
 * 物品栈接口单实现类
 *
 * @version 1.0.1
 * @author Month_Light
 */
class ItemExpressionWrapped extends ItemExpression {
    
    private final BaseExpression base;

    /**
     * 物品栈接口单实现类构造函数
     */
    public ItemExpressionWrapped() {
        
        this.base = new BaseExpression();
    }

    @Override
    public ItemStack createSkull() {

        return base.createSkull();
    }

    @Override
    public ItemStack createSkull(String skullOwner) {

        return base.createSkull(skullOwner);
    }

    @Override
    public ItemStack createSkullWithOwner(String skullOwner) {

        return base.createSkullWithOwner(skullOwner);
    }

    @Override
    public ItemStack createSkullWithOwner(String skullOwner, String displayName) {

        return base.createSkullWithOwner(skullOwner, displayName);
    }

    @Override
    public ItemStack createSkullWithSkin(String skinURL) throws MoonLakeException {

        return base.createSkullWithSkin(skinURL);
    }

    @Override
    public ItemStack createSkullWithSkins(String value, String signature) throws MoonLakeException {

        return base.createSkullWithSkins(value, signature);
    }

    @Override
    public ItemStack createSkullWithSkin(String skinURL, String displayName) {

        return base.createSkullWithSkin(skinURL, displayName);
    }

    @Override
    public ItemStack createSkullWithSkins(String value, String signature, String displayName) throws MoonLakeException {

        return base.createSkullWithSkins(value, signature, displayName);
    }

    @Override
    public String getSkullOwner(ItemStack itemStack) {

        return base.getSkullOwner(itemStack);
    }

    @Override
    public ItemStack setSkullWithSkin(ItemStack itemStack, String data) throws MoonLakeException {

        return base.setSkullWithSkin(itemStack, data);
    }

    @Override
    public ItemStack setSkullWithSkin(ItemStack itemStack, String value, String signature) throws MoonLakeException {

        return base.setSkullWithSkin(itemStack, value, signature);
    }

    @Override
    public ItemStack setUnbreakable(ItemStack itemStack, boolean unbreakable) {

        return base.setUnbreakable(itemStack, unbreakable);
    }

    @Override
    public void setUnbreakable(NBTCompound nbtCompound, boolean unbreakable) {

        base.setUnbreakable(nbtCompound, unbreakable);
    }

    @Override
    public boolean isUnbreakable(ItemStack itemStack) {

        return base.isUnbreakable(itemStack);
    }

    @Override
    public boolean isUnbreakable(NBTCompound nbtCompound) {

        return base.isUnbreakable(nbtCompound);
    }

    @Override
    public ItemStack setAttribute(ItemStack itemStack, AttributeModify attribute) {

        return base.setAttribute(itemStack, attribute);
    }

    @Override
    public void setAttribute(NBTCompound nbtCompound, AttributeModify attribute) {

        base.setAttribute(nbtCompound, attribute);
    }

    @Override
    public ItemStack setAttribute(ItemStack itemStack, AttributeModify.Type type, AttributeModify.Operation operation, double amount) {

        return base.setAttribute(itemStack, type, operation, amount);
    }

    @Override
    public ItemStack setAttribute(ItemStack itemStack, AttributeModify.Type type, AttributeModify.Slot slot, AttributeModify.Operation operation, double amount) {

        return base.setAttribute(itemStack, type, slot, operation, amount);
    }

    @Override
    public void setAttribute(NBTCompound nbtCompound, AttributeModify.Type type, AttributeModify.Operation operation, double amount) {

        base.setAttribute(nbtCompound, type, operation, amount);
    }

    @Override
    public void setAttribute(NBTCompound nbtCompound, AttributeModify.Type type, AttributeModify.Slot slot, AttributeModify.Operation operation, double amount) {

        base.setAttribute(nbtCompound, type, slot, operation, amount);
    }

    @Override
    public Set<AttributeModify> getAttributes(ItemStack itemStack) {

        return base.getAttributes(itemStack);
    }

    @Override
    public Set<AttributeModify> getAttributes(NBTCompound nbtCompound) {

        return base.getAttributes(nbtCompound);
    }

    @Override
    public boolean hasAttribute(ItemStack itemStack, AttributeModify.Type type) {

        return base.hasAttribute(itemStack, type);
    }

    @Override
    public boolean hasAttribute(NBTCompound nbtCompound, AttributeModify.Type type) {

        return base.hasAttribute(nbtCompound, type);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectCustom... effects) {

        return base.setCustomPotion(itemStack, effects);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, Collection<? extends PotionEffectCustom> effects) {

        return base.setCustomPotion(itemStack, effects);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration) {

        return base.setCustomPotion(itemStack, effectType, amplifier, duration);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return base.setCustomPotion(itemStack, effectType, amplifier, duration, ambient);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return base.setCustomPotion(itemStack, effectType, amplifier, duration, showParticles);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration) {

        return base.setCustomPotion(itemStack, effectType, amplifier, duration);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return base.setCustomPotion(itemStack, effectType, amplifier, duration, ambient);
    }

    @Override
    public ItemStack setCustomPotion(ItemStack itemStack, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return base.setCustomPotion(itemStack, effectType, amplifier, duration, ambient, showParticles);
    }

    @Override
    public Set<PotionEffectCustom> getCustomPotion(ItemStack itemStack) {

        return base.getCustomPotion(itemStack);
    }

    @Override
    public boolean hasCustomPotion(ItemStack itemStack, PotionEffectType effectType) {

        return base.hasCustomPotion(itemStack, effectType);
    }

    @Override
    public boolean hasCustomPotion(ItemStack itemStack, com.minecraft.moonlake.enums.PotionEffectType effectType) {

        return base.hasCustomPotion(itemStack, effectType);
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, PotionEffectCustom... effects) {

        base.setCustomPotion(nbtCompound, effects);
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, Collection<? extends PotionEffectCustom> effects) {

        base.setCustomPotion(nbtCompound, effects);
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, PotionEffectType effectType, int amplifier, int duration) {

        base.setCustomPotion(nbtCompound, effectType, amplifier, duration);
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        base.setCustomPotion(nbtCompound, effectType, amplifier, duration, ambient);
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        base.setCustomPotion(nbtCompound, effectType, amplifier, duration, ambient, showParticles);
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration) {

        base.setCustomPotion(nbtCompound, effectType, amplifier, duration);
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        base.setCustomPotion(nbtCompound, effectType, amplifier, duration, ambient);
    }

    @Override
    public void setCustomPotion(NBTCompound nbtCompound, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        base.setCustomPotion(nbtCompound, effectType, amplifier, duration, ambient, showParticles);
    }

    @Override
    public Set<PotionEffectCustom> getCustomPotion(NBTCompound nbtCompound) {

        return base.getCustomPotion(nbtCompound);
    }

    @Override
    public boolean hasCustomPotion(NBTCompound nbtCompound, PotionEffectType effectType) {

        return base.hasCustomPotion(nbtCompound, effectType);
    }

    @Override
    public boolean hasCustomPotion(NBTCompound nbtCompound, com.minecraft.moonlake.enums.PotionEffectType effectType) {

        return base.hasCustomPotion(nbtCompound, effectType);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(int id) {

        return base.create(id);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(int id, int data) {

        return base.create(id, data);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(int id, int data, int amount) {

        return base.create(id, data, amount);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(int id, int data, int amount, String displayName) {

        return base.create(id, data, amount, displayName);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(int id, int data, int amount, String displayName, String... lore) {

        return base.create(id, data, amount, displayName, lore);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(String type) {

        return base.create(type);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(String type, int data) {

        return base.create(type, data);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(String type, int data, int amount) {

        return base.create(type, data);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(String type, int data, int amount, String displayName) {

        return base.create(type, data, amount, displayName);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(String type, int data, int amount, String displayName, String... lore) {

        return base.create(type, data, amount, displayName, lore);
    }

    @Override
    public ItemStack create(Material material) {

        return base.create(material);
    }

    @Override
    public ItemStack create(Material material, int data) {

        return base.create(material, data);
    }

    @Override
    public ItemStack create(Material material, int data, int amount) {

        return base.create(material, data, amount);
    }

    @Override
    public ItemStack create(Material material, int data, int amount, String displayName) {

        return base.create(material, data, amount, displayName);
    }

    @Override
    public ItemStack create(Material material, int data, int amount, String displayName, String... lore) {

        return base.create(material, data, amount, displayName, lore);
    }

    @Override
    public ItemStack createPotion(PotionType type, PotionBase base) {

        return this.base.createPotion(type, base);
    }

    @Override
    public ItemStack createPotion(PotionType type, PotionBase base, int amount) {

        return this.base.createPotion(type, base, amount);
    }

    @Override
    public ItemStack createPotion(PotionType type, PotionBase base, int amount, String displayName) {

        return this.base.createPotion(type, base, amount, displayName);
    }

    @Override
    public ItemStack createNormalPotion(PotionBase base) {

        return this.base.createNormalPotion(base);
    }

    @Override
    public ItemStack createNormalPotion(PotionBase base, int amount) {

        return this.base.createNormalPotion(base, amount);
    }

    @Override
    public ItemStack createNormalPotion(PotionBase base, int amount, String displayName) {

        return this.base.createNormalPotion(base, amount, displayName);
    }

    @Override
    public ItemStack createSplashPotion(PotionBase base) {

        return this.base.createSplashPotion(base);
    }

    @Override
    public ItemStack createSplashPotion(PotionBase base, int amount) {

        return this.base.createSplashPotion(base, amount);
    }

    @Override
    public ItemStack createSplashPotion(PotionBase base, int amount, String displayName) {

        return this.base.createSplashPotion(base, amount, displayName);
    }

    @Override
    public ItemStack createLingeringPotion(PotionBase base) {

        return this.base.createLingeringPotion(base);
    }

    @Override
    public ItemStack createLingeringPotion(PotionBase base, int amount) {

        return this.base.createLingeringPotion(base, amount);
    }

    @Override
    public ItemStack createLingeringPotion(PotionBase base, int amount, String displayName) {

        return this.base.createLingeringPotion(base, amount, displayName);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, PotionEffectCustom... effects) {

        return base.createCustomPotion(type, effects);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, PotionEffectCustom... effects) {

        return base.createCustomPotion(type, amount, effects);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectCustom... effects) {

        return base.createCustomPotion(type, amount, displayName, effects);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, Collection<? extends PotionEffectCustom> effects) {

        return base.createCustomPotion(type, effects);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, Collection<? extends PotionEffectCustom> effects) {

        return base.createCustomPotion(type, amount, effects);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, Collection<? extends PotionEffectCustom> effects) {

        return base.createCustomPotion(type, amount, displayName, effects);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, PotionEffectType effectType, int amplifier, int duration) {

        return base.createCustomPotion(type, effectType, amplifier, duration);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, PotionEffectType effectType, int amplifier, int duration) {

        return base.createCustomPotion(type, amount, effectType, amplifier, duration);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectType effectType, int amplifier, int duration) {

        return base.createCustomPotion(type, amount, displayName, effectType, amplifier, duration);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return base.createCustomPotion(type, effectType, amplifier, duration, ambient);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return base.createCustomPotion(type, amount, effectType, amplifier, duration, ambient);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return base.createCustomPotion(type, amount, displayName, effectType, amplifier, duration, ambient);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return base.createCustomPotion(type, effectType, amplifier, duration, ambient, showParticles);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return base.createCustomPotion(type, amount, effectType, amplifier, duration, ambient, showParticles);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return base.createCustomPotion(type, amount, displayName, effectType, amplifier, duration, ambient, showParticles);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration) {

        return base.createCustomPotion(type, effectType, amplifier, duration);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration) {

        return base.createCustomPotion(type, amount, effectType, amplifier, duration);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration) {

        return base.createCustomPotion(type, amount, displayName, effectType, amplifier, duration);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return base.createCustomPotion(type, effectType, amplifier, duration, ambient);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return base.createCustomPotion(type, amount, effectType, amplifier, duration, ambient);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return base.createCustomPotion(type, amount, displayName, effectType, amplifier, duration, ambient);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return base.createCustomPotion(type, effectType, amplifier, duration, ambient, showParticles);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return base.createCustomPotion(type, amount, effectType, amplifier, duration, ambient, showParticles);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return base.createCustomPotion(type, amount, displayName, effectType, amplifier, duration, ambient, showParticles);
    }

    @Override
    public ItemStack setDisplayName(ItemStack itemStack, String displayName) {

        return base.setDisplayName(itemStack, displayName);
    }

    @Override
    public ItemStack setLocalizedName(ItemStack itemStack, String localizedName) {

        return base.setLocalizedName(itemStack, localizedName);
    }

    @Override
    public String getLocalizedName(ItemStack itemStack) {

        return base.getLocalizedName(itemStack);
    }

    @Override
    public boolean hasLocalizedName(ItemStack itemStack) {

        return base.hasLocalizedName(itemStack);
    }

    @Override
    public ItemStack setAmount(ItemStack itemStack, int amount) {

        return base.setAmount(itemStack, amount);
    }

    @Override
    public ItemStack setDurability(ItemStack itemStack, int durability) {

        return base.setDurability(itemStack, durability);
    }

    @Override
    public int getDurability(ItemStack itemStack) {

        return base.getDurability(itemStack);
    }

    @Override
    public ItemStack resetDurability(ItemStack itemStack) {

        return base.resetDurability(itemStack);
    }

    @Override
    public ItemStack addDurability(ItemStack itemStack, int durability) {

        return base.addDurability(itemStack, durability);
    }

    @Override
    public ItemStack takeDurability(ItemStack itemStack, int durability) {

        return base.takeDurability(itemStack, durability);
    }

    @Override
    public List<String> getLore(ItemStack itemStack) {

        return base.getLore(itemStack);
    }

    @Override
    public List<String> getLore(ItemStack itemStack, boolean ignoreColor) {

        return base.getLore(itemStack, ignoreColor);
    }

    @Override
    public ItemStack setLore(ItemStack itemStack, String... lore) {

        return base.setLore(itemStack, lore);
    }

    @Override
    public ItemStack setLore(ItemStack itemStack, Collection<? extends String> lore) {

        return base.setLore(itemStack, lore);
    }

    @Override
    public ItemStack addLore(ItemStack itemStack, String... lore) {

        return base.addLore(itemStack, lore);
    }

    @Override
    public ItemStack addLore(ItemStack itemStack, Collection<? extends String> lore) {

        return base.addLore(itemStack, lore);
    }

    @Override
    public ItemStack clearLore(ItemStack itemStack) {

        return base.clearLore(itemStack);
    }

    @Override
    public boolean hasLore(ItemStack itemStack) {

        return base.hasLore(itemStack);
    }

    @Override
    public boolean containLore(ItemStack itemStack, String... lore) {

        return base.containLore(itemStack, lore);
    }

    @Override
    public boolean containLore(ItemStack itemStack, Collection<? extends String> lore) {

        return base.containLore(itemStack, lore);
    }

    @Override
    public boolean containLoreIgnoreColor(ItemStack itemStack, String... lore) {

        return base.containLoreIgnoreColor(itemStack, lore);
    }

    @Override
    public boolean containLoreIgnoreColor(ItemStack itemStack, Collection<? extends String> lore) {

        return base.containLoreIgnoreColor(itemStack, lore);
    }

    @Override
    public Map<Enchantment, Integer> getEnchantments(ItemStack itemStack) {

        return base.getEnchantments(itemStack);
    }

    @Override
    public ItemStack addEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {

        return base.addEnchantment(itemStack, enchantment, level);
    }

    @Override
    public ItemStack addEnchantment(ItemStack itemStack, Map<Enchantment, Integer> enchantmentMap) {

        return base.addEnchantment(itemStack, enchantmentMap);
    }

    @Override
    public ItemStack addSafeEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {

        return base.addSafeEnchantment(itemStack, enchantment, level);
    }

    @Override
    public ItemStack addSafeEnchantment(ItemStack itemStack, Map<Enchantment, Integer> enchantmentMap) {

        return base.addSafeEnchantment(itemStack, enchantmentMap);
    }

    @Override
    public ItemStack removeEnchantment(ItemStack itemStack, Enchantment enchantment) {

        return base.removeEnchantment(itemStack, enchantment);
    }

    @Override
    public ItemStack removeEnchantment(ItemStack itemStack, Collection<? extends Enchantment> enchantments) {

        return base.removeEnchantment(itemStack, enchantments);
    }

    @Override
    public ItemStack clearEnchantment(ItemStack itemStack) {

        return base.clearEnchantment(itemStack);
    }

    @Override
    public boolean hasEnchantment(ItemStack itemStack, Enchantment enchantment) {

        return base.hasEnchantment(itemStack, enchantment);
    }

    @Override
    public ItemStack addEnchantment(ItemStack itemStack, com.minecraft.moonlake.enums.Enchantment enchantment, int level) {

        return base.addEnchantment(itemStack, enchantment, level);
    }

    @Override
    public ItemStack addSafeEnchantment(ItemStack itemStack, com.minecraft.moonlake.enums.Enchantment enchantment, int level) {

        return base.addSafeEnchantment(itemStack, enchantment, level);
    }

    @Override
    public ItemStack removeEnchantment(ItemStack itemStack, com.minecraft.moonlake.enums.Enchantment enchantment) {

        return base.removeEnchantment(itemStack, enchantment);
    }

    @Override
    public boolean hasEnchantment(ItemStack itemStack, com.minecraft.moonlake.enums.Enchantment enchantment) {

        return base.hasEnchantment(itemStack, enchantment);
    }

    @Override
    public Set<ItemFlag> getFlags(ItemStack itemStack) {

        return base.getFlags(itemStack);
    }

    @Override
    public ItemStack addFlags(ItemStack itemStack, ItemFlag... flags) {

        return base.addFlags(itemStack, flags);
    }

    @Override
    public ItemStack addFlags(ItemStack itemStack, Collection<? extends ItemFlag> flags) {

        return base.addFlags(itemStack, flags);
    }

    @Override
    public ItemStack removeFlags(ItemStack itemStack, ItemFlag... flags) {

        return base.removeFlags(itemStack, flags);
    }

    @Override
    public ItemStack removeFlags(ItemStack itemStack, Collection<? extends ItemFlag> flags) {

        return base.removeFlags(itemStack, flags);
    }

    @Override
    public boolean hasFlags(ItemStack itemStack, ItemFlag... flags) {

        return base.hasFlags(itemStack, flags);
    }

    @Override
    public ItemStack clearFlags(ItemStack itemStack) {

        return base.clearFlags(itemStack);
    }

    @Override
    public ItemStack setLeatherColor(ItemStack itemStack, Color color) {

        return base.setLeatherColor(itemStack, color);
    }

    @Override
    public ItemStack setLeatherColor(ItemStack itemStack, int red, int green, int blue) {

        return base.setLeatherColor(itemStack, red, green, blue);
    }

    @Override
    public ItemStack setLeatherColorFromRandom(ItemStack itemStack) {

        return base.setLeatherColorFromRandom(itemStack);
    }
}
