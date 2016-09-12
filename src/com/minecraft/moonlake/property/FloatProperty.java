package com.minecraft.moonlake.property;

import com.minecraft.moonlake.value.WritableFloatValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class FloatProperty extends ReadOnlyFloatProperty implements Property<Number>, WritableFloatValue {

    @Override
    public void setValue(Number value) {

        if(value == null) {

            set(0.0f);
        }
        else {

            set(value.floatValue());
        }
    }

    @Override
    public String toString() {

        return "FloatProperty: [value: " + get() + "]";
    }
}
