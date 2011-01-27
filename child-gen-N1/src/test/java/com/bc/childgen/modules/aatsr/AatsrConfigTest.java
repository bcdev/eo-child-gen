package com.bc.childgen.modules.aatsr;

import junit.framework.TestCase;

public class AatsrConfigTest extends TestCase {

    public void testValues() {
        final AatsrConfig config = new AatsrConfig();

        assertEquals("GEOLOCATION_ADS             ", config.getTiePointAdsName());
        assertEquals("SUMMARY_QUALITY_ADS         ", config.getQualityAdsName());
        assertEquals(20, config.getTiePointLatOffset());
        assertEquals(112, config.getTiePointLonOffset());
        assertEquals(23, config.getNumberOfGeoCoordinates());
    }
}
