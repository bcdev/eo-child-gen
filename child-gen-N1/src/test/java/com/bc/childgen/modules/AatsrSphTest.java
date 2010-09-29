package com.bc.childgen.modules;

import com.bc.childgen.ChildGenConstants;
import com.bc.childgen.ChildGenException;
import com.bc.childgen.DatasetDescriptor;
import junit.framework.TestCase;

@SuppressWarnings({"MagicNumber"})
public class AatsrSphTest extends TestCase {

    public void testGetRawData() {
        byte[] buffer = sph.getRawData();
        assertEquals(EXP_SIZE, buffer.length);
    }

    public void testGetLinesPerTiePoint() {
        assertEquals(32, sph.getLinesPerTiePoint());
    }

    public void testAdjustSqadDsds() throws ChildGenException {
        MdsrLineMap lineMap = new MdsrLineMap();
        final StringBuffer sphBuffer = SphTest.createBlankBuffer(12);
        SphTest.appendDsdToBuffer(sphBuffer, ChildGenConstants.AATSR_QUALITY_ADS_NAME, 'A', "                                                              ", 7077, 6966, 81, 86);
        SphTest.appendDsdToBuffer(sphBuffer, ChildGenConstants.AATSR_TIE_PT_ADS_NAME, 'A', "                                                              ", 14044, 6966, 81, 86);
        final byte[] bytes = sphBuffer.toString().getBytes();

        final AatsrSph sph = new AatsrSph(bytes.length, 2, 280);
        sph.buffer = bytes;
        sph.parseDSDs();

        sph.adjustDSDs(24867, 1423, 5, lineMap);   // expect dsds 48 - 51

        final DatasetDescriptor[] dsds = sph.getDsds();
        assertEquals(2, dsds.length);

        final DatasetDescriptor sqadDsd = dsds[0];
        assertEquals(4, sqadDsd.getNumDsr());
        assertEquals(4 * 86, sqadDsd.getDsSize());
        assertEquals(7077, sqadDsd.getDsOffset());

        final DatasetDescriptor tpDsd = dsds[1];
        assertEquals(5, tpDsd.getNumDsr());
        assertEquals(5 * 86, tpDsd.getDsSize());
        assertEquals(7077 + 4 * 86, tpDsd.getDsOffset());
    }

    public void testAdjustDsds_MDS_attachmentFlag() throws ChildGenException {
        final MdsrLineMap lineMap = new MdsrLineMap();
        int[] mdsrLines = new int[] {0,
                1,
                2,
                -1, // 3
                -1, // 4
                -1, // 5
                -1, // 6
                3,  // 7
                4,  // 8
                5,  // 9
                6,  // 10
                7,  // 11
                -1, // 12
                -1, // 13
                -1, // 14
                -1, // 15
                8,  // 16
                9,  // 17
                10,  // 18
                11,  // 19
                12,  // 20
                13,  // 21
                14,  // 22
                15,  // 23
        };
        lineMap.setMappingInfo(mdsrLines);
        
        final StringBuffer sphBuffer = SphTest.createBlankBuffer(12);
        SphTest.appendDsdToBuffer(sphBuffer, ChildGenConstants.AATSR_TIE_PT_ADS_NAME, 'A', "                                                              ", 7077, 6966, 81, 86);
        SphTest.appendDsdToBuffer(sphBuffer, "MEASUREMENT_DATA            ", 'M', "                                                              ", 14044, 6966, 81, 86);
        final byte[] bytes = sphBuffer.toString().getBytes();

        final AatsrSph sph = new AatsrSph(bytes.length, 2, 280);
        sph.buffer = bytes;
        sph.parseDSDs();

        sph.adjustDSDs(10, 12, 1, lineMap);

        final DatasetDescriptor[] dsds = sph.getDsds();
        assertEquals(2, dsds.length);

        final DatasetDescriptor adsDsd = dsds[0];
        assertEquals(1, adsDsd.getNumDsr());
        //noinspection PointlessArithmeticExpression
        assertEquals(1 * 86, adsDsd.getDsSize());
        assertEquals(7077, adsDsd.getDsOffset());

        final DatasetDescriptor mdsDsd = dsds[1];
        assertEquals(8, mdsDsd.getNumDsr());
        assertEquals(8 * 86, mdsDsd.getDsSize());
        assertEquals(7077 + 1 * 86, mdsDsd.getDsOffset());
    }

