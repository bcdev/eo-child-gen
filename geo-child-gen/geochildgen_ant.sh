#!/bin/bash

export JAVAHOME=/usr/lib/j2sdk1.4.2_04/bin
export CHILDGENDIR=/home/merci/childgen

export JAVAEXE=$JAVAHOME/java
export LIBDIR=$CHILDGENDIR/lib
export OLD_CLASSPATH=$CLASSPATH

export CLASSPATH=$LIBDIR/{{geochildgen-jar}}:$LIBDIR/{{beam-jar}}:$LIBDIR/{{bc-commons-jar}}:$LIBDIR/{{bc-webapp-jar}}:$LIBDIR/{{merci-childgen-jar}}:$LIBDIR/{{merci-commons-jar}}:$LIBDIR/{{jai-core-jar}}:$LIBDIR/{{apache-dbcp-jar}}:$LIBDIR/{{apache-pool-jar}}:$LIBDIR/{{jdbc-jar}}:$LIBDIR/{{apache-collections-jar}}:$LIBDIR/{{ceres-core-jar}}:$LIBDIR/{{ceres-glayer-jar}}:$LIBDIR/{{envisat-reader-jar}}:$LIBDIR/{{jama-jar}}

$JAVAEXE -Xmx512M  -classpath "$CLASSPATH" com.bc.merci.geochildgen.GeoChildGenMain "$@"

export CLASSPATH=$OLD_CLASSPATH