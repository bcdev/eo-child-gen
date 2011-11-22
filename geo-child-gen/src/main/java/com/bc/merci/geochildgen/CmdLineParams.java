package com.bc.merci.geochildgen;

import java.util.ArrayList;
import java.util.List;

class CmdLineParams {

    private String propertiesFileName;
    private String outputDirName;
    private boolean createChildOption;
    private List<String> inputFileNameList;
    private boolean databaseUsed;
    private String[] siteCategoryNames;
    private boolean verbose;
    private String inputSource;
    private boolean mergeIntersections;
    private boolean inputFromFile;

    CmdLineParams() {
        propertiesFileName = CmdLineConstants.PROPERTIES_FILE_NAME_DEFAULT;
        outputDirName = CmdLineConstants.OUTPUT_DIR_NAME_DEFAULT;
        createChildOption = CmdLineConstants.CREATE_CHILD_OPTION_DEFAULT;
        inputFileNameList = new ArrayList<String>();
        siteCategoryNames = new String[0];
    }

    String getInputSource() {
        return this.inputSource;
    }

    void setInputSource(final String filesFrom) {
        if (filesFrom != null) {
            this.inputSource = filesFrom;
        }
    }

    String getPropertiesFileName() {
        return propertiesFileName;
    }

    void setPropertiesFileName(final String fileName) {
        if (fileName != null) {
            propertiesFileName = fileName;
        }
    }

    String getOutputDirName() {
        return outputDirName;
    }

    void setOutputDirName(final String dirName) {
        if (dirName != null) {
            outputDirName = dirName;
        }
    }

    boolean isCreateChildOption() {
        return createChildOption;
    }

    void setCreateChildOption(boolean createChildOption) {
        this.createChildOption = createChildOption;
    }

    List<String> getInputFileNameList() {
        return inputFileNameList;
    }

    void addInputFileName(final String fileName) {
        if (fileName != null) {
            inputFileNameList.add(fileName);
        }
    }

    boolean isDatabaseUsed() {
        return databaseUsed;
    }

    void setDatabaseUsed(boolean databaseUsed) {
        this.databaseUsed = databaseUsed;
    }

    void setSiteCategoryNames(String[] siteCategoryNames) {
        this.siteCategoryNames = siteCategoryNames;
    }

    String[] getSiteCategoryNames() {
        return siteCategoryNames;
    }

    void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    boolean isVerbose() {
        return verbose;
    }

    void setMergeIntersections(boolean mergeIntersections) {
        this.mergeIntersections = mergeIntersections;
    }

    boolean isMergeIntersections() {
        return mergeIntersections;
    }

    public void setInputFromFile(boolean inputFromFile) {
        this.inputFromFile = inputFromFile;
    }

    public boolean isInputFromFile() {
        return inputFromFile;
    }
}
