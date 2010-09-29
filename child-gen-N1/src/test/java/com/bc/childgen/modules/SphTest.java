package com.bc.childgen.modules;

import com.bc.childgen.ChildGenException;
import com.bc.childgen.DatasetDescriptor;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.text.DecimalFormat;

@SuppressWarnings({"MagicNumber"})
public class SphTest extends TestCase {

    public void testParseDsds() throws ChildGenException {
        final int sphLength = 1024;
        final int numDsds = 3;
        final int dsdSize = 280;
        final Sph sph = createPreparedSph(sphLength, numDsds, dsdSize);

        sph.parseDSDs();

        final DatasetDescriptor[] dsds = sph.getDsds();
        assertEquals(DSD_ONE_NAME, dsds[0].getDsName());
        assertEquals(DSD_ONE_TYPE, dsds[0].getDsType());
        assertEquals(DSD_ONE_FILE_NAME, dsds[0].getFilename());
        assertEquals(DSD_ONE_OFFSET, dsds[0].getDsOffset());
        assertEquals(DSD_ONE_SIZE, dsds[0].getDsSize());
        assertEquals(DSD_ONE_NUM_DSR, dsds[0].getNumDsr());
        assertEquals(DSD_ONE_DSR_SIZE, dsds[0].getDsrSize());
        Assert.assertFalse(dsds[0].isSpare());

        Assert.assertTrue(dsds[1].isSpare());

        assertEquals(DSD_THREE_NAME, dsds[2].getDsName());
        assertEquals(DSD_THREE_TYPE, dsds[2].getDsType());
        assertEquals(DSD_THREE_FILE_NAME, dsds[2].getFilename());
        assertEquals(DSD_THREE_OFFSET, dsds[2].getDsOffset());
        assertEquals(DSD_THREE_SIZE, dsds[2].getDsSize());
        assertEquals(DSD_THREE_NUM_DSR, dsds[2].getNumDsr());
        assertEquals(DSD_THREE_DSR_SIZE, dsds[2].getDsrSize());
        Assert.assertFalse(dsds[2].isSpare());
    }

    public void testGetFirstMdsDsd() throws ChildGenException {
        final int sphLength = 1024;
        final int numDsds = 3;
        final int dsdSize = 280;
        final Sph sph = createPreparedSph(sphLength, numDsds, dsdSize);

        sph.parseDSDs();

        final DatasetDescriptor dsd = sph.getFirstMdsDsd();
        assertEquals(DSD_ONE_NAME, dsd.getDsName());
    }

    public void testAssignTo() throws ChildGenException {
        final int sphLength = 876;
        final int numDsds = 3;
        final int dsdSize = 280;
        final Sph source = createPreparedSph(sphLength, numDsds, dsdSize);
        source.parseDSDs();

        final Sph target = new TstSph(sphLength, numDsds, dsdSize);

        source.assignTo(target);

        final byte[] sourceData = source.getRawData();
        final byte[] targetData = target.getRawData();
        assertEquals(sourceData.length, targetData.length);
        for (int i = 0; i < sourceData.length; i++) {
            assertEquals(sourceData[i], targetData[i]);
        }

        final DatasetDescriptor[] sourceDsds = source.getDsds();
        final DatasetDescriptor[] targetDsds = target.getDsds();
        assertEquals(sourceDsds.length, targetDsds.length);
        for (int i = 0; i < sourceDsds.length; i++) {
            assertEquals(sourceDsds[i].getDsName(), targetDsds[i].getDsName());
            assertEquals(sourceDsds[i].getDsSize(), targetDsds[i].getDsSize());
        }
    }

