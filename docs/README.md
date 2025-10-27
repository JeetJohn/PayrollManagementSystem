# Payroll Management System

A comprehensive Java-based payroll management system with GUI and database integration.

## Features

- **Add Employee**: Add new employees with auto-generated IDs and role selection (Manager, Clerk, Technical)
- **View Employees**: Display all employees and their details in a table format
- **Payroll Management**: Generate and view payroll records with automatic calculations
- **Database Integration**: SQLite database for persistent data storage
- **User-friendly GUI**: Swing-based graphical interface with tabbed navigation

## Setup Instructions

### 1. Download Dependencies
Run the setup script to download the SQLite JDBC driver:
```bash
setup.bat
```

### 2. Compile the Application
```bash
compile.bat
```

### 3. Run the Application
```bash
run.bat
```

## Manual Setup (Alternative)

If the batch files don't work, you can set up manually:

1. Create a `lib` folder
2. Download SQLite JDBC driver from: https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/sqlite-jdbc-3.44.1.0.jar
3. Place the JAR file in the `lib` folder
4. Compile: `javac -cp "lib/*" *.java`
5. Run: `java -cp ".;lib/*" PayrollGUI`

## System Components

### Core Classes
- **Employee**: Represents employee data
- **PayrollCalculator**: Handles salary calculations with overtime
- **PayrollRecord**: Stores payroll transaction details
- **DatabaseManager**: Manages SQLite database operations
- **PayrollGUI**: Main GUI application

### Database Schema
- **employees**: Stores employee information with auto-generated IDs
- **payroll_records**: Stores historical payroll data

### Payroll Calculations
- Standard work week: 40 hours
- Overtime rate: 1.5x base rate
- Tax rate: 15%
- Automatic gross pay, tax, and net pay calculations

## Usage

1. **Adding Employees**: 
   - Go to "Add Employee" tab
   - Enter name, select role, and base salary
   - Employee ID is auto-generated

2. **Viewing Employees**:
   - "View Employees" tab shows all employee details
   - Use "Refresh" to update the display

3. **Generating Payroll**:
   - "Payroll Management" tab
   - Select employee, enter hours worked and overtime
   - Click "Generate Payroll" to calculate and save

## Requirements

- Java 8 or higher
- SQLite JDBC driver (automatically downloaded by setup.bat)
- Windows OS (for batch files, can be adapted for other OS)