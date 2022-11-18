package com.caverock.androidsvg.model;

public class Rule {
    public final Selector selector;
    public final Style style;
    final Source source;

    public Rule(Selector selector, Style style, Source source) {
        this.selector = selector;
        this.style = style;
        this.source = source;
    }

    @Override
    public String toString() {
        return selector + " {...} (src=" + this.source + ")";
    }
}
