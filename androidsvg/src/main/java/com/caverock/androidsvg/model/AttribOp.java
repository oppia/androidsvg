package com.caverock.androidsvg.model;

public enum AttribOp {
    EXISTS,     // *[foo]
    EQUALS,     // *[foo=bar]
    INCLUDES,   // *[foo~=bar]
    DASHMATCH,  // *[foo|=bar]
}
