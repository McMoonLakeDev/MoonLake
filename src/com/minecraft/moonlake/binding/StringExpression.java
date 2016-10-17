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

import com.minecraft.moonlake.value.ObservableStringValue;
import com.minecraft.moonlake.value.ObservableValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class StringExpression implements ObservableStringValue {

    @Override
    public String getValue() {

        return get();
    }

    public final String getValueSafe() {

        final String value = get();
        return value == null ? "" : value;
    }

    public static StringExpression stringExpression(final ObservableValue<?> value) {

        if(value == null) {

            throw new NullPointerException("The observable value is null.");
        }
        return StringFormatter.convert(value);
    }

    public StringExpression concat(Object other) {

        return Bindings.concat(other);
    }

    public BooleanBinding isEqualTo(final ObservableStringValue other) {

        return Bindings.equal(this, other);
    }

    public BooleanBinding isEqualTo(final String other) {

        return Bindings.equal(this, other);
    }

    public BooleanBinding isNotEqualTo(final ObservableStringValue other) {

        return Bindings.notEqual(this, other);
    }

    public BooleanBinding isNotEqualTo(final String other) {

        return Bindings.notEqual(this, other);
    }

    public BooleanBinding isEqualToIgnoreCase(final ObservableStringValue other) {

        return Bindings.equalIgnoreCase(this, other);
    }

    public BooleanBinding isEqualToIgnoreCase(final String other) {

        return Bindings.equalIgnoreCase(this, other);
    }

    public BooleanBinding isNotEqualToIgnoreCase(final ObservableStringValue other) {

        return Bindings.notEqualIgnoreCase(this, other);
    }

    public BooleanBinding isNotEqualToIgnoreCase(final String other) {

        return Bindings.notEqualIgnoreCase(this, other);
    }

    public BooleanBinding greaterThan(final ObservableStringValue other) {

        return Bindings.greaterThan(this, other);
    }

    public BooleanBinding greaterThan(final String other) {

        return Bindings.greaterThan(this, other);
    }

    public BooleanBinding lessThan(final ObservableStringValue other) {

        return Bindings.lessThan(this, other);
    }

    public BooleanBinding lessThan(final String other) {

        return Bindings.lessThan(this, other);
    }

    public BooleanBinding greaterThanOrEqualTo(final ObservableStringValue other) {

        return Bindings.greaterThanOrEqual(this, other);
    }

    public BooleanBinding greaterThanOrEqualTo(final String other) {

        return Bindings.greaterThanOrEqual(this, other);
    }

    public BooleanBinding lessThanOrEqualTo(final ObservableStringValue other) {

        return Bindings.lessThanOrEqual(this, other);
    }

    public BooleanBinding lessThanOrEqualTo(final String other) {

        return Bindings.lessThanOrEqual(this, other);
    }

    public BooleanBinding isNull() {

        return Bindings.isNull(this);
    }

    public BooleanBinding isNotNull() {

        return Bindings.isNotNull(this);
    }

    public IntegerBinding length() {

        return Bindings.length(this);
    }

    public BooleanBinding isEmpty() {

        return Bindings.isEmpty(this);
    }

    public BooleanBinding isNotEmpty() {

        return Bindings.isNotEmpty(this);
    }
}
