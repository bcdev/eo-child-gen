package com.bc.childgen.sqad;

import junit.framework.TestCase;

@SuppressWarnings({"MagicNumber"})
public class Meris_L2_SqadTest extends TestCase {

    public void testGetRawData() {
        final byte[] buffer = sqad.getRawData();
        assertEquals(32, buffer.length);
    }

    public void testSetDsrTime() {
        final String dateString = "123456789012";
        sqad.setDsrTime(dateString);
        assertEquals(dateString, new String(sqad.getRawData(), 0, 12));
    }

    public void testSetAttachmentFlag() {
        sqad.setAttachmentFlag(true);
        assertEquals(1, (int) sqad.getRawData()[12]);

        sqad.setAttachmentFlag(false);
        assertEquals(0, (int) sqad.getRawData()[12]);
    }

    public void testSetWaterPixelAbsorbingAerosolpercent() {
        sqad.setWaterPixelAbsorbingAerosolPercent(13.65f);
        assertEquals(14, (int) sqad.getRawData()[13]);
    }

    public void testSetWaterPixelPercent() {
        sqad.setWaterPixelPercent(14.75f);
        assertEquals(15, (int) sqad.getRawData()[14]);
    }

    public void testSetDDVLandPixelPercent() {
        sqad.setDDVLandPixelPercent(15.85f);
        assertEquals(16, (int) sqad.getRawData()[15]);
    }

    public void testSetLandPixelPercent() {
        sqad.setLandPixelPercent(16.95f);
        assertEquals(17, (int) sqad.getRawData()[16]);
    }

    public void testSetCloudPixelPercent() {
        sqad.setCloudPixelPercent(18.05f);
        assertEquals(18, (int) sqad.getRawData()[17]);
    }

    public void testSetLowPolynomialPressurePixelPercent() {
        sqad.setLowPolynomialPressurePercent(19.15f);
        assertEquals(19, (int) sqad.getRawData()[18]);
    }

    public void testSetLowNNPressurePixelPercent() {
        sqad.setLowNNPressurePercent(20.25f);
        assertEquals(20, (int) sqad.getRawData()[19]);
    }

    public void testSetOutOfRangeWaterVapourProcessingInputPixelPercent() {
        sqad.setOutOfRangeWaterVapourProcessingInputPixelPercent(21.35f);
        assertEquals(21, (int) sqad.getRawData()[20]);
    }

    public void testSetOutOfRangeWaterVapourProcessingOutputPixelPercent() {
        sqad.setOutOfRangeWaterVapourProcessingOutputPixelPercent(22.45f);
        assertEquals(22, (int) sqad.getRawData()[21]);
    }

    public void testSetOutOfRangeCloudProcessingInputPixelPercent() {
        sqad.setOutOfRangeCloudProcessingInputPixelPercent(23.55f);
        assertEquals(24, (int) sqad.getRawData()[22]);
    }

    public void testSetOutOfRangeCloudProcessingOutputPixelPercent() {
        sqad.setOutOfRangeCloudProcessingOutputPixelPercent(24.65f);
        assertEquals(25, (int) sqad.getRawData()[23]);
    }

    public void testSetOutOfRangeLandProcessingInputPixelPercent() {
        sqad.setOutOfRangeLandProcessingInputPixelPercent(25.75f);
        assertEquals(26, (int) sqad.getRawData()[24]);
    }

    public void testSetOutOfRangeLandProcessingOutputPixelPercent() {
        sqad.setOutOfRangeLandProcessingOutputPixelPercent(26.85f);
        assertEquals(27, (int) sqad.getRawData()[25]);
    }

    public void testSetOutOfRangeWaterProcessingInputPixelPercent() {
        sqad.setOutOfRangeWaterProcessingInputPixelPercent(27.95f);
        assertEquals(28, (int) sqad.getRawData()[26]);
    }

    public void testSetOutOfRangeWaterProcessingOutputPixelPercent() {
        sqad.setOutOfRangeWaterProcessingOutputPixelPercent(29.05f);
        assertEquals(29, (int) sqad.getRawData()[27]);
    }

    public void testSetOutOfRangeCase_1_ProcessingInputPixelPercent() {
        sqad.setOutOfRangeCase_1_ProcessingInputPixelPercent(30.15f);
        assertEquals(30, (int) sqad.getRawData()[28]);
    }

    public void testSetOutOfRangeCase_1_ProcessingOutputPixelPercent() {
        sqad.setOutOfRangeCase_1_ProcessingOutputPixelPercent(31.25f);
        assertEquals(31, (int) sqad.getRawData()[29]);
    }

    public void testSetOutOfRangeCase_2_ProcessingInputPixelPercent() {
        sqad.setOutOfRangeCase_2_ProcessingInputPixelPercent(32.35f);
        assertEquals(32, (int) sqad.getRawData()[30]);
    }

    public void testSetOutOfRangeCase_2_ProcessingOutputPixelPercent() {
        sqad.setOutOfRangeCase_2_ProcessingOutputPixelPercent(33.45f);
        assertEquals(33, (int) sqad.getRawData()[31]);
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private Meris_L2_Sqad sqad;

    protected void setUp() {
        sqad = new Meris_L2_Sqad();
    }
}
