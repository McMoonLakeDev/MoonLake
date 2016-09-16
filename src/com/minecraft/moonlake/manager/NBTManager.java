package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.data.Conversions;
import com.minecraft.moonlake.data.NBTTagData;
import com.minecraft.moonlake.data.NBTTagDataWrapped;
import com.minecraft.moonlake.reflect.Reflect;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by MoonLake on 2016/7/17.
 */
public class NBTManager extends MoonLakeManager {

    public final static Pattern NBTTAG_NUMBER_PATTERN;

    static {

        NBTTAG_NUMBER_PATTERN = Pattern.compile("(([0-9]+)([bB|sS|lL|fF|dD|]?)|([-+]?)([0-9]+)([bB|sS|lL|fF|dD|]?))|(([0-9]+)|([-+]?)([0-9]+))");
    }

    private NBTManager() {

    }

    /**
     * 将 NBT 标签属性的字符串值转换到 NBT 数据对象
     *
     * @param data 字符串数据
     * @return NBT 数据对象 异常返回 null
     */
    public static NBTTagData conversionNBTData(String data) {

        if(isNBTTagNumber(data)) {
            // the nbt data is number
            if(data.matches("([0-9]+)b|([-+]?)([0-9]+)b")) {
                // nbt byte
                return new NBTTagDataWrapped(Conversions.toByte(data.replace("b", "")));
            }
            else if(data.matches("([0-9]+)s|([-+]?)([0-9]+)s")) {
                // nbt short
                return new NBTTagDataWrapped(Conversions.toShort(data.replace("s", "")));
            }
            else if(data.matches("([0-9]+)|([-+]?)([0-9]+)")) {
                // nbt int
                return new NBTTagDataWrapped(Conversions.toInt(data.replace(" ", "")));
            }
            else if(data.matches("([0-9]+)L|([-+]?)([0-9]+)L")) {
                // nbt long
                return new NBTTagDataWrapped(Conversions.toLong(data.replace("L", "")));
            }
            else if(data.matches("([0-9]+)f|([-+]?)([0-9]+)f")) {
                // nbt float
                return new NBTTagDataWrapped(Conversions.toFloat(data.replace("f", "")));
            }
            else if(data.matches("([0-9]+)d|([-+]?)([0-9]+)d")) {
                // nbt double
                return new NBTTagDataWrapped(Conversions.toDouble(data.replace("d", "")));
            }
        }
        return null;
    }

    /**
     * 获取指定 Object 类型的 NBT 标签对象类
     *
     * @param object 值
     * @return NBT 标签对象类 异常返回 null
     */
    public static Class<?> fromValueObject(Object object) {

        Class<?> value = null;

        try {

            if(object instanceof Byte) {

                value = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagByte");
            }
            else if(object instanceof Short) {

                value = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagShort");
            }
            else if(object instanceof Integer) {

                value = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagInt");
            }
            else if(object instanceof Long) {

                value = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagLong");
            }
            else if(object instanceof Float) {

                value = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagFloat");
            }
            else if(object instanceof Double) {

                value = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagDouble");
            }
            else if(object instanceof Byte[]) {

                value = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagByteArray");
            }
            else if(object instanceof String) {

                value = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagString");
            }
            else if(object instanceof List) {

                value = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagList");
            }
            else if(object instanceof Integer[]) {

                value = Reflect.PackageType.MINECRAFT_SERVER.getClass("NBTTagIntArray");
            }
        }
        catch (Exception e) {

            getMain().getMLogger().warn("获取 NBT 标签属性类时异常: " + e.getMessage());
        }
        return value;
    }

    /**
     * 判断指定字符串是否是 NBT 整数标签对象
     *
     * @param baseString 字符串
     * @return true 是 NBT 整数标签对象的值 else 不是
     */
    public static boolean isNBTTagNumber(String baseString) {

        return baseString != null && baseString.matches(NBTTAG_NUMBER_PATTERN.pattern());
    }
}
