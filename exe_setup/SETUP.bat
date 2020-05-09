@echo ON
set currPath=%cd%
cd %appdata%
MD TurnOnOff
cd TurnOnOff
set newPath=%cd%
cd %currPath%
MOVE DELETE.bat %newPath%
MOVE hideConsole.vbs %newPath%
MOVE hideConsole.bat %newPath%
MOVE xml.xml %newPath%
MOVE programm.bat %newPath%
MOVE TurnOnOff.exe %newPath%
MOVE ADDTOREG.bat %newPath%
start %newPath%
MOVE SETUP.bat %newPath%