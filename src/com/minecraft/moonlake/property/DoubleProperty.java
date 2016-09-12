package com.minecraft.moonlake.property;

import com.minecraft.moonlake.value.WritableDoubleValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class DoubleProperty extends ReadOnlyDoubleProperty implements Property<Number>, WritableDoubleValue {

    @Override
    public void setValue(Number value) {

        if(value == null) {

            set(0.0d);
        }
        else {

            set(value.doubleValue());
        }
    }

    @Override
    public String toString() {

        return "DoubleProperty: [value: " + get() + "]";
    }
}
