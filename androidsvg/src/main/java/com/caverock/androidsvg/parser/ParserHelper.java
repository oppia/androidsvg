package com.caverock.androidsvg.parser;

import static com.caverock.androidsvg.model.CSSFontFeatureSettings.CAPS_ALL_OFF;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.EAST_ASIAN_ALL_OFF;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_AFRC;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_C2PC;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_C2SC;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_CALT;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_CLIG;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_DLIG;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_FRAC;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_FWID;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_HLIG;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_JP04;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_JP78;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_JP83;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_JP90;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_LIGA;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_LNUM;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_ONUM;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_ORDN;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_PCAP;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_PNUM;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_PWID;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_RUBY;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_SMCP;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_SMPL;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_SUBS;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_SUPS;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_TITL;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_TNUM;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_TRAD;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_UNIC;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FEATURE_ZERO;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_ALL_PETITE_CAPS;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_ALL_SMALL_CAPS;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_AUTO;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_COMMON_LIGATURES;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_CONTEXTUAL_LIGATURES;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_DIAGONAL_FRACTIONS;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_DISCRETIONARY_LIGATURES;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_FULL_WIDTH;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_HISTORICAL_LIGATURES;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_JIS04;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_JIS78;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_JIS83;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_JIS90;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_LINING_NUMS;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_NONE;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_NORMAL;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_NO_COMMON_LIGATURES;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_NO_CONTEXTUAL_LIGATURES;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_NO_DISCRETIONARY_LIGATURES;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_NO_HISTORICAL_LIGATURES;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_OLDSTYLE_NUMS;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_ORDINAL;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_PETITE_CAPS;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_PROPORTIONAL_NUMS;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_PROPORTIONAL_WIDTH;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_RUBY;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_SIMPLIFIED;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_SLASHED_ZERO;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_SMALL_CAPS;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_STACKED_FRACTIONS;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_SUB;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_SUPER;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_TABULAR_NUMS;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_TITLING_CAPS;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_TRADITIONAL;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.FONT_VARIANT_UNICASE;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.LIGATURES_ALL_OFF;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.LIGATURES_NORMAL;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.NUMERIC_ALL_OFF;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.POSITION_ALL_OFF;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.TOKEN_ERROR;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.VALUE_OFF;
import static com.caverock.androidsvg.model.CSSFontFeatureSettings.VALUE_ON;

