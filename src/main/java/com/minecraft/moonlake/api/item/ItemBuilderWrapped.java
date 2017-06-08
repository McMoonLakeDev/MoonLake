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

import com.minecraft.moonlake.api.item.potion.PotionEffectCustom;
import com.minecraft.moonlake.api.item.potion.PotionEffectType;
import com.minecraft.moonlake.api.item.potion.PotionType;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;

/**
 * <h1>ItemBuilderWrapped</h1>
 * 物品栈建造接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class ItemBuilderWrapped implements ItemBuilder {

    private final ItemLibrary itemLibrary;
    private ItemStack itemStack;

    /**
     * 物品栈建造接口实现类构造函数
     *
     * @param itemStack 物品栈对象
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    public ItemBuilderWrapped(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        this.itemStack = itemStack;
        this.itemLibrary = ItemLibraryFactorys.item();
    }

    /**
     * 物品栈建造接口实现类构造函数
     *
     * @param material 物品栈类型
     */
    public ItemBuilderWrapped(Material material) {

        this(ItemLibraryFactorys.craft().create(material));
    }

    /**
     * 物品栈建造接口实现类构造函数
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     */
    public ItemBuilderWrapped(Material material, int data) {

        this(ItemLibraryFactorys.craft().create(material, data));
    }

    /**
     * 物品栈建造接口实现类构造函数
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     */
    public ItemBuilderWrapped(Material material, int data, int amount) {

        this(ItemLibraryFactorys.craft().create(material, data, amount));
    }

    /**
     * 物品栈建造接口实现类构造函数
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     */
    public ItemBuilderWrapped(Material material, int data, int amount, String displayName) {

        this(ItemLibraryFactorys.craft().create(material, data, amount, displayName));
    }

    /**
     * 物品栈建造接口实现类构造函数
     *
     * @param material 物品栈类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param lore 物品栈标签
     */
    public ItemBuilderWrapped(Material material, int data, int amount, String displayName, String... lore) {

        this(ItemLibraryFactorys.craft().create(material, data, amount, displayName, lore));
    }

    /**
     * 物品栈建造接口实现类构造函数
     *
     * @param potion 药水类型
     * @throws IllegalBukkitVersionException 如果服务端不支持药水类型则抛出异常
     */
    public ItemBuilderWrapped(PotionType potion) {

        this(potion.getMaterial());
    }

    /**
     * 物品栈建造接口实现类构造函数
     *
     * @param potion 药水类型
     * @param data 物品栈数据
     * @throws IllegalBukkitVersionException 如果服务端不支持药水类型则抛出异常
     */
    public ItemBuilderWrapped(PotionType potion, int data) {

        this(potion.getMaterial(), data);
    }

    /**
     * 物品栈建造接口实现类构造函数
     *
     * @param potion 药水类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @throws IllegalBukkitVersionException 如果服务端不支持药水类型则抛出异常
     */
    public ItemBuilderWrapped(PotionType potion, int data, int amount) {

        this(potion.getMaterial(), data, amount);
    }

    /**
     * 物品栈建造接口实现类构造函数
     *
     * @param potion 药水类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @throws IllegalBukkitVersionException 如果服务端不支持药水类型则抛出异常
     */
    public ItemBuilderWrapped(PotionType potion, int data, int amount, String displayName) {

        this(potion.getMaterial(), data, amount, displayName);
    }

    /**
     * 物品栈建造接口实现类构造函数
     *
     * @param potion 药水类型
     * @param data 物品栈数据
     * @param amount 物品栈数量
     * @param displayName 物品栈显示名称
     * @param lore 物品栈标签
     * @throws IllegalBukkitVersionException 如果服务端不支持药水类型则抛出异常
     */
    public ItemBuilderWrapped(PotionType potion, int data, int amount, String displayName, String... lore) {

        this(potion.getMaterial(), data, amount, displayName, lore);
    }

    protected void update(ItemStack itemStack) {

        this.itemStack = itemStack;
    }
    
    protected ItemStack get() {
        
        return this.itemStack;
    }
    
    protected ItemLibrary library() {
        
        return this.itemLibrary;
    }

    @Override
    public ItemBuilder setDisplayName(String displayName) {
        
        update(library().setDisplayName(get(), displayName));
        
        return this;
    }

    @Override
    public ItemBuilder setLocalizedName(String localizedName) {

        update(library().setLocalizedName(get(), localizedName));

        return this;
    }

    @Override
    public ItemBuilder setAmount(int amount) {

        update(library().setAmount(get(), amount));

        return this;
    }

    @Override
    public ItemBuilder setDurability(int durability) {

        update(library().setDurability(get(), durability));

        return this;
    }

    @Override
    public ItemBuilder resetDurability() {

        update(library().resetDurability(get()));

        return this;
    }

    @Override
    public ItemBuilder addDurability(int durability) {

        update(library().addDurability(get(), durability));

        return this;
    }

    @Override
    public ItemBuilder takeDurability(int durability) {

        update(library().takeDurability(get(), durability));

        return this;
    }

    @Override
    public ItemBuilder setLore(String... lore) {

        update(library().setLore(get(), lore));

        return this;
    }

    @Override
    public ItemBuilder setLore(Collection<? extends String> lore) {

        update(library().setLore(get(), lore));

        return this;
    }

    @Override
    public ItemBuilder addLore(String... lore) {

        update(library().addLore(get(), lore));

        return this;
    }

    @Override
    public ItemBuilder addLore(Collection<? extends String> lore) {

        update(library().addLore(get(), lore));

        return this;
    }

    @Override
    public ItemBuilder clearLore() {

        update(library().clearLore(get()));

        return this;
    }

    @Override
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {

        update(library().addEnchantment(get(), enchantment, level));

        return this;
    }

    @Override
    public ItemBuilder addEnchantment(Map<Enchantment, Integer> enchantmentMap) {

        update(library().addEnchantment(get(), enchantmentMap));

        return this;
    }

    @Override
    public ItemBuilder addSafeEnchantment(Enchantment enchantment, int level) {

        update(library().addSafeEnchantment(get(), enchantment, level));

        return this;
    }

    @Override
    public ItemBuilder addSafeEnchantment(Map<Enchantment, Integer> enchantmentMap) {

        update(library().addSafeEnchantment(get(), enchantmentMap));

        return this;
    }

    @Override
    public ItemBuilder removeEnchantment(Enchantment enchantment) {

        update(library().removeEnchantment(get(), enchantment));

        return this;
    }

    @Override
    public ItemBuilder removeEnchantment(Collection<? extends Enchantment> enchantments) {

        update(library().removeEnchantment(get(), enchantments));

        return this;
    }

    @Override
    public ItemBuilder clearEnchantment() {

        update(library().clearEnchantment(get()));

        return this;
    }

    @Override
    public ItemBuilder addEnchantment(com.minecraft.moonlake.enums.Enchantment enchantment, int level) {

        Validate.notNull(enchantment, "The itemstack enchantment object is null.");

        return addEnchantment(enchantment.as(), level);
    }

    @Override
    public ItemBuilder addSafeEnchantment(com.minecraft.moonlake.enums.Enchantment enchantment, int level) {

        Validate.notNull(enchantment, "The itemstack enchantment object is null.");

        return addSafeEnchantment(enchantment.as(), level);
    }

    @Override
    public ItemBuilder removeEnchantment(com.minecraft.moonlake.enums.Enchantment enchantment) {

        Validate.notNull(enchantment, "The itemstack enchantment object is null.");

        return removeEnchantment(enchantment.as());
    }

    @Override
    public ItemBuilder addFlags(ItemFlag... flags) {

        update(library().addFlags(get(), flags));

        return this;
    }

    @Override
    public ItemBuilder addFlags(Collection<? extends ItemFlag> flags) {

        update(library().addFlags(get(), flags));

        return this;
    }

    @Override
    public ItemBuilder removeFlags(ItemFlag... flags) {

        update(library().removeFlags(get(), flags));

        return this;
    }

    @Override
    public ItemBuilder removeFlags(Collection<? extends ItemFlag> flags) {

        update(library().removeFlags(get(), flags));

        return this;
    }

    @Override
    public ItemBuilder clearFlags() {

        update(library().clearFlags(get()));

        return this;
    }

    @Override
    public ItemBuilder setLeatherColor(Color color) {

        update(library().setLeatherColor(get(), color));

        return this;
    }

    @Override
    public ItemBuilder setLeatherColor(int red, int green, int blue) {

        update(library().setLeatherColor(get(), red, green, blue));

        return this;
    }

    @Override
    public ItemBuilder setLeatherColorFromRandom() {

        update(library().setLeatherColorFromRandom(get()));

        return this;
    }

    @Override
    public ItemBuilder setUnbreakable(boolean unbreakable) {

        update(library().setUnbreakable(get(), unbreakable));

        return this;
    }

    @Override
    public ItemBuilder setAttribute(AttributeModify.Type type, AttributeModify.Operation operation, double amount) {

        update(library().setAttribute(get(), type, operation, amount));

        return this;
    }

    @Override
    public ItemBuilder setAttribute(AttributeModify.Type type, AttributeModify.Slot slot, AttributeModify.Operation operation, double amount) {

        update(library().setAttribute(get(), type, slot, operation, amount));

        return this;
    }

    @Override
    public ItemBuilder setAttribute(AttributeModify attribute) {

        update(library().setAttribute(get(), attribute));

        return this;
    }

    @Override
    public ItemBuilder setCustomPotion(PotionEffectCustom... effect) {

        update(library().setCustomPotion(get(), effect));

        return this;
    }

    @Override
    public ItemBuilder setCustomPotion(Collection<? extends PotionEffectCustom> effect) {

        update(library().setCustomPotion(get(), effect));

        return this;
    }

    @Override
    public ItemBuilder setCustomPotion(PotionEffectType effectType, int amplifier, int duration) {

        update(library().setCustomPotion(get(), effectType, amplifier, duration));

        return this;
    }

    @Override
    public ItemBuilder setCustomPotion(PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        update(library().setCustomPotion(get(), effectType, amplifier, duration, ambient));

        return this;
    }

    @Override
    public ItemBuilder setCustomPotion(PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        update(library().setCustomPotion(get(), effectType, amplifier, duration, ambient, showParticles));

        return this;
    }

    @Override
    public ItemBuilder setCustomPotion(com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration) {

        return setCustomPotion(effectType, amplifier, duration, false);
    }

    @Override
    public ItemBuilder setCustomPotion(com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient) {

        return setCustomPotion(effectType, amplifier, duration, ambient, false);
    }

    @Override
    public ItemBuilder setCustomPotion(com.minecraft.moonlake.enums.PotionEffectType effectType, int amplifier, int duration, boolean ambient, boolean showParticles) {

        Validate.notNull(effectType, "The itemstack potion effect object is null.");

        return setCustomPotion(effectType.createCustom(amplifier, duration, ambient, showParticles));
    }

    @Override
    public ItemBuilder setSkullWithSkin(String data) {

        update(library().setSkullWithSkin(get(), data));

        return this;
    }

    @Override
    public ItemBuilder setSkullWithSkin(String value, String signature) {

        update(library().setSkullWithSkin(get(), value, signature));

        return this;
    }

    @Override
    public ItemStack build() {

        return get().clone();
    }

    @Override
    public ItemStack build(boolean safeObj) {

        return safeObj ? get().clone() : get();
    }
}
