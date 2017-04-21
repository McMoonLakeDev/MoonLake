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

import com.minecraft.moonlake.exception.MoonLakeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * <h1>SimpleConstructorAccessor</h1>
 * 简单构造函数访问器类
 *
 * @param <T> 类型
 * @version 1.0
 * @author Month_Light
 */
final class SimpleConstructorAccessor<T> implements ConstructorAccessor<T> {

    private final Constructor<T> constructor;

    /**
     * 简单构造函数访问器类构造函数
     *
     * @param constructor 构造函数
     */
    public SimpleConstructorAccessor(Constructor<T> constructor) {
        this.constructor = constructor;
    }

    @Override
    public T invoke(Object... params) {
        try {
            return this.constructor.newInstance(params);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot use constructor.", e);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (InvocationTargetException e) {
            throw new MoonLakeException("An internal error occurred.", e.getCause());
        } catch (InstantiationException e) {
            throw new MoonLakeException("Cannot instantiate object.", e);
        }
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof SimpleConstructorAccessor) {
            SimpleConstructorAccessor other = (SimpleConstructorAccessor) obj;
            return this.constructor == other.constructor;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return constructor != null ? constructor.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SimpleConstructorAccessor [constructor=" + this.constructor + "]";
    }
}
