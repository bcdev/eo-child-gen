package com.bc.childgen;


import java.awt.*;
import java.io.File;

/**
 * Simple command line client for the  Child Generator. Usage is as follows
 * <p/>
 * <pre>java ChildGeneratorMain &lt;inputfile&gt; &lt;outputdir&gt; &lt;firstline&gt; &lt;lastline&gt;
 * &lt;originator-id&gt; &lt;filecounter&gt; &lt;invalidflag&gt;</pre>
 *
 * @author Alexander Gruenewald (alexander.gruenewald@informus.de)
 */
public class ChildGeneratorMain {

    public static final String PROGRAM_NAME = "childgen";
    public static final String VERSION_INFO = "1.6";
    public static final String COPYRIGHT_INFO = "Copyright (C) 2004-2010 by Brockmann Consult (info@brockmann-consult.de)";
    private static final int IGNORED_NUM_COLUMNS = 1121;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(PROGRAM_NAME + " - Version " + VERSION_INFO);
        System.out.println(COPYRIGHT_INFO);

        try {
            File inputFile = new File(args[0]);
            File outputDir = new File(args[1]);

            int firstLine = Integer.parseInt(args[2]);
            int lastLine = Integer.parseInt(args[3]);

            String originatorId = args[4];

            int fileCounter = Integer.parseInt(args[5]);

            long start = System.currentTimeMillis();

            ChildGeneratorImpl childGenerator = ChildGeneratorFactory.createChildGenerator(inputFile.getName());
            int numLines = lastLine - firstLine + 1;
            // note width is not evaluated so we set anything here (except 0 !)
            Rectangle region = new Rectangle(0, firstLine, IGNORED_NUM_COLUMNS, numLines);
            childGenerator.process(inputFile,
                                   outputDir,
                                   originatorId,
                                   fileCounter,
                                   region);
            
            long stop = System.currentTimeMillis();
            System.out.println("Processed in " + (stop - start) + " ms");
        } catch (Exception e) {

            System.err.println(
                    "Supported product types: \n" +
                            "  MER_RR__1P, MER_RR__2P, MER_FR__1P, MER_FR__2P,\n" +
                            "  ATS_TOA_1P, ATS_NR__2P, AT1_TOA_1P, AT1_NR__2P, AT2_TOA_1P, AT2_NR__2P\n\n" +
                            "Usage: java "
                            + ChildGeneratorMain.class.getName()
                            + " <inputfile> <outputdir> <firstline> <lastline> <pkey> <filecounter>\n\n");
            e.printStackTrace();
        }
    }
}
