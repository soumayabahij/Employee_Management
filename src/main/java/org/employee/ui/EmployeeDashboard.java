package org.employee.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.employee.ui.panel.AddEmployeePanel;
import org.employee.ui.panel.DeleteEmployeePanel;
import org.employee.ui.panel.ViewEmployeesPanel;
import org.employee.ui.panel.EditEmployeePanel;
import org.employee.model.PermissionEnum;

public class EmployeeDashboard {
    private final List<String> permissions;

    public EmployeeDashboard(List<String> permissions) {
        this.permissions = permissions;
    }

    public void manage() {
        JFrame mainFrame = createMainFrame();
        JPanel buttonPanel = createButtonPanel(mainFrame);
        JPanel displayPanel = createDisplayPanel();

        mainFrame.add(buttonPanel, BorderLayout.NORTH);
        mainFrame.add(displayPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private JFrame createMainFrame() {
        JFrame mainFrame = new JFrame("Employee Management System");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        return mainFrame;
    }

    private JPanel createButtonPanel(JFrame mainFrame) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        buttonPanel.add(createButton("Add Employee", PermissionEnum.CREATE, mainFrame));
        buttonPanel.add(createButton("Update Employee", PermissionEnum.UPDATE, mainFrame));
        buttonPanel.add(createButton("Delete Employee", PermissionEnum.DELETE, mainFrame));
        buttonPanel.add(createButton("View Employees", PermissionEnum.READ, mainFrame));

        return buttonPanel;
    }

    private JButton createButton(String label, PermissionEnum permission, JFrame mainFrame) {
        JButton button = new JButton(label);
        button.setEnabled(hasPermission(permission.name()));
        button.addActionListener(e -> {
            JPanel displayPanel = (JPanel) mainFrame.getContentPane().getComponent(1);
            displayPanel.removeAll();
            displayPanel.add(createPanel(permission), BorderLayout.CENTER);
            displayPanel.revalidate();
            displayPanel.repaint();
        });
        return button;
    }

    private JPanel createPanel(PermissionEnum permission) {
        switch (permission) {
            case CREATE:
                AddEmployeePanel addPanel = new AddEmployeePanel();
                addPanel.add();
                return addPanel;
            case UPDATE:
                EditEmployeePanel editPanel = new EditEmployeePanel();
                editPanel.edit();
                return editPanel;
            case DELETE:
                DeleteEmployeePanel deletePanel = new DeleteEmployeePanel();
                deletePanel.delete();
                return deletePanel;
            case READ:
                ViewEmployeesPanel displayPanel = new ViewEmployeesPanel();
                displayPanel.view();
                return displayPanel;
            default:
                return new JPanel();
        }
    }

    private JPanel createDisplayPanel() {
        return new JPanel(new BorderLayout());
    }

    private boolean hasPermission(String permission) {
        return permissions.stream().anyMatch(p -> p.equals(permission));
    }
}