import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class BackgroundPanel extends JPanel {
    private Image bgImage;

    public BackgroundPanel(String path) {
        bgImage = new ImageIcon(path).getImage();
        setLayout(new BorderLayout());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
}

public class TicTacToeUI {

    static TicTacToeBackend game = new TicTacToeBackend();
    static JLabel statusLabel;
    static TicTacToePanel boardPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeUI());
    }

    public TicTacToeUI() {

        JFrame frame = new JFrame("TIC TAC TOE");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel bgPanel = new BackgroundPanel("images/tictactoe.png");
        frame.setContentPane(bgPanel);

        // 🔥 TITLE
        JLabel title = new JLabel("TIC TAC TOE", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));
        frame.add(title, BorderLayout.NORTH);

        // 🔥 CENTER PANEL
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // 🔥 STATUS (SHIFTED DOWN PROPERLY)
        statusLabel = new JLabel("Player X Turn", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 34));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(40)); // 👈 pushes it DOWN from title
        centerPanel.add(statusLabel);
        centerPanel.add(Box.createVerticalStrut(10)); // 👈 keeps it CLOSE to board

        // 🔥 BOARD
        boardPanel = new TicTacToePanel();

        JPanel boardWrapper = new JPanel(new GridBagLayout());
        boardWrapper.setOpaque(false);
        boardWrapper.add(boardPanel);

        centerPanel.add(boardWrapper);
        frame.add(centerPanel, BorderLayout.CENTER);

        // 🔴 BUTTON
        JButton resetBtn = new JButton("RESTART");
        resetBtn.setPreferredSize(new Dimension(120, 40));
        resetBtn.setBackground(Color.RED);
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setFocusPainted(false);

        resetBtn.addActionListener(e -> resetGame());

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.add(resetBtn);

        frame.add(bottom, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    static void handleMove(int r, int c) {

        if (!game.makeMove(r, c))
            return;

        if (game.checkWin()) {
            statusLabel.setText("PLAYER " + game.getCurrentPlayer() + " WINS!");
            statusLabel.setForeground(new Color(34, 139, 34));
            boardPanel.repaint();
            return;
        }

        if (game.isDraw()) {
            statusLabel.setText("It's a Draw!");
            statusLabel.setForeground(Color.RED);
            boardPanel.repaint();
            return;
        }

        game.switchPlayer();
        statusLabel.setText("Player " + game.getCurrentPlayer() + " Turn");

        boardPanel.repaint();
    }

    static void resetGame() {
        game.resetBoard();
        statusLabel.setText("Player X Turn");
        statusLabel.setForeground(Color.BLACK);
        boardPanel.repaint();
    }

    static class TicTacToePanel extends JPanel {

        public TicTacToePanel() {
            setPreferredSize(new Dimension(400, 400));
            setOpaque(false);

            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int cell = getWidth() / 3;
                    int col = e.getX() / cell;
                    int row = e.getY() / cell;

                    handleMove(row, col);
                }
            });
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int cell = w / 3;

            // 🔥 BLACK GRID
            g2.setStroke(new BasicStroke(5));
            g2.setColor(Color.BLACK);

            g2.drawLine(cell, 0, cell, h);
            g2.drawLine(2 * cell, 0, 2 * cell, h);
            g2.drawLine(0, cell, w, cell);
            g2.drawLine(0, 2 * cell, w, 2 * cell);

            // 🔥 DRAW X/O
            char[][] board = game.getBoard();

            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {

                    char val = board[r][c];
                    if (val == '\0')
                        continue;

                    int x = c * cell;
                    int y = r * cell;

                    if (val == 'X') {
                        g2.setColor(new Color(220, 60, 60));
                        g2.setStroke(new BasicStroke(6));

                        g2.drawLine(x + 30, y + 30, x + cell - 30, y + cell - 30);
                        g2.drawLine(x + cell - 30, y + 30, x + 30, y + cell - 30);

                    } else {
                        g2.setColor(new Color(60, 100, 220));
                        g2.setStroke(new BasicStroke(6));

                        g2.drawOval(x + 30, y + 30, cell - 60, cell - 60);
                    }
                }
            }
        }
    }
}