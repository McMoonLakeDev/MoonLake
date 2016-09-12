package com.minecraft.moonlake.property;

import com.minecraft.moonlake.binding.ExpressionHelper;
import com.minecraft.moonlake.value.ChangeListener;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.ObservableStringValue;

/**
 * Created by MoonLake on 2016/9/12.
 */
public abstract class StringPropertyBase extends StringProperty {

    private String value;
    private boolean valid = true;
    private ObservableStringValue observable = null;
    private InvalidationListener listener = null;
    private ExpressionHelper<String> helper = null;

    public StringPropertyBase() {

    }

    public StringPropertyBase(String initialValue) {

        this.value = initialValue;
    }

    @Override
    public void addListener(InvalidationListener listener) {

        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {

        helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super String> listener) {

        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super String> listener) {

        helper = ExpressionHelper.removeListener(helper, listener);
    }

    protected void fireValueChangedEvent() {

        ExpressionHelper.fireValueChangedEvent(helper);
    }

    private void markInvalid() {

        if(valid) {

            valid = false;
            invalidated();
            fireValueChangedEvent();
        }
    }

    protected void invalidated() {

    }

    @Override
    public String get() {

        valid = true;
        return observable == null ? value : observable.get();
    }

    @Override
    public void set(String newValue) {

        if((value == null) ? newValue != null : !value.equals(newValue)) {

            value = newValue;
            markInvalid();
        }
    }

    @Override
    public String toString() {

        return "StringProperty [value: " + get() + "]";
    }
}
