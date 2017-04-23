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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <h1>SimpleMethodAccessor</h1>
 * 简单函数访问器类
 *
 * @version 1.0
 * @author Month_Light
 */
final class SimpleMethodAccessor implements MethodAccessor {

    private final Method method;

    /**
     * 简单函数访问器类构造函数
     *
     * @param method 函数
     */
    public SimpleMethodAccessor(Method method) {
        this.method = method;
    }

    @Override
    public Object invoke(Object instance, Object... params) {
        try {
            return this.method.invoke(instance, params);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot use reflection.", e);
        } catch (InvocationTargetException e) {
            throw new MoonLakeException("An internal error occurred.", e.getCause());
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof SimpleMethodAccessor) {
            SimpleMethodAccessor other = (SimpleMethodAccessor) obj;
            return this.method == other.method;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return method != null ? method.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SimpleMethodAccessor [method=" + this.method + "]";
    }
}
