package com.minecraft.moonlake.binding;

import com.minecraft.moonlake.collections.ObservableList;
import com.minecraft.moonlake.value.ChangeListener;
import com.minecraft.moonlake.value.InvalidationListener;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class IntegerBinding extends IntegerExpression implements NumberBinding {

    private int value;
    private boolean valid = false;
    private BindingHelperObserver observer;
    private ExpressionHelper<Number> helper = null;

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

    protected final void bind(Observable... dependencies) {

        if((dependencies != null) && (dependencies.length > 0)) {

            if(observer == null) {

                observer = new BindingHelperObserver(this);
            }
            for(final Observable dep : dependencies) {

                dep.addListener(observer);
            }
        }
    }

    protected final void unbind(Observable... dependencies) {

        if(observer != null) {

            for(final Observable dep : dependencies) {

                dep.removeListener(observer);
            }
            observer = null;
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public ObservableList<?> getDependencies() {

        return null;
    }

    @Override
    public int get() {

        if(!valid) {

            value = computeValue();
            valid = true;
        }
        return value;
    }

    protected void onInvalidating() {

    }

    @Override
    public void invalidate() {

        if(valid) {

            valid = false;
            onInvalidating();
            ExpressionHelper.fireValueChangedEvent(helper);
        }
    }

    @Override
    public boolean isValid() {

        return valid;
    }

    protected abstract int computeValue();

    @Override
    public String toString() {

        return valid ? "IntegerBinding [value: " + get() + "]" : "IntegerBinding [invalid]";
    }
}
