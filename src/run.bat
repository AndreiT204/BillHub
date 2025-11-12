@echo off
cd /d "%~dp0"
java -cp ..\out billhub.app.Main %*
pause