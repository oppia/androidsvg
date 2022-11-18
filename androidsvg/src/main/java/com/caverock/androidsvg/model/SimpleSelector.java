package com.caverock.androidsvg.model;

import java.util.ArrayList;
import java.util.List;

public class SimpleSelector {
    public Combinator combinator;
    public String tag;       // null means "*"
    public List<Attrib> attribs = null;
    public List<PseudoClass> pseudos = null;

    public SimpleSelector(Combinator combinator, String tag) {
        this.combinator = (combinator != null) ? combinator : Combinator.DESCENDANT;
        this.tag = tag;
    }

    public void addAttrib(String attrName, AttribOp op, String attrValue) {
        if (attribs == null)
            attribs = new ArrayList<>();
        attribs.add(new Attrib(attrName, op, attrValue));
    }

    public void addPseudo(PseudoClass pseudo) {
        if (pseudos == null)
            pseudos = new ArrayList<>();
        pseudos.add(pseudo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (combinator == Combinator.CHILD)
            sb.append("> ");
        else if (combinator == Combinator.FOLLOWS)
            sb.append("+ ");
        sb.append((tag == null) ? "*" : tag);
        if (attribs != null) {
            for (Attrib attr : attribs) {
                sb.append('[').append(attr.name);
                switch (attr.operation) {
                    case EQUALS:
                        sb.append('=').append(attr.value);
                        break;
                    case INCLUDES:
                        sb.append("~=").append(attr.value);
                        break;
                    case DASHMATCH:
                        sb.append("|=").append(attr.value);
                        break;
                    default:
                        break;
                }
                sb.append(']');
            }
        }
        if (pseudos != null) {
            for (PseudoClass pseu : pseudos)
                sb.append(':').append(pseu);
        }
        return sb.toString();
    }
}
