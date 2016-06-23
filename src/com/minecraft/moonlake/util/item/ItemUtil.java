package com.minecraft.moonlake.util.item;

import com.minecraft.moonlake.api.itemlib.AttributeStack;
import com.minecraft.moonlake.api.itemlib.Itemlib;
import com.minecraft.moonlake.api.potionlib.CustomPotionEffect;
import com.minecraft.moonlake.exception.item.NotArmorItemException;
import com.minecraft.moonlake.exception.item.NotBookItemException;
import com.minecraft.moonlake.exception.item.NotPotionItemException;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.type.potion.PotionEnum;
import com.minecraft.moonlake.util.Util;
import com.minecraft.moonlake.util.lore.LoreUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

/**
 * Created by MoonLake on 2016/4/26.
 * @version 1.2
 * @author Month_Light
 */
public class ItemUtil extends LoreUtil implements Itemlib {

    static {}

    public ItemUtil() {

    }

    /**
     * 设置物品栈的名称
     *
     * @param item 物品栈
     * @param name 名称
     * @return ItemStack
     */
    @Override
    public ItemStack setName(ItemStack item, String name) {

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Util.color(name));
        item.setItemMeta(meta);
        return item;
    }

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @return ItemStack
     */
    @Override
    public ItemStack create(int id) {
        return new ItemStack(id);
    }

    /**
     * 创建物品栈对象
     *
     * @param id   物品ID
     * @param data 物品副ID
     * @return ItemStack
     */
    @Override
    public ItemStack create(int id, int data) {
        return new ItemStack(id, data);
    }

    /**
     * 创建物品栈对象
     *
     * @param id     物品ID
     * @param data   物品副ID
     * @param amount 物品数量
     * @return ItemStack
     */
    @Override
    public ItemStack create(int id, int data, int amount) {
        return new ItemStack(id, amount, (byte)data);
    }

    /**
     * 创建物品栈对象
     *
     * @param id     物品ID
     * @param data   物品副ID
     * @param amount 物品数量
     * @param name   物品名称
     * @return ItemStack
     */
    @Override
    public ItemStack create(int id, int data, int amount, String name) {
        ItemStack item = create(id, data, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Util.color(name));
        item.setItemMeta(meta);;
        return item;
    }

    /**
     * 创建物品栈对象
     *
     * @param id     物品ID
     * @param data   物品副ID
     * @param amount 物品数量
     * @param name   物品名称
     * @param lore   物品标签
     * @return ItemStack
     */
    @Override
    public ItemStack create(int id, int data, int amount, String name, String... lore) {
        return setLore(create(id, data, amount, name), lore);
    }

    /**
     * 创建物品栈对象
     *
     * @param id     物品ID
     * @param data   物品副ID
     * @param amount 物品数量
     * @param name   物品名称
     * @param lore   物品标签
     * @return ItemStack
     */
    @Override
    public ItemStack create(int id, int data, int amount, String name, List<String> lore) {
        return setLore(create(id, data, amount, name), lore);
    }

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @return ItemStack
     */
    @Override
    public ItemStack create(String id) {
        return create(Material.matchMaterial(id));
    }

    /**
     * 创建物品栈对象
     *
     * @param id   物品ID
     * @param data 物品副ID
     * @return ItemStack
     */
    @Override
    public ItemStack create(String id, int data) {
        return create(Material.matchMaterial(id), data);
    }

    /**
     * 创建物品栈对象
     *
     * @param id     物品ID
     * @param data   物品副ID
     * @param amount 物品数量
     * @return ItemStack
     */
    @Override
    public ItemStack create(String id, int data, int amount) {
        return create(Material.matchMaterial(id), data, amount);
    }

    /**
     * 创建物品栈对象
     *
     * @param id     物品ID
     * @param data   物品副ID
     * @param amount 物品数量
     * @param name   物品名称
     * @return ItemStack
     */
    @Override
    public ItemStack create(String id, int data, int amount, String name) {
        return create(Material.matchMaterial(id), data, amount, name);
    }

    /**
     * 创建物品栈对象
     *
     * @param id     物品ID
     * @param data   物品副ID
     * @param amount 物品数量
     * @param name   物品名称
     * @param lore   物品标签
     * @return ItemStack
     */
    @Override
    public ItemStack create(String id, int data, int amount, String name, String... lore) {
        return create(Material.matchMaterial(id), data, amount, name, lore);
    }

    /**
     * 创建物品栈对象
     *
     * @param id     物品ID
     * @param data   物品副ID
     * @param amount 物品数量
     * @param name   物品名称
     * @param lore   物品标签
     * @return ItemStack
     */
    @Override
    public ItemStack create(String id, int data, int amount, String name, List<String> lore) {
        return create(Material.matchMaterial(id), data, amount, name, lore);
    }

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @return ItemStack
     */
    @Override
    public ItemStack create(Material id) {
        Util.notNull(id, "待创建的物品栈类型是 null 值");

        return new ItemStack(id);
    }

    /**
     * 创建物品栈对象
     *
     * @param id   物品ID
     * @param data 物品副ID
     * @return ItemStack
     */
    @Override
    public ItemStack create(Material id, int data) {
        return new ItemStack(id, data);
    }

    /**
     * 创建物品栈对象
     *
     * @param id     物品ID
     * @param data   物品副ID
     * @param amount 物品数量
     * @return ItemStack
     */
    @Override
    public ItemStack create(Material id, int data, int amount) {
        return new ItemStack(id, amount, (byte)data);
    }

    /**
     * 创建物品栈对象
     *
     * @param id     物品ID
     * @param data   物品副ID
     * @param amount 物品数量
     * @param name   物品名称
     * @return ItemStack
     */
    @Override
    public ItemStack create(Material id, int data, int amount, String name) {
        ItemStack item = create(id, data, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Util.color(name));
        item.setItemMeta(meta);
        return item;
    }

    /**
     * 创建物品栈对象
     *
     * @param id     物品ID
     * @param data   物品副ID
     * @param amount 物品数量
     * @param name   物品名称
     * @param lore   物品标签
     * @return ItemStack
     */
    @Override
    public ItemStack create(Material id, int data, int amount, String name, String... lore) {
        return setLore(create(id, data, amount, name), lore);
    }

    /**
     * 创建物品栈对象
     *
     * @param id     物品ID
     * @param data   物品副ID
     * @param amount 物品数量
     * @param name   物品名称
     * @param lore   物品标签
     * @return ItemStack
     */
    @Override
    public ItemStack create(Material id, int data, int amount, String name, List<String> lore) {
        return setLore(create(id, data, amount, name), lore);
    }

    /**
     * 创建默认药水物品栈对象
     *
     * @param potion       药水类型
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createPotion(PotionEnum potion, String potionEffect) {
        return createPotion(potion, potionEffect, 1);
    }

    /**
     * 创建默认药水物品栈对象
     *
     * @param potion       药水类型
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount       数量
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createPotion(PotionEnum potion, String potionEffect, int amount) {
        Util.notNull(potion, "待创建的药水物品栈的类型是 null 值");
        Util.notNull(potionEffect, "待创建的药水物品栈的效果类型是 null 值");

        ItemStack item = create(potion.getMaterial(), 0, amount);

        try {

            Class<?> ItemStack = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            Class<?> CraftItemStack = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
            Class<?> NBTTagCompound = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");

            Object NMSItemStack = Reflect.getMethod(CraftItemStack, "asNMSCopy", ItemStack.class).invoke(null, item);
            Object tag = Reflect.getMethod(ItemStack, "getTag").invoke(NMSItemStack);

            if(tag == null) {

                tag = Reflect.instantiateObject(NBTTagCompound);
            }
            Reflect.getMethod(NBTTagCompound, "setString", String.class, String.class).invoke(tag, "Potion", potionEffect);
            Reflect.getMethod(ItemStack, "setTag", NBTTagCompound).invoke(NMSItemStack, tag);

            return (ItemStack) Reflect.getMethod(CraftItemStack, "asBukkitCopy", ItemStack).invoke(null, NMSItemStack);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建默认药水物品栈对象
     *
     * @param potion       药水类型
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount       药水数量
     * @param name         药水名称
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createPotion(PotionEnum potion, String potionEffect, int amount, String name) {
        Util.notNull(name, "待创建的药水物品栈的名称是 null 值");

        ItemStack item = createPotion(potion, potionEffect, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Util.color(name));
        item.setItemMeta(meta);
        return item;
    }

    /**
     * 创建默认基础药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createBasePotion(String potionEffect) {
        return createPotion(PotionEnum.POTION, potionEffect);
    }

    /**
     * 创建默认基础药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount       药水数量
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createBasePotion(String potionEffect, int amount) {
        return createPotion(PotionEnum.POTION, potionEffect, amount);
    }

    /**
     * 创建默认基础药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount       药水数量
     * @param name         药水名称
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createBasePotion(String potionEffect, int amount, String name) {
        return createPotion(PotionEnum.POTION, potionEffect, amount, name);
    }

    /**
     * 创建默认投掷药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createSplashPotion(String potionEffect) {
        return createPotion(PotionEnum.SPLASH_POTION, potionEffect);
    }

    /**
     * 创建默认投掷药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount       药水数量
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createSplashPotion(String potionEffect, int amount) {
        return createPotion(PotionEnum.SPLASH_POTION, potionEffect, amount);
    }

    /**
     * 创建默认投掷药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount       药水数量
     * @param name         药水名称
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createSplashPotion(String potionEffect, int amount, String name) {
        return createPotion(PotionEnum.SPLASH_POTION, potionEffect, amount, name);
    }

    /**
     * 创建默认滞留药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createLingeringPotion(String potionEffect) {
        return createPotion(PotionEnum.LINGERING_POTION, potionEffect);
    }

    /**
     * 创建默认滞留药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount       药水数量
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createLingeringPotion(String potionEffect, int amount) {
        return createPotion(PotionEnum.LINGERING_POTION, potionEffect, amount);
    }

    /**
     * 创建默认滞留药水物品栈对象
     *
     * @param potionEffect 药水效果 (PotionEffectEnum)
     * @param amount       药水数量
     * @param name         药水名称
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createLingeringPotion(String potionEffect, int amount, String name) {
        return createPotion(PotionEnum.LINGERING_POTION, potionEffect, amount, name);
    }

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion             药水类型
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomPotion(PotionEnum potion, CustomPotionEffect... customPotionEffect) {
        Util.notNull(potion, "待创建的药水物品栈的类型是 null 值");
        Util.notNull(customPotionEffect, "待创建的药水物品栈的自定义效果是 null 值");

        return addCustomPotion(create(potion.getMaterial()), customPotionEffect);
    }

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion    药水类型
     * @param id        药水ID
     * @param amplifier 药水等级
     * @param duration  药水时间
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomPotion(PotionEnum potion, int id, int amplifier, int duration) {
        return createCustomPotion(potion, new CustomPotionEffect(id, amplifier, duration));
    }

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion        药水类型
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomPotion(PotionEnum potion, int id, int amplifier, int duration, boolean showParticles) {
        return createCustomPotion(potion, new CustomPotionEffect(id, amplifier, duration, showParticles));
    }

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion        药水类型
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param ambient       是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomPotion(PotionEnum potion, int id, int amplifier, int duration, boolean ambient, boolean showParticles) {
        return createCustomPotion(potion, new CustomPotionEffect(id, amplifier, duration, ambient, showParticles));
    }

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion             药水类型
     * @param amount             药水数量
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomPotion(PotionEnum potion, int amount, CustomPotionEffect... customPotionEffect) {
        Util.notNull(potion, "待创建的药水物品栈的类型是 null 值");
        Util.notNull(customPotionEffect, "待创建的药水物品栈的自定义效果是 null 值");

        return addCustomPotion(create(potion.getMaterial(), 0, amount), customPotionEffect);
    }

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion    药水类型
     * @param amount    药水数量
     * @param id        药水ID
     * @param amplifier 药水等级
     * @param duration  药水时间
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomPotion(PotionEnum potion, int amount, int id, int amplifier, int duration) {
        return createCustomPotion(potion, amount, new CustomPotionEffect(id, amplifier, duration));
    }

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion        药水类型
     * @param amount        药水数量
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomPotion(PotionEnum potion, int amount, int id, int amplifier, int duration, boolean showParticles) {
        return createCustomPotion(potion, amount, new CustomPotionEffect(id, amplifier, duration, showParticles));
    }

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion        药水类型
     * @param amount        药水数量
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param ambient       是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomPotion(PotionEnum potion, int amount, int id, int amplifier, int duration, boolean ambient, boolean showParticles) {
        return createCustomPotion(potion, amount, new CustomPotionEffect(id, amplifier, duration, ambient, showParticles));
    }

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion             药水类型
     * @param amount             药水数量
     * @param name               药水名称
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomPotion(PotionEnum potion, int amount, String name, CustomPotionEffect... customPotionEffect) {
        Util.notNull(potion, "待创建的药水物品栈的类型是 null 值");
        Util.notNull(customPotionEffect, "待创建的药水物品栈的自定义效果是 null 值");

        return addCustomPotion(create(potion.getMaterial(), 0, amount, name), customPotionEffect);
    }

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion    药水类型
     * @param amount    药水数量
     * @param name      药水名称
     * @param id        药水ID
     * @param amplifier 药水等级
     * @param duration  药水时间
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomPotion(PotionEnum potion, int amount, String name, int id, int amplifier, int duration) {
        return createCustomPotion(potion, amount, name, new CustomPotionEffect(id, amplifier, duration));
    }

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion        药水类型
     * @param amount        药水数量
     * @param name          药水名称
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomPotion(PotionEnum potion, int amount, String name, int id, int amplifier, int duration, boolean showParticles) {
        return createCustomPotion(potion, amount, name, new CustomPotionEffect(id, amplifier, duration, showParticles));
    }

    /**
     * 创建自定义的药水物品栈对象
     *
     * @param potion        药水类型
     * @param amount        药水数量
     * @param name          药水名称
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param ambient       是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomPotion(PotionEnum potion, int amount, String name, int id, int amplifier, int duration, boolean ambient, boolean showParticles) {
        return createCustomPotion(potion, amount, name, new CustomPotionEffect(id, amplifier, duration, ambient, showParticles));
    }

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomBasePotion(CustomPotionEffect... customPotionEffect) {
        return createCustomPotion(PotionEnum.POTION, customPotionEffect);
    }

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount             药水数量
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomBasePotion(int amount, CustomPotionEffect... customPotionEffect) {
        return createCustomPotion(PotionEnum.POTION, amount, customPotionEffect);
    }

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount    药水数量
     * @param id        药水ID
     * @param amplifier 药水等级
     * @param duration  药水时间
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomBasePotion(int amount, int id, int amplifier, int duration) {
        return createCustomPotion(PotionEnum.POTION, new CustomPotionEffect(id, amplifier, duration));
    }

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount        药水数量
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomBasePotion(int amount, int id, int amplifier, int duration, boolean showParticles) {
        return createCustomPotion(PotionEnum.POTION, new CustomPotionEffect(id, amplifier, duration, showParticles));
    }

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount        药水数量
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param ambient       是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomBasePotion(int amount, int id, int amplifier, int duration, boolean ambient, boolean showParticles) {
        return createCustomPotion(PotionEnum.POTION, new CustomPotionEffect(id, amplifier, duration, ambient, showParticles));
    }

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount             药水数量
     * @param name               药水名称
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomBasePotion(int amount, String name, CustomPotionEffect... customPotionEffect) {
        return createCustomPotion(PotionEnum.POTION, amount, name, customPotionEffect);
    }

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount    药水数量
     * @param name      药水名称
     * @param id        药水ID
     * @param amplifier 药水等级
     * @param duration  药水时间
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomBasePotion(int amount, String name, int id, int amplifier, int duration) {
        return createCustomPotion(PotionEnum.POTION, amount, name, new CustomPotionEffect(id, amplifier, duration));
    }

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount        药水数量
     * @param name          药水名称
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomBasePotion(int amount, String name, int id, int amplifier, int duration, boolean showParticles) {
        return createCustomPotion(PotionEnum.POTION, amount, name, new CustomPotionEffect(id, amplifier, duration, showParticles));
    }

    /**
     * 创建自定义的基础药水物品栈对象
     *
     * @param amount        药水数量
     * @param name          药水名称
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param ambient       是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomBasePotion(int amount, String name, int id, int amplifier, int duration, boolean ambient, boolean showParticles) {
        return createCustomPotion(PotionEnum.POTION, amount, name, new CustomPotionEffect(id, amplifier, duration, ambient, showParticles));
    }

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomSplashPotion(CustomPotionEffect... customPotionEffect) {
        return createCustomPotion(PotionEnum.SPLASH_POTION, customPotionEffect);
    }

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount             药水数量
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomSplashPotion(int amount, CustomPotionEffect... customPotionEffect) {
        return createCustomPotion(PotionEnum.SPLASH_POTION, amount, customPotionEffect);
    }

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount    药水数量
     * @param id        药水ID
     * @param amplifier 药水等级
     * @param duration  药水时间
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomSplashPotion(int amount, int id, int amplifier, int duration) {
        return createCustomPotion(PotionEnum.SPLASH_POTION, amount, new CustomPotionEffect(id, amplifier, duration));
    }

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount        药水数量
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomSplashPotion(int amount, int id, int amplifier, int duration, boolean showParticles) {
        return createCustomPotion(PotionEnum.SPLASH_POTION, amount, new CustomPotionEffect(id, amplifier, duration, showParticles));
    }

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount        药水数量
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param ambient       是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomSplashPotion(int amount, int id, int amplifier, int duration, boolean ambient, boolean showParticles) {
        return createCustomPotion(PotionEnum.SPLASH_POTION, amount, new CustomPotionEffect(id, amplifier, duration, ambient, showParticles));
    }

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount             药水数量
     * @param name               药水名称
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomSplashPotion(int amount, String name, CustomPotionEffect... customPotionEffect) {
        return createCustomPotion(PotionEnum.SPLASH_POTION, amount, name, customPotionEffect);
    }

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount    药水数量
     * @param name      药水名称
     * @param id        药水ID
     * @param amplifier 药水等级
     * @param duration  药水时间
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomSplashPotion(int amount, String name, int id, int amplifier, int duration) {
        return createCustomPotion(PotionEnum.SPLASH_POTION, amount, name, new CustomPotionEffect(id, amplifier, duration));
    }

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount        药水数量
     * @param name          药水名称
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomSplashPotion(int amount, String name, int id, int amplifier, int duration, boolean showParticles) {
        return createCustomPotion(PotionEnum.SPLASH_POTION, amount, name, new CustomPotionEffect(id, amplifier, duration, showParticles));
    }

    /**
     * 创建自定义的投掷药水物品栈对象
     *
     * @param amount        药水数量
     * @param name          药水名称
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param ambient       是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomSplashPotion(int amount, String name, int id, int amplifier, int duration, boolean ambient, boolean showParticles) {
        return createCustomPotion(PotionEnum.SPLASH_POTION, amount, name, new CustomPotionEffect(id, amplifier, duration, ambient, showParticles));
    }

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomLingeringPotion(CustomPotionEffect... customPotionEffect) {
        return createCustomPotion(PotionEnum.LINGERING_POTION, customPotionEffect);
    }

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount             药水数量
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomLingeringPotion(int amount, CustomPotionEffect... customPotionEffect) {
        return createCustomPotion(PotionEnum.LINGERING_POTION, amount, customPotionEffect);
    }

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount    药水数量
     * @param id        药水ID
     * @param amplifier 药水等级
     * @param duration  药水时间
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomLingeringPotion(int amount, int id, int amplifier, int duration) {
        return createCustomPotion(PotionEnum.LINGERING_POTION, amount, new CustomPotionEffect(id, amplifier, duration));
    }

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount        药水数量
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomLingeringPotion(int amount, int id, int amplifier, int duration, boolean showParticles) {
        return createCustomPotion(PotionEnum.LINGERING_POTION, amount, new CustomPotionEffect(id, amplifier, duration, showParticles));
    }

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount        药水数量
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param ambient       是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomLingeringPotion(int amount, int id, int amplifier, int duration, boolean ambient, boolean showParticles) {
        return createCustomPotion(PotionEnum.LINGERING_POTION, amount, new CustomPotionEffect(id, amplifier, duration, ambient, showParticles));
    }

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount             药水数量
     * @param name               药水名称
     * @param customPotionEffect 自定义药水效果数组
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomLingeringPotion(int amount, String name, CustomPotionEffect... customPotionEffect) {
        return createCustomPotion(PotionEnum.LINGERING_POTION, amount, name, customPotionEffect);
    }

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount    药水数量
     * @param name      药水名称
     * @param id        药水ID
     * @param amplifier 药水等级
     * @param duration  药水时间
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomLingeringPotion(int amount, String name, int id, int amplifier, int duration) {
        return createCustomPotion(PotionEnum.LINGERING_POTION, amount, name, new CustomPotionEffect(id, amplifier, duration));
    }

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount        药水数量
     * @param name          药水名称
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomLingeringPotion(int amount, String name, int id, int amplifier, int duration, boolean showParticles) {
        return createCustomPotion(PotionEnum.LINGERING_POTION, amount, name, new CustomPotionEffect(id, amplifier, duration, showParticles));
    }

    /**
     * 创建自定义的滞留药水物品栈对象
     *
     * @param amount        药水数量
     * @param name          药水名称
     * @param id            药水ID
     * @param amplifier     药水等级
     * @param duration      药水时间
     * @param ambient       是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 药水 ItemStack
     */
    @Override
    public ItemStack createCustomLingeringPotion(int amount, String name, int id, int amplifier, int duration, boolean ambient, boolean showParticles) {
        return createCustomPotion(PotionEnum.LINGERING_POTION, amount, name, new CustomPotionEffect(id, amplifier, duration, ambient, showParticles));
    }

    /**
     * 给药水物品栈添加自定义药水效果
     *
     * @param potion             药水物品栈
     * @param customPotionEffect 自定义药水效果数组
     * @return 添加药水效果后的 ItemStack
     * @throws NotPotionItemException 如果物品栈不是药水类型则抛出异常
     */
    @Override
    public ItemStack addCustomPotion(ItemStack potion, CustomPotionEffect... customPotionEffect) {
        Util.notNull(potion, "待添加的药水物品栈的类型是 null 值");
        Util.notNull(customPotionEffect, "待添加的药水物品栈的自定义效果是 null 值");

        if(!isPotion(potion))
            throw new NotPotionItemException();

        try {

            Class<?> ItemStack = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            Class<?> CraftItemStack = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
            Class<?> NBTTagCompound = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
            Class<?> NBTTagList = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagList");

            Object NMSItemStack = Reflect.getMethod(CraftItemStack, "asNMSCopy", ItemStack.class).invoke(null, potion);
            Object tag = Reflect.getMethod(ItemStack, "getTag").invoke(NMSItemStack);

            if(tag == null) {

                tag = Reflect.instantiateObject(NBTTagCompound);
            }
            Object potionList = Reflect.getMethod(NBTTagCompound, "getList", String.class, Integer.class).invoke(tag, "CustomPotionEffects", 10);

            if(potionList == null) {

                potionList = Reflect.instantiateObject(NBTTagList);
            }
            for(CustomPotionEffect cpe : customPotionEffect) {

                Object pf = Reflect.instantiateObject(NBTTagCompound);
                Reflect.getMethod(NBTTagCompound, "setByte", String.class, Byte.class).invoke(pf, "Id", (byte)cpe.getId());
                Reflect.getMethod(NBTTagCompound, "setByte", String.class, Byte.class).invoke(pf, "Amplifier", (byte)cpe.getAmplifier());
                Reflect.getMethod(NBTTagCompound, "setInt", String.class, Integer.class).invoke(pf, "Duration", cpe.getDuration());
                Reflect.getMethod(NBTTagCompound, "setByte", String.class, Byte.class).invoke(pf, "Ambient", (byte)(cpe.isAmbient() ? 1 : 0));
                Reflect.getMethod(NBTTagCompound, "setByte", String.class, Byte.class).invoke(pf, "ShowParticles", (byte)(cpe.isShowParticles() ? 1 : 0));

                Reflect.getMethod(NBTTagList, "add", NBTTagCompound).invoke(potionList, pf);
            }
            Reflect.getMethod(NBTTagCompound, "set", String.class, Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTBase")).invoke("CustomPotionEffects", potionList);
            Reflect.getMethod(ItemStack, "setTag", NBTTagCompound).invoke(NMSItemStack, tag);

            return (ItemStack) Reflect.getMethod(CraftItemStack, "asBukkitCopy", ItemStack).invoke(null, NMSItemStack);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给药水物品栈添加自定义药水效果
     *
     * @param potion    药水物品栈
     * @param id        效果ID
     * @param amplifier 效果等级
     * @param duration  效果时间
     * @return 添加药水效果后的 ItemStack
     * @throws NotPotionItemException 如果物品栈不是药水类型则抛出异常
     */
    @Override
    public ItemStack addCustomPotion(ItemStack potion, int id, int amplifier, int duration) {
        return addCustomPotion(potion, new CustomPotionEffect(id, amplifier, duration));
    }

    /**
     * 给药水物品栈添加自定义药水效果
     *
     * @param potion        药水物品栈
     * @param id            效果ID
     * @param amplifier     效果等级
     * @param duration      效果时间
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 添加药水效果后的 ItemStack
     * @throws NotPotionItemException 如果物品栈不是药水类型则抛出异常
     */
    @Override
    public ItemStack addCustomPotion(ItemStack potion, int id, int amplifier, int duration, boolean showParticles) {
        return addCustomPotion(potion, new CustomPotionEffect(id, amplifier, duration, showParticles));
    }

    /**
     * 给药水物品栈添加自定义药水效果
     *
     * @param potion        药水物品栈
     * @param id            效果ID
     * @param amplifier     效果等级
     * @param duration      效果时间
     * @param ambient       是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @return 添加药水效果后的 ItemStack
     * @throws NotPotionItemException 如果物品栈不是药水类型则抛出异常
     */
    @Override
    public ItemStack addCustomPotion(ItemStack potion, int id, int amplifier, int duration, boolean ambient, boolean showParticles) {
        return addCustomPotion(potion, new CustomPotionEffect(id, amplifier, duration, ambient, showParticles));
    }

    /**
     * 获取药水物品栈的自定义药水效果集合
     *
     * @param potion 药水物品栈
     * @return 自定义药水效果集合 如果药水没有自定义效果则返回空集合
     * @throws NotPotionItemException 如果物品栈不是药水类型则抛出异常
     */
    @Override
    public Set<CustomPotionEffect> getCustomPoionEffectList(ItemStack potion) {
        Util.notNull(potion, "待添加的药水物品栈的类型是 null 值");

        if(!isPotion(potion))
            throw new NotPotionItemException();

        try {

            Class<?> ItemStack = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            Class<?> CraftItemStack = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
            Class<?> NBTTagCompound = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
            Class<?> NBTTagList = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagList");

            Object NMSItemStack = Reflect.getMethod(CraftItemStack, "asNMSCopy", ItemStack.class).invoke(null, potion);
            Object tag = Reflect.getMethod(ItemStack, "getTag").invoke(NMSItemStack);

            if(tag == null) {

                tag = Reflect.instantiateObject(NBTTagCompound);
            }
            Object potionList = Reflect.getMethod(NBTTagCompound, "getList", String.class, Integer.class).invoke(tag, "CustomPotionEffects", 10);

            if(potionList == null) {

                potionList = Reflect.instantiateObject(NBTTagList);
            }
            List<CustomPotionEffect> cpeList = new ArrayList<>();
            int size = (int) Reflect.getMethod(NBTTagList, "size").invoke(potionList);

            for(int i = 0; i < size; i++) {

                Object pf = Reflect.getMethod(NBTTagList, "get", Integer.class).invoke(potionList, i);

                if(pf != null) {

                    CustomPotionEffect cpe = new CustomPotionEffect(

                            (int) Reflect.getMethod(NBTTagCompound, "getByte", String.class).invoke(pf, "Id"),
                            (int) Reflect.getMethod(NBTTagCompound, "getByte", String.class).invoke(pf, "Amplifier"),
                            (int) Reflect.getMethod(NBTTagCompound, "getInt", String.class).invoke(pf, "Duration"),
                            ((byte)Reflect.getMethod(NBTTagCompound, "getByte", String.class).invoke(pf, "Ambient") == 1),
                            ((byte)Reflect.getMethod(NBTTagCompound, "getByte", String.class).invoke(pf, "ShowParticles") == 1)
                    );
                    cpeList.add(cpe);
                }
            }
            return cpeList.size() > 0 ? new HashSet<>(cpeList) : new HashSet<CustomPotionEffect>();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return new HashSet<>();
    }

    /**
     * 给物品栈添加的附魔
     *
     * @param item 物品栈
     * @param ench 附魔
     * @param lvl  附魔等级
     * @return 附魔后的 ItemStack
     */
    @Override
    public ItemStack addEnchantment(ItemStack item, Enchantment ench, int lvl) {
        Util.notNull(item, "待添加附魔的物品栈是 null 值");
        Util.notNull(ench, "待添加附魔的物品栈的附魔是 null 值");

        item.addUnsafeEnchantment(ench, lvl);
        return item;
    }

    /**
     * 给物品栈添加的附魔
     *
     * @param item 物品栈
     * @param ench 附魔和等级Map
     * @return 附魔后的 ItemStack
     */
    @Override
    public ItemStack addEnchantment(ItemStack item, Map<Enchantment, Integer> ench) {
        Util.notNull(item, "待添加附魔的物品栈是 null 值");
        Util.notNull(ench, "待添加附魔的物品栈的附魔是 null 值");

        item.addUnsafeEnchantments(ench);
        return item;
    }

    /**
     * 给物品栈添加的附魔
     *
     * @param item 物品栈
     * @param id   附魔ID
     * @param lvl  附魔等级
     * @return 附魔后的 ItemStack
     */
    @Override
    public ItemStack addEnchantment(ItemStack item, int id, int lvl) {
        return addEnchantment(item, Enchantment.getById(id), lvl);
    }

    /**
     * 给物品栈添加的附魔
     *
     * @param item 物品栈
     * @param id   附魔类型
     * @param lvl  附魔等级
     * @return 附魔后的 ItemStack
     */
    @Override
    public ItemStack addEnchantment(ItemStack item, String id, int lvl) {
        return addEnchantment(item, Enchantment.getByName(id), lvl);
    }

    /**
     * 给物品栈删除指定的附魔
     *
     * @param item 物品栈
     * @param ench 附魔
     * @return 删除附魔后的 ItemStack
     */
    @Override
    public ItemStack removeEnchantment(ItemStack item, Enchantment ench) {
        Util.notNull(item, "待删除附魔的物品栈是 null 值");
        Util.notNull(ench, "待删除附魔的物品栈的附魔是 null 值");

        ItemMeta meta = item.getItemMeta();
        if(meta.hasEnchant(ench)) {
            meta.removeEnchant(ench);
        }
        item.setItemMeta(meta);
        return item;
    }

    /**
     * 给物品栈删除指定的附魔
     *
     * @param item 物品栈
     * @param id   附魔ID
     * @return 删除附魔后的 ItemStack
     */
    @Override
    public ItemStack removeEnchantment(ItemStack item, int id) {
        return removeEnchantment(item, Enchantment.getById(id));
    }

    /**
     * 给物品栈删除指定的附魔
     *
     * @param item 物品栈
     * @param id   附魔类型
     * @return 删除附魔后的 ItemStack
     */
    @Override
    public ItemStack removeEnchantment(ItemStack item, String id) {
        return removeEnchantment(item, Enchantment.getByName(id));
    }

    /**
     * 获取物品栈的附魔
     *
     * @param item 物品栈
     * @return 附魔Map
     */
    @Override
    public Map<Enchantment, Integer> getEnchantments(ItemStack item) {
        Util.notNull(item, "待获取附魔的物品栈是 null 值");

        return item.getItemMeta().getEnchants();
    }

    /**
     * 获取物品栈的标示
     *
     * @param item 物品栈
     * @return 标示数组
     */
    @Override
    public Set<ItemFlag> getFlags(ItemStack item) {
        Util.notNull(item, "待获取标示的物品栈是 null 值");

        return item.getItemMeta().getItemFlags();
    }

    /**
     * 给物品栈添加标示
     *
     * @param item  物品栈
     * @param flags 标示
     * @return 添加标示后的 ItemStack
     */
    @Override
    public ItemStack addFlags(ItemStack item, ItemFlag... flags) {
        Util.notNull(item, "待添加标示的物品栈是 null 值");
        Util.notNull(flags, "待添加标示的物品栈的标示是 null 值");

        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flags);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * 给物品栈清除标示
     *
     * @param item  物品栈
     * @param flags 标示
     * @return 清除标示后的 ItemStack
     */
    @Override
    public ItemStack removeFlags(ItemStack item, ItemFlag... flags) {
        Util.notNull(item, "待删除标示的物品栈是 null 值");

        ItemMeta meta = item.getItemMeta();
        meta.removeItemFlags(flags);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * 获取物品栈是否拥有标示
     *
     * @param item 物品栈
     * @return 物品栈
     */
    @Override
    public boolean hasFlag(ItemStack item) {
        Util.notNull(item, "待获取标示的物品栈是 null 值");

        return item.hasItemMeta() && item.getItemMeta().getItemFlags().size() >= 1;
    }

    /**
     * 获取物品栈是否拥有标示
     *
     * @param item 物品栈
     * @param flag 标示
     * @return 物品栈
     */
    @Override
    public boolean hasFlag(ItemStack item, ItemFlag flag) {
        Util.notNull(item, "待获取标示的物品栈是 null 值");

        return item.hasItemMeta() && item.getItemMeta().hasItemFlag(flag);
    }

    /**
     * 设置物品栈是否无法破坏
     *
     * @param item 物品栈
     * @param unbreakable 状态
     * @return 设置后的 ItemStack
     */
    @Override
    public ItemStack setUnbreakable(ItemStack item, boolean unbreakable) {
        Util.notNull(item, "待设置的物品栈是 null 值");

        ItemMeta meta = item.getItemMeta();
        meta.spigot().setUnbreakable(unbreakable);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * 给物品栈添加特殊属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param type 属性类型
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置特殊属性后的 ItemStack 异常返回 null
     */
    @Override
    public ItemStack addAttribute(ItemStack item, AttributeType type, double count, boolean isPercent, AttributeType.Slot slot) {
        Util.notNull(item, "待设置的物品栈是 null 值");
        Util.notNull(type, "待添加特殊属性的物品栈的属性类型是 null 值");

        try {

            Class<?> ItemStack = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            Class<?> CraftItemStack = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
            Class<?> NBTTagCompound = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
            Class<?> NBTTagList = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagList");

            Object NMSItemStack = Reflect.getMethod(CraftItemStack, "asNMSCopy", ItemStack.class).invoke(null, item);
            Object tag = Reflect.getMethod(ItemStack, "getTag").invoke(NMSItemStack);

            if(tag == null) {

                tag = Reflect.instantiateObject(NBTTagCompound);
            }
            Object attList = Reflect.getMethod(NBTTagCompound, "getList", String.class, Integer.class).invoke(tag, "AttributeModifiers", 10);

            if(attList == null) {

                attList = Reflect.instantiateObject(NBTTagList);
            }
            Object att = Reflect.instantiateObject(NBTTagCompound);

            if(slot != null) {

                Reflect.getMethod(NBTTagCompound, "setString", String.class, String.class).invoke("Slot", slot.getSlot());
            }
            Reflect.getMethod(NBTTagCompound, "setString", String.class, String.class).invoke("Name", type.getName());
            Reflect.getMethod(NBTTagCompound, "setString", String.class, String.class).invoke("AttributeName", type.getAttributeName());
            Reflect.getMethod(NBTTagCompound, "setDouble", String.class, Double.class).invoke("Amount", count);
            Reflect.getMethod(NBTTagCompound, "setInt", String.class, Integer.class).invoke("Operation", isPercent ? 1 : 0);

            UUID uuid = UUID.randomUUID();
            Reflect.getMethod(NBTTagCompound, "setLong", String.class, Long.class).invoke("UUIDMost", uuid.getMostSignificantBits());
            Reflect.getMethod(NBTTagCompound, "setLong", String.class, Long.class).invoke("UUIDLeast", uuid.getLeastSignificantBits());

            Reflect.getMethod(NBTTagList, "add", NBTTagCompound).invoke(attList, att);
            Reflect.getMethod(NBTTagCompound, "set", String.class, Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTBase")).invoke(tag, attList);
            Reflect.getMethod(ItemStack, "setTag", NBTTagCompound).invoke(NMSItemStack, tag);

            return (ItemStack) Reflect.getMethod(CraftItemStack, "asBukkitCopy", ItemStack.class).invoke(null, NMSItemStack);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给物品栈添加特殊属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param typeDoubleMap 属性类型和数量Map
     * @param isPercent 是否百分比数组
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置特殊属性后的 ItemStack 异常返回 null
     */
    @Override
    public ItemStack addAttribute(ItemStack item, Map<AttributeType, Double> typeDoubleMap, boolean[] isPercent, AttributeType.Slot... slot) {
        Util.notNull(item, "待设置的物品栈是 null 值");
        Util.notNull(typeDoubleMap, "待添加特殊属性的物品栈的属性类型是 null 值");

        try {

            Class<?> ItemStack = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            Class<?> CraftItemStack = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
            Class<?> NBTTagCompound = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
            Class<?> NBTTagList = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagList");

            Object NMSItemStack = Reflect.getMethod(CraftItemStack, "asNMSCopy", ItemStack.class).invoke(null, item);
            Object tag = Reflect.getMethod(ItemStack, "getTag").invoke(NMSItemStack);

            if(tag == null) {

                tag = Reflect.instantiateObject(NBTTagCompound);
            }
            Object attList = Reflect.getMethod(NBTTagCompound, "getList", String.class, Integer.class).invoke(tag, "AttributeModifiers", 10);

            if(attList == null) {

                attList = Reflect.instantiateObject(NBTTagList);
            }
            Object att = Reflect.instantiateObject(NBTTagCompound);
            int index = 0;
            Iterator<Map.Entry<AttributeType, Double>> iterator = typeDoubleMap.entrySet().iterator();

            while (iterator.hasNext()) {

                Map.Entry<AttributeType, Double> entry = iterator.next();
                AttributeType type = entry.getKey();

                Util.notNull(type, "待添加特殊属性的物品栈的属性类型是 null 值");

                if(slot != null) {

                    Reflect.getMethod(NBTTagCompound, "setString", String.class, String.class).invoke("Slot", slot[index].getSlot());
                }
                Reflect.getMethod(NBTTagCompound, "setString", String.class, String.class).invoke("Name", type.getName());
                Reflect.getMethod(NBTTagCompound, "setString", String.class, String.class).invoke("AttributeName", type.getAttributeName());
                Reflect.getMethod(NBTTagCompound, "setDouble", String.class, Double.class).invoke("Amount", entry.getValue());
                Reflect.getMethod(NBTTagCompound, "setInt", String.class, Integer.class).invoke("Operation", isPercent[index] ? 1 : 0);

                UUID uuid = UUID.randomUUID();
                Reflect.getMethod(NBTTagCompound, "setLong", String.class, Long.class).invoke("UUIDMost", uuid.getMostSignificantBits());
                Reflect.getMethod(NBTTagCompound, "setLong", String.class, Long.class).invoke("UUIDLeast", uuid.getLeastSignificantBits());

                Reflect.getMethod(NBTTagList, "add", NBTTagCompound).invoke(attList, att);
                index++;
            }
            Reflect.getMethod(NBTTagCompound, "set", String.class, Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTBase")).invoke(tag, attList);
            Reflect.getMethod(ItemStack, "setTag", NBTTagCompound).invoke(NMSItemStack, tag);
            return (ItemStack) Reflect.getMethod(CraftItemStack, "asBukkitCopy", ItemStack.class).invoke(null, NMSItemStack);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取物品栈的特殊属性集合
     *
     * @param item 物品栈
     * @return 特殊属性集合 如果物品栈没有特殊属性则返回空集合
     */
    @Override
    public Set<AttributeStack> getAttributeList(ItemStack item) {
        Util.notNull(item, "待获取的物品栈是 null 值");

        try {

            Class<?> ItemStack = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            Class<?> CraftItemStack = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
            Class<?> NBTTagCompound = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
            Class<?> NBTTagList = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagList");

            Object NMSItemStack = Reflect.getMethod(CraftItemStack, "asNMSCopy", ItemStack.class).invoke(null, item);
            Object tag = Reflect.getMethod(ItemStack, "getTag").invoke(NMSItemStack);

            if(tag == null) {

                tag = Reflect.instantiateObject(NBTTagCompound);
            }
            Object attList = Reflect.getMethod(NBTTagCompound, "getList", String.class, Integer.class).invoke(tag, "AttributeModifiers", 10);

            if(attList == null) {

                attList = Reflect.instantiateObject(NBTTagList);
            }
            List<AttributeStack> attStackList = new ArrayList<>();
            int size = (int) Reflect.getMethod(NBTTagList, "size").invoke(attList);

            for(int i = 0; i < size; i++) {

                Object att = Reflect.getMethod(NBTTagList, "get", Integer.class).invoke(attList, i);

                if(att != null) {

                    AttributeType attType = AttributeType.fromType((String) Reflect.getMethod(NBTTagCompound, "getString", String.class).invoke(att, "AttributeName"));
                    AttributeType.Slot attSlot = AttributeType.Slot.fromType((String) Reflect.getMethod(NBTTagCompound, "getString", String.class).invoke(att, "Slot"));

                    if(attType != null) {

                        AttributeStack attStack = new AttributeStack(

                                attType, attSlot,
                                (Double) Reflect.getMethod(NBTTagCompound, "getDouble", String.class).invoke(att, "Amount"),
                                (Integer) Reflect.getMethod(NBTTagCompound, "getInte", String.class).invoke(att, "Operation")
                        );
                        attStackList.add(attStack);
                    }
                }
            }
            return attStackList.size() > 0 ? new HashSet<>(attStackList) : new HashSet<AttributeStack>();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return new HashSet<>();
    }

    /**
     * 设置物品栈的攻击伤害属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置攻击伤害属性后的 ItemStack
     */
    @Override
    public ItemStack setItemAttackDamage(ItemStack item, double count, boolean isPercent, AttributeType.Slot slot) {
        return addAttribute(item, AttributeType.ATTACK_DAMAGE, count, isPercent, slot);
    }

    /**
     * 设置物品栈的血量上限属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置血量上限属性后的 ItemStack
     */
    @Override
    public ItemStack setItemMaxHealth(ItemStack item, double count, boolean isPercent, AttributeType.Slot slot) {
        return addAttribute(item, AttributeType.MAX_HEALTH, count, isPercent, slot);
    }

    /**
     * 设置物品栈的移动速度属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置移动速度属性后的 ItemStack
     */
    @Override
    public ItemStack setItemMoveSpeed(ItemStack item, double count, boolean isPercent, AttributeType.Slot slot) {
        return addAttribute(item, AttributeType.MOVE_SPEED, count, isPercent, slot);
    }

    /**
     * 设置物品栈的击退抗性属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置击退抗性属性后的 ItemStack
     */
    @Override
    @Deprecated
    public ItemStack setItemKnockbackResistance(ItemStack item, double count, boolean isPercent, AttributeType.Slot slot) {
        return addAttribute(item, AttributeType.KNOCKBACK_RESISTANCE, count, isPercent, slot);
    }

    /**
     * 设置物品栈的跟踪范围属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item 物品栈
     * @param count 属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置跟踪范围属性后的 ItemStack
     */
    @Override
    public ItemStack setItemFollowRange(ItemStack item, double count, boolean isPercent, AttributeType.Slot slot) {
        return addAttribute(item, AttributeType.FOLLOW_RANGE, count, isPercent, slot);
    }

    /**
     * 设置物品栈的幸运属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item      物品栈
     * @param count     属性数量
     * @param isPercent 是否百分比
     * @param slot      属性生效的槽位 如果全部槽位则 null 值
     * @return 设置幸运属性后的 ItemStack
     */
    @Override
    public ItemStack setItemLuck(ItemStack item, double count, boolean isPercent, AttributeType.Slot slot) {
        return addAttribute(item, AttributeType.LUCK, count, isPercent, slot);
    }

    /**
     * 设置护甲物品栈的护甲防御属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param armor     护甲物品栈
     * @param count     属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置护甲防御属性后的 ItemStack
     */
    @Override
    public ItemStack setItemArmorDefense(ItemStack armor, double count, boolean isPercent, AttributeType.Slot slot) {
        return addAttribute(armor, AttributeType.ARMOR_DEFENSE, count, isPercent, slot);
    }

    /**
     * 设置护甲物品栈的护甲韧性属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param armor     护甲物品栈
     * @param count     属性数量
     * @param isPercent 是否百分比
     * @param slot 属性生效的槽位 如果全部槽位则 null 值
     * @return 设置护甲韧性属性后的 ItemStack
     */
    @Override
    public ItemStack setItemArmorToughness(ItemStack armor, double count, boolean isPercent, AttributeType.Slot slot) {
        return addAttribute(armor, AttributeType.ARMOR_TOUGHNESS, count, isPercent, slot);
    }

    /**
     * 设置物品栈的攻击速度属性 (NMS映射设置不推荐使用 && 谨慎设置数量防止蹦服)
     *
     * @param item      物品栈
     * @param count     属性数量
     * @param isPercent 是否百分比
     * @param slot      属性生效的槽位 如果全部槽位则 null 值
     * @return 设置攻击速度属性后的 ItemStack
     */
    @Override
    public ItemStack setItemAttackSpeed(ItemStack item, double count, boolean isPercent, AttributeType.Slot slot) {
        return addAttribute(item, AttributeType.ATTACK_SPEED, count, isPercent, slot);
    }

    /**
     * 将字串符类型序列化为物品栈标示
     *
     * @param type 类型
     * @return 物品栈标示 如果不存在则返回 null
     */
    @Override
    public ItemFlag getItemFlagFromType(String type) {
        Util.notEmpty(type, "待转换的类型标示是 null 值");

        switch (type.toLowerCase()){
            case "enchants":
                return ItemFlag.HIDE_ENCHANTS;
            case "attributes":
                return ItemFlag.HIDE_ATTRIBUTES;
            case "unbreakable":
                return ItemFlag.HIDE_UNBREAKABLE;
            case "destroys":
                return ItemFlag.HIDE_DESTROYS;
            case "placed":
            case "placedon":
            case "placed_on":
                return ItemFlag.HIDE_PLACED_ON;
            case "potioneffects":
            case "potion_effects":
                return ItemFlag.HIDE_POTION_EFFECTS;
            default:
                return null;
        }
    }

    /**
     * 获取物品栈的特殊属性
     *
     * @param item 物品栈
     * @param type 属性类型
     * @return 特殊属性数据类
     */
    @Override
    public AttributeStack getItemAttributeFromType(ItemStack item, AttributeType type) {
        List<AttributeStack> attList = new ArrayList<>(getAttributeList(item));
        for(int i = 0; i < attList.size(); i++) {
            if(attList.get(i).getType() == type) {
                return attList.get(i);
            }
        }
        return null;
    }

    /**
     * 获取物品栈的特殊属性
     *
     * @param item 物品栈
     * @param type 属性类型
     * @return 特殊属性数据类
     */
    @Override
    public AttributeStack getItemAttributeFromType(ItemStack item, String type) {
        return getItemAttributeFromType(item, AttributeType.fromType(type));
    }

    /**
     * 设置皮革护甲物品栈的颜色
     *
     * @param leatherArmor 皮革护甲物品栈
     * @param color        颜色
     * @return 设置颜色后的皮革护甲物品栈
     * @throws NotArmorItemException 如果物品栈不是护甲类型则抛出异常
     */
    @Override
    public ItemStack setLeatherArmorColor(ItemStack leatherArmor, Color color) {
        if(!isLeatherArmor(leatherArmor))
            throw new NotArmorItemException();

        LeatherArmorMeta laMeta = (LeatherArmorMeta)leatherArmor.getItemMeta();
        laMeta.setColor(color);
        leatherArmor.setItemMeta(laMeta);
        return leatherArmor;
    }

    /**
     * 设置皮革护甲物品栈的颜色
     *
     * @param leatherArmor 皮革护甲物品栈
     * @param r            红色值 (min: 0, max: 255)
     * @param g            绿色值 (min: 0, max: 255)
     * @param b            蓝色值 (min: 0, max: 255)
     * @return 设置颜色后的皮革护甲物品栈
     * @throws NotArmorItemException 如果物品栈不是护甲类型则抛出异常
     */
    @Override
    public ItemStack setLeatherArmorColorRGB(ItemStack leatherArmor, int r, int g, int b) {
        return setLeatherArmorColor(leatherArmor, Color.fromRGB(r, g, b));
    }

    /**
     * 设置皮革护甲物品栈的颜色
     *
     * @param leatherArmor 皮革护甲物品栈
     * @param g            绿色值 (min: 0, max: 255)
     * @param b            蓝色值 (min: 0, max: 255)
     * @param r            红色值 (min: 0, max: 255)
     * @return 设置颜色后的皮革护甲物品栈
     * @throws NotArmorItemException 如果物品栈不是护甲类型则抛出异常
     */
    @Override
    public ItemStack setLeatherArmorColorGBR(ItemStack leatherArmor, int g, int b, int r) {
        return setLeatherArmorColor(leatherArmor, Color.fromBGR(b, g, r));
    }

    /**
     * 获取皮革护甲物品栈的颜色
     *
     * @param leatherArmor 皮革护甲物品栈
     * @return 皮革护甲物品栈的颜色
     * @throws NotArmorItemException 如果物品栈不是护甲类型则抛出异常
     */
    @Override
    public Color getLeatherArmorColor(ItemStack leatherArmor) {
        if(!isLeatherArmor(leatherArmor))
            throw new NotArmorItemException();

        return ((LeatherArmorMeta)leatherArmor.getItemMeta()).getColor();
    }

    /**
     * 获取成书物品栈的页内容集合
     *
     * @param book 成书物品栈
     * @return 成书的页内容集合 如果成书没有内容则返回空集合
     * @throws NotBookItemException 如果物品栈不是成书类型则抛出异常
     */
    @Override
    public Set<String> getBookPageCentents(ItemStack book) {
        if(!isWrittenBook(book))
            throw new NotBookItemException();

        BookMeta bMeta = (BookMeta)book.getItemMeta();
        return bMeta.hasPages() ? new HashSet<>(bMeta.getPages()) : new HashSet<String>();
    }

    /**
     * 获取成书物品栈的作者
     *
     * @param book 成书物品栈
     * @return 成书的作者 如果成书没有作者则返回 null
     * @throws NotBookItemException 如果物品栈不是成书类型则抛出异常
     */
    @Override
    public String getBookAuther(ItemStack book) {
        if(!isWrittenBook(book))
            throw new NotBookItemException();

        BookMeta bMeta = (BookMeta)book.getItemMeta();
        return bMeta.hasAuthor() ? bMeta.getAuthor() : null;
    }

    /**
     * 判断物品栈是否是护甲物品栈
     *
     * @param item 物品栈
     * @return 是否是护甲物品栈
     */
    @Override
    public boolean isArmor(ItemStack item) {
        Util.notNull(item, "待判断的物品栈对象是 null 值");

        return isArmor(item.getType());
    }

    /**
     * 判断物品栈类型是否是护甲物品栈类型
     *
     * @param type 物品栈类型
     * @return 是否是护甲物品栈类型
     */
    @Override
    public boolean isArmor(Material type) {
        Util.notNull(type, "待判断的物品栈类型对象是 null 值");

        return
                type == Material.LEATHER_HELMET ||
                type == Material.LEATHER_CHESTPLATE ||
                type == Material.LEATHER_LEGGINGS ||
                type == Material.LEATHER_BOOTS ||
                type == Material.CHAINMAIL_HELMET ||
                type == Material.CHAINMAIL_CHESTPLATE ||
                type == Material.CHAINMAIL_LEGGINGS ||
                type == Material.CHAINMAIL_BOOTS ||
                type == Material.IRON_HELMET ||
                type == Material.IRON_CHESTPLATE ||
                type == Material.IRON_LEGGINGS ||
                type == Material.IRON_BOOTS ||
                type == Material.DIAMOND_HELMET ||
                type == Material.DIAMOND_CHESTPLATE ||
                type == Material.DIAMOND_LEGGINGS ||
                type == Material.DIAMOND_BOOTS ||
                type == Material.GOLD_HELMET ||
                type == Material.GOLD_CHESTPLATE ||
                type == Material.GOLD_LEGGINGS ||
                type == Material.GOLD_BOOTS;
    }

    /**
     * 判断物品栈类型是否是皮革护甲物品栈类型
     *
     * @param item 物品栈类型
     * @return 是否是皮革护甲物品栈类型
     */
    @Override
    public boolean isLeatherArmor(ItemStack item) {
        Util.notNull(item, "待判断的物品栈对象是 null 值");

        return isLeatherArmor(item.getType());
    }

    /**
     * 判断物品栈类型是否是皮革护甲物品栈类型
     *
     * @param type 物品栈类型
     * @return 是否是皮革护甲物品栈类型
     */
    @Override
    public boolean isLeatherArmor(Material type) {
        Util.notNull(type, "待判断的物品栈类型对象是 null 值");

        return
                type == Material.LEATHER_HELMET ||
                type == Material.LEATHER_CHESTPLATE ||
                type == Material.LEATHER_LEGGINGS ||
                type == Material.LEATHER_BOOTS ;
    }

    /**
     * 判断物品栈是否是药水物品栈
     *
     * @param item 物品栈
     * @return 是否是药水物品栈
     */
    @Override
    public boolean isPotion(ItemStack item) {
        Util.notNull(item, "待判断的物品栈对象是 null 值");

        return isPotion(item.getType());
    }

    /**
     * 判断物品栈类型是否是药水物品栈类型
     *
     * @param type 物品栈类型
     * @return 是否是药水物品栈类型
     */
    @Override
    public boolean isPotion(Material type) {
        Util.notNull(type, "待判断的物品栈类型对象是 null 值");

        return
                type == Material.POTION ||
                type == Material.SPLASH_POTION ||
                type == Material.LINGERING_POTION;
    }

    /**
     * 判断物品栈是否是成书物品栈
     *
     * @param item 物品栈
     * @return 是否是成书物品栈
     */
    @Override
    public boolean isWrittenBook(ItemStack item) {
        Util.notNull(item, "待判断的物品栈对象是 null 值");

        return isWrittenBook(item.getType());
    }

    /**
     * 判断物品栈类型是否是成书物品栈类型
     *
     * @param type 物品栈类型
     * @return 是否是成书物品栈类型
     */
    @Override
    public boolean isWrittenBook(Material type) {
        Util.notNull(type, "待判断的物品栈类型对象是 null 值");

        return type == Material.WRITTEN_BOOK;
    }
}
