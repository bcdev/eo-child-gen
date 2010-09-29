package com.bc.childgen;

/*
import junit.framework.Assert;
import org.esa.beam.framework.datamodel.MetadataElement;
import org.esa.beam.framework.datamodel.Product;
import org.esa.beam.framework.datamodel.ProductData;
import org.esa.beam.framework.datamodel.TiePointGrid;
import java.io.IOException;
*/

@SuppressWarnings({"MagicNumber"})
class MerisProductTester extends ProductTester {
/*
    static void test_RR_1P_Basics(Product product) {
        Assert.assertEquals(MER_RR__1P_CHILD_PRODUCT_NAME, product.getName());
        Assert.assertEquals(1121, product.getSceneRasterWidth());
        Assert.assertEquals(258, product.getSceneRasterHeight());
    }

    static void test_RR_1P_BandNames(Product product) {
        final String[] bandNames = product.getBandNames();
        Assert.assertEquals(17, bandNames.length);
        Assert.assertEquals("radiance_1", bandNames[0]);
        Assert.assertEquals("radiance_2", bandNames[1]);
        Assert.assertEquals("radiance_3", bandNames[2]);
        Assert.assertEquals("radiance_4", bandNames[3]);
        Assert.assertEquals("radiance_5", bandNames[4]);
        Assert.assertEquals("radiance_6", bandNames[5]);
        Assert.assertEquals("radiance_7", bandNames[6]);
        Assert.assertEquals("radiance_8", bandNames[7]);
        Assert.assertEquals("radiance_9", bandNames[8]);
        Assert.assertEquals("radiance_10", bandNames[9]);
        Assert.assertEquals("radiance_11", bandNames[10]);
        Assert.assertEquals("radiance_12", bandNames[11]);
        Assert.assertEquals("radiance_13", bandNames[12]);
        Assert.assertEquals("radiance_14", bandNames[13]);
        Assert.assertEquals("radiance_15", bandNames[14]);
        Assert.assertEquals("l1_flags", bandNames[15]);
        Assert.assertEquals("detector_index", bandNames[16]);
    }

    static void test_RR_1P_BandValues(Product product) throws IOException {
        Assert.assertEquals(110.2113265991211, getSampleBandValue(product, 0), 1e-8);
        Assert.assertEquals(113.90254974365234, getSampleBandValue(product, 1), 1e-8);
        Assert.assertEquals(112.26374816894531, getSampleBandValue(product, 2), 1e-8);
        Assert.assertEquals(113.55514526367188, getSampleBandValue(product, 3), 1e-8);
        Assert.assertEquals(121.52398681640625, getSampleBandValue(product, 4), 1e-8);
        Assert.assertEquals(125.7286376953125, getSampleBandValue(product, 5), 1e-8);
        Assert.assertEquals(127.63441467285156, getSampleBandValue(product, 6), 1e-8);
        Assert.assertEquals(127.03425598144531, getSampleBandValue(product, 7), 1e-8);
        Assert.assertEquals(126.45073699951172, getSampleBandValue(product, 8), 1e-8);
        Assert.assertEquals(134.69174194335938, getSampleBandValue(product, 9), 1e-8);
        Assert.assertEquals(43.486324310302734, getSampleBandValue(product, 10), 1e-8);
        Assert.assertEquals(128.9069061279297, getSampleBandValue(product, 11), 1e-8);
        Assert.assertEquals(109.43817138671875, getSampleBandValue(product, 12), 1e-8);
        Assert.assertEquals(105.75299072265625, getSampleBandValue(product, 13), 1e-8);
        Assert.assertEquals(68.27298736572266, getSampleBandValue(product, 14), 1e-8);
        Assert.assertEquals(16.0, getSampleBandValue(product, 15), 1e-8);
        Assert.assertEquals(906.0, getSampleBandValue(product, 16), 1e-8);
    }

    static void test_RR_1P_TiePointValues(Product product) {
        final TiePointGrid[] tiePointGrids = product.getTiePointGrids();
        Assert.assertEquals(15, tiePointGrids.length);
        Assert.assertEquals(31.371868133544922, getSampleTiePointValue(product, 0), 1e-8);
        Assert.assertEquals(9.287653923034668, getSampleTiePointValue(product, 1), 1e-8);
        Assert.assertEquals(357.8984375, getSampleTiePointValue(product, 2), 1e-8);
        Assert.assertEquals(0.5859375, getSampleTiePointValue(product, 3), 1e-8);
        Assert.assertEquals(-3.9554672548547387E-4, getSampleTiePointValue(product, 4), 1e-8);
        Assert.assertEquals(0.0024123280309140682, getSampleTiePointValue(product, 5), 1e-8);
        Assert.assertEquals(29.37315559387207, getSampleTiePointValue(product, 6), 1e-8);
        Assert.assertEquals(111.7332534790039, getSampleTiePointValue(product, 7), 1e-4);
        Assert.assertEquals(32.841304779052734, getSampleTiePointValue(product, 8), 1e-8);
        Assert.assertEquals(100.95291137695312, getSampleTiePointValue(product, 9), 1e-8);
        Assert.assertEquals(-2.058594226837158, getSampleTiePointValue(product, 10), 1e-8);
        Assert.assertEquals(-1.787500023841858, getSampleTiePointValue(product, 11), 1e-8);
        Assert.assertEquals(1014.2710571289062, getSampleTiePointValue(product, 12), 1e-8);
        Assert.assertEquals(328.5726318359375, getSampleTiePointValue(product, 13), 1e-8);
        Assert.assertEquals(30.341400146484375, getSampleTiePointValue(product, 14), 1e-8);
    }

    static void test_RR_1P_GeoCoding(Product product) {
        testGeoCoding(product, 9.38344669342041, 33.505645751953125, 99.99068450927734, 200.00311279296875);
    }

    static void test_RR_1P_MPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement mph = metadataRoot.getElement(MPH);
        Assert.assertNotNull(mph);
        Assert.assertEquals(MER_RR__1P_CHILD_PRODUCT_NAME, getStringAttribute(mph, Constants.PRODUCT));
        Assert.assertEquals("N", getStringAttribute(mph, Constants.PROC_STAGE));
        Assert.assertEquals("PO-RS-MDA-GS-2009_11_5A", getStringAttribute(mph, Constants.REF_DOC));
        Assert.assertEquals("PDHS-K              ", getStringAttribute(mph, Constants.ACQUISITION_STATION));
        Assert.assertEquals("PDHS-K", getStringAttribute(mph, Constants.PROC_CENTER));
        Assert.assertEquals("09-MAY-2006 11:13:06.000000", getDateAttributeAsString(mph, Constants.PROC_TIME));
        Assert.assertEquals("MERIS/5.02    ", getStringAttribute(mph, Constants.SOFTWARE_VER));
        Assert.assertEquals("09-MAY-2006 09:23:53.753364", getDateAttributeAsString(mph, Constants.SENSING_START));
        Assert.assertEquals("09-MAY-2006 09:24:38.806292", getDateAttributeAsString(mph, "SENSING_STOP"));
        Assert.assertEquals("2", getStringAttribute(mph, "PHASE"));
        Assert.assertEquals(47, getIntAttribute(mph, "CYCLE"));
        Assert.assertEquals(308, getIntAttribute(mph, "REL_ORBIT"));
        Assert.assertEquals(21905, getIntAttribute(mph, "ABS_ORBIT"));
        Assert.assertEquals("09-MAY-2006 08:43:25.844843", getDateAttributeAsString(mph, "STATE_VECTOR_TIME"));
        Assert.assertEquals(0.226314, getDoubleAttribute(mph, "DELTA_UT1"), 1e-8);
        Assert.assertEquals(-6767773.59, getDoubleAttribute(mph, "X_POSITION"), 1e-8);
        Assert.assertEquals(-2353108.625, getDoubleAttribute(mph, "Y_POSITION"), 1e-8);
        Assert.assertEquals(-0.0020, getDoubleAttribute(mph, "Z_POSITION"), 1e-8);
        Assert.assertEquals(-527.481595, getDoubleAttribute(mph, "X_VELOCITY"), 1e-8);
        Assert.assertEquals(1543.434748, getDoubleAttribute(mph, "Y_VELOCITY"), 1e-8);
        Assert.assertEquals(7377.476806, getDoubleAttribute(mph, "Z_VELOCITY"), 1e-8);
        Assert.assertEquals("DN", getStringAttribute(mph, "VECTOR_SOURCE"));
        Assert.assertEquals("09-MAY-2006 07:27:43.798179", getDateAttributeAsString(mph, "UTC_SBT_TIME"));
        Assert.assertEquals(3787747584L, getLongAttribute(mph, "SAT_BINARY_TIME"));
        Assert.assertEquals(3906249797L, getLongAttribute(mph, "CLOCK_STEP"));
        Assert.assertEquals("31-DEC-2005 23:59:59.000000", getDateAttributeAsString(mph, "LEAP_UTC"));
        Assert.assertEquals(1, getIntAttribute(mph, "LEAP_SIGN"));
        Assert.assertEquals(0, getIntAttribute(mph, "LEAP_ERR"));
        Assert.assertEquals(1, getIntAttribute(mph, "PRODUCT_ERR"));
        Assert.assertEquals(9670009L, getLongAttribute(mph, "TOT_SIZE"));
        Assert.assertEquals(9942, getIntAttribute(mph, "SPH_SIZE"));
        Assert.assertEquals(30, getIntAttribute(mph, "NUM_DSD"));
        Assert.assertEquals(280, getIntAttribute(mph, "DSD_SIZE"));
        Assert.assertEquals(19, getIntAttribute(mph, "NUM_DATA_SETS"));
    }

    static void test_RR_1P_SPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        MetadataElement sph = metadataRoot.getElement(SPH);
        Assert.assertNotNull(sph);
        Assert.assertEquals("MER_RR__1P SPECIFIC HEADER  ", getStringAttribute(sph, "SPH_DESCRIPTOR"));
        Assert.assertEquals(0, getIntAttribute(sph, "STRIPLINE_CONTINUITY_INDICATOR"));
        Assert.assertEquals(1, getIntAttribute(sph, "SLICE_POSITION"));
        Assert.assertEquals(1, getIntAttribute(sph, "NUM_SLICES"));
        Assert.assertEquals("09-MAY-2006 09:23:53.753364", getDateAttributeAsString(sph, "FIRST_LINE_TIME"));
        Assert.assertEquals("09-MAY-2006 09:24:38.806292", getDateAttributeAsString(sph, "LAST_LINE_TIME"));
        Assert.assertEquals(33283922, getIntAttribute(sph, "FIRST_FIRST_LAT"));
        Assert.assertEquals(21073209, getIntAttribute(sph, "FIRST_FIRST_LONG"));
        Assert.assertEquals(34670703, getIntAttribute(sph, "FIRST_MID_LAT"));
        Assert.assertEquals(14993375, getIntAttribute(sph, "FIRST_MID_LONG"));
        Assert.assertEquals(35744659, getIntAttribute(sph, "FIRST_LAST_LAT"));
        Assert.assertEquals(8731391, getIntAttribute(sph, "FIRST_LAST_LONG"));
        Assert.assertEquals(30662367, getIntAttribute(sph, "LAST_FIRST_LAT"));
        Assert.assertEquals(20147878, getIntAttribute(sph, "LAST_FIRST_LONG"));
        Assert.assertEquals(32016577, getIntAttribute(sph, "LAST_MID_LAT"));
        Assert.assertEquals(14233813, getIntAttribute(sph, "LAST_MID_LONG"));
        Assert.assertEquals(33087293, getIntAttribute(sph, "LAST_LAST_LAT"));
        Assert.assertEquals(8162191, getIntAttribute(sph, "LAST_LAST_LONG"));
        Assert.assertEquals(0, getIntAttribute(sph, "TRANS_ERR_FLAG"));
        Assert.assertEquals(1, getIntAttribute(sph, "FORMAT_ERR_FLAG"));
        Assert.assertEquals(0, getIntAttribute(sph, "DATABASE_FLAG"));
        Assert.assertEquals(0, getIntAttribute(sph, "COARSE_ERR_FLAG"));
        Assert.assertEquals(0, getIntAttribute(sph, "ECMWF_TYPE"));
        Assert.assertEquals(0, getIntAttribute(sph, "NUM_TRANS_ERR"));
        Assert.assertEquals(20976, getIntAttribute(sph, "NUM_FORMAT_ERR"));
        Assert.assertEquals(0.0, getDoubleAttribute(sph, "TRANS_ERR_THRESH"), 1e-8);
        Assert.assertEquals(0.0, getDoubleAttribute(sph, "FORMAT_ERR_THRESH"), 1e-8);
        Assert.assertEquals(15, getIntAttribute(sph, "NUM_BANDS"));

        final ProductData waveLengths = sph.getAttribute("BAND_WAVELEN").getData();
        Assert.assertEquals(412691, waveLengths.getElemIntAt(0));
        Assert.assertEquals(442559, waveLengths.getElemIntAt(1));
        Assert.assertEquals(489882, waveLengths.getElemIntAt(2));
        Assert.assertEquals(509819, waveLengths.getElemIntAt(3));
        Assert.assertEquals(559694, waveLengths.getElemIntAt(4));
        Assert.assertEquals(619601, waveLengths.getElemIntAt(5));
        Assert.assertEquals(664573, waveLengths.getElemIntAt(6));
        Assert.assertEquals(680821, waveLengths.getElemIntAt(7));
        Assert.assertEquals(708329, waveLengths.getElemIntAt(8));
        Assert.assertEquals(753371, waveLengths.getElemIntAt(9));
        Assert.assertEquals(761508, waveLengths.getElemIntAt(10));
        Assert.assertEquals(778409, waveLengths.getElemIntAt(11));
        Assert.assertEquals(864876, waveLengths.getElemIntAt(12));
        Assert.assertEquals(884944, waveLengths.getElemIntAt(13));
        Assert.assertEquals(900000, waveLengths.getElemIntAt(14));

        final ProductData bandWidths = sph.getAttribute("BANDWIDTH").getData();
        Assert.assertEquals(9937, bandWidths.getElemIntAt(0));
        Assert.assertEquals(9946, bandWidths.getElemIntAt(1));
        Assert.assertEquals(9957, bandWidths.getElemIntAt(2));
        Assert.assertEquals(9961, bandWidths.getElemIntAt(3));
        Assert.assertEquals(9970, bandWidths.getElemIntAt(4));
        Assert.assertEquals(9979, bandWidths.getElemIntAt(5));
        Assert.assertEquals(9985, bandWidths.getElemIntAt(6));
        Assert.assertEquals(7488, bandWidths.getElemIntAt(7));
        Assert.assertEquals(9992, bandWidths.getElemIntAt(8));
        Assert.assertEquals(7495, bandWidths.getElemIntAt(9));
        Assert.assertEquals(3744, bandWidths.getElemIntAt(10));
        Assert.assertEquals(15010, bandWidths.getElemIntAt(11));
        Assert.assertEquals(20047, bandWidths.getElemIntAt(12));
        Assert.assertEquals(10018, bandWidths.getElemIntAt(13));
        Assert.assertEquals(10020, bandWidths.getElemIntAt(14));

        Assert.assertEquals(76639, getIntAttribute(sph, "INST_FOV"));
        Assert.assertEquals(1, getIntAttribute(sph, "PROC_MODE"));
        Assert.assertEquals(0, getIntAttribute(sph, "OFFSET_COMP"));
        Assert.assertEquals(175988, getIntAttribute(sph, "LINE_TIME_INTERVAL"));
        Assert.assertEquals(1121, getIntAttribute(sph, "LINE_LENGTH"));
        Assert.assertEquals(16, getIntAttribute(sph, "LINES_PER_TIE_PT"));
        Assert.assertEquals(16, getIntAttribute(sph, "SAMPLES_PER_TIE_PT"));
        Assert.assertEquals(1040.0, getDoubleAttribute(sph, "COLUMN_SPACING"), 1e-8);
    }

    static void test_RR_1P_DSDs(Product childProduct) {
        final MetadataElement metadataRoot = childProduct.getMetadataRoot();
        final MetadataElement dsds = metadataRoot.getElement("DSD");
        Assert.assertEquals(30, dsds.getNumElements());
        assertDSD(dsds.getElementAt(0), "Quality ADS", "A", "", 11189, 99, 3, 33);
        assertDSD(dsds.getElementAt(1), "Scaling Factor GADS", "G", "", 11288, 292, 1, 292);
        assertDSD(dsds.getElementAt(2), "Tie points ADS", "A", "", 11580, 60571, 17, 3563);
        assertDSD(dsds.getElementAt(3), "Radiance MDS(1)", "M", "", 72151, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(4), "Radiance MDS(2)", "M", "", 653941, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(5), "Radiance MDS(3)", "M", "", 1235731, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(6), "Radiance MDS(4)", "M", "", 1817521, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(7), "Radiance MDS(5)", "M", "", 2399311, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(8), "Radiance MDS(6)", "M", "", 2981101, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(9), "Radiance MDS(7)", "M", "", 3562891, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(10), "Radiance MDS(8)", "M", "", 4144681, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(11), "Radiance MDS(9)", "M", "", 4726471, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(12), "Radiance MDS(10)", "M", "", 5308261, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(13), "Radiance MDS(11)", "M", "", 5890051, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(14), "Radiance MDS(12)", "M", "", 6471841, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(15), "Radiance MDS(13)", "M", "", 7053631, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(16), "Radiance MDS(14)", "M", "", 7635421, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(17), "Radiance MDS(15)", "M", "", 8217211, 581790, 258, 2255);
        assertDSD(dsds.getElementAt(18), "Flags MDS(16)", "M", "", 8799001, 871008, 258, 3376);
        assertDSD(dsds.getElementAt(19), "MERIS_SOURCE_PACKETS", "R", "MER_RR__0PNPDK20060509_091240_000021802047_00308_21905_0850.N1", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(20), "INSTRUMENT_DATA_FILE", "R", "MER_INS_AXVIEC20050708_134312_20050101_000000_20150101_000000", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(21), "PROCESSING_PARAMS_L1B_FILE", "R", "MER_CP1_AXVIEC20050607_065745_20020321_193100_20120321_193100", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(22), "RADIOMETRIC_CALIBRATION_FILE", "R", "MER_RAC_AXVIEC20050708_135806_20041213_220000_20141213_220000", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(23), "DIGITAL_ELEVATION_MODEL_FILE", "R", "AUX_DEM_AXVIEC20031201_000000_20031201_000000_20200101_000000", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(24), "DIGITAL_ROUGHNESS_MODEL_FILE", "R", "MER_DRM_AXVIEC20020122_083343_20020101_000000_20200101_000000", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(25), "LAND_SEA_MASK_DATA_FILE", "R", "AUX_LSM_AXVIEC20020123_141228_20020101_000000_20200101_000000", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(26), "ECMWF_DATA_FILE", "R", "AUX_ECF_AXNECM20060509_104042_20060509_090000_20060509_210000", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(27), "ORBIT_STATE_VECTOR_FILE", "R", "DOR_NAV_0PNPDK20060509_072603_000060562047_00307_21904_0618.N1", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(28), "ATTITUDE_DATA_FILE", "R", "AUX_ATT_AXVIEC20020924_131534_20020703_120000_20781231_235959", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(29), "", "?", "", 0, 0, 0, 0);
    }

    static void test_FR_2P_Basics(Product product) {
        Assert.assertEquals(MER_FR_2P_CHILD_PRODUCT_NAME, product.getName());
        Assert.assertEquals(2241, product.getSceneRasterWidth());
        Assert.assertEquals(321, product.getSceneRasterHeight());
    }

    static void test_FR_2P_BandNames(Product product) {
        final String[] bandNames = product.getBandNames();
        Assert.assertEquals(31, bandNames.length);
        Assert.assertEquals("reflec_1", bandNames[0]);
        Assert.assertEquals("reflec_2", bandNames[1]);
        Assert.assertEquals("reflec_3", bandNames[2]);
        Assert.assertEquals("reflec_4", bandNames[3]);
        Assert.assertEquals("reflec_5", bandNames[4]);
        Assert.assertEquals("reflec_6", bandNames[5]);
        Assert.assertEquals("reflec_7", bandNames[6]);
        Assert.assertEquals("reflec_8", bandNames[7]);
        Assert.assertEquals("reflec_9", bandNames[8]);
        Assert.assertEquals("reflec_10", bandNames[9]);
        Assert.assertEquals("reflec_12", bandNames[10]);
        Assert.assertEquals("reflec_13", bandNames[11]);
        Assert.assertEquals("reflec_14", bandNames[12]);
        Assert.assertEquals("water_vapour", bandNames[13]);
        Assert.assertEquals("algal_1", bandNames[14]);
        Assert.assertEquals("algal_2", bandNames[15]);
        Assert.assertEquals("yellow_subs", bandNames[16]);
        Assert.assertEquals("total_susp", bandNames[17]);
        Assert.assertEquals("photosyn_rad", bandNames[18]);
        Assert.assertEquals("toa_veg", bandNames[19]);
        Assert.assertEquals("boa_veg", bandNames[20]);
        Assert.assertEquals("rect_refl_red", bandNames[21]);
        Assert.assertEquals("rect_refl_nir", bandNames[22]);
        Assert.assertEquals("surf_press", bandNames[23]);
        Assert.assertEquals("aero_epsilon", bandNames[24]);
        Assert.assertEquals("aero_opt_thick", bandNames[25]);
        Assert.assertEquals("cloud_albedo", bandNames[26]);
        Assert.assertEquals("cloud_opt_thick", bandNames[27]);
        Assert.assertEquals("cloud_top_press", bandNames[28]);
        Assert.assertEquals("cloud_type", bandNames[29]);
        Assert.assertEquals("l2_flags", bandNames[30]);
    }

    static void test_FR_2P_BandValues(Product product) throws IOException {
        Assert.assertEquals(0.02606280706822872, getSampleBandValue(product, 0), 1e-8);
        Assert.assertEquals(0.024002807214856148, getSampleBandValue(product, 1), 1e-8);
        Assert.assertEquals(0.02294991910457611, getSampleBandValue(product, 2), 1e-8);
        Assert.assertEquals(0.02212591841816902, getSampleBandValue(product, 3), 1e-8);
        Assert.assertEquals(0.02047792077064514, getSampleBandValue(product, 4), 1e-8);
        Assert.assertEquals(0.011719107627868652, getSampleBandValue(product, 5), 1e-8);
        Assert.assertEquals(0.009292885661125183, getSampleBandValue(product, 6), 1e-8);
        Assert.assertEquals(0.00958281196653843, getSampleBandValue(product, 7), 1e-8);
        Assert.assertEquals(0.007248145993798971, getSampleBandValue(product, 8), 1e-8);
        Assert.assertEquals(0.0040894802659749985, getSampleBandValue(product, 9), 1e-8);
        Assert.assertEquals(0.003616443369537592, getSampleBandValue(product, 10), 1e-8);
        Assert.assertEquals(0.0015106662176549435, getSampleBandValue(product, 11), 1e-8);
        Assert.assertEquals(0.0, getSampleBandValue(product, 12), 1e-8);
        Assert.assertEquals(1.2300000190734863, getSampleBandValue(product, 13), 1e-8);
        Assert.assertEquals(1.1772431135177612, getSampleBandValue(product, 14), 1e-8);
        Assert.assertEquals(2.2611570358276367, getSampleBandValue(product, 15), 1e-8);
        Assert.assertEquals(0.09842519462108612, getSampleBandValue(product, 16), 1e-8);
        Assert.assertEquals(3.689173936843872, getSampleBandValue(product, 17), 1e-8);
        Assert.assertEquals(1396.150146484375, getSampleBandValue(product, 18), 1e-8);
        Assert.assertFalse(isSamplePixelValid(product, 19));
        Assert.assertFalse(isSamplePixelValid(product, 20));
        Assert.assertFalse(isSamplePixelValid(product, 21));
        Assert.assertFalse(isSamplePixelValid(product, 22));
        Assert.assertFalse(isSamplePixelValid(product, 23));
        Assert.assertEquals(1.0078740119934082, getSampleBandValue(product, 24), 1e-8);
        Assert.assertEquals(0.4346456527709961, getSampleBandValue(product, 25), 1e-8);
        Assert.assertFalse(isSamplePixelValid(product, 26));
        Assert.assertFalse(isSamplePixelValid(product, 27));
        Assert.assertFalse(isSamplePixelValid(product, 28));
        Assert.assertFalse(isSamplePixelValid(product, 29));
        Assert.assertEquals(3654545.0, getSampleBandValue(product, 30), 1e-8);
    }

    static void testTiePointNames(Product product) {
        final String[] tiePointGridNames = product.getTiePointGridNames();
        Assert.assertEquals(15, tiePointGridNames.length);

        Assert.assertEquals("latitude", tiePointGridNames[0]);
        Assert.assertEquals("longitude", tiePointGridNames[1]);
        Assert.assertEquals("dem_alt", tiePointGridNames[2]);
        Assert.assertEquals("dem_rough", tiePointGridNames[3]);
        Assert.assertEquals("lat_corr", tiePointGridNames[4]);
        Assert.assertEquals("lon_corr", tiePointGridNames[5]);
        Assert.assertEquals("sun_zenith", tiePointGridNames[6]);
        Assert.assertEquals("sun_azimuth", tiePointGridNames[7]);
        Assert.assertEquals("view_zenith", tiePointGridNames[8]);
        Assert.assertEquals("view_azimuth", tiePointGridNames[9]);
        Assert.assertEquals("zonal_wind", tiePointGridNames[10]);
        Assert.assertEquals("merid_wind", tiePointGridNames[11]);
        Assert.assertEquals("atm_press", tiePointGridNames[12]);
        Assert.assertEquals("ozone", tiePointGridNames[13]);
        Assert.assertEquals("rel_hum", tiePointGridNames[14]);
    }

    static void test_FR_2P_TiePointValues(Product product) {
        final TiePointGrid[] tiePointGrids = product.getTiePointGrids();
        Assert.assertEquals(15, tiePointGrids.length);
        Assert.assertEquals(55.14881896972656, getSampleTiePointValue(product, 0), 1e-8);
        Assert.assertEquals(7.134877681732178, getSampleTiePointValue(product, 1), 1e-8);
        Assert.assertEquals(-21.7626953125, getSampleTiePointValue(product, 2), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 3), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 4), 1e-8);
        Assert.assertEquals(0.0, getSampleTiePointValue(product, 5), 1e-8);
        Assert.assertEquals(46.625152587890625, getSampleTiePointValue(product, 6), 1e-8);
        Assert.assertEquals(156.13522338867188, getSampleTiePointValue(product, 7), 1e-8);
        Assert.assertEquals(0.12683673202991486, getSampleTiePointValue(product, 8), 1e-8);
        Assert.assertEquals(174.0037841796875, getSampleTiePointValue(product, 9), 1e-8);
        Assert.assertEquals(-7.9584479331970215, getSampleTiePointValue(product, 10), 1e-8);
        Assert.assertEquals(-0.22968749701976776, getSampleTiePointValue(product, 11), 1e-8);
        Assert.assertEquals(1005.453125, getSampleTiePointValue(product, 12), 1e-8);
        Assert.assertEquals(391.284912109375, getSampleTiePointValue(product, 13), 1e-8);
        Assert.assertEquals(79.07783508300781, getSampleTiePointValue(product, 14), 1e-8);
    }

    static void test_FR_2P_GeoCoding(Product product) {
        testGeoCoding(product, 7.276681900024414, 55.67790603637695, 100.00660705566406, 200.02471923828125);
    }

    static void test_FR_2P_MPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        final MetadataElement mph = metadataRoot.getElement(MPH);
        Assert.assertNotNull(mph);
        Assert.assertEquals(MER_FR_2P_CHILD_PRODUCT_NAME, getStringAttribute(mph, Constants.PRODUCT));
        Assert.assertEquals("N", getStringAttribute(mph, Constants.PROC_STAGE));
        Assert.assertEquals("PO-RS-MDA-GS-2009_11_4A", getStringAttribute(mph, Constants.REF_DOC));
        Assert.assertEquals("DLR-NSG             ", getStringAttribute(mph, Constants.ACQUISITION_STATION));
        Assert.assertEquals("D-PAC ", getStringAttribute(mph, Constants.PROC_CENTER));
        Assert.assertEquals("27-APR-2005 02:05:31.872751", getDateAttributeAsString(mph, Constants.PROC_TIME));
        Assert.assertEquals("MERIS/4.10    ", getStringAttribute(mph, Constants.SOFTWARE_VER));
        Assert.assertEquals("17-APR-2005 10:21:06.799678", getDateAttributeAsString(mph, Constants.SENSING_START));
        Assert.assertEquals("17-APR-2005 10:21:20.878718", getDateAttributeAsString(mph, "SENSING_STOP"));
        Assert.assertEquals("2", getStringAttribute(mph, "PHASE"));
        Assert.assertEquals(36, getIntAttribute(mph, "CYCLE"));
        Assert.assertEquals(280, getIntAttribute(mph, "REL_ORBIT"));
        Assert.assertEquals(16366, getIntAttribute(mph, "ABS_ORBIT"));
        Assert.assertEquals("17-APR-2005 10:16:00.000000", getDateAttributeAsString(mph, "STATE_VECTOR_TIME"));
        Assert.assertEquals(-0.583885, getDoubleAttribute(mph, "DELTA_UT1"), 1e-8);
        Assert.assertEquals(1881106.765, getDoubleAttribute(mph, "X_POSITION"), 1e-8);
        Assert.assertEquals(902565.485, getDoubleAttribute(mph, "Y_POSITION"), 1e-8);
        Assert.assertEquals(6843496.178, getDoubleAttribute(mph, "Z_POSITION"), 1e-8);
        Assert.assertEquals(7249.016224, getDoubleAttribute(mph, "X_VELOCITY"), 1e-8);
        Assert.assertEquals(-913.538139, getDoubleAttribute(mph, "Y_VELOCITY"), 1e-8);
        Assert.assertEquals(-1868.216698, getDoubleAttribute(mph, "Z_VELOCITY"), 1e-8);
        Assert.assertEquals("FR", getStringAttribute(mph, "VECTOR_SOURCE"));
        Assert.assertEquals("17-APR-2005 10:10:00.948054", getDateAttributeAsString(mph, "UTC_SBT_TIME"));
        Assert.assertEquals(3820351744L, getLongAttribute(mph, "SAT_BINARY_TIME"));
        Assert.assertEquals(3906249800L, getLongAttribute(mph, "CLOCK_STEP"));
        Assert.assertEquals("17-OCT-2001 00:00:00.000000", getDateAttributeAsString(mph, "LEAP_UTC"));
        Assert.assertEquals(1, getIntAttribute(mph, "LEAP_SIGN"));
        Assert.assertEquals(0, getIntAttribute(mph, "LEAP_ERR"));
        Assert.assertEquals(1, getIntAttribute(mph, "PRODUCT_ERR"));
        Assert.assertEquals(26724036L, getLongAttribute(mph, "TOT_SIZE"));
        Assert.assertEquals(11622, getIntAttribute(mph, "SPH_SIZE"));
        Assert.assertEquals(36, getIntAttribute(mph, "NUM_DSD"));
        Assert.assertEquals(280, getIntAttribute(mph, "DSD_SIZE"));
        Assert.assertEquals(23, getIntAttribute(mph, "NUM_DATA_SETS"));
    }

    static void test_FR_2P_SPH(Product product) {
        final MetadataElement metadataRoot = product.getMetadataRoot();
        MetadataElement sph = metadataRoot.getElement(SPH);
        Assert.assertNotNull(sph);
        Assert.assertEquals("MER_FR__2P SPECIFIC HEADER  ", getStringAttribute(sph, "SPH_DESCRIPTOR"));
        Assert.assertEquals(0, getIntAttribute(sph, "STRIPLINE_CONTINUITY_INDICATOR"));
        Assert.assertEquals(1, getIntAttribute(sph, "SLICE_POSITION"));
        Assert.assertEquals(1, getIntAttribute(sph, "NUM_SLICES"));
        Assert.assertEquals("17-APR-2005 10:21:06.799678", getDateAttributeAsString(sph, "FIRST_LINE_TIME"));
        Assert.assertEquals("17-APR-2005 10:21:20.878718", getDateAttributeAsString(sph, "LAST_LINE_TIME"));
        Assert.assertEquals(54400821, getIntAttribute(sph, "FIRST_FIRST_LAT"));
        Assert.assertEquals(15750206, getIntAttribute(sph, "FIRST_FIRST_LONG"));
        Assert.assertEquals(55428373, getIntAttribute(sph, "FIRST_MID_LAT"));
        Assert.assertEquals(11432620, getIntAttribute(sph, "FIRST_MID_LONG"));
        Assert.assertEquals(56251953, getIntAttribute(sph, "FIRST_LAST_LAT"));
        Assert.assertEquals(7160226, getIntAttribute(sph, "FIRST_LAST_LONG"));
        Assert.assertEquals(53620370, getIntAttribute(sph, "LAST_FIRST_LAT"));
        Assert.assertEquals(15160676, getIntAttribute(sph, "LAST_FIRST_LONG"));
        Assert.assertEquals(54630300, getIntAttribute(sph, "LAST_MID_LAT"));
        Assert.assertEquals(10913249, getIntAttribute(sph, "LAST_MID_LONG"));
        Assert.assertEquals(55440821, getIntAttribute(sph, "LAST_LAST_LAT"));
        Assert.assertEquals(6719157, getIntAttribute(sph, "LAST_LAST_LONG"));
        Assert.assertEquals(1, getIntAttribute(sph, "TRANS_ERR_FLAG"));
        Assert.assertEquals(0, getIntAttribute(sph, "FORMAT_ERR_FLAG"));
        Assert.assertEquals(0, getIntAttribute(sph, "DATABASE_FLAG"));
        Assert.assertEquals(1, getIntAttribute(sph, "COARSE_ERR_FLAG"));
        Assert.assertEquals(1, getIntAttribute(sph, "ECMWF_TYPE"));
        Assert.assertEquals(15, getIntAttribute(sph, "NUM_TRANS_ERR"));
        Assert.assertEquals(0, getIntAttribute(sph, "NUM_FORMAT_ERR"));
        Assert.assertEquals(0.0, getDoubleAttribute(sph, "TRANS_ERR_THRESH"), 1e-8);
        Assert.assertEquals(0.0, getDoubleAttribute(sph, "FORMAT_ERR_THRESH"), 1e-8);
        Assert.assertEquals(15, getIntAttribute(sph, "NUM_BANDS"));

        final ProductData waveLengths = sph.getAttribute("BAND_WAVELEN").getData();
        Assert.assertEquals(412545, waveLengths.getElemIntAt(0));
        Assert.assertEquals(442401, waveLengths.getElemIntAt(1));
        Assert.assertEquals(489744, waveLengths.getElemIntAt(2));
        Assert.assertEquals(509700, waveLengths.getElemIntAt(3));
        Assert.assertEquals(559634, waveLengths.getElemIntAt(4));
        Assert.assertEquals(619620, waveLengths.getElemIntAt(5));
        Assert.assertEquals(664640, waveLengths.getElemIntAt(6));
        Assert.assertEquals(680902, waveLengths.getElemIntAt(7));
        Assert.assertEquals(708426, waveLengths.getElemIntAt(8));
        Assert.assertEquals(753472, waveLengths.getElemIntAt(9));
        Assert.assertEquals(761606, waveLengths.getElemIntAt(10));
        Assert.assertEquals(778498, waveLengths.getElemIntAt(11));
        Assert.assertEquals(864833, waveLengths.getElemIntAt(12));
        Assert.assertEquals(884849, waveLengths.getElemIntAt(13));
        Assert.assertEquals(899860, waveLengths.getElemIntAt(14));

        final ProductData bandWidths = sph.getAttribute("BANDWIDTH").getData();
        Assert.assertEquals(9930, bandWidths.getElemIntAt(0));
        Assert.assertEquals(9946, bandWidths.getElemIntAt(1));
        Assert.assertEquals(9965, bandWidths.getElemIntAt(2));
        Assert.assertEquals(9971, bandWidths.getElemIntAt(3));
        Assert.assertEquals(9983, bandWidths.getElemIntAt(4));
        Assert.assertEquals(9991, bandWidths.getElemIntAt(5));
        Assert.assertEquals(9994, bandWidths.getElemIntAt(6));
        Assert.assertEquals(7493, bandWidths.getElemIntAt(7));
        Assert.assertEquals(9996, bandWidths.getElemIntAt(8));
        Assert.assertEquals(7493, bandWidths.getElemIntAt(9));
        Assert.assertEquals(3742, bandWidths.getElemIntAt(10));
        Assert.assertEquals(15000, bandWidths.getElemIntAt(11));
        Assert.assertEquals(19999, bandWidths.getElemIntAt(12));
        Assert.assertEquals(9990, bandWidths.getElemIntAt(13));
        Assert.assertEquals(9989, bandWidths.getElemIntAt(14));

        Assert.assertEquals(19159, getIntAttribute(sph, "INST_FOV"));
        Assert.assertEquals(1, getIntAttribute(sph, "PROC_MODE"));
        Assert.assertEquals(0, getIntAttribute(sph, "OFFSET_COMP"));
        Assert.assertEquals(43997, getIntAttribute(sph, "LINE_TIME_INTERVAL"));
        Assert.assertEquals(2241, getIntAttribute(sph, "LINE_LENGTH"));
        Assert.assertEquals(64, getIntAttribute(sph, "LINES_PER_TIE_PT"));
        Assert.assertEquals(64, getIntAttribute(sph, "SAMPLES_PER_TIE_PT"));
        Assert.assertEquals(260.0, getDoubleAttribute(sph, "COLUMN_SPACING"), 1e-8);
    }

    static void test_FR_2P_DSDs(Product childProduct) {
        final MetadataElement metadataRoot = childProduct.getMetadataRoot();
        final MetadataElement dsds = metadataRoot.getElement("DSD");
        Assert.assertEquals(36, dsds.getNumElements());
        assertDSD(dsds.getElementAt(0), "Quality ADS", "A", "", 12869, 32, 1, 32);
        assertDSD(dsds.getElementAt(1), "Scaling Factor GADS", "G", "", 12901, 440, 1, 440);
        assertDSD(dsds.getElementAt(2), "Tie points ADS", "A", "", 13341, 10878, 6, 1813);
        assertDSD(dsds.getElementAt(3), "Norm. rho_surf - MDS(1)", "M", "", 24219, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(4), "Norm. rho_surf - MDS(2)", "M", "", 1467114, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(5), "Norm. rho_surf - MDS(3)", "M", "", 2910009, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(6), "Norm. rho_surf - MDS(4)", "M", "", 4352904, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(7), "Norm. rho_surf - MDS(5)", "M", "", 5795799, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(8), "Norm. rho_surf - MDS(6)", "M", "", 7238694, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(9), "Norm. rho_surf - MDS(7)", "M", "", 8681589, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(10), "Norm. rho_surf - MDS(8)", "M", "", 10124484, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(11), "Norm. rho_surf - MDS(9)", "M", "", 11567379, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(12), "Norm. rho_surf - MDS(10)", "M", "", 13010274, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(13), "Norm. rho_surf - MDS(11)", "M", "", 14453169, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(14), "Norm. rho_surf - MDS(12)", "M", "", 15896064, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(15), "Norm. rho_surf - MDS(13)", "M", "", 17338959, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(16), "Vapour Content - MDS(14)", "M", "", 18781854, 723534, 321, 2254);
        assertDSD(dsds.getElementAt(17), "Chl_1, TOAVI   - MDS(15)", "M", "", 19505388, 723534, 321, 2254);
        assertDSD(dsds.getElementAt(18), "YS, SPM, Rect. Rho- MDS(16)", "M", "", 20228922, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(19), "Chl_2, BOAVI   - MDS(17)", "M", "", 21671817, 723534, 321, 2254);
        assertDSD(dsds.getElementAt(20), "Press PAR Alb  - MDS(18)", "M", "", 22395351, 723534, 321, 2254);
        assertDSD(dsds.getElementAt(21), "Epsilon, OPT   - MDS(19)", "M", "", 23118885, 1442895, 321, 4495);
        assertDSD(dsds.getElementAt(22), "Flags          - MDS(20)", "M", "", 24561780, 2162256, 321, 6736);
        assertDSD(dsds.getElementAt(23), "", "?", "", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(24), "LEVEL_1B_PRODUCT", "R", "MER_FR__1PNDPA20050417_101956_000000982036_00280_16366_6385.N1", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(25), "AEROSOL_CLIMATOLOGY_FILE", "R", "MER_AER_AXVIEC20030620_120000_20020321_193100_20200101_000000", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(26), "PROCESSING_PARAMS_L2_FILE", "R", "MER_CP2_AXVIEC20031120_104149_20021224_121445_20121224_121445", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(27), "ATMOSPHERIC_PARAMETERS_FILE", "R", "MER_ATP_AXVIEC20030620_120000_20021224_121445_20121224_121445", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(28), "WATER_VAPOUR_PARAMETERS_FILE", "R", "MER_WVP_AXVIEC20030620_120000_20020321_193100_20120321_193100", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(29), "OCEAN_AEROSOLS_PARAMS_FILE", "R", "MER_OAP_AXVIEC20030620_120001_20020321_193100_20120321_193100", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(30), "LAND_AEROSOLS_PARAMS_FILE", "R", "MER_LAP_AXVIEC20030715_151450_20020321_193100_20120321_193100", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(31), "OCEAN_I_PARAMETERS_FILE", "R", "MER_OC1_AXVIEC20030620_120000_20020321_193100_20120321_193100", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(32), "OCEAN_II_PARAMETERS_FILE", "R", "MER_OC2_AXVIEC20030620_120000_20020321_193100_20120624_174339", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(33), "CLOUD_MEASUREMENT_FILE", "R", "MER_CMP_AXVIEC20030620_120000_20021224_121445_20120321_193100", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(34), "LAND_VEGETATION_INDEX_FILE", "R", "MER_LVI_AXVIEC20030620_120000_20020321_193100_20130224_164916", 0, 0, 0, 0);
        assertDSD(dsds.getElementAt(35), "", "?", "", 0, 0, 0, 0);
    }

    static void test_FR_2P_SQADs(Product childProduct) {
        final MetadataElement metadataRoot = childProduct.getMetadataRoot();
        final MetadataElement sqads = metadataRoot.getElement("Quality_ADS");
        Assert.assertEquals(0, sqads.getNumElements());
        Assert.assertEquals("17-APR-2005 10:21:03.983870", sqads.getAttribute("dsr_time").getData().getElemString());
        Assert.assertEquals(0, getIntAttribute(sqads, "attach_flag"));
        Assert.assertEquals(46, getIntAttribute(sqads, "perc_water_abs_aero"));
        Assert.assertEquals(54, getIntAttribute(sqads, "perc_water"));
        Assert.assertEquals(0, getIntAttribute(sqads, "perc_ddv_land"));
        Assert.assertEquals(40, getIntAttribute(sqads, "perc_land"));
        Assert.assertEquals(4, getIntAttribute(sqads, "perc_cloud"));
        Assert.assertEquals(24, getIntAttribute(sqads, "perc_low_poly_press"));
        Assert.assertEquals(96, getIntAttribute(sqads, "perc_low_neural_press"));
        Assert.assertEquals(0, getIntAttribute(sqads, "perc_out_ran_inp_wvapour"));
        Assert.assertEquals(0, getIntAttribute(sqads, "per_out_ran_outp_wvapour"));
        Assert.assertEquals(0, getIntAttribute(sqads, "perc_out_range_inp_cl"));
        Assert.assertEquals(0, getIntAttribute(sqads, "perc_out_ran_outp_cl"));
        Assert.assertEquals(0, getIntAttribute(sqads, "perc_in_ran_inp_land"));
        Assert.assertEquals(0, getIntAttribute(sqads, "perc_out_ran_outp_land"));
        Assert.assertEquals(68, getIntAttribute(sqads, "perc_out_ran_inp_ocean"));
        Assert.assertEquals(0, getIntAttribute(sqads, "perc_out_ran_outp_ocean"));
        Assert.assertEquals(0, getIntAttribute(sqads, "perc_out_ran_inp_case1"));
        Assert.assertEquals(2, getIntAttribute(sqads, "perc_out_ran_outp_case1"));
        Assert.assertEquals(3, getIntAttribute(sqads, "perc_out_ran_inp_case2"));
        Assert.assertEquals(44, getIntAttribute(sqads, "perc_out_ran_outp_case2"));
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private static final String MER_RR__1P_CHILD_PRODUCT_NAME = "MER_RR__1PNTOM20060509_092353_000000452047_00308_21905_0345.N1";
    private static final String MER_FR_2P_CHILD_PRODUCT_NAME = "MER_FR__2PNTOM20050417_102106_000000142036_00280_16366_0976.N1";
*/
}
