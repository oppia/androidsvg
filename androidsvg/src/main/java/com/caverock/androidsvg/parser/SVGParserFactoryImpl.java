package com.caverock.androidsvg.parser;

import com.caverock.androidsvg.model.SvgStructure;
import com.caverock.androidsvg.util.Logger;
import com.caverock.androidsvg.xml.XmlParser;

public class SVGParserFactoryImpl implements SVGParser.Factory {
    @Override
    public SVGParser createParser(SvgStructure.Factory structureFactory, XmlParser xmlParser, Logger logger) {
        return new SVGParserImpl(structureFactory, xmlParser, logger);
    }
}
