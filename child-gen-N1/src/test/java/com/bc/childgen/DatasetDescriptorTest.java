package com.bc.childgen;

import junit.framework.TestCase;

@SuppressWarnings({"MagicNumber"})
public class DatasetDescriptorTest extends TestCase {

    public void testDefaultConstructor() {
        assertFalse(dsd.isSpare());
    }

    public void testSetGetIsSpare() {
        dsd.setSpare(true);
        assertTrue(dsd.isSpare());

        dsd.setSpare(false);
        assertFalse(dsd.isSpare());
    }

    public void testSetGetDsType() {
        final char type_1 = 'B';
        final char type_2 = 'F';

        dsd.setDsType(type_1);
        assertEquals(type_1, dsd.getDsType());

        dsd.setDsType(type_2);
        assertEquals(type_2, dsd.getDsType());
    }

    public void testSetGetDsSize() {
        final int size_1 = 34;
        final int size_2 = 2006;

        dsd.setDsSize(size_1);
        assertEquals(size_1, dsd.getDsSize());

        dsd.setDsSize(size_2);
        assertEquals(size_2, dsd.getDsSize());
    }

    public void testSetGetDsOffset() {
        final long offset_1 = 33655671234L;
        final long offset_2 = 883463L;

        dsd.setDsOffset(offset_1);
        assertEquals(offset_1, dsd.getDsOffset());

        dsd.setDsOffset(offset_2);
        assertEquals(offset_2, dsd.getDsOffset());
    }

    public void testSetGetSphBytePosition() {
        final long position_1 = 63955671234L;
        final long position_2 = 5523463L;

        dsd.setSphBytePosition(position_1);
        assertEquals(position_1, dsd.getSphBytePosition());

        dsd.setSphBytePosition(position_2);
        assertEquals(position_2, dsd.getSphBytePosition());
    }

    public void testSetGetNumDsr() {
        final int num_1 = 77;
        final int num_2 = 19;

        dsd.setNumDsr(num_1);
        assertEquals(num_1, dsd.getNumDsr());

        dsd.setNumDsr(num_2);
        assertEquals(num_2, dsd.getNumDsr());
    }

    public void testSetGetDsrSize() {
        final int size_1 = 33;
        final int size_2 = 82;

        dsd.setDsrSize(size_1);
        assertEquals(size_1, dsd.getDsrSize());

        dsd.setDsrSize(size_2);
        assertEquals(size_2, dsd.getDsrSize());
    }

    public void testSetGetDsName() {
        final String name_1 = "blabla";
        final String name_2 = "huhukuckuck";

        dsd.setDsName(name_1);
        assertEquals(name_1, dsd.getDsName());

        dsd.setDsName(name_2);
        assertEquals(name_2, dsd.getDsName());
    }

    public void testSetGetFilename() {
        final String name_1 = "myFile.de";
        final String name_2 = "yourFile.com";

        dsd.setFilename(name_1);
        assertEquals(name_1, dsd.getFilename());

        dsd.setFilename(name_2);
        assertEquals(name_2, dsd.getFilename());
    }

    public void testCopyConstruction() {
        final String dsName = "test_me";
        final long dsOffset = 6634;
        final int dsrSize = 662;
        final int dsSize = 3104;
        final String filename = "file - popeil";
        final int numDsr = 5;
        final int sphBytePosition = 613285;

        dsd.setDsName(dsName);
        dsd.setDsOffset(dsOffset);
        dsd.setDsrSize(dsrSize);
        dsd.setDsSize(dsSize);
        dsd.setFilename(filename);
        dsd.setNumDsr(numDsr);
        dsd.setSpare(true);
        dsd.setSphBytePosition(sphBytePosition);

        final DatasetDescriptor target = new DatasetDescriptor(dsd);
        assertEquals(dsName, target.getDsName());
        assertEquals(dsOffset, target.getDsOffset());
        assertEquals(dsrSize, target.getDsrSize());
        assertEquals(dsSize, target.getDsSize());
        assertEquals(filename, target.getFilename());
        assertEquals(numDsr, target.getNumDsr());
        assertTrue(target.isSpare());
        assertEquals(sphBytePosition, target.getSphBytePosition());
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private DatasetDescriptor dsd;

    protected void setUp() {
        dsd = new DatasetDescriptor();
    }
}
