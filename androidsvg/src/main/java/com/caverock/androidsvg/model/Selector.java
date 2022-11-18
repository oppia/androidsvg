package com.caverock.androidsvg.model;

import java.util.ArrayList;
import java.util.List;

public class Selector {
    public static final int SPECIFICITY_ID_ATTRIBUTE             = 1000000;
    public static final int SPECIFICITY_ATTRIBUTE_OR_PSEUDOCLASS = 1000;
    public static final int SPECIFICITY_ELEMENT_OR_PSEUDOELEMENT = 1;

    public List<SimpleSelector> simpleSelectors = null;
    public int specificity = 0;

    public void add(SimpleSelector part) {
        if (this.simpleSelectors == null)
            this.simpleSelectors = new ArrayList<>();
        this.simpleSelectors.add(part);
    }

    public int size() {
        return (this.simpleSelectors == null) ? 0 : this.simpleSelectors.size();
    }

    public SimpleSelector get(int i) {
        return this.simpleSelectors.get(i);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isEmpty() {
        return (this.simpleSelectors == null) || this.simpleSelectors.isEmpty();
    }

    // Methods for accumulating a specificity value as SimpleSelector entries are added.
    // Number of ID selectors in the simpleSelectors
    public void addedIdAttribute() {
        specificity += SPECIFICITY_ID_ATTRIBUTE;
    }

    // Number of class selectors, attributes selectors, and pseudo-classes
    public void addedAttributeOrPseudo() {
        specificity += SPECIFICITY_ATTRIBUTE_OR_PSEUDOCLASS;
    }

    // Number of type (element) selectors and pseudo-elements
    public void addedElement() {
        specificity += SPECIFICITY_ELEMENT_OR_PSEUDOELEMENT;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (SimpleSelector sel : simpleSelectors)
            sb.append(sel).append(' ');
        return sb.append('[').append(specificity).append(']').toString();
    }
}
