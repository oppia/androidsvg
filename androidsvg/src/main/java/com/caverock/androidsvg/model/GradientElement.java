package com.caverock.androidsvg.model;

import com.caverock.androidsvg.parser.SVGParseException;

import java.util.ArrayList;
import java.util.List;

public abstract class GradientElement extends SvgElementBase implements SvgContainer {
    public List<SvgObject> children = new ArrayList<>();

    public Boolean gradientUnitsAreUser;
    public Matrix gradientTransform;
    public GradientSpread spreadMethod;
    public String href;

    @Override
    public List<SvgObject> getChildren() {
        return children;
    }

    @Override
    public void addChild(SvgObject elem) throws SVGParseException {
        if (elem instanceof Stop)
            children.add(elem);
        else
            throw new SVGParseException("Gradient elements cannot contain " + elem + " elements.");
    }
}
