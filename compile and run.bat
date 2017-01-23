@echo off
set PATH=%PATH%;"F:\Program Files\Java\jdk8\bin";"%PROGRAMFILES%/Java/jdk1.8.0_121/bin"
echo Building...
rmdir out /S /Q
mkdir out
javac src/pokemon/menu/MainMenu.java -cp src -d out
echo Press any key to run
pause >nul
echo Running...
cls
java -cp out/ pokemon.menu.MainMenu 
pause