package com.caverock.androidsvg.model;

public class Length implements Cloneable {
    public static final double  SQRT2 = 1.414213562373095;

    final float value;
    public final Unit unit;

    public final static Length ZERO = new Length(0f);

    public Length(float value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    public Length(float value) {
        this.value = value;
        this.unit = Unit.px;
    }

    public float floatValue() {
        return value;
    }

    public boolean isZero() {
        return Math.abs(value) <= 1e-5f;
    }

    public boolean isNegative() {
        return value < 0f;
    }

    @Override
    public String toString() {
        return String.valueOf(value) + unit;
    }
}
