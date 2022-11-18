package com.caverock.androidsvg.model;

public class Ellipse extends GraphicsElement {
    public Length cx;
    public Length cy;
    public Length rx;
    public Length ry;

    @Override
    public String getNodeName() {
        return "ellipse";
    }
}
