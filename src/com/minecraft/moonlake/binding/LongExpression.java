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
import com.minecraft.moonlake.value.ObservableLongValue;
import com.minecraft.moonlake.value.ObservableNumberValue;
import com.minecraft.moonlake.value.ObservableValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class LongExpression extends NumberExpressionBase implements ObservableLongValue {

    @Override
    public int intValue() {

        return (int) get();
    }

    @Override
    public long longValue() {

        return get();
    }

    @Override
    public float floatValue() {

        return (float) get();
    }

    @Override
    public double doubleValue() {

        return (double) get();
    }

    @Override
    public Long getValue() {

        return get();
    }

    public static LongExpression longExpression(final ObservableLongValue value) {

        if(value == null) {

            throw new NullPointerException("The observable integer value is null.");
        }
        return value instanceof LongExpression ? (LongExpression) value

                : new LongBinding() {

                    {
                        super.bind(value);
                    }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected long computeValue() {

                    return value.get();
                }

                @Override
                public ObservableList<?> getDependencies() {
                    return null;
                }
            };
    }

    public static <T extends Number> LongExpression integerExpression(final ObservableValue<T> value) {

        if(value == null) {

            throw new NullPointerException("The observable integer value is null.");
        }
        return (value instanceof LongExpression) ? (LongExpression) value

                : new LongBinding() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected long computeValue() {

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
    public LongBinding negate() {

        return (LongBinding) Bindings.negate(this);
    }

    @Override
    public DoubleBinding add(double other) {

        return Bindings.add(this, other);
    }

    @Override
    public FloatBinding add(float other) {

        return (FloatBinding) Bindings.add(this, other);
    }

    @Override
    public LongBinding add(long other) {

        return (LongBinding) Bindings.add(this, other);
    }

    @Override
    public LongBinding add(int other) {

        return (LongBinding) Bindings.add(this, other);
    }

    @Override
    public LongBinding subtract(ObservableNumberValue other) {

        return (LongBinding) Bindings.subtract(this, other);
    }

    @Override
    public DoubleBinding subtract(double other) {

        return Bindings.subtract(this, other);
    }

    @Override
    public FloatBinding subtract(float other) {

        return (FloatBinding) Bindings.subtract(this, other);
    }

    @Override
    public LongBinding subtract(long other) {

        return (LongBinding) Bindings.subtract(this, other);
    }

    @Override
    public LongBinding subtract(int other) {

        return (LongBinding) Bindings.subtract(this, other);
    }

    @Override
    public LongBinding multiply(ObservableNumberValue other) {

        return (LongBinding) Bindings.multiply(this, other);
    }

    @Override
    public DoubleBinding multiply(double other) {

        return Bindings.multiply(this, other);
    }

    @Override
    public FloatBinding multiply(float other) {

        return (FloatBinding) Bindings.multiply(this, other);
    }

    @Override
    public LongBinding multiply(long other) {

        return (LongBinding) Bindings.multiply(this, other);
    }

    @Override
    public LongBinding multiply(int other) {

        return (LongBinding) Bindings.multiply(this, other);
    }

    @Override
    public LongBinding divide(ObservableNumberValue other) {

        return (LongBinding) Bindings.divide(this, other);
    }

    @Override
    public DoubleBinding divide(double other) {

        return Bindings.divide(this, other);
    }

    @Override
    public FloatBinding divide(float other) {

        return (FloatBinding) Bindings.divide(this, other);
    }

    @Override
    public LongBinding divide(long other) {

        return (LongBinding) Bindings.divide(this, other);
    }

    @Override
    public LongBinding divide(int other) {

        return (LongBinding) Bindings.divide(this, other);
    }

    @Override
    public LongBinding zero() {

        return (LongBinding) Bindings.zero(this);
    }

    public ObjectExpression<Long> asObject() {

        return new ObjectBinding<Long>() {

            {
                super.bind(LongExpression.this);
            }

            @Override
            public void dispose() {

                super.unbind(LongExpression.this);
            }

            @Override
            protected Long computeValue() {

                return LongExpression.this.getValue();
            }
        };
    }
}
