package com.caverock.androidsvg.model;

public class Text extends TextPositionedContainer implements TextRoot, HasTransform {
    public Matrix transform;

    @Override
    public void setTransform(Matrix transform) {
        this.transform = transform;
    }

    @Override
    public String getNodeName() {
        return "text";
    }
}
