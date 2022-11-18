package com.caverock.androidsvg.model;

public class CSSClipRect {
    public final Length top;
    public final Length right;
    public final Length bottom;
    public final Length left;

    public CSSClipRect(Length top, Length right, Length bottom, Length left) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }
}
