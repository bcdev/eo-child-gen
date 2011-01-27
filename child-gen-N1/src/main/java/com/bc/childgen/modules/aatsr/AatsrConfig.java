package com.bc.childgen.modules.aatsr;

import com.bc.childgen.Config;

public class AatsrConfig implements Config {
    private static final String QUALITY_ADS_NAME = "SUMMARY_QUALITY_ADS         ";
    private static final int TIE_POINT_LAT_OFFSET = 20;
    private static final int TIE_POINT_LON_OFFSET = 112;
    private static final int NUMBER_OF_GEO_COORDINATES = 23;

    @Override
    public String getTiePointAdsName() {
        return Constants.TIE_PT_ADS_NAME;
    }

    @Override
    public String getQualityAdsName() {
        return QUALITY_ADS_NAME;
    }

    @Override
    public int getTiePointLatOffset() {
        return TIE_POINT_LAT_OFFSET;
    }

    @Override
    public int getTiePointLonOffset() {
        return TIE_POINT_LON_OFFSET;
    }

    @Override
    public int getNumberOfGeoCoordinates() {
        return NUMBER_OF_GEO_COORDINATES;
    }
}
