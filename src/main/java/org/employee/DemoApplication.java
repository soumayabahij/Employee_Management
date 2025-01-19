package org.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.employee.ui.EmployeeLogin;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(DemoApplication.class);
        new EmployeeLogin();
    }
}
