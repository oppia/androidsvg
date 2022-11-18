package com.caverock.androidsvg.model;

// An SVG element that can contain other elements.
public class Group extends SvgConditionalContainer implements HasTransform {
    public Matrix transform;

    @Override
    public void setTransform(Matrix transform) {
        this.transform = transform;
    }

    @Override
    public String getNodeName() {
        return "group";
    }
}
