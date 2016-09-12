package com.minecraft.moonlake.property;

import com.minecraft.moonlake.value.WritableObjectValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class ObjectProperty<T> extends ReadOnlyObjectProperty<T> implements Property<T>, WritableObjectValue<T> {

    @Override
    public void setValue(T value) {

        set(value);
    }

    @Override
    public String toString() {

        return "ObjectProperty [value: " + get() + "]";
    }
}
