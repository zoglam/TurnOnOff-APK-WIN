Set WshShell = CreateObject("WScript.Shell")
WshShell.Run Chr(34) & "%APPDATA%/TurnOnOff/programm.bat" & Chr(34), 0
Set WshShell = Nothing