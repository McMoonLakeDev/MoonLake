package com.minecraft.moonlake.value;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface WritableBooleanValue extends WritableValue<Boolean> {

    boolean get();

    void set(boolean value);

    void setValue(Boolean value);
}
