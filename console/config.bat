@echo off

rem 
rem  Krudo 0.16a - a chess engine for cooks
rem  by Francesco Bianco <bianco@javanile.org>
rem

rem common tools
set JAVA=C:\ProgramData\Oracle\Java\javapath\java.exe
set PYTHON=C:\Python27\python.exe
set WINBOARD=c:\WinBoard-4.7.2\WinBoard\winboard.exe
set POLYGLOT=%WINBOARD%\..\polyglot.exe
set POSTGAME=%PYTHON% %ENGINEDIR%\tool\postgame.py
set GIN=gin.exe
set FRUIT=polyglot _PG\fruit.ini

rem check java exists
where %JAVA% > nul 2> nul
if %ERRORLEVEL% NEQ 0 if not exist %JAVA% echo [ERROR] Please install Java

rem check python exists
where %PYTHON% > nul 2> nul
if %ERRORLEVEL% NEQ 0 if not exist %PYTHON% echo [ERROR] Please install Python

rem check winboard exists
where %WINBOARD% > nul 2> nul
if %ERRORLEVEL% NEQ 0 if not exist %WINBOARD% echo [ERROR] Please install WinBoard

rem engine settings
set KRUDO_DIR=%~dp0
set KRUDO_GIN=Krudo.gin
set KRUDO_INI=Krudo.ini
set KRUDO_VER=0.16a
set KRUDO_IMG=%KRUDO_DIR%logo.bmp
set KRUDO_LOG=Krudo.log
set KRUDO_TAG=Krudo %KRUDO_VER%
set KRUDO_CMD=%JAVA% -cp %ENGINEDIR%..\build\classes org.krudo.Krudo 