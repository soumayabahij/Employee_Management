package org.employee.ui.panel;

import java.awt.Component;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ReportEmployeesPanel extends JPanel {
    private JButton generateReportButton;

    public ReportEmployeesPanel() {

        generateReportButton = new JButton("Generate Report");

        add(generateReportButton);

        generateReportButton.addActionListener(e -> generateReport());
    }

    private void generateReport() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/api/employees/report";

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String reportContent = responseEntity.getBody();

                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(null);

                if (option == JFileChooser.APPROVE_OPTION) {
                    try (FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile())) {
                        fos.write(reportContent.getBytes());
                        showPopUp(this, "Report saved successfully.");
                    } catch (Exception e) {
                        showPopUp(this, "Failed to save the report.");
                    }
                }
            } else {
                showPopUp(this,
                    "Failed to generate report.");
            }
        } catch (Exception ex) {
            showPopUp(this,
                "An error occurred while generating the report.");
        }
    }

    private void showPopUp(Component component, String message) {
        JOptionPane.showMessageDialog(component, message);
    }
}
