package org.employee.service;

import java.util.Optional;

import org.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.employee.model.Employee;

@Service
public class AuthServiceImp implements AuthService {
    private final EmployeeRepository employeeRepository;

    public AuthServiceImp(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Optional<Employee> authenticate(String fullName, String employeeId) {
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByFullNameAndEmployeeId(fullName, employeeId));
        return employee;
    }
}
