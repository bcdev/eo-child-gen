package com.bc.childgen;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Mph {
    private static final int PRODUCTNAME_OFFSET = 9;
    private static final int PRODUCTNAME_LENGTH = 62;
    private static final int SOFTWARE_VER_OFFSET = 279;
    private static final int SOFTWARE_VER_LENGTH = 14;
    private static final int NUM_DSD_OFFSET = 1141;
    private static final int NUM_DSD_LENGTH = 10;
    private static final int DSD_SIZE_OFFSET = 1162;
    private static final int DSD_SIZE_LENGTH = 10;
    private static final int SPH_SIZE_OFFSET = 1114;
    private static final int SPH_SIZE_LENGTH = 10;
    private static final int TOT_SIZE_OFFSET = 1076;
    private static final int TOT_SIZE_LENGTH = 20;
    private static final int SENSING_START_OFFSET = 351;
    private static final int SENSING_STOP_OFFSET = 394;
    private static final int UTC_DATE_LENGTH = 27;

    private static final String HEADER_DATE_FORMAT = "dd-MMM-yyyy HH:mm:ss";

    private DecimalFormat sizeFormat;
    private DecimalFormat microSecsFormat;
    private SimpleDateFormat dateFormat;
    byte[] buffer;

    Mph() {
        buffer = new byte[ChildGenConstants.MPH_SIZE_IN_BYTES];
        sizeFormat = new DecimalFormat("00000000000000000000");
        microSecsFormat = new DecimalFormat("000000");

        final TimeZone utc = TimeZone.getTimeZone("UTC");
        dateFormat = new SimpleDateFormat(HEADER_DATE_FORMAT, Locale.ENGLISH);
        dateFormat.setTimeZone(utc);
    }

    public byte[] getRawData() {
        return buffer;
    }

    public String getProductFileName() {
        return new String(buffer, PRODUCTNAME_OFFSET, PRODUCTNAME_LENGTH);
    }

    public String getSoftwareVer() {
        return new String(buffer, SOFTWARE_VER_OFFSET, SOFTWARE_VER_LENGTH);
    }

    public void setProductFileName(String fileName) {
        if (fileName == null || fileName.length() != PRODUCTNAME_LENGTH) {
            throw new IllegalStateException("generated file name invalid:" + fileName);
        }
        System.arraycopy(fileName.getBytes(), 0, buffer, PRODUCTNAME_OFFSET, PRODUCTNAME_LENGTH);
    }

    public int getNumDsds() throws ChildGenException {
        final String numDsdsString = new String(buffer, NUM_DSD_OFFSET, NUM_DSD_LENGTH);

        int numDsds;
        try {
            numDsds = Integer.parseInt(numDsdsString);
        } catch (NumberFormatException e) {
            throw new ChildGenException("Illegal MPH field 'NUM_DSD': " + numDsdsString);
        }
        return numDsds;
    }

    public int getDsdSize() throws ChildGenException {
        final String dsdSizeString = new String(buffer, DSD_SIZE_OFFSET, DSD_SIZE_LENGTH);

        int dsdSize;
        try {
            dsdSize = Integer.parseInt(dsdSizeString);
        } catch (NumberFormatException e) {
            throw new ChildGenException("Illegal MPH field 'DSD_SIZE': " + dsdSizeString);
        }

        return dsdSize;
    }

    public int getSphSize() throws ChildGenException {
        final String sphSizeString = new String(buffer, SPH_SIZE_OFFSET, SPH_SIZE_LENGTH);

        int sphSize;
        try {
            sphSize = Integer.parseInt(sphSizeString);
        } catch (NumberFormatException e) {
            throw new ChildGenException("Illegal MPH field 'SPH_SIZE': " + sphSizeString);
        }
        return sphSize;
    }

    void setTotalSize(long size) {
        final String totalSizeString = sizeFormat.format(size);

        System.arraycopy(totalSizeString.getBytes(), 0, buffer, TOT_SIZE_OFFSET, TOT_SIZE_LENGTH);
    }

    void setSensingStart(Date sensingStart, int microSecs) {
        final String dateString = getDateString(sensingStart, microSecs);
        System.arraycopy(dateString.getBytes(), 0, buffer, SENSING_START_OFFSET, UTC_DATE_LENGTH);
    }

    void setSensingStop(Date sensingStop, int microSecs) {
        final String dateString = getDateString(sensingStop, microSecs);
        System.arraycopy(dateString.getBytes(), 0, buffer, SENSING_STOP_OFFSET, UTC_DATE_LENGTH);
    }

    private String getDateString(Date sensingStart, int microSecs) {
        final StringBuffer dateBuffer = new StringBuffer(32);
        dateBuffer.append(dateFormat.format(sensingStart));
        dateBuffer.append('.');
        dateBuffer.append(microSecsFormat.format(microSecs));

        return dateBuffer.toString().toUpperCase();
    }
}
