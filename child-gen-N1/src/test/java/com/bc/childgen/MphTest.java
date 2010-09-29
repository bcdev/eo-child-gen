package com.bc.childgen;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@SuppressWarnings({"MagicNumber"})
public class MphTest extends TestCase {

    public void testGetRawData() {
        byte[] rawData = mph.getRawData();
        assertEquals(ChildGenConstants.MPH_SIZE_IN_BYTES, rawData.length);
    }

    public void testGetProductFileName() {
        final byte[] buffer = new byte[ChildGenConstants.MPH_SIZE_IN_BYTES];
        final String testFileName = "ABCDEFGHIKPOIDYYYYMMDD_HHMMSS_00000000PCYC_RELOR_ABSOR_0000.N1";
        System.arraycopy(testFileName.getBytes(), 0, buffer, 9, 62);
        mph.buffer = buffer;

        assertEquals(testFileName, mph.getProductFileName());
    }

    public void testSetProductFileName() {
        final String testFileName = "ABCDEFGHIKPOIDYYYYMMDD_HHMMSS_00000000PCYC_RELOR_ABSOR_0000.N1";

        mph.setProductFileName(testFileName);

        final String actualName = new String(mph.buffer, 9, 62);
        assertEquals(testFileName, actualName);

        final String testFileName_tooShort = "ABCDEFGHIKPOIDYYYYMM_HHMMSS_00000000PCYC_RELOR_ABSOR_0000.N1";
        try {
            mph.setProductFileName(testFileName_tooShort);
            fail("IllegalStateException expected");
        } catch (IllegalStateException expected) {
        }

        final String testFileName_tooLong = "ABCDEFGHIKPOIDYYYYMMDD_HHMMSS_00000000PCYC_RELOR_ABSOR_0000.N123";
        try {
            mph.setProductFileName(testFileName_tooLong);
            fail("IllegalStateException expected");
        } catch (IllegalStateException expected) {
        }
    }

    public void testGetNumDsds() throws ChildGenException {
        final byte[] buffer = new byte[ChildGenConstants.MPH_SIZE_IN_BYTES];
        String testNumDsds = "+0000000234";
        System.arraycopy(testNumDsds.getBytes(), 0, buffer, 1140, 11);

        mph.buffer = buffer;
        assertEquals(234, mph.getNumDsds());

        testNumDsds = "+0000ouurtn";
        System.arraycopy(testNumDsds.getBytes(), 0, buffer, 1140, 11);

        try {
            mph.getNumDsds();
            fail("ChildGenException expected");
        } catch (ChildGenException expected) {
        }
    }

    public void testGetDsdSize() throws ChildGenException {
        final byte[] buffer = new byte[ChildGenConstants.MPH_SIZE_IN_BYTES];
        String testDsdSize = "+0000000564";
        System.arraycopy(testDsdSize.getBytes(), 0, buffer, 1161, 11);

        mph.buffer = buffer;
        assertEquals(564, mph.getDsdSize());

        testDsdSize = "+0000abc564";
        System.arraycopy(testDsdSize.getBytes(), 0, buffer, 1161, 11);

        try {
            mph.getDsdSize();
            fail("ChildGenException expected");
        } catch (ChildGenException expected) {
        }
    }

    public void testGetSphSize() throws ChildGenException {
        final byte[] buffer = new byte[ChildGenConstants.MPH_SIZE_IN_BYTES];
        String testSphSize = "+0000000789";
        System.arraycopy(testSphSize.getBytes(), 0, buffer, 1113, 11);

        mph.buffer = buffer;
        assertEquals(789, mph.getSphSize());

        testSphSize = "+0ab-000789";
        System.arraycopy(testSphSize.getBytes(), 0, buffer, 1113, 11);
        try {
            mph.getSphSize();
            fail("ChildGenException expected");
        } catch (ChildGenException expected) {
        }
    }

    public void testSetTotalSize() {
        final long expSize = 1234567890;

        mph.setTotalSize(expSize);

        final String totalSizeString = new String(mph.getRawData(), 1076, 20);
        assertEquals(expSize, Long.parseLong(totalSizeString));
    }

    public void testSetSensingStart() {
        final Date sensingStart = new Date(1000627211342L);

        mph.setSensingStart(sensingStart, 125688);

        final String sensingStartString = new String(mph.getRawData(), 351, 27);

        assertEquals(dateFormat.format(sensingStart).toUpperCase() + ".125688", sensingStartString);
    }

    public void testSetSensingStop() {
        final Date sensingStop = new Date(1100667211342L);

        mph.setSensingStop(sensingStop, 234);

        final String sensingStopString = new String(mph.getRawData(), 394, 27);

        assertEquals(dateFormat.format(sensingStop).toUpperCase() + ".000234", sensingStopString);
    }

    public void testGetSoftwareVer() {
        final byte[] buffer = new byte[ChildGenConstants.MPH_SIZE_IN_BYTES];
        String softwareVer = "STEP1/4       ";
        System.arraycopy(softwareVer.getBytes(), 0, buffer, 279, 14);

        mph.buffer = buffer;

        assertEquals(softwareVer, mph.getSoftwareVer());
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private static final String HEADER_DATE_FORMAT = "dd-MMM-yyyy HH:mm:ss";

    private Mph mph;
    private SimpleDateFormat dateFormat;

    protected void setUp() {
        mph = new Mph();
        final TimeZone utc = TimeZone.getTimeZone("UTC");
        dateFormat = new SimpleDateFormat(HEADER_DATE_FORMAT, Locale.ENGLISH);
        dateFormat.setTimeZone(utc);
    }
}
