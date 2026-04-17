// import javax.swing.*;
// import java.awt.*;

// public class TicTacToeUI {

//     public static void main(String[] args) {

//         JFrame frame = new JFrame("TIC TAC TOE");
//         frame.setSize(400, 500);
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setLayout(new BorderLayout(10, 10));

//         // 🔝 Title
//         JLabel title = new JLabel("TIC TAC TOE", JLabel.CENTER);
//         title.setFont(new Font("Arial", Font.BOLD, 26));
//         frame.add(title, BorderLayout.NORTH);

//         // 🎯 Grid (3x3)
//         JPanel gridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
//         gridPanel.setPreferredSize(new Dimension(300, 300)); // square

//         JButton[][] buttons = new JButton[3][3];

//         for (int i = 0; i < 3; i++) {
//             for (int j = 0; j < 3; j++) {

//                 JButton cell = new JButton("");
//                 cell.setFont(new Font("Arial", Font.BOLD, 40));
//                 cell.setFocusPainted(false);
//                 cell.setBackground(Color.WHITE);
//                 cell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

//                 buttons[i][j] = cell;
//                 gridPanel.add(cell);
//             }
//         }

//         // 🔥 Center the grid
//         JPanel wrapper = new JPanel(new GridBagLayout());
//         wrapper.add(gridPanel);

//         frame.add(wrapper, BorderLayout.CENTER);

//         // 🔻 Bottom Panel
//         JPanel bottomPanel = new JPanel(new BorderLayout());

//         JButton resetBtn = new JButton("RESET");
//         styleButton(resetBtn);

//         // 🔥 Wrap button to avoid full width
//         JPanel resetPanel = new JPanel();
//         resetPanel.add(resetBtn);

//         JLabel statusLabel = new JLabel("Player X Turn", JLabel.CENTER);
//         statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
//         statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

//         bottomPanel.add(resetPanel, BorderLayout.NORTH);
//         bottomPanel.add(statusLabel, BorderLayout.SOUTH);

//         frame.add(bottomPanel, BorderLayout.SOUTH);

//         frame.setVisible(true);
//     }

//     // 🎮 Button Style
//     static void styleButton(JButton b) {
//         b.setFont(new Font("Arial", Font.BOLD, 14));
//         b.setFocusPainted(false);
//         b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
//         b.setPreferredSize(new Dimension(100, 40));
//     }
// }
import javax.swing.*;
import java.awt.*;

public class TicTacToeUI {

    JFrame frame;

    public TicTacToeUI() {

        frame = new JFrame("TIC TAC TOE");
        // frame.setSize(400, 500);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 🔥 important
        frame.setLayout(new BorderLayout(10, 10));

        // 🔝 Title
        JLabel title = new JLabel("TIC TAC TOE", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        frame.add(title, BorderLayout.NORTH);

        // 🎯 Grid
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        gridPanel.setPreferredSize(new Dimension(300, 300));

        JButton[][] buttons = new JButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                JButton cell = new JButton("");
                cell.setFont(new Font("Arial", Font.BOLD, 40));
                cell.setFocusPainted(false);
                cell.setBackground(Color.WHITE);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

                buttons[i][j] = cell;
                gridPanel.add(cell);
            }
        }

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(gridPanel);
        frame.add(wrapper, BorderLayout.CENTER);

        // 🔻 Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JButton resetBtn = new JButton("RESET");
        styleButton(resetBtn);

        JPanel resetPanel = new JPanel();
        resetPanel.add(resetBtn);

        JLabel statusLabel = new JLabel("Player X Turn", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        bottomPanel.add(resetPanel, BorderLayout.NORTH);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null); // center
        frame.setVisible(true);
    }

    static void styleButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        b.setPreferredSize(new Dimension(100, 40));
    }
}