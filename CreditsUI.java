import javax.swing.*;
import java.awt.*;

public class CreditsUI {

    public CreditsUI() {

        JFrame frame = new JFrame("Credits");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ================= BACKGROUND =================
        JPanel background = new JPanel() {

            Image bg = loadImage("/images/bg.jpg");

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null) {
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        background.setLayout(new BorderLayout());
        frame.setContentPane(background);

        // ================= TITLE =================
        JLabel title = new JLabel("CREDITS", JLabel.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 90));
        title.setForeground(Color.BLACK);
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));

        background.add(title, BorderLayout.NORTH);

        // ================= CENTER =================
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        center.add(createSection("Developers",
                "C. TARUN VIGNESH   [BL.SC.U4CSE25106]",
                "DHRUV ASATI   [BL.SC.U4CSE25109]",
                "MOHD. ANAS   [BL.SC.U4CSE25118]",
                "AYUSH GUPTA   [BL.SC.U4CSE25158]"));

        center.add(Box.createRigidArea(new Dimension(0, 40)));

        center.add(createSection("Special Thanks",
                "Mentor :  Ms. B SARANYA DEVI",
                "Mole :  MOHAMMAD SHAHEER SHAIK"));

        background.add(center, BorderLayout.CENTER);

        // ================= BACK BUTTON =================
        JButton backBtn = new JButton("BACK");
        styleButton(backBtn, new Color(255, 255, 51));

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.add(backBtn);

        background.add(bottom, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }

    // ================= SECTION =================
    private JPanel createSection(String heading, String... names) {

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel head = new JLabel(heading, JLabel.CENTER);
        head.setFont(new Font("Arial", Font.BOLD, 44));
        head.setForeground(Color.BLACK);
        head.setAlignmentX(Component.CENTER_ALIGNMENT);
        head.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));

        panel.add(head);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        for (String name : names) {
            JLabel label = new JLabel(name, JLabel.CENTER);
            label.setFont(new Font("Trebuchet MS", Font.BOLD, 28));
            label.setForeground(Color.DARK_GRAY);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);
        }

        return panel;
    }

    // ================= IMAGE LOADER =================
    private Image loadImage(String path) {
        java.net.URL url = CreditsUI.class.getResource(path);
        if (url != null) {
            return new ImageIcon(url).getImage();
        }
        System.out.println("Missing: " + path);
        return null;
    }

    // ================= BUTTON STYLE =================
    private void styleButton(JButton b, Color bg) {

        b.setBackground(bg);
        b.setForeground(Color.BLACK);
        b.setFont(new Font("Algerian", Font.BOLD, 25));
        b.setFocusPainted(false);

        b.setPreferredSize(new Dimension(200, 60));

        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 3),
                BorderFactory.createLineBorder(Color.WHITE, 3)));
    }
}