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


package com.minecraft.moonlake.reflect.matcher;

import com.minecraft.moonlake.reflect.FuzzyReflect;
import com.minecraft.moonlake.reflect.Reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <h1>FuzzyMethodMatcher</h1>
 * 模糊函数匹配类
 *
 * @version 1.0
 * @author Month_Light
 * @see FuzzyMatcher
 * @see Method
 */
public final class FuzzyMethodMatcher extends FuzzyMatcher<List<Method>> {

    private boolean isAbstract;
    private Class<?> returnType;
    private Class<?>[] paramTypes;

    /**
     * 模糊函数匹配类构造函数
     */
    public FuzzyMethodMatcher() {
        super();
    }

    /**
     * 获取此模糊函数匹配的是否具有抽象属性
     *
     * @return 是否具有抽象属性
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    /**
     * 获取此模糊函数匹配的具有返回值类型属性
     *
     * @return 返回值类型属性
     */
    public Class<?> getReturnType() {
        return returnType;
    }

    /**
     * 获取此模糊函数匹配的具有参数类型属性
     *
     * @return 参数类型属性
     */
    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    @Override
    public FuzzyMethodMatcher withName(String name) {
        return (FuzzyMethodMatcher) super.withName(name);
    }

    @Override
    public FuzzyMethodMatcher withStatic() {
        return (FuzzyMethodMatcher) super.withStatic();
    }

    @Override
    public FuzzyMethodMatcher withFinal() {
        return (FuzzyMethodMatcher) super.withFinal();
    }

    /**
     * 设置此模糊函数匹配具有抽象属性
     */
    public FuzzyMethodMatcher withAbstract() {
        this.isAbstract = true;
        return this;
    }

    /**
     * 设置此模糊函数匹配具有返回值类型属性
     *
     * @param returnType 返回值类型
     */
    public FuzzyMethodMatcher withReturnType(Class<?> returnType) {
        this.returnType = returnType;
        return this;
    }

    /**
     * 设置此模糊函数匹配具有参数类型属性
     *
     * @param paramTypes 参数类型
     */
    public FuzzyMethodMatcher withParamTypes(Class<?>... paramTypes) {
        this.paramTypes = paramTypes;
        return this;
    }

    @Override
    public List<Method> find(FuzzyReflect fuzzyReflect) {
        List<Method> methodList = new ArrayList<>(fuzzyReflect.getMethods());

        if(getName() != null) {
            Iterator<Method> iterable = methodList.iterator();
            while (iterable.hasNext())
                if(!iterable.next().getName().equals(getName()))
                    iterable.remove();
        }
        if(isStatic()) {
            Iterator<Method> iterable = methodList.iterator();
            while (iterable.hasNext())
                if(Modifier.isStatic(iterable.next().getModifiers()) != isStatic())
                    iterable.remove();
        }
        if(isFinal()) {
            Iterator<Method> iterable = methodList.iterator();
            while (iterable.hasNext())
                if(Modifier.isFinal(iterable.next().getModifiers()) != isFinal())
                    iterable.remove();
        }
        if(isAbstract()) {
            Iterator<Method> iterable = methodList.iterator();
            while (iterable.hasNext())
                if(Modifier.isAbstract(iterable.next().getModifiers()) != isAbstract())
                    iterable.remove();
        }
        if(getReturnType() != null) {
            Iterator<Method> iterable = methodList.iterator();
            while (iterable.hasNext())
                if(!iterable.next().getReturnType().equals(Reflect.DataType.getPrimitive(getReturnType())))
                    iterable.remove();
        }
        if(getParamTypes() != null && getParamTypes().length > 0) {
            Iterator<Method> iterator = methodList.iterator();
            Class<?>[] primitiveTypes = Reflect.DataType.getPrimitive(getParamTypes());
            while (iterator.hasNext())
                if(!Reflect.DataType.compare(iterator.next().getParameterTypes(), primitiveTypes))
                    iterator.remove();
        }
        return methodList;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof FuzzyMethodMatcher) {
            FuzzyMethodMatcher other = (FuzzyMethodMatcher) obj;
            return super.equals(other) && isAbstract == other.isAbstract &&
                    returnType != null ? returnType.equals(other.returnType) : other.returnType == null && Arrays.equals(paramTypes, other.paramTypes);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isAbstract ? 1 : 0);
        result = 31 * result + (returnType != null ? returnType.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(paramTypes);
        return result;
    }
}
