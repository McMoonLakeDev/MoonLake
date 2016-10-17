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
import com.minecraft.moonlake.value.ObservableBooleanValue;
import com.minecraft.moonlake.value.ObservableValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class BooleanExpression implements ObservableBooleanValue {

    public BooleanExpression() {

    }

    @Override
    public Boolean getValue() {

        return get();
    }

    public static BooleanExpression booleanExpression(final ObservableBooleanValue value) {

        if(value == null) {

            throw new NullPointerException("The observable boolean value is null.");
        }
        return value instanceof BooleanExpression ? (BooleanExpression) value

                : new BooleanBinding() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected boolean computeValue() {

                    return value.get();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
    }

    public static BooleanExpression booleanExpression(final ObservableValue<Boolean> value) {

        if(value == null) {

            throw new NullPointerException("The observable boolean value is null.");
        }
        return value instanceof BooleanExpression ? (BooleanExpression) value

                : new BooleanBinding() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {

                    super.unbind(value);
                }

                @Override
                protected boolean computeValue() {

                    final Boolean val = value.getValue();
                    return val == null ? false : val;
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
    }

    public BooleanBinding and(final ObservableBooleanValue other) {

        return Bindings.and(this, other);
    }

    public BooleanBinding or(final ObservableBooleanValue other) {

        return Bindings.or(this, other);
    }

    public BooleanBinding not() {

        return Bindings.not(this);
    }

    public BooleanBinding isEqualTo(final ObservableBooleanValue other) {

        return Bindings.equal(this, other);
    }

    public BooleanBinding isNotEqualTo(final ObservableBooleanValue other) {

        return Bindings.notEqual(this, other);
    }

    public StringBinding asString() {

        return (StringBinding) StringFormatter.convert(this);
    }

    public ObjectExpression<Boolean> asObject() {

        return new ObjectBinding<Boolean>() {

            {
                super.bind(BooleanExpression.this);
            }

            @Override
            public void dispose() {

                super.unbind(BooleanExpression.this);
            }

            @Override
            protected Boolean computeValue() {

                return BooleanExpression.this.getValue();
            }
        };
    }
}
