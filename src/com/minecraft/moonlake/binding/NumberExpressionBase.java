package com.minecraft.moonlake.binding;

import com.minecraft.moonlake.value.*;

import java.util.Locale;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class NumberExpressionBase implements NumberExpression {

    public static <S extends Number> NumberExpressionBase numberExpression(final ObservableNumberValue value) {

        if(value == null) {

            throw new NullPointerException("The observablue number value is null.");
        }
        NumberExpressionBase result = (NumberExpressionBase) ((value instanceof NumberExpressionBase) ? value
                : (value instanceof ObservableIntegerValue) ? IntegerExpression
                        .integerExpression((ObservableIntegerValue) value)
                        : (value instanceof ObservableLongValue) ? LongExpression
                                .longExpression((ObservableLongValue) value)
                                : (value instanceof ObservableFloatValue) ? FloatExpression
                                        .floatExpression((ObservableFloatValue) value)
                                        : (value instanceof ObservableDoubleValue) ? DoubleExpression
                                        .doubleExpression((ObservableDoubleValue) value)
                                        : null);

        if(result != null) {

            return result;
        }
        else {

            throw new IllegalArgumentException("The argument type tnsupported.");
        }
    }

    @Override
    public NumberBinding negate() {

        return Bindings.negate(this);
    }

    @Override
    public NumberBinding add(ObservableNumberValue other) {

        return Bindings.add(this, other);
    }

    @Override
    public NumberBinding subtract(ObservableNumberValue other) {

        return Bindings.subtract(this, other);
    }

    @Override
    public NumberBinding multiply(ObservableNumberValue other) {

        return Bindings.multiply(this, other);
    }

    @Override
    public NumberBinding divide(ObservableNumberValue other) {

        return Bindings.divide(this, other);
    }

    @Override
    public BooleanBinding isEqualTo(ObservableNumberValue other, double epsilon) {

        return Bindings.equal(this, other, epsilon);
    }

    @Override
    public BooleanBinding isEqualTo(ObservableNumberValue other) {

        return Bindings.equal(this, other);
    }

    @Override
    public BooleanBinding isEqualTo(double other, double epsilon) {

        return Bindings.equal(this, other, epsilon);
    }

    @Override
    public BooleanBinding isEqualTo(float other, double epsilon) {

        return Bindings.equal(this, other, epsilon);
    }

    @Override
    public BooleanBinding isEqualTo(long other, double epsilon) {

        return Bindings.equal(this, other, epsilon);
    }

    @Override
    public BooleanBinding isEqualTo(long other) {

        return Bindings.equal(this, other);
    }

    @Override
    public BooleanBinding isEqualTo(int other, double epsilon) {

        return Bindings.equal(this, other, epsilon);
    }

    @Override
    public BooleanBinding isEqualTo(int other) {

        return Bindings.equal(this, other);
    }

    @Override
    public BooleanBinding isNotEqualTo(ObservableNumberValue other, double epsilon) {

        return Bindings.notEqual(this, other, epsilon);
    }

    @Override
    public BooleanBinding isNotEqualTo(ObservableNumberValue other) {

        return Bindings.notEqual(this, other);
    }

    @Override
    public BooleanBinding isNotEqualTo(double other, double epsilon) {

        return Bindings.notEqual(this, other, epsilon);
    }

    @Override
    public BooleanBinding isNotEqualTo(float other, double epsilon) {

        return Bindings.notEqual(this, other, epsilon);
    }

    @Override
    public BooleanBinding isNotEqualTo(long other, double epsilon) {

        return Bindings.notEqual(this, other, epsilon);
    }

    @Override
    public BooleanBinding isNotEqualTo(long other) {

        return Bindings.notEqual(this, other);
    }

    @Override
    public BooleanBinding isNotEqualTo(int other, double epsilon) {

        return Bindings.notEqual(this, other);
    }

    @Override
    public BooleanBinding isNotEqualTo(int other) {

        return Bindings.notEqual(this, other);
    }

    @Override
    public BooleanBinding greaterThan(ObservableNumberValue other) {

        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding greaterThan(double other) {

        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding greaterThan(float other) {

        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding greaterThan(long other) {

        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding greaterThan(int other) {

        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding lessThan(ObservableNumberValue other) {

        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding lessThan(double other) {

        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding lessThan(float other) {

        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding lessThan(long other) {

        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding lessThan(int other) {

        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(ObservableNumberValue other) {

        return Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(double other) {

        return Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(float other) {

        return Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(long other) {

        return Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(int other) {

        return Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(ObservableNumberValue other) {

        return Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(double other) {

        return Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(float other) {

        return Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(long other) {

        return Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(int other) {

        return Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public StringBinding asString() {

        return (StringBinding) StringFormatter.convert(this);
    }

    @Override
    public StringBinding asString(String format) {

        return (StringBinding) Bindings.format(format, this);
    }

    @Override
    public StringBinding asString(Locale locale, String format) {

        return (StringBinding) Bindings.format(locale, format, this);
    }
}
