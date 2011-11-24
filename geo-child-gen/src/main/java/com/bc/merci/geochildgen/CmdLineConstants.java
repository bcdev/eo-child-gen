package com.bc.merci.geochildgen;

class CmdLineConstants {

    static final String CREATE_CHILD_OPTION = "-c";
    static final String OUT_DIR_OPTION = "-o";
    static final String GEO_PROPS_OPTION = "-g";
    static final String DB_PROPS_OPTION = "-d";
    static final String SITE_CAT_OPTION = "-s";
    //The option for specifying that input filenames are read from a file instead of from the command line
    // the name "files-from" was chosen to be the same as the same option in "rsync"
    static final String FILES_FROM_OPTION = "-f";
    static final String MERGE_INTERSECTIONS_OPTION = "-m";
    static final String VERBOSE_OPTION = "-v";

    static final String PROPERTIES_FILE_NAME_DEFAULT = "";
    static final String OUTPUT_DIR_NAME_DEFAULT = ".";
    static final boolean CREATE_CHILD_OPTION_DEFAULT = false;

    static final String VERSION_STRING = "1.7.5";
}
