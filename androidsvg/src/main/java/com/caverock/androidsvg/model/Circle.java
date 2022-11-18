package com.caverock.androidsvg.model;

public class Circle extends GraphicsElement {
    public Length cx;
    public Length cy;
    public Length r;

    @Override
    public String getNodeName() {
        return "circle";
    }
}
