package com.caverock.androidsvg.model;

import java.util.Collections;
import java.util.List;

public class Stop extends SvgElementBase implements SvgContainer {
    public Float offset;

    // Dummy container methods. Stop is officially a container, but we
    // are not interested in any of its possible child elements.
    @Override
    public List<SvgObject> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public void addChild(SvgObject elem) { /* do nothing */ }

    @Override
    public String getNodeName() {
        return "stop";
    }
}
