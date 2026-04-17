import javax.swing.*;
import java.awt.*;

public class FourInARowUI {

    private JFrame frame;
    private JPanel boardPanel;
    private JLabel statusLabel;
    private JButton restartButton;

    private final int ROWS = 6;
    private final int COLS = 7;

    public FourInARowUI() {

        frame = new JFrame("4 IN A ROW");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // ================= TOP =================
        statusLabel = new JLabel("Player 1's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 26));
        frame.add(statusLabel, BorderLayout.NORTH);

        // ================= BOARD =================
        boardPanel = new JPanel(new GridLayout(ROWS, COLS, 15, 15));
        boardPanel.setBackground(new Color(20, 40, 120)); // darker blue
        boardPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Add circular cells
        for (int i = 0; i < ROWS * COLS; i++) {
            boardPanel.add(new CirclePanel());
        }

        // Center board
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(boardPanel);
        frame.add(wrapper, BorderLayout.CENTER);

        // ================= BOTTOM =================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        restartButton = new JButton("Restart");
        bottomPanel.add(restartButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // ================= CUSTOM CIRCLE =================
    class CirclePanel extends JPanel {

        public CirclePanel() {
            setPreferredSize(new Dimension(80, 80));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // white empty slot
            g2.setColor(Color.WHITE);
            g2.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
        }
    }

    // Getters for backend later
    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public JButton getRestartButton() {
        return restartButton;
    }
}