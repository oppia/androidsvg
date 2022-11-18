package com.caverock.androidsvg.model;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    public float[] values;
    public List<Transform> preTransforms = new ArrayList<>();

    public Matrix() {
        this.values = new float[] { 1, 0, 0, 0, 1, 0, 0, 0, 1 };
    }

    public Matrix(float[] values) {
        this.values = values;
    }

    public void preConcat(Matrix other) {
        preTransforms.add(new MultiplyMatrix(other));
    }

    public void preTranslate(float translationX, float translationY) {
        preTransforms.add(new Translation(translationX, translationY));
    }

    public void preScale(float scaleX, float scaleY) {
        preTransforms.add(new Scale(scaleX, scaleY));
    }

    public void preRotate(float rotationRads) {
        preTransforms.add(new Rotation(rotationRads));
    }

    public void preRotate(float rotationRads, float centerX, float centerY) {
        preTransforms.add(new Rotation(rotationRads, centerX, centerY));
    }

    public void preSkew(float skewX, float skewY) {
        preTransforms.add(new Skew(skewX, skewY));
    }

    public interface Transform {}

    public static class MultiplyMatrix implements Transform {
        public Matrix other;

        public MultiplyMatrix(Matrix other) {
            this.other = other;
        }
    }

    public static class Rotation implements Transform {
        public float angleRads;
        public float centerX;
        public float centerY;

        public Rotation(float angleRads) {
            this(angleRads, 0, 0);
        }

        public Rotation(float angleRads, float centerX, float centerY) {
            this.angleRads = angleRads;
            this.centerX = centerX;
            this.centerY = centerY;
        }
    }

    public static class Scale implements Transform {
        public float scaleX;
        public float scaleY;

        public Scale(float scaleX, float scaleY) {
            this.scaleX = scaleX;
            this.scaleY = scaleY;
        }
    }

    public static class Translation implements Transform {
        public float translateX;
        public float translateY;

        public Translation(float translateX, float translateY) {
            this.translateX = translateX;
            this.translateY = translateY;
        }
    }

    public static class Skew implements Transform {
        public float angleRadsX;
        public float angleRadsY;

        public Skew(float angleRadsX, float angleRadsY) {
            this.angleRadsX = angleRadsX;
            this.angleRadsY = angleRadsY;
        }
    }
}
