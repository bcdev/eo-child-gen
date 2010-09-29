/*
 * Created on 19.03.2004
 */
package com.bc.childgen;

/**
 * Factory class to create instances of an implementation of the <code>ChildGenerator</code> interface.
 *
 * @author Alexander Gruenewald (alexander.gruenewald@informus.de)
 */
public final class ChildGeneratorFactory {

    /**
     * Creates an instance of a <code>ChildGenerator</code> implementation.
     *
     * @param productType The product type name. Supported names start with "MER_RR", "MER_FR", "AT".
     * @return The new instance.
     */
    public static ChildGeneratorImpl createChildGenerator(String productType) throws ChildGenException {
        final ChildGeneratorImpl childGeneratorImpl = new ChildGeneratorImpl();

        final Config config = getConfigFor(productType);
        childGeneratorImpl.setConfig(config);

        return childGeneratorImpl;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    static Config getConfigFor(String productType) throws ChildGenException {
        final Config config = new Config();
        final String fileNameCapitals = productType.toUpperCase();

        if (fileNameCapitals.startsWith("MER_RR")) {
            config.setTiePointAdsName(ChildGenConstants.MERIS_TIE_PT_ADS_NAME);
            config.setQualityAdsName(ChildGenConstants.MERIS_QUALITY_ADS_NAME);
            config.setTiePointLatOffset(ChildGenConstants.MERIS_TIE_PT_LAT_OFFSET);
            config.setTiePointLonOffset(ChildGenConstants.MERIS_RR_TIE_PT_LON_OFFSET);
            config.setNumberOfGeoCoordinates(ChildGenConstants.MERIS_RR_NUM_OF_GEOCOORDS);
        } else if (fileNameCapitals.startsWith("MER_FR")) {
            config.setTiePointAdsName(ChildGenConstants.MERIS_TIE_PT_ADS_NAME);
            config.setQualityAdsName(ChildGenConstants.MERIS_QUALITY_ADS_NAME);
            config.setTiePointLatOffset(ChildGenConstants.MERIS_TIE_PT_LAT_OFFSET);
            config.setTiePointLonOffset(ChildGenConstants.MERIS_FR_TIE_PT_LON_OFFSET);
            config.setNumberOfGeoCoordinates(ChildGenConstants.MERIS_FR_NUM_OF_GEOCOORDS);
        } else if (fileNameCapitals.startsWith("AT")) {
            config.setTiePointAdsName(ChildGenConstants.AATSR_TIE_PT_ADS_NAME);
            config.setQualityAdsName(ChildGenConstants.AATSR_QUALITY_ADS_NAME);
            config.setTiePointLatOffset(ChildGenConstants.AATSR_TIE_PT_LAT_OFFSET);
            config.setTiePointLonOffset(ChildGenConstants.AATSR_TIE_PT_LON_OFFSET);
            config.setNumberOfGeoCoordinates(ChildGenConstants.AATSR_NUM_OF_GEOCOORDS);
        } else {
            throw new ChildGenException("Invalid file name: " + productType);
        }

        return config;
    }
}