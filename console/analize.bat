@echo off

rem 
rem  Krudo 0.16a - a chess engine for cooks
rem  by Francesco Bianco <bianco@javanile.org>
rem

rem load config
call config.bat

rem prepare file
%BUILDENV% %ENGINEDIR%\%ENGINESRC% %ENGINEDIR%\%ENGINEINI%
%BUILDENV% %ENGINEDIR%\with-fruit.src %ENGINEDIR%\with-fruit.ini
%BUILDENV% %ENGINEDIR%\with-krudo.src %ENGINEDIR%\with-krudo.ini

rem analize with Fruit 
start %WINBOARD% @%ENGINEDIR%\with-fruit.ini

rem analize with Krudo 
start %WINBOARD% @%ENGINEDIR%\with-krudo.ini