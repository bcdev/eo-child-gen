package com.bc.merci.geochildgen;

import com.bc.util.encoder.MD5Encoder;
import com.bc.util.string.StringUtils;
import com.bc.util.test.BcTestUtils;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

// Most of this class is duplicated (copied from smos-data-distribution); should go in a test library
public class TestDataTest extends TestCase {
    private String testDataDirPath;

    public void testAllAcceptanceTestFilesHaveCorrectHash() throws IOException {
        verifyFileHashSum("066fb1148c70ad857d24208ab6cca1a4", "ATS_TOA_1PPTOM20070110_192521_000000822054_00328_25432_0001.N1");
    }

    public void testCorrectNumberOfAcceptanceTestFiles() throws IOException {
        final File[] files = new File(getTestDataDirPath()).listFiles();
        assertEquals(1, files.length);
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    protected String getTestDataDirPath() throws IOException {
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
}
