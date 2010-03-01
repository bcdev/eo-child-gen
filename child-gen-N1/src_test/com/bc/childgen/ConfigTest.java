package com.bc.childgen;

import junit.framework.TestCase;

@SuppressWarnings({"MagicNumber"})
public class ConfigTest extends TestCase {

    public void testSetGetTiePointAdsName() {
        final String name_1 = "blablabla";
        final String name_2 = "humpelmumpft";

        config.setTiePointAdsName(name_1);
        assertEquals(name_1, config.getTiePointAdsName());

        config.setTiePointAdsName(name_2);
        assertEquals(name_2, config.getTiePointAdsName());
    }

    public void testSetGetQualityAdsName() {
        final String name_1 = "hibbeligeNahm";
        final String name_2 = "TomUndKlaus";

        config.setQualityAdsName(name_1);
        assertEquals(name_1, config.getQualityAdsName());

        config.setQualityAdsName(name_2);
        assertEquals(name_2, config.getQualityAdsName());
    }

    public void testSetGetTiePointLatOffset() {
        final int offset_1 = 44;
        final int offset_2 = 92;

        config.setTiePointLatOffset(offset_1);
        assertEquals(offset_1, config.getTiePointLatOffset());

        config.setTiePointLatOffset(offset_2);
        assertEquals(offset_2, config.getTiePointLatOffset());
    }

    public void testSetGetTiePointLonOffset() {
        final int offset_1 = 198;
        final int offset_2 = 3355;

        config.setTiePointLonOffset(offset_1);
        assertEquals(offset_1, config.getTiePointLonOffset());

        config.setTiePointLonOffset(offset_2);
        assertEquals(offset_2, config.getTiePointLonOffset());
    }

    public void testSetGetNumberOfGeoCoordinates() {
        final int num_1 = 33;
        final int num_2 = 82;

        config.setNumberOfGeoCoordinates(num_1);
        assertEquals(num_1, config.getNumberOfGeoCoordinates());

        config.setNumberOfGeoCoordinates(num_2);
        assertEquals(num_2, config.getNumberOfGeoCoordinates());
    }

    public void testDefaultConstruction() {
        assertEquals(-1, config.getTiePointLatOffset());
        assertEquals(-1, config.getTiePointLonOffset());
        assertEquals(-1, config.getNumberOfGeoCoordinates());
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private Config config;

    protected void setUp() {
        config = new Config();
    }
}
