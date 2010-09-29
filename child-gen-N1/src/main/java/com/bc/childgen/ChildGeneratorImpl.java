/*
 * Created on 10.03.2004
 */
package com.bc.childgen;

import com.bc.childgen.modules.MdsrLineMap;
import com.bc.childgen.modules.Module;
import com.bc.childgen.modules.ModuleFactory;
import com.bc.childgen.modules.Roi;
import com.bc.childgen.modules.Sph;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A child generation tool for Envisat N1 files.
 */
public final class ChildGeneratorImpl {

    private static final int MPH_PRODUCTNAME_LENGTH = 62;

    private static final int SPH_LAST_LINE_TIME_OFFSET = 180;
    private static final int SPH_FIRST_LINE_TIME_OFFSET = 135;

    private static final int DSD_DS_OFFSET_LENGTH = 21;
    private static final int DSD_DS_OFFSET_OFFSET = 133;
    private static final int DSD_DS_SIZE_LENGTH = 21;
    private static final int DSD_DS_SIZE_OFFSET = 170;
    private static final int DSD_NUM_DSR_LENGTH = 11;
    private static final int DSD_NUM_DSR_OFFSET = 207;

    private static final int FIRST_FIRST_LAT_OFFSET = 225;
    private static final int FIRST_FIRST_LON_OFFSET = 264;
    private static final int FIRST_MID_LAT_OFFSET = 300;
    private static final int FIRST_MID_LON_OFFSET = 337;
    private static final int FIRST_LAST_ALT_OFFSET = 374;
    private static final int FIRST_LAST_LON_OFFSET = 412;
    private static final int LAST_FIRST_LAT_OFFSET = 449;
    private static final int LAST_FIRST_LON_OFFSET = 487;
    private static final int LAST_MID_LAT_OFFSET = 522;
    private static final int LAST_MID_LON_OFFSET = 558;
    private static final int LAST_LAST_LAT_OFFSET = 594;
    private static final int LAST_LAST_LON_OFFSET = 631;
    private static final int ROI_FIELD_LENGTH = 11;

    private static final String FILENAME_DATE_FORMAT = "yyyyMMdd_HHmmss";
    private static final String HEADER_DATE_FORMAT = "dd-MMM-yyyy HH:mm:ss";

    private static final int FILE_COUNTER_MAX = 9999;

    private Config config;

    private File outputFile;

    private int lastLastLon;
    private int firstLastLon;
    private int lastMidLon;
    private int firstMidLon;
    private int lastFirstLon;
    private int firstFirstLon;
    private int lastLastLat;
    private int firstLastLat;
    private int lastMidLat;
    private int firstMidLat;
    private int lastFirstLat;
    private int firstFirstLat;

    private int stopMics;
    private int stopSecs;
    private int stopDays;
    private int startMics;
    private int startSecs;
    private int startDays;

    private Mph mph;
    private Sph sourceSph;
    private Sph targetSph;
    private Roi roi;


    /**
     * Performs the child generation processing.
     *
     * @param inputFile    the input N1 product file
     * @param outputDir    the target directory for the child product
     * @param originatorID DPA in MER_FR__1PN<i>DPA</i>20030812_102139_000000982019_00008_07577_0094.N1 Must be THREE
     *                     characters
     * @param fileCounter  the last four numbers in MER_FR__1PNDPA20030812_102139_000000982019_00008_07577_<i>0094</i>.N1
     * @throws java.io.IOException If an I/O error occurs
     * @deprecated Since version 1.6
     */
    @Deprecated
    public synchronized void process(File inputFile,
                                     File outputDir,
                                     String originatorID,
                                     int fileCounter,
                                     Rectangle region) throws IOException {
        process(inputFile, outputDir, originatorID, fileCounter,
                (int) region.getMinY(), (int) region.getMaxY());
    }