    public void testIsInvalidDSD() {
        byte[] validBuffer = new byte[]{'D', 'S', '_', 'N', 'A', 'M', 'E'};
        assertFalse(Sph.isInvalidDsd(validBuffer, 0));

        validBuffer = new byte[]{'#', '#', '#', 'D', 'S', '_', 'N', 'A', 'M', 'E'};
        assertFalse(Sph.isInvalidDsd(validBuffer, 3));

        byte[] invalidBuffer = new byte[]{'D', 'S', '!', 'N', 'A', 'I', 'L'};
        assertTrue(Sph.isInvalidDsd(invalidBuffer, 0));

        invalidBuffer = new byte[]{'6', '2', 'q', '6', '7', '2', 'L', 'L', 'L', 'L'};
        assertTrue(Sph.isInvalidDsd(invalidBuffer, 0));

        invalidBuffer = new byte[]{'6', '2', 'q', '6', '7', '2', 'L', 'L', 'L', 'L'};
        assertTrue(Sph.isInvalidDsd(invalidBuffer, 2));

        invalidBuffer = new byte[]{'6', '2'};
        assertTrue(Sph.isInvalidDsd(invalidBuffer, 0));
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private static final String DSD_ONE_NAME = "Dsd one name                ";
    private static final char DSD_ONE_TYPE = 'M';
    private static final String DSD_ONE_FILE_NAME = "Dsd one FileName                                              ";
    private static final String DSD_THREE_NAME = "Dsd three name              ";
    private static final char DSD_THREE_TYPE = 'G';
    private static final String DSD_THREE_FILE_NAME = "Dsd three FileName                                            ";
    private static final int DSD_ONE_OFFSET = 12345;
    private static final int DSD_THREE_OFFSET = 52345;
    private static final int DSD_ONE_SIZE = 22345;
    private static final int DSD_THREE_SIZE = 62345;
    private static final int DSD_ONE_NUM_DSR = 32345;
    private static final int DSD_THREE_NUM_DSR = 72345;
    private static final int DSD_ONE_DSR_SIZE = 42345;
    private static final int DSD_THREE_DSR_SIZE = 82345;

    private StringBuffer createSph(int dsdStartPos, int dsdSize) {
        final StringBuffer buffer = createBlankBuffer(dsdStartPos);

        appendDsdToBuffer(buffer, DSD_ONE_NAME, DSD_ONE_TYPE, DSD_ONE_FILE_NAME, DSD_ONE_OFFSET, DSD_ONE_SIZE, DSD_ONE_NUM_DSR, DSD_ONE_DSR_SIZE);

        appendSpareDsdToBuffer(dsdSize, buffer);

        appendDsdToBuffer(buffer, DSD_THREE_NAME, DSD_THREE_TYPE, DSD_THREE_FILE_NAME, DSD_THREE_OFFSET, DSD_THREE_SIZE, DSD_THREE_NUM_DSR, DSD_THREE_DSR_SIZE);

        return buffer;
    }

    static void appendSpareDsdToBuffer(int dsdSize, StringBuffer buffer) {
        for (int n = 0; n < dsdSize; n++) {
            buffer.append(" ");
        }
    }

    static void appendDsdToBuffer(StringBuffer buffer, String name, char type, String fileName,
                                  long offset, long size, int numDsr, int dsrSize) {
        buffer.append("DS_NAME=\"");
        buffer.append(name);
        buffer.append("\"\n");
        buffer.append("DS_TYPE=");
        buffer.append(type);
        buffer.append("\n");
        buffer.append("FILENAME=\"");
        buffer.append(fileName);
        buffer.append("\"\n");
        buffer.append("DS_OFFSET=+");
        final DecimalFormat longFormat = new DecimalFormat("00000000000000000000");
        buffer.append(longFormat.format(offset));
        buffer.append("<bytes>");
        buffer.append("\n");
        buffer.append("DS_SIZE=+");
        buffer.append(longFormat.format(size));
        buffer.append("<bytes>");
        buffer.append("\n");
        buffer.append("NUM_DSR=+");
        final DecimalFormat shortFormat = new DecimalFormat("0000000000");
        buffer.append(shortFormat.format(numDsr));
        buffer.append("\n");
        buffer.append("DSR_SIZE=+");
        buffer.append(shortFormat.format(dsrSize));
        buffer.append("<bytes>");
        buffer.append("\n");
        buffer.append("                                ");
        buffer.append("\n");
    }

    static StringBuffer createBlankBuffer(int sphLength) {
        final StringBuffer buffer = new StringBuffer(sphLength);
        for (int n = 0; n < sphLength; n++) {
            buffer.append(" ");
        }
        return buffer;
    }

    private Sph createPreparedSph(int sphLength, int numDsds, int dsdSize) {
        final int dsdStartPos = sphLength - numDsds * dsdSize;

        final StringBuffer buffer = createSph(dsdStartPos, dsdSize);

        final Sph sph = new TstSph(sphLength, numDsds, dsdSize);
        sph.buffer = buffer.toString().getBytes();
        return sph;
    }
}
