cd %appdata%\TurnOnOff
reg add HKCU\Software\Microsoft\Windows\CurrentVersion\Run /v TurnOnOff /d "%appdata%\TurnOnOff\hideConsole.bat" 
pause