package com.minecraft.moonlake.binding;

import com.minecraft.moonlake.value.ChangeListener;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.ObservableIntegerValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public final class IntegerConstant implements ObservableIntegerValue {

    private final int value;

    private IntegerConstant(int value) {

        this.value = value;
    }

    public static IntegerConstant valueOf(int value) {

        return new IntegerConstant(value);
    }

    @Override
    public int get() {

        return value;
    }

    @Override
    public Integer getValue() {

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

        return value;
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

        return (double) value;
    }
}
