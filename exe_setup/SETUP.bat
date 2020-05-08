@echo ON
set currPath=%cd%
cd %WINDIR%/..
MD TurnOnOff
cd TurnOnOff
set newPath=%cd%
cd %currPath%
MOVE DELETE.bat %newPath%
MOVE hideConsole.vbs %newPath%
MOVE xml.xml %newPath%
MOVE programm.bat %newPath%
MOVE TurnOnOff.exe %newPath%
MOVE ADDTOREG.bat %newPath%
MOVE SETUP.bat %newPath%