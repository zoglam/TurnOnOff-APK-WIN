reg delete HKCU\Software\Microsoft\Windows\CurrentVersion\Run /v TurnOnOff
TASKKILL /F /IM TurnOnOff.exe /T
pause