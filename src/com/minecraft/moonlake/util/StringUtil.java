package com.minecraft.moonlake.util;

import com.minecraft.moonlake.property.ReadOnlyObjectProperty;
import com.minecraft.moonlake.property.ReadOnlyStringProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.ChatColor;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by MoonLake on 2016/9/13.
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
    public static ReadOnlyStringProperty toColor(String source) {

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
    public static ReadOnlyStringProperty toColor(char altColorChar, String source) {

        Validate.notNull(source, "The string source object is null.");

        return new SimpleStringProperty(ChatColor.translateAlternateColorCodes(altColorChar, source));
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
    public static ReadOnlyObjectProperty<String[]> toColor(String... source) {

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
    public static ReadOnlyObjectProperty<String[]> toColor(char altColorChar, String... source) {

        Validate.notNull(source, "The string source object is null.");

        int index = 0;
        String[] strColorArray = new String[source.length];

        for(final String str : source) {

            strColorArray[index++] = ChatColor.translateAlternateColorCodes(altColorChar, str);
        }
        return new SimpleObjectProperty<>(strColorArray);
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
    public static ReadOnlyObjectProperty<List<String>> toColor(Collection<? extends String> source) {

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
    public static ReadOnlyObjectProperty<List<String>> toColor(char altColorChar, Collection<? extends String> source) {

        Validate.notNull(source, "The string source object is null.");

        List<String> strColorList = source.stream().map(str -> ChatColor.translateAlternateColorCodes(altColorChar, str)).collect(Collectors.toList());

        return new SimpleObjectProperty<>(strColorList);
    }

    /**
     * 将颜色字符串转换到无颜色字符字符串
     *
     * @param source 字符串源
     * @return 无颜色字符字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static ReadOnlyStringProperty stripColor(String source) {

        Validate.notNull(source, "The string source object is null.");

        return new SimpleStringProperty(ChatColor.stripColor(source));
    }

    /**
     * 将颜色字符串转换到无颜色字符字符串
     *
     * @param source 字符串源
     * @return 无颜色字符字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static ReadOnlyObjectProperty<String[]> stripColor(String... source) {

        Validate.notNull(source, "The string source object is null.");

        int index = 0;
        String[] strColorArray = new String[source.length];

        for(final String str : source) {

            strColorArray[index++] = ChatColor.stripColor(str);
        }
        return new SimpleObjectProperty<>(strColorArray);
    }

    /**
     * 将颜色字符串转换到无颜色字符字符串
     *
     * @param source 字符串源
     * @return 无颜色字符字符串
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     */
    public static ReadOnlyObjectProperty<List<String>> stripColor(Collection<? extends String> source) {

        Validate.notNull(source, "The string source object is null.");

        List<String> strColorList = source.stream().map((Function<String, String>) ChatColor::stripColor).collect(Collectors.toList());

        return new SimpleObjectProperty<>(strColorList);
    }

    /**
     * 将字符串进行格式化处理
     *
     * @param format 格式化
     * @param args 参数
     * @return 格式化后的字符串
     * @throws IllegalArgumentException 如果格式化字符串对象为 {@code null} 则抛出异常
     */
    public static ReadOnlyStringProperty format(String format, Object... args) {

        Validate.notNull(format, "The string format object is null.");

        return new SimpleStringProperty(String.format(format, args));
    }

    /**
     * 将字符串进行消息格式化处理
     *
     * @param format 格式化
     * @param args 参数
     * @return 格式化后的字符串
     * @throws IllegalArgumentException 如果格式化字符串对象为 {@code null} 则抛出异常
     */
    public static ReadOnlyStringProperty messageFormat(String format, Object... args) {

        Validate.notNull(format, "The string format object is null.");

        return new SimpleStringProperty(MessageFormat.format(format, args));
    }

    /**
     * 获取系统当前的时间 (yyyy-MM-dd HH:mm:ss)
     *
     * @see StringUtil#getSystemTime(String)
     * @return 系统时间
     */
    public static ReadOnlyStringProperty getSystemTime() {

        return getSystemTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取指定格式的系统当前的时间
     *
     * @param format 时间格式
     * @return 系统时间
     * @throws IllegalArgumentException 如果格式字符串对象为 {@code null} 则抛出异常
     */
    public static ReadOnlyStringProperty getSystemTime(String format) {

        Validate.notNull(format, "The string format object is null.");

        DateFormat dateFormat = new SimpleDateFormat(format);

        return new SimpleStringProperty(dateFormat.format(new Date()));
    }
}
