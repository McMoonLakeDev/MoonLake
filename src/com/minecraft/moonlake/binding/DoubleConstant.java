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
