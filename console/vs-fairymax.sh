#!/bin/bash

#
# Krudo 0.16a - a chess engine for cooks
# by Francesco Bianco <bianco@javanile.org>
#

# load settings
source config.sh

# generate ini files
$GIN $KRDUO_DIR/$KRDUO_GIN
$GIN $KRDUO_DIR/vs-fairymax.gin

# Piriton vs. Fairymax 
$XBOARD @$KRUDO_DIR/vs-fairymax.ini
