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

import com.minecraft.moonlake.value.ObservableNumberValue;

import java.util.Locale;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface NumberExpression extends ObservableNumberValue {

    NumberBinding negate();

    NumberBinding add(final ObservableNumberValue other);

    NumberBinding add(final double other);

    NumberBinding add(final float other);

    NumberBinding add(final long other);

    NumberBinding add(final int other);

    NumberBinding subtract(final ObservableNumberValue other);

    NumberBinding subtract(final double other);

    NumberBinding subtract(final float other);

    NumberBinding subtract(final long other);

    NumberBinding subtract(final int other);

    NumberBinding multiply(final ObservableNumberValue other);

    NumberBinding multiply(final double other);

    NumberBinding multiply(final float other);

    NumberBinding multiply(final long other);

    NumberBinding multiply(final int other);

    NumberBinding divide(final ObservableNumberValue other);

    NumberBinding divide(final double other);

    NumberBinding divide(final float other);

    NumberBinding divide(final long other);

    NumberBinding divide(final int other);

    NumberBinding zero();

    BooleanBinding isEqualTo(final ObservableNumberValue other, double epsilon);

    BooleanBinding isEqualTo(final ObservableNumberValue other);

    BooleanBinding isEqualTo(final double other, double epsilon);

    BooleanBinding isEqualTo(final float other, double epsilon);

    BooleanBinding isEqualTo(final long other, double epsilon);

    BooleanBinding isEqualTo(final long other);

    BooleanBinding isEqualTo(final int other, double epsilon);

    BooleanBinding isEqualTo(final int other);

    BooleanBinding isNotEqualTo(final ObservableNumberValue other, double epsilon);

    BooleanBinding isNotEqualTo(final ObservableNumberValue other);

    BooleanBinding isNotEqualTo(final double other, double epsilon);

    BooleanBinding isNotEqualTo(final float other, double epsilon);

    BooleanBinding isNotEqualTo(final long other, double epsilon);

    BooleanBinding isNotEqualTo(final long other);

    BooleanBinding isNotEqualTo(final int other, double epsilon);

    BooleanBinding isNotEqualTo(final int other);

    BooleanBinding greaterThan(final ObservableNumberValue other);

    BooleanBinding greaterThan(final double other);

    BooleanBinding greaterThan(final float other);

    BooleanBinding greaterThan(final long other);

    BooleanBinding greaterThan(final int other);

    BooleanBinding lessThan(final ObservableNumberValue other);

    BooleanBinding lessThan(final double other);

    BooleanBinding lessThan(final float other);

    BooleanBinding lessThan(final long other);

    BooleanBinding lessThan(final int other);

    BooleanBinding greaterThanOrEqualTo(final ObservableNumberValue other);

    BooleanBinding greaterThanOrEqualTo(final double other);

    BooleanBinding greaterThanOrEqualTo(final float other);

    BooleanBinding greaterThanOrEqualTo(final long other);

    BooleanBinding greaterThanOrEqualTo(final int other);

    BooleanBinding lessThanOrEqualTo(final ObservableNumberValue other);

    BooleanBinding lessThanOrEqualTo(final double other);

    BooleanBinding lessThanOrEqualTo(final float other);

    BooleanBinding lessThanOrEqualTo(final long other);

    BooleanBinding lessThanOrEqualTo(final int other);

    StringBinding asString();

    StringBinding asString(String format);

    StringBinding asString(Locale locale, String format);
}
