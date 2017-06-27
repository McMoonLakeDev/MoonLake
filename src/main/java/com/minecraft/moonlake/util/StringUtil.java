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
 
 
package com.minecraft.moonlake.util;

import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>StringUtil</h1>
 * 字符串实现类（详细doc待补充...）
 *
 * @version 1.2
 * @author Month_Light
 */
public final class StringUtil {

    /**
     * 颜色字符: \u0026
     */
    public final static char COLOR_CHAR = '\u0026';

    /**
     * 默认合并字符: 44
     */
    public final static char DEF_MERGE_CHAR = 44; // ansi code = ','

    /**
     * 颜色字符串: {@link #COLOR_CHAR}
     */
    public final static String COLOR_STRING = "" + COLOR_CHAR;

    /**
     * Unicode 码匹配模式对象
     */
    public final static Pattern UNICODE_PATTERN = Pattern.compile("\\\\u[a-zA-Z0-9]{2,4}");

    /**
     * 字符编码: UTF-8
     */
    public final static Charset UTF_8 = Charset.forName("UTF-8");

    /**
     * 字符串实现类构造函数
     */
    private StringUtil() {

    }

    /**
     * <h1>将字符串按 {@link #COLOR_CHAR} 颜色字符转换到颜色字符串</h1>
     *
     * @param source 字符串源
     * @return 颜色字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     * @see ColorTableDoc
     */
    public static String toColor(String source) {

        return toColor(COLOR_CHAR, source);
    }

    /**
     * 将字符串按指定颜色字符转换到颜色字符串
     *
     * @param source 字符串源
     * @param altColorChar 颜色字符
     * @return 颜色字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     * @see ColorTableDoc
     */
    public static String toColor(char altColorChar, String source) {

        Validate.notNull(source, "The string source object is null.");

        return ChatColor.translateAlternateColorCodes(altColorChar, source);
    }

    /**
     * <h1>将字符串按 {@link #COLOR_CHAR} 颜色字符转换到颜色字符串</h1>
     *
     * @param source 字符串源
     * @return 颜色字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     * @see ColorTableDoc
     */
    public static String[] toColor(String... source) {

        return toColor(COLOR_CHAR, source);
    }

    /**
     * 将字符串按指定颜色字符转换到颜色字符串
     *
     * @param source 字符串源
     * @param altColorChar 颜色字符
     * @return 颜色字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     * @see ColorTableDoc
     */
    public static String[] toColor(char altColorChar, String... source) {

        Validate.notNull(source, "The string source object is null.");

        int index = 0;
        String[] strColorArray = new String[source.length];

        for(final String str : source) {

            strColorArray[index++] = ChatColor.translateAlternateColorCodes(altColorChar, str);
        }
        return strColorArray;
    }

    /**
     * <h1>将字符串按 {@link #COLOR_CHAR} 颜色字符转换到颜色字符串</h1>
     *
     * @param source 字符串源
     * @return 颜色字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     * @see ColorTableDoc
     */
    public static List<String> toColor(Collection<? extends String> source) {

        return toColor(COLOR_CHAR, source);
    }

    /**
     * 将字符串按指定颜色字符转换到颜色字符串
     *
     * @param source 字符串源
     * @param altColorChar 颜色字符
     * @return 颜色字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     * @see ColorTableDoc
     */
    public static List<String> toColor(char altColorChar, Collection<? extends String> source) {

        Validate.notNull(source, "The string source object is null.");

        List<String> strColorList = new ArrayList<>();

        for(final String str : source) {

            strColorList.add(ChatColor.translateAlternateColorCodes(altColorChar, str));
        }
        return strColorList;
    }

    /**
     * 将颜色字符串转换到无颜色字符字符串
     *
     * @param source 字符串源
     * @return 无颜色字符字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static String stripColor(String source) {

        Validate.notNull(source, "The string source object is null.");

        return ChatColor.stripColor(source);
    }

    /**
     * 将颜色字符串转换到无颜色字符字符串
     *
     * @param source 字符串源
     * @return 无颜色字符字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static String[] stripColor(String... source) {

        Validate.notNull(source, "The string source object is null.");

        int index = 0;
        String[] strColorArray = new String[source.length];

        for(final String str : source) {

            strColorArray[index++] = ChatColor.stripColor(str);
        }
        return strColorArray;
    }

    /**
     * 将颜色字符串转换到无颜色字符字符串
     *
     * @param source 字符串源
     * @return 无颜色字符字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static List<String> stripColor(Collection<? extends String> source) {

        Validate.notNull(source, "The string source object is null.");

        List<String> strColorList = new ArrayList<>();

        for(final String str : source) {

            strColorList.add(stripColor(str));
        }
        return strColorList;
    }

    /**
     * 将字符串进行格式化处理
     *
     * @param format 格式化
     * @param args 参数
     * @return 格式化后的字符串
     * @throws IllegalArgumentException 如果格式化字符串对象为 {@code null} 则抛出异常
     */
    public static String format(String format, Object... args) {

        Validate.notNull(format, "The string format object is null.");

        return String.format(format, args);
    }

