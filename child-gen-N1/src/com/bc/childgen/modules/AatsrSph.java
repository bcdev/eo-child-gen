package com.bc.childgen.modules;

import com.bc.childgen.ChildGenConstants;
import com.bc.childgen.DatasetDescriptor;
import com.bc.childgen.sqad.AatsrSqadUtils;

import javax.imageio.stream.ImageOutputStream;
import java.io.IOException;

class AatsrSph extends Sph {

    AatsrSph(int byteSize, int numDsds, int dsdSize) {
        super(byteSize, numDsds, dsdSize);
    }

    public int getLinesPerTiePoint() {
        return LINES_PER_TIE_POINT;
    }

    public void adjustDSDs(int startLine, int height, int tpHeight, MdsrLineMap lineMap) {
        long offset = 0;

        // detect the first non-spare and use offset as starting point
        for (int i = 0; i < dsds.length; i++) {
            if (!dsds[i].isSpare()) {
                offset = dsds[i].getDsOffset();
                break;
            }
        }
        
        for (int i = 0; i < dsds.length; i++) {
            final DatasetDescriptor currentDsd = dsds[i];

            if (currentDsd.getDsType() == 'A') {
                if (isSQAD(currentDsd)) {
                    int startIndex = AatsrSqadUtils.getSQADIndex(startLine);
                    int endIndex = AatsrSqadUtils.getSQADIndex(startLine + height);

                    final int numDsr = endIndex - startIndex + 1;
                    currentDsd.setNumDsr(numDsr);
                    currentDsd.setDsSize(numDsr * currentDsd.getDsrSize());
                } else {
                    currentDsd.setNumDsr(tpHeight);
                }
            } else if (currentDsd.getDsType() == 'M') {
                int validMdsrs = 0;
                for (int n = startLine; n < startLine + height; n++) {
                    if (lineMap.getMdsrIndex(n) >= 0) {
                        ++validMdsrs;
                    }
                }
                currentDsd.setNumDsr(validMdsrs);
            }

            // offset calculation depends on missing MDSRs due to attach flag raised
            currentDsd.setDsSize(currentDsd.getDsrSize() * currentDsd.getNumDsr());
            currentDsd.setDsOffset(offset);
            offset += currentDsd.getDsSize();
        }
    }

    public long calculateSourceOffset(int startLine, int tpStartLine, DatasetDescriptor dsd, MdsrLineMap lineMap) {
        if (dsd.getDsType() == 'A') {
            if (isSQAD(dsd)) {
                final int sqadIndex = AatsrSqadUtils.getSQADIndex(startLine);
                return dsd.getDsOffset() + sqadIndex * dsd.getDsrSize();
            }
            return dsd.getDsOffset() + tpStartLine * dsd.getDsrSize();
        } else if (dsd.getDsType() == 'M') {
            int mdsLines = 0;
            for (int i = 0; i < startLine; i++) {
                if (lineMap.getMdsrIndex(i) >= 0) {
                    ++mdsLines;
                }
            }
            return dsd.getDsOffset() + mdsLines * dsd.getDsrSize();
        }
        return dsd.getDsOffset();
    }

    // @todo 1 tb/tb write test
    public void patchDatasets(ImageOutputStream out, int startLine) throws IOException {
        for (int i = 0; i < dsds.length; i++) {
            final DatasetDescriptor descriptor = dsds[i];

            if (descriptor.isSpare()) {
                // @todo 3 tb/tb write test for this
                continue;
            }

            if (isSQAD(descriptor)) {
                for (int j = 0; j < descriptor.getNumDsr(); j++) {
                    long offset = descriptor.getDsOffset() + (j * descriptor.getDsrSize() + 16);
                    out.seek(offset);

                    int lineNumber = (startLine / 512) * 512 - startLine + j * 512;
                    if (lineNumber < 0) {
                        lineNumber = 0;
                    }

                    out.write(new byte[]{
                            (byte) (0xff & (lineNumber >> 8)),
                            (byte) (0xff & lineNumber)
                    });
                }

                break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PACKAGE
    ////////////////////////////////////////////////////////////////////////////////

    private static final int LINES_PER_TIE_POINT = 32;

    private boolean isSQAD(DatasetDescriptor dsd) {
        final String dsName = dsd.getDsName();
        if (dsName == null || dsName.length() == 0) {
            return false;
        }
        return ChildGenConstants.AATSR_QUALITY_ADS_NAME.indexOf(dsName) != -1;
    }

    // sqad dataset record
    // field name           offset  size
    // NADIR UTC            0       12
    // Attachment Flag      12      1
    // Spare                13      3
    // Image Scan Number    16      2
    // ... and more
}
