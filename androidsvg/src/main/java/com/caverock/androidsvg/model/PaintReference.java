package com.caverock.androidsvg.model;

public class PaintReference extends SvgPaint {
    public final String href;
    public final SvgPaint fallback;

    public PaintReference(String href, SvgPaint fallback) {
        this.href = href;
        this.fallback = fallback;
    }

    public String toString() {
        return href + " " + fallback;
    }
}
