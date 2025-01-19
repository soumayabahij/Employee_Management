package org.employee.model;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    @JsonProperty("full_name")
    private String fullName;

    @Column(name = "employee_id", unique = true, nullable = false)
    @JsonProperty("employee_id")
    private String employeeId;

    @Column(name = "job_title")
    @JsonProperty("job_title")
    private String jobTitle;

    @Column(name = "department")
    @JsonProperty("department")
    private String department;

    @Column(name = "hire_date")
    @JsonProperty("hire_date")
    private LocalDate hireDate;

    @Column(name = "employment_status")
    @JsonProperty("employment_status")
    private String employmentStatus;

    @Column(name = "contact_info")
    @JsonProperty("contact_info")
    private String contactInfo;

    @Column(name = "address")
    @JsonProperty("address")
    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "employee_roles",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore
    private Set<Role> roles;

    public Employee() {
    }

    public Employee(
        String employeeId,
        String fullName,
        String employmentStatus,
        String department,
        String contactInfo,
        String address,
        LocalDate hireDate,
        String jobTitle,
        Set<Role> roles) {
        this.address = address;
        this.contactInfo = contactInfo;
        this.department = department;
        this.employeeId = employeeId;
        this.employmentStatus = employmentStatus;
        this.fullName = fullName;
        this.hireDate = hireDate;
        this.id = id;
        this.jobTitle = jobTitle;
        this.roles = roles;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public Long getId() {
        return id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}