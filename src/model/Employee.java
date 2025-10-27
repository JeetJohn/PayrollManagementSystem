package model;

public class Employee {
    private int employeeId;
    private String name;
    private String position;
    private double baseSalary;
    private double hoursWorked;
    private double overtimeHours;
    
    public Employee(int employeeId, String name, String position, double baseSalary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.baseSalary = baseSalary;
        this.hoursWorked = 0;
        this.overtimeHours = 0;
    }
    
    // Getters and Setters
    public int getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public double getBaseSalary() { return baseSalary; }
    public double getHoursWorked() { return hoursWorked; }
    public double getOvertimeHours() { return overtimeHours; }
    
    public void setHoursWorked(double hoursWorked) { this.hoursWorked = hoursWorked; }
    public void setOvertimeHours(double overtimeHours) { this.overtimeHours = overtimeHours; }
    public void setBaseSalary(double baseSalary) { this.baseSalary = baseSalary; }
    
    @Override
    public String toString() {
        return String.format("%s (ID: %d)", name, employeeId);
    }
    
    public String getDetailedString() {
        return String.format("Employee[ID=%d, Name=%s, Position=%s, Base Salary=%.2f]", 
                           employeeId, name, position, baseSalary);
    }
}