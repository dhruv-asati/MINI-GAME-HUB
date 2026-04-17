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

    public CarGameEngine(CarGameUI.GamePanel panel, CarGameUI ui) {
        this.panel = panel;
        this.ui = ui;

        panel.setEnemies(enemies);
        panel.addKeyListener(this);

        SwingUtilities.invokeLater(() -> panel.reset());
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
            System.out.println("Game Loop Error: " + e.getMessage());
        }
    }

    public void stop() {
        running = false;
    }

    private void updateGame() {

        movePlayer();
        moveEnemies();
        spawnEnemies();
        checkCollision();

        panel.score++;
    }

    private void movePlayer() {

        int roadLeft = panel.getWidth() / 2 - 200;
        int roadRight = panel.getWidth() / 2 + 200 - 50;

        int moveSpeed = 8;

        if (leftPressed && panel.playerX > roadLeft) {
            panel.playerX -= moveSpeed;
        }

        if (rightPressed && panel.playerX < roadRight) {
            panel.playerX += moveSpeed;
        }
    }

    private void moveEnemies() {
        for (Enemy e : enemies) {
            e.move();
        }
        enemies.removeIf(e -> e.y > panel.getHeight());
    }

    private boolean isSafeToSpawn(int newX) {

        for (Enemy e : enemies) {

            // horizontal distance check
            int dx = Math.abs(e.x - newX);

            // vertical distance check (only near top)
            int dy = e.y;

            if (dy < 150 && dx < 80) {
                return false; // too close
            }
        }

        return true;
    }

    private void spawnEnemies() {

        if (rand.nextInt(25) != 0)
            return;

        int roadLeft = panel.getWidth() / 2 - 200;
        int roadRight = panel.getWidth() / 2 + 200 - 50;

        int x = roadLeft + rand.nextInt(roadRight - roadLeft);

        // 🔥 Check spacing before adding
        if (isSafeToSpawn(x)) {
            enemies.add(new Enemy(x, -100));
        }
        if (enemies.size() > 6)
            return;
    }

    private void checkCollision() {
        Rectangle player = new Rectangle(panel.playerX, panel.playerY, 50, 80);

        for (Enemy e : enemies) {
            if (player.intersects(e.getBounds())) {
                endGame();
            }
        }
    }

    private void endGame() {

        if (!running)
            return; // prevents multiple dialogs

        running = false;

        SwingUtilities.invokeLater(() -> {

            // check if window still visible
            if (!ui.isDisplayable()) {
                return;
            }

            int option = JOptionPane.showConfirmDialog(
                    panel,
                    "Game Over! Score: " + panel.score + "\nPlay Again?",
                    "Game Over",
                    JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                ui.restartGame();
            } else {
                ui.dispose();
            }
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}