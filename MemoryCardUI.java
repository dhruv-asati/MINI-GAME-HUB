import java.awt.*;
import java.util.List;
import javax.swing.*;

public class MemoryCardUI {

    private JFrame frame;
    private JPanel cardPanel;
    private JLabel timerLabel;

    private int ROWS;
    private int COLS;

    private GameLogic game;
    private ImageIcon coverIcon;

    private Timer gameTimer;
    private int timeLeft;
    private int multiplier;

    public MemoryCardUI() {

        frame = new JFrame("Fruit Match"); // 🔥 window title updated
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 🔥 BACKGROUND IMAGE
        JPanel background = new JPanel() {
            private Image bg = new ImageIcon("images/memorybg.png").getImage();

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        background.setLayout(new BorderLayout());
        frame.setContentPane(background);

        // 🔥 TITLE UPDATED
        JLabel title = new JLabel("Fruit Match", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 34));
        title.setForeground(new Color(180, 220, 255));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));
        topPanel.add(title, BorderLayout.CENTER);

        frame.add(topPanel, BorderLayout.NORTH);

        // 🔥 CENTER
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        JPanel gameContainer = new JPanel();
        gameContainer.setLayout(new BoxLayout(gameContainer, BoxLayout.Y_AXIS));
        gameContainer.setOpaque(false);

        cardPanel = new JPanel();
        cardPanel.setOpaque(false);
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        timerLabel = new JLabel("Time Left: 0", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gameContainer.add(cardPanel);
        gameContainer.add(Box.createRigidArea(new Dimension(0, 18)));
        gameContainer.add(timerLabel);

        centerWrapper.add(gameContainer);
        frame.add(centerWrapper, BorderLayout.CENTER);

        // 🔥 RESTART BUTTON
        JButton restartButton = new JButton("Restart");
        restartButton.setPreferredSize(new Dimension(120, 35));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        bottomPanel.add(restartButton);

        restartButton.addActionListener(e -> chooseDifficulty());

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // 🔥 COVER TILE
        coverIcon = new ImageIcon("images/TILE.png");
        Image img = coverIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        coverIcon = new ImageIcon(img);

        chooseDifficulty();

        frame.setVisible(true);
    }

    private void chooseDifficulty() {

        String difficulty = DifficultySelector.getDifficulty();

        if (difficulty.equals("easy")) {
            ROWS = 4;
            COLS = 4;
            timeLeft = 120;
            multiplier = 1;

        } else if (difficulty.equals("medium")) {
            ROWS = 4;
            COLS = 5;
            timeLeft = 150;
            multiplier = 2;

        } else { // hard
            ROWS = 6;
            COLS = 6;
            timeLeft = 180;
            multiplier = 3;
        }

        setupGame();
        startTimer();
    }

    private void setupGame() {

        cardPanel.removeAll();

        cardPanel.setLayout(new GridLayout(ROWS, COLS, 16, 16));
        cardPanel.setMaximumSize(new Dimension(450, 450));

        int total = ROWS * COLS;

        List<CardData> cards = ImageLoader.getCards(total, 60);
        game = new GameLogic(cards, coverIcon, this::handleWin);

        for (int i = 0; i < total; i++) {

            JButton card = new JButton();

            card.setPreferredSize(new Dimension(65, 65));
            card.setBackground(new Color(48, 0, 72));
            card.setBorder(BorderFactory.createEmptyBorder());
            card.setFocusPainted(false);

            card.setIcon(coverIcon);

            card.putClientProperty("revealed", false);
            card.putClientProperty("matched", false);

            int index = i;

            card.addActionListener(e -> game.handleClick(card, index));

            cardPanel.add(card);
        }

        cardPanel.revalidate();
        cardPanel.repaint();
    }

    private void startTimer() {

        timerLabel.setText("Time Left: " + timeLeft);

        if (gameTimer != null)
            gameTimer.stop();

        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft);

            if (timeLeft <= 0) {
                gameTimer.stop();
                handleLoss();
            }
        });

        gameTimer.start();
    }

    private void handleWin() {

        gameTimer.stop();

        int baseScore = timeLeft * multiplier;
        int pairs = (ROWS * COLS) / 2;
        int bonus = pairs * 10;

        int totalScore = baseScore + bonus;

        JOptionPane.showMessageDialog(frame,
                "🎉 You Win!\n\n" +
                        "Time Score: " + baseScore +
                        "\nPair Bonus: " + bonus +
                        "\nTotal Score: " + totalScore,
                "Victory",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleLoss() {

        JOptionPane.showMessageDialog(frame,
                "⏰ Time's up!\nYou Lost!",
                "Game Over",
                JOptionPane.ERROR_MESSAGE);
    }

}