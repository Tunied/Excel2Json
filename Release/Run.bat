@echo off
SET EXCEL_FOLER=D:\Workground\Tools\Excel2Json\TestZone\Excel
SET EXPORT_JSON_FOLDER=D:\Workground\Tools\Excel2Json\TestZone\Json

for /r %EXCEL_FOLER% %%i in (*.xls) do (
	java -jar Excel2Json-1.1.jar %%i %EXPORT_JSON_FOLDER%
)
pause
