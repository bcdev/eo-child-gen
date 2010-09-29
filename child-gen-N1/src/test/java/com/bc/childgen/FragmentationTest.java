package com.bc.childgen;

import junit.framework.TestCase;

import javax.imageio.stream.FileCacheImageInputStream;
import javax.imageio.stream.FileCacheImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FragmentationTest extends TestCase {

    private static final String N1_TEST_DATA_NAME = "MER_RR__1P.N1";

    public void testFragmentation() throws IOException, ChildGenException {
        InputStream is = getClass().getResourceAsStream(N1_TEST_DATA_NAME);
        assertNotNull("Test data not found: " + N1_TEST_DATA_NAME, is);
        ImageInputStream iis = new FileCacheImageInputStream(is, null);

        ChildGeneratorImpl childGenerator = ChildGeneratorFactory.createChildGenerator("MER_RR");
        MyFragmentHandler myFragmentHandler = new MyFragmentHandler();
        childGenerator.fragment(iis, "BC_", 16 * 3, myFragmentHandler);

        String expectedTrace =
                "beginFragment:\n" +
                "  fragmentIndex = 0\n" +
                "  productName = MER_RR__1PPBC_20090804_080334_000000082081_00207_38838_0001.N1\n" +
                "  firstLine = 0\n" +
                "  lastLine = 48\n" +
                "endFragment:\n" +
                "  fragmentIndex = 0\n" +
                "  out.length() = 1848615\n" +
                "beginFragment:\n" +
                "  fragmentIndex = 1\n" +
                "  productName = MER_RR__1PPBC_20090804_080343_000000082081_00207_38838_0002.N1\n" +
                "  firstLine = 48\n" +
                "  lastLine = 96\n" +
                "endFragment:\n" +
                "  fragmentIndex = 1\n" +
                "  out.length() = 1848615\n" +
                "beginFragment:\n" +
                "  fragmentIndex = 2\n" +
                "  productName = MER_RR__1PPBC_20090804_080351_000000082081_00207_38838_0003.N1\n" +
                "  firstLine = 96\n" +
                "  lastLine = 144\n" +
                "endFragment:\n" +
                "  fragmentIndex = 2\n" +
                "  out.length() = 1848648\n" +
                "beginFragment:\n" +
                "  fragmentIndex = 3\n" +
                "  productName = MER_RR__1PPBC_20090804_080359_000000082081_00207_38838_0004.N1\n" +
                "  firstLine = 144\n" +
                "  lastLine = 192\n" +
                "endFragment:\n" +
                "  fragmentIndex = 3\n" +
                "  out.length() = 1848615\n" +
                "beginFragment:\n" +
                "  fragmentIndex = 4\n" +
                "  productName = MER_RR__1PPBC_20090804_080408_000000082081_00207_38838_0005.N1\n" +
                "  firstLine = 192\n" +
                "  lastLine = 240\n" +
                "endFragment:\n" +
                "  fragmentIndex = 4\n" +
                "  out.length() = 1848615\n" +
                "beginFragment:\n" +
                "  fragmentIndex = 5\n" +
                "  productName = MER_RR__1PPBC_20090804_080416_000000082081_00207_38838_0006.N1\n" +
                "  firstLine = 240\n" +
                "  lastLine = 288\n" +
                "endFragment:\n" +
                "  fragmentIndex = 5\n" +
                "  out.length() = 1848648\n" +
                "beginFragment:\n" +
                "  fragmentIndex = 6\n" +
                "  productName = MER_RR__1PPBC_20090804_080425_000000032081_00207_38838_0007.N1\n" +
                "  firstLine = 288\n" +
                "  lastLine = 305\n" +
                "endFragment:\n" +
                "  fragmentIndex = 6\n" +
                "  out.length() = 688258\n";

        assertEquals(expectedTrace, myFragmentHandler.getTrace());
    }

    private static class MyFragmentHandler implements ChildGeneratorImpl.FragmentHandler {
        private static final String NL = "\n";
        private StringBuilder trace = new StringBuilder();

        String getTrace() {
            return trace.toString();
        }

        @Override
        public ImageOutputStream beginFragment(int fragmentIndex, String productName, int firstLine, int lastLine) throws IOException {
            trace.append("beginFragment:").append(NL)
                    .append("  fragmentIndex = ").append(fragmentIndex).append(NL)
                    .append("  productName = ").append(productName).append(NL)
                    .append("  firstLine = ").append(firstLine).append(NL)
                    .append("  lastLine = ").append(lastLine).append(NL);
            return new FileCacheImageOutputStream(new ByteArrayOutputStream(), null);
        }

        @Override
        public void endFragment(int fragmentIndex, ImageOutputStream out) throws IOException {
            trace.append("endFragment:").append(NL)
                    .append("  fragmentIndex = ").append(fragmentIndex).append(NL)
                    .append("  out.length() = ").append(out.length()).append(NL);
        }

        @Override
        public void handleError(int fragmentIndex, IOException e) {
            trace.append("handleError:").append(NL)
                    .append("  fragmentIndex = ").append(fragmentIndex).append(NL)
                    .append("  e.getMessage() = ").append(e.getMessage()).append(NL);
        }
    }
}
