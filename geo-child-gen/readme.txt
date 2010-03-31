README - version 1.7.0
~~~~~~~~~~~~~~~~~~~~~~

splitOrbit (.bat/.sh)
~~~~~~~~~~~~~~~~~~~~~
- adapted the script
    * change the JAVAHOME according to your needs
    * change CHILDGENDIR to the location where you have the zip-file extracted to
    * change RAL in the parameter list to whatever you like, it is used to set
      the PROC_CENTER in the MPH of the created products
- The scripts expects 3 parameters when it is called in the following order
    1. OUT_DIR - the path to the directory where the generated products are written to
    2. SPLIT_LINE - the line number where the product should be split
    3. INPUT_FILE - the path to the Envisat product file

