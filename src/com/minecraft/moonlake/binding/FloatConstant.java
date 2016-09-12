package com.minecraft.moonlake.binding;

import com.minecraft.moonlake.value.ChangeListener;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.ObservableFloatValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public final class FloatConstant implements ObservableFloatValue {

    private final float value;

    private FloatConstant(float value) {

        this.value = value;
    }

    public static FloatConstant valueOf(float value) {

        return new FloatConstant(value);
    }

    @Override
    public float get() {

        return value;
    }

    @Override
    public Float getValue() {

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

        return value;
    }

    @Override
    public double doubleValue() {

        return (double) value;
    }
}
