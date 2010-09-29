package com.bc.childgen;
/*
import com.bc.ceres.core.ProgressMonitor;
import junit.framework.Assert;
import org.esa.beam.framework.datamodel.*;

import java.io.IOException;
*/
@SuppressWarnings({"MagicNumber"})
class ProductTester {
/*
    protected static final String MPH = "MPH";
    protected static final String SPH = "SPH";

    protected static float getSampleBandValue(Product childProduct, int bandIndex) throws IOException {
        float[] sample = new float[1];
        final Band band = childProduct.getBandAt(bandIndex);
        band.readPixels(56, 34, 1, 1, sample, ProgressMonitor.NULL);
        return sample[0];
    }

    protected static boolean isSamplePixelValid(Product childProduct, int bandIndex) {
        final Band band = childProduct.getBandAt(bandIndex);
        return band.isPixelValid(56, 34);
    }

    protected static float getSampleTiePointValue(Product childProduct, int grindIndex) {
        final float[] sample = new float[1];
        final TiePointGrid tiePointGrid = childProduct.getTiePointGridAt(grindIndex);
        tiePointGrid.getPixels(134, 399, 1, 1, sample, ProgressMonitor.NULL);
        return sample[0];
    }

    protected static String getStringAttribute(MetadataElement mph, String attribName) {
        return mph.getAttribute(attribName).getData().getElemString();
    }

    protected static String getDateAttributeAsString(MetadataElement mph, String attributeName) {
        return mph.getAttribute(attributeName).getData().getElemString();
    }

    protected static int getIntAttribute(MetadataElement mph, String attributeName) {
        return mph.getAttribute(attributeName).getData().getElemInt();
    }

    protected static double getDoubleAttribute(MetadataElement mph, String attributeName) {
        return mph.getAttribute(attributeName).getData().getElemDouble();
    }

    protected static long getLongAttribute(MetadataElement mph, String attributeName) {
        return mph.getAttribute(attributeName).getData().getElemUInt();
    }

    protected static void assertDSD(MetadataElement dsd, String datasetName, String datasetType, String fileName,
                                    long offset, long size, int numRecords, int recordSize) {
        Assert.assertEquals(datasetName, getStringAttribute(dsd, "DATASET_NAME"));
        Assert.assertEquals(datasetType, getStringAttribute(dsd, "DATASET_TYPE"));
        Assert.assertEquals(fileName, getStringAttribute(dsd, "FILE_NAME"));
        Assert.assertEquals(offset, getLongAttribute(dsd, "OFFSET"));
        Assert.assertEquals(size, getLongAttribute(dsd, "SIZE"));
        Assert.assertEquals(numRecords, getIntAttribute(dsd, "NUM_RECORDS"));
        Assert.assertEquals(recordSize, getIntAttribute(dsd, "RECORD_SIZE"));
    }

    protected static void testGeoCoding(Product product, double expLon, double expLat, double expX, double expY) {
        final GeoCoding geoCoding = product.getGeoCoding();
        Assert.assertFalse(geoCoding.isCrossingMeridianAt180());
        Assert.assertEquals("WGS-84", geoCoding.getDatum().getName());
        final GeoPos geoPos = new GeoPos();
        geoCoding.getGeoPos(new PixelPos(100, 200), geoPos);
        Assert.assertEquals(expLat, geoPos.getLat(), 1e-5);
        Assert.assertEquals(expLon, geoPos.getLon(), 1e-4);

        final PixelPos pixelPos = new PixelPos();
        geoCoding.getPixelPos(geoPos, pixelPos);
        // @todo should be 8 decimals but there seems to be a beam issue here with interpolation states
        Assert.assertEquals(expX, pixelPos.getX(), 1);
        Assert.assertEquals(expY, pixelPos.getY(), 1);
    }
*/
}
