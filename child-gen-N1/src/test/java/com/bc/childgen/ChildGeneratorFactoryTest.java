package com.bc.childgen;

import junit.framework.TestCase;

public class ChildGeneratorFactoryTest extends TestCase {

    public void testGetConfigFor_MERIS_RR() throws ChildGenException {
        final Config config = ChildGeneratorFactory.getConfigFor("MER_RR__1PNPDE20051019_050112_000018682041_00420_19011_7432.N1");
        assertNotNull(config);

        assertEquals(ChildGenConstants.MERIS_TIE_PT_ADS_NAME, config.getTiePointAdsName());
        assertEquals(ChildGenConstants.MERIS_QUALITY_ADS_NAME, config.getQualityAdsName());
        assertEquals(ChildGenConstants.MERIS_TIE_PT_LAT_OFFSET, config.getTiePointLatOffset());
        assertEquals(ChildGenConstants.MERIS_RR_TIE_PT_LON_OFFSET, config.getTiePointLonOffset());
        assertEquals(ChildGenConstants.MERIS_RR_NUM_OF_GEOCOORDS, config.getNumberOfGeoCoordinates());            
    }

    public void testGetConfigFor_MERIS_FR() throws ChildGenException {
        final Config config = ChildGeneratorFactory.getConfigFor("MER_FR__1PNDPA20050412_093736_000000982036_00208_16294_6129.N1");
        assertNotNull(config);

        assertEquals(ChildGenConstants.MERIS_TIE_PT_ADS_NAME, config.getTiePointAdsName());
        assertEquals(ChildGenConstants.MERIS_QUALITY_ADS_NAME, config.getQualityAdsName());
        assertEquals(ChildGenConstants.MERIS_TIE_PT_LAT_OFFSET, config.getTiePointLatOffset());
        assertEquals(ChildGenConstants.MERIS_FR_TIE_PT_LON_OFFSET, config.getTiePointLonOffset());
        assertEquals(ChildGenConstants.MERIS_FR_NUM_OF_GEOCOORDS, config.getNumberOfGeoCoordinates());
    }

    public void testGetConfigFor_MERIS_FRS() throws ChildGenException {
        final Config config = ChildGeneratorFactory.getConfigFor("MER_FRS_1PNUPA20050822_135243_000001122040_00096_18186_3499.N1");
        assertNotNull(config);

        assertEquals(ChildGenConstants.MERIS_TIE_PT_ADS_NAME, config.getTiePointAdsName());
        assertEquals(ChildGenConstants.MERIS_QUALITY_ADS_NAME, config.getQualityAdsName());
        assertEquals(ChildGenConstants.MERIS_TIE_PT_LAT_OFFSET, config.getTiePointLatOffset());
        assertEquals(ChildGenConstants.MERIS_FRS_TIE_PT_LON_OFFSET, config.getTiePointLonOffset());
        assertEquals(ChildGenConstants.MERIS_FRS_NUM_OF_GEOCOORDS, config.getNumberOfGeoCoordinates());
    }

     public void testGetConfigFor_MERIS_ATSR() throws ChildGenException {
        final Config config = ChildGeneratorFactory.getConfigFor("ATS_NR__2PNPDK20060329_103452_000065272046_00223_21319_0188.N1");
        assertNotNull(config);

        assertEquals(ChildGenConstants.AATSR_TIE_PT_ADS_NAME, config.getTiePointAdsName());
        assertEquals(ChildGenConstants.AATSR_QUALITY_ADS_NAME, config.getQualityAdsName());
        assertEquals(ChildGenConstants.AATSR_TIE_PT_LAT_OFFSET, config.getTiePointLatOffset());
        assertEquals(ChildGenConstants.AATSR_TIE_PT_LON_OFFSET, config.getTiePointLonOffset());
        assertEquals(ChildGenConstants.AATSR_NUM_OF_GEOCOORDS, config.getNumberOfGeoCoordinates());
    }

    public void testGetConfigFor_unknown() {
        try {
            ChildGeneratorFactory.getConfigFor("rimbelzack");
            fail("ChildGenException expected");
        } catch (ChildGenException expected) {
        }
    }

}
