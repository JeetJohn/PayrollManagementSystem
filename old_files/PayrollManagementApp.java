import java.util.List;
import java.util.Scanner;

public class PayrollManagementApp {
    private static PayrollSystem payrollSystem = new PayrollSystem();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        // Add some sample employees
        initializeSampleData();
        
        System.out.println("=== Payroll Management System ===");
        
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    updateHours();
                    break;
                case 3:
                    processIndividualPayroll();
                    break;
                case 4:
                    processAllPayrolls();
                    break;
                case 5:
                    payrollSystem.displayAllEmployees();
                    break;
                case 6:
                    payrollSystem.displayPayrollHistory();
                    break;
                case 7:
                    displayTotalCost();
                    break;
                case 8:
                    System.out.println("Thank you for using Payroll Management System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Add Employee");
        System.out.println("2. Update Employee Hours");
        System.out.println("3. Process Individual Payroll");
        System.out.println("4. Process All Payrolls");
        System.out.println("5. Display All Employees");
        System.out.println("6. Display Payroll History");
        System.out.println("7. Display Total Payroll Cost");
        System.out.println("8. Exit");
    }
    
    private static void initializeSampleData() {
        payrollSystem.addEmployee(new Employee(1, "John Doe", "Software Engineer", 4000.0));
        payrollSystem.addEmployee(new Employee(2, "Jane Smith", "Manager", 5000.0));
        payrollSystem.addEmployee(new Employee(3, "Bob Johnson", "Analyst", 3500.0));
        
        // Set some sample hours
        payrollSystem.updateEmployeeHours(1, 42.0, 2.0);
        payrollSystem.updateEmployeeHours(2, 40.0, 0.0);
        payrollSystem.updateEmployeeHours(3, 38.0, 0.0);
    }   
 
    private static void addEmployee() {
        System.out.println("\n--- Add New Employee ---");
        int id = getIntInput("Employee ID: ");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Position: ");
        String position = scanner.nextLine();
        double salary = getDoubleInput("Base Salary: ");
        
        Employee employee = new Employee(id, name, position, salary);
        payrollSystem.addEmployee(employee);
    }
    
    private static void updateHours() {
        System.out.println("\n--- Update Employee Hours ---");
        int id = getIntInput("Employee ID: ");
        double hours = getDoubleInput("Hours Worked: ");
        double overtime = getDoubleInput("Overtime Hours: ");
        
        payrollSystem.updateEmployeeHours(id, hours, overtime);
    }
    
    private static void processIndividualPayroll() {
        System.out.println("\n--- Process Individual Payroll ---");
        int id = getIntInput("Employee ID: ");
        
        PayrollRecord record = payrollSystem.processPayroll(id);
        if (record != null) {
            System.out.println("Payroll processed:");
            System.out.println(record);
        } else {
            System.out.println("Employee not found.");
        }
    }
    
    private static void processAllPayrolls() {
        System.out.println("\n--- Processing All Payrolls ---");
        List<PayrollRecord> records = payrollSystem.processAllPayrolls();
        
        System.out.println("Processed " + records.size() + " payroll records:");
        for (PayrollRecord record : records) {
            System.out.println(record);
        }
    }
    
    private static void displayTotalCost() {
        double totalCost = payrollSystem.getTotalPayrollCost();
        System.out.printf("\nTotal Payroll Cost: $%.2f\n", totalCost);
    }
    
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }
    
    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return value;
    }
}