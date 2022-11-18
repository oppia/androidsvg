package com.caverock.androidsvg.model;

public class Marker extends SvgViewBoxContainer implements NotDirectlyRendered {
    public boolean markerUnitsAreUser;
    public Length refX;
    public Length refY;
    public Length markerWidth;
    public Length markerHeight;
    public Float orient;

    @Override
    public String getNodeName() {
        return "marker";
    }
}
