package com.bc.childgen.modules;

import junit.framework.TestCase;


public class RoiTest extends TestCase {

    public void testSetGetFirstLine() {
        final int line_1 = 34;
        final int line_2 = 9746;

        roi.setFirstLine(line_1);
        assertEquals(line_1, roi.getFirstLine());

        roi.setFirstLine(line_2);
        assertEquals(line_2, roi.getFirstLine());
    }

    public void testSetGetLastLine() {
        final int line_1 = 104;
        final int line_2 = 33;

        roi.setLastLine(line_1);
        assertEquals(line_1, roi.getLastLine());

        roi.setLastLine(line_2);
        assertEquals(line_2, roi.getLastLine());
    }

    public void testSetGetFirstTiePointLine() {
        final int line_1 = 632;
        final int line_2 = 11;

        roi.setFirstTiePointLine(line_1);
        assertEquals(line_1, roi.getFirstTiePointLine());

        roi.setFirstTiePointLine(line_2);
        assertEquals(line_2, roi.getFirstTiePointLine());
    }

    public void testSetGetLastTiePointLine() {
        final int line_1 = 8;
        final int line_2 = 103;

        roi.setLastTiePointLine(line_1);
        assertEquals(line_1, roi.getLastTiePointLine());

        roi.setLastTiePointLine(line_2);
        assertEquals(line_2, roi.getLastTiePointLine());
    }

    public void testIsValid() {
        roi.setFirstLine(12);
        roi.setLastLine(23);
        assertTrue(roi.isValid());

        roi.setFirstLine(-5);
        assertFalse(roi.isValid());
        
        roi.setFirstLine(-1);
        assertFalse(roi.isValid());

        roi.setFirstLine(19);
        roi.setLastLine(18);
        assertFalse(roi.isValid());
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private Roi roi;

    protected void setUp() {
        roi = new Roi();
    }
}
