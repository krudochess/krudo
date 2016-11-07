#!/bin/bash

#
#  Krudo 0.16a - a chess engine for cooks
#  by Francesco Bianco <bianco@javanile.org>
#

# commands
export JAVA=java
export PYTHON=python
export XBOARD=xboard
export POLYGLOT=polyglot
export POSTGAME="$PYTHON $KRUDO_DIR/../utils/postgame.py"
export FAIRYMAX=fairymax
export FRUIT=fruit
export GIN=gin

# engine vars
export KRUDO_DIR="$( cd "$( dirname "$0" )" && pwd )"
export KRUDO_GIN="krudo.gin"
export KRUDO_INI=krudo.ini
export KRUDO_VER=0.16a
export KRUDO_IMG=$KRUDO_DIR/logo.bmp
export KRUDO_LOG=krudo.log
export KRUDO_TAG="Krudo $KRUDO_VER"
export KRUDO_CMD="$JAVA -cp $KRUDO_DIR/../build/classes org.krudo.Krudo" 