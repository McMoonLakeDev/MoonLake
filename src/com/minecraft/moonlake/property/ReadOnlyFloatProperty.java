package com.minecraft.moonlake.property;

import com.minecraft.moonlake.binding.FloatExpression;
import com.minecraft.moonlake.binding.ObjectExpression;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.WeakInvalidationListener;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class ReadOnlyFloatProperty extends FloatExpression implements ReadOnlyProperty<Number> {

    public ReadOnlyFloatProperty() {

    }

    @Override
    public String toString() {

        return "ReadOnlyFloatProperty [value: " + get() + "]";
    }

    public static <T extends Number> ReadOnlyFloatProperty readOnlyFloatProperty(final ReadOnlyProperty<T> property) {

        if(property == null) {

            throw new NullPointerException("The read only property is null.");
        }
        return property instanceof ReadOnlyFloatProperty ? (ReadOnlyFloatProperty) property

                : new ReadOnlyFloatPropertyBase() {

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
            public float get() {

                valid = true;
                final T value = property.getValue();
                return value == null ? 0.0f : value.floatValue();
            }
        };
    }

    @Override
    public ObjectExpression<Float> asObject() {

        return new ReadOnlyObjectPropertyBase<Float>() {

            private boolean valid = true;
            private final InvalidationListener listener = observable -> {

                if(valid) {

                    valid = false;
                    fireValueChangedEvent();
                }
            };

            {
                ReadOnlyFloatProperty.this.addListener(new WeakInvalidationListener(listener));
            }

            @Override
            public Float get() {

                valid = true;
                return ReadOnlyFloatProperty.this.getValue();
            }
        };
    }
}
