package com.minecraft.moonlake.property;

import com.minecraft.moonlake.value.WritableIntegerValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class IntegerProperty extends ReadOnlyIntegerProperty implements Property<Number>, WritableIntegerValue {

    @Override
    public void setValue(Number value) {

        if(value == null) {

            set(0);
        }
        else {

            set(value.intValue());
        }
    }

    @Override
    public String toString() {

        return "IntegerProperty: [value: " + get() + "]";
    }
}
