package com.bc.childgen;

import junit.framework.TestCase;

import javax.imageio.stream.FileCacheImageInputStream;
import javax.imageio.stream.FileCacheImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.bc.childgen.ChildGenConstants.MPH_SIZE_IN_BYTES;

public class IIOTest extends TestCase {
    
    private static final String N1_TEST_DATA_NAME = "MER_RR__1P.N1";
    private static final int DSD_SIZE = 280;
    private static final int RR_FRAME_SIZE = 16;
    private static final int M = 1024 * 1024;


    public void testStreamingIO() throws ChildGenException, IOException {
        InputStream is = getClass().getResourceAsStream(N1_TEST_DATA_NAME);
        assertNotNull("Test data not found: " + N1_TEST_DATA_NAME, is);
        ImageInputStream iis = new FileCacheImageInputStream(is, null);

        int expectedNumTpDsr;

        expectedNumTpDsr = 8;
        testContents(process(iis, "XYZ-1", 0, 100),
                     4243698L,
                     "XYZ-1",
                     "DS_NAME=\"Tie points ADS              \"\n" +
                             "DS_TYPE=A\n" +
                             "FILENAME=\"                                                              \"\n" +
                             "DS_OFFSET=+00000000000000011481<bytes>\n" +
                             "DS_SIZE=+00000000000000028504<bytes>\n" +
                             "NUM_DSR=+000000000" + expectedNumTpDsr + "\n" +
                             "DSR_SIZE=+0000003563<bytes>\n" +
                             "                                \n",
                     "DS_NAME=\"Radiance MDS(1)             \"\n" +
                             "DS_TYPE=M\n" +
                             "FILENAME=\"                                                              \"\n" +
                             "DS_OFFSET=+00000000000000039985<bytes>\n" +
                             "DS_SIZE=+00000000000000254815<bytes>\n" +
                             "NUM_DSR=+0000000" + ((expectedNumTpDsr - 1) * RR_FRAME_SIZE + 1) + "\n" +
                             "DSR_SIZE=+0000002255<bytes>\n" +
                             "                                \n");

        expectedNumTpDsr = 11;
        testContents(process(iis, "XYZ-2", 50, 200),
                     6040068L,
                     "XYZ-2",
                     "DS_NAME=\"Tie points ADS              \"\n" +
                             "DS_TYPE=A\n" +
                             "FILENAME=\"                                                              \"\n" +
                             "DS_OFFSET=+00000000000000011514<bytes>\n" +
                             "DS_SIZE=+00000000000000039193<bytes>\n" +
                             "NUM_DSR=+00000000"+expectedNumTpDsr+"\n" +
                             "DSR_SIZE=+0000003563<bytes>\n" +
                             "                                \n",
                     "DS_NAME=\"Radiance MDS(1)             \"\n" +
                             "DS_TYPE=M\n" +
                             "FILENAME=\"                                                              \"\n" +
                             "DS_OFFSET=+00000000000000050707<bytes>\n" +
                             "DS_SIZE=+00000000000000363055<bytes>\n" +
                             "NUM_DSR=+0000000" + ((expectedNumTpDsr-1) * RR_FRAME_SIZE + 1) + "\n" +
                             "DSR_SIZE=+0000002255<bytes>\n" +
                             "                                \n");

        // same as before, but this time on tie-point boundaries
        testContents(process(iis, "XYZ-3", 3 * 16, 13 * 16),
                     6040068L,
                     "XYZ-3",
                     "DS_NAME=\"Tie points ADS              \"\n" +
                             "DS_TYPE=A\n" +
                             "FILENAME=\"                                                              \"\n" +
                             "DS_OFFSET=+00000000000000011514<bytes>\n" +
                             "DS_SIZE=+00000000000000039193<bytes>\n" +
                             "NUM_DSR=+00000000"+expectedNumTpDsr+"\n" +
                             "DSR_SIZE=+0000003563<bytes>\n" +
                             "                                \n",
                     "DS_NAME=\"Radiance MDS(1)             \"\n" +
                             "DS_TYPE=M\n" +
                             "FILENAME=\"                                                              \"\n" +
                             "DS_OFFSET=+00000000000000050707<bytes>\n" +
                             "DS_SIZE=+00000000000000363055<bytes>\n" +
                             "NUM_DSR=+0000000" + ((expectedNumTpDsr-1) * RR_FRAME_SIZE + 1) + "\n" +
                             "DSR_SIZE=+0000002255<bytes>\n" +
                             "                                \n");

        iis.close();
    }

    private static byte[] process(ImageInputStream iis, String productName, int firstLine, int lastLine) throws IOException, ChildGenException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(16 * M);
        ImageOutputStream ios = new FileCacheImageOutputStream(os, null);

        ChildGeneratorImpl childGenerator = ChildGeneratorFactory.createChildGenerator("MER_RR");
        childGenerator.process(iis, ios, productName, firstLine, lastLine);

        ios.close();

        return os.toByteArray();
    }

    private void testContents(byte[] contents, long size, String productName, String tiePointsAdsDsd, String radianceMds1Dsd) {
        assertEquals(size, contents.length);

        assertEquals(String.format("PRODUCT=\"%-62s\"\n", productName),
                     new String(contents, 0, 73));

        assertEquals(tiePointsAdsDsd,
                     new String(contents,
                                MPH_SIZE_IN_BYTES + 1542 + 2 * DSD_SIZE, DSD_SIZE));

        assertEquals(radianceMds1Dsd,
                     new String(contents,
                                MPH_SIZE_IN_BYTES + 1542 + 3 * DSD_SIZE, DSD_SIZE));
    }
}
