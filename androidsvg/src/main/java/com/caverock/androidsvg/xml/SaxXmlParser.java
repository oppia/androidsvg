package com.caverock.androidsvg.xml;

import static com.caverock.androidsvg.parser.ParserHelper.parseProcessingInstructionAttributes;

import com.caverock.androidsvg.parser.SVGParseException;
import com.caverock.androidsvg.parser.TextScanner;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxXmlParser implements XmlParser {
    private final boolean forceSaxOnEarlyAndroids;

    public SaxXmlParser() {
        this(false);
    }

    SaxXmlParser(boolean forceSaxOnEarlyAndroids) {
        this.forceSaxOnEarlyAndroids = forceSaxOnEarlyAndroids;
    }

    @Override
    public void parseDocument(InputStream inputStream, XmlDocumentHandler documentHandler, boolean enableInternalEntities) throws SVGParseException {
        try
        {
            // Invoke the SAX XML parser on the input.
            SAXParserFactory spf = SAXParserFactory.newInstance();

            if (!forceSaxOnEarlyAndroids) {
                // Disable external entity resolving
                spf.setFeature("http://xml.org/sax/features/external-general-entities", false);
                spf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            }

            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            SAXHandler handler = new SAXHandler(documentHandler);
            xr.setContentHandler(handler);
            xr.setProperty("http://xml.org/sax/properties/lexical-handler", handler);

            xr.parse(new InputSource(inputStream));
        }
        catch (ParserConfigurationException e)
        {
            throw new SVGParseException("XML parser problem", e);
        }
        catch (SAXException e)
        {
            throw new SVGParseException("SVG parse error", e);
        }
        catch (IOException e)
        {
            throw new SVGParseException("Stream error", e);
        }
    }

    private static class SAXHandler extends DefaultHandler2
    {
        private final XmlDocumentHandler documentHandler;

        private SAXHandler(XmlDocumentHandler documentHandler) {
            this.documentHandler = documentHandler;
        }

        @Override
        public void startDocument()
        {
            documentHandler.startDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
        {
            documentHandler.startElement(uri, localName, qName, attributes);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException
        {
            documentHandler.text(new String(ch, start, length));
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException
        {
            documentHandler.endElement(uri, localName, qName);
        }

        @Override
        public void endDocument()
        {
            documentHandler.endDocument();
        }

        @Override
        public void processingInstruction(String target, String data)
        {
            TextScanner scan = new TextScanner(data);
            Map<String, String> attributes = parseProcessingInstructionAttributes(scan);
            documentHandler.handleProcessingInstruction(target, attributes);
        }
    }
}
