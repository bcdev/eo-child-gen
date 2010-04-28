package com.bc.product.intersect;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;

public class ProductIntersectTest extends TestCase {

    public void testPrintUsage() {
        final String lineSep = System.getProperty("line.separator");
        final String expected = "product-intersect version 1.0.0" + lineSep + lineSep +
                "Usage: product-intersect -g <propertiesFile> [-o <outputFile>] <inputFile> ... " +
                lineSep + lineSep +
                "Options:" + lineSep +
                "  -g  - select a properties file that contains the geometries" + lineSep +
                "  -o  - select output file name (if not set the default is used 'intersections.txt')" + lineSep;

        final ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
        ProductIntersect.printUsageTo(out);

        assertEquals(expected, out.toString());
    }
}
