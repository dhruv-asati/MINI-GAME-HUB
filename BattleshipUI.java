
import javax.swing.*;
import java.awt.*;

public class BattleshipUI {

    public BattleshipUI() {

        JFrame frame = new JFrame("BATTLESHIP GAME");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 🔥 important
        frame.setLayout(new BorderLayout(10, 10));

        // 🔝 TOP: Title
        JLabel title = new JLabel("BATTLESHIP GAME", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(title, BorderLayout.NORTH);

        // 🎯 CENTER: Two Grids
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 40, 10));

        JPanel playerGrid = createGrid("Your Board");
        JPanel enemyGrid = createGrid("Enemy Board");

        centerPanel.add(playerGrid);
        centerPanel.add(enemyGrid);

        frame.add(centerPanel, BorderLayout.CENTER);

        // 🔻 BOTTOM: Controls
        JPanel bottomPanel = new JPanel();

        JButton fireBtn = new JButton("FIRE");
        JButton resetBtn = new JButton("RESET");

        styleButton(fireBtn);
        styleButton(resetBtn);

        bottomPanel.add(fireBtn);
        bottomPanel.add(resetBtn);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null); // center
        frame.setVisible(true);
    }

    // 🟦 Grid creator (10x10)
    static JPanel createGrid(String titleText) {

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(titleText, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(label, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(10, 10, 2, 2));
        grid.setPreferredSize(new Dimension(500, 500));

        for (int i = 0; i < 100; i++) {
            JButton cell = new JButton();
            cell.setBackground(Color.CYAN);
            cell.setFocusPainted(false);
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            grid.add(cell);
        }

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(grid);

        mainPanel.add(wrapper, BorderLayout.CENTER);

        return mainPanel;
    }

    // 🎮 Button styling
    static void styleButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        b.setPreferredSize(new Dimension(100, 35));
    }
}