package com.minecraft.moonlake.property;

import com.minecraft.moonlake.binding.ExpressionHelper;
import com.minecraft.moonlake.value.ChangeListener;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.ObservableFloatValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class FloatPropertyBase extends FloatProperty {

    private float value;
    private boolean valid = true;
    private ObservableFloatValue observable = null;
    private InvalidationListener listener = null;
    private ExpressionHelper<Number> helper = null;

    public FloatPropertyBase() {

    }

    public FloatPropertyBase(float initialValue) {

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
    public void addListener(ChangeListener<? super Number> listener) {

        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {

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
    public float get() {

        valid = true;
        return observable == null ? value : observable.get();
    }

    @Override
    public void set(float newValue) {

        if(value != newValue) {

            value = newValue;
            markInvalid();
        }
    }

    @Override
    public String toString() {

        return "FloatProperty [value: " + get() + "]";
    }
}
