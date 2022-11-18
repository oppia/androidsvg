package com.caverock.androidsvg.model;

import java.util.Set;

// Any element that can appear inside a <switch> element.
public interface SvgConditional {
    void setRequiredFeatures(Set<String> features);

    Set<String> getRequiredFeatures();

    void setRequiredExtensions(String extensions);

    String getRequiredExtensions();

    void setSystemLanguage(Set<String> languages);

    Set<String> getSystemLanguage();

    void setRequiredFormats(Set<String> mimeTypes);

    Set<String> getRequiredFormats();

    void setRequiredFonts(Set<String> fontNames);

    Set<String> getRequiredFonts();
}
