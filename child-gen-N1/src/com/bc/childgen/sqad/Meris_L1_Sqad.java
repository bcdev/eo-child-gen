package com.bc.childgen.sqad;

public class Meris_L1_Sqad extends Sqad {

    public Meris_L1_Sqad() {
        super(SQAD_BYTE_SIZE);
    }

    public void setOutOfRangeFlags(String outOfRangeFlags) {
        System.arraycopy(outOfRangeFlags.getBytes(), 0, buffer, 13, 10);
    }

     public void setOutOfRangeBlankFlags(String outOfRangeBlankFlags) {
        System.arraycopy(outOfRangeBlankFlags.getBytes(), 0, buffer, 23, 10);
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private static final int SQAD_BYTE_SIZE = 33;
}
