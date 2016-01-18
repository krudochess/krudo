@echo off

rem load config
call config.bat

rem prepare file
%BUILDENV% %ENGINEDIR%\%ENGINESRC% %ENGINEDIR%\%ENGINEINI%
%BUILDENV% %ENGINEDIR%\vs-frittle.src %ENGINEDIR%\vs-frittle.ini

rem clean logs
del %ENGINELOG%
rem del %ENGINEDIR%polyglot.log

rem Piriton vs. Fairymax 
%WINBOARD% @%ENGINEDIR%\vs-frittle.ini

rem post game
rem %POSTGAME% %ENGINEDIR%\vs-fairymax.pgn 
rem del %ENGINEDIR%\vs-fairymax.pgn

rem dump logs
rem type %ENGINELOG%
rem type %ENGINEDIR%polyglot.log