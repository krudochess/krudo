@echo off

rem load config
call config.bat

rem prepare file
%BUILDENV% %ENGINEDIR%\%ENGINESRC% %ENGINEDIR%\%ENGINEINI%
%BUILDENV% %ENGINEDIR%\vs-krudo.src %ENGINEDIR%\vs-krudo.ini

rem clean logs
del %ENGINELOG%
rem del %ENGINEDIR%polyglot.log

rem Piriton vs. Fairymax 
%WINBOARD% @%ENGINEDIR%\vs-krudo.ini

rem post game
rem %POSTGAME% %ENGINEDIR%\vs-krudo.pgn 
rem del %ENGINEDIR%\vs-krudo.pgn

rem dump logs
rem type %ENGINELOG%
rem type %ENGINEDIR%polyglot.log