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
 
 
package com.minecraft.moonlake.reflect;

import com.minecraft.moonlake.MoonLakeAPI;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Reflect</h1>
 * Java Reflect Class
 *
 * @version 1.0
 * @author Month_Light
 */
@SuppressWarnings("deprecation")
public class Reflect {

    /**
     * 获取 Bukkit 的服务端版本
     *
     * @return 版本 v1_10_R1
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link MoonLakeAPI#currentBukkitVersionString()}
     */
    @Deprecated
    public static String getServerVersion() { // TODO 2.0

        return MoonLakeAPI.currentBukkitVersionString();
    }

    /**
     * 获取 Bukkit 的服务端版本号
     *
     * @return 版本号 10
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public static int getServerVersionNumber() { // TODO 2.0

        return MoonLakeAPI.getReleaseNumber(); // 等到 v2.0 一起删除
    }

    /**
     * 获取指定类的构造函数对象
     *
     * @param clazz 类
     * @param argsType 类构造参数类型
     * @return 构造函数对象
     * @throws NoSuchMethodException 没有存在此参数类型构造则抛出异常
     */
    public static Constructor<?> getConstructor(Class<?> clazz, Class... argsType) throws NoSuchMethodException {

        Class<?>[] primitiveTypes = DataType.getPrimitive(argsType);

        for(Constructor<?> constructor : clazz.getConstructors()) {

            if(DataType.compare(DataType.getPrimitive(constructor.getParameterTypes()), primitiveTypes)) {

                return constructor;
            }
        }
        throw new NoSuchMethodException("在这个类中没有指定的参数类型构造.");
    }

    /**
     * 获取指定类的构造函数对象
     *
     * @param clazz 类
     * @param argsType 类构造参数类型
     * @return 构造函数对象
     * @throws NoSuchMethodException 没有存在此参数类型构造则抛出异常
     */
    public static Constructor<?> getDeclaredConstructor(Class<?> clazz, Class... argsType) throws NoSuchMethodException {

        Class<?>[] primitiveTypes = DataType.getPrimitive(argsType);

        for(Constructor<?> constructor : clazz.getDeclaredConstructors()) {

            if(DataType.compare(DataType.getPrimitive(constructor.getParameterTypes()), primitiveTypes)) {

                return constructor;
            }
        }
        throw new NoSuchMethodException("在这个类中没有指定的参数类型构造.");
    }

    /**
     * 获取指定类的构造函数对象
     *
     * @param className 类名
     * @param packageType 包类型
     * @param argsType 类构造参数类型
     * @return 构造函数对象
     * @throws NoSuchMethodException 没有存在此参数类型构造则抛出异常
     * @throws ClassNotFoundException 类没有存在则抛出异常
     */
    public static Constructor<?> getConstructor(String className, PackageType packageType, Class<?>... argsType) throws NoSuchMethodException, ClassNotFoundException {

        return getConstructor(packageType.getClass(className), argsType);
    }

    /**
     * 获取指定类的构造函数对象
     *
     * @param className 类名
     * @param packageType 包类型
     * @param argsType 类构造参数类型
     * @return 构造函数对象
     * @throws NoSuchMethodException 没有存在此参数类型构造则抛出异常
     * @throws ClassNotFoundException 类没有存在则抛出异常
     */
    public static Constructor<?> getDeclaredConstructor(String className, PackageType packageType, Class<?>... argsType) throws NoSuchMethodException, ClassNotFoundException {

        return getDeclaredConstructor(packageType.getClass(className), argsType);
    }

    /**
     * 获取并创建类的实例对象
     *
     * @param clazz 类
     * @param argsObject 类构造参数值
     * @return 类的实例对象
     * @throws NoSuchMethodException 没有存在此参数类型构造则抛出异常
     * @throws IllegalAccessException 非法存取则抛出异常
     * @throws InvocationTargetException 调用目标时异常
     * @throws InstantiationException 实例化时异常
     */
    public static Object instantiateObject(Class<?> clazz, Object... argsObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        return getConstructor(clazz, DataType.getPrimitive(argsObject)).newInstance(argsObject);
    }

