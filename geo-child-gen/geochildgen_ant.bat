@ECHO OFF

SET JAVAHOME=E:\Programme\Java\jre1.5.0_04\bin
SET CHILDGENDIR=C:\Temp\geoChildGen

SET JAVAEXE=%JAVAHOME%\java.exe
SET LIBDIR=%CHILDGENDIR%\lib
SET OLD_CLASSPATH=%CLASSPATH%

SET CLASSPATH=%LIBDIR%\{{geochildgen-jar}};%LIBDIR%\{{beam-jar}};%LIBDIR%\{{bc-commons-jar}};%LIBDIR%\{{bc-webapp-jar}};%LIBDIR%\{{merci-childgen-jar}};%LIBDIR%\{{merci-commons-jar}};%LIBDIR%\{{jai-core-jar}};%LIBDIR%\{{apache-dbcp-jar}};%LIBDIR%\{{apache-pool-jar}};%LIBDIR%\{{jdbc-jar}};%LIBDIR%\{{apache-collections-jar}};%LIBDIR%\{{ceres-core-jar}};%LIBDIR%\{{ceres-glayer-jar}};%LIBDIR%\{{envisat-reader-jar}};%LIBDIR%\{{jama-jar}}


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