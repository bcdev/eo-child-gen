package com.bc.product.intersect;

import junit.framework.TestCase;

import java.util.List;

public class CmdLineParamsTest extends TestCase {
    private CmdLineParams params;

    public void testConstruction() {
        assertEquals("", params.getPropertiesFileName());
        assertEquals("intersections.txt", params.getOutputFileName());
        final List<String> inputFileNames = params.getInputFileNames();
        assertNotNull(inputFileNames);
        assertEquals(0, inputFileNames.size());
        assertFalse(params.isPrintUsage());
    }

    public void testParse_emptyCmdLine() {
        params.parse(new String[]{});

        assertEquals("", params.getPropertiesFileName());
        assertEquals("intersections.txt", params.getOutputFileName());
        final List<String> inputFileNames = params.getInputFileNames();
        assertNotNull(inputFileNames);
        assertEquals(0, inputFileNames.size());
        assertTrue(params.isPrintUsage());
    }

    public void testParse_propertiesFile() {
        params.parse(new String[] {"-g" ,"input.properties"});

        assertEquals("input.properties", params.getPropertiesFileName());
        assertEquals("intersections.txt", params.getOutputFileName());
        final List<String> inputFileNames = params.getInputFileNames();
        assertNotNull(inputFileNames);
        assertEquals(0, inputFileNames.size());
        assertFalse(params.isPrintUsage());    
    }

    public void testParse_propertiesFile_missingArgument() {
        try {
            params.parse(new String[] {"-g"});
            fail("IllegalStateException expected");
        } catch (IllegalStateException expected) {
        }
    }

    public void testParse_outputFile() {
        params.parse(new String[] {"-o" ,"output.txt"});

        assertEquals("", params.getPropertiesFileName());
        assertEquals("output.txt", params.getOutputFileName());
        final List<String> inputFileNames = params.getInputFileNames();
        assertNotNull(inputFileNames);
        assertEquals(0, inputFileNames.size());
        assertFalse(params.isPrintUsage());
    }

    public void testParse_outputFile_missingArgument() {
        try {
            params.parse(new String[] {"-o"});
            fail("IllegalStateException expected");
        } catch (IllegalStateException expected) {
        }
    }

    public void testParse_inputFile() {
        params.parse(new String[] {"one" ,"two","three"});

        assertEquals("", params.getPropertiesFileName());
        assertEquals("intersections.txt", params.getOutputFileName());
        final List<String> inputFileNames = params.getInputFileNames();
        assertNotNull(inputFileNames);
        assertEquals(3, inputFileNames.size());
        assertEquals("one", inputFileNames.get(0));
        assertEquals("two", inputFileNames.get(1));
        assertEquals("three", inputFileNames.get(2));

        assertFalse(params.isPrintUsage());
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void setUp() throws Exception {
        params = new CmdLineParams();
    }
}
