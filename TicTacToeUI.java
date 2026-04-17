import java.awt.*;
import javax.swing.*;

// Custom panel to display background image
class BackgroundPanel extends JPanel {
    private Image bgImage; // stores background image

    public BackgroundPanel(String path) {
        bgImage = new ImageIcon(path).getImage(); // load image from file
        setLayout(new BorderLayout()); // set layout for panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this); // draw image full screen
    }
}

// Main UI class for Tic Tac Toe
public class TicTacToeUI {

    static TicTacToeBackend game = new TicTacToeBackend(); // backend game logic object
    static JButton[][] buttons = new JButton[3][3];
    static JLabel statusLabel;

    // 🔥 Constructor instead of main
    public TicTacToeUI() {

        JFrame frame = new JFrame("TIC TAC TOE"); // create window
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // changed so hub doesn't close

        BackgroundPanel bgPanel = new BackgroundPanel("images/tictactoe.png"); // background image panel
        frame.setContentPane(bgPanel);

        JLabel title = new JLabel("TIC TAC TOE", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));

        frame.add(title, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 10, 10)); // 3x3 grid layout
        gridPanel.setPreferredSize(new Dimension(400, 400)); // fixed size

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                int r = i, c = j;

                JButton btn = new JButton("");
                btn.setFont(new Font("Arial", Font.BOLD, 40));

                btn.addActionListener(e -> handleMove(r, c)); // handle click

                buttons[i][j] = btn; // store button
                gridPanel.add(btn); // add to grid
            }
        }

        JPanel centerWrapper = new JPanel(new GridBagLayout()); // wrapper to center grid
        centerWrapper.add(gridPanel);
        gridPanel.setOpaque(false); // transparent background
        centerWrapper.setOpaque(false);

        statusLabel = new JLabel("Player X Turn", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 30));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JButton resetBtn = new JButton("RESTART"); // restart button
        resetBtn.setPreferredSize(new Dimension(120, 40));
        resetBtn.setBackground(Color.RED);
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setFocusPainted(false);
        resetBtn.setFont(new Font("Arial", Font.BOLD, 14));

        resetBtn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true)); // rounded border
        resetBtn.addActionListener(e -> resetGame()); // reset action

        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonWrapper.add(resetBtn);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS)); // vertical layout
        bottom.add(statusLabel);
        bottom.add(buttonWrapper);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        buttonWrapper.setOpaque(false);
        bottom.setOpaque(false);

        frame.add(centerWrapper, BorderLayout.CENTER); // add grid center
        frame.add(bottom, BorderLayout.SOUTH); // add bottom section

        frame.setVisible(true); // show window
    }

    // Handles player move when a button is clicked
    static void handleMove(int r, int c) {

        if (!game.makeMove(r, c))
            return;

        char player = game.getCurrentPlayer();
        buttons[r][c].setText(String.valueOf(player));

        if (player == 'X') {
            buttons[r][c].setForeground(Color.RED);
        } else {
            buttons[r][c].setForeground(Color.BLUE);
        }

        if (game.checkWin()) {

            statusLabel.setText("PLAYER " + player + " WINS!");
            statusLabel.setForeground(new Color(34, 139, 34));

            statusLabel.setFont(new Font("Arial", Font.BOLD, 36));
            disableButtons();
        } else if (game.isDraw()) {
            statusLabel.setText("It's a Draw!");
            statusLabel.setForeground(Color.RED);

        } else {
            game.switchPlayer();
            statusLabel.setText("Player " + game.getCurrentPlayer() + " Turn");

        }
    }

    // Resets the game to initial state
    static void resetGame() {
        game.resetBoard();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setForeground(Color.BLACK);
            }

        statusLabel.setText("Player X Turn");
        statusLabel.setForeground(Color.BLACK);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 30));
    }

    // Disables all buttons after game ends
    static void disableButtons() {
        for (JButton[] row : buttons)
            for (JButton b : row)
                b.setEnabled(false); // disable button
    }
}