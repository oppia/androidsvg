package com.caverock.androidsvg.model;

public class Rect extends GraphicsElement {
    public Length x;
    public Length y;
    public Length width;
    public Length height;
    public Length rx;
    public Length ry;

    @Override
    public String getNodeName() {
        return "rect";
    }
}
