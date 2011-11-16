package com.bc.merci.geochildgen;

import junit.framework.TestCase;

public class CmdLineParserTest extends TestCase {

    public void testNullArgs() {
        final String[] args = null;

        try {
            CmdLineParser.parse(args);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testEmptyArgs() {
        final String[] args = {};

        try {
            CmdLineParser.parse(args);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testFilesFromOption() {
        final String[] args = {"-d", PROPERTIES_FILE_NAME,
                CmdLineConstants.OUT_DIR_OPTION, OUTPUT_DIR_NAME,
                CmdLineConstants.FILES_FROM_OPTION, INPUT_FILE_NAME};
        final CmdLineParams params = CmdLineParser.parse(args);

        assertNotNull(params);
        assertEquals(PROPERTIES_FILE_NAME, params.getPropertiesFileName());
        assertFalse(params.isCreateChildOption());
        assertEquals(OUTPUT_DIR_NAME, params.getOutputDirName());
        assertNotNull(params.getInputFileNameList());
        assertEquals(0, params.getInputFileNameList().size());
        assertTrue(params.getFilesFrom().equals(INPUT_FILE_NAME));
        assertTrue(params.isDatabaseUsed());
        assertFalse(params.isVerbose());
    }

    public void testSimpleArgs() {
        final String[] args = {"-g", PROPERTIES_FILE_NAME, INPUT_FILE_NAME};
        final CmdLineParams params = CmdLineParser.parse(args);

        assertNotNull(params);
        assertEquals(PROPERTIES_FILE_NAME, params.getPropertiesFileName());
        assertFalse(params.isCreateChildOption());
        assertEquals(CmdLineConstants.OUTPUT_DIR_NAME_DEFAULT, params.getOutputDirName());
        assertNotNull(params.getInputFileNameList());
        assertEquals(1, params.getInputFileNameList().size());
        assertTrue(params.getInputFileNameList().contains(INPUT_FILE_NAME));
        assertFalse(params.isDatabaseUsed());
        assertFalse(params.isVerbose());
    }

    public void testOutputDirOption() {
        final String[] args = {"-d", PROPERTIES_FILE_NAME,
                CmdLineConstants.OUT_DIR_OPTION, OUTPUT_DIR_NAME, INPUT_FILE_NAME};
        final CmdLineParams params = CmdLineParser.parse(args);

        assertNotNull(params);
        assertEquals(PROPERTIES_FILE_NAME, params.getPropertiesFileName());
        assertFalse(params.isCreateChildOption());
        assertEquals(OUTPUT_DIR_NAME, params.getOutputDirName());
        assertNotNull(params.getInputFileNameList());
        assertEquals(1, params.getInputFileNameList().size());
        assertTrue(params.getInputFileNameList().contains(INPUT_FILE_NAME));
        assertTrue(params.isDatabaseUsed());
        assertFalse(params.isVerbose());
    }

    public void testCreateChildOption() {
        final String[] args = {"-g", PROPERTIES_FILE_NAME,
                CmdLineConstants.CREATE_CHILD_OPTION, INPUT_FILE_NAME};
        final CmdLineParams params = CmdLineParser.parse(args);

        assertNotNull(params);
        assertEquals(PROPERTIES_FILE_NAME, params.getPropertiesFileName());
        assertTrue(params.isCreateChildOption());
        assertEquals(CmdLineConstants.OUTPUT_DIR_NAME_DEFAULT, params.getOutputDirName());
        assertNotNull(params.getInputFileNameList());
        assertEquals(1, params.getInputFileNameList().size());
        assertTrue(params.getInputFileNameList().contains(INPUT_FILE_NAME));
        assertFalse(params.isDatabaseUsed());
        assertFalse(params.isVerbose());
    }

    public void testIsDatabaseIsToggledCorrectly() {
        final String[] args_1 = {"-g", PROPERTIES_FILE_NAME, INPUT_FILE_NAME};

        CmdLineParams params = CmdLineParser.parse(args_1);
        assertFalse(params.isDatabaseUsed());
        assertEquals(PROPERTIES_FILE_NAME, params.getPropertiesFileName());

        final String[] args_2 = {"-d", PROPERTIES_FILE_NAME, INPUT_FILE_NAME};

        params = CmdLineParser.parse(args_2);
        assertTrue(params.isDatabaseUsed());
        assertEquals(PROPERTIES_FILE_NAME, params.getPropertiesFileName());
    }

    public void testParseCategoryNames_oneName() {
        final String testCategoryName = "testSiteCategory";
        final String[] args = {"-g", PROPERTIES_FILE_NAME, "-s", testCategoryName, INPUT_FILE_NAME};

        final CmdLineParams params = CmdLineParser.parse(args);
        final String[] siteCategoryNames = params.getSiteCategoryNames();
        assertEquals(1, siteCategoryNames.length);
        assertEquals(testCategoryName, siteCategoryNames[0]);
    }

    public void testParseCategoryNames_threeNames() {
        final String cat_1 = "cat_1";
        final String cat_2 = "cat with blank";
        final String cat_3 = "cat-with-dash";
        final String cat_csv = cat_1 + "," + cat_2 + " , " + cat_3;
        final String[] args = {"-g", PROPERTIES_FILE_NAME, "-s", cat_csv, INPUT_FILE_NAME};

        final CmdLineParams params = CmdLineParser.parse(args);
        final String[] siteCategoryNames = params.getSiteCategoryNames();
        assertEquals(3, siteCategoryNames.length);
        assertEquals(cat_1, siteCategoryNames[0]);
        assertEquals(cat_2, siteCategoryNames[1]);
        assertEquals(cat_3, siteCategoryNames[2]);
    }

    public void testParseVerbose() {
        final String[] args = {"-v", "-g", PROPERTIES_FILE_NAME, INPUT_FILE_NAME};

        final CmdLineParams params = CmdLineParser.parse(args);
        assertTrue(params.isVerbose());
    }

    public void testParseMergeIntersections() {
        final String[] args = {"-g", PROPERTIES_FILE_NAME, "-m", INPUT_FILE_NAME};

        final CmdLineParams params = CmdLineParser.parse(args);
        assertTrue(params.isMergeIntersections());
    }

    public void testIllegalArgs() {
        final String[] args = {CmdLineConstants.CREATE_CHILD_OPTION, INPUT_FILE_NAME};

        try {
            CmdLineParser.parse(args);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException expected) {
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private static final String PROPERTIES_FILE_NAME = "propertiesFileName";
    private static final String OUTPUT_DIR_NAME = "outputDirName";
    private static final String INPUT_FILE_NAME = "inputFileName";

}
