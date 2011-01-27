package com.bc.childgen.modules.meris;

import com.bc.childgen.ChildGenConstants;
import com.bc.childgen.ChildGenException;
import com.bc.childgen.DatasetDescriptor;
import com.bc.childgen.modules.MdsrLineMap;
import junit.framework.TestCase;

@SuppressWarnings({"MagicNumber"})
public class MerisSphTest extends TestCase {

    public void testGetRawData() {
        byte[] buffer = sph.getRawData();
        assertEquals(EXP_SIZE, buffer.length);
    }

    public void testGetLinesPerTiePoint() throws ChildGenException {
        String testLinesPerTp = "+045";
        System.arraycopy(testLinesPerTp.getBytes(), 0, sph.getRawData(), 1437, 4);

        assertEquals(45, sph.getLinesPerTiePoint());

        testLinesPerTp = "+abc";
        System.arraycopy(testLinesPerTp.getBytes(), 0, sph.getRawData(), 1437, 4);

        try {
            sph.getLinesPerTiePoint();
            fail("ChildGenException expected");
        } catch (ChildGenException expected) {
        }
    }

    public void testGetSourceOffset() {
        final DatasetDescriptor dsd = new DatasetDescriptor();
        final MdsrLineMap lineMap = new MdsrLineMap();

        final int dsOffset = 5391;
        final int dsrSize = 167;
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

        String testLinesPerTp = "+045";
        System.arraycopy(testLinesPerTp.getBytes(), 0, sph.getRawData(), 1437, 4);
        dsd.setDsOffset(dsOffset);
        dsd.setDsrSize(dsrSize);
        dsd.setDsType('A');
        dsd.setDsName(ChildGenConstants.MERIS_QUALITY_ADS_NAME);
        assertEquals(dsOffset, sph.calculateSourceOffset(175, 4, dsd, lineMap));

        dsd.setDsOffset(dsOffset);
        dsd.setDsrSize(dsrSize);
        dsd.setDsType('M');
        assertEquals(dsOffset + 175 * dsrSize, sph.calculateSourceOffset(175, 4, dsd, lineMap));
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private static final int EXP_SIZE = 1600;

    private MerisSph sph;

    protected void setUp() throws ChildGenException {
        sph = new MerisSph(EXP_SIZE, 0, 0);
    }

    /*
    offsets in SPH                      value
    SPH_DESCRIPTOR                  0       16
    STRIPLINE_CONTINUITY_INDICATOR  46      77
    SLICE_POSITION                  82      97
    NUM_SLICES                      102    113
    FIRST_LINE_TIME                 118    135
    LAST_LINE_TIME                  164    180
    FIRST_FIRST_LAT                 209    225
    FIRST_FIRST_LONG                247    264
    FIRST_MID_LAT                   286    300
    FIRST_MID_LONG                  322    337
    FIRST_LAST_LAT                  359    374
    FIRST_LAST_LONG                 396    412
    LAST_FIRST_LAT                  434    449
    LAST_FIRST_LONG                 471    487
    LAST_MID_LAT                    509    522
    LAST_MID_LONG                   544    558
    LAST_LAST_LAT                   580    594
    LAST_LAST_LONG                  616    631
    Spare                           652
    TRANS_ERR_FLAG                  700    715
    FORMAT_ERR_FLAG                 717    733
    DATABASE_FLAG                   735    749
    COARSE_ERR_FLAG                 751    767
    ECMWF_TYPE                      769    780
    NUM_TRANS_ERR                   782    796
    NUM_FORMAT_ERR                  808    823
    TRANS_ERR_THRESH                835    852
    FORMAT_ERR_THRESH               871    889
    Spare                           908
    NUM_BANDS                       986    996
    BAND_WAVELEN                   1001   1014
    BANDWIDTH                      1188   1198
    INST_FOV                       1297   1306
    PROC_MODE                      1327   1337
    OFFSET_COMP                    1339   1351
    LINE_TIME_INTERVAL             1353   1372
    LINE_LENGTH                    1391   1403
    LINES_PER_TIE_PT               1419   1436
    SAMPLES_PER_TIE_PT             1441   1460
    COLUMN_SPACING                 1465   1480
    */
}
