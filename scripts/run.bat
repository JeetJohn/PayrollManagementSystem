@echo off
echo Starting Payroll Management System...

REM Navigate to project root directory
cd ..

REM Check if compiled classes exist
if not exist "build\gui\PayrollGUI.class" (
    echo Error: Application not compiled. Please run compile.bat first.
    echo.
    echo To compile: scripts\compile.bat
    pause
    exit /b 1
)

echo Current directory: %CD%
echo Running Payroll Management System...
echo.

REM Run the application
java -cp build gui.PayrollGUI

echo.
echo Application closed.
pause