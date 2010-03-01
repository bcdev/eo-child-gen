package com.bc.merci.geochildgen;

import junit.framework.TestCase;

import java.util.List;


public class CmdLineParamsTest extends TestCase {

    public void testConstruction() {
        assertEquals(CmdLineConstants.PROPERTIES_FILE_NAME_DEFAULT, params.getPropertiesFileName());
        assertEquals(CmdLineConstants.OUTPUT_DIR_NAME_DEFAULT, params.getOutputDirName());
        assertFalse(params.isCreateChildOption());

        final List<String> list = params.getInputFileNameList();
        assertNotNull(list);
        assertTrue(list.isEmpty());

        assertFalse(params.isDatabaseUsed());
        final String[] siteCategoryNames = params.getSiteCategoryNames();
        assertNotNull(siteCategoryNames);
        assertEquals(0, siteCategoryNames.length);
    }

    public void testSetGetGeometryFileName() {
        final String fileName = "test";

        params.setPropertiesFileName(fileName);
        assertEquals(fileName, params.getPropertiesFileName());

        params.setPropertiesFileName(null);
        assertEquals(fileName, params.getPropertiesFileName());
    }

    public void testSetGetOutputDirName() {
        final String dirName = "test";

        params.setOutputDirName(dirName);
        assertEquals(dirName, params.getOutputDirName());

        params.setOutputDirName(null);
        assertEquals(dirName, params.getOutputDirName());
    }

    public void testSetGetCreateChildOption() {
        params.setCreateChildOption(true);
        assertTrue(params.isCreateChildOption());

        params.setCreateChildOption(false);
        assertFalse(params.isCreateChildOption());
    }

    public void testAddGetInputFileNames() {
        final String fileName = "test";

        params.addInputFileName(fileName);
        List<String> list = params.getInputFileNameList();
        assertNotNull(list);
        assertEquals(1, list.size());
        assertTrue(list.contains(fileName));

        params.addInputFileName(null);
        list = params.getInputFileNameList();
        assertNotNull(list);
        assertEquals(1, list.size());
        assertFalse(list.contains(null));
    }

    public void testSetIsDatabaseUsed() {
        params.setDatabaseUsed(true);
        assertTrue(params.isDatabaseUsed());

        params.setDatabaseUsed(false);
        assertFalse(params.isDatabaseUsed());
    }

    public void testSetGetSiteCategoryNames() {
        final String[] catNames_1 = new String[]{"aber", "dieauch"};
        final String[] catNames_2 = new String[]{"category", "imperativ"};

        params.setSiteCategoryNames(catNames_1);
        assertEquals(catNames_1, params.getSiteCategoryNames());

        params.setSiteCategoryNames(catNames_2);
        assertEquals(catNames_2, params.getSiteCategoryNames());
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private CmdLineParams params;

    protected void setUp() throws Exception {
        params = new CmdLineParams();
    }


}
