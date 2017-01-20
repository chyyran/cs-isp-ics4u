@echo off
set PATH=%PATH%;"F:\Program Files\Java\jdk8\bin";"%PROGRAMFILES%/Java/jdk8/bin"
echo Building...
mkdir out
javac src/pokemon/menu/MainMenu.java -cp src -d out
echo Running...
cls
java -cp out/ pokemon.menu.MainMenu 