package com.minecraft.moonlake.value;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface WritableIntegerValue extends WritableNumberValue {

    int get();

    void set(int value);

    @Override
    void setValue(Number value);
}
