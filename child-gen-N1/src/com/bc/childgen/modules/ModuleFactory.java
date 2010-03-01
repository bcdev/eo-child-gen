package com.bc.childgen.modules;

import com.bc.childgen.ChildGenException;
import com.bc.childgen.Mph;

public class ModuleFactory {

    public static Sph getSph(Mph mph) throws ChildGenException {
        final String productFileName = mph.getProductFileName();
        final int sphSize = mph.getSphSize();
        int numDsds = mph.getNumDsds();
        final int dsdSize = mph.getDsdSize();

        if (isMerisFile(productFileName)) {
            return new MerisSph(sphSize, numDsds, dsdSize);
        } else if (isAtsrFile(productFileName)) {
            // ATSR1/2 TOA products sometimes have two spare DSD's that are not counted in the MPH!
            if (productFileName.startsWith("AT1_TOA") || productFileName.startsWith("AT2_TOA")) {
                if (isIncorrectDsdNumberProductVersion(mph.getSoftwareVer())) {
                    numDsds += 2;
                }
            }
            return new AatsrSph(sphSize, numDsds, dsdSize);
        }
        throw new ChildGenException("Unsupported product: " + productFileName);
    }

    public static Module getModule(Mph mph) throws ChildGenException {
        final String fileName = mph.getProductFileName();

        if (isMerisFile(fileName)) {
            return new MerisModule();
        }
        if (isAtsrFile(fileName)) {
            return new AatsrModule();
        }
        throw new ChildGenException("Unsupported product: " + fileName);
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    static boolean isIncorrectDsdNumberProductVersion(String versionString) {
        int versionHigh = 0;
        int versionLow = 0;
        int i;

        if (!versionString.startsWith("STEP")) {
            return true;
        }

        for (i = 0; i < versionString.length(); i++) {
            if (Character.isDigit(versionString.charAt(i))) {
                versionHigh = Integer.parseInt(String.valueOf(versionString.charAt(i)));
                break;
            }
        }

        if (i + 2 < versionString.length()) {
            versionLow = Integer.parseInt(String.valueOf(versionString.charAt(i + 2)));
        }

        return (versionHigh == 1 && versionLow < 1) || (versionHigh < 1);

    }

////////////////////////////////////////////////////////////////////////////////
/////// END OF PACKAGE
////////////////////////////////////////////////////////////////////////////////

    private static boolean isMerisFile(String productFileName) {
        return productFileName.startsWith("MER_");
    }

    private static boolean isAtsrFile(String productFileName) {
        return productFileName.startsWith("ATS_")
                || productFileName.startsWith("AT1_")
                || productFileName.startsWith("AT2_");
    }


}
