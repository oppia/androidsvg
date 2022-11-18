package com.caverock.androidsvg.model;

import com.caverock.androidsvg.loader.CssResolver;

public interface SvgStructure {
    /**
     * Returns the contents of the {@code <title>} element in the SVG document.
     *
     * @return title contents if available, otherwise an empty string.
     * @throws IllegalArgumentException if there is no current SVG document loaded.
     */
    @SuppressWarnings({"WeakerAccess", "unused"})
    String getDocumentTitle();

    /**
     * Returns the contents of the {@code <desc>} element in the SVG document.
     *
     * @return desc contents if available, otherwise an empty string.
     * @throws IllegalArgumentException if there is no current SVG document loaded.
     */
    @SuppressWarnings({"WeakerAccess", "unused"})
    String getDocumentDescription();

    /**
     * Returns the SVG version number as provided in the root {@code <svg>} tag of the document.
     *
     * @return the version string if declared, otherwise an empty string.
     * @throws IllegalArgumentException if there is no current SVG document loaded.
     */
    @SuppressWarnings({"WeakerAccess", "unused"})
    String getDocumentSVGVersion();

    Svg getRootElement();

    void setTitle(String title);

    void setDesc(String desc);

    void setRootElement(Svg rootElement);

    void addCSSRules(Ruleset ruleset);

    interface Factory {
        SvgStructure createNewStructure(boolean enableInternalEntities, CssResolver cssResolver);
    }
}
