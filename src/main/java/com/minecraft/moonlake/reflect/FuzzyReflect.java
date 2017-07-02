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

import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.matcher.FuzzyFieldMatcher;
import com.minecraft.moonlake.reflect.matcher.FuzzyMethodMatcher;
import com.minecraft.moonlake.validate.Validate;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * <h1>FuzzyReflect</h1>
 * 模糊反射类（详细doc待补充...）
 *
 * @version 1.0.2
 * @author Month_Light
 */
public class FuzzyReflect {

    private Class<?> source;
    private boolean forceAccess;

    /**
     * 模糊反射类构造函数
     *
     * @param source 类源
     * @param forceAccess 是否强制访问
     * @throws IllegalArgumentException 如果类源对象为 {@code null} 则抛出异常
     */
    public FuzzyReflect(Class<?> source, boolean forceAccess) {
        super();
        this.source = Validate.checkNotNull(source);
        this.forceAccess = forceAccess;
    }

    /**
     * 从指定类源获取模糊反射 FuzzyReflect 实例对象
     *
     * @param source 类源
     * @return FuzzyReflect
     * @throws IllegalArgumentException 如果类源对象为 {@code null} 则抛出异常
     */
    public static FuzzyReflect fromClass(Class<?> source) {
        return fromClass(source, false);
    }

    /**
     * 从指定类源获取模糊反射 FuzzyReflect 实例对象
     *
     * @param source 类源
     * @param forceAccess 是否强制访问
     * @return FuzzyReflect
     * @throws IllegalArgumentException 如果类源对象为 {@code null} 则抛出异常
     */
    public static FuzzyReflect fromClass(Class<?> source, boolean forceAccess) {
        return new FuzzyReflect(source, forceAccess);
    }

    /**
     * 从指定对象获取精确反射 FuzzyReflect 实例对象
     *
     * @param reference 对象
     * @return FuzzyReflect
     * @throws IllegalArgumentException 如果对象为 {@code null} 则抛出异常
     */
    public static FuzzyReflect fromObject(Object reference) {
        return fromObject(reference, false);
    }

    /**
     * 从指定对象获取精确反射 FuzzyReflect 实例对象
     *
     * @param reference 对象
     * @param forceAccess 是否强制访问
     * @return FuzzyReflect
     * @throws IllegalArgumentException 如果对象为 {@code null} 则抛出异常
     */
    public static FuzzyReflect fromObject(Object reference, boolean forceAccess) {
        Validate.notNull(reference, "The reference object is null.");
        return new FuzzyReflect(reference.getClass(), forceAccess);
    }

    /**
     * 获取此模糊反射的类源对象
     *
     * @return 类源对象
     */
    public Class<?> getSource() {
        return source;
    }

    /**
     * 获取此模糊反射是否以强制访问模式
     *
     * @return 是否强制访问
     */
    public boolean isForceAccess() {
        return forceAccess;
    }

    /**
     * 设置此模糊反射是否以强制访问模式
     *
     * @param forceAccess 是否强制访问
     */
    public void setForceAccess(boolean forceAccess) {
        this.forceAccess = forceAccess;
    }

    /**
     * 获取此模糊反射的类源指定函数对象
     *
     * @param nameRegex 函数名称正则表达式
     * @return 函数对象
     * @throws IllegalArgumentException 如果名称正则表达式对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果未找到匹配函数则抛出异常
     */
    public Method getMethodByName(String nameRegex) {
        Validate.notNull(nameRegex, "The name regex object is null.");
        Pattern pattern = Pattern.compile(nameRegex);
        for(Method method : getMethods())
            if(pattern.matcher(method.getName()).matches())
                return method;
        throw new IllegalArgumentException("Unable to find a method with the pattern " + nameRegex + " in " + this.source.getName());
    }

    /**
     * 获取此模糊反射的类源指定函数对象
     *
     * @param name 名称 (仅用于异常信息)
     * @param params 函数参数
     * @return 函数对象
     * @throws IllegalArgumentException 如果未找到匹配函数则抛出异常
     */
    public Method getMethodByParameters(String name, Class<?>... params) {
        Class<?>[] primitiveTypes = Reflect.DataType.getPrimitive(params);
        for(Method method : getMethods())
            if(Reflect.DataType.compare(method.getParameterTypes(), primitiveTypes))
                return method;
        throw new IllegalArgumentException("Unable to find " + name + " in " + this.source.getName());
    }

