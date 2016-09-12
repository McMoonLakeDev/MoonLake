package com.minecraft.moonlake.binding;

import com.minecraft.moonlake.value.ChangeListener;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.ObservableObjectValue;

/**
 * Created by MoonLake on 2016/9/12.
 */
public class ObjectConstant<T> implements ObservableObjectValue<T> {

    private final T value;

    private ObjectConstant(T value) {

        this.value = value;
    }

    public static <T> ObjectConstant<T> valueOf(T value) {

        return new ObjectConstant<T>(value);
    }

    @Override
    public T get() {

        return value;
    }

    @Override
    public T getValue() {

        return value;
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }

    @Override
    public void addListener(ChangeListener<? super T> listener) {

    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {

    }
}
