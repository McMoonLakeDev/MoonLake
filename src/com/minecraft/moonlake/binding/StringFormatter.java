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
import com.minecraft.moonlake.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class StringFormatter extends StringBinding {

    private static Object extractValue(Object obj) {

        return obj instanceof ObservableValue ? ((ObservableValue<?>) obj).getValue() : obj;
    }

    private static Object[] extractValues(Object[] objs) {

        final int length = objs.length;
        final Object[] values = new Object[length];

        for(int i = 0; i < length; i++) {

            values[i] = extractValue(objs[i]);
        }
        return values;
    }

    private static ObservableValue<?>[] extractDependencies(Object... args) {

        final List<ObservableValue<?>> dependencies = new ArrayList<>();

        for(final Object obj : args) {

            if(obj instanceof ObservableValue) {

                dependencies.add((ObservableValue<?>) obj);
            }
        }
        return dependencies.toArray(new ObservableValue[dependencies.size()]);
    }

    public static StringExpression convert(final ObservableValue<?> observableValue) {

        if(observableValue == null) {

            throw new NullPointerException("The observable value is null.");
        }
        if(observableValue instanceof StringExpression) {

            return (StringExpression) observableValue;
        }
        else {

            return new StringBinding() {

                {
                    super.bind(observableValue);
                }

                @Override
                public void dispose() {

                    super.unbind(observableValue);
                }

                @Override
                protected String computeValue() {

                    final Object value = observableValue.getValue();
                    return value == null ? "null" : value.toString();
                }

                @Override
                public ObservableList<?> getDependencies() {

                    return null;
                }
            };
        }
    }

    public static StringExpression concat(final Object... args) {

        if(args == null || args.length == 0) {

            return StringConstant.valueOf("");
        }
        if(args.length == 1) {

            final Object cur = args[0];
            return cur instanceof ObservableValue ? convert((ObservableValue<?>) cur) : StringConstant.valueOf(cur.toString());
        }
        if(extractDependencies(args).length == 0) {

            final StringBuilder builder = new StringBuilder();

            for(final Object obj : args) {

                builder.append(obj);
            }
            return StringConstant.valueOf(builder.toString());
        }
        return new StringFormatter() {

            {
                super.bind((Observable[]) StringFormatter.extractDependencies(args));
            }

            @Override
            public void dispose() {

                super.unbind((Observable[]) StringFormatter.extractDependencies(args));
            }

            @Override
            protected String computeValue() {

                final StringBuilder builder = new StringBuilder();

                for(final Object obj : args) {

                    builder.append(StringFormatter.extractValue(obj));
                }
                return builder.toString();
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
    }
    public static StringExpression format(final String format, final Object... args) {

        if(format == null) {

            throw new NullPointerException("The format is null.");
        }
        if(extractDependencies(args).length == 0) {

            return StringConstant.valueOf(String.format(format, args));
        }
        final StringFormatter formatter = new StringFormatter() {

            {
                super.bind((Observable[]) StringFormatter.extractDependencies(args));
            }

            @Override
            public void dispose() {

                super.unbind((Observable[]) StringFormatter.extractDependencies(args));
            }

            @Override
            protected String computeValue() {

                final Object[] values = StringFormatter.extractValues(args);
                return String.format(format, values);
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
        formatter.get();
        return formatter;
    }


    public static StringExpression format(final Locale locale, final String format, final Object... args) {

        if(format == null) {

            throw new NullPointerException("The format is null.");
        }
        if(extractDependencies(args).length == 0) {

            return StringConstant.valueOf(String.format(locale, format, args));
        }
        final StringFormatter formatter = new StringFormatter() {

            {
                super.bind((Observable[]) StringFormatter.extractDependencies(args));
            }

            @Override
            public void dispose() {

                super.unbind((Observable[]) StringFormatter.extractDependencies(args));
            }

            @Override
            protected String computeValue() {

                final Object[] values = StringFormatter.extractValues(args);
                return String.format(locale, format, values);
            }

            @Override
            public ObservableList<?> getDependencies() {

                return null;
            }
        };
        formatter.get();
        return formatter;
    }
}
