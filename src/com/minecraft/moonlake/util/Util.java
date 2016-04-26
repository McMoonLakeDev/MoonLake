package com.minecraft.moonlake.util;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by MoonLake on 2016/4/26.
 * @version 1.0
 * @author Month_Light
 */
public final class Util {

    private Util() {}

    /**
     * 将字串符文本序列化为颜色 (&)
     *
     * @param text 文本
     * @return 颜色文本
     */
    public static String color(String text) {
        notNull(text, "待序列化颜色文本是 null 值");

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * 将字串符文本数组序列化为颜色 (&)
     *
     * @param textArr 文本数组
     * @return 颜色文本数组
     */
    public static String[] color(String[] textArr) {
        notNull(textArr, "待序列化颜色文本数组是 null 值");

        for(int i = 0; i < textArr.length; i++) {
            textArr[i] = color(textArr[i]);
        }
        return textArr;
    }

    /**
     * 将字符串文本集合序列化为颜色 (&)
     *
     * @param textList 文本集合
     * @return 颜色文本集合
     */
    public static List<String> color(List<String> textList) {
        notNull(textList, "待序列化颜色文本集合是 null 值");

        String[] textArr = textList.toArray(new String[textList.size()]);
        return Arrays.asList(color(textArr));
    }

    /**
     * 验证对象是否为 null 则抛出异常
     *
     * @param object Object
     */
    public static void notNull(Object object) {
        notNull(object, "The Object is Null");
    }

    /**
     * 验证对象是否为 null 则抛出异常
     *
     * @param object Object
     * @param message Exception Message
     */
    public static void notNull(Object object, String message) {
        if(object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
