package org.employee.ui.panel;

import java.awt.Component;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.employee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import net.miginfocom.swing.MigLayout;

public class AddEmployeePanel extends JPanel {
    private JTextField employeeIdField;
    private JTextField fullNameField;
    private JTextField jobTitleField;
    private JTextField departmentField;
    private JTextField hireDateField;
    private JTextField employmentStatusField;
    private JTextField contactInfoField;
    private JTextField addressField;

    public void add() {
        this.setLayout(new MigLayout());
        initializeFields();
        createUIComponents();
        setVisible(true);
    }

    private void initializeFields() {
        fullNameField = new JTextField(20);
        employeeIdField = new JTextField(20);
        jobTitleField = new JTextField(20);
        departmentField = new JTextField(20);
        hireDateField = new JTextField(20);
        employmentStatusField = new JTextField(20);
        contactInfoField = new JTextField(20);
        addressField = new JTextField(20);
    }

    private void createUIComponents() {
        add(new JLabel("Full Name:*"));
        add(fullNameField, "wrap");

        add(new JLabel("Employee ID:*"));
        add(employeeIdField, "wrap");

        add(new JLabel("Job Title:"));
        add(jobTitleField, "wrap");

        add(new JLabel("Department:"));
        add(departmentField, "wrap");

        add(new JLabel("Hire Date (YYYY-mm-dd):"));
        add(hireDateField, "wrap");

        add(new JLabel("Employment Status:"));
        add(employmentStatusField, "wrap");

        add(new JLabel("Contact Info:"));
        add(contactInfoField, "wrap");

        add(new JLabel("Address:"));
        add(addressField, "wrap");

        JButton submitButton = new JButton("Save Employee");
        add(submitButton, "wrap");

        submitButton.addActionListener(e -> handleSubmit());
    }

    private void handleSubmit() {
        if (!validateFields()) {
            return;
        }
        try {
            Employee employee = new Employee();
            employee.setFullName(fullNameField.getText());
            employee.setEmployeeId(employeeIdField.getText());
            employee.setJobTitle(jobTitleField.getText());
            employee.setDepartment(departmentField.getText());
            employee.setHireDate(LocalDate.parse(hireDateField.getText()));
            employee.setEmploymentStatus(employmentStatusField.getText());
            employee.setContactInfo(contactInfoField.getText());
            employee.setAddress(addressField.getText());

            if (sendEmployeeData(employee)) {
                showPopUp(this, "Employee saved successfully!");
            } else {
                showPopUp(this, "Failed to save employee. Please try again.");
            }
        } catch (Exception ex) {
            showPopUp(this, "An error occurred while saving the employee.");
        }
    }

    private boolean validateFields() {
        if (employeeIdField.getText().isEmpty() || fullNameField.getText().isEmpty()) {
            showPopUp(this, "Please fill all fields marked with *");
            return false;
        }
        return true;
    }

    private boolean sendEmployeeData(Employee employee) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        restTemplate.getMessageConverters().add(0, converter);

        String url = "http://localhost:8080/api/employees/create";
        ResponseEntity<Employee> response = restTemplate.postForEntity(url, employee, Employee.class);

        return response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED;
    }

    private void showPopUp(Component component, String message) {
        JOptionPane.showMessageDialog(component, message);
    }
}