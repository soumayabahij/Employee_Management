package com.example.demo.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.employee.DemoApplication;
import org.employee.model.Employee;
import org.employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void should_get_all_employees() throws Exception {
        Employee employee1 = new Employee("CZ001", "name1", "CDI", "IT", "name1@yopmail.com", "address 01", LocalDate.now(), "BE",
            Collections.emptySet());
        Employee employee2 = new Employee("CZ002", "name2", "CDD", "HR", "name2@yopmail.com", "address 02", LocalDate.now(), "BE",
            Collections.emptySet());
        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/employees"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].full_name").value("name1"))
            .andExpect(jsonPath("$[1].full_name").value("name2"));

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    public void should_create_employee() throws Exception {
        Employee employee = new Employee("CZ003", "name3", "CDI", "IT", "name3@yopmail.com", "address 03", LocalDate.now(), "BE",
            Collections.emptySet());
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees/create")
                .contentType("application/json")
                .content("{\"employee_id\":\"CZ003\",\"full_name\":\"name3\",\"job_title\":\"BE\",\"department\":\"IT\",\"contact_info\":\"name3@yopmail" +
                    ".com\",\"address\":\"address 03\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.full_name").value("name3"));

        verify(employeeService, times(1)).createEmployee(any(Employee.class));
    }
}
