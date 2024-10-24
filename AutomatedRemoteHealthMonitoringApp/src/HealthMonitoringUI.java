import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HealthMonitoringUI extends JFrame {

    private JComboBox<String> patientDropdown;
    private JTextField ecgField, heartRateField, tempField, airflowField, positionField;
    private JLabel ecgErrorLabel, heartRateErrorLabel, tempErrorLabel, airflowErrorLabel, positionErrorLabel;

    public HealthMonitoringUI() {
        setTitle("Automated Health Monitoring System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Fixed layout

        // Left Panel for Graphs and Visual Representation
        JPanel graphPanel = new JPanel();
        graphPanel.setBounds(10, 10, 380, 540);
        graphPanel.setBackground(new Color(200, 230, 240)); // Lighter blue for graphs
        graphPanel.setBorder(BorderFactory.createTitledBorder("Visual Data Representation"));
        graphPanel.setLayout(null);

        // Adding fixed-size blocks for visual representation
        JPanel ecgBlock = createDataBlock("ECG Graph", 10, 20);
        JPanel heartRateBlock = createDataBlock("Heart Rate Graph", 200, 20);
        JPanel tempBlock = createDataBlock("Temperature Graph", 10, 200);
        JPanel airflowBlock = createDataBlock("Airflow Graph", 200, 200);

        // Add blocks to the graph panel
        graphPanel.add(ecgBlock);
        graphPanel.add(heartRateBlock);
        graphPanel.add(tempBlock);
        graphPanel.add(airflowBlock);
        add(graphPanel);

        // Right Panel for Controls
        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(420, 10, 350, 540);
        controlPanel.setBackground(new Color(245, 255, 250)); // Very light green
        controlPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
        controlPanel.setLayout(null);

        // Dropdown for Patient Selection
        JLabel patientLabel = new JLabel("Select Patient:");
        patientLabel.setBounds(10, 30, 150, 25);
        controlPanel.add(patientLabel);

        patientDropdown = new JComboBox<>(new String[]{"", "Patient 1", "Patient 2", "Patient 3"});
        patientDropdown.setBounds(10, 60, 150, 25);
        styleComponent(patientDropdown);
        controlPanel.add(patientDropdown);

        // Input fields with error labels
        ecgField = createRoundedField("Enter ECG (mV):", controlPanel, 100, -1, 2, "Valid range: -1 to 2 mV");
        heartRateField = createRoundedField("Enter Heart Rate (BPM):", controlPanel, 180, 0, 300, "Valid range: 0 to 300 BPM");
        tempField = createRoundedField("Enter Temperature (°C):", controlPanel, 260, 34, 42, "Valid range: 34 to 42 °C");
        airflowField = createRoundedField("Enter Airflow (L/min):", controlPanel, 340, 0, 60, "Valid range: 0 to 60 L/min");
        positionField = createRoundedField("Enter Position (Degrees):", controlPanel, 420, 0, 360, "Valid range: 0 to 360°");

        // Button to simulate data entry
        JButton enterDataButton = createRoundedButton("Enter Data");
        enterDataButton.setBounds(10, 500, 150, 40);
        enterDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    enterData();
                }
            }
        });
        controlPanel.add(enterDataButton);

        // Button to show health summary
        JButton healthSummaryButton = createRoundedButton("Get Health Status");
        healthSummaryButton.setBounds(170, 500, 150, 40);
        healthSummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    showHealthSummary();
                }
            }
        });
        controlPanel.add(healthSummaryButton);

        add(controlPanel);
        setVisible(true);
    }

    // Create a data block panel for visual representation
    private JPanel createDataBlock(String title, int x, int y) {
        JPanel block = new JPanel();
        block.setBackground(new Color(255, 255, 255)); // White background
        block.setBorder(BorderFactory.createTitledBorder(title));
        block.setBounds(x, y, 160, 160); // Fixed size block
        return block;
    }

    // Create a JTextField with rounded corners and error label
    private JTextField createRoundedField(String labelText, JPanel panel, int y, double min, double max, String rangeText) {
        JLabel label = new JLabel(labelText);
        label.setBounds(10, y, 200, 25);
        panel.add(label);

        JTextField field = new JTextField();
        field.setBounds(10, y + 30, 150, 30);
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        styleComponent(field);
        panel.add(field);

        JLabel errorLabel = new JLabel();
        errorLabel.setBounds(10, y + 65, 300, 25);
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(errorLabel);

        if (labelText.contains("ECG")) {
            ecgErrorLabel = errorLabel;
        } else if (labelText.contains("Heart Rate")) {
            heartRateErrorLabel = errorLabel;
        } else if (labelText.contains("Temperature")) {
            tempErrorLabel = errorLabel;
        } else if (labelText.contains("Airflow")) {
            airflowErrorLabel = errorLabel;
        } else if (labelText.contains("Position")) {
            positionErrorLabel = errorLabel;
        }

        return field;
    }

    // Create a JButton with rounded corners
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 153, 153)); // Teal color
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102), 1, true));
        return button;
    }

    // Method to style components for a consistent look
    private void styleComponent(JComponent comp) {
        comp.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    // Method to validate user inputs and display error messages
    private boolean validateInput() {
        boolean valid = true;
        resetErrorLabels();

        try {
            double ecg = Double.parseDouble(ecgField.getText());
            if (ecg < -1 || ecg > 2) {
                ecgErrorLabel.setText("Invalid ECG. Valid range: -1 to 2 mV");
                valid = false;
            }
        } catch (Exception e) {
            ecgErrorLabel.setText("Invalid input. Valid range: -1 to 2 mV");
            valid = false;
        }

        try {
            int heartRate = Integer.parseInt(heartRateField.getText());
            if (heartRate < 0 || heartRate > 300) {
                heartRateErrorLabel.setText("Invalid Heart Rate. Valid range: 0 to 300 BPM");
                valid = false;
            }
        } catch (Exception e) {
            heartRateErrorLabel.setText("Invalid input. Valid range: 0 to 300 BPM");
            valid = false;
        }

        try {
            double temp = Double.parseDouble(tempField.getText());
            if (temp < 34 || temp > 42) {
                tempErrorLabel.setText("Invalid Temperature. Valid range: 34 to 42 °C");
                valid = false;
            }
        } catch (Exception e) {
            tempErrorLabel.setText("Invalid input. Valid range: 34 to 42 °C");
            valid = false;
        }

        try {
            double airflow = Double.parseDouble(airflowField.getText());
            if (airflow < 0 || airflow > 60) {
                airflowErrorLabel.setText("Invalid Airflow. Valid range: 0 to 60 L/min");
                valid = false;
            }
        } catch (Exception e) {
            airflowErrorLabel.setText("Invalid input. Valid range: 0 to 60 L/min");
            valid = false;
        }

        try {
            int position = Integer.parseInt(positionField.getText());
            if (position < 0 || position > 360) {
                positionErrorLabel.setText("Invalid Position. Valid range: 0 to 360°");
                valid = false;
            }
        } catch (Exception e) {
            positionErrorLabel.setText("Invalid input. Valid range: 0 to 360°");
            valid = false;
        }

        return valid;
    }

    // Method to reset error labels
    private void resetErrorLabels() {
        ecgErrorLabel.setText("");
        heartRateErrorLabel.setText("");
        tempErrorLabel.setText("");
        airflowErrorLabel.setText("");
        positionErrorLabel.setText("");
    }

    // Method to simulate entering data
    private void enterData() {
        String ecg = ecgField.getText();
        String heartRate = heartRateField.getText();
        String temp = tempField.getText();
        String airflow = airflowField.getText();
        String position = positionField.getText();

        // Display entered data in console (for now)
        System.out.println("ECG: " + ecg + " mV");
        System.out.println("Heart Rate: " + heartRate + " BPM");
        System.out.println("Temperature: " + temp + " °C");
        System.out.println("Airflow: " + airflow + " L/min");
        System.out.println("Position: " + position + " Degrees");
    }

    // Method to show health summary based on inputs
    private void showHealthSummary() {
        StringBuilder summary = new StringBuilder("Health Status:\n");

        double ecg = Double.parseDouble(ecgField.getText());
        int heartRate = Integer.parseInt(heartRateField.getText());
        double temp = Double.parseDouble(tempField.getText());
        double airflow = Double.parseDouble(airflowField.getText());
        int position = Integer.parseInt(positionField.getText());

        // Analyze input and build summary
        summary.append("ECG: ").append(ecg < -0.5 || ecg > 1.0 ? "Abnormal" : "Normal").append("\n");
        summary.append("Heart Rate: ").append(heartRate > 100 ? "High" : heartRate < 60 ? "Low" : "Normal").append("\n");
        summary.append("Temperature: ").append(temp > 37.5 ? "Fever" : temp < 36.1 ? "Hypothermia" : "Normal").append("\n");
        summary.append("Airflow: ").append(airflow < 12 ? "Low Airflow" : "Normal").append("\n");
        summary.append("Position: ").append(position > 180 ? "Unusual Position" : "Normal Position").append("\n");

        // Add suggestions based on combinations
        if (heartRate > 100 && temp > 37.5) {
            summary.append("\nPossible cause: Infection. Suggested care: Rest and consult a doctor.");
        } else if (ecg > 1.0 || ecg < -0.5) {
            summary.append("\nPossible cause: Arrhythmia. Suggested care: Seek medical advice.");
        } else if (airflow < 12) {
            summary.append("\nPossible cause: Respiratory issue. Suggested care: Monitor breathing closely.");
        } else {
            summary.append("\nSuggested care: Monitor regularly.");
        }

        // Display summary in a dialog
        JOptionPane.showMessageDialog(this, summary.toString(), "Health Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HealthMonitoringUI());
    }
}
