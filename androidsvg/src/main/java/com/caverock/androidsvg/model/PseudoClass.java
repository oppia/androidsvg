package com.caverock.androidsvg.model;

public interface PseudoClass {
    boolean matches(RuleMatchContext ruleMatchContext, SvgElementBase obj);
}
