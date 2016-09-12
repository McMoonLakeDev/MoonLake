package com.minecraft.moonlake.property;

import com.minecraft.moonlake.value.WritableLongValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class LongProperty extends ReadOnlyLongProperty implements Property<Number>, WritableLongValue {

    @Override
    public void setValue(Number value) {

        if(value == null) {

            set(0L);
        }
        else {

            set(value.longValue());
        }
    }

    @Override
    public String toString() {

        return "LongProperty: [value: " + get() + "]";
    }
}
