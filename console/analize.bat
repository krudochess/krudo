@echo off

rem 
rem  Krudo 0.16a - a chess engine for cooks
rem  by Francesco Bianco <bianco@javanile.org>
rem

rem load config
call config.bat

rem prepare file
%GIN% %KRUDO_DIR%%KRUDO_GIN%
%GIN% %KRUDO_DIR%fruit.gin
%GIN% %KRUDO_DIR%with-fruit.gin
%GIN% %KRUDO_DIR%with-krudo.gin

rem analize with Fruit 
start %WINBOARD% @%KRUDO_DIR%\with-fruit.ini

rem analize with Krudo 
start %WINBOARD% @%KRUDO_DIR%\with-krudo.ini