import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class WhackGamePanel extends JPanel {

    private final String GAME_ID = "whack";

    private Image bg;
    private Image mole;

    private Image idleHammer;
    private Image hitHammer;

    private int mouseX = 0, mouseY = 0;
    private boolean isClicking = false;

    private Cursor invisibleCursor;
    private Cursor defaultCursor;

    private java.util.List<int[]> holes = new ArrayList<>();
    private Set<Integer> activeHoles = new HashSet<>();
    private Random rand = new Random();

    private int score = 0;
    private int bestScore = ScoreManager.getBestScore(GAME_ID);
    private int timeLeft = 60;

    private boolean justHit = false;

    private javax.swing.Timer spawnTimer;
    private javax.swing.Timer gameTimer;

    private enum State {
        MENU, PLAYING, GAME_OVER
    }

    private State state = State.MENU;

    public WhackGamePanel(Image bgImage) {

        this.bg = bgImage;

        // ✅ FIXED IMAGE LOADING
        this.mole = loadImage("/images/mole.png");
        this.idleHammer = loadImage("/images/idle.png");
        this.hitHammer = loadImage("/images/hit.png");

        Toolkit tk = Toolkit.getDefaultToolkit();
        Image blank = tk.createImage(new byte[0]);

        invisibleCursor = tk.createCustomCursor(blank, new Point(0, 0), "blank");
        defaultCursor = Cursor.getDefaultCursor();

        setCursor(defaultCursor);

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                isClicking = true;

                if (state == State.PLAYING) {
                    handleClick(e.getX(), e.getY());
                }

                if (state == State.MENU &&
                        isInside(e.getX(), e.getY(), getWidth() / 2 - 100, getHeight() / 2 - 40, 200, 80)) {
                    startGame();
                }

                if (state == State.GAME_OVER &&
                        isInside(e.getX(), e.getY(), getWidth() / 2 - 120, getHeight() / 2 + 70, 100, 50)) {
                    startGame();
                }

                if (state == State.GAME_OVER &&
                        isInside(e.getX(), e.getY(), getWidth() / 2 + 20, getHeight() / 2 + 70, 100, 50)) {
                    System.exit(0);
                }

                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                isClicking = false;
                repaint();
            }
        });

        // HOLES (unchanged)
        holes.add(new int[] { 340, 230, 490, 310 });
        holes.add(new int[] { 570, 230, 720, 310 });
        holes.add(new int[] { 800, 230, 960, 310 });
        holes.add(new int[] { 1030, 230, 1190, 310 });

        holes.add(new int[] { 320, 380, 470, 470 });
        holes.add(new int[] { 560, 380, 720, 470 });
        holes.add(new int[] { 810, 380, 970, 470 });
        holes.add(new int[] { 1050, 380, 1220, 470 });

        holes.add(new int[] { 270, 550, 450, 650 });
        holes.add(new int[] { 540, 550, 720, 650 });
        holes.add(new int[] { 810, 550, 990, 650 });
        holes.add(new int[] { 1080, 550, 1270, 650 });

        spawnTimer = new javax.swing.Timer(800, e -> spawnMoles());

        gameTimer = new javax.swing.Timer(1000, e -> {
            if (timeLeft > 0) {
                if (justHit)
                    justHit = false;
                else
                    timeLeft--;
            } else {
                timeLeft = 0;
                endGame();
            }
            repaint();
        });
    }

    private void startGame() {
        score = 0;
        timeLeft = 60;
        state = State.PLAYING;

        setCursor(invisibleCursor);

        spawnTimer.start();
        gameTimer.start();
    }

    private void endGame() {
        state = State.GAME_OVER;

        setCursor(defaultCursor);

        spawnTimer.stop();
        gameTimer.stop();

        // ✅ FIXED AUDIO PATH
        SoundManager.playSFX("/audio/whackdead.wav");

        if (score > bestScore) {
            bestScore = score;
            ScoreManager.updateBestScore(GAME_ID, bestScore);
        }
    }

    private void spawnMoles() {

        activeHoles.clear();

        int count = 1 + rand.nextInt(3);

        java.util.List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < holes.size(); i++)
            indices.add(i);

        Collections.shuffle(indices);

        for (int i = 0; i < count; i++) {
            activeHoles.add(indices.get(i));
        }
    }

    private void handleClick(int x, int y) {

        for (int i : new ArrayList<>(activeHoles)) {

            int[] h = holes.get(i);

            if (x >= h[0] && x <= h[2] && y >= h[1] && y <= h[3]) {

                score += 50;
                timeLeft += 5;
                justHit = true;

                // ✅ FIXED AUDIO PATH
                SoundManager.playSFX("/audio/bonk.wav");

                activeHoles.remove(i);
                return;
            }
        }

        timeLeft = Math.max(0, timeLeft - 10);
    }

    private boolean isInside(int x, int y, int bx, int by, int w, int h) {
        return x >= bx && x <= bx + w && y >= by && y <= by + h;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bg != null)
            g.drawImage(bg, 0, 0, this);

        if (state == State.PLAYING) {
            for (int i : activeHoles) {
                int[] h = holes.get(i);
                if (mole != null)
                    g.drawImage(mole, h[0], h[1], h[2] - h[0], h[3] - h[1], this);
            }
        }

        Graphics2D g2 = (Graphics2D) g;

        if (state == State.MENU) {
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(0, 0, getWidth(), getHeight());
            drawButton(g2, "START", getWidth() / 2 - 100, getHeight() / 2 - 40, 200, 80);
        }

        else if (state == State.PLAYING) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 28));
            g.drawString("Score: " + score, 30, 85);
            g.drawString("Time: " + timeLeft, getWidth() - 180, 85);
        }

        else if (state == State.GAME_OVER) {
            g2.setColor(new Color(0, 0, 0, 170));
            g2.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", getWidth() / 2 - 140, getHeight() / 2 - 60);

            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("Score: " + score, getWidth() / 2 - 80, getHeight() / 2);
            g.drawString("Best: " + bestScore, getWidth() / 2 - 80, getHeight() / 2 + 30);

            drawButton(g2, "RESTART", getWidth() / 2 - 120, getHeight() / 2 + 70, 100, 50);
            drawButton(g2, "EXIT", getWidth() / 2 + 20, getHeight() / 2 + 70, 100, 50);
        }

        // HAMMER
        if (state == State.PLAYING) {
            Image current = isClicking ? hitHammer : idleHammer;

            if (current != null) {
                int w = 120, h = 120;
                int offsetX = 30;
                int offsetY = 70;

                g.drawImage(current, mouseX - offsetX, mouseY - offsetY, w, h, this);
            }
        }
    }

    private void drawButton(Graphics2D g, String text, int x, int y, int w, int h) {
        g.setColor(Color.WHITE);
        g.fillRoundRect(x, y, w, h, 20, 20);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        FontMetrics fm = g.getFontMetrics();
        int tx = x + (w - fm.stringWidth(text)) / 2;
        int ty = y + (h + fm.getAscent()) / 2 - 5;

        g.drawString(text, tx, ty);
    }

    // ================= SAFE IMAGE LOADER =================
    static Image loadImage(String path) {
        java.net.URL url = WhackGamePanel.class.getResource(path);
        if (url != null) {
            return new ImageIcon(url).getImage();
        }
        System.out.println("Missing: " + path);
        return null;
    }
}