package com.bc.merci.geochildgen;

import junit.framework.TestCase;
import org.esa.beam.util.math.Range;

public class FileProcessorTest extends TestCase{

    public void testMergeIntersectionRanges_noRanges() {
        final Range[] noRanges = new Range[0];

        final Range[] merged = FileProcessor.mergeIntersectionRanges(noRanges);
        assertEquals(0, merged.length);
    }

    public void testMergeIntersectionRanges_oneRange() {
        final Range[] oneRange = new Range[1];
        oneRange[0] = new Range(100, 200);

        final Range[] merged = FileProcessor.mergeIntersectionRanges(oneRange);
        assertEquals(1, merged.length);
        final Range range = merged[0];
        assertEquals(100, range.getMin(), 1e-6);
        assertEquals(200, range.getMax(), 1e-6);
    }

    public void testMergeIntersectionRanges_twoRanges() {
        final Range[] twoRanges = new Range[2];
        twoRanges[0] = new Range(100, 200);
        twoRanges[1] = new Range(150, 250);

        final Range[] merged = FileProcessor.mergeIntersectionRanges(twoRanges);
        assertEquals(1, merged.length);
        final Range range = merged[0];
        assertEquals(100, range.getMin(), 1e-6);
        assertEquals(250, range.getMax(), 1e-6);
    }

    public void testMergeIntersectionRanges_threeRanges() {
        final Range[] threeRanges = new Range[3];
        threeRanges[0] = new Range(100, 200);
        threeRanges[1] = new Range(150, 250);
        threeRanges[2] = new Range(618, 1044);

        final Range[] merged = FileProcessor.mergeIntersectionRanges(threeRanges);
        assertEquals(1, merged.length);
        final Range range = merged[0];
        assertEquals(100, range.getMin(), 1e-6);
        assertEquals(1044, range.getMax(), 1e-6);
    }

    public void testMergeIntersectionRanges_threeRanges_mixedPositions() {
        final Range[] threeRanges = new Range[3];
        threeRanges[0] = new Range(150, 250);
        threeRanges[1] = new Range(618, 1044);
        threeRanges[2] = new Range(100, 200);

        final Range[] merged = FileProcessor.mergeIntersectionRanges(threeRanges);
        assertEquals(1, merged.length);
        final Range range = merged[0];
        assertEquals(100, range.getMin(), 1e-6);
        assertEquals(1044, range.getMax(), 1e-6);
    }
}
