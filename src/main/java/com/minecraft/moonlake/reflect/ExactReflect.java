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

public class ExactReflect {

    private Class<?> source;
    private boolean forceAccess;

    public ExactReflect(Class<?> source, boolean forceAccess) {
        super();
        this.source = Validate.checkNotNull(source);
        this.forceAccess = forceAccess;
    }

    public static ExactReflect fromClass(Class<?> source) {
        return fromClass(source, false);
    }

    public static ExactReflect fromClass(Class<?> source, boolean forceAccess) {
        return new ExactReflect(source, forceAccess);
    }

    public static ExactReflect fromObject(Object reference) {
        return fromObject(reference, false);
    }

    public static ExactReflect fromObject(Object reference, boolean forceAccess) {
        return new ExactReflect(reference.getClass(), forceAccess);
    }

    public Method getMethod(String methodName, Class<?>... params) {
        return getMethod(this.source, methodName, params);
    }

    private Method getMethod(Class<?> instanceClass, String methodName, Class<?>... params) {
        Class<?>[] primitiveTypes = Reflect.DataType.getPrimitive(params);
        for(Method method : instanceClass.getDeclaredMethods()) {
            if((this.forceAccess || Modifier.isPublic(method.getModifiers()) &&
                    (methodName == null || method.getName().equals(methodName) &&
                            Reflect.DataType.compare(method.getParameterTypes(), primitiveTypes)))) {
                method.setAccessible(this.forceAccess);
                return method;
            }
        }
        if(instanceClass.getSuperclass() != null)
            return getMethod(instanceClass.getSuperclass(), methodName, params);
        throw new IllegalArgumentException(String.format("Unable to find method %s (%s) in %s.", methodName, Arrays.asList(params), instanceClass));
    }

    public Field getField(String fieldName) {
        return getField(this.source, fieldName);
    }

    private Field getField(Class<?> instanceClass, String fieldName) {
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

    public ExactReflect forceAccess() {
        //return new ExactReflect(this.source, true);
        this.forceAccess = true;
        return this;
    }

    public boolean isForceAccess() {
        return this.forceAccess;
    }

    public Class<?> getSource() {
        return this.source;
    }
}
