package com.minecraft.moonlake.util;

import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by MoonLake on 2016/4/26.
 * @version 1.0
 * @author Month_Light
 */
public final class Util {

    private Util() {}

    /**
     * <h1>将字串符文本序列化为颜色 (&)</h1>
     * <img src="http://a2.qpic.cn/psb?/V146eXZS0CF2Hk/kvJ2xbLnUa7lHBXQI61bD05xx5DwHpyx.4C.hf2Nk7g!/b/dKwAAAAAAAAA&bo=vALxAbwC8QEDByI!&rf=viewer_4"></img>
     *
     * @param text 文本
     * @return 颜色文本
     * @throws IllegalArgumentException 参数空指针则抛出异常
     */
    public static String color(String text) {
        notNull(text, "待序列化颜色文本是 null 值");

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * <h1>将字串符文本序列化为颜色 (&)</h1>
     * <img src="http://a2.qpic.cn/psb?/V146eXZS0CF2Hk/kvJ2xbLnUa7lHBXQI61bD05xx5DwHpyx.4C.hf2Nk7g!/b/dKwAAAAAAAAA&bo=vALxAbwC8QEDByI!&rf=viewer_4"></img>
     *
     * @param textArr 文本数组
     * @return 颜色文本数组
     * @throws IllegalArgumentException 参数空指针则抛出异常
     */
    public static String[] color(String[] textArr) {
        notNull(textArr, "待序列化颜色文本数组是 null 值");

        for(int i = 0; i < textArr.length; i++) {
            textArr[i] = color(textArr[i]);
        }
        return textArr;
    }

    /**
     * <h1>将字串符文本序列化为颜色 (&)</h1>
     * <img src="http://a2.qpic.cn/psb?/V146eXZS0CF2Hk/kvJ2xbLnUa7lHBXQI61bD05xx5DwHpyx.4C.hf2Nk7g!/b/dKwAAAAAAAAA&bo=vALxAbwC8QEDByI!&rf=viewer_4"></img>
     *
     * @param textList 文本集合
     * @return 颜色文本集合
     * @throws IllegalArgumentException 参数空指针则抛出异常
     */
    public static List<String> color(List<String> textList) {
        notNull(textList, "待序列化颜色文本集合是 null 值");

        String[] textArr = textList.toArray(new String[textList.size()]);
        return Arrays.asList(color(textArr));
    }

    /**
     * <h1>格式化字串符文本</h1>
     *
     * @param key 需格式化的字串符
     * @param values 格式化的值
     * @return 格式化后的字符串
     * @throws IllegalArgumentException 参数空指针则抛出异常
     */
    public static String format(String key, Object... values) {
        notNull(key, "待格式化的字符串是 null 值");

        return String.format(key, values);
    }

    /**
     * <h1>获取当前系统的时间 (年-月-日 时:分:秒)</h1>
     *
     * @return 当前系统时间
     */
    public static String getSystemTime() {
        return getSystemTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * <h1>获取当前系统的时间</h1>
     *
     * @param format 时间格式
     * @return 当前系统时间
     * @throws IllegalArgumentException 参数空指针则抛出异常
     */
    public static String getSystemTime(String format) {
        notNull(format, "待格式化的时间格式是 null 值");

        Date date = new Date();
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * <h1>四舍五入保留位数</h1>
     *
     * @param value 需要保留的值
     * @param bit 保留的位数
     * @return 四舍五入后的值
     */
    public static double rounding(double value, int bit) {
        return new BigDecimal(value).setScale(bit, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * <h1>验证对象是否为 null 则抛出异常</h1>
     *
     * @param object Object
     */
    public static void notNull(Object object) {
        notNull(object, "The Object is Null");
    }

    /**
     * <h1>验证对象是否为 null 则抛出异常</h1>
     *
     * @param object Object
     * @param message Exception Message
     */
    public static void notNull(Object object, String message) {
        if(object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * <h1>验证字符串是否为 null 或 empty 则抛出异常</h1>
     *
     * @param str String
     */
    public static void notEmpty(String str) {
        notEmpty(str, "The String is Null or Empty");
    }

    /**
     * <h1>验证字符串是否为 null 或 empty 则抛出异常</h1>
     *
     * @param str String
     * @param message Exception Message
     */
    public static void notEmpty(String str, String message) {
        if(str == null || str.length() == 0) {
            throw new IllegalArgumentException(message);
        }
    }
}
