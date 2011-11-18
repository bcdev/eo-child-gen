package com.bc.merci.geochildgen;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class InputFileParserTest extends TestCase {

    private InputFileParser parser;

    public void testParseEmptyInputFile() throws IOException {
        final InputStream inputStream = getInputStream(EMPTY_INPUT_FILE);

        final List<String> inputExpressions = parser.parse(inputStream);
        assertEquals(0, inputExpressions.size());
    }


    public void testParseInputFilesWithOnlyComments() throws IOException {
        final InputStream inputStream = getInputStream(ONLY_COMMENT_INPUT_FILE);

        final List<String> inputExpressions = parser.parse(inputStream);
        assertEquals(0, inputExpressions.size());
    }

    public void testParseInputFilesWithOnlyValidLines() throws IOException {
        final InputStream inputStream = getInputStream(ONLY_VALID_INPUT_FILE);

        final List<String> inputExpressions = parser.parse(inputStream);
        assertEquals(2, inputExpressions.size());

        assertEquals("Line one", inputExpressions.get(0));
        assertEquals("and Line TWO", inputExpressions.get(1));
    }

    public void testParseInputFilesWithMixedContent() throws IOException {
        final InputStream inputStream = getInputStream(MIXED_CONTENT_INPUT_FILE);

        final List<String> inputExpressions = parser.parse(inputStream);
        assertEquals(2, inputExpressions.size());

        assertEquals("First line", inputExpressions.get(0));
        assertEquals("third line", inputExpressions.get(1));
    }

    public void testParseInputFilesWithMixedContent_withEmptyLines() throws IOException {
        final InputStream inputStream = getInputStream(MIXED_CONTENT_EMPTY_LINES_INPUT_FILE);

        final List<String> inputExpressions = parser.parse(inputStream);
        assertEquals(2, inputExpressions.size());

        assertEquals("First line", inputExpressions.get(0));
        assertEquals("third line", inputExpressions.get(1));
    }

    public void testParseLinesEndingWithComments() throws IOException {
        final InputStream inputStream = getInputStream(ENDING_COMMENT_INPUT_FILE);

        final List<String> inputExpressions = parser.parse(inputStream);
        assertEquals(3, inputExpressions.size());

        assertEquals("valid data", inputExpressions.get(0));
        assertEquals("more valid info", inputExpressions.get(1));
        assertEquals("infopopinfo", inputExpressions.get(2));
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private final static String EMPTY_INPUT_FILE = "";

    private final static String ONLY_COMMENT_INPUT_FILE = "# comment one\n" +
            "#comment two\n" +
            "# and number THREEE!";

    private final static String ONLY_VALID_INPUT_FILE = "Line one\n" +
            "and Line TWO";

    private final static String MIXED_CONTENT_INPUT_FILE = "First line\n" +
            "# this a stupid comment\n" +
            "third line\n" +
            "# followed by more commented stuff";

     private final static String MIXED_CONTENT_EMPTY_LINES_INPUT_FILE = "First line\n" +
            "# this a stupid comment\n" +
             "\n" +
            "third line\n" +
             "\n" +
            "# followed by more commented stuff";

    private final static String ENDING_COMMENT_INPUT_FILE = "valid data # comment one\n" +
            "more valid info # comment two\n" +
            "infopopinfo  # and number THREEE!";

    @Override
    protected void setUp() {
        parser = new InputFileParser();
    }

    private InputStream getInputStream(String emptyInputFile) {
        return new ByteArrayInputStream(emptyInputFile.getBytes());
    }
}
