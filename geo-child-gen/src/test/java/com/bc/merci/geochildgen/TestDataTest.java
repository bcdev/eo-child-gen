package com.bc.merci.geochildgen;

import com.bc.util.encoder.MD5Encoder;
import com.bc.util.string.StringUtils;
import com.bc.util.test.BcTestUtils;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

// Most of this class is duplicated (copied from smos-data-distribution); should go in a test library
public class TestDataTest extends TestCase {
    private String testDataDirPath;

    public void testAllAcceptanceTestFilesHaveCorrectHash() throws IOException {
        if (isIOTestsSuppressed()) {
            System.err.println("testAllAcceptanceTestFilesHaveCorrectHash() suppressed");
            return;
        }
        verifyFileHashSum("066fb1148c70ad857d24208ab6cca1a4", "ATS_TOA_1PPTOM20070110_192521_000000822054_00328_25432_0001.N1");
        verifyFileHashSum("fefb8710fa1d78d618c809b9fc463f1a", "AT1_NR__2PTRAL19930614_131152_000000004013_00338_10002_0000.E1");
    }

    public void testCorrectNumberOfAcceptanceTestFiles() throws IOException {
        final File[] files = new File(getTestDataDirPath()).listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().startsWith("ATS_") || pathname.getName().startsWith("AT1_");
            }
        });
        assertEquals(2, files.length);
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private String getTestDataDirPath() throws IOException {
        if (StringUtils.isEmpty(testDataDirPath)) {
            testDataDirPath = BcTestUtils.getPropertyFromResource("/com/bc/merci/geochildgen/testData.properties", "testDataPath");
        }
        return testDataDirPath;
    }

    private void verifyFileHashSum(String md5sum, String name) throws IOException {
        File testFile = new File(getTestDataDirPath() + "/" + name);
        assertTrue("Test data file should exist: " + testFile.getAbsolutePath(), testFile.exists());
        assertEquals(md5sum, calculateMd5sum(testFile.getAbsolutePath()));
    }

    private static String calculateMd5sum(String filename) throws IOException {
        try {
            return calculateFileHash(new File(filename));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String calculateFileHash(File inputFile) throws IOException, NoSuchAlgorithmException {
        final MD5Encoder md5Encoder = new MD5Encoder();
        return md5Encoder.encode(inputFile);
    }

    // @todo 3 tb/** this is also a common check - move to testing framework if exists - tb 2011-11-28
    private static boolean isIOTestsSuppressed() {
        return "true".equals(System.getProperty("noiotests"));
    }
}
