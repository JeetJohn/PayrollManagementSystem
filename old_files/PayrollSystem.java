import java.util.*;

public class PayrollSystem {
    private Map<Integer, Employee> employees;
    private List<PayrollRecord> payrollHistory;
    
    public PayrollSystem() {
        this.employees = new HashMap<>();
        this.payrollHistory = new ArrayList<>();
    }
    
    public void addEmployee(Employee employee) {
        employees.put(employee.getEmployeeId(), employee);
        System.out.println("Employee added: " + employee.getName());
    }
    
    public Employee getEmployee(int employeeId) {
        return employees.get(employeeId);
    }
    
    public void removeEmployee(int employeeId) {
        Employee removed = employees.remove(employeeId);
        if (removed != null) {
            System.out.println("Employee removed: " + removed.getName());
        } else {
            System.out.println("Employee not found with ID: " + employeeId);
        }
    }
    
    public void updateEmployeeHours(int employeeId, double hoursWorked, double overtimeHours) {
        Employee employee = employees.get(employeeId);
        if (employee != null) {
            employee.setHoursWorked(hoursWorked);
            employee.setOvertimeHours(overtimeHours);
            System.out.println("Hours updated for: " + employee.getName());
        } else {
            System.out.println("Employee not found with ID: " + employeeId);
        }
    }
    
    public PayrollRecord processPayroll(int employeeId) {
        Employee employee = employees.get(employeeId);
        if (employee != null) {
            PayrollRecord record = PayrollCalculator.generatePayrollRecord(employee);
            payrollHistory.add(record);
            return record;
        }
        return null;
    }
    
    public List<PayrollRecord> processAllPayrolls() {
        List<PayrollRecord> currentPayroll = new ArrayList<>();
        for (Employee employee : employees.values()) {
            PayrollRecord record = PayrollCalculator.generatePayrollRecord(employee);
            payrollHistory.add(record);
            currentPayroll.add(record);
        }
        return currentPayroll;
    }
    
    public void displayAllEmployees() {
        System.out.println("\n=== All Employees ===");
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        for (Employee employee : employees.values()) {
            System.out.println(employee);
        }
    }
    
    public void displayPayrollHistory() {
        System.out.println("\n=== Payroll History ===");
        if (payrollHistory.isEmpty()) {
            System.out.println("No payroll records found.");
            return;
        }
        for (PayrollRecord record : payrollHistory) {
            System.out.println(record);
        }
    }
    
    public double getTotalPayrollCost() {
        return payrollHistory.stream()
                           .mapToDouble(PayrollRecord::getGrossPay)
                           .sum();
    }
}