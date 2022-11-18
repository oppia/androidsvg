package com.caverock.androidsvg.model;

public class View extends SvgViewBoxContainer implements NotDirectlyRendered {
    public static final String NODE_NAME = "view";

    @Override
    public String getNodeName() {
        return NODE_NAME;
    }
}
