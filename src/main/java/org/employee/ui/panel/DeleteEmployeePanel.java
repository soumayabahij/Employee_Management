package org.employee.ui.panel;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.web.client.RestTemplate;

import net.miginfocom.swing.MigLayout;

public class DeleteEmployeePanel extends JPanel {
    private JTextField employeeIdField;

    public void delete() {
            this.setLayout(new MigLayout());
            initializeUIComponents();
            setVisible(true);
    }

    private void initializeUIComponents() {
        add(new JLabel("Employee ID:"));
        employeeIdField = new JTextField(20);
        add(employeeIdField, "wrap");

        JButton deleteButton = new JButton("Delete Employee");
        add(deleteButton, "wrap");

        deleteButton.addActionListener(e -> handleDelete());
    }

    private void handleDelete() {
        String employeeId = employeeIdField.getText();

        if (validateInput(employeeId)) {
            try {
                deleteEmployee(employeeId);
                showPopUp(this, "Employee deleted successfully.");
            } catch (Exception ex) {
                showPopUp(this, "Employee not found.");
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

    private void deleteEmployee(String employeeId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/employees/" + employeeId;
        restTemplate.delete(url);
    }

    private void showPopUp(Component component, String message) {
        JOptionPane.showMessageDialog(component, message);
    }
}