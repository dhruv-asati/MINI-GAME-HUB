import javax.swing.*;
import java.awt.*;

public class HangmanUI {
    private HangmanBackend backend;
    private JLabel wordLabel;
    private JLabel attempts;
    private JLabel wrongLetters;
    private JLabel guessLabel;

    // constructor to set up the UI
    public HangmanUI() {
        JFrame frame = new JFrame("HANGMAN GAME");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(20, 20));
        frame.getContentPane().setBackground(new Color(255, 228, 181)); // light background color
        String selectedDifficulty = DifficultySelector.getDifficulty();

        backend = new HangmanBackend(selectedDifficulty);
        // TITLE
        JLabel title = new JLabel("HANGMAN GAME", JLabel.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 50));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        frame.add(title, BorderLayout.NORTH);

        // MAIN PANEL
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 50, 50));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
        frame.add(mainPanel, BorderLayout.CENTER);
        // LEFT PANEL (Hangman Image)
        JLabel hangmanImageLabel = new JLabel();
        hangmanImageLabel.setHorizontalAlignment(JLabel.CENTER);
        hangmanImageLabel.setVerticalAlignment(JLabel.CENTER);
        mainPanel.setOpaque(false);
        // set initial image
        hangmanImageLabel.setIcon(new ImageIcon("images/hangman-wrong-images/guess0.png")); // your folder and first
        mainPanel.add(hangmanImageLabel); // add to left column

        // RIGHT PANEL
        JPanel rightPanel = new JPanel(new BorderLayout(20, 20));
        rightPanel.setOpaque(false);

        // 🔝 TOP INFO PANEL
        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        infoPanel.setOpaque(false);

        guessLabel = new JLabel("Guess the word:", JLabel.CENTER);
        guessLabel.setFont(new Font("Helvetica", Font.BOLD, 40));

        wordLabel = new JLabel(getUnderscores(backend.getWordToGuess()), JLabel.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 40));

        attempts = new JLabel("Attempts Left: " + backend.attemptsLeft(), JLabel.CENTER);
        attempts.setFont(new Font("Arial", Font.BOLD, 25));

        wrongLetters = new JLabel("Wrong Letters: ", JLabel.CENTER);
        wrongLetters.setFont(new Font("Arial", Font.PLAIN, 20));

        infoPanel.add(guessLabel);
        infoPanel.add(wordLabel);
        infoPanel.add(attempts);
        infoPanel.add(wrongLetters);

        rightPanel.add(infoPanel, BorderLayout.NORTH);

        // LETTERS PANEL
        JPanel lettersPanel = new JPanel(new GridLayout(5, 6, 10, 10));
        lettersPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (char c = 'A'; c <= 'Z'; c++) {
            final char letter = c;
            JButton letterBtn = new JButton(String.valueOf(letter));
            letterBtn.setFont(new Font("Arial", Font.BOLD, 22));
            letterBtn.setFocusPainted(false);
            letterBtn.setBackground(Color.WHITE);

            // Event listener for letter buttons
            letterBtn.addActionListener(e -> {
                letterBtn.setEnabled(false);// disable button after click
                char guessedChar = letter;
                boolean correct = backend.guessLetter(guessedChar); // check guess
                wordLabel.setText(backend.getDisplayWord().replaceAll("", " ").trim()); // update display word
                attempts.setText("Attempts Left: " + backend.attemptsLeft()); // update attempts
                if (!correct) {
                    wrongLetters.setText(wrongLetters.getText() + " " + guessedChar);
                    letterBtn.setBackground(Color.RED); // update wrong letters
                    // update hangman image based on wrong attempts
                    int wrongAttempts = backend.attemptsLeft();
                    hangmanImageLabel
                            .setIcon(new ImageIcon("images/hangman-wrong-images/guess" + (wrongAttempts + 1) + ".png"));
                } else if (correct)
                    letterBtn.setBackground(Color.GREEN); // update correct letters
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
        lettersPanel.setOpaque(false);

        // BOTTOM PANEL
        JPanel bottomPanel = new JPanel();

        JButton resetBtn = new JButton("RESET GAME");
        styleButton(resetBtn);

        // action listener for the reset button
        resetBtn.addActionListener(e -> {
            // resetting the backend
            backend = new HangmanBackend(selectedDifficulty);
            // resetting the UI
            wordLabel.setText(backend.getDisplayWord().replaceAll("", " ").trim());
            attempts.setText("Attempts Left: " + backend.attemptsLeft());
            wrongLetters.setText("Wrong: ");
            hangmanImageLabel.setIcon(new ImageIcon("images/hangman-wrong-images/guess0.png"));

            // resetting letter buttons
            Component[] components = lettersPanel.getComponents();
            for (Component component : components) {
                if (component instanceof JButton) {
                    component.setEnabled(true);
                    component.setBackground(Color.WHITE);
                }
            }
        });
        bottomPanel.setOpaque(false);

        bottomPanel.add(resetBtn);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(rightPanel);
        frame.setVisible(true);
    }

    // BUTTON STYLE
    static void styleButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 20));
        b.setBackground(new Color(30, 144, 255));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);

        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
    }

    // getting underscores
    private static String getUnderscores(String word) {
        return "_ ".repeat(word.length()).trim();
    }
}