package com.caverock.androidsvg.xml;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;

import com.caverock.androidsvg.parser.ParserHelper;
import com.caverock.androidsvg.parser.SVGParseException;
import com.caverock.androidsvg.util.Logger;

import java.io.IOException;
import java.io.InputStream;

public class AutomaticAndroidXmlParser implements XmlParser {
    // Versions of Android earlier than 15 (ICS) have an XmlPullParser that doesn't support the
    // nextToken() method. Also, they throw an exception when calling setFeature().
    // So for simplicity, we'll just force the use of the SAX parser on Androids < 15.
    @SuppressLint("ObsoleteSdkInt")
    private static final boolean FORCE_SAX_ON_EARLY_ANDROIDS = VERSION.SDK_INT < 15;

    // Used by the automatic XML parser switching code.
    // This value defines how much of the SVG file preamble will we keep in order to check for
    // a doctype definition that has internal entities defined.
    private static final int  ENTITY_WATCH_BUFFER_SIZE = 4096;

    private final XmlParser saxParser = new SaxXmlParser(FORCE_SAX_ON_EARLY_ANDROIDS);
    private final XmlParser pullParser = new PullXmlParser();
    private final Logger logger;

    public AutomaticAndroidXmlParser(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void parseDocument(InputStream inputStream, XmlDocumentHandler documentHandler, boolean enableInternalEntities) throws SVGParseException {
        try {
            if (FORCE_SAX_ON_EARLY_ANDROIDS) {
                saxParser.parseDocument(inputStream, documentHandler, enableInternalEntities);
                return;
            }

            if (enableInternalEntities) {
                // We need to check for the presence of entities in the file so we can decide which parser to use.
                inputStream.mark(ENTITY_WATCH_BUFFER_SIZE);
                // Read that number of bytes into a buffer so we
                byte[] checkBuf = new byte[ENTITY_WATCH_BUFFER_SIZE];
                int n = inputStream.read(checkBuf);
                // Read in the bytes as a string. We should probably use UTF-8 here, but the string
                // constructor that takes a charset requires SDK 9. We should be okay though, since we
                // are only looking for plain ASCII. And that'll be the same in any encoding.
                String preamble = new String(checkBuf, 0, n);
                // Reset the stream so that the XML parsers can do their job.
                inputStream.reset();
                if (preamble.contains("<!ENTITY ")) {
                    // Found something that looks like an entity definition.
                    // So we'll use the SAX parser which supports them.
                    saxParser.parseDocument(inputStream, documentHandler, /* enableInternalEntities= */ true);
                    return;
                }
            }

            // Use the (faster) XmlPullParser
            pullParser.parseDocument(inputStream, documentHandler, enableInternalEntities);
        } catch (IOException e) {
            logger.logError(ParserHelper.TAG, "Error occurred while performing check for entities.  File may not be parsed correctly if it contains entity definitions.", e);
            pullParser.parseDocument(inputStream, documentHandler, /* enableInternalEntities= */ true);
        }
    }
}
