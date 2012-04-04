package com.bc.childgen;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;

public class ChildGeneratorMainTest extends TestCase{
    
    public void testPrintVersionInfoTo() {
        final String lineSep = System.getProperty("line.separator");
        final ByteArrayOutputStream out = new ByteArrayOutputStream(256);

        ChildGeneratorMain.printVersionInfoTo(out);

        final String output = out.toString();
        assertEquals("childgen - Version 2.4" +
                             lineSep +
                             "Copyright (C) 2004-2012 by Brockmann Consult (info@brockmann-consult.de)" +
                             lineSep, output);
    }
}
