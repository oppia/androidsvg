package com.caverock.androidsvg.model;

public class TextPath extends TextContainer implements TextChild {
    public String href;
    public Length startOffset;

    private TextRoot textRoot;

    @Override
    public void setTextRoot(TextRoot obj) {
        this.textRoot = obj;
    }

    @Override
    public TextRoot getTextRoot() {
        return this.textRoot;
    }

    @Override
    public String getNodeName() {
        return "textPath";
    }
}