    /**
     * 获取此模糊反射的类源指定函数对象
     *
     * @param name 名称 (仅用于异常信息)
     * @param returnType 函数返回类型
     * @param params 函数参数
     * @return 函数对象
     * @throws IllegalArgumentException 如果返回类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果未找到匹配函数则抛出异常
     */
    public Method getMethodByParameters(String name, Class<?> returnType, Class<?>[] params) {
        List<Method> methodList = getMethodListByParameters(returnType, params);
        if(methodList.size() > 0)
            return methodList.get(0);
        throw new IllegalArgumentException("Unable to find " + name + " in " + this.source.getName());
    }

    /**
     * 获取此模糊反射的类源指定函数对象列表
     *
     * @param returnType 函数返回类型
     * @param params 函数参数
     * @return 函数对象列表
     * @throws IllegalArgumentException 如果返回类型对象为 {@code null} 则抛出异常
     */
    public List<Method> getMethodListByParameters(Class<?> returnType, Class<?>[] params) {
        Validate.notNull(returnType, "The return type object is null.");
        List<Method> methodList = new ArrayList<>();
        Class<?>[] primitiveTypes = Reflect.DataType.getPrimitive(params);
        for(Method method : getMethods())
            if(method.getReturnType().equals(Reflect.DataType.getPrimitive(returnType)) && Reflect.DataType.compare(method.getParameterTypes(), primitiveTypes))
                methodList.add(method);
        return methodList;
    }

    /**
     * 从指定模糊函数匹配获取指定函数对象
     *
     * @param matcher 模糊函数匹配
     * @return 函数对象
     * @throws IllegalArgumentException 如果模糊函数匹配对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果未找到匹配函数则抛出异常
     */
    public Method getMethodByMatcher(FuzzyMethodMatcher matcher) {
        List<Method> methodList = getMethodListByMatcher(matcher);
        if(methodList.size() > 0)
            return methodList.get(0);
        throw new IllegalArgumentException("Unable to find " + matcher + " in " + this.source.getName());
    }

    /**
     * 从指定模糊函数匹配获取指定函数对象列表
     *
     * @param matcher 模糊函数匹配
     * @return 函数对象列表
     * @throws IllegalArgumentException 如果模糊函数匹配对象为 {@code null} 则抛出异常
     */
    public List<Method> getMethodListByMatcher(FuzzyMethodMatcher matcher) {
        Validate.notNull(matcher, "The fuzzy method matcher is null.");
        return matcher.find(this);
    }

    /**
     * 调用此模糊反射的类源指定函数对象
     *
     * @param instance 实例
     * @param name 名称 (仅用于异常信息)
     * @param returnType 函数返回类型
     * @param params 函数参数
     * @return 函数返回值
     * @throws IllegalArgumentException 如果返回类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果未找到匹配函数则抛出异常
     */
    public Object invokeMethod(Object instance, String name, Class<?> returnType, Object... params) {
        Validate.notNull(returnType, "The return type object is null.");
        Class<?>[] parameters = new Class<?>[params.length];
        for(int i = 0; i < parameters.length; i++)
            parameters[i] = Reflect.DataType.getPrimitive(params[i].getClass());
        return Accessors.getMethodAccessor(getMethodByParameters(name, returnType, parameters)).invoke(instance, params);
    }

    /**
     * 获取此模糊反射的类源指定字段对象
     *
     * @param nameRegex 字段名称正则表达式
     * @return 字段对象
     * @throws IllegalArgumentException 如果名称正则表达式对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果未找到匹配字段则抛出异常
     */
    public Field getFieldByName(String nameRegex) {
        Pattern pattern = Pattern.compile(nameRegex);
        for(Field field : getFields())
            if(pattern.matcher(field.getName()).matches())
                return field;
        throw new IllegalArgumentException("Unable to find a field with the pattern " + nameRegex + " in " + this.source.getName());
    }

