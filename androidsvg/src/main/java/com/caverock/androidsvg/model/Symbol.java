package com.caverock.androidsvg.model;

public class Symbol extends SvgViewBoxContainer implements NotDirectlyRendered {
    @Override
    public String getNodeName() {
        return "symbol";
    }
}
