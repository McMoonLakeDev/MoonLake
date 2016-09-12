package com.minecraft.moonlake.property;

import com.minecraft.moonlake.binding.ObjectExpression;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class ReadOnlyObjectProperty<T> extends ObjectExpression<T> implements ReadOnlyProperty<T> {

    public ReadOnlyObjectProperty() {

    }

    @Override
    public String toString() {

        return "ReadOnlyObjectProperty [value: " + get() + "]";
    }
}
