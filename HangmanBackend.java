import java.util.*;

public class HangmanBackend {
    private String wordToGuess;
    private char[] displayWord;
    private int attemptsLeft = 8;
    private String guessedLetters = "";

    public HangmanBackend(String difficulty) {
        try {
            String word = WordList.getRandomWord(difficulty);
            if (word == null || word.isEmpty()) {
                throw new IllegalArgumentException("Word list returned invalid word");
            }
            this.wordToGuess = word.toLowerCase();
        } catch (Exception e) {
            System.out.println("Error loading word: " + e.getMessage());
            // fallback word (so game never crashes)
            this.wordToGuess = "default";
        }
        this.displayWord = new char[wordToGuess.length()];
        Arrays.fill(displayWord, '_');
    }

    public boolean guessLetter(char letter) {
        letter = Character.toLowerCase(letter);
        if (guessedLetters.indexOf(letter) != -1) {
            return false; // Letter already guessed
        }
        guessedLetters += letter;
        boolean correctGuess = false;
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == letter) {
                displayWord[i] = letter;
                correctGuess = true;
            }
        }
        if (!correctGuess) {
            attemptsLeft--;
        }
        return correctGuess;
    }

    public int attemptsLeft() {
        return attemptsLeft;
    }

    public boolean isGameOver() {
        return attemptsLeft == 0 || new String(displayWord).equals(wordToGuess);
    }

    public String getDisplayWord() {
        return new String(displayWord);
    }

    public boolean isWin() {
        return new String(displayWord).equals(wordToGuess);
    }

    public String getWordToGuess() {
        return wordToGuess;
    }
}
