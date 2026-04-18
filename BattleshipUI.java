import java.awt.*;
import javax.swing.*;

public class BattleshipUI {

    private BattleshipGameController game;

    private JFrame frame;

    private JLabel turnLabel;
    private JLabel timerLabel;
    private JLabel p1Score;
    private JLabel p2Score;

    private JButton[][] p1Buttons = new JButton[6][6];
    private JButton[][] p2Buttons = new JButton[6][6];

    private BattleshipTurnTimer currentTimer;

    public BattleshipUI() {

        game = new BattleshipGameController();

        frame = new JFrame("BATTLESHIPS");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // BACKGROUND PANEL
        JPanel bgPanel = new JPanel() {
            Image bg = new ImageIcon("images/battleshipbg.png").getImage();

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        bgPanel.setLayout(new BorderLayout());
        frame.setContentPane(bgPanel);

        // TITLE
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("BATTLESHIPS");
        title.setFont(new Font("Arial", Font.BOLD, 38));
        title.setForeground(Color.BLACK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(Box.createVerticalStrut(25));
        titlePanel.add(title);

        bgPanel.add(titlePanel, BorderLayout.NORTH);

        // MAIN PANEL
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 60, 20, 60);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(createBoard(false), gbc);

        gbc.gridx = 1;

        JPanel centerBox = new JPanel();
        centerBox.setOpaque(false);
        centerBox.setLayout(new BoxLayout(centerBox, BoxLayout.Y_AXIS));

        turnLabel = new JLabel("Player 1 Turn");
        timerLabel = new JLabel("Time: 15");

        turnLabel.setFont(new Font("Arial", Font.BOLD, 22));
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));

        turnLabel.setForeground(Color.BLACK);
        timerLabel.setForeground(Color.RED);

        centerBox.add(turnLabel);
        centerBox.add(Box.createVerticalStrut(10));
        centerBox.add(timerLabel);

        mainPanel.add(centerBox, gbc);

        gbc.gridx = 2;
        mainPanel.add(createBoard(true), gbc);

        bgPanel.add(mainPanel, BorderLayout.CENTER);

        // RESTART BUTTON
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setOpaque(false);

        JButton restart = new JButton("RESTART");
        restart.setPreferredSize(new Dimension(130, 45));
        restart.addActionListener(e -> resetGame());

        bottom.add(restart);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        bgPanel.add(bottom, BorderLayout.SOUTH);

        frame.setVisible(true);

        startTurnTimer();
    }

    public static void main(String[] args) {
        new BattleshipUI(); // 🔥 constructor call
    }

    private JPanel createBoard(boolean isP2) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel label = new JLabel(isP2 ? "Player 2" : "Player 1", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(6, 6, 2, 2));
        grid.setOpaque(false);
        grid.setPreferredSize(new Dimension(300, 300));

        for (int i = 0; i < 36; i++) {
            int row = i / 6;
            int col = i % 6;

            JButton btn = new JButton();
            btn.setBackground(Color.CYAN);

            if (!isP2)
                p1Buttons[row][col] = btn;
            else
                p2Buttons[row][col] = btn;

            btn.addActionListener(e -> {

                if (game.player1Turn && !isP2)
                    return;
                if (!game.player1Turn && isP2)
                    return;

                BattleshipBoard target = game.player1Turn ? game.player2Board : game.player1Board;

                if (target.grid[row][col] == 2 || target.grid[row][col] == 3)
                    return;

                boolean hit = game.attack(row, col);

                btn.setBackground(hit ? Color.RED : Color.GRAY);

                if (!hit)
                    game.player1Turn = !game.player1Turn;

                updateLabels();
                startTurnTimer();

                if (game.isGameOver()) {
                    JOptionPane.showMessageDialog(null,
                            game.player1Turn ? "Player 1 Wins!" : "Player 2 Wins!");
                    currentTimer.stopTimer();
                }
            });

            grid.add(btn);
        }

        panel.add(grid, BorderLayout.CENTER);

        JLabel score = new JLabel("Ships destroyed: 0/3", JLabel.CENTER);
        score.setFont(new Font("Arial", Font.BOLD, 16));

        if (!isP2)
            p1Score = score;
        else
            p2Score = score;

        panel.add(score, BorderLayout.SOUTH);

        return panel;
    }

    private void startTurnTimer() {

        if (currentTimer != null)
            currentTimer.stopTimer();

        currentTimer = new BattleshipTurnTimer(15, new BattleshipTurnTimer.TimerListener() {

            public void onTick(int timeLeft) {
                timerLabel.setText("Time: " + timeLeft);
                timerLabel.setForeground(timeLeft <= 5 ? Color.RED : Color.BLACK);
            }

            public void onTimeout() {
                game.player1Turn = !game.player1Turn;
                updateLabels();
                startTurnTimer();
            }
        });

        currentTimer.start();
    }

    private void resetGame() {

        game = new BattleshipGameController();

        turnLabel.setText("Player 1 Turn");
        p1Score.setText("Ships destroyed: 0/3");
        p2Score.setText("Ships destroyed: 0/3");

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                p1Buttons[i][j].setBackground(Color.CYAN);
                p2Buttons[i][j].setBackground(Color.CYAN);
            }
        }

        startTurnTimer();
    }

    private void updateLabels() {
        turnLabel.setText(game.getTurnText());

        p1Score.setText("Ships destroyed: " + game.getP2Destroyed() + "/3");
        p2Score.setText("Ships destroyed: " + game.getP1Destroyed() + "/3");
    }
}