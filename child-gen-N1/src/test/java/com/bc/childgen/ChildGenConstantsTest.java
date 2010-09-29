package com.bc.childgen;

import junit.framework.TestCase;

@SuppressWarnings({"MagicNumber"})
public class ChildGenConstantsTest extends TestCase {

    public void testMERISConstants() {
        assertEquals(13, ChildGenConstants.MERIS_TIE_PT_LAT_OFFSET);
        assertEquals(297, ChildGenConstants.MERIS_RR_TIE_PT_LON_OFFSET);
        assertEquals(157, ChildGenConstants.MERIS_FR_TIE_PT_LON_OFFSET);
        assertEquals("Tie points ADS              ", ChildGenConstants.MERIS_TIE_PT_ADS_NAME);
        assertEquals("Quality ADS                 ", ChildGenConstants.MERIS_QUALITY_ADS_NAME);
        assertEquals(71, ChildGenConstants.MERIS_RR_NUM_OF_GEOCOORDS);
        assertEquals(36, ChildGenConstants.MERIS_FR_NUM_OF_GEOCOORDS);
        assertEquals(3, ChildGenConstants.MERIS_LINES_PER_TIE_LENGTH);
        assertEquals(1438, ChildGenConstants.MERIS_LINES_PER_TIE_OFFSET);
    }

    public void testAatsrConstants() {
        assertEquals(20, ChildGenConstants.AATSR_TIE_PT_LAT_OFFSET);
        assertEquals(112, ChildGenConstants.AATSR_TIE_PT_LON_OFFSET);
        assertEquals("GEOLOCATION_ADS             ", ChildGenConstants.AATSR_TIE_PT_ADS_NAME);
        assertEquals("SUMMARY_QUALITY_ADS         ", ChildGenConstants.AATSR_QUALITY_ADS_NAME);
        assertEquals(23, ChildGenConstants.AATSR_NUM_OF_GEOCOORDS);
        assertEquals(32, ChildGenConstants.AATSR_LINES_PER_TIE_POINT);
    }
}
