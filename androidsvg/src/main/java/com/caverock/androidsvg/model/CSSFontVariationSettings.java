package com.caverock.androidsvg.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CSSFontVariationSettings
{
   public final HashMap<String, Float> settings;

   public static final String  NORMAL = "normal";

   public static final String  VARIATION_WEIGHT = "wght";
   public static final String  VARIATION_ITALIC = "ital";
   public static final String  VARIATION_OBLIQUE = "slnt";
   public static final String  VARIATION_WIDTH = "wdth";

   public static final Float  VARIATION_ITALIC_VALUE_ON = 1f;
   public static final Float  VARIATION_OBLIQUE_VALUE_ON = -14f;  // -14 degrees

   private static final NumberFormat NUMBER_FORMATTER = createNumberFormatter();

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
         DecimalFormat format = new DecimalFormat("#.##");
         sb.append(NUMBER_FORMATTER.format(entry.getValue()));
      }
      return sb.toString();
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
