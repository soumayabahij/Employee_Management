package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.employee.DemoApplication;
import org.employee.model.Employee;
import org.employee.repository.EmployeeRepository;
import org.employee.service.EmployeeServiceImp;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
public class EmployeeServiceImpTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImp employeeService;

    @Test
    public void should_return_all_employees() {
        List<Employee> employees = Arrays.asList(new Employee("CZ001", "Soumaya", "CDD", "IT", "soumia@yopmail.com",
            "123 address", LocalDate.now(), "FE", Collections.emptySet()));
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getFullName(), "Soumaya");
        assertEquals(result.get(0).getEmployeeId(), "CZ001");
        assertEquals(result.get(0).getEmploymentStatus(), "CDD");
    }

    @Test
    public void should_create_employee() {
        Employee employee = new Employee("CZ001", "Soumaya", "CDD", "IT", "soumia@yopmail.com",
            "123 address", LocalDate.now(), "FE", Collections.emptySet());
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.createEmployee(employee);

        verify(employeeRepository, times(1)).save(employee);
        assertEquals(result.getFullName(), "Soumaya");
        assertEquals(result.getEmployeeId(), "CZ001");
        assertEquals(result.getEmploymentStatus(), "CDD");
    }
}
