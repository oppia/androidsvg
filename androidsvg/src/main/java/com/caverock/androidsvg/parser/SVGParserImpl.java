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
import com.caverock.androidsvg.model.A;
import com.caverock.androidsvg.model.Circle;
import com.caverock.androidsvg.model.ClipPath;
import com.caverock.androidsvg.model.Ellipse;
import com.caverock.androidsvg.model.GradientElement;
import com.caverock.androidsvg.model.Group;
import com.caverock.androidsvg.model.HasTransform;
import com.caverock.androidsvg.model.Image;
import com.caverock.androidsvg.model.Line;
import com.caverock.androidsvg.model.Marker;
import com.caverock.androidsvg.model.Mask;
import com.caverock.androidsvg.model.Matrix;
import com.caverock.androidsvg.model.Path;
import com.caverock.androidsvg.model.Pattern;
import com.caverock.androidsvg.model.PolyLine;
import com.caverock.androidsvg.model.Polygon;
import com.caverock.androidsvg.model.Rect;
import com.caverock.androidsvg.model.SolidColor;
import com.caverock.androidsvg.model.Source;
import com.caverock.androidsvg.model.Stop;
import com.caverock.androidsvg.model.Style;
import com.caverock.androidsvg.model.Svg;
import com.caverock.androidsvg.model.SvgConditional;
import com.caverock.androidsvg.model.SvgConditionalContainer;
import com.caverock.androidsvg.model.SvgContainer;
import com.caverock.androidsvg.model.SvgLinearGradient;
import com.caverock.androidsvg.model.SvgRadialGradient;
import com.caverock.androidsvg.model.SvgViewBoxContainer;
import com.caverock.androidsvg.model.Switch;
import com.caverock.androidsvg.model.Symbol;
import com.caverock.androidsvg.model.TRef;
import com.caverock.androidsvg.model.TSpan;
import com.caverock.androidsvg.model.Text;
import com.caverock.androidsvg.model.TextContainer;
import com.caverock.androidsvg.model.TextPath;
import com.caverock.androidsvg.model.TextSequence;
import com.caverock.androidsvg.model.Use;
import com.caverock.androidsvg.model.View;
import com.caverock.androidsvg.model.SvgStructure;
import com.caverock.androidsvg.parser.CSSParser.MediaType;
import com.caverock.androidsvg.model.GradientSpread;
import com.caverock.androidsvg.model.SvgElementBase;
import com.caverock.androidsvg.model.SvgObject;
import com.caverock.androidsvg.model.TextChild;
import com.caverock.androidsvg.model.TextPositionedContainer;
import com.caverock.androidsvg.model.TextRoot;
import com.caverock.androidsvg.model.Defs;
import com.caverock.androidsvg.util.Logger;
import com.caverock.androidsvg.xml.XmlDocumentHandler;
import com.caverock.androidsvg.xml.XmlParser;

import org.xml.sax.Attributes;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/*
 * SVG parser code. Used by SVG class. Should not be called directly.
 */

class SVGParserImpl implements SVGParser, XmlDocumentHandler
{
   // SVG parser
   private SvgStructure svgDocument = null;
   private SvgContainer currentElement = null;
   private boolean                  enableInternalEntities = true;
   private CssResolver  cssResolver = null;

   // For handling elements we don't support
   private boolean   ignoring = false;
   private int       ignoreDepth;

   // For handling <title> and <desc>
   private boolean        inMetadataElement = false;
   private ParserHelper.SVGElem metadataTag = null;
   private StringBuilder  metadataElementContents = null;

   // For handling <style>
   private boolean        inStyleElement = false;
   private StringBuilder  styleElementContents = null;


   // Element types that we don't support. Those that are containers have their
   // contents ignored.
   //private static final String  TAG_ANIMATECOLOR        = "animateColor";
   //private static final String  TAG_ANIMATEMOTION       = "animateMotion";
   //private static final String  TAG_ANIMATETRANSFORM    = "animateTransform";
   //private static final String  TAG_ALTGLYPH            = "altGlyph";
   //private static final String  TAG_ALTGLYPHDEF         = "altGlyphDef";
   //private static final String  TAG_ALTGLYPHITEM        = "altGlyphItem";
   //private static final String  TAG_ANIMATE             = "animate";
   //private static final String  TAG_COLORPROFILE        = "color-profile";
   //private static final String  TAG_CURSOR              = "cursor";
   //private static final String  TAG_FEBLEND             = "feBlend";
   //private static final String  TAG_FECOLORMATRIX       = "feColorMatrix";
   //private static final String  TAG_FECOMPONENTTRANSFER = "feComponentTransfer";
   //private static final String  TAG_FECOMPOSITE         = "feComposite";
   //private static final String  TAG_FECONVOLVEMATRIX    = "feConvolveMatrix";
   //private static final String  TAG_FEDIFFUSELIGHTING   = "feDiffuseLighting";
   //private static final String  TAG_FEDISPLACEMENTMAP   = "feDisplacementMap";
   //private static final String  TAG_FEDISTANTLIGHT      = "feDistantLight";
   //private static final String  TAG_FEFLOOD             = "feFlood";
   //private static final String  TAG_FEFUNCA             = "feFuncA";
   //private static final String  TAG_FEFUNCB             = "feFuncB";
   //private static final String  TAG_FEFUNCG             = "feFuncG";
   //private static final String  TAG_FEFUNCR             = "feFuncR";
   //private static final String  TAG_FEGAUSSIANBLUR      = "feGaussianBlur";
   //private static final String  TAG_FEIMAGE             = "feImage";
   //private static final String  TAG_FEMERGE             = "feMerge";
   //private static final String  TAG_FEMERGENODE         = "feMergeNode";
   //private static final String  TAG_FEMORPHOLOGY        = "feMorphology";
   //private static final String  TAG_FEOFFSET            = "feOffset";
   //private static final String  TAG_FEPOINTLIGHT        = "fePointLight";
   //private static final String  TAG_FESPECULARLIGHTING  = "feSpecularLighting";
   //private static final String  TAG_FESPOTLIGHT         = "feSpotLight";
   //private static final String  TAG_FETILE              = "feTile";
   //private static final String  TAG_FETURBULENCE        = "feTurbulence";
   //private static final String  TAG_FILTER              = "filter";
   //private static final String  TAG_FONT                = "font";
   //private static final String  TAG_FONTFACE            = "font-face";
   //private static final String  TAG_FONTFACEFORMAT      = "font-face-format";
   //private static final String  TAG_FONTFACENAME        = "font-face-name";
   //private static final String  TAG_FONTFACESRC         = "font-face-src";
   //private static final String  TAG_FONTFACEURI         = "font-face-uri";
   //private static final String  TAG_FOREIGNOBJECT       = "foreignObject";
   //private static final String  TAG_GLYPH               = "glyph";
   //private static final String  TAG_GLYPHREF            = "glyphRef";
   //private static final String  TAG_HKERN               = "hkern";
   //private static final String  TAG_MASK                = "mask";
   //private static final String  TAG_METADATA            = "metadata";
   //private static final String  TAG_MISSINGGLYPH        = "missing-glyph";
   //private static final String  TAG_MPATH               = "mpath";
   //private static final String  TAG_SCRIPT              = "script";
   //private static final String  TAG_SET                 = "set";
   //private static final String  TAG_STYLE               = "style";
   //private static final String  TAG_VKERN               = "vkern";


