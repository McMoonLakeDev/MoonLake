/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package com.minecraft.moonlake.binding;

import com.minecraft.moonlake.collections.ObservableList;
import com.minecraft.moonlake.value.ChangeListener;
import com.minecraft.moonlake.value.InvalidationListener;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class FloatBinding extends FloatExpression implements NumberBinding {

    private float value;
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
    public float get() {

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

    protected abstract float computeValue();

    @Override
    public String toString() {

        return valid ? "FloatBinding [value: " + get() + "]" : "FloatBinding [invalid]";
    }
}
