package com.bc.childgen;

import junit.framework.TestCase;

public class ChildGeneratorFactoryTest extends TestCase {

    public void testGetConfigFor_MERIS_RR() throws ChildGenException {
        final Config config = ChildGeneratorFactory.getConfigFor("MER_RR__1PNPDE20051019_050112_000018682041_00420_19011_7432.N1");
        assertNotNull(config);

        assertEquals("Tie points ADS              ", config.getTiePointAdsName());
        assertEquals("Quality ADS                 ", config.getQualityAdsName());
        assertEquals(13, config.getTiePointLatOffset());
        assertEquals(297, config.getTiePointLonOffset());
        assertEquals(71, config.getNumberOfGeoCoordinates());            
    }

    public void testGetConfigFor_MERIS_FR() throws ChildGenException {
        final Config config = ChildGeneratorFactory.getConfigFor("MER_FR__1PNDPA20050412_093736_000000982036_00208_16294_6129.N1");
        assertNotNull(config);

        assertEquals("Tie points ADS              ", config.getTiePointAdsName());
        assertEquals("Quality ADS                 ", config.getQualityAdsName());
        assertEquals(13, config.getTiePointLatOffset());
        assertEquals(157, config.getTiePointLonOffset());
        assertEquals(36, config.getNumberOfGeoCoordinates());
    }

    public void testGetConfigFor_MERIS_FRS() throws ChildGenException {
        final Config config = ChildGeneratorFactory.getConfigFor("MER_FRS_1PNUPA20050822_135243_000001122040_00096_18186_3499.N1");
        assertNotNull(config);

        assertEquals("Tie points ADS              ", config.getTiePointAdsName());
        assertEquals("Quality ADS                 ", config.getQualityAdsName());
        assertEquals(13, config.getTiePointLatOffset());
        assertEquals(297, config.getTiePointLonOffset());
        assertEquals(71, config.getNumberOfGeoCoordinates());
    }

     public void testGetConfigFor_MERIS_ATSR() throws ChildGenException {
        final Config config = ChildGeneratorFactory.getConfigFor("ATS_NR__2PNPDK20060329_103452_000065272046_00223_21319_0188.N1");
        assertNotNull(config);

        assertEquals("GEOLOCATION_ADS             ", config.getTiePointAdsName());
        assertEquals("SUMMARY_QUALITY_ADS         ", config.getQualityAdsName());
        assertEquals(20, config.getTiePointLatOffset());
        assertEquals(112, config.getTiePointLonOffset());
        assertEquals(23, config.getNumberOfGeoCoordinates());
    }

    public void testGetConfigFor_unknown() {
        try {
            ChildGeneratorFactory.getConfigFor("rimbelzack");
            fail("ChildGenException expected");
        } catch (ChildGenException expected) {
        }
    }

}