   //=========================================================================
   // Main parser invocation methods
   //=========================================================================

   private final SvgStructure.Factory structureFactory;
   private final XmlParser xmlParser;
   private final Logger logger;

   SVGParserImpl(SvgStructure.Factory structureFactory, XmlParser xmlParser, Logger logger) {
      this.structureFactory = structureFactory;
      this.xmlParser = xmlParser;
      this.logger = logger;
   }

   @Override
   public SvgStructure parseStream(InputStream is) throws SVGParseException
   {
      // Transparently handle zipped files (.svgz)
      if (!is.markSupported()) {
         // We need a a buffered stream so we can use mark() and reset()
         is = new BufferedInputStream(is);
      }
      try
      {
         is.mark(3);
         int  firstTwoBytes = is.read() + (is.read() << 8);
         is.reset();
         if (firstTwoBytes == GZIPInputStream.GZIP_MAGIC) {
            // Looks like a zipped file.
            is = new BufferedInputStream( new GZIPInputStream(is) );
         }
      }
      catch (IOException ioe)
      {
         // Not a zipped SVG. Fall through and try parsing it normally.
      }

      try
      {
         xmlParser.parseDocument(is, this, enableInternalEntities);
         return svgDocument;
      }
      finally
      {
         try {
            is.close();
         } catch (IOException e) {
            logger.logError(ParserHelper.TAG, "Exception thrown closing input stream");
         }
      }
   }

   //=========================================================================
   // Attribute setters
   //=========================================================================

   @Override
   public SVGParser setInternalEntitiesEnabled(boolean enable) {
      enableInternalEntities = enable;
      return this;
   }

   @Override
   public SVGParser setExternalFileResolver(CssResolver cssResolver) {
      this.cssResolver = cssResolver;
      return this;
   }

   //=========================================================================
   // Parser event classes used by both XML parser implementations
   //=========================================================================

   @Override
   public void startDocument()
   {
      SVGParserImpl.this.svgDocument = structureFactory.createNewStructure(enableInternalEntities, cssResolver);
   }

   @Override
   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SVGParseException
   {
      if (ignoring) {
         ignoreDepth++;
         return;
      }
      if (!ParserHelper.SVG_NAMESPACE.equals(uri) && !"".equals(uri)) {
         return;
      }

      String tag = (localName.length() > 0) ? localName : qName;

      ParserHelper.SVGElem elem = ParserHelper.SVGElem.fromString(tag);
      switch (elem)
      {
         case svg:
            svg(attributes); break;
         case g:
            g(attributes); break;
         case defs:
            defs(attributes); break;
         case a:
            a(attributes); break;
         case use:
            use(attributes); break;
         case path:
            path(attributes); break;
         case rect:
            rect(attributes); break;
         case circle:
            circle(attributes); break;
         case ellipse:
            ellipse(attributes); break;
         case line:
            line(attributes); break;
         case polyline:
            polyline(attributes); break;
         case polygon:
            polygon(attributes); break;
         case text:
            text(attributes); break;
         case tspan:
            tspan(attributes); break;
         case tref:
            tref(attributes); break;
         case SWITCH:
            zwitch(attributes); break;
         case symbol:
            symbol(attributes); break;
         case marker:
            marker(attributes); break;
         case linearGradient:
            linearGradient(attributes); break;
         case radialGradient:
            radialGradient(attributes); break;
         case stop:
            stop(attributes); break;
         case title:
         case desc:
            inMetadataElement = true;
            metadataTag = elem;
            break;
         case clipPath:
            clipPath(attributes); break;
         case textPath:
            textPath(attributes); break;
         case pattern:
            pattern(attributes); break;
         case image:
            image(attributes); break;
         case view:
            view(attributes); break;
         case mask:
            mask(attributes); break;
         case style:
            style(attributes); break;
         case solidColor:
            solidColor(attributes); break;
         default:
            ignoring = true;
            ignoreDepth = 1;
            break;
      }
   }

   @Override
   public void text(String characters) throws SVGParseException
   {
      if (ignoring)
         return;

      if (inMetadataElement)
      {
         if (metadataElementContents == null)
            metadataElementContents = new StringBuilder(characters.length());
         metadataElementContents.append(characters);
      }
      else if (inStyleElement)
      {
         if (styleElementContents == null)
            styleElementContents = new StringBuilder(characters.length());
         styleElementContents.append(characters);
      }
      else if (currentElement instanceof TextContainer)
      {
         appendToTextContainer(characters);
      }
   }

