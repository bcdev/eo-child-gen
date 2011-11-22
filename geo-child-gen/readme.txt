README - version 1.7.5
~~~~~~~~~~~~~~~~~~~~~~

Geo-Childgen is a command line tool which generates child products by using specified geometries.

INSTALLATION
~~~~~~~~~~~~
First unpack the zip-file to any destination folder.
Within the extracted folder you'll find a script named geochildgen (unix and windows). This scripts has to be adapted
to your local settings.
The JAVAHOME variable must be set to a Java installation directory.
The CHILDGENDIR variable must be set to the location where you have extracted the zip-file to.


EXECUTION
~~~~~~~~~
If the script is invoked without any parameters the following usage text is displayed.

    geochildgen version 1.7.5

    Usage: geochildgen [-g <propertiesFile>] [-d <propertiesFile>]
           [-s <cat_a, cat_b, ...>] [-c]
           [-o <outputDir>] [-m] [-v] [-f] <inputFile>...

    Options:
        -g - select to use geometry properties
             from <propertiesFile>
        -d - select to use Site geometries from database
             defined in <propertiesFile>
        -s - define site categories to be used as comma separated
             list of category name. Use together with -d option.
        -c - select to create a child product in <outputDir>.
             If not set, intersecting products are copied.
        -o - defines the <outputDir>.
             If not set, current directory is used.
        -m - select to merge geometries in case of multiple intersections.
             If not set a subset will be generated for each intersection
        -f - switch to use a textfile with filepaths as input.
             If set, <inputFile> must be a textfile. Please refer to
             'inputFiles_example.txt' for the format definition.
        -v - set program to verbose logging.

One ore more input files can be specified on the command line. The file paths must be space separated.
Example:
    ./geochildgen.sh -g geochildgen.properties -c -o /data/childProducts /data/input/MER_RR_L1_xxx1.N1 /data/input/MER_RR_L1_xxx2.N1

The properties file (geochildgen.properties) shipped with this delivery can be used as an example. The available
properties are described within the file.

When using the "-f" option the input files are described in a text file. These descriptions allow various wildcard
search options. Please refer to the example file (inputFiles_example.txt) for a detailed description.
