package com.caverock.androidsvg.xml;

import static com.caverock.androidsvg.parser.ParserHelper.parseProcessingInstructionAttributes;

import android.util.Xml;

import com.caverock.androidsvg.parser.SVGParseException;
import com.caverock.androidsvg.parser.TextScanner;

import org.xml.sax.Attributes;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class PullXmlParser implements XmlParser {
    public PullXmlParser() {

    }

    @Override
    public void parseDocument(InputStream inputStream, XmlDocumentHandler documentHandler, boolean enableInternalEntities) throws SVGParseException {
        try
        {
            XmlPullParser parser = Xml.newPullParser();
            XPPAttributesWrapper attributes = new XPPAttributesWrapper(parser);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, false);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(inputStream, null);

            int  eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                switch(eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        documentHandler.startDocument();
                        break;
                    case XmlPullParser.START_TAG:
                        String qName = parser.getName();
                        if (parser.getPrefix() != null)
                            qName = parser.getPrefix() + ':' + qName;
                        documentHandler.startElement(parser.getNamespace(), parser.getName(), qName, attributes);
                        break;
                    case XmlPullParser.END_TAG:
                        qName = parser.getName();
                        if (parser.getPrefix() != null)
                            qName = parser.getPrefix() + ':' + qName;
                        documentHandler.endElement(parser.getNamespace(), parser.getName(), qName);
                        break;
                    case XmlPullParser.TEXT:
                        int[] startAndLength = new int[2];
                        char[] text = parser.getTextCharacters(startAndLength);
                        documentHandler.text(text, startAndLength[0], startAndLength[1]);
                        break;
                    case XmlPullParser.CDSECT:
                        documentHandler.text(parser.getText());
                        break;
                    //case XmlPullParser.COMMENT:
                    //   text(parser.getText());
                    //   break;
                    //case XmlPullParser.DOCDECL:
                    //   text(parser.getText());
                    //   break;
                    //case XmlPullParser.IGNORABLE_WHITESPACE:
                    //   text(parser.getText());
                    //   break;
                    case XmlPullParser.PROCESSING_INSTRUCTION:
                        TextScanner scan = new TextScanner(parser.getText());
                        String       instr = scan.nextToken();
                        documentHandler.handleProcessingInstruction(instr, parseProcessingInstructionAttributes(scan));
                        break;
                }
                eventType = parser.nextToken();
            }
            documentHandler.endDocument();

        }
        catch (XmlPullParserException e)
        {
            throw new SVGParseException("XML parser problem", e);
        }
        catch (IOException e)
        {
            throw new SVGParseException("Stream error", e);
        }
    }

    /*
     * Implements the SAX Attributes class so that our parser can share a common attributes object
     */
    private static class XPPAttributesWrapper  implements Attributes
    {
        private final XmlPullParser parser;

        public XPPAttributesWrapper(XmlPullParser parser)
        {
            this.parser = parser;
        }

        @Override
        public int getLength()
        {
            return parser.getAttributeCount();
        }

        @Override
        public String getURI(int index)
        {
            return parser.getAttributeNamespace(index);
        }

        @Override
        public String getLocalName(int index)
        {
            return parser.getAttributeName(index);
        }

        @Override
        public String getQName(int index)
        {
            String qName = parser.getAttributeName(index);
            if (parser.getAttributePrefix(index) != null)
                qName = parser.getAttributePrefix(index) + ':' + qName;
            return qName;
        }

        @Override
        public String getValue(int index)
        {
            return parser.getAttributeValue(index);
        }

        // Not used, and not implemented
        @Override
        public String getType(int index) { return null; }
        @Override
        public int getIndex(String uri, String localName) { return -1; }
        @Override
        public int getIndex(String qName) { return -1; }
        @Override
        public String getType(String uri, String localName) { return null; }
        @Override
        public String getType(String qName) { return null; }
        @Override
        public String getValue(String uri, String localName) { return null; }
        @Override
        public String getValue(String qName) { return null; }
    }
}