    public void testCalculateSourceOffset() {
        final DatasetDescriptor dsd = new DatasetDescriptor();
        final MdsrLineMap lineMap = new MdsrLineMap();

        final int dsOffset = 6912;
        final int dsrSize = 188;
        dsd.setDsOffset(dsOffset);
        dsd.setSpare(true);
        assertEquals(dsOffset, sph.calculateSourceOffset(23, 56, dsd, lineMap));

        dsd.setDsOffset(dsOffset);
        dsd.setSpare(false);
        dsd.setDsType('R');
        assertEquals(dsOffset, sph.calculateSourceOffset(175, 4, dsd, lineMap));

        dsd.setDsOffset(dsOffset);
        dsd.setDsrSize(dsrSize);
        dsd.setDsType('A');
        assertEquals(dsOffset + 4 * dsrSize, sph.calculateSourceOffset(175, 4, dsd, lineMap));

        dsd.setDsOffset(dsOffset);
        dsd.setDsrSize(dsrSize);
        dsd.setDsType('A');
        dsd.setDsName(ChildGenConstants.AATSR_QUALITY_ADS_NAME);
        assertEquals(dsOffset, sph.calculateSourceOffset(175, 4, dsd, lineMap));

        dsd.setDsOffset(dsOffset);
        dsd.setDsrSize(dsrSize);
        dsd.setDsType('M');
        assertEquals(dsOffset + 175 * dsrSize, sph.calculateSourceOffset(175, 4, dsd, lineMap));
    }

    public void testCalculateSourceOffset_attachmentFlagSet() {
        final DatasetDescriptor dsd = new DatasetDescriptor();
        final MdsrLineMap lineMap = new MdsrLineMap();
        int[] mdsrLines = new int[] {0,
                1,
                2,
                -1, // 3
                -1, // 4
                -1, // 5
                -1, // 6
                3,  // 7
                4,  // 8
                5,  // 9
                6,  // 10
                7,  // 11
        };
        lineMap.setMappingInfo(mdsrLines);

        final int dsOffset = 6912;
        final int dsrSize = 188;        

        dsd.setDsOffset(dsOffset);
        dsd.setDsrSize(dsrSize);
        dsd.setDsType('M');
        assertEquals(dsOffset + 6 * dsrSize, sph.calculateSourceOffset(10, 0, dsd, lineMap));
    }

    public void testCalculateSourceOffset_attachmentFlagSet_allMDSRsFlagged() {
        final DatasetDescriptor dsd = new DatasetDescriptor();
        final MdsrLineMap lineMap = new MdsrLineMap();
        int[] mdsrLines = new int[] {-1,
                -1,
                -1,
                -1, // 3
                -1, // 4
                -1, // 5
                -1, // 6
                -1,  // 7
                -1,  // 8
                -1,  // 9
                0,  // 10
                1,  // 11
        };
        lineMap.setMappingInfo(mdsrLines);

        final int dsOffset = 6912;
        final int dsrSize = 188;

        dsd.setDsOffset(dsOffset);
        dsd.setDsrSize(dsrSize);
        dsd.setDsType('M');
        //noinspection PointlessArithmeticExpression
        assertEquals(dsOffset + 0 * dsrSize, sph.calculateSourceOffset(8, 0, dsd, lineMap));
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PACKAGE
    ////////////////////////////////////////////////////////////////////////////////

    private static final int EXP_SIZE = 1298;
    private AatsrSph sph;

    protected void setUp() throws ChildGenException {
        sph = new AatsrSph(EXP_SIZE, 0, 0);
    }
}
