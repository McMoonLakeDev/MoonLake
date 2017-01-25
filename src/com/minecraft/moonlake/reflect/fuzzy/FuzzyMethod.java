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

import java.lang.reflect.Method;

public class FuzzyMethod<T, R> {

    private Method method;
    private boolean forceAccess;

    public FuzzyMethod(Method method, boolean forceAccess) {

        this.method = method;
        this.setForceAccess(forceAccess);
    }

    public FuzzyMethod(Method method) {

        this(method, false);
    }

    public FuzzyMethod(FuzzyReflect<T> fuzzy, Method method) {

        this(method, fuzzy.isForceAccess());
    }

    public Method getMethod() {

        return method;
    }

    public boolean isForceAccess() {

        return forceAccess;
    }

    public void setForceAccess(boolean forceAccess) {

        this.forceAccess = forceAccess;

        if(forceAccess && !method.isAccessible())
            method.setAccessible(true);
        else if(!forceAccess && method.isAccessible())
            method.setAccessible(false);
    }

    public R invoke(T instance) {

        return invoke(instance, new Object[0]);
    }

    @SuppressWarnings("unchecked")
    public R invoke(T instance, Object... args) {

        try {

            return (R) method.invoke(instance, args);
        }
        catch (Exception e) {

            throw new MoonLakeException(e.getMessage(), e);
        }
    }
}
