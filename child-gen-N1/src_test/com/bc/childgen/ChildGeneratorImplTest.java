package com.bc.childgen;

import junit.framework.TestCase;

public class ChildGeneratorImplTest extends TestCase {

    public void testCanDoBandSubset() {
        assertFalse(impl.canDoBandSubset());
    }

     public void testCanDoLineSubset() {
        assertFalse(impl.canDoLineSubset());
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private ChildGeneratorImpl impl;

    protected void setUp() {
        impl = new ChildGeneratorImpl();
    }
}
