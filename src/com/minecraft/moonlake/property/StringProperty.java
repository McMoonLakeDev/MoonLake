package com.minecraft.moonlake.property;

import com.minecraft.moonlake.value.WritableStringValue;

/**
 * Created by MoonLake on 2016/9/12.
 */
public abstract class StringProperty extends ReadOnlyStringProperty implements Property<String>, WritableStringValue {

    @Override
    public void setValue(String value) {

        set(value);
    }

    @Override
    public String toString() {

        return "StringProperty [value: " + get() + "]";
    }
}
