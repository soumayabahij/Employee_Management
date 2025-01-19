package org.employee.ui.panel;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;

import org.employee.model.Employee;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ViewEmployeesPanel extends JPanel {
    private EmployeeTablePanel employeeTablePanel;
    private ReportEmployeesPanel reportPanel;
    private FilterEmployeePanel filterPanel;
    private SearchEmployeePanel searchPanel;

    public void view() {
        setLayout(new BorderLayout());
        initializeUIComponents();

        loadAllEmployees();
    }

    private void initializeUIComponents() {
        employeeTablePanel = new EmployeeTablePanel();
        reportPanel = new ReportEmployeesPanel();
        filterPanel = new FilterEmployeePanel(employeeTablePanel);
        searchPanel = new SearchEmployeePanel(employeeTablePanel);

        add(searchPanel, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.CENTER);
        add(reportPanel, BorderLayout.WEST);
        add(employeeTablePanel, BorderLayout.SOUTH);
    }

    private void loadAllEmployees() {
        List<Employee> employees = getAllEmployees();
        employeeTablePanel.updateTable(employees);
    }

    private List<Employee> getAllEmployees() {
        List<Employee> employees = null;
        try {
            RestTemplate restTemplate = new RestTemplate();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setObjectMapper(objectMapper);
            restTemplate.getMessageConverters().add(0, converter);
            String url = "http://localhost:8080/api/employees";
            ResponseEntity<List<Employee>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Employee>>() {
                }
            );
            employees = response.getBody();
        } catch (Exception e) {
            e.getMessage();
        }
        return employees;
    }
}