package com.bc.childgen;

public class DatasetDescriptor {

    public DatasetDescriptor() {        
    }

    public void setSpare(boolean spare) {
        this.spare = spare;
    }

    public boolean isSpare() {
        return spare;
    }

    public void setDsType(char dsType) {
        this.dsType = dsType;
    }

    public char getDsType() {
        return dsType;
    }

    public void setDsSize(int dsSize) {
        this.dsSize = dsSize;
    }

    public int getDsSize() {
        return dsSize;
    }

    public void setDsOffset(long dsOffset) {
        this.dsOffset = dsOffset;
    }

    public long getDsOffset() {
        return dsOffset;
    }

    public void setSphBytePosition(long sphBytePosition) {
        this.sphBytePosition = sphBytePosition;
    }

    public long getSphBytePosition() {
        return sphBytePosition;
    }

    public void setNumDsr(int numDsr) {
        this.numDsr = numDsr;
    }

    public int getNumDsr() {
        return numDsr;
    }

    public void setDsrSize(int dsrSize) {
        this.dsrSize = dsrSize;
    }

    public int getDsrSize() {
        return dsrSize;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getDsName() {
        return dsName;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
    
    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    DatasetDescriptor(DatasetDescriptor descriptor) {
        this.dsName = descriptor.dsName;
        this.dsSize = descriptor.dsSize;
        this.dsType = descriptor.dsType;
        this.numDsr = descriptor.numDsr;
        this.spare = descriptor.spare;
        this.dsrSize = descriptor.dsrSize;
        this.dsOffset = descriptor.dsOffset;
        this.sphBytePosition = descriptor.sphBytePosition;
        this.filename = descriptor.filename;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PACKAGE
    ////////////////////////////////////////////////////////////////////////////////

    private boolean spare;
    private char dsType;
    private int dsSize;
    private long dsOffset;
    private long sphBytePosition;
    private int numDsr;
    private int dsrSize;
    private String dsName;
    private String filename;
}
