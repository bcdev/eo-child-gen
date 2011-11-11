package com.bc.merci.geochildgen;

import com.bc.util.string.StringUtils;
import junit.framework.TestCase;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

// Most of this class is duplicated (copied from smos-data-distribution); should go in a test library
public class TestDataTest extends TestCase {
    private String testDataDirPath;

    public void testAllAcceptanceTestFilesHaveCorrectHash() throws IOException {
        verifyFileHashSum("066fb1148c70ad857d24208ab6cca1a4", "ATS_TOA_1PPTOM20070110_192521_000000822054_00328_25432_0001.N1");
    }

    protected String getTestDataDirPath() throws IOException {
        if (StringUtils.isEmpty(testDataDirPath)) {
            testDataDirPath = getPropertyFromResource("/com/bc/merci/geochildgen/testData.properties", "testDataPath");
        }
        return testDataDirPath;
    }

    /*
     * Makes sure you are not using data files that nobody else has. This test only checks the number of files,
     * and therefore relies on testAllFiles() to run successfully.
     */
    public void testCorrectNumberOfAcceptanceTestFiles() throws IOException {
        final File[] files = new File(getTestDataDirPath()).listFiles();
        assertEquals(1, files.length);
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

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

    private MessageDigest md5Digester;
    private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String calculateFileHash(File inputFile) throws IOException, NoSuchAlgorithmException {
        MD5Encoder md5Encoder = new MD5Encoder();
        return md5Encoder.encode(inputFile);
    }

    // This method is copied from eo-data-managg 1.3 BcTestUtils
    public static String getPropertyFromResource(String resourcePath, String propertyName) throws IOException {
        final InputStream resourceAsStream = TestDataTest.class.getResourceAsStream(resourcePath);
        if(resourceAsStream == null) {
        	System.out.println("The requested resource is missing: " + resourcePath);
        	throw new IOException("The requested resource is missing: " + resourcePath);
        }
        Properties props = new Properties();
        props.load(resourceAsStream);
        return props.getProperty(propertyName);
    }


    // This class was copied and changed from bc-commons 1.2, which conflicts with the dependency on version 1.1
    // The limitation is that it does not support files larger than Integer.MAX_VALUE (which seems reasonable)
    // reimplementing a simple version of MD5Encoder seems simpler than reimplementing code that exists in 1.2 and
    // adapting it to use the 1.1 Md5Encoder (which reads bytes from files instead of Strings)
    public static class MD5Encoder {
        private MessageDigest md5Digester;
        private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        public MD5Encoder() throws NoSuchAlgorithmException {
            md5Digester = MessageDigest.getInstance("MD5");
        }

        public void testNothing() {
            //For some reason, JUnit complains that there are no tests in this inner class... so this fake test exists
        }

        public String encode(File inputFile) throws IOException {
            FileInputStream in = new FileInputStream(inputFile);
            if( inputFile.length() > Integer.MAX_VALUE ) {
                throw new RuntimeException("File was too large... not supported by MD5Encoder");
            }
            byte[] bytes = new byte[(int)inputFile.length()];
            in.read(bytes);

            byte[] hashed = md5Digester.digest(bytes);
            return new String(checkAndConvertToString(hashed));
        }

        private char[] checkAndConvertToString(byte[] hashed) {
            if (hashed.length != 16) {
                throw new RuntimeException("MD5 digesting failed");
            }
            // convert binary digest to hex string
            char[] buffer = new char[32];
            for (int i = 0; i < 16; i++) {
                int low = hashed[i] & 0x0f;
                int high = (hashed[i] & 0xf0) >> 4;
                buffer[i * 2] = hex[high];
                buffer[i * 2 + 1] = hex[low];
            }
            return buffer;
        }
    }
}
