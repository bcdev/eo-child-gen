package com.bc.merci.geochildgen;

import com.bc.childgen.ChildGenException;
import com.bc.util.test.BcTestUtils;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class GeoChildGenAcceptanceTest extends TestCase {

    private File propertiesFile;
    private File testDir;

    public void testSubsetFrom_AATSR_copyProduct() throws IOException, SQLException, ChildGenException, ParseException {
        if (isIOTestsSuppressed()) {
            System.err.println("testSubsetFrom_AATSR_copyProduct() suppressed");
            return;
        }

        final File aatsrFile = getTestDataFile("ATS_TOA_1PPTOM20070110_192521_000000822054_00328_25432_0001.N1");

        final File propsFile = createPropertiesFile("polygon((-170 -68,-159 -70,-164 -74,-176 -71))");
        final CmdLineParams cmdLineParams = new CmdLineParams();
        cmdLineParams.setPropertiesFileName(propsFile.getAbsolutePath());
        cmdLineParams.setOutputDirName(testDir.getAbsolutePath());
        cmdLineParams.addInputFileName(aatsrFile.getAbsolutePath());

        GeoChildGen.run(cmdLineParams);

        assertTargetFileCreated(aatsrFile.getName(), 10365697L);
    }

    public void testSubsetFrom_AATSR_createSubset() throws IOException, SQLException, ChildGenException, ParseException {
        if (isIOTestsSuppressed()) {
            System.err.println("testSubsetFrom_AATSR_createSubset() suppressed");
            return;
        }

        final File aatsrFile = getTestDataFile("ATS_TOA_1PPTOM20070110_192521_000000822054_00328_25432_0001.N1");

        final File propsFile = createPropertiesFile("polygon((-170 -68,-159 -70,-164 -74,-176 -71))");
        final CmdLineParams cmdLineParams = new CmdLineParams();
        cmdLineParams.setPropertiesFileName(propsFile.getAbsolutePath());
        cmdLineParams.setOutputDirName(testDir.getAbsolutePath());
        cmdLineParams.setCreateChildOption(true);
        cmdLineParams.addInputFileName(aatsrFile.getAbsolutePath());

        GeoChildGen.run(cmdLineParams);

        assertTargetFileCreated("ATS_TOA_1PPMAP20070110_192521_000000772054_00328_25432_0001.N1", 9738161L);
    }

    public void testSubsetFrom_ATSR1_createSplitSubsetSubsets() {
        if (isIOTestsSuppressed()) {
            System.err.println("testSubsetFrom_ATSR1_createSplitSubsetSubsets() suppressed");
            return;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void setUp() throws Exception {
        testDir = new File("testDir");
        if (!testDir.mkdirs()) {
            fail("unable to create test directory: " + testDir.getAbsolutePath());
        }
    }

    @Override
    protected void tearDown() {
        if (propertiesFile != null) {
            if (!propertiesFile.delete()) {
                fail("unable to delete test file: " + propertiesFile.getAbsolutePath());
            }
        }

        if (testDir != null) {
            deleteFileTree(testDir);
            if (testDir.isDirectory()) {
                fail("unable to delete test directory - check your file streams!");
            }
        }
    }

    // @todo 3 tb/** this is also a common check - move to testing framework if exists - tb 2011-11-17
    private static boolean isIOTestsSuppressed() {
        return "true".equals(System.getProperty("noiotests"));
    }

    private void assertTargetFileCreated(String name, long expected) {
        final File targetFile = new File(testDir, name);
        assertTrue(targetFile.isFile());
        assertEquals(expected, targetFile.length());
    }

    private static File getTestDataFile(String filename) throws IOException {
        final String testDataPath = BcTestUtils.getPropertyFromResource("/com/bc/merci/geochildgen/testData.properties", "testDataPath");
        final File testDataFile = new File(testDataPath, filename);
        if (!testDataFile.isFile()) {
            fail("test data file not found: " + testDataFile.getAbsolutePath());
        }
        return testDataFile;
    }

    private File createPropertiesFile(String geometryWKT) throws IOException {
        propertiesFile = new File("test_geochildgen.properties");
        if (!propertiesFile.createNewFile()) {
            fail("unable to create test file: " + propertiesFile.getAbsolutePath());
        }

        final FileWriter writer = new FileWriter(propertiesFile);
        writer.write("childProductOriginatorId = map\n");
        writer.write("geometry[0] = ");
        writer.write(geometryWKT);
        writer.flush();
        writer.close();

        return propertiesFile;
    }

    // @todo 3 - this code is copied from BcCommons:FileUtils - as Peter stated, we should
    // create a general testing framework that covers this functionality
    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    private static void deleteFileTree(File treeRoot) {
        File[] files = treeRoot.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isDirectory()) {
                    deleteFileTree(file);
                }
                file.delete();
            }
        }
        treeRoot.delete();
    }
}


