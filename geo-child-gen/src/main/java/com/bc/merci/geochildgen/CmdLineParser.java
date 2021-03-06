package com.bc.merci.geochildgen;

import org.esa.snap.core.util.StringUtils;

class CmdLineParser {

    static CmdLineParams parse(String[] args) throws IllegalArgumentException {
        final CmdLineParams params = new CmdLineParams();

        if (args == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < args.length; ++i) {
            final String current_arg = args[i];
            if (CmdLineConstants.CREATE_CHILD_OPTION.equalsIgnoreCase(current_arg)) {
                params.setCreateChildOption(true);
            } else if (CmdLineConstants.VERBOSE_OPTION.equalsIgnoreCase(current_arg)) {
                params.setVerbose(true);
            } else if (CmdLineConstants.MERGE_INTERSECTIONS_OPTION.equalsIgnoreCase(current_arg)) {
                params.setMergeIntersections(true);
            } else if (CmdLineConstants.OUT_DIR_OPTION.equalsIgnoreCase(current_arg)) {
                i++;
                params.setOutputDirName(args[i]);
            } else if (CmdLineConstants.GEO_PROPS_OPTION.equalsIgnoreCase(current_arg)) {
                i++;
                params.setPropertiesFileName(args[i]);
                params.setDatabaseUsed(false);
            } else if (CmdLineConstants.DB_PROPS_OPTION.equalsIgnoreCase(current_arg)) {
                i++;
                params.setPropertiesFileName(args[i]);
                params.setDatabaseUsed(true);
            } else if (CmdLineConstants.SITE_CAT_OPTION.equalsIgnoreCase(current_arg)) {
                i++;
                final String categoriesCsv = args[i];
                final String[] categoryNames = StringUtils.csvToArray(categoriesCsv);
                for (int j = 0; j < categoryNames.length; j++) {
                    categoryNames[j] = categoryNames[j].trim();
                }
                params.setSiteCategoryNames(categoryNames);
            } else if (CmdLineConstants.FILES_FROM_OPTION.equalsIgnoreCase(current_arg)) {
                i++;
                if (i < args.length) {
                    params.setInputSource(args[i]);
                } else {
                    throw new IllegalArgumentException("Missing value for parameter '-f'.");
                }
                params.setInputFromFile(true);
            } else {
                params.addInputFileName(current_arg);
            }
        }


        if (params.getPropertiesFileName().equals("") ||
                params.getOutputDirName().equals("") ||
                params.getInputSource() == null && params.getInputFileNameList().isEmpty()) {
            throw new IllegalArgumentException();
        }

        return params;
    }
}
