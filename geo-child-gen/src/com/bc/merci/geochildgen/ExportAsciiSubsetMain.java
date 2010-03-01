package com.bc.merci.geochildgen;

import org.esa.beam.dataio.ascii.AsciiProductWriterPlugIn;
import org.esa.beam.framework.dataio.ProductIO;
import org.esa.beam.framework.dataio.ProductSubsetDef;
import org.esa.beam.framework.dataio.ProductWriter;
import org.esa.beam.framework.datamodel.GeoCoding;
import org.esa.beam.framework.datamodel.GeoPos;
import org.esa.beam.framework.datamodel.PixelPos;
import org.esa.beam.framework.datamodel.Product;
import org.esa.beam.util.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ExportAsciiSubsetMain {

    public static void main(String[] args) throws IOException {
        if (args.length < 4) {
            printUsageAndExit();
        }

        final float lat = Float.parseFloat(args[0]);
        final float lon = Float.parseFloat(args[1]);
        final int width = Integer.parseInt(args[2]);
        final int halfWith = width / 2;

        for (int i = 3; i < args.length; i++) {
            final File inputFile = new File(args[i]);
            writeSubset(lat, lon, width, halfWith, inputFile);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private static void writeSubset(float lat, float lon, int width, int halfWith, File inputFile) throws IOException {
        final Product product = ProductIO.readProduct(inputFile, null);
        final GeoCoding geoCoding = product.getGeoCoding();
        final PixelPos pixelPos = geoCoding.getPixelPos(new GeoPos(lat, lon), null);
        if (pixelPos.isValid()) {
            try {
                final ProductSubsetDef def = new ProductSubsetDef("ascii");
                final Rectangle rectangle = new Rectangle((int) pixelPos.x - halfWith, (int) pixelPos.y - halfWith, width, width);
                def.setRegion(rectangle);
                final Product subset = product.createSubset(def, "ascii", null);
                final String name = FileUtils.getFilenameWithoutExtension(inputFile);

                final File outFile = new File(name + "_ascii.txt");
                outFile.createNewFile();
                final FileOutputStream fileOutputStream = new FileOutputStream(outFile);
                final ProductWriter writer = new AsciiProductWriterPlugIn().createWriterInstance();
                writer.writeProductNodes(subset, new OutputStreamWriter(fileOutputStream));
                writer.close();
            } catch (IllegalArgumentException e) {
                System.out.println("Region not completely inside product - skipped.");
            } catch(IOException e) {
                System.out.println("IOException: " + e.getMessage());                
            }
        }

        product.closeIO();
    }

    private static void printUsageAndExit() {
        System.out.println("ASCII Hack:");
        System.out.println("usage: asciiHack [lat] [lon] [width] <files>");
        System.exit(0);
    }
}
