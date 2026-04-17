import javax.swing.*;
import java.awt.*;

public class MemoryCardUI {

    private JFrame frame;
    private JPanel cardPanel;
    private JLabel statusLabel;
    private JButton restartButton;
    private JButton exitButton;

    private final int ROWS = 4;
    private final int COLS = 4;

    public MemoryCardUI() {
        // Create main frame
        frame = new JFrame("Memory Card Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Top status label
        statusLabel = new JLabel("Find all matching pairs!", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        frame.add(statusLabel, BorderLayout.NORTH);

        // Card grid panel (5x5)
        cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(ROWS, COLS, 15, 15)); // rows, cols, hgap, vgap

        // Add placeholder buttons
        for (int i = 0; i < ROWS * COLS; i++) {
            JButton cardButton = new JButton();
            cardButton.setFont(new Font("Arial", Font.BOLD, 24));
            cardButton.setBackground(Color.LIGHT_GRAY);
            cardButton.setFocusPainted(false);
            cardButton.setPreferredSize(new Dimension(100, 140)); // taller than wide
            cardPanel.add(cardButton);
        }

        // Wrap card panel in a center panel to keep grid centered
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerWrapper.add(cardPanel);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.add(centerWrapper, BorderLayout.CENTER);

        // Bottom panel for controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        restartButton = new JButton("Restart");
        exitButton = new JButton("Exit");
        controlPanel.add(restartButton);
        controlPanel.add(exitButton);
        frame.add(controlPanel, BorderLayout.SOUTH);

        // Show frame
        frame.setVisible(true);
    }

    // Getters
    public JPanel getCardPanel() {
        return cardPanel;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public JButton getRestartButton() {
        return restartButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
}