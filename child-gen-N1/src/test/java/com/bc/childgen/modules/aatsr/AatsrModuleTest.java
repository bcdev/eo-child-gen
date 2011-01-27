package com.bc.childgen.modules.aatsr;

import com.bc.childgen.ChildGenConstants;
import com.bc.childgen.ChildGenException;
import com.bc.childgen.DatasetDescriptor;
import com.bc.childgen.modules.*;
import junit.framework.TestCase;

import java.io.IOException;


public class AatsrModuleTest extends TestCase {

    @SuppressWarnings({"ConstantConditions"})
    public void testInterfaceImplemented() {
        assertTrue(module instanceof Module);
    }

    public void testAdjustRoi_snapToTiePoints() throws ChildGenException {
        final TstSph sph = new TstSph();
        final DatasetDescriptor geolocationDSD = new DatasetDescriptor();
        geolocationDSD.setDsName(ChildGenConstants.AATSR_TIE_PT_ADS_NAME);
        geolocationDSD.setNumDsr(4000);     // set high enough to avoid roi clipping
        sph.getDsds()[0] = geolocationDSD;
        final Roi roi = new Roi();

        sph.setLinesPerTiePoint(32);
        roi.setFirstLine(116);
        roi.setLastLine(203);

        Roi result = module.adjustRoi(roi, sph);
        assertEquals(96, result.getFirstLine());
        assertEquals(223, result.getLastLine());
        assertEquals(3, result.getFirstTiePointLine());
        assertEquals(7, result.getLastTiePointLine());

        sph.setLinesPerTiePoint(32);
        roi.setFirstLine(28755);
        roi.setLastLine(41007);

        result = module.adjustRoi(roi, sph);
        assertEquals(28736, result.getFirstLine());
        assertEquals(41023, result.getLastLine());
        assertEquals(898, result.getFirstTiePointLine());
        assertEquals(1282, result.getLastTiePointLine());

        sph.setLinesPerTiePoint(32);
        roi.setFirstLine(864);
        roi.setLastLine(3479);

        result = module.adjustRoi(roi, sph);
        assertEquals(864, result.getFirstLine());
        assertEquals(3487, result.getLastLine());
        assertEquals(27, result.getFirstTiePointLine());
        assertEquals(109, result.getLastTiePointLine());

        sph.setLinesPerTiePoint(32);
        roi.setFirstLine(882);
        roi.setLastLine(3487);

        result = module.adjustRoi(roi, sph);
        assertEquals(864, result.getFirstLine());
        assertEquals(3487, result.getLastLine());
        assertEquals(27, result.getFirstTiePointLine());
        assertEquals(109, result.getLastTiePointLine());
    }

    public void testAdjustRoi_clipAtProductEnd() throws ChildGenException {
        final TstSph sph = new TstSph();
        final DatasetDescriptor geolocationDSD = new DatasetDescriptor();
        geolocationDSD.setDsName(ChildGenConstants.AATSR_TIE_PT_ADS_NAME);
        geolocationDSD.setNumDsr(101);     // equals 3200 lines
        sph.getDsds()[0] = geolocationDSD;
        final Roi roi = new Roi();

        sph.setLinesPerTiePoint(32);
        roi.setFirstLine(2800);
        roi.setLastLine(3320);

        final Roi result = module.adjustRoi(roi, sph);
        assertEquals(2784, result.getFirstLine());
        assertEquals(3200, result.getLastLine());
        assertEquals(87, result.getFirstTiePointLine());
        assertEquals(100, result.getLastTiePointLine());
    }

    public void testCreateLineMap_noGeolocationDSD() throws IOException {
        final DatasetDescriptor[] dsds = new DatasetDescriptor[2];
        DatasetDescriptor dsd = new DatasetDescriptor();
        dsd.setDsName("some_name");
        dsd.setNumDsr(15);
        dsds[0] = dsd;

        dsd = new DatasetDescriptor();
        dsd.setDsType('M');
        dsd.setNumDsr((15 - 1) * ChildGenConstants.AATSR_LINES_PER_TIE_POINT);
        dsds[1] = dsd;

        try {
            module.createLineMap(dsds, null);
            fail("ChildGenException expected");
        } catch (ChildGenException expected) {
        }
    }

    public void testCreateLineMap_noMapping() throws IOException, ChildGenException {
        final DatasetDescriptor[] dsds = new DatasetDescriptor[2];
        DatasetDescriptor dsd = new DatasetDescriptor();
        dsd.setDsName(ChildGenConstants.AATSR_TIE_PT_ADS_NAME);
        dsd.setNumDsr(15);
        dsds[0] = dsd;

        dsd = new DatasetDescriptor();
        dsd.setDsType('M');
        dsd.setNumDsr((15 - 1) * ChildGenConstants.AATSR_LINES_PER_TIE_POINT);
        dsds[1] = dsd;

        final MdsrLineMap map = module.createLineMap(dsds, null);
        assertEquals(23, map.getMdsrIndex(23));
    }

    public void testCreateLineMap_noMDS_DSD() throws IOException, ChildGenException {
        final DatasetDescriptor[] dsds = new DatasetDescriptor[2];
        DatasetDescriptor dsd = new DatasetDescriptor();
        dsd.setDsName(ChildGenConstants.AATSR_TIE_PT_ADS_NAME);
        dsd.setNumDsr(15);
        dsds[0] = dsd;

        dsd = new DatasetDescriptor();
        dsd.setDsType('D');
        dsd.setNumDsr((15 - 1) * ChildGenConstants.AATSR_LINES_PER_TIE_POINT);
        dsds[1] = dsd;

        try {
            module.createLineMap(dsds, null);
            fail("ChildGenException expected");
        } catch (ChildGenException expected) {
        }

    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private AatsrModule module;

    protected void setUp() {
        module = new AatsrModule();
    }
}
