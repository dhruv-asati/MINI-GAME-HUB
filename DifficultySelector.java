import javax.swing.*;

public class DifficultySelector {

    public static String getDifficulty() {
        String[] options = { "Easy", "Medium", "Hard" };

        int choice;

        while (true) {
            choice = JOptionPane.showOptionDialog(
                    null,
                    "Select difficulty level:",
                    "Select Difficulty",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1] // Medium selected by default
            );

            if (choice == -1) {
                // user closed dialog → ask again (game won't start)
                JOptionPane.showMessageDialog(
                        null,
                        "You must select a difficulty to start the game!");
            } else {
                break; // valid selection
            }
        }

        return switch (choice) {
            case 0 -> "easy";
            case 1 -> "medium";
            case 2 -> "hard";
            default -> "medium";
        };
    }
}