    /**
     * Performs the child generation processing.
     *
     * @param inputFile    the input N1 product file
     * @param outputDir    the target directory for the child product
     * @param originatorID DPA in MER_FR__1PN<i>DPA</i>20030812_102139_000000982019_00008_07577_0094.N1 Must be THREE
     *                     characters
     * @param fileCounter  the last four numbers in MER_FR__1PNDPA20030812_102139_000000982019_00008_07577_<i>0094</i>.N1
     * @param firstLine    The first scan line
     * @param lastLine     The last scan line
     * @throws java.io.IOException If an I/O error occurs
     */
    public void process(File inputFile,
                        File outputDir,
                        String originatorID,
                        int fileCounter,
                        int firstLine,
                        int lastLine) throws IOException {

        checkArguments(outputDir, originatorID, fileCounter);

        roi.setFirstLine(firstLine);
        roi.setLastLine(lastLine);

        ImageInputStream in = new FileImageInputStream(inputFile);
        try {
            read(in);
            outputFile = new File(outputDir, createProductName(originatorID, fileCounter));
            ImageOutputStream out = new FileImageOutputStream(outputFile);
            try {
                write(in, out, outputFile.getName());
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    /**
     * Performs the child generation processing.
     *
     * @param in          The N1 input stream
     * @param out         The N1 output stream
     * @param productName The N1 product name
     * @param firstLine   The first scan line
     * @param lastLine    The last scan line
     * @throws java.io.IOException If an I/O error occurs
     */
    public void process(ImageInputStream in,
                        ImageOutputStream out,
                        String productName,
                        int firstLine,
                        int lastLine) throws IOException {
        // todo - check that this.config is set and valid. (nf, 29.09.2010)
        roi.setFirstLine(firstLine);
        roi.setLastLine(lastLine);
        read(in);
        write(in, out, productName);
    }

    private void read(ImageInputStream in) throws IOException {
        try {
            readMPH(in);
            parseSPH(in);
            adjustROI();
            parseDSs(in);
        } catch (ChildGenException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    private void write(ImageInputStream in, ImageOutputStream out, String productName) throws IOException {
        try {
            MdsrLineMap lineMap = ModuleFactory.getModule(mph).createLineMap(sourceSph.getDsds(), in);
            copyHeader(out);
            copyDataSets(in, out, lineMap);
            patchMPH(out, productName);
            patchSPH(out);
            targetSph.patchDatasets(out, roi.getFirstLine());
        } catch (ChildGenException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * Sets the product-type specific configuration.
     *
     * @param config The product-type specific configuration.
     */
    public void setConfig(Config config) {
        this.config = config;
    }

    /**
     * Retrieves the target product written by the last call to process().
     *
     * @return The target product's file path. May be {@code null}, if unknown.
     */
    public File getTargetProduct() {
        return outputFile;
    }

    /**
     * Tells whether the child generator can perform band sub-setting or not
     *
     * @return {@code true} if so.
     */
    public boolean canDoBandSubset() {
        return false;
    }

    /**
     * Tells whether the child generator can only cut across the whole product or subset also the lines.
     *
     * @return {@code true} if so.
     */
    public boolean canDoLineSubset() {
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private void parseDSs(ImageInputStream in) throws IOException {
        final DatasetDescriptor[] dsds = sourceSph.getDsds();
        for (final DatasetDescriptor descriptor : dsds) {
            if (descriptor.isSpare()) {
                continue;
            }

            if (descriptor.getDsName().equals(config.getTiePointAdsName())) {
                if (descriptor.getNumDsr() < roi.getLastTiePointLine() + 1) {
                    throw new IllegalArgumentException("Invalid last line.");
                }

                final long firstOffset = descriptor.getDsOffset() + roi.getFirstTiePointLine() * descriptor.getDsrSize();
                final long lastOffset = descriptor.getDsOffset() + roi.getLastTiePointLine() * descriptor.getDsrSize();

                final byte[] firstLine = new byte[descriptor.getDsrSize()];
                final byte[] lastLine = new byte[descriptor.getDsrSize()];

                in.seek(firstOffset);
                in.read(firstLine);

                in.seek(lastOffset);
                in.read(lastLine);

                getSensingStartFromBuffer(firstLine);
                getSensingStopFromBuffer(lastLine);

                setRoiFields(firstLine, lastLine);

                break;
            }
        }
    }

    private String createProductName(String originatorID, int fileCounter) {
        final StringBuffer sb = new StringBuffer(mph.getProductFileName());
        final TimeZone utc = TimeZone.getTimeZone("UTC");
        DecimalFormat decFormat = new DecimalFormat("00000000");

        final SimpleDateFormat dateFormat = new SimpleDateFormat(FILENAME_DATE_FORMAT, Locale.ENGLISH);
        dateFormat.setTimeZone(utc);

        final Calendar calendarStart = getUtcCalendar(startDays, startSecs, startMics);
        final String startTime = dateFormat.format(calendarStart.getTime());

        final Calendar calendarStop = getUtcCalendar(stopDays, stopSecs, stopMics);
        long durationInMillisecs = calendarStop.getTimeInMillis() - calendarStart.getTimeInMillis();
        int durationInSeconds = (int) (Math.floor(durationInMillisecs * 1e-3 + 0.5));

        sb.replace(11, 14, originatorID);
        sb.replace(14, 29, startTime);
        sb.replace(30, 38, decFormat.format(durationInSeconds));

        decFormat = new DecimalFormat("0000");
        sb.replace(55, 59, decFormat.format(fileCounter));

        String productName = sb.toString();
        if (productName.length() > MPH_PRODUCTNAME_LENGTH) {
            throw new IllegalStateException("Product name too long: " + productName);
        }
        return productName;
    }

    private void patchSPH(ImageOutputStream out) throws IOException {
        patchSphSensingTime(out);
        patchRoiFields(out);

        final DatasetDescriptor[] dsds = targetSph.getDsds();
        for (DatasetDescriptor dsd : dsds) {
            patchDSD(dsd, out);
        }
    }

    private void patchDSD(DatasetDescriptor descriptor, ImageOutputStream out) throws IOException {
        if (descriptor.isSpare()) {
            return;
        }

        if (descriptor.getDsType() == 'R') {
            return;
        }

        out.seek(descriptor.getSphBytePosition() + DSD_DS_OFFSET_OFFSET + ChildGenConstants.MPH_SIZE_IN_BYTES);
        oWrite(descriptor.getDsOffset(), DSD_DS_OFFSET_LENGTH, out);
        out.seek(descriptor.getSphBytePosition() + DSD_DS_SIZE_OFFSET + ChildGenConstants.MPH_SIZE_IN_BYTES);
        oWrite(descriptor.getDsSize(), DSD_DS_SIZE_LENGTH, out);
        out.seek(descriptor.getSphBytePosition() + DSD_NUM_DSR_OFFSET + ChildGenConstants.MPH_SIZE_IN_BYTES);
        oWrite(descriptor.getNumDsr(), DSD_NUM_DSR_LENGTH, out);
    }

    private void patchMPH(ImageOutputStream out, String productName) throws IOException {
        if (productName.length() != MPH_PRODUCTNAME_LENGTH) {
            productName = String.format("%-" + MPH_PRODUCTNAME_LENGTH + "s", productName);
        }

        mph.setProductFileName(productName);
        mph.setTotalSize(out.length());

        final Calendar startCalendar = getUtcCalendar(startDays, startSecs, 0);
        mph.setSensingStart(startCalendar.getTime(), startMics);

        final Calendar stopCalendar = getUtcCalendar(stopDays, stopSecs, 0);
        mph.setSensingStop(stopCalendar.getTime(), stopMics);

        out.seek(0);
        out.write(mph.getRawData());
    }

    private void setRoiFields(final byte[] firstLine, final byte[] lastLine) {
        final int recordSize = 4;
        final int tiePointLatOffset = config.getTiePointLatOffset();
        final int tiePointLonOffset = config.getTiePointLonOffset();
        final int numberOfGeoCoordinates = config.getNumberOfGeoCoordinates();

        int mLatOffset = tiePointLatOffset + recordSize * (numberOfGeoCoordinates / 2);
        int lLatOffset = tiePointLatOffset + recordSize * (numberOfGeoCoordinates - 1);

        int mLonOffset = tiePointLonOffset + recordSize * (numberOfGeoCoordinates / 2);
        int lLonOffset = tiePointLonOffset + recordSize * (numberOfGeoCoordinates - 1);

        firstFirstLat = getInt32(firstLine, tiePointLatOffset);
        lastFirstLat = getInt32(lastLine, tiePointLatOffset);
        firstMidLat = getInt32(firstLine, mLatOffset);
        lastMidLat = getInt32(lastLine, mLatOffset);
        firstLastLat = getInt32(firstLine, lLatOffset);
        lastLastLat = getInt32(lastLine, lLatOffset);

        firstFirstLon = getInt32(firstLine, tiePointLonOffset);
        lastFirstLon = getInt32(lastLine, tiePointLonOffset);
        firstMidLon = getInt32(firstLine, mLonOffset);
        lastMidLon = getInt32(lastLine, mLonOffset);
        firstLastLon = getInt32(firstLine, lLonOffset);
        lastLastLon = getInt32(lastLine, lLonOffset);
    }

    private void patchRoiFields(ImageOutputStream out) throws IOException {
        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES + FIRST_FIRST_LAT_OFFSET);
        oWrite(firstFirstLat, ROI_FIELD_LENGTH, out);

        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES + FIRST_FIRST_LON_OFFSET);
        oWrite(firstFirstLon, ROI_FIELD_LENGTH, out);

        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES + FIRST_MID_LAT_OFFSET);
        oWrite(firstMidLat, ROI_FIELD_LENGTH, out);

        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES + FIRST_MID_LON_OFFSET);
        oWrite(firstMidLon, ROI_FIELD_LENGTH, out);

        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES + FIRST_LAST_ALT_OFFSET);
        oWrite(firstLastLat, ROI_FIELD_LENGTH, out);

        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES + FIRST_LAST_LON_OFFSET);
        oWrite(firstLastLon, ROI_FIELD_LENGTH, out);

        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES + LAST_FIRST_LAT_OFFSET);
        oWrite(lastFirstLat, ROI_FIELD_LENGTH, out);

        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES + LAST_FIRST_LON_OFFSET);
        oWrite(lastFirstLon, ROI_FIELD_LENGTH, out);

        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES + LAST_MID_LAT_OFFSET);
        oWrite(lastMidLat, ROI_FIELD_LENGTH, out);

        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES + LAST_MID_LON_OFFSET);
        oWrite(lastMidLon, ROI_FIELD_LENGTH, out);

        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES + LAST_LAST_LAT_OFFSET);
        oWrite(lastLastLat, ROI_FIELD_LENGTH, out);

        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES + LAST_LAST_LON_OFFSET);
        oWrite(lastLastLon, ROI_FIELD_LENGTH, out);
    }

    private int getInt32(byte[] buf, int offset) {
        //noinspection PointlessArithmeticExpression
        return (
                ((buf[offset + 0] & 0xff) << 24)
                        | ((buf[offset + 1] & 0xff) << 16)
                        | ((buf[offset + 2] & 0xff) << 8)
                        | (buf[offset + 3] & 0xff));
    }

    private void patchSphSensingTime(ImageOutputStream out) throws IOException {
        final DecimalFormat decFormat = new DecimalFormat("000000");
        final SimpleDateFormat dateFormat = createUTCDateFormat();

        final Calendar startCalendar = getUtcCalendar(startDays, startSecs, 0);
        byte[] startTime = (dateFormat.format(startCalendar.getTime()).toUpperCase()
                + "."
                + decFormat.format(startMics))
                .getBytes();

        final Calendar stopCalendar = getUtcCalendar(stopDays, stopSecs, 0);
        byte[] stopTime = (dateFormat.format(stopCalendar.getTime()).toUpperCase()
                + "."
                + decFormat.format(stopMics))
                .getBytes();

//        /*
//         * Patchen der Felder SENSING_START, SENSING_STOP
//         */
//        oSeek(MPH_SENSING_START_OFFSET);
//        oWrite(startTime);
//        oSeek(MPH_SENSING_STOP_OFFSET);
//        oWrite(stopTime);

        /*
         * Patchen der Felder FIRST_LINE_TIME, LAST_LINE_TIME
         */
        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES + SPH_FIRST_LINE_TIME_OFFSET);
        out.write(startTime);
        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES + SPH_LAST_LINE_TIME_OFFSET);
        out.write(stopTime);
    }


    private void getSensingStartFromBuffer(final byte[] buf) {
        startDays = getInt32(buf, 0);
        startSecs = getInt32(buf, 4);
        startMics = getInt32(buf, 8);
    }

    private void getSensingStopFromBuffer(final byte[] buf) {
        stopDays = getInt32(buf, 0);
        stopSecs = getInt32(buf, 4);
        stopMics = getInt32(buf, 8);
    }

    private void copyHeader(ImageOutputStream out) throws IOException {
        out.seek(0);
        out.write(mph.getRawData());
        out.seek(ChildGenConstants.MPH_SIZE_IN_BYTES);
        out.write(targetSph.getRawData());
    }

    private void copyDataSets(ImageInputStream in, ImageOutputStream out, MdsrLineMap lineMap) throws IOException {
        final DatasetDescriptor[] sourceDsds = sourceSph.getDsds();
        final DatasetDescriptor[] targetDsds = targetSph.getDsds();

        final int firstLine = roi.getFirstLine();
        final int firstTiePointLine = roi.getFirstTiePointLine();
        targetSph.adjustDSDs(firstLine, roi.getLastLine() - firstLine + 1, roi.getLastTiePointLine() - firstTiePointLine + 1, lineMap);

        for (int i = 0; i < sourceDsds.length; i++) {
            final long sourceOffset = sourceSph.calculateSourceOffset(firstLine, firstTiePointLine, sourceDsds[i], lineMap);
            final DatasetDescriptor targetDsd = targetDsds[i];
            if (targetDsd.getDsType() != 'M') {
                // these can be copied normally
                copyDS(sourceOffset, targetDsd.getDsOffset(), targetDsd.getDsSize(), in, out);
            } else {
                // here, we have to take the attach_flag into account
                copyDS(sourceOffset, targetDsd.getDsOffset(), targetDsd.getDsSize(), in, out);
            }
        }
    }

    private void copyDS(long sourceOffset, long targetOffset, long size, ImageInputStream in, ImageOutputStream out) throws IOException {
        final byte[] buf = new byte[(int) size];

        in.seek(sourceOffset);
        in.read(buf);
        out.seek(targetOffset);
        out.write(buf);
    }

    private void parseSPH(ImageInputStream in) throws IOException, ChildGenException {
        sourceSph = ModuleFactory.getSph(mph);

        in.seek(ChildGenConstants.MPH_SIZE_IN_BYTES);
        in.read(sourceSph.getRawData());

        sourceSph.parseDSDs();

        targetSph = ModuleFactory.getSph(mph);
        sourceSph.assignTo(targetSph);
    }

    /*
     * Ermitteln der korrigierten Start/Stop-Zeilen in den Datasets.
     * Diese werden so gewaehlt, dass sie genau auf die Tie Point Grids
     * passen.
     */

    private void adjustROI() throws ChildGenException {
        final Module module = ModuleFactory.getModule(mph);
        module.adjustRoi(roi, sourceSph);
    }

    private void readMPH(ImageInputStream in) throws IOException {
        in.seek(0);
        in.read(mph.getRawData());
    }

    private void oWrite(long value, int length, ImageOutputStream out) throws IOException {
        length--;

        StringBuffer formatPattern = new StringBuffer();

        for (int i = 0; i < length; i++) {
            formatPattern.append('0');
        }

        DecimalFormat format = new DecimalFormat(formatPattern.toString());
        String valueAsStr = ((value < 0) ? "" : "+") + format.format(value);

        out.write(valueAsStr.getBytes());
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    ChildGeneratorImpl() {
        mph = new Mph();
        roi = new Roi();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PACKAGE
    ////////////////////////////////////////////////////////////////////////////////

    private void checkArguments(File outputDir, String originatorID, int fileCounter) {
        if (!roi.isValid()) {
            throw new IllegalArgumentException("Illegal first- or last-line specified");
        }

        if (!outputDir.isDirectory()) {
            throw new IllegalArgumentException("Illegal directory: " + outputDir.getPath());
        }

        if (originatorID == null || originatorID.length() != 3) {
            throw new IllegalArgumentException("Illegal originator ID: " + originatorID);
        }

        if (fileCounter < 0 || fileCounter > FILE_COUNTER_MAX) {
            throw new IllegalArgumentException("Invalid file counter: " + fileCounter);
        }
    }

    private Calendar createBaseUTCCalendar() {
        final TimeZone utc = TimeZone.getTimeZone("UTC");
        final Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeZone(utc);
        calendar.clear();
        calendar.set(2000, 0, 1);
        return calendar;
    }

    private Calendar getUtcCalendar(int days, int seconds, int microSeconds) {
        final Calendar calendar = createBaseUTCCalendar();
        calendar.add(Calendar.DATE, days);
        calendar.add(Calendar.SECOND, seconds);
        if (microSeconds != 0) {
            calendar.add(Calendar.MILLISECOND, (int) (0.5 + 1e-3 * microSeconds));
        }
        return calendar;
    }

    private SimpleDateFormat createUTCDateFormat() {
        final TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat dateFormat = new SimpleDateFormat(HEADER_DATE_FORMAT, Locale.ENGLISH);
        dateFormat.setTimeZone(utc);
        return dateFormat;
    }

    public void fragment(ImageInputStream in, String originatorID, int numLines, FragmentHandler fragmentHandler) throws IOException {
        int firstLine = 0;
        for (int index = 0; ; index++) {
            try {
                int lastLine = firstLine + numLines - 1;
                roi.setFirstLine(firstLine);
                roi.setLastLine(lastLine);
                read(in); // Note: adjusts firstLine / lastLine
                String productName = createProductName(originatorID, index + 1);
                ImageOutputStream out = fragmentHandler.beginFragment(index, productName, roi.getFirstLine(), roi.getLastLine());
                try {
                    write(in, out, productName);
                    firstLine = roi.getLastLine();
                    fragmentHandler.endFragment(index, out);
                    if (lastLine > roi.getLastLine()) {
                        break;
                    }
                } finally {
                    out.close();
                }
            } catch (IOException e) {
                fragmentHandler.handleError(index, e);
                throw e;
            }
        }
    }

    public interface FragmentHandler {
        ImageOutputStream beginFragment(int fragmentIndex, String productName, int firstLine, int lastLine)throws IOException ;

        void endFragment(int fragmentIndex, ImageOutputStream out)throws IOException ;

        void handleError(int fragmentIndex, IOException e);
    }
}
