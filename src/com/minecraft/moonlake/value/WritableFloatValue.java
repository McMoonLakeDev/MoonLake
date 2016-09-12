package com.minecraft.moonlake.value;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface WritableFloatValue extends WritableNumberValue {

    float get();

    void set(float value);

    @Override
    void setValue(Number value);
}
