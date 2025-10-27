@echo off
echo Compiling Payroll Management System...

REM Navigate to project root directory
cd ..

REM Create build directory for compiled classes
if not exist "build" mkdir build

REM Display current directory for debugging
echo Current directory: %CD%

REM Compile all Java files with proper package structure
echo Compiling Java source files...
javac -d build -sourcepath src src/gui/PayrollGUI.java src/model/*.java src/data/*.java src/utils/*.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✓ Compilation successful!
    echo.
    echo Project structure:
    echo   build/          - Compiled .class files
    echo   src/            - Source code
    echo   scripts/        - Build scripts
    echo.
    echo To run the application:
    echo   Option 1: scripts\run.bat
    echo   Option 2: java -cp build gui.PayrollGUI
    echo.
) else (
    echo.
    echo ✗ Compilation failed! Check for errors above.
    echo Make sure all Java files are in their correct packages.
    echo.
)

pause