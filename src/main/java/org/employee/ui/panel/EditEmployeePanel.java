package org.employee.ui.panel;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.web.client.RestTemplate;

import net.miginfocom.swing.MigLayout;

public class EditEmployeePanel extends JPanel {
    private JTextField employeeIdField;
    private JTextField fullNameField;
    private JTextField jobTitleField;
    private JTextField departmentField;
    private JTextField hireDateField;
    private JTextField employmentStatusField;
    private JTextField contactInfoField;
    private JTextField addressField;

    public void edit() {
        setLayout(new MigLayout());
        initializeUIComponents();
        setVisible(true);
    }

    private void initializeUIComponents() {
        employeeIdField = createTextFieldWithLabel("Employee ID:*");
        fullNameField = createTextFieldWithLabel("Full Name:*");
        jobTitleField = createTextFieldWithLabel("Job Title:");
        departmentField = createTextFieldWithLabel("Department:");
        hireDateField = createTextFieldWithLabel("Hire Date (YYYY-mm-dd):");
        employmentStatusField = createTextFieldWithLabel("Employment Status:");
        contactInfoField = createTextFieldWithLabel("Contact Info:");
        addressField = createTextFieldWithLabel("Address:");

        JButton submitButton = new JButton("Update Employee");
        add(submitButton, "wrap");
        submitButton.addActionListener(e -> handleUpdate());
    }

    private JTextField createTextFieldWithLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        add(label);
        JTextField textField = new JTextField(20);
        add(textField, "wrap");
        return textField;
    }

    private void handleUpdate() {
        String employeeId = employeeIdField.getText();

        if (validateInput(employeeId)) {
            try {
                updateEmployee(employeeId);
                showPopUp(this, "Employee updated successfully!");
            } catch (Exception ex) {
                showPopUp(this, "An error occurred while updating the employee.");
            }
        }
    }

    private boolean validateInput(String employeeId) {
        if (employeeId.isEmpty()) {
            showPopUp(this, "Employee ID is required!");
            return false;
        }
        return true;
    }

    private void updateEmployee(String employeeId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/employees/" + employeeId;

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("full_name", fullNameField.getText());
        requestPayload.put("employee_id", employeeId);
        requestPayload.put("job_title", jobTitleField.getText());
        requestPayload.put("department", departmentField.getText());
        requestPayload.put("hire_date", hireDateField.getText());
        requestPayload.put("employment_status", employmentStatusField.getText());
        requestPayload.put("contact_info", contactInfoField.getText());
        requestPayload.put("address", addressField.getText());

        restTemplate.put(url, requestPayload);
    }

    private void showPopUp(Component component, String message) {
        JOptionPane.showMessageDialog(component, message);
    }
}