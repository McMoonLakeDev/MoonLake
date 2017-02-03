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
 
 
package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.item.AttributeModify;
import com.minecraft.moonlake.api.item.ItemBuilder;
import com.minecraft.moonlake.api.item.ItemLibraryFactorys;
import com.minecraft.moonlake.api.nbt.NBTCompound;
import com.minecraft.moonlake.api.nbt.NBTFactory;
import com.minecraft.moonlake.data.Conversions;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>ItemManager</h1>
 * 物品栈管理实现类
 *
 * @version 1.0
 * @author Month_Light
 */
public class ItemManager extends MoonLakeManager {

    private final static Class<?> CLASS_ITEMSTACK;
    private final static Class<?> CLASS_NBTTAGCOMPOUND;
    private final static Class<?> CLASS_CRAFTITEMSTACK;
    private final static Class<?> CLASS_NBTCOMPRESSEDSTREAMTOOLS;
    private final static Constructor<?> CONSTRUCTOR_NBTTAGCOMPOUND;
    private final static Method METHOD_ASNMSCOPY;
    private final static Method METHOD_SAVE;
    private final static Method METHOD_A0;
    private final static Method METHOD_A1;
    private final static Method METHOD_CREATESTACK;
    private final static Method METHOD_ASBUKKITCOPY;

    static {

        try {

            CLASS_ITEMSTACK = PackageType.MINECRAFT_SERVER.getClass("ItemStack");
            CLASS_NBTTAGCOMPOUND = PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
            CLASS_CRAFTITEMSTACK = PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
            CLASS_NBTCOMPRESSEDSTREAMTOOLS = PackageType.MINECRAFT_SERVER.getClass("NBTCompressedStreamTools");
            CONSTRUCTOR_NBTTAGCOMPOUND = getConstructor(CLASS_NBTTAGCOMPOUND);
            METHOD_ASNMSCOPY = getMethod(CLASS_CRAFTITEMSTACK, "asNMSCopy", ItemStack.class);
            METHOD_SAVE = getMethod(CLASS_ITEMSTACK, "save", CLASS_NBTTAGCOMPOUND);
            METHOD_A0 = getMethod(CLASS_NBTCOMPRESSEDSTREAMTOOLS, "a", InputStream.class);
            METHOD_A1 = getMethod(CLASS_NBTCOMPRESSEDSTREAMTOOLS, "a", CLASS_NBTTAGCOMPOUND, OutputStream.class);
            METHOD_CREATESTACK = getMethod(CLASS_ITEMSTACK, "createStack", CLASS_NBTTAGCOMPOUND);
            METHOD_ASBUKKITCOPY = getMethod(CLASS_CRAFTITEMSTACK, "asBukkitCopy", CLASS_ITEMSTACK);
        }
        catch (Exception e) {

            throw new MoonLakeException("The item manager reflect raw exception.", e);
        }
    }

    /**
     * 物品栈管理实现类构造函数
     */
    private ItemManager() {

    }

    /**
     * 将 Bukkit 物品栈对象转换到 NMS 物品栈对象
     *
     * @param itemStack Bukkit 物品栈
     * @return NMS 物品栈
     * @throws IllegalArgumentException 如果 Bukkit 物品栈对象为 {@code null} 则抛出异常
     */
    public static Object asNMSCopy(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        try {

            return METHOD_ASNMSCOPY.invoke(null, itemStack);
        }
        catch (Exception e) {

            throw new MoonLakeException("The as nms copy itemstack exception.", e);
        }
    }

    /**
     * 将 NMS 物品栈对象转换到 Bukkit 物品栈对象
     *
     * @param nmsItemStack NMS 物品栈
     * @return Bukkit 物品栈
     * @throws IllegalArgumentException 如果 NMS 物品栈对象为 {@code null} 则抛出异常
     */
    public static ItemStack asBukkitCopy(Object nmsItemStack) {

        Validate.notNull(nmsItemStack, "The nms itemstack object is null.");

        try {

            return (ItemStack) METHOD_ASBUKKITCOPY.invoke(null, nmsItemStack);
        }
        catch (Exception e) {

            throw new MoonLakeException("The as bukkit copy itemstack exception.", e);
        }
    }

