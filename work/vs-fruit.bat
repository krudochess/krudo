@echo off

rem load config
call config.bat

rem prepare file
%BUILDENV% %ENGINEDIR%\%ENGINESRC% %ENGINEDIR%\%ENGINEINI%
%BUILDENV% %ENGINEDIR%\vs-fruit.src %ENGINEDIR%\vs-fruit.ini

rem clean logs
del %ENGINELOG%
rem del %ENGINEDIR%\polyglot.log

rem Piriton vs. Fairymax 
%WINBOARD% @%ENGINEDIR%\vs-fruit.ini

rem post game
%POSTGAME% %ENGINEDIR%\vs-fruit.pgn 
del %ENGINEDIR%\vs-fruit.pgn

rem dump logs
type %ENGINELOG%
rem type %ENGINEDIR%\polyglot.log