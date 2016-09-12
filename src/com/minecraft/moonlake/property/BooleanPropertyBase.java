package com.minecraft.moonlake.property;

import com.minecraft.moonlake.binding.ExpressionHelper;
import com.minecraft.moonlake.value.ChangeListener;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.ObservableBooleanValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class BooleanPropertyBase extends BooleanProperty {

    private boolean value;
    private boolean valid = true;
    private ObservableBooleanValue observable = null;
    private InvalidationListener listener = null;
    private ExpressionHelper<Boolean> helper = null;

    public BooleanPropertyBase() {

    }

    public BooleanPropertyBase(boolean initialValue) {

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
    public void addListener(ChangeListener<? super Boolean> listener) {

        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Boolean> listener) {

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
    public boolean get() {

        valid = true;
        return observable == null ? value : observable.get();
    }

    @Override
    public void set(boolean newValue) {

        if(value != newValue) {

            value = newValue;
            markInvalid();
        }
    }

    @Override
    public String toString() {

        return "BooleanProperty [value: " + get() + "]";
    }
}
