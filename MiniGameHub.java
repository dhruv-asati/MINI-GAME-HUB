import javax.swing.*;
import java.awt.*;

public class MiniGameHub {

    static JButton muteBtn;

    public static void main(String[] args) {

        JFrame frame = new JFrame("MINI GAME HUB");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================= BACKGROUND =================
        JPanel background = new JPanel() {

            Image img = loadImage("/images/bg.jpg");

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (img != null) {
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        background.setLayout(new BorderLayout());
        frame.setContentPane(background);

        // ================= TOP PANEL =================
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);

        ImageIcon soundIcon = scaleIcon("/images/sound.png");
        ImageIcon muteIcon = scaleIcon("/images/mute.png");

        muteBtn = new JButton(soundIcon);
        muteBtn.setFocusPainted(false);
        muteBtn.setBorderPainted(false);
        muteBtn.setContentAreaFilled(false);
        muteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        topPanel.add(muteBtn);

        // ================= TITLE =================
        JLabel title = new JLabel("MINI GAME HUB", JLabel.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 100));
        title.setForeground(Color.BLACK);
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setOpaque(false);
        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(title, BorderLayout.CENTER);

        background.add(northPanel, BorderLayout.NORTH);

        // ================= BUTTON PANEL =================
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 20, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));

        JButton b1 = new JButton("CAR DRIVING");
        JButton b2 = new JButton("HANGMAN");
        JButton b3 = new JButton("TIC TAC TOE");
        JButton b4 = new JButton("4 IN A LINE");
        JButton b5 = new JButton("BATTLESHIP");
        JButton b6 = new JButton("MEMORY CARDS");
        JButton b7 = new JButton("Whack A Shaheer");
        JButton b8 = new JButton("Credits");

        styleButton(b1, new Color(244, 116, 59));
        styleButton(b2, new Color(190, 238, 98));
        styleButton(b3, new Color(112, 174, 110));
        styleButton(b4, new Color(60, 110, 113));
        styleButton(b5, new Color(215, 235, 0));
        styleButton(b6, new Color(204, 208, 89));
        styleButton(b7, new Color(255, 0, 255));
        styleButton(b8, new Color(255, 255, 51));

        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.add(b3);
        buttonPanel.add(b4);
        buttonPanel.add(b5);
        buttonPanel.add(b6);
        buttonPanel.add(b7);
        buttonPanel.add(b8);

        background.add(buttonPanel, BorderLayout.CENTER);

        // ================= MUSIC =================
        SoundManager.playMusic("/audio/theme.wav");

        // ================= MUTE BUTTON =================
        muteBtn.addActionListener(e -> {

            SoundManager.muteMusic = !SoundManager.muteMusic;

            if (SoundManager.muteMusic) {
                SoundManager.stopMusic();
                muteBtn.setIcon(muteIcon);
            } else {
                SoundManager.playMusic("/audio/theme.wav");
                muteBtn.setIcon(soundIcon);
            }
        });

        frame.setVisible(true);

        // ================= GAME LAUNCHERS =================
        b1.addActionListener(e -> {
            SoundManager.stopMusic();
            new CarGameUI();
        });

        b2.addActionListener(e -> {
            SoundManager.stopMusic();
            new HangmanUI();
        });

        b3.addActionListener(e -> {
            SoundManager.stopMusic();
            new TicTacToeUI();
        });

        b4.addActionListener(e -> {
            SoundManager.stopMusic();
            new FourInARowUI();
        });

        b5.addActionListener(e -> {
            SoundManager.stopMusic();
            new BattleshipUI();
        });

        b6.addActionListener(e -> {
            SoundManager.stopMusic();
            new MemoryCardUI();
        });

        b7.addActionListener(e -> {
            SoundManager.stopMusic();
            new WhackMain();
        });

        b8.addActionListener(e -> {
            SoundManager.stopMusic();
            new CreditsUI();
        });
    }

    // ================= IMAGE LOADER =================
    static Image loadImage(String path) {
        java.net.URL url = MiniGameHub.class.getResource(path);
        if (url != null) {
            return new ImageIcon(url).getImage();
        }
        System.out.println("Missing: " + path);
        return null;
    }

    static ImageIcon scaleIcon(String path) {

        Image img = loadImage(path);

        if (img == null)
            return new ImageIcon();

        Image scaled = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    // ================= BUTTON STYLE =================
    static void styleButton(JButton b, Color bg) {

        b.setBackground(bg);
        b.setForeground(Color.BLACK);
        b.setFont(new Font("Algerian", Font.BOLD, 30));
        b.setFocusPainted(false);

        b.setPreferredSize(new Dimension(300, 80));

        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 3),
                BorderFactory.createLineBorder(Color.WHITE, 3)));
    }
}