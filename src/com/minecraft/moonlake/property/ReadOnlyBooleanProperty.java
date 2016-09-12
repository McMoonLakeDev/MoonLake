package com.minecraft.moonlake.property;

import com.minecraft.moonlake.binding.BooleanExpression;
import com.minecraft.moonlake.binding.ObjectExpression;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.WeakInvalidationListener;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class ReadOnlyBooleanProperty extends BooleanExpression implements ReadOnlyProperty<Boolean> {

    public ReadOnlyBooleanProperty() {

    }

    @Override
    public String toString() {

        return "ReadOnlyBooleanProperty [value: " + get() + "]";
    }

    public static ReadOnlyBooleanProperty readOnlyBooleanProperty(final ReadOnlyProperty<Boolean> property) {

        if(property == null) {

            throw new NullPointerException("The read only property is null.");
        }
        return property instanceof ReadOnlyBooleanProperty ? (ReadOnlyBooleanProperty) property

                : new ReadOnlyBooleanPropertyBase() {

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
            public boolean get() {

                valid = true;
                final Boolean value = property.getValue();
                return value == null ? false : value;
            }
        };
    }

    @Override
    public ObjectExpression<Boolean> asObject() {

        return new ReadOnlyObjectPropertyBase<Boolean>() {

            private boolean valid = true;
            private final InvalidationListener listener = observable -> {

                if(valid) {

                    valid = false;
                    fireValueChangedEvent();
                }
            };

            {
                ReadOnlyBooleanProperty.this.addListener(new WeakInvalidationListener(listener));
            }

            @Override
            public Boolean get() {

                valid = true;
                return ReadOnlyBooleanProperty.this.getValue();
            }
        };
    }
}
