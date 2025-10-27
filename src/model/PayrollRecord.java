package model;

import java.time.LocalDate;

public class PayrollRecord {
    private int employeeId;
    private String employeeName;
    private double hoursWorked;
    private double overtimeHours;
    private double grossPay;
    private double tax;
    private double netPay;
    private LocalDate payDate;
    
    public PayrollRecord(int employeeId, String employeeName, double hoursWorked, 
                        double overtimeHours, double grossPay, double tax, double netPay) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.hoursWorked = hoursWorked;
        this.overtimeHours = overtimeHours;
        this.grossPay = grossPay;
        this.tax = tax;
        this.netPay = netPay;
        this.payDate = LocalDate.now();
    }
    
    // Getters
    public int getEmployeeId() { return employeeId; }
    public String getEmployeeName() { return employeeName; }
    public double getHoursWorked() { return hoursWorked; }
    public double getOvertimeHours() { return overtimeHours; }
    public double getGrossPay() { return grossPay; }
    public double getTax() { return tax; }
    public double getNetPay() { return netPay; }
    public LocalDate getPayDate() { return payDate; }
    
    @Override
    public String toString() {
        return String.format(
            "PayrollRecord[Employee: %s (ID: %d), Hours: %.1f, Overtime: %.1f, " +
            "Gross: $%.2f, Tax: $%.2f, Net: $%.2f, Date: %s]",
            employeeName, employeeId, hoursWorked, overtimeHours, 
            grossPay, tax, netPay, payDate
        );
    }
}