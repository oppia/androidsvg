package com.caverock.androidsvg.model;

public interface PathInterface {
    void moveTo(float x, float y);

    void lineTo(float x, float y);

    void cubicTo(float x1, float y1, float x2, float y2, float x3, float y3);

    void quadTo(float x1, float y1, float x2, float y2);

    void arcTo(float rx, float ry, float xAxisRotation, boolean largeArcFlag, boolean sweepFlag, float x, float y);

    void close();
}
