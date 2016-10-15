package com.minecraft.moonlake.json;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * <h1>JsonHelper</h1>
 * Json 帮助器（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class JsonHelper {

    /**
     * Json 帮助器类构造函数
     */
    private JsonHelper() {

    }

    public static String toJson(Object obj) {

        return new Gson().toJson(obj);
    }

    public static String toJson(Object obj, Type type) {

        return new Gson().toJson(obj, type);
    }

    public static void toJson(Object obj, Type type, JsonWrite jsonWrite) {

        new Gson().toJson(obj, type, jsonWrite);
    }

    public static <T> T fromJson(Class<T> clazz, String json) {

        return new Gson().fromJson(json, clazz);
    }

    public static <T> T fromJson(Type type, String json) {

        return new Gson().fromJson(json, type);
    }

    public static <T> T fromJson(Class<T> clazz, JsonRead read) {

        return new Gson().fromJson(read, clazz);
    }

    public static <T> T fromJson(Type type, JsonRead read) {

        return new Gson().fromJson(read, type);
    }
}
