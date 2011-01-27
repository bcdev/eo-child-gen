package com.bc.childgen.modules;

import com.bc.childgen.ChildGenException;
import com.bc.childgen.DatasetDescriptor;

import javax.imageio.stream.ImageOutputStream;
import java.io.IOException;
import java.util.Arrays;

abstract public class Sph {

    public Sph(int byteSize, int numDsds, int dsdSize) {
        buffer = new byte[byteSize];
        dsds = new DatasetDescriptor[numDsds];
        this.numDsds = numDsds;
        this.dsdSize = dsdSize;
    }

    public byte[] getRawData() {
        return buffer;
    }

    public void setRawData(byte[] buffer) {
        this.buffer = buffer;
    }

    public void parseDSDs() throws ChildGenException {
        int dsdPtr = buffer.length - (numDsds * dsdSize);

        for (int i = 0; i < dsds.length; i++) {
            final DatasetDescriptor current = dsds[i] = new DatasetDescriptor();

            // a spare DSD is completely empty by definition, i.e. has a blank at the first byte position
            //current.setSpare((char) buffer[dsdPtr] == ' ');
            current.setSpare(isInvalidDsd(buffer, dsdPtr));
            if (current.isSpare()) {
                //System.out.println(i + ": " + new String(buffer, dsdPtr, 280));
                dsdPtr += dsdSize;
                continue;
            }

            current.setDsName(new String(buffer, dsdPtr + DSD_DS_NAME_OFFSET, DSD_DS_NAME_LENGTH));
            current.setDsType((char) buffer[dsdPtr + DSD_DS_TYPE_OFFSET]);
            current.setFilename(new String(buffer, dsdPtr + DSD_FILE_NAME_OFFSET, DSD_FILE_NAME_LENGTH));
            current.setDsOffset(readIntWithouSign(dsdPtr + DSD_DS_OFFSET_OFFSET, DSD_DS_OFFSET_LENGTH));
            current.setDsSize(readIntWithouSign(dsdPtr + DSD_DS_SIZE_OFFSET, DSD_DS_SIZE_LENGTH));
            current.setNumDsr(readIntWithouSign(dsdPtr + DSD_NUM_DSR_OFFSET, DSD_NUM_DSR_LENGTH));
            current.setDsrSize(readIntWithouSign(dsdPtr + DSD_DSR_SIZE_OFFSET, DSD_DSR_SIZE_LENGTH));

            current.setSphBytePosition(dsdPtr);

            dsdPtr += dsdSize;
        }
    }

    public DatasetDescriptor[] getDsds() {
        return dsds;
    }

    public DatasetDescriptor getFirstMdsDsd() {
        for (int i = 0; i < dsds.length; i++) {
            DatasetDescriptor dsd = dsds[i];
            if (dsd.getDsType() == 'M') {
                return dsd;
            }
        }
        return null;
    }

    public void assignTo(Sph target) throws ChildGenException {
        byte[] newBuffer = new byte[buffer.length];
        System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);

        target.buffer = newBuffer;

        target.parseDSDs();
    }

    abstract public int getLinesPerTiePoint() throws ChildGenException;

    abstract public void adjustDSDs(int startLine, int height, int tpHeight, MdsrLineMap lineMap);

    abstract public long calculateSourceOffset(int startLine, int tpStartLine, DatasetDescriptor dsd, MdsrLineMap lineMap);

    abstract public void patchDatasets(ImageOutputStream out, int startLine) throws IOException;

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////    

    byte[] buffer;
    int numDsds;
    int dsdSize;
    DatasetDescriptor dsds[];

    // package access for testing purpose
    static boolean isInvalidDsd(byte[] buffer, int offset) {
        final byte[] dsName = Arrays.copyOfRange(buffer, offset, offset + 7);
        return !DSD_NAME_FIELD.equals(new String(dsName));
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PACKAGE
    ////////////////////////////////////////////////////////////////////////////////

    private static final int DSD_DS_NAME_OFFSET = 9;
    private static final int DSD_DS_NAME_LENGTH = 28;
    private static final int DSD_DS_TYPE_OFFSET = 47;
    private static final int DSD_FILE_NAME_OFFSET = 59;
    private static final int DSD_FILE_NAME_LENGTH = 62;
    private static final int DSD_DS_OFFSET_OFFSET = 133;
    private static final int DSD_DS_OFFSET_LENGTH = 21;
    private static final int DSD_DS_SIZE_OFFSET = 170;
    private static final int DSD_DS_SIZE_LENGTH = 21;
    private static final int DSD_NUM_DSR_OFFSET = 207;
    private static final int DSD_NUM_DSR_LENGTH = 11;
    private static final int DSD_DSR_SIZE_OFFSET = 228;
    private static final int DSD_DSR_SIZE_LENGTH = 11;
    private static final String DSD_NAME_FIELD = "DS_NAME";

    private int readIntWithouSign(int offset, int length) throws ChildGenException {
        final String s = new String(buffer, offset + 1, length - 1);

        int result;

        try {
            result = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new ChildGenException("Invalid number in DSD: " + s);
        }

        return result;
    }
}
