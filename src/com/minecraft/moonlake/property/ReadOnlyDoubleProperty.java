package com.minecraft.moonlake.property;

import com.minecraft.moonlake.binding.DoubleExpression;
import com.minecraft.moonlake.binding.ObjectExpression;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.WeakInvalidationListener;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class ReadOnlyDoubleProperty extends DoubleExpression implements ReadOnlyProperty<Number> {

    public ReadOnlyDoubleProperty() {

    }

    @Override
    public String toString() {

        return "ReadOnlyDoubleProperty [value: " + get() + "]";
    }

    public static <T extends Number> ReadOnlyDoubleProperty readOnlyDoubleProperty(final ReadOnlyProperty<T> property) {

        if(property == null) {

            throw new NullPointerException("The read only property is null.");
        }
        return property instanceof ReadOnlyDoubleProperty ? (ReadOnlyDoubleProperty) property

                : new ReadOnlyDoublePropertyBase() {

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
            public double get() {

                valid = true;
                final T value = property.getValue();
                return value == null ? 0.0d : value.doubleValue();
            }
        };
    }

    @Override
    public ObjectExpression<Double> asObject() {

        return new ReadOnlyObjectPropertyBase<Double>() {

            private boolean valid = true;
            private final InvalidationListener listener = observable -> {

                if(valid) {

                    valid = false;
                    fireValueChangedEvent();
                }
            };

            {
                ReadOnlyDoubleProperty.this.addListener(new WeakInvalidationListener(listener));
            }

            @Override
            public Double get() {

                valid = true;
                return ReadOnlyDoubleProperty.this.getValue();
            }
        };
    }
}
