package com.caverock.androidsvg.model;

// A <defs> object contains objects that are not rendered directly, but are instead
// referenced from other parts of the file.
public class Defs extends Group implements NotDirectlyRendered {
    @Override
    public String getNodeName() {
        return "defs";
    }
}
