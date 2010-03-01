package com.bc.childgen.modules;

import com.bc.childgen.ChildGenException;
import com.bc.childgen.DatasetDescriptor;

import javax.imageio.stream.ImageInputStream;


class MerisModule implements Module {

    public Roi adjustRoi(Roi roi, Sph sph) throws ChildGenException {
        final int linesPerTiePoint = sph.getLinesPerTiePoint();
        int firstLine = roi.getFirstLine();

        if (firstLine % linesPerTiePoint != 0) {
            firstLine -= firstLine % linesPerTiePoint;
            roi.setFirstLine(firstLine);
        }

        int lastLine = roi.getLastLine();
        if (lastLine % linesPerTiePoint != 0) {
            lastLine += linesPerTiePoint - lastLine % linesPerTiePoint;
            roi.setLastLine(lastLine);
        }

        DatasetDescriptor firstMDSDsd = null;
        final DatasetDescriptor[] dsds = sph.getDsds();
        for (int i = 0; i < dsds.length; i++) {
            if (dsds[i].getDsType() == 'M') {
                firstMDSDsd = dsds[i];
                break;
            }
        }

        roi.setFirstTiePointLine(firstLine / linesPerTiePoint);
        if (firstMDSDsd != null && roi.getLastLine() > firstMDSDsd.getNumDsr()) {
            roi.setLastLine(firstMDSDsd.getNumDsr());
            roi.setLastTiePointLine(roi.getLastLine() / linesPerTiePoint);
        } else {
            roi.setLastTiePointLine(lastLine / linesPerTiePoint);
        }

        return roi;
    }

    public MdsrLineMap createLineMap(DatasetDescriptor[] dsds, ImageInputStream in) {
        return new MdsrLineMap();
    }
}
