package com.caverock.androidsvg.model;

public class RuleMatchContext {
    public SvgElementBase targetElement;    // From RenderOptions.target() and used for the :target selector

    @Override
    public String toString() {
        if (targetElement != null)
            return String.format("<%s id=\"%s\">", targetElement.getNodeName(), targetElement.id);
        else
            return "";
    }
}
