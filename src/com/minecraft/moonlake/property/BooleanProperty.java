package com.minecraft.moonlake.property;

import com.minecraft.moonlake.value.WritableBooleanValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class BooleanProperty extends ReadOnlyBooleanProperty implements Property<Boolean>, WritableBooleanValue {

    public BooleanProperty() {

    }

    @Override
    public void setValue(Boolean value) {

        if(value == null) {

            set(false);
        }
        else {

            set(value);
        }
    }

    @Override
    public String toString() {

        return "BooleanProperty [value: " + get() + "]";
    }
}
