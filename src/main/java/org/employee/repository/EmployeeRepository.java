package org.employee.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.employee.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e " +
        "WHERE LOWER(e.fullName) LIKE LOWER(CONCAT('%', :query, '%')) " +
        "OR LOWER(e.employeeId) LIKE LOWER(CONCAT('%', :query, '%')) " +
        "OR LOWER(e.department) LIKE LOWER(CONCAT('%', :query, '%')) " +
        "OR LOWER(e.jobTitle) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Employee> searchEmployees(@Param("query") String query);

    @Query("SELECT e FROM Employee e " +
        "WHERE (:employmentStatus IS NULL OR e.employmentStatus = :employmentStatus) " +
        "AND (:department IS NULL OR e.department = :department) " +
        "AND (:hireDate IS NULL OR e.hireDate = :hireDate)")
    List<Employee> filterEmployees(@Param("employmentStatus") String employmentStatus,
                                   @Param("department") String department,
                                   @Param("hireDate") LocalDate hireDate);

    Employee findByFullNameAndEmployeeId(String fullName, String employeeId);

    Employee findByEmployeeId(String employeeId);
}