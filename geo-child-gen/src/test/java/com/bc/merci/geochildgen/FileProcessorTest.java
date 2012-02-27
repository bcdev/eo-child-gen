package com.bc.merci.geochildgen;

import com.bc.util.geom.SubsetRange;
import junit.framework.TestCase;

public class FileProcessorTest extends TestCase {

    public void testMergeIntersectionRanges_noRanges() {
        final SubsetRange[] noRanges = new SubsetRange[0];

        final SubsetRange[] merged = FileProcessor.mergeIntersectionRanges(noRanges);
        assertEquals(0, merged.length);
    }

    public void testMergeIntersectionRanges_oneRange() {
        final SubsetRange[] oneRange = new SubsetRange[1];
        oneRange[0] = new SubsetRange(100, 200);

        final SubsetRange[] merged = FileProcessor.mergeIntersectionRanges(oneRange);
        assertEquals(1, merged.length);
        final SubsetRange range = merged[0];
        assertEquals(100, range.getMin(), 1e-6);
        assertEquals(200, range.getMax(), 1e-6);
    }

    public void testMergeIntersectionRanges_twoRanges() {
        final SubsetRange[] twoRanges = new SubsetRange[2];
        twoRanges[0] = new SubsetRange(100, 200);
        twoRanges[1] = new SubsetRange(150, 250);

        final SubsetRange[] merged = FileProcessor.mergeIntersectionRanges(twoRanges);
        assertEquals(1, merged.length);
        final SubsetRange range = merged[0];
        assertEquals(100, range.getMin(), 1e-6);
        assertEquals(250, range.getMax(), 1e-6);
    }

    public void testMergeIntersectionRanges_threeRanges() {
        final SubsetRange[] threeRanges = new SubsetRange[3];
        threeRanges[0] = new SubsetRange(100, 200);
        threeRanges[1] = new SubsetRange(150, 250);
        threeRanges[2] = new SubsetRange(618, 1044);

        final SubsetRange[] merged = FileProcessor.mergeIntersectionRanges(threeRanges);
        assertEquals(1, merged.length);
        final SubsetRange range = merged[0];
        assertEquals(100, range.getMin(), 1e-6);
        assertEquals(1044, range.getMax(), 1e-6);
    }

    public void testMergeIntersectionRanges_threeRanges_mixedPositions() {
        final SubsetRange[] threeRanges = new SubsetRange[3];
        threeRanges[0] = new SubsetRange(150, 250);
        threeRanges[1] = new SubsetRange(618, 1044);
        threeRanges[2] = new SubsetRange(100, 200);

        final SubsetRange[] merged = FileProcessor.mergeIntersectionRanges(threeRanges);
        assertEquals(1, merged.length);
        final SubsetRange range = merged[0];
        assertEquals(100, range.getMin(), 1e-6);
        assertEquals(1044, range.getMax(), 1e-6);
    }
}
