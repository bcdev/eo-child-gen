package com.bc.childgen.sqad;

public class Meris_L2_Sqad extends Sqad {

    public Meris_L2_Sqad() {
        super(SQAD_BYTE_SIZE);
    }

    public void setWaterPixelAbsorbingAerosolPercent(float percent) {
        buffer[13] = (byte) roundToInt(percent);
    }

    public void setWaterPixelPercent(float percent) {
        buffer[14] = (byte) roundToInt(percent);
    }

    public void setDDVLandPixelPercent(float percent) {
        buffer[15] = (byte) roundToInt(percent);
    }

    public void setLandPixelPercent(float percent) {
        buffer[16] = (byte) roundToInt(percent);
    }

    public void setCloudPixelPercent(float percent) {
        buffer[17] = (byte) roundToInt(percent);
    }

    public void setLowPolynomialPressurePercent(float percent) {
        buffer[18] = (byte) roundToInt(percent);
    }

     public void setLowNNPressurePercent(float percent) {
        buffer[19] = (byte) roundToInt(percent);
    }

    public void setOutOfRangeWaterVapourProcessingInputPixelPercent(float percent) {
        buffer[20] = (byte) roundToInt(percent);
    }

    public void setOutOfRangeWaterVapourProcessingOutputPixelPercent(float percent) {
        buffer[21] = (byte) roundToInt(percent);
    }

    public void setOutOfRangeCloudProcessingInputPixelPercent(float percent) {
        buffer[22] = (byte) roundToInt(percent);
    }

    public void setOutOfRangeCloudProcessingOutputPixelPercent(float percent) {
        buffer[23] = (byte) roundToInt(percent);
    }

    public void setOutOfRangeLandProcessingInputPixelPercent(float percent) {
        buffer[24] = (byte) roundToInt(percent);
    }

    public void setOutOfRangeLandProcessingOutputPixelPercent(float percent) {
        buffer[25] = (byte) roundToInt(percent);
    }

    public void setOutOfRangeWaterProcessingInputPixelPercent(float percent) {
        buffer[26] = (byte) roundToInt(percent);
    }

    public void setOutOfRangeWaterProcessingOutputPixelPercent(float percent) {
        buffer[27] = (byte) roundToInt(percent);
    }

    public void setOutOfRangeCase_1_ProcessingInputPixelPercent(float percent) {
        buffer[28] = (byte) roundToInt(percent);
    }

    public void setOutOfRangeCase_1_ProcessingOutputPixelPercent(float percent) {
        buffer[29] = (byte) roundToInt(percent);
    }

    public void setOutOfRangeCase_2_ProcessingInputPixelPercent(float percent) {
        buffer[30] = (byte) roundToInt(percent);
    }

    public void setOutOfRangeCase_2_ProcessingOutputPixelPercent(float percent) {
        buffer[31] = (byte) roundToInt(percent);
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private static final int SQAD_BYTE_SIZE = 32;

    private int roundToInt(float percent) {
        return (int) Math.floor(percent + 0.5);
    }
}
