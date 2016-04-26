package com.minecraft.moonlake.util;

/**
 * Created by MoonLake on 2016/4/26.
 * @version 1.0
 * @author Month_Light
 */
public final class Util {

    private Util() {}

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
