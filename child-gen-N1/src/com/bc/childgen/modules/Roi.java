package com.bc.childgen.modules;

public class Roi {

    public int getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(int firstLine) {
        this.firstLine = firstLine;
    }

    public int getLastLine() {
        return lastLine;
    }

    public void setLastLine(int lastLine) {
        this.lastLine = lastLine;
    }

    public int getFirstTiePointLine() {
        return firstTiePointLine;
    }

    public void setFirstTiePointLine(int firstTiePointLine) {
        this.firstTiePointLine = firstTiePointLine;
    }

    public int getLastTiePointLine() {
        return lastTiePointLine;
    }

    public void setLastTiePointLine(int lastTiePointLine) {
        this.lastTiePointLine = lastTiePointLine;
    }

    public boolean isValid() {
        return firstLine >= 0 && lastLine > firstLine;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private int firstLine;
    private int lastLine;
    private int firstTiePointLine;
    private int lastTiePointLine;
}
