package com.caverock.androidsvg.loader;

public interface CssResolver {
    /**
     * Called by the parser to resolve CSS stylesheet file references in &lt;?xml-stylesheet?&gt;
     * processing instructions.
     * <p>
     * An implementation of this method should return a {@code String} whose contents
     * correspond to the URL passed in.
     * <p>
     * Note that AndroidSVG does not attempt to cache stylesheet references.  If you want
     * them cached, for speed or memory reasons, you should do so yourself.
     *
     * @param url the URL of the CSS file as it appears in the SVG file.
     * @return a AndroidSVG CSSStyleSheet object, or null if the stylesheet could not be found.
     * @since 1.3
     */
    String resolveCSSStyleSheet(String url);
}
