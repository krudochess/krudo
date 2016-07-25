@echo off

rem load config
call config.bat

rem prepare file
%BUILDENV% %ENGINEDIR%\%ENGINESRC% %ENGINEDIR%\%ENGINEINI%
%BUILDENV% %ENGINEDIR%\handicap.src %ENGINEDIR%\handicap.ini

rem clean logs
del %ENGINELOG%
rem del %ENGINEDIR%\polyglot.log

rem Piriton vs. Fairymax 
%WINBOARD% @%ENGINEDIR%\handicap.ini

rem post game
rem %POSTGAME% %ENGINEDIR%\vs-fruit.pgn 
rem del %ENGINEDIR%\vs-fruit.pgn

rem dump logs
rem type %ENGINELOG%
rem type %ENGINEDIR%\polyglot.log