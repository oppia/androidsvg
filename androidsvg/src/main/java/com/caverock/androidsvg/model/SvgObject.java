package com.caverock.androidsvg.model;

// Any object that can be part of the tree
public class SvgObject {
    public SvgStructure document;
    public SvgContainer parent;

    public String getNodeName() {
        return "";
    }
}
