package com.bc.childgen.modules.meris;

import com.bc.childgen.ChildGenException;
import com.bc.childgen.DatasetDescriptor;
import com.bc.childgen.modules.MdsrLineMap;
import com.bc.childgen.modules.Sph;

import javax.imageio.stream.ImageOutputStream;
import java.io.IOException;

public class MerisSph extends Sph {

    public MerisSph(int byteSize, int numDsds, int dsdSize) {
        super(byteSize, numDsds, dsdSize);
    }

    public int getLinesPerTiePoint() throws ChildGenException {
        final String linesPerTiePointString = new String(getRawData(), Constants.MERIS_LINES_PER_TIE_OFFSET, 3);

        int numLines;
        try {
            numLines = Integer.parseInt(linesPerTiePointString);
        } catch (NumberFormatException e) {
            throw new ChildGenException("Illegal SPH field 'LINES_PER_TIE_PT':" + linesPerTiePointString);
        }

        return numLines;
    }

    // @todo 1 tb/tb TEST THIS!
    public void adjustDSDs(int startLine, int height, int tpHeight, MdsrLineMap lineMap) {
        final DatasetDescriptor[] dsds = getDsds();
        long offset = dsds[0].getDsOffset();
        for (int i = 0; i < dsds.length; i++) {
            final DatasetDescriptor currentDsd = dsds[i];

            if (currentDsd.getDsType() == 'A') {
                if (isSQAD(currentDsd)) {
                    handleSQADDsd(tpHeight, currentDsd);
                } else {
                    currentDsd.setNumDsr(tpHeight);
                }
            } else if (currentDsd.getDsType() == 'M') {
                currentDsd.setNumDsr(height);
            }

            currentDsd.setDsSize(currentDsd.getDsrSize() * currentDsd.getNumDsr());
            currentDsd.setDsOffset(offset);
            offset += currentDsd.getDsSize();
        }
    }

    public long calculateSourceOffset(int startLine, int tpStartLine, DatasetDescriptor dsd, MdsrLineMap lineMap) {
        if (dsd.getDsType() == 'A') {
            if (isSQAD(dsd)) {
                final int sqadIndex;
                try {
                    sqadIndex = getSQADIndex(startLine);
                    return dsd.getDsOffset() + sqadIndex * dsd.getDsrSize();
                } catch (ChildGenException e) {
                    // @todo 3 tb/tb handle
                }
            }
            return dsd.getDsOffset() + tpStartLine * dsd.getDsrSize();
        } else if (dsd.getDsType() == 'M') {
            return dsd.getDsOffset() + startLine * dsd.getDsrSize();
        }
        return dsd.getDsOffset();
    }

    public void patchDatasets(ImageOutputStream out, int startLine) throws IOException {
        // nothing to do here
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private boolean isSQAD(DatasetDescriptor dsd) {
        final String dsName = dsd.getDsName();
        if (dsName == null || dsName.length() == 0) {
            return false;
        }
        return Constants.QUALITY_ADS_NAME.indexOf(dsName) != -1;
    }

    private void handleSQADDsd(int tpHeight, DatasetDescriptor currentDsd) {
        final int supposedNumSqads = (int) Math.floor((tpHeight - 1) / 8.0 + 1);

        currentDsd.setNumDsr(supposedNumSqads);
        currentDsd.setDsSize(supposedNumSqads * currentDsd.getDsrSize());


    }

    private int getSQADIndex(int lineNumber) throws ChildGenException {
        final int sqadRaster = getLinesPerTiePoint() * 8;
        return lineNumber / sqadRaster;
    }
}
