#!/bin/bash

export JAVAHOME=/usr/lib/j2sdk1.4.2_04/bin
export CHILDGENDIR=/home/merci/childgen

export JAVAEXE=$JAVAHOME/java
export LIBDIR=$CHILDGENDIR/lib
export OLD_CLASSPATH=$CLASSPATH

export CLASSPATH=$LIBDIR/geochildgen.jar:$LIBDIR/beam.jar:$LIBDIR/bc-commons.jar:$LIBDIR/bc-webapp.jar:$LIBDIR/merci-childgen.jar:$LIBDIR/merci-prodreg.jar:$LIBDIR/merci-commons.jar:$LIBDIR/jai_core.jar:$LIBDIR/commons-dbcp-1.2.1.jar:$LIBDIR/commons-pool-1.2.jar:$LIBDIR/mysql-connector-java-3.1.12-bin.jar:$LIBDIR/commons-collections-3.1.jar

export OUT_DIR=$1
export SPLIT_LINE=$2
export INPUT_FILE=$3

#                                                            <inputfile> <outputdir> <firstline> <lastline> <pkey> <filecounter> <invalidflag>
$JAVAEXE -Xms64M -Xmx512M -classpath "$CLASSPATH" com.bc.merci.childgen.ChildGeneratorMain $INPUT_FILE $OUT_DIR 0 $SPLIT_LINE RAL 0001 0

#                                                            <inputfile> <outputdir> <firstline> <lastline> <pkey> <filecounter> <invalidflag>
$JAVAEXE -Xms64M -Xmx512M -classpath "$CLASSPATH" com.bc.merci.childgen.ChildGeneratorMain $INPUT_FILE $OUT_DIR $SPLIT_LINE 100000 RAL 0001 0

export CLASSPATH=$OLD_CLASSPATH