    /**
     * 获取物品栈 NBT 标签的字符串值
     * @param itemStack 物品栈
     * @return 字符串 NBT 标签值 异常返回空
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    public static String getTagString(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        return NBTFactory.get().readSafe(itemStack).toString();
    }

    /**
     * 获取指定物品栈的显示名称
     *
     * @param itemStack 物品栈
     * @return 物品栈的显示名 没有则返回 类型名
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    public static String getDisplayName(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        return itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() ? itemStack.getItemMeta().getDisplayName() : itemStack.getType().name();
    }

    /**
     * 比较指定物品栈是否符合目标物品栈
     *
     * @param source 源物品栈
     * @param target 目标物品栈
     * @return true 则类型和数据都相同 else 不相同
     */
    @SuppressWarnings("deprecation")
    public static boolean compare(ItemStack source, ItemStack target) {

        return compareNull(source, target) || (source != null && target != null && source.getType() == target.getType() && source.getData().getData() == target.getData().getData());
    }

    /**
     * 获取物品栈对象是否是空气类型
     *
     * @param item 物品栈
     * @return true 是空气物品栈 else 不是
     */
    public static boolean isAir(ItemStack item) {

        return item == null || item.getType() == Material.AIR;
    }

    /**
     * 比较指定物品栈是否符合目标物品栈类型
     *
     * @param source 源物品栈
     * @param target 目标物品栈类型
     * @return true 则类型和数据都相同 else 不相同
     */
    public static boolean compare(ItemStack source, Material target) {

        return compare(source, target, 0);
    }

    /**
     * 比较指定物品栈是否符合目标物品栈类型
     *
     * @param source 源物品栈
     * @param target 目标物品栈类型
     * @param data 目标物品栈数据
     * @return true 则类型和数据都相同 else 不相同
     */
    public static boolean compare(ItemStack source, Material target, int data) {

        return compare(source, new ItemStack(target, 1, (byte)data));
    }

    /**
     * 比较指定物品栈是否符合目标物品栈的数量
     *
     * @param source 源物品栈
     * @param target 目标物品栈
     * @return true 则两个物品栈符合数量 else 不符合
     */
    public static boolean compareAmount(ItemStack source, ItemStack target) {

        return compareNull(source, target) || (source != null && target != null && source.getAmount() == target.getAmount());
    }

    /**
     * 比较指定物品栈是否符合目标物品栈的显示名
     *
     * @param source 源物品栈
     * @param target 目标物品栈
     * @return true 则两个物品栈符合显示名 else 不符合
     */
    public static boolean compareDisplayName(ItemStack source, ItemStack target) {

        String sourceName = getDisplayName(source);
        String targetName = getDisplayName(target);

        return compareNull(source, target) || (sourceName != null && targetName != null && sourceName.equals(targetName));
    }

    /**
     * 比较指定物品栈是否符合目标物品栈的属性
     *
     * @param source 源物品栈
     * @param target 目标物品栈
     * @return true 则两个物品栈符合属性 else 不符合
     */
    public static boolean compareMeta(ItemStack source, ItemStack target) {

        return compareNull(source, target) || (source != null && target != null && source.isSimilar(target));
    }

    /**
     * 比较指定物品栈是否完全符合目标物品栈
     *
     * @param source 源物品栈
     * @param target 目标物品栈
     * @return true 则两个物品栈完全符合 else 不符合
     */
    public static boolean compareAll(ItemStack source, ItemStack target) {

        return compareNull(source, target) || (compare(source, target) && compareMeta(source, target));
    }

    /**
     * 比较指定物品栈是否完全和目标物品栈对象均为 {@code null}
     *
     * @param source 源物品栈
     * @param target 目标物品栈
     * @return true 则两个物品栈均为 {@code null}
     */
    public static boolean compareNull(ItemStack source, ItemStack target) {

        return source == null && target == null;
    }

    /**
     * 判断指定物品栈是否为工具类型
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为工具
     */
    public static boolean isTool(ItemStack itemStack) {

        return !isAir(itemStack) && ItemLibraryFactorys.item().isTool(itemStack);
    }

    /**
     * 判断指定物品栈是否为武器类型
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为武器
     */
    public static boolean isWeapon(ItemStack itemStack) {

        return !isAir(itemStack) && ItemLibraryFactorys.item().isWeapon(itemStack);
    }

    /**
     * 判断指定物品栈是否为护甲类型
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为护甲
     */
    public static boolean isArmor(ItemStack itemStack) {

        return !isAir(itemStack) && ItemLibraryFactorys.item().isArmor(itemStack);
    }

    /**
     * 判断指定物品栈是否为皮革护甲类型
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为皮革护甲
     */
    public static boolean isLeatherArmor(ItemStack itemStack) {

        return !isAir(itemStack) && ItemLibraryFactorys.item().isLeatherArmor(itemStack);
    }

    /**
     * 判断指定物品栈是否为药水类型
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为药水
     */
    public static boolean isPotion(ItemStack itemStack) {

        return !isAir(itemStack) && ItemLibraryFactorys.item().isPotion(itemStack);
    }

