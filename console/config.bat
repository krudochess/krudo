@echo off

rem common interpreters/compilers
set JAVA=java.exe
set PYTHON=C:\Python27\python.exe

rem engine settings
set ENGINEDIR=%~dp0
set ENGINESRC=Krudo.src
set ENGINEINI=Krudo.ini
set ENGINEVER=0.15a
set ENGINEIMG=%ENGINEDIR%logo.bmp
set ENGINELOG=Krudo.log
set ENGINETAG=Krudo %ENGINEVER%
set ENGINECMD=%JAVA% -cp %ENGINEDIR%..\build\classes org.krudo.Krudo 

rem chess tools
set WINBOARD=c:\WinBoard-4.7.2\WinBoard\winboard.exe
set POLYGLOT=c:\WinBoard-4.7.2\WinBoard\polyglot.exe
set BUILDENV=%PYTHON% %ENGINEDIR%\tool\buildenv.py
set POSTGAME=%PYTHON% %ENGINEDIR%\tool\postgame.py