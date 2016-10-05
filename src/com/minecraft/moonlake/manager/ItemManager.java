package com.minecraft.moonlake.manager;

import com.google.common.io.BaseEncoding;
import com.minecraft.moonlake.api.nbt.NBTCompound;
import com.minecraft.moonlake.api.nbt.NBTFactory;
import com.minecraft.moonlake.data.NBTTagData;
import com.minecraft.moonlake.data.NBTTagDataWrapped;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

/**
 * Created by MoonLake on 2016/7/17.
 */
public class ItemManager extends MoonLakeManager {

    private ItemManager() {

    }

    /**
     * 设置物品栈 NBT 标签指定键的值
     *
     * @param itemStack 物品栈
     * @param key 键
     * @param value 值
     * @return 改变 NBT 标签属性后的物品栈对象 异常返回 源
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @deprecated 已过期, 详情查看新版 {@link com.minecraft.moonlake.api.nbt.NBTLibrary}
     */
    @Deprecated
    public static ItemStack setTagValue(ItemStack itemStack, String key, Object value) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        NBTCompound nbtCompound = NBTFactory.get().readSafe(itemStack);
        nbtCompound.put(key, value);

        NBTFactory.get().write(itemStack, nbtCompound);

        return itemStack;
    }

    /**
     * 获取物品栈 NBT 标签指定键的值
     *
     * @param itemStack 物品栈
     * @param key 键
     * @return 物品栈指定 NBT 标签属性的值 异常或没有返回 null
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @deprecated 已过期, 详情查看新版 {@link com.minecraft.moonlake.api.nbt.NBTLibrary}
     */
    @Deprecated
    public static NBTTagData getTagValue(ItemStack itemStack, String key) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        NBTCompound nbtCompound = NBTFactory.get().readSafe(itemStack);
        Object value = nbtCompound.get(key);

        if(value == null) {

            return null;
        }
        return new NBTTagDataWrapped(value);
    }

    /**
     * 获取物品栈 NBT 标签指定键的字符串值
     *
     * @param itemStack 物品栈
     * @param key 键
     * @return 物品栈指定 NBT 标签属性的字符串值 异常或没有返回 null
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @deprecated 已过期, 详情查看新版 {@link com.minecraft.moonlake.api.nbt.NBTLibrary}
     */
    @Deprecated
    public static String getTagStringValue(ItemStack itemStack, String key) {

        NBTTagData nbtTagData = getTagValue(itemStack, key);
        return nbtTagData == null ? null : nbtTagData.asString();
    }

    /**
     * 获取物品栈是否拥有 NBT 标签
     *
     * @param itemStack 物品栈
     * @return true 拥有 NBT 标签属性 else 没有
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @deprecated 已过期, 详情查看新版 {@link com.minecraft.moonlake.api.nbt.NBTLibrary}
     */
    @Deprecated
    public static boolean hasTag(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        return NBTFactory.get().read(itemStack) != null;
    }

    /**
     * 获取物品栈是否拥有 NBT 标签指定键
     *
     * @param itemStack 物品栈
     * @param key 键
     * @return true 拥有 NBT 标签属性键 else 没有
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @deprecated 已过期, 详情查看新版 {@link com.minecraft.moonlake.api.nbt.NBTLibrary}
     */
    @Deprecated
    public static boolean hasTagKey(ItemStack itemStack, String key) {

        return getTagValue(itemStack, key) != null;
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

        return source != null && target != null && source.getType() == target.getType() && source.getData().getData() == target.getData().getData();
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

        return compare(source, new ItemStack(target, 1, (byte)0));
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

        return source != null && target != null && source.getAmount() == target.getAmount();
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

        return (sourceName == null && targetName == null) || (sourceName != null && targetName != null && sourceName.equals(targetName));
    }

    /**
     * 比较指定物品栈是否符合目标物品栈的属性
     *
     * @param source 源物品栈
     * @param target 目标物品栈
     * @return true 则两个物品栈符合属性 else 不符合
     */
    public static boolean compareMeta(ItemStack source, ItemStack target) {

        return source != null && target != null && source.isSimilar(target);
    }

    /**
     * 比较指定物品栈是否完全符合目标物品栈
     *
     * @param source 源物品栈
     * @param target 目标物品栈
     * @return true 则两个物品栈完全符合 else 不符合
     */
    public static boolean compareAll(ItemStack source, ItemStack target) {

        return compare(source, target) && compareMeta(source, target);
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

                Class<?> ItemStack = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
                Class<?> NBTTagCompound = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
                Class<?> CraftItemStack = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
                Class<?> NBTCompressedStreamTools = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTCompressedStreamTools");

                Object NBTTag = Reflect.getConstructor(NBTTagCompound).newInstance();

                Method asNMSCopy = Reflect.getMethod(CraftItemStack, "asNMSCopy", ItemStack.class);
                Object NMSItemStack = asNMSCopy.invoke(null, itemStack);

                Reflect.getMethod(ItemStack, "save", NBTTagCompound).invoke(NMSItemStack, NBTTag);
                outputStream = new ByteArrayOutputStream();

                Method a = Reflect.getMethod(NBTCompressedStreamTools, "a", NBTTagCompound, OutputStream.class);
                a.invoke(null, NBTTag, outputStream);

                return BaseEncoding.base64().encode(outputStream.toByteArray());
            }
            catch (Exception e) {

                getMain().getMLogger().warn("序列化物品栈时异常: " + e.getMessage());
            }
            finally {

                if(outputStream != null) {

                    try {

                        outputStream.close();
                    }
                    catch (Exception e) {

                        e.printStackTrace();
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

                Class<?> ItemStack = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
                Class<?> NBTTagCompound = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
                Class<?> CraftItemStack = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
                Class<?> NBTCompressedStreamTools = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTCompressedStreamTools");

                inputStream = new ByteArrayInputStream(BaseEncoding.base64().decode(data));

                Method a = Reflect.getMethod(NBTCompressedStreamTools, "a", InputStream.class);
                Object NBTTag = a.invoke(null, inputStream);

                Method createStack = Reflect.getMethod(ItemStack, "createStack", NBTTagCompound);
                Object NMSItemStack = createStack.invoke(null, NBTTag);

                Method asBukkitCopy = Reflect.getMethod(CraftItemStack, "asBukkitCopy", ItemStack);
                return (ItemStack)asBukkitCopy.invoke(null, NMSItemStack);
            }
            catch (Exception e) {

                getMain().getMLogger().warn("反序列化字符串数据时异常: " + e.getMessage());
            }
            finally {

                if(inputStream != null) {

                    try {

                        inputStream.close();
                    }
                    catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
