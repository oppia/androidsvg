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
import java.util.Map;

/*
 * Keeps a list of font feature settings and their values.
 */
public class CSSFontFeatureSettings
{
   public static final CSSFontFeatureSettings  FONT_FEATURE_SETTINGS_NORMAL = makeDefaultSettings();
   public static final CSSFontFeatureSettings  ERROR = new CSSFontFeatureSettings((HashMap<String, Integer>) null);

   public static final String  FONT_VARIANT_NORMAL = "normal";
   public static final String  FONT_VARIANT_AUTO = "auto";
   public static final String  FONT_VARIANT_NONE = "none";

   public static final int  VALUE_ON  = 1;
   public static final int  VALUE_OFF = 0;

   public static final String  TOKEN_ERROR = "ERR";

   // For font-kerning
   public static final String FEATURE_KERN = "kern";

   // For font-variant-ligatures
   public static CSSFontFeatureSettings  LIGATURES_NORMAL = null;
   public static CSSFontFeatureSettings LIGATURES_ALL_OFF = null;

   public static final String  FONT_VARIANT_COMMON_LIGATURES = "common-ligatures";
   public static final String  FONT_VARIANT_NO_COMMON_LIGATURES = "no-common-ligatures";
   public static final String  FONT_VARIANT_DISCRETIONARY_LIGATURES = "discretionary-ligatures";
   public static final String  FONT_VARIANT_NO_DISCRETIONARY_LIGATURES = "no-discretionary-ligatures";
   public static final String  FONT_VARIANT_HISTORICAL_LIGATURES = "historical-ligatures";
   public static final String  FONT_VARIANT_NO_HISTORICAL_LIGATURES = "no-historical-ligatures";
   public static final String  FONT_VARIANT_CONTEXTUAL_LIGATURES = "contextual";
   public static final String  FONT_VARIANT_NO_CONTEXTUAL_LIGATURES = "no-contextual";

   public static final String FEATURE_CLIG = "clig";
   public static final String FEATURE_LIGA = "liga";
   public static final String FEATURE_DLIG = "dlig";
   public static final String FEATURE_HLIG = "hlig";
   public static final String FEATURE_CALT = "calt";

   // For font-variant-position
   public static CSSFontFeatureSettings  POSITION_ALL_OFF = null;

   public static final String  FONT_VARIANT_SUB = "sub";
   public static final String  FONT_VARIANT_SUPER = "super";

   public static final String  FEATURE_SUBS = "subs";
   public static final String  FEATURE_SUPS = "sups";

   // For font-variant-caps
   public static CSSFontFeatureSettings          CAPS_ALL_OFF = null;
   private static CSSFontFeatureSettings  CAPS_SMALL_CAPS = null;

   public static final String  FONT_VARIANT_SMALL_CAPS = "small-caps";
   public static final String  FONT_VARIANT_ALL_SMALL_CAPS = "all-small-caps";
   public static final String  FONT_VARIANT_PETITE_CAPS = "petite-caps";
   public static final String  FONT_VARIANT_ALL_PETITE_CAPS = "all-petite-caps";
   public static final String  FONT_VARIANT_UNICASE = "unicase";
   public static final String  FONT_VARIANT_TITLING_CAPS = "titling-caps";

   public static final String  FEATURE_SMCP = "smcp";
   public static final String  FEATURE_C2SC = "c2sc";
   public static final String  FEATURE_PCAP = "pcap";
   public static final String  FEATURE_C2PC = "c2pc";
   public static final String  FEATURE_UNIC = "unic";
   public static final String  FEATURE_TITL = "titl";

   // For font-variant-numeric
   public static CSSFontFeatureSettings  NUMERIC_ALL_OFF = null;

   public static final String  FONT_VARIANT_LINING_NUMS = "lining-nums";
   public static final String  FONT_VARIANT_OLDSTYLE_NUMS = "oldstyle-nums";
   public static final String  FONT_VARIANT_PROPORTIONAL_NUMS = "proportional-nums";
   public static final String  FONT_VARIANT_TABULAR_NUMS = "tabular-nums";
   public static final String  FONT_VARIANT_DIAGONAL_FRACTIONS = "diagonal-fractions";
   public static final String  FONT_VARIANT_STACKED_FRACTIONS = "stacked-fractions";
   public static final String  FONT_VARIANT_ORDINAL = "ordinal";
   public static final String  FONT_VARIANT_SLASHED_ZERO = "slashed-zero";

