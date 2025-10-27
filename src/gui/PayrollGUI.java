package gui;

import model.Employee;
import model.PayrollRecord;
import data.SimpleDataManager;
import utils.PayrollCalculator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PayrollGUI extends JFrame {
    private SimpleDataManager dataManager;
    private JTabbedPane tabbedPane;
    
    // Add Employee Panel Components
    private JTextField nameField;
    private JComboBox<String> roleComboBox;
    private JTextField salaryField;
    
    // View Employees Panel Components
    private JTable employeeTable;
    private DefaultTableModel employeeTableModel;
    
    // Payroll Panel Components
    private JTable payrollTable;
    private DefaultTableModel payrollTableModel;
    private JComboBox<Employee> employeeComboBox;
    private JTextField hoursField;
    private JTextField overtimeField;
    
    public PayrollGUI() {
        dataManager = new SimpleDataManager();
        initializeGUI();
        loadEmployeeData();
        loadPayrollData();
        loadEmployeeComboBox();
    }
    
    private void initializeGUI() {
        setTitle("Payroll Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        
        // Create tabs
        tabbedPane.addTab("Add Employee", createAddEmployeePanel());
        tabbedPane.addTab("View Employees", createViewEmployeesPanel());
        tabbedPane.addTab("Payroll Management", createPayrollPanel());
        
        add(tabbedPane);
        
        // Add window closing event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }    

    private JPanel createAddEmployeePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("Add New Employee");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        // Name field
        gbc.gridwidth = 1; gbc.gridy = 1;
        gbc.gridx = 0; panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; 
        nameField = new JTextField(20);
        panel.add(nameField, gbc);
        
        // Role field
        gbc.gridy = 2;
        gbc.gridx = 0; panel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        roleComboBox = new JComboBox<>(new String[]{"Manager", "Clerk", "Technical"});
        panel.add(roleComboBox, gbc);
        
        // Salary field
        gbc.gridy = 3;
        gbc.gridx = 0; panel.add(new JLabel("Base Salary:"), gbc);
        gbc.gridx = 1;
        salaryField = new JTextField(20);
        panel.add(salaryField, gbc);
        
        // Add button
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Employee");
        addButton.addActionListener(new AddEmployeeListener());
        panel.add(addButton, gbc);
        
        return panel;
    }
    
    private JPanel createViewEmployeesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("All Employees", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Table
        String[] columnNames = {"ID", "Name", "Role", "Base Salary", "Hours Worked", "Overtime Hours"};
        employeeTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        employeeTable = new JTable(employeeTableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadEmployeeData());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createPayrollPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Payroll Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Input panel
        JPanel inputPanel = createPayrollInputPanel();
        panel.add(inputPanel, BorderLayout.WEST);
        
        // Payroll records table
        JPanel tablePanel = createPayrollTablePanel();
        panel.add(tablePanel, BorderLayout.CENTER);
        
        return panel;
    }    

    private JPanel createPayrollInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Generate Payroll"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Employee selection
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Employee:"), gbc);
        gbc.gridy = 1;
        employeeComboBox = new JComboBox<>();
        employeeComboBox.setPreferredSize(new Dimension(200, 25));
        panel.add(employeeComboBox, gbc);
        
        // Hours worked
        gbc.gridy = 2;
        panel.add(new JLabel("Hours Worked:"), gbc);
        gbc.gridy = 3;
        hoursField = new JTextField(15);
        panel.add(hoursField, gbc);
        
        // Overtime hours
        gbc.gridy = 4;
        panel.add(new JLabel("Overtime Hours:"), gbc);
        gbc.gridy = 5;
        overtimeField = new JTextField(15);
        panel.add(overtimeField, gbc);
        
        // Buttons
        gbc.gridy = 6;
        JButton generateButton = new JButton("Generate Payroll");
        generateButton.addActionListener(new GeneratePayrollListener());
        panel.add(generateButton, gbc);
        
        gbc.gridy = 7;
        JButton refreshEmployeesButton = new JButton("Refresh Employees");
        refreshEmployeesButton.addActionListener(e -> loadEmployeeComboBox());
        panel.add(refreshEmployeesButton, gbc);
        
        return panel;
    }
    
    private JPanel createPayrollTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Payroll Records"));
        
        String[] columnNames = {"Employee ID", "Name", "Hours", "Overtime", "Gross Pay", "Tax", "Net Pay", "Date"};
        payrollTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        payrollTable = new JTable(payrollTableModel);
        
        JScrollPane scrollPane = new JScrollPane(payrollTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton refreshButton = new JButton("Refresh Payroll Records");
        refreshButton.addActionListener(e -> loadPayrollData());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadEmployeeData() {
        employeeTableModel.setRowCount(0);
        List<Employee> employees = dataManager.getAllEmployees();
        
        for (Employee emp : employees) {
            Object[] row = {
                emp.getEmployeeId(),
                emp.getName(),
                emp.getPosition(),
                String.format("$%.2f", emp.getBaseSalary()),
                emp.getHoursWorked(),
                emp.getOvertimeHours()
            };
            employeeTableModel.addRow(row);
        }
    }
    
    private void loadEmployeeComboBox() {
        employeeComboBox.removeAllItems();
        List<Employee> employees = dataManager.getAllEmployees();
        
        for (Employee emp : employees) {
            employeeComboBox.addItem(emp);
        }
    }
    
    private void loadPayrollData() {
        payrollTableModel.setRowCount(0);
        List<PayrollRecord> records = dataManager.getAllPayrollRecords();
        
        for (PayrollRecord record : records) {
            Object[] row = {
                record.getEmployeeId(),
                record.getEmployeeName(),
                record.getHoursWorked(),
                record.getOvertimeHours(),
                String.format("$%.2f", record.getGrossPay()),
                String.format("$%.2f", record.getTax()),
                String.format("$%.2f", record.getNetPay()),
                record.getPayDate()
            };
            payrollTableModel.addRow(row);
        }
    }  
  
    private class AddEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = nameField.getText().trim();
                String role = (String) roleComboBox.getSelectedItem();
                double salary = Double.parseDouble(salaryField.getText().trim());
                
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(PayrollGUI.this, "Please enter employee name.");
                    return;
                }
                
                if (salary <= 0) {
                    JOptionPane.showMessageDialog(PayrollGUI.this, "Please enter a valid salary.");
                    return;
                }
                
                int employeeId = dataManager.addEmployee(name, role, salary);
                
                if (employeeId > 0) {
                    JOptionPane.showMessageDialog(PayrollGUI.this, 
                        "Employee added successfully! ID: " + employeeId);
                    
                    // Clear fields
                    nameField.setText("");
                    salaryField.setText("");
                    roleComboBox.setSelectedIndex(0);
                    
                    // Refresh data
                    loadEmployeeData();
                    loadEmployeeComboBox();
                } else {
                    JOptionPane.showMessageDialog(PayrollGUI.this, "Error adding employee.");
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(PayrollGUI.this, "Please enter a valid salary amount.");
            }
        }
    }
    
    private class GeneratePayrollListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Employee selectedEmployee = (Employee) employeeComboBox.getSelectedItem();
                if (selectedEmployee == null) {
                    JOptionPane.showMessageDialog(PayrollGUI.this, "Please select an employee.");
                    return;
                }
                
                double hours = Double.parseDouble(hoursField.getText().trim());
                double overtime = Double.parseDouble(overtimeField.getText().trim());
                
                if (hours < 0 || overtime < 0) {
                    JOptionPane.showMessageDialog(PayrollGUI.this, "Hours cannot be negative.");
                    return;
                }
                
                // Update employee hours in data manager
                dataManager.updateEmployeeHours(selectedEmployee.getEmployeeId(), hours, overtime);
                
                // Update employee object
                selectedEmployee.setHoursWorked(hours);
                selectedEmployee.setOvertimeHours(overtime);
                
                // Generate payroll record
                PayrollRecord record = PayrollCalculator.generatePayrollRecord(selectedEmployee);
                
                // Save to data manager
                dataManager.savePayrollRecord(record);
                
                // Show success message
                JOptionPane.showMessageDialog(PayrollGUI.this, 
                    String.format("Payroll generated for %s\nNet Pay: $%.2f", 
                    selectedEmployee.getName(), record.getNetPay()));
                
                // Clear fields
                hoursField.setText("");
                overtimeField.setText("");
                
                // Refresh data
                loadEmployeeData();
                loadPayrollData();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(PayrollGUI.this, "Please enter valid numbers for hours.");
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                new PayrollGUI().setVisible(true);
            }
        });
    }
}