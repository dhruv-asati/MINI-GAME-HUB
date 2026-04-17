import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;

public class MiniGameHub {

    static Clip clip; // global so we can control it later if needed

    public static void main(String[] args) {

        JFrame frame = new JFrame("MINI GAME HUB");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background Panel with Image
        JPanel background = new JPanel() {
            Image img = new ImageIcon("images/bg.jpg").getImage();

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };

        background.setLayout(new BorderLayout());
        frame.setContentPane(background);

        // ================= TITLE =================
        JLabel title = new JLabel("MINI GAME HUB", JLabel.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 100));
        title.setForeground(Color.BLACK);
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));

        background.add(title, BorderLayout.NORTH);

        // ================= BUTTON PANEL =================
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 20, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));

        // ================= BUTTONS =================
        JButton b1 = new JButton("CAR DRIVING");
        JButton b2 = new JButton("HANGMAN");
        JButton b3 = new JButton("TIC TAC TOE");
        JButton b4 = new JButton("4 IN A LINE");
        JButton b5 = new JButton("BATTLESHIP");
        JButton b6 = new JButton("MEMORY CARDS");

        styleButton(b1, new Color(244, 116, 59));
        styleButton(b2, new Color(190, 238, 98));
        styleButton(b3, new Color(112, 174, 110));
        styleButton(b4, new Color(60, 110, 113));
        styleButton(b5, new Color(215, 235, 0));
        styleButton(b6, new Color(204, 208, 89));

        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.add(b3);
        buttonPanel.add(b4);
        buttonPanel.add(b5);
        buttonPanel.add(b6);

        background.add(buttonPanel, BorderLayout.CENTER);

        // Play Music
        playMusic("audio/theme.wav");

        frame.setVisible(true);

        // making buttons clickable to their respective games
        b1.addActionListener(e -> new CarGameUI());
        b2.addActionListener(e -> new HangmanUI());
        b3.addActionListener(e -> new TicTacToeUI());
        b4.addActionListener(e -> new FourInARowUI());
        b5.addActionListener(e -> new BattleshipUI());
        b6.addActionListener(e -> new MemoryCardUI());

    }

    // ================= BUTTON STYLE =================
    static void styleButton(JButton b, Color bg) {
        b.setBackground(bg);
        b.setForeground(Color.BLACK);
        b.setFont(new Font("Algerian", Font.BOLD, 30));
        b.setFocusPainted(false);
        b.setOpaque(true);

        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 3),
                BorderFactory.createLineBorder(Color.WHITE, 3)));

        b.setPreferredSize(new Dimension(300, 80));
    }

    // MUSIC METHOD
    static void playMusic(String filePath) {
        try {
            File file = new File(filePath);
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);

            clip = AudioSystem.getClip();
            clip.open(audio);

            // 🔉 volume control (optional)
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-50.0f); // reduce volume

            clip.loop(Clip.LOOP_CONTINUOUSLY); // loop forever
            clip.start();

        } catch (Exception e) {
            System.out.println("Error playing music");
            e.printStackTrace();
        }
    }
}