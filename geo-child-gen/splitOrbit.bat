@ECHO OFF

SET JAVAHOME=C:\Programme\Java\jre1.5.0_06\bin
SET CHILDGENDIR=D:\Test\geoChildGen

SET JAVAEXE=%JAVAHOME%\java.exe
SET LIBDIR=%CHILDGENDIR%\lib
SET OLD_CLASSPATH=%CLASSPATH%

SET CLASSPATH=%LIBDIR%\geochildgen.jar;%LIBDIR%\beam.jar;%LIBDIR%\bc-commons.jar;%LIBDIR%\bc-webapp.jar;%LIBDIR%\merci-childgen.jar;%LIBDIR%\merci-commons.jar;%LIBDIR%\jai_core.jar;%LIBDIR%\commons-dbcp-1.2.1.jar;%LIBDIR%\commons-pool-1.2.jar;%LIBDIR%\mysql-connector-java-3.1.12-bin.jar;%LIBDIR%\commons-collections-3.1.jar


::------------------------------------------------------------------
:: You can adjust the Java minimum and maximum heap space here.
:: Just change the Xms and Xmx options. Space is given in megabyte.
::    '-Xms64M' sets the minimum heap space to 64 megabytes
::    '-Xmx512M' sets the maximum heap space to 512 megabytes
:: If you want to get debugging messages out of REVAMP-Processor,
:: append "--debug" to the end of the following line.
::------------------------------------------------------------------

SET OUT_DIR=%1
SET SPLIT_LINE=%2
SET INPUT_FILE=%3

::                                                            <inputfile> <outputdir> <firstline> <lastline> <pkey> <filecounter> <invalidflag>
CALL "%JAVAEXE%" -Xms64M -Xmx512M -classpath "%CLASSPATH%" com.bc.merci.childgen.ChildGeneratorMain %INPUT_FILE% %OUT_DIR% 0 %SPLIT_LINE% RAL 0001 0

::                                                            <inputfile> <outputdir> <firstline> <lastline> <pkey> <filecounter> <invalidflag>
CALL "%JAVAEXE%" -Xms64M -Xmx512M -classpath "%CLASSPATH%" com.bc.merci.childgen.ChildGeneratorMain %INPUT_FILE% %OUT_DIR% %SPLIT_LINE% 100000 RAL 0001 0

SET CLASSPATH=%OLD_CLASSPATH%