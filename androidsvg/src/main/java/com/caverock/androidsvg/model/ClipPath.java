package com.caverock.androidsvg.model;

public class ClipPath extends Group implements NotDirectlyRendered {
    public Boolean clipPathUnitsAreUser;

    @Override
    public String getNodeName() {
        return "clipPath";
    }
}
