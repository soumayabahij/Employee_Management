package org.employee.ui.panel;

import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.employee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SearchEmployeePanel extends JPanel {
    private JTextField searchTextField;
    private JButton searchButton;
    private EmployeeTablePanel employeeTablePanel;

    public SearchEmployeePanel(EmployeeTablePanel employeeTablePanel) {
        this.employeeTablePanel = employeeTablePanel;
        searchTextField = new JTextField(20);
        searchButton = new JButton("Search");

        add(searchTextField);
        add(searchButton);

        searchButton.addActionListener(e -> search());
    }

    private void search() {
        List<Employee> searchedEmployees = getSearchedEmployees();
        employeeTablePanel.updateTable(searchedEmployees);
    }

    private List<Employee> getSearchedEmployees() {
        String searchText = searchTextField.getText();
        try {

            String url = "http://localhost:8080/api/employees/search?query=" + searchText;

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Employee[]> response = restTemplate.getForEntity(url, Employee[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return List.of(response.getBody());
            } else {
                return Collections.emptyList();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while searching employees.");
        }
        return Collections.emptyList();
    }
}