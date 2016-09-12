package com.minecraft.moonlake.binding;

import com.minecraft.moonlake.value.ChangeListener;
import com.minecraft.moonlake.value.InvalidationListener;

/**
 * Created by MoonLake on 2016/9/11.
 */
public class StringConstant extends StringExpression {

    private final String value;

    private StringConstant(String value) {

        this.value = value;
    }

    public static StringConstant valueOf(String value) {

        return new StringConstant(value);
    }

    @Override
    public String get() {

        return value;
    }

    @Override
    public String getValue() {

        return value;
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }

    @Override
    public void addListener(ChangeListener<? super String> listener) {

    }

    @Override
    public void removeListener(ChangeListener<? super String> listener) {

    }
}
