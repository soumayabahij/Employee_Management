# Employee Management System

## Description

The Employee Management System is a Spring Boot application designed to manage employee records efficiently. It provides RESTful APIs to authenticate, create, read, update, delete, search, filter and generate report for employees.

## Features

A user of the system is an employee that has a role (HR, ADMIN,MANAGER), each role has a list of permissions (CREATE, READ, DELETE, UPDATE)
Based on the permissions of the user, he can interact with the system, if the user doesn't have a specific permission, the related button going to be disabled
- **Employee CRUD Operations**: Create, Read, Update, and Delete employee records.
- **Employee search and filtering Operations**: Allow users to search for employees by name, ID, department, or job title and provide filtering options based on employment status, department, and hire date.
- **Employee report Operation**: generate a report of the list of employee into a file
- **RESTful API**: Exposes endpoints for interacting with employee data.
- **Unit/Integration Testing**: Comprehensive unit tests using JUnit and Mockito and Spring Test.

## Technologies Used

This project utilizes a variety of technologies and frameworks to provide a robust and efficient solution. The following tools and libraries were employed:
- **Java 17**
- **Spring Boot**
- **Spring MVC**
- **Spring DATA JPA**
- **Maven**
- **Jackson Annotations**
- **Spring Test**
- **JUnit 5**
- **Mockito**
- **RestTemplate**
- **Hibernate**
- **Oracle Database**
- **Docker**
- **MigLayout**
- **Postman**

## Getting Started

### Prerequisites
- Java 17
- Maven

### Docker Image
(ubuntu)

docker pull gvenzl/oracle-xe:18.4.0

docker run -d --name oracle-xe \
-p 1521:1521 -p 5500:5500 \
-e ORACLE_PASSWORD=test_test \
gvenzl/oracle-xe:18.4.0

docker ps

docker start {{image_ID}}

### Setup Project

Clone the repository: https://github.com/soumayabahij/Employee_Management.git

mvn clean install

### Some SQL Commands to Add Roles, Permissions and Employees

Employee to Role:
The Employee class has a Set<Role> to represent the roles assigned to an employee.
The Role class has a Set<Employee> to represent all employees assigned to that role.
This is a Many-to-Many relationship, managed through the employee_roles join table.

Role to Permission:
The Role class has a Set<Permission> to represent the permissions granted to that role.
The Permission class has a Set<Role> to represent all roles that have that permission.
This is also a Many-to-Many relationship, managed through the role_permissions join table.

--------------------------------- table permissions
- INSERT INTO permissions (name) VALUES ('READ');
- INSERT INTO permissions (name) VALUES ('UPDATE');
- INSERT INTO permissions (name) VALUES ('DELETE');
- INSERT INTO permissions (name) VALUES ('CREATE');


----------------------------------- table roles

- INSERT INTO roles (name) VALUES ('ADMIN');
- INSERT INTO roles (name) VALUES ('HR');

------------------------------------ table employees

- INSERT INTO employees (full_name, employee_id, job_title, department, hire_date, employment_status, contact_info, address)
VALUES ('test', 'EMP0010', 'IT', 'IT', SYSDATE, 'CDD', 'test@yopmail.com', '26, cadem');

- INSERT INTO employees (full_name, employee_id, job_title, department, hire_date, employment_status, contact_info, address)
VALUES ('HR', 'EMP009', 'HR', 'Management', SYSDATE, 'CDI', 'rh@yopmail.com', '26, cadem');

- INSERT INTO employees (full_name, employee_id, job_title, department, hire_date, employment_status, contact_info, address)
VALUES ('HR', 'EMP009', 'HR', 'HR', SYSDATE, 'CDI', 'rh@yopmail.com', '26, cadem');


------------------------------------ table Employee Roles

- INSERT INTO employee_roles (employee_id, role_id) VALUES (1, 1);
- INSERT INTO employee_roles (employee_id, role_id) VALUES (2, 2);


------------------------------------ table  Role Permissions

- INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 1); -- ADMIN -> READ
- INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 2); -- ADMIN -> WRITE
- INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 3); -- ADMIN -> DELETE
- INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 4); -- ADMIN -> READ