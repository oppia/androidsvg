package com.caverock.androidsvg.model;

public class Image extends SvgPreserveAspectRatioContainer implements HasTransform {
    public String href;
    public Length x;
    public Length y;
    public Length width;
    public Length height;
    public Matrix transform;

    @Override
    public void setTransform(Matrix transform) {
        this.transform = transform;
    }

    @Override
    public String getNodeName() {
        return "image";
    }
}