    /**
     * 判断指定物品栈是否为书类型
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为书
     */
    public static boolean isWrittenBook(ItemStack itemStack) {

        return !isAir(itemStack) && ItemLibraryFactorys.item().isWrittenBook(itemStack);
    }

    /**
     * 将物品栈对象数据序列化为字符串数据
     *
     * @param itemStack 物品栈
     * @return 物品栈字符串数据 异常返回 null
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    public static String serialize(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        if(!isAir(itemStack)) {

            ByteArrayOutputStream outputStream = null;

            try {

                outputStream = new ByteArrayOutputStream();

                Object NBTTag = CONSTRUCTOR_NBTTAGCOMPOUND.newInstance();
                Object nmsItemStack = asNMSCopy(itemStack);

                METHOD_SAVE.invoke(nmsItemStack, NBTTag);
                METHOD_A1.invoke(null, NBTTag, outputStream);

                return Base64.getEncoder().encodeToString(outputStream.toByteArray());
            }
            catch (Exception e) {

                throw new MoonLakeException("The itemstack serialize exception.", e);
            }
            finally {

                if(outputStream != null) {

                    try {

                        outputStream.close();
                    }
                    catch (Exception e) {

                    }
                }
            }
        }
        return null;
    }

    /**
     * 将字符串数据反序列化为物品栈对象
     *
     * @param data 字符串数据
     * @return 物品栈对象 异常返回 null
     * @throws IllegalArgumentException 如果字符串数据对象为 {@code null} 则抛出异常
     */
    public static ItemStack deserialize(String data) {

        Validate.notNull(data, "The string data object is null.");

        if(!data.isEmpty()) {

            ByteArrayInputStream inputStream = null;

            try {

                inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(data));

                Object NBTTag = METHOD_A0.invoke(null, inputStream);
                Object nmsItemStack = METHOD_CREATESTACK.invoke(null, NBTTag);

                return asBukkitCopy(nmsItemStack);
            }
            catch (Exception e) {

                throw new MoonLakeException("The itemstack deserialize exception.", e);
            }
            finally {

                if(inputStream != null) {

                    try {

                        inputStream.close();
                    }
                    catch (Exception e) {

                    }
                }
            }
        }
        return null;
    }

    /**
     * 将物品栈对象数据序列化为特定 YAML 文件数据
     *
     * @param builder 物品栈构建器
     * @param out 输出的文件
     * @throws IllegalArgumentException 如果物品栈构建器对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果输出文件对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果保存到文件时错误则抛出异常
     */
    public static void serializeToFile(ItemBuilder builder, File out) {

        serializeToFile(builder, out, null);
    }

    /**
     * 将物品栈对象数据序列化为特定 YAML 文件数据
     *
     * @param builder 物品栈构建器
     * @param out 输出的文件
     * @param charset 文件编码
     * @throws IllegalArgumentException 如果物品栈构建器对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果输出文件对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果保存到文件时错误则抛出异常
     */
    public static void serializeToFile(ItemBuilder builder, File out, String charset) {

        Validate.notNull(builder, "The item builder object is null.");

        serializeToFile(builder.build(true), out, charset);
    }

    /**
     * 将物品栈对象数据序列化为特定 YAML 文件数据
     *
     * @param itemStack 物品栈
     * @param out 输出的文件
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果输出文件对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果保存到文件时错误则抛出异常
     */
    public static void serializeToFile(ItemStack itemStack, File out) {

        serializeToFile(itemStack, out, null);
    }

    /**
     * 将物品栈对象数据序列化为特定 YAML 文件数据
     *
     * @param itemStack 物品栈
     * @param out 输出的文件
     * @param charset 文件编码
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果输出文件对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果保存到文件时错误则抛出异常
     */
    @SuppressWarnings("deprecation")
    public static void serializeToFile(ItemStack itemStack, File out, String charset) {

        Validate.notNull(itemStack, "The itemstack object is null.");
        Validate.notNull(out, "The file out object is null.");

        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("Type", itemStack.getType().name());
        yaml.set("Data", (int) itemStack.getDurability());
        yaml.set("Amount", itemStack.getAmount());

        ItemMeta itemMeta = itemStack.getItemMeta();

        // display name
        if(itemMeta.hasDisplayName())
            yaml.set("DisplayName", itemMeta.getDisplayName());

        // lore
        if(itemMeta.hasLore())
            yaml.set("Lore", itemMeta.getLore());

        // enchantment
        if(itemMeta.hasEnchants()) {

            Map<Enchantment, Integer> enchantments = itemMeta.getEnchants();

            for (final Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {

                yaml.set("Enchantment." + entry.getKey().getName(), entry.getValue());
            }
        }

        // hide flag
        Set<ItemFlag> hideFlags = itemMeta.getItemFlags();

        if(hideFlags != null && !hideFlags.isEmpty()) {

            List<String> result = new ArrayList<>();

            for(final ItemFlag flag : hideFlags)
                result.add(flag.name());

            yaml.set("HideFlag", result);
        }

        // unbreakable
        if(MoonLakeAPI.getItemLibrary().isUnbreakable(itemStack))
            yaml.set("Unbreakable", true);

        // attribute modifiers
        Set<AttributeModify> atts = MoonLakeAPI.getItemLibrary().getAttributes(itemStack);

        if(atts != null && !atts.isEmpty()) {

            for(final AttributeModify att : atts) {

                AttributeModify.Slot slot = att.getSlot().get();
                AttributeModify.Type type = att.getType().get();
                AttributeModify.Operation operation = att.getOperation().get();

                yaml.set("AttributeModifiers." + type.getType() + ".Value", att.getAmount().getValue());

                if(operation == AttributeModify.Operation.ADD_PERCENTAGE)
                    // percent
                    yaml.set("AttributeModifiers." + type.getType() + ".Percent", true);

                if(slot != null && slot != AttributeModify.Slot.MAIN_HAND && slot != AttributeModify.Slot.ALL)
                    // slot
                    yaml.set("AttributeModifiers." + type.getType() + ".Slot", slot.getType());
            }
        }

        // nbt modifiers
        NBTCompound tag = MoonLakeAPI.getNBTLibrary().readSafe(itemStack);

        // skull owner
        if(itemStack.getType() == Material.SKULL_ITEM) {

            String skullOwner = tag.getString("SkullOwner", null);

            if(skullOwner != null)
                yaml.set("SkullOwner", skullOwner);
        }

        // age
        int age = tag.getInt("Age", 6000);

        if(age != 6000)
            yaml.set("Age", age);

        // pickup delay
        int pickupDelay = tag.getInt("PickupDelay", -1);

        if(pickupDelay != -1)
            yaml.set("PickupDelay", pickupDelay);

        // save file
        OutputStreamWriter osw = null;

        try {

            if(charset == null) {
                // use yaml default save
                yaml.save(out);
            }
            else {
                // use out stream save
                String data = yaml.saveToString();

                if(data.contains("\\u"))
                    // unicode
                    data = StringUtil.decodeUnicode(data);

                osw = new OutputStreamWriter(new FileOutputStream(out), charset);
                osw.write(data);
            }
        }
        catch (Exception e) {

            throw new MoonLakeException("The serialize to file save exception.");
        }
        finally {

            try {

                if(osw != null) {
                    osw.flush();
                    osw.close();
                }
            }
            catch (Exception e) {
            }
        }
    }

    /**
     * 将特定 YAML 文件数据反序列化为物品栈对象
     *
     * @param path 文件路径
     * @return 物品栈对象 异常返回 null
     * @throws IllegalArgumentException 如果路径对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果文件对象不存在或不是文件则抛出异常
     * @throws IllegalArgumentException 如果文件对象后缀类型名不为 {@code yml} 则抛出异常
     * @throws MoonLakeException 如果反序列化错误则抛出异常
     */
    public static ItemStack deserializeFromFile(String path) {

        Validate.notNull(path, "The file path object is null.");

        return deserializeFromFile(new File(path));
    }

    /**
     * 将特定 YAML 文件数据反序列化为物品栈对象
     *
     * @param file 文件
     * @return 物品栈对象 异常返回 null
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果文件对象不存在或不是文件则抛出异常
     * @throws IllegalArgumentException 如果文件对象后缀类型名不为 {@code yml} 则抛出异常
     * @throws MoonLakeException 如果反序列化错误则抛出异常
     */
    @SuppressWarnings("deprecation")
    public static ItemStack deserializeFromFile(File file) {

        Validate.notNull(file, "The file object is null.");
        Validate.isTrue(file.exists() && file.isFile(), "The file not exists or not file.");
        Validate.isTrue(file.getName().endsWith(".yml"), "The file not is yml suffix.");

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        ItemBuilder itemBuilder = null;

        if(!yml.isSet("Type"))
            throw new MoonLakeException("The yml file not exists 'Type' attribute.");

        String type = yml.getString("Type");
        Material material = Material.matchMaterial(type);

        if(material == null)
            throw new MoonLakeException("The yml file 'Type' attribute is unknown material: " + type);

        if(material == Material.AIR)
            return new ItemStack(Material.AIR);

        // base
        int data = yml.isSet("Data") ? yml.getInt("Data", 0) : 0;
        int amount = yml.isSet("Amount") ? yml.getInt("Amount", 1) : 1;

        // init
        itemBuilder = MoonLakeAPI.newItemBuilder(material, data, amount);

        // displayName
        String displayName = yml.isSet("DisplayName") ? yml.getString("DisplayName", null) : null;

        if(displayName != null)
            itemBuilder.setDisplayName(displayName);

        // lore
        List<String> lore = yml.isSet("Lore") ? yml.getStringList("Lore") : null;

        if(lore != null)
            itemBuilder.setLore(StringUtil.toColor(lore));

        // enchantment
        if(yml.isSet("Enchantment")) {

            ConfigurationSection section = yml.getConfigurationSection("Enchantment");
            Set<String> keys = section != null ? section.getKeys(false) : null;

            if(keys != null && !keys.isEmpty()) {

                for(final String key : keys) {

                    Enchantment enchantment = null;
                    int level = yml.getInt("Enchantment." + key);

                    if(key.matches("([0-9])+"))
                        enchantment = Enchantment.getById(Conversions.toInt(key));
                    else
                        enchantment = Enchantment.getByName(key.toUpperCase());

                    if(enchantment == null)
                        MoonLakeAPI.getLogger().warning("The deserialize from file '" + file.getName() + "' key 'Enchantment' error enchantment: " + key);
                    else
                        itemBuilder.addEnchantment(enchantment, level);
                }
            }
        }

        // hide flag
        if(yml.isSet("HideFlag")) {

            List<String> list = yml.getStringList("HideFlag");

            if(list != null && !list.isEmpty()) {

                for(final String hideFlag : list) {

                    ItemFlag itemFlag = null;

                    try {

                        itemFlag = ItemFlag.valueOf(hideFlag.toUpperCase());
                    }
                    catch (Exception e) {
                    }

                    if(itemFlag == null)
                        MoonLakeAPI.getLogger().warning("The deserialize from file '" + file.getName() + "' key 'HideFlag' error flag: " + hideFlag);
                    else
                        itemBuilder.addFlags(itemFlag);
                }
            }
        }

        // unbreakable
        if(yml.isSet("Unbreakable")) {

            boolean result = yml.getBoolean("Unbreakable", false);

            if(result)
                itemBuilder.setUnbreakable(true);
        }

        // attribute modifiers
        if(yml.isSet("AttributeModifiers")) {

            ConfigurationSection section = yml.getConfigurationSection("AttributeModifiers");
            Set<String> keys = section != null ? section.getKeys(false) : null;

            if(keys != null && !keys.isEmpty()) {

                for(final String key : keys) {

                    AttributeModify.Type attType = AttributeModify.Type.fromType(key);

                    if(attType == null) {

                        MoonLakeAPI.getLogger().warning("The deserialize from file '" + file.getName() + "' key 'AttributeModifiers' error type: " + key);
                        continue;
                    }
                    double value = yml.getDouble("AttributeModifiers." + key + ".Value", -1d);
                    boolean percent = yml.getBoolean("AttributeModifiers." + key + ".Percent", false);
                    String slot = yml.getString("AttributeModifiers." + key + ".Slot", null);
                    AttributeModify.Slot slotObj = slot != null ? AttributeModify.Slot.fromType(slot) : AttributeModify.Slot.MAIN_HAND;
                    // add
                    itemBuilder.setAttribute(new AttributeModify(
                            attType,
                            slotObj,
                            percent ? AttributeModify.Operation.ADD_PERCENTAGE : AttributeModify.Operation.ADD_NUMBER,
                            value
                    ));
                }
            }
        }

        // nbt modifiers
        ItemStack result = itemBuilder.build(true);
        NBTCompound tag = MoonLakeAPI.getNBTLibrary().read(result);

        // skull owner
        if(yml.isSet("SkullOwner")) {

            if(material == Material.SKULL_ITEM) {

                String target = yml.getString("SkullOwner", null);

                if(target != null && !target.isEmpty())
                    tag.put("SkullOwner", target);
            }
        }

        // age
        if(yml.isSet("Age")) {

            int age = yml.getInt("Age", 6000);

            if(age != 6000)
                tag.put("Age", age);
        }

        // pickup delay
        if(yml.isSet("PickupDelay")) {

            int pickupDelay = yml.getInt("PickupDelay", -1);

            if(pickupDelay != -1)
                tag.put("PickupDelay", pickupDelay);
        }

        // write nbt
        MoonLakeAPI.getNBTLibrary().write(result, tag);

        return result;
    }
}
