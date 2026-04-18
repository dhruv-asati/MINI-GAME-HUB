import java.awt.Rectangle;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class CarGameEngine implements Runnable, KeyListener {

    private CarGameUI.GamePanel panel;
    private CarGameUI ui;
    private volatile boolean running = true;

    private List<Enemy> enemies = new ArrayList<>();
    private Random rand = new Random();

    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private int spawnCooldown = 0;

    // 🔥 NEW: timing + scaling
    private long startTime;
    private int lastSecond = 0;
    private int difficultyLevel = 0;

    private double speedMultiplier = 1.0;

    public CarGameEngine(CarGameUI.GamePanel panel, CarGameUI ui) {
        this.panel = panel;
        this.ui = ui;

        panel.setEnemies(enemies);
        panel.addKeyListener(this);

        SwingUtilities.invokeLater(() -> panel.reset());

        startTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        try {
            while (running) {
                updateGame();
                panel.repaint();
                Thread.sleep(20);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void stop() {
        running = false;
    }

    private void updateGame() {

        updateScoreAndDifficulty(); // 🔥 NEW

        movePlayer();
        moveEnemies();
        spawnEnemies();
        checkCollision();
    }

    // 🔥 SCORING + DIFFICULTY SYSTEM
    private void updateScoreAndDifficulty() {

        int seconds = (int)((System.currentTimeMillis() - startTime) / 1000);

        // add score every second
        if (seconds > lastSecond) {
            panel.score += 20;
            lastSecond = seconds;
        }

        // increase difficulty every 10 sec
        int newLevel = seconds / 10;

        if (newLevel > difficultyLevel) {
            difficultyLevel = newLevel;

            // increase speed by 30%
            speedMultiplier *= 1.3;
        }
    }

    private void movePlayer() {

        int baseSpeed = 8;
        int moveSpeed = (int)(baseSpeed * speedMultiplier);

        if (leftPressed && panel.playerX > panel.roadLeft)
            panel.playerX -= moveSpeed;

        if (rightPressed && panel.playerX < panel.roadRight - 70)
            panel.playerX += moveSpeed;
    }

    private void moveEnemies() {
        for (Enemy e : enemies) {
            e.y += (int)(e.speed * speedMultiplier);
        }
        enemies.removeIf(e -> e.y > panel.getHeight());
    }

    private void spawnEnemies() {

        if (spawnCooldown > 0) {
            spawnCooldown--;
            return;
        }

        if (rand.nextInt(25) != 0) return;

        int laneCount = panel.laneCount;
        int roadWidth = panel.roadRight - panel.roadLeft;
        int laneWidth = roadWidth / laneCount;

        int carsInWave = 2 + rand.nextInt(2);

        List<Integer> lanes = new ArrayList<>();
        for (int i = 0; i < laneCount; i++) lanes.add(i);
        Collections.shuffle(lanes);

        for (int i = 0; i < carsInWave; i++) {

            int lane = lanes.get(i);

            int x = panel.roadLeft + lane * laneWidth + (laneWidth / 2) - 35;

            int y = -150 - rand.nextInt(300);

            enemies.add(new Enemy(x, y));
        }

        spawnCooldown = 35;
    }

    private void checkCollision() {
        Rectangle player = new Rectangle(panel.playerX, panel.playerY, 70, 100);

        for (Enemy e : enemies) {
            if (player.intersects(e.getBounds())) {
                endGame();
            }
        }
    }

    private void endGame() {
        if (!running) return;

        running = false;

        SwingUtilities.invokeLater(() -> {

            int option = JOptionPane.showConfirmDialog(
                    panel,
                    "Game Over! Score: " + panel.score + "\nPlay Again?",
                    "Game Over",
                    JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION)
                ui.restartGame();
            else
                ui.dispose();
        });
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    public void keyTyped(KeyEvent e) {}
}