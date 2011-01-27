package com.bc.childgen.modules.meris;

import com.bc.childgen.Config;

public class MerisFRSConfig implements Config {

    @Override
    public String getTiePointAdsName() {
        return Constants.TIE_POINTS_ADS_NAME; 
    }

    @Override
    public String getQualityAdsName() {
        return Constants.QUALITY_ADS_NAME;
    }

    @Override
    public int getTiePointLatOffset() {
        return Constants.TIE_PT_LAT_OFFSET;
    }

    @Override
    public int getTiePointLonOffset() {
        return Constants.TIE_PT_LON_OFFSET_FRS;
    }

    @Override
    public int getNumberOfGeoCoordinates() {
        return Constants.NUM_GEO_COORDS_FRS;
    }
}
