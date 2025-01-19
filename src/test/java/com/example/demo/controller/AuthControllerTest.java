package com.example.demo.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.Set;

import org.employee.DemoApplication;
import org.employee.model.Employee;
import org.employee.model.Permission;
import org.employee.model.Role;
import org.employee.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    public void should_login() throws Exception {
        Permission permission = new Permission();
        permission.setName("VIEW_DASHBOARD");
        Role role = new Role();
        role.setName("ADMIN");
        role.setPermissions(Set.of(permission));

        Employee employee = new Employee();
        employee.setFullName("Soumaya");
        employee.setEmployeeId("EMP123");
        employee.setRoles(Set.of(role));

        when(authService.authenticate("Soumaya", "EMP123")).thenReturn(Optional.of(employee));

        mockMvc.perform(post("/api/auth/login")
                .param("fullName", "Soumaya")
                .param("employeeId", "EMP123"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("Soumaya"));

        verify(authService, times(1)).authenticate("Soumaya", "EMP123");
    }
}