   @Override
   public void text(char[] ch, int start, int length) throws SVGParseException
   {
      if (ignoring)
         return;

      if (inMetadataElement)
      {
         if (metadataElementContents == null)
            metadataElementContents = new StringBuilder(length);
         metadataElementContents.append(ch, start, length);
      }
      else if (inStyleElement)
      {
         if (styleElementContents == null)
            styleElementContents = new StringBuilder(length);
         styleElementContents.append(ch, start, length);
      }
      else if (currentElement instanceof TextContainer)
      {
         appendToTextContainer(new String(ch, start, length));
      }

   }


   private void  appendToTextContainer(String characters) throws SVGParseException
   {
      // The parser can pass us several text nodes in a row. If this happens, we
      // want to collapse them all into one SVGBase.TextSequence node
      SvgConditionalContainer parent = (SvgConditionalContainer) currentElement;
      int  numOlderSiblings = parent.getChildren().size();
      SvgObject previousSibling = (numOlderSiblings == 0) ? null : parent.getChildren().get(numOlderSiblings-1);
      if (previousSibling instanceof TextSequence) {
         // Last sibling was a TextSequence also, so merge them.
         ((TextSequence) previousSibling).text += characters;
      } else {
         // Add a new TextSequence to the child node list
         currentElement.addChild(new TextSequence( characters ));
      }
   }

   @Override
   public void endElement(String uri, String localName, String qName) throws SVGParseException
   {
      if (ignoring) {
         if (--ignoreDepth == 0) {
            ignoring = false;
         }
         return;
      }

      if (!ParserHelper.SVG_NAMESPACE.equals(uri) && !"".equals(uri)) {
         return;
      }

      String tag = (localName.length() > 0) ? localName : qName;
      switch (ParserHelper.SVGElem.fromString(tag))
      {
         case title:
         case desc:
            inMetadataElement = false;
            if (metadataElementContents != null)
            {
               if (metadataTag == ParserHelper.SVGElem.title)
                  svgDocument.setTitle(metadataElementContents.toString());
               else if (metadataTag == ParserHelper.SVGElem.desc)
                  svgDocument.setDesc(metadataElementContents.toString());
               metadataElementContents.setLength(0);
            }
            return;

         case style:
            if (styleElementContents != null) {
               inStyleElement = false;
               parseCSSStyleSheet(styleElementContents.toString());
               styleElementContents.setLength(0);
               return;
            }
            break;

         case svg:
         case g:
         case defs:
         case a:
         case use:
         case image:
         case text:
         case tspan:
         case SWITCH:
         case symbol:
         case marker:
         case linearGradient:
         case radialGradient:
         case stop:
         case clipPath:
         case textPath:
         case pattern:
         case view:
         case mask:
         case solidColor:
            if (currentElement == null) {
               // This situation has been reported by a user. But I am unable to reproduce this fault.
               // If you can get this error please add your SVG file to https://github.com/BigBadaboom/androidsvg/issues/177
               // For now we'll return a parse exception for consistency (instead of NPE).
               throw new SVGParseException(String.format("Unbalanced end element </%s> found", tag));
            }
            currentElement = ((SvgObject) currentElement).parent;
            break;

         default:
            // no action
      }

   }

   @Override
   public void endDocument()
   {
      // Dump document
//      if (BuildConfig.DEBUG)
//         dumpNode(svgDocument.getRootElement(), "");
   }

   @Override
   public void handleProcessingInstruction(String instruction, Map<String, String> attributes)
   {
      if (instruction.equals(ParserHelper.XML_STYLESHEET_PROCESSING_INSTRUCTION) && cssResolver != null)
      {
         // If a "type" is specified, make sure it is the CSS type
         String  attr = attributes.get(ParserHelper.XML_STYLESHEET_ATTR_TYPE);
         if (attr != null && !CSSParser.CSS_MIME_TYPE.equals(attributes.get("type")))
            return;
         // Alternate stylesheets are not supported
         attr = attributes.get(ParserHelper.XML_STYLESHEET_ATTR_ALTERNATE);
         if (attr != null && !ParserHelper.XML_STYLESHEET_ATTR_ALTERNATE_NO.equals(attributes.get("alternate")))
            return;

         attr = attributes.get(ParserHelper.XML_STYLESHEET_ATTR_HREF);
         if (attr != null)
         {
            String  css = cssResolver.resolveCSSStyleSheet(attr);
            if (css == null)
               return;

            String  mediaAttr = attributes.get(ParserHelper.XML_STYLESHEET_ATTR_MEDIA);
            if (mediaAttr != null && !ParserHelper.XML_STYLESHEET_ATTR_MEDIA_ALL.equals(mediaAttr.trim())) {
               css = "@media " + mediaAttr + " { " + css + "}";
            }

            parseCSSStyleSheet(css);
         }

      }
   }


   //=========================================================================


   private void  dumpNode(SvgObject elem, String indent)
   {
//      if (!BuildConfig.DEBUG)
//         return;
//      Log.d(TAG, indent+elem);
//      if (elem instanceof SVGBase.SvgConditionalContainer) {
//         indent = indent+"  ";
//         for (SVGBase.SvgObject child: ((SVGBase.SvgConditionalContainer) elem).children) {
//            dumpNode(child, indent);
//         }
//      }
   }


   private void  debug(String format, Object... args)
   {
//      if (BuildConfig.DEBUG)
//         Log.d(TAG, String.format(format, args));
   }


   //=========================================================================
   // Handlers for each SVG element
   //=========================================================================
   // <svg> element

