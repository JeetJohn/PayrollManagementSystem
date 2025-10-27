@echo off
echo Setting up Payroll Management System...

REM Create lib directory if it doesn't exist
if not exist "lib" mkdir lib

REM Download SQLite JDBC driver
echo Downloading SQLite JDBC driver...
curl -L -o lib/sqlite-jdbc-3.44.1.0.jar https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/sqlite-jdbc-3.44.1.0.jar

if exist "lib/sqlite-jdbc-3.44.1.0.jar" (
    echo SQLite JDBC driver downloaded successfully!
) else (
    echo Failed to download SQLite JDBC driver. Please download manually from:
    echo https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/sqlite-jdbc-3.44.1.0.jar
    echo and place it in the lib folder.
)

echo.
echo Setup complete! You can now compile and run the application.
echo.
echo To compile: javac -cp "lib/*" *.java
echo To run: java -cp ".;lib/*" PayrollGUI
pause