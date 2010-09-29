package com.bc.childgen.modules;

import junit.framework.TestCase;

public class MdsrLineMapTest extends TestCase {

    public void testDefaultMap() {
        assertEquals(12, map.getMdsrIndex(12));
        assertEquals(1765, map.getMdsrIndex(1765));
        assertEquals(41905, map.getMdsrIndex(41905));
    }

    public void testMapWithMappingInfo() {
        final int[] mdsMap = new int[]{8, 9, 12, 13, 17};

        map.setMappingInfo(mdsMap);

        assertEquals(8, map.getMdsrIndex(0));
        assertEquals(9, map.getMdsrIndex(1));
        assertEquals(17, map.getMdsrIndex(4));

        assertEquals(-1, map.getMdsrIndex(-1));
        assertEquals(-1, map.getMdsrIndex(5));
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private MdsrLineMap map;
    
    protected void setUp() {
        map = new MdsrLineMap();
    }
}
