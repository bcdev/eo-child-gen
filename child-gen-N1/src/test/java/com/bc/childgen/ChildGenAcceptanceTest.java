package com.bc.childgen;


import junit.framework.TestCase;

/*
import com.bc.util.product.ChildGenerator;
import org.esa.beam.framework.dataio.ProductIO;
import org.esa.beam.framework.dataio.ProductSubsetDef;
import org.esa.beam.framework.datamodel.Product;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
*/

@SuppressWarnings({"MagicNumber"})
public class ChildGenAcceptanceTest extends TestCase {
    public void testNothing() {}
/*

    public void testCreateChild_MER_RR__1P() throws IOException, ChildGenException {
        if (TRUE.equals(System.getProperty("noiotests"))) {
            System.out.println("ChildGenAcceptanceTest.testCreateChild_MER_RR__1P SUPPRESSED");
            return;
        }

        final File testInputFile = getTestInputFile("MER_RR__1PNMAP20060509_092342_000000562047_00308_21905_0001.N1");

        final ChildGeneratorImpl childGenerator = ChildGeneratorFactory.createChildGenerator(testInputFile.getName());
        final ProductSubsetDef subsetInfo = new ProductSubsetDef();
        subsetInfo.setRegion(0, 64, 1121, 267);
        childGenerator.process(testInputFile, testOutDir, subsetInfo, "TOM", 345, 1);

        final File targetProduct = childGenerator.getTargetProduct();
        assertNotNull(targetProduct);

        Product childProduct = null;
        try {
            childProduct = ProductIO.readProduct(targetProduct, null);

            MerisProductTester.test_RR_1P_Basics(childProduct);
            MerisProductTester.test_RR_1P_BandNames(childProduct);
            MerisProductTester.test_RR_1P_BandValues(childProduct);

            MerisProductTester.testTiePointNames(childProduct);
            MerisProductTester.test_RR_1P_TiePointValues(childProduct);
            MerisProductTester.test_RR_1P_GeoCoding(childProduct);

            MerisProductTester.test_RR_1P_MPH(childProduct);
            MerisProductTester.test_RR_1P_SPH(childProduct);
            MerisProductTester.test_RR_1P_DSDs(childProduct);
        } finally {
            if (childProduct != null) {
                childProduct.closeIO();
            }
            targetProduct.delete();
        }
    }

    public void testCreateChild_MER_FR__2P() throws IOException, ChildGenException {
        if (TRUE.equals(System.getProperty("noiotests"))) {
            System.out.println("ChildGenAcceptanceTest.testCreateChild_MER_FR__2P SUPPRESSED");
            return;
        }
        final File testInputFile = getTestInputFile("MER_FR__2PNDPA20050417_101956_000000982036_00280_16366_6386.N1");
        final ChildGenerator childGenerator = ChildGeneratorFactory.createChildGenerator(testInputFile.getName());

        final ProductSubsetDef subsetInfo = new ProductSubsetDef();
        subsetInfo.setRegion(0, 1600, 2241, 267);
        childGenerator.process(testInputFile, testOutDir, subsetInfo, "TOM", 976, 1);

        final File targetProduct = childGenerator.getTargetProduct();
        assertNotNull(targetProduct);

        Product childProduct = null;
        try {
            childProduct = ProductIO.readProduct(targetProduct, null);

            MerisProductTester.test_FR_2P_Basics(childProduct);
            MerisProductTester.test_FR_2P_BandNames(childProduct);
            MerisProductTester.test_FR_2P_BandValues(childProduct);

            MerisProductTester.testTiePointNames(childProduct);
            MerisProductTester.test_FR_2P_TiePointValues(childProduct);
            MerisProductTester.test_FR_2P_GeoCoding(childProduct);

            MerisProductTester.test_FR_2P_MPH(childProduct);
            MerisProductTester.test_FR_2P_SPH(childProduct);
            MerisProductTester.test_FR_2P_DSDs(childProduct);

            MerisProductTester.test_FR_2P_SQADs(childProduct);
        } finally {
            if (childProduct != null) {
                childProduct.closeIO();
            }
            targetProduct.delete();
        }
    }

    public void testCreateChild_ATS_TOA_1P() throws ChildGenException, IOException {
        if (TRUE.equals(System.getProperty("noiotests"))) {
            System.out.println("ChildGenAcceptanceTest.testCreateChild_ATS_TOA_1P SUPPRESSED");
            return;
        }

        final File testInputFile = getTestInputFile("ATS_TOA_1PPLRA20070110_181252_000065272054_00328_25432_6605.N1");
        final ChildGenerator childGenerator = ChildGeneratorFactory.createChildGenerator(testInputFile.getName());

        final ProductSubsetDef subsetInfo = new ProductSubsetDef();
        subsetInfo.setRegion(0, 33705, 512, 295);
        childGenerator.process(testInputFile, testOutDir, subsetInfo, "MOT", 679, 1);

        final File targetProduct = childGenerator.getTargetProduct();
        assertNotNull(targetProduct);

        Product childProduct = null;
        try {
            childProduct = ProductIO.readProduct(targetProduct, null);

            AatsrProductTester.test_ATS_TOA_1P_Basics(childProduct);
            AatsrProductTester.test_TOA_1P_BandNames(childProduct);
            AatsrProductTester.test_ATS_TOA_1P_BandValues(childProduct);

            AatsrProductTester.test_TiePointNames(childProduct);
            AatsrProductTester.test_ATS_TOA_1P_TiePointValues(childProduct);
            AatsrProductTester.test_ATS_TOA_1P_GeoCoding(childProduct);

            AatsrProductTester.test_ATS_TOA_1P_MPH(childProduct);
            AatsrProductTester.test_ATS_TOA_1P_SPH(childProduct);
            AatsrProductTester.test_ATS_TOA_1P_DSDs(childProduct);

            AatsrProductTester.test_ATS_TOA_1P_SQADs(childProduct);
        } finally {
            if (childProduct != null) {
                childProduct.closeIO();
            }
            targetProduct.delete();
        }
    }

    public void testCreateChild_ATS_NR__2P() throws IOException, ChildGenException {
        if (TRUE.equals(System.getProperty("noiotests"))) {
            System.out.println("ChildGenAcceptanceTest.testCreateChild_ATS_NR__2P SUPPRESSED");
            return;
        }

        final File testInputFile = getTestInputFile("ATS_NR__2PNPDK20060329_103452_000065272046_00223_21319_0188.N1");
        final ChildGenerator childGenerator = ChildGeneratorFactory.createChildGenerator(testInputFile.getName());

        final ProductSubsetDef subsetInfo = new ProductSubsetDef();
        subsetInfo.setRegion(0, 8456, 512, 478);
        childGenerator.process(testInputFile, testOutDir, subsetInfo, "TOM", 499, 1);

        final File targetProduct = childGenerator.getTargetProduct();
        assertNotNull(targetProduct);

        Product childProduct = null;
        try {
            childProduct = ProductIO.readProduct(targetProduct, null);

            AatsrProductTester.test_ATS_NR_2P_Basics(childProduct);
            AatsrProductTester.test_NR_2P_BandNames(childProduct);
            AatsrProductTester.test_ATS_NR_2P_BandValues(childProduct);

            AatsrProductTester.test_TiePointNames(childProduct);
            AatsrProductTester.test_ATS_NR_2P_TiePointValues(childProduct);
            AatsrProductTester.test_ATS_NR_2P_GeoCoding(childProduct);

            AatsrProductTester.test_ATS_NR_2P_MPH(childProduct);
            AatsrProductTester.test_ATS_NR_2P_SPH(childProduct);
            AatsrProductTester.test_ATS_NR_2P_DSDs(childProduct);

            AatsrProductTester.test_ATS_NR_2P_SQADs(childProduct);
        } finally {
            if (childProduct != null) {
                childProduct.closeIO();
            }
            targetProduct.delete();
        }
    }

    public void testCreateChild_AT1_NR__2P() throws IOException, ChildGenException {
        if (TRUE.equals(System.getProperty("noiotests"))) {
            System.out.println("ChildGenAcceptanceTest.testCreateChild_AT1_NR__2P SUPPRESSED");
            return;
        }

        final File testInputFile = getTestInputFile("AT1_NR__2PTRAL19930614_131152_000000004013_00338_10002_0000.E1");
        final ChildGenerator childGenerator = ChildGeneratorFactory.createChildGenerator(testInputFile.getName());

        final ProductSubsetDef subsetInfo = new ProductSubsetDef();
        subsetInfo.setRegion(0, 4456, 512, 195);
        childGenerator.process(testInputFile, testOutDir, subsetInfo, "OMT", 699, 1);

        final File targetProduct = childGenerator.getTargetProduct();
        assertNotNull(targetProduct);

        Product childProduct = null;
        try {
            childProduct = ProductIO.readProduct(targetProduct, null);

            AatsrProductTester.test_AT1_NR_2P_Basics(childProduct);
            AatsrProductTester.test_NR_2P_BandNames(childProduct);
            AatsrProductTester.test_AT1_NR_2P_BandValues(childProduct);

            AatsrProductTester.test_TiePointNames(childProduct);
            AatsrProductTester.test_AT1_NR_2P_TiePointValues(childProduct);
            AatsrProductTester.test_AT1_NR_2P_GeoCoding(childProduct);

            AatsrProductTester.test_AT1_NR_2P_MPH(childProduct);
            AatsrProductTester.test_AT1_NR_2P_SPH(childProduct);
            AatsrProductTester.test_AT1_NR_2P_DSDs(childProduct);

            AatsrProductTester.test_AT1_NR_2P_SQADs(childProduct);
        } finally {
            if (childProduct != null) {
                childProduct.closeIO();
            }
            targetProduct.delete();
        }
    }

    public void testCreateChild_AT1_TOA_1P() throws IOException, ChildGenException {
        if (TRUE.equals(System.getProperty("noiotests"))) {
            System.out.println("ChildGenAcceptanceTest.testCreateChild_AT1_TOA_1P SUPPRESSED");
            return;
        }

        final File testInputFile = getTestInputFile("AT1_TOA_1PTRAL19950714_120848_000000008004_00366_20895_0000.E1");
        final ChildGenerator childGenerator = ChildGeneratorFactory.createChildGenerator(testInputFile.getName());

        final ProductSubsetDef subsetInfo = new ProductSubsetDef();
        subsetInfo.setRegion(0, 22856, 512, 217);
        childGenerator.process(testInputFile, testOutDir, subsetInfo, "MTO", 799, 1);

        final File targetProduct = childGenerator.getTargetProduct();
        assertNotNull(targetProduct);

        Product childProduct = null;
        try {
            childProduct = ProductIO.readProduct(targetProduct, null);

            AatsrProductTester.test_AT1_TOA_1P(childProduct);
            AatsrProductTester.test_TOA_1P_BandNames(childProduct);
            AatsrProductTester.test_AT1_TOA_1P_BandValues(childProduct);

            AatsrProductTester.test_TiePointNames(childProduct);
            AatsrProductTester.test_AT1_TOA_1P_TiePointValues(childProduct);
            AatsrProductTester.test_AT1_TOA_1P_GeoCoding(childProduct);

            AatsrProductTester.test_AT1_TOA_1P_MPH(childProduct);
            AatsrProductTester.test_AT1_TOA_1P_SPH(childProduct);
            AatsrProductTester.test_AT1_TOA_1P_DSDs(childProduct);

            AatsrProductTester.test_AT1_TOA_1P_SQADs(childProduct);
        } finally {
            if (childProduct != null) {
                childProduct.closeIO();
            }
            targetProduct.delete();
        }
    }

    public void testCreateChild_AT1_TOA_1P_AttachmentFlag() throws IOException, ChildGenException {
        if (TRUE.equals(System.getProperty("noiotests"))) {
            System.out.println("ChildGenAcceptanceTest.testCreateChild_AT1_TOA_1P_AttachmentFlag SUPPRESSED");
            return;
        }

        final File testInputFile = getTestInputFile("at1_toa_1ptral19931012_101929_000000004017_00050_11718_0000.e1");
        final ChildGenerator childGenerator = ChildGeneratorFactory.createChildGenerator(testInputFile.getName());

        final ProductSubsetDef subsetInfo = new ProductSubsetDef();
        subsetInfo.setRegion(0, 11856, 512, 217);
        childGenerator.process(testInputFile, testOutDir, subsetInfo, "OMT", 801, 1);

        final File targetProduct = childGenerator.getTargetProduct();
        assertNotNull(targetProduct);

        Product childProduct = null;
        try {
            childProduct = ProductIO.readProduct(targetProduct, null);

            AatsrProductTester.test_AT1_TOA_1P_AttachmentFlag_Basics(childProduct);
            AatsrProductTester.test_TOA_1P_AttachmentFlag_BandNames(childProduct);

            AatsrProductTester.test_TiePointNames(childProduct);
            AatsrProductTester.test_AT1_TOA_1P_AttachmentFlag_TiePointValues(childProduct);
            // @todo 2 tb/tb add more tests, see above
        } finally {
            if (childProduct != null) {
                childProduct.closeIO();
            }
            targetProduct.delete();
        }
    }

    public void testCreateChild_AT2_NR__2P() throws IOException, ChildGenException {
        if (TRUE.equals(System.getProperty("noiotests"))) {
            System.out.println("ChildGenAcceptanceTest.testCreateChild_AT2_NR__2P SUPPRESSED");
            return;
        }

        final File testInputFile = getTestInputFile("AT2_NR__2PTRAL20001107_145530_000000001058_00110_29022_0000.E2");
        final ChildGenerator childGenerator = ChildGeneratorFactory.createChildGenerator(testInputFile.getName());

        final ProductSubsetDef subsetInfo = new ProductSubsetDef();
        subsetInfo.setRegion(0, 5456, 512, 287);
        childGenerator.process(testInputFile, testOutDir, subsetInfo, "MTO", 799, 1);

        final File targetProduct = childGenerator.getTargetProduct();
        assertNotNull(targetProduct);

        Product childProduct = null;
        try {
            childProduct = ProductIO.readProduct(targetProduct, null);

            AatsrProductTester.test_AT2_NR_2P_Basics(childProduct);
            AatsrProductTester.test_NR_2P_BandNames(childProduct);
            AatsrProductTester.test_AT2_NR_2P_BandValues(childProduct);

            AatsrProductTester.test_TiePointNames(childProduct);
            AatsrProductTester.test_AT2_NR_2P_TiePointValues(childProduct);
            AatsrProductTester.test_AT2_NR_2P_GeoCoding(childProduct);

            AatsrProductTester.test_AT2_NR_2P_MPH(childProduct);
        } finally {
            if (childProduct != null) {
                childProduct.closeIO();
            }
            targetProduct.delete();
        }
    }

    public void testCreateChild_AT2_TOA_1P() throws IOException, ChildGenException {
        if (TRUE.equals(System.getProperty("noiotests"))) {
            System.out.println("ChildGenAcceptanceTest.testCreateChild_AT2_TOA_1P SUPPRESSED");
            return;
        }

        final File testInputFile = getTestInputFile("AT2_TOA_1PTRAL20001107_131453_000000001058_00109_29021_0000.E2");
        final ChildGenerator childGenerator = ChildGeneratorFactory.createChildGenerator(testInputFile.getName());

        final ProductSubsetDef subsetInfo = new ProductSubsetDef();
        subsetInfo.setRegion(0, 17856, 512, 256);
        childGenerator.process(testInputFile, testOutDir, subsetInfo, "TOM", 899, 1);

        final File targetProduct = childGenerator.getTargetProduct();
        assertNotNull(targetProduct);

        Product childProduct = null;
        try {
            childProduct = ProductIO.readProduct(targetProduct, null);

            AatsrProductTester.test_AT2_TOA_1P_Basics(childProduct);
            AatsrProductTester.test_TOA_1P_BandNames(childProduct);
            AatsrProductTester.test_AT2_TOA_1P_BandValues(childProduct);

            AatsrProductTester.test_TiePointNames(childProduct);
            AatsrProductTester.test_AT2_TOA_1P_TiePointValues(childProduct);
            AatsrProductTester.test_AT2_TOA_1P_GeoCoding(childProduct);

            AatsrProductTester.test_AT2_TOA_1P_MPH(childProduct);
        } finally {
            if (childProduct != null) {
                childProduct.closeIO();
            }
            targetProduct.delete();
        }
    }

    public void testCreateChild_AT2_TOA_1P_invalid_DSD() throws IOException, ChildGenException {
        if (TRUE.equals(System.getProperty("noiotests"))) {
            System.out.println("ChildGenAcceptanceTest.testCreateChild_AT2_TOA_1P_invalid_DSD SUPPRESSED");
            return;
        }

        final File testInputFile = getTestInputFile("AT2_TOA_1PTRAL19970530_031700_000000001022_00146_11022_0000.E2");
        final ChildGenerator childGenerator = ChildGeneratorFactory.createChildGenerator(testInputFile.getName());

        final ProductSubsetDef subsetInfo = new ProductSubsetDef();
        subsetInfo.setRegion(0, 2766, 512, 206);
        childGenerator.process(testInputFile, testOutDir, subsetInfo, "MTO", 900, 1);

        final File targetProduct = childGenerator.getTargetProduct();
        assertNotNull(targetProduct);

        Product childProduct = null;
        try {
            childProduct = ProductIO.readProduct(targetProduct, null);

            AatsrProductTester.test_AT2_TOA_1P_invDSD_Basics(childProduct);
            AatsrProductTester.test_TOA_1P_BandNames(childProduct);
            AatsrProductTester.test_AT2_TOA_1P_invDSD_BandValues(childProduct);

            AatsrProductTester.test_TiePointNames(childProduct);
            AatsrProductTester.test_AT2_TOA_1P_invDSD_TiePointValues(childProduct);
            AatsrProductTester.test_AT2_TOA_1P_invDSD_GeoCoding(childProduct);

            AatsrProductTester.test_AT2_TOA_1P_invDSD_MPH(childProduct);
        } finally {
            if (childProduct != null) {
                childProduct.closeIO();
            }
            targetProduct.delete();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private static final String TRUE = "true";
    private File testOutDir;

    protected void setUp() throws Exception {
        testOutDir = new File("testDir");
        testOutDir.mkdirs();
    }

    protected void tearDown() throws Exception {
        if (testOutDir != null) {
            testOutDir.delete();
        }
    }

    private File getTestInputFile(String fileName) throws IOException {
        final String resourcePath = "/com/bc/childgen/testData.properties";
        final InputStream resourceAsStream = ChildGenAcceptanceTest.class.getResourceAsStream(resourcePath);
        if (resourceAsStream == null) {
            System.err.println("The requested resource is missing: " + resourcePath);
            throw new IOException();
        }
        final Properties props = new Properties();
        props.load(resourceAsStream);
        final String testdataPath = props.getProperty("testDataPath");
        final File testFile = new File(testdataPath, fileName);
        assertTrue(testFile.isFile());
        return testFile;
    }
*/
}
