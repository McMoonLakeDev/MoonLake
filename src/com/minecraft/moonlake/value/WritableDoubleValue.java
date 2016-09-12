package com.minecraft.moonlake.value;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface WritableDoubleValue extends WritableNumberValue {

    double get();

    void set(double value);

    @Override
    void setValue(Number value);
}
