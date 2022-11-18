package com.caverock.androidsvg.model;

public class PathDefinition implements PathInterface {
    private byte[] commands;
    private int commandsLength = 0;
    private float[] coords;
    private int coordsLength = 0;

    private static final byte MOVETO = 0;
    private static final byte LINETO = 1;
    private static final byte CUBICTO = 2;
    private static final byte QUADTO = 3;
    private static final byte ARCTO = 4;   // 4-7
    private static final byte CLOSE = 8;


    public PathDefinition() {
        this.commands = new byte[8];
        this.coords = new float[16];
    }


    public boolean isEmpty() {
        return commandsLength == 0;
    }


    private void addCommand(byte value) {
        if (commandsLength == commands.length) {
            byte[] newCommands = new byte[commands.length * 2];
            System.arraycopy(commands, 0, newCommands, 0, commands.length);
            commands = newCommands;
        }
        commands[commandsLength++] = value;
    }


    private void coordsEnsure(int num) {
        if (coords.length < (coordsLength + num)) {
            float[] newCoords = new float[coords.length * 2];
            System.arraycopy(coords, 0, newCoords, 0, coords.length);
            coords = newCoords;
        }
    }


    @Override
    public void moveTo(float x, float y) {
        addCommand(MOVETO);
        coordsEnsure(2);
        coords[coordsLength++] = x;
        coords[coordsLength++] = y;
    }


    @Override
    public void lineTo(float x, float y) {
        addCommand(LINETO);
        coordsEnsure(2);
        coords[coordsLength++] = x;
        coords[coordsLength++] = y;
    }


    @Override
    public void cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) {
        addCommand(CUBICTO);
        coordsEnsure(6);
        coords[coordsLength++] = x1;
        coords[coordsLength++] = y1;
        coords[coordsLength++] = x2;
        coords[coordsLength++] = y2;
        coords[coordsLength++] = x3;
        coords[coordsLength++] = y3;
    }


    @Override
    public void quadTo(float x1, float y1, float x2, float y2) {
        addCommand(QUADTO);
        coordsEnsure(4);
        coords[coordsLength++] = x1;
        coords[coordsLength++] = y1;
        coords[coordsLength++] = x2;
        coords[coordsLength++] = y2;
    }


    @Override
    public void arcTo(float rx, float ry, float xAxisRotation, boolean largeArcFlag, boolean sweepFlag, float x, float y) {
        int arc = ARCTO | (largeArcFlag ? 2 : 0) | (sweepFlag ? 1 : 0);
        addCommand((byte) arc);
        coordsEnsure(5);
        coords[coordsLength++] = rx;
        coords[coordsLength++] = ry;
        coords[coordsLength++] = xAxisRotation;
        coords[coordsLength++] = x;
        coords[coordsLength++] = y;
    }


    @Override
    public void close() {
        addCommand(CLOSE);
    }


    public void enumeratePath(PathInterface handler) {
        int coordsPos = 0;

        for (int commandPos = 0; commandPos < commandsLength; commandPos++) {
            byte command = commands[commandPos];
            switch (command) {
                case MOVETO:
                    handler.moveTo(coords[coordsPos++], coords[coordsPos++]);
                    break;
                case LINETO:
                    handler.lineTo(coords[coordsPos++], coords[coordsPos++]);
                    break;
                case CUBICTO:
                    handler.cubicTo(coords[coordsPos++], coords[coordsPos++], coords[coordsPos++], coords[coordsPos++], coords[coordsPos++], coords[coordsPos++]);
                    break;
                case QUADTO:
                    handler.quadTo(coords[coordsPos++], coords[coordsPos++], coords[coordsPos++], coords[coordsPos++]);
                    break;
                case CLOSE:
                    handler.close();
                    break;
                default:
                    boolean largeArcFlag = (command & 2) != 0;
                    boolean sweepFlag = (command & 1) != 0;
                    handler.arcTo(coords[coordsPos++], coords[coordsPos++], coords[coordsPos++], largeArcFlag, sweepFlag, coords[coordsPos++], coords[coordsPos++]);
            }
        }
    }

}
