package com.bc.merci.geochildgen;

import junit.framework.TestCase;

public class CmdLineConstantsTest extends TestCase {

    public void testCmdLineConstants() {
        assertEquals("-c", CmdLineConstants.CREATE_CHILD_OPTION);
        assertEquals("-o", CmdLineConstants.OUT_DIR_OPTION);
        assertEquals("-g", CmdLineConstants.GEO_PROPS_OPTION);
        assertEquals("-d", CmdLineConstants.DB_PROPS_OPTION);
        assertEquals("-s", CmdLineConstants.SITE_CAT_OPTION);

        assertEquals("", CmdLineConstants.PROPERTIES_FILE_NAME_DEFAULT);
        assertEquals(".", CmdLineConstants.OUTPUT_DIR_NAME_DEFAULT);
        assertFalse(CmdLineConstants.CREATE_CHILD_OPTION_DEFAULT);
    }

    public void testVersionConstant() {
        assertEquals("1.7.1", CmdLineConstants.VERSION_STRING);
    }
}
