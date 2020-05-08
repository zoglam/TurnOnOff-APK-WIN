reg delete HKLM\Software\Microsoft\Windows\CurrentVersion\Run /v TurnOnOff
TASKKILL /F /IM TurnOnOff.exe /T
pause