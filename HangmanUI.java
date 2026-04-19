import javax.swing.*;
import java.awt.*;

public class HangmanUI {

    private HangmanBackend backend;
    private JLabel wordLabel;
    private JLabel attempts;
    private JLabel wrongLetters;
    private JLabel guessLabel;

    public HangmanUI() {

        JFrame frame = new JFrame("HANGMAN GAME");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ================= BACKGROUND =================
        BackgroundPanel background = new BackgroundPanel();
        background.setLayout(new BorderLayout(20, 20));
        frame.setContentPane(background);

        String selectedDifficulty = DifficultySelector.getDifficulty();
        backend = new HangmanBackend(selectedDifficulty);

        // ================= TITLE =================
        JLabel title = new JLabel("HANGMAN", JLabel.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 50));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        title.setForeground(Color.WHITE);

        background.add(title, BorderLayout.NORTH);

        // ================= MAIN =================
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 50, 50));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        background.add(mainPanel, BorderLayout.CENTER);

        // ================= LEFT IMAGE =================
        JLabel hangmanImageLabel = new JLabel();
        hangmanImageLabel.setHorizontalAlignment(JLabel.CENTER);

        hangmanImageLabel.setIcon(loadImage("/images/hangman-wrong-images/guess0.png"));

        mainPanel.add(hangmanImageLabel);

        // ================= RIGHT PANEL =================
        JPanel rightPanel = new JPanel(new BorderLayout(20, 20));
        rightPanel.setOpaque(false);

        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        infoPanel.setOpaque(false);

        guessLabel = new JLabel("Guess the word:", JLabel.CENTER);
        guessLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
        guessLabel.setForeground(Color.WHITE);

        wordLabel = new JLabel(getUnderscores(backend.getWordToGuess()), JLabel.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 40));
        wordLabel.setForeground(Color.WHITE);

        attempts = new JLabel("Attempts Left: " + backend.attemptsLeft(), JLabel.CENTER);
        attempts.setFont(new Font("Arial", Font.BOLD, 25));
        attempts.setForeground(Color.WHITE);

        wrongLetters = new JLabel("Wrong Letters: ", JLabel.CENTER);
        wrongLetters.setFont(new Font("Arial", Font.PLAIN, 20));
        wrongLetters.setForeground(Color.WHITE);

        infoPanel.add(guessLabel);
        infoPanel.add(wordLabel);
        infoPanel.add(attempts);
        infoPanel.add(wrongLetters);

        rightPanel.add(infoPanel, BorderLayout.NORTH);

        // ================= LETTERS =================
        JPanel lettersPanel = new JPanel(new GridLayout(5, 6, 10, 10));
        lettersPanel.setOpaque(false);
        lettersPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (char c = 'A'; c <= 'Z'; c++) {

            final char letter = c;

            JButton letterBtn = new JButton(String.valueOf(letter));
            letterBtn.setFont(new Font("Arial", Font.BOLD, 22));
            letterBtn.setFocusPainted(false);
            letterBtn.setBackground(Color.WHITE);

            letterBtn.addActionListener(e -> {

                letterBtn.setEnabled(false);

                boolean correct = backend.guessLetter(letter);

                wordLabel.setText(backend.getDisplayWord().replaceAll("", " ").trim());
                attempts.setText("Attempts Left: " + backend.attemptsLeft());

                if (!correct) {

                    wrongLetters.setText(wrongLetters.getText() + " " + letter);
                    letterBtn.setBackground(Color.RED);

                    int wrongAttempts = backend.attemptsLeft();

                    hangmanImageLabel.setIcon(loadImage(
                            "/images/hangman-wrong-images/guess" + (wrongAttempts + 1) + ".png"));

                } else {
                    letterBtn.setBackground(Color.GREEN);
                }

                if (backend.isGameOver()) {

                    String message = backend.isWin()
                            ? "Congratulations! You guessed the word: " + backend.getWordToGuess()
                            : "Game Over! The word was: " + backend.getWordToGuess();

                    JOptionPane.showMessageDialog(null, message);
                }
            });

            lettersPanel.add(letterBtn);
        }

        rightPanel.add(lettersPanel, BorderLayout.CENTER);

        // ================= RESET =================
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);

        JButton resetBtn = new JButton("RESET GAME");
        styleButton(resetBtn);

        resetBtn.addActionListener(e -> {

            backend = new HangmanBackend(selectedDifficulty);

            wordLabel.setText(getUnderscores(backend.getWordToGuess()));
            attempts.setText("Attempts Left: " + backend.attemptsLeft());
            wrongLetters.setText("Wrong: ");

            hangmanImageLabel.setIcon(loadImage("/images/hangman-wrong-images/guess0.png"));

            Component[] components = lettersPanel.getComponents();
            for (Component comp : components) {
                if (comp instanceof JButton) {
                    comp.setEnabled(true);
                    comp.setBackground(Color.WHITE);
                }
            }
        });

        bottomPanel.add(resetBtn);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(rightPanel);

        frame.setVisible(true);
    }

    // ================= SAFE IMAGE LOADER =================
    private ImageIcon loadImage(String path) {

        java.net.URL url = getClass().getResource(path);

        if (url != null) {
            return new ImageIcon(url);
        } else {
            System.out.println("Missing: " + path);
            return new ImageIcon();
        }
    }

    // ================= BACKGROUND =================
    static class BackgroundPanel extends JPanel {

        Image img;

        public BackgroundPanel() {

            java.net.URL url = getClass().getResource("/images/hangmanbg.jpeg");

            if (url != null) {
                img = new ImageIcon(url).getImage();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            if (img != null) {
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // ================= STYLE =================
    static void styleButton(JButton b) {

        b.setFont(new Font("Arial", Font.BOLD, 20));
        b.setBackground(new Color(30, 144, 255));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);

        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
    }

    // ================= HELPER =================
    private static String getUnderscores(String word) {
        return "_ ".repeat(word.length()).trim();
    }
}