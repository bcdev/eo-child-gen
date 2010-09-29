package com.bc.childgen.sqad;

import junit.framework.TestCase;

@SuppressWarnings({"MagicNumber"})
public class Meris_L1_SqadTest extends TestCase {

    public void testgetRawData() {
        final byte[] buffer = sqad.getRawData();
        assertEquals(33, buffer.length);
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

    public void testSetOutOfRangeFlags() {
        final String outOfRangeFlags = "1234567890";

        sqad.setOutOfRangeFlags(outOfRangeFlags);
        assertEquals(outOfRangeFlags, new String(sqad.getRawData(), 13, 10));
    }

    public void testSetOutOfRangeBlankFlags() {
        final String outOfRangeBlankFlags = "1234567890";

        sqad.setOutOfRangeBlankFlags(outOfRangeBlankFlags);
        assertEquals(outOfRangeBlankFlags, new String(sqad.getRawData(), 23, 10));
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private Meris_L1_Sqad sqad;

    protected void setUp() {
        sqad = new Meris_L1_Sqad();
    }
}
