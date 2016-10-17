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
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.value.*;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.concurrent.Callable;

/**
 * Created by MoonLake on 2016/9/11.
 */
public class Bindings {

    private Bindings() {

    }

    public static BooleanBinding createBooleanBinding(final Callable<Boolean> func, final Observable... dependencies) {

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            protected boolean computeValue() {

                try {

                    return func.call();
                }
                catch (Exception e) {

                    throw new MoonLakeException("Exception while evaluating binding: " + e.getMessage());
                }
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    public static DoubleBinding createDoubleBinding(final Callable<Double> func, final Observable... dependencies) {

        return new DoubleBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            protected double computeValue() {

                try {

                    return func.call();
                }
                catch (Exception e) {

                    throw new MoonLakeException("Exception while evaluating binding: " + e.getMessage());
                }
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    public static FloatBinding createFloatBinding(final Callable<Float> func, final Observable... dependencies) {

        return new FloatBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            protected float computeValue() {

                try {

                    return func.call();
                }
                catch (Exception e) {

                    throw new MoonLakeException("Exception while evaluating binding: " + e.getMessage());
                }
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    public static IntegerBinding createIntegerBinding(final Callable<Integer> func, final Observable... dependencies) {

        return new IntegerBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            protected int computeValue() {

                try {

                    return func.call();
                }
                catch (Exception e) {

                    throw new MoonLakeException("Exception while evaluating binding: " + e.getMessage());
                }
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    public static LongBinding createLongBinding(final Callable<Long> func, final Observable... dependencies) {

        return new LongBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            protected long computeValue() {

                try {

                    return func.call();
                }
                catch (Exception e) {

                    throw new MoonLakeException("Exception while evaluating binding: " + e.getMessage());
                }
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    public static <T> ObjectBinding<T> createObjectBinding(final Callable<T> func, final Observable... dependencies) {

        return new ObjectBinding<T>() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            protected T computeValue() {

                try {

                    return func.call();
                }
                catch (Exception e) {

                    throw new MoonLakeException("Exception while evaluating binding: " + e.getMessage());
                }
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    public static StringBinding createStringBinding(final Callable<String> func, final Observable... dependencies) {

        return new StringBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            protected String computeValue() {

                try {

                    return func.call();
                }
                catch (Exception e) {

                    throw new MoonLakeException("Exception while evaluating binding: " + e.getMessage());
                }
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    public static NumberBinding negate(final ObservableNumberValue value) {

        if (value == null) {

            throw new NullPointerException("The observable number value is null.");
        }
        if (value instanceof ObservableDoubleValue) {

            return new DoubleBinding() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected double computeValue() {

                    return -value.doubleValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else if (value instanceof ObservableFloatValue) {

            return new FloatBinding() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected float computeValue() {

                    return -value.floatValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else if (value instanceof ObservableLongValue) {

            return new LongBinding() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected long computeValue() {

                    return -value.longValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else {

            return new IntegerBinding() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected int computeValue() {

                    return -value.intValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        }
    }

    //////////////////
    /// Number Add ///
    //////////////////

    private static NumberBinding add(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {

        if (op1 == null || op2 == null) {

            throw new NullPointerException("The observable number value1 or value2 is null");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if (op1 instanceof ObservableDoubleValue || op2 instanceof ObservableDoubleValue) {

            return new DoubleBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected double computeValue() {

                    return op1.doubleValue() + op2.doubleValue();
                }
            };
        } else if (op1 instanceof ObservableFloatValue || op2 instanceof ObservableFloatValue) {

            return new FloatBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected float computeValue() {

                    return op1.floatValue() + op2.floatValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else if (op1 instanceof ObservableLongValue || op2 instanceof ObservableLongValue) {

            return new LongBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected long computeValue() {

                    return op1.longValue() + op2.longValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else {

            return new IntegerBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected int computeValue() {

                    return op1.intValue() + op2.intValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        }
    }

    public static NumberBinding add(final ObservableNumberValue op1, final ObservableNumberValue op2) {

        return add(op1, op2, op1, op2);
    }

    public static DoubleBinding add(final ObservableNumberValue op1, double op2) {

        return (DoubleBinding) add(op1, DoubleConstant.valueOf(op2), op1);
    }

    public static DoubleBinding add(double op1, final ObservableNumberValue op2) {

        return (DoubleBinding) add(DoubleConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding add(final ObservableNumberValue op1, float op2) {

        return add(op1, FloatConstant.valueOf(op2), op1);
    }

    public static NumberBinding add(float op1, final ObservableNumberValue op2) {

        return add(FloatConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding add(final ObservableNumberValue op1, long op2) {

        return add(op1, FloatConstant.valueOf(op2), op1);
    }

    public static NumberBinding add(long op1, final ObservableNumberValue op2) {

        return add(FloatConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding add(final ObservableNumberValue op1, int op2) {

        return add(op1, FloatConstant.valueOf(op2), op1);
    }

    public static NumberBinding add(int op1, final ObservableNumberValue op2) {

        return add(FloatConstant.valueOf(op1), op2, op2);
    }

    ///////////////////////
    /// Number Subtract ///
    ///////////////////////

    private static NumberBinding subtract(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {

        if (op1 == null || op2 == null) {

            throw new NullPointerException("The observable number value1 or value2 is null");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if (op1 instanceof ObservableDoubleValue || op2 instanceof ObservableDoubleValue) {

            return new DoubleBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected double computeValue() {

                    return op1.doubleValue() - op2.doubleValue();
                }
            };
        } else if (op1 instanceof ObservableFloatValue || op2 instanceof ObservableFloatValue) {

            return new FloatBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected float computeValue() {

                    return op1.floatValue() - op2.floatValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else if (op1 instanceof ObservableLongValue || op2 instanceof ObservableLongValue) {

            return new LongBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected long computeValue() {

                    return op1.longValue() - op2.longValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else {

            return new IntegerBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected int computeValue() {

                    return op1.intValue() - op2.intValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        }
    }

    public static NumberBinding subtract(final ObservableNumberValue op1, final ObservableNumberValue op2) {

        return subtract(op1, op2, op1, op2);
    }

    public static DoubleBinding subtract(final ObservableNumberValue op1, double op2) {

        return (DoubleBinding) subtract(op1, DoubleConstant.valueOf(op2), op1);
    }

    public static DoubleBinding subtract(double op1, final ObservableNumberValue op2) {

        return (DoubleBinding) subtract(DoubleConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding subtract(final ObservableNumberValue op1, float op2) {

        return subtract(op1, FloatConstant.valueOf(op2), op1);
    }

    public static NumberBinding subtract(float op1, final ObservableNumberValue op2) {

        return subtract(FloatConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding subtract(final ObservableNumberValue op1, long op2) {

        return subtract(op1, FloatConstant.valueOf(op2), op1);
    }

    public static NumberBinding subtract(long op1, final ObservableNumberValue op2) {

        return subtract(FloatConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding subtract(final ObservableNumberValue op1, int op2) {

        return subtract(op1, FloatConstant.valueOf(op2), op1);
    }

    public static NumberBinding subtract(int op1, final ObservableNumberValue op2) {

        return subtract(FloatConstant.valueOf(op1), op2, op2);
    }

    ///////////////////////
    /// Number Multiply ///
    ///////////////////////

    private static NumberBinding multiply(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {

        if (op1 == null || op2 == null) {

            throw new NullPointerException("The observable number value1 or value2 is null");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if (op1 instanceof ObservableDoubleValue || op2 instanceof ObservableDoubleValue) {

            return new DoubleBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected double computeValue() {

                    return op1.doubleValue() * op2.doubleValue();
                }
            };
        } else if (op1 instanceof ObservableFloatValue || op2 instanceof ObservableFloatValue) {

            return new FloatBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected float computeValue() {

                    return op1.floatValue() * op2.floatValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else if (op1 instanceof ObservableLongValue || op2 instanceof ObservableLongValue) {

            return new LongBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected long computeValue() {

                    return op1.longValue() * op2.longValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else {

            return new IntegerBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected int computeValue() {

                    return op1.intValue() * op2.intValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        }
    }

    public static NumberBinding multiply(final ObservableNumberValue op1, final ObservableNumberValue op2) {

        return multiply(op1, op2, op1, op2);
    }

    public static DoubleBinding multiply(final ObservableNumberValue op1, double op2) {

        return (DoubleBinding) multiply(op1, DoubleConstant.valueOf(op2), op1);
    }

    public static DoubleBinding multiply(double op1, final ObservableNumberValue op2) {

        return (DoubleBinding) multiply(DoubleConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding multiply(final ObservableNumberValue op1, float op2) {

        return multiply(op1, FloatConstant.valueOf(op2), op1);
    }

    public static NumberBinding multiply(float op1, final ObservableNumberValue op2) {

        return multiply(FloatConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding multiply(final ObservableNumberValue op1, long op2) {

        return multiply(op1, FloatConstant.valueOf(op2), op1);
    }

    public static NumberBinding multiply(long op1, final ObservableNumberValue op2) {

        return multiply(FloatConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding multiply(final ObservableNumberValue op1, int op2) {

        return multiply(op1, FloatConstant.valueOf(op2), op1);
    }

    public static NumberBinding multiply(int op1, final ObservableNumberValue op2) {

        return multiply(FloatConstant.valueOf(op1), op2, op2);
    }

    /////////////////////
    /// Number Divide ///
    /////////////////////

    private static NumberBinding divide(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {

        if (op1 == null || op2 == null) {

            throw new NullPointerException("The observable number value1 or value2 is null");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if (op1 instanceof ObservableDoubleValue || op2 instanceof ObservableDoubleValue) {

            return new DoubleBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected double computeValue() {

                    return op1.doubleValue() / op2.doubleValue();
                }
            };
        } else if (op1 instanceof ObservableFloatValue || op2 instanceof ObservableFloatValue) {

            return new FloatBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected float computeValue() {

                    return op1.floatValue() / op2.floatValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else if (op1 instanceof ObservableLongValue || op2 instanceof ObservableLongValue) {

            return new LongBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected long computeValue() {

                    return op1.longValue() / op2.longValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else {

            return new IntegerBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected int computeValue() {

                    return op1.intValue() / op2.intValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        }
    }

    public static NumberBinding divide(final ObservableNumberValue op1, final ObservableNumberValue op2) {

        return divide(op1, op2, op1, op2);
    }

    public static DoubleBinding divide(final ObservableNumberValue op1, double op2) {

        return (DoubleBinding) divide(op1, DoubleConstant.valueOf(op2), op1);
    }

    public static DoubleBinding divide(double op1, final ObservableNumberValue op2) {

        return (DoubleBinding) divide(DoubleConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding divide(final ObservableNumberValue op1, float op2) {

        return divide(op1, FloatConstant.valueOf(op2), op1);
    }

    public static NumberBinding divide(float op1, final ObservableNumberValue op2) {

        return divide(FloatConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding divide(final ObservableNumberValue op1, long op2) {

        return divide(op1, FloatConstant.valueOf(op2), op1);
    }

    public static NumberBinding divide(long op1, final ObservableNumberValue op2) {

        return divide(FloatConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding divide(final ObservableNumberValue op1, int op2) {

        return divide(op1, FloatConstant.valueOf(op2), op1);
    }

    public static NumberBinding divide(int op1, final ObservableNumberValue op2) {

        return divide(FloatConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding zero(final ObservableNumberValue op) {

        if(op == null) {

            throw new NullPointerException("The observable number value is null.");
        }
        if(op instanceof ObservableDoubleValue) {

            return new DoubleBinding() {

                {
                    super.bind(op);
                }

                @Override
                public void dispose() {

                    super.unbind(op);
                }

                @Override
                protected double computeValue() {

                    return 0.0d;
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        }
        else if(op instanceof ObservableFloatValue) {

            return new FloatBinding() {

                {
                    super.bind(op);
                }

                @Override
                public void dispose() {

                    super.unbind(op);
                }

                @Override
                protected float computeValue() {

                    return 0.0f;
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        }
        else if(op instanceof ObservableLongValue) {

            return new LongBinding() {

                {
                    super.bind(op);
                }

                @Override
                public void dispose() {

                    super.unbind(op);
                }

                @Override
                protected long computeValue() {

                    return 0L;
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        }
        else {

            return new IntegerBinding() {

                {
                    super.bind(op);
                }

                @Override
                public void dispose() {

                    super.unbind(op);
                }

                @Override
                protected int computeValue() {

                    return 0;
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        }
    }

    //////////////
    /// Equals ///
    //////////////

    private static BooleanBinding equal(final ObservableNumberValue op1, final ObservableNumberValue op2, final double epsilon, final Observable... dependencies) {

        if (op1 == null || op2 == null) {

            throw new NullPointerException("The observable number value1 or value2 is null");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if (op1 instanceof ObservableDoubleValue || op2 instanceof ObservableDoubleValue) {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return Math.abs(op1.doubleValue() - op2.doubleValue()) <= epsilon;
                }
            };
        } else if (op1 instanceof ObservableFloatValue || op2 instanceof ObservableFloatValue) {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return Math.abs(op1.floatValue() - op2.floatValue()) <= epsilon;
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else if (op1 instanceof ObservableLongValue || op2 instanceof ObservableLongValue) {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return Math.abs(op1.longValue() - op2.longValue()) <= epsilon;
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return Math.abs(op1.intValue() - op2.intValue()) <= epsilon;
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        }
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final ObservableNumberValue op2, final double epsilon) {

        return equal(op1, op2, epsilon, op1, op2);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final ObservableNumberValue op2) {

        return equal(op1, op2, 0.0d, op1, op2);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final double op2, final double epsilon) {

        return equal(op1, DoubleConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding equal(final double op1, final ObservableNumberValue op2, final double epsilon) {

        return equal(DoubleConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final double op2) {

        return equal(op1, DoubleConstant.valueOf(op2), 0.0d, op1);
    }

    public static BooleanBinding equal(final double op1, final ObservableNumberValue op2) {

        return equal(DoubleConstant.valueOf(op1), op2, 0.0d, op2);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final float op2, final double epsilon) {

        return equal(op1, FloatConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding equal(final float op1, final ObservableNumberValue op2, final double epsilon) {

        return equal(FloatConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final float op2) {

        return equal(op1, FloatConstant.valueOf(op2), 0.0d, op1);
    }

    public static BooleanBinding equal(final float op1, final ObservableNumberValue op2) {

        return equal(FloatConstant.valueOf(op1), op2, 0.0d, op2);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final long op2, final double epsilon) {

        return equal(op1, LongConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding equal(final long op1, final ObservableNumberValue op2, final double epsilon) {

        return equal(LongConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final long op2) {

        return equal(op1, LongConstant.valueOf(op2), 0.0d, op1);
    }

    public static BooleanBinding equal(final long op1, final ObservableNumberValue op2) {

        return equal(LongConstant.valueOf(op1), op2, 0.0d, op2);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final int op2, final double epsilon) {

        return equal(op1, IntegerConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding equal(final int op1, final ObservableNumberValue op2, final double epsilon) {

        return equal(IntegerConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final int op2) {

        return equal(op1, IntegerConstant.valueOf(op2), 0.0d, op1);
    }

    public static BooleanBinding equal(final int op1, final ObservableNumberValue op2) {

        return equal(IntegerConstant.valueOf(op1), op2, 0.0d, op2);
    }

    //////////////////
    /// Not Equals ///
    //////////////////

    private static BooleanBinding notEqual(final ObservableNumberValue op1, final ObservableNumberValue op2, final double epsilon, final Observable... dependencies) {

        if (op1 == null || op2 == null) {

            throw new NullPointerException("The observable number value1 or value2 is null");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if (op1 instanceof ObservableDoubleValue || op2 instanceof ObservableDoubleValue) {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return Math.abs(op1.doubleValue() - op2.doubleValue()) > epsilon;
                }
            };
        } else if (op1 instanceof ObservableFloatValue || op2 instanceof ObservableFloatValue) {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return Math.abs(op1.floatValue() - op2.floatValue()) > epsilon;
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else if (op1 instanceof ObservableLongValue || op2 instanceof ObservableLongValue) {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return Math.abs(op1.longValue() - op2.longValue()) > epsilon;
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return Math.abs(op1.intValue() - op2.intValue()) > epsilon;
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        }
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final ObservableNumberValue op2, final double epsilon) {

        return notEqual(op1, op2, epsilon, op1, op2);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final ObservableNumberValue op2) {

        return notEqual(op1, op2, 0.0d, op1, op2);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final double op2, final double epsilon) {

        return notEqual(op1, DoubleConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding notEqual(final double op1, final ObservableNumberValue op2, final double epsilon) {

        return notEqual(DoubleConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final double op2) {

        return notEqual(op1, DoubleConstant.valueOf(op2), 0.0d, op1);
    }

    public static BooleanBinding notEqual(final double op1, final ObservableNumberValue op2) {

        return notEqual(DoubleConstant.valueOf(op1), op2, 0.0d, op2);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final float op2, final double epsilon) {

        return notEqual(op1, FloatConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding notEqual(final float op1, final ObservableNumberValue op2, final double epsilon) {

        return notEqual(FloatConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final float op2) {

        return notEqual(op1, FloatConstant.valueOf(op2), 0.0d, op1);
    }

    public static BooleanBinding notEqual(final float op1, final ObservableNumberValue op2) {

        return notEqual(FloatConstant.valueOf(op1), op2, 0.0d, op2);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final long op2, final double epsilon) {

        return notEqual(op1, LongConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding notEqual(final long op1, final ObservableNumberValue op2, final double epsilon) {

        return notEqual(LongConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final long op2) {

        return notEqual(op1, LongConstant.valueOf(op2), 0.0d, op1);
    }

    public static BooleanBinding notEqual(final long op1, final ObservableNumberValue op2) {

        return notEqual(LongConstant.valueOf(op1), op2, 0.0d, op2);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final int op2, final double epsilon) {

        return notEqual(op1, IntegerConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding notEqual(final int op1, final ObservableNumberValue op2, final double epsilon) {

        return notEqual(IntegerConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final int op2) {

        return notEqual(op1, IntegerConstant.valueOf(op2), 0.0d, op1);
    }

    public static BooleanBinding notEqual(final int op1, final ObservableNumberValue op2) {

        return notEqual(IntegerConstant.valueOf(op1), op2, 0.0d, op2);
    }

    ///////////////////////////
    /// Number Greater Than ///
    ///////////////////////////

    private static BooleanBinding greaterThan(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {

        if (op1 == null || op2 == null) {

            throw new NullPointerException("The observable number value1 or value2 is null");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if (op1 instanceof ObservableDoubleValue || op2 instanceof ObservableDoubleValue) {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return op1.doubleValue() > op2.doubleValue();
                }
            };
        } else if (op1 instanceof ObservableFloatValue || op2 instanceof ObservableFloatValue) {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return op1.floatValue() > op2.floatValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else if (op1 instanceof ObservableLongValue || op2 instanceof ObservableLongValue) {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return op1.longValue() > op2.longValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return op1.intValue() > op2.intValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        }
    }

    public static BooleanBinding greaterThan(final ObservableNumberValue op1, final ObservableNumberValue op2) {

        return greaterThan(op1, op2, op1, op2);
    }

    public static BooleanBinding greaterThan(final ObservableNumberValue op1, double op2) {

        return greaterThan(op1, DoubleConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThan(double op1, final ObservableNumberValue op2) {

        return greaterThan(DoubleConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding greaterThan(final ObservableNumberValue op1, float op2) {

        return greaterThan(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThan(float op1, final ObservableNumberValue op2) {

        return greaterThan(FloatConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding greaterThan(final ObservableNumberValue op1, long op2) {

        return greaterThan(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThan(long op1, final ObservableNumberValue op2) {

        return greaterThan(FloatConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding greaterThan(final ObservableNumberValue op1, int op2) {

        return greaterThan(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThan(int op1, final ObservableNumberValue op2) {

        return greaterThan(FloatConstant.valueOf(op1), op2, op2);
    }

    ////////////////////////
    /// Number Less Than ///
    ////////////////////////

    private static BooleanBinding lessThan(final ObservableNumberValue op1, final ObservableNumberValue op2, Observable... dependencies) {

        return greaterThan(op2, op1, dependencies);
    }

    public static BooleanBinding lessThan(final ObservableNumberValue op1, final ObservableNumberValue op2) {

        return lessThan(op1, op2, op1, op2);
    }

    public static BooleanBinding lessThan(final ObservableNumberValue op1, double op2) {

        return lessThan(op1, DoubleConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThan(double op1, final ObservableNumberValue op2) {

        return lessThan(DoubleConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding lessThan(final ObservableNumberValue op1, float op2) {

        return lessThan(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThan(float op1, final ObservableNumberValue op2) {

        return lessThan(FloatConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding lessThan(final ObservableNumberValue op1, long op2) {

        return lessThan(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThan(long op1, final ObservableNumberValue op2) {

        return lessThan(FloatConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding lessThan(final ObservableNumberValue op1, int op2) {

        return lessThan(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThan(int op1, final ObservableNumberValue op2) {

        return lessThan(FloatConstant.valueOf(op1), op2, op2);
    }

    ////////////////////////////////////
    /// Number Greater Than or Equal ///
    ////////////////////////////////////

    private static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {

        if (op1 == null || op2 == null) {

            throw new NullPointerException("The observable number value1 or value2 is null");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if (op1 instanceof ObservableDoubleValue || op2 instanceof ObservableDoubleValue) {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return op1.doubleValue() >= op2.doubleValue();
                }
            };
        } else if (op1 instanceof ObservableFloatValue || op2 instanceof ObservableFloatValue) {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return op1.floatValue() >= op2.floatValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else if (op1 instanceof ObservableLongValue || op2 instanceof ObservableLongValue) {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return op1.longValue() >= op2.longValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        } else {

            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {

                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {

                    return op1.intValue() >= op2.intValue();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        }
    }

    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, final ObservableNumberValue op2) {

        return greaterThanOrEqual(op1, op2, op1, op2);
    }

    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, double op2) {

        return greaterThanOrEqual(op1, DoubleConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThanOrEqual(double op1, final ObservableNumberValue op2) {

        return greaterThanOrEqual(DoubleConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, float op2) {

        return greaterThanOrEqual(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThanOrEqual(float op1, final ObservableNumberValue op2) {

        return greaterThanOrEqual(FloatConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, long op2) {

        return greaterThanOrEqual(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThanOrEqual(long op1, final ObservableNumberValue op2) {

        return greaterThanOrEqual(FloatConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, int op2) {

        return greaterThanOrEqual(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThanOrEqual(int op1, final ObservableNumberValue op2) {

        return greaterThanOrEqual(FloatConstant.valueOf(op1), op2, op2);
    }

    /////////////////////////////////
    /// Number Less Than or Equal ///
    /////////////////////////////////

    private static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, final ObservableNumberValue op2, Observable... dependencies) {

        return greaterThanOrEqual(op2, op1, dependencies);
    }

    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, final ObservableNumberValue op2) {

        return lessThanOrEqual(op1, op2, op1, op2);
    }

    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, double op2) {

        return lessThanOrEqual(op1, DoubleConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThanOrEqual(double op1, final ObservableNumberValue op2) {

        return lessThanOrEqual(DoubleConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, float op2) {

        return lessThanOrEqual(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThanOrEqual(float op1, final ObservableNumberValue op2) {

        return lessThanOrEqual(FloatConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, long op2) {

        return lessThanOrEqual(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThanOrEqual(long op1, final ObservableNumberValue op2) {

        return lessThanOrEqual(FloatConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, int op2) {

        return lessThanOrEqual(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThanOrEqual(int op1, final ObservableNumberValue op2) {

        return lessThanOrEqual(FloatConstant.valueOf(op1), op2, op2);
    }

    //////////////
    /// String ///
    //////////////

    public static String getStringSafe(String value) {

        return value == null ? "" : value;
    }

    public static StringExpression format(String format, Object... args) {

        return StringFormatter.format(format, args);
    }

    public static StringExpression format(Locale locale, String format, Object... args) {

        return StringFormatter.format(locale, format, args);
    }

    public static StringExpression concat(Object... args) {

        return StringFormatter.concat(args);
    }

    private static BooleanBinding equal(final ObservableStringValue op1, final ObservableStringValue op2, Observable... dependencies) {

        if(op1 == null || op2 == null) {

            throw new NullPointerException("The observable string value1 or value2 is null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {

                final String str1 = getStringSafe(op1.get());
                final String str2 = getStringSafe(op2.get());
                return str1.equals(str2);
            }
        };
    }

    public static BooleanBinding equal(final ObservableStringValue op1, final ObservableStringValue op2) {

        return equal(op1, op2, op1, op2);
    }

    public static BooleanBinding equal(final ObservableStringValue op1, String op2) {

        return equal(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding equal(final String op1, ObservableStringValue op2) {

        return equal(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding notEqual(final ObservableStringValue op1, final ObservableStringValue op2, Observable... dependencies) {

        if(op1 == null || op2 == null) {

            throw new NullPointerException("The observable string value1 or value2 is null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {

                final String str1 = getStringSafe(op1.get());
                final String str2 = getStringSafe(op2.get());
                return !str1.equals(str2);
            }
        };
    }

    public static BooleanBinding notEqual(final ObservableStringValue op1, final ObservableStringValue op2) {

        return notEqual(op1, op2, op1, op2);
    }

    public static BooleanBinding notEqual(final ObservableStringValue op1, String op2) {

        return notEqual(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding notEqual(final String op1, ObservableStringValue op2) {

        return notEqual(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding equalIgnoreCase(final ObservableStringValue op1, final ObservableStringValue op2, Observable... dependencies) {

        if(op1 == null || op2 == null) {

            throw new NullPointerException("The observable string value1 or value2 is null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {

                final String str1 = getStringSafe(op1.get());
                final String str2 = getStringSafe(op2.get());
                return str1.equalsIgnoreCase(str2);
            }
        };
    }

    public static BooleanBinding equalIgnoreCase(final ObservableStringValue op1, final ObservableStringValue op2) {

        return equalIgnoreCase(op1, op2, op1, op2);
    }

    public static BooleanBinding equalIgnoreCase(final ObservableStringValue op1, String op2) {

        return equalIgnoreCase(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding equalIgnoreCase(final String op1, ObservableStringValue op2) {

        return equalIgnoreCase(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding notEqualIgnoreCase(final ObservableStringValue op1, final ObservableStringValue op2, Observable... dependencies) {

        if(op1 == null || op2 == null) {

            throw new NullPointerException("The observable string value1 or value2 is null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {

                final String str1 = getStringSafe(op1.get());
                final String str2 = getStringSafe(op2.get());
                return !str1.equalsIgnoreCase(str2);
            }
        };
    }

    public static BooleanBinding notEqualIgnoreCase(final ObservableStringValue op1, final ObservableStringValue op2) {

        return notEqualIgnoreCase(op1, op2, op1, op2);
    }

    public static BooleanBinding notEqualIgnoreCase(final ObservableStringValue op1, String op2) {

        return notEqualIgnoreCase(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding notEqualIgnoreCase(final String op1, ObservableStringValue op2) {

        return notEqualIgnoreCase(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding greaterThan(final ObservableStringValue op1, final ObservableStringValue op2, Observable... dependencies) {

        if(op1 == null || op2 == null) {

            throw new NullPointerException("The observable string value1 or value2 is null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {

                final String str1 = getStringSafe(op1.get());
                final String str2 = getStringSafe(op2.get());
                return str1.compareTo(str2) > 0;
            }
        };
    }

    public static BooleanBinding greaterThan(final ObservableStringValue op1, final ObservableStringValue op2) {

        return greaterThan(op1, op2, op1, op2);
    }

    public static BooleanBinding greaterThan(final ObservableStringValue op1, String op2) {

        return greaterThan(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThan(final String op1, ObservableStringValue op2) {

        return greaterThan(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding lessThan(final ObservableStringValue op1, final ObservableStringValue op2, final Observable... dependencies) {

        return greaterThan(op2, op1, dependencies);
    }

    public static BooleanBinding lessThan(final ObservableStringValue op1, final ObservableStringValue op2) {

        return lessThan(op1, op2, op1, op2);
    }

    public static BooleanBinding lessThan(final ObservableStringValue op1, String op2) {

        return lessThan(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThan(final String op1, ObservableStringValue op2) {

        return lessThan(StringConstant.valueOf(op1), op2, op2);
    }

    //

    private static BooleanBinding greaterThanOrEqual(final ObservableStringValue op1, final ObservableStringValue op2, Observable... dependencies) {

        if(op1 == null || op2 == null) {

            throw new NullPointerException("The observable string value1 or value2 is null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {

                final String str1 = getStringSafe(op1.get());
                final String str2 = getStringSafe(op2.get());
                return str1.compareTo(str2) >= 0;
            }
        };
    }

    public static BooleanBinding greaterThanOrEqual(final ObservableStringValue op1, final ObservableStringValue op2) {

        return greaterThanOrEqual(op1, op2, op1, op2);
    }

    public static BooleanBinding greaterThanOrEqual(final ObservableStringValue op1, String op2) {

        return greaterThanOrEqual(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThanOrEqual(final String op1, ObservableStringValue op2) {

        return greaterThanOrEqual(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding lessThanOrEqual(final ObservableStringValue op1, final ObservableStringValue op2, final Observable... dependencies) {

        return lessThanOrEqual(op2, op1, dependencies);
    }

    public static BooleanBinding lessThanOrEqual(final ObservableStringValue op1, final ObservableStringValue op2) {

        return lessThanOrEqual(op1, op2, op1, op2);
    }

    public static BooleanBinding lessThanOrEqual(final ObservableStringValue op1, String op2) {

        return lessThanOrEqual(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThanOrEqual(final String op1, ObservableStringValue op2) {

        return lessThanOrEqual(StringConstant.valueOf(op1), op2, op2);
    }

    public static IntegerBinding length(final ObservableStringValue op) {

        if(op == null) {

            throw new NullPointerException("The observable string value is null.");
        }
        return new IntegerBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {

                super.unbind(op);
            }

            @Override
            protected int computeValue() {

                return getStringSafe(op.get()).length();
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    public static BooleanBinding isEmpty(final ObservableStringValue op) {

        if(op == null) {

            throw new NullPointerException("The observable string value is null.");
        }
        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {

                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {

                return getStringSafe(op.get()).isEmpty();
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    public static BooleanBinding isNotEmpty(final ObservableStringValue op) {

        if(op == null) {

            throw new NullPointerException("The observable string value is null.");
        }
        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {

                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {

                return !getStringSafe(op.get()).isEmpty();
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    ///////////////
    /// Boolean ///
    ///////////////

    private static class BooleanAndBinding extends BooleanBinding {

        private final ObservableBooleanValue op1;
        private final ObservableBooleanValue op2;
        private final InvalidationListener observer;

        public BooleanAndBinding(ObservableBooleanValue op1, ObservableBooleanValue op2) {

            this.op1 = op1;
            this.op2 = op2;
            this.observer = new ShortCircuitAndInvalidator(this);
            this.op1.addListener(observer);
            this.op2.addListener(observer);
        }

        @Override
        public void dispose() {

            op1.removeListener(observer);
            op2.removeListener(observer);
        }

        @Override
        protected boolean computeValue() {

            return op1.get() && op2.get();
        }

        @Override
        public ObservableList<?> getDependencies() {

            return null;
        }
    }

    private static class ShortCircuitAndInvalidator implements InvalidationListener {

        private final WeakReference<BooleanAndBinding> ref;

        private ShortCircuitAndInvalidator(BooleanAndBinding binding) {

            assert binding != null;

            this.ref = new WeakReference<>(binding);
        }

        @Override
        public void invalidated(Observable observable) {

            final BooleanAndBinding binding = ref.get();

            if(binding == null) {

                observable.removeListener(this);
            }
            else {

                if((binding.op1.equals(observable)) || (binding.isValid() && binding.op1.get())) {

                    binding.invalidate();
                }
            }
        }
    }

    public static BooleanBinding and(final ObservableBooleanValue op1, final ObservableBooleanValue op2) {

        if(op1 == null || op2 == null) {

            throw new NullPointerException("The observable boolean value1 or value2 is null.");
        }
        return new BooleanAndBinding(op1, op2);
    }

    private static class BooleanOrBinding extends BooleanBinding {

        private final ObservableBooleanValue op1;
        private final ObservableBooleanValue op2;
        private final InvalidationListener observer;

        public BooleanOrBinding(ObservableBooleanValue op1, ObservableBooleanValue op2) {

            this.op1 = op1;
            this.op2 = op2;
            this.observer = new ShortCircuitOrInvalidator(this);
            this.op1.addListener(observer);
            this.op2.addListener(observer);
        }

        @Override
        public void dispose() {

            op1.removeListener(observer);
            op2.removeListener(observer);
        }

        @Override
        protected boolean computeValue() {

            return op1.get() || op2.get();
        }

        @Override
        public ObservableList<?> getDependencies() {

            return null;
        }
    }

    private static class ShortCircuitOrInvalidator implements InvalidationListener {

        private final WeakReference<BooleanOrBinding> ref;

        private ShortCircuitOrInvalidator(BooleanOrBinding binding) {

            assert binding != null;

            this.ref = new WeakReference<>(binding);
        }

        @Override
        public void invalidated(Observable observable) {

            final BooleanOrBinding binding = ref.get();

            if(binding == null) {

                observable.removeListener(this);
            }
            else {

                if((binding.op1.equals(observable)) || (binding.isValid() && !binding.op1.get())) {

                    binding.invalidate();
                }
            }
        }
    }

    public static BooleanBinding or(final ObservableBooleanValue op1, final ObservableBooleanValue op2) {

        if(op1 == null || op2 == null) {

            throw new NullPointerException("The observable boolean value1 or value2 is null.");
        }
        return new BooleanOrBinding(op1, op2);
    }

    public static BooleanBinding not(final ObservableBooleanValue op) {

        if(op == null) {

            throw new NullPointerException("The observable boolean value is null.");
        }
        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {

                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {

                return !op.get();
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    public static BooleanBinding equal(final ObservableBooleanValue op1, final ObservableBooleanValue op2) {

        if(op1 == null || op2 == null) {

            throw new NullPointerException("The observable boolean value1 or value2 is null.");
        }
        return new BooleanBinding() {

            {
                super.bind(op1, op2);
            }

            @Override
            public void dispose() {

                super.unbind(op1, op2);
            }

            @Override
            protected boolean computeValue() {

                return op1.get() == op2.get();
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    public static BooleanBinding notEqual(final ObservableBooleanValue op1, final ObservableBooleanValue op2) {

        if(op1 == null || op2 == null) {

            throw new NullPointerException("The observable boolean value1 or value2 is null.");
        }
        return new BooleanBinding() {

            {
                super.bind(op1, op2);
            }

            @Override
            public void dispose() {

                super.unbind(op1, op2);
            }

            @Override
            protected boolean computeValue() {

                return op1.get() != op2.get();
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    //////////////
    /// Object ///
    //////////////

    private static BooleanBinding equal(final ObservableObjectValue<?> op1, final ObservableObjectValue<?> op2, Observable... dependencies) {

        if(op1 == null || op2 == null) {

            throw new NullPointerException("The observable object value1 or value2 is null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {

                final Object obj1 = op1.get();
                final Object obj2 = op2.get();

                return obj1 == null ? obj2 == null : obj1.equals(obj2);
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    public static BooleanBinding equal(final ObservableObjectValue<?> op1, ObservableObjectValue<?> op2) {

        return equal(op1, op2, op1, op2);
    }

    public static BooleanBinding equal(final ObservableObjectValue<?> op1, Object op2) {

        return equal(op1, ObjectConstant.valueOf(op2), op1);
    }

    public static BooleanBinding equal(Object op1, ObservableObjectValue op2) {

        return equal(ObjectConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding notEqual(final ObservableObjectValue<?> op1, final ObservableObjectValue<?> op2, Observable... dependencies) {

        if(op1 == null || op2 == null) {

            throw new NullPointerException("The observable object value1 or value2 is null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {

                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {

                final Object obj1 = op1.get();
                final Object obj2 = op2.get();

                return obj1 == null ? obj2 != null : !obj1.equals(obj2);
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    public static BooleanBinding notEqual(final ObservableObjectValue<?> op1, ObservableObjectValue<?> op2) {

        return notEqual(op1, op2, op1, op2);
    }

    public static BooleanBinding notEqual(final ObservableObjectValue<?> op1, Object op2) {

        return notEqual(op1, ObjectConstant.valueOf(op2), op1);
    }

    public static BooleanBinding notEqual(Object op1, ObservableObjectValue op2) {

        return notEqual(ObjectConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding isNull(final ObservableObjectValue<?> op) {

        if(op == null) {

            throw new NullPointerException("The observable object value is null.");
        }
        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {

                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {

                return op.get() == null;
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }

    public static BooleanBinding isNotNull(final ObservableObjectValue<?> op) {

        if(op == null) {

            throw new NullPointerException("The observable object value is null.");
        }
        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {

                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {

                return op.get() != null;
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }
}
