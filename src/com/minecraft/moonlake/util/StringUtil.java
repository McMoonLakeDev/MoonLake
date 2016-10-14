package com.minecraft.moonlake.util;

import com.minecraft.moonlake.validate.Validate;
import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <h1>StringUtil</h1>
 * 字符串实现类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class StringUtil {

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

        return toColor('&', source);
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

        return toColor('&', source);
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

        return toColor('&', source);
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

        List<String> strColorList = source.stream().map(str -> ChatColor.translateAlternateColorCodes(altColorChar, str)).collect(Collectors.toList());

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

        List<String> strColorList = source.stream().map((Function<String, String>) ChatColor::stripColor).collect(Collectors.toList());

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
}
