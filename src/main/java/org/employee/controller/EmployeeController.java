package org.employee.controller;

import java.time.LocalDate;
import java.util.List;

import org.employee.model.Employee;
import org.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee updatedEmployee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, updatedEmployee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/report")
    public ResponseEntity<String> generateEmployeeReport() {
        List<Employee> employees = employeeService.getAllEmployees();
        StringBuilder report = new StringBuilder("Employee Report\n");
        report.append("ID, Name, Job Title, Department, Hire Date, Employment Status, Contact Info, Address\n");

        for (Employee emp : employees) {
            report.append(emp.getId()).append(", ")
                .append(emp.getFullName()).append(", ")
                .append(emp.getJobTitle()).append(", ")
                .append(emp.getDepartment()).append(", ")
                .append(emp.getHireDate()).append(", ")
                .append(emp.getEmploymentStatus()).append(", ")
                .append(emp.getContactInfo()).append(", ")
                .append(emp.getAddress()).append("\n");
        }

        return ResponseEntity.ok(report.toString());
    }

    @GetMapping("/search")
    public List<Employee> searchEmployees(@RequestParam String query) {
        return employeeService.searchEmployees(query);
    }

    @GetMapping("/filter")
    public List<Employee> filterEmployees(
        @RequestParam(required = false) String employmentStatus,
        @RequestParam(required = false) String department,
        @RequestParam(required = false) String hireDate
    ) {
        LocalDate parsedHireDate = (hireDate != null) ? LocalDate.parse(hireDate) : null;
        return employeeService.filterEmployees(employmentStatus, department, parsedHireDate);
    }
}