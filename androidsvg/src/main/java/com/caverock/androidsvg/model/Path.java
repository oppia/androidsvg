package com.caverock.androidsvg.model;

public class Path extends GraphicsElement {
    public PathDefinition d;
    public Float pathLength;

    @Override
    public String getNodeName() {
        return "path";
    }
}
