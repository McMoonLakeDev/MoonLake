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
import com.minecraft.moonlake.value.ObservableObjectValue;

import java.util.Locale;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class ObjectExpression<T> implements ObservableObjectValue<T> {

    @Override
    public T getValue() {

        return get();
    }

    public static <T> ObjectExpression<T> objectExpression(final ObservableObjectValue<T> value) {

        if(value == null) {

            throw new NullPointerException("The observable object value is null.");
        }
        return value instanceof ObjectExpression ? (ObjectExpression<T>) value

                : new ObjectBinding<T>() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected T computeValue() {

                    return value.get();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
        };
    }

    public BooleanBinding isEqualTo(final ObservableObjectValue<?> other) {

        return Bindings.equal(this, other);
    }

    public BooleanBinding isEqualTo(final Object other) {

        return Bindings.equal(this, other);
    }

    public BooleanBinding isNotEqualTo(final ObservableObjectValue<?> other) {

        return Bindings.notEqual(this, other);
    }

    public BooleanBinding isNotEqualTo(final Object other) {

        return Bindings.notEqual(this, other);
    }

    public BooleanBinding isNull() {

        return Bindings.isNull(this);
    }

    public BooleanBinding isNotNull() {

        return Bindings.isNotNull(this);
    }

    public StringBinding asString() {

        return (StringBinding) StringFormatter.convert(this);
    }

    public StringBinding asString(String format) {

        return (StringBinding) Bindings.format(format, this);
    }

    public StringBinding asString(Locale locale, String format) {

        return (StringBinding) Bindings.format(locale, format, this);
    }
}