   public static final String FEATURE_LNUM = "lnum";
   public static final String FEATURE_ONUM = "onum";
   public static final String FEATURE_PNUM = "pnum";
   public static final String FEATURE_TNUM = "tnum";
   public static final String FEATURE_FRAC = "frac";
   public static final String FEATURE_AFRC = "afrc";
   public static final String FEATURE_ORDN = "ordn";
   public static final String FEATURE_ZERO = "zero";

   // For font-variant-east-asian
   public static CSSFontFeatureSettings  EAST_ASIAN_ALL_OFF = null;

   public static final String  FONT_VARIANT_JIS78 = "jis78";
   public static final String  FONT_VARIANT_JIS83 = "jis83";
   public static final String  FONT_VARIANT_JIS90 = "jis90";
   public static final String  FONT_VARIANT_JIS04 = "jis04";
   public static final String  FONT_VARIANT_SIMPLIFIED = "simplified";
   public static final String  FONT_VARIANT_TRADITIONAL = "traditional";
   public static final String  FONT_VARIANT_FULL_WIDTH = "full-width";
   public static final String  FONT_VARIANT_PROPORTIONAL_WIDTH = "proportional-width";
   public static final String  FONT_VARIANT_RUBY = "ruby";

   public static final String FEATURE_JP78 = "jp78";
   public static final String FEATURE_JP83 = "jp83";
   public static final String FEATURE_JP90 = "jp90";
   public static final String FEATURE_JP04 = "jp04";
   public static final String FEATURE_SMPL = "smpl";
   public static final String FEATURE_TRAD = "trad";
   public static final String FEATURE_FWID = "fwid";
   public static final String FEATURE_PWID = "pwid";
   public static final String FEATURE_RUBY = "ruby";


   public final HashMap<String, Integer>  settings;


   static {
      LIGATURES_NORMAL = new CSSFontFeatureSettings();
      LIGATURES_NORMAL.settings.put(FEATURE_LIGA, VALUE_ON);
      LIGATURES_NORMAL.settings.put(FEATURE_CLIG, VALUE_ON);
      LIGATURES_NORMAL.settings.put(FEATURE_DLIG, VALUE_OFF);
      LIGATURES_NORMAL.settings.put(FEATURE_HLIG, VALUE_OFF);
      LIGATURES_NORMAL.settings.put(FEATURE_CALT, VALUE_ON);

      POSITION_ALL_OFF = new CSSFontFeatureSettings();
      POSITION_ALL_OFF.settings.put(FEATURE_SUBS, VALUE_OFF);
      POSITION_ALL_OFF.settings.put(FEATURE_SUPS, VALUE_OFF);

      CAPS_ALL_OFF = new CSSFontFeatureSettings();
      CAPS_ALL_OFF.settings.put(FEATURE_SMCP, VALUE_OFF);
      CAPS_ALL_OFF.settings.put(FEATURE_C2SC, VALUE_OFF);
      CAPS_ALL_OFF.settings.put(FEATURE_PCAP, VALUE_OFF);
      CAPS_ALL_OFF.settings.put(FEATURE_C2PC, VALUE_OFF);
      CAPS_ALL_OFF.settings.put(FEATURE_UNIC, VALUE_OFF);
      CAPS_ALL_OFF.settings.put(FEATURE_TITL, VALUE_OFF);

      NUMERIC_ALL_OFF = new CSSFontFeatureSettings();
      NUMERIC_ALL_OFF.settings.put(FEATURE_LNUM, VALUE_OFF);
      NUMERIC_ALL_OFF.settings.put(FEATURE_ONUM, VALUE_OFF);
      NUMERIC_ALL_OFF.settings.put(FEATURE_PNUM, VALUE_OFF);
      NUMERIC_ALL_OFF.settings.put(FEATURE_TNUM, VALUE_OFF);
      NUMERIC_ALL_OFF.settings.put(FEATURE_FRAC, VALUE_OFF);
      NUMERIC_ALL_OFF.settings.put(FEATURE_AFRC, VALUE_OFF);
      NUMERIC_ALL_OFF.settings.put(FEATURE_ORDN, VALUE_OFF);
      NUMERIC_ALL_OFF.settings.put(FEATURE_ZERO, VALUE_OFF);

      EAST_ASIAN_ALL_OFF = new CSSFontFeatureSettings();
      EAST_ASIAN_ALL_OFF.settings.put(FEATURE_JP78, VALUE_OFF);
      EAST_ASIAN_ALL_OFF.settings.put(FEATURE_JP83, VALUE_OFF);
      EAST_ASIAN_ALL_OFF.settings.put(FEATURE_JP90, VALUE_OFF);
      EAST_ASIAN_ALL_OFF.settings.put(FEATURE_JP04, VALUE_OFF);
      EAST_ASIAN_ALL_OFF.settings.put(FEATURE_SMPL, VALUE_OFF);
      EAST_ASIAN_ALL_OFF.settings.put(FEATURE_TRAD, VALUE_OFF);
      EAST_ASIAN_ALL_OFF.settings.put(FEATURE_FWID, VALUE_OFF);
      EAST_ASIAN_ALL_OFF.settings.put(FEATURE_PWID, VALUE_OFF);
      EAST_ASIAN_ALL_OFF.settings.put(FEATURE_RUBY, VALUE_OFF);
   }


