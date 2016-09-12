package com.minecraft.moonlake.value;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface WritableLongValue extends WritableNumberValue {

    long get();

    void set(long value);

    @Override
    void setValue(Number value);
}
