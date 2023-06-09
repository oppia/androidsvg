package com.caverock.androidsvg.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CSSFontVariationSettings
{
   private final HashMap<String, Float> settings;

   private static final String  NORMAL = "normal";

   static final String  VARIATION_WEIGHT = "wght";
   static final String  VARIATION_ITALIC = "ital";
   static final String  VARIATION_OBLIQUE = "slnt";
   static final String  VARIATION_WIDTH = "wdth";

   static final Float  VARIATION_ITALIC_VALUE_ON = 1f;
   static final Float  VARIATION_OBLIQUE_VALUE_ON = -14f;  // -14 degrees

   private static final NumberFormat NUMBER_FORMATTER = createNumberFormatter();

   private static class FontVariationEntry {
      String  name;
      Float   val;

      public FontVariationEntry(String name, Float val) {
         this.name = name;
         this.val = val;
      }
   }


   public CSSFontVariationSettings()
   {
      this.settings = new HashMap<>();
   }

   private CSSFontVariationSettings(HashMap<String, Float> initialMap)
   {
      this.settings = initialMap;
   }

   public CSSFontVariationSettings(CSSFontVariationSettings other)
   {
      this.settings = new HashMap<>(other.settings);
   }


   public void  addSetting(String key, float value)
   {
      this.settings.put(key, value);
   }


   public void  applySettings(CSSFontVariationSettings variationSet)
   {
      if (variationSet == null)
         return;
      this.settings.putAll(variationSet.settings);
   }


   @Override
   public String toString()
   {
      StringBuilder  sb = new StringBuilder();
      for (Map.Entry<String, Float> entry: this.settings.entrySet()) {
         if (sb.length() > 0)
            sb.append(',');
         sb.append("'");
         sb.append(entry.getKey());
         sb.append("' ");
         sb.append(NUMBER_FORMATTER.format(entry.getValue()));
      }
      return sb.toString();
   }


   //-----------------------------------------------------------------------------------------------
   // Parsing font-variation-settings property value

   /*
    * Parse the value of the CSS property "font-variation-settings".
    *
    * Format is: normal | [ <string> <number>]#
    */
   static CSSFontVariationSettings  parseFontVariationSettings(String val)
   {
      CSSFontVariationSettings  result = new CSSFontVariationSettings();

      TextScanner  scan = new TextScanner(val);
      scan.skipWhitespace();

      if (scan.consume(NORMAL))
         return null;

      while (true) {
         if (scan.empty())
            break;
         CSSFontVariationSettings.FontVariationEntry entry = nextFeatureEntry(scan);
         if (entry == null)
            return null;
         result.settings.put(entry.name, entry.val);
         scan.skipCommaWhitespace();
      }
      return result;
   }


   private static CSSFontVariationSettings.FontVariationEntry nextFeatureEntry(TextScanner scan)
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
      return new CSSFontVariationSettings.FontVariationEntry(name, num);
   }


   private static final NumberFormat createNumberFormatter() {
      // Use the US locale for strong consistency in number-only formatting for high confidence in
      // the parsability of formatted values.
      NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);

      // NumberFormat's factories should generally be used instead of directly instantiating a
      // DecimalFormat (per documentation). Also, the Locale must be fixed because this is producing
      // a string that will be parsed so non-Hindi-Arabic numerals won't work for such times when
      // the default Locale is changed to use one of those numeral types for formatting (such as
      // eastern Arabic numerals).
      if (formatter instanceof DecimalFormat) {
         ((DecimalFormat) formatter).applyPattern("#.##");
      }

      return formatter;
   }

}
