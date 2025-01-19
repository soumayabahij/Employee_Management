package org.employee.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.springframework.web.client.RestTemplate;

public class EmployeeLogin {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public EmployeeLogin() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Login");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new LoginActionListener());

        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (isInputValid(username, password)) {
                authenticateUser(username, password);
            }
        }
    }

    private boolean isInputValid(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password.");
            return false;
        }
        return true;
    }

    private void authenticateUser(String username, String password) {
        String url = "http://localhost:8080/api/auth/login?fullName=" + username + "&employeeId=" + password;
        Map<String, String> requestPayload = Map.of(
            "username", username,
            "password", password
        );

        try {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> response = restTemplate.postForObject(url, requestPayload, Map.class);

            if (response != null) {
                List<String> permissions = (List<String>) response.get("permissions");
                frame.dispose();
                new EmployeeDashboard(permissions).manage();
            } else {
                showError("Invalid username or password.");
            }
        } catch (Exception ex) {
            showError("Bad credentials");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
