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


package com.minecraft.moonlake.reflect.accessors;

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.utility.MinecraftBukkitVersion;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.builder.Builder;
import com.minecraft.moonlake.builder.SingleParamBuilder;
import com.minecraft.moonlake.reflect.ExactReflect;
import com.minecraft.moonlake.reflect.FuzzyReflect;
import com.minecraft.moonlake.validate.Validate;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Accessors</h1>
 * 访问器类 (函数暂时不提供doc文档)
 *
 * @version 1.0
 * @author Month_Light
 */
public final class Accessors {

    static {
    }

    private Accessors() {
        super();
    }

    public static FieldAccessor[] getFieldAccessorArray(Class<?> instanceClass, Class<?> fieldClass, boolean forceAccess) {
        List<Field> fieldList = FuzzyReflect.fromClass(instanceClass, forceAccess).getFieldListByType(fieldClass);
        FieldAccessor[] fieldAccessors = new FieldAccessor[fieldList.size()];
        for(int i = 0; i < fieldAccessors.length; i++)
            fieldAccessors[i] = getFieldAccessor(fieldList.get(i));
        return fieldAccessors;
    }

    public static FieldAccessor getFieldAccessor(Class<?> instanceClass, Class<?> fieldClass, boolean forceAccess) {
        Field field = FuzzyReflect.fromClass(instanceClass, forceAccess).getFieldByType(null, fieldClass);
        field.setAccessible(forceAccess);
        return new SimpleFieldAccessor(field);
    }

    public static FieldAccessor getFieldAccessor(Class<?> instanceClass, String fieldName, boolean forceAccess) {
        return getFieldAccessor(ExactReflect.fromClass(instanceClass, forceAccess).getField(fieldName));
    }

    public static FieldAccessor getFieldAccessor(Class<?> instanceClass, int index, boolean forceAccess) {
        return getFieldAccessor(ExactReflect.fromClass(instanceClass, forceAccess).getFieldByIndex(index));
    }

    public static FieldAccessor getFieldAccessor(Field field) {
        return getFieldAccessor(field, true);
    }

    public static FieldAccessor getFieldAccessor(Field field, boolean forceAccess) {
        Validate.checkNotNull(field).setAccessible(forceAccess);
        return new SimpleFieldAccessor(field);
    }

    public static MethodAccessor getMethodAccessor(Class<?> instanceClass, String methodName, Class<?>... params) {
        return getMethodAccessor(ExactReflect.fromClass(instanceClass, true).getMethod(methodName, params));
    }

    public static MethodAccessor getMethodAccessor(Method method) {
        return getMethodAccessor(method, true);
    }

    public static MethodAccessor getMethodAccessor(Method method, boolean forceAccess) {
        Validate.checkNotNull(method).setAccessible(forceAccess);
        return new SimpleMethodAccessor(method);
    }

    public static MethodAccessor getMethodAccessorBuilder(Builder<MethodAccessor> paramBuilder) {
        return Validate.checkNotNull(paramBuilder).build();
    }

    public static MethodAccessor getMethodAccessorBuilderMCVer(SingleParamBuilder<MethodAccessor, MinecraftVersion> paramBuilder) {
        return Validate.checkNotNull(paramBuilder).build(MoonLakeAPI.currentMCVersion());
    }

    public static MethodAccessor getMethodAccessorBuilderBukkitVer(SingleParamBuilder<MethodAccessor, MinecraftBukkitVersion> paramBuilder) {
        return Validate.checkNotNull(paramBuilder).build(MoonLakeAPI.currentBukkitVersion());
    }

    public static <T> ConstructorAccessor<T> getConstructorAccessor(Class<T> instanceClass, Class<?>... params) {
        try {
            return getConstructorAccessor(instanceClass.getConstructor(params));
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format("Unable to find constructor %s(%s).", instanceClass, Arrays.toString(params)));
        } catch (SecurityException e) {
            throw new IllegalStateException("Cannot access constructors.", e);
        }
    }

    public static ConstructorAccessor<?> getConstructorAccessorBuilder(Builder<ConstructorAccessor<?>> paramBuilder) {
        return Validate.checkNotNull(paramBuilder).build();
    }

    public static ConstructorAccessor<?> getConstructorAccessorBuilderMCVer(SingleParamBuilder<ConstructorAccessor<?>, MinecraftVersion> paramBuilder) {
        return Validate.checkNotNull(paramBuilder).build(MoonLakeAPI.currentMCVersion());
    }

    public static ConstructorAccessor<?> getConstructorAccessorBuilderBukkitVer(SingleParamBuilder<ConstructorAccessor<?>, MinecraftBukkitVersion> paramBuilder) {
        return Validate.checkNotNull(paramBuilder).build(MoonLakeAPI.currentBukkitVersion());
    }

    public static <T> ConstructorAccessor<T> getConstructorAccessor(Constructor<T> constructor) {
        return new SimpleConstructorAccessor<>(constructor);
    }

    public static FieldAccessor getFieldAccessorOrNull(Class<?> clazz, String fieldName, Class<?> fieldType) {
        try {
            FieldAccessor accessor = getFieldAccessor(clazz, fieldName, true);
            if(fieldType.isAssignableFrom(accessor.getField().getType()))
                return accessor;
            return null;
        } catch (IllegalArgumentException e) {
        }
        return null;
    }

    public static MethodAccessor getMethodAccessorOrNull(Class<?> clazz, String methodName, Class<?>... params) {
        try {
            return getMethodAccessor(clazz, methodName, params);
        } catch (IllegalArgumentException e) {
        }
        return null;
    }

    public static ConstructorAccessor<?> getConstructorAccessorOrNull(Class<?> clazz, Class<?>... params) {
        try {
            return getConstructorAccessor(clazz, params);
        } catch (IllegalArgumentException e) {
        }
        return null;
    }

    public static MethodAccessor getConstantMethodAccessor(Object returnValue, final Method method) {
        return new MethodAccessor() {
            @Override
            public Object invoke(Object instance, Object... params) {
                return returnValue;
            }

            @Override
            public Method getMethod() {
                return method;
            }
        };
    }

    public static FieldAccessor getSynchronizedFieldAccessor(FieldAccessor fieldAccessor) {
        return fieldAccessor instanceof SynchronizedFieldAccessor ? fieldAccessor : new SynchronizedFieldAccessor(fieldAccessor);
    }

    public final static class SynchronizedFieldAccessor implements FieldAccessor {

        private final FieldAccessor accessor;

        public SynchronizedFieldAccessor(FieldAccessor accessor) {
            this.accessor = accessor;
        }

        @Override
        public Object get(Object instance) {
            return this.accessor.get(instance);
        }

        @Override
        public void set(Object instance, Object value) {
            Object lock = this.accessor.get(instance);
            if(lock != null) {
                synchronized (lock) {
                    this.accessor.set(instance, value);
                }
            } else {
                this.accessor.set(instance, value);
            }
        }

        @Override
        public Field getField() {
            return this.accessor.getField();
        }
    }
}
