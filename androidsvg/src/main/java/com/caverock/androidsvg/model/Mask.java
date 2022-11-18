package com.caverock.androidsvg.model;

public class Mask extends SvgConditionalContainer implements NotDirectlyRendered {
    public Boolean maskUnitsAreUser;
    public Boolean maskContentUnitsAreUser;
    public Length x;
    public Length y;
    public Length width;
    public Length height;

    @Override
    public String getNodeName() {
        return "mask";
    }
}
