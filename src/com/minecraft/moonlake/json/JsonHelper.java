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
public final class JsonHelper {

    /**
     * Json 帮助器类构造函数
     */
    private JsonHelper() {

    }

    /**
     * 将指定对象转换到 Json 字符串
     *
     * @param obj 对象
     * @return Json
     */
    public static String toJson(Object obj) {

        return new Gson().toJson(obj);
    }

    /**
     * 将指定对象转换到 Json 字符串
     *
     * @param obj 对象
     * @param type 类型
     * @return Json
     */
    public static String toJson(Object obj, Type type) {

        return new Gson().toJson(obj, type);
    }

    /**
     * 将指定对象转换到 Json 字符串写入到 JsonWrite
     *
     * @param obj 对象
     * @param type 类型
     * @param jsonWrite Json 写对象
     */
    public static void toJson(Object obj, Type type, JsonWrite jsonWrite) {

        new Gson().toJson(obj, type, jsonWrite);
    }

    /**
     * 将指定 Json 字符串转换到指定类的实例对象
     *
     * @param clazz 类
     * @param json Json 字符串
     * @param <T> 类
     * @return 类的实例
     */
    public static <T> T fromJson(Class<T> clazz, String json) {

        return new Gson().fromJson(json, clazz);
    }

    /**
     * 将指定 Json 字符串转换到指定类型的实例对象
     *
     * @param type 类型
     * @param json Json 字符串
     * @param <T> 类型
     * @return 类型的实例
     */
    public static <T> T fromJson(Type type, String json) {

        return new Gson().fromJson(json, type);
    }

    /**
     * 将指定 JsonRead 转换到指定类的实例对象
     *
     * @param clazz 类
     * @param read Json 读对象
     * @param <T> 类
     * @return 类的实例
     */
    public static <T> T fromJson(Class<T> clazz, JsonRead read) {

        return new Gson().fromJson(read, clazz);
    }

    /**
     * 将指定 JsonRead 转换到指定类型的实例对象
     *
     * @param type 类型
     * @param read Json 读对象
     * @param <T> 类型
     * @return 类型的实例
     */
    public static <T> T fromJson(Type type, JsonRead read) {

        return new Gson().fromJson(read, type);
    }
}
