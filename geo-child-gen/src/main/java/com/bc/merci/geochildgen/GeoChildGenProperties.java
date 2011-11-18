package com.bc.merci.geochildgen;

import com.bc.util.sql.DataSourceConfig;


public class GeoChildGenProperties {

    static final String CHILD_PRODUCT_ORIGINATOR_ID_DEFAULT = "BCG";

    public String getChildProductOriginatorId() {
        return childProductOriginatorId;
    }

    public void setChildProductOriginatorId(final String childProductOriginatorId) {
        if (childProductOriginatorId != null) {
            this.childProductOriginatorId = childProductOriginatorId.toUpperCase();
        }
    }

    public String[] getGeometry() {
        return geometries;
    }

    public void setGeometry(final String[] geometries) {
        if (geometries != null) {
            this.geometries = geometries;
        }
    }

    public void setSourceOption(String sourceOption) {
        this.sourceOption = sourceOption;
    }

    public String getSourceOption() {
        return sourceOption;
    }

    public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private String childProductOriginatorId = CHILD_PRODUCT_ORIGINATOR_ID_DEFAULT;
    private String[] geometries = {};
    private String sourceOption;
    private DataSourceConfig dataSourceConfig;
}
