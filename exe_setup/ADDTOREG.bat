cd %WINDIR%\..
cd TurnOnOff
reg add HKLM\Software\Microsoft\Windows\CurrentVersion\Run /v TurnOnOff /d "%cd%\hideConsole.vbs"
pause