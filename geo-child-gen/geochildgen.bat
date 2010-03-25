@ECHO OFF

REM this software requires a JRE version 1.6 or higher

SET JAVAHOME=C:\Programme\Java\jdk1.6.0_10\bin
SET CHILDGENDIR=C:\Temp\geoChildGen

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

CALL "%JAVAEXE%" -Xms64M -Xmx512M -classpath "%CLASSPATH%" com.bc.merci.geochildgen.GeoChildGenMain %1 %2 %3 %4 %5 %6 %7 %8 %9

SET CLASSPATH=%OLD_CLASSPATH%