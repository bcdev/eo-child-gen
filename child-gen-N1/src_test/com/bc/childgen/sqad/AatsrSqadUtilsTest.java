package com.bc.childgen.sqad;

import junit.framework.TestCase;

@SuppressWarnings({"MagicNumber"})
public class AatsrSqadUtilsTest extends TestCase {

    public void testGetSQADIndex() {
        assertEquals(0, AatsrSqadUtils.getSQADIndex(0));
        assertEquals(0, AatsrSqadUtils.getSQADIndex(456));
        assertEquals(1, AatsrSqadUtils.getSQADIndex(512));
        assertEquals(1, AatsrSqadUtils.getSQADIndex(745));
        assertEquals(57, AatsrSqadUtils.getSQADIndex(29695));
        assertEquals(58, AatsrSqadUtils.getSQADIndex(29696));
        assertEquals(58, AatsrSqadUtils.getSQADIndex(29700));
    }    
}
