package org.employee.ui.panel;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.employee.model.Employee;

public class EmployeeTablePanel extends JPanel {
    private JTable table;

    public EmployeeTablePanel() {
        setLayout(new BorderLayout());
        initializeUIComponents();
    }

    private void initializeUIComponents() {
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTable(List<Employee> employees) {
        if (employees != null && !employees.isEmpty()) {
            String[] columnNames = {"Full Name", "Employee ID", "Job Title", "Department", "Hire Date", "Employment Status", "Contact Information", "Address"};
            Object[][] data = new Object[employees.size()][columnNames.length];

            for (int i = 0; i < employees.size(); i++) {
                Employee emp = employees.get(i);
                data[i][0] = emp.getFullName();
                data[i][1] = emp.getEmployeeId();
                data[i][2] = emp.getJobTitle();
                data[i][3] = emp.getDepartment();
                data[i][4] = emp.getHireDate();
                data[i][5] = emp.getEmploymentStatus();
                data[i][6] = emp.getContactInfo();
                data[i][7] = emp.getAddress();
            }

            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
            table.setModel(tableModel);
        } else {
            JOptionPane.showMessageDialog(this, "No employees found for the selected criteria.");
        }
    }
}