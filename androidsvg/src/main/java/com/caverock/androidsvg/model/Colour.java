package com.caverock.androidsvg.model;

public class Colour extends SvgPaint {
    public final int colour;

    public static final Colour BLACK = new Colour(0xff000000);  // Black singleton - a common default value.
    public static final Colour TRANSPARENT = new Colour(0);     // Transparent black

    public Colour(int val) {
        this.colour = val;
    }

    public String toString() {
        return String.format("#%08x", colour);
    }
}
