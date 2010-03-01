package com.bc.childgen.modules;

import com.bc.childgen.ChildGenException;
import com.bc.childgen.DummyMph;
import com.bc.childgen.Mph;
import junit.framework.TestCase;

@SuppressWarnings({"MagicNumber"})
public class ModuleFactoryTest extends TestCase {

    public void testGetMerisSph() throws ChildGenException {
        final Mph mph = new DummyMph();
        String testSphSize = "+0000000789";
        System.arraycopy(testSphSize.getBytes(), 0, mph.getRawData(), DSD_SIZE_OFFSET, FIELD_SIZE);
        mph.setProductFileName("MER_RR__1PNMAP20060509_092342_000000562047_00308_21905_0001.N1");

        Sph sph = ModuleFactory.getSph(mph);
        assertTrue(sph instanceof MerisSph);
        assertEquals(789, sph.buffer.length);

        testSphSize = "+0000001389";
        System.arraycopy(testSphSize.getBytes(), 0, mph.getRawData(), DSD_SIZE_OFFSET, FIELD_SIZE);
        mph.setProductFileName("MER_FR__2PNDPA20050417_101956_000000982036_00280_16366_6386.N1");

        sph = ModuleFactory.getSph(mph);
        assertTrue(sph instanceof MerisSph);
        assertEquals(1389, sph.buffer.length);
    }

    public void testGetAatsrSph() throws ChildGenException {
        final Mph mph = new DummyMph();
        String testSphSize = "+0000005543";
        System.arraycopy(testSphSize.getBytes(), 0, mph.getRawData(), DSD_SIZE_OFFSET, FIELD_SIZE);
        mph.setProductFileName("ATS_NR__2PNPDK20060329_103452_000065272046_00223_21319_0188.N1");

        Sph sph = ModuleFactory.getSph(mph);
        assertTrue(sph instanceof AatsrSph);
        assertEquals(5543, sph.buffer.length);

        testSphSize = "+0000002211";
        System.arraycopy(testSphSize.getBytes(), 0, mph.getRawData(), DSD_SIZE_OFFSET, FIELD_SIZE);
        mph.setProductFileName("ATS_TOA_1PPLRA20070110_181252_000065272054_00328_25432_6605.N1");

        sph = ModuleFactory.getSph(mph);
        assertTrue(sph instanceof AatsrSph);
        assertEquals(2211, sph.buffer.length);
    }

    public void testGetInvalidSphTypeThrowsException() {
        final Mph mph = new DummyMph();
        mph.setProductFileName("GNU_TUP_1PPLRA20070110_181252_000065272054_00328_25432_6605.N1");

        try {
            ModuleFactory.getSph(mph);
            fail("ChildGenException expected");
        } catch (ChildGenException expected) {
        }
    }

    public void testAatsrDSDsAreCorrectlyPatched() throws ChildGenException {
        final DummyMph mph = new DummyMph();
        mph.setPatchNumDsds(false);
        mph.setSoftwareVer("STEP/1.2+     ");

        String testSphSize = "+0000005543";
        String numDsds = "+0000000038";
        System.arraycopy(testSphSize.getBytes(), 0, mph.getRawData(), DSD_SIZE_OFFSET, FIELD_SIZE);
        System.arraycopy(numDsds.getBytes(), 0, mph.getRawData(), NUM_DSD_OFFSET, FIELD_SIZE);
        mph.setProductFileName("AT1_NR__2PNPDK20060329_103452_000065272046_00223_21319_0188.N1");

        Sph sph = ModuleFactory.getSph(mph);
        assertTrue(sph instanceof AatsrSph);
        assertEquals(5543, sph.buffer.length);
        assertEquals(38, sph.getDsds().length);

        mph.setProductFileName("AT2_NR__2PNPDK20060329_103452_000065272046_00223_21319_0188.N1");

        sph = ModuleFactory.getSph(mph);
        assertTrue(sph instanceof AatsrSph);
        assertEquals(5543, sph.buffer.length);
        assertEquals(38, sph.getDsds().length);

        mph.setSoftwareVer("STEP/1.0      ");

        testSphSize = "+0000002211";
        numDsds = "+0000000036";
        System.arraycopy(testSphSize.getBytes(), 0, mph.getRawData(), DSD_SIZE_OFFSET, FIELD_SIZE);
        System.arraycopy(numDsds.getBytes(), 0, mph.getRawData(), NUM_DSD_OFFSET, FIELD_SIZE);
        mph.setProductFileName("AT1_TOA_1PPLRA20070110_181252_000065272054_00328_25432_6605.N1");

        sph = ModuleFactory.getSph(mph);
        assertTrue(sph instanceof AatsrSph);
        assertEquals(2211, sph.buffer.length);
        assertEquals(38, sph.getDsds().length);

        mph.setProductFileName("AT2_TOA_1PPLRA20070110_181252_000065272054_00328_25432_6605.N1");

        sph = ModuleFactory.getSph(mph);
        assertTrue(sph instanceof AatsrSph);
        assertEquals(2211, sph.buffer.length);
        assertEquals(38, sph.getDsds().length);
    }

