/*
   Copyright 2013-2020 Paul LeBeau, Cave Rock Software Ltd.

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

package com.caverock.androidsvg.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  Style implements Cloneable
{
   // Which properties have been explicitly specified by this element
   public long       specifiedFlags = 0;

   public SvgPaint fill;
   public FillRule   fillRule;
   public Float      fillOpacity;

   public SvgPaint   stroke;
   public Float      strokeOpacity;
   public Length strokeWidth;
   public LineCap    strokeLineCap;
   public LineJoin   strokeLineJoin;
   public Float      strokeMiterLimit;
   public Length[]   strokeDashArray;
   public Length     strokeDashOffset;

   public Float      opacity; // master opacity of both stroke and fill

   public Colour color;

   public List<String>    fontFamily;
   public Length          fontSize;
   public Float           fontWeight;
   public FontStyle       fontStyle;
   public Float           fontStretch;
   public TextDecoration  textDecoration;
   public TextDirection   direction;

   public TextAnchor   textAnchor;

   public Boolean      overflow;  // true if overflow visible
   public CSSClipRect clip;

   public String     markerStart;
   public String     markerMid;
   public String     markerEnd;

   public Boolean    display;    // true if we should display
   public Boolean    visibility; // true if visible

   public SvgPaint   stopColor;
   public Float      stopOpacity;

   public String     clipPath;
   public FillRule   clipRule;

   public String     mask;

   public SvgPaint   solidColor;
   public Float      solidOpacity;

   public SvgPaint   viewportFill;
   public Float      viewportFillOpacity;

   public VectorEffect  vectorEffect;

   public RenderQuality  imageRendering;

   public Isolation     isolation;
   public CSSBlendMode  mixBlendMode;

   public FontKerning               fontKerning;
   public CSSFontFeatureSettings fontVariantLigatures;
   public CSSFontFeatureSettings    fontVariantPosition;
   public CSSFontFeatureSettings    fontVariantCaps;
   public CSSFontFeatureSettings    fontVariantNumeric;
   public CSSFontFeatureSettings    fontVariantEastAsian;
   public CSSFontFeatureSettings    fontFeatureSettings;
   public CSSFontVariationSettings fontVariationSettings;
   public WritingMode               writingMode;
   public GlypOrientationVertical   glyphOrientationVertical;
   public TextOrientation           textOrientation;

   public Length     letterSpacing;
   public Length     wordSpacing;

   public Length verticalAlignment;


   public static final float  FONT_WEIGHT_MIN = 1f;
   public static final float  FONT_WEIGHT_NORMAL = 400f;
   public static final float  FONT_WEIGHT_BOLD = 700f;
   public static final float  FONT_WEIGHT_MAX = 1000f;
   public static final float  FONT_WEIGHT_LIGHTER = Float.MIN_VALUE;
   public static final float  FONT_WEIGHT_BOLDER = Float.MAX_VALUE;

   public static final float  FONT_STRETCH_MIN = 0f;
   public static final float  FONT_STRETCH_NORMAL = 100f;


   public static final long SPECIFIED_FILL                       = (1<<0);
   public static final long SPECIFIED_FILL_RULE                  = (1<<1);
   public static final long SPECIFIED_FILL_OPACITY               = (1<<2);
   public static final long SPECIFIED_STROKE                     = (1<<3);
   public static final long SPECIFIED_STROKE_OPACITY             = (1<<4);
   public static final long SPECIFIED_STROKE_WIDTH               = (1<<5);
   public static final long SPECIFIED_STROKE_LINECAP             = (1<<6);
   public static final long SPECIFIED_STROKE_LINEJOIN            = (1<<7);
   public static final long SPECIFIED_STROKE_MITERLIMIT          = (1<<8);
   public static final long SPECIFIED_STROKE_DASHARRAY           = (1<<9);
   public static final long SPECIFIED_STROKE_DASHOFFSET          = (1<<10);
   public static final long SPECIFIED_OPACITY                    = (1<<11);
   public static final long SPECIFIED_COLOR                      = (1<<12);
   public static final long SPECIFIED_FONT_FAMILY                = (1<<13);
   public static final long SPECIFIED_FONT_SIZE                  = (1<<14);
   public static final long SPECIFIED_FONT_WEIGHT                = (1<<15);
   public static final long SPECIFIED_FONT_STYLE                 = (1<<16);
   public static final long SPECIFIED_TEXT_DECORATION            = (1<<17);
   public static final long SPECIFIED_TEXT_ANCHOR                = (1<<18);
   public static final long SPECIFIED_OVERFLOW                   = (1<<19);
   public static final long SPECIFIED_CLIP                       = (1<<20);
   public static final long SPECIFIED_MARKER_START               = (1<<21);
   public static final long SPECIFIED_MARKER_MID                 = (1<<22);
   public static final long SPECIFIED_MARKER_END                 = (1<<23);
   public static final long SPECIFIED_DISPLAY                    = (1<<24);
   public static final long SPECIFIED_VISIBILITY                 = (1<<25);
   public static final long SPECIFIED_STOP_COLOR                 = (1<<26);
   public static final long SPECIFIED_STOP_OPACITY               = (1<<27);
   public static final long SPECIFIED_CLIP_PATH                  = (1<<28);
   public static final long SPECIFIED_CLIP_RULE                  = (1<<29);
   public static final long SPECIFIED_MASK                       = (1<<30);
   public static final long SPECIFIED_SOLID_COLOR                = (1L<<31);
   public static final long SPECIFIED_SOLID_OPACITY              = (1L<<32);
   public static final long SPECIFIED_VIEWPORT_FILL              = (1L<<33);
   public static final long SPECIFIED_VIEWPORT_FILL_OPACITY      = (1L<<34);
   public static final long SPECIFIED_VECTOR_EFFECT              = (1L<<35);
   public static final long SPECIFIED_DIRECTION                  = (1L<<36);
   public static final long SPECIFIED_IMAGE_RENDERING            = (1L<<37);
   public static final long SPECIFIED_ISOLATION                  = (1L<<38);
   public static final long SPECIFIED_MIX_BLEND_MODE             = (1L<<39);
   public static final long SPECIFIED_FONT_VARIANT_LIGATURES     = (1L<<40);
   public static final long SPECIFIED_FONT_VARIANT_POSITION      = (1L<<41);
   public static final long SPECIFIED_FONT_VARIANT_CAPS          = (1L<<42);
   public static final long SPECIFIED_FONT_VARIANT_NUMERIC       = (1L<<43);
   public static final long SPECIFIED_FONT_VARIANT_EAST_ASIAN    = (1L<<44);
   public static final long SPECIFIED_FONT_FEATURE_SETTINGS      = (1L<<45);
   public static final long SPECIFIED_WRITING_MODE               = (1L<<46);
   public static final long SPECIFIED_GLYPH_ORIENTATION_VERTICAL = (1L<<47);
   public static final long SPECIFIED_TEXT_ORIENTATION           = (1L<<48);
   public static final long SPECIFIED_FONT_KERNING               = (1L<<49);
   public static final long SPECIFIED_FONT_VARIATION_SETTINGS    = (1L<<50);
   public static final long SPECIFIED_FONT_STRETCH               = (1L<<51);
   public static final long SPECIFIED_LETTER_SPACING             = (1L<<52);
   public static final long SPECIFIED_WORD_SPACING               = (1L<<53);
   public static final long SPECIFIED_VERTICAL_ALIGNMENT         = (1L<<54);

   // Flags for the settings that are applied to reset the root style
   private static final long SPECIFIED_RESET = 0xffffffffffffffffL &
                           ~(SPECIFIED_FONT_VARIANT_LIGATURES  |
                             SPECIFIED_FONT_VARIANT_POSITION   |
                             SPECIFIED_FONT_VARIANT_CAPS       |
                             SPECIFIED_FONT_VARIANT_NUMERIC    |
                             SPECIFIED_FONT_VARIANT_EAST_ASIAN |
                             SPECIFIED_FONT_VARIATION_SETTINGS);


   public enum FillRule
   {
      NonZero,
      EvenOdd
   }

   public enum LineCap
   {
      Butt,
      Round,
      Square
   }

   public enum LineJoin
   {
      Miter,
      Round,
      Bevel
   }

   public enum FontStyle
   {
      normal,
      italic,
      oblique
   }

   public enum TextAnchor
   {
      Start,
      Middle,
      End
   }

   public enum TextDecoration
   {
      None,
      Underline,
      Overline,
      LineThrough,
      Blink
   }

   public enum TextDirection
   {
      LTR,
      RTL
   }

   public enum VectorEffect
   {
      None,
      NonScalingStroke
   }

   public enum RenderQuality
   {
      auto,
      optimizeQuality,
      optimizeSpeed
   }

   public enum Isolation
   {
      auto,
      isolate
   }

   public enum CSSBlendMode
   {
      normal,
      multiply,
      screen,
      overlay,
      darken,
      lighten,
      color_dodge,
      color_burn,
      hard_light,
      soft_light,
      difference,
      exclusion,
      hue,
      saturation,
      color,
      luminosity,
      UNSUPPORTED;

      private static final Map<String, CSSBlendMode> cache = new HashMap<>();

      static {
         for (CSSBlendMode mode : values()) {
            if (mode != UNSUPPORTED) {
               final String key = mode.name().replace('_', '-');
               cache.put(key, mode);
            }
         }
      }

      public static CSSBlendMode fromString(String str)
      {
         // First check cache to see if it is there
         CSSBlendMode mode = cache.get(str);
         if (mode != null) {
            return mode;
         }

         return UNSUPPORTED;
      }
   }


   public enum  FontKerning
   {
      auto,
      normal,
      none
   }

   public enum  WritingMode
   {
      // Old SVG 1.1 values
      lr_tb,
      rl_tb,
      tb_rl,
      lr,
      rl,
      tb,
      // New CSS3 values
      horizontal_tb,
      vertical_rl,
      vertical_lr
   }


   public enum  GlypOrientationVertical
   {
      auto,
      angle0,
      angle90,
      angle180,
      angle270
   }


   public enum  TextOrientation
   {
      mixed,
      upright,
      sideways
   }


   public static Style  getDefaultStyle()
   {
      Style  def = new Style();

      def.fill = Colour.BLACK;
      def.fillRule = FillRule.NonZero;
      def.fillOpacity = 1f;
      def.stroke = null;         // none
      def.strokeOpacity = 1f;
      def.strokeWidth = new Length(1f);
      def.strokeLineCap = LineCap.Butt;
      def.strokeLineJoin = LineJoin.Miter;
      def.strokeMiterLimit = 4f;
      def.strokeDashArray = null;
      def.strokeDashOffset = Length.ZERO;
      def.opacity = 1f;
      def.color = Colour.BLACK; // currentColor defaults to black
      def.fontFamily = null;
      def.fontSize = new Length(12, Unit.pt);
      def.fontWeight = FONT_WEIGHT_NORMAL;
      def.fontStyle = FontStyle.normal;
      def.fontStretch = FONT_STRETCH_NORMAL;
      def.textDecoration = TextDecoration.None;
      def.direction = TextDirection.LTR;
      def.textAnchor = TextAnchor.Start;
      def.overflow = true;  // Overflow shown/visible for root, but not for other elements (see section 14.3.3).
      def.clip = null;
      def.markerStart = null;
      def.markerMid = null;
      def.markerEnd = null;
      def.display = Boolean.TRUE;
      def.visibility = Boolean.TRUE;
      def.stopColor = Colour.BLACK;
      def.stopOpacity = 1f;
      def.clipPath = null;
      def.clipRule = FillRule.NonZero;
      def.mask = null;
      def.solidColor = null;
      def.solidOpacity = 1f;
      def.viewportFill = null;
      def.viewportFillOpacity = 1f;
      def.vectorEffect = VectorEffect.None;
      def.imageRendering = RenderQuality.auto;
      def.isolation = Isolation.auto;
      def.mixBlendMode = CSSBlendMode.normal;
      def.fontKerning = FontKerning.auto;
      def.fontVariantLigatures = CSSFontFeatureSettings.LIGATURES_NORMAL;
      def.fontVariantPosition = CSSFontFeatureSettings.POSITION_ALL_OFF;
      def.fontVariantCaps =  CSSFontFeatureSettings.CAPS_ALL_OFF;
      def.fontVariantNumeric =  CSSFontFeatureSettings.NUMERIC_ALL_OFF;
      def.fontVariantEastAsian =  CSSFontFeatureSettings.EAST_ASIAN_ALL_OFF;
      def.fontFeatureSettings = CSSFontFeatureSettings.FONT_FEATURE_SETTINGS_NORMAL;
      def.fontVariationSettings = null;
      def.letterSpacing = Length.ZERO;
      def.wordSpacing = Length.ZERO;
      def.verticalAlignment = Length.ZERO;
      def.writingMode = WritingMode.horizontal_tb;
      def.glyphOrientationVertical = GlypOrientationVertical.auto;
      def.textOrientation = TextOrientation.mixed;

      def.specifiedFlags = SPECIFIED_RESET;
      //def.inheritFlags = 0;

      return def;
   }


   // Called on the state.style object to reset the properties that don't inherit
   // from the parent style.
   public void  resetNonInheritingProperties(boolean isRootSVG)
   {
      this.display = Boolean.TRUE;
      this.overflow = isRootSVG ? Boolean.TRUE : Boolean.FALSE;
      this.clip = null;
      this.clipPath = null;
      this.opacity = 1f;
      this.stopColor = Colour.BLACK;
      this.stopOpacity = 1f;
      this.mask = null;
      this.solidColor = null;
      this.solidOpacity = 1f;
      this.viewportFill = null;
      this.viewportFillOpacity = 1f;
      this.vectorEffect = VectorEffect.None;
      this.isolation = Isolation.auto;
      this.mixBlendMode = CSSBlendMode.normal;
   }


   @Override
   public Object  clone() throws CloneNotSupportedException
   {
      Style obj = (Style) super.clone();
      if (strokeDashArray != null) {
         obj.strokeDashArray = strokeDashArray.clone();
      }
      return obj;
   }
}
