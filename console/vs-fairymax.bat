@echo off

rem load config
call config.bat

rem prepare file
%BUILDENV% %ENGINEDIR%\%ENGINESRC% %ENGINEDIR%\%ENGINEINI%
%BUILDENV% %ENGINEDIR%\vs-fairymax.src %ENGINEDIR%\vs-fairymax.ini

rem clean logs
del %ENGINELOG% > nul 2> null
rem del %ENGINEDIR%polyglot.log

rem Piriton vs. Fairymax 
%WINBOARD% @%ENGINEDIR%\vs-fairymax.ini

rem post game
rem %POSTGAME% %ENGINEDIR%\vs-fairymax.pgn 
rem del %ENGINEDIR%\vs-fairymax.pgn

rem dump logs
rem type %ENGINELOG%
rem type %ENGINEDIR%polyglot.log