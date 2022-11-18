package com.caverock.androidsvg.model;

public class SvgRadialGradient extends GradientElement {
    public Length cx;
    public Length cy;
    public Length r;
    public Length fx;
    public Length fy;

    @Override
    public String getNodeName() {
        return "radialGradient";
    }
}
