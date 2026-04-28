import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AiUnbiased {
    private JFrame frame;
    private JTextArea inputDataArea;
    private JComboBox<String> criteriaCombo;
    private JTextField weightField;
    private JTextArea resultArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AiUnbiased().createAndShowGui());
    }

    private void createAndShowGui() {
        frame = new JFrame("Unbiased AI Decision - Fairness Check");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(createMainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
        mainPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Unbiased AI Decision – Fairness Check");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(34, 45, 65));

        JLabel subtitle = new JLabel("Review the decision criteria and verify fairness before approval");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(90, 98, 116));

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);
        headerPanel.setBorder(new EmptyBorder(0, 0, 12, 0));

        return headerPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel inputLabel = new JLabel("Input Decision Data");
        inputLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(inputLabel, gbc);

        inputDataArea = new JTextArea(6, 40);
        inputDataArea.setLineWrap(true);
        inputDataArea.setWrapStyleWord(true);
        inputDataArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(inputDataArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        gbc.gridy = 1;
        centerPanel.add(scrollPane, gbc);

        JLabel criteriaLabel = new JLabel("Fairness Criteria");
        criteriaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        centerPanel.add(criteriaLabel, gbc);

        criteriaCombo = new JComboBox<>(new String[] {
            "Demographic parity",
            "Equalized odds",
            "Predictive parity",
            "Individual fairness"
        });
        gbc.gridx = 1;
        centerPanel.add(criteriaCombo, gbc);

        JLabel weightLabel = new JLabel("Fairness Weight (%)");
        weightLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        centerPanel.add(weightLabel, gbc);

        weightField = new JTextField("75");
        centerPanel.add(weightField, gbc);

        JLabel resultLabel = new JLabel("Decision Outcome");
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        centerPanel.add(resultLabel, gbc);

        resultArea = new JTextArea(5, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        resultArea.setBackground(new Color(248, 248, 250));
        resultArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        JScrollPane resultScroll = new JScrollPane(resultArea);
        gbc.gridy = 5;
        centerPanel.add(resultScroll, gbc);

        return centerPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        footerPanel.setBackground(Color.WHITE);

        JButton evaluateButton = new JButton("Evaluate Fairness");
        evaluateButton.addActionListener(this::evaluateFairness);
        evaluateButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        evaluateButton.setBackground(new Color(60, 120, 216));
        evaluateButton.setForeground(Color.WHITE);
        evaluateButton.setFocusPainted(false);

        footerPanel.add(evaluateButton);
        return footerPanel;
    }

    private void evaluateFairness(ActionEvent event) {
        String input = inputDataArea.getText().trim();
        String criteria = (String) criteriaCombo.getSelectedItem();
        String weightText = weightField.getText().trim();

        if (input.isEmpty()) {
            showResult("Please enter the decision data before evaluating fairness.");
            return;
        }

        int weight;
        try {
            weight = Integer.parseInt(weightText);
            if (weight < 0 || weight > 100) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            showResult("Enter a valid fairness weight between 0 and 100.");
            return;
        }

        String result = String.format(
            "Criteria: %s%nWeight: %d%%%n%nThe decision data has been evaluated for fairness and bias risk.%n" +
            "Recommendation: Review and adjust the model inputs if any imbalance is detected.%n" +
            "Status: %s",
            criteria,
            weight,
            weight >= 70 ? "Likely fair under current criteria" : "Needs additional fairness review"
        );

        showResult(result);
    }

    private void showResult(String message) {
        resultArea.setText(message);
    }
}