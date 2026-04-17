import javax.swing.*;

public class DifficultySelector {

    public static String getDifficulty() {
        String[] options = { "Easy", "Medium", "Hard" };
        int choice = JOptionPane.showOptionDialog(
                null,
                "Select difficulty level:",
                "Select Difficulty",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1] // default is Medium
        );

        return switch (choice) {
            case 0 -> "easy";
            case 1 -> "medium";
            case 2 -> "hard";
            default -> "medium"; // default if user closes dialog
        };
    }
}