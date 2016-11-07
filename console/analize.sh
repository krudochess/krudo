@echo off

# 
#  Krudo 0.16a - a chess engine for cooks
#  by Francesco Bianco <bianco@javanile.org>
#

# load config
source config.sh

# generate ini file
$GIN $KRUDO_DIR/$KRUDO_GIN
$GIN $KRUDO_DIR/fruit.gin
$GIN $KRUDO_DIR/with-fruit.gin
$GIN $KRUDO_DIR/with-krudo.gin

# analize with Fruit 
$XBOARD @$KRUDO_DIR/with-fruit.ini &

# analize with Krudo 
$XBOARD @$KRUDO_DIR/with-krudo.ini