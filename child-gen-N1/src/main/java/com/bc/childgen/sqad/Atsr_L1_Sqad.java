package com.bc.childgen.sqad;

public class Atsr_L1_Sqad extends Sqad {

    public Atsr_L1_Sqad() {
        super(86);
    }

    public void setImageScanNumber(short scanNumber) {
        writeShortToBuffer(scanNumber, 16);
    }

    public void setNadirViewNumberOfNullPacketScans(short numberOfScans) {
        writeShortToBuffer(numberOfScans, 18);
    }

    public void setNadirViewNumberOfValidationFailedScans(short numberOfScans) {
        writeShortToBuffer(numberOfScans, 20);
    }

    public void setNadirViewNumberOfCRCFailedScans(short numberOfScans) {
        writeShortToBuffer(numberOfScans, 22);
    }

    public void setNadirViewNumberOfBufferFullScans(short numberOfScans) {
        writeShortToBuffer(numberOfScans, 24);
    }

    public void setNadirViewNumberOfScanJitterScans(short numberOfScans) {
        writeShortToBuffer(numberOfScans, 26);
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private static final int BIT_MASK = 0xff;

    private void writeShortToBuffer(short scanNumber, int offset) {
        buffer[offset] = (byte)(scanNumber & BIT_MASK);
        scanNumber = (short) (scanNumber >> 8);
        buffer[offset + 1] = (byte)(scanNumber & BIT_MASK);
    }

    // AATSR L1b SQAD
    // NADIR UTC                                12  0
    // att flag                                 1   12
    // spare                                    3   13
    // image scan number                        2   16
    // setNadirViewNumberOfNullPacketScans      2   18
    // NadirViewNumberOfValidationFailedScans   2   20
    // NadirViewNumberOfCRCFailedScans          2   22
    // NadirViewNumberOfBufferFullScans         2   24
    // NadirViewNumberOfScanJitterScans         2   26
    // *spare* 8 bytes
    // NadirViewPacketValidationOtherErrors     2   34
}
