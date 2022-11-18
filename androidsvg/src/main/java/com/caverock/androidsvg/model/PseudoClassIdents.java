package com.caverock.androidsvg.model;

import java.util.HashMap;
import java.util.Map;

// Supported SVG attributes
public enum PseudoClassIdents {
    target,
    root,
    nth_child,
    nth_last_child,
    nth_of_type,
    nth_last_of_type,
    first_child,
    last_child,
    first_of_type,
    last_of_type,
    only_child,
    only_of_type,
    empty,
    not,

    // Others from  Selectors 3 (and earlier)
    // Supported but always fail to match.
    lang,  // might support later
    link, visited, hover, active, focus, enabled, disabled, checked, indeterminate,

    // Added in Selectors 4 spec
    // Might support these later
    //matches,
    //something,  // Not final name(?)
    //has,
    //dir,  might support later
    //target_within,
    //blank,

    // Operators from Selectors 4
    // any-link, local-link, scope, focus-visible, focus-within, drop, current, past,
    // future, playing, paused, read-only, read-write, placeholder-shown, default, valid, invalid,
    // in-range, out-of-range, required, optional, user-invalid, nth-col, nth-last-col
    UNSUPPORTED;

    private static final Map<String, PseudoClassIdents> cache = new HashMap<>();

    static {
        for (PseudoClassIdents attr : values()){
            if (attr != UNSUPPORTED) {
                final String key = attr.name().replace('_', '-');
                cache.put(key, attr);
            }
        }
    }

    public static PseudoClassIdents fromString(String str) {
        PseudoClassIdents attr = cache.get(str);
        if (attr != null) {
            return attr;
        }
        return UNSUPPORTED;
    }
}
