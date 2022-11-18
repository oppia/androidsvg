package com.caverock.androidsvg.model;

// One of the element types that can cause graphics to be drawn onto the target canvas.
// Specifically: 'circle', 'ellipse', 'image', 'line', 'path', 'polygon', 'polyline', 'rect', 'text' and 'use'.
public abstract class GraphicsElement extends SvgConditionalElement implements HasTransform {
    public Matrix transform;

    @Override
    public void setTransform(Matrix transform) {
        this.transform = transform;
    }
}
