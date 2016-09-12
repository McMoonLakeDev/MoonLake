package com.minecraft.moonlake.binding;

import com.minecraft.moonlake.collections.ObservableList;
import com.minecraft.moonlake.value.ObservableIntegerValue;
import com.minecraft.moonlake.value.ObservableNumberValue;
import com.minecraft.moonlake.value.ObservableValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class IntegerExpression extends NumberExpressionBase implements ObservableIntegerValue {

    @Override
    public int intValue() {

        return get();
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

        return (double) get();
    }

    @Override
    public Integer getValue() {

        return get();
    }

    public static IntegerExpression integerExpression(final ObservableIntegerValue value) {

        if(value == null) {

            throw new NullPointerException("The observable integer value is null.");
        }
        return (value instanceof IntegerExpression) ? (IntegerExpression) value

                : new IntegerBinding() {

                    {
                        super.bind(value);
                    }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected int computeValue() {

                    return value.get();
                }

                @Override
                public ObservableList<?> getDependencies() {
                    return null;
                }
            };
    }

    public static <T extends Number> IntegerExpression integerExpression(final ObservableValue<T> value) {

        if(value == null) {

            throw new NullPointerException("The observable integer value is null.");
        }
        return (value instanceof IntegerExpression) ? (IntegerExpression) value

                : new IntegerBinding() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected int computeValue() {

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
    public IntegerBinding negate() {

        return (IntegerBinding) Bindings.negate(this);
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
    public IntegerBinding add(int other) {

        return (IntegerBinding) Bindings.add(this, other);
    }

    @Override
    public IntegerBinding subtract(ObservableNumberValue other) {

        return (IntegerBinding) Bindings.subtract(this, other);
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
    public IntegerBinding subtract(int other) {

        return (IntegerBinding) Bindings.subtract(this, other);
    }

    @Override
    public IntegerBinding multiply(ObservableNumberValue other) {

        return (IntegerBinding) Bindings.multiply(this, other);
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
    public IntegerBinding multiply(int other) {

        return (IntegerBinding) Bindings.multiply(this, other);
    }

    @Override
    public IntegerBinding divide(ObservableNumberValue other) {

        return (IntegerBinding) Bindings.divide(this, other);
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
    public IntegerBinding divide(int other) {

        return (IntegerBinding) Bindings.divide(this, other);
    }

    @Override
    public IntegerBinding zero() {

        return (IntegerBinding) Bindings.zero(this);
    }

    public ObjectExpression<Integer> asObject() {

        return new ObjectBinding<Integer>() {

            {
                super.bind(IntegerExpression.this);
            }

            @Override
            public void dispose() {

                super.unbind(IntegerExpression.this);
            }

            @Override
            protected Integer computeValue() {

                return IntegerExpression.this.getValue();
            }
        };
    }
}
