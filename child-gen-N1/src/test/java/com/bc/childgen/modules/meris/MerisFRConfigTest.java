package com.bc.childgen.modules.meris;

import junit.framework.TestCase;

public class MerisFRConfigTest extends TestCase {

    public void testValues() {
        final MerisFRConfig config = new MerisFRConfig();

        assertEquals("Tie points ADS              ", config.getTiePointAdsName());
        assertEquals("Quality ADS                 ", config.getQualityAdsName());
        assertEquals(13, config.getTiePointLatOffset());
        assertEquals(157, config.getTiePointLonOffset());
        assertEquals(36, config.getNumberOfGeoCoordinates());
    }
}
