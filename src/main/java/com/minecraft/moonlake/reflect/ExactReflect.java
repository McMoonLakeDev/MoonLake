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


package com.minecraft.moonlake.reflect;

import com.minecraft.moonlake.validate.Validate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * <h1>ExactReflect</h1>
 * 精确反射类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class ExactReflect {

    private Class<?> source;
    private boolean forceAccess;

    /**
     * 精确反射类构造函数
     *
     * @param source 类源
     * @param forceAccess 是否强制访问
     * @throws IllegalArgumentException 如果类源对象为 {@code null} 则抛出异常
     */
    public ExactReflect(Class<?> source, boolean forceAccess) {
        super();
        this.source = Validate.checkNotNull(source);
        this.forceAccess = forceAccess;
    }

    /**
     * 从指定类源获取精确反射 ExactReflect 实例对象
     *
     * @param source 类源
     * @return ExactReflect
     * @throws IllegalArgumentException 如果类源对象为 {@code null} 则抛出异常
     */
    public static ExactReflect fromClass(Class<?> source) {
        return fromClass(source, false);
    }

    /**
     * 从指定类源获取精确反射 ExactReflect 实例对象
     *
     * @param source 类源
     * @param forceAccess 是否强制访问
     * @return ExactReflect
     * @throws IllegalArgumentException 如果类源对象为 {@code null} 则抛出异常
     */
    public static ExactReflect fromClass(Class<?> source, boolean forceAccess) {
        return new ExactReflect(source, forceAccess);
    }

    /**
     * 从指定对象获取精确反射 ExactReflect 实例对象
     *
     * @param reference 对象
     * @return ExactReflect
     * @throws IllegalArgumentException 如果对象为 {@code null} 则抛出异常
     */
    public static ExactReflect fromObject(Object reference) {
        return fromObject(reference, false);
    }

    /**
     * 从指定对象获取精确反射 ExactReflect 实例对象
     *
     * @param reference 对象
     * @param forceAccess 是否强制访问
     * @return ExactReflect
     * @throws IllegalArgumentException 如果对象为 {@code null} 则抛出异常
     */
    public static ExactReflect fromObject(Object reference, boolean forceAccess) {
        Validate.notNull(reference, "The reference object is null.");
        return new ExactReflect(reference.getClass(), forceAccess);
    }

    /**
     * 获取此精确反射的类源指定函数对象
     *
     * @param methodName 函数名
     * @param params 函数参数
     * @return 函数对象
     * @throws IllegalArgumentException 如果函数名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果未找到匹配函数则抛出异常
     */
    public Method getMethod(String methodName, Class<?>... params) {
        return getMethod(this.source, methodName, params);
    }

    /**
     * 获取此精确反射的类源指定函数对象
     *
     * @param instanceClass 实例类
     * @param methodName 函数名
     * @param params 函数参数
     * @return 函数对象
     * @throws IllegalArgumentException 如果实例类对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果函数名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果未找到匹配函数则抛出异常
     */
    private Method getMethod(Class<?> instanceClass, String methodName, Class<?>... params) {
        Validate.notNull(instanceClass, "The instance class object is null.");
        Validate.notNull(methodName, "The method name object is null.");
        Class<?>[] primitiveTypes = Reflect.DataType.getPrimitive(params);
        for(Method method : instanceClass.getDeclaredMethods()) {
            if((this.forceAccess || Modifier.isPublic(method.getModifiers())) &&
                    (methodName == null || method.getName().equals(methodName)) &&
                            Reflect.DataType.compare(method.getParameterTypes(), primitiveTypes)) {
                method.setAccessible(this.forceAccess);
                return method;
            }
        }
        if(instanceClass.getSuperclass() != null)
            return getMethod(instanceClass.getSuperclass(), methodName, params);
        throw new IllegalArgumentException(String.format("Unable to find method %s (%s) in %s.", methodName, Arrays.asList(params), instanceClass));
    }

    /**
     * 获取此精确反射的类源指定字段对象
     *
     * @param fieldName 字段名
     * @return 字段对象
     * @throws IllegalArgumentException 如果字段名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果未找到匹配字段则抛出异常
     */
    public Field getField(String fieldName) {
        return getField(this.source, fieldName);
    }

    /**
     * 获取此精确反射的类源指定字段对象
     *
     * @param index 索引
     * @return 字段对象
     * @throws IndexOutOfBoundsException 如果索引越界则抛出异常
     */
    public Field getFieldByIndex(int index) {
        Field field = this.source.getDeclaredFields()[index];
        field.setAccessible(this.forceAccess);
        return field;
    }

    /**
     * 获取此精确反射的类源指定字段对象
     *
     * @param instanceClass 实例类
     * @param fieldName 字段名
     * @return 字段对象
     * @throws IllegalArgumentException 如果实例类对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果字段名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果未找到匹配字段则抛出异常
     */
    private Field getField(Class<?> instanceClass, String fieldName) {
        Validate.notNull(instanceClass, "The instance class object is null.");
        Validate.notNull(fieldName, "The field name object is null.");
        for(Field field : instanceClass.getDeclaredFields()) {
            if(field.getName().equals(fieldName)) {
                field.setAccessible(this.forceAccess);
                return field;
            }
        }
        if(instanceClass.getSuperclass() != null)
            return getField(instanceClass.getSuperclass(), fieldName);
        throw new IllegalArgumentException(String.format("Unable to find field %s in %s.", fieldName, instanceClass));
    }

    /**
     * 设置此精确反射以强制访问模式
     *
     * @return {@code this}
     */
    public ExactReflect forceAccess() {
        //return new ExactReflect(this.source, true);
        this.forceAccess = true;
        return this;
    }

    /**
     * 获取此精确反射是否以强制访问模式
     *
     * @return 是否强制访问
     */
    public boolean isForceAccess() {
        return this.forceAccess;
    }

    /**
     * 获取此精确反射的类源对象
     *
     * @return 类源对象
     */
    public Class<?> getSource() {
        return this.source;
    }
}
