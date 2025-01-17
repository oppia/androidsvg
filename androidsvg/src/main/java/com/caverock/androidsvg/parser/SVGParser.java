/*
   Copyright 2013 Paul LeBeau, Cave Rock Software Ltd.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.caverock.androidsvg.parser;

import com.caverock.androidsvg.loader.CssResolver;
import com.caverock.androidsvg.model.SvgStructure;
import com.caverock.androidsvg.util.Logger;
import com.caverock.androidsvg.xml.XmlParser;

import java.io.InputStream;

public interface SVGParser
{
    /**
     * Try to parse the stream contents to an {@link SvgStructure} instance.
     */
    SvgStructure parseStream(InputStream is) throws SVGParseException;

    /**
     * Tells the parser whether to allow the expansion of internal entities.
     * An example of a document containing an internal entities is:
     */
    SVGParser setInternalEntitiesEnabled(boolean enable);

    /**
     * Register an {@link CssResolver} instance that the parser should use when resolving
     * external references such as images, fonts, and CSS stylesheets.
     */
    SVGParser setExternalFileResolver(CssResolver cssResolver);

    interface Factory {
        SVGParser createParser(SvgStructure.Factory structureFactory, XmlParser xmlParser, Logger logger);
    }
}
