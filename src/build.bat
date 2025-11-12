@echo off
cd /d "%~dp0"
if not exist "..\out" mkdir "..\out"
dir /s /b *.java > sources.txt
javac -d ..\out @sources.txt && echo Build OK || echo Build FAILED
pause