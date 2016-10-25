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
import com.minecraft.moonlake.api.nbt.NBTFactory;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;

/**
 * <h1>CraftExpression</h1>
 * 物品栈创建接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class CraftExpression extends MetaExpression implements CraftLibrary {

    /**
     * 物品栈创建接口实现类构造函数
     */
    public CraftExpression() {

    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(int id) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(int id, int data) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material, data);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(int id, int data, int amount) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material, data, amount);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(int id, int data, int amount, String displayName) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material, data, amount, displayName);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(int id, int data, int amount, String displayName, String... lore) {

        Material material = Material.getMaterial(id);
        return material == null ? null : create(material, data, amount, displayName, lore);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(String type) {

        Validate.notNull(type, "The itemstack type object is null.");
        
        Material material = Material.getMaterial(type);
        return material == null ? null : create(material);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(String type, int data) {

        Validate.notNull(type, "The itemstack type object is null.");

        Material material = Material.getMaterial(type);
        return material == null ? null : create(material, data);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(String type, int data, int amount) {

        Validate.notNull(type, "The itemstack type object is null.");

        Material material = Material.getMaterial(type);
        return material == null ? null : create(material, data, amount);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack create(String type, int data, int amount, String displayName) {

        Validate.notNull(type, "The itemstack type object is null.");

        Material material = Material.getMaterial(type);
        return material == null ? null : create(material, data, amount, displayName);
    }

    @Override
    @SuppressWarnings("deprecation")
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

        itemStack = setDisplayName(itemStack, displayName);

        return itemStack;
    }

    @Override
    public ItemStack create(Material material, int data, int amount, String displayName, String... lore) {

        Validate.notNull(material, "The itemstack material object is null.");

        ItemStack itemStack = new ItemStack(material, amount, (short) data);

        itemStack = setDisplayName(itemStack, displayName);
        itemStack = setLore(itemStack, lore);

        return itemStack;
    }

    @Override
    public ItemStack createPotion(PotionType type, PotionBase base) {

        return createPotion(type, base, 1);
    }

    @Override
    public ItemStack createPotion(PotionType type, PotionBase base, int amount) {

        Validate.notNull(type, "The itemstack potion type object is null.");
        Validate.notNull(base, "The itemstack potion base object is null.");

        ItemStack itemStack = create(type.getMaterial(), 0, amount);

        NBTCompound nbtCompound = NBTFactory.get().readSafe(itemStack);
        nbtCompound.put("Potion", base.getValue());

        NBTFactory.get().write(itemStack, nbtCompound);

        return itemStack;
    }

    @Override
    public ItemStack createPotion(PotionType type, PotionBase base, int amount, String displayName) {

        ItemStack itemStack = createPotion(type, base, amount);

        itemStack = setDisplayName(itemStack, displayName);

        return itemStack;
    }

    @Override
    public ItemStack createNormalPotion(PotionBase base) {

        return createPotion(PotionType.POTION, base);
    }

    @Override
    public ItemStack createNormalPotion(PotionBase base, int amount) {

        return createPotion(PotionType.POTION, base, amount);
    }

    @Override
    public ItemStack createNormalPotion(PotionBase base, int amount, String displayName) {

        return createPotion(PotionType.POTION, base, amount, displayName);
    }

    @Override
    public ItemStack createSplashPotion(PotionBase base) {

        return createPotion(PotionType.SPLASH_POTION, base);
    }

    @Override
    public ItemStack createSplashPotion(PotionBase base, int amount) {

        return createPotion(PotionType.SPLASH_POTION, base, amount);
    }

    @Override
    public ItemStack createSplashPotion(PotionBase base, int amount, String displayName) {

        return createPotion(PotionType.SPLASH_POTION, base, amount, displayName);
    }

    @Override
    public ItemStack createLingeringPotion(PotionBase base) {

        return createPotion(PotionType.LINGERING_POTION, base);
    }

    @Override
    public ItemStack createLingeringPotion(PotionBase base, int amount) {

        return createPotion(PotionType.LINGERING_POTION, base, amount);
    }

    @Override
    public ItemStack createLingeringPotion(PotionBase base, int amount, String displayName) {

        return createPotion(PotionType.LINGERING_POTION, base, amount, displayName);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, PotionEffectCustom... effects) {

        Validate.notNull(effects, "The itemstack potion effect object is null.");

        return createCustomPotion(type, Arrays.asList(effects));
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, PotionEffectCustom... effects) {

        Validate.notNull(effects, "The itemstack potion effect object is null.");

        return createCustomPotion(type, amount, Arrays.asList(effects));
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectCustom... effects) {

        Validate.notNull(effects, "The itemstack potion effect object is null.");

        return createCustomPotion(type, amount, displayName, Arrays.asList(effects));
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, Collection<? extends PotionEffectCustom> effects) {

        return createCustomPotion(type, 1, effects);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, Collection<? extends PotionEffectCustom> effects) {

        Validate.notNull(type, "The itemstack potion type object is null.");

        ItemStack itemStack = create(type.getMaterial());

        itemStack = setAmount(itemStack, amount);
        itemStack = setCustomPotion(itemStack, effects);

        return itemStack;
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, Collection<? extends PotionEffectCustom> effects) {

        ItemStack itemStack = createCustomPotion(type, amount, effects);

        itemStack = setDisplayName(itemStack, displayName);

        return itemStack;
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, PotionEffectType effectType, int amplifier, int duration) {

        return createCustomPotion(type, 1, effectType, amplifier, duration);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, PotionEffectType effectType, int amplifier, int duration) {

        return createCustomPotion(type, amount, effectType, amplifier, duration, false);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectType effectType, int amplifier, int duration) {

        return createCustomPotion(type, amount, displayName, effectType, amplifier, duration, false);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return createCustomPotion(type, 1, effectType, amplifier, duration, ambient);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return createCustomPotion(type, amount, effectType, amplifier, duration, ambient, false);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return createCustomPotion(type, amount, displayName, effectType, amplifier, duration, ambient, false);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return createCustomPotion(type, 1, effectType, amplifier, duration, ambient, showParticles);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        Validate.notNull(effectType, "The itemstack potion effect type object is null.");

        return createCustomPotion(type, amount, new PotionEffectCustom(effectType, amplifier, duration, ambient, showParticles));
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        Validate.notNull(effectType, "The itemstack potion effect type object is null.");

        return createCustomPotion(type, amount, displayName, new PotionEffectCustom(effectType, amplifier, duration, ambient, showParticles));
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration) {

        return createCustomPotion(type, effectType, amplifier, duration, false);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration) {

        return createCustomPotion(type, amount, effectType, amplifier, duration, false);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration) {

        return createCustomPotion(type, amount, displayName, effectType, amplifier, duration, false);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return createCustomPotion(type, effectType, amplifier, duration, ambient, false);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return createCustomPotion(type, amount, effectType, amplifier, duration, ambient, false);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return createCustomPotion(type, amount, displayName, effectType, amplifier, duration, ambient, false);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        return createCustomPotion(type, 1, effectType, amplifier, duration, ambient, showParticles);
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        Validate.notNull(effectType, "The itemstack potion effect type object is null.");

        return createCustomPotion(type, amount, effectType.createCustom(amplifier, duration, ambient, showParticles));
    }

    @Override
    public ItemStack createCustomPotion(PotionType type, int amount, String displayName, com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        Validate.notNull(effectType, "The itemstack potion effect type object is null.");

        return createCustomPotion(type, amount, displayName, effectType.createCustom(amplifier, duration, ambient, showParticles));
    }
}
