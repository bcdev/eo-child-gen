package com.bc.childgen.modules;

public class MdsrLineMap {

    public int getMdsrIndex(int lineNumber) {
        if (mdsMap == null) {
            return lineNumber;
        }

        if (lineNumber < 0 || lineNumber >= mdsMap.length) {
            return -1;
        }

        return mdsMap[lineNumber];
    }

    public void setMappingInfo(int[] mdsMap) {
        this.mdsMap = mdsMap;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private int[] mdsMap;
}
