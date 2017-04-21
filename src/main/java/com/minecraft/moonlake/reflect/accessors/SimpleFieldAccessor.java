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

import java.lang.reflect.Field;

/**
 * <h1>SimpleFieldAccessor</h1>
 * 简单字段访问器类
 *
 * @version 1.0
 * @author Month_Light
 */
final class SimpleFieldAccessor implements FieldAccessor {

    private final Field field;

    /**
     * 简单字段访问器类构造函数
     *
     * @param field 字段
     */
    public SimpleFieldAccessor(Field field) {
        this.field = field;
    }

    @Override
    public Object get(Object instance) {
        try {
            return this.field.get(instance);
        } catch (IllegalArgumentException e) {
            throw new MoonLakeException("Cannot read  " + this.field, e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot use reflection.", e);
        }
    }

    @Override
    public void set(Object instance, Object value) {
        try {
            this.field.set(instance, value);
        } catch (IllegalArgumentException e) {
            throw new MoonLakeException("Cannot set field " + this.field + " to value " + value, e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot use reflection.", e);
        }
    }

    @Override
    public Field getField() {
        return this.field;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof SimpleFieldAccessor) {
            SimpleFieldAccessor other = (SimpleFieldAccessor) obj;
            return this.field == other.field;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return field != null ? field.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SimpleFieldAccessor [field=" + this.field + "]";
    }
}
