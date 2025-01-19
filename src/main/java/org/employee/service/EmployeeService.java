package org.employee.service;

import java.time.LocalDate;
import java.util.List;

import org.employee.model.Employee;


public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(String id);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(String id, Employee updatedEmployee);

    void deleteEmployee(String id);

    List<Employee> searchEmployees(String query);

    List<Employee> filterEmployees(String employmentStatus, String department, LocalDate hireDate);
}
