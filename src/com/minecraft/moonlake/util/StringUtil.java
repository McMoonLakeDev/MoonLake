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

import com.minecraft.moonlake.validate.Validate;
import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>StringUtil</h1>
 * 字符串实现类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class StringUtil {

    public final static char COLOR_CHAR = '\u0026';
    public final static String COLOR_STRING = "" + COLOR_CHAR;
    public final static Pattern UNICODE_PATTERN = Pattern.compile("\\\\u[a-zA-Z0-9]{2,4}");

    /**
     * 字符串实现类构造函数
     */
    public StringUtil() {

    }

    /**
     * <h1>将字符串按 & 颜色字符转换到颜色字符串</h1>
     *
     * <img src="http://a2.qpic.cn/psb?/V146eXZS0CF2Hk/kvJ2xbLnUa7lHBXQI61bD05xx5DwHpyx.4C.hf2Nk7g!/b/dKwAAAAAAAAA&bo=vALxAbwC8QEDByI!&rf=viewer_4" />
     *
     * @param source 字符串源
     * @return 颜色字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
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
     */
    public static String toColor(char altColorChar, String source) {

        Validate.notNull(source, "The string source object is null.");

        return ChatColor.translateAlternateColorCodes(altColorChar, source);
    }

    /**
     * <h1>将字符串按 & 颜色字符转换到颜色字符串</h1>
     *
     * <img src="http://a2.qpic.cn/psb?/V146eXZS0CF2Hk/kvJ2xbLnUa7lHBXQI61bD05xx5DwHpyx.4C.hf2Nk7g!/b/dKwAAAAAAAAA&bo=vALxAbwC8QEDByI!&rf=viewer_4" />
     *
     * @param source 字符串源
     * @return 颜色字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
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
     * <h1>将字符串按 & 颜色字符转换到颜色字符串</h1>
     *
     * <img src="http://a2.qpic.cn/psb?/V146eXZS0CF2Hk/kvJ2xbLnUa7lHBXQI61bD05xx5DwHpyx.4C.hf2Nk7g!/b/dKwAAAAAAAAA&bo=vALxAbwC8QEDByI!&rf=viewer_4" />
     *
     * @param source 字符串源
     * @return 颜色字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
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
}
