package com.caverock.androidsvg.model;

public class Pattern extends SvgViewBoxContainer implements NotDirectlyRendered {
    public Boolean patternUnitsAreUser;
    public Boolean patternContentUnitsAreUser;
    public Matrix patternTransform;
    public Length x;
    public Length y;
    public Length width;
    public Length height;
    public String href;

    @Override
    public String getNodeName() {
        return "pattern";
    }
}
