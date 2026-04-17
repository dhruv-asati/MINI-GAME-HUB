import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CarGameUI extends JFrame {

    public GamePanel panel;
    private CarGameEngine engine;
    private Thread gameThread;

    public CarGameUI() {

        setTitle("Car Dodge Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new GamePanel();
        add(panel);

        setVisible(true);

        startGame();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (engine != null) {
                    engine.stop();
                }
            }
        });
    }

    public void startGame() {
        engine = new CarGameEngine(panel, this);
        gameThread = new Thread(engine);
        gameThread.start();
    }

    public void restartGame() {
        engine.stop();
        panel.reset();
        startGame();
    }

    // PANEL
    static class GamePanel extends JPanel {

        public int playerX;
        public int playerY;
        public int score = 0;

        public List<Enemy> enemies;

        public GamePanel() {
            setBackground(Color.BLACK);
            setFocusable(true);
        }

        public void setEnemies(List<Enemy> enemies) {
            this.enemies = enemies;
        }

        public void reset() {
            score = 0;
            playerX = getWidth() / 2 - 25;
            playerY = getHeight() - 120;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {
                Graphics2D g2 = (Graphics2D) g;

                int w = getWidth();
                int h = getHeight();

                int roadWidth = 400;
                int roadX = w / 2 - roadWidth / 2;

                // road
                g2.setColor(Color.DARK_GRAY);
                g2.fillRect(roadX, 0, roadWidth, h);

                // lane lines (visual only)
                g2.setColor(Color.WHITE);
                for (int i = 0; i < h; i += 40) {
                    g2.fillRect(roadX + roadWidth / 3, i, 10, 20);
                    g2.fillRect(roadX + 2 * roadWidth / 3, i, 10, 20);
                }

                drawPlayerCar(g2);

                if (enemies != null) {
                    for (Enemy e : enemies) {
                        e.draw(g2);
                    }
                }

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Verdana", Font.BOLD, 20));
                g2.drawString("Score: " + score, 20, 30);

            } catch (Exception e) {
                System.out.println("UI Error: " + e.getMessage());
            }
        }

        private void drawPlayerCar(Graphics2D g2) {

            g2.setColor(Color.CYAN);
            g2.fillRoundRect(playerX, playerY, 50, 80, 15, 15);

            g2.setColor(Color.BLACK);
            g2.fillRoundRect(playerX + 10, playerY + 10, 30, 20, 10, 10);

            g2.fillOval(playerX + 5, playerY + 60, 10, 15);
            g2.fillOval(playerX + 35, playerY + 60, 10, 15);

            g2.setColor(Color.GREEN);
            g2.fillOval(playerX + 5, playerY + 5, 8, 8);
            g2.fillOval(playerX + 37, playerY + 5, 8, 8);
        }
    }
}