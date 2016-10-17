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
import com.minecraft.moonlake.value.ObservableDoubleValue;
import com.minecraft.moonlake.value.ObservableNumberValue;
import com.minecraft.moonlake.value.ObservableValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class DoubleExpression extends NumberExpressionBase implements ObservableDoubleValue {

    @Override
    public int intValue() {

        return (int) get();
    }

    @Override
    public long longValue() {

        return (long) get();
    }

    @Override
    public float floatValue() {

        return (float) get();
    }

    @Override
    public double doubleValue() {

        return get();
    }

    @Override
    public Double getValue() {

        return get();
    }

    public static DoubleExpression doubleExpression(final ObservableDoubleValue value) {

        if(value == null) {

            throw new NullPointerException("The observable integer value is null.");
        }
        return value instanceof DoubleExpression ? (DoubleExpression) value

                : new DoubleBinding() {

                    {
                        super.bind(value);
                    }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected double computeValue() {

                    return value.get();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
    }

    public static <T extends Number> DoubleExpression doubleExpression(final ObservableValue<T> value) {

        if(value == null) {

            throw new NullPointerException("The observable integer value is null.");
        }
        return (value instanceof DoubleExpression) ? (DoubleExpression) value

                : new DoubleBinding() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected double computeValue() {

                    final T val = value.getValue();
                    return val == null ? 0 : val.intValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
        };
    }

    @Override
    public DoubleBinding negate() {

        return (DoubleBinding) Bindings.negate(this);
    }

    @Override
    public DoubleBinding add(double other) {

        return Bindings.add(this, other);
    }

    @Override
    public DoubleBinding add(float other) {

        return (DoubleBinding) Bindings.add(this, other);
    }

    @Override
    public DoubleBinding add(long other) {

        return (DoubleBinding) Bindings.add(this, other);
    }

    @Override
    public DoubleBinding add(int other) {

        return (DoubleBinding) Bindings.add(this, other);
    }

    @Override
    public DoubleBinding subtract(ObservableNumberValue other) {

        return (DoubleBinding) Bindings.subtract(this, other);
    }

    @Override
    public DoubleBinding subtract(double other) {

        return Bindings.subtract(this, other);
    }

    @Override
    public DoubleBinding subtract(float other) {

        return (DoubleBinding) Bindings.subtract(this, other);
    }

    @Override
    public DoubleBinding subtract(long other) {

        return (DoubleBinding) Bindings.subtract(this, other);
    }

    @Override
    public DoubleBinding subtract(int other) {

        return (DoubleBinding) Bindings.subtract(this, other);
    }

    @Override
    public DoubleBinding multiply(ObservableNumberValue other) {

        return (DoubleBinding) Bindings.multiply(this, other);
    }

    @Override
    public DoubleBinding multiply(double other) {

        return Bindings.multiply(this, other);
    }

    @Override
    public DoubleBinding multiply(float other) {

        return (DoubleBinding) Bindings.multiply(this, other);
    }

    @Override
    public DoubleBinding multiply(long other) {

        return (DoubleBinding) Bindings.multiply(this, other);
    }

    @Override
    public DoubleBinding multiply(int other) {

        return (DoubleBinding) Bindings.multiply(this, other);
    }

    @Override
    public DoubleBinding divide(ObservableNumberValue other) {

        return (DoubleBinding) Bindings.divide(this, other);
    }

    @Override
    public DoubleBinding divide(double other) {

        return Bindings.divide(this, other);
    }

    @Override
    public DoubleBinding divide(float other) {

        return (DoubleBinding) Bindings.divide(this, other);
    }

    @Override
    public DoubleBinding divide(long other) {

        return (DoubleBinding) Bindings.divide(this, other);
    }

    @Override
    public DoubleBinding divide(int other) {

        return (DoubleBinding) Bindings.divide(this, other);
    }

    @Override
    public DoubleBinding zero() {

        return (DoubleBinding) Bindings.zero(this);
    }

    public ObjectExpression<Double> asObject() {

        return new ObjectBinding<Double>() {

            {
                super.bind(DoubleExpression.this);
            }

            @Override
            public void dispose() {

                super.unbind(DoubleExpression.this);
            }

            @Override
            protected Double computeValue() {

                return DoubleExpression.this.getValue();
            }
        };
    }
}
