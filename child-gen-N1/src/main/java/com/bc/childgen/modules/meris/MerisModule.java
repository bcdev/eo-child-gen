package com.bc.childgen.modules.meris;

import com.bc.childgen.ChildGenConstants;
import com.bc.childgen.ChildGenException;
import com.bc.childgen.DatasetDescriptor;
import com.bc.childgen.modules.MdsrLineMap;
import com.bc.childgen.modules.Module;
import com.bc.childgen.modules.Roi;
import com.bc.childgen.modules.Sph;

import javax.imageio.stream.ImageInputStream;


public class MerisModule implements Module {

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
        for (DatasetDescriptor dsd : dsds) {
            if (dsd.getDsType() == 'M') {
                firstMDSDsd = dsd;
                break;
            }
        }

        roi.setFirstTiePointLine(firstLine / linesPerTiePoint);
        if (firstMDSDsd != null && roi.getLastLine() >= firstMDSDsd.getNumDsr()) {
            roi.setLastLine(firstMDSDsd.getNumDsr() - 1);

            DatasetDescriptor firstTDSDsd = null;
            for (DatasetDescriptor dsd : dsds) {
                if (Constants.TIE_POINTS_ADS_NAME.equals(dsd.getDsName())) {
                    firstTDSDsd = dsd;
                    break;
                }
            }
            if (firstTDSDsd == null) {
                throw new IllegalStateException("No Tie Point DSDs found");
            }
            roi.setLastTiePointLine(firstTDSDsd.getNumDsr() - 1);
        } else {
            roi.setLastTiePointLine(lastLine / linesPerTiePoint);
        }

        return roi;
    }

    public MdsrLineMap createLineMap(DatasetDescriptor[] dsds, ImageInputStream in) {
        return new MdsrLineMap();
    }
}
