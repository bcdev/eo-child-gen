package com.bc.childgen.sqad;

import junit.framework.TestCase;

public class Atsr_L1_SqadTest extends TestCase {

    public void testGetRawData() {
        final byte[] buffer = sqad.getRawData();
        assertEquals(86, buffer.length);
    }

    public void testSetDsrTime() {
        final String dateString = "123456789012";
        sqad.setDsrTime(dateString);
        assertEquals(dateString, new String(sqad.getRawData(), 0, 12));
    }

    public void testSetAttachmentFlag() {
        sqad.setAttachmentFlag(true);
        assertEquals(1, (int) sqad.getRawData()[12]);

        sqad.setAttachmentFlag(false);
        assertEquals(0, (int) sqad.getRawData()[12]);
    }

    public void testSetImageScanNumber() {
        sqad.setImageScanNumber((short) 33);
        assertEquals(33, getShortFromSqadBuffer(16));
    }

    public void testSetNadirViewNumberOfNullPacketScans() {
        sqad.setNadirViewNumberOfNullPacketScans((short) 34);
        assertEquals(34, getShortFromSqadBuffer(18));
    }

    public void testSetNadirViewNumberOfValidationFailedScans() {
        sqad.setNadirViewNumberOfValidationFailedScans((short) 35);
        assertEquals(35, getShortFromSqadBuffer(20));
    }

    public void testSetNadirViewNumberOfCRCFailedScans() {
        sqad.setNadirViewNumberOfCRCFailedScans((short) 36);
        assertEquals(36, getShortFromSqadBuffer(22));
    }

    public void testSetNadirViewNumberOfBufferFullScans() {
        sqad.setNadirViewNumberOfBufferFullScans((short) 37);
        assertEquals(37, getShortFromSqadBuffer(24));
    }

    public void testSetNadirViewNumberOfScanJitterScans() {
        sqad.setNadirViewNumberOfScanJitterScans((short) 38);
        assertEquals(38, getShortFromSqadBuffer(26));
    }

//    public void testSetNadirPacketValidationOtherErrors() {
//        sqad.setNadirViewPacketValidationOtherErrors((short) 39);
//        assertEquals(39, getShortFromSqadBuffer(34));
//    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private Atsr_L1_Sqad sqad;

    protected void setUp() {
        sqad = new Atsr_L1_Sqad();
    }

    private short getShortFromSqadBuffer(int index) {
        byte[] shortBuffer = new byte[2];
        System.arraycopy(sqad.getRawData(), index, shortBuffer, 0, 2);
        return (short) (((shortBuffer[1] & 0xff) << 8) | ((shortBuffer[0] & 0xff)));
    }
}
