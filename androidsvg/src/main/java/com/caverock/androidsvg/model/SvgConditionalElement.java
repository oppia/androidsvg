package com.caverock.androidsvg.model;

import java.util.Set;

// Any element that can appear inside a <switch> element.
public abstract class SvgConditionalElement extends SvgElement implements SvgConditional {
    Set<String> requiredFeatures = null;
    String requiredExtensions = null;
    Set<String> systemLanguage = null;
    Set<String> requiredFormats = null;
    Set<String> requiredFonts = null;

    @Override
    public void setRequiredFeatures(Set<String> features) {
        this.requiredFeatures = features;
    }

    @Override
    public Set<String> getRequiredFeatures() {
        return this.requiredFeatures;
    }

    @Override
    public void setRequiredExtensions(String extensions) {
        this.requiredExtensions = extensions;
    }

    @Override
    public String getRequiredExtensions() {
        return this.requiredExtensions;
    }

    @Override
    public void setSystemLanguage(Set<String> languages) {
        this.systemLanguage = languages;
    }

    @Override
    public Set<String> getSystemLanguage() {
        return this.systemLanguage;
    }

    @Override
    public void setRequiredFormats(Set<String> mimeTypes) {
        this.requiredFormats = mimeTypes;
    }

    @Override
    public Set<String> getRequiredFormats() {
        return this.requiredFormats;
    }

    @Override
    public void setRequiredFonts(Set<String> fontNames) {
        this.requiredFonts = fontNames;
    }

    @Override
    public Set<String> getRequiredFonts() {
        return this.requiredFonts;
    }
}