   private void  svg(Attributes attributes) throws SVGParseException
   {
      debug("<svg>");

      Svg obj = new Svg();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesViewBox(obj, attributes);
      parseAttributesSVG(obj, attributes);
      if (currentElement == null) {
         svgDocument.setRootElement(obj);
      } else {
         currentElement.addChild(obj);
      }
      currentElement = obj;
   }

   
   private void  parseAttributesSVG(Svg obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case x:
               obj.x = ParserHelper.parseLength(val);
               break;
            case y:
               obj.y = ParserHelper.parseLength(val);
               break;
            case width:
               obj.width = ParserHelper.parseLength(val);
               if (obj.width.isNegative())
                  throw new SVGParseException("Invalid <svg> element. width cannot be negative");
               break;
            case height:
               obj.height = ParserHelper.parseLength(val);
               if (obj.height.isNegative())
                  throw new SVGParseException("Invalid <svg> element. height cannot be negative");
               break;
            case version:
               obj.version = val;
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <g> group element


   private void  g(Attributes attributes) throws SVGParseException
   {
      debug("<g>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Group obj = new Group();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      parseAttributesConditional(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   //=========================================================================
   // <defs> group element


   private void  defs(Attributes attributes) throws SVGParseException
   {
      debug("<defs>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Defs obj = new Defs();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   //=========================================================================
   // <a> element


   private void  a(Attributes attributes) throws SVGParseException
   {
      debug("<a>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      A obj = new A();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesA(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   private void  parseAttributesA(A obj, Attributes attributes)
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         //noinspection SwitchStatementWithTooFewBranches
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case href:
               if ("".equals(attributes.getURI(i)) || ParserHelper.XLINK_NAMESPACE.equals(attributes.getURI(i)))
                  obj.href = val;
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <use> element


   private void  use(Attributes attributes) throws SVGParseException
   {
      debug("<use>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Use obj = new Use();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesUse(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   private void  parseAttributesUse(Use obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case x:
               obj.x = ParserHelper.parseLength(val);
               break;
            case y:
               obj.y = ParserHelper.parseLength(val);
               break;
            case width:
               obj.width = ParserHelper.parseLength(val);
               if (obj.width.isNegative())
                  throw new SVGParseException("Invalid <use> element. width cannot be negative");
               break;
            case height:
               obj.height = ParserHelper.parseLength(val);
               if (obj.height.isNegative())
                  throw new SVGParseException("Invalid <use> element. height cannot be negative");
               break;
            case href:
               if ("".equals(attributes.getURI(i)) || ParserHelper.XLINK_NAMESPACE.equals(attributes.getURI(i)))
                  obj.href = val;
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <image> element


   private void  image(Attributes attributes) throws SVGParseException
   {
      debug("<image>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Image obj = new Image();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesImage(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   private void  parseAttributesImage(Image obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case x:
               obj.x = ParserHelper.parseLength(val);
               break;
            case y:
               obj.y = ParserHelper.parseLength(val);
               break;
            case width:
               obj.width = ParserHelper.parseLength(val);
               if (obj.width.isNegative())
                  throw new SVGParseException("Invalid <use> element. width cannot be negative");
               break;
            case height:
               obj.height = ParserHelper.parseLength(val);
               if (obj.height.isNegative())
                  throw new SVGParseException("Invalid <use> element. height cannot be negative");
               break;
            case href:
               if ("".equals(attributes.getURI(i)) || ParserHelper.XLINK_NAMESPACE.equals(attributes.getURI(i)))
                  obj.href = val;
               break;
            case preserveAspectRatio:
               ParserHelper.parsePreserveAspectRatio(obj, val);
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <path> element


   private void  path(Attributes attributes) throws SVGParseException
   {
      debug("<path>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Path obj = new Path();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesPath(obj, attributes);
      currentElement.addChild(obj);     
   }


   private void  parseAttributesPath(Path obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case d:
               obj.d = ParserHelper.parsePath(logger, val);
               break;
            case pathLength:
               obj.pathLength = ParserHelper.parseFloat(val);
               if (obj.pathLength < 0f)
                  throw new SVGParseException("Invalid <path> element. pathLength cannot be negative");
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <rect> element


   private void  rect(Attributes attributes) throws SVGParseException
   {
      debug("<rect>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Rect obj = new Rect();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesRect(obj, attributes);
      currentElement.addChild(obj);     
   }


   private void  parseAttributesRect(Rect obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case x:
               obj.x = ParserHelper.parseLength(val);
               break;
            case y:
               obj.y = ParserHelper.parseLength(val);
               break;
            case width:
               obj.width = ParserHelper.parseLength(val);
               if (obj.width.isNegative())
                  throw new SVGParseException("Invalid <rect> element. width cannot be negative");
               break;
            case height:
               obj.height = ParserHelper.parseLength(val);
               if (obj.height.isNegative())
                  throw new SVGParseException("Invalid <rect> element. height cannot be negative");
               break;
            case rx:
               obj.rx = ParserHelper.parseLength(val);
               if (obj.rx.isNegative())
                  throw new SVGParseException("Invalid <rect> element. rx cannot be negative");
               break;
            case ry:
               obj.ry = ParserHelper.parseLength(val);
               if (obj.ry.isNegative())
                  throw new SVGParseException("Invalid <rect> element. ry cannot be negative");
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <circle> element


   private void  circle(Attributes attributes) throws SVGParseException
   {
      debug("<circle>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Circle obj = new Circle();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesCircle(obj, attributes);
      currentElement.addChild(obj);     
   }


   private void  parseAttributesCircle(Circle obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case cx:
               obj.cx = ParserHelper.parseLength(val);
               break;
            case cy:
               obj.cy = ParserHelper.parseLength(val);
               break;
            case r:
               obj.r = ParserHelper.parseLength(val);
               if (obj.r.isNegative())
                  throw new SVGParseException("Invalid <circle> element. r cannot be negative");
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <ellipse> element


   private void  ellipse(Attributes attributes) throws SVGParseException
   {
      debug("<ellipse>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Ellipse obj = new Ellipse();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesEllipse(obj, attributes);
      currentElement.addChild(obj);     
   }


   private void  parseAttributesEllipse(Ellipse obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case cx:
               obj.cx = ParserHelper.parseLength(val);
               break;
            case cy:
               obj.cy = ParserHelper.parseLength(val);
               break;
            case rx:
               obj.rx = ParserHelper.parseLength(val);
               if (obj.rx.isNegative())
                  throw new SVGParseException("Invalid <ellipse> element. rx cannot be negative");
               break;
            case ry:
               obj.ry = ParserHelper.parseLength(val);
               if (obj.ry.isNegative())
                  throw new SVGParseException("Invalid <ellipse> element. ry cannot be negative");
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <line> element


   private void  line(Attributes attributes) throws SVGParseException
   {
      debug("<line>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Line obj = new Line();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesLine(obj, attributes);
      currentElement.addChild(obj);     
   }


   private void  parseAttributesLine(Line obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case x1:
               obj.x1 = ParserHelper.parseLength(val);
               break;
            case y1:
               obj.y1 = ParserHelper.parseLength(val);
               break;
            case x2:
               obj.x2 = ParserHelper.parseLength(val);
               break;
            case y2:
               obj.y2 = ParserHelper.parseLength(val);
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <polyline> element


   private void  polyline(Attributes attributes) throws SVGParseException
   {
      debug("<polyline>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      PolyLine obj = new PolyLine();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesPolyLine(obj, attributes, "polyline");
      currentElement.addChild(obj);     
   }


   /*
    *  Parse the "points" attribute. Used by both <polyline> and <polygon>.
    */
   private void  parseAttributesPolyLine(PolyLine obj, Attributes attributes, String tag) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         if (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)) == ParserHelper.SVGAttr.points)
         {
            TextScanner scan = new TextScanner(attributes.getValue(i));
            List<Float> points = new ArrayList<>();
            scan.skipWhitespace();

            while (!scan.empty()) {
               float x = scan.nextFloat();
               if (Float.isNaN(x))
                  throw new SVGParseException("Invalid <"+tag+"> points attribute. Non-coordinate content found in list.");
               scan.skipCommaWhitespace();
               float y = scan.nextFloat();
               if (Float.isNaN(y))
                  throw new SVGParseException("Invalid <"+tag+"> points attribute. There should be an even number of coordinates.");
               scan.skipCommaWhitespace();
               points.add(x);
               points.add(y);
            }
            obj.points = new float[points.size()];
            int j = 0;
            for (float f: points) {
               obj.points[j++] = f;
            }
         }
      }
   }


   //=========================================================================
   // <polygon> element


   private void  polygon(Attributes attributes) throws SVGParseException
   {
      debug("<polygon>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Polygon obj = new Polygon();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesPolyLine(obj, attributes, "polygon"); // reuse of polyline "points" parser
      currentElement.addChild(obj);     
   }


   //=========================================================================
   // <text> element


   private void  text(Attributes attributes) throws SVGParseException
   {
      debug("<text>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Text obj = new Text();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesTextPosition(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   private void  parseAttributesTextPosition(TextPositionedContainer obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case x:
               obj.x = ParserHelper.parseLengthList(val);
               break;
            case y:
               obj.y = ParserHelper.parseLengthList(val);
               break;
            case dx:
               obj.dx = ParserHelper.parseLengthList(val);
               break;
            case dy:
               obj.dy = ParserHelper.parseLengthList(val);
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <tspan> element


   private void  tspan(Attributes attributes) throws SVGParseException
   {
      debug("<tspan>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      if (!(currentElement instanceof TextContainer))
         throw new SVGParseException("Invalid document. <tspan> elements are only valid inside <text> or other <tspan> elements.");
      TSpan obj = new TSpan();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesTextPosition(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
      if (obj.parent instanceof TextRoot)
         obj.setTextRoot((TextRoot) obj.parent);
      else
         obj.setTextRoot(((TextChild) obj.parent).getTextRoot());
   }


   //=========================================================================
   // <tref> element


   private void  tref(Attributes attributes) throws SVGParseException
   {
      debug("<tref>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      if (!(currentElement instanceof TextContainer))
         throw new SVGParseException("Invalid document. <tref> elements are only valid inside <text> or <tspan> elements.");
      TRef obj = new TRef();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesTRef(obj, attributes);
      currentElement.addChild(obj);
      if (obj.parent instanceof TextRoot)
         obj.setTextRoot((TextRoot) obj.parent);
      else
         obj.setTextRoot(((TextChild) obj.parent).getTextRoot());
   }


   private void  parseAttributesTRef(TRef obj, Attributes attributes)
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         //noinspection SwitchStatementWithTooFewBranches
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case href:
               if ("".equals(attributes.getURI(i)) || ParserHelper.XLINK_NAMESPACE.equals(attributes.getURI(i)))
                  obj.href = val;
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <switch> element


   private void  zwitch(Attributes attributes) throws SVGParseException
   {
      debug("<switch>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Switch obj = new Switch();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      parseAttributesConditional(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   private void  parseAttributesConditional(SvgConditional obj, Attributes attributes)
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case requiredFeatures:
               obj.setRequiredFeatures(ParserHelper.parseRequiredFeatures(val));
               break;
            case requiredExtensions:
               obj.setRequiredExtensions(val);
               break;
            case systemLanguage:
               obj.setSystemLanguage(ParserHelper.parseSystemLanguage(val));
               break;
            case requiredFormats:
               obj.setRequiredFormats(ParserHelper.parseRequiredFormats(val));
               break;
            case requiredFonts:
               List<String>  fonts = ParserHelper.parseFontFamily(val);
               Set<String>  fontSet = (fonts != null) ? new HashSet<>(fonts) : new HashSet<String>(0);
               obj.setRequiredFonts(fontSet);
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <symbol> element


   private void  symbol(Attributes attributes) throws SVGParseException
   {
      debug("<symbol>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Symbol obj = new Symbol();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesViewBox(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }

   
   //=========================================================================
   // <marker> element


   private void  marker(Attributes attributes) throws SVGParseException
   {
      debug("<marker>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Marker obj = new Marker();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesViewBox(obj, attributes);
      parseAttributesMarker(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   private void  parseAttributesMarker(Marker obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case refX:
               obj.refX = ParserHelper.parseLength(val);
               break;
            case refY:
               obj.refY = ParserHelper.parseLength(val);
               break;
            case markerWidth:
               obj.markerWidth = ParserHelper.parseLength(val);
               if (obj.markerWidth.isNegative())
                  throw new SVGParseException("Invalid <marker> element. markerWidth cannot be negative");
               break;
            case markerHeight:
               obj.markerHeight = ParserHelper.parseLength(val);
               if (obj.markerHeight.isNegative())
                  throw new SVGParseException("Invalid <marker> element. markerHeight cannot be negative");
               break;
            case markerUnits:
               if ("strokeWidth".equals(val)) {
                  obj.markerUnitsAreUser = false;
               } else if ("userSpaceOnUse".equals(val)) {
                  obj.markerUnitsAreUser = true;
               } else {
                  throw new SVGParseException("Invalid value for attribute markerUnits");
               } 
               break;
            case orient:
               if ("auto".equals(val)) {
                  obj.orient = Float.NaN;
               } else {
                  obj.orient = ParserHelper.parseFloat(val);
               }
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <linearGradient> element


   private void  linearGradient(Attributes attributes) throws SVGParseException
   {
      debug("<linearGradient>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      SvgLinearGradient obj = new SvgLinearGradient();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesGradient(obj, attributes);
      parseAttributesLinearGradient(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   private void  parseAttributesGradient(GradientElement obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case gradientUnits:
               if ("objectBoundingBox".equals(val)) {
                  obj.gradientUnitsAreUser = false;
               } else if ("userSpaceOnUse".equals(val)) {
                  obj.gradientUnitsAreUser = true;
               } else {
                  throw new SVGParseException("Invalid value for attribute gradientUnits");
               } 
               break;
            case gradientTransform:
               obj.gradientTransform = parseTransformList(val);
               break;
            case spreadMethod:
               try
               {
                  obj.spreadMethod = GradientSpread.valueOf(val);
               } 
               catch (IllegalArgumentException e)
               {
                  throw new SVGParseException("Invalid spreadMethod attribute. \""+val+"\" is not a valid value.");
               }
               break;
            case href:
               if ("".equals(attributes.getURI(i)) || ParserHelper.XLINK_NAMESPACE.equals(attributes.getURI(i)))
                  obj.href = val;
               break;
            default:
               break;
         }
      }
   }


   private void  parseAttributesLinearGradient(SvgLinearGradient obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case x1:
               obj.x1 = ParserHelper.parseLength(val);
               break;
            case y1:
               obj.y1 = ParserHelper.parseLength(val);
               break;
            case x2:
               obj.x2 = ParserHelper.parseLength(val);
               break;
            case y2:
               obj.y2 = ParserHelper.parseLength(val);
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <radialGradient> element


   private void  radialGradient(Attributes attributes) throws SVGParseException
   {
      debug("<radialGradient>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      SvgRadialGradient obj = new SvgRadialGradient();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesGradient(obj, attributes);
      parseAttributesRadialGradient(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   private void  parseAttributesRadialGradient(SvgRadialGradient obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case cx:
               obj.cx = ParserHelper.parseLength(val);
               break;
            case cy:
               obj.cy = ParserHelper.parseLength(val);
               break;
            case r:
               obj.r = ParserHelper.parseLength(val);
               if (obj.r.isNegative())
                  throw new SVGParseException("Invalid <radialGradient> element. r cannot be negative");
               break;
            case fx:
               obj.fx = ParserHelper.parseLength(val);
               break;
            case fy:
               obj.fy = ParserHelper.parseLength(val);
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // Gradient <stop> element


   private void  stop(Attributes attributes) throws SVGParseException
   {
      debug("<stop>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      if (!(currentElement instanceof GradientElement))
         throw new SVGParseException("Invalid document. <stop> elements are only valid inside <linearGradient> or <radialGradient> elements.");
      Stop obj = new Stop();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesStop(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   private void  parseAttributesStop(Stop obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         //noinspection SwitchStatementWithTooFewBranches
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case offset:
               obj.offset = parseGradientOffset(val);
               break;
            default:
               break;
         }
      }
   }


   private Float  parseGradientOffset(String val) throws SVGParseException
   {
      if (val.length() == 0)
         throw new SVGParseException("Invalid offset value in <stop> (empty string)");
      int      end = val.length();
      boolean  isPercent = false;

      if (val.charAt(val.length()-1) == '%') {
         end -= 1;
         isPercent = true;
      }
      try
      {
         float scalar = ParserHelper.parseFloat(val, 0, end);
         if (isPercent)
            scalar /= 100f;
         return (scalar < 0) ? 0 : (scalar > 100) ? 100 : scalar;
      }
      catch (NumberFormatException e)
      {
         throw new SVGParseException("Invalid offset value in <stop>: "+val, e);
      }
   }


   //=========================================================================
   // <solidColor> element


   private void  solidColor(Attributes attributes) throws SVGParseException
   {
      debug("<solidColor>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      SolidColor obj = new SolidColor();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   //=========================================================================
   // <clipPath> element


   private void  clipPath(Attributes attributes) throws SVGParseException
   {
      debug("<clipPath>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      ClipPath obj = new ClipPath();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesTransform(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesClipPath(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   private void  parseAttributesClipPath(ClipPath obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         //noinspection SwitchStatementWithTooFewBranches
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case clipPathUnits:
               if ("objectBoundingBox".equals(val)) {
                  obj.clipPathUnitsAreUser = false;
               } else if ("userSpaceOnUse".equals(val)) {
                  obj.clipPathUnitsAreUser = true;
               } else {
                  throw new SVGParseException("Invalid value for attribute clipPathUnits");
               }
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <textPath> element


   private void textPath(Attributes attributes) throws SVGParseException
   {
      debug("<textPath>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      TextPath obj = new TextPath();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesTextPath(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
      if (obj.parent instanceof TextRoot)
         obj.setTextRoot((TextRoot) obj.parent);
      else
         obj.setTextRoot(((TextChild) obj.parent).getTextRoot());
   }


   private void  parseAttributesTextPath(TextPath obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case href:
               if ("".equals(attributes.getURI(i)) || ParserHelper.XLINK_NAMESPACE.equals(attributes.getURI(i)))
                  obj.href = val;
               break;
            case startOffset:
               obj.startOffset = ParserHelper.parseLength(val);
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <pattern> element


   private void pattern(Attributes attributes) throws SVGParseException
   {
      debug("<pattern>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Pattern obj = new Pattern();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesViewBox(obj, attributes);
      parseAttributesPattern(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   private void  parseAttributesPattern(Pattern obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case patternUnits:
               if ("objectBoundingBox".equals(val)) {
                  obj.patternUnitsAreUser = false;
               } else if ("userSpaceOnUse".equals(val)) {
                  obj.patternUnitsAreUser = true;
               } else {
                  throw new SVGParseException("Invalid value for attribute patternUnits");
               } 
               break;
            case patternContentUnits:
               if ("objectBoundingBox".equals(val)) {
                  obj.patternContentUnitsAreUser = false;
               } else if ("userSpaceOnUse".equals(val)) {
                  obj.patternContentUnitsAreUser = true;
               } else {
                  throw new SVGParseException("Invalid value for attribute patternContentUnits");
               } 
               break;
            case patternTransform:
               obj.patternTransform = parseTransformList(val);
               break;
            case x:
               obj.x = ParserHelper.parseLength(val);
               break;
            case y:
               obj.y = ParserHelper.parseLength(val);
               break;
            case width:
               obj.width = ParserHelper.parseLength(val);
               if (obj.width.isNegative())
                  throw new SVGParseException("Invalid <pattern> element. width cannot be negative");
               break;
            case height:
               obj.height = ParserHelper.parseLength(val);
               if (obj.height.isNegative())
                  throw new SVGParseException("Invalid <pattern> element. height cannot be negative");
               break;
            case href:
               if ("".equals(attributes.getURI(i)) || ParserHelper.XLINK_NAMESPACE.equals(attributes.getURI(i)))
                  obj.href = val;
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // <view> element


   private void  view(Attributes attributes) throws SVGParseException
   {
      debug("<view>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      View obj = new View();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesViewBox(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }

   
   //=========================================================================
   // <mask> element


   private void mask(Attributes attributes) throws SVGParseException
   {
      debug("<mask>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");
      Mask obj = new Mask();
      obj.document = svgDocument;
      obj.parent = currentElement;
      parseAttributesCore(obj, attributes);
      parseAttributesStyle(obj, attributes);
      parseAttributesConditional(obj, attributes);
      parseAttributesMask(obj, attributes);
      currentElement.addChild(obj);
      currentElement = obj;
   }


   private void  parseAttributesMask(Mask obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case maskUnits:
               if ("objectBoundingBox".equals(val)) {
                  obj.maskUnitsAreUser = false;
               } else if ("userSpaceOnUse".equals(val)) {
                  obj.maskUnitsAreUser = true;
               } else {
                  throw new SVGParseException("Invalid value for attribute maskUnits");
               } 
               break;
            case maskContentUnits:
               if ("objectBoundingBox".equals(val)) {
                  obj.maskContentUnitsAreUser = false;
               } else if ("userSpaceOnUse".equals(val)) {
                  obj.maskContentUnitsAreUser = true;
               } else {
                  throw new SVGParseException("Invalid value for attribute maskContentUnits");
               } 
               break;
            case x:
               obj.x = ParserHelper.parseLength(val);
               break;
            case y:
               obj.y = ParserHelper.parseLength(val);
               break;
            case width:
               obj.width = ParserHelper.parseLength(val);
               if (obj.width.isNegative())
                  throw new SVGParseException("Invalid <mask> element. width cannot be negative");
               break;
            case height:
               obj.height = ParserHelper.parseLength(val);
               if (obj.height.isNegative())
                  throw new SVGParseException("Invalid <mask> element. height cannot be negative");
               break;
            default:
               break;
         }
      }
   }


   //=========================================================================
   // Attribute parsing
   //=========================================================================


   private void  parseAttributesCore(SvgElementBase obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String  qname = attributes.getQName(i);
         if (qname.equals("id") || qname.equals("xml:id"))
         {
            obj.id = attributes.getValue(i).trim();
            break;
         }
         else if (qname.equals("xml:space")) {
            String  val = attributes.getValue(i).trim();
            if ("default".equals(val)) {
               obj.spacePreserve = Boolean.FALSE;
            } else if ("preserve".equals(val)) {
               obj.spacePreserve = Boolean.TRUE;
            } else {
               throw new SVGParseException("Invalid value for \"xml:space\" attribute: "+val);
            }
            break;
         }
      }
   }


   /*
    * Parse the style attributes for an element.
    */
   private void  parseAttributesStyle(SvgElementBase obj, Attributes attributes)
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String  val = attributes.getValue(i).trim();
         if (val.length() == 0) {  // Empty attribute. Ignore it.
            continue;
         }
         //boolean  inherit = val.equals("inherit");   // NYI

         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case style:
               ParserHelper.parseStyle(obj, val);
               break;

            case CLASS:
               obj.classNames = CSSParser.parseClassAttribute(val);
               break;

            default:
               if (obj.baseStyle == null)
                  obj.baseStyle = new Style();
               ParserHelper.processStyleProperty(obj.baseStyle, attributes.getLocalName(i), attributes.getValue(i).trim(), true);
               break;
         }
      }
   }


   private void  parseAttributesViewBox(SvgViewBoxContainer obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case viewBox:
               obj.viewBox = ParserHelper.parseViewBox(val);
               break;
            case preserveAspectRatio:
               ParserHelper.parsePreserveAspectRatio(obj, val);
               break;
            default:
               break;
         }
      }
   }


   private void  parseAttributesTransform(HasTransform obj, Attributes attributes) throws SVGParseException
   {
      for (int i=0; i<attributes.getLength(); i++)
      {
         if (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)) == ParserHelper.SVGAttr.transform)
         {
            obj.setTransform( parseTransformList(attributes.getValue(i)) );
         }
      }
   }


   private Matrix parseTransformList(String val) throws SVGParseException
   {
      Matrix  matrix = new Matrix();

      TextScanner  scan = new TextScanner(val);
      scan.skipWhitespace();

      while (!scan.empty())
      {
         String  cmd = scan.nextFunction();

         if (cmd == null)
            throw new SVGParseException("Bad transform function encountered in transform list: "+val);

         switch (cmd) {
            case "matrix":
               scan.skipWhitespace();
               float a = scan.nextFloat();
               scan.skipCommaWhitespace();
               float b = scan.nextFloat();
               scan.skipCommaWhitespace();
               float c = scan.nextFloat();
               scan.skipCommaWhitespace();
               float d = scan.nextFloat();
               scan.skipCommaWhitespace();
               float e = scan.nextFloat();
               scan.skipCommaWhitespace();
               float f = scan.nextFloat();
               scan.skipWhitespace();

               if (Float.isNaN(f) || !scan.consume(')'))
                  throw new SVGParseException("Invalid transform list: " + val);

               Matrix m = new Matrix(new float[]{a, c, e, b, d, f, 0, 0, 1});
               matrix.preConcat(m);
               break;

            case "translate":
               scan.skipWhitespace();
               float tx = scan.nextFloat();
               float ty = scan.possibleNextFloat();
               scan.skipWhitespace();

               if (Float.isNaN(tx) || !scan.consume(')'))
                  throw new SVGParseException("Invalid transform list: " + val);

               if (Float.isNaN(ty))
                  matrix.preTranslate(tx, 0f);
               else
                  matrix.preTranslate(tx, ty);
               break;

            case "scale":
               scan.skipWhitespace();
               float sx = scan.nextFloat();
               float sy = scan.possibleNextFloat();
               scan.skipWhitespace();

               if (Float.isNaN(sx) || !scan.consume(')'))
                  throw new SVGParseException("Invalid transform list: " + val);

               if (Float.isNaN(sy))
                  matrix.preScale(sx, sx);
               else
                  matrix.preScale(sx, sy);
               break;

            case "rotate": {
               scan.skipWhitespace();
               float ang = scan.nextFloat();
               float cx = scan.possibleNextFloat();
               float cy = scan.possibleNextFloat();
               scan.skipWhitespace();

               if (Float.isNaN(ang) || !scan.consume(')'))
                  throw new SVGParseException("Invalid transform list: " + val);

               if (Float.isNaN(cx)) {
                  matrix.preRotate(ang);
               } else if (!Float.isNaN(cy)) {
                  matrix.preRotate(ang, cx, cy);
               } else {
                  throw new SVGParseException("Invalid transform list: " + val);
               }
               break;
            }

            case "skewX": {
               scan.skipWhitespace();
               float ang = scan.nextFloat();
               scan.skipWhitespace();

               if (Float.isNaN(ang) || !scan.consume(')'))
                  throw new SVGParseException("Invalid transform list: " + val);

               matrix.preSkew((float) Math.tan(Math.toRadians(ang)), 0f);
               break;
            }

            case "skewY": {
               scan.skipWhitespace();
               float ang = scan.nextFloat();
               scan.skipWhitespace();

               if (Float.isNaN(ang) || !scan.consume(')'))
                  throw new SVGParseException("Invalid transform list: " + val);

               matrix.preSkew(0f, (float) Math.tan(Math.toRadians(ang)));
               break;
            }

            default:
               throw new SVGParseException("Invalid transform list fn: " + cmd + ")");
         }

         if (scan.empty())
            break;
         scan.skipCommaWhitespace();
      }

      return matrix;
   }


   //=========================================================================
   // Parsing various SVG value types
   //=========================================================================


   //=========================================================================


   //=========================================================================
   // Conditional processing (ie for <switch> element)


   //=========================================================================
   // Parsing <style> element. Very basic CSS parser.
   //=========================================================================


   private void  style(Attributes attributes) throws SVGParseException
   {
      debug("<style>");

      if (currentElement == null)
         throw new SVGParseException("Invalid document. Root element must be <svg>");

      // Check style sheet is in CSS format
      boolean  isTextCSS = true;
      String   media = "all";

      for (int i=0; i<attributes.getLength(); i++)
      {
         String val = attributes.getValue(i).trim();
         switch (ParserHelper.SVGAttr.fromString(attributes.getLocalName(i)))
         {
            case type:
               isTextCSS = val.equals(CSSParser.CSS_MIME_TYPE);
               break;
            case media:
               media = val;
               break;
            default:
               break;
         }
      }

      if (isTextCSS && CSSParser.mediaMatches(media, MediaType.screen)) {
         inStyleElement = true;
      } else {
         ignoring = true;
         ignoreDepth = 1;
      }
   }


   private void  parseCSSStyleSheet(String sheet)
   {
      CSSParser  cssp = new CSSParser(logger, MediaType.screen, Source.Document, cssResolver);
      svgDocument.addCSSRules(cssp.parse(sheet));
   }
}

