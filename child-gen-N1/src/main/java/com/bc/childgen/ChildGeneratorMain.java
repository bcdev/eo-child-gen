package com.bc.childgen;


import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Simple command line client for the  Child Generator. Usage is as follows
 * <p/>
 * <pre>java ChildGeneratorMain &lt;inputfile&gt; &lt;outputdir&gt; &lt;firstline&gt; &lt;lastline&gt;
 * &lt;originator-id&gt; &lt;filecounter&gt; &lt;invalidflag&gt;</pre>
 *
 * @author Alexander Gruenewald (alexander.gruenewald@informus.de)
 */
public class ChildGeneratorMain {

    private static final String PROGRAM_NAME = "childgen";
    private static final String VERSION_INFO = "2.4";
    private static final String COPYRIGHT_INFO = "Copyright (C) 2004-2012 by Brockmann Consult (info@brockmann-consult.de)";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        printVersionInfoTo(System.out);

        try {
            if (args.length != 6) {
                throw new IllegalArgumentException("Number of cmd-line arguments not matching");
            }

            File inputFile = new File(args[0]);
            File outputDir = new File(args[1]);

            int firstLine = Integer.parseInt(args[2]);
            int lastLine = Integer.parseInt(args[3]);

            String originatorId = args[4];

            int fileCounter = Integer.parseInt(args[5]);

            long start = System.currentTimeMillis();

            final ChildGeneratorImpl childGenerator = ChildGeneratorFactory.createChildGenerator(inputFile.getName());
            childGenerator.process(inputFile,
                                   outputDir,
                                   originatorId,
                                   fileCounter,
                                   firstLine,
                                   lastLine);

            long stop = System.currentTimeMillis();
            System.out.println("Processed in " + (stop - start) + " ms");
        } catch (Exception e) {
            System.err.println(
                    "Supported product types: \n" +
                            "  MER_RR__1P, MER_RR__2P, MER_FR__1P, MER_FR__2P, MER_FRS_1P, MER_FRS_2P, MER_FSG_1P, MER_FSG_2P\n" +
                            "  ATS_TOA_1P, ATS_NR__2P, AT1_TOA_1P, AT1_NR__2P, AT2_TOA_1P, AT2_NR__2P\n\n" +
                            "Usage: java "
                            + ChildGeneratorMain.class.getName()
                            + " <inputfile> <outputdir> <firstline> <lastline> <pkey> <filecounter>\n\n");
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    static void printVersionInfoTo(OutputStream outputStream) {
        final PrintWriter writer = new PrintWriter(outputStream);
        writer.println(PROGRAM_NAME + " - Version " + VERSION_INFO);
        writer.println(COPYRIGHT_INFO);
        writer.flush();
    }
}
