package com.bc.product.intersect;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

public class ProductIntersect {

    public void run(CmdLineParams params) {
        final String propertiesFileName = params.getPropertiesFileName();
        final File propertiesFile = new File(propertiesFileName);
        if (!propertiesFile.isFile()) {
            throw new IllegalStateException("Properties file '" + propertiesFileName + "' does not exist");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    static void printUsageTo(OutputStream out) {
        final PrintStream stream = new PrintStream(out);

        stream.print("product-intersect version ");
        stream.println("1.0.0");
        stream.println();
        stream.print("Usage: product-intersect ");
        stream.print("-g");
        stream.print(" <propertiesFile> ");
        stream.print("[");
        stream.print("-o");
        stream.print(" <outputFile>] ");
        stream.println("<inputFile> ... ");
        stream.println();
        stream.println("Options:");
        stream.println("  -g  - select a properties file that contains the geometries");
        stream.println("  -o  - select output file name (if not set the default is used 'intersections.txt')");
    }
}
