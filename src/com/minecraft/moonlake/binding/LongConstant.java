package com.minecraft.moonlake.binding;

import com.minecraft.moonlake.value.ChangeListener;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.ObservableLongValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public final class LongConstant implements ObservableLongValue {

    private final long value;

    private LongConstant(long value) {

        this.value = value;
    }

    public static LongConstant valueOf(long value) {

        return new LongConstant(value);
    }

    @Override
    public long get() {

        return value;
    }

    @Override
    public Long getValue() {

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

        return value;
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
