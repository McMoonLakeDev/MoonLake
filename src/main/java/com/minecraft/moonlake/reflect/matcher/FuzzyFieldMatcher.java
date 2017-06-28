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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <h1>FuzzyFieldMatcher</h1>
 * 模糊字段匹配类
 *
 * @version 1.0
 * @author Month_Light
 * @see FuzzyMatcher
 * @see Field
 */
public final class FuzzyFieldMatcher extends FuzzyMatcher<List<Field>> {

    private Class<?> type;

    /**
     * 模糊字段匹配类构造函数
     */
    public FuzzyFieldMatcher() {
        super();
    }

    /**
     * 获取此模糊字段匹配的具有类型属性
     *
     * @return 类型属性
     */
    public Class<?> getType() {
        return type;
    }

    @Override
    public FuzzyFieldMatcher withName(String name) {
        return (FuzzyFieldMatcher) super.withName(name);
    }

    @Override
    public FuzzyFieldMatcher withStatic() {
        return (FuzzyFieldMatcher) super.withStatic();
    }

    @Override
    public FuzzyFieldMatcher withFinal() {
        return (FuzzyFieldMatcher) super.withFinal();
    }

    /**
     * 设置此模糊字段匹配的具有类型属性
     *
     * @param type 类型
     */
    public FuzzyFieldMatcher withType(Class<?> type) {
        this.type = type;
        return this;
    }

    @Override
    public List<Field> find(FuzzyReflect fuzzyReflect) {
        List<Field> fieldList = new ArrayList<>(fuzzyReflect.getFields());

        if(getName() != null) {
            Iterator<Field> iterable = fieldList.iterator();
            while (iterable.hasNext())
                if(!iterable.next().getName().equals(getName()))
                    iterable.remove();
        }
        if(isStatic()) {
            Iterator<Field> iterable = fieldList.iterator();
            while (iterable.hasNext())
                if(Modifier.isStatic(iterable.next().getModifiers()) != isStatic())
                    iterable.remove();
        }
        if(isFinal()) {
            Iterator<Field> iterable = fieldList.iterator();
            while (iterable.hasNext())
                if(Modifier.isFinal(iterable.next().getModifiers()) != isFinal())
                    iterable.remove();
        }
        if(getType() != null) {
            Iterator<Field> iterable = fieldList.iterator();
            while (iterable.hasNext())
                if(!iterable.next().getType().equals(Reflect.DataType.getPrimitive(getType())))
                    iterable.remove();
        }
        return fieldList;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof FuzzyFieldMatcher) {
            FuzzyFieldMatcher other = (FuzzyFieldMatcher) obj;
            return super.equals(other) && type != null ? type.equals(other.type) : other.type == null;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
