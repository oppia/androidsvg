package com.caverock.androidsvg.model;

// Special version of Colour that indicates use of 'currentColor' keyword
public class CurrentColor extends SvgPaint {
    private final static CurrentColor instance = new CurrentColor();

    private CurrentColor() {
    }

    public static CurrentColor getInstance() {
        return instance;
    }
}
