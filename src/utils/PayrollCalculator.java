package utils;

import model.Employee;
import model.PayrollRecord;

public class PayrollCalculator {
    private static final double OVERTIME_RATE = 1.5;
    private static final double STANDARD_HOURS = 40.0;
    private static final double TAX_RATE = 0.15; // 15% tax rate
    
    public static double calculateGrossPay(Employee employee) {
        double regularPay = Math.min(employee.getHoursWorked(), STANDARD_HOURS) * 
                           (employee.getBaseSalary() / STANDARD_HOURS);
        
        double overtimePay = employee.getOvertimeHours() * 
                            (employee.getBaseSalary() / STANDARD_HOURS) * OVERTIME_RATE;
        
        return regularPay + overtimePay;
    }
    
    public static double calculateTax(double grossPay) {
        return grossPay * TAX_RATE;
    }
    
    public static double calculateNetPay(Employee employee) {
        double grossPay = calculateGrossPay(employee);
        double tax = calculateTax(grossPay);
        return grossPay - tax;
    }
    
    public static PayrollRecord generatePayrollRecord(Employee employee) {
        double grossPay = calculateGrossPay(employee);
        double tax = calculateTax(grossPay);
        double netPay = grossPay - tax;
        
        return new PayrollRecord(employee.getEmployeeId(), employee.getName(), 
                               employee.getHoursWorked(), employee.getOvertimeHours(),
                               grossPay, tax, netPay);
    }
}