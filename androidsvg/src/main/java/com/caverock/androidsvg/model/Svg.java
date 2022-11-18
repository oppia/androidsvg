package com.caverock.androidsvg.model;

public class Svg extends SvgViewBoxContainer {
    public Length x;
    public Length y;
    public Length width;
    public Length height;
    public String version;

    @Override
    public String getNodeName() {
        return "svg";
    }
}
