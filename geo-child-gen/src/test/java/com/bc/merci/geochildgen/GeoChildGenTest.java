package com.bc.merci.geochildgen;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;

public class GeoChildGenTest extends TestCase {

    public void testPrintUsage() {
        final String lineSep = System.getProperty("line.separator");
        final String expected = "geochildgen version " + CmdLineConstants.VERSION_STRING + lineSep +
                lineSep +
                "Usage: geochildgen [" + CmdLineConstants.GEO_PROPS_OPTION + " <propertiesFile>] " +
                "[" + CmdLineConstants.DB_PROPS_OPTION + " <propertiesFile>]" + lineSep +
                "       [" + CmdLineConstants.SITE_CAT_OPTION + " <cat_a, cat_b, ...>] [" + CmdLineConstants.CREATE_CHILD_OPTION + "]" + lineSep +
                "       [" + CmdLineConstants.OUT_DIR_OPTION + " <outputDir>] [" + CmdLineConstants.MERGE_INTERSECTIONS_OPTION + "] [" + CmdLineConstants.VERBOSE_OPTION + "] [" + CmdLineConstants.FILES_FROM_OPTION +
                "] <inputFile>..." + lineSep + lineSep +
                "Options:" + lineSep +
                "    " + CmdLineConstants.GEO_PROPS_OPTION + " - select to use geometry properties" + lineSep +
                "         from <propertiesFile>" + lineSep +
                "    " + CmdLineConstants.DB_PROPS_OPTION + " - select to use Site geometries from database" + lineSep +
                "         defined in <propertiesFile>" + lineSep +
                "    " + CmdLineConstants.SITE_CAT_OPTION + " - define site categories to be used as comma separated" + lineSep +
                "         list of category name. Use together with -d option." + lineSep +
                "    " + CmdLineConstants.CREATE_CHILD_OPTION + " - select to create a child product in <outputDir>." + lineSep +
                "         If not set, intersecting products are copied." + lineSep +
                "    " + CmdLineConstants.OUT_DIR_OPTION + " - defines the <outputDir>." + lineSep +
                "         If not set, current directory is used." + lineSep +
                "    " + CmdLineConstants.MERGE_INTERSECTIONS_OPTION + " - select to merge geometries in case of multiple intersections." + lineSep +
                "         If not set a subset will be generated for each intersection" + lineSep +
                "    " + CmdLineConstants.FILES_FROM_OPTION + " - switch to use a textfile with filepaths as input." + lineSep +
                "         If set, <inputFile> must be a textfile. Please refer to" + lineSep +
                "         'inputFiles_example.txt' for the format definition." + lineSep +
                "    " + CmdLineConstants.VERBOSE_OPTION + " - set program to verbose logging." + lineSep;

        final ByteArrayOutputStream out = new ByteArrayOutputStream(2048);

        GeoChildGen.printUsageTo(out);

        assertEquals(expected, out.toString());
    }
}