    /**
     * 获取此模糊反射的类源指定字段对象
     *
     * @param name 名称 (仅用于异常信息)
     * @param type 字段类型
     * @return 字段对象
     * @throws IllegalArgumentException 如果字段类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果未找到匹配字段则抛出异常
     */
    public Field getFieldByType(String name, Class<?> type) {
        List<Field> fieldList = getFieldListByType(type);
        if(fieldList.size() > 0)
            return fieldList.get(0);
        throw new IllegalArgumentException(String.format("Unable to find a field %s with the type %s in %s", name, type.getName(), this.source.getName()));
    }

    /**
     * 获取此模糊反射的类源 List 的指定参数类型字段对象
     *
     * @param name 名称 (仅用于异常信息)
     * @param paramType 参数字段类型
     * @return 字段对象
     * @throws IllegalArgumentException 如果参数字段类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果未找到匹配字段则抛出异常
     */
    public Field getFieldListByParamType(String name, Class<?> paramType) {
        Validate.notNull(paramType, "The list param type object is null.");
        List<Field> fieldList = getFieldListByType(List.class);
        for(Field field : fieldList) {
            ParameterizedType param = (ParameterizedType) field.getGenericType();
            Type[] typeArr = param.getActualTypeArguments();
            if(typeArr != null && typeArr.length > 0)
                if(paramType.getTypeName().equals(typeArr[0].getTypeName()))
                    return field;
        }
        throw new IllegalArgumentException(String.format("Unable to find a field %s with the list type %s in %s", name, paramType.getName(), this.source.getName()));
    }

    /**
     * 获取此模糊反射的类源指定字段对象列表
     *
     * @param type 字段类型
     * @return 字段对象列表
     * @throws IllegalArgumentException 如果字段类型对象为 {@code null} 则抛出异常
     */
    public List<Field> getFieldListByType(Class<?> type) {
        Validate.notNull(type, "The field type object is null.");
        List<Field> fieldList = new ArrayList<>();
        for(Field field : getFields())
            if(type.isAssignableFrom(field.getType()))
                fieldList.add(field);
        return fieldList;
    }

    /**
     * 从指定模糊字段匹配获取指定字段对象
     *
     * @param matcher 模糊字段匹配
     * @return 字段对象
     * @throws IllegalArgumentException 如果模糊字段匹配对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果未找到匹配字段则抛出异常
     */
    public Field getFieldByMatcher(FuzzyFieldMatcher matcher) {
        List<Field> methodList = getFieldListByMatcher(matcher);
        if(methodList.size() > 0)
            return methodList.get(0);
        throw new IllegalArgumentException("Unable to find a field with the " + matcher + " in " + this.source.getName());
    }

    /**
     * 从指定模糊自字段匹配获取指定字段对象列表
     *
     * @param matcher 模糊字段匹配
     * @return 字段对象列表
     * @throws IllegalArgumentException 如果模糊字段匹配对象为 {@code null} 则抛出异常
     */
    public List<Field> getFieldListByMatcher(FuzzyFieldMatcher matcher) {
        Validate.notNull(matcher, "The fuzzy field matcher is null.");
        return matcher.find(this);
    }

    /**
     * 获取此模糊反射的类源的字段对象集合
     *
     * @return 字段对象集合
     */
    public Set<Field> getFields() {
        if(this.forceAccess)
            return union(this.source.getDeclaredFields(), this.source.getFields());
        return union(this.source.getFields());
    }

    /**
     * 获取此模糊反射的类源的函数对象集合
     *
     * @return 函数对象集合
     */
    public Set<Method> getMethods() {
        if(this.forceAccess)
            return union(this.source.getDeclaredMethods(), this.source.getMethods());
        return union(this.source.getMethods());
    }

    /**
     * 获取此模糊反射的类源的构造函数对象集合
     *
     * @return 构造函数对象集合
     */
    public Set<Constructor<?>> getConstructors() {
        if(this.forceAccess)
            return union(this.source.getDeclaredConstructors());
        return union(this.source.getConstructors());
    }

    @SafeVarargs
    private static <T> Set<T> union(T[]... arrays) {
        Set<T> result = new LinkedHashSet<>();
        for(T[] elements : arrays)
            for(T element : elements)
                result.add(element);
        return result;
    }
}