    public void testGetMerisModule() throws ChildGenException {
        final DummyMph mph = new DummyMph();
        mph.setProductFileName("MER_RR__1PNMAP20060509_092342_000000562047_00308_21905_0001.N1");

        Module module = ModuleFactory.getModule(mph);
        assertTrue(module instanceof MerisModule);

        mph.setProductFileName("MER_FR__2PNDPA20050417_101956_000000982036_00280_16366_6386.N1");

        module = ModuleFactory.getModule(mph);
        assertTrue(module instanceof MerisModule);
    }

    public void testGetAatsrModule() throws ChildGenException {
        final DummyMph mph = new DummyMph();
        mph.setProductFileName("AT1_NR__2PNPDK20060329_103452_000065272046_00223_21319_0188.N1");

        Module module = ModuleFactory.getModule(mph);
        assertTrue(module instanceof AatsrModule);

        mph.setProductFileName("AT2_TOA_1PNPDK20060329_103452_000065272046_00223_21319_0188.N1");

        module = ModuleFactory.getModule(mph);
        assertTrue(module instanceof AatsrModule);

        mph.setProductFileName("ATS_TOA_1PPLRA20070110_181252_000065272054_00328_25432_6605.N1");

        module = ModuleFactory.getModule(mph);
        assertTrue(module instanceof AatsrModule);
    }

    public void testGetModuleThrowsExceptionOnIllegalType() {
        final Mph mph = new DummyMph();
        mph.setProductFileName("BLA_TUP_1PPLRA20070110_181252_000065272054_00328_25432_6605.N1");

        try {
            ModuleFactory.getModule(mph);
            fail("ChildGenException expected");
        } catch (ChildGenException expected) {
        }       
    }

    public void testIsIncorrectDsdNumberProductVersion() {
        assertFalse(ModuleFactory.isIncorrectDsdNumberProductVersion("STEP/1.3"));        
        assertFalse(ModuleFactory.isIncorrectDsdNumberProductVersion("STEP/1.2"));
        assertFalse(ModuleFactory.isIncorrectDsdNumberProductVersion("STEP/1.1"));

        assertTrue(ModuleFactory.isIncorrectDsdNumberProductVersion("STEP/1.0"));        
        assertTrue(ModuleFactory.isIncorrectDsdNumberProductVersion("STEP/0.9"));        
        assertTrue(ModuleFactory.isIncorrectDsdNumberProductVersion("STEP/0.0+"));        

        assertTrue(ModuleFactory.isIncorrectDsdNumberProductVersion("thisisshit"));        
        assertTrue(ModuleFactory.isIncorrectDsdNumberProductVersion(""));        
        assertTrue(ModuleFactory.isIncorrectDsdNumberProductVersion("proto2_static/"));        
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private static final int DSD_SIZE_OFFSET = 1113;
    private static final int NUM_DSD_OFFSET = 1140;
    private static final int FIELD_SIZE = 11;
}
