#!/bin/bash

# this software requires a JRE version 1.6 or higher
export JAVAHOME=/usr/lib/j2sdk1.4.2_04/bin
export CHILDGENDIR=/home/merci/childgen

export JAVAEXE=$JAVAHOME/java
export LIBDIR=$CHILDGENDIR/lib
export OLD_CLASSPATH=$CLASSPATH

export CLASSPATH=$LIBDIR/*

$JAVAEXE -Xmx512M  -classpath "$CLASSPATH" com.bc.merci.geochildgen.GeoChildGenMain "$@"

export CLASSPATH=$OLD_CLASSPATH