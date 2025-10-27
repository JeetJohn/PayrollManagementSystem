import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:payroll.db";
    private Connection connection;
    
    public DatabaseManager() {
        initializeDatabase();
    }
    
    private void initializeDatabase() {
        try {
            // Explicitly load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }
    
    private void createTables() throws SQLException {
        String createEmployeeTable = """
            CREATE TABLE IF NOT EXISTS employees (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                role TEXT NOT NULL,
                base_salary REAL NOT NULL,
                hours_worked REAL DEFAULT 0,
                overtime_hours REAL DEFAULT 0
            )
        """;
        
        String createPayrollTable = """
            CREATE TABLE IF NOT EXISTS payroll_records (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                employee_id INTEGER,
                employee_name TEXT,
                hours_worked REAL,
                overtime_hours REAL,
                gross_pay REAL,
                tax REAL,
                net_pay REAL,
                pay_date TEXT,
                FOREIGN KEY (employee_id) REFERENCES employees (id)
            )
        """;
        
        Statement stmt = connection.createStatement();
        stmt.execute(createEmployeeTable);
        stmt.execute(createPayrollTable);
        stmt.close();
    }
    
    public int addEmployee(String name, String role, double baseSalary) {
        String sql = "INSERT INTO employees (name, role, base_salary) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setString(2, role);
            pstmt.setDouble(3, baseSalary);
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
        }
        return -1;
    }
    
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Employee emp = new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("role"),
                    rs.getDouble("base_salary")
                );
                emp.setHoursWorked(rs.getDouble("hours_worked"));
                emp.setOvertimeHours(rs.getDouble("overtime_hours"));
                employees.add(emp);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        }
        return employees;
    }
    
    public void updateEmployeeHours(int employeeId, double hoursWorked, double overtimeHours) {
        String sql = "UPDATE employees SET hours_worked = ?, overtime_hours = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, hoursWorked);
            pstmt.setDouble(2, overtimeHours);
            pstmt.setInt(3, employeeId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating employee hours: " + e.getMessage());
        }
    }
    
    public void savePayrollRecord(PayrollRecord record) {
        String sql = """
            INSERT INTO payroll_records 
            (employee_id, employee_name, hours_worked, overtime_hours, gross_pay, tax, net_pay, pay_date) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, record.getEmployeeId());
            pstmt.setString(2, record.getEmployeeName());
            pstmt.setDouble(3, record.getHoursWorked());
            pstmt.setDouble(4, record.getOvertimeHours());
            pstmt.setDouble(5, record.getGrossPay());
            pstmt.setDouble(6, record.getTax());
            pstmt.setDouble(7, record.getNetPay());
            pstmt.setString(8, record.getPayDate().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving payroll record: " + e.getMessage());
        }
    }
    
    public List<PayrollRecord> getAllPayrollRecords() {
        List<PayrollRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM payroll_records ORDER BY pay_date DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                PayrollRecord record = new PayrollRecord(
                    rs.getInt("employee_id"),
                    rs.getString("employee_name"),
                    rs.getDouble("hours_worked"),
                    rs.getDouble("overtime_hours"),
                    rs.getDouble("gross_pay"),
                    rs.getDouble("tax"),
                    rs.getDouble("net_pay")
                );
                records.add(record);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving payroll records: " + e.getMessage());
        }
        return records;
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}