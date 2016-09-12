package com.minecraft.moonlake.binding;

import com.minecraft.moonlake.collections.ObservableList;
import com.minecraft.moonlake.value.ObservableFloatValue;
import com.minecraft.moonlake.value.ObservableNumberValue;
import com.minecraft.moonlake.value.ObservableValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class FloatExpression extends NumberExpressionBase implements ObservableFloatValue {

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

        return get();
    }

    @Override
    public double doubleValue() {

        return (double) get();
    }

    @Override
    public Float getValue() {

        return get();
    }

    public static FloatExpression floatExpression(final ObservableFloatValue value) {

        if(value == null) {

            throw new NullPointerException("The observable integer value is null.");
        }
        return value instanceof FloatExpression ? (FloatExpression) value

                : new FloatBinding() {

                    {
                        super.bind(value);
                    }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected float computeValue() {

                    return value.get();
                }

                @Override
                public ObservableList<?> getDependencies() {
                    return null;
                }
            };
    }

    public static <T extends Number> FloatExpression floatExpression(final ObservableValue<T> value) {

        if(value == null) {

            throw new NullPointerException("The observable integer value is null.");
        }
        return value instanceof FloatExpression ? (FloatExpression) value

                : new FloatBinding() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected float computeValue() {

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
    public FloatBinding negate() {

        return (FloatBinding) Bindings.negate(this);
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
    public FloatBinding add(long other) {

        return (FloatBinding) Bindings.add(this, other);
    }

    @Override
    public FloatBinding add(int other) {

        return (FloatBinding) Bindings.add(this, other);
    }

    @Override
    public FloatBinding subtract(ObservableNumberValue other) {

        return (FloatBinding) Bindings.subtract(this, other);
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
    public FloatBinding subtract(long other) {

        return (FloatBinding) Bindings.subtract(this, other);
    }

    @Override
    public FloatBinding subtract(int other) {

        return (FloatBinding) Bindings.subtract(this, other);
    }

    @Override
    public FloatBinding multiply(ObservableNumberValue other) {

        return (FloatBinding) Bindings.multiply(this, other);
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
    public FloatBinding multiply(long other) {

        return (FloatBinding) Bindings.multiply(this, other);
    }

    @Override
    public FloatBinding multiply(int other) {

        return (FloatBinding) Bindings.multiply(this, other);
    }

    @Override
    public FloatBinding divide(ObservableNumberValue other) {

        return (FloatBinding) Bindings.divide(this, other);
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
    public FloatBinding divide(long other) {

        return (FloatBinding) Bindings.divide(this, other);
    }

    @Override
    public FloatBinding divide(int other) {

        return (FloatBinding) Bindings.divide(this, other);
    }

    @Override
    public FloatBinding zero() {

        return (FloatBinding) Bindings.zero(this);
    }

    public ObjectExpression<Float> asObject() {

        return new ObjectBinding<Float>() {

            {
                super.bind(FloatExpression.this);
            }

            @Override
            public void dispose() {

                super.unbind(FloatExpression.this);
            }

            @Override
            protected Float computeValue() {

                return FloatExpression.this.getValue();
            }
        };
    }
}
