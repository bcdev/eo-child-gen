package com.bc.childgen;

import junit.framework.Assert;
import org.esa.beam.framework.datamodel.MetadataElement;
import org.esa.beam.framework.datamodel.Product;
import org.esa.beam.framework.datamodel.ProductData;
import org.esa.beam.framework.datamodel.TiePointGrid;

import java.io.IOException;

@SuppressWarnings({"MagicNumber"})
class AatsrProductTester extends ProductTester {

    static void test_ATS_NR_2P_Basics(Product product) {
        Assert.assertEquals(ATS_NR__2P_CHILD_PRODUCT_NAME, product.getName());
        Assert.assertEquals(512, product.getSceneRasterWidth());
        Assert.assertEquals(512, product.getSceneRasterHeight());
    }

    static void test_NR_2P_BandNames(Product product) {
        final String[] bandNames = product.getBandNames();
        Assert.assertEquals(7, bandNames.length);
        Assert.assertEquals("sst_nadir", bandNames[0]);
        Assert.assertEquals("sst_comb", bandNames[1]);
        Assert.assertEquals("cloud_top_temp", bandNames[2]);
        Assert.assertEquals("cloud_top_height", bandNames[3]);
        Assert.assertEquals("lst", bandNames[4]);
        Assert.assertEquals("ndvi", bandNames[5]);
        Assert.assertEquals("flags", bandNames[6]);
    }

    static void test_ATS_NR_2P_BandValues(Product product) throws IOException {
        Assert.assertFalse(isSamplePixelValid(product, 0));
        Assert.assertFalse(isSamplePixelValid(product, 1));
        Assert.assertFalse(isSamplePixelValid(product, 2));
        Assert.assertFalse(isSamplePixelValid(product, 3));
        //Assert.assertEquals(0.0, getSampleBandValue(product, 3), 1e-8);
        Assert.assertEquals(306.0, getSampleBandValue(product, 4), 1e-8);
        Assert.assertEquals(0.09079999476671219, getSampleBandValue(product, 5), 1e-8);
        Assert.assertEquals(21.0, getSampleBandValue(product, 6), 1e-8);
    }

    static void test_TiePointNames(Product product) {
        final String[] tiePointGridNames = product.getTiePointGridNames();
        Assert.assertEquals(15, tiePointGridNames.length);
        Assert.assertEquals("latitude", tiePointGridNames[0]);
        Assert.assertEquals("longitude", tiePointGridNames[1]);
        Assert.assertEquals("lat_corr_nadir", tiePointGridNames[2]);
        Assert.assertEquals("lon_corr_nadir", tiePointGridNames[3]);
        Assert.assertEquals("lat_corr_fward", tiePointGridNames[4]);
        Assert.assertEquals("lon_corr_fward", tiePointGridNames[5]);
        Assert.assertEquals("altitude", tiePointGridNames[6]);
        Assert.assertEquals("sun_elev_nadir", tiePointGridNames[7]);
        Assert.assertEquals("view_elev_nadir", tiePointGridNames[8]);
        Assert.assertEquals("sun_azimuth_nadir", tiePointGridNames[9]);
        Assert.assertEquals("view_azimuth_nadir", tiePointGridNames[10]);
        Assert.assertEquals("sun_elev_fward", tiePointGridNames[11]);
        Assert.assertEquals("view_elev_fward", tiePointGridNames[12]);
        Assert.assertEquals("sun_azimuth_fward", tiePointGridNames[13]);
        Assert.assertEquals("view_azimuth_fward", tiePointGridNames[14]);
    }

    static void test_ATS_NR_2P_TiePointValues(Product product) {
        final TiePointGrid[] tiePointGrids = product.getTiePointGrids();
        Assert.assertEquals(15, tiePointGrids.length);
        Assert.assertEquals(20.227096557617188, getSampleTiePointValue(product, 0), 1e-8);
        Assert.assertEquals(-12.260025978088379, getSampleTiePointValue(product, 1), 1e-8);
        Assert.assertEquals(-3.4201875678263605E-5, getSampleTiePointValue(product, 2), 1e-8);
        Assert.assertEquals(8.30303761176765E-4, getSampleTiePointValue(product, 3), 1e-8);
        Assert.assertEquals(0.00590574461966753, getSampleTiePointValue(product, 4), 1e-8);
        Assert.assertEquals(0.0022667627781629562, getSampleTiePointValue(product, 5), 1e-8);
        Assert.assertEquals(491.7456359863281, getSampleTiePointValue(product, 6), 1e-8);
        Assert.assertEquals(56.92633056640625, getSampleTiePointValue(product, 7), 1e-8);
        Assert.assertEquals(79.99713897705078, getSampleTiePointValue(product, 8), 1e-8);
        Assert.assertEquals(116.7018814086914, getSampleTiePointValue(product, 9), 1e-8);
        Assert.assertEquals(92.59154510498047, getSampleTiePointValue(product, 10), 1e-8);
        Assert.assertEquals(56.46405029296875, getSampleTiePointValue(product, 11), 1e-8);
        Assert.assertEquals(35.27763366699219, getSampleTiePointValue(product, 12), 1e-8);
        Assert.assertEquals(116.1793441772461, getSampleTiePointValue(product, 13), 1e-8);
        Assert.assertEquals(19.898061752319336, getSampleTiePointValue(product, 14), 1e-8);
    }

    static void test_ATS_NR_2P_GeoCoding(Product product) {
        testGeoCoding(product, -12.170738220214844, 22.06627655029297, 100.03973388671875, 199.9920196533203);
    }

