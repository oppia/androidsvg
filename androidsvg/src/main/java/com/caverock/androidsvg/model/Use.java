package com.caverock.androidsvg.model;

public class Use extends Group {
    public String href;
    public Length x;
    public Length y;
    public Length width;
    public Length height;

    @Override
    public String getNodeName() {
        return "use";
    }
}