import com.caverock.androidsvg.model.PreserveAspectRatio;
import com.caverock.androidsvg.model.Box;
import com.caverock.androidsvg.model.CSSClipRect;
import com.caverock.androidsvg.model.CSSFontFeatureSettings;
import com.caverock.androidsvg.model.CSSFontVariationSettings;
import com.caverock.androidsvg.model.Colour;
import com.caverock.androidsvg.model.CurrentColor;
import com.caverock.androidsvg.model.Length;
import com.caverock.androidsvg.model.PaintReference;
import com.caverock.androidsvg.model.PathDefinition;
import com.caverock.androidsvg.model.Style;
import com.caverock.androidsvg.model.SvgElementBase;
import com.caverock.androidsvg.model.SvgPaint;
import com.caverock.androidsvg.model.SvgPreserveAspectRatioContainer;
import com.caverock.androidsvg.model.Unit;
import com.caverock.androidsvg.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ParserHelper {
    public static final String  TAG = "SVGParser";
    public static final String  SVG_NAMESPACE = "http://www.w3.org/2000/svg";
    public static final String  XLINK_NAMESPACE = "http://www.w3.org/1999/xlink";
    public static final String  FEATURE_STRING_PREFIX = "http://www.w3.org/TR/SVG11/feature#";
    public static final String  XML_STYLESHEET_PROCESSING_INSTRUCTION = "xml-stylesheet";
    // <?xml-stylesheet> attribute names and values
    public static final String  XML_STYLESHEET_ATTR_TYPE = "type";
    public static final String  XML_STYLESHEET_ATTR_ALTERNATE = "alternate";
    public static final String  XML_STYLESHEET_ATTR_HREF = "href";
    public static final String  XML_STYLESHEET_ATTR_MEDIA = "media";
    public static final String  XML_STYLESHEET_ATTR_MEDIA_ALL = "all";
    public static final String  XML_STYLESHEET_ATTR_ALTERNATE_NO = "no";
    // Special attribute keywords
    public static final String  NONE = "none";
    public static final String  CURRENTCOLOR = "currentColor";
    public static final String VALID_DISPLAY_VALUES = "|inline|block|list-item|run-in|compact|marker|table|inline-table"+
                                               "|table-row-group|table-header-group|table-footer-group|table-row"+
                                               "|table-column-group|table-column|table-cell|table-caption|none|";
    public static final String VALID_VISIBILITY_VALUES = "|visible|hidden|collapse|";

    /*
     * Parse the 'style' attribute.
     */
    public static void  parseStyle(SvgElementBase obj, String style)
    {
       CSSTextScanner scan = new CSSTextScanner(style.replaceAll("/\\*.*?\\*/", ""));  // regex strips block comments

       while (!scan.empty())
       {
          scan.skipWhitespace();
          String  propertyName = scan.nextIdentifier();
          scan.skipWhitespace();
          if (scan.consume(';'))
             continue;  // Handle stray/extra separators gracefully
          if (!scan.consume(':'))
             break;  // Unrecoverable parse error
          scan.skipWhitespace();
          String  propertyValue = scan.nextPropertyValue();
          if (propertyValue == null)
             continue;  // Empty value. Just ignore this property and keep parsing
          scan.skipWhitespace();
          if (scan.empty() || scan.consume(';'))
          {
             if (obj.style == null)
                obj.style = new Style();
             processStyleProperty(obj.style, propertyName, propertyValue, false);
             scan.skipWhitespace();
          }
       }
    }

    /*
     * Parse an SVG 'Length' value (usually a coordinate).
     * Spec says: length ::= number ("em" | "ex" | "px" | "in" | "cm" | "mm" | "pt" | "pc" | "%")?
     */
    public static Length parseLength(String val) throws SVGParseException
    {
       if (val.length() == 0)
          throw new SVGParseException("Invalid length value (empty string)");
       int   end = val.length();
       Unit unit = Unit.px;
       char  lastChar = val.charAt(end-1);

       if (lastChar == '%') {
          end -= 1;
          unit = Unit.percent;
       } else if (end > 2 && Character.isLetter(lastChar) && Character.isLetter(val.charAt(end-2))) {
          end -= 2;
          String unitStr = val.substring(end);
          try {
             unit = Unit.valueOf(unitStr.toLowerCase(Locale.US));
          } catch (IllegalArgumentException e) {
             throw new SVGParseException("Invalid length unit specifier: "+val);
          }
       }
       try
       {
          float scalar = parseFloat(val, 0, end);
          return new Length(scalar, unit);
       }
       catch (NumberFormatException e)
       {
          throw new SVGParseException("Invalid length value: "+val, e);
       }
    }

    /*
     * Parse a list of Length/Coords
     */
    public static List<Length> parseLengthList(String val) throws SVGParseException
    {
       if (val.length() == 0)
          throw new SVGParseException("Invalid length list (empty string)");

       List<Length>  coords = new ArrayList<>(1);

       TextScanner scan = new TextScanner(val);
       scan.skipWhitespace();

       while (!scan.empty())
       {
          float scalar = scan.nextFloat();
          if (Float.isNaN(scalar))
             throw new SVGParseException("Invalid length list value: "+scan.ahead());
          Unit unit = scan.nextUnit();
          if (unit == null)
             unit = Unit.px;
          coords.add(new Length(scalar, unit));
          scan.skipCommaWhitespace();
       }
       return coords;
    }

    /*
     * Parse a generic float value.
     */
    public static float  parseFloat(String val) throws SVGParseException
    {
       int  len = val.length();
       if (len == 0)
          throw new SVGParseException("Invalid float value (empty string)");
       return parseFloat(val, 0, len);
    }

    public static float  parseFloat(String val, int offset, int len) throws SVGParseException
    {
       NumberParser np = new NumberParser();
       float  num = np.parseNumber(val, offset, len);
       if (!Float.isNaN(num)) {
          return num;
       } else {
          throw new SVGParseException("Invalid float value: "+val);
       }
    }

    /*
     * Parse an opacity value (a float clamped to the range 0..1).
     */
    public static Float  parseOpacity(String val)
    {
       try {
          float  o = parseFloat(val);
          return (o < 0f) ? 0f : Math.min(o, 1f);
       } catch (SVGParseException e) {
          return null;
       }
    }

    /*
     * Parse a viewBox attribute.
     */
    public static Box parseViewBox(String val) throws SVGParseException
    {
       TextScanner scan = new TextScanner(val);
       scan.skipWhitespace();

       float minX = scan.nextFloat();
       scan.skipCommaWhitespace();
       float minY = scan.nextFloat();
       scan.skipCommaWhitespace();
       float width = scan.nextFloat();
       scan.skipCommaWhitespace();
       float height = scan.nextFloat();

       if (Float.isNaN(minX) || Float.isNaN(minY) || Float.isNaN(width) || Float.isNaN(height))
          throw new SVGParseException("Invalid viewBox definition - should have four numbers");
       if (width < 0)
          throw new SVGParseException("Invalid viewBox. width cannot be negative");
       if (height < 0)
          throw new SVGParseException("Invalid viewBox. height cannot be negative");

       return new Box(minX, minY, width, height);
    }

    /*
     * Parse a preserveAspectRation attribute
     */
    public static void  parsePreserveAspectRatio(SvgPreserveAspectRatioContainer obj, String val)
    {
        PreserveAspectRatio result;
        try {
            result = parsePreserveAspectRatio(val);
        } catch (SVGParseException e) {
           throw new IllegalArgumentException(e.getMessage());
        }
        obj.preserveAspectRatio = result;
    }

    private static PreserveAspectRatio  parsePreserveAspectRatio(String val) throws SVGParseException
    {
        TextScanner scan = new TextScanner(val);
        scan.skipWhitespace();

        String  word = scan.nextToken();
        if ("defer".equals(word)) {    // Ignore defer keyword
            scan.skipWhitespace();
            word = scan.nextToken();
        }

        PreserveAspectRatio.Alignment  align = PreserveAspectRatio.aspectRatioKeywords.get(word);
        PreserveAspectRatio.Scale      scale = null;

        scan.skipWhitespace();

        if (!scan.empty()) {
            String meetOrSlice = scan.nextToken();
            switch (meetOrSlice) {
                case "meet":
                    scale = PreserveAspectRatio.Scale.meet; break;
                case "slice":
                    scale = PreserveAspectRatio.Scale.slice; break;
                default:
                    throw new SVGParseException("Invalid preserveAspectRatio definition: " + val);
            }
        }
        return new PreserveAspectRatio(align, scale);
    }

    /*
     * Parse a paint specifier such as in the fill and stroke attributes.
     */
    public static SvgPaint parsePaintSpecifier(String val)
    {
       if (val.startsWith("url("))
       {
          int  closeBracket = val.indexOf(")");
          if (closeBracket != -1)
          {
             String    href = val.substring(4, closeBracket).trim();
             SvgPaint fallback = null;

             val = val.substring(closeBracket+1).trim();
             if (val.length() > 0)
                fallback = parseColourSpecifer(val);
             return new PaintReference(href, fallback);
          }
          else
          {
             String    href = val.substring(4).trim();
             return new PaintReference(href, null);
          }
       }
       return parseColourSpecifer(val);
    }

    public static SvgPaint parseColourSpecifer(String val)
    {
       switch (val) {
          case NONE:
             return Colour.TRANSPARENT;
          case CURRENTCOLOR:
             return CurrentColor.getInstance();
          default:
             return parseColour(val);
       }
    }

    /*
     * Parse a colour definition.
     */
    public static Colour parseColour(String val)
    {
       if (val.charAt(0) == '#')
       {
          IntegerParser ip = IntegerParser.parseHex(val, 1, val.length());
          if (ip == null) {
             return Colour.BLACK;
          }
          int  pos = ip.getEndPos();
          int  h1, h2, h3, h4;
          switch (pos) {
             case 4:
                int threehex = ip.value();
                h1 = threehex & 0xf00;  // r
                h2 = threehex & 0x0f0;  // g
                h3 = threehex & 0x00f;  // b
                return new Colour(0xff000000|h1<<12|h1<<8|h2<<8|h2<<4|h3<<4|h3);
             case 5:
                int fourhex = ip.value();
                h1 = fourhex & 0xf000;  // r
                h2 = fourhex & 0x0f00;  // g
                h3 = fourhex & 0x00f0;  // b
                h4 = fourhex & 0x000f;  // alpha
                return new Colour(h4<<28|h4<<24 | h1<<8|h1<<4 | h2<<4|h2 | h3|h3>>4);
             case 7:
                return new Colour(0xff000000 | ip.value());
             case 9:
                return new Colour(ip.value() << 24 | ip.value() >>> 8);
             default:
                // Hex value had bad length for a colour
                return Colour.BLACK;
          }
       }

       // Parse an rgb() or rgba() colour.
       // In CSS Color 4, these are synonyms, and the alpha parameter is optional in both cases.
       String   valLowerCase = val.toLowerCase(Locale.US);
       boolean  isRGBA = valLowerCase.startsWith("rgba(");
       if (isRGBA || valLowerCase.startsWith("rgb("))
       {
          TextScanner  scan = new TextScanner(val.substring(isRGBA ? 5 : 4));
          scan.skipWhitespace();

          float  red = scan.nextFloat();
          if (!Float.isNaN(red)) {
             if (scan.consume('%'))
                red = (red * 256) / 100;

             // If there is a comma, then it is the "legacy" format: rgb(r, g, b, a?).
             // Otherwise we assume it is the new format: rgb[a?](r g b / a?).
             boolean isLegacyCSSColor3 = scan.skipCommaWhitespace();

             float green = scan.nextFloat();
             if (!Float.isNaN(green)) {
                if (scan.consume('%'))
                   green = (green * 256) / 100;

                if (isLegacyCSSColor3) {
                   if (!scan.skipCommaWhitespace())
                      return Colour.BLACK;   // Error
                } else {
                   scan.skipWhitespace();
                }

                float blue = scan.nextFloat();
                if (!Float.isNaN(blue)) {
                   if (scan.consume('%'))
                      blue = (blue * 256) / 100;

                   // Now look for optional alpha
                   float alpha = Float.NaN;
                   if (isLegacyCSSColor3) {
                      if (scan.skipCommaWhitespace())
                         alpha = scan.nextFloat();
                   } else {
                      scan.skipWhitespace();
                      if (scan.consume('/')) {
                         scan.skipWhitespace();
                         alpha = scan.nextFloat();
                      }
                   }
                   scan.skipWhitespace();
                   if (!scan.consume(')'))
                      return Colour.BLACK;
                   if (Float.isNaN(alpha))
                      return new Colour( 0xff000000 | clamp255(red)<<16 | clamp255(green)<<8 | clamp255(blue) );
                   else
                      return new Colour( clamp255(alpha * 256)<<24 | clamp255(red)<<16 | clamp255(green)<<8 | clamp255(blue) );
                }
             }
          }
       }
       else
       {
          // Parse an hsl() or hsla() colour.
          // In CSS Color 4, these are synonyms, and the alpha parameter is optional in both cases.
          boolean  isHSLA = valLowerCase.startsWith("hsla(");
          if (isHSLA || valLowerCase.startsWith("hsl("))
          {
             TextScanner  scan = new TextScanner(val.substring(isHSLA ? 5 : 4));
             scan.skipWhitespace();

             float  hue = scan.nextFloat();
             if (!Float.isNaN(hue)) {
                scan.consume("deg");  // Optional units

                // If there is a comma, then it is the "legacy" format: rgb(r, g, b, a?).
                // Otherwise we assume it is the new format: rgb[a?](r g b / a?).
                boolean isLegacyCSSColor3 = scan.skipCommaWhitespace();

                float saturation = scan.nextFloat();
                if (!Float.isNaN(saturation)) {
                   if (!scan.consume('%'))
                      return Colour.BLACK;

                   if (isLegacyCSSColor3) {
                      if (!scan.skipCommaWhitespace())
                         return Colour.BLACK;
                   } else {
                      scan.skipWhitespace();
                   }

                   float lightness = scan.nextFloat();
                   if (!Float.isNaN(lightness)) {
                      if (!scan.consume('%'))
                         return Colour.BLACK;

                      // Now look for optional alpha
                      float alpha = Float.NaN;
                      if (isLegacyCSSColor3) {
                         if (scan.skipCommaWhitespace())
                            alpha = scan.nextFloat();
                      } else {
                         scan.skipWhitespace();
                         if (scan.consume('/')) {
                            scan.skipWhitespace();
                            alpha = scan.nextFloat();
                         }
                      }
                      scan.skipWhitespace();
                      if (!scan.consume(')'))
                         return Colour.BLACK;
                      if (Float.isNaN(alpha))
                         return new Colour( 0xff000000 | hslToRgb(hue, saturation, lightness) );
                      else
                         return new Colour( clamp255(alpha * 256)<<24 | hslToRgb(hue, saturation, lightness) );
                   }
                }
             }
          }
       }

       // Must be a colour keyword
       return parseColourKeyword(valLowerCase);
    }

    // Clamp a float to the range 0..255
    public static int  clamp255(float val)
    {
       return (val < 0) ? 0 : (val > 255) ? 255 : Math.round(val);
    }

    // Hue (degrees), saturation [0, 100], lightness [0, 100]
    public static int  hslToRgb(float hue, float sat, float light)
    {
       hue = (hue >= 0f) ? hue % 360f : (hue % 360f) + 360f;  // positive modulo (ie. -10 => 350)
       hue /= 60f;    // [0, 360] -> [0, 6]
       sat /= 100;   // [0, 100] -> [0, 1]
       light /= 100; // [0, 100] -> [0, 1]
       sat = (sat < 0f) ? 0f : Math.min(sat, 1f);
       light = (light < 0f) ? 0f : Math.min(light, 1f);
       float  t1, t2;
       if (light <= 0.5f) {
          t2 = light * (sat + 1f);
       } else {
          t2 = light + sat - (light * sat);
       }
       t1 = light * 2f - t2;
       float  r = hueToRgb(t1, t2, hue + 2f);
       float  g = hueToRgb(t1, t2, hue);
       float  b = hueToRgb(t1, t2, hue - 2f);
       return clamp255(r * 256f)<<16 | clamp255(g * 256f)<<8 | clamp255(b * 256f);
    }

    public static float  hueToRgb(float t1, float t2, float hue) {
       if (hue < 0f) hue += 6f;
       if (hue >= 6f) hue -= 6f;

       if (hue < 1) return (t2 - t1) * hue + t1;
       else if (hue < 3f) return t2;
       else if (hue < 4f) return (t2 - t1) * (4f - hue) + t1;
       else return t1;
    }

    // Parse a colour component value (0..255 or 0%-100%)
    public static Colour parseColourKeyword(String nameLowerCase)
    {
       Integer  col = ColourKeywords.get(nameLowerCase);
       return (col == null) ? Colour.BLACK : new Colour(col);
    }

    // Parse a font attribute
    // [ [ <'font-style'> || <'font-variant'> || <'font-weight'> ]? <'font-size'> [ / <'line-height'> ]? <'font-family'> ] | caption | icon | menu | message-box | small-caption | status-bar | inherit
    public static void  parseFont(Style style, String val)
    {
       Float            fontWeight = null;
       Style.FontStyle  fontStyle = null;
       String           fontVariant = null;
       Float            fontStretch = null;
       Boolean          fontVariantSmallCaps = null;

       final String  NORMAL = "normal";

       // Start by checking for the fixed size standard system font names (which we don't support)
       if ("|caption|icon|menu|message-box|small-caption|status-bar|".contains('|'+val+'|'))
          return;

       // First part: style/variant/weight (opt - one or more)
       TextScanner  scan = new TextScanner(val);
       String       item;
       while (true)
       {
          item = scan.nextToken('/');
          scan.skipWhitespace();
          if (item == null)
             return;
          if (fontWeight != null && fontStyle != null)
             break;
          if (item.equals(NORMAL))  {
             // indeterminate right now which of these this refers to
             continue;
          }
          if (fontWeight == null && FontWeightKeywords.contains(item)) {
             fontWeight = FontWeightKeywords.get(item);
             continue;
          }
          if (fontStyle == null) {
             fontStyle = parseFontStyle(item);
             if (fontStyle != null)
                continue;
          }
          // Must be a font-variant keyword?
          if (fontVariantSmallCaps == null && item.equals(FONT_VARIANT_SMALL_CAPS)) {
             fontVariantSmallCaps = true;
             continue;
          }
          if (fontStretch == null && FontStretchKeywords.contains(item)) {
             fontStretch = FontStretchKeywords.get(item);
             continue;
          }
          // Not any of these. Break and try next section
          break;
       }

       // Second part: font size (reqd) and line-height (opt)
       Length fontSize = parseFontSize(item);

       // Check for line-height (which we don't support)
       if (scan.consume('/'))
       {
          scan.skipWhitespace();
          item = scan.nextToken();
          if (item != null) {
             try {
                parseLength(item);
             } catch (SVGParseException e) {
                return;
             }
          }
          scan.skipWhitespace();
       }

       // Third part: font family
       style.fontFamily = parseFontFamily(scan.restOfText());

       style.fontSize = fontSize;
       style.fontWeight = (fontWeight == null) ? Style.FONT_WEIGHT_NORMAL : fontWeight;
       style.fontStyle = (fontStyle == null) ? Style.FontStyle.normal : fontStyle;
       style.fontStretch = (fontStretch == null) ? Style.FONT_STRETCH_NORMAL : fontStretch;
       style.fontKerning = Style.FontKerning.auto;
       style.fontVariantLigatures = LIGATURES_NORMAL;
       style.fontVariantPosition = POSITION_ALL_OFF;
       style.fontVariantCaps = CAPS_ALL_OFF;
       if (fontVariantSmallCaps == Boolean.TRUE)
          style.fontVariantCaps = CSSFontFeatureSettings.makeSmallCaps();
       style.fontVariantNumeric =  NUMERIC_ALL_OFF;
       style.fontVariantEastAsian =  EAST_ASIAN_ALL_OFF;
       style.fontFeatureSettings = CSSFontFeatureSettings.FONT_FEATURE_SETTINGS_NORMAL;
       style.fontVariationSettings = null;

       style.specifiedFlags |= (Style.SPECIFIED_FONT_FAMILY | Style.SPECIFIED_FONT_SIZE | Style.SPECIFIED_FONT_WEIGHT | Style.SPECIFIED_FONT_STYLE | Style.SPECIFIED_FONT_STRETCH |
                                Style.SPECIFIED_FONT_KERNING | Style.SPECIFIED_FONT_VARIANT_LIGATURES | Style.SPECIFIED_FONT_VARIANT_POSITION | Style.SPECIFIED_FONT_VARIANT_CAPS |
                                Style.SPECIFIED_FONT_VARIANT_NUMERIC | Style.SPECIFIED_FONT_VARIANT_EAST_ASIAN | Style.SPECIFIED_FONT_FEATURE_SETTINGS | Style.SPECIFIED_FONT_VARIATION_SETTINGS);
    }

    // Parse a font family list
    public static List<String>  parseFontFamily(String val)
    {
       List<String> fonts = null;
       TextScanner  scan = new TextScanner(val);
       while (true)
       {
          String item = scan.nextQuotedString();
          if (item == null)
             item = scan.nextTokenWithWhitespace(',');
          if (item == null)
             break;
          if (fonts == null)
             fonts = new ArrayList<>();
          fonts.add(item);
          scan.skipCommaWhitespace();
          if (scan.empty())
             break;
       }
       return fonts;
    }

    // Parse a font size keyword or numerical value
    public static Length parseFontSize(String val)
    {
       try {
          Length size = FontSizeKeywords.get(val);
          if (size == null)
             size = parseLength(val);
          return size;
       } catch (SVGParseException e) {
          return null;
       }
    }

    // Parse a font weight keyword or numerical value
    public static Float  parseFontWeight(String val)
    {
       Float  result = FontWeightKeywords.get(val);
       if (result == null) {
          // Check for a number
          TextScanner  scan = new TextScanner(val);
          result = scan.nextFloat();
          scan.skipWhitespace();
          if (!scan.empty())
             return null;
          if (result < Style.FONT_WEIGHT_MIN || result > Style.FONT_WEIGHT_MAX)
             return null;   // Invalid
       }
       return result;
    }

    // Parse a font stretch keyword or numerical value
    public static Float  parseFontStretch(String val)
    {
       Float  result = FontStretchKeywords.get(val);
       if (result == null) {
          // Check for a percentage value
          TextScanner  scan = new TextScanner(val);
          result = scan.nextFloat();
          if (!scan.consume('%'))
             return null;
          scan.skipWhitespace();
          if (!scan.empty())
             return null;
          if (result < Style.FONT_STRETCH_MIN)
             return null;   // Invalid
       }
       return result;
    }

    // Parse a font style keyword
    public static Style.FontStyle  parseFontStyle(String val)
    {
       // Italic is probably the most common, so test that first :)
       switch (val)
       {
          case "italic":  return Style.FontStyle.italic;
          case "normal":  return Style.FontStyle.normal;
          case "oblique": return Style.FontStyle.oblique;
          default:        return null;
       }
    }

    // Parse a text decoration keyword
    public static Style.TextDecoration parseTextDecoration(String val)
    {
       switch (val)
       {
          case NONE:           return Style.TextDecoration.None;
          case "underline":    return Style.TextDecoration.Underline;
          case "overline":     return Style.TextDecoration.Overline;
          case "line-through": return Style.TextDecoration.LineThrough;
          case "blink":        return Style.TextDecoration.Blink;
          default:             return null;
       }
    }

    // Parse a text decoration keyword
    public static Style.TextDirection parseTextDirection(String val)
    {
       switch (val)
       {
          case "ltr": return Style.TextDirection.LTR;
          case "rtl": return Style.TextDirection.RTL;
          default:    return null;
       }
    }

    // Parse fill rule
    public static Style.FillRule  parseFillRule(String val)
    {
       if ("nonzero".equals(val))
          return Style.FillRule.NonZero;
       if ("evenodd".equals(val))
          return Style.FillRule.EvenOdd;
       return null;
    }

    // Parse stroke-linecap
    public static Style.LineCap  parseStrokeLineCap(String val)
    {
       if ("butt".equals(val))
          return Style.LineCap.Butt;
       if ("round".equals(val))
          return Style.LineCap.Round;
       if ("square".equals(val))
          return Style.LineCap.Square;
       return null;
    }

    // Parse stroke-linejoin
    public static Style.LineJoin  parseStrokeLineJoin(String val)
    {
       if ("miter".equals(val))
          return Style.LineJoin.Miter;
       if ("round".equals(val))
          return Style.LineJoin.Round;
       if ("bevel".equals(val))
          return Style.LineJoin.Bevel;
       return null;
    }

    // Parse stroke-dasharray
    public static Length[]  parseStrokeDashArray(String val)
    {
       TextScanner scan = new TextScanner(val);
       scan.skipWhitespace();

       if (scan.empty())
          return null;

       Length dash = scan.nextLength();
       if (dash == null)
          return null;
       if (dash.isNegative())
          return null;

       float sum = dash.floatValue();

       List<Length> dashes = new ArrayList<>();
       dashes.add(dash);
       while (!scan.empty())
       {
          scan.skipCommaWhitespace();
          dash = scan.nextLength();
          if (dash == null)  // must have hit something unexpected
             return null;
          if (dash.isNegative())
             return null;
          dashes.add(dash);
          sum += dash.floatValue();
       }

       // Spec (section 11.4) says if the sum of dash lengths is zero, it should
       // be treated as "none" ie a solid stroke.
       if (sum == 0f)
          return null;

       return dashes.toArray(new Length[0]);
    }

    // Parse a text anchor keyword
    public static Style.TextAnchor  parseTextAnchor(String val)
    {
       switch (val)
       {
          case "start":  return Style.TextAnchor.Start;
          case "middle": return Style.TextAnchor.Middle;
          case "end":    return Style.TextAnchor.End;
          default:       return null;
       }
    }

    // Parse a text anchor keyword
    public static Boolean  parseOverflow(String val)
    {
       switch (val)
       {
          case "visible":
          case "auto":
             return Boolean.TRUE;
          case "hidden":
          case "scroll":
             return Boolean.FALSE;
          default:
             return null;
       }
    }

    // Parse CSS clip shape (always a rect())
    public static CSSClipRect parseClip(String val)
    {
       if ("auto".equals(val))
          return null;
       if (!val.startsWith("rect("))
          return null;

       TextScanner scan = new TextScanner(val.substring(5));
       scan.skipWhitespace();

       Length top = parseLengthOrAuto(scan);
       scan.skipCommaWhitespace();
       Length right = parseLengthOrAuto(scan);
       scan.skipCommaWhitespace();
       Length bottom = parseLengthOrAuto(scan);
       scan.skipCommaWhitespace();
       Length left = parseLengthOrAuto(scan);

       scan.skipWhitespace();
       if (!scan.consume(')') && !scan.empty())   // Be forgibing. Allow missing ')'.
          return null;

       return new CSSClipRect(top, right, bottom, left);
    }

    public static Length parseLengthOrAuto(TextScanner scan)
    {
       if (scan.consume("auto"))
          return Length.ZERO;

       return scan.nextLength();
    }

    // Parse a vector effect keyword
    public static Style.VectorEffect parseVectorEffect(String val)
    {
       switch (val)
       {
          case NONE:                 return Style.VectorEffect.None;
          case "non-scaling-stroke": return Style.VectorEffect.NonScalingStroke;
          default:                   return null;
       }
    }

    // Parse a rendering quality property
    public static Style.RenderQuality parseRenderQuality(String val)
    {
       switch (val)
       {
          case "auto":            return Style.RenderQuality.auto;
          case "optimizeQuality": return Style.RenderQuality.optimizeQuality;
          case "optimizeSpeed":   return Style.RenderQuality.optimizeSpeed;
          default:                return null;
       }
    }

    // Parse a isolation property
    public static Style.Isolation parseIsolation(String val)
    {
       switch (val)
       {
          case "auto":    return Style.Isolation.auto;
          case "isolate": return Style.Isolation.isolate;
          default:        return null;
       }
    }

    public static Length parseLetterOrWordSpacing(String val)
    {
       if ("normal".equals(val))
          return Length.ZERO;
       else {
          try {
             Length result = parseLength(val);
             // Percent units were removed in SVG2 and are treated as an error.
             return (result.unit == Unit.percent) ? null : result;
          } catch (SVGParseException e) {
             return null;
          }
       }
    }

    // Parse the string that defines a path.
    public static PathDefinition  parsePath(Logger logger, String val)
    {
       TextScanner  scan = new TextScanner(val);

       float   currentX = 0f, currentY = 0f;    // The last point visited in the subpath
       float   lastMoveX = 0f, lastMoveY = 0f;  // The initial point of current subpath
       float   lastControlX = 0f, lastControlY = 0f;  // Last control point of the just completed bezier curve.
       float   x,y, x1,y1, x2,y2;
       float   rx,ry, xAxisRotation;
       Boolean largeArcFlag, sweepFlag;

       PathDefinition path = new PathDefinition();

       if (scan.empty())
          return path;

       int  pathCommand = scan.nextChar();

       if (pathCommand != 'M' && pathCommand != 'm')
          return path;  // Invalid path - doesn't start with a move

       while (true)
       {
          scan.skipWhitespace();

          switch (pathCommand)
          {
             // Move
             case 'M':
             case 'm':
                x = scan.nextFloat();
                y = scan.checkedNextFloat(x);
                if (Float.isNaN(y)) {
                   logger.logError(TAG, "Bad path coords for "+((char)pathCommand)+" path segment");
                   return path;
                }
                // Relative moveto at the start of a path is treated as an absolute moveto.
                if (pathCommand=='m' && !path.isEmpty()) {
                   x += currentX;
                   y += currentY;
                }
                path.moveTo(x, y);
                currentX = lastMoveX = lastControlX = x;
                currentY = lastMoveY = lastControlY = y;
                // Any subsequent coord pairs should be treated as a lineto.
                pathCommand = (pathCommand=='m') ? 'l' : 'L';
                break;

                // Line
             case 'L':
             case 'l':
                x = scan.nextFloat();
                y = scan.checkedNextFloat(x);
                if (Float.isNaN(y)) {
                    logger.logError(TAG, "Bad path coords for "+((char)pathCommand)+" path segment");
                   return path;
                }
                if (pathCommand=='l') {
                   x += currentX;
                   y += currentY;
                }
                path.lineTo(x, y);
                currentX = lastControlX = x;
                currentY = lastControlY = y;
                break;

                // Cubic bezier
             case 'C':
             case 'c':
                x1 = scan.nextFloat();
                y1 = scan.checkedNextFloat(x1);
                x2 = scan.checkedNextFloat(y1);
                y2 = scan.checkedNextFloat(x2);
                x = scan.checkedNextFloat(y2);
                y = scan.checkedNextFloat(x);
                if (Float.isNaN(y)) {
                    logger.logError(TAG, "Bad path coords for "+((char)pathCommand)+" path segment");
                   return path;
                }
                if (pathCommand=='c') {
                   x += currentX;
                   y += currentY;
                   x1 += currentX;
                   y1 += currentY;
                   x2 += currentX;
                   y2 += currentY;
                }
                path.cubicTo(x1, y1, x2, y2, x, y);
                lastControlX = x2;
                lastControlY = y2;
                currentX = x;
                currentY = y;
                break;

                // Smooth curve (first control point calculated)
             case 'S':
             case 's':
                x1 = 2 * currentX - lastControlX;
                y1 = 2 * currentY - lastControlY;
                x2 = scan.nextFloat();
                y2 = scan.checkedNextFloat(x2);
                x = scan.checkedNextFloat(y2);
                y = scan.checkedNextFloat(x);
                if (Float.isNaN(y)) {
                    logger.logError(TAG, "Bad path coords for "+((char)pathCommand)+" path segment");
                   return path;
                }
                if (pathCommand=='s') {
                   x += currentX;
                   y += currentY;
                   x2 += currentX;
                   y2 += currentY;
                }
                path.cubicTo(x1, y1, x2, y2, x, y);
                lastControlX = x2;
                lastControlY = y2;
                currentX = x;
                currentY = y;
                break;

                // Close path
             case 'Z':
             case 'z':
                path.close();
                currentX = lastControlX = lastMoveX;
                currentY = lastControlY = lastMoveY;
                break;

                // Horizontal line
             case 'H':
             case 'h':
                x = scan.nextFloat();
                if (Float.isNaN(x)) {
                    logger.logError(TAG, "Bad path coords for "+((char)pathCommand)+" path segment");
                   return path;
                }
                if (pathCommand=='h') {
                   x += currentX;
                }
                path.lineTo(x, currentY);
                currentX = lastControlX = x;
                break;

                // Vertical line
             case 'V':
             case 'v':
                y = scan.nextFloat();
                if (Float.isNaN(y)) {
                    logger.logError(TAG, "Bad path coords for "+((char)pathCommand)+" path segment");
                   return path;
                }
                if (pathCommand=='v') {
                   y += currentY;
                }
                path.lineTo(currentX, y);
                currentY = lastControlY = y;
                break;

                // Quadratic bezier
             case 'Q':
             case 'q':
                x1 = scan.nextFloat();
                y1 = scan.checkedNextFloat(x1);
                x = scan.checkedNextFloat(y1);
                y = scan.checkedNextFloat(x);
                if (Float.isNaN(y)) {
                    logger.logError(TAG, "Bad path coords for "+((char)pathCommand)+" path segment");
                   return path;
                }
                if (pathCommand=='q') {
                   x += currentX;
                   y += currentY;
                   x1 += currentX;
                   y1 += currentY;
                }
                path.quadTo(x1, y1, x, y);
                lastControlX = x1;
                lastControlY = y1;
                currentX = x;
                currentY = y;
                break;

                // Smooth quadratic bezier
             case 'T':
             case 't':
                x1 = 2 * currentX - lastControlX;
                y1 = 2 * currentY - lastControlY;
                x = scan.nextFloat();
                y = scan.checkedNextFloat(x);
                if (Float.isNaN(y)) {
                    logger.logError(TAG, "Bad path coords for "+((char)pathCommand)+" path segment");
                   return path;
                }
                if (pathCommand=='t') {
                   x += currentX;
                   y += currentY;
                }
                path.quadTo(x1, y1, x, y);
                lastControlX = x1;
                lastControlY = y1;
                currentX = x;
                currentY = y;
                break;

                // Arc
             case 'A':
             case 'a':
                rx = scan.nextFloat();
                ry = scan.checkedNextFloat(rx);
                xAxisRotation = scan.checkedNextFloat(ry);
                largeArcFlag = scan.checkedNextFlag(xAxisRotation);
                sweepFlag = scan.checkedNextFlag(largeArcFlag);
                x = scan.checkedNextFloat(sweepFlag);
                y = scan.checkedNextFloat(x);
                if (Float.isNaN(y) || rx < 0 || ry < 0) {
                    logger.logError(TAG, "Bad path coords for "+((char)pathCommand)+" path segment");
                   return path;
                }
                if (pathCommand=='a') {
                   x += currentX;
                   y += currentY;
                }
                path.arcTo(rx, ry, xAxisRotation, largeArcFlag, sweepFlag, x, y);
                currentX = lastControlX = x;
                currentY = lastControlY = y;
                break;

             default:
                return path;
          }

          scan.skipCommaWhitespace();
          if (scan.empty())
             break;

          // Test to see if there is another set of coords for the current path command
          if (scan.hasLetter()) {
             // Nope, so get the new path command instead
             pathCommand = scan.nextChar();
          }
       }
       return path;
    }

    // Parse the attribute that declares the list of SVG features that must be
    // supported if we are to render this element
    public static Set<String> parseRequiredFeatures(String val)
    {
       TextScanner      scan = new TextScanner(val);
       HashSet<String> result = new HashSet<>();

       while (!scan.empty())
       {
          String feature = scan.nextToken();
          if (feature.startsWith(FEATURE_STRING_PREFIX)) {
             result.add(feature.substring(FEATURE_STRING_PREFIX.length()));
          } else {
             // Not a feature string we recognise or support. (In order to avoid accidentally
             // matches with our truncated feature strings, we'll replace it with a string
             // we know for sure won't match anything.
             result.add("UNSUPPORTED");
          }
          scan.skipWhitespace();
       }
       return result;
    }

    // Parse the attribute that declares the list of languages, one of which
    // must be supported if we are to render this element
    public static Set<String>  parseSystemLanguage(String val)
    {
       TextScanner      scan = new TextScanner(val);
       HashSet<String>  result = new HashSet<>();

       while (!scan.empty())
       {
          String language = scan.nextToken();
          int  hyphenPos = language.indexOf('-');
          if (hyphenPos != -1) {
             language = language.substring(0, hyphenPos);
          }
          // Get canonical version of language code in case it has changed (see the JavaDoc for Locale.getLanguage())
          language = new Locale(language, "", "").getLanguage();
          result.add(language);
          scan.skipWhitespace();
       }
       return result;
    }

    // Parse the attribute that declares the list of MIME types that must be
    // supported if we are to render this element
    public static Set<String>  parseRequiredFormats(String val)
    {
       TextScanner      scan = new TextScanner(val);
       HashSet<String>  result = new HashSet<>();

       while (!scan.empty())
       {
          String mimetype = scan.nextToken();
          result.add(mimetype);
          scan.skipWhitespace();
       }
       return result;
    }

    public static String  parseFunctionalIRI(String val, String attrName)
    {
       if (val.equals(NONE))
          return null;
       if (!val.startsWith("url("))
          return null;
       if (val.endsWith(")"))
          return val.substring(4, val.length()-1).trim();
       else
          return val.substring(4).trim();
       // Unlike CSS, the SVG spec seems to indicate that quotes are not allowed in "url()" references
    }

   public static void  processStyleProperty(Style style, String localName, String val, boolean isFromAttribute)
   {
      if (val.length() == 0) { // The spec doesn't say how to handle empty style attributes.
         return;               // Our strategy is just to ignore them.
      }
      if (val.equals("inherit"))
         return;

      switch (SVGAttr.fromString(localName))
      {
         case fill:
            style.fill = parsePaintSpecifier(val);
            if (style.fill != null)
               style.specifiedFlags |= Style.SPECIFIED_FILL;
            break;

         case fill_rule:
            style.fillRule = parseFillRule(val);
            if (style.fillRule != null)
               style.specifiedFlags |= Style.SPECIFIED_FILL_RULE;
            break;

         case fill_opacity:
            style.fillOpacity = parseOpacity(val);
            if (style.fillOpacity != null)
               style.specifiedFlags |= Style.SPECIFIED_FILL_OPACITY;
            break;

         case stroke:
            style.stroke = parsePaintSpecifier(val);
            if (style.stroke != null)
               style.specifiedFlags |= Style.SPECIFIED_STROKE;
            break;

         case stroke_opacity:
            style.strokeOpacity = parseOpacity(val);
            if (style.strokeOpacity != null)
               style.specifiedFlags |= Style.SPECIFIED_STROKE_OPACITY;
            break;

         case stroke_width:
            try {
               style.strokeWidth = parseLength(val);
               style.specifiedFlags |= Style.SPECIFIED_STROKE_WIDTH;
            } catch (SVGParseException e) {
               // Do nothing
            }
            break;

         case stroke_linecap:
            style.strokeLineCap = parseStrokeLineCap(val);
            if (style.strokeLineCap != null)
               style.specifiedFlags |= Style.SPECIFIED_STROKE_LINECAP;
            break;

         case stroke_linejoin:
            style.strokeLineJoin = parseStrokeLineJoin(val);
            if (style.strokeLineJoin != null)
               style.specifiedFlags |= Style.SPECIFIED_STROKE_LINEJOIN;
            break;

         case stroke_miterlimit:
            try {
               style.strokeMiterLimit = parseFloat(val);
               style.specifiedFlags |= Style.SPECIFIED_STROKE_MITERLIMIT;
            } catch (SVGParseException e) {
               // Do nothing
            }
            break;

         case stroke_dasharray:
            if (NONE.equals(val)) {
               style.strokeDashArray = null;
               style.specifiedFlags |= Style.SPECIFIED_STROKE_DASHARRAY;
               break;
            }
            style.strokeDashArray = parseStrokeDashArray(val);
            if (style.strokeDashArray != null)
               style.specifiedFlags |= Style.SPECIFIED_STROKE_DASHARRAY;
            break;

         case stroke_dashoffset:
            try {
               style.strokeDashOffset = parseLength(val);
               style.specifiedFlags |= Style.SPECIFIED_STROKE_DASHOFFSET;
            } catch (SVGParseException e) {
               // Do nothing
            }
            break;

         case opacity:
            style.opacity = parseOpacity(val);
            style.specifiedFlags |= Style.SPECIFIED_OPACITY;
            break;

         case color:
            style.color = parseColour(val);
            style.specifiedFlags |= Style.SPECIFIED_COLOR;
            break;

         case font:
            if (isFromAttribute)
               break;
            parseFont(style, val);
            break;

         case font_family:
            style.fontFamily = parseFontFamily(val);
            if (style.fontFamily != null)
               style.specifiedFlags |= Style.SPECIFIED_FONT_FAMILY;
            break;

         case font_size:
            style.fontSize = parseFontSize(val);
            if (style.fontSize != null)
               style.specifiedFlags |= Style.SPECIFIED_FONT_SIZE;
            break;

         case font_weight:
            style.fontWeight = parseFontWeight(val);
            if (style.fontWeight != null)
               style.specifiedFlags |= Style.SPECIFIED_FONT_WEIGHT;
            break;

         case font_style:
            style.fontStyle = parseFontStyle(val);
            if (style.fontStyle != null)
               style.specifiedFlags |= Style.SPECIFIED_FONT_STYLE;
            break;

         case font_stretch:
            style.fontStretch = parseFontStretch(val);
            if (style.fontStretch != null)
               style.specifiedFlags |= Style.SPECIFIED_FONT_STRETCH;
            break;

         case text_decoration:
            style.textDecoration = parseTextDecoration(val);
            if (style.textDecoration != null)
               style.specifiedFlags |= Style.SPECIFIED_TEXT_DECORATION;
            break;

         case direction:
            style.direction = parseTextDirection(val);
            if (style.direction != null)
               style.specifiedFlags |= Style.SPECIFIED_DIRECTION;
            break;

         case text_anchor:
            style.textAnchor = parseTextAnchor(val);
            if (style.textAnchor != null)
               style.specifiedFlags |= Style.SPECIFIED_TEXT_ANCHOR;
            break;

         case overflow:
            style.overflow = parseOverflow(val);
            if (style.overflow != null)
               style.specifiedFlags |= Style.SPECIFIED_OVERFLOW;
            break;

         case marker:
            style.markerStart = parseFunctionalIRI(val, localName);
            style.markerMid = style.markerStart;
            style.markerEnd = style.markerStart;
            style.specifiedFlags |= (Style.SPECIFIED_MARKER_START | Style.SPECIFIED_MARKER_MID | Style.SPECIFIED_MARKER_END);
            break;

         case marker_start:
            style.markerStart = parseFunctionalIRI(val, localName);
            style.specifiedFlags |= Style.SPECIFIED_MARKER_START;
            break;

         case marker_mid:
            style.markerMid = parseFunctionalIRI(val, localName);
            style.specifiedFlags |= Style.SPECIFIED_MARKER_MID;
            break;

         case marker_end:
            style.markerEnd = parseFunctionalIRI(val, localName);
            style.specifiedFlags |= Style.SPECIFIED_MARKER_END;
            break;

         case display:
            if (val.indexOf('|') >= 0 || !VALID_DISPLAY_VALUES.contains('|'+val+'|'))
               break;
            style.display = !val.equals(NONE);
            style.specifiedFlags |= Style.SPECIFIED_DISPLAY;
            break;

         case visibility:
            if (val.indexOf('|') >= 0 || !VALID_VISIBILITY_VALUES.contains('|'+val+'|'))
               break;
            style.visibility = val.equals("visible");
            style.specifiedFlags |= Style.SPECIFIED_VISIBILITY;
            break;

         case stop_color:
            if (val.equals(CURRENTCOLOR)) {
               style.stopColor = CurrentColor.getInstance();
            } else {
               style.stopColor = parseColour(val);
            }
            style.specifiedFlags |= Style.SPECIFIED_STOP_COLOR;
            break;

         case stop_opacity:
            style.stopOpacity = parseOpacity(val);
            style.specifiedFlags |= Style.SPECIFIED_STOP_OPACITY;
            break;

         case clip:
            style.clip = parseClip(val);
            if (style.clip != null)
               style.specifiedFlags |= Style.SPECIFIED_CLIP;
            break;

         case clip_path:
            style.clipPath = parseFunctionalIRI(val, localName);
            style.specifiedFlags |= Style.SPECIFIED_CLIP_PATH;
            break;

         case clip_rule:
            style.clipRule = parseFillRule(val);
            style.specifiedFlags |= Style.SPECIFIED_CLIP_RULE;
            break;

         case mask:
            style.mask = parseFunctionalIRI(val, localName);
            style.specifiedFlags |= Style.SPECIFIED_MASK;
            break;

         case solid_color:
            // SVG 1.2 Tiny
            if (!isFromAttribute)
               break;
            if (val.equals(CURRENTCOLOR)) {
               style.solidColor = CurrentColor.getInstance();
            } else {
               style.solidColor = parseColour(val);
            }
            style.specifiedFlags |= Style.SPECIFIED_SOLID_COLOR;
            break;

         case solid_opacity:
            // SVG 1.2 Tiny
            if (!isFromAttribute)
               break;
            style.solidOpacity = parseOpacity(val);
            style.specifiedFlags |= Style.SPECIFIED_SOLID_OPACITY;
            break;

         case viewport_fill:
            // SVG 1.2 Tiny
            if (val.equals(CURRENTCOLOR)) {
               style.viewportFill = CurrentColor.getInstance();
            } else {
               style.viewportFill = parseColour(val);
            }
            style.specifiedFlags |= Style.SPECIFIED_VIEWPORT_FILL;
            break;

         case viewport_fill_opacity:
            // SVG 1.2 Tiny
            style.viewportFillOpacity = parseOpacity(val);
            style.specifiedFlags |= Style.SPECIFIED_VIEWPORT_FILL_OPACITY;
            break;

         case vector_effect:
            style.vectorEffect = parseVectorEffect(val);
            if (style.vectorEffect != null)
               style.specifiedFlags |= Style.SPECIFIED_VECTOR_EFFECT;
            break;

         case image_rendering:
            style.imageRendering = parseRenderQuality(val);
            if (style.imageRendering != null)
               style.specifiedFlags |= Style.SPECIFIED_IMAGE_RENDERING;
            break;

         case isolation:
            if (isFromAttribute)
               break;
            style.isolation = parseIsolation(val);
            if (style.isolation != null)
               style.specifiedFlags |= Style.SPECIFIED_ISOLATION;
            break;

         case mix_blend_mode:
            if (isFromAttribute)
               break;
            style.mixBlendMode = Style.CSSBlendMode.fromString(val);
            if (style.mixBlendMode != null)
               style.specifiedFlags |= Style.SPECIFIED_MIX_BLEND_MODE;
            break;

         case font_kerning:
            if (isFromAttribute)
               break;
            style.fontKerning = parseFontKerning(val);
            if (style.fontKerning != null)
               style.specifiedFlags |= Style.SPECIFIED_FONT_KERNING;
            break;

         case font_variant:
            if (isFromAttribute)
               break;
            parseFontVariant(style, val);
            break;

         case font_variant_ligatures:
            if (isFromAttribute)
               break;
            style.fontVariantLigatures = parseVariantLigatures(val);
            if (style.fontVariantLigatures != null)
               style.specifiedFlags |= Style.SPECIFIED_FONT_VARIANT_LIGATURES;
            break;

         case font_variant_position:
            if (isFromAttribute)
               break;
            style.fontVariantPosition = parseVariantPosition(val);
            if (style.fontVariantPosition != null)
               style.specifiedFlags |= Style.SPECIFIED_FONT_VARIANT_POSITION;
            break;

         case font_variant_caps:
            if (isFromAttribute)
               break;
            style.fontVariantCaps = parseVariantCaps(val);
            if (style.fontVariantCaps != null)
               style.specifiedFlags |= Style.SPECIFIED_FONT_VARIANT_CAPS;
            break;

         case font_variant_numeric:
            if (isFromAttribute)
               break;
            style.fontVariantNumeric = parseVariantNumeric(val);
            if (style.fontVariantNumeric != null)
               style.specifiedFlags |= Style.SPECIFIED_FONT_VARIANT_NUMERIC;
            break;

         case font_variant_east_asian:
            if (isFromAttribute)
               break;
            style.fontVariantEastAsian = parseEastAsian(val);
            if (style.fontVariantEastAsian != null)
               style.specifiedFlags |= Style.SPECIFIED_FONT_VARIANT_EAST_ASIAN;
            break;

         case font_feature_settings:
            if (isFromAttribute)
               break;
            style.fontFeatureSettings = parseFontFeatureSettings(val);
            if (style.fontFeatureSettings != null)
               style.specifiedFlags |= Style.SPECIFIED_FONT_FEATURE_SETTINGS;
            break;

         case font_variation_settings:
            if (isFromAttribute)
               break;
            style.fontVariationSettings = parseFontVariationSettings(val);
            if (style.fontVariationSettings != null)
               style.specifiedFlags |= Style.SPECIFIED_FONT_VARIATION_SETTINGS;
            break;

         case letter_spacing:
            style.letterSpacing = parseLetterOrWordSpacing(val);
            if (style.letterSpacing != null)
               style.specifiedFlags |= Style.SPECIFIED_LETTER_SPACING;
            break;

         case word_spacing:
            style.wordSpacing = parseLetterOrWordSpacing(val);
            if (style.wordSpacing != null)
               style.specifiedFlags |= Style.SPECIFIED_WORD_SPACING;
            break;

         case vertical_align:
            try {
               style.verticalAlignment = parseLength(val);
            } catch (SVGParseException ignored) {}
            if (style.verticalAlignment != null)
               style.specifiedFlags |= Style.SPECIFIED_VERTICAL_ALIGNMENT;
            break;


         /*
         case writing_mode:
            style.writingMode = WritingMode.fromString(val);
            if (style.writingMode != null)
               style.specifiedFlags |= SPECIFIED_WRITING_MODE;
            break;

         case glyph_orientation_vertical:
            style.glyphOrientationVertical = GlypOrientationVertical.fromString(val);
            if (style.glyphOrientationVertical != null)
               style.specifiedFlags |= SPECIFIED_GLYPH_ORIENTATION_VERTICAL;
            break;

         case text_orientation:
            if (isFromAttribute)
               break;
            style.textOrientation = TextOrientation.fromString(val);
            if (style.textOrientation != null)
               style.specifiedFlags |= SPECIFIED_TEXT_ORIENTATION;
            break;
         */

         default:
            break;
      }
   }

   //-----------------------------------------------------------------------------------------------
   // Parsing font-feature-settings property value

   /*
    * Parse the value of the CSS property "font-feature-settings".
    *
    * Format is: <feature-tag-value>[comma-wsp <feature-tag-value>]*
    *            <feature-tag-value> = <string> [ <integer> | on | off ]?
    */
   public static CSSFontFeatureSettings  parseFontFeatureSettings(String val)
   {
      CSSFontFeatureSettings  result = new CSSFontFeatureSettings();

      TextScanner scan = new TextScanner(val);
      scan.skipWhitespace();

      while (true) {
         if (scan.empty())
            break;
         FontFeatureEntry entry = nextFeatureEntry(scan);
         if (entry == null)
            return null;
         result.settings.put(entry.name, entry.val);
         scan.skipCommaWhitespace();
      }
      return result;
   }

   private static final String  FEATURE_ON  = "on";
   private static final String  FEATURE_OFF = "off";

   private static FontFeatureEntry nextFeatureEntry(TextScanner scan)
   {
      scan.skipWhitespace();
      String name = scan.nextQuotedString();
      if (name == null || name.length() != 4)
         return null;
      scan.skipWhitespace();
      int  val = 1;
      if (!scan.empty()) {
         Integer  num = scan.nextInteger(false);
         if (num == null) {
            if (scan.consume(FEATURE_OFF))
               val = 0;
            else
               scan.consume(FEATURE_ON);  // "on" == 1 == default, so consume quietly if it is present
         } else if (val > 99) {
            return null;
         } else {
            val = num;
         }
      }
      return new FontFeatureEntry(name, val);
   }


   //-----------------------------------------------------------------------------------------------


   // Parse a font-kerning keyword
   static Style.FontKerning  parseFontKerning(String val)
   {
      switch (val)
      {
         case FONT_VARIANT_AUTO:   return Style.FontKerning.auto;
         case FONT_VARIANT_NORMAL: return Style.FontKerning.normal;
         case FONT_VARIANT_NONE:   return Style.FontKerning.none;
         default:                  return null;
      }
   }

   /*
    * Parse a font-variant-ligatures property
    * Format:
    *   normal | none | [ <common-lig-values> || <discretionary-lig-values> || <historical-lig-values> || <contextual-alt-values> ]
    *   <common-lig-values>        = [ common-ligatures | no-common-ligatures ]
    *   <discretionary-lig-values> = [ discretionary-ligatures | no-discretionary-ligatures ]
    *   <historical-lig-values>    = [ historical-ligatures | no-historical-ligatures ]
    *   <contextual-alt-values>    = [ contextual | no-contextual ]
    */
   static CSSFontFeatureSettings  parseVariantLigatures(String val)
   {
      if (val.equals(FONT_VARIANT_NORMAL))
         return LIGATURES_NORMAL;
      else if (val.equals(FONT_VARIANT_NONE)) {
         ensureLigaturesNone();
         return LIGATURES_ALL_OFF;
      }

      List<String>  tokens = extractTokensAsList(val);
      if (tokens == null)  // No tokens found
         return null;

      ensureLigaturesNone();
      CSSFontFeatureSettings  result = parseVariantLigaturesSpecial(tokens);

      // If nothing found, or duplicate keywords found, or tokens left over, then we have an error
      if (result == null || result == CSSFontFeatureSettings.ERROR || tokens.size() > 0)
         return null;

      return result;
   }


   private static CSSFontFeatureSettings  parseVariantLigaturesSpecial(List<String> tokens)
   {
      ensureLigaturesNone();
      CSSFontFeatureSettings  result = new CSSFontFeatureSettings(LIGATURES_ALL_OFF);
      boolean                 found = false;

      switch (containsWhich(tokens, FONT_VARIANT_COMMON_LIGATURES, FONT_VARIANT_NO_COMMON_LIGATURES))
      {
         case 1: result.addSettings(FEATURE_CLIG, FEATURE_LIGA, VALUE_ON); found = true; break;
         case 2: result.addSettings(FEATURE_CLIG, FEATURE_LIGA, VALUE_OFF); found = true; break;
         case 3: return CSSFontFeatureSettings.ERROR;
      }

      switch (containsWhich(tokens, FONT_VARIANT_DISCRETIONARY_LIGATURES, FONT_VARIANT_NO_DISCRETIONARY_LIGATURES))
      {
         case 1: result.settings.put(FEATURE_DLIG, VALUE_ON); found = true; break;
         case 2: result.settings.put(FEATURE_DLIG, VALUE_OFF); found = true; break;
         case 3: return CSSFontFeatureSettings.ERROR;
      }

      switch (containsWhich(tokens, FONT_VARIANT_HISTORICAL_LIGATURES, FONT_VARIANT_NO_HISTORICAL_LIGATURES))
      {
         case 1: result.settings.put(FEATURE_HLIG, VALUE_ON); found = true; break;
         case 2: result.settings.put(FEATURE_HLIG, VALUE_OFF); found = true; break;
         case 3: return CSSFontFeatureSettings.ERROR;
      }

      switch (containsWhich(tokens, FONT_VARIANT_CONTEXTUAL_LIGATURES, FONT_VARIANT_NO_CONTEXTUAL_LIGATURES))
      {
         case 1: result.settings.put(FEATURE_CALT, VALUE_ON); found = true; break;
         case 2: result.settings.put(FEATURE_CALT, VALUE_OFF); found = true; break;
         case 3: return CSSFontFeatureSettings.ERROR;
      }
      return found ? result : null;
   }


   // Parse a font-kerning property
   static CSSFontFeatureSettings  parseVariantPosition(String val)
   {
      if (val.equals(FONT_VARIANT_NORMAL))
         return POSITION_ALL_OFF;

      CSSFontFeatureSettings  result = new CSSFontFeatureSettings(POSITION_ALL_OFF);
      switch (val)
      {
         case FONT_VARIANT_SUB:    result.settings.put(FEATURE_SUBS, VALUE_ON); break;
         case FONT_VARIANT_SUPER:  result.settings.put(FEATURE_SUPS, VALUE_ON); break;
         default:                  return null;
      }
      return result;
   }


   // Used only by parseFontVariant()
   // Only looks for the values unique to this property
   private static CSSFontFeatureSettings  parseVariantPositionSpecial(List<String> tokens)
   {
      CSSFontFeatureSettings  result = new CSSFontFeatureSettings(POSITION_ALL_OFF);
      boolean                 found = false;

      switch (containsWhich(tokens, FONT_VARIANT_SUB, FONT_VARIANT_SUPER))
      {
         case 1: result.settings.put(FEATURE_SUBS, VALUE_ON); found = true; break;
         case 2: result.settings.put(FEATURE_SUPS, VALUE_ON); found = true; break;
         case 3: return CSSFontFeatureSettings.ERROR;
      }
      return found ? result : null;
   }


   // Parse a font-variant-caps property
   static CSSFontFeatureSettings  parseVariantCaps(String val)
   {
      if (val.equals(FONT_VARIANT_NORMAL))
         return CAPS_ALL_OFF;

      CSSFontFeatureSettings  result = new CSSFontFeatureSettings(CAPS_ALL_OFF);
      return setCapsFeature(result, val) ? result : null;
   }

   private static boolean  setCapsFeature(CSSFontFeatureSettings result, String val)
   {
      switch (val)
      {
         case FONT_VARIANT_SMALL_CAPS:      result.settings.put(FEATURE_SMCP, VALUE_ON); break;
         case FONT_VARIANT_ALL_SMALL_CAPS:  result.addSettings(FEATURE_SMCP, FEATURE_C2SC, VALUE_ON); break;
         case FONT_VARIANT_PETITE_CAPS:     result.settings.put(FEATURE_PCAP, VALUE_ON); break;
         case FONT_VARIANT_ALL_PETITE_CAPS: result.addSettings(FEATURE_PCAP, FEATURE_C2PC, VALUE_ON); break;
         case FONT_VARIANT_UNICASE:         result.settings.put(FEATURE_UNIC, VALUE_ON); break;
         case FONT_VARIANT_TITLING_CAPS:    result.settings.put(FEATURE_TITL, VALUE_ON); break;
         default:                           return false;
      }
      return true;
   }


   // Used only by parseFontVariant()
   // Only looks for the values unique to this property
   private static CSSFontFeatureSettings  parseVariantCapsSpecial(List<String> tokens)
   {
      CSSFontFeatureSettings  result = new CSSFontFeatureSettings(CAPS_ALL_OFF);

      String which = containsOneOf(tokens, FONT_VARIANT_SMALL_CAPS, FONT_VARIANT_ALL_SMALL_CAPS, FONT_VARIANT_PETITE_CAPS,
              FONT_VARIANT_ALL_PETITE_CAPS, FONT_VARIANT_UNICASE, FONT_VARIANT_TITLING_CAPS);
      if (which == TOKEN_ERROR)
         return CSSFontFeatureSettings.ERROR;
      if (which == null)
         return null;

      setCapsFeature(result, which);
      return result;
   }




   private static List<String>  extractTokensAsList(String val)
   {
      TextScanner  scan = new TextScanner(val);
      scan.skipWhitespace();
      if (scan.empty())
         return null;
      ArrayList<String>  result = new ArrayList<>();
      while (!scan.empty())
      {
         result.add(scan.nextToken());
         scan.skipWhitespace();
      }
      return result;
   }


   /*
    * Returns:
    *   1 if token list contains token1,
    *   2 if it contains token2,
    *   3 if it contains both, or more than one of either,
    *   0 if it contains neither.
    */
   private static int  containsWhich(List<String> tokens, String token1, String token2)
   {
      if (tokens.remove(token1)) {
         return tokens.contains(token1) || tokens.contains(token2) ? 3 : 1;
      } else if (tokens.remove(token2)) {
         return tokens.contains(token2) ? 3 : 2;
      }
      return 0;
   }


   /*
    * Returns:
    *   1 if token list contains token1,
    *   2 if it contains more than one token1,
    *   0 if it doesn't contains token1.
    */
   private static int  containsOnce(List<String> tokens, String token1)
   {
      if (tokens.remove(token1)) {
         return tokens.contains(token1) ? 2 : 1;
      }
      return 0;
   }

   /*
    * Checks haystack to see which needle is present (if any).  Returns the needle.
    * If there is more than one of the needles present, then returns null.
    */
   private static String  containsOneOf(List<String> haystack, String... needles)
   {
      String found = null;
      for (String needle: needles)
      {
         if (found == null && haystack.remove(needle)) {
            found = needle;
         }
         if (haystack.contains(needle))
            return TOKEN_ERROR;
      }
      return found;
   }

   /*
    * Parse a font-variant-numeric property
    * Format:
    *   normal | [ <numeric-figure-values> || <numeric-spacing-values> || <numeric-fraction-values> || ordinal || slashed-zero ]
    *   <numeric-figure-values>   = [ lining-nums | oldstyle-nums ]
    *   <numeric-spacing-values>  = [ proportional-nums | tabular-nums ]
    *   <numeric-fraction-values> = [ diagonal-fractions | stacked-fractions ]
    */
   static CSSFontFeatureSettings  parseVariantNumeric(String val)
   {
      if (val.equals(FONT_VARIANT_NORMAL))
         return NUMERIC_ALL_OFF;

      List<String>  tokens = extractTokensAsList(val);
      if (tokens == null)
         return null;

      CSSFontFeatureSettings  result = parseVariantNumericSpecial(tokens);

      // If nothing found, or duplicate keywords found, or tokens left over, then we have an error
      if (result == null || result == CSSFontFeatureSettings.ERROR || tokens.size() > 0)
         return null;

      return result;
   }


   private static CSSFontFeatureSettings  parseVariantNumericSpecial(List<String> tokens)
   {
      CSSFontFeatureSettings  result = new CSSFontFeatureSettings(NUMERIC_ALL_OFF);
      boolean                 found = false;

      switch (containsWhich(tokens, FONT_VARIANT_LINING_NUMS, FONT_VARIANT_OLDSTYLE_NUMS))
      {
         case 1: result.settings.put(FEATURE_LNUM, VALUE_ON); found = true; break;
         case 2: result.settings.put(FEATURE_ONUM, VALUE_ON); found = true; break;
         case 3: return CSSFontFeatureSettings.ERROR;
      }

      switch (containsWhich(tokens, FONT_VARIANT_PROPORTIONAL_NUMS, FONT_VARIANT_TABULAR_NUMS))
      {
         case 1: result.settings.put(FEATURE_PNUM, VALUE_ON); found = true; break;
         case 2: result.settings.put(FEATURE_TNUM, VALUE_ON); found = true; break;
         case 3: return CSSFontFeatureSettings.ERROR;
      }

      switch (containsWhich(tokens, FONT_VARIANT_DIAGONAL_FRACTIONS, FONT_VARIANT_STACKED_FRACTIONS))
      {
         case 1: result.settings.put(FEATURE_FRAC, VALUE_ON); found = true; break;
         case 2: result.settings.put(FEATURE_AFRC, VALUE_ON); found = true; break;
         case 3: return CSSFontFeatureSettings.ERROR;
      }

      switch (containsOnce(tokens, FONT_VARIANT_ORDINAL))
      {
         case 1: result.settings.put(FEATURE_ORDN, VALUE_ON); found = true; break;
         case 2: return CSSFontFeatureSettings.ERROR;
      }

      switch (containsOnce(tokens, FONT_VARIANT_SLASHED_ZERO))
      {
         case 1: result.settings.put(FEATURE_ZERO, VALUE_ON); found = true; break;
         case 2: return CSSFontFeatureSettings.ERROR;
      }

      return found ? result : null;
   }


   /*
    * Parse a font-variant-east-asian property
    * Format:
    *   normal | [ <east-asian-variant-values> || <east-asian-width-values> || ruby ]
    *   <east-asian-variant-values> = [ jis78 | jis83 | jis90 | jis04 | simplified | traditional ]
    *   <east-asian-width-values>   = [ full-width | proportional-width ]
    */
   static CSSFontFeatureSettings  parseEastAsian(String val)
   {
      if (val.equals(FONT_VARIANT_NORMAL))
         return EAST_ASIAN_ALL_OFF;

      List<String>  tokens = extractTokensAsList(val);
      if (tokens == null)
         return null;

      CSSFontFeatureSettings  result = parseVariantEastAsianSpecial(tokens);

      // If nothing found, or duplicate keywords found, or tokens left over, then we have an error
      if (result == null || result == CSSFontFeatureSettings.ERROR || tokens.size() > 0)
         return null;

      return result;
   }


   private static CSSFontFeatureSettings  parseVariantEastAsianSpecial(List<String> tokens)
   {
      CSSFontFeatureSettings  result = new CSSFontFeatureSettings(EAST_ASIAN_ALL_OFF);
      boolean                 found = false;

      String which = containsOneOf(tokens, FONT_VARIANT_JIS78, FONT_VARIANT_JIS83, FONT_VARIANT_JIS90,
              FONT_VARIANT_JIS04, FONT_VARIANT_SIMPLIFIED, FONT_VARIANT_TRADITIONAL);
      if (which != null)
      {
         switch (which)
         {
            case FONT_VARIANT_JIS78:       result.settings.put(FEATURE_JP78, VALUE_ON); break;
            case FONT_VARIANT_JIS83:       result.settings.put(FEATURE_JP83, VALUE_ON); break;
            case FONT_VARIANT_JIS90:       result.settings.put(FEATURE_JP90, VALUE_ON); break;
            case FONT_VARIANT_JIS04:       result.settings.put(FEATURE_JP04, VALUE_ON); break;
            case FONT_VARIANT_SIMPLIFIED:  result.settings.put(FEATURE_SMPL, VALUE_ON); break;
            case FONT_VARIANT_TRADITIONAL: result.settings.put(FEATURE_TRAD, VALUE_ON); break;
            case TOKEN_ERROR:              return CSSFontFeatureSettings.ERROR;  // more than one, or duplicate, found
         }
         found = true;
      }

      switch (containsWhich(tokens, FONT_VARIANT_FULL_WIDTH, FONT_VARIANT_PROPORTIONAL_WIDTH))
      {
         case 1: result.settings.put(FEATURE_FWID, VALUE_ON); found = true; break;
         case 2: result.settings.put(FEATURE_PWID, VALUE_ON); found = true; break;
         case 3: return CSSFontFeatureSettings.ERROR;
      }

      switch (containsOnce(tokens, FONT_VARIANT_RUBY))
      {
         case 1: result.settings.put(FEATURE_RUBY, VALUE_ON); found = true; break;
         case 2: return CSSFontFeatureSettings.ERROR;
      }

      return found ? result : null;
   }


   //-----------------------------------------------------------------------------------------------

   static void parseFontVariant(Style style, String val)
   {
      if (val.equals(FONT_VARIANT_NORMAL))
      {
         style.fontVariantLigatures = LIGATURES_NORMAL;
         style.fontVariantPosition = POSITION_ALL_OFF;
         style.fontVariantCaps = CAPS_ALL_OFF;
         style.fontVariantNumeric = NUMERIC_ALL_OFF;
         style.fontVariantEastAsian = EAST_ASIAN_ALL_OFF;
         style.specifiedFlags |= (Style.SPECIFIED_FONT_VARIANT_LIGATURES | Style.SPECIFIED_FONT_VARIANT_POSITION |
                 Style.SPECIFIED_FONT_VARIANT_CAPS | Style.SPECIFIED_FONT_VARIANT_NUMERIC |
                 Style.SPECIFIED_FONT_VARIANT_EAST_ASIAN);
         return;
      }
      else if (val.equals(FONT_VARIANT_NONE))
      {
         ensureLigaturesNone();
         style.fontVariantLigatures = LIGATURES_ALL_OFF;
         style.fontVariantPosition = POSITION_ALL_OFF;
         style.fontVariantCaps = CAPS_ALL_OFF;
         style.fontVariantNumeric = NUMERIC_ALL_OFF;
         style.fontVariantEastAsian = EAST_ASIAN_ALL_OFF;
         style.specifiedFlags |= (Style.SPECIFIED_FONT_VARIANT_LIGATURES | Style.SPECIFIED_FONT_VARIANT_POSITION |
                 Style.SPECIFIED_FONT_VARIANT_CAPS | Style.SPECIFIED_FONT_VARIANT_NUMERIC |
                 Style.SPECIFIED_FONT_VARIANT_EAST_ASIAN);
         return;
      }

      List<String>  tokens = extractTokensAsList(val);
      if (tokens == null)
         return;

      CSSFontFeatureSettings  ligatures = parseVariantLigaturesSpecial(tokens);
      if (ligatures == CSSFontFeatureSettings.ERROR)
         return;

      CSSFontFeatureSettings  position = null;
      if (tokens.size() > 0) {
         position = parseVariantPositionSpecial(tokens);
         if (position == CSSFontFeatureSettings.ERROR)
            return;
      }

      CSSFontFeatureSettings  caps = null;
      if (tokens.size() > 0) {
         caps = parseVariantCapsSpecial(tokens);
         if (caps == CSSFontFeatureSettings.ERROR)
            return;
      }

      CSSFontFeatureSettings  numeric = null;
      if (tokens.size() > 0) {
         numeric = parseVariantNumericSpecial(tokens);
         if (numeric == CSSFontFeatureSettings.ERROR)
            return;
      }

      CSSFontFeatureSettings  eastAsian = null;
      if (tokens.size() > 0) {
         eastAsian = parseVariantEastAsianSpecial(tokens);
         if (eastAsian == CSSFontFeatureSettings.ERROR)
            return;
      }

      //if (tokens.size() > 0)  // Tokens left over in line?
      // Ignore them, as they may be CSS Fonts 4 keywords, for example.

      // We found some good keywords in this value
      if (ligatures != null) {
         style.fontVariantLigatures = ligatures;
         style.specifiedFlags |= Style.SPECIFIED_FONT_VARIANT_LIGATURES;
      }

      if (position != null) {
         style.fontVariantPosition = position;
         style.specifiedFlags |= Style.SPECIFIED_FONT_VARIANT_POSITION;
      }

      if (caps != null) {
         style.fontVariantCaps = caps;
         style.specifiedFlags |= Style.SPECIFIED_FONT_VARIANT_CAPS;
      }

      if (numeric != null) {
         style.fontVariantNumeric = numeric;
         style.specifiedFlags |= Style.SPECIFIED_FONT_VARIANT_NUMERIC;
      }

      if (eastAsian != null) {
         style.fontVariantEastAsian = eastAsian;
         style.specifiedFlags |= Style.SPECIFIED_FONT_VARIANT_EAST_ASIAN;
      }
   }

   private static void  ensureLigaturesNone()
   {
      // all ligatures OFF
      if (LIGATURES_ALL_OFF != null)
         return;
      CSSFontFeatureSettings result = new CSSFontFeatureSettings();
      result.settings.put("liga", VALUE_OFF);
      result.settings.put("clig", VALUE_OFF);
      result.settings.put("dlig", VALUE_OFF);
      result.settings.put("hlig", VALUE_OFF);
      result.settings.put("calt", VALUE_OFF);
      LIGATURES_ALL_OFF = result;
   }

   //-----------------------------------------------------------------------------------------------

   //-----------------------------------------------------------------------------------------------
   // Parsing font-variation-settings property value

   /*
    * Parse the value of the CSS property "font-variation-settings".
    *
    * Format is: normal | [ <string> <number>]#
    */
   private static CSSFontVariationSettings  parseFontVariationSettings(String val)
   {
      CSSFontVariationSettings  result = new CSSFontVariationSettings();

      TextScanner scan = new TextScanner(val);
      scan.skipWhitespace();

      if (scan.consume(CSSFontVariationSettings.NORMAL))
         return null;

      while (true) {
         if (scan.empty())
            break;
         FontVariationEntry entry = nextFontFeatureEntry(scan);
         if (entry == null)
            return null;
         result.settings.put(entry.name, entry.val);
         scan.skipCommaWhitespace();
      }
      return result;
   }

   private static FontVariationEntry nextFontFeatureEntry(TextScanner scan)
   {
      scan.skipWhitespace();
      String name = scan.nextQuotedString();
      if (name == null || name.length() != 4)
         return null;
      scan.skipWhitespace();
      if (scan.empty())
         return null;
      Float  num = scan.nextFloat();
      if (num == null)
         return null;
      return new FontVariationEntry(name, num);
   }

   private static class FontFeatureEntry {
      String  name;
      int     val;

      public FontFeatureEntry(String name, int val) {
         this.name = name;
         this.val = val;
      }
   }

   private static class FontVariationEntry {
      String  name;
      Float   val;

      public FontVariationEntry(String name, Float val) {
         this.name = name;
         this.val = val;
      }
   }

   // Define SVG tags
    public enum  SVGElem
    {
       svg,
       a,
       circle,
       clipPath,
       defs,
       desc,
       ellipse,
       g,
       image,
       line,
       linearGradient,
       marker,
       mask,
       path,
       pattern,
       polygon,
       polyline,
       radialGradient,
       rect,
       solidColor,
       stop,
       style,
       SWITCH,
       symbol,
       text,
       textPath,
       title,
       tref,
       tspan,
       use,
       view,
       UNSUPPORTED;

       private static final Map<String, SVGElem> cache = new HashMap<>();

       static {
          for (SVGElem elem : values()) {
             if (elem == SWITCH) {
                cache.put("switch", elem);
             } else if (elem != UNSUPPORTED) {
                final String key = elem.name();
                cache.put(key, elem);
             }
          }
       }

       public static SVGElem fromString(String str) {
          // First check cache to see if it is there
          SVGElem elem = cache.get(str);
          if (elem != null) {
             return elem;
          }

          return UNSUPPORTED;
       }
    }

    // Supported SVG attributes
    public enum  SVGAttr
    {
       CLASS,    // Upper case because 'class' is a reserved word. Handled as a special case.
       clip,
       clip_path,
       clipPathUnits,
       clip_rule,
       color,
       cx, cy,
       direction,
       dx, dy,
       fx, fy,
       d,
       display,
       fill,
       fill_rule,
       fill_opacity,
       font,
       font_family,
       font_feature_settings,
       font_size,
       font_stretch,                // @since 1.5
       font_style,
       font_weight,
       // font_size_adjust, font_stretch,
       font_kerning,                // @since 1.5
       font_variant,                // @since 1.5
       font_variant_ligatures,      // @since 1.5
       font_variant_position,       // @since 1.5
       font_variant_caps,           // @since 1.5
       font_variant_numeric,        // @since 1.5
       font_variant_east_asian,     // @since 1.5
       font_variation_settings,     // @since 1.5
       glyph_orientation_vertical,  // @since 1.5
       gradientTransform,
       gradientUnits,
       height,
       href,
       // id,
       image_rendering,
       isolation,       // @since 1.5
       letter_spacing,  // @since 1.5
       marker,
       marker_start, marker_mid, marker_end,
       markerHeight, markerUnits, markerWidth,
       mask,
       maskContentUnits, maskUnits,
       media,
       vertical_align,
       mix_blend_mode,  // @since 1.5
       offset,
       opacity,
       orient,
       overflow,
       pathLength,
       patternContentUnits, patternTransform, patternUnits,
       points,
       preserveAspectRatio,
       r,
       refX,
       refY,
       requiredFeatures, requiredExtensions, requiredFormats, requiredFonts,
       rx, ry,
       solid_color, solid_opacity,
       spreadMethod,
       startOffset,
       stop_color, stop_opacity,
       stroke,
       stroke_dasharray,
       stroke_dashoffset,
       stroke_linecap,
       stroke_linejoin,
       stroke_miterlimit,
       stroke_opacity,
       stroke_width,
       style,
       systemLanguage,
       text_anchor,
       text_decoration,
       text_orientation,  // @since 1.5
       transform,
       type,
       vector_effect,
       version,
       viewBox,
       width,
       word_spacing,  // @since 1.5
       writing_mode,  // @since 1.5
       x, y,
       x1, y1,
       x2, y2,
       viewport_fill, viewport_fill_opacity,
       visibility,
       UNSUPPORTED;

       private static final Map<String, SVGAttr> cache = new HashMap<>();

       static {
          for (SVGAttr attr : values()) {
             if (attr == CLASS) {
                cache.put("class", attr);
             } else if (attr != UNSUPPORTED) {
                final String key = attr.name().replace('_', '-');
                cache.put(key, attr);
             }
          }
       }

       public static SVGAttr fromString(String str)
       {
          // First check cache to see if it is there
          SVGAttr attr = cache.get(str);
          if (attr != null) {
             return attr;
          }

          return UNSUPPORTED;
       }
    }

    // These static inner classes are only loaded/initialized when first used and are thread safe
    public static class ColourKeywords {
       private static final Map<String, Integer> colourKeywords = new HashMap<>(47);
       static {
          colourKeywords.put("aliceblue", 0xfff0f8ff);
          colourKeywords.put("antiquewhite", 0xfffaebd7);
          colourKeywords.put("aqua", 0xff00ffff);
          colourKeywords.put("aquamarine", 0xff7fffd4);
          colourKeywords.put("azure", 0xfff0ffff);
          colourKeywords.put("beige", 0xfff5f5dc);
          colourKeywords.put("bisque", 0xffffe4c4);
          colourKeywords.put("black", 0xff000000);
          colourKeywords.put("blanchedalmond", 0xffffebcd);
          colourKeywords.put("blue", 0xff0000ff);
          colourKeywords.put("blueviolet", 0xff8a2be2);
          colourKeywords.put("brown", 0xffa52a2a);
          colourKeywords.put("burlywood", 0xffdeb887);
          colourKeywords.put("cadetblue", 0xff5f9ea0);
          colourKeywords.put("chartreuse", 0xff7fff00);
          colourKeywords.put("chocolate", 0xffd2691e);
          colourKeywords.put("coral", 0xffff7f50);
          colourKeywords.put("cornflowerblue", 0xff6495ed);
          colourKeywords.put("cornsilk", 0xfffff8dc);
          colourKeywords.put("crimson", 0xffdc143c);
          colourKeywords.put("cyan", 0xff00ffff);
          colourKeywords.put("darkblue", 0xff00008b);
          colourKeywords.put("darkcyan", 0xff008b8b);
          colourKeywords.put("darkgoldenrod", 0xffb8860b);
          colourKeywords.put("darkgray", 0xffa9a9a9);
          colourKeywords.put("darkgreen", 0xff006400);
          colourKeywords.put("darkgrey", 0xffa9a9a9);
          colourKeywords.put("darkkhaki", 0xffbdb76b);
          colourKeywords.put("darkmagenta", 0xff8b008b);
          colourKeywords.put("darkolivegreen", 0xff556b2f);
          colourKeywords.put("darkorange", 0xffff8c00);
          colourKeywords.put("darkorchid", 0xff9932cc);
          colourKeywords.put("darkred", 0xff8b0000);
          colourKeywords.put("darksalmon", 0xffe9967a);
          colourKeywords.put("darkseagreen", 0xff8fbc8f);
          colourKeywords.put("darkslateblue", 0xff483d8b);
          colourKeywords.put("darkslategray", 0xff2f4f4f);
          colourKeywords.put("darkslategrey", 0xff2f4f4f);
          colourKeywords.put("darkturquoise", 0xff00ced1);
          colourKeywords.put("darkviolet", 0xff9400d3);
          colourKeywords.put("deeppink", 0xffff1493);
          colourKeywords.put("deepskyblue", 0xff00bfff);
          colourKeywords.put("dimgray", 0xff696969);
          colourKeywords.put("dimgrey", 0xff696969);
          colourKeywords.put("dodgerblue", 0xff1e90ff);
          colourKeywords.put("firebrick", 0xffb22222);
          colourKeywords.put("floralwhite", 0xfffffaf0);
          colourKeywords.put("forestgreen", 0xff228b22);
          colourKeywords.put("fuchsia", 0xffff00ff);
          colourKeywords.put("gainsboro", 0xffdcdcdc);
          colourKeywords.put("ghostwhite", 0xfff8f8ff);
          colourKeywords.put("gold", 0xffffd700);
          colourKeywords.put("goldenrod", 0xffdaa520);
          colourKeywords.put("gray", 0xff808080);
          colourKeywords.put("green", 0xff008000);
          colourKeywords.put("greenyellow", 0xffadff2f);
          colourKeywords.put("grey", 0xff808080);
          colourKeywords.put("honeydew", 0xfff0fff0);
          colourKeywords.put("hotpink", 0xffff69b4);
          colourKeywords.put("indianred", 0xffcd5c5c);
          colourKeywords.put("indigo", 0xff4b0082);
          colourKeywords.put("ivory", 0xfffffff0);
          colourKeywords.put("khaki", 0xfff0e68c);
          colourKeywords.put("lavender", 0xffe6e6fa);
          colourKeywords.put("lavenderblush", 0xfffff0f5);
          colourKeywords.put("lawngreen", 0xff7cfc00);
          colourKeywords.put("lemonchiffon", 0xfffffacd);
          colourKeywords.put("lightblue", 0xffadd8e6);
          colourKeywords.put("lightcoral", 0xfff08080);
          colourKeywords.put("lightcyan", 0xffe0ffff);
          colourKeywords.put("lightgoldenrodyellow", 0xfffafad2);
          colourKeywords.put("lightgray", 0xffd3d3d3);
          colourKeywords.put("lightgreen", 0xff90ee90);
          colourKeywords.put("lightgrey", 0xffd3d3d3);
          colourKeywords.put("lightpink", 0xffffb6c1);
          colourKeywords.put("lightsalmon", 0xffffa07a);
          colourKeywords.put("lightseagreen", 0xff20b2aa);
          colourKeywords.put("lightskyblue", 0xff87cefa);
          colourKeywords.put("lightslategray", 0xff778899);
          colourKeywords.put("lightslategrey", 0xff778899);
          colourKeywords.put("lightsteelblue", 0xffb0c4de);
          colourKeywords.put("lightyellow", 0xffffffe0);
          colourKeywords.put("lime", 0xff00ff00);
          colourKeywords.put("limegreen", 0xff32cd32);
          colourKeywords.put("linen", 0xfffaf0e6);
          colourKeywords.put("magenta", 0xffff00ff);
          colourKeywords.put("maroon", 0xff800000);
          colourKeywords.put("mediumaquamarine", 0xff66cdaa);
          colourKeywords.put("mediumblue", 0xff0000cd);
          colourKeywords.put("mediumorchid", 0xffba55d3);
          colourKeywords.put("mediumpurple", 0xff9370db);
          colourKeywords.put("mediumseagreen", 0xff3cb371);
          colourKeywords.put("mediumslateblue", 0xff7b68ee);
          colourKeywords.put("mediumspringgreen", 0xff00fa9a);
          colourKeywords.put("mediumturquoise", 0xff48d1cc);
          colourKeywords.put("mediumvioletred", 0xffc71585);
          colourKeywords.put("midnightblue", 0xff191970);
          colourKeywords.put("mintcream", 0xfff5fffa);
          colourKeywords.put("mistyrose", 0xffffe4e1);
          colourKeywords.put("moccasin", 0xffffe4b5);
          colourKeywords.put("navajowhite", 0xffffdead);
          colourKeywords.put("navy", 0xff000080);
          colourKeywords.put("oldlace", 0xfffdf5e6);
          colourKeywords.put("olive", 0xff808000);
          colourKeywords.put("olivedrab", 0xff6b8e23);
          colourKeywords.put("orange", 0xffffa500);
          colourKeywords.put("orangered", 0xffff4500);
          colourKeywords.put("orchid", 0xffda70d6);
          colourKeywords.put("palegoldenrod", 0xffeee8aa);
          colourKeywords.put("palegreen", 0xff98fb98);
          colourKeywords.put("paleturquoise", 0xffafeeee);
          colourKeywords.put("palevioletred", 0xffdb7093);
          colourKeywords.put("papayawhip", 0xffffefd5);
          colourKeywords.put("peachpuff", 0xffffdab9);
          colourKeywords.put("peru", 0xffcd853f);
          colourKeywords.put("pink", 0xffffc0cb);
          colourKeywords.put("plum", 0xffdda0dd);
          colourKeywords.put("powderblue", 0xffb0e0e6);
          colourKeywords.put("purple", 0xff800080);
          colourKeywords.put("rebeccapurple", 0xff663399);
          colourKeywords.put("red", 0xffff0000);
          colourKeywords.put("rosybrown", 0xffbc8f8f);
          colourKeywords.put("royalblue", 0xff4169e1);
          colourKeywords.put("saddlebrown", 0xff8b4513);
          colourKeywords.put("salmon", 0xfffa8072);
          colourKeywords.put("sandybrown", 0xfff4a460);
          colourKeywords.put("seagreen", 0xff2e8b57);
          colourKeywords.put("seashell", 0xfffff5ee);
          colourKeywords.put("sienna", 0xffa0522d);
          colourKeywords.put("silver", 0xffc0c0c0);
          colourKeywords.put("skyblue", 0xff87ceeb);
          colourKeywords.put("slateblue", 0xff6a5acd);
          colourKeywords.put("slategray", 0xff708090);
          colourKeywords.put("slategrey", 0xff708090);
          colourKeywords.put("snow", 0xfffffafa);
          colourKeywords.put("springgreen", 0xff00ff7f);
          colourKeywords.put("steelblue", 0xff4682b4);
          colourKeywords.put("tan", 0xffd2b48c);
          colourKeywords.put("teal", 0xff008080);
          colourKeywords.put("thistle", 0xffd8bfd8);
          colourKeywords.put("tomato", 0xffff6347);
          colourKeywords.put("turquoise", 0xff40e0d0);
          colourKeywords.put("violet", 0xffee82ee);
          colourKeywords.put("wheat", 0xfff5deb3);
          colourKeywords.put("white", 0xffffffff);
          colourKeywords.put("whitesmoke", 0xfff5f5f5);
          colourKeywords.put("yellow", 0xffffff00);
          colourKeywords.put("yellowgreen", 0xff9acd32);
          colourKeywords.put("transparent", 0x00000000);
       }

       static Integer get(String colourName) {
          return colourKeywords.get(colourName);
       }
    }

    public static class FontSizeKeywords {
       private static final Map<String, Length> fontSizeKeywords = new HashMap<>(9);
       static {
          fontSizeKeywords.put("xx-small", new Length(0.694f, Unit.pt));
          fontSizeKeywords.put("x-small", new Length(0.833f, Unit.pt));
          fontSizeKeywords.put("small", new Length(10.0f, Unit.pt));
          fontSizeKeywords.put("medium", new Length(12.0f, Unit.pt));
          fontSizeKeywords.put("large", new Length(14.4f, Unit.pt));
          fontSizeKeywords.put("x-large", new Length(17.3f, Unit.pt));
          fontSizeKeywords.put("xx-large", new Length(20.7f, Unit.pt));
          fontSizeKeywords.put("smaller", new Length(83.33f, Unit.percent));
          fontSizeKeywords.put("larger", new Length(120f, Unit.percent));
       }

       static Length get(String fontSize) {
          return fontSizeKeywords.get(fontSize);
       }
    }

    public static class FontWeightKeywords {
       private static final Map<String, Float> fontWeightKeywords = new HashMap<>(4);
       static {
          fontWeightKeywords.put("normal", Style.FONT_WEIGHT_NORMAL);
          fontWeightKeywords.put("bold", Style.FONT_WEIGHT_BOLD);
          fontWeightKeywords.put("bolder", Style.FONT_WEIGHT_BOLDER);
          fontWeightKeywords.put("lighter", Style.FONT_WEIGHT_LIGHTER);
       }

       static Float get(String fontWeight) {
          return fontWeightKeywords.get(fontWeight);
       }

       static boolean contains(String fontStretch) {
          return fontWeightKeywords.containsKey(fontStretch);
       }
    }

    public static class FontStretchKeywords {
       private static final Map<String, Float> fontStretchKeywords = new HashMap<>(9);
       static {
          fontStretchKeywords.put("ultra-condensed", 50f);
          fontStretchKeywords.put("extra-condensed", 62.5f);
          fontStretchKeywords.put("condensed", 75f);
          fontStretchKeywords.put("semi-condensed", 87.5f);
          fontStretchKeywords.put("normal", Style.FONT_STRETCH_NORMAL);
          fontStretchKeywords.put("semi-expanded", 112.5f);
          fontStretchKeywords.put("expanded", 125f);
          fontStretchKeywords.put("extra-expanded", 150f);
          fontStretchKeywords.put("ultra-expanded", 200f);
       }

       static Float get(String fontStretch) {
          return fontStretchKeywords.get(fontStretch);
       }

       static boolean contains(String fontStretch) {
          return fontStretchKeywords.containsKey(fontStretch);
       }
    }

    public static Map<String,String> parseProcessingInstructionAttributes(TextScanner scan)
    {
        HashMap<String, String> attributes = new HashMap<>();

        scan.skipWhitespace();
        String  attrName = scan.nextToken('=');
        while (attrName != null)
        {
            scan.consume('=');
            String value = scan.nextQuotedString();
            attributes.put(attrName, value);

            scan.skipWhitespace();
            attrName = scan.nextToken('=');
        }
        return attributes;
    }
}
