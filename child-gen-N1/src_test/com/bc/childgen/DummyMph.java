package com.bc.childgen;

public class DummyMph extends Mph {

    public int getNumDsds() throws ChildGenException {
        if (patchNumDsds) {
            return 0;
        } else {
            return super.getNumDsds();
        }
    }

    public int getDsdSize() throws ChildGenException {
        return 0;
    }

    public void setPatchNumDsds(boolean patchNumDsds) {
        this.patchNumDsds = patchNumDsds;
    }

    public String getSoftwareVer() {
        return softwareVer;
    }

    public void setSoftwareVer(String softwareVer) {
        this.softwareVer = softwareVer;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private boolean patchNumDsds = true;
    private String softwareVer = "default";
}
