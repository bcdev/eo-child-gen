package com.bc.childgen;

import junit.framework.TestCase;

import javax.imageio.stream.FileCacheImageInputStream;
import javax.imageio.stream.FileCacheImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SliceGenerationTest extends TestCase {

    private static final String N1_TEST_DATA_NAME = "MER_RR__1P.N1";

    public void testFragmentation() throws IOException, ChildGenException {
        InputStream is = getClass().getResourceAsStream(N1_TEST_DATA_NAME);
        assertNotNull("Test data not found: " + N1_TEST_DATA_NAME, is);
        ImageInputStream iis = new FileCacheImageInputStream(is, null);

        ChildGeneratorImpl childGenerator = ChildGeneratorFactory.createChildGenerator("MER_RR");
        MySliceHandler myFragmentHandler = new MySliceHandler();
        childGenerator.slice(iis, 16 * 3, myFragmentHandler);

        String expectedTrace =
                "beginSlice:\n" +
                "  sliceIndex = 0\n" +
                "  productName = MER_RR__1PPBCM20090804_080334_000000082081_00207_38838_0001.N1\n" +
                "  firstLine = 0\n" +
                "  lastLine = 48\n" +
                "endSlice:\n" +
                "  sliceIndex = 0\n" +
                "  bytesWritten = 1848582\n" +
                "beginSlice:\n" +
                "  sliceIndex = 1\n" +
                "  productName = MER_RR__1PPBCM20090804_080343_000000082081_00207_38838_0002.N1\n" +
                "  firstLine = 48\n" +
                "  lastLine = 96\n" +
                "endSlice:\n" +
                "  sliceIndex = 1\n" +
                "  bytesWritten = 1848582\n" +
                "beginSlice:\n" +
                "  sliceIndex = 2\n" +
                "  productName = MER_RR__1PPBCM20090804_080351_000000082081_00207_38838_0003.N1\n" +
                "  firstLine = 96\n" +
                "  lastLine = 144\n" +
                "endSlice:\n" +
                "  sliceIndex = 2\n" +
                "  bytesWritten = 1848615\n" +
                "beginSlice:\n" +
                "  sliceIndex = 3\n" +
                "  productName = MER_RR__1PPBCM20090804_080359_000000082081_00207_38838_0004.N1\n" +
                "  firstLine = 144\n" +
                "  lastLine = 192\n" +
                "endSlice:\n" +
                "  sliceIndex = 3\n" +
                "  bytesWritten = 1848582\n" +
                "beginSlice:\n" +
                "  sliceIndex = 4\n" +
                "  productName = MER_RR__1PPBCM20090804_080408_000000082081_00207_38838_0005.N1\n" +
                "  firstLine = 192\n" +
                "  lastLine = 240\n" +
                "endSlice:\n" +
                "  sliceIndex = 4\n" +
                "  bytesWritten = 1848582\n" +
                "beginSlice:\n" +
                "  sliceIndex = 5\n" +
                "  productName = MER_RR__1PPBCM20090804_080416_000000082081_00207_38838_0006.N1\n" +
                "  firstLine = 240\n" +
                "  lastLine = 288\n" +
                "endSlice:\n" +
                "  sliceIndex = 5\n" +
                "  bytesWritten = 1848615\n" +
                "beginSlice:\n" +
                "  sliceIndex = 6\n" +
                "  productName = MER_RR__1PPBCM20090804_080425_000000032081_00207_38838_0007.N1\n" +
                "  firstLine = 288\n" +
                "  lastLine = 304\n" +
                "endSlice:\n" +
                "  sliceIndex = 6\n" +
                "  bytesWritten = 651024\n";

        assertEquals(expectedTrace, myFragmentHandler.getTrace());
    }

    private static class MySliceHandler implements ChildGeneratorImpl.SliceHandler {
        private static final String NL = "\n";
        private StringBuilder trace = new StringBuilder();

        String getTrace() {
            return trace.toString();
        }

        @Override
        public ImageOutputStream beginSlice(int sliceIndex, String productName, int firstLine, int lastLine) throws IOException {
            trace.append("beginSlice:").append(NL)
                    .append("  sliceIndex = ").append(sliceIndex).append(NL)
                    .append("  productName = ").append(productName).append(NL)
                    .append("  firstLine = ").append(firstLine).append(NL)
                    .append("  lastLine = ").append(lastLine).append(NL);
            return new FileCacheImageOutputStream(new ByteArrayOutputStream(), null);
        }

        @Override
        public void endSlice(int sliceIndex, String productName, long bytesWritten) throws IOException {
            trace.append("endSlice:").append(NL)
                    .append("  sliceIndex = ").append(sliceIndex).append(NL)
                    .append("  bytesWritten = ").append(bytesWritten).append(NL);
        }

        @Override
        public boolean handleError(int sliceIndex, IOException cause) {
            trace.append("handleError:").append(NL)
                    .append("  sliceIndex = ").append(sliceIndex).append(NL)
                    .append("  e.getMessage() = ").append(cause.getMessage()).append(NL);
            return true;
        }
    }
}
