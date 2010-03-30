package com.bc.product.intersect;

import java.util.ArrayList;
import java.util.List;

public class CmdLineParams {
    private String propertiesFileName;
    private String outputFileName;
    private List<String> inputFileNames;
    private boolean printUsage;

    public CmdLineParams() {
        propertiesFileName = "";
        outputFileName = "intersections.txt";
        inputFileNames = new ArrayList<String>();

    }

    public String getPropertiesFileName() {
        return propertiesFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public List<String> getInputFileNames() {
        return inputFileNames;
    }

    public boolean isPrintUsage() {
        return printUsage;
    }

    public void parse(String[] cmdLine) {
        printUsage = cmdLine == null || cmdLine.length == 0;
        if (isPrintUsage()) {
            return;
        }

        //noinspection ConstantConditions
        for (int i = 0; i < cmdLine.length; ++i) {
            final String current_arg = cmdLine[i];
            if ("-g".equals(current_arg)) {
                ++i;
                if (i < cmdLine.length) {
                    propertiesFileName = cmdLine[i];
                } else {
                    throw new IllegalStateException("argument expected after '-g'");
                }
            } else if ("-o".equals(current_arg)) {
                ++i;
                if (i < cmdLine.length) {
                    outputFileName = cmdLine[i];
                } else {
                    throw new IllegalStateException("argument expected after '-o'");
                }
            } else {
                inputFileNames.add(current_arg);
            }
        }
    }
}