    /**
     * 获取并创建类的实例对象
     *
     * @param className 类名
     * @param packageType 包类型
     * @param argsObject 类构造参数值
     * @return 类的实例对象
     * @throws ClassNotFoundException 类没有存在则抛出异常
     * @throws InvocationTargetException 调用目标时异常
     * @throws NoSuchMethodException 没有存在此参数类型构造则抛出异常
     * @throws InstantiationException 实例化时异常
     * @throws IllegalAccessException 非法存取则抛出异常
     */
    public static Object instantiateObject(String className, PackageType packageType, Object... argsObject) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        return instantiateObject(packageType.getClass(className), argsObject);
    }

    /**
     * 获取指定类的函数对象
     *
     * @param clazz 类
     * @param methodName 函数名
     * @param argsType 函数参数类型
     * @return 类的指定函数对象
     * @throws NoSuchMethodException 没有存在此参数类型函数则抛出异常
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... argsType) throws NoSuchMethodException {

        Class<?>[] primitiveTypes = DataType.getPrimitive(argsType);

        for(Method method : clazz.getMethods()) {

            if(method.getName().equals(methodName) && DataType.compare(DataType.getPrimitive(method.getParameterTypes()), primitiveTypes)) {

                return method;
            }
        }
        throw new NoSuchMethodException("在这个类中没有指定的参数类型函数.");
    }

    /**
     * 获取指定类的函数对象
     *
     * @param clazz 类
     * @param methodName 函数名
     * @param argsType 函数参数类型
     * @return 类的指定函数对象
     * @throws NoSuchMethodException 没有存在此参数类型函数则抛出异常
     */
    public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... argsType) throws NoSuchMethodException {

        Class<?>[] primitiveTypes = DataType.getPrimitive(argsType);

        for(Method method : clazz.getDeclaredMethods()) {

            if(method.getName().equals(methodName) && DataType.compare(DataType.getPrimitive(method.getParameterTypes()), primitiveTypes)) {

                return method;
            }
        }
        throw new NoSuchMethodException("在这个类中没有指定的参数类型函数.");
    }

    /**
     * 获取指定类的函数对象
     *
     * @param className 类名
     * @param packageType 包类型
     * @param methodName 函数名
     * @param argsType 函数参数类型
     * @return 类的指定函数对象
     * @throws ClassNotFoundException 类没有存在则抛出异常
     * @throws NoSuchMethodException 没有存在此参数类型函数则抛出异常
     */
    public static Method getMethod(String className, PackageType packageType, String methodName, Class<?>... argsType) throws ClassNotFoundException, NoSuchMethodException {

        return getMethod(packageType.getClass(className), methodName, argsType);
    }

    /**
     * 获取指定类的函数对象
     *
     * @param className 类名
     * @param packageType 包类型
     * @param methodName 函数名
     * @param argsType 函数参数类型
     * @return 类的指定函数对象
     * @throws ClassNotFoundException 类没有存在则抛出异常
     * @throws NoSuchMethodException 没有存在此参数类型函数则抛出异常
     */
    public static Method getDeclaredMethod(String className, PackageType packageType, String methodName, Class<?>... argsType) throws ClassNotFoundException, NoSuchMethodException {

        return getDeclaredMethod(packageType.getClass(className), methodName, argsType);
    }

    /**
     * 获取并执行指定类对象的指定函数
     *
     * @param instance 实例对象
     * @param methodName 函数名
     * @param argsObject 函数参数类型值
     * @return 函数的回调值
     * @throws NoSuchMethodException 没有存在此参数类型函数则抛出异常
     * @throws InvocationTargetException 调用目标时异常
     * @throws IllegalAccessException 非法存取则抛出异常
     */
    public static Object invokeMethod(Object instance, String methodName, Object... argsObject) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        return getMethod(instance.getClass(), methodName, DataType.getPrimitive(argsObject)).invoke(instance, argsObject);
    }

    /**
     * 获取并执行指定类对象的指定函数
     *
     * @param instance 实例对象
     * @param clazz 类
     * @param methodName 函数名
     * @param argsObject 函数参数类型值
     * @return 函数的回调值
     * @throws NoSuchMethodException 没有存在此参数类型函数则抛出异常
     * @throws InvocationTargetException 调用目标时异常
     * @throws IllegalAccessException 非法存取则抛出异常
     */
    public static Object invokeMethod(Object instance, Class<?> clazz, String methodName, Object... argsObject) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        return getMethod(clazz, methodName, DataType.getPrimitive(argsObject)).invoke(instance, argsObject);
    }

    /**
     * 获取并执行指定类对象的指定函数
     *
     * @param instance 实例对象
     * @param className 类名
     * @param packageType 包类型
     * @param methodName 函数名
     * @param argsObject 函数参数类型值
     * @return 函数的回调值
     * @throws ClassNotFoundException 类没有存在则抛出异常
     * @throws NoSuchMethodException 没有存在此参数类型函数则抛出异常
     * @throws IllegalAccessException 非法存取则抛出异常
     * @throws InvocationTargetException 调用目标时异常
     */
    public static Object invokeMethod(Object instance, String className, PackageType packageType, String methodName, Object... argsObject) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        return invokeMethod(instance, packageType.getClass(className), methodName, argsObject);
    }

    /**
     * 获取指定类对象的指定字段
     *
     * @param clazz 类
     * @param declared 是否声明字段
     * @param fieldName 字段名
     * @return 类的字段对象
     * @throws NoSuchFieldException 没有存在此字段则抛出异常
     */
    public static Field getField(Class<?> clazz, boolean declared, String fieldName) throws NoSuchFieldException {

        Field field = declared ? clazz.getDeclaredField(fieldName) : clazz.getField(fieldName);
        field.setAccessible(true);
        return field;
    }

    /**
     * 获取指定类对象的指定字段
     *
     * @param className 类名
     * @param packageType 包类型
     * @param declared 是否声明字段
     * @param fieldName 字段名
     * @return 类的字段对象
     * @throws ClassNotFoundException 类没有存在则抛出异常
     * @throws NoSuchFieldException 没有存在此字段则抛出异常
     */
    public static Field getField(String className, PackageType packageType, boolean declared, String fieldName) throws ClassNotFoundException, NoSuchFieldException {

        return getField(packageType.getClass(className), declared, fieldName);
    }

    /**
     * 获取指定类对象的指定字段值
     *
     * @param instance 实例对象
     * @param clazz 类
     * @param declared 是否声明字段
     * @param fieldName 字段名
     * @return 类的字段对象的值
     * @throws NoSuchFieldException 没有存在此字段则抛出异常
     * @throws IllegalAccessException 非法存取则抛出异常
     */
    public static Object getValue(Object instance, Class<?> clazz, boolean declared, String fieldName) throws NoSuchFieldException, IllegalAccessException {

        return getField(clazz, declared, fieldName).get(instance);
    }

    /**
     * 获取指定类对象的指定字段值
     *
     * @param instance 实例对象
     * @param className 类名
     * @param packageType 包类型
     * @param declared 是否声明字段
     * @param fieldName 字段名
     * @return 类的字段对象的值
     * @throws NoSuchFieldException 没有存在此字段则抛出异常
     * @throws IllegalAccessException 非法存取则抛出异常
     * @throws ClassNotFoundException 类没有存在则抛出异常
     */
    public static Object getValue(Object instance, String className, PackageType packageType, boolean declared, String fieldName) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {

        return getValue(instance, packageType.getClass(className), declared, fieldName);
    }

    /**
     * 获取指定类对象的指定字段值
     *
     * @param instance 实例对象
     * @param declared 是否声明字段
     * @param fieldName 字段名
     * @return 类的字段对象的值
     * @throws NoSuchFieldException 没有存在此字段则抛出异常
     * @throws IllegalAccessException 非法存取则抛出异常
     */
    public static Object getValue(Object instance, boolean declared, String fieldName) throws NoSuchFieldException, IllegalAccessException {

        return getValue(instance, instance.getClass(), declared, fieldName);
    }

    /**
     * 设置指定类对象的指定字段值
     *
     * @param instance 实例对象
     * @param clazz 类
     * @param declared 是否声明字段
     * @param fieldName 字段名
     * @param value 字段值
     * @throws NoSuchFieldException 没有存在此字段则抛出异常
     * @throws IllegalAccessException 非法存取则抛出异常
     */
    public static void setValue(Object instance, Class<?> clazz, boolean declared, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {

        getField(clazz, declared, fieldName).set(instance, value);
    }

    /**
     * 设置指定类对象的指定字段值
     *
     * @param instance 实例对象
     * @param className 类名
     * @param packageType 包类型
     * @param declared 是否声明字段
     * @param fieldName 字段名
     * @param value 字段值
     * @throws NoSuchFieldException 没有存在此字段则抛出异常
     * @throws IllegalAccessException 非法存取则抛出异常
     * @throws ClassNotFoundException 类没有存在则抛出异常
     */
    public static void setValue(Object instance, String className, PackageType packageType, boolean declared, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {

        setValue(instance, packageType.getClass(className), declared, fieldName, value);
    }

    /**
     * 设置指定类对象的指定字段值
     *
     * @param instance 实例对象
     * @param declared 是否声明字段
     * @param fieldName 字段名
     * @param value 字段值
     * @throws NoSuchFieldException 没有存在此字段则抛出异常
     * @throws IllegalAccessException 非法存取则抛出异常
     */
    public static void setValue(Object instance, boolean declared, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {

        setValue(instance, instance.getClass(), declared, fieldName, value);
    }

    public enum PackageType {

        MINECRAFT_SERVER("net.minecraft.server." + MoonLakeAPI.currentBukkitVersionString()),
        CRAFTBUKKIT("org.bukkit.craftbukkit." + MoonLakeAPI.currentBukkitVersionString()),
        CRAFTBUKKIT_BLOCK(CRAFTBUKKIT, "block"),
        CRAFTBUKKIT_CHUNKIO(CRAFTBUKKIT, "chunkio"),
        CRAFTBUKKIT_COMMAND(CRAFTBUKKIT, "command"),
        CRAFTBUKKIT_CONVERSATIONS(CRAFTBUKKIT, "conversations"),
        CRAFTBUKKIT_ENCHANTMENTS(CRAFTBUKKIT, "enchantments"),
        CRAFTBUKKIT_ENTITY(CRAFTBUKKIT, "entity"),
        CRAFTBUKKIT_EVENT(CRAFTBUKKIT, "event"),
        CRAFTBUKKIT_GENERATOR(CRAFTBUKKIT, "generator"),
        CRAFTBUKKIT_HELP(CRAFTBUKKIT, "help"),
        CRAFTBUKKIT_INVENTORY(CRAFTBUKKIT, "inventory"),
        CRAFTBUKKIT_MAP(CRAFTBUKKIT, "map"),
        CRAFTBUKKIT_METADATA(CRAFTBUKKIT, "metadata"),
        CRAFTBUKKIT_POTION(CRAFTBUKKIT, "potion"),
        CRAFTBUKKIT_PROJECTILES(CRAFTBUKKIT, "projectiles"),
        CRAFTBUKKIT_SCHEDULER(CRAFTBUKKIT, "scheduler"),
        CRAFTBUKKIT_SCOREBOARD(CRAFTBUKKIT, "scoreboard"),
        CRAFTBUKKIT_UPDATER(CRAFTBUKKIT, "updater"),
        CRAFTBUKKIT_UTIL(CRAFTBUKKIT, "util"),;

        private final String path;

        PackageType(String path) {

            this.path = path;
        }

        PackageType(PackageType parent, String path) {

            this(parent.path + "." + path);
        }

        public Class<?> getClass(String className) throws ClassNotFoundException {

            return Class.forName(path + "." + className);
        }
    }

    public enum DataType {

        VOID(Void.TYPE, Void.class),
        BYTE(Byte.TYPE, Byte.class),
        SHORT(Short.TYPE, Short.class),
        INTEGER(Integer.TYPE, Integer.class),
        LONG(Long.TYPE, Long.class),
        CHARACTER(Character.TYPE, Character.class),
        FLOAT(Float.TYPE, Float.class),
        DOUBLE(Double.TYPE, Double.class),
        BOOLEAN(Boolean.TYPE, Boolean.class),
        ;

        private final static Map<Class<?>, DataType> CLASS_MAP;
        private final Class<?> primitive;
        private final Class<?> reference;

        static {

            CLASS_MAP = new HashMap<>();

            for(DataType type : DataType.values()) {

                CLASS_MAP.put(type.primitive, type);
                CLASS_MAP.put(type.reference, type);
            }
        }

        DataType(Class<?> primitive, Class<?> reference) {

            this.primitive = primitive;
            this.reference = reference;
        }

        public Class<?> getPrimitive() {

            return primitive;
        }

        public Class<?> getReference() {

            return reference;
        }

        public static DataType fromClass(Class<?> clazz) {

            return DataType.CLASS_MAP.get(clazz);
        }

        public static Class<?> getPrimitive(Class<?> clazz) {

            DataType type = DataType.fromClass(clazz);
            return type == null ? clazz : type.getPrimitive();
        }

        public static Class<?> getReference(Class<?> clazz) {

            DataType type = DataType.fromClass(clazz);
            return type == null ? clazz : type.getReference();
        }

        public static Class<?>[] getPrimitive(Class<?>[] clazzs) {

            int length = clazzs == null ? 0 : clazzs.length;
            Class<?>[] types = new Class[length];

            for(int i = 0; i < length; i++) {

                types[i] = DataType.getPrimitive(clazzs[i]);
            }
            return types;
        }

        public static Class<?>[] getReference(Class<?>[] clazzs) {

            int length = clazzs == null ? 0 : clazzs.length;
            Class<?>[] types = new Class[length];

            for(int i = 0; i < length; i++) {

                types[i] = DataType.getReference(clazzs[i]);
            }
            return types;
        }

        public static Class<?>[] getPrimitive(Object[] objects) {

            int length = objects == null ? 0 : objects.length;
            Class<?>[] types = new Class[length];

            for(int i = 0; i < length; i++) {

                types[i] = DataType.getPrimitive(objects[i].getClass());
            }
            return types;
        }

        public static Class<?>[] getReference(Object[] objects) {

            int length = objects == null ? 0 : objects.length;
            Class<?>[] types = new Class[length];

            for(int i = 0; i < length; i++) {

                types[i] = DataType.getPrimitive(objects[i].getClass());
            }
            return types;
        }

        public static boolean compare(Class<?>[] primary, Class<?>[] secondary) {

            if(primary == null || secondary == null || primary.length != secondary.length) {

                return false;
            }
            for(int i = 0; i < primary.length; i++) {

                if(!primary[i].equals(secondary[i]) && !primary[i].isAssignableFrom(secondary[i])) {

                    return false;
                }
            }
            return true;
        }
    }
}
