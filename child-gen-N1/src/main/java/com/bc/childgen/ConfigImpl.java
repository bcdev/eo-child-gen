package com.bc.childgen;

class ConfigImpl implements Config {

    void setTiePointAdsName(String tiePointAdsName) {
        this.tiePointAdsName = tiePointAdsName;
    }

    public String getTiePointAdsName() {
        return tiePointAdsName;
    }

    void setQualityAdsName(String qualityAdsName) {
        this.qualityAdsName = qualityAdsName;
    }

    public String getQualityAdsName() {
        return qualityAdsName;
    }

    void setTiePointLatOffset(int tiePointLatOffset) {
        this.tiePointLatOffset = tiePointLatOffset;
    }

    public int getTiePointLatOffset() {
        return tiePointLatOffset;
    }

    void setTiePointLonOffset(int tiePointLonOffset) {
        this.tiePointLonOffset = tiePointLonOffset;
    }

    public int getTiePointLonOffset() {
        return tiePointLonOffset;
    }

    void setNumberOfGeoCoordinates(int numberOfGeoCoordinates) {
        this.numberOfGeoCoordinates = numberOfGeoCoordinates;
    }

    public int getNumberOfGeoCoordinates() {
        return numberOfGeoCoordinates;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private String tiePointAdsName;
    private String qualityAdsName;
    private int tiePointLatOffset = -1;
    private int tiePointLonOffset = -1;
    private int numberOfGeoCoordinates = -1;
}
