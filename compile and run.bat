@echo off
set PATH=%PATH%;"F:\Program Files\Java\jdk8\bin";"%PROGRAMFILES%/Java/jdk8/bin"
echo Building...
mkdir out
javac src/pokemon/menu/MainMenu.java -cp src -d out
echo press any key to run
pause
echo Running...
cls
java -cp out/ pokemon.menu.MainMenu 
pause