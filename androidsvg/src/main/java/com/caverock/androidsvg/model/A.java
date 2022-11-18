package com.caverock.androidsvg.model;

// A linking element (we don't currently do anything with this. It is basically just treated like a Group.
public class A extends Group {
    public String href;

    @Override
    public String getNodeName() {
        return "a";
    }
}
