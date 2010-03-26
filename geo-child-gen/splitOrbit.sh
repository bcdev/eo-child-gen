#!/bin/bash

# this software requires a JRE version 1.6 or higher
export JAVAHOME=/usr/lib/j2sdk1.4.2_04/bin
export CHILDGENDIR=/home/merci/childgen

export JAVAEXE=$JAVAHOME/java
export LIBDIR=$CHILDGENDIR/lib
export OLD_CLASSPATH=$CLASSPATH

export CLASSPATH=$LIBDIR/*

export OUT_DIR=$1
export SPLIT_LINE=$2
export INPUT_FILE=$3

#                                                            <inputfile> <outputdir> <firstline> <lastline> <pkey> <filecounter> <invalidflag>
$JAVAEXE -Xms64M -Xmx512M -classpath "$CLASSPATH" com.bc.childgen.ChildGeneratorMain $INPUT_FILE $OUT_DIR 0 $SPLIT_LINE RAL 0001 0

#                                                            <inputfile> <outputdir> <firstline> <lastline> <pkey> <filecounter> <invalidflag>
$JAVAEXE -Xms64M -Xmx512M -classpath "$CLASSPATH" com.bc.childgen.ChildGeneratorMain $INPUT_FILE $OUT_DIR $SPLIT_LINE 100000 RAL 0001 0

export CLASSPATH=$OLD_CLASSPATH