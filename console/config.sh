#!/bin/bash

#
# Krudo 0.16a - a chess engine for cooks
# by Francesco Bianco <bianco@javanile.org>
#

# commands
JAVA=java
PYTHON=python
XBOARD=xboard
POLYGLOT=polyglot
POSTGAME="$PYTHON $KRUDO_DIR/../utils/postgame.py"
GIN=gin

# engine vars
KRUDO_DIR="$( cd "$( dirname "$0" )" && pwd )"
KRUDO_GIN="krudo.gin"
KRUDO_INI=krudo.ini
KRUDO_VER=0.16a
KRUDO_IMG=$KRUDO_DIR/logo.bmp
KRUDO_LOG=krudo.log
KRUDO_TAG="Krudo $KRUDO_VER"
KRUDO_CMD="$JAVA -cp $KRUDO_DIR/../build/classes org.krudo.Krudo" 

AAAA=100