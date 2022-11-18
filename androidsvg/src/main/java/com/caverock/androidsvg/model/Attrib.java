package com.caverock.androidsvg.model;

public class Attrib {
    final public String name;
    final AttribOp operation;
    final public String value;

    Attrib(String name, AttribOp op, String value) {
        this.name = name;
        this.operation = op;
        this.value = value;
    }
}
