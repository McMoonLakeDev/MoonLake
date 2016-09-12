package com.minecraft.moonlake.property;

import com.minecraft.moonlake.binding.StringExpression;

/**
 * Created by MoonLake on 2016/9/12.
 */
public abstract class ReadOnlyStringProperty extends StringExpression implements ReadOnlyProperty<String> {

    public ReadOnlyStringProperty() {

    }

    @Override
    public String toString() {

        return "ReadOnlyStringProperty [value: " + get() + "]";
    }
}
