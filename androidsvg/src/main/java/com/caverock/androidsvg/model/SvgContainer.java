package com.caverock.androidsvg.model;

import com.caverock.androidsvg.parser.SVGParseException;

import java.util.List;

public interface SvgContainer {
    List<SvgObject> getChildren();

    void addChild(SvgObject elem) throws SVGParseException;
}
