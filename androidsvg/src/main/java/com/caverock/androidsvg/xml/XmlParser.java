package com.caverock.androidsvg.xml;

import com.caverock.androidsvg.parser.SVGParseException;

import java.io.InputStream;

public interface XmlParser {
    void parseDocument(InputStream inputStream, XmlDocumentHandler documentHandler, boolean enableInternalEntities) throws SVGParseException;
}
