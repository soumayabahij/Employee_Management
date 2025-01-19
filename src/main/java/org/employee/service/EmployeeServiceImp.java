package org.employee.service;

import java.time.LocalDate;
import java.util.List;

import org.employee.repository.EmployeeRepository;
import org.employee.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImp implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImp(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(String id) {
        return employeeRepository.findByEmployeeId(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(String id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);
        if (!updatedEmployee.getFullName().equals("")) {
            existingEmployee.setFullName(updatedEmployee.getFullName());
        }
        if (!updatedEmployee.getJobTitle().equals("")) {
            existingEmployee.setJobTitle(updatedEmployee.getJobTitle());
        }
        if (!updatedEmployee.getDepartment().equals("")) {
            existingEmployee.setDepartment(updatedEmployee.getDepartment());
        }
        if (updatedEmployee.getHireDate() != null) {
            existingEmployee.setHireDate(updatedEmployee.getHireDate());
        }
        if (!updatedEmployee.getEmploymentStatus().equals("")) {
            existingEmployee.setEmploymentStatus(updatedEmployee.getEmploymentStatus());
        }
        if (!updatedEmployee.getContactInfo().equals("")) {
            existingEmployee.setContactInfo(updatedEmployee.getContactInfo());
        }
        if (!updatedEmployee.getAddress().equals("")) {
            existingEmployee.setAddress(updatedEmployee.getAddress());
        }
        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(String id) {
        Employee employee = employeeRepository.findByEmployeeId(id);
        employeeRepository.deleteById(employee.getId());
    }

    public List<Employee> searchEmployees(String query) {
        return employeeRepository.searchEmployees(query);
    }

    public List<Employee> filterEmployees(String employmentStatus, String department, LocalDate hireDate) {
        return employeeRepository.filterEmployees(employmentStatus, department, hireDate);
    }
}
