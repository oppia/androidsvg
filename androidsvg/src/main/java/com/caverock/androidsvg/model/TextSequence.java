package com.caverock.androidsvg.model;

public class TextSequence extends SvgObject implements TextChild {
    public String text;

    private TextRoot textRoot;

    public TextSequence(String text) {
        this.text = text;
    }

    public String toString() {
        return "TextChild: '" + text + "'";
    }

    @Override
    public void setTextRoot(TextRoot obj) {
        this.textRoot = obj;
    }

    @Override
    public TextRoot getTextRoot() {
        return this.textRoot;
    }
}
