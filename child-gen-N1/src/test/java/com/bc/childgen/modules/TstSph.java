package com.bc.childgen.modules;

import com.bc.childgen.ChildGenException;
import com.bc.childgen.DatasetDescriptor;

import javax.imageio.stream.ImageOutputStream;

public class TstSph extends Sph {

    public TstSph() {
        super(12, 1, 1);
    }

    public TstSph(int numDsds) {
        super(12, numDsds, 1);
    }

    public TstSph(int byteSize, int numDsds, int dsdSize) {
        super(byteSize, numDsds, dsdSize);
    }

    public int getLinesPerTiePoint() throws ChildGenException {
        return linesPerTiePoint;
    }

    public void setLinesPerTiePoint(int linesPerTiePoint) {
        this.linesPerTiePoint = linesPerTiePoint;
    }

    public void adjustDSDs(int startLine, int height, int tpHeight, MdsrLineMap lineMap) {
    }

    public long calculateSourceOffset(int startLine, int tpHeight, DatasetDescriptor dsd, MdsrLineMap lineMap) {
        return 0;
    }

    public void patchDatasets(ImageOutputStream out, int startLine) {
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private int linesPerTiePoint;
}
