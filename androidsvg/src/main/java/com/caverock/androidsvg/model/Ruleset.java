package com.caverock.androidsvg.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ruleset {
    private List<Rule> rules = null;

    // Add a rule to the ruleset. The position at which it is inserted is determined by its specificity value.
    public void add(Rule rule) {
        if (this.rules == null)
            this.rules = new ArrayList<>();
        for (int i = 0; i < rules.size(); i++) {
            Rule nextRule = rules.get(i);
            if (nextRule.selector.specificity > rule.selector.specificity) {
                rules.add(i, rule);
                return;
            }
        }
        rules.add(rule);
    }

    public void addAll(Ruleset rules) {
        if (rules.rules == null)
            return;
        if (this.rules == null)
            this.rules = new ArrayList<>(rules.rules.size());
        for (Rule rule : rules.rules) {
            this.add(rule);
        }
    }

    public List<Rule> getRules() {
        return this.rules;
    }

    public boolean isEmpty() {
        return this.rules == null || this.rules.isEmpty();
    }

    int ruleCount() {
        return (this.rules != null) ? this.rules.size() : 0;
    }

    /*
     * Remove all rules that were added from a given Source.
     */
    public void removeFromSource(Source sourceToBeRemoved) {
        if (this.rules == null)
            return;
        Iterator<Rule> iter = this.rules.iterator();
        while (iter.hasNext()) {
            if (iter.next().source == sourceToBeRemoved)
                iter.remove();
        }
    }

    @Override
    public String toString() {
        if (rules == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (Rule rule : rules)
            sb.append(rule.toString()).append('\n');
        return sb.toString();
    }
}