    static void test_ATS_NR_2P_MPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement mph = metadataRoot.getElement(MPH);
        Assert.assertNotNull(mph);
        Assert.assertEquals(ATS_NR__2P_CHILD_PRODUCT_NAME, getStringAttribute(mph, Constants.PRODUCT));
        Assert.assertEquals("N", getStringAttribute(mph, Constants.PROC_STAGE));
        Assert.assertEquals("PO-RS-MDA-GS-2009_3/H  ", getStringAttribute(mph, Constants.REF_DOC));
        Assert.assertEquals("PDHS-K              ", getStringAttribute(mph, Constants.ACQUISITION_STATION));
        Assert.assertEquals("PDHS-K", getStringAttribute(mph, Constants.PROC_CENTER));
        Assert.assertEquals("29-MAR-2006 13:01:00.205101", getDateAttributeAsString(mph, Constants.PROC_TIME));
        Assert.assertEquals("AATSR/5.59    ", getStringAttribute(mph, Constants.SOFTWARE_VER));
        Assert.assertEquals("29-MAR-2006 10:55:59.719250", getDateAttributeAsString(mph, Constants.SENSING_START));
        Assert.assertEquals("29-MAR-2006 10:57:16.519250", getDateAttributeAsString(mph, "SENSING_STOP"));
        Assert.assertEquals("2", getStringAttribute(mph, "PHASE"));
        Assert.assertEquals(46, getIntAttribute(mph, "CYCLE"));
        Assert.assertEquals(223, getIntAttribute(mph, "REL_ORBIT"));
        Assert.assertEquals(21319, getIntAttribute(mph, "ABS_ORBIT"));
        Assert.assertEquals("29-MAR-2006 11:52:58.467950", getDateAttributeAsString(mph, "STATE_VECTOR_TIME"));
        Assert.assertEquals(0.27056, getDoubleAttribute(mph, "DELTA_UT1"), 1e-8);
        Assert.assertEquals(-6312564.59, getDoubleAttribute(mph, "X_POSITION"), 1e-8);
        Assert.assertEquals(3390148.182, getDoubleAttribute(mph, "Y_POSITION"), 1e-8);
        Assert.assertEquals(-3827.491, getDoubleAttribute(mph, "Z_POSITION"), 1e-8);
        Assert.assertEquals(776.183311, getDoubleAttribute(mph, "X_VELOCITY"), 1e-8);
        Assert.assertEquals(1435.518384, getDoubleAttribute(mph, "Y_VELOCITY"), 1e-8);
        Assert.assertEquals(7377.256702, getDoubleAttribute(mph, "Z_VELOCITY"), 1e-8);
        Assert.assertEquals("FP", getStringAttribute(mph, "VECTOR_SOURCE"));
        Assert.assertEquals("29-MAR-2006 08:55:49.451009", getDateAttributeAsString(mph, "UTC_SBT_TIME"));
        Assert.assertEquals(2882244608L, getLongAttribute(mph, "SAT_BINARY_TIME"));
        Assert.assertEquals(3906249795L, getLongAttribute(mph, "CLOCK_STEP"));
        Assert.assertEquals("31-DEC-2005 23:59:59.000000", getDateAttributeAsString(mph, "LEAP_UTC"));
        Assert.assertEquals(1, getIntAttribute(mph, "LEAP_SIGN"));
        Assert.assertEquals(0, getIntAttribute(mph, "LEAP_ERR"));
        Assert.assertEquals(0, getIntAttribute(mph, "PRODUCT_ERR"));
        Assert.assertEquals(1692761L, getLongAttribute(mph, "TOT_SIZE"));
        Assert.assertEquals(5830, getIntAttribute(mph, "SPH_SIZE"));
        Assert.assertEquals(13, getIntAttribute(mph, "NUM_DSD"));
        Assert.assertEquals(280, getIntAttribute(mph, "DSD_SIZE"));
        Assert.assertEquals(8, getIntAttribute(mph, "NUM_DATA_SETS"));
    }

    static void test_ATS_NR_2P_SPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        MetadataElement sph = metadataRoot.getElement(SPH);
        Assert.assertNotNull(sph);
        Assert.assertEquals("GSST                        ", getStringAttribute(sph, "SPH_DESCRIPTOR"));
        Assert.assertEquals(0, getIntAttribute(sph, "STRIPLINE_CONTINUITY_INDICATOR"));
        Assert.assertEquals(1, getIntAttribute(sph, "SLICE_POSITION"));
        Assert.assertEquals(1, getIntAttribute(sph, "NUM_SLICES"));
        Assert.assertEquals("29-MAR-2006 10:55:59.719250", getDateAttributeAsString(sph, "FIRST_LINE_TIME"));
        Assert.assertEquals("29-MAR-2006 10:57:16.519250", getDateAttributeAsString(sph, "LAST_LINE_TIME"));
        Assert.assertEquals(22957426, getIntAttribute(sph, "FIRST_FIRST_LAT"));
        Assert.assertEquals(-7645876, getIntAttribute(sph, "FIRST_FIRST_LONG"));
        Assert.assertEquals(23534706, getIntAttribute(sph, "FIRST_MID_LAT"));
        Assert.assertEquals(-10259533, getIntAttribute(sph, "FIRST_MID_LONG"));
        Assert.assertEquals(24067681, getIntAttribute(sph, "FIRST_LAST_LAT"));
        Assert.assertEquals(-12895210, getIntAttribute(sph, "FIRST_LAST_LONG"));
        Assert.assertEquals(18422514, getIntAttribute(sph, "LAST_FIRST_LAT"));
        Assert.assertEquals(-8826305, getIntAttribute(sph, "LAST_FIRST_LONG"));
        Assert.assertEquals(18988375, getIntAttribute(sph, "LAST_MID_LAT"));
        Assert.assertEquals(-11365046, getIntAttribute(sph, "LAST_MID_LONG"));
        Assert.assertEquals(19519159, getIntAttribute(sph, "LAST_LAST_LAT"));
        Assert.assertEquals(-13920456, getIntAttribute(sph, "LAST_LAST_LONG"));
        Assert.assertEquals(79.400482, getDoubleAttribute(sph, "MIN_FPA_BASEPLATE_TEM"), 1e-8);
        Assert.assertEquals(79.6345976, getDoubleAttribute(sph, "MIN_12_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(79.7308424, getDoubleAttribute(sph, "MIN_11_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(79.6091994, getDoubleAttribute(sph, "MIN_3_7_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(79.5099791, getDoubleAttribute(sph, "MIN_1_6_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(263.036986, getDoubleAttribute(sph, "MIN_0_87_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(80.5058287, getDoubleAttribute(sph, "MAX_FPA_BASEPLATE_TEM"), 1e-8);
        Assert.assertEquals(80.2853392, getDoubleAttribute(sph, "MAX_12_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(80.385269, getDoubleAttribute(sph, "MAX_11_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(80.2617186, getDoubleAttribute(sph, "MAX_3_7_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(80.2563246, getDoubleAttribute(sph, "MAX_1_6_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(263.68997, getDoubleAttribute(sph, "MAX_0_87_MICRON_DETECTOR_TEMP"), 1e-8);

        checkCommonAtsrSPHElements(sph);
    }

    static void test_ATS_NR_2P_DSDs(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement dsds = metadataRoot.getElement("DSD");
        Assert.assertEquals(13, dsds.getNumElements());

        assertDSD(dsds.getElementAt(0), "SUMMARY_QUALITY_ADS", "A", "", 7077, 172, 2, 86);
        assertDSD(dsds.getElementAt(1), "GEOLOCATION_ADS", "A", "", 7249, 10642, 17, 626);
        assertDSD(dsds.getElementAt(2), "SCAN_PIXEL_X_AND_Y_ADS", "A", "", 17891, 14110, 17, 830);
        assertDSD(dsds.getElementAt(3), "NADIR_VIEW_SOLAR_ANGLES_ADS", "A", "", 32001, 3672, 17, 216);
        assertDSD(dsds.getElementAt(4), "FWARD_VIEW_SOLAR_ANGLES_ADS", "A", "", 35673, 3672, 17, 216);
        assertDSD(dsds.getElementAt(5), "NADIR_VIEW_SCAN_PIX_NUM_ADS", "A", "", 39345, 35156, 17, 2068);
        assertDSD(dsds.getElementAt(6), "FWARD_VIEW_SCAN_PIX_NUM_ADS", "A", "", 74501, 35156, 17, 2068);
        assertDSD(dsds.getElementAt(7), "DISTRIB_SST_CLOUD_LAND_MDS", "M", "", 109657, 1583104, 512, 3092);
        assertDSD(dsds.getElementAt(8), "", "?", "", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(9), "LEVEL_1B_PRODUCT", "R", "ATS_TOA_1PNPDK20060329_103452_000065272046_00223_21319_0191.N1", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(10), "PROCESSING_PARAMS_L2_FILE", "R", "ATS_PC2_AXVIEC20020123_074151_20020101_000000_20200101_000000", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(11), "RETRIEVAL_COEFS_DATA_FILE", "R", "ATS_SST_AXVIEC20051205_102103_20020101_000000_20200101_000000", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(12), "LST_COEFS_DATA_FILE", "R", "ATS_LST_AXVIEC20040311_095537_20020301_000001_20070801_235959", 0, 0, 0, 0);
    }

    static void test_ATS_NR_2P_SQADs(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement sqads = metadataRoot.getElement("SUMMARY_QUALITY_ADS");
        Assert.assertEquals(2, sqads.getNumElements());

        MetadataElement sqad = sqads.getElementAt(0);
        Assert.assertEquals("29-MAR-2006 10:55:21.319250", sqad.getAttribute("dsr_time").getData().getElemString());
        Assert.assertEquals(0, getIntAttribute(sqad, "attach_flag"));
        Assert.assertEquals(0, getIntAttribute(sqad, "scan_num"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_show_buf_full"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_jitt"));
        Assert.assertEquals(842, getIntAttribute(sqad, "per_cloud_pix"));
        Assert.assertEquals(6, getIntAttribute(sqad, "per_ndvi_inv"));
        Assert.assertEquals(0, getIntAttribute(sqad, "per_sst_for_inv"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_error"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_error"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_show_buf_full"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_jitt"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_5"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_6"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_7"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_8"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_error"));

        sqad = sqads.getElementAt(1);
        Assert.assertEquals("29-MAR-2006 10:56:38.119250", sqad.getAttribute("dsr_time").getData().getElemString());
        Assert.assertEquals(0, getIntAttribute(sqad, "attach_flag"));
        Assert.assertEquals(256, getIntAttribute(sqad, "scan_num"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_show_buf_full"));
        Assert.assertEquals(161, getIntAttribute(sqad, "pv_nad_scan_jitt"));
        Assert.assertEquals(2931, getIntAttribute(sqad, "per_cloud_pix"));
        Assert.assertEquals(12, getIntAttribute(sqad, "per_ndvi_inv"));
        Assert.assertEquals(0, getIntAttribute(sqad, "per_sst_for_inv"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_error"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_error"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_show_buf_full"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_jitt"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_5"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_6"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_7"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_8"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_error"));
    }

    static void test_ATS_TOA_1P_Basics(Product product) {
        Assert.assertEquals(ATS_TOA_1P_CHILD_PRODUCT_NAME, product.getName());
        Assert.assertEquals(512, product.getSceneRasterWidth());
        Assert.assertEquals(320, product.getSceneRasterHeight());
    }

    static void test_TOA_1P_BandNames(Product product) {
        final String[] bandNames = product.getBandNames();
        Assert.assertEquals(18, bandNames.length);
        Assert.assertEquals("btemp_nadir_1200", bandNames[0]);
        Assert.assertEquals("btemp_nadir_1100", bandNames[1]);
        Assert.assertEquals("btemp_nadir_0370", bandNames[2]);
        Assert.assertEquals("reflec_nadir_1600", bandNames[3]);
        Assert.assertEquals("reflec_nadir_0870", bandNames[4]);
        Assert.assertEquals("reflec_nadir_0670", bandNames[5]);
        Assert.assertEquals("reflec_nadir_0550", bandNames[6]);
        Assert.assertEquals("btemp_fward_1200", bandNames[7]);
        Assert.assertEquals("btemp_fward_1100", bandNames[8]);
        Assert.assertEquals("btemp_fward_0370", bandNames[9]);
        Assert.assertEquals("reflec_fward_1600", bandNames[10]);
        Assert.assertEquals("reflec_fward_0870", bandNames[11]);
        Assert.assertEquals("reflec_fward_0670", bandNames[12]);
        Assert.assertEquals("reflec_fward_0550", bandNames[13]);
        Assert.assertEquals("confid_flags_nadir", bandNames[14]);
        Assert.assertEquals("confid_flags_fward", bandNames[15]);
        Assert.assertEquals("cloud_flags_nadir", bandNames[16]);
        Assert.assertEquals("cloud_flags_fward", bandNames[17]);
    }

    static void test_ATS_TOA_1P_BandValues(Product product) throws IOException {
        Assert.assertEquals(257.42999267578125, getSampleBandValue(product, 0), 1e-8);
        Assert.assertEquals(258.41998291015625, getSampleBandValue(product, 1), 1e-8);
        Assert.assertEquals(255.11000061035156, getSampleBandValue(product, 2), 1e-8);
        Assert.assertEquals(0.03999999910593033, getSampleBandValue(product, 3), 1e-8);
        Assert.assertEquals(0.14000000059604645, getSampleBandValue(product, 4), 1e-8);
        Assert.assertEquals(0.14999999105930328, getSampleBandValue(product, 5), 1e-8);
        Assert.assertEquals(0.07999999821186066, getSampleBandValue(product, 6), 1e-8);
        Assert.assertEquals(256.5199890136719, getSampleBandValue(product, 7), 1e-8);
        Assert.assertEquals(257.0899963378906, getSampleBandValue(product, 8), 1e-8);
        Assert.assertEquals(253.01998901367188, getSampleBandValue(product, 9), 1e-8);
        Assert.assertEquals(0.04999999701976776, getSampleBandValue(product, 10), 1e-8);
        Assert.assertEquals(0.2800000011920929, getSampleBandValue(product, 11), 1e-8);
        Assert.assertEquals(0.25, getSampleBandValue(product, 12), 1e-8);
        Assert.assertEquals(0.17000000178813934, getSampleBandValue(product, 13), 1e-8);
        Assert.assertEquals(1.0, getSampleBandValue(product, 14), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 15), 1e-8);
        Assert.assertEquals(2658.0, getSampleBandValue(product, 16), 1e-8);
        Assert.assertEquals(2658.0, getSampleBandValue(product, 17), 1e-8);
    }

    static void test_ATS_TOA_1P_TiePointValues(Product product) {
        final TiePointGrid[] tiePointGrids = product.getTiePointGrids();
        Assert.assertEquals(15, tiePointGrids.length);
        // @todo should be 8 decimals but there seems to be a beam issue here with interpolation states
        Assert.assertEquals(-62.314754486083984, getSampleTiePointValue(product, 0), 1e-4);
        Assert.assertEquals(54.501434326171875, getSampleTiePointValue(product, 1), 1e-4);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 2), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 3), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 4), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 5), 1e-8);
        Assert.assertEquals(-4969.6806640625, getSampleTiePointValue(product, 6), 1e-8);
        Assert.assertEquals(-5.142609596252441, getSampleTiePointValue(product, 7), 1e-8);
        Assert.assertEquals(80.2741928100586, getSampleTiePointValue(product, 8), 1e-8);
        Assert.assertEquals(-168.02047729492188, getSampleTiePointValue(product, 9), 1e-8);
        Assert.assertEquals(-121.90804290771484, getSampleTiePointValue(product, 10), 1e-8);
        Assert.assertEquals(-5.082096099853516, getSampleTiePointValue(product, 11), 1e-8);
        Assert.assertEquals(34.924251556396484, getSampleTiePointValue(product, 12), 1e-8);
        Assert.assertEquals(-167.50303649902344, getSampleTiePointValue(product, 13), 1e-8);
        Assert.assertEquals(165.4502410888672, getSampleTiePointValue(product, 14), 1e-8);
    }

    static void test_AT1_NR_2P_GeoCoding(Product product) {
        testGeoCoding(product, 130.9321746826172, 41.47075271606445, 99.86795043945312, 199.99510192871094);
    }

    static void test_ATS_TOA_1P_GeoCoding(Product product) {
        testGeoCoding(product, 56.72055435180664, -63.84092330932617, 99.9892349243164, 200.006591796875);
    }

    static void test_ATS_TOA_1P_MPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement mph = metadataRoot.getElement(MPH);
        Assert.assertNotNull(mph);
        Assert.assertEquals(ATS_TOA_1P_CHILD_PRODUCT_NAME, getStringAttribute(mph, Constants.PRODUCT));
        Assert.assertEquals("P", getStringAttribute(mph, Constants.PROC_STAGE));
        Assert.assertEquals("PO-RS-MDA-GS-2009_07_3H", getStringAttribute(mph, Constants.REF_DOC));
        Assert.assertEquals("PDHS-K              ", getStringAttribute(mph, Constants.ACQUISITION_STATION));
        Assert.assertEquals("LRAC  ", getStringAttribute(mph, Constants.PROC_CENTER));
        Assert.assertEquals("24-JAN-2007 18:46:12.259100", getDateAttributeAsString(mph, Constants.PROC_TIME));
        Assert.assertEquals("AATSR/5.60    ", getStringAttribute(mph, Constants.SOFTWARE_VER));
        Assert.assertEquals("10-JAN-2007 19:37:07.304404", getDateAttributeAsString(mph, Constants.SENSING_START));
        Assert.assertEquals("10-JAN-2007 19:37:55.304404", getDateAttributeAsString(mph, "SENSING_STOP"));
        Assert.assertEquals("2", getStringAttribute(mph, "PHASE"));
        Assert.assertEquals(54, getIntAttribute(mph, "CYCLE"));
        Assert.assertEquals(328, getIntAttribute(mph, "REL_ORBIT"));
        Assert.assertEquals(25432, getIntAttribute(mph, "ABS_ORBIT"));
        Assert.assertEquals("10-JAN-2007 18:15:22.848190", getDateAttributeAsString(mph, "STATE_VECTOR_TIME"));
        Assert.assertEquals(0.028545, getDoubleAttribute(mph, "DELTA_UT1"), 1e-8);
        Assert.assertEquals(3986535.041, getDoubleAttribute(mph, "X_POSITION"), 1e-8);
        Assert.assertEquals(5950076.377, getDoubleAttribute(mph, "Y_POSITION"), 1e-8);
        Assert.assertEquals(0.0, getDoubleAttribute(mph, "Z_POSITION"), 1e-8);
        Assert.assertEquals(1347.940069, getDoubleAttribute(mph, "X_VELOCITY"), 1e-8);
        Assert.assertEquals(-913.322291, getDoubleAttribute(mph, "Y_VELOCITY"), 1e-8);
        Assert.assertEquals(7373.939725, getDoubleAttribute(mph, "Z_VELOCITY"), 1e-8);
        Assert.assertEquals("FR", getStringAttribute(mph, "VECTOR_SOURCE"));
        Assert.assertEquals("10-JAN-2007 10:15:31.663772", getDateAttributeAsString(mph, "UTC_SBT_TIME"));
        Assert.assertEquals(641518080L, getLongAttribute(mph, "SAT_BINARY_TIME"));
        Assert.assertEquals(3906249788L, getLongAttribute(mph, "CLOCK_STEP"));
        Assert.assertEquals("31-DEC-2005 23:59:59.000000", getDateAttributeAsString(mph, "LEAP_UTC"));
        Assert.assertEquals(1, getIntAttribute(mph, "LEAP_SIGN"));
        Assert.assertEquals(0, getIntAttribute(mph, "LEAP_ERR"));
        Assert.assertEquals(0, getIntAttribute(mph, "PRODUCT_ERR"));
        Assert.assertEquals(6093953L, getLongAttribute(mph, "TOT_SIZE"));
        Assert.assertEquals(12830, getIntAttribute(mph, "SPH_SIZE"));
        Assert.assertEquals(38, getIntAttribute(mph, "NUM_DSD"));
        Assert.assertEquals(280, getIntAttribute(mph, "DSD_SIZE"));
        Assert.assertEquals(26, getIntAttribute(mph, "NUM_DATA_SETS"));
    }

    static void test_ATS_TOA_1P_SPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        MetadataElement sph = metadataRoot.getElement(SPH);
        Assert.assertNotNull(sph);
        Assert.assertEquals("GBTR                        ", getStringAttribute(sph, "SPH_DESCRIPTOR"));
        Assert.assertEquals(0, getIntAttribute(sph, "STRIPLINE_CONTINUITY_INDICATOR"));
        Assert.assertEquals(1, getIntAttribute(sph, "SLICE_POSITION"));
        Assert.assertEquals(1, getIntAttribute(sph, "NUM_SLICES"));
        Assert.assertEquals("10-JAN-2007 19:37:07.304404", getDateAttributeAsString(sph, "FIRST_LINE_TIME"));
        Assert.assertEquals("10-JAN-2007 19:37:55.304404", getDateAttributeAsString(sph, "LAST_LINE_TIME"));
        Assert.assertEquals(-66904050, getIntAttribute(sph, "FIRST_FIRST_LAT"));
        Assert.assertEquals(49600256, getIntAttribute(sph, "FIRST_FIRST_LONG"));
        Assert.assertEquals(-66050880, getIntAttribute(sph, "FIRST_MID_LAT"));
        Assert.assertEquals(55394329, getIntAttribute(sph, "FIRST_MID_LONG"));
        Assert.assertEquals(-64995031, getIntAttribute(sph, "FIRST_LAST_LAT"));
        Assert.assertEquals(60770532, getIntAttribute(sph, "FIRST_LAST_LONG"));
        Assert.assertEquals(-64169051, getIntAttribute(sph, "LAST_FIRST_LAT"));
        Assert.assertEquals(47597603, getIntAttribute(sph, "LAST_FIRST_LONG"));
        Assert.assertEquals(-63381749, getIntAttribute(sph, "LAST_MID_LAT"));
        Assert.assertEquals(52883482, getIntAttribute(sph, "LAST_MID_LONG"));
        Assert.assertEquals(-62409869, getIntAttribute(sph, "LAST_LAST_LAT"));
        Assert.assertEquals(57855947, getIntAttribute(sph, "LAST_LAST_LONG"));
        Assert.assertEquals(79.4004821, getDoubleAttribute(sph, "MIN_FPA_BASEPLATE_TEM"), 1e-8);
        Assert.assertEquals(79.6345977, getDoubleAttribute(sph, "MIN_12_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(79.7308425, getDoubleAttribute(sph, "MIN_11_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(79.5159454, getDoubleAttribute(sph, "MIN_3_7_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(79.5099792, getDoubleAttribute(sph, "MIN_1_6_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(263.036987, getDoubleAttribute(sph, "MIN_0_87_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(80.5058288, getDoubleAttribute(sph, "MAX_FPA_BASEPLATE_TEM"), 1e-8);
        Assert.assertEquals(80.3782653, getDoubleAttribute(sph, "MAX_12_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(80.3852691, getDoubleAttribute(sph, "MAX_11_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(80.3548965, getDoubleAttribute(sph, "MAX_3_7_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(80.2563247, getDoubleAttribute(sph, "MAX_1_6_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(263.689971, getDoubleAttribute(sph, "MAX_0_87_MICRON_DETECTOR_TEMP"), 1e-8);

        checkCommonAtsrSPHElements(sph);
    }

    static void test_ATS_TOA_1P_DSDs(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement dsds = metadataRoot.getElement("DSD");
        Assert.assertEquals(38, dsds.getNumElements());

        assertDSD(dsds.getElementAt(0), "SUMMARY_QUALITY_ADS", "A", "", 14077, 172, 2, 86);
        assertDSD(dsds.getElementAt(1), "GEOLOCATION_ADS", "A", "", 14249, 6886, 11, 626);
        assertDSD(dsds.getElementAt(2), "SCAN_PIXEL_X_AND_Y_ADS", "A", "", 21135, 9130, 11, 830);
        assertDSD(dsds.getElementAt(3), "NADIR_VIEW_SOLAR_ANGLES_ADS", "A", "", 30265, 2376, 11, 216);
        assertDSD(dsds.getElementAt(4), "FWARD_VIEW_SOLAR_ANGLES_ADS", "A", "", 32641, 2376, 11, 216);
        assertDSD(dsds.getElementAt(5), "VISIBLE_CALIB_COEFS_GADS", "A", "MISSING", 35017, 0, 11, 0);
        assertDSD(dsds.getElementAt(6), "NADIR_VIEW_SCAN_PIX_NUM_ADS", "A", "", 35017, 22748, 11, 2068);
        assertDSD(dsds.getElementAt(7), "FWARD_VIEW_SCAN_PIX_NUM_ADS", "A", "", 57765, 22748, 11, 2068);
        assertDSD(dsds.getElementAt(8), "11500_12500_NM_NADIR_TOA_MDS", "M", "", 80513, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(9), "10400_11300_NM_NADIR_TOA_MDS", "M", "", 414593, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(10), "03505_03895_NM_NADIR_TOA_MDS", "M", "", 748673, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(11), "01580_01640_NM_NADIR_TOA_MDS", "M", "", 1082753, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(12), "00855_00875_NM_NADIR_TOA_MDS", "M", "", 1416833, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(13), "00649_00669_NM_NADIR_TOA_MDS", "M", "", 1750913, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(14), "00545_00565_NM_NADIR_TOA_MDS", "M", "", 2084993, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(15), "11500_12500_NM_FWARD_TOA_MDS", "M", "", 2419073, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(16), "10400_11300_NM_FWARD_TOA_MDS", "M", "", 2753153, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(17), "03505_03895_NM_FWARD_TOA_MDS", "M", "", 3087233, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(18), "01580_01640_NM_FWARD_TOA_MDS", "M", "", 3421313, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(19), "00855_00875_NM_FWARD_TOA_MDS", "M", "", 3755393, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(20), "00649_00669_NM_FWARD_TOA_MDS", "M", "", 4089473, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(21), "00545_00565_NM_FWARD_TOA_MDS", "M", "", 4423553, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(22), "NADIR_VIEW_CONFIDENCE_MDS", "M", "", 4757633, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(23), "FWARD_VIEW_CONFIDENCE_MDS", "M", "", 5091713, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(24), "NADIR_VIEW_CLOUD_MDS", "M", "", 5425793, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(25), "FWARD_VIEW_CLOUD_MDS", "M", "", 5759873, 334080, 320, 1044);
        assertDSD(dsds.getElementAt(26), "", "?", "", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(27), "AATSR_SOURCE_PACKETS", "R", "ATS_NL__0PPLRA20070110_181252_000063342054_00328_25432_6601.N1", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(28), "INSTRUMENT_DATA_FILE", "R", "ATS_INS_AXVIEC20030731_092706_20020301_000000_20070801_235959", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(29), "PROCESSING_PARAMS_L1B_FILE", "R", "ATS_PC1_AXVIEC20040812_063722_20020301_000000_20070801_235959", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(30), "VISIBLE_CALIBRATION_FILE", "R", "ATS_VC1_AXVIEC20070112_053603_20070110_062600_20070117_062600", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(31), "GENERAL_CALIBRATION_FILE", "R", "ATS_GC1_AXVIEC20041214_154941_20020301_000000_20070801_235959", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(32), "CHARACTERIZATION_L1B_FILE", "R", "ATS_CH1_AXVIEC20021114_113144_20020301_000000_20070801_235959", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(33), "LAND_SEA_MASK_DATA_FILE", "R", "AUX_LSM_AXVIEC20020123_141228_20020101_000000_20200101_000000", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(34), "DIGITAL_ELEVATION_MODEL_FILE", "R", "AUX_DEM_AXVIEC20031201_000000_20031201_000000_20200101_000000", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(35), "CLOUD_LUT_DATA_FILE", "R", "ATS_CL1_AXVIEC20020123_073044_20020101_000000_20200101_000000", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(36), "ORBIT_STATE_VECTOR_FILE", "R", "AUX_FRO_AXVPDS20070112_234201_20070109_221000_20070112_005000", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(37), "", "?", "", 0, 0, 0, 0);
    }

    static void test_ATS_TOA_1P_SQADs(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement sqads = metadataRoot.getElement("SUMMARY_QUALITY_ADS");
        Assert.assertEquals(2, sqads.getNumElements());

        MetadataElement sqad = sqads.getElementAt(0);
        Assert.assertEquals("10-JAN-2007 19:36:04.904404", sqad.getAttribute("dsr_time").getData().getElemString());
        Assert.assertEquals(0, getIntAttribute(sqad, "attach_flag"));
        Assert.assertEquals(0, getIntAttribute(sqad, "scan_num"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_show_buf_full"));
        Assert.assertEquals(253, getIntAttribute(sqad, "pv_nad_scan_jitt"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_1"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_2"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_3"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_4"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_error"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_show_buf_full"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_jitt"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_5"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_6"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_7"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_8"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_error"));

        sqad = sqads.getElementAt(1);
        Assert.assertEquals("10-JAN-2007 19:37:21.704404", sqad.getAttribute("dsr_time").getData().getElemString());
        Assert.assertEquals(0, getIntAttribute(sqad, "attach_flag"));
        Assert.assertEquals(96, getIntAttribute(sqad, "scan_num"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_show_buf_full"));
        Assert.assertEquals(274, getIntAttribute(sqad, "pv_nad_scan_jitt"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_1"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_2"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_3"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_4"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_error"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_show_buf_full"));
        Assert.assertEquals(273, getIntAttribute(sqad, "pv_for_scan_jitt"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_5"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_6"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_7"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_8"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_error"));
    }

    static void test_AT1_NR_2P_Basics(Product product) {
        Assert.assertEquals(AT1_NR__2P_CHILD_PRODUCT_NAME, product.getName());
        Assert.assertEquals(512, product.getSceneRasterWidth());
        Assert.assertEquals(224, product.getSceneRasterHeight());
    }

    static void test_AT1_NR_2P_BandValues(Product product) throws IOException {
        Assert.assertFalse(isSamplePixelValid(product, 0));
        Assert.assertEquals(0.0, getSampleBandValue(product, 1), 1e-8);
        Assert.assertEquals(244.0399932861328, getSampleBandValue(product, 2), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 3), 1e-8);
        Assert.assertFalse(isSamplePixelValid(product, 4));
        Assert.assertEquals(0.0, getSampleBandValue(product, 5), 1e-8);
        Assert.assertEquals(4385.0, getSampleBandValue(product, 6), 1e-8);
    }

    static void test_AT1_NR_2P_TiePointValues(Product product) {
        final TiePointGrid[] tiePointGrids = product.getTiePointGrids();
        Assert.assertEquals(15, tiePointGrids.length);
        Assert.assertEquals(43.15705490112305, getSampleTiePointValue(product, 0), 1e-4);
        Assert.assertEquals(129.97467041015625, getSampleTiePointValue(product, 1), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 2), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 3), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 4), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 5), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 6), 1e-8);
        Assert.assertEquals(-18.36856460571289, getSampleTiePointValue(product, 7), 1e-8);
        Assert.assertEquals(80.03468322753906, getSampleTiePointValue(product, 8), 1e-8);
        Assert.assertEquals(-28.069353103637695, getSampleTiePointValue(product, 9), 1e-8);
        Assert.assertEquals(-112.37125396728516, getSampleTiePointValue(product, 10), 1e-8);
        Assert.assertEquals(-18.178651809692383, getSampleTiePointValue(product, 11), 1e-8);
        Assert.assertEquals(35.073238372802734, getSampleTiePointValue(product, 12), 1e-8);
        Assert.assertEquals(-28.56743621826172, getSampleTiePointValue(product, 13), 1e-8);
        Assert.assertEquals(173.70358276367188, getSampleTiePointValue(product, 14), 1e-8);
    }

    static void test_AT1_NR_2P_MPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement mph = metadataRoot.getElement(MPH);
        Assert.assertNotNull(mph);
        Assert.assertEquals(AT1_NR__2P_CHILD_PRODUCT_NAME, getStringAttribute(mph, Constants.PRODUCT));
        Assert.assertEquals("T", getStringAttribute(mph, Constants.PROC_STAGE));
        Assert.assertEquals("PO-TN-RAL-GS-10003_12/1", getStringAttribute(mph, Constants.REF_DOC));
        Assert.assertEquals("UK-PAC              ", getStringAttribute(mph, Constants.ACQUISITION_STATION));
        Assert.assertEquals("RAL   ", getStringAttribute(mph, Constants.PROC_CENTER));
        Assert.assertEquals("05-JUN-2007 14:06:20.000000", getDateAttributeAsString(mph, Constants.PROC_TIME));
        Assert.assertEquals("proto2_static/", getStringAttribute(mph, Constants.SOFTWARE_VER));
        Assert.assertEquals("14-JUN-1993 13:22:59.424000", getDateAttributeAsString(mph, Constants.SENSING_START));
        Assert.assertEquals("14-JUN-1993 13:23:33.024000", getDateAttributeAsString(mph, "SENSING_STOP"));
        Assert.assertEquals("4", getStringAttribute(mph, "PHASE"));
        Assert.assertEquals(13, getIntAttribute(mph, "CYCLE"));
        Assert.assertEquals(338, getIntAttribute(mph, "REL_ORBIT"));
        Assert.assertEquals(10002, getIntAttribute(mph, "ABS_ORBIT"));
        Assert.assertEquals("14-JUN-1993 14:52:29.656000", getDateAttributeAsString(mph, "STATE_VECTOR_TIME"));
        Assert.assertEquals(0.0, getDoubleAttribute(mph, "DELTA_UT1"), 1e-8);
        Assert.assertEquals(-2958002.75, getDoubleAttribute(mph, "X_POSITION"), 1e-8);
        Assert.assertEquals(6526493.18, getDoubleAttribute(mph, "Y_POSITION"), 1e-8);
        Assert.assertEquals(1.23, getDoubleAttribute(mph, "Z_POSITION"), 1e-8);
        Assert.assertEquals(1489.4, getDoubleAttribute(mph, "X_VELOCITY"), 1e-8);
        Assert.assertEquals(665.56, getDoubleAttribute(mph, "Y_VELOCITY"), 1e-8);
        Assert.assertEquals(7377.1, getDoubleAttribute(mph, "Z_VELOCITY"), 1e-8);
        Assert.assertEquals("FP", getStringAttribute(mph, "VECTOR_SOURCE"));
        Assert.assertEquals("14-JUN-1993 15:12:25.798000", getDateAttributeAsString(mph, "UTC_SBT_TIME"));
        Assert.assertEquals(2573031678L, getLongAttribute(mph, "SAT_BINARY_TIME"));
        Assert.assertEquals(3906249000L, getLongAttribute(mph, "CLOCK_STEP"));
        Assert.assertEquals("                           ", getDateAttributeAsString(mph, "LEAP_UTC"));
        Assert.assertEquals(0, getIntAttribute(mph, "LEAP_SIGN"));
        Assert.assertEquals(0, getIntAttribute(mph, "LEAP_ERR"));
        Assert.assertEquals(0, getIntAttribute(mph, "PRODUCT_ERR"));
        Assert.assertEquals(748049L, getLongAttribute(mph, "TOT_SIZE"));
        Assert.assertEquals(5830, getIntAttribute(mph, "SPH_SIZE"));
        Assert.assertEquals(13, getIntAttribute(mph, "NUM_DSD"));
        Assert.assertEquals(280, getIntAttribute(mph, "DSD_SIZE"));
        Assert.assertEquals(8, getIntAttribute(mph, "NUM_DATA_SETS"));
    }

    static void test_AT1_NR_2P_DSDs(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement dsds = metadataRoot.getElement("DSD");
        Assert.assertEquals(13, dsds.getNumElements());

        assertDSD(dsds.getElementAt(0), "SUMMARY_QUALITY_ADS", "A", "", 7077, 172, 2, 86);
        assertDSD(dsds.getElementAt(1), "GEOLOCATION_ADS", "A", "", 7249, 5008, 8, 626);
        assertDSD(dsds.getElementAt(2), "SCAN_PIXEL_X_AND_Y_ADS", "A", "", 12257, 6640, 8, 830);
        assertDSD(dsds.getElementAt(3), "NADIR_VIEW_SOLAR_ANGLES_ADS", "A", "", 18897, 1728, 8, 216);
        assertDSD(dsds.getElementAt(4), "FWARD_VIEW_SOLAR_ANGLES_ADS", "A", "", 20625, 1728, 8, 216);
        assertDSD(dsds.getElementAt(5), "NADIR_VIEW_SCAN_PIX_NUM_ADS", "A", "", 22353, 16544, 8, 2068);
        assertDSD(dsds.getElementAt(6), "FWARD_VIEW_SCAN_PIX_NUM_ADS", "A", "", 38897, 16544, 8, 2068);
        assertDSD(dsds.getElementAt(7), "DISTRIB_SST_CLOUD_LAND_MDS", "M", "proto2_output_v2.0_05-06-2007-14:06:20.gst", 55441, 692608, 224, 3092);
        assertDSD(dsds.getElementAt(8), "", "?", "", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(9), "LEVEL_1B_PRODUCT", "R", "AT1_TOA_1PTRAL19930614_131152_000000004013_00338_10002_0000.E1", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(10), "PROCESSING_PARAMS_L2_FILE", "R", "ATS_PC2_AXVRAL20020118_160919_20020118_160919_20020801_235959", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(11), "RETRIEVAL_COEFS_DATA_FILE", "R", "", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(12), "", "?", "", 0, 0, 0, 0);
    }

    static void test_AT1_NR_2P_SQADs(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement sqads = metadataRoot.getElement("SUMMARY_QUALITY_ADS");
        Assert.assertEquals(2, sqads.getNumElements());

        MetadataElement sqad = sqads.getElementAt(0);
        Assert.assertEquals("14-JUN-1993 13:22:06.624000", sqad.getAttribute("dsr_time").getData().getElemString());
        Assert.assertEquals(0, getIntAttribute(sqad, "attach_flag"));
        Assert.assertEquals(0, getIntAttribute(sqad, "scan_num"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_show_buf_full"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_jitt"));
        Assert.assertEquals(19228, getIntAttribute(sqad, "per_cloud_pix"));
        Assert.assertEquals(4135, getIntAttribute(sqad, "per_ndvi_inv"));
        Assert.assertEquals(0, getIntAttribute(sqad, "per_sst_for_inv"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_error"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_error"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_show_buf_full"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_jitt"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_5"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_6"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_7"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_8"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_error"));

        sqad = sqads.getElementAt(1);
        Assert.assertEquals("14-JUN-1993 13:23:23.424000", sqad.getAttribute("dsr_time").getData().getElemString());
        Assert.assertEquals(0, getIntAttribute(sqad, "attach_flag"));
        Assert.assertEquals(160, getIntAttribute(sqad, "scan_num"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_show_buf_full"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_jitt"));
        Assert.assertEquals(19716, getIntAttribute(sqad, "per_cloud_pix"));
        Assert.assertEquals(4135, getIntAttribute(sqad, "per_ndvi_inv"));
        Assert.assertEquals(0, getIntAttribute(sqad, "per_sst_for_inv"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_error"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_error"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_show_buf_full"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_jitt"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_5"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_6"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_7"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_8"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_error"));
    }

    static void test_AT1_TOA_1P(Product product) {
        Assert.assertEquals(AT1_TOA_1P_CHILD_PRODUCT_NAME, product.getName());
        Assert.assertEquals(512, product.getSceneRasterWidth());
        Assert.assertEquals(226, product.getSceneRasterHeight());
    }

    static void test_AT1_TOA_1P_BandValues(Product product) throws IOException {
        Assert.assertEquals(292.1499938964844, getSampleBandValue(product, 0), 1e-8);
        Assert.assertEquals(292.9100036621094, getSampleBandValue(product, 1), 1e-8);
        Assert.assertFalse(isSamplePixelValid(product, 2));
        Assert.assertEquals(0.08999999612569809, getSampleBandValue(product, 3), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 4), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 5), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 6), 1e-8);
        Assert.assertEquals(291.1300048828125, getSampleBandValue(product, 7), 1e-8);
        Assert.assertEquals(291.8999938964844, getSampleBandValue(product, 8), 1e-8);
        Assert.assertFalse(isSamplePixelValid(product, 9));
        Assert.assertEquals(0.2199999988079071, getSampleBandValue(product, 10), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 11), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 12), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 13), 1e-8);
        Assert.assertEquals(256.0, getSampleBandValue(product, 14), 1e-8);
        Assert.assertEquals(256.0, getSampleBandValue(product, 15), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 16), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 17), 1e-8);
    }

    static void test_AT1_TOA_1P_TiePointValues(Product product) {
        final TiePointGrid[] tiePointGrids = product.getTiePointGrids();
        Assert.assertEquals(15, tiePointGrids.length);
        Assert.assertEquals(-27.682233810424805, getSampleTiePointValue(product, 0), 1e-4);
        Assert.assertEquals(-44.92152404785156, getSampleTiePointValue(product, 1), 1e-4);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 2), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 3), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 4), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 5), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 6), 1e-8);
        Assert.assertEquals(32.89971923828125, getSampleTiePointValue(product, 7), 1e-8);
        Assert.assertEquals(80.1079330444336, getSampleTiePointValue(product, 8), 1e-8);
        Assert.assertEquals(33.179161071777344, getSampleTiePointValue(product, 9), 1e-8);
        Assert.assertEquals(94.98296356201172, getSampleTiePointValue(product, 10), 1e-8);
        Assert.assertEquals(32.627262115478516, getSampleTiePointValue(product, 11), 1e-8);
        Assert.assertEquals(34.953643798828125, getSampleTiePointValue(product, 12), 1e-8);
        Assert.assertEquals(33.70918273925781, getSampleTiePointValue(product, 13), 1e-8);
        Assert.assertEquals(21.602134704589844, getSampleTiePointValue(product, 14), 1e-8);
    }

    static void test_AT1_TOA_1P_GeoCoding(Product product) {
        testGeoCoding(product, -44.785213470458984, -25.845752716064453, 100.05215454101562, 200.00048828125);
    }

    static void test_AT1_TOA_1P_MPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement mph = metadataRoot.getElement(MPH);
        Assert.assertNotNull(mph);
        Assert.assertEquals(AT1_TOA_1P_CHILD_PRODUCT_NAME, getStringAttribute(mph, Constants.PRODUCT));
        Assert.assertEquals("T", getStringAttribute(mph, Constants.PROC_STAGE));
        Assert.assertEquals("PO-TN-RAL-GS-10003_12/1", getStringAttribute(mph, Constants.REF_DOC));
        Assert.assertEquals("UK-PAC              ", getStringAttribute(mph, Constants.ACQUISITION_STATION));
        Assert.assertEquals("RAL   ", getStringAttribute(mph, Constants.PROC_CENTER));
        Assert.assertEquals("01-FEB-2007 21:13:45.000000", getDateAttributeAsString(mph, Constants.PROC_TIME));
        Assert.assertEquals("STEP/0.0+     ", getStringAttribute(mph, Constants.SOFTWARE_VER));
        Assert.assertEquals("14-JUL-1995 13:05:55.968000", getDateAttributeAsString(mph, Constants.SENSING_START));
        Assert.assertEquals("14-JUL-1995 13:06:29.568000", getDateAttributeAsString(mph, "SENSING_STOP"));
        Assert.assertEquals("8", getStringAttribute(mph, "PHASE"));
        Assert.assertEquals(4, getIntAttribute(mph, "CYCLE"));
        Assert.assertEquals(366, getIntAttribute(mph, "REL_ORBIT"));
        Assert.assertEquals(20895, getIntAttribute(mph, "ABS_ORBIT"));
        Assert.assertEquals("14-JUL-1995 13:49:26.410000", getDateAttributeAsString(mph, "STATE_VECTOR_TIME"));
        Assert.assertEquals(0.0, getDoubleAttribute(mph, "DELTA_UT1"), 1e-8);
        Assert.assertEquals(-4624374.54, getDoubleAttribute(mph, "X_POSITION"), 1e-8);
        Assert.assertEquals(5473488.65, getDoubleAttribute(mph, "Y_POSITION"), 1e-8);
        Assert.assertEquals(1.23, getDoubleAttribute(mph, "Z_POSITION"), 1e-8);
        Assert.assertEquals(1251.27, getDoubleAttribute(mph, "X_VELOCITY"), 1e-8);
        Assert.assertEquals(1045.89, getDoubleAttribute(mph, "Y_VELOCITY"), 1e-8);
        Assert.assertEquals(7377.27, getDoubleAttribute(mph, "Z_VELOCITY"), 1e-8);
        Assert.assertEquals("FP", getStringAttribute(mph, "VECTOR_SOURCE"));
        Assert.assertEquals("14-JUL-1995 14:11:02.982000", getDateAttributeAsString(mph, "UTC_SBT_TIME"));
        Assert.assertEquals(2202205471L, getLongAttribute(mph, "SAT_BINARY_TIME"));
        Assert.assertEquals(3906249000L, getLongAttribute(mph, "CLOCK_STEP"));
        Assert.assertEquals("                           ", getDateAttributeAsString(mph, "LEAP_UTC"));
        Assert.assertEquals(0, getIntAttribute(mph, "LEAP_SIGN"));
        Assert.assertEquals(0, getIntAttribute(mph, "LEAP_ERR"));
        Assert.assertEquals(0, getIntAttribute(mph, "PRODUCT_ERR"));
        Assert.assertEquals(4309433L, getLongAttribute(mph, "TOT_SIZE"));
        Assert.assertEquals(12830, getIntAttribute(mph, "SPH_SIZE"));
        Assert.assertEquals(36, getIntAttribute(mph, "NUM_DSD"));
        Assert.assertEquals(280, getIntAttribute(mph, "DSD_SIZE"));
        Assert.assertEquals(26, getIntAttribute(mph, "NUM_DATA_SETS"));
    }

    static void test_AT1_TOA_1P_SPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        MetadataElement sph = metadataRoot.getElement(SPH);
        Assert.assertNotNull(sph);
        Assert.assertEquals("GBTR Product SPH            ", getStringAttribute(sph, "SPH_DESCRIPTOR"));
        Assert.assertEquals(0, getIntAttribute(sph, "STRIPLINE_CONTINUITY_INDICATOR"));
        Assert.assertEquals(1, getIntAttribute(sph, "SLICE_POSITION"));
        Assert.assertEquals(1, getIntAttribute(sph, "NUM_SLICES"));
        Assert.assertEquals("14-JUL-1995 13:05:55.968000", getDateAttributeAsString(sph, "FIRST_LINE_TIME"));
        Assert.assertEquals("14-JUL-1995 13:06:29.568000", getDateAttributeAsString(sph, "LAST_LINE_TIME"));
        Assert.assertEquals(-24934948, getIntAttribute(sph, "FIRST_FIRST_LAT"));
        Assert.assertEquals(-40166106, getIntAttribute(sph, "FIRST_FIRST_LONG"));
        Assert.assertEquals(-24401427, getIntAttribute(sph, "FIRST_MID_LAT"));
        Assert.assertEquals(-42819556, getIntAttribute(sph, "FIRST_MID_LONG"));
        Assert.assertEquals(-23821778, getIntAttribute(sph, "FIRST_LAST_LAT"));
        Assert.assertEquals(-45449858, getIntAttribute(sph, "FIRST_LAST_LONG"));
        Assert.assertEquals(-26919274, getIntAttribute(sph, "LAST_FIRST_LAT"));
        Assert.assertEquals(-40626570, getIntAttribute(sph, "LAST_FIRST_LONG"));
        Assert.assertEquals(-26383971, getIntAttribute(sph, "LAST_MID_LAT"));
        Assert.assertEquals(-43323813, getIntAttribute(sph, "LAST_MID_LONG"));
        Assert.assertEquals(-25798287, getIntAttribute(sph, "LAST_LAST_LAT"));
        Assert.assertEquals(-45995154, getIntAttribute(sph, "LAST_LAST_LONG"));
        Assert.assertEquals(100.086, getDoubleAttribute(sph, "MIN_FPA_BASEPLATE_TEM"), 1e-8);
        Assert.assertEquals(103.428, getDoubleAttribute(sph, "MIN_12_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(102.759, getDoubleAttribute(sph, "MIN_11_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(104.03, getDoubleAttribute(sph, "MIN_3_7_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(102.614, getDoubleAttribute(sph, "MIN_1_6_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(999.0, getDoubleAttribute(sph, "MIN_0_87_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(101.88, getDoubleAttribute(sph, "MAX_FPA_BASEPLATE_TEM"), 1e-8);
        Assert.assertEquals(108.04, getDoubleAttribute(sph, "MAX_12_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(106.67, getDoubleAttribute(sph, "MAX_11_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(108.77, getDoubleAttribute(sph, "MAX_3_7_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(107.0, getDoubleAttribute(sph, "MAX_1_6_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(-999.0, getDoubleAttribute(sph, "MAX_0_87_MICRON_DETECTOR_TEMP"), 1e-8);

        checkCommonAtsrSPHElements(sph);
    }

    static void test_AT1_TOA_1P_DSDs(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement dsds = metadataRoot.getElement("DSD");
        Assert.assertEquals(36, dsds.getNumElements());

        assertDSD(dsds.getElementAt(0), "SUMMARY_QUALITY_ADS", "A", "", 14077, 172, 2, 86);
        assertDSD(dsds.getElementAt(1), "GEOLOCATION_ADS", "A", "", 14249, 5008, 8, 626);
        assertDSD(dsds.getElementAt(2), "SCAN_PIXEL_X_AND_Y_ADS", "A", "", 19257, 6640, 8, 830);
        assertDSD(dsds.getElementAt(3), "NADIR_VIEW_SOLAR_ANGLES_ADS", "A", "", 25897, 1728, 8, 216);
        assertDSD(dsds.getElementAt(4), "FWARD_VIEW_SOLAR_ANGLES_ADS", "A", "", 27625, 1728, 8, 216);
        assertDSD(dsds.getElementAt(5), "VISIBLE_CALIB_COEFS_GADS", "A", "MISSING", 29353, 0, 8, 0);
        assertDSD(dsds.getElementAt(6), "NADIR_VIEW_SCAN_PIX_NUM_ADS", "A", "", 29353, 16544, 8, 2068);
        assertDSD(dsds.getElementAt(7), "FWARD_VIEW_SCAN_PIX_NUM_ADS", "A", "", 45897, 16544, 8, 2068);
        assertDSD(dsds.getElementAt(8), "11500_12500_NM_NADIR_TOA_MDS", "M", "", 62441, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(9), "10400_11300_NM_NADIR_TOA_MDS", "M", "", 298385, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(10), "03505_03895_NM_NADIR_TOA_MDS", "M", "", 534329, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(11), "01580_01640_NM_NADIR_TOA_MDS", "M", "", 770273, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(12), "00855_00875_NM_NADIR_TOA_MDS", "M", "", 1006217, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(13), "00649_00669_NM_NADIR_TOA_MDS", "M", "", 1242161, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(14), "00545_00565_NM_NADIR_TOA_MDS", "M", "", 1478105, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(15), "11500_12500_NM_FWARD_TOA_MDS", "M", "", 1714049, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(16), "10400_11300_NM_FWARD_TOA_MDS", "M", "", 1949993, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(17), "03505_03895_NM_FWARD_TOA_MDS", "M", "", 2185937, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(18), "01580_01640_NM_FWARD_TOA_MDS", "M", "", 2421881, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(19), "00855_00875_NM_FWARD_TOA_MDS", "M", "", 2657825, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(20), "00649_00669_NM_FWARD_TOA_MDS", "M", "", 2893769, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(21), "00545_00565_NM_FWARD_TOA_MDS", "M", "", 3129713, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(22), "NADIR_VIEW_CONFIDENCE_MDS", "M", "", 3365657, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(23), "FWARD_VIEW_CONFIDENCE_MDS", "M", "", 3601601, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(24), "NADIR_VIEW_CLOUD_MDS", "M", "", 3837545, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(25), "FWARD_VIEW_CLOUD_MDS", "M", "", 4073489, 235944, 226, 1044);
        assertDSD(dsds.getElementAt(26), "", "?", "", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(27), "AATSR_SOURCE_PACKETS", "R", "ATS_UU__1PXRAL19950714_120632_000062808004_00366_20895_0000.E1", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(28), "INSTRUMENT_DATA_FILE", "R", "ATS_INS_AXVRAL20060607_130000_19910717_000000_20000310_235959", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(29), "PROCESSING_PARAMS_L1B_FILE", "R", "ATS_PC1_AXVRAL20050725_110545_19910717_000000_20000310_235959", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(30), "VISIBLE_CALIBRATION_FILE", "R", "ATS_VC1_AXVRAL20050527_153635_20050524_114739_20050531_114739", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(31), "GENERAL_CALIBRATION_FILE", "R", "ATS_GC1_AXVRAL20060607_130000_19910717_000000_20000310_235959", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(32), "CHARACTERIZATION_L1B_FILE", "R", "ATS_CH1_AXVRAL20070110_151234_19910717_000000_20000310_235959", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(33), "LAND_SEA_MASK_DATA_FILE", "R", "", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(34), "DIGITAL_ELEVATION_MODEL_FILE", "R", "NOT USED", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(35), "CLOUD_LUT_DATA_FILE", "R", "ATS_CL1_AXVRAL20060526_123601_19910717_000000_20000310_235959", 0, 0, 0, 0);
    }

    static void test_AT1_TOA_1P_SQADs(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement sqads = metadataRoot.getElement("SUMMARY_QUALITY_ADS");
        Assert.assertEquals(2, sqads.getNumElements());

        MetadataElement sqad = sqads.getElementAt(0);
        Assert.assertEquals("14-JUL-1995 13:05:07.968000", sqad.getAttribute("dsr_time").getData().getElemString());
        Assert.assertEquals(0, getIntAttribute(sqad, "attach_flag"));
        Assert.assertEquals(0, getIntAttribute(sqad, "scan_num"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_show_buf_full"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_jitt"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_1"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_2"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_3"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_4"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_error"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_show_buf_full"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_jitt"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_5"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_6"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_7"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_8"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_error"));

        sqad = sqads.getElementAt(1);
        Assert.assertEquals("14-JUL-1995 13:06:24.768000", sqad.getAttribute("dsr_time").getData().getElemString());
        Assert.assertEquals(0, getIntAttribute(sqad, "attach_flag"));
        Assert.assertEquals(192, getIntAttribute(sqad, "scan_num"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_show_buf_full"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_jitt"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_1"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_2"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_3"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_4"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_nad_scan_error"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_null_pac"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_val"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_fail_crc_chk"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_show_buf_full"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_jitt"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_5"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_6"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_7"));
        Assert.assertEquals(0, getIntAttribute(sqad, "resv_char_8"));
        Assert.assertEquals(0, getIntAttribute(sqad, "pv_for_scan_error"));
    }

    static void test_AT1_TOA_1P_AttachmentFlag_Basics(Product product) {
        Assert.assertEquals("AT1_TOA_1PTOMT19931012_104905_000000384017_00050_11718_0801.E1", product.getName());
        Assert.assertEquals(512, product.getSceneRasterWidth());
        Assert.assertEquals(256, product.getSceneRasterHeight());
    }

    static void test_TOA_1P_AttachmentFlag_BandNames(Product product) {
        final String[] bandNames = product.getBandNames();
        // @todo 3 tb/tb this is an error in BEAM - all bands contain no real MDSR's in this region ...
        Assert.assertEquals(0, bandNames.length);
    }

    static void test_AT1_TOA_1P_AttachmentFlag_TiePointValues(Product product) {
        final TiePointGrid[] tiePointGrids = product.getTiePointGrids();
        Assert.assertEquals(15, tiePointGrids.length);
        Assert.assertEquals(69.24927520751953, getSampleTiePointValue(product, 0), 1e-4);
        Assert.assertEquals(14.793353080749512, getSampleTiePointValue(product, 1), 1e-4);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 2), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 3), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 4), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 5), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 6), 1e-8);
        Assert.assertEquals(13.26479721069336, getSampleTiePointValue(product, 7), 1e-8);
        Assert.assertEquals(80.10839080810547, getSampleTiePointValue(product, 8), 1e-8);
        Assert.assertEquals(-179.3123779296875, getSampleTiePointValue(product, 9), 1e-8);
        Assert.assertEquals(104.0246353149414, getSampleTiePointValue(product, 10), 1e-8);
        Assert.assertEquals(13.260679244995117, getSampleTiePointValue(product, 11), 1e-8);
        Assert.assertEquals(35.04933166503906, getSampleTiePointValue(product, 12), 1e-8);
        Assert.assertEquals(-179.8702850341797, getSampleTiePointValue(product, 13), 1e-8);
        Assert.assertEquals(29.79308319091797, getSampleTiePointValue(product, 14), 1e-8);
    }

    static void test_AT1_NR_2P_SPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        MetadataElement sph = metadataRoot.getElement(SPH);
        Assert.assertNotNull(sph);
        Assert.assertEquals("GSST Product SPH            ", getStringAttribute(sph, "SPH_DESCRIPTOR"));
        Assert.assertEquals(0, getIntAttribute(sph, "STRIPLINE_CONTINUITY_INDICATOR"));
        Assert.assertEquals(1, getIntAttribute(sph, "SLICE_POSITION"));
        Assert.assertEquals(1, getIntAttribute(sph, "NUM_SLICES"));
        Assert.assertEquals("14-JUN-1993 13:22:59.424000", getDateAttributeAsString(sph, "FIRST_LINE_TIME"));
        Assert.assertEquals("14-JUN-1993 13:23:33.024000", getDateAttributeAsString(sph, "LAST_LINE_TIME"));
        Assert.assertEquals(38735031, getIntAttribute(sph, "FIRST_FIRST_LAT"));
        Assert.assertEquals(126635025, getIntAttribute(sph, "FIRST_FIRST_LONG"));
        Assert.assertEquals(39378547, getIntAttribute(sph, "FIRST_MID_LAT"));
        Assert.assertEquals(129703231, getIntAttribute(sph, "FIRST_MID_LONG"));
        Assert.assertEquals(39939674, getIntAttribute(sph, "FIRST_LAST_LAT"));
        Assert.assertEquals(132824600, getIntAttribute(sph, "FIRST_LAST_LONG"));
        Assert.assertEquals(40692832, getIntAttribute(sph, "LAST_FIRST_LAT"));
        Assert.assertEquals(125898162, getIntAttribute(sph, "LAST_FIRST_LONG"));
        Assert.assertEquals(41349037, getIntAttribute(sph, "LAST_MID_LAT"));
        Assert.assertEquals(129051086, getIntAttribute(sph, "LAST_MID_LONG"));
        Assert.assertEquals(41917133, getIntAttribute(sph, "LAST_LAST_LAT"));
        Assert.assertEquals(132263595, getIntAttribute(sph, "LAST_LAST_LONG"));
        Assert.assertEquals(90.221, getDoubleAttribute(sph, "MIN_FPA_BASEPLATE_TEM"), 1e-8);
        Assert.assertEquals(94.009, getDoubleAttribute(sph, "MIN_12_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(92.94, getDoubleAttribute(sph, "MIN_11_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(93.697, getDoubleAttribute(sph, "MIN_3_7_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(93.18, getDoubleAttribute(sph, "MIN_1_6_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(999.0, getDoubleAttribute(sph, "MIN_0_87_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(92.06, getDoubleAttribute(sph, "MAX_FPA_BASEPLATE_TEM"), 1e-8);
        Assert.assertEquals(98.02, getDoubleAttribute(sph, "MAX_12_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(97.85, getDoubleAttribute(sph, "MAX_11_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(98.72, getDoubleAttribute(sph, "MAX_3_7_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(97.37, getDoubleAttribute(sph, "MAX_1_6_MICRON_DETECTOR_TEMP"), 1e-8);
        Assert.assertEquals(-999.0, getDoubleAttribute(sph, "MAX_0_87_MICRON_DETECTOR_TEMP"), 1e-8);

        checkCommonAtsrSPHElements(sph);
    }

    static void test_AT2_NR_2P_Basics(Product product) {
        Assert.assertEquals(AT2_NR__2P_CHILD_PRODUCT_NAME, product.getName());
        Assert.assertEquals(512, product.getSceneRasterWidth());
        Assert.assertEquals(320, product.getSceneRasterHeight());
    }

    static void test_AT2_NR_2P_BandValues(Product product) throws IOException {
        Assert.assertFalse(isSamplePixelValid(product, 0));
        Assert.assertFalse(isSamplePixelValid(product, 1));
        Assert.assertEquals(247.67999267578125, getSampleBandValue(product, 2), 1e-8);
        Assert.assertFalse(isSamplePixelValid(product, 3));
        Assert.assertFalse(isSamplePixelValid(product, 4));
        Assert.assertFalse(isSamplePixelValid(product, 5));
        Assert.assertEquals(18481.0, getSampleBandValue(product, 6), 1e-8);
    }

    static void test_AT2_NR_2P_TiePointValues(Product product) {
        final TiePointGrid[] tiePointGrids = product.getTiePointGrids();
        Assert.assertEquals(15, tiePointGrids.length);
        Assert.assertEquals(51.89004898071289, getSampleTiePointValue(product, 0), 1e-5);
        Assert.assertEquals(100.85364532470703, getSampleTiePointValue(product, 1), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 2), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 3), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 4), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 5), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 6), 1e-8);
        Assert.assertEquals(-48.408294677734375, getSampleTiePointValue(product, 7), 1e-8);
        Assert.assertEquals(79.99071502685547, getSampleTiePointValue(product, 8), 1e-8);
        Assert.assertEquals(-41.94278335571289, getSampleTiePointValue(product, 9), 1e-8);
        Assert.assertEquals(-116.18212127685547, getSampleTiePointValue(product, 10), 1e-8);
        Assert.assertEquals(-48.18309783935547, getSampleTiePointValue(product, 11), 1e-8);
        Assert.assertEquals(35.33045196533203, getSampleTiePointValue(product, 12), 1e-8);
        Assert.assertEquals(-42.67622375488281, getSampleTiePointValue(product, 13), 1e-8);
        Assert.assertEquals(172.30970764160156, getSampleTiePointValue(product, 14), 1e-8);
    }

    static void test_AT2_NR_2P_GeoCoding(Product product) {
        testGeoCoding(product, 102.04106903076172, 50.223514556884766, 99.92236328125, 199.9903106689453);
    }

    static void test_AT2_NR_2P_MPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement mph = metadataRoot.getElement(MPH);
        Assert.assertNotNull(mph);
        Assert.assertEquals(AT2_NR__2P_CHILD_PRODUCT_NAME, getStringAttribute(mph, Constants.PRODUCT));
        Assert.assertEquals("T", getStringAttribute(mph, Constants.PROC_STAGE));
        Assert.assertEquals("PO-TN-RAL-GS-10003_12/1", getStringAttribute(mph, Constants.REF_DOC));
        Assert.assertEquals("UK-PAC              ", getStringAttribute(mph, Constants.ACQUISITION_STATION));
        Assert.assertEquals("RAL   ", getStringAttribute(mph, Constants.PROC_CENTER));
        Assert.assertEquals("27-FEB-2007 17:39:01.000000", getDateAttributeAsString(mph, Constants.PROC_TIME));
        Assert.assertEquals("proto2/2.0    ", getStringAttribute(mph, Constants.SOFTWARE_VER));
        Assert.assertEquals("07-NOV-2000 15:09:06.432000", getDateAttributeAsString(mph, Constants.SENSING_START));
        Assert.assertEquals("07-NOV-2000 15:09:54.432000", getDateAttributeAsString(mph, "SENSING_STOP"));
        Assert.assertEquals("1", getStringAttribute(mph, "PHASE"));
        Assert.assertEquals(58, getIntAttribute(mph, "CYCLE"));
        Assert.assertEquals(110, getIntAttribute(mph, "REL_ORBIT"));
        Assert.assertEquals(29022, getIntAttribute(mph, "ABS_ORBIT"));
        Assert.assertEquals("07-NOV-2000 16:36:07.290000", getDateAttributeAsString(mph, "STATE_VECTOR_TIME"));
        Assert.assertEquals(0.0, getDoubleAttribute(mph, "DELTA_UT1"), 1e-8);
        Assert.assertEquals(185836.35, getDoubleAttribute(mph, "X_POSITION"), 1e-8);
        Assert.assertEquals(7163119.18, getDoubleAttribute(mph, "Y_POSITION"), 1e-8);
        Assert.assertEquals(1.23, getDoubleAttribute(mph, "Z_POSITION"), 1e-8);
        Assert.assertEquals(1629.08, getDoubleAttribute(mph, "X_VELOCITY"), 1e-8);
        Assert.assertEquals(-50.7, getDoubleAttribute(mph, "Y_VELOCITY"), 1e-8);
        Assert.assertEquals(7377.25, getDoubleAttribute(mph, "Z_VELOCITY"), 1e-8);
        Assert.assertEquals("FP", getStringAttribute(mph, "VECTOR_SOURCE"));
        Assert.assertEquals("07-NOV-2000 16:51:35.162000", getDateAttributeAsString(mph, "UTC_SBT_TIME"));
        Assert.assertEquals(1905173351L, getLongAttribute(mph, "SAT_BINARY_TIME"));
        Assert.assertEquals(3906250000L, getLongAttribute(mph, "CLOCK_STEP"));
        Assert.assertEquals("                           ", getDateAttributeAsString(mph, "LEAP_UTC"));
        Assert.assertEquals(0, getIntAttribute(mph, "LEAP_SIGN"));
        Assert.assertEquals(0, getIntAttribute(mph, "LEAP_ERR"));
        Assert.assertEquals(0, getIntAttribute(mph, "PRODUCT_ERR"));
        Assert.assertEquals(1062953L, getLongAttribute(mph, "TOT_SIZE"));
        Assert.assertEquals(5830, getIntAttribute(mph, "SPH_SIZE"));
        Assert.assertEquals(13, getIntAttribute(mph, "NUM_DSD"));
        Assert.assertEquals(280, getIntAttribute(mph, "DSD_SIZE"));
        Assert.assertEquals(8, getIntAttribute(mph, "NUM_DATA_SETS"));
    }

    static void test_AT2_TOA_1P_Basics(Product product) {
        Assert.assertEquals(AT2_TOA_1P_CHILD_PRODUCT_NAME, product.getName());
        Assert.assertEquals(512, product.getSceneRasterWidth());
        Assert.assertEquals(288, product.getSceneRasterHeight());
    }

    static void test_AT2_TOA_1P_BandValues(Product product) throws IOException {
        Assert.assertEquals(289.67999267578125, getSampleBandValue(product, 0), 1e-8);
        Assert.assertEquals(292.3699951171875, getSampleBandValue(product, 1), 1e-8);
        Assert.assertFalse(isSamplePixelValid(product, 2));
        Assert.assertEquals(2.509999990463257, getSampleBandValue(product, 3), 1e-8);
        Assert.assertEquals(2.8899998664855957, getSampleBandValue(product, 4), 1e-8);
        Assert.assertEquals(3.7200000286102295, getSampleBandValue(product, 5), 1e-8);
        Assert.assertFalse(isSamplePixelValid(product, 6));
        Assert.assertEquals(287.41998291015625, getSampleBandValue(product, 7), 1e-8);
        Assert.assertEquals(290.1199951171875, getSampleBandValue(product, 8), 1e-8);
        Assert.assertFalse(isSamplePixelValid(product, 9));
        Assert.assertEquals(2.879999876022339, getSampleBandValue(product, 10), 1e-8);
        Assert.assertFalse(isSamplePixelValid(product, 11));
        Assert.assertFalse(isSamplePixelValid(product, 12));
        Assert.assertFalse(isSamplePixelValid(product, 13));
        Assert.assertEquals(8.0, getSampleBandValue(product, 14), 1e-8);
        Assert.assertEquals(8.0, getSampleBandValue(product, 15), 1e-8);
        Assert.assertEquals(34.0, getSampleBandValue(product, 16), 1e-8);
        Assert.assertEquals(34.0, getSampleBandValue(product, 17), 1e-8);
    }

    static void test_AT2_TOA_1P_TiePointValues(Product product) {
        final TiePointGrid[] tiePointGrids = product.getTiePointGrids();
        Assert.assertEquals(15, tiePointGrids.length);
        Assert.assertEquals(16.66931915283203, getSampleTiePointValue(product, 0), 1e-5);
        Assert.assertEquals(-51.17422866821289, getSampleTiePointValue(product, 1), 1e-4);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 2), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 3), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 4), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 5), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 6), 1e-8);
        Assert.assertEquals(52.867919921875, getSampleTiePointValue(product, 7), 1e-8);
        Assert.assertEquals(79.91788482666016, getSampleTiePointValue(product, 8), 1e-8);
        Assert.assertEquals(152.36390686035156, getSampleTiePointValue(product, 9), 1e-8);
        Assert.assertEquals(90.97491455078125, getSampleTiePointValue(product, 10), 1e-8);
        Assert.assertEquals(52.61534881591797, getSampleTiePointValue(product, 11), 1e-8);
        Assert.assertEquals(35.34031295776367, getSampleTiePointValue(product, 12), 1e-8);
        Assert.assertEquals(151.60743713378906, getSampleTiePointValue(product, 13), 1e-8);
        Assert.assertEquals(19.943693161010742, getSampleTiePointValue(product, 14), 1e-8);
    }

    static void test_AT2_TOA_1P_GeoCoding(Product product) {
        testGeoCoding(product, -51.08735275268555, 18.50958824157715, 99.97146606445312, 199.837646484375);
    }

    static void test_AT2_TOA_1P_MPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement mph = metadataRoot.getElement(MPH);
        Assert.assertNotNull(mph);
        Assert.assertEquals(AT2_TOA_1P_CHILD_PRODUCT_NAME, getStringAttribute(mph, Constants.PRODUCT));
        Assert.assertEquals("T", getStringAttribute(mph, Constants.PROC_STAGE));
        Assert.assertEquals("PO-TN-RAL-GS-10003_12/1", getStringAttribute(mph, Constants.REF_DOC));
        Assert.assertEquals("UK-PAC              ", getStringAttribute(mph, Constants.ACQUISITION_STATION));
        Assert.assertEquals("RAL   ", getStringAttribute(mph, Constants.PROC_CENTER));
        Assert.assertEquals("24-NOV-2006 20:20:59.000000", getDateAttributeAsString(mph, Constants.PROC_TIME));
        Assert.assertEquals("STEP/0.0+     ", getStringAttribute(mph, Constants.SOFTWARE_VER));
        Assert.assertEquals("07-NOV-2000 13:59:32.064000", getDateAttributeAsString(mph, Constants.SENSING_START));
        Assert.assertEquals("07-NOV-2000 14:00:15.264000", getDateAttributeAsString(mph, "SENSING_STOP"));
        Assert.assertEquals("1", getStringAttribute(mph, "PHASE"));
        Assert.assertEquals(58, getIntAttribute(mph, "CYCLE"));
        Assert.assertEquals(109, getIntAttribute(mph, "REL_ORBIT"));
        Assert.assertEquals(29021, getIntAttribute(mph, "ABS_ORBIT"));
        Assert.assertEquals("07-NOV-2000 14:55:31.301000", getDateAttributeAsString(mph, "STATE_VECTOR_TIME"));
        Assert.assertEquals(0.0, getDoubleAttribute(mph, "DELTA_UT1"), 1e-8);
        Assert.assertEquals(-2876107.5, getDoubleAttribute(mph, "X_POSITION"), 1e-8);
        Assert.assertEquals(6562959.59, getDoubleAttribute(mph, "Y_POSITION"), 1e-8);
        Assert.assertEquals(1.23, getDoubleAttribute(mph, "Z_POSITION"), 1e-8);
        Assert.assertEquals(1496.37, getDoubleAttribute(mph, "X_VELOCITY"), 1e-8);
        Assert.assertEquals(646.48, getDoubleAttribute(mph, "Y_VELOCITY"), 1e-8);
        Assert.assertEquals(7377.32, getDoubleAttribute(mph, "Z_VELOCITY"), 1e-8);
        Assert.assertEquals("FP", getStringAttribute(mph, "VECTOR_SOURCE"));
        Assert.assertEquals("07-NOV-2000 15:13:50.915000", getDateAttributeAsString(mph, "UTC_SBT_TIME"));
        Assert.assertEquals(1903672104L, getLongAttribute(mph, "SAT_BINARY_TIME"));
        Assert.assertEquals(3906250000L, getLongAttribute(mph, "CLOCK_STEP"));
        Assert.assertEquals("                           ", getDateAttributeAsString(mph, "LEAP_UTC"));
        Assert.assertEquals(0, getIntAttribute(mph, "LEAP_SIGN"));
        Assert.assertEquals(0, getIntAttribute(mph, "LEAP_ERR"));
        Assert.assertEquals(0, getIntAttribute(mph, "PRODUCT_ERR"));
        Assert.assertEquals(5486585L, getLongAttribute(mph, "TOT_SIZE"));
        Assert.assertEquals(12830, getIntAttribute(mph, "SPH_SIZE"));
        Assert.assertEquals(36, getIntAttribute(mph, "NUM_DSD"));
        Assert.assertEquals(280, getIntAttribute(mph, "DSD_SIZE"));
        Assert.assertEquals(26, getIntAttribute(mph, "NUM_DATA_SETS"));
    }

    static void test_AT2_TOA_1P_invDSD_Basics(Product product) {
        Assert.assertEquals(AT2_TOA_1P_CHILD_INV_DSD_PRODUCT_NAME, product.getName());
        Assert.assertEquals(512, product.getSceneRasterWidth());
        Assert.assertEquals(224, product.getSceneRasterHeight());
    }

    static void test_AT2_TOA_1P_invDSD_BandValues(Product product) throws IOException {
        Assert.assertEquals(292.0099792480469, getSampleBandValue(product, 0), 1e-8);
        Assert.assertEquals(294.5299987792969, getSampleBandValue(product, 1), 1e-8);
        Assert.assertEquals(296.3299865722656, getSampleBandValue(product, 2), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 3), 1e-8);
        Assert.assertFalse(isSamplePixelValid(product, 4));
        Assert.assertFalse(isSamplePixelValid(product, 5));
        Assert.assertFalse(isSamplePixelValid(product, 6));
        Assert.assertEquals(289.4100036621094, getSampleBandValue(product, 7), 1e-8);
        Assert.assertEquals(292.3299865722656, getSampleBandValue(product, 8), 1e-8);
        Assert.assertEquals(295.1099853515625, getSampleBandValue(product, 9), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 10), 1e-8);
        Assert.assertFalse(isSamplePixelValid(product, 11));
        Assert.assertFalse(isSamplePixelValid(product, 12));
        Assert.assertFalse(isSamplePixelValid(product, 13));
        Assert.assertEquals(8.0, getSampleBandValue(product, 14), 1e-8);
        Assert.assertEquals(8.0, getSampleBandValue(product, 15), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 16), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 17), 1e-8);
    }

    static void test_AT2_TOA_1P_invDSD_TiePointValues(Product product) {
        final TiePointGrid[] tiePointGrids = product.getTiePointGrids();
        Assert.assertEquals(15, tiePointGrids.length);
        Assert.assertEquals(28.217761993408203, getSampleTiePointValue(product, 0), 1e-5);
        Assert.assertEquals(-77.03311157226562, getSampleTiePointValue(product, 1), 1e-4);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 2), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 3), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 4), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 5), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 6), 1e-8);
        Assert.assertEquals(-34.41516876220703, getSampleTiePointValue(product, 7), 1e-8);
        Assert.assertEquals(79.91600036621094, getSampleTiePointValue(product, 8), 1e-8);
        Assert.assertEquals(-28.613815307617188, getSampleTiePointValue(product, 9), 1e-8);
        Assert.assertEquals(-113.94967651367188, getSampleTiePointValue(product, 10), 1e-8);
        Assert.assertEquals(-34.1906852722168, getSampleTiePointValue(product, 11), 1e-8);
        Assert.assertEquals(35.33645248413086, getSampleTiePointValue(product, 12), 1e-8);
        Assert.assertEquals(-29.145219802856445, getSampleTiePointValue(product, 13), 1e-8);
        Assert.assertEquals(174.9927215576172, getSampleTiePointValue(product, 14), 1e-8);
    }

    static void test_AT2_TOA_1P_invDSD_GeoCoding(Product product) {
        testGeoCoding(product, -76.25853729248047, 26.515056610107422, 99.97146606445312, 199.837646484375);
    }

    static void test_AT2_TOA_1P_invDSD_MPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement mph = metadataRoot.getElement(MPH);
        Assert.assertNotNull(mph);
        Assert.assertEquals(AT2_TOA_1P_CHILD_INV_DSD_PRODUCT_NAME, getStringAttribute(mph, Constants.PRODUCT));
        Assert.assertEquals("T", getStringAttribute(mph, Constants.PROC_STAGE));
        Assert.assertEquals("PO-TN-RAL-GS-10003_12/1", getStringAttribute(mph, Constants.REF_DOC));
        Assert.assertEquals("UK-PAC              ", getStringAttribute(mph, Constants.ACQUISITION_STATION));
        Assert.assertEquals("RAL   ", getStringAttribute(mph, Constants.PROC_CENTER));
        Assert.assertEquals("06-MAY-2008 01:36:39.000000", getDateAttributeAsString(mph, Constants.PROC_TIME));
        Assert.assertEquals("STEP/1.3      ", getStringAttribute(mph, Constants.SOFTWARE_VER));
        Assert.assertEquals("30-MAY-1997 03:23:53.184000", getDateAttributeAsString(mph, Constants.SENSING_START));
        Assert.assertEquals("30-MAY-1997 03:24:26.784000", getDateAttributeAsString(mph, "SENSING_STOP"));
        Assert.assertEquals("1", getStringAttribute(mph, "PHASE"));
        Assert.assertEquals(22, getIntAttribute(mph, "CYCLE"));
        Assert.assertEquals(146, getIntAttribute(mph, "REL_ORBIT"));
        Assert.assertEquals(11022, getIntAttribute(mph, "ABS_ORBIT"));
        Assert.assertEquals("30-MAY-1997 03:17:01.067000", getDateAttributeAsString(mph, "STATE_VECTOR_TIME"));
        Assert.assertEquals(0.0, getDoubleAttribute(mph, "DELTA_UT1"), 1e-8);
        Assert.assertEquals(2247438.62, getDoubleAttribute(mph, "X_POSITION"), 1e-8);
        Assert.assertEquals(-6804005.54, getDoubleAttribute(mph, "Y_POSITION"), 1e-8);
        Assert.assertEquals(1.23, getDoubleAttribute(mph, "Z_POSITION"), 1e-8);
        Assert.assertEquals(-1551.1, getDoubleAttribute(mph, "X_VELOCITY"), 1e-8);
        Assert.assertEquals(-503.23, getDoubleAttribute(mph, "Y_VELOCITY"), 1e-8);
        Assert.assertEquals(7377.13, getDoubleAttribute(mph, "Z_VELOCITY"), 1e-8);
        Assert.assertEquals("FP", getStringAttribute(mph, "VECTOR_SOURCE"));
        Assert.assertEquals("30-MAY-1997 00:12:44.641000", getDateAttributeAsString(mph, "UTC_SBT_TIME"));
        Assert.assertEquals(4151775363L, getLongAttribute(mph, "SAT_BINARY_TIME"));
        Assert.assertEquals(3906250000L, getLongAttribute(mph, "CLOCK_STEP"));
        Assert.assertEquals("                           ", getDateAttributeAsString(mph, "LEAP_UTC"));
        Assert.assertEquals(0, getIntAttribute(mph, "LEAP_SIGN"));
        Assert.assertEquals(0, getIntAttribute(mph, "LEAP_ERR"));
        Assert.assertEquals(0, getIntAttribute(mph, "PRODUCT_ERR"));
        Assert.assertEquals(4271763L, getLongAttribute(mph, "TOT_SIZE"));
        Assert.assertEquals(12830, getIntAttribute(mph, "SPH_SIZE"));
        Assert.assertEquals(38, getIntAttribute(mph, "NUM_DSD"));
        Assert.assertEquals(280, getIntAttribute(mph, "DSD_SIZE"));
        Assert.assertEquals(26, getIntAttribute(mph, "NUM_DATA_SETS"));
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private static final String ATS_NR__2P_CHILD_PRODUCT_NAME = "ATS_NR__2PNTOM20060329_105559_000000772046_00223_21319_0499.N1";
    private static final String ATS_TOA_1P_CHILD_PRODUCT_NAME = "ATS_TOA_1PPMOT20070110_193707_000000482054_00328_25432_0679.N1";
    private static final String AT1_NR__2P_CHILD_PRODUCT_NAME = "AT1_NR__2PTOMT19930614_132259_000000344013_00338_10002_0699.E1";
    private static final String AT1_TOA_1P_CHILD_PRODUCT_NAME = "AT1_TOA_1PTMTO19950714_130555_000000348004_00366_20895_0799.E1";
    private static final String AT2_NR__2P_CHILD_PRODUCT_NAME = "AT2_NR__2PTMTO20001107_150906_000000481058_00110_29022_0799.E2";
    private static final String AT2_TOA_1P_CHILD_PRODUCT_NAME = "AT2_TOA_1PTTOM20001107_135932_000000431058_00109_29021_0899.E2";
    private static final String AT2_TOA_1P_CHILD_INV_DSD_PRODUCT_NAME = "AT2_TOA_1PTMTO19970530_032353_000000341022_00146_11022_0900.E2";

    private static void checkCommonAtsrSPHElements(MetadataElement sph) {
        final ProductData latLongTiePts = sph.getAttribute("LAT_LONG_TIE_POINTS").getData();
        Assert.assertEquals(-275, latLongTiePts.getElemIntAt(0));
        Assert.assertEquals(-250, latLongTiePts.getElemIntAt(1));
        Assert.assertEquals(-225, latLongTiePts.getElemIntAt(2));
        Assert.assertEquals(-200, latLongTiePts.getElemIntAt(3));
        Assert.assertEquals(-175, latLongTiePts.getElemIntAt(4));
        Assert.assertEquals(-150, latLongTiePts.getElemIntAt(5));
        Assert.assertEquals(-125, latLongTiePts.getElemIntAt(6));
        Assert.assertEquals(-100, latLongTiePts.getElemIntAt(7));
        Assert.assertEquals(-75, latLongTiePts.getElemIntAt(8));
        Assert.assertEquals(-50, latLongTiePts.getElemIntAt(9));
        Assert.assertEquals(-25, latLongTiePts.getElemIntAt(10));
        Assert.assertEquals(0, latLongTiePts.getElemIntAt(11));
        Assert.assertEquals(25, latLongTiePts.getElemIntAt(12));
        Assert.assertEquals(50, latLongTiePts.getElemIntAt(13));
        Assert.assertEquals(75, latLongTiePts.getElemIntAt(14));
        Assert.assertEquals(100, latLongTiePts.getElemIntAt(15));
        Assert.assertEquals(125, latLongTiePts.getElemIntAt(16));
        Assert.assertEquals(150, latLongTiePts.getElemIntAt(17));
        Assert.assertEquals(175, latLongTiePts.getElemIntAt(18));
        Assert.assertEquals(200, latLongTiePts.getElemIntAt(19));
        Assert.assertEquals(225, latLongTiePts.getElemIntAt(20));
        Assert.assertEquals(250, latLongTiePts.getElemIntAt(21));
        Assert.assertEquals(275, latLongTiePts.getElemIntAt(22));

        final ProductData viewAngleTiePts = sph.getAttribute("VIEW_ANGLE_TIE_POINTS").getData();
        Assert.assertEquals(-250, viewAngleTiePts.getElemIntAt(0));
        Assert.assertEquals(-200, viewAngleTiePts.getElemIntAt(1));
        Assert.assertEquals(-150, viewAngleTiePts.getElemIntAt(2));
        Assert.assertEquals(-100, viewAngleTiePts.getElemIntAt(3));
        Assert.assertEquals(-50, viewAngleTiePts.getElemIntAt(4));
        Assert.assertEquals(0, viewAngleTiePts.getElemIntAt(5));
        Assert.assertEquals(50, viewAngleTiePts.getElemIntAt(6));
        Assert.assertEquals(100, viewAngleTiePts.getElemIntAt(7));
        Assert.assertEquals(150, viewAngleTiePts.getElemIntAt(8));
        Assert.assertEquals(200, viewAngleTiePts.getElemIntAt(9));
        Assert.assertEquals(250, viewAngleTiePts.getElemIntAt(10));

        final ProductData xyTiePts = sph.getAttribute("XY_TIE_POINTS_PIXEL_NUM").getData();
        for (int i = 0; i < 57; i++) {
            Assert.assertEquals(10 * i, xyTiePts.getElemIntAt(i));
        }

        Assert.assertEquals(574, xyTiePts.getElemIntAt(58));

        for (int i = 59; i < 99; i++) {
            Assert.assertEquals(10 * (i - 59), xyTiePts.getElemIntAt(i));
        }
    }
}
