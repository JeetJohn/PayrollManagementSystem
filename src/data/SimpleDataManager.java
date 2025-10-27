package data;

import model.Employee;
import model.PayrollRecord;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleDataManager {
    private Map<Integer, Employee> employees;
    private List<PayrollRecord> payrollRecords;
    private AtomicInteger nextEmployeeId;
    
    public SimpleDataManager() {
        employees = new HashMap<>();
        payrollRecords = new ArrayList<>();
        nextEmployeeId = new AtomicInteger(1);
        
        // Add some sample data
        addSampleData();
    }
    
    private void addSampleData() {
        addEmployee("John Doe", "Manager", 5000.0);
        addEmployee("Jane Smith", "Technical", 4000.0);
        addEmployee("Bob Johnson", "Clerk", 3000.0);
    }
    
    public int addEmployee(String name, String role, double baseSalary) {
        int id = nextEmployeeId.getAndIncrement();
        Employee employee = new Employee(id, name, role, baseSalary);
        employees.put(id, employee);
        return id;
    }
    
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }
    
    public Employee getEmployee(int id) {
        return employees.get(id);
    }
    
    public void updateEmployeeHours(int employeeId, double hoursWorked, double overtimeHours) {
        Employee employee = employees.get(employeeId);
        if (employee != null) {
            employee.setHoursWorked(hoursWorked);
            employee.setOvertimeHours(overtimeHours);
        }
    }
    
    public void savePayrollRecord(PayrollRecord record) {
        payrollRecords.add(record);
    }
    
    public List<PayrollRecord> getAllPayrollRecords() {
        // Return a copy sorted by most recent first
        List<PayrollRecord> sorted = new ArrayList<>(payrollRecords);
        sorted.sort((a, b) -> b.getPayDate().compareTo(a.getPayDate()));
        return sorted;
    }
    
    public void removeEmployee(int employeeId) {
        employees.remove(employeeId);
    }
}