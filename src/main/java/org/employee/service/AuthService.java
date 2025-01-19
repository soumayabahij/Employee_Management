package org.employee.service;

import java.util.Optional;

import org.employee.model.Employee;

public interface AuthService {
    Optional<Employee> authenticate(String fullName, String employeeId);
}
