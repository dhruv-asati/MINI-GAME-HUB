import java.awt.*;
import java.util.List;
import javax.swing.*;

public class CarGameUI extends JFrame {

    public GamePanel panel;
    private CarGameEngine engine;
    private Thread gameThread;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CarGameUI::new);
    }

    public CarGameUI() {

        setTitle("Car Dodge 2D Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new GamePanel();
        add(panel);

        setVisible(true);

        startGame();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (engine != null)
                    engine.stop();
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

    // ================= PANEL =================
    static class GamePanel extends JPanel {

        public int playerX, playerY, score = 0;
        public List<Enemy> enemies;

        private Image bg;

        public int roadLeft, roadRight;
        public int laneCount = 5;

        private final int IMG_ROAD_LEFT = 256;
        private final int IMG_ROAD_RIGHT = 766;

        public GamePanel() {
            setFocusable(true);

            // 🔥 FIXED FOR EXE (IMPORTANT)
            java.net.URL url = getClass().getResource("/images/road.png");
            if (url != null) {
                bg = new ImageIcon(url).getImage();
            } else {
                System.out.println("ERROR: road.png not found in resources!");
            }
        }

        public void setEnemies(List<Enemy> enemies) {
            this.enemies = enemies;
        }

        public void reset() {
            score = 0;
            playerX = getWidth() / 2 - 35;
            playerY = getHeight() - 130;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (bg == null)
                return;

            Graphics2D g2 = (Graphics2D) g;

            int w = getWidth();
            int h = getHeight();

            int imgW = bg.getWidth(null);
            int imgH = bg.getHeight(null);

            double scale = Math.max((double) w / imgW, (double) h / imgH);

            int newW = (int) (imgW * scale);
            int newH = (int) (imgH * scale);

            int x = (w - newW) / 2;
            int y = (h - newH) / 2;

            g2.drawImage(bg, x, y, newW, newH, null);

            // ROAD BOUNDS
            roadLeft = x + (int) (IMG_ROAD_LEFT * scale);
            roadRight = x + (int) (IMG_ROAD_RIGHT * scale);

            int roadWidth = roadRight - roadLeft;
            int laneWidth = roadWidth / laneCount;

            g2.setColor(Color.WHITE);

            for (int i = 1; i < laneCount; i++) {
                int lx = roadLeft + i * laneWidth;

                for (int yy = 0; yy < h; yy += 35) {
                    g2.fillRect(lx, yy, 2, 18);
                }
            }

            drawPlayerCar(g2);

            if (enemies != null) {
                for (Enemy e : enemies)
                    e.draw(g2);
            }

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Verdana", Font.BOLD, 20));
            g2.drawString("Score: " + score, 20, 30);
        }

        private void drawPlayerCar(Graphics2D g2) {

            g2.setColor(Color.CYAN);
            g2.fillRoundRect(playerX, playerY, 70, 100, 15, 15);

            g2.setColor(Color.BLACK);
            g2.fillRoundRect(playerX + 12, playerY + 12, 45, 25, 10, 10);

            g2.fillOval(playerX + 8, playerY + 75, 15, 20);
            g2.fillOval(playerX + 47, playerY + 75, 15, 20);

            g2.setColor(Color.GREEN);
            g2.fillOval(playerX + 8, playerY + 6, 10, 10);
            g2.fillOval(playerX + 52, playerY + 6, 10, 10);
        }
    }
}