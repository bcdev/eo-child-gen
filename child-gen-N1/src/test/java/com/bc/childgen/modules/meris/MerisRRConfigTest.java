package com.bc.childgen.modules.meris;

import junit.framework.TestCase;

public class MerisRRConfigTest extends TestCase {

    public void testValues() {
        final MerisRRConfig config = new MerisRRConfig();

        assertEquals("Tie points ADS              ", config.getTiePointAdsName());
        assertEquals("Quality ADS                 ", config.getQualityAdsName());
        assertEquals(13, config.getTiePointLatOffset());
        assertEquals(297, config.getTiePointLonOffset());
        assertEquals(71, config.getNumberOfGeoCoordinates());
    }
}
