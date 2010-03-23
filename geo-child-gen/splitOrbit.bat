@ECHO OFF

REM this software requires a JRE version 1.6 or higher

SET JAVAHOME=C:\Programme\Java\jdk1.6.0_10\bin
SET CHILDGENDIR=D:\Test\geoChildGen

SET JAVAEXE=%JAVAHOME%\java.exe
SET LIBDIR=%CHILDGENDIR%\lib
SET OLD_CLASSPATH=%CLASSPATH%

SET CLASSPATH=%LIBDIR%/*


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