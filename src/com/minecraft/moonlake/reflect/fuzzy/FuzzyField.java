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


package com.minecraft.moonlake.reflect.fuzzy;

import com.minecraft.moonlake.exception.MoonLakeException;

import java.lang.reflect.Field;

public class FuzzyField<T, R> {

    private Field field;
    private boolean forceAccess;

    public FuzzyField(Field field, boolean forceAccess) {

        this.field = field;
        this.setForceAccess(forceAccess);
    }

    public FuzzyField(Field field) {

        this(field, false);
    }

    public FuzzyField(FuzzyReflect<T> fuzzy, Field field) {

        this(field, fuzzy.isForceAccess());
    }

    public Field getField() {

        return field;
    }

    public boolean isForceAccess() {

        return forceAccess;
    }

    public void setForceAccess(boolean forceAccess) {

        this.forceAccess = forceAccess;

        if(forceAccess && !field.isAccessible())
            field.setAccessible(true);
        else if(!forceAccess && field.isAccessible())
            field.setAccessible(false);
    }

    public R getValue() {

        return getValue(null);
    }

    @SuppressWarnings("unchecked")
    public R getValue(T instance) {

        try {

            return (R) field.get(instance);
        }
        catch (Exception e) {

            throw new MoonLakeException(e.getMessage(), e);
        }
    }

    public void setValue(R value) {

        setValue(null, value);
    }

    public void setValue(T instance, R value) {

        try {

            field.set(instance, value);
        }
        catch (Exception e) {

            throw new MoonLakeException(e.getMessage(), e);
        }
    }
}
