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

/**
 * <h1>FuzzyMatcher</h1>
 * 模糊匹配类
 *
 * @param <T> T
 * @version 1.0
 * @author Month_Light
 */
public abstract class FuzzyMatcher<T> {

    private String name;
    private boolean isStatic;
    private boolean isFinal;

    /**
     * 模糊匹配类构造函数
     */
    FuzzyMatcher() {
    }

    /**
     * 获取此模糊匹配的具有名称属性
     *
     * @return 名称属性
     */
    public final String getName() {
        return name;
    }

    /**
     * 获取此模糊匹配的是否具有静态属性
     *
     * @return 是否具有静态属性
     */
    public final boolean isStatic() {
        return isStatic;
    }

    /**
     * 获取此模糊匹配的是否具有终态属性
     *
     * @return 是否具有终态属性
     */
    public final boolean isFinal() {
        return isFinal;
    }

    /**
     * 设置此模糊匹配具有名称属性
     *
     * @param name 名称
     */
    public FuzzyMatcher withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 设置此模糊匹配具有静态属性
     */
    public FuzzyMatcher withStatic() {
        this.isStatic = true;
        return this;
    }

    /**
     * 设置此模糊匹配具有终态属性
     */
    public FuzzyMatcher withFinal() {
        this.isFinal = true;
        return this;
    }

    /**
     * 从指定类源按提供的属性进行模糊匹配
     *
     * @param source 类源
     * @param forceAccess 是否强制访问
     * @return 结果
     * @throws IllegalArgumentException 如果类源对象为 {@code null} 则抛出异常
     */
    public T find(Class<?> source, boolean forceAccess) {
        return find(FuzzyReflect.fromClass(source, forceAccess));
    }

    /**
     * 从指定模糊反射按提供的属性进行模糊匹配
     *
     * @param fuzzyReflect 模糊反射
     * @return 结果
     * @throws IllegalArgumentException 如果模糊反射对象为 {@code null} 则抛出异常
     */
    public abstract T find(FuzzyReflect fuzzyReflect);

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof FuzzyMatcher) {
            FuzzyMatcher other = (FuzzyMatcher) obj;
            return isStatic == other.isStatic && isFinal == other.isFinal &&
                    name != null ? name.equals(other.name) : other.name == null;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (isStatic ? 1 : 0);
        result = 31 * result + (isFinal ? 1 : 0);
        return result;
    }
}
