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
