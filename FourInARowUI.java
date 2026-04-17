import java.awt.*;
import javax.swing.*;

// UI class for 4 in a Row game
public class FourInARowUI {

    private JFrame frame; // main window
    private JPanel boardPanel; // panel containing grid
    private JLabel statusLabel; // shows current turn or winner
    private JButton restartButton; // restart button

    private final int ROWS = 6;
    private final int COLS = 7;

    private FourInARowBackend game = new FourInARowBackend(); // backend game logic
    private CirclePanel[][] cells = new CirclePanel[ROWS][COLS]; // grid cells

    private boolean gameOver = false; // stops moves after win

    public FourInARowUI() {

        frame = new JFrame("4 IN A ROW"); // create window
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // changed so hub doesn't close
        frame.setLocationRelativeTo(null); // center window
        frame.setLayout(new BorderLayout(10, 10));

        statusLabel = new JLabel("Player 1's Turn", SwingConstants.CENTER); // initial text
        statusLabel.setFont(new Font("Arial", Font.BOLD, 36));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(35, 0, 15, 0));
        frame.add(statusLabel, BorderLayout.NORTH);

        boardPanel = new JPanel(new GridLayout(ROWS, COLS, 10, 10));
        boardPanel.setBackground(new Color(20, 40, 120));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < ROWS * COLS; i++) {
            int row = i / COLS; // calculate row
            int col = i % COLS; // calculate column

            CirclePanel cell = new CirclePanel(); // create circular cell
            cells[row][col] = cell; // store reference

            int finalCol = col;

            // handle mouse click (drop piece in column)
            cell.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    handleMove(finalCol); // call move logic
                }
            });

            boardPanel.add(cell); // add to board
        }

        JPanel wrapper = new JPanel(new GridBagLayout()); // wrapper to center board
        wrapper.add(boardPanel);
        frame.add(wrapper, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        restartButton = new JButton("Restart"); // restart button

        // 🔥 BIG + RED BUTTON STYLE
        restartButton.setPreferredSize(new Dimension(160, 60));
        restartButton.setBackground(Color.RED);
        restartButton.setForeground(Color.WHITE);
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.setFocusPainted(false);

        bottomPanel.add(restartButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // restart button action
        restartButton.addActionListener(e -> {
            game.reset(); // reset backend
            gameOver = false; // allow moves again
            statusLabel.setText("Player 1's Turn"); // reset text
            statusLabel.setFont(new Font("Arial", Font.BOLD, 36)); // reset font
            statusLabel.setForeground(Color.BLACK);

            for (int i = 0; i < ROWS; i++)
                for (int j = 0; j < COLS; j++)
                    cells[i][j].setColor(Color.WHITE); // clear board
        });

        frame.setVisible(true); // display window
    }

    // handles move when user clicks a column
    private void handleMove(int col) {

        if (gameOver)
            return; // stop if game ended

        try { // 🔥 exception handling added

            int row = game.dropPiece(col); // drop piece in column

            CirclePanel cell = cells[row][col]; // get cell

            if (game.getCurrentPlayer() == 1) {
                cell.setColor(Color.RED); // player 1 = red
            } else {
                cell.setColor(Color.YELLOW); // player 2 = yellow
            }

            if (game.checkWin(row, col)) { // check win
                statusLabel.setText("PLAYER " + game.getCurrentPlayer() + " WINS!");
                statusLabel.setFont(new Font("Arial", Font.BOLD, 36));
                statusLabel.setForeground(new Color(34, 139, 34));
                gameOver = true; // stop game
            } else {
                game.switchPlayer(); // change turn
                statusLabel.setText("Player " + game.getCurrentPlayer() + "'s Turn"); // update text
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage()); // show error popup
        }
    }

    // custom panel to draw circular pieces
    class CirclePanel extends JPanel {

        private Color color = Color.WHITE; // default empty

        public CirclePanel() {
            setPreferredSize(new Dimension(80, 80)); // cell size
            setOpaque(false); // transparent background
        }

        public void setColor(Color c) {
            color = c; // set color
            repaint(); // redraw
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g; // cast for advanced drawing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // smooth edges

            int size = Math.min(getWidth(), getHeight()); // keep circle proportional

            g2.setColor(color); // set color
            g2.fillOval(
                    (getWidth() - size) / 2, // center horizontally
                    (getHeight() - size) / 2, // center vertically
                    size - 10, // width
                    size - 10 // height
            );
        }
    }

}