    /**
     * 将字符串进行消息格式化处理
     *
     * @param format 格式化
     * @param args 参数
     * @return 格式化后的字符串
     * @throws IllegalArgumentException 如果格式化字符串对象为 {@code null} 则抛出异常
     */
    public static String messageFormat(String format, Object... args) {

        Validate.notNull(format, "The string format object is null.");

        return MessageFormat.format(format, args);
    }

    /**
     * 获取系统当前的时间 (yyyy-MM-dd HH:mm:ss)
     *
     * @see StringUtil#getSystemTime(String)
     * @return 系统时间
     */
    public static String getSystemTime() {

        return getSystemTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取指定格式的系统当前的时间
     *
     * @param format 时间格式
     * @return 系统时间
     * @throws IllegalArgumentException 如果格式字符串对象为 {@code null} 则抛出异常
     */
    public static String getSystemTime(String format) {

        Validate.notNull(format, "The string format object is null.");

        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 将指定双精度浮点数按保留 {@code 2} 位数四舍五入
     *
     * @param value 值
     * @return 最终值
     */
    public static double rounding(double value) {

        return rounding(value, 2);
    }

    /**
     * 将指定双精度浮点数按指定位数四舍五入
     *
     * @param value 值
     * @param bit 保留位数
     * @return 最终值
     */
    public static double rounding(double value, int bit) {

        return new BigDecimal(value).setScale(bit, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 将指定包含 Unicode 码的字符串转换为中文
     *
     * @param unicode 包含 Unicode 码的字符串
     * @return 转换中文后的字符串
     */
    public static String decodeUnicode(final String unicode) {

        if(unicode == null)
            return null;

        Matcher matcher = UNICODE_PATTERN.matcher(unicode);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String group = matcher.group();
            group = group.substring(group.indexOf("\\u") + 2, group.length());
            matcher.appendReplacement(buffer, String.valueOf((char) Integer.parseInt(group, 16)));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * 获取指定对象是否为 {@code null}
     *
     * @param obj 对象
     * @return 为 {@code null} 则返回 true
     */
    public static boolean isNull(Object obj) {

        return obj == null;
    }

    /**
     * 获取指定对象是否不为 {@code null}
     *
     * @param obj 对象
     * @return 不为 {@code null} 则返回 true
     */
    public static boolean isNotNull(Object obj) {

        return obj != null;
    }

    /**
     * 获取指定字符串是否为 {@code null | empty}
     *
     * @param str 字符串
     * @return 为则返回 true
     */
    public static boolean isEmpty(String str) {

        return isNull(str) || str.trim().isEmpty();
    }

    /**
     * 获取指定对象数组是否为 {@code null | empty}
     *
     * @param objArray 对象数组
     * @return 为则返回 true
     */
    public static boolean isEmpty(Object[] objArray) {

        return isNull(objArray) || objArray.length == 0;
    }

    /**
     * 获取指定集合对象是否为 {@code null | empty}
     *
     * @param collection 集合对象
     * @return 为则返回 true
     */
    public static boolean isEmpty(Collection<?> collection) {

        return isNull(collection) || collection.isEmpty();
    }

    /**
     * 判断指定两个字符串对象是否完全相等
     *
     * @param str1 字符串 1
     * @param str2 字符串 2
     * @return 完全相等则返回 true
     */
    public static boolean isEquals(String str1, String str2) {

        return (isEmpty(str1) && isEmpty(str2)) || (isNotNull(str1) && str1.equals(str2));
    }

    /**
     * 判断指定两个对象是否完全相等
     *
     * @param obj1 对象 1
     * @param obj2 对象 2
     * @return 完全相等则返回 true
     */
    public static boolean isEquals(Object obj1, Object obj2) {

        return (isNull(obj1) && isNull(obj2) || (isNotNull(obj1) && obj1.equals(obj2)));
    }

    /**
     * 判断指定两个字符串对象是否完全相等 (忽略大小写)
     *
     * @param str1 字符串 1
     * @param str2 字符串 2
     * @return 完全相等 (忽略大小写) 则返回 true
     */
    public static boolean isEqualsIgnoreCase(String str1, String str2) {

        return (isEmpty(str1) && isEmpty(str2)) || (isNotNull(str1) && str1.equalsIgnoreCase(str2));
    }

    /**
     * 判断指定字符串是否符合指定正则表达式匹配
     *
     * @param str 字符串
     * @param regex 正则表达式匹配
     * @return 匹配则返回 true
     * @throws IllegalArgumentException 如果匹配的字符串对象为 {@code null} 则抛出异常
     */
    public static boolean isMatches(String str, String regex) {

        return Validate.checkNotNull(str).matches(regex);
    }

    /**
     * 获取指定字符串是否为整数
     *
     * @param str 字符串
     * @return 是否为整数
     */
    public static boolean isInteger(String str) {

        if(isNotNull(str)) try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    /**
     * 获取指定字符串是否为双精度浮点数
     *
     * @param str 字符串
     * @return 是否为双精度浮点数
     */
    public static boolean isDouble(String str) {

        if(isNotNull(str)) try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    /**
     * 将指定字符串解析为整数
     *
     * @param str 字符串
     * @return 整数, 异常返回 0
     */
    public static int parseInt(String str) {

        return parseInt(str, 0);
    }

    /**
     * 将指定字符串解析为整数
     *
     * @param str 字符串
     * @param def 默认值
     * @return 整数, 异常返回默认值
     */
    public static int parseInt(String str, int def) {

        return parseInt((Object) str, def);
    }

    /**
     * 将指定对象解析为整数
     *
     * @param obj 对象
     * @return 整数, 异常返回 0
     */
    public static int parseInt(Object obj) {

        return parseInt(obj, 0);
    }

    /**
     * 将指定对象解析为整数
     *
     * @param obj 对象
     * @param def 默认值
     * @return 整数, 异常返回默认值
     */
    public static int parseInt(Object obj, int def) {

        if(obj instanceof Number)
            return ((Number) obj).intValue();

        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * 将指定字符串解析为长整数
     *
     * @param str 字符串
     * @return 长整数, 异常返回 0
     */
    public static long parseLong(String str) {

        return parseLong(str, 0L);
    }

    /**
     * 将指定字符串解析为长整数
     *
     * @param str 字符串
     * @param def 默认值
     * @return 长整数, 异常返回默认值
     */
    public static long parseLong(String str, long def) {

        return parseLong((Object) str, def);
    }

    /**
     * 将指定对象解析为长整数
     *
     * @param obj 对象
     * @return 长整数, 异常返回 0
     */
    public static long parseLong(Object obj) {

        return parseLong(obj, 0L);
    }

    /**
     * 将指定对象解析为长整数
     *
     * @param obj 对象
     * @param def 默认值
     * @return 长整数, 异常返回默认值
     */
    public static long parseLong(Object obj, long def) {

        if(obj instanceof Number)
            return ((Number) obj).longValue();

        try {
            return Long.parseLong(obj.toString());
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * 将指定字符串解析为单精度浮点数
     *
     * @param str 字符串
     * @return 单精度浮点数, 异常返回 0
     */
    public static float parseFloat(String str) {

        return parseFloat(str, 0f);
    }

    /**
     * 将指定字符串解析为单精度浮点数
     *
     * @param str 字符串
     * @param def 默认值
     * @return 单精度浮点数, 异常返回默认值
     */
    public static float parseFloat(String str, float def) {

        return parseFloat((Object) str, def);
    }

    /**
     * 将指定对象解析为单精度浮点数
     *
     * @param obj 对象
     * @return 单精度浮点数, 异常返回 0
     */
    public static float parseFloat(Object obj) {

        return parseFloat(obj, 0f);
    }

    /**
     * 将指定对象解析为单精度浮点数
     *
     * @param obj 对象
     * @param def 默认值
     * @return 单精度浮点数, 异常返回默认值
     */
    public static float parseFloat(Object obj, float def) {

        if(obj instanceof Number)
            return ((Number) obj).floatValue();

        try {
            return Float.parseFloat(obj.toString());
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * 将指定字符串解析为双精度浮点数
     *
     * @param str 字符串
     * @return 双精度浮点数, 异常返回 0
     */
    public static double parseDouble(String str) {

        return parseDouble(str, 0d);
    }

    /**
     * 将指定字符串解析为双精度浮点数
     *
     * @param str 字符串
     * @param def 默认值
     * @return 双精度浮点数, 异常返回默认值
     */
    public static double parseDouble(String str, double def) {

        return parseDouble((Object) str, def);
    }

    /**
     * 将指定对象解析为双精度浮点数
     *
     * @param obj 对象
     * @return 双精度浮点数, 异常返回 0
     */
    public static double parseDouble(Object obj) {

        return parseDouble(obj, 0d);
    }

    /**
     * 将指定对象解析为双精度浮点数
     *
     * @param obj 对象
     * @param def 默认值
     * @return 双精度浮点数, 异常返回默认值
     */
    public static double parseDouble(Object obj, double def) {

        if(obj instanceof Number)
            return ((Number) obj).doubleValue();

        try {
            return Double.parseDouble(obj.toString());
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * 将指定字符串数组按 {@link #DEF_MERGE_CHAR} 合并为单个字符串对象
     *
     * @param strArray 字符串数组
     * @return 合并后的字符串
     */
    public static String arrayMerge(String[] strArray) {

        return arrayMerge(strArray, DEF_MERGE_CHAR);
    }

    /**
     * 将指定字符串数组按指定字符合并为单个字符串对象
     *
     * @param strArray 字符串数组
     * @param mergeChar 合并字符
     * @return 合并后的字符串
     */
    public static String arrayMerge(String[] strArray, char mergeChar) {

        if(isEmpty(strArray))
            return null;

        StringBuilder builder = new StringBuilder();

        for(String str : strArray)
            builder.append(str).append(mergeChar);

        builder.deleteCharAt(builder.length() - 1); // delete last merge char
        return builder.toString();
    }

    /**
     * 将指定字符串集合转换到字符串数组对象
     *
     * @param strCollection 字符串集合
     * @return 字符串数组
     */
    public static String[] collectionToArray(Collection<? extends String> strCollection) {

        return collectionToArray(strCollection, strCollection.size());
    }

    /**
     * 将指定字符串集合转换到指定大小长度的字符串数组对象
     *
     * @param strCollection 字符串集合
     * @param size 大小
     * @return 字符串数组
     */
    public static String[] collectionToArray(Collection<? extends String> strCollection, int size) {

        return !isNull(strCollection) ? strCollection.toArray(new String[size]) : new String[0];
    }

    /**
     * 将指定字符串集合按 {@link #DEF_MERGE_CHAR} 合并为单个字符串对象
     *
     * @param strCollection 字符串集合
     * @return 合并后的字符串
     */
    public static String collectionMerge(Collection<? extends String> strCollection) {

        return collectionMerge(strCollection, DEF_MERGE_CHAR);
    }

    /**
     * 将指定字符串集合按指定字符合并为单个字符串对象
     *
     * @param strCollection 字符串集合
     * @param mergeChar 合并字符
     * @return 合并后的字符串
     */
    public static String collectionMerge(Collection<? extends String> strCollection, char mergeChar) {

        return arrayMerge(collectionToArray(strCollection), mergeChar);
    }

    /**
     * 获取指定配置对象的指定 Bukkit 配置序列化类指定键的对象
     *
     * @param clazz 类
     * @param configuration 配置对象
     * @param key 键
     * @param <T> 类型
     * @return 类对象
     * @throws IllegalArgumentException 如果类对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果配置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果键对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果配置序列化类没有被注册获取 Map 后没有 {@code deserialize | valueOf} 函数则抛出异常
     */
    public static <T extends ConfigurationSerializable> T deserializeConfigurationClass(Class<T> clazz, Configuration configuration, String key) {

        return deserializeConfigurationClass(clazz, configuration, key, null);
    }

    /**
     * 获取指定配置对象的指定 Bukkit 配置序列化类指定键的对象
     *
     * @param clazz 类
     * @param configuration 配置对象
     * @param key 键
     * @param def 默认值
     * @param <T> 类型
     * @return 类对象
     * @throws IllegalArgumentException 如果类对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果配置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果键对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果配置序列化类没有被注册获取 Map 后没有 {@code deserialize | valueOf} 函数则抛出异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends ConfigurationSerializable> T deserializeConfigurationClass(Class<T> clazz, Configuration configuration, String key, T def) {

        Validate.notNull(clazz, "The class object is null.");
        Validate.notNull(configuration, "The configuration object is null.");
        Validate.notNull(key, "The configuration key object is null.");

        Object obj = configuration.get(key, null);

        if(obj == null)
            return def;

        if(clazz.isInstance(obj))
            return (T) obj;
        else if(obj instanceof Map) {
            // map deserialize
            try {
                Method method = clazz.getMethod("deserialize");
                if(method == null) clazz.getMethod("valueOf");
                if(method == null) throw new MoonLakeException("The value is map, but class not exists 'deserialize' or 'valueOf' method.");
                // deserialize
                return (T) method.invoke(null, obj);
            } catch (Exception e) {
                throw new MoonLakeException(e.getMessage(), e);
            }
        } else {
            return def;
        }
    }

    /**
     * 判断指定字符串是否为 {@code null | empty} 则返回 {@code null} 否则返回源
     *
     * @param string 字符串
     * @return {@code null | string}
     */
    public static String emptyToNull(String string) {
        return isNullOrEmpty(string) ? null : string;
    }

    /**
     * 判断指定字符串是否为 {@code null} 则返回 {@code empty} 否则返回源
     *
     * @param string 字符串
     * @return {@code empty | string}
     */
    public static String nullToEmpty(String string) {
        return isNull(string) ? "" : string;
    }

    /**
     * 判断指定字符串是否为 {@code null | empty} 值
     *
     * @param string 字符串
     * @return 是否为 {@code null | empty} 值
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
