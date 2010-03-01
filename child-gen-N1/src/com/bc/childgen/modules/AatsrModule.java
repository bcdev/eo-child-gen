package com.bc.childgen.modules;

import com.bc.childgen.ChildGenConstants;
import com.bc.childgen.ChildGenException;
import com.bc.childgen.DatasetDescriptor;

import javax.imageio.stream.ImageInputStream;
import java.io.IOException;


class AatsrModule implements Module {

    public Roi adjustRoi(Roi roi, Sph sph) throws ChildGenException {
        final int linesPerTiePoint = sph.getLinesPerTiePoint();
        final DatasetDescriptor geolocationDsd = getGeolocationDsd(sph.getDsds());
        final int expectedHeight = calculateExpectedProductHeight(geolocationDsd);

        int firstLine = roi.getFirstLine();
        if (firstLine % linesPerTiePoint != 0) {
            firstLine -= firstLine % linesPerTiePoint;
            roi.setFirstLine(firstLine);
        }

        int lastLine = roi.getLastLine();
        if (lastLine % linesPerTiePoint != 1) {
            lastLine += linesPerTiePoint - lastLine % linesPerTiePoint;
            lastLine -= 1;
            if (lastLine > expectedHeight) {
                lastLine = expectedHeight;
            }
            roi.setLastLine(lastLine);
        }

        roi.setFirstTiePointLine(firstLine / linesPerTiePoint);
        roi.setLastTiePointLine((lastLine + 1) / linesPerTiePoint);

        return roi;
    }

    public MdsrLineMap createLineMap(DatasetDescriptor[] dsds, ImageInputStream in) throws IOException, ChildGenException {
        // find geolocation ADS
        final DatasetDescriptor geolocationDsd = getGeolocationDsd(dsds);

        // find first MDS
        DatasetDescriptor firstMdsDsd = null;
        for (int i = 0; i < dsds.length; i++) {
            if (dsds[i].getDsType() == 'M') {
                firstMdsDsd = dsds[i];
                break;
            }
        }

        if (firstMdsDsd == null) {
            throw new ChildGenException("MDS DSD not present");
        }

        final MdsrLineMap result = new MdsrLineMap();

        // compare number of lines
        final int mdsLines = firstMdsDsd.getNumDsr();
        final int expectedProductHeight = calculateExpectedProductHeight(geolocationDsd);
        if (expectedProductHeight > mdsLines) {
            // read geolocation records
            final long offset = geolocationDsd.getDsOffset();
            in.seek(offset);
            final byte[] buffer = new byte[geolocationDsd.getDsSize()];
            in.read(buffer);

            //iterate over records and fill map
            int[] lineMap = new int[expectedProductHeight];
            int mdsrIndex = 0;
            final int records = geolocationDsd.getNumDsr() - 1;
            for (int adsIndex = 0; adsIndex < records; adsIndex++) {
                final int flagIndex = adsIndex * geolocationDsd.getDsrSize() + 12;
                for (int scanIndex = 0; scanIndex < ChildGenConstants.AATSR_LINES_PER_TIE_POINT; scanIndex++) {
                    final int line = adsIndex * ChildGenConstants.AATSR_LINES_PER_TIE_POINT + scanIndex;
                    if (buffer[flagIndex] != 0) {
                        lineMap[line] = -1;
                    } else {
                        lineMap[line] = mdsrIndex;
                        ++mdsrIndex;
                    }
                }
            }
            result.setMappingInfo(lineMap);
        }

        return result;
    }

    private int calculateExpectedProductHeight(DatasetDescriptor geolocationDsd) {
        final int tiePointLines = geolocationDsd.getNumDsr();
        return (tiePointLines - 1) * ChildGenConstants.AATSR_LINES_PER_TIE_POINT;
    }

    private DatasetDescriptor getGeolocationDsd(DatasetDescriptor[] dsds) throws ChildGenException {
        for (int i = 0; i < dsds.length; i++) {
            if (ChildGenConstants.AATSR_TIE_PT_ADS_NAME.equals(dsds[i].getDsName())) {
                return dsds[i];
            }
        }
        throw new ChildGenException("geolocation DSD not present");
    }
}
