package com.bc.childgen.modules.meris;

import com.bc.childgen.ChildGenConstants;
import com.bc.childgen.ChildGenException;
import com.bc.childgen.DatasetDescriptor;
import com.bc.childgen.modules.Module;
import com.bc.childgen.modules.Roi;
import com.bc.childgen.modules.TstSph;
import junit.framework.TestCase;


public class MerisModuleTest extends TestCase {

    @SuppressWarnings({"ConstantConditions"})
    public void testInterfaceImplemented() {
        assertTrue(module instanceof Module);
    }

    // @todo 3 tb/tb add tests for no adjustments on first and/or last line
    public void testAdjustRoi() throws ChildGenException {
        final TstSph sph = new TstSph();
        final Roi roi = new Roi();
        sph.getDsds()[0] = new DatasetDescriptor();
        sph.setLinesPerTiePoint(16);
        roi.setFirstLine(116);
        roi.setLastLine(203);

        Roi result = module.adjustRoi(roi, sph);
        assertEquals(112, result.getFirstLine());
        assertEquals(208, result.getLastLine());
        assertEquals(7, result.getFirstTiePointLine());
        assertEquals(13, result.getLastTiePointLine());

        sph.setLinesPerTiePoint(64);
        roi.setFirstLine(1419);
        roi.setLastLine(3203);

        result = module.adjustRoi(roi, sph);
        assertEquals(1408, result.getFirstLine());
        assertEquals(3264, result.getLastLine());
        assertEquals(22, result.getFirstTiePointLine());
        assertEquals(51, result.getLastTiePointLine());

        sph.setLinesPerTiePoint(32);
        roi.setFirstLine(256);
        roi.setLastLine(1906);

        result = module.adjustRoi(roi, sph);
        assertEquals(256, result.getFirstLine());
        assertEquals(1920, result.getLastLine());
        assertEquals(8, result.getFirstTiePointLine());
        assertEquals(60, result.getLastTiePointLine());

        sph.setLinesPerTiePoint(64);
        roi.setFirstLine(4812);
        roi.setLastLine(6784);

        result = module.adjustRoi(roi, sph);
        assertEquals(4800, result.getFirstLine());
        assertEquals(6784, result.getLastLine());
        assertEquals(75, result.getFirstTiePointLine());
        assertEquals(106, result.getLastTiePointLine());
    }

    public void testAdjustRoi_clipOnLastLine() throws ChildGenException {
        final TstSph sph = new TstSph(2);
        final Roi roi = new Roi();
        final DatasetDescriptor mdsDsd = new DatasetDescriptor();
        mdsDsd.setDsType('M');
        final DatasetDescriptor adsDsd = new DatasetDescriptor();
        adsDsd.setDsName(ChildGenConstants.MERIS_TIE_PT_ADS_NAME);
        sph.getDsds()[0] = mdsDsd;
        sph.getDsds()[1] = adsDsd;

        sph.setLinesPerTiePoint(16);
        mdsDsd.setNumDsr(192);
        adsDsd.setNumDsr(192 / 16);
        roi.setFirstLine(116);
        roi.setLastLine(203);

        Roi result = module.adjustRoi(roi, sph);
        assertEquals(112, result.getFirstLine());
        assertEquals(191, result.getLastLine());
        assertEquals(7, result.getFirstTiePointLine());
        assertEquals(11, result.getLastTiePointLine());

        sph.setLinesPerTiePoint(64);
        mdsDsd.setNumDsr(3200);
        adsDsd.setNumDsr(3200 / 64);
        roi.setFirstLine(1419);
        roi.setLastLine(3203);

        result = module.adjustRoi(roi, sph);
        assertEquals(1408, result.getFirstLine());
        assertEquals(3199, result.getLastLine());
        assertEquals(22, result.getFirstTiePointLine());
        assertEquals(49, result.getLastTiePointLine());
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private MerisModule module;

    protected void setUp() {
        module = new MerisModule();
    }
}
