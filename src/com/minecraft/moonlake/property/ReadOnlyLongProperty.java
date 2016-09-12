package com.minecraft.moonlake.property;

import com.minecraft.moonlake.binding.LongExpression;
import com.minecraft.moonlake.binding.ObjectExpression;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.WeakInvalidationListener;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class ReadOnlyLongProperty extends LongExpression implements ReadOnlyProperty<Number> {

    public ReadOnlyLongProperty() {

    }

    @Override
    public String toString() {

        return "ReadOnlyLongProperty [value: " + get() + "]";
    }

    public static <T extends Number> ReadOnlyLongProperty readOnlyLongProperty(final ReadOnlyProperty<T> property) {

        if(property == null) {

            throw new NullPointerException("The read only property is null.");
        }
        return property instanceof ReadOnlyLongProperty ? (ReadOnlyLongProperty) property

                : new ReadOnlyLongPropertyBase() {

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
            public long get() {

                valid = true;
                final T value = property.getValue();
                return value == null ? 0L : value.longValue();
            }
        };
    }

    @Override
    public ObjectExpression<Long> asObject() {

        return new ReadOnlyObjectPropertyBase<Long>() {

            private boolean valid = true;
            private final InvalidationListener listener = observable -> {

                if(valid) {

                    valid = false;
                    fireValueChangedEvent();
                }
            };

            {
                ReadOnlyLongProperty.this.addListener(new WeakInvalidationListener(listener));
            }

            @Override
            public Long get() {

                valid = true;
                return ReadOnlyLongProperty.this.getValue();
            }
        };
    }
}
