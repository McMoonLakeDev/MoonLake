package com.minecraft.moonlake._temp.util;

import org.bukkit.ChatColor;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by MoonLake on 2016/4/26.
 * @version 1.1.2
 * @author Month_Light
 */
public class Util {

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
     * <h1>将颜色字符串进行反序列化</h1>
     *
     * @param color 颜色文本
     * @return 清除颜色后的文本
     * @throws IllegalArgumentException 参数空指针则抛出异常
     */
    public static String fColor(String color) {
        notNull(color, "待反序列化的颜色文本是 null 值");

        return color.replaceAll("&([0-9a-fA-Fk-oK-OrR]?)|§([0-9a-fA-Fk-oK-OrR]?)", "");
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
     * <h1>格式化字符串文本</h1>
     *
     * @param key 需格式化的字串符
     * @param values 格式化的值
     * @return 格式化后的字符串
     * @throws IllegalArgumentException 参数空指针则抛出异常
     */
    public static String mformat(String key, Object... values) {
        notNull(key, "待格式化的字符串是 null 值");

        return MessageFormat.format(key, values);
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

    /**
     * 将指定字符串源转换到缓冲图片对象
     *
     * @param font 字体
     * @param string 字符串
     * @return  缓冲图片对象
     */
    public static BufferedImage stringToBufferedImage(Font font, String... string) {

        BufferedImage image = getStringImage(font, string);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.black);
        graphics.setFont(font);

        FontMetrics fontMetrics = graphics.getFontMetrics();

        for(int i = 0; i < string.length; i++) {

            graphics.drawString(string[i], 0, i * fontMetrics.getHeight() + 15);
        }
        graphics.dispose();

        return image;
    }

    /**
     * 获取字符串所占图片的宽度、高度的缓存图片对象
     *
     * @param font 字体
     * @param strs 字符串源
     * @return 缓存图片对象
     */
    public static BufferedImage getStringImage(Font font, String... strs) {

        BufferedImage image = new BufferedImage(1, 1, 6);
        Graphics graphics = image.getGraphics();
        graphics.setFont(font);

        FontRenderContext fontRenderContext = graphics.getFontMetrics().getFontRenderContext();
        Rectangle2D rectangle2D = font.getStringBounds(getStringArrayMaxLengthString(strs), fontRenderContext);
        graphics.dispose();

        int width = (int)Math.ceil(rectangle2D.getWidth());
        int height = 0;

        for(int i = 0; i < strs.length; i++) {

            height += (int)Math.ceil(rectangle2D.getHeight());
        }
        return new BufferedImage(width, height, 6);
    }

    /**
     * 获取字符串数据最大长度的字符串
     *
     * @param strs 字符串数组
     * @return 最大长度的字符串
     */
    public static String getStringArrayMaxLengthString(String[] strs) {

        java.util.List<Integer> list = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();

        for(int i = 0; i < strs.length; i++) {

            list.add(strs[i].length());
            map.put(strs[i].length(), strs[i]);
        }
        Collections.sort(list);

        return map.get(list.get(list.size() - 1));
    }
}
