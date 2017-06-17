/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.MoonLakePluginDebug;

import javax.annotation.Nullable;

/**
 * <h1>ClassManager</h1>
 * 类管理器类
 *
 * @version 1.0
 * @author Month_Light
 */
public class ClassManager {

    static {
    }

    /**
     * 类管理器类构造函数
     */
    private ClassManager() {
    }

    /**
     * 将指定错误消息和异常进行打印
     *
     * @param error 错误消息
     * @param ex 异常
     */
    private static void printError(String error, Throwable ex) {
        MoonLakePluginDebug.debug(error, ex);
    }

    /**
     * 返回与带有给定字符串名的类或接口相关联的 Class 对象
     *
     * @param name 名称
     * @return Class | null
     */
    @Nullable
    public static Class<?> forName(String name) {
        return forName(name, "The for class '" + name + "' exception.");
    }

    /**
     * 返回与带有给定字符串名的类或接口相关联的 Class 对象
     *
     * @param name 名称
     * @param error 错误信息
     * @return Class | null
     */
    @Nullable
    public static Class<?> forName(String name, String error) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(name);
        } catch (Exception e) {
            printError(error, e);
        }
        return clazz;
    }

    /**
     * 返回与带有给定字符串名的类或接口相关联的 Class 对象
     *
     * @param name 名称
     * @param initialize 是否初始化此类
     * @param loader 加载此类的加载器
     * @return Class | null
     */
    @Nullable
    public static Class<?> forName(String name, boolean initialize, ClassLoader loader) {
        return forName(name, initialize, loader, "The for class '" + name + "' exception.");
    }

    /**
     * 返回与带有给定字符串名的类或接口相关联的 Class 对象
     *
     * @param name 名称
     * @param initialize 是否初始化此类
     * @param loader 加载此类的加载器
     * @param error 错误信息
     * @return Class | null
     */
    @Nullable
    public static Class<?> forName(String name, boolean initialize, ClassLoader loader, String error) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(name, initialize, loader);
        } catch (Exception e) {
            printError(error, e);
        }
        return clazz;
    }

    /**
     * 调用当前的类加载器加载给定字符串名的类或接口
     *
     * @param name 名称
     * @return Class | null
     */
    @Nullable
    public static Class<?> loadClass(String name) {
        return loadClass(name, "The load class '" + name + "' exception.");
    }

    /**
     * 调用当前的类加载器加载给定字符串名的类或接口
     *
     * @param name 名称
     * @param error 错误信息
     * @return Class | null
     */
    @Nullable
    public static Class<?> loadClass(String name, String error) {
        return loadClass(ClassManager.class.getClassLoader(), name, error);
    }

    /**
     * 调用指定类加载器加载给定字符串名的类或接口
     *
     * @param name 名
     * @return Class | null
     */
    @Nullable
    public static Class<?> loadClass(ClassLoader loader, String name) {
        return loadClass(loader, name, "The load class '" + name + "' exception.");
    }

    /**
     * 调用指定类加载器加载给定字符串名的类或接口
     *
     * @param name 名称
     * @param error 错误信息
     * @return Class | null
     */
    @Nullable
    public static Class<?> loadClass(ClassLoader loader, String name, String error) {
        Class<?> clazz = null;
        try {
            clazz = loader.loadClass(name);
        } catch (Exception e) {
            printError(error, e);
        }
        return clazz;
    }

    /**
     * 获取当前类管理器的类加载器
     *
     * @return ClassLoader
     */
    public static ClassLoader getClassLoader() {
        return ClassManager.class.getClassLoader();
    }
}
