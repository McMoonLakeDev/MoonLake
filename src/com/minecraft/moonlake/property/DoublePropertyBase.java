package com.minecraft.moonlake.property;

import com.minecraft.moonlake.binding.ExpressionHelper;
import com.minecraft.moonlake.value.ChangeListener;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.ObservableDoubleValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class DoublePropertyBase extends DoubleProperty {

    private double value;
    private boolean valid = true;
    private ObservableDoubleValue observable = null;
    private InvalidationListener listener = null;
    private ExpressionHelper<Number> helper = null;

    public DoublePropertyBase() {

    }

    public DoublePropertyBase(double initialValue) {

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
    public double get() {

        valid = true;
        return observable == null ? value : observable.get();
    }

    @Override
    public void set(double newValue) {

        if(value != newValue) {

            value = newValue;
            markInvalid();
        }
    }

    @Override
    public String toString() {

        return "DoubleProperty [value: " + get() + "]";
    }
}
