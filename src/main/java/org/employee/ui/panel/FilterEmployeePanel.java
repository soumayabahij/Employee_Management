package org.employee.ui.panel;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.employee.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class FilterEmployeePanel extends JPanel {
    private JComboBox<String> employmentStatusDropdown;
    private JComboBox<String> departmentDropdown;
    private JTextField hireDateField;
    private JButton filterButton;
    private EmployeeTablePanel employeeTablePanel;

    public FilterEmployeePanel(EmployeeTablePanel employeeTablePanel) {
        this.employeeTablePanel = employeeTablePanel;

        employmentStatusDropdown = new JComboBox<>(new String[]{"", "CDD", "CDI"});
        departmentDropdown = new JComboBox<>(new String[]{"", "IT", "HR", "Marketing"});
        hireDateField = new JTextField(20);
        filterButton = new JButton("Filter");

        add(new JLabel("Employment Status:"));
        add(employmentStatusDropdown);
        add(new JLabel("Department:"));
        add(departmentDropdown);
        add(new JLabel("Hire Date (YYYY-mm-dd):"));
        add(hireDateField);
        add(filterButton);

        filterButton.addActionListener(e -> applyFilter());
    }

    private void applyFilter() {
        String employmentStatus = (String) employmentStatusDropdown.getSelectedItem();
        String department = (String) departmentDropdown.getSelectedItem();
        LocalDate hireDate = null;
        if (!hireDateField.getText().equals("")) {
            hireDate = LocalDate.parse(hireDateField.getText());
        }

        List<Employee> filteredEmployees = fetchFilteredEmployees(employmentStatus, department, hireDate);
        employeeTablePanel.updateTable(filteredEmployees);
    }

    private List<Employee> fetchFilteredEmployees(String employmentStatus, String department, LocalDate hireDate) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/api/employees/filter?";

            if (employmentStatus != null && !employmentStatus.isEmpty()) {
                url += "employmentStatus=" + employmentStatus + "&";
            }
            if (department != null && !department.isEmpty()) {
                url += "department=" + department + "&";
            }
            if (hireDate != null) {
                url += "hireDate=" + hireDate;
            }

            ResponseEntity<Employee[]> response = restTemplate.getForEntity(url, Employee[].class);
            return List.of(response.getBody());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred while fetching employees.");
            return null;
        }
    }
}