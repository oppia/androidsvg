package com.caverock.androidsvg.model;

public class PolyLine extends GraphicsElement {
    public float[] points;

    @Override
    public String getNodeName() {
        return "polyline";
    }
}
