package com.bc.childgen.sqad;

class Sqad {

    Sqad(int bufferSize) {
        buffer = new byte[bufferSize];
    }

    public byte[] getRawData() {
        return buffer;
    }

    public void setDsrTime(String dateString) {
        System.arraycopy(dateString.getBytes(), 0, buffer, 0, DSR_TIME_LENGTH);
    }

    public void setAttachmentFlag(boolean attachmentFlag) {
        if (attachmentFlag) {
            buffer[DSR_TIME_LENGTH] = (byte) 1;
        } else {
            buffer[DSR_TIME_LENGTH] = (byte) 0;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////
    protected static final int DSR_TIME_LENGTH = 12;

    protected byte[] buffer;
}
