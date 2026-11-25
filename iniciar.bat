@echo off
setlocal
cd /d "%~dp0"
chcp 65001 >nul

where javac >nul 2>&1
if errorlevel 1 (
    echo O JDK nao foi encontrado. Instale o JDK e disponibilize javac no PATH.
    pause
    exit /b 1
)

if not exist bin mkdir bin
(for %%F in (src\bolonhaemilano\*.java) do @echo src/bolonhaemilano/%%~nxF) > bin\fontes.txt
javac -encoding UTF-8 -d bin @bin/fontes.txt
if errorlevel 1 (
    pause
    exit /b 1
)

if /i "%~1"=="testes" (
    java -Dfile.encoding=UTF-8 -cp bin bolonhaemilano.Testes
) else (
    java -Dfile.encoding=UTF-8 -cp bin bolonhaemilano.Main
)
set "resultado=%ERRORLEVEL%"
if not "%resultado%"=="0" pause
exit /b %resultado%
