package com.caverock.androidsvg.xml;

import com.caverock.androidsvg.parser.SVGParseException;

import org.xml.sax.Attributes;

import java.util.Map;

public interface XmlDocumentHandler {
    void startDocument();

    void startElement(String uri, String localName, String qName, Attributes attributes) throws SVGParseException;

    void endElement(String uri, String localName, String qName) throws SVGParseException;

    void text(String characters) throws SVGParseException;

    void text(char[] ch, int start, int length) throws SVGParseException;

    void handleProcessingInstruction(String instruction, Map<String, String> attributes);

    void endDocument();
}
