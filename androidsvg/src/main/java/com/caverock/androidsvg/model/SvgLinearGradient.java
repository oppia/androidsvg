package com.caverock.androidsvg.model;

public class SvgLinearGradient extends GradientElement {
    public Length x1;
    public Length y1;
    public Length x2;
    public Length y2;

    @Override
    public String getNodeName() {
        return "linearGradient";
    }
}
