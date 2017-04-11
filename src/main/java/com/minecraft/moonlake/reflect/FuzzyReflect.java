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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class FuzzyReflect {

    private Class<?> source;
    private boolean forceAccess;

    public FuzzyReflect(Class<?> source, boolean forceAccess) {
        super();
        this.source = source;
        this.forceAccess = forceAccess;
    }

    public static FuzzyReflect fromClass(Class<?> source) {
        return fromClass(source, false);
    }

    public static FuzzyReflect fromClass(Class<?> source, boolean forceAccess) {
        return new FuzzyReflect(source, forceAccess);
    }

    public static FuzzyReflect fromObject(Object reference) {
        return fromObject(reference, false);
    }

    public static FuzzyReflect fromObject(Object reference, boolean forceAccess) {
        return new FuzzyReflect(reference.getClass(), forceAccess);
    }

    public static <T> T getFieldValue(Object instance, Class<T> fieldClass, boolean forceAccess) {
        @SuppressWarnings("unchecked")
        T result = (T) Accessors.getFieldAccessor(instance.getClass(), fieldClass, forceAccess).get(instance);
        return result;
    }

    public Class<?> getSource() {
        return source;
    }

    public boolean isForceAccess() {
        return forceAccess;
    }

    public void setForceAccess(boolean forceAccess) {
        this.forceAccess = forceAccess;
    }

    public Method getMethodByName(String nameRegex) {
        Pattern pattern = Pattern.compile(nameRegex);
        for(Method method : getMethods())
            if(pattern.matcher(method.getName()).matches())
                return method;
        throw new IllegalArgumentException("Unable to find a method with the pattern " + nameRegex + " in " + this.source.getName());
    }

    public Method getMethodByParameters(String name, Class<?>... params) {
        Class<?>[] primitiveTypes = Reflect.DataType.getPrimitive(params);
        for(Method method : getMethods())
            if(Reflect.DataType.compare(method.getParameterTypes(), primitiveTypes))
                return method;
        throw new IllegalArgumentException("Unable to find " + name + " in " + this.source.getName());
    }

    public Method getMethodByParameters(String name, Class<?> returnType, Class<?>[] params) {
        List<Method> methodList = getMethodListByParameters(returnType, params);
        if(methodList.size() > 0)
            return methodList.get(0);
        throw new IllegalArgumentException("Unable to find " + name + " in " + this.source.getName());
    }

    public List<Method> getMethodListByParameters(Class<?> returnType, Class<?>[] params) {
        List<Method> methodList = new ArrayList<>();
        Class<?>[] primitiveTypes = Reflect.DataType.getPrimitive(params);
        for(Method method : getMethods())
            if(method.getReturnType().equals(Reflect.DataType.getPrimitive(returnType)) && Reflect.DataType.compare(method.getParameterTypes(), primitiveTypes))
                methodList.add(method);
        return methodList;
    }

    public Object invokeMethod(Object instance, String name, Class<?> returnType, Object... params) {
        Class<?>[] parameters = new Class<?>[params.length];
        for(int i = 0; i < parameters.length; i++)
            parameters[i] = Reflect.DataType.getPrimitive(params[i].getClass());
        return Accessors.getMethodAccessor(getMethodByParameters(name, returnType, parameters)).invoke(instance, params);
    }

    public Field getFieldByName(String nameRegex) {
        Pattern pattern = Pattern.compile(nameRegex);
        for(Field field : getFields())
            if(pattern.matcher(field.getName()).matches())
                return field;
        throw new IllegalArgumentException("Unable to find a field with the pattern " + nameRegex + " in " + this.source.getName());
    }

    public Field getFieldByType(String name, Class<?> type) {
        List<Field> fieldList = getFieldListByType(type);
        if(fieldList.size() > 0)
            return fieldList.get(0);
        throw new IllegalArgumentException(String.format("Unable to find a field %s with the type %s in %s", name, type.getName(), this.source.getName()));
    }

    public List<Field> getFieldListByType(Class<?> type) {
        List<Field> fieldList = new ArrayList<>();
        for(Field field : getFields())
            if(type.isAssignableFrom(field.getType()))
                fieldList.add(field);
        return fieldList;
    }

    public Set<Field> getFields() {
        if(this.forceAccess)
            return union(this.source.getDeclaredFields(), this.source.getFields());
        return union(this.source.getFields());
    }

    public Set<Method> getMethods() {
        if(this.forceAccess)
            return union(this.source.getDeclaredMethods(), this.source.getMethods());
        return union(this.source.getMethods());
    }

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
