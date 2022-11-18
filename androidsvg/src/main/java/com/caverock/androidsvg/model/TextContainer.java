package com.caverock.androidsvg.model;

import com.caverock.androidsvg.parser.SVGParseException;

public abstract class TextContainer extends SvgConditionalContainer {
    @Override
    public void addChild(SvgObject elem) throws SVGParseException {
        if (elem instanceof TextChild)
            children.add(elem);
        else
            throw new SVGParseException("Text content elements cannot contain " + elem + " elements.");
    }
}
