package com.minecraft.moonlake.util.item;

import com.minecraft.moonlake.api.itemlib.Itemlib;
import com.minecraft.moonlake.util.Util;
import com.minecraft.moonlake.util.lore.LoreUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by MoonLake on 2016/4/26.
 * @version 1.0
 * @author Month_Light
 */
public class ItemUtil extends LoreUtil implements Itemlib {

    static {}

    public ItemUtil() {

    }

    /**
     * 创建物品栈对象
     *
     * @param id 物品ID
     * @return ItemStack
     */
    @Override
    @SuppressWarnings("deprecation")
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
    @SuppressWarnings("deprecation")
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
    @SuppressWarnings("deprecation")
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
     * 给物品栈添加的附魔
     *
     * @param item 物品栈
     * @param ench 附魔
     * @param lvl  附魔等级
     * @return 附魔后的 ItemStack
     */
    @Override
    public ItemStack enchantment(ItemStack item, Enchantment ench, int lvl) {
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
    public ItemStack enchantment(ItemStack item, Map<Enchantment, Integer> ench) {
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
    @SuppressWarnings("deprecation")
    public ItemStack enchantment(ItemStack item, int id, int lvl) {
        return enchantment(item, Enchantment.getById(id), lvl);
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
    public ItemStack enchantment(ItemStack item, String id, int lvl) {
        return enchantment(item, Enchantment.getByName(id), lvl);
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
     * 设置物品栈是否无法破坏 (NMS映射设置不推荐使用)
     *
     * @param item 物品栈
     * @param unbreakable 状态
     * @return 设置后的 ItemStack 异常返回 null
     */
    @Override
    public ItemStack setUnbreakableFromNMS(ItemStack item, boolean unbreakable) {
        Util.notNull(item, "待设置的物品栈是 null 值");

        Field field = null;
        net.minecraft.server.v1_9_R1.ItemStack nms = null;

        try {
            field = org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack.class.getDeclaredField("handle");
            field.setAccessible(true);
            nms = (net.minecraft.server.v1_9_R1.ItemStack)field.get(item);
        }
        catch (Exception e) {
            return null;
        }
        net.minecraft.server.v1_9_R1.NBTTagCompound tag = nms.getTag();
        if(tag == null) {
            tag = new net.minecraft.server.v1_9_R1.NBTTagCompound();
        }
        tag.setByte("Unbreakable", unbreakable ? (byte)1 : (byte)0);
        nms.setTag(tag);
        org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack craftItem = org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack.asCraftCopy(item);
        try {
            field.set(craftItem, nms);
        }
        catch (Exception e) {
            return null;
        }
        return craftItem;
    }
}
