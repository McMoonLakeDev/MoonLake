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

import com.minecraft.moonlake.binding.IntegerExpression;
import com.minecraft.moonlake.binding.ObjectExpression;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.WeakInvalidationListener;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class ReadOnlyIntegerProperty extends IntegerExpression implements ReadOnlyProperty<Number> {

    public ReadOnlyIntegerProperty() {

    }

    @Override
    public String toString() {

        return "ReadOnlyIntegerProperty [value: " + get() + "]";
    }

    public static <T extends Number> ReadOnlyIntegerProperty readOnlyIntegerProperty(final ReadOnlyProperty<T> property) {

        if(property == null) {

            throw new NullPointerException("The read only property is null.");
        }
        return property instanceof ReadOnlyIntegerProperty ? (ReadOnlyIntegerProperty) property

                : new ReadOnlyIntegerPropertyBase() {

            private boolean valid = true;
            private final InvalidationListener listener = observable -> {

                if(valid) {

                    valid = false;
                    fireValueChangedEvent();
                }
            };

            {
                property.addListener(new WeakInvalidationListener(listener));
            }

            @Override
            public int get() {

                valid = true;
                final T value = property.getValue();
                return value == null ? 0 : value.intValue();
            }
        };
    }

    @Override
    public ObjectExpression<Integer> asObject() {

        return new ReadOnlyObjectPropertyBase<Integer>() {

            private boolean valid = true;
            private final InvalidationListener listener = observable -> {

                if(valid) {

                    valid = false;
                    fireValueChangedEvent();
                }
            };

            {
                ReadOnlyIntegerProperty.this.addListener(new WeakInvalidationListener(listener));
            }

            @Override
            public Integer get() {

                valid = true;
                return ReadOnlyIntegerProperty.this.getValue();
            }
        };
    }
}