   public CSSFontFeatureSettings()
   {
      this.settings = new HashMap<>();
   }

   private CSSFontFeatureSettings(HashMap<String, Integer> initialMap)
   {
      this.settings = initialMap;
   }

   public CSSFontFeatureSettings(CSSFontFeatureSettings other)
   {
      this.settings = new HashMap<>(other.settings);
   }


   public void  applySettings(CSSFontFeatureSettings featureSettings)
   {
      if (featureSettings == null)
         return;
      this.settings.putAll(featureSettings.settings);
   }


   public void  applyKerning(Style.FontKerning kern)
   {
      if (kern == Style.FontKerning.none )
         this.settings.put(FEATURE_KERN, VALUE_OFF);
      else
         this.settings.put(FEATURE_KERN, VALUE_ON);
   }


   public boolean  hasSettings()
   {
      return this.settings.size() > 0;
   }

   public void  addSettings(String feature1, String feature2, int onOrOff)
   {
      this.settings.put(feature1, onOrOff);
      this.settings.put(feature2, onOrOff);
   }

   @Override
   public String toString()
   {
      StringBuilder  sb = new StringBuilder();
      for (Map.Entry<String, Integer> entry: this.settings.entrySet()) {
         if (sb.length() > 0)
            sb.append(',');
         sb.append("'");
         sb.append(entry.getKey());
         sb.append("' ");
         sb.append(entry.getValue());
      }
      return sb.toString();
   }

   private static final CSSFontFeatureSettings  makeDefaultSettings()
   {
      // See: https://www.w3.org/TR/css-fonts-3/#default-features
      CSSFontFeatureSettings result = new CSSFontFeatureSettings();
      result.settings.put("rlig", VALUE_ON);
      result.settings.put("liga", VALUE_ON);
      result.settings.put("clig", VALUE_ON);
      result.settings.put("calt", VALUE_ON);
      result.settings.put("locl", VALUE_ON);
      result.settings.put("ccmp", VALUE_ON);
      result.settings.put("mark", VALUE_ON);
      result.settings.put("mkmk", VALUE_ON);
      // TODO FIXME  also enable "vert" for vertical runs in complex scripts
      return result;
   }


   private void  ensurePositionNormal()
   {
      // common and contextual ligatures ON; discretionary  and historical ligatures OFF
      if (POSITION_ALL_OFF == null) {
         CSSFontFeatureSettings result = new CSSFontFeatureSettings();
         result.settings.put(FEATURE_SUBS, VALUE_OFF);
         result.settings.put(FEATURE_SUPS, VALUE_OFF);
         this.POSITION_ALL_OFF = result;
      }
   }


   // Used when parsing the CSS "font" shortcut property
   public static CSSFontFeatureSettings  makeSmallCaps()
   {
      if (CAPS_SMALL_CAPS == null) {
         CAPS_SMALL_CAPS = new CSSFontFeatureSettings();
         CAPS_SMALL_CAPS.settings.put(FEATURE_SMCP, VALUE_ON);
         CAPS_SMALL_CAPS.settings.put(FEATURE_C2SC, VALUE_OFF);
         CAPS_SMALL_CAPS.settings.put(FEATURE_PCAP, VALUE_OFF);
         CAPS_SMALL_CAPS.settings.put(FEATURE_C2PC, VALUE_OFF);
         CAPS_SMALL_CAPS.settings.put(FEATURE_UNIC, VALUE_OFF);
         CAPS_SMALL_CAPS.settings.put(FEATURE_TITL, VALUE_OFF);
      }
      return CAPS_SMALL_CAPS;
   }
}
