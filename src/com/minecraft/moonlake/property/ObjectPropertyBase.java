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
 
 
package com.minecraft.moonlake.property;

import com.minecraft.moonlake.binding.ExpressionHelper;
import com.minecraft.moonlake.value.ChangeListener;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.ObservableObjectValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class ObjectPropertyBase<T> extends ObjectProperty<T> {

    private T value;
    private boolean valid = true;
    private ObservableObjectValue<? extends T> observable = null;
    private InvalidationListener listener = null;
    private ExpressionHelper<T> helper;

    public ObjectPropertyBase() {

    }

    public ObjectPropertyBase(T initialValue) {

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
    public void addListener(ChangeListener<? super T> listener) {

        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {

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
    public T get() {

        valid = true;
        return observable == null ? value : observable.get();
    }

    @Override
    public void set(T newValue) {

        if(value != newValue) {

            value = newValue;
            markInvalid();
        }
    }

    @Override
    public String toString() {

        return "ObjectProperty [value: " + get() + "]";
    }
}
