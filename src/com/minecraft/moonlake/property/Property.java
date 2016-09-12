package com.minecraft.moonlake.property;

import com.minecraft.moonlake.value.WritableValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface Property<T> extends ReadOnlyProperty<T>, WritableValue<T> {
}
