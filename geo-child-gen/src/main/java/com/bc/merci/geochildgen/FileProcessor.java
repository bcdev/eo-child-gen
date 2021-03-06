package com.bc.merci.geochildgen;

import com.bc.childgen.ChildGenException;
import com.bc.childgen.ChildGeneratorFactory;
import com.bc.childgen.ChildGeneratorImpl;
import com.bc.util.file.StreamUtils;
import com.bc.util.file.ZipUtils;
import com.bc.util.geom.Geometry;
import com.bc.util.geom.GeometryUtils;
import com.bc.util.geom.RangeConverter;
import com.bc.util.geom.SubsetRange;
import com.bc.util.product.ProductHelper;
import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.GeoCoding;
import org.esa.snap.core.datamodel.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

class FileProcessor {

    private static final int PRODUCT_BOUNDARY_STEP = 128;
    private static final int MINIMUM_NUMBER_OF_LINES = 300;

    private final CmdLineParams params;
    private final File outputDir;
    private final GeoChildGenProperties config;
    private final List<Geometry> siteGeometryList;

    FileProcessor(CmdLineParams params, File outputDir, GeoChildGenProperties config, List<Geometry> siteGeometryList) {
        this.siteGeometryList = siteGeometryList;
        this.params = params;
        this.outputDir = outputDir;
        this.config = config;
    }

    void process(String inputFileName) throws IOException, ChildGenException {
        final File inputFile = new File(inputFileName);
        if (!inputFile.isFile()) {
            System.err.println("Input file '" + inputFile.getAbsolutePath() + "' does not exist");
            return;
        }

        final Product product = ProductIO.readProduct(inputFile);
        if (product == null) {
            System.err.println("No product reader was found to open file '" + inputFile.getAbsolutePath() + "'");
            return;
        }

        final Geometry productBoundary = ProductHelper.extractGeoBoundary(product, PRODUCT_BOUNDARY_STEP);
        final GeoCoding geoCoding = product.getSceneGeoCoding();
        final int width = product.getSceneRasterWidth();
        final int height = product.getSceneRasterHeight();

        for (final Geometry siteGeometry : siteGeometryList) {
            if (contains(siteGeometry, productBoundary)) {
                final File outputFile = createTargetFile(outputDir, inputFile);
                copyProduct(inputFile, outputFile);
            } else if (isIntersection(siteGeometry, productBoundary)) {
                if (params.isCreateChildOption()) {
                    SubsetRange[] ranges = getIntersectionLineRanges(siteGeometry, productBoundary, geoCoding, width, height);
                    if (params.isMergeIntersections()) {
                        ranges = mergeIntersectionRanges(ranges);
                    }
                    for (SubsetRange range : ranges) {
                        createChildProduct(inputFile, range, outputDir, config.getChildProductOriginatorId());
                    }
                } else {
                    final File outputFile = createTargetFile(outputDir, inputFile);
                    copyProduct(inputFile, outputFile);
                }
            } else {
                if (params.isVerbose()) {
                    System.out.println("No intersections with file " + inputFileName);
                }
            }
        }

        product.dispose();
    }

    // package access for testing only tb 2011-11-17
    static SubsetRange[] mergeIntersectionRanges(SubsetRange[] ranges) {
        if (ranges.length == 0) {
            return new SubsetRange[0];
        }

        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (SubsetRange range : ranges) {
            if (range.getMin() < min) {
                min = range.getMin();
            }
            if (range.getMax() > max) {
                max = range.getMax();
            }
        }

        final SubsetRange[] result = new SubsetRange[1];
        result[0] = new SubsetRange(min, max);
        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PACKAGE LOCAL
    ////////////////////////////////////////////////////////////////////////////////

    private static boolean contains(final Geometry geometry, final Geometry geometry2) {
        final int status = GeometryUtils.performGeometryOp(GeometryUtils.GEOM_OP_CONTAINS,
                geometry, geometry2);

        return status == Geometry.TRUE;
    }

    private static File createTargetFile(File outputDir, File inputFile) throws IOException {
        final File outputFile = new File(outputDir.getAbsolutePath() + File.separator + inputFile.getName());
        if (!outputFile.createNewFile()) {
            System.err.println("File '" + outputFile.getAbsolutePath() + "' already exists or could not be created");
            return null;
        }
        return outputFile;
    }

    private static void copyProduct(File inputFile, File outputFile) throws IOException {
        final FileInputStream fis = new FileInputStream(inputFile);
        final FileOutputStream fos = new FileOutputStream(outputFile);

        try {
            StreamUtils.copyStream(fis, fos);
            System.out.println("copied product '" + inputFile.getPath() + "'");
        } catch (IOException e) {
            System.err.println("Failed to copy '" + inputFile.getAbsolutePath() +
                    "' to output directory");
        }

        fis.close();
        fos.close();
    }

    private static boolean isIntersection(final Geometry geometry, final Geometry geometry2) {
        final int status = GeometryUtils.performGeometryOp(GeometryUtils.GEOM_OP_INTERSECTS,
                geometry, geometry2);

        return status == Geometry.TRUE;
    }

    /**
     * @param siteGeometry    the geometry of the test site
     * @param productBoundary the bounding geometry of the product
     * @param geoCoding
     * @param width
     * @param height
     * @return the line ranges of the intersection
     */
    private static SubsetRange[] getIntersectionLineRanges(final Geometry siteGeometry,
                                                           final Geometry productBoundary, final GeoCoding geoCoding,
                                                           final int width, final int height) {
        final SubsetRange[] ranges = RangeConverter.getRangeFromPolygonGeometry(siteGeometry, productBoundary, geoCoding,
                width, height);

        for (SubsetRange range : ranges) {
            RangeConverter.adjustRange(range, height, MINIMUM_NUMBER_OF_LINES, range);
        }

        return ranges;
    }

    /**
     * @param productFile              the input file
     * @param range                    the line range
     * @param outputDir                the target directory
     * @param childProductOriginatorId the originator identifier
     * @throws com.bc.childgen.ChildGenException
     *          when something goes wrong
     */
    private static void createChildProduct(final File productFile, final SubsetRange range,
                                           final File outputDir, final String childProductOriginatorId) throws ChildGenException {
        final int firstLine = (int) range.getMin();
        final int lastLine = (int) range.getMax();

        final ChildGeneratorImpl childGenerator = ChildGeneratorFactory.createChildGenerator(productFile.getName());

        File expandedFile = null;

        try {
            File workFile = productFile;
            if (ZipUtils.isCompressedFileName(productFile)) {
                expandedFile = ZipUtils.unpackInTempDir(productFile);
                workFile = expandedFile;
            }

            childGenerator.process(workFile, outputDir, childProductOriginatorId, 1, firstLine, lastLine);

            System.out.println("created child product of '" + productFile.getPath() + "'");
        } catch (IOException e) {
            final File childProduct = childGenerator.getTargetProduct();
            if (childProduct != null && childProduct.exists()) {
                childProduct.delete();
            }
            System.err.println("Failed to write child product to '" + outputDir.getAbsolutePath() + "'");
            System.err.println(e.getMessage());
        } finally {
            if (expandedFile != null) {
                expandedFile.delete();
            }
        }
    }
}
