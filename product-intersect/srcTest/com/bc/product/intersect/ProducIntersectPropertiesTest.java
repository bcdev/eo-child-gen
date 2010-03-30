package com.bc.product.intersect;

import junit.framework.TestCase;

public class ProducIntersectPropertiesTest extends TestCase {

    public void testSetGetGeometries() {
        final ProductIntersectProperties properties = new ProductIntersectProperties();
        final String[] geometries = new String[] {"triangle", "circle"};

        properties.setGeometry(geometries);
        assertEquals(geometries, properties.getGeometry());
    }
}
