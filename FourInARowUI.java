import java.awt.*;
import javax.swing.*;

public class FourInARowUI {

    private JFrame frame;
    private JPanel boardPanel;
    private JLabel titleLabel;
    private JLabel turnLabel;
    private JLabel timerLabel;
    private JButton restartButton;

    private final int ROWS = 6;
    private final int COLS = 7;

    private FourInARowBackend game = new FourInARowBackend();
    private CirclePanel[][] cells = new CirclePanel[ROWS][COLS];

    private boolean gameOver = false;

    private Timer turnTimer;
    private int timeLeft = 15;

    public FourInARowUI() {

        frame = new JFrame("4 IN A ROW");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel bgPanel = new BackgroundPanel("images/fourinarowbg.png");
        bgPanel.setLayout(new BorderLayout());
        frame.setContentPane(bgPanel);

        // 🔥 TITLE
        titleLabel = new JLabel("FOUR IN A ROW", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        titleLabel.setForeground(Color.WHITE);
        bgPanel.add(titleLabel, BorderLayout.NORTH);

        // 🔥 CENTER PANEL
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // 🔥 INCREASED FONT SIZE HERE
        turnLabel = new JLabel("", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 34)); // was ~26
        turnLabel.setForeground(Color.WHITE);
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        timerLabel = new JLabel("", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 26)); // was ~20
        timerLabel.setForeground(new Color(203, 213, 245)); // softer white
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(turnLabel);
        centerPanel.add(timerLabel);
        centerPanel.add(Box.createVerticalStrut(20));

        int gap = 10;

        boardPanel = new JPanel(new GridLayout(ROWS, COLS, gap, gap));
        boardPanel.setBackground(new Color(11, 31, 91)); // improved palette
        boardPanel.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));

        for (int i = 0; i < ROWS * COLS; i++) {
            int row = i / COLS;
            int col = i % COLS;

            CirclePanel cell = new CirclePanel();
            cells[row][col] = cell;

            int finalCol = col;

            cell.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    handleMove(finalCol);
                }
            });

            boardPanel.add(cell);
        }

        JPanel boardWrapper = new JPanel(new GridBagLayout());
        boardWrapper.setOpaque(false);
        boardWrapper.add(boardPanel);

        centerPanel.add(boardWrapper);
        bgPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);

        restartButton = new JButton("Restart");
        restartButton.setPreferredSize(new Dimension(120, 45));
        restartButton.setFont(new Font("Arial", Font.BOLD, 14));
        restartButton.setBackground(Color.RED);
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);

        bottomPanel.add(restartButton);
        bgPanel.add(bottomPanel, BorderLayout.SOUTH);

        restartButton.addActionListener(e -> {
            game.reset();
            gameOver = false;
            resetBoardUI();
            startTurnTimer();
        });

        frame.setVisible(true);
        startTurnTimer();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FourInARowUI());
    }

    private void handleMove(int col) {

        if (gameOver)
            return;

        try {
            int row = game.dropPiece(col);

            CirclePanel cell = cells[row][col];

            if (game.getCurrentPlayer() == 1) {
                cell.setColor(Color.RED);
            } else {
                cell.setColor(Color.YELLOW);
            }

            if (game.checkWin(row, col)) {
                turnLabel.setText("PLAYER " + game.getCurrentPlayer() + " WINS!");
                timerLabel.setText("");
                turnLabel.setForeground(new Color(13, 255, 86));
                turnTimer.stop();
                gameOver = true;
            } else {
                game.switchPlayer();
                startTurnTimer();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
    }

    private void startTurnTimer() {
        timeLeft = 15;

        if (turnTimer != null) {
            turnTimer.stop();
        }

        turnTimer = new Timer(1000, e -> {
            timeLeft--;
            updateLabels();

            if (timeLeft <= 0) {
                turnTimer.stop();
                handleTimeout();
            }
        });

        updateLabels();
        turnTimer.start();
    }

    private void handleTimeout() {
        if (gameOver)
            return;

        game.switchPlayer();
        timeLeft = 15;
        startTurnTimer();
    }

    private void updateLabels() {
        turnLabel.setText("Player " + game.getCurrentPlayer() + "'s Turn");
        turnLabel.setForeground(Color.white);
        timerLabel.setText("Time Left: " + timeLeft + "s");

        if (timeLeft <= 5) {
            timerLabel.setForeground(Color.RED);
        } else {
            timerLabel.setForeground(new Color(203, 213, 245));
        }
    }

    private void resetBoardUI() {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                cells[i][j].setColor(Color.WHITE);
    }

    class BackgroundPanel extends JPanel {
        private Image bg;

        public BackgroundPanel(String path) {
            bg = new ImageIcon(path).getImage();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }

    class CirclePanel extends JPanel {

        private Color color = Color.WHITE;

        public CirclePanel() {
            setPreferredSize(new Dimension(65, 65));
            setOpaque(false);
        }

        public void setColor(Color c) {
            color = c;
            repaint();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int size = Math.min(getWidth(), getHeight());

            g2.setColor(color);
            g2.fillOval(
                    (getWidth() - size) / 2,
                    (getHeight() - size) / 2,
                    size,
                    size);
        }
    }
}