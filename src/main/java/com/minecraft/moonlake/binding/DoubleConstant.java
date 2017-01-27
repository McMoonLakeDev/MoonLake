/*
 * Copyright (C) 2016 The MoonLake Authors
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
 
 
package com.minecraft.moonlake.binding;

import com.minecraft.moonlake.value.ChangeListener;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.ObservableDoubleValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public final class DoubleConstant implements ObservableDoubleValue {

    private final double value;

    private DoubleConstant(double value) {

        this.value = value;
    }

    public static DoubleConstant valueOf(double value) {

        return new DoubleConstant(value);
    }

    @Override
    public double get() {

        return value;
    }

    @Override
    public Double getValue() {

        return value;
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }

    @Override
    public void addListener(ChangeListener<? super Number> listener) {

    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {

    }

    @Override
    public int intValue() {

        return (int) value;
    }

    @Override
    public long longValue() {

        return (long) value;
    }

    @Override
    public float floatValue() {

        return (float) value;
    }

    @Override
    public double doubleValue() {

        return value;
    }